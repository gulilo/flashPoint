package Gui;

import board.Tile;
import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TilePanel extends JPanel
{
	public static final Dimension TILE_SIZE = new Dimension(100, 100);
	private Tile tile;
	
	public TilePanel(Point location, Tile tile)
	{
		super();
		setLayout(null);
		setLocation(location);
		setSize(TILE_SIZE);
		//setBackground(new Color(255,130,40));
		//setBackground(Color.GRAY);
		this.tile = tile;
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(new Color(255,130,40));
		g.fillRect(2,2,getWidth()-2,getHeight()-2);
		ArrayList<Piece> pieces = tile.getPieces();
		for(Piece piece : pieces)
		{
			paintPiece(piece, g);

		}
	}
	
	private void paintPiece(Piece piece, Graphics g)
	{
		Dimension size = new Dimension(30,30);
		if(piece instanceof Player)
		{
			g.setColor(((Player) piece).getColor());
			g.fillRect((3*TILE_SIZE.width)/4-size.width/2, (3*TILE_SIZE.height)/4-size.height/2,size.width,size.height);
			
		}
		else if(piece instanceof Flame)
		{
			if(piece instanceof Fire)
			{
				g.setColor(Color.red);
			}
			else
			{
				g.setColor(Color.gray);
			}
			g.fillRect(TILE_SIZE.width/4-size.width/2, TILE_SIZE.height/4-size.height/2,size.width,size.height);
		}
		else
		{
			if(piece instanceof HiddenPoi)
			{
				g.setColor(Color.CYAN);
			}
			else
			{
				g.setColor(Color.MAGENTA);
			}
			g.fillRect((3*TILE_SIZE.width)/4-size.width/2, TILE_SIZE.height/4-size.height/2,size.width,size.height);
		}
	}
}
