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
	private Tile[][] tiles;
	
	public Map(){
		try {
			originalimage = ImageIO.read(new File("tiles.png"));
			ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
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
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
