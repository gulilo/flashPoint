package board;

import java.util.ArrayList;

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
		Tile t = new Tile();
		t.pieces = new ArrayList<>(pieces);
		return t;
	}
	
	@Override
	public boolean canMoveTo()
	{
		return true;
	}
}
