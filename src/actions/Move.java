package actions;

import board.Board;
import exeptions.BadBoardException;
import mecanics.Direction;
import pieces.Player;

import java.awt.*;

public class Move extends PlayerAction
{
	public Move(Direction direction)
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
		return Board.getTileInDirection(playerLocation, direction, board) != null;
	}
	
	@Override
	public String toString()
	{
		return "Move "+ direction.toString() + "  cost:"+cost ;
	}
}
