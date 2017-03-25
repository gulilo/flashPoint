package actions;

import board.Board;
import exeptions.BadBoardException;
import mecanics.Direction;
import pieces.Player;

import java.awt.*;

public class Chop extends PlayerAction
{
	public Chop(Direction direction)
	{
		super(direction);
		cost = 2;
	}
	
	@Override
	public boolean isAvailable(Player player, Point playerLocation, Board board) throws BadBoardException
	{
		if(player.getActionPoints() < cost)
		{
			return false;
		}
		Point p = Board.getLocationInDirection(direction,playerLocation);
		return Board.isValidLocation(Board.getActualLocation(p),board) && board.isWall(playerLocation, p) && !board.getWall(playerLocation, p).isBroken();
	}
	
	@Override
	public String toString()
	{
		return "Chop "+ direction.toString();
	}
}
