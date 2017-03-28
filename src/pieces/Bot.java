package pieces;

import actions.PlayerAction;

import java.awt.*;

public class Bot extends Player
{
	public Bot(String name, Color color)
	{
		super(name, color);
	}
	
	@Override
	public PlayerAction getAction()
	{
		return null;
	}
}
