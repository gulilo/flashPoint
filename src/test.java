import Gui.Window;
import mecanics.GameMaster;

public class test
{
	public static void main(String[] arr)
	{
		GameMaster.getInstance().startGame(3);
		Window w = new Window();
	}
}
