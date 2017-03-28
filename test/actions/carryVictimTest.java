package actions;

import board.Board;
import board.Tile;
import exeptions.*;
import mecanics.ArraylistHelper;
import mecanics.GameMaster;
import mecanics.Reducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.Piece;
import pieces.Player;
import pieces.Victim;

import java.awt.*;
import java.util.ArrayList;

import static junit.framework.TestCase.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class carryVictimTest
{
	Board board;
	private Player player;
	private CarryVictim action;
	
	//run before each test
	@BeforeEach
	void setUp()
	{
		GameMaster.getInstance().startGame(new String[]{"bla"}, new Color[]{Color.BLUE});
		board = new Board(new Point(3,3));
		
		player = GameMaster.getInstance().getCurrentPlayer();
		board.addPiece(new Point(1,1),player);
		action = new CarryVictim();
	}
	
	@Test
	void actionCarryVictim()
	{
		Victim victim = new Victim();
		board.addPiece(new Point(1,1),victim);
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
		
		assertEquals("something added to the tile", 0, added.size());
		assertEquals("something removed from the tile", 1, removed.size());
		assertTrue("the victim didn't removed from the tile", removed.contains(victim));
		assertTrue("the player is not carrying that victim", player.isCarry());
		assertEquals("the player didn't used any action point but it did go down", 4, player.getActionPoints());
	}
	
	@Test
	void actionCarryNoVictim()
	{
		assertThrows(CarryVictimException.class, () ->
		{
			Reducer.doAction(player, action, board);
		});
	}
}
