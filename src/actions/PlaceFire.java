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
}
