package actions;

import board.Board;
import exeptions.BadBoardException;
import mecanics.ArraylistHelper;
import mecanics.Direction;
import pieces.Fire;
import pieces.Player;

import java.awt.*;

public class LeaveVictim extends PlayerAction
{
	public LeaveVictim()
	{
		super(Direction.stay);
		cost = 0;
	}
	
	@Override
	public boolean isAvailable(Player player, Point playerLocation, Board board) throws BadBoardException
	{
		if(player.isCarry())
		{
			if(!ArraylistHelper.containsInstance(board.getTile(playerLocation).getPieces(), Fire.class))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return "LeaveVictim";
	}
}
