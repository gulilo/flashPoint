package actions;

import board.Board;
import exeptions.BadBoardException;
import mecanics.ArraylistHelper;
import mecanics.Direction;
import pieces.Flame;
import pieces.Player;

import java.awt.*;

public class FinishTurn extends PlayerAction
{
	public FinishTurn()
	{
		super(Direction.stay);
		cost = 0;
	}
	
	@Override
	public boolean isAvailable(Player player, Point playerLocation, Board board) throws BadBoardException
	{
		if(!ArraylistHelper.containsInstance(board.getTile(playerLocation).getPieces(), Flame.class))
		{
			return true;
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return "FinishTurn";
	}
}
