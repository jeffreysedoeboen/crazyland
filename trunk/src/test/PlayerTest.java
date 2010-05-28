package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.World;

public class PlayerTest {

	private World world;

	@Before
	public void beforeTest(){
		world = new World();
	}
	
	@Test
	public void testwalkLeft(){
		world.walkLeft();
		assertEquals(1,world.getPlayerPosition().getX(),0);
	}
	
	@Test
	public void testwalkRight(){
		world.walkRight();
		assertEquals(3,world.getPlayerPosition().getX(),0);
	}
}
