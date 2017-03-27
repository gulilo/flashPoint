package actions;

import board.*;
import exeptions.*;
import mecanics.ArraylistHelper;
import mecanics.Direction;
import mecanics.Reducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.*;

import java.awt.*;
import java.util.ArrayList;

import static junit.framework.TestCase.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ExtinguishTest
{
	private Board board;
	private Player player;
	private Extinguish action;
	
	//run before each test
	@BeforeEach
	void setUp()
	{
		board = new Board(new Point(3,3));
		
		player = new Human("bla", Color.BLUE);
		board.addPiece(new Point(1, 1), player);
		action = new Extinguish(Direction.up);
	}
	
	@Test
	void actionExtinguishSmoke()
	{
		Smoke smoke = new Smoke();
		board.addPiece(new Point(0,1),smoke);
		Board afterAction = null;
		try
		{
			afterAction = Reducer.doAction(player, action, board);
		}
		catch(ActionException | BadBoardException | PoiException | WallException e)
		{
			e.printStackTrace();
			fail();
		}
		
		
		ArrayList<ArrayList<Piece>> change = ArraylistHelper.changes(((Tile) board.getTile(new Point(0, 1))).getPieces(), ((Tile) afterAction.getTile(new Point(0, 1))).getPieces());
		ArrayList<Piece> added = change.get(0);
		ArrayList<Piece> removed = change.get(1);
		assertEquals("a piece removed that not supposed to", 1, removed.size());
		assertTrue("the smoke is still there", removed.contains(smoke));
		assertEquals("a piece added to the tile", 0, added.size());
		assertEquals("the player used an action point but is didn't go down", 3, player.getActionPoints());
	}
	
	@Test
	void actionExtinguishFire()
	{
		Fire fire = new Fire();
		board.addPiece(new Point(0,1),fire);
		Board afterAction = null;
		try
		{
			afterAction = Reducer.doAction(player, action, board);
		}
		catch(ActionException | BadBoardException | PoiException | WallException e)
		{
			e.printStackTrace();
			fail();
		}
		ArrayList<ArrayList<Piece>> change = ArraylistHelper.changes(((Tile) board.getTile(new Point(0, 1))).getPieces(), ((Tile) afterAction.getTile(new Point(0, 1))).getPieces());
		ArrayList<Piece> added = change.get(0);
		ArrayList<Piece> removed = change.get(1);
		assertEquals("a piece removed that not supposed to", 1, removed.size());
		assertTrue("the fire still there", removed.contains(fire));
		assertEquals("a piece added that not supposed to", 1, added.size());
		assertTrue("the smoke didn't added", ArraylistHelper.containsInstance(added, Smoke.class));
		assertEquals("the player used an action point but is didn't go down", 3, player.getActionPoints());
	}
	
	@Test
	void actionExtinguishNothing()
	{
		assertThrows(ExtinguishException.class, () ->
		{
			Reducer.doAction(player, action, board);
		});
	}
	
	@Test
	void actionExtinguishPlayer()
	{
		board.addPiece(new Point(0,1),new Human("bla", Color.BLUE));
		assertThrows(ExtinguishException.class, () ->
		{
			Reducer.doAction(player, action, board);
		});
	}
	
	@Test
	void actionExtinguishPoi()
	{
		try
		{
			board.addPiece(new Point(0,1),new HiddenPoi(Poi.type.victim));
		}
		catch(PoiException e)
		{
			e.printStackTrace();
			fail();
		}
		assertThrows(ExtinguishException.class, () ->
		{
			Reducer.doAction(player, action, board);
		});
	}
	
	@Test
	void actionExtinguishThroughWall()
	{
		try
		{
			board.addWall(new Point(1,1),new Point(0,1));
		}
		catch(BadBoardException e)
		{
			e.printStackTrace();
			fail();
		}
		
		assertThrows(ExtinguishException.class, () ->
		{
			Reducer.doAction(player, action, board);
		});
	}
	
	@Test
	void actionExtinguishDoor()
	{
		try
		{
			board.addDoor(new Point(1,1),new Point(0,1));
		}
		catch(BadBoardException e)
		{
			e.printStackTrace();
			fail();
		}
		assertThrows(ExtinguishException.class, () ->
		{
			Reducer.doAction(player, action, board);
		});
	}
	
	@Test
	void actionExtinguishStay()
	{
		action = new Extinguish(Direction.stay);
		Smoke smoke = new Smoke();
		board.addPiece(new Point(1,1),smoke);
		Board afterAction = null;
		try
		{
			afterAction = Reducer.doAction(player, action, board);
		}
		catch(ActionException | BadBoardException | PoiException | WallException e)
		{
			e.printStackTrace();
			fail();
		}
		
		
		ArrayList<ArrayList<Piece>> change = ArraylistHelper.changes(((Tile) board.getTile(new Point(1, 1))).getPieces(), ((Tile) afterAction.getTile(new Point(1, 1))).getPieces());
		ArrayList<Piece> added = change.get(0);
		ArrayList<Piece> removed = change.get(1);
		assertEquals("a piece removed that not supposed to", 1, removed.size());
		assertTrue("the smoke is still there", removed.contains(smoke));
		assertEquals("a piece added to the tile", 0, added.size());
		assertEquals("the player used an action point but is didn't go down", 3, player.getActionPoints());
	}
}
