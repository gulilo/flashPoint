package actions;

import board.Board;
import exeptions.BadBoardException;
import mecanics.Direction;
import pieces.Player;
import java.awt.Point;

public abstract class PlayerAction extends Action
{
	int cost;
	Direction direction;
	
	public PlayerAction(Direction direction)
	{
		this.direction = direction;
	}
	
	public int getCost()
	{
		return cost;
	}
	
	public Direction getDirection()
	{
		return direction;
	}
	
	public abstract boolean isAvailable(Player player,Point playerLocation, Board board) throws BadBoardException;
}

