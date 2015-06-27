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
	
	/** 
	 * Constructor de Main. Aquí se especifican los estados con los que contará el juego.
	 * @param gameName El nombre del juego.
	 */
	public Main(String gameName) {
		super(gameName);
		this.addState(new MainMenu(MAINMENU));
		this.addState(new Play(PLAY));
		this.addState(new Menu(MENU));
		this.addState(new Controls(CONTROLS));
		this.addState(new Options(OPTIONS));
	}
	
	/**
	 * Inicialización de los estados del juego y selección de estado inicial.
	 */
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(MAINMENU).init(gc, this);
		this.getState(PLAY).init(gc, this);
		this.getState(MENU).init(gc, this);
		this.getState(CONTROLS).init(gc, this);
		this.getState(OPTIONS).init(gc, this);
		this.enterState(MAINMENU);
	}
	
	@Override
	/**
	 * Override de la función para entrar en un estado.
	 * Gestiona si una canción debe terminar, pausarse... dependiendo el estado.
	 * @param id Estado en el que se va a entrar.
	 */
	public void enterState(int id) {
		switch(this.getCurrentStateID()) {
		case MAINMENU:
			if (id == PLAY) {
				AudioManager.get().playMusic(AudioManager.get().getCurrentlyPlaying());
			}
			break;
		case PLAY:
			if (id == MAINMENU) {
				AudioManager.get().stopMusic();
			}
			if (id == MENU) {
				AudioManager.get().pauseMusic(AudioManager.get().getCurrentlyPlaying());
			}
			break;
		case MENU:
			if (id == PLAY) {
				AudioManager.get().resumeMusic(AudioManager.get().getCurrentlyPlaying());
			}
			if (id == MAINMENU) {
				AudioManager.get().stopMusic();
			}
			break;
		}
		super.enterState(id);
	}
	
	/**
	 * El inicio de todo el software.
	 * Por el momento no contempla argumentos.
	 * Carga las configuraciones iniciales del juego y crea el juego con Slick2D.
	 * Además, inicializa el volumen del juego.
	 */
    public static void main(String[] args) {
    	try {
    		XMLLoader.get().getConfigFromXML("Game.xml");
	    	AppGameContainer appContainer = new AppGameContainer(new Main(GAMENAME));
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