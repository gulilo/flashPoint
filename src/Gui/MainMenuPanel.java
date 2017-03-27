package Gui;

import mecanics.GuiMaster;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel
{
	private final Dimension BUTTON_HOLDER_SIZE = new Dimension(200,400);
	
	public MainMenuPanel(Dimension size)
	{
		super();
		setLayout( null);
		setLocation(0,0);
		setSize(size);
		setBackground(Color.GRAY);
		
		JPanel buttonHolder = new JPanel();
		buttonHolder.setSize(BUTTON_HOLDER_SIZE);
		buttonHolder.setLocation(size.width - BUTTON_HOLDER_SIZE.width,size.height - BUTTON_HOLDER_SIZE.height);
		buttonHolder.setLayout(new GridLayout(0,1,0,50));
		add(buttonHolder);
		
		JButton start = new JButton("Start game");
		start.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GuiMaster.getInstance().choosePlayers();
			}
		});
		buttonHolder.add(start);
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GuiMaster.getInstance().closeWindow();
			}
		});
		buttonHolder.add(exit);
		buttonHolder.revalidate();
	}
	
}
