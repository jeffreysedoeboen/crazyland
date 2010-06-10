package server.model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import server.model.tile.Tile;

public class Mapfactory {
/*	private static BufferedImage originalimage;*/
	
	/*
	 * Plaatje met alle subplaatjes wordt ingeladen
	 * XML-file met posities en eigenschappen van alle Tiles wordt uitgelezen
	 * subimages worden aangemaakt en in arraylist gezet
	 * Tiles worden aangemaakt en in tweedimensionale array gezet
	 * Deze array wordt teruggegeven
	 */
	public static Tile[][] getMap(String mapsource) {
		BufferedImage originalimage;
		Tile[][] tiles = null;
		try {
			originalimage = ImageIO.read(new File("tiles/tiles.png"));
			//ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
			ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
			
			
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File("maps/" + mapsource + ".tmx"));
            doc.getDocumentElement ().normalize ();
            
            NodeList mapNode = doc.getElementsByTagName("map");
            Element mapElement = (Element) mapNode.item(0);
            int width = Integer.valueOf(mapElement.getAttribute("width")).intValue();
            int height = Integer.valueOf(mapElement.getAttribute("height")).intValue();
            tiles = new Tile[width][height];
            
            /*NodeList tilesetNode = doc.getElementsByTagName("tileset");
			Element tilesetElement = (Element) tilesetNode.item(0);
			NodeList listOfProperties = tilesetElement.getElementsByTagName("tile");
            HashMap<Integer, Boolean> tileproperties = new HashMap<Integer, Boolean>();*/
            
            // create arraylist voor shape en corner
//            ArrayList<String> shapes = new ArrayList<String>();
//            ArrayList<String> corners = new ArrayList<String>();
//            ArrayList<Boolean> solids = new ArrayList<Boolean>();
            
			int counter = 0;
			for(int y = 0; y < 192; y += 32) {
				for(int x = 0; x < 224; x += 32) {
					BufferedImage img = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB); ;
					Graphics2D gr = img.createGraphics();
					gr.drawImage(originalimage.getSubimage(x, y, 32, 32), 0, 0, null);
					images.add(img);
					counter++;
				}
			}
			
            NodeList propertiesNodes = doc.getElementsByTagName("properties");
            String[][] propertyNodes = new String[propertiesNodes.getLength()][4];
            for(int i = 0; i < propertiesNodes.getLength(); i++) {
            	Node propertiesNode= propertiesNodes.item(i);
            	Element propertiesElement = (Element) propertiesNode;
            	NodeList listOfProperties = propertiesElement.getElementsByTagName("property");
            	System.out.println(i);
            	for(int s = 0; s < listOfProperties.getLength(); s++) {
            		Node propertyNode= listOfProperties.item(s);
            		if (propertyNode.getNodeType() == Node.ELEMENT_NODE) {
            			Element propertyElement = (Element) propertyNode;
                		String name = propertyElement.getAttribute("name");
                		
                		if(name.equals("solid")) {
                			propertyNodes[i][0] = propertyElement.getAttribute("value");
                			System.out.println("solid: " + propertyElement.getAttribute("value"));
                		} else if(name.equals("shape")) {
                			propertyNodes[i][1] = propertyElement.getAttribute("value");
                			System.out.println(propertyElement.getAttribute("value"));
                		} else if(name.equals("corner")) {
                			propertyNodes[i][2] = propertyElement.getAttribute("value");
                			System.out.println(propertyElement.getAttribute("value"));
                		} else if(name.equals("respawn")) {
                			propertyNodes[i][3] = propertyElement.getAttribute("value");
                			System.out.println("respawn: " + propertyElement.getAttribute("value"));
                		}
            		}
            	}
            	
            	
            }
            
            // get level data
			NodeList dataNode = doc.getElementsByTagName("data");
			Element dataElement = (Element) dataNode.item(0);
			NodeList listOfTilesData = dataElement.getElementsByTagName("tile");
			
            int x = 0;
            int y = 0;
            for(int s = 0; s < listOfTilesData.getLength(); s++) {
            	Node tileNode = listOfTilesData.item(s);
            	if (tileNode.getNodeType() == Node.ELEMENT_NODE) {
            		Element firstPersonElement = (Element)tileNode;
                	
            		//TODO: mooier oplossen
                	int tiletype = Integer.valueOf(firstPersonElement.getAttribute("gid")).intValue();
                	
                	int min = 0;
                	if (tiletype != 0) min = 1;
                	
                	boolean solid = (Integer.valueOf(propertyNodes[tiletype - min][0]).intValue() == 1);
                	String shape = propertyNodes[tiletype - min][1];
                	String corner = propertyNodes[tiletype - min][2];
                	boolean respawn = (Integer.valueOf(propertyNodes[tiletype - min][3]).intValue() != 0);
                	
                	tiles[x][y] = new Tile(x,y,images.get(tiletype - min), solid, shape, corner, respawn);
//                	if (tiletype != 0) {
//                		tiles[x][y] = new Tile(x,y,images.get(tiletype - 1), true, shape, corner);
//                	} else {
//                		tiles[x][y] = new Tile(x,y,images.get(tiletype), false, shape, corner);
//                	}
                	
            	}
            	x += 1;
            	if(x % width == 0) {
            		x = 0;
            		y += 1;
            	}
            }

		} catch (SAXParseException err) {
        System.out.println ("** Parsing error" + ", line " 
             + err.getLineNumber () + ", uri " + err.getSystemId ());
        System.out.println(" " + err.getMessage ());

        }catch (SAXException e) {
        Exception x = e.getException ();
        ((x == null) ? e : x).printStackTrace ();

        }catch (Throwable t) {
        t.printStackTrace ();
        }
        
        return tiles;
	}
}
