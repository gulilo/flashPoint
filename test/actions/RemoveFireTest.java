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

public class RemoveFireTest
{
	private Board board;
	private Player player;
	private RemoveFire action;
	
	//run before each test
	@BeforeEach
	void setUp()
	{
		board = new Board(new Point(3,3));
		
		player = new Human(Color.BLUE);
		board.addPiece(new Point(1,1), player);
		action = new RemoveFire(Direction.up);
	}
	
	@Test
	void actionRemoveFireTest()
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
		assertEquals("something removed that didn't supposed to", 1, removed.size());
		assertTrue("fire didn't removed", removed.contains(fire));
		assertEquals("something added that didn't supposed to", 0, added.size());
		
		assertEquals("the player used action points but is didn't go down", 2, player.getActionPoints());
	}
	
	@Test
	void actionRemoveFireSmokeTest()
	{
		Smoke smoke = new Smoke();
		board.addPiece(new Point(0,1),smoke);
		assertThrows(RemoveFireException.class, () ->
		{
			Reducer.doAction(player, action, board);
		});
	}
	
	@Test
	void actionRemoveFireVictimTest()
	{
		Victim poi = new Victim();
		board.addPiece(new Point(0,1),poi);
		assertThrows(RemoveFireException.class, () ->
		{
			Reducer.doAction(player, action, board);
		});
	}
	
	@Test
	void actionRemoveFirePlayerTest()
	{
		Human otherPlayer = new Human(Color.BLUE);
		board.addPiece(new Point(0,1),otherPlayer);
		
		assertThrows(RemoveFireException.class, () ->
		{
			Reducer.doAction(player, action, board);
		});
	}
	
	@Test
	void actionRemoveFireNothingTest()
	{
		assertThrows(RemoveFireException.class, () ->
		{
			Reducer.doAction(player, action, board);
		});
	}
	
	@Test
	void actionRemoveFireThroughWallTest()
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
		assertThrows(RemoveFireException.class, () ->
		{
			Reducer.doAction(player, action, board);
		});
	}
	
	@Test
	void actionRemoveFireThroughDoorTest()
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
		assertThrows(RemoveFireException.class, () ->
		{
			Reducer.doAction(player, action, board);
		});
	}
}