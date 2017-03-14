package board;

import actions.*;
import exeptions.BadBoardException;
import mecanics.ArraylistHelper;
import mecanics.Direction;
import pieces.Piece;
import pieces.Player;
import pieces.Victim;

import java.awt.*;
import java.util.ArrayList;

public class Board
{
	public static final int BOARD_WIDTH = 8;
	public static final int BOARD_HEIGHT = 6;
	
	private Point size;
	private AbstractTile[][] board;
	
	public Board(Point size)
	{
		this.size = new Point(size);
		board = new AbstractTile[(size.x * 2) - 1][(size.y * 2) - 1];
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[0].length; j++)
			{
				if(i % 2 == 0 && j % 2 == 0)
				{
					board[i][j] = new Tile();
				}
			}
		}
	}
	
	public Board(AbstractTile[][] board)
	{
		size = new Point(board.length/2,board[0].length/2);//maybe?
		this.board = board;
	}
	
	public Board(Board board)
	{
		size = board.getSize();
		this.board = copyTiles(board.getTiles());
	}
	
	public AbstractTile[][] getTiles()
	{
		return board;
	}
	
	//TODO write getAvailableActions
	public ArrayList<Action> getAvailableActions(Player player)
	{
		ArrayList<Action> ans = new ArrayList<>();
		Point playerLocation = Board.findPlayer(player,this);
		
		Point up = getTileInDirection(playerLocation,Direction.up,this);
		Point down = getTileInDirection(playerLocation,Direction.down,this);
		Point left = getTileInDirection(playerLocation,Direction.left,this);
		Point right = getTileInDirection(playerLocation,Direction.right,this);
		
		Tile tile = getTile(playerLocation);
		if(player.isCarry())
		{
			//leave victim
			ans.add(new LeaveVictim());
		}
		else
		{
			//carry victim
			if(ArraylistHelper.containsInstance(tile.getPieces(), Victim.class))
			{
				ans.add(new CarryVictim());
			}
			
		}
		try
		{
			//open close door
			if(isDoor(playerLocation,up))
			{
				ans.add(new OpenCloseDoor(Direction.up));
			}
			if(isDoor(playerLocation,down))
			{
				ans.add(new OpenCloseDoor(Direction.down));
			}
			if(isDoor(playerLocation,left))
			{
				ans.add(new OpenCloseDoor(Direction.left));
			}
			if(isDoor(playerLocation,right))
			{
				ans.add(new OpenCloseDoor(Direction.right));
			}
			
			//chop wall
			if(isWall(playerLocation,up) && !getWall(playerLocation,up).isBroken())
			{
				ans.add(new Chop(Direction.up));
			}
			if(isDoor(playerLocation,down)&& !getWall(playerLocation,down).isBroken())
			{
				ans.add(new Chop(Direction.down));
			}
			if(isDoor(playerLocation,left)&& !getWall(playerLocation,left).isBroken())
			{
				ans.add(new Chop(Direction.left));
			}
			if(isDoor(playerLocation,right)&& !getWall(playerLocation,right).isBroken())
			{
				ans.add(new Chop(Direction.right));
			}
		}
		catch(BadBoardException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public int numberOfWallDamage()
	{
		int ans = 0;
		for(int i = 0;i < board.length;i++)
		{
			for(int j = 0;j < board[0].length;j++)
			{
				if(board[i][j] instanceof WallTile)
				{
					WallTile tile = (WallTile) board[i][j];
					if(tile.isDamage())
					{
						ans += 1;
					}
					else if(tile.isBroken())
					{
						ans += 2;
					}
				}
			}
		}
		return ans;
	}
	
	public Point getSize()
	{
		return size;
	}
	
	public WallTile addWall(Point p1, Point p2) throws BadBoardException
	{
		Point loc = pointBetween(getActualLocation(p1), getActualLocation(p2));
		WallTile tile = new WallTile();
		board[loc.x][loc.y] = tile;
		return tile;
	}
	
	public DoorTile addDoor(Point p1, Point p2) throws BadBoardException
	{
		Point loc = pointBetween(getActualLocation(p1), getActualLocation(p2));
		DoorTile tile = new DoorTile();
		board[loc.x][loc.y] = tile;
		return tile;
	}
	
	public DoorTile getDoor(Point p1, Point p2) throws BadBoardException
	{
		Point loc = pointBetween(getActualLocation(p1), getActualLocation(p2));
		if(!(board[loc.x][loc.y] instanceof DoorTile))
		{
			throw new BadBoardException("there is no door in the points given");
		}
		return (DoorTile) board[loc.x][loc.y];
	}
	
	public WallTile getWall(Point p1, Point p2) throws BadBoardException
	{
		Point loc = pointBetween(getActualLocation(p1), getActualLocation(p2));
		if(!(board[loc.x][loc.y] instanceof WallTile))
		{
			throw new BadBoardException("there is no wall in the points given");
		}
		return (WallTile) board[loc.x][loc.y];
	}
	
	public boolean isWall(Point p1, Point p2) throws BadBoardException
	{
		Point loc = pointBetween(getActualLocation(p1),getActualLocation(p2));
		return board[loc.x][loc.y] instanceof WallTile;
	}
	
	public boolean isDoor(Point p1, Point p2) throws BadBoardException
	{
		Point loc = pointBetween(getActualLocation(p1),getActualLocation(p2));
		return board[loc.x][loc.y] instanceof DoorTile;
	}
	
	private Point pointBetween(Point loc1, Point loc2) throws BadBoardException
	{
		if(Math.abs(loc1.x - loc2.x) > 2 || Math.abs(loc1.y - loc2.y) > 2)
		{
			throw new BadBoardException("the tiles are not connected");
		}
		if(loc1.x == loc2.x && loc1.y == loc2.y + 2)
		{
			return new Point(loc2.x, loc2.y + 1);
		}
		else if(loc1.x == loc2.x && loc1.y == loc2.y - 2)
		{
			return new Point(loc2.x, loc2.y - 1);
		}
		else if(loc1.x == loc2.x + 2 && loc1.y == loc2.y)
		{
			return new Point(loc2.x + 1, loc2.y);
		}
		else if(loc1.x == loc2.x - 2 && loc1.y == loc2.y)
		{
			return new Point(loc2.x - 1, loc2.y);
		}
		else
		{
			throw new BadBoardException("the tiles are not connected");
		}
	}
	
	public void addPiece(Point location, Piece piece)
	{
		
		Tile tile = getTile(location);
		System.out.println(tile);
		tile.addPiece(piece);
	}
	
	public void removePiece(Point location, Piece piece)
	{
		Tile tile = getTile(location);
		tile.removePiece(piece);
	}
	
	public void setAmbulance(Point p)
	{
		Point loc = getActualLocation(p);
		board[loc.x][loc.y] = new AmbulanceTile(getTile(p));
	}
	
	public AmbulanceTile getAmbulance(Point location) throws BadBoardException
	{
		Point playerLocation = getActualLocation(location);
		int size = Math.max(board.length, board[0].length);
		for(int i = 0; i < size; i++)
		{
			for(int j = i - 1; j <= i + 1; j++)
			{
				for(int k = i - 1; k <= i + 1; k++)
				{
					if(isValidLocation(new Point(playerLocation.x + j, playerLocation.y + k), this) && board[playerLocation.x + j][playerLocation.y + k] instanceof AmbulanceTile)
					{
						return (AmbulanceTile) board[playerLocation.x + j][playerLocation.y + k];
					}
				}
			}
		}
		throw new BadBoardException("there is not an ambulance on board");
	}
	
	public Tile getTile(Point location)
	{
		Point p = getActualLocation(location);
		return (Tile) board[p.x][p.y];
	}
	
	//statics functions
	public static Point getActualLocation(Point location)
	{
		return new Point(location.x * 2, location.y * 2);
	}
	
	public static Point getRealLocation(Point location)
	{
		return new Point(location.x / 2, location.y / 2);
	}
	
	public static Tile getTile(Point location, Board board)
	{
		return board.getTile(location);
	}
	
	
	public static boolean isValidLocation(Point newLocation, Board board)
	{
		return newLocation.x >= 0 && newLocation.x < board.board.length && newLocation.y >= 0 && newLocation.y < board.board[0].length;
	}
	
	public static Point getTileInDirection(Point oldLocation, Direction direction, Board board)
	{
		Point location = getActualLocation(oldLocation);
		Point newLocation = getLocationInDirection(direction, location);
		if(!isValidLocation(newLocation,board))
		{
			return null;
		}
		AbstractTile tile = board.getTiles()[newLocation.x][newLocation.y];
		if(tile != null && ((tile instanceof DoorTile && !((DoorTile) tile).isOpen()) || (tile instanceof WallTile && !((WallTile) tile).isBroken())))
		{
			return null;
		}
		else
		{
			
			return getRealLocation(getLocationInDirection(direction, newLocation));
		}
	}
	
	public static Point getLocationInDirection(Direction direction, Point oldLocation)
	{
		Point newLocation = new Point(oldLocation);
		switch(direction)
		{
			case up:
				newLocation.x -= 1;
				break;
			case down:
				newLocation.x += 1;
				break;
			case left:
				newLocation.y -= 1;
				break;
			case right:
				newLocation.y += 1;
				break;
			case stay:
				newLocation = oldLocation;
				break;
		}
		return newLocation;
	}
	
	private static AbstractTile[][] copyTiles(AbstractTile[][] tiles)
	{
		AbstractTile[][] newTiles = new AbstractTile[tiles.length][tiles[0].length];
		for(int i = 0; i < tiles.length; i++)
		{
			for(int j = 0; j < tiles[0].length; j++)
			{
				if(tiles[i][j] != null)
				{
					newTiles[i][j] = tiles[i][j].copy();
				}
			}
		}
		return newTiles;
	}
	
	//actual location
	public static Point findPlayer(Player p, Board board)
	{
		for(int i = 0; i < board.getSize().x; i++)
		{
			for(int j = 0; j < board.getSize().y; j++)
			{
				Tile tile = board.getTile(new Point(i,j));
				if(tile.getPieces().contains(p))
				{
					return new Point(i, j);
				}
			}
		}
		return null;
	}
}
