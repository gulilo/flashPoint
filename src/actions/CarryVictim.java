package actions;

import board.Board;
import mecanics.ArraylistHelper;
import mecanics.Direction;
import pieces.Piece;
import pieces.Player;
import pieces.Victim;

import java.awt.Point;
import java.util.ArrayList;

public class CarryVictim extends PlayerAction
{
	public CarryVictim()
	{
		super(Direction.stay);
		cost = 0;
	}
	
	@Override
	public boolean isAvailable(Player player,Point playerLocation, Board board)
	{
		if(!player.isCarry())
		{
			ArrayList<Piece> pieces = board.getTile(playerLocation).getPieces();
			if(ArraylistHelper.containsInstance(pieces, Victim.class))
			{
				return true;
			}
		}
		return false;
	}
}
