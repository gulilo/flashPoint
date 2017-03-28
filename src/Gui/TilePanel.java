package Gui;

import board.AmbulanceTile;
import board.Tile;
import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TilePanel extends JPanel
{
	public static final Color AMBULANCE_COLOR = new Color(255,170,200);
	
	public static final Dimension TILE_SIZE = new Dimension(100, 100);
	private Tile tile;
	private Point playerLoc = null;
	private int i,j;
	
	public TilePanel(Point location, Tile tile,int i, int j)
	{
		super();
		setLayout(null);
		setLocation(location);
		setSize(TILE_SIZE);
		//setBackground(new Color(255,130,40));
		//setBackground(Color.GRAY);
		this.tile = tile;
		this.i = i;
		this.j = j;
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor((tile instanceof AmbulanceTile)?AMBULANCE_COLOR :new Color(255, 130, 40));
		g.fillRect(2, 2, getWidth() - 3, getHeight() - 3);
		ArrayList<Piece> pieces = tile.getPieces();
		for(Piece piece : pieces)
		{
			paintPiece(piece, g);
		}
		g.setColor(Color.black);
		g.drawString(i+","+j,TILE_SIZE.width/2,TILE_SIZE.height/2);
	}
	
	private void paintPiece(Piece piece, Graphics g)
	{
		Dimension size = new Dimension(20, 20);
		if(piece instanceof Player)
		{
			g.setColor(((Player) piece).getColor());
			if(playerLoc == null)
			{
				playerLoc = new Point(size.width / 2, TILE_SIZE.height / 2 - size.height / 2);
			}
			else
			{
				playerLoc = new Point(playerLoc.x + TILE_SIZE.width / 3, playerLoc.y);
				if(playerLoc.x > TILE_SIZE.width)
				{
					playerLoc = new Point(size.width/2, TILE_SIZE.height / 2 - size.height / 2);
				}
			}
			g.fillRect(playerLoc.x,playerLoc.y, size.width, size.height);
			
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
			g.fillRect(TILE_SIZE.width / 4 - size.width / 2, TILE_SIZE.height / 4 - size.height / 2, size.width, size.height);
		}
		else
		{
			if(piece instanceof HiddenPoi)
			{
				g.setColor(Color.CYAN);
			}
			else
			{
				g.setColor(Color.DARK_GRAY);
			}
			g.fillRect((3 * TILE_SIZE.width) / 4 - size.width / 2, TILE_SIZE.height / 4 - size.height / 2, size.width, size.height);
		}
	}
}
