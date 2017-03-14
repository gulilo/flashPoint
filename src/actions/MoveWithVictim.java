package actions;

import mecanics.Direction;

public class MoveWithVictim extends Move
{
	public MoveWithVictim(Direction direction)
	{
		super(direction);
		cost = 2;
	}
}
