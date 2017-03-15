package Gui;

import board.Tile;
import pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TilePanel extends JPanel
{
	public static final Dimension TILE_SIZE = new Dimension(50, 50);
	private Tile tile;
	
	public TilePanel(Point location, Tile tile)
	{
		super();
		setLayout(null);
		setLocation(location);
		setSize(TILE_SIZE);
		setBackground(new Color(255,130,40));
		this.tile = tile;
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		ArrayList<Piece> pieces = tile.getPieces();
		
		for(int i = 0; i < pieces.size(); i++)
		{
			paintPiece(pieces.get(i),g);
		}
	}
	
	private void paintPiece(Piece piece,Graphics g)
	{
		
	}
}
