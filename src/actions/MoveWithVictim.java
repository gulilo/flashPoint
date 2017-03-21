package actions;

import board.Board;
import exeptions.BadBoardException;
import mecanics.ArraylistHelper;
import mecanics.Direction;
import pieces.Fire;
import pieces.Player;

import java.awt.*;

public class MoveWithVictim extends Move
{
	public MoveWithVictim(Direction direction)
	{
		super(direction);
		cost = 2;
	}
	
	@Override
	public boolean isAvailable(Player player, Point playerLocation, Board board) throws BadBoardException
	{
		boolean move = super.isAvailable(player, playerLocation, board);
		Point p = Board.getTileInDirection(playerLocation,direction,board);
		return move && player.isCarry() && p != null && !ArraylistHelper.containsInstance(board.getTile(p).getPieces(), Fire.class);
	}
	
	@Override
	public String toString()
	{
		return "MoveWithVictim "+direction.toString();
	}
}
