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
		
		add(new BoardPanel(new Point(10, 10), new Dimension(350,280)));
		
		availableActions = new JComboBox<>();
		availableActions.setSize(150,30);
		availableActions.setLocation(500,50);
		add(availableActions);
		updateComboBox();
		
		JButton doAction = new JButton("do action");
		doAction.setSize(150,100);
		doAction.setLocation(500,100);
		doAction.addActionListener(e -> GameMaster.getInstance().doAction((PlayerAction) availableActions.getSelectedItem()));
		add(doAction);
		
	}
	
	public void updateComboBox()
	{
		availableActions.setModel(new DefaultComboBoxModel<PlayerAction>());
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
