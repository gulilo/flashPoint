package Gui;

import mecanics.GuiMaster;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChoosePlayersPanel extends JPanel
{
	private final Dimension BUTTON_HOLDER_SIZE = new Dimension(200, 400);
	
	private static final int MAX_PLAYERS = 6;
	private final Color[] playersColors = {Color.BLUE, Color.green, Color.YELLOW, Color.MAGENTA, Color.orange, Color.white};
	private final String[] colorNames = {"Blue", "Green", "Yellow", "Pink", "Orange", "White"};
	
	private int numOfPlayers;
	
	private JPanel players;
	private JPanel playersButtonPanel;
	private PlayerPanel[] playerPanels;
	
	public ChoosePlayersPanel(Dimension size)
	{
		super();
		setLocation(0, 0);
		setSize(size);
		setLayout(null);
		setBackground(Color.gray);
		
		playerPanels = new PlayerPanel[6];
		
		players = new JPanel();
		players.setSize(800, 500);
		players.setLocation(20, 20);
		players.setLayout(null);
		
		playersButtonPanel = new JPanel();
		playersButtonPanel.setLocation(30, 70);
		playersButtonPanel.setSize(250, 30);
		players.add(playersButtonPanel);
		
		JButton addPlayerButton = new JButton("Add player");
		addPlayerButton.setSize(100, 30);
		addPlayerButton.setLocation(0, 0);
		addPlayerButton.setFocusable(false);
		addPlayerButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				addPlayer();
			}
		});
		playersButtonPanel.add(addPlayerButton);
		addPlayer();
		
		JButton removePlayerButton = new JButton("Remove Player");
		removePlayerButton.setSize(100, 30);
		removePlayerButton.setLocation(150, 0);
		removePlayerButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				removePlayer();
			}
		});
		playersButtonPanel.add(removePlayerButton);
		
		JPanel buttons = new JPanel();
		buttons.setSize(BUTTON_HOLDER_SIZE);
		buttons.setLocation(size.width - BUTTON_HOLDER_SIZE.width, size.height - BUTTON_HOLDER_SIZE.height);
		buttons.setLayout(new GridLayout(0, 1, 0, 50));
		add(buttons);
		JButton start = new JButton("Start");
		start.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String[] names = new String[numOfPlayers];
				Color[] colors = new Color[numOfPlayers];
				
				for(int i = 0; i < numOfPlayers; i++)
				{
					names[i] = playerPanels[i].getText();
					colors[i] = playerPanels[i].getColor();
				}
				GuiMaster.getInstance().startGame(names, colors);
			}
		});
		buttons.add(start);
		JButton back = new JButton("back");
		back.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GuiMaster.getInstance().backToMainMenu();
			}
		});
		buttons.add(back);
		
		add(players);
	}
	
	private void addPlayer()
	{
		Point loc = playersButtonPanel.getLocation();
		playerPanels[numOfPlayers] = new PlayerPanel(loc);
		players.add(playerPanels[numOfPlayers]);
		
		numOfPlayers++;
		if(numOfPlayers < MAX_PLAYERS)
		{
			playersButtonPanel.setLocation(loc.x, loc.y + 30);
		}
		else
		{
			playersButtonPanel.setVisible(false);
		}
		players.revalidate();
		players.repaint();
	}
	
	private void removePlayer()//TODO the remove is also gone
	{
		Point loc = playersButtonPanel.getLocation();
		
		numOfPlayers--;
		players.remove(playerPanels[numOfPlayers]);
		playerPanels[numOfPlayers] = null;
		
		playersButtonPanel.setLocation(loc.x, loc.y - 30);
		players.revalidate();
		players.repaint();
	}
	
	
	private class PlayerPanel extends JPanel
	{
		private JComboBox<String> colorBox;
		private JTextField nameField;
		
		public PlayerPanel(Point location)
		{
			super();
			setSize(600, 20);
			setLocation(location);
			setLayout(null);
			setBackground(Color.YELLOW);
			
			//name section
			JLabel nameLabel = new JLabel("Name");
			nameLabel.setLocation(0, 0);
			nameLabel.setSize(50, 20);
			add(nameLabel);
			nameField = new JTextField();
			nameField.setSize(100, 20);
			nameField.setLocation(50, 0);
			add(nameField);
			
			//color section
			JLabel colorLabel = new JLabel("Color");
			colorLabel.setLocation(200, 0);
			colorLabel.setSize(500, 20);
			add(colorLabel);
			colorBox = new JComboBox<>(colorNames);
			colorBox.setLocation(240, 0);
			colorBox.setSize(100, 20);
			add(colorBox);
		}
		
		private Color getColor()
		{
			for(int i = 0; i < colorNames.length; i++)
			{
				if(colorBox.getSelectedItem().equals(colorNames[i]))
				{
					return playersColors[i];
				}
			}
			return null;//will never happen
		}
		
		public String getText()
		{
			return nameField.getText();
		}
	}
}
