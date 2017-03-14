package pieces;

import board.Board;
import exeptions.PoiException;

import java.awt.*;

//dont thing this is needed
public class FalseAlarm extends Poi
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
