package actions;

import exeptions.ReplenishPoiException;
import pieces.Poi;

import java.awt.*;

public class ReplenishPoi extends Action
{
	public static final int MAX_NUMBER_OF_POI_REPLENISH = 3;
	private Point[] tiles;
	private Poi.type[] types;
	
	public ReplenishPoi(Point[] points, Poi.type[] types) throws ReplenishPoiException
	{
		if(points.length != types.length || types.length > MAX_NUMBER_OF_POI_REPLENISH)
		{
			throw new ReplenishPoiException("max of 3 pois");
		}
		tiles = new Point[points.length];
		this.types = new Poi.type[types.length];
		for(int i = 0; i < tiles.length; i++)
		{
			tiles[i] = points[i];
		}
		for(int i = 0; i < types.length; i++)
		{
			this.types[i] = types[i];
		}
	}
	
	public Point[] getTiles()
	{
		return tiles;
	}
	
	public Poi.type[] getTypes()
	{
		return types;
	}
	
	@Override
	public String toString()
	{
		String s ="Replenish poi   " + "tiles: (";
		for(int i = 0 ; i< tiles.length;i++)
		{
			s+= tiles[i].x+", "+tiles[i].y;
		}
		return s+")";
	}
}
