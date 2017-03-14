package board;

public abstract class AbstractTile
{
	public abstract AbstractTile copy();
	
	public abstract boolean canMoveTo();
}
