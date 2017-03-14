package pieces;

import board.AbstractTile;
import board.Board;
import board.Tile;
import exeptions.PoiException;
import mecanics.ArraylistHelper;

import java.awt.*;

public class HiddenPoi extends Poi
{
	type type;
	
	public HiddenPoi(type type) throws PoiException
	{
		this.type = type;
		switch(type)
		{
			case victim:
				if(USED_VICTIM == NUMBER_OF_VICTIM)
				{
					throw new PoiException("used all the victims");
				}
				USED_VICTIM++;
				break;
			case falseAlarm:
				if(USED_FALSE_ALARM == NUMBER_OF_FALSE_ALARM)
				{
					throw new PoiException("used all the false alarms");
				}
				USED_FALSE_ALARM++;
				break;
		}
	}
	
	@Override
	public boolean isHidden()
	{
		return true;
	}
	
	@Override
	public void reveal(Point location, Board board) throws PoiException
	{
		Tile tile = (Tile) board.getTile(location);
		if(ArraylistHelper.containsInstance(tile.getPieces(), HiddenPoi.class))
		{
			if(type == Poi.type.victim)
			{
				tile.addPiece(new Victim());
			}
			tile.removePiece(this);
		}
		else
		{
			throw new PoiException("there is not poi to reveal");
		}
	}
}
