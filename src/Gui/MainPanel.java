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
	private JLabel points;
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
		
		points = new JLabel("action points left: 4");
		points.setSize(200,30);
		points.setLocation(availableActions.getX()+ 170,availableActions.getY());
		add(points);
		
		IndexPanel index = new IndexPanel(new Point(size.width-500,size.height-170),new Dimension(500,170));
		add(index);
		/*JPanel index = new JPanel();
		index.setSize(200,170);
		index.setLocation(size.width-200,size.height-170);
		index.setLayout(null);
		add(index);*/
		//initIndex(index);
	}
	
	private void initIndex(JPanel index)
	{
		JLabel l = new JLabel("Index");
		l.setLocation(index.getSize().width/2 - 30,10);
		l.setSize(60,20);
		index.add(l);
		JLabel wall = new JLabel("Wall");
		wall.setLocation(10,40);
		wall.setSize(60,20);
		index.add(wall);
		JLabel door = new JLabel("Door");
		door.setLocation(10,90);
		door.setSize(60,20);
		index.add(door);
		JLabel dmgWall = new JLabel("Dmg wall");
		dmgWall.setLocation(50,40);
		dmgWall.setSize(60,20);
		index.add(dmgWall);
		JLabel brkWall = new JLabel("Brk wall");
		brkWall.setLocation(120,40);
		brkWall.setSize(60,20);
		index.add(brkWall);
		
		Graphics g = getGraphics();
		g.setColor(WallPanel.FIX_COLOR);
		g.fillRect(30,10,10,10);
	}
	
	private void updateComboBox()
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
	
	private void updateActionPoints()
	{
		points.setText("action points left: "+ GameMaster.getInstance().getCurrentPlayer().getActionPoints());
	}
	
	public void updatePanel()
	{
		updateComboBox();
		updateActionPoints();
		repaint();
	}
}
