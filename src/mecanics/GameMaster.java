package mecanics;

import Gui.MainPanel;
import actions.Action;
import actions.PlayerAction;
import board.Board;
import board.DoorTile;
import exeptions.ActionException;
import exeptions.BadBoardException;
import exeptions.PoiException;
import exeptions.WallException;
import pieces.Fire;
import pieces.HiddenPoi;
import pieces.Human;
import pieces.Player;

import java.awt.*;
import java.util.ArrayList;

public class GameMaster
{
	private static GameMaster instance;
	private static Board board;
	private Player[] players;
	private int turn;
	private ArrayList<Action> pastActions;
	
	private MainPanel mainPanel; // TEMPERARy
	
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
		for(int i = 0; i < players.length; i++)
		{
			players[i] = new Human(Color.BLUE);
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
			board.addPiece(new Point(5,1),new HiddenPoi());
			board.addPiece(new Point(2,4),new HiddenPoi());
			board.addPiece(new Point(5,8),new HiddenPoi());
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
	
	public void doAction(PlayerAction action)
	{
		try
		{
			board = Reducer.doAction(getCurrentPlayer(), action, board);
		}
		catch(ActionException | BadBoardException | WallException | PoiException e)
		{
			e.printStackTrace();
		}
		
/*		for(int i = 0;i < board.getSize().x;i++)
		{
			for(int j = 0;j < board.getSize().y;j++)
			{
				System.out.print(board.getTile(new Point(i,j)).getPieces());
			}
			System.out.println();
		}*/
		mainPanel.updateComboBox();
		mainPanel.repaint();
	}
	
	public void updateList()
	{
		mainPanel.updateComboBox();
	}
	
	//TEMPORARY
	public void setMainPanel(MainPanel mainPanel)
	{
		this.mainPanel = mainPanel;
	}
}
