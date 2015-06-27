package mesiah.danmaku;

import mesiah.danmaku.audio.AudioManager;
import mesiah.danmaku.xml.XMLLoader;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
 
public class Main extends StateBasedGame {
	public static String GAMENAME = "Danmaku";
	public static final int MAINMENU = 0;
	public static final int PLAY = 1;
	public static final int MENU = 2;
	public static final int CONTROLS = 3;
	public static final int OPTIONS = 4;
	
	public static int GAMEWIDTH = 1024;
	public static int GAMEHEIGHT = 768;
	public static int LIMITLEFT = GAMEWIDTH/4;
	public static int LIMITRIGHT = GAMEWIDTH*3/4;
	public static int LIMITTOP = 0;
	public static int LIMITBOTTOM = GAMEHEIGHT;
	public static boolean FULLSCREEN = false;
	
	public static int LASTMENU = MAINMENU;
	
	public static final int PLAYERNUM = 0;
	
	public static final int KEYDELAY = 200;
	
	public Main(String gameName) {
		super(gameName);
		this.addState(new MainMenu(MAINMENU));
		this.addState(new Play(PLAY));
		this.addState(new Menu(MENU));
		this.addState(new Controls(CONTROLS));
		this.addState(new Options(OPTIONS));
	}
	
	
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(MAINMENU).init(gc, this);
		//this.getState(PLAY).init(gc, this);
		this.getState(MENU).init(gc, this);
		this.getState(CONTROLS).init(gc, this);
		this.getState(OPTIONS).init(gc, this);
		this.enterState(MAINMENU);
	}
	
	@Override
	public void enterState(int id) {
		switch(this.getCurrentStateID()) {
		case MAINMENU:
			if (id == PLAY) {
				AudioManager.get().playMusic("pay");
			}
			break;
		case PLAY:
			if (id == MAINMENU) {
				AudioManager.get().stopMusic();
			}
			if (id == MENU) {
				AudioManager.get().pauseMusic("pay");
			}
			break;
		case MENU:
			if (id == PLAY) {
				AudioManager.get().resumeMusic("pay");
			}
			if (id == MAINMENU) {
				AudioManager.get().stopMusic();
			}
			break;
		}
		super.enterState(id);
	}
	
    public static void main(String[] args) {
    	try {
    		XMLLoader.get().getConfigFromXML("Game.xml");
	    	AppGameContainer appContainer = new AppGameContainer(new Main(GAMENAME));
	    	System.out.println(Main.FULLSCREEN);
	        appContainer.setDisplayMode(GAMEWIDTH, GAMEHEIGHT, FULLSCREEN);
	        appContainer.setTargetFrameRate(60);
			AudioManager.get().setMusicVolume(Options.MAX_MUSIC_VOLUME);
			AudioManager.get().setSoundVolume(Options.MAX_MUSIC_VOLUME);
	        appContainer.start();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
 
}