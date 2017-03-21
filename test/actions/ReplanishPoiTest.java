package actions;

import board.AbstractTile;
import board.Board;
import board.Tile;
import exeptions.*;
import mecanics.ArraylistHelper;
import mecanics.Reducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.*;

import java.awt.*;
import java.util.ArrayList;

import static junit.framework.TestCase.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReplanishPoiTest
{
	private Board board;
	private ReplenishPoi action;
	
	@BeforeEach
	void setUp()
	{
		board = new Board(new Point(3,3));
		try
		{
			action = new ReplenishPoi(new Point[]{new Point(1, 1), new Point(2, 1)}, new Poi.type[]{Poi.type.victim, Poi.type.falseAlarm});
		}
		catch(ReplenishPoiException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	void actionPlacePoi()
	{

		Board afterAction = null;
		try
		{
			afterAction = Reducer.doAction(null, action, board);
		}
		catch(ActionException | BadBoardException | PoiException | WallException e)
		{
			e.printStackTrace();
			fail();
		}
		
		ArrayList<ArrayList<Piece>> change = ArraylistHelper.changes(((Tile) board.getTile(new Point(1, 1))).getPieces(), ((Tile) afterAction.getTile(new Point(1, 1))).getPieces());
		ArrayList<Piece> added = change.get(0);
		ArrayList<Piece> removed = change.get(1);
		assertEquals("something removed that didn't supposed to", 0, removed.size());
		assertEquals("something added that didn't supposed to", 1, added.size());
		assertTrue("the poi didn't added", ArraylistHelper.containsInstance(added, HiddenPoi.class));
		assertTrue("the victim revealed", ((Poi) added.get(0)).isHidden());

		change = ArraylistHelper.changes(((Tile) board.getTile(new Point(2, 1))).getPieces(), ((Tile) afterAction.getTile(new Point(2, 1))).getPieces());
		added = change.get(0);
		removed = change.get(1);
		assertEquals("something removed that didn't supposed to", 0, removed.size());
		assertEquals("something added that didn't supposed to", 1, added.size());
		assertTrue("the poi didn't add", ArraylistHelper.containsInstance(added, HiddenPoi.class));
		assertTrue("the false alarm revealed", ((Poi) added.get(0)).isHidden());
	}
	
	@Test
	void actionPlacePoiOnPlayer()
	{
		Human human = new Human(Color.BLUE);
		board.addPiece(new Point(1,1),human);
		Human human2 = new Human(Color.BLUE);
		board.addPiece(new Point(2,1),human2);
		Board afterAction = null;
		try
		{
			afterAction = Reducer.doAction(null, action, board);
		}
		catch(ActionException | BadBoardException | PoiException | WallException e)
		{
			e.printStackTrace();
			fail();
		}
		ArrayList<ArrayList<Piece>> change = ArraylistHelper.changes(((Tile) board.getTile(new Point(1, 1))).getPieces(), ((Tile) afterAction.getTile(new Point(1, 1))).getPieces());
		ArrayList<Piece> added = change.get(0);
		ArrayList<Piece> removed = change.get(1);
		assertEquals("something removed that didn't supposed to", 0, removed.size());
		assertEquals("something added that didn't supposed to", 1, added.size());
		assertTrue("the poi didn't add", ArraylistHelper.containsInstance(added, Victim.class));
		assertFalse("the victim didn't revealed", ((Poi) added.get(0)).isHidden());

		change = ArraylistHelper.changes(((Tile) board.getTile(new Point(2, 1))).getPieces(), ((Tile) afterAction.getTile(new Point(2, 1))).getPieces());
		added = change.get(0);
		removed = change.get(1);
		assertEquals("something removed that didn't supposed to", 0, removed.size());
		assertEquals("something added that didn't supposed to", 0, added.size());
	}
	
	@Test
	void actionPlacePoiOnFire()
	{
		Fire fire = new Fire();
		board.addPiece(new Point(1,1),fire);
		Fire fire2 = new Fire();
		board.addPiece(new Point(2,1),fire2);
		Board afterAction = null;
		try
		{
			afterAction = Reducer.doAction(null, action, board);
		}
		catch(ActionException | BadBoardException | PoiException | WallException e)
		{
			e.printStackTrace();
			fail();
		}
		ArrayList<ArrayList<Piece>> change = ArraylistHelper.changes((board.getTile(new Point(1, 1))).getPieces(), (afterAction.getTile(new Point(1, 1))).getPieces());
		ArrayList<Piece> added = change.get(0);
		ArrayList<Piece> removed = change.get(1);
		assertEquals("something removed that didn't supposed to", 1, removed.size());
		assertTrue("the fire didn't remove", ArraylistHelper.containsInstance(removed, Fire.class));
		assertEquals("something added that didn't supposed to", 1, added.size());
		assertTrue("the poi didn't added", ArraylistHelper.containsInstance(added, HiddenPoi.class));
		assertTrue("the victim revealed", ((Poi) added.get(0)).isHidden());

		change = ArraylistHelper.changes(((Tile) board.getTile(new Point(2, 1))).getPieces(), ((Tile) afterAction.getTile(new Point(2, 1))).getPieces());
		added = change.get(0);
		removed = change.get(1);
		assertEquals("something removed that didn't supposed to", 1, removed.size());
		assertTrue("the fire didn't remove", ArraylistHelper.containsInstance(removed, Fire.class));
		assertEquals("something added that didn't supposed to", 1, added.size());
		assertTrue("the poi didn't added", ArraylistHelper.containsInstance(added, HiddenPoi.class));
		assertTrue("the false alarm revealed", ((Poi) added.get(0)).isHidden());
	}
	
	@Test
	void actionPlacePoiOnSmoke()
	{
		Smoke smoke = new Smoke();
		board.addPiece(new Point(1,1),smoke);
		Smoke smoke2 = new Smoke();
		board.addPiece(new Point(2,1),smoke2);
		Board afterAction = null;
		try
		{
			afterAction = Reducer.doAction(null, action, board);
		}
		catch(ActionException | BadBoardException | PoiException | WallException e)
		{
			e.printStackTrace();
			fail();
		}
		AbstractTile[][] tiles = afterAction.getTiles();
		ArrayList<ArrayList<Piece>> change = ArraylistHelper.changes(((Tile) board.getTile(new Point(1, 1))).getPieces(), ((Tile) afterAction.getTile(new Point(1, 1))).getPieces());
		ArrayList<Piece> added = change.get(0);
		ArrayList<Piece> removed = change.get(1);
		assertEquals("something removed that didn't supposed to", 1, removed.size());
		assertTrue("the fire didn't remove", ArraylistHelper.containsInstance(removed, Smoke.class));
		assertEquals("something added that didn't supposed to", 1, added.size());
		assertTrue("the poi didn't added", ArraylistHelper.containsInstance(added, HiddenPoi.class));
		assertTrue("the victim revealed", ((Poi) added.get(0)).isHidden());

		change = ArraylistHelper.changes(((Tile) board.getTile(new Point(2, 1))).getPieces(), ((Tile) afterAction.getTile(new Point(2, 1))).getPieces());
		added = change.get(0);
		removed = change.get(1);
		
		assertEquals("something removed that didn't supposed to", 1, removed.size());
		assertTrue("the fire didn't remove", ArraylistHelper.containsInstance(removed, Smoke.class));
		assertEquals("something added that didn't supposed to", 1, added.size());
		assertTrue("the poi didn't added", ArraylistHelper.containsInstance(added, HiddenPoi.class));
		assertTrue("the false alarm revealed", ((Poi) added.get(0)).isHidden());
	}
	
	@Test
	void actionPlacePoiOnPoi()
	{
		HiddenPoi victim = null;
		Victim victim2 = null;
		try
		{
			victim = new HiddenPoi(Poi.type.victim);
			victim2 = new Victim();
		}
		catch(PoiException e)
		{
			e.printStackTrace();
		}
		board.addPiece(new Point(1,1),victim);
		board.addPiece(new Point(1,1),victim2);
		assertThrows(ReplenishPoiException.class, () ->
		{
			Reducer.doAction(null, action, board);
		});
	}
}
