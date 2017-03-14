package actions;

import mecanics.Direction;

public class MoveToFire extends Move
{
	public MoveToFire(Direction direction)
	{
		super(direction);
		cost = 2;
	}
}
