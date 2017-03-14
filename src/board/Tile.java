package board;

import pieces.Piece;

import java.util.ArrayList;

public class Tile extends AbstractTile
{
	private ArrayList<Piece> pieces;
	
	public Tile()
	{
		pieces = new ArrayList<>();
	}
	
	public ArrayList<Piece> getPieces()
	{
		return pieces;
	}
	
	//TODO make sure that there is only 1 flame on the tile
	public void addPiece(Piece p)
	{
		pieces.add(p);
	}
	
	public void removePiece(Piece p)
	{
		pieces.remove(p);
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
