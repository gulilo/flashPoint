package mecanics;

import actions.*;
import board.AmbulanceTile;
import board.Board;
import board.Tile;
import exeptions.*;
import pieces.*;

import java.awt.*;
import java.util.ArrayList;

public class Reducer
{
	public static Board doAction(Player player, Action action, Board board) throws ActionException, BadBoardException, PoiException, WallException
	{
		Board newBoard = new Board(board);
		if(action instanceof PlayerAction)
		{
			if(player.getActionPoints() - ((PlayerAction) action).getCost() >= 0)
			{
				Point playerLocation = Board.findPlayer(player, newBoard);
				
				if(playerLocation == null)
				{
					if(action instanceof FirstAction)
					{
						doFirstAction(player, (FirstAction) action, newBoard);
						doFinishTurnAction();
					}
					else
					{
						throw new BadBoardException("player given is not on the board");
					}
				}
				Direction direction = ((PlayerAction) action).getDirection();
				if(action instanceof MoveWithVictim)
				{
					doMoveWithVictim(player, direction, playerLocation, newBoard);
				}
				else if(action instanceof MoveToFire)
				{
					doMoveToFire(player, direction, playerLocation, newBoard);
				}
				else if(action instanceof Move)
				{
					doMoveAction(player, direction, playerLocation, newBoard);
				}
				else if(action instanceof OpenCloseDoor)
				{
					doOpenCloseDoorAction(playerLocation, direction, newBoard);
				}
				else if(action instanceof Chop)
				{
					doChopAction(playerLocation, direction, newBoard);
				}
				else if(action instanceof Extinguish)
				{
					doExtinguishAction(playerLocation, direction, newBoard);
				}
				else if(action instanceof RemoveFire)
				{
					doRemoveFireAction(playerLocation, direction, newBoard);
				}
				else if(action instanceof CarryVictim)
				{
					doCarryVictim(player, playerLocation, newBoard);
				}
				else if(action instanceof LeaveVictim)
				{
					doLeaveVictim(player, playerLocation, newBoard);
				}
				else if(action instanceof FinishTurn)
				{
					doFinishTurnAction();
				}
				player.useActionPoints(((PlayerAction) action).getCost());
			}
			else
			{
				throw new ActionException("the player don't have the action points to do this action");
			}
		}
		else
		{
			if(action instanceof PlaceFire)
			{
				doPlaceFire((PlaceFire) action, newBoard);
			}
			else if(action instanceof ReplenishPoi)
			{
				doReplenishPoi((ReplenishPoi) action, newBoard);
			}
		}
		GameMaster.getInstance().addPastAction(action);
		return newBoard;
	}
	
	private static void doFinishTurnAction()
	{
		GameMaster.getInstance().finishTurn();
	}
	
	private static void doFirstAction(Player player, FirstAction action, Board board) throws FirstActionException
	{
		Point loc = action.getTile();
		if(!(loc.x == 0 || loc.y == 0 || loc.x == board.getSize().x-1 || loc.y == board.getSize().y-1))
		{
			throw new FirstActionException("cant place inside the house");
		}
		board.addPiece(loc,player);
	}
	
	//TODO victim carried count for poi on the board.....
	private static void doReplenishPoi(ReplenishPoi action, Board board) throws ReplenishPoiException, PoiException
	{
		Point[] locations = action.getTiles();
		Poi.type[] types = action.getTypes();

		for(int i = 0;i< locations.length;i++)
		{
			Tile tile = board.getTile(locations[i]);
			ArrayList<Piece> pieces = tile.getPieces();
			if(ArraylistHelper.containsInstance(pieces,Poi.class))
			{
				throw new ReplenishPoiException("there is already poi there");
			}
			if(ArraylistHelper.containsInstance(pieces, Fire.class))
			{
				Fire fire = null;
				for(int j = 0; j < pieces.size(); j++)
				{
					if(pieces.get(j) instanceof Fire)
					{
						fire = (Fire) pieces.get(j);
						break;
					}
				}
				pieces.remove(fire);
			}
			else if(ArraylistHelper.containsInstance(pieces, Smoke.class))
			{
				Smoke smoke = null;
				for(int j = 0; j < pieces.size(); j++)
				{
					if(pieces.get(j) instanceof Smoke)
					{
						
						smoke = (Smoke) pieces.get(j);
						break;
					}
				}
				pieces.remove(smoke);
			}
			Poi poi = null;
			switch(types[i])
			{
				case falseAlarm:
					poi = new HiddenPoi(Poi.type.falseAlarm);
					break;
				case victim:
					poi = new HiddenPoi(Poi.type.victim);
					break;
			}
			pieces.add(poi);

			if(ArraylistHelper.containsInstance(pieces, Player.class))
			{
				poi.reveal(locations[i], board);
			}
		}
	}
	
	private static void doPlaceFire(PlaceFire action, Board board) throws PlaceFireException, BadBoardException, WallException
	{
		Point location = action.getTile();
		Tile tile = board.getTile(location);
		if(ArraylistHelper.containsInstance(tile.getPieces(),Smoke.class))
		{
			Smoke smoke = null;
			for(int i = 0; i < tile.getPieces().size(); i++)
			{
				if(tile.getPieces().get(i) instanceof Smoke)
				{
					smoke = (Smoke) tile.getPieces().get(i);
					break;
				}
			}
			tile.removePiece(smoke);
			tile.addPiece(new Fire());
			if(ArraylistHelper.containsInstance(tile.getPieces(), Player.class))
			{
				Player player = null;
				for(int i = 0; i < tile.getPieces().size(); i++)
				{
					if(tile.getPieces().get(i) instanceof Player)
					{
						player = (Player) tile.getPieces().get(i);
						break;
					}
				}
				doKnockDown(player, location, board);
			}
		}
		else if(ArraylistHelper.containsInstance(tile.getPieces(), Fire.class))
		{
			doExplosion(location, board);
		}
		else if((Board.getTileInDirection(location, Direction.up, board) != null && ArraylistHelper.containsInstance(Board.getTile(Board.getTileInDirection(location, Direction.up, board), board).getPieces(), Fire.class))
				|| (Board.getTileInDirection(location, Direction.down, board) != null && ArraylistHelper.containsInstance(Board.getTile(Board.getTileInDirection(location, Direction.down, board), board).getPieces(), Fire.class))
				|| (Board.getTileInDirection(location, Direction.left, board) != null && ArraylistHelper.containsInstance(Board.getTile(Board.getTileInDirection(location, Direction.left, board), board).getPieces(), Fire.class))
				|| (Board.getTileInDirection(location, Direction.right, board) != null && ArraylistHelper.containsInstance(Board.getTile(Board.getTileInDirection(location, Direction.right, board), board).getPieces(), Fire.class)))
		{
			tile.addPiece(new Fire());
		}
		else
		{
			tile.addPiece(new Smoke());
		}
		doFlashOver(board);
	}
	
	private static void doExplosion(Point location, Board board) throws BadBoardException, WallException
	{
		doShockwave(location, Direction.up, board);
		doShockwave(location, Direction.down, board);
		doShockwave(location, Direction.left, board);
		doShockwave(location, Direction.right, board);
	}
	
	private static void doShockwave(Point location, Direction direction, Board board) throws BadBoardException, WallException
	{
		Point newLocation = Board.getTileInDirection(location,direction,board);
		Point oldLocation = newLocation;
		while(newLocation != null && Board.isValidLocation(newLocation,board) && ArraylistHelper.containsInstance(board.getTile(newLocation).getPieces(),Fire.class))
		{
			oldLocation = newLocation;
			newLocation = Board.getTileInDirection(oldLocation,direction,board);
		}
		if(newLocation != null)
		{
			Tile tile = board.getTile(newLocation);
			ArrayList<Piece> pieces = tile.getPieces();
			for(int i = 0;i< pieces.size();i++)
			{
				Piece piece = pieces.get(i);
				if(piece instanceof Player)
				{
					doKnockDown((Player) piece, newLocation, board);
				}
				else
				{
					tile.removePiece(piece);
				}
			}
			tile.addPiece(new Fire());
		}
		else
		{
			if(oldLocation == null)
			{
				oldLocation = location;
			}
			newLocation = Board.getLocationInDirection(direction,oldLocation);
			if(board.isDoor(oldLocation,newLocation))
			{
				board.getDoor(oldLocation,newLocation).brake();
			}
			else if(board.isWall(oldLocation,newLocation))
			{
				board.getWall(oldLocation,newLocation).doDamage();
			}
		}
	}
	
	private static void doKnockDown(Player player, Point playerLocation, Board board) throws BadBoardException
	{
		AmbulanceTile ambulance = board.getAmbulance(playerLocation);
		ambulance.addPiece(player);
		board.removePiece(playerLocation,player);
	}
	
	private static void doFlashOver(Board board)
	{
		ArrayList<Point> fires = new ArrayList<>();
		for(int i = 0;i< board.getSize().x;i++)
		{
			for(int j = 0;j < board.getSize().y;j++)
			{
				ArrayList<Piece> pieces = board.getTile(new Point(i,j)).getPieces();
				if(ArraylistHelper.containsInstance(pieces,Fire.class))
				{
					fires.add(new Point(i,j));
				}
			}
		}
		
		while(fires.size() > 0)
		{
			Point cur = fires.remove(0);
			flashOverHelper(cur,Direction.up,board,fires);
			flashOverHelper(cur,Direction.down,board,fires);
			flashOverHelper(cur,Direction.left,board,fires);
			flashOverHelper(cur,Direction.right,board,fires);
		}
	}
	
	private static void flashOverHelper(Point cur, Direction direction, Board board, ArrayList<Point> fires)
	{
		Point p;
		if((p = Board.getTileInDirection(cur,direction,board)) != null && ArraylistHelper.containsInstance(board.getTile(p).getPieces(),Smoke.class))
		{
			Tile tile = board.getTile(p);
			Smoke smoke = null;
			for(int a = 0; a < tile.getPieces().size(); a++)
			{
				if(tile.getPieces().get(a) instanceof Smoke)
				{
					smoke = (Smoke) tile.getPieces().get(a);
					break;
				}
			}
			tile.removePiece(smoke);
			tile.addPiece(new Fire());
			fires.add(p);
		}
	}
	
	
	private static void doMoveWithVictim(Player player, Direction direction, Point playerLocation, Board board) throws MoveException, MoveWithVictimException, PoiException
	{
		if(!player.isCarry())
		{
			throw new MoveWithVictimException("the player is not carrying a victim");
		}
		Point newLocation = Board.getLocationInDirection(direction,playerLocation);
		Tile tile = board.getTile(newLocation);
		if(ArraylistHelper.containsInstance(tile.getPieces(),Fire.class))
		{
			throw new MoveWithVictimException("cant move to fire with victim");
		}
		if(newLocation.x == 0 || newLocation.x == board.getSize().x-1 || newLocation.y == 0 || newLocation.y == board.getSize().y-1)
		{
			board.rescue();
			player.stopCarryVictim();
		}
		doMoveAction(player,direction,playerLocation,board);
	}
	
	private static void doMoveToFire(Player player, Direction direction, Point playerLocation, Board board) throws MoveException, MoveToFireException, PoiException
	{
		Point fireLocation = Board.getLocationInDirection(direction,playerLocation);
		Tile tile = board.getTile(fireLocation);
		if(!ArraylistHelper.containsInstance(tile.getPieces(), Fire.class))
		{
			throw new MoveToFireException("there is no fire");
		}
		if(player.isCarry())
		{
			throw new MoveToFireException("cant move to fire with victim");
		}
		if(player.getActionPoints() - 2 < 1)//TODO more complicated then that
		{
			throw new MoveToFireException("cant end turn on fire");
		}
		
		doMoveAction(player, direction, playerLocation, board);
	}
	
	private static void doLeaveVictim(Player player, Point playerLocation, Board board) throws LeaveVictimException, PoiException
	{
		if(!player.isCarry())
		{
			throw new LeaveVictimException("the player is not carrying a victim");
		}
		board.addPiece(playerLocation, new Victim());
		player.stopCarryVictim();
	}
	
	private static void doCarryVictim(Player player, Point playerLocation, Board board) throws CarryVictimException
	{
		Tile tile = board.getTile(playerLocation);
		if(ArraylistHelper.containsInstance(tile.getPieces(), Victim.class))
		{
			Victim victim = null;
			for(int i = 0; i < tile.getPieces().size(); i++)
			{
				if(tile.getPieces().get(i) instanceof Victim)
				{
					victim = (Victim) tile.getPieces().get(i);
					break;
				}
			}
			tile.removePiece(victim);
			player.carryVictim();
		}
		else
		{
			throw new CarryVictimException("there is no victim there");
		}
	}
	
	private static void doRemoveFireAction(Point playerLocation, Direction direction, Board board) throws RemoveFireException
	{
		Point flameLocation = Board.getTileInDirection(playerLocation, direction, board);
		if(flameLocation == null)
		{
			throw new RemoveFireException("cant remove fire through wall or door");
		}
		Tile tile = board.getTile(flameLocation);
		if(ArraylistHelper.containsInstance(tile.getPieces(), Fire.class))
		{
			Fire fire = null;
			for(int i = 0; i < tile.getPieces().size(); i++)
			{
				if(tile.getPieces().get(i) instanceof Fire)
				{
					fire = (Fire) tile.getPieces().get(i);
					break;
				}
			}
			tile.removePiece(fire);
		}
		else
		{
			throw new RemoveFireException("there is no fire there");
		}
	}
	
	private static void doExtinguishAction(Point playerLocation, Direction direction, Board board) throws ExtinguishException
	{
		Point flameLocation = Board.getTileInDirection(playerLocation, direction, board);
		if(flameLocation == null)
		{
			throw new ExtinguishException("cant extinguish through wall or door");
		}
		Tile tile = board.getTile(flameLocation);
		if(ArraylistHelper.containsInstance(tile.getPieces(), Fire.class))
		{
			Fire fire = null;
			for(int i = 0; i < tile.getPieces().size(); i++)
			{
				if(tile.getPieces().get(i) instanceof Fire)
				{
					fire = (Fire) tile.getPieces().get(i);
					break;
				}
			}
			tile.removePiece(fire);
			tile.addPiece(new Smoke());
		}
		else if(ArraylistHelper.containsInstance(tile.getPieces(), Smoke.class))
		{
			Smoke smoke = null;
			for(int i = 0; i < tile.getPieces().size(); i++)
			{
				if(tile.getPieces().get(i) instanceof Smoke)
				{
					smoke = (Smoke) tile.getPieces().get(i);
					break;
				}
			}
			tile.removePiece(smoke);
		}
		else
		{
			throw new ExtinguishException("there is no flame there");
		}
	}
	
	private static void doChopAction(Point playerLocation, Direction direction, Board board) throws ChopException, WallException
	{
		Point newLocation = Board.getLocationInDirection(direction, playerLocation);
		try
		{
			board.getWall(playerLocation, newLocation).doDamage();
		}
		catch(BadBoardException e)
		{
			throw new ChopException("there is not wall there or its already broken");
		}
	}
	
	private static void doMoveAction(Player player, Direction direction, Point playerLocation, Board board) throws MoveException, PoiException
	{
		Point newLocation = Board.getTileInDirection(playerLocation, direction, board);
		if(newLocation == null)
		{
			throw new MoveException("cant move trough wall or closed door");
		}
		Tile tile = board.getTile(newLocation);
		if(ArraylistHelper.containsInstance(tile.getPieces(), HiddenPoi.class))
		{
			HiddenPoi poi = null;
			for(int i = 0; i < tile.getPieces().size(); i++)
			{
				if(tile.getPieces().get(i) instanceof HiddenPoi)
				{
					poi = (HiddenPoi) tile.getPieces().get(i);
					break;
				}
			}
			poi.reveal(newLocation, board);
		}
		board.addPiece(newLocation, player);
		board.removePiece(playerLocation, player);
	}
	
	private static void doOpenCloseDoorAction(Point playerLocation, Direction direction, Board board) throws DoorException
	{
		Point newLocation = Board.getLocationInDirection(direction, playerLocation);
		try
		{
			board.getDoor(playerLocation, newLocation).changeState();
		}
		catch(BadBoardException e)
		{
			throw new DoorException("cant open/close wall");
		}
	}
	
	
}
