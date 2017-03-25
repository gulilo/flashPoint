package actions;

import board.Board;
import exeptions.BadBoardException;
import mecanics.Direction;
import pieces.Player;

import java.awt.*;

public class OpenCloseDoor extends PlayerAction
{
	public OpenCloseDoor(Direction direction)
	{
		super(direction);
		cost = 1;
	}
	
	@Override
	public boolean isAvailable(Player player, Point playerLocation, Board board) throws BadBoardException
	{
		if(player.getActionPoints() < cost)
		{
			return false;
		}
		Point p = Board.getLocationInDirection(direction,playerLocation);
		return Board.isValidLocation(Board.getActualLocation(p),board) && board.isDoor(playerLocation, p);
	}
	
	@Override
	public String toString()
	{
		return "OpenCloseDoor "+direction.toString();
	}
}
