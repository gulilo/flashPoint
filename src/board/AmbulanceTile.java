package board;

public class AmbulanceTile extends Tile
{
	public AmbulanceTile()
	{
		super();
	}

    public AmbulanceTile(Tile tile)
	{
		for(int i = 0;i<tile.getPieces().size();i++)
		{
			addPiece(tile.getPieces().get(i));
		}
    }

    @Override
	public AbstractTile copy()
	{
		return new AmbulanceTile();
	}
	
	@Override
	public boolean canMoveTo()
	{
		return true;
	}
}
