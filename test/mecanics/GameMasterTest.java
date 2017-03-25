package mecanics;

import board.AbstractTile;
import board.Board;
import board.Tile;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;


class GameMasterTest
{
	
	AbstractTile[][] initBoard;// do i init here again or use the already initialized in board to chack the start game
	
	@Test
	void createGameMaster()
	{
		assertNotNull(GameMaster.getInstance());
	}
	
	@Test
	void startGameTest()
	{
		GameMaster gm = GameMaster.getInstance();
		gm.startGame(3);//start game with 3 players
		
		//assertEquals("board tiles not equals to the init state", initBoard , gm.getBoard().getBoard());
		assertEquals("there suppose to be 3 players", 3 ,gm.getPlayers().length);
		assertEquals("starting turn suppose to be 0",0,gm.getTurn());
		
	}
	
	@Test
	void placeFireTest()
	{
		GameMaster gm = GameMaster.getInstance(); // maybe this need to be in a setup function?
		AbstractTile[][] resumedGame = new AbstractTile[Board.BOARD_WIDTH][Board.BOARD_HEIGHT];
		
		for(int i = 0;i < Board.BOARD_WIDTH;i++)
		{
			for(int j = 0; j < Board.BOARD_HEIGHT;j++)
			{
				resumedGame[i][j] = new Tile();
			}
		}
		
		Dice dice = new Dice(1234); // find the real seed later
		//gm.placeFire(dice);
		
		//assertTrue("there suppose to be smoke in tile 2 2",((Tile)gm.getBoard().getBoard()[2][2]).getPiece() instanceof Smoke);
		
		// add more tests for placing fire on smoke and adjusted or on fire. according to the rules of the game.
}
	
	
	@Test
	void ReplenishPoiTest()
	{
		GameMaster gm = GameMaster.getInstance();
		AbstractTile[][] resumedGame = new AbstractTile[Board.BOARD_WIDTH][Board.BOARD_HEIGHT];
		
		for(int i = 0;i < Board.BOARD_WIDTH;i++)
		{
			for(int j = 0; j < Board.BOARD_HEIGHT;j++)
			{
				resumedGame[i][j] = new Tile();
			}
		}
		Dice dice = new Dice(1234); // find real seed later...
		//gm.replenishPoi(dice);
		//assertTrue("there suppose to be POI in tile 2 2", ((Tile)gm.getBoard().getBoard()[2][2]).getPiece() instanceof Poi);
	}
	
	@Test
	void GameOverTest()
	{
		
	}
	
}