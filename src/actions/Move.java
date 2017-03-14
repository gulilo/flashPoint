package actions;

import mecanics.Direction;

public class Move extends PlayerAction
{
	public Move(Direction direction)
	{
		super(direction);
		cost = 1;
	}
}
