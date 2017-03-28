package actions;

import java.awt.*;

public class PlaceFire extends Action
{
	private Point tile;
	
	public PlaceFire(Point point)
	{
		tile = new Point(point);
	}
	
	public Point getTile()
	{
		return tile;
	}
	
	@Override
	public String toString()
	{
		return "Place fire   tile:(" + tile.x + ", "+ tile.y+")";
	}
}
