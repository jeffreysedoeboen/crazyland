package model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import model.tile.Tile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class Map {
	private BufferedImage originalimage;
	private Tile[][] tiles;
	
	public Map(){
		try {
			originalimage = ImageIO.read(new File("tiles/grass_main.png"));
			ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
			tiles = new Tile[20][20];
			images.add(originalimage.getSubimage(0, 0, 32, 32));
			images.add(originalimage.getSubimage(0, 32, 32, 32));
			images.add(originalimage.getSubimage(0, 64, 32, 32));
			images.add(originalimage.getSubimage(0, 96, 32, 32));
			images.add(originalimage.getSubimage(32, 32, 32, 32));
			images.add(originalimage.getSubimage(32, 64, 32, 32));
			images.add(originalimage.getSubimage(32, 96, 32, 32));
			images.add(originalimage.getSubimage(64, 32, 32, 32));
			images.add(originalimage.getSubimage(64, 64, 32, 32));
			images.add(originalimage.getSubimage(64, 96, 32, 32));
			images.add(originalimage.getSubimage(96, 32, 32, 32));
			images.add(originalimage.getSubimage(96, 64, 32, 32));
			images.add(originalimage.getSubimage(96, 96, 32, 32));
			
			for(BufferedImage image : images) {
				this.tiles[1][1] = new Tile(1,1,image, true);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		 Mapfactory.getMap("map1");
	}
	
	
}
