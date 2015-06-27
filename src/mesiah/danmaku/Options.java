package mesiah.danmaku;

import mesiah.danmaku.audio.AudioManager;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Clase que contiene el estado en el que se puede cambiar de opciones.
 * Por el momento, se puede cambiar las opciones de sonido.
 * @author Mesiah
 *
 */
public class Options extends BasicGameState {	
	private static final float MENU_X = 100.0f;
	private static final String MENU1 = "Return";
	private static final float MENU1_Y = 50.0f;
	private static final float MENU2_X = 200.0f;
	private static final String MENU2 = "Music";
	private static final float MENU2_Y = 100.0f;
	private static final String MENU3 = "SFX";
	private static final float MENU3_Y = 150.0f;
	
	public static final float MAX_MUSIC_VOLUME = 0.5f;
	public static final float MAX_SOUND_VOLUME = 1.0f;
	
	private int option;
	private static final int MAXOPTION = 2;
	
	private int lastKey;
	
	public Options(int code) {
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		option = 0;
		lastKey = 0;
    }
	
	/**
	 * Gestiona los inputs.
	 */
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
    	Input input = gc.getInput();
    	lastKey += delta;
    	if (lastKey >= Main.KEYDELAY) {
	    	if (input.isKeyDown(Input.KEY_UP)) {
	    		option -= 1;
	    		if (option < 0) {
	    			option = MAXOPTION;
	    		}
	    		lastKey = 0;
	    	}
	    	
	    	if (input.isKeyDown(Input.KEY_DOWN)) {
	    		option += 1;
	    		if (option > MAXOPTION) {
	    			option = 0;
	    		}
	    		lastKey = 0;
	    	}
	    	
	    	if (input.isKeyDown(Input.KEY_LEFT)) {
	    		if (option == 1) {
	    			AudioManager.get().musicVolumeDown();
	    		} else if (option == 2) {
	    			AudioManager.get().soundVolumeDown();
	    		}
	    	}
	    	
	    	if (input.isKeyDown(Input.KEY_RIGHT)) {
	    		if (option == 1) {
	    			AudioManager.get().musicVolumeUp();
	    		} else if (option == 2) {
	    			AudioManager.get().soundVolumeUp();
	    		}
	    	}
	    	
	    	if (input.isKeyDown(Input.KEY_ESCAPE)) {
	    		sbg.enterState(Main.PLAY);
	    		lastKey = 0;
	    	}
	    	
	    	if (input.isKeyDown(Input.KEY_ENTER) || input.isKeyDown(Input.KEY_Z)) {
	    		lastKey = 0;
	    		switch(option) {
		    		case 0:
		    			sbg.enterState(Main.LASTMENU);
		    			break;
	    		}
	    	}
    	}
    }
 
    /**
     * Muestra las opciones por pantalla.
     */
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
    	AudioManager am = AudioManager.get();
    	float musicVolume = am.getMusicVolume();
    	float soundVolume = am.getSoundVolume();
    	float musicPercent = musicVolume / MAX_MUSIC_VOLUME;
    	float soundPercent = soundVolume / MAX_SOUND_VOLUME;
    	float selectionX, selectionY = 0;
    	
    	selectionX = MENU_X - 20.0f;
    	switch(option) {
	    	case 0:
	    		selectionY = MENU1_Y - 10.0f;
	    		break;
	    	case 1:
	    		selectionY = MENU2_Y - 10.0f;
	    		break;
	    	case 2:
	    		selectionY = MENU3_Y - 10.0f;
	    		break;
    	}
    	
    	g.setColor(new Color(1.0f, 0.0f, 0.0f));
    	g.fillRoundRect(selectionX, selectionY, 100, 40.0f, 8);
    	g.setColor(new Color(0.2f, 0.0f, 0.9f));
    	g.fillRect(selectionX+10, selectionY+10, 80, 20.0f);
    	g.setColor(new Color(1.0f, 1.0f, 1.0f));
    	g.drawString(MENU1, MENU_X, MENU1_Y);
    	g.drawString(MENU2, MENU_X, MENU2_Y);
    	g.drawString(MENU3, MENU_X, MENU3_Y);
    	
    	g.setColor(new Color(1.0f, 1.0f, 1.0f));
    	
    	for (int i = 0; i < 50; i++) {
	    	if (musicPercent > i*0.02f) {
	    		g.fillRect(MENU2_X + i*12.0f, MENU2_Y+1, 10.0f, 18.0f);
	    	}
	    	if (soundPercent > i*0.02f) {
	    		g.fillRect(MENU2_X + i*12.0f, MENU3_Y+1, 10.0f, 18.0f);
	    	}
    	}
    }
    
    public int getID() {
    	return Main.OPTIONS;
    }
}
