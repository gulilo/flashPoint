package pieces;

import board.Board;
import exeptions.PoiException;

import java.awt.*;

public abstract class Poi extends Piece
{
	public enum type
	{
		victim, falseAlarm
	}
	
	public static int NUMBER_OF_VICTIM = 12;
	public static int NUMBER_OF_FALSE_ALARM = 6;
	
	public static int USED_VICTIM;
	public static int USED_FALSE_ALARM;
	
	public abstract void reveal(Point location, Board board) throws PoiException;
	
	public abstract boolean isHidden();
}
