package pieces;

import actions.PlayerAction;

import java.awt.*;

public class Human extends Player
{
	public Human(String name, Color color)
	{
		super(name, color);
	}
	
	@Override
	public PlayerAction getAction()
	{
		return action;
	}
	
	public void setAction(PlayerAction action)
	{
		this.action = action;
	}
}
