package Gui;

import javax.swing.*;
import java.awt.*;

public class IndexPanel extends JPanel
{
	public IndexPanel(Point location, Dimension size)
	{
		super();
		setSize(size);
		setLocation(location);
		setLayout(null);
		
		JLabel l = new JLabel("Index");
		l.setLocation(size.width/2 - 30,10);
		l.setSize(60,20);
		add(l);
		JLabel wall = new JLabel("Wall");
		wall.setLocation(10,40);
		wall.setSize(60,20);
		add(wall);
		JLabel dmgWall = new JLabel("Dmg wall");
		dmgWall.setLocation(65,40);
		dmgWall.setSize(60,20);
		add(dmgWall);
		JLabel brkWall = new JLabel("Brk wall");
		brkWall.setLocation(140,40);
		brkWall.setSize(60,20);
		add(brkWall);
		
		JLabel openDoor = new JLabel("Open door");
		openDoor.setLocation(2,90);
		openDoor.setSize(60,20);
		add(openDoor);
		JLabel closeDoor = new JLabel("Close door");
		closeDoor.setLocation(70,90);
		closeDoor.setSize(70,20);
		add(closeDoor);
		JLabel brkDoor = new JLabel("Broken door");
		brkDoor.setLocation(150,90);
		brkDoor.setSize(80,20);
		add(brkDoor);
		
		JLabel fire = new JLabel("Fire");
		fire.setLocation(250,40);
		fire.setSize(80,20);
		add(fire);
		
		JLabel smoke = new JLabel("Smoke");
		smoke.setLocation(250,90);
		smoke.setSize(80,20);
		add(smoke);
		
		JLabel hidden = new JLabel("Hidden Poi");
		hidden.setLocation(300,40);
		hidden.setSize(80,20);
		add(hidden);
		
		JLabel victim = new JLabel("Victim ");
		victim.setLocation(300,90);
		victim.setSize(80,20);
		add(victim);
		
		
		JLabel amnu = new JLabel("ambulance Tile");
		amnu.setLocation(390,40);
		amnu.setSize(120,20);
		add(amnu);
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(WallPanel.FIX_COLOR);
		g.fillRect(7,65,30,5);
		
		g.setColor(WallPanel.BRK_COLOR);
		g.fillRect(75,65,30,5);
		
		g.setColor(WallPanel.DMG_COLOR);
		g.fillRect(150,65,30,5);

		g.setColor(DoorPanel.OPEN_COLOR);
		g.fillRect(7,115,30,5);
		
		g.setColor(DoorPanel.CLOSE_COLOR);
		g.fillRect(75,115,30,5);
		
		g.setColor(DoorPanel.BROKEN_COLOR);
		g.fillRect(150,115,30,5);
		
		g.setColor(Color.RED);
		g.fillRect(250,65,20,20);
		g.setColor(Color.GRAY);
		g.fillRect(250,115,20,20);
		
		g.setColor(Color.CYAN);
		g.fillRect(300,65,20,20);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(300,115,20,20);
		
		g.setColor(TilePanel.AMBULANCE_COLOR);
		g.fillRect(420,65,30,30);
	}
}
