package actions;

import board.Board;
import board.Tile;
import exeptions.*;
import mecanics.ArraylistHelper;
import mecanics.Reducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.Human;
import pieces.Piece;
import pieces.Player;
import pieces.Victim;

import java.awt.*;
import java.util.ArrayList;

import static junit.framework.TestCase.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LeaveVictimTest
{
	Board board;
	private Player player;
	private LeaveVictim action;
	
	//run before each test
	@BeforeEach
	void setUp()
	{
		board = new Board(new Point(3, 3));
		player = new Human();
		board.addPiece(new Point(1,1),player);
		action = new LeaveVictim();
	}
	
	@Test
	void actionLeaveVictim()
	{
		player.carryVictim();
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
		
		assertEquals("the victim didn't added to the tile", 1, added.size());
		assertTrue("the victim didn't removed from the tile", ArraylistHelper.containsInstance(added, Victim.class));
		assertEquals("something removed from the tile that didn't supposed to", 0, removed.size());
		assertFalse("the player is not carrying that victim", player.isCarry());
		assertEquals("the player didn't used any action point but it did go down", 4, player.getActionPoints());
	}
	
	@Test
	void actionLeaveNoVictim()
	{
		assertThrows(LeaveVictimException.class, () ->
		{
			Reducer.doAction(player, action, board);
		});
	}
}
