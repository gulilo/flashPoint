package actions;

import mecanics.Direction;

public class OpenCloseDoor extends PlayerAction
{
	public OpenCloseDoor(Direction direction)
	{
		super(direction);
		cost = 1;
	}
}
