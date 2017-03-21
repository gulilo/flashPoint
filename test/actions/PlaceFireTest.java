package actions;

import board.Board;
import board.WallTile;
import exeptions.ActionException;
import exeptions.BadBoardException;
import exeptions.PoiException;
import exeptions.WallException;
import mecanics.ArraylistHelper;
import mecanics.Reducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.*;

import java.awt.*;
import java.util.ArrayList;

import static junit.framework.TestCase.*;

public class PlaceFireTest
{
	private Board board;
	private PlaceFire action;
	
	//run before each test
	@BeforeEach
	void setUp()
	{
		board = new Board(new Point(5, 5));
		board.setAmbulance(new Point(4, 4));
		action = new PlaceFire(new Point(2, 2));
	}
	
	@Test
	void actionPlaceFireTile()
	{
		Board afterAction = null;
		try
		{
			afterAction = Reducer.doAction(null, action, board);
		}
		catch(ActionException | BadBoardException | PoiException | WallException e)
		{
			e.printStackTrace();
			fail();
		}
		ArrayList<ArrayList<Piece>> change = ArraylistHelper.changes((board.getTile(new Point(2, 2))).getPieces(), (afterAction.getTile(new Point(2, 2))).getPieces());
		ArrayList<Piece> added = change.get(0);
		ArrayList<Piece> removed = change.get(1);
		assertEquals("something added to the tile", 1, added.size());
		assertTrue("the smoke didn't added from the tile", ArraylistHelper.containsInstance(added, Smoke.class));
		assertEquals("something removed from the tile that didn't supposed to", 0, removed.size());
	}
	
	@Test
	void actionPlaceFireSmoke()
	{
		Smoke smoke = new Smoke();
		board.addPiece(new Point(2, 2), smoke);
		Board afterAction = null;
		try
		{
			afterAction = Reducer.doAction(null, action, board);
		}
		catch(ActionException | BadBoardException | PoiException | WallException e)
		{
			e.printStackTrace();
			fail();
		}
		
		ArrayList<ArrayList<Piece>> change = ArraylistHelper.changes((board.getTile(new Point(2, 2))).getPieces(), (afterAction.getTile(new Point(2, 2))).getPieces());
		ArrayList<Piece> added = change.get(0);
		ArrayList<Piece> removed = change.get(1);
		assertEquals("something added to the tile", 1, added.size());
		assertTrue("the victim didn't removed from the tile", ArraylistHelper.containsInstance(added, Fire.class));
		assertEquals("something removed from the tile that didn't supposed to", 1, removed.size());
		assertTrue("the victim didn't removed from the tile", removed.contains(smoke));
	}
	
	@Test
	void actionPlaceFireAdjacentFire()
	{
		Fire fire = new Fire();
		board.addPiece(new Point(1, 2), fire);
		Board afterAction = null;
		try
		{
			afterAction = Reducer.doAction(null, action, board);
		}
		catch(ActionException | BadBoardException | PoiException | WallException e)
		{
			e.printStackTrace();
			fail();
		}
		ArrayList<ArrayList<Piece>> change = ArraylistHelper.changes((board.getTile(new Point(2, 2))).getPieces(), (afterAction.getTile(new Point(2, 2))).getPieces());
		ArrayList<Piece> added = change.get(0);
		ArrayList<Piece> removed = change.get(1);
		assertEquals("something added to the tile", 1, added.size());
		assertTrue("the victim didn't removed from the tile", ArraylistHelper.containsInstance(added, Fire.class));
		assertEquals("something removed from the tile that didn't supposed to", 0, removed.size());
	}
	
	@Test
	void actionPlaceFireExplosion()
	{
		Fire fire = new Fire();
		board.addPiece(new Point(2, 2), fire);
		Smoke smoke = new Smoke();
		Human human = new Human(Color.BLUE);
		Victim victim = new Victim();
		WallTile wall = null;
		try
		{
			wall = board.addWall(new Point(2, 2), new Point(1, 2));
		}
		catch(BadBoardException e)
		{
			e.printStackTrace();
			fail();
		}
		board.addPiece(new Point(2, 3), smoke);
		board.addPiece(new Point(2, 1), human);
		board.addPiece(new Point(3, 2), victim);
		Board afterAction = null;
		try
		{
			afterAction = Reducer.doAction(null, action, board);
		}
		catch(ActionException | BadBoardException | PoiException | WallException e)
		{
			e.printStackTrace();
			fail();
		}
		ArrayList<ArrayList<Piece>> change = ArraylistHelper.changes((board.getTile(new Point(2, 2))).getPieces(), (afterAction.getTile(new Point(2, 2))).getPieces());
		ArrayList<Piece> added = change.get(0);
		ArrayList<Piece> removed = change.get(1);
		assertEquals("something added to the tile", 0, added.size());
		assertEquals("something removed from the tile that didn't supposed to", 0, removed.size());
		
		change = ArraylistHelper.changes((board.getTile(new Point(2, 3))).getPieces(), (afterAction.getTile(new Point(2, 3))).getPieces());
		added = change.get(0);
		removed = change.get(1);
		assertEquals("something added to the tile", 1, added.size());
		assertTrue("the fire didn't added to the tile", ArraylistHelper.containsInstance(added, Fire.class));
		assertEquals("something removed from the tile that didn't supposed to", 1, removed.size());
		assertTrue("the smoke didn't removed from the tile", ArraylistHelper.containsInstance(removed, Smoke.class));
		
		change = ArraylistHelper.changes((board.getTile(new Point(2, 1))).getPieces(), (afterAction.getTile(new Point(2, 1))).getPieces());
		added = change.get(0);
		removed = change.get(1);
		assertEquals("something added to the tile", 1, added.size());
		assertTrue("the fire didn't added to the tile", ArraylistHelper.containsInstance(added, Fire.class));
		assertEquals("something removed from the tile that didn't supposed to", 1, removed.size());
		assertTrue("the player didn't removed from the tile", ArraylistHelper.containsInstance(removed, Player.class));
		
		change = ArraylistHelper.changes((board.getTile(new Point(4, 4))).getPieces(), (afterAction.getTile(new Point(4, 4))).getPieces());
		added = change.get(0);
		removed = change.get(1);
		assertEquals("something added to the tile", 1, added.size());
		assertTrue("the fire didn't added to the tile", added.contains(human));
		assertEquals("something removed from the tile that didn't supposed to", 0, removed.size());
		
		change = ArraylistHelper.changes((board.getTile(new Point(3, 2))).getPieces(), (afterAction.getTile(new Point(3, 2)).getPieces()));
		added = change.get(0);
		removed = change.get(1);
		assertEquals("something added to the tile", 1, added.size());
		assertTrue("the fire didn't added to the tile", ArraylistHelper.containsInstance(added, Fire.class));
		assertEquals("something removed from the tile that didn't supposed to", 1, removed.size());
		assertTrue("the victim didn't removed from the tile", ArraylistHelper.containsInstance(removed, Victim.class));
		
		try
		{
			assertTrue("the wall didn't got damage", afterAction.getWall(new Point(2,2),new Point(1,2)).isDamage());
		}
		catch(BadBoardException e)
		{
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	void actionPlaceFireShockwave() throws WallException
	{
		board.addPiece(new Point(2, 2), new Fire());
		board.addPiece(new Point(1, 2), new Fire());
		board.addPiece(new Point(2, 1), new Fire());
		board.addPiece(new Point(3, 2), new Fire());
		board.addPiece(new Point(2, 3), new Fire());
		WallTile wall = null;
		try
		{
			wall = board.addWall(new Point(1, 2), new Point(0, 2));
			board.addDoor(new Point(3, 2), new Point(4, 2));
			board.addDoor(new Point(2,1),new Point(2,0)).changeState();
		}
		catch(BadBoardException e)
		{
			e.printStackTrace();
			fail();
		}
		try
		{
			wall.doDamage();
			wall.doDamage();
		}
		catch(WallException e)
		{
			e.printStackTrace();
			fail();
		}
		Board afterAction = null;
		try
		{
			afterAction = Reducer.doAction(null, action, board);
		}
		catch(ActionException | BadBoardException | PoiException e)
		{
			e.printStackTrace();
			fail();
		}
		
		ArrayList<ArrayList<Piece>> change = ArraylistHelper.changes((board.getTile(new Point(2, 2))).getPieces(), (afterAction.getTile(new Point(2, 2))).getPieces());
		ArrayList<Piece> added = change.get(0);
		ArrayList<Piece> removed = change.get(1);
		assertEquals("something added to the tile", 0, added.size());
		assertEquals("something removed from the tile that didn't supposed to", 0, removed.size());
		
		change = ArraylistHelper.changes((board.getTile(new Point(0, 2))).getPieces(), (afterAction.getTile(new Point(0, 2)).getPieces()));
		added = change.get(0);
		removed = change.get(1);
		assertEquals("something added to the tile", 1, added.size());
		assertTrue("the fire didn't added to the tile", ArraylistHelper.containsInstance(added, Fire.class));
		assertEquals("something removed from the tile that didn't supposed to", 0, removed.size());
		
		change = ArraylistHelper.changes((board.getTile(new Point(2, 4))).getPieces(), (afterAction.getTile(new Point(2, 4)).getPieces()));
		added = change.get(0);
		removed = change.get(1);
		assertEquals("something added to the tile", 1, added.size());
		assertTrue("the fire didn't added to the tile", ArraylistHelper.containsInstance(added, Fire.class));
		assertEquals("something removed from the tile that didn't supposed to", 0, removed.size());
		
		change = ArraylistHelper.changes((board.getTile(new Point(2, 0))).getPieces(), (afterAction.getTile(new Point(2, 0)).getPieces()));
		added = change.get(0);
		removed = change.get(1);
		assertEquals("something added to the tile", 1, added.size());
		assertTrue("the fire didn't added to the tile", ArraylistHelper.containsInstance(added, Fire.class));
		assertEquals("something removed from the tile that didn't supposed to", 0, removed.size());
		
		try
		{
			assertTrue("the door didn't got damage", afterAction.getDoor(new Point(3,2),new Point(4,2)).isBroken());
		}
		catch(BadBoardException e)
		{
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	void actionPlaceFireFlashOver() throws WallException
	{
		board.addPiece(new Point(2,2),new Smoke());
		try
		{
			board.addDoor(new Point(2,2),new Point(2,1)).changeState();
			board.addWall(new Point(2,2),new Point(3,2));
		}
		catch(BadBoardException e)
		{
			e.printStackTrace();
			fail();
		}
		board.addPiece(new Point(2,1),new Smoke());
		board.addPiece(new Point(3,2),new Smoke());
		board.addPiece(new Point(2,3),new Smoke());
		board.addPiece(new Point(2,4),new Smoke());
		
		Board afterAction = null;
		try
		{
			afterAction = Reducer.doAction(null, action, board);
		}
		catch(ActionException | BadBoardException | PoiException e)
		{
			e.printStackTrace();
			fail();
		}
		ArrayList<ArrayList<Piece>> change = ArraylistHelper.changes((board.getTile(new Point(2, 2))).getPieces(), (afterAction.getTile(new Point(2, 2))).getPieces());
		ArrayList<Piece> added = change.get(0);
		ArrayList<Piece> removed = change.get(1);
		assertEquals("something added to the tile", 1, added.size());
		assertTrue("the fire didn't added to the tile", ArraylistHelper.containsInstance(added, Fire.class));
		assertEquals("something removed from the tile that didn't supposed to", 1, removed.size());
		assertTrue("the fire didn't added to the tile", ArraylistHelper.containsInstance(removed, Smoke.class));
		
		
		change = ArraylistHelper.changes((board.getTile(new Point(2, 1))).getPieces(), (afterAction.getTile(new Point(2, 1)).getPieces()));
		added = change.get(0);
		removed = change.get(1);
		assertEquals("something added to the tile", 1, added.size());
		assertTrue("the fire didn't added to the tile", ArraylistHelper.containsInstance(added, Fire.class));
		assertEquals("something removed from the tile that didn't supposed to", 1, removed.size());
		assertTrue("the fire didn't added to the tile", ArraylistHelper.containsInstance(removed, Smoke.class));
		
		change = ArraylistHelper.changes((board.getTile(new Point(2, 3))).getPieces(), (afterAction.getTile(new Point(2, 3)).getPieces()));
		added = change.get(0);
		removed = change.get(1);
		assertEquals("something added to the tile", 1, added.size());
		assertTrue("the fire didn't added to the tile", ArraylistHelper.containsInstance(added, Fire.class));
		assertEquals("something removed from the tile that didn't supposed to", 1, removed.size());
		assertTrue("the fire didn't added to the tile", ArraylistHelper.containsInstance(removed, Smoke.class));
		
		change = ArraylistHelper.changes((board.getTile(new Point(2, 4))).getPieces(), (afterAction.getTile(new Point(2, 4)).getPieces()));
		added = change.get(0);
		removed = change.get(1);
		assertEquals("something added to the tile", 1, added.size());
		assertTrue("the fire didn't added to the tile", ArraylistHelper.containsInstance(added, Fire.class));
		assertEquals("something removed from the tile that didn't supposed to", 1, removed.size());
		assertTrue("the fire didn't added to the tile", ArraylistHelper.containsInstance(removed, Smoke.class));
		
		change = ArraylistHelper.changes((board.getTile(new Point(3, 2))).getPieces(), (afterAction.getTile(new Point(3, 2)).getPieces()));
		added = change.get(0);
		removed = change.get(1);
		assertEquals("something added to the tile", 0, added.size());
		assertEquals("something removed from the tile that didn't supposed to", 0, removed.size());
	}
}
