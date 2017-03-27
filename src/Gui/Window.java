package Gui;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame
{
	private boolean open;
	
	public Window(Point location, Dimension size)
	{
		super("flash point");
		setResizable(false);
		setUndecorated(false);
		setLayout(null);
		setLocation(location);
		setSize(size);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void open()
	{
		open = true;
		setVisible(true);
	}
	
	public void close()
	{
		open = false;
		setVisible(false);
		dispose();
	}
	
	public boolean isOpen()
	{
		return open;
	}
}
