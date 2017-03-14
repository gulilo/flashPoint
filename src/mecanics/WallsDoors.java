package mecanics;

import java.awt.*;

public class WallsDoors
{
	public static final Point[][] WALLS = {{new Point(0, 1),new Point(1, 1)},{new Point(0, 2),new Point(1, 2)},
			{new Point(0, 3),new Point(1, 3)},{new Point(0, 4),new Point(1, 4)},{new Point(0,5),new Point(1,5)},{new Point(0,7),new Point(1,7)},
			{new Point(0,8),new Point(1,8)},{new Point(1,0),new Point(1,1)},{new Point(1,5),new Point(1,6)},{new Point(1,8),new Point(1,9)},
			{new Point(2,0),new Point(2,1)},{new Point(2,3),new Point(2,4)},{new Point(2,8),new Point(2,9)}};
	public static final Point[][] DOORS = {{new Point(1,6),new Point(0,6)},{new Point(1,3),new Point(1,4)},{new Point(2,5),new Point(2,6)}};
}