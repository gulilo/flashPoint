package pieces;

import actions.PlayerAction;
import exeptions.ActionException;

import java.awt.*;

public abstract class Player extends Piece
{
	public static final int MAX_ACTION_POINTS = 8;
	private Color color;
	private String name;
	
	protected PlayerAction action;
	
	private int actionPoints;
	private boolean carryVictim;
	
	public Player(String name, Color color)
	{
		actionPoints = 4;
		this.color = color;
		this.name = name;
		action = null;
	}
	
	public abstract PlayerAction getAction();
	
	public int getActionPoints()
	{
		return actionPoints;
	}
	
	public void useActionPoints(int actionPoints) throws ActionException
	{
		if(this.actionPoints >= actionPoints)
		{
			this.actionPoints -= actionPoints;
		}
		else
		{
			throw new ActionException("not enough action points");
		}
	}
	
	public void addActionPoint(int actionPoints)
	{
		this.actionPoints = Math.min(actionPoints + this.actionPoints, MAX_ACTION_POINTS);
	}
	
	public void carryVictim()
	{
		carryVictim = true;
	}
	
	public void stopCarryVictim()
	{
		carryVictim = false;
	}
	
	public boolean isCarry()
	{
		return carryVictim;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public String getName()
	{
		return name;
	}
}

