package board;

import exeptions.WallException;

public class WallTile extends AbstractTile
{
	private int damage;
	
	public void doDamage() throws WallException
	{
		if(isBroken())
		{
			throw new WallException("cant damage wall more the 2 times");
		}
		damage++;
	}
	
	@Override
	public AbstractTile copy()
	{
		WallTile t = new WallTile();
		t.damage = damage;
		return t;
	}
	
	@Override
	public boolean canMoveTo()
	{
		return damage == 2;
	}
	
	public boolean isDamage()
	{
		return damage == 1;
	}
	
	public boolean isBroken()
	{
		return damage == 2;
	}
}
