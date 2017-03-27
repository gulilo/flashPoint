package Gui;

import mecanics.GuiMaster;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChoosePlayersPanel extends JPanel
{
	private final Dimension BUTTON_HOLDER_SIZE = new Dimension(200,400);
	
	private static final int MAX_PLAYERS = 6;
	private final Color[] playersColors = {Color.BLUE, Color.green, Color.YELLOW, Color.MAGENTA, Color.orange, Color.white};
	private final String[] colorNames = {"Blue", "Green", "Yellow","Pink", "Orange", "White"};
	
	private int numOfPlayers;
	
	private JPanel players;
	private JButton addPlayerButton;
	private JComboBox<String>[] colorsboxes;
	private JTextField[] namesFields;
	
	public ChoosePlayersPanel(Dimension size)
	{
		super();
		setLocation(0, 0);
		setSize(size);
		setLayout(null);
		setBackground(Color.gray);
		
		colorsboxes = new JComboBox[6];
		namesFields = new JTextField[6];
		
		players = new JPanel();
		players.setSize(800, 500);
		players.setLocation(20, 20);
		players.setLayout(null);
		addPlayerButton = new JButton("Add Player");
		addPlayerButton.setSize(100, 30);
		addPlayerButton.setLocation(30, 70);
		addPlayerButton.setFocusable(false);
		addPlayerButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				addPlayer();
			}
		});
		players.add(addPlayerButton);
		addPlayer();
		JPanel buttons = new JPanel();
		buttons.setSize(BUTTON_HOLDER_SIZE);
		buttons.setLocation(size.width-BUTTON_HOLDER_SIZE.width,size.height-BUTTON_HOLDER_SIZE.height);
		buttons.setLayout(new GridLayout(0,1,0,50));
		add(buttons);
		JButton start = new JButton("Start");
		start.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String[] names = new String[numOfPlayers];
				Color[] colors = new Color[numOfPlayers];
				
				for(int i = 0;i < numOfPlayers;i++)
				{
					names[i] = namesFields[i].getText();
					colors[i] = getColor((String) colorsboxes[i].getSelectedItem());
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
		Point loc = addPlayerButton.getLocation();
		
		//name section
		JLabel nameLabel = new JLabel("Name");
		nameLabel.setLocation(30, loc.y);
		nameLabel.setSize(50, 20);
		players.add(nameLabel);
		namesFields[numOfPlayers] = new JTextField();
		namesFields[numOfPlayers].setSize(100, 20);
		namesFields[numOfPlayers].setLocation(80, loc.y);
		players.add(namesFields[numOfPlayers]);
		
		//color section
		JLabel colorLabel = new JLabel("Color");
		colorLabel.setLocation(220, loc.y);
		colorLabel.setSize(500, 20);
		players.add(colorLabel);
		colorsboxes[numOfPlayers] = new JComboBox<>(colorNames);
		colorsboxes[numOfPlayers].setLocation(260, loc.y);
		colorsboxes[numOfPlayers].setSize(100, 20);
		players.add(colorsboxes[numOfPlayers]);
		
		numOfPlayers++;
		if(numOfPlayers < MAX_PLAYERS)
		{
			addPlayerButton.setLocation(loc.x, loc.y + 30);
		}
		else
		{
			addPlayerButton.setVisible(false);
		}
		players.revalidate();
		players.repaint();
	}
	
	private Color getColor(String color)
	{
		Color c;
		for(int i = 0; i < colorNames.length;i++)
		{
			if(color.equals(colorNames[i]))
			{
				return playersColors[i];
			}
		}
		return null;//will never happen
	}
}
