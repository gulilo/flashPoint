package Gui;

import board.Board;
import exeptions.BadBoardException;
import mecanics.GameMaster;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel
{
	private final Dimension TILE_SIZE = new Dimension(50, 50);
	private final Dimension HOR_WALL_SIZE = new Dimension(5, 50);
	private final Dimension VER_WALL_SIZE = new Dimension(50, 5);
	
	
	public BoardPanel(Point location, Dimension size)
	{
		setLayout(null);
		setSize(size);
		setLocation(location);
		setBackground(Color.black);
		Board board = GameMaster.getInstance().getBoard();
		
		
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Board board = GameMaster.getInstance().getBoard();
		Point tileLocation = new Point(2, 2);
		Point wallLocation;
		for(int i = 0; i < board.getSize().x; i++)
		{
			for(int j = 0; j < board.getSize().y; j++)
			{
				wallLocation = new Point(tileLocation.x-3,tileLocation.y-3);
				Point p1 = new Point(i, j);
				Point p2 = new Point(i - 1, j);
				
				if(Board.isValidLocation(p2, board))
				{
					try
					{
						if(board.isWall(p1, p2))
						{
							add(new WallPanel(wallLocation, VER_WALL_SIZE, board.getWall(p1, p2)));
							
						}
						else if(board.isDoor(p1, p2))
						{
							add(new DoorPanel(wallLocation, VER_WALL_SIZE, board.getDoor(p1, p2)));
						}
					}
					catch(BadBoardException e)
					{
						e.printStackTrace();
					}
				}
				p2 = new Point(i, j - 1);
				if(Board.isValidLocation(p2, board))
				{
					try
					{
						if(board.isWall(p1, p2))
						{
							add(new WallPanel(wallLocation, HOR_WALL_SIZE, board.getWall(p1, p2)));
							
						}
						else if(board.isDoor(p1, p2))
						{
							add(new DoorPanel(wallLocation, HOR_WALL_SIZE, board.getDoor(p1, p2)));
						}
					}
					catch(BadBoardException e)
					{
						e.printStackTrace();
					}
				}
				
				add(new TilePanel(tileLocation, TILE_SIZE, board.getTile(new Point(i, j))));
				tileLocation = new Point(tileLocation.x + TILE_SIZE.width + 2, tileLocation.y);
			}
			tileLocation = new Point(2, tileLocation.y + TILE_SIZE.height + 2);
		}
		
	}
}
