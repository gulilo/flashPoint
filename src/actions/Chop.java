package actions;

import mecanics.Direction;

public class Chop extends PlayerAction
{
	public Chop(Direction direction)
	{
		super(direction);
		cost = 2;
	}
}
