package actions;

import board.Board;
import board.WallTile;
import exeptions.*;
import mecanics.Direction;
import mecanics.Reducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.Human;
import pieces.Player;

import java.awt.*;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChopTest
{
	private Board board;
	private Player player;
	private Chop action;
	
	//run before each test
	@BeforeEach
	void setUp()
	{
		board = new Board(new Point(3,3));
		
		player = new Human("bla", Color.BLUE);
		board.addPiece(new Point(1, 1), player);
		action = new Chop(Direction.up);
		
		try
		{
			board.addWall(new Point(1, 1), new Point(0, 1));
		}
		catch(BadBoardException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	void chopWallTest()
	{
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
		
		WallTile tile = null;
		try
		{
			tile = afterAction.getWall(new Point(1, 1), new Point(0, 1));
		}
		catch(BadBoardException e)
		{
			e.printStackTrace();
			fail();
		}
		
		assertTrue("the wall is supposed to be damaged", tile.isDamage());
		assertEquals("the player used an action point but is didn't go down", 2, player.getActionPoints());
	}
	
	@Test
	void chopDamagedWallTest()
	{
		try
		{
			board.getWall(new Point(1, 1), new Point(0, 1)).doDamage();
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
		catch(ActionException | BadBoardException | PoiException | WallException e)
		{
			e.printStackTrace();
			fail();
		}
		
		WallTile tile = null;
		try
		{
			tile = afterAction.getWall(new Point(1, 1), new Point(0, 1));
		}
		catch(BadBoardException e)
		{
			e.printStackTrace();
			fail();
		}
		assertTrue("the wall is supposed to be broken", tile.isBroken());
		assertEquals("the player used an action point but is didn't go down", 2, player.getActionPoints());
	}
	
	@Test
	void chopBrokenWallTest()
	{
		WallTile tile = null;
		try
		{
			tile = board.getWall(new Point(1, 1), new Point(0, 1));
			tile.doDamage();
			tile.doDamage();
		}
		catch(BadBoardException | WallException e)
		{
			e.printStackTrace();
			fail();
		}
		
		assertThrows(WallException.class, () ->
		{
			Reducer.doAction(player, action, board);
		});
		
	}
	
	@Test
	void chopDoorTest()
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
		assertThrows(ChopException.class, () ->
		{
			Reducer.doAction(player, action, board);
		});
	}
	
	@Test
	void chopNothingTest()
	{
		action = new Chop(Direction.down);
		assertThrows(ChopException.class, () ->
		{
			Reducer.doAction(player, action, board);
		});
	}
}
