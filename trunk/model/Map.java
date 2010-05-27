package model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import model.tile.Tile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Map {
	private BufferedImage originalimage;
	private ArrayList<Tile> tiles;
	
	public Map(String imagesource) {
		originalimage = new BufferedImage();
	}

	private BufferedImage org;
	
	public Map(){
		try {
			org = ImageIO.read(new File("kaas.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
