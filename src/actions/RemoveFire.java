package actions;

import mecanics.Direction;

public class RemoveFire extends PlayerAction
{
	public RemoveFire(Direction direction)
	{
		super(direction);
		cost = 2;
	}
}
