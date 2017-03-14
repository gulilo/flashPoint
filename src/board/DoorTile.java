package board;

//door start close
public class DoorTile extends AbstractTile
{
	private boolean open;
	private boolean broken;
	
	public void changeState()
	{
		open = !open;
	}
	
	@Override
	public AbstractTile copy()
	{
		DoorTile t = new DoorTile();
		t.open = open;
		return t;
	}
	
	@Override
	public boolean canMoveTo()
	{
		return open;
	}
	
	public boolean isOpen()
	{
		return open;
	}
	
	public void brake()
	{
		broken = true;
		open = true;
	}
	
	public boolean isBroken()
	{
		return broken;
	}
}
