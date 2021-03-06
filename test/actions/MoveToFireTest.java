package actions;

import board.*;
import exeptions.*;
import mecanics.ArraylistHelper;
import mecanics.Direction;
import mecanics.Reducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.Fire;
import pieces.Human;
import pieces.Piece;
import pieces.Player;

import java.awt.*;
import java.util.ArrayList;

import static junit.framework.TestCase.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MoveToFireTest
{
	private Board board;
	private Player player;
	private MoveToFire action;
	
	//run before each test
	@BeforeEach
	void setUp()
	{
		board = new Board(new Point(3,3));
		player = new Human("bla", Color.BLUE);
		board.addPiece(new Point(1,1),player);
		action = new MoveToFire(Direction.up);
	}
	
	@Test
	void actionMoveToFireTest()
	{
		Fire fire = new Fire();
		board.addPiece(new Point(0,1),fire);
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
		
		assertEquals("the player used an action point but is didn't go down", 2, player.getActionPoints());
	}
	
	@Test
	void actionMoveToFireOpenDoorTest()
	{
		try
		{
			board.addDoor(new Point(1,1), new Point(0,1)).changeState();
		}
		catch(BadBoardException e)
		{
			e.printStackTrace();
			fail();
		}
		Fire fire = new Fire();
		board.addPiece(new Point(0,1),fire);
		
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
		
		assertEquals("the player used an action point but is didn't go down", 2, player.getActionPoints());
		
	}
	
	@Test
	void actionMoveToFireCloseDoorTest()
	{
		board.addPiece(new Point(0,1),new Fire());
		try
		{
			board.addDoor(new Point(1,1),new Point(0,1));
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
	void actionMoveToFireWallTest()
	{
		board.addPiece(new Point(0,1),new Fire());
		try
		{
			board.addWall(new Point(1,1),new Point(0,1));
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
	void actionMoveToFireDamagedWall()
	{
		board.addPiece(new Point(0,1),new Fire());
		try
		{
			board.addWall(new Point(1,1),new Point(0,1)).doDamage();
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
	void actionMoveToFireBrokenWall()
	{
		Fire fire = new Fire();
		board.addPiece(new Point(0,1),fire);
		try
		{
			WallTile tile = board.addWall(new Point(1,1),new Point(0,1));
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
		AbstractTile[][] tiles = afterAction.getTiles();
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
		
		assertEquals("the player used an action point but is didn't go down", 2, player.getActionPoints());
	}
	
	@Test
	void actionMoveToFireVictimTest()
	{
		player.carryVictim();
		assertThrows(MoveToFireException.class, () ->
		{
			Reducer.doAction(player, action, board);
		});
	}
	
	@Test
	void actionMoveToFireNoPointsTest()
	{
		try
		{
			player.useActionPoints(1);
		}
		catch(ActionException e)
		{
			e.printStackTrace();
			fail();
		}
		assertThrows(MoveToFireException.class, () ->
		{
			Reducer.doAction(player, action, board);
		});
		
	}
}
