package Gui;

import mecanics.GameMaster;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame
{
	private final Dimension DEFAULT_SIZE = new Dimension(700, 700);
	private final Point DEFAULT_LOCATION = new Point(200, 200);
	
	public Window()
	{
		super("flush point");
		setResizable(false);
		setUndecorated(false);
		setLayout(null);
		setLocation(DEFAULT_LOCATION);
		setSize(DEFAULT_SIZE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		MainPanel panel = new MainPanel(getSize());
		setContentPane(panel);
		GameMaster.getInstance().setMainPanel(panel);
		setVisible(true);
	}
	
}
