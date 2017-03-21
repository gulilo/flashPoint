package Gui;

import actions.PlayerAction;
import exeptions.BadBoardException;
import mecanics.GameMaster;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainPanel extends JPanel
{
	private JComboBox<PlayerAction> availableActions;
	public MainPanel(Dimension size)
	{
		super();
		
		setLayout(null);
		setLocation(0, 0);
		setSize(size);
		setBackground(Color.red);
		Dimension boardSize = new Dimension(TilePanel.TILE_SIZE.width* GameMaster.getInstance().getBoard().getSize().y, TilePanel.TILE_SIZE.height * GameMaster.getInstance().getBoard().getSize().x);
		add(new BoardPanel(new Point(10, 10), boardSize));
		
		availableActions = new JComboBox<>();
		availableActions.setSize(150,30);
		availableActions.setLocation(50,boardSize.height+30);
		add(availableActions);
		updateComboBox();
		
		JButton doAction = new JButton("do action");
		doAction.setSize(150,70);
		doAction.setLocation(50,boardSize.height+70);
		doAction.addActionListener(e -> GameMaster.getInstance().doAction((PlayerAction) availableActions.getSelectedItem()));
		add(doAction);
		
	}
	
	public void updateComboBox()
	{
		availableActions.setModel(new DefaultComboBoxModel<>());
		try
		{
			ArrayList<PlayerAction> actions = GameMaster.getInstance().getBoard().getAvailableActions(GameMaster.getInstance().getCurrentPlayer());
			for(PlayerAction action : actions)
			{
				availableActions.addItem(action);
			}
		}
		catch(BadBoardException e)
		{
			e.printStackTrace();
		}
	}
}
