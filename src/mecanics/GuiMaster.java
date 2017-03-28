package mecanics;

import Gui.ChoosePlayersPanel;
import Gui.MainMenuPanel;
import Gui.MainPanel;
import Gui.Window;

import java.awt.*;

public class GuiMaster
{
	private final Dimension DEFAULT_SIZE = new Dimension(1300, 1000);
	private final Point DEFAULT_LOCATION = new Point(100, 100);
	
	private static GuiMaster instance;
	private Window window;
	private MainPanel mainPanel;
	
	public static GuiMaster getInstance()
	{
		if(instance == null)
		{
			instance = new GuiMaster();
		}
		return instance;
	}
	
	private GuiMaster()
	{
		window = new Window(DEFAULT_LOCATION,DEFAULT_SIZE);
		MainMenuPanel mainMenu = new MainMenuPanel(DEFAULT_SIZE);
		window.setContentPane(mainMenu);
		window.open();
	}
	
	public void closeWindow()
	{
		window.close();
	}
	
	public void choosePlayers()
	{
		ChoosePlayersPanel panel = new ChoosePlayersPanel(DEFAULT_SIZE);
		window.setContentPane(panel);
	}
	
	public void startGame(String[] names, Color[] colors)
	{
		GameMaster.getInstance().startGame(names, colors);
		mainPanel = new MainPanel(DEFAULT_SIZE);
		window.setContentPane(mainPanel);
	}
	
	public void backToMainMenu()
	{
		MainMenuPanel mainMenu = new MainMenuPanel(DEFAULT_SIZE);
		window.setContentPane(mainMenu);
	}
	
	public void updateGamePanel()
	{
		mainPanel.updatePanel();
	}
	
	public void log(String s)
	{
		mainPanel.log(s);
	}
}
