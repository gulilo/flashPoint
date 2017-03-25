package mecanics;
import java.awt.*;
import java.util.Random;

public class Dice
{
	Random random;
	
	public Dice()
	{
		random = new Random();
	}
	
	
	public Dice(int seed)
	{
		random = new Random(seed);
	}
	
	public int rollD8()
	{
		return random.nextInt(8)+1;
	}
	
	public int rollD6()
	{
		return random.nextInt(6)+1;
	}
	
	public Point getLocation()
	{
		return new Point(rollD6(),rollD8());
	}
	
	public int rollX(int x)
	{
		return random.nextInt(x)+1;
	}
	
}
