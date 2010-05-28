package model;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

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
		Tile[][] tiles = new Tile[10][10];
		BufferedImage originalimage;
		try {
			originalimage = ImageIO.read(new File("../tiles/tiles.png"));
			ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
			for(int y = 0; y < 192; y += 32) {
				for(int x = 0; x < 224; x += 32) {
					images.add(originalimage.getSubimage(x, y, 32, 32));
				}
			}
			
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File("../maps/" + mapsource + ".tmx"));
            doc.getDocumentElement ().normalize ();

            NodeList listOfTiles = doc.getElementsByTagName("tile");
            
            int x = 0;
            int y = 0;
            for(int s = 0; s < listOfTiles.getLength(); s++) {
            	Node firstPersonNode = listOfTiles.item(s);
            	Element firstPersonElement = (Element)firstPersonNode;
            	
            	int tiletype = Integer.valueOf(firstPersonElement.getAttribute("gid")).intValue();
            	
            	if (tiletype != 0) {
            		tiles[x][y] = new Tile(x,y,images.get(tiletype - 1), true);
            	} else {
            		tiles[x][y] = new Tile(x,y,images.get(tiletype), false);
            	}
            	
            	x += 1;
            	if(x % 10 == 0) {
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
