package Gui;

import board.Board;
import exeptions.BadBoardException;
import mecanics.GameMaster;

import javax.swing.*;
import java.awt.*;

import static Gui.TilePanel.TILE_SIZE;
import static Gui.WallPanel.HOR_WALL_SIZE;
import static Gui.WallPanel.VER_WALL_SIZE;

public class BoardPanel extends JPanel
{
	public BoardPanel(Point location, Dimension size)
	{
		super();
		setLayout(null);
		setSize(size);
		setLocation(location);
		setBackground(Color.black);
		updateBoard();
	}
	
	private void updateBoard()
	{
		removeAll();
		Board board = GameMaster.getInstance().getBoard();
		Point tileLocation = new Point(0, 0);
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
				add(new TilePanel(tileLocation, board.getTile(new Point(i, j))));
				tileLocation = new Point(tileLocation.x + TILE_SIZE.width, tileLocation.y);
			}
			tileLocation = new Point(0, tileLocation.y + TILE_SIZE.height);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		updateBoard();
	}
}
