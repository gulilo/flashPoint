package mecanics;

import actions.Action;
import actions.PlaceFire;
import actions.ReplenishPoi;
import board.Board;
import board.DoorTile;
import exeptions.ActionException;
import exeptions.BadBoardException;
import exeptions.PoiException;
import exeptions.WallException;
import pieces.*;

import java.awt.*;
import java.util.ArrayList;

public class GameMaster
{
	private final Color[] playersColors = {Color.BLUE,Color.green,Color.YELLOW,Color.MAGENTA,Color.orange,Color.white};
	
	private static GameMaster instance;
	private static Board board;
	private Player[] players;
	private int turn;
	private ArrayList<Action> pastActions;
	private boolean firstTurn;
	
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
	
	public void startGame(String[] names, Color[] colors)
	{
		firstTurn = true;
		players = new Player[colors.length];
		for(int i = 0; i < players.length; i++)
		{
			players[i] = new Human(names[i], colors[i]);
		}
		initBoard();
		turn = 0;
		pastActions = new ArrayList<>();
		
	}
	
	private void initBoard()
	{
		board = new Board(new Point(8, 10));
		try
		{
			for(int i = 0; i < WallsDoors.WALLS.length; i++)
			{
				board.addWall(WallsDoors.WALLS[i][0], WallsDoors.WALLS[i][1]);
			}
			for(int i = 0; i < WallsDoors.DOORS.length; i++)
			{
				DoorTile door = board.addDoor(WallsDoors.DOORS[i][0], WallsDoors.DOORS[i][1]);
				if(WallsDoors.DOOR_STATE[i])
				{
					door.changeState();
				}
			}
		}
		catch(BadBoardException e)
		{
			e.printStackTrace();
		}
		board.addPiece(new Point(2, 2), new Fire());
		board.addPiece(new Point(2, 3), new Fire());
		board.addPiece(new Point(3, 2), new Fire());
		board.addPiece(new Point(3, 3), new Fire());
		board.addPiece(new Point(3, 4), new Fire());
		board.addPiece(new Point(3, 5), new Fire());
		board.addPiece(new Point(4, 4), new Fire());
		board.addPiece(new Point(5, 6), new Fire());
		board.addPiece(new Point(5, 7), new Fire());
		board.addPiece(new Point(6, 6), new Fire());
		
		try
		{
			board.addPiece(new Point(5, 1), new HiddenPoi());
			board.addPiece(new Point(2, 4), new HiddenPoi());
			board.addPiece(new Point(5, 8), new HiddenPoi());
		}
		catch(PoiException e)
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
	
	public static Board placeFire(Dice dice, Board board) throws WallException, PoiException, BadBoardException, ActionException
	{
		//System.out.println("bla");
		Point p = dice.getLocation();
		//System.out.println(p);
		//Point p = new Point(1, 1);
		return Reducer.doAction(null, new PlaceFire(p), board);///////////////////////////FIX   ME!!!!!
	}
	
	public static Board replenishPoi(Dice dice, Board board) throws ActionException, WallException, PoiException, BadBoardException
	{
		int MAX_POI = 3;
		int num = MAX_POI - board.numberOfPoi();
		Point[] loc = new Point[num];
		Poi.type[] types = new Poi.type[num];
		for(int i = 0; i < num; i++)
		{
			loc[i] = dice.getLocation();
			int r = dice.rollX(2);
			switch(r)
			{
				case 1:
					types[i] = Poi.type.falseAlarm;
					break;
				case 2:
					types[i] = Poi.type.victim;
					break;
			}
		}
		return Reducer.doAction(null, new ReplenishPoi(loc, types), board);
	}
	
	
	public Player getCurrentPlayer()
	{
		return players[turn];
	}
	
	public void addPastAction(Action action)
	{
		pastActions.add(action);
	}
	
	public void doAction(Action action)
	{
		try
		{
			board = Reducer.doAction(getCurrentPlayer(), action, board);
		}
		catch(ActionException | BadBoardException | WallException | PoiException e)
		{
			e.printStackTrace();
		}
		/*System.out.println(action);
		System.out.println("----------------------------------------------------------");
		for(int i = 0; i < board.getSize().x; i++)
		{
			for(int j = 0; j < board.getSize().y; j++)
			{
				System.out.print(board.getTile(new Point(i, j)).getPieces());
			}
			System.out.println();
		}
		System.out.println("----------------------------------------------------------");*/
		GuiMaster.getInstance().updateGamePanel();
	}
	
	public void nextTurn()
	{
		if(!firstTurn)
		{
			Player player = getCurrentPlayer();
			if(player.getActionPoints() + 4 < 8)
			{
				player.addActionPoint(4);
			}
			else
			{
				player.addActionPoint(8 - player.getActionPoints());
			}
		}
		turn = (turn + 1) % players.length;
		if(firstTurn && turn == 0)
		{
			firstTurn = false;
		}
		
	}
}
