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
		player = new Player("Jan",2,2);
	}
	
	@Test
	public void testwalkLeftTrueX(){
		player.moveLeft(true);
		assertEquals(-0.5,player.getX(),0);
	}
	
	@Test
	public void testwalkLeftTrueY(){
		player.moveLeft(true);
		assertEquals(2,player.getY(),0);
	}
	
	@Test
	public void testwalkLeftFalseX(){
		player.moveLeft(false);
		assertEquals(0,player.getX(),0);
	}
	
	@Test
	public void testwalkLeftFalseY(){
		player.moveLeft(false);
		assertEquals(2,player.getY(),0);
	}
	
	@Test
	public void testwalkRightTrueX(){
		player.moveRight(true);
		assertEquals(4.5,player.getX(),0);
	}
	
	@Test
	public void testwalkRightTrueY(){
		player.moveRight(true);
		assertEquals(2,player.getY(),0);
	}
	
	@Test
	public void testwalkRightFalseX(){
		player.moveRight(false);
		assertEquals(4,player.getX(),0);
	}
	
	@Test
	public void testwalkRightFalseY(){
		player.moveRight(false);
		assertEquals(2,player.getY(),0);
	}
	
}
