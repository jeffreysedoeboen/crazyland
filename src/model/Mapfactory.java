package model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import model.tile.Tile;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class Mapfactory {
/*	private static BufferedImage originalimage;*/
	
	public static Tile[][] getMap(String mapsource) {
		Tile[][] tiles = new Tile[50][10];
		BufferedImage originalimage;
		try {
			originalimage = ImageIO.read(new File("../tiles/tiles.png"));
			//ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
			ArrayList<Image> images = new ArrayList<Image>();
			
			
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File("../maps/" + mapsource + ".tmx"));
            doc.getDocumentElement ().normalize ();
            
            NodeList tilesetNode = doc.getElementsByTagName("tileset");
			Element tilesetElement = (Element) tilesetNode.item(0);
			NodeList listOfProperties = tilesetElement.getElementsByTagName("tile");
            HashMap<Integer, Boolean> tileproperties = new HashMap<Integer, Boolean>();
			int counter = 0;
			for(int y = 0; y < 192; y += 32) {
				for(int x = 0; x < 224; x += 32) {
					Node propertiesNode = listOfProperties.item(counter);
					if(propertiesNode != null) {
						if (propertiesNode.getNodeType() == Node.ELEMENT_NODE) {
							Element propertiesElement = (Element)propertiesNode;
			            	Node propertyNode = propertiesElement.getElementsByTagName("property").item(0);
			            	Element propertyElement = (Element)propertyNode;
			            	boolean tileproperty = Boolean.valueOf(propertyElement.getAttribute("solid")).booleanValue();
			            	tileproperties.put(counter, tileproperty);
						}
					}
					images.add(originalimage.getSubimage(x, y, 32, 32));
					System.out.println("Im Size: "+originalimage.getSubimage(x, y, 32, 32).getWidth());
					counter++;
				}
			}
			
			NodeList dataNode = doc.getElementsByTagName("data");
			Element dataElement = (Element) dataNode.item(0);
			NodeList listOfTiles = dataElement.getElementsByTagName("tile");
            int x = 0;
            int y = 0;
            for(int s = 0; s < listOfTiles.getLength(); s++) {
            	Node tileNode = listOfTiles.item(s);
            	if (tileNode.getNodeType() == Node.ELEMENT_NODE) {
            		Element firstPersonElement = (Element)tileNode;
                	
                	int tiletype = Integer.valueOf(firstPersonElement.getAttribute("gid")).intValue();
                	
                	if (tiletype != 0) {
                		tiles[x][y] = new Tile(x,y,images.get(tiletype - 1), true);
                	} else {
                		tiles[x][y] = new Tile(x,y,images.get(tiletype), false);
                	}
            	}
            	x += 1;
            	if(x % 50 == 0) {
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
