import Gui.Window;
import mecanics.GameMaster;

public class test
{
	public static void main(String[] arr)
	{
		GameMaster.getInstance().startGame(1);
		Window w = new Window();
		
		while(true)
		{
			w.repaint();
			try
			{
				Thread.sleep(40);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
