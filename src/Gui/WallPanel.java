package Gui;

import board.WallTile;

import javax.swing.*;
import java.awt.*;

public class WallPanel extends JPanel
{
	public static final Dimension HOR_WALL_SIZE = new Dimension(10, 100);
	public static final Dimension VER_WALL_SIZE = new Dimension(100, 10);
	
	public static final Color FIX_COLOR = new Color(60 ,70,200);
	public static final Color DMG_COLOR = new Color(0 ,160,230);
	public static final Color BRK_COLOR = new Color(150 ,210,230);
	private WallTile tile;
	
	public WallPanel(Point location, Dimension size, WallTile tile)
	{
		super();
		setLayout(null);
		setLocation(location);
		setSize(size);
		
		this.tile = tile;
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(tile.isBroken())
		{
			g.setColor(BRK_COLOR);
		}
		else if(tile.isDamage())
		{
			g.setColor(DMG_COLOR);
		}
		else
		{
			g.setColor(FIX_COLOR);
		}
		setBackground(g.getColor());
	}
}
