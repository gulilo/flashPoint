package mecanics;

import actions.Action;
import board.Board;
import exeptions.BadBoardException;
import pieces.Human;
import pieces.Player;

import java.awt.*;
import java.util.ArrayList;

public class GameMaster
{
	private final Point[][] WALLS = {{new Point(0,1),new Point(1,1)},{new Point(0,2),new Point(1,2)},{new Point(0,3),new Point(1,3)},{new Point(0,4),new Point(1,4)},{}	};
	private final Point[][] DOORS = {};
	
	private static GameMaster instance;
	private static Board board;
	private Player[] players;
	private int turn;
	private ArrayList<Action> pastActions;
	
	public static GameMaster getInstance()
	{
		if(instance == null)
		{
			instance = new GameMaster();
		}
		
		return instance;
	}
	
	public Board getBoard()
	{
		return board;
	}
	
	public void startGame(int numberOfPlayers)
	{
		players = new Player[numberOfPlayers];
		for(int i = 0;i< players.length;i++)
		{
			players[i] = new Human();
		}
		initBoard();
		turn = 0;
		pastActions = new ArrayList<>();
	}
	
	private void initBoard()
	{
		board = new Board(new Point(8,10));
		try
		{
			for(int i = 0;i<WallsDoors.WALLS.length;i++)
			{
				board.addWall(WallsDoors.WALLS[i][0],WallsDoors.WALLS[i][1]);
			}
			for( int i = 0;i< WallsDoors.DOORS.length;i++)
			{
				board.addDoor(WallsDoors.DOORS[i][0],WallsDoors.DOORS[i][1]);
			}
		}
		catch(BadBoardException e)
		{
			e.printStackTrace();
		}
	}
	
	public Player[] getPlayers()
	{
		return players;
	}
	
	public int getTurn()
	{
		return turn;
	}
	
	public ArrayList<Action> getPastActions()
	{
		return pastActions;
	}
	
	public void resumeGame(Board board)
	{
		this.board = board;
	}
	
	public void placeFire(Dice dice)
	{
		
	}
	
	public void ReplenishPoi(Dice dice)
	{
		
	}
	
	public Player getCurrentPlayer()
	{
		return players[turn];
	}
	
	public void addPastAction(Action action)
	{
		pastActions.add(action);
	}
}
