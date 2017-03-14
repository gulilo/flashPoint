package actions;

import mecanics.Direction;

public class Extinguish extends PlayerAction
{
	public Extinguish(Direction direction)
	{
		super(direction);
		cost = 1;
	}
}
