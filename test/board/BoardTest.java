package board;

import exeptions.BadBoardException;
import exeptions.WallException;
import mecanics.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.*;

import static junit.framework.TestCase.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BoardTest
{
	Board board;
	@BeforeEach
	void setup()
	{
		AbstractTile[][] tiles = new AbstractTile[5][5];
		tiles[0][0] = new Tile();
		tiles[0][2] = new Tile();
		tiles[0][0] = new Tile();
		tiles[0][4] = new Tile();
		tiles[2][0] = new Tile();
		tiles[2][2] = new Tile();
		tiles[2][4] = new Tile();
		tiles[4][0] = new Tile();
		tiles[4][2] = new Tile();
		tiles[4][4] = new Tile();
		board = new Board(tiles);
	}
	
	
	@Test
	void numberOfWallDamage()
	{
		
	}
	
	@Test
	void getAvailableActions()
	{
		
	}
	
	//getAmbulance Test
	
	@Test
	void getAmbulance()
	{
		board.setAmbulance(new Point(2,2));
		AmbulanceTile ans = null;
		try
		{
			ans = board.getAmbulance(new Point(1,1));
		}
		catch(BadBoardException e)
		{
			e.printStackTrace();
			fail();
		}
		assertEquals("got the wrong tile", board.getTile(new Point(2,2)),ans);
	}
	
	@Test
	void getAmbulanceNoAmbulance()
	{
		assertThrows(BadBoardException.class,()->board.getAmbulance(new Point(1,1)));
	}
	
	//getTileInDirection Test
	@Test
	void getTileInDirection()
	{
		
		Point ans = Board.getTileInDirection(new Point(2,2), Direction.up,board);
		assertEquals("got the wrong tile", new Point(1,2),ans);
	}
	
	@Test
	void getTileInDirectionThroughOpenDoor()
	{
		AbstractTile[][] tiles = new AbstractTile[5][5];
		for(int i = 0;i< tiles.length;i++)
		{
			for(int j = 0;j<tiles[0].length;j++)
			{
				tiles[i][j] = new Tile();
			}
		}
		tiles[1][2] = new DoorTile();
		((DoorTile)tiles[1][2]).changeState();
		Point ans = Board.getTileInDirection(new Point(2,2), Direction.up,board);
		assertNotNull("got null",ans);
		assertEquals("got the wrong tile", new Point(0,2),ans);
	}
	@Test
	void getTileInDirectionThroughClosedDoor()
	{
		AbstractTile[][] tiles = new AbstractTile[5][5];
		for(int i = 0;i< tiles.length;i++)
		{
			for(int j = 0;j<tiles[0].length;j++)
			{
				tiles[i][j] = new Tile();
			}
		}
		tiles[1][2] = new DoorTile();
		Point ans = Board.getTileInDirection(new Point(2,2), Direction.up,board);
		assertNull("got though closed door",ans);
	}
	
	
	@Test
	void getTileInDirectionThroughBrokenWall()
	{
		AbstractTile[][] tiles = new AbstractTile[5][5];
		for(int i = 0;i< tiles.length;i++)
		{
			for(int j = 0;j<tiles[0].length;j++)
			{
				tiles[i][j] = new Tile();
			}
		}
		tiles[1][2] = new WallTile();
		try
		{
			((WallTile)tiles[1][2]).doDamage();
			((WallTile)tiles[1][2]).doDamage();
		}
		catch(WallException e)
		{
			e.printStackTrace();
			fail();
		}
		Point ans = Board.getTileInDirection(new Point(2,2), Direction.up,board);
		assertNotNull("got null",ans);
		assertEquals("got the wrong tile",new Point(0,2),ans);
	}
	
	@Test
	void getTileInDirectionThroughDamagedWall()
	{
		AbstractTile[][] tiles = new AbstractTile[5][5];
		for(int i = 0;i< tiles.length;i++)
		{
			for(int j = 0;j<tiles[0].length;j++)
			{
				tiles[i][j] = new Tile();
			}
		}
		tiles[1][2] = new WallTile();
		try
		{
			((WallTile)tiles[1][2]).doDamage();
		}
		catch(WallException e)
		{
			e.printStackTrace();
			fail();
		}
		Point ans = Board.getTileInDirection(new Point(2,2), Direction.up,board);
		assertNull("got though damaged wall", ans);
	}
	
	@Test
	void getTileInDirectionThroughWall()
	{
		AbstractTile[][] tiles = new AbstractTile[5][5];
		for(int i = 0;i< tiles.length;i++)
		{
			for(int j = 0;j<tiles[0].length;j++)
			{
				tiles[i][j] = new Tile();
			}
		}
		tiles[1][2] = new WallTile();
		Point ans = Board.getTileInDirection(new Point(2,2), Direction.up,board);
		assertNull("got though  wall", ans);
	}
	
	//getActualLocation Test
	@Test
	void getActualLocation()
	{
		AbstractTile[][] tiles = new AbstractTile[5][5];
		tiles[0][0] = new Tile();
		tiles[0][2] = new Tile();
		tiles[0][0] = new Tile();
		tiles[0][4] = new Tile();
		tiles[2][0] = new Tile();
		tiles[2][2] = new Tile();
		tiles[2][4] = new Tile();
		tiles[4][0] = new Tile();
		tiles[4][2] = new Tile();
		tiles[4][4] = new Tile();
		tiles[1][2] = new WallTile();
		tiles[2][1] = new WallTile();
		tiles[2][3] = new WallTile();
		tiles[3][2] = new WallTile();
		
		Point ans = Board.getActualLocation(new Point(2,1));
		assertEquals("got wrong tile",new Point(4,2),ans);
	}
	
	//addWall test
	@Test
	void getWall()
	{
		AbstractTile[][] tiles = new AbstractTile[5][5];
		tiles[0][0] = new Tile();
		tiles[0][2] = new Tile();
		tiles[0][0] = new Tile();
		tiles[0][4] = new Tile();
		tiles[2][0] = new Tile();
		tiles[2][2] = new Tile();
		tiles[2][4] = new Tile();
		tiles[4][0] = new Tile();
		tiles[4][2] = new Tile();
		tiles[4][4] = new Tile();
		Board board = new Board(tiles);
		try
		{
			board.addWall(new Point(1,1),new Point(0,1));
		}
		catch(BadBoardException e)
		{
			e.printStackTrace();
			fail();
		}
		if(!(tiles[1][2] instanceof WallTile))
		{
			fail();
		}
	}
	
	@Test
	void getWallFail()
	{
		
		assertThrows(BadBoardException.class,()->board.addWall(new Point(0,0),new Point(2,2)));
	}
	
	@Test
	void getWallDiagonal()
	{
		AbstractTile[][] tiles = new AbstractTile[5][5];
		tiles[0][0] = new Tile();
		tiles[0][2] = new Tile();
		tiles[0][0] = new Tile();
		tiles[0][4] = new Tile();
		tiles[2][0] = new Tile();
		tiles[2][2] = new Tile();
		tiles[2][4] = new Tile();
		tiles[4][0] = new Tile();
		tiles[4][2] = new Tile();
		tiles[4][4] = new Tile();
		Board board = new Board(tiles);
		assertThrows(BadBoardException.class,()->board.addWall(new Point(0,0),new Point(1,1)));
	}
}