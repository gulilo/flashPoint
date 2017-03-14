package Gui;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel
{
	public MainPanel(Dimension size)
	{
		setLayout(null);
		setLocation(0, 0);
		setSize(size);
		setBackground(Color.red);
		
		add(new BoardPanel(new Point(10, 10), new Dimension(350,280)));
	}
}
