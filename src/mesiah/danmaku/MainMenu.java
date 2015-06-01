package mesiah.danmaku;

import mesiah.danmaku.audio.AudioManager;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenu extends BasicGameState {	
	private static final float MENU_X = 100.0f;
	private static final String MENU1 = "Play";
	private static final float MENU1_Y = 100.0f;
	private static final String MENU2 = "Controls";
	private static final float MENU2_Y = 150.0f;
	private static final String MENU3 = "Quit";
	private static final float MENU3_Y = 200.0f;
	/*private static final String MENU3 = "Resume";
	private static final String MENU4 = "Resume";*/
	
	private int option;
	private static final int MAXOPTION = 2;
	
	private int lastKey;
	
	public MainMenu(int code) {
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		option = 0;
		lastKey = 0;
    }
 
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
    	Input input = gc.getInput();
    	lastKey += delta;
    	if (lastKey >= Main.KEYDELAY) {
	    	if (input.isKeyDown(Input.KEY_UP)) {
	    		option -= 1;
	    		if (option < 0) {
	    			option = 0;
	    		}
	    		lastKey = 0;
	    	}
	    	
	    	if (input.isKeyDown(Input.KEY_DOWN)) {
	    		option += 1;
	    		if (option > MAXOPTION) {
	    			option = MAXOPTION;
	    		}
	    		lastKey = 0;
	    	}
	    	
	    	if (input.isKeyDown(Input.KEY_ESCAPE)) {
	    		sbg.enterState(Main.PLAY);
	    		lastKey = 0;
	    	}
	    	
	    	if (input.isKeyDown(Input.KEY_ENTER) || input.isKeyDown(Input.KEY_Z)) {
	    		lastKey = 0;
	    		switch(option) {
		    		case 0:
		    			Play.newGame();
		    			AudioManager.get().playMusic("pay");
		    			sbg.enterState(Main.PLAY);
		    			break;
		    		case 1:
		    			sbg.enterState(Main.CONTROLS);
		    			break;
		    		case 2:
		    			System.exit(0);
		    			break;
	    		}
	    	}
    	}
    }
 
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
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
    	g.fillRoundRect(selectionX, selectionY, 120.0f, 40.0f, 8);
    	g.setColor(new Color(0.2f, 0.0f, 0.9f));
    	g.fillRect(selectionX+10, selectionY+10, 100.0f, 20.0f);
    	g.setColor(new Color(1.0f, 1.0f, 1.0f));
    	g.drawString(MENU1, MENU_X, MENU1_Y);
    	g.drawString(MENU2, MENU_X, MENU2_Y);
    	g.drawString(MENU3, MENU_X, MENU3_Y);
    }
    
    public int getID() {
    	return Main.MAINMENU;
    }
}
