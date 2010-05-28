package test;

import static org.junit.Assert.*;

import java.awt.Point;

import model.Player;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {

	private Player player;
	
	@Before
	public void beforeTest(){
		player = new Player("Jan", new Point(2,2));
	}
	
	@Test
	public void testwalkLeft(){
		player.moveLeft();
		assertEquals(0,player.getPosition().getX(),0);
	}
	
	@Test
	public void testwalkRight(){
		player.moveRight();
		assertEquals(4,player.getPosition().getX(),0);
	}
}
