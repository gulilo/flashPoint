package mecanics;

import mecanics.Dice;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertEquals;

class DiceTest
{
	@Test
	void rollD4Test()
	{
		Dice d = new Dice(1234);
		assertEquals("for the seed 1234 rollD4 not returned 2",2,d.rollD8());
	}
	
	@Test
	void rollD6Test()
	{
		Dice d = new Dice(1234);
		assertEquals("for the seed 1234 rollD6 not returned 2",2,d.rollD6());
	}
	
	@Test
	void getLocationTest()
	{
		Dice d = new Dice(1234);//get the right seed later
		int[] loc = d.getLocation();
		assertEquals("for the seed 1234 getlocation[0] not returned 2",2,loc[0]);
		assertEquals("for the seed 1234 getlocation[1] not returned 2",2,loc[1]);
	}
	
}