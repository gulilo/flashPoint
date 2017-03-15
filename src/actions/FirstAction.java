package actions;

import board.Board;
import exeptions.BadBoardException;
import mecanics.Direction;
import pieces.Player;

import java.awt.*;

public class FirstAction extends PlayerAction
{
	Point tile;
	
	public FirstAction(Point tile)
	{
		super(Direction.stay);
		cost = 0;
		this.tile = tile;
	}
	
	public Point getTile()
	{
		return tile;
	}
	
	@Override
	public boolean isAvailable(Player player, Point playerLocation, Board board) throws BadBoardException
	{
		return tile.x == 0 || tile.x == board.getSize().x - 1 || tile.y == 0 || tile.y == board.getSize().y - 1;
	}
	
	@Override
	public String toString()
	{
		return "FirstAction " + tile.x +" , "+tile.y ;
	}
}
