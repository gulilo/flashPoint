package Gui;

import board.WallTile;

import javax.swing.*;
import java.awt.*;

public class WallPanel extends JPanel
{
	private final Color FIX_COLOR = new Color(60 ,70,200);
	private final Color DMG_COLOR = new Color(0 ,160,230);
	private final Color BRK_COLOR = new Color(150 ,210,230);
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
