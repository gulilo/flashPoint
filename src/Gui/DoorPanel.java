package Gui;

import board.DoorTile;

import javax.swing.*;
import java.awt.*;

public class DoorPanel extends JPanel
{
	public static final Color OPEN_COLOR = new Color(30, 170, 80);
	public static final Color CLOSE_COLOR = new Color(180, 230, 30);
	public static final Color BROKEN_COLOR = new Color(70, 140, 20);
	private DoorTile tile;
	
	public DoorPanel(Point location, Dimension size, DoorTile tile)
	{
		super();
		setLayout(null);
		setLocation(location);
		setSize(size);
		setBackground(Color.black);
		this.tile = tile;
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(tile.isBroken())
		{
			g.setColor(BROKEN_COLOR);
		}
		else if(tile.isOpen())
		{
			g.setColor(OPEN_COLOR);
		}
		else
		{
			g.setColor(CLOSE_COLOR);
		}
		setBackground(g.getColor());
		//g.fillRect(getX(), getY(), getWidth() * 5, getHeight() * 5);
		//g.drawRect(0,0,getWidth()*5,getHeight()*5);
		
	}
}
