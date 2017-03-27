package actions;

import board.Board;
import board.DoorTile;
import exeptions.BadBoardException;
import exeptions.DoorException;
import exeptions.MoveException;
import mecanics.Direction;
import mecanics.Reducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.Human;
import pieces.Player;

import java.awt.*;

import static junit.framework.TestCase.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OpenCloseDoorTest
{
	private Board board;
	private Player player;
	private OpenCloseDoor action;
	
	//run before each test
	@BeforeEach
	void setUp()
	{
		board = new Board(new Point(3,3));
		player = new Human("bla", Color.BLUE);
		board.addPiece(new Point(1, 1), player);
		action = new OpenCloseDoor(Direction.up);
		try
		{
			board.addDoor(new Point(1, 1), new Point(0, 1));
		}
		catch(BadBoardException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	void actionOpenDoor()
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
		
		DoorTile tile = null;
		try
		{
			tile = afterAction.getDoor(new Point(1, 1), new Point(0, 1));
		}
		catch(BadBoardException e)
		{
			e.printStackTrace();
			fail();
		}
		
		assertTrue("the door didn't open", tile.isOpen());
		assertEquals("the player used an action point but is didn't go down", 3, player.getActionPoints());
		
	}
	
	@Test
	void actionCloseDoor()
	{
		try
		{
			board.getDoor(new Point(1, 1), new Point(0, 1)).changeState();
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
		
		DoorTile tile = null;
		try
		{
			tile = afterAction.getDoor(new Point(1, 1), new Point(0, 1));
		}
		catch(BadBoardException e)
		{
			e.printStackTrace();
			fail();
		}
		assertFalse("the door didn't close", tile.isOpen());
		assertEquals("the player used an action point but is didn't go down", 3, player.getActionPoints());
		
	}
	
	@Test
	void actionOpenNothing() throws MoveException, BadBoardException
	{
		action = new OpenCloseDoor(Direction.down);
		assertThrows(DoorException.class, () ->
		{
			Reducer.doAction(player, action, board);
		});
	}
	
	@Test
	void actionOpenWall()
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
		
		assertThrows(DoorException.class, () ->
		{
			Reducer.doAction(player, action, board);
		});
	}
}
