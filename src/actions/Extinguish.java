package actions;

import board.Board;
import exeptions.BadBoardException;
import mecanics.ArraylistHelper;
import mecanics.Direction;
import pieces.Flame;
import pieces.Player;

import java.awt.*;

public class Extinguish extends PlayerAction
{
	public Extinguish(Direction direction)
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
		Point p = Board.getTileInDirection(playerLocation, direction, board);
		if(p != null)
		{
			if(ArraylistHelper.containsInstance(board.getTile(p).getPieces(), Flame.class))
			{
				return true;
			}
		} return false;
	}
	
	@Override
	public String toString()
	{
		return "Extinguish "+ direction.toString();
	}
}
