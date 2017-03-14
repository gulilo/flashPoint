package actions;

import mecanics.Direction;

public class LeaveVictim extends PlayerAction
{
	public LeaveVictim()
	{
		super(Direction.stay);
		cost = 0;
	}
}
