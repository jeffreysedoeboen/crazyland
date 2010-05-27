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
	
	public Map(){
		try {
			originalimage = ImageIO.read(new File("tiles.png"));
			
			BufferedImage tile1 = originalimage.getSubimage(x, y, width, height);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
