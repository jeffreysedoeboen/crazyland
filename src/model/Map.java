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
	private Tile[][] tiles;
	
	public Map(){
		
	}
	
	public Tile[][] getTiles() {
		return tiles;
	}

	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}
	
	public static void main(String[] args) {
		Map map = new Map();
		map.setTiles(Mapfactory.getMap("map1"));
		map.tekenMap();
	}
	
	public void tekenMap() {
		for(Tile[] row : tiles) {
			for(Tile tile : row) {
				System.out.println("x: " + tile.getX() + ", y: " + tile.getY());
			}
		}
	}
}
