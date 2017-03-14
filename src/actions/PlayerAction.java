package actions;

import mecanics.Direction;

public abstract class PlayerAction extends Action
{
	int cost;
	Direction direction;
	
	public PlayerAction(Direction direction)
	{
		this.direction = direction;
	}
	
	public int getCost()
	{
		return cost;
	}
	
	public Direction getDirection()
	{
		return direction;
	}
}

