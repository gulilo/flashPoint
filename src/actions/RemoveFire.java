package actions;

import board.Board;
import exeptions.BadBoardException;
import mecanics.ArraylistHelper;
import mecanics.Direction;
import pieces.Fire;
import pieces.Player;

import java.awt.*;

public class RemoveFire extends PlayerAction
{
	public RemoveFire(Direction direction)
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
		Point p = Board.getTileInDirection(playerLocation,direction, board);
		if(p != null)
		{
			if(ArraylistHelper.containsInstance(board.getTile(p).getPieces(), Fire.class))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return "RemoveFire "+direction.toString();
	}
}
