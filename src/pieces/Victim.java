package pieces;

import board.Board;
import exeptions.PoiException;

import java.awt.*;

public class Victim extends Poi
{
	@Override
	public void reveal(Point location, Board board) throws PoiException
	{
		throw new PoiException("already revealed");
	}
	
	@Override
	public boolean isHidden()
	{
		return false;
	}
}
