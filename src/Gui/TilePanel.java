package Gui;

import board.Tile;
import pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TilePanel extends JPanel
{
	private Tile tile;
	
	public TilePanel(Point location, Dimension size, Tile tile)
	{
		setLayout(null);
		setLocation(location);
		setSize(size);
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
