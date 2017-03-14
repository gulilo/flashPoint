package actions;

import board.Board;
import board.Tile;
import board.WallTile;
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

public class MoveTest
{
	private Board board;
	private Player player;
	private Move action;
	
	//run before each test
	@BeforeEach
	void setUp()
	{
		board = new Board(new Point(3,3));
		
		player = new Human();
		board.addPiece(new Point(1, 1), player);
		action = new Move(Direction.up);
	}
	
	@Test
	void actionMoveTest()
	{
		Board afterAction = null;
		try
		{
			afterAction = Reducer.doAction(player, action, board);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}
		
		ArrayList<ArrayList<Piece>> change = ArraylistHelper.changes(((Tile) board.getTile(new Point(0, 1))).getPieces(), ((Tile) afterAction.getTile(new Point(0, 1))).getPieces());
		ArrayList<Piece> added = change.get(0);
		ArrayList<Piece> removed = change.get(1);
		assertEquals("the player didn't added to the new tile", 1, added.size());
		assertTrue("the player didn't added to the new tile", added.contains(player));
		assertEquals("something removed from the new tile", 0, removed.size());
		
		change = ArraylistHelper.changes(((Tile) board.getTile(new Point(1, 1))).getPieces(), ((Tile) afterAction.getTile(new Point(1, 1))).getPieces());
		added = change.get(0);
		removed = change.get(1);
		assertEquals("the player didn't removed from the old tile", 1, removed.size());
		assertTrue("the player didn't removed from the old tile", removed.contains(player));
		assertEquals("something added to the old tile", 0, added.size());
		
		assertEquals("the player used an action point but is didn't go down", 3, player.getActionPoints());
	}
	
	@Test
	void actionMoveOpenDoorTest()
	{
		try
		{
			board.addDoor(new Point(1, 1), new Point(0, 1)).changeState();
		}
		catch(BadBoardException e)
		{
			e.printStackTrace();
			fail();
		}
		
		Board afterAction = null;
		try
		{
			afterAction = Reducer.doAction(player, action, board);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}
		
		ArrayList<ArrayList<Piece>> change = ArraylistHelper.changes(((Tile) board.getTile(new Point(0, 1))).getPieces(), ((Tile) afterAction.getTile(new Point(0, 1))).getPieces());
		ArrayList<Piece> added = change.get(0);
		ArrayList<Piece> removed = change.get(1);
		assertEquals("the player didn't added to the new tile", 1, added.size());
		assertTrue("the player didn't added to the new tile", added.contains(player));
		assertEquals("something removed from the new tile", 0, removed.size());
		
		change = ArraylistHelper.changes(((Tile) board.getTile(new Point(1, 1))).getPieces(), ((Tile) afterAction.getTile(new Point(1, 1))).getPieces());
		added = change.get(0);
		removed = change.get(1);
		assertEquals("the player didn't removed from the old tile", 1, removed.size());
		assertTrue("the player didn't removed from the old tile", removed.contains(player));
		assertEquals("something added to the old tile", 0, added.size());
		
		assertEquals("the player used an action point but is didn't go down", 3, player.getActionPoints());
		
	}
	
	@Test
	void actionMoveCloseDoorTest() throws MoveException, BadBoardException
	{
		try
		{
			board.addDoor(new Point(1, 1), new Point(0, 1));
			
		}
		catch(BadBoardException e)
		{
			e.printStackTrace();
			fail();
		}
		assertThrows(MoveException.class, () ->
		{
			Reducer.doAction(player, action, board);
		});
	}
	
	@Test
	void actionMoveWallTest()
	{
		try
		{
			board.addWall(new Point(1, 1), new Point(0, 1));
		}
		catch(BadBoardException e)
		{
			e.printStackTrace();
			fail();
		}
		assertThrows(MoveException.class, () ->
		{
			Reducer.doAction(player, action, board);
		});
	}
	
	@Test
	void actionMoveDamagedWall()
	{
		try
		{
			board.addWall(new Point(1, 1), new Point(0, 1)).doDamage();
		}
		catch(BadBoardException | WallException e)
		{
			e.printStackTrace();
			fail();
		}
		assertThrows(MoveException.class, () ->
		{
			Reducer.doAction(player, action, board);
		});
	}
	
	@Test
	void actionMoveBrokenWall()
	{
		try
		{
			WallTile tile = board.addWall(new Point(1, 1), new Point(0, 1));
			tile.doDamage();
			tile.doDamage();
		}
		catch(BadBoardException | WallException e)
		{
			e.printStackTrace();
			fail();
		}
		Board afterAction = null;
		try
		{
			afterAction = Reducer.doAction(player, action, board);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}
		ArrayList<ArrayList<Piece>> change = ArraylistHelper.changes(((Tile) board.getTile(new Point(0, 1))).getPieces(), ((Tile) afterAction.getTile(new Point(0, 1))).getPieces());
		ArrayList<Piece> added = change.get(0);
		ArrayList<Piece> removed = change.get(1);
		assertEquals("the player didn't added to the new tile", 1, added.size());
		assertTrue("the player didn't added to the new tile", added.contains(player));
		assertEquals("something removed from the new tile", 0, removed.size());
		
		change = ArraylistHelper.changes(((Tile) board.getTile(new Point(1, 1))).getPieces(), ((Tile) afterAction.getTile(new Point(1, 1))).getPieces());
		added = change.get(0);
		removed = change.get(1);
		assertEquals("the player didn't removed from the old tile", 1, removed.size());
		assertTrue("the player didn't removed from the old tile", removed.contains(player));
		assertEquals("something added to the old tile", 0, added.size());
		
		assertEquals("the player used an action point but is didn't go down", 3, player.getActionPoints());
	}
	
	@Test
	void actionMoveVictimTest()
	{
		Victim victim = new Victim();
		board.addPiece(new Point(0, 1), victim);
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
		assertEquals("the player didn't added to the new tile", 1, added.size());
		assertTrue("the player didn't added to the new tile", added.contains(player));
		assertEquals("something removed from the new tile", 0, removed.size());
		
		change = ArraylistHelper.changes(((Tile) board.getTile(new Point(1, 1))).getPieces(), ((Tile) afterAction.getTile(new Point(1, 1))).getPieces());
		added = change.get(0);
		removed = change.get(1);
		assertEquals("the player didn't removed from the old tile", 1, removed.size());
		assertTrue("the player didn't removed from the old tile", removed.contains(player));
		assertEquals("something added to the old tile", 0, added.size());
		
		assertEquals("the player used an action point but is didn't go down", 3, player.getActionPoints());
	}
	
	@Test
	void actionMoveHiddenPoiTest()
	{
		HiddenPoi poi = null;
		try
		{
			poi = new HiddenPoi(Poi.type.falseAlarm);
		}
		catch(PoiException e)
		{
			e.printStackTrace();
			fail();
		}
		board.addPiece(new Point(0, 1), poi);
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
		
		System.out.println(((Tile) board.getTile(new Point(0, 1))).getPieces());
		System.out.println(((Tile) afterAction.getTile(new Point(0, 1))).getPieces());
		ArrayList<ArrayList<Piece>> change = ArraylistHelper.changes(((Tile) board.getTile(new Point(0, 1))).getPieces(), ((Tile) afterAction.getTile(new Point(0, 1))).getPieces());
		ArrayList<Piece> added = change.get(0);
		ArrayList<Piece> removed = change.get(1);
		assertEquals("the player didn't added to the new tile", 1, added.size());
		assertTrue("the player didn't added to the new tile", added.contains(player));
		assertEquals("something removed from the new tile", 1, removed.size());
		assertTrue("the player didn't added to the new tile", removed.contains(poi));
		
		
		change = ArraylistHelper.changes(((Tile) board.getTile(new Point(1, 1))).getPieces(), ((Tile) afterAction.getTile(new Point(1, 1))).getPieces());
		added = change.get(0);
		removed = change.get(1);
		assertEquals("the player didn't removed from the old tile", 1, removed.size());
		assertTrue("the player didn't removed from the old tile", removed.contains(player));
		assertEquals("something added to the old tile", 0, added.size());
		
		assertEquals("the player used an action point but is didn't go down", 3, player.getActionPoints());
	}
	
	@Test
	void actionLeftMoveTest()
	{
		action = new Move(Direction.left);
		Board afterAction = null;
		try
		{
			afterAction = Reducer.doAction(player, action, board);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}
		ArrayList<ArrayList<Piece>> change = ArraylistHelper.changes(((Tile) board.getTile(new Point(1, 0))).getPieces(), ((Tile) afterAction.getTile(new Point(1, 0))).getPieces());
		ArrayList<Piece> added = change.get(0);
		ArrayList<Piece> removed = change.get(1);
		assertEquals("the player didn't added to the new tile", 1, added.size());
		assertTrue("the player didn't added to the new tile", added.contains(player));
		assertEquals("something removed from the new tile", 0, removed.size());
		
		change = ArraylistHelper.changes(((Tile) board.getTile(new Point(1, 1))).getPieces(), ((Tile) afterAction.getTile(new Point(1, 1))).getPieces());
		added = change.get(0);
		removed = change.get(1);
		assertEquals("the player didn't removed from the old tile", 1, removed.size());
		assertTrue("the player didn't removed from the old tile", removed.contains(player));
		assertEquals("something added to the old tile", 0, added.size());
		
		assertEquals("the player used an action point but is didn't go down", 3, player.getActionPoints());
	}
	
	@Test
	void actionDownMoveTest()
	{
		action = new Move(Direction.down);
		Board afterAction = null;
		try
		{
			afterAction = Reducer.doAction(player, action, board);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}
		ArrayList<ArrayList<Piece>> change = ArraylistHelper.changes(((Tile) board.getTile(new Point(2, 1))).getPieces(), ((Tile) afterAction.getTile(new Point(2, 1))).getPieces());
		ArrayList<Piece> added = change.get(0);
		ArrayList<Piece> removed = change.get(1);
		assertEquals("the player didn't added to the new tile", 1, added.size());
		assertTrue("the player didn't added to the new tile", added.contains(player));
		assertEquals("something removed from the new tile", 0, removed.size());
		
		change = ArraylistHelper.changes(((Tile) board.getTile(new Point(1, 1))).getPieces(), ((Tile) afterAction.getTile(new Point(1, 1))).getPieces());
		added = change.get(0);
		removed = change.get(1);
		assertEquals("the player didn't removed from the old tile", 1, removed.size());
		assertTrue("the player didn't removed from the old tile", removed.contains(player));
		assertEquals("something added to the old tile", 0, added.size());
		
		assertEquals("the player used an action point but is didn't go down", 3, player.getActionPoints());
	}
	
	@Test
	void actionRightMoveTest()
	{
		action = new Move(Direction.right);
		Board afterAction = null;
		try
		{
			afterAction = Reducer.doAction(player, action, board);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}
		ArrayList<ArrayList<Piece>> change = ArraylistHelper.changes(((Tile) board.getTile(new Point(1, 2))).getPieces(), ((Tile) afterAction.getTile(new Point(1, 2))).getPieces());
		ArrayList<Piece> added = change.get(0);
		ArrayList<Piece> removed = change.get(1);
		assertEquals("the player didn't added to the new tile", 1, added.size());
		assertTrue("the player didn't added to the new tile", added.contains(player));
		assertEquals("something removed from the new tile", 0, removed.size());
		
		change = ArraylistHelper.changes(((Tile) board.getTile(new Point(1, 1))).getPieces(), ((Tile) afterAction.getTile(new Point(1, 1))).getPieces());
		added = change.get(0);
		removed = change.get(1);
		assertEquals("the player didn't removed from the old tile", 1, removed.size());
		assertTrue("the player didn't removed from the old tile", removed.contains(player));
		assertEquals("something added to the old tile", 0, added.size());
		
		assertEquals("the player used an action point but is didn't go down", 3, player.getActionPoints());
	}
}
