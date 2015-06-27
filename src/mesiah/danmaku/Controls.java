package mesiah.danmaku;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Clase estado para el menú en que se cambian los controles.
 * @author Mesiah
 *
 */
public class Controls extends BasicGameState {	
	private static final float MENU_X = 100.0f;
	private static final float MENU2_X = 250.0f;
	private static final String MENU1 = "Return";
	private static final float MENU1_Y = 50.0f;
	private static final String MENU2 = "Up";
	private static final float MENU2_Y = 100.0f;
	private static final String MENU3 = "Down";
	private static final float MENU3_Y = 150.0f;
	private static final String MENU4 = "Left";
	private static final float MENU4_Y = 200.0f;
	private static final String MENU5 = "Right";
	private static final float MENU5_Y = 250.0f;
	private static final String MENU6 = "Shot";
	private static final float MENU6_Y = 300.0f;
	private static final String MENU7 = "Focus";
	private static final float MENU7_Y = 350.0f;
	private static final String MENU8 = "Menu";
	private static final float MENU8_Y = 400.0f;
	private static final String MENU9 = "Spawn 1";
	private static final float MENU9_Y = 450.0f;
	private static final String MENU10 = "Spawn 2";
	private static final float MENU10_Y = 500.0f;
	private static final String MENU11 = "Spawn 3";
	private static final float MENU11_Y = 550.0f;
	private static final String MENU12 = "Spawn 4";
	private static final float MENU12_Y = 600.0f;
	private static final String WAIT = "Waiting for input";
	private static final int WAIT_X = 512;
	private static final int WAIT_Y = 256;
	
	private int option;
	private static final int MAXOPTION = 11;
	
	private int lastKey;
	private boolean waitForInput;
	private int optionToChange;
	
	public Controls(int code) {
	}
	
	/**
	 * Inicializador de la clase. Carga los controles actuales.
	 */
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		option = 0;
		optionToChange = 0;
		lastKey = 0;
		waitForInput = false;
		loadControls();
    }
	
	/**
	 * Función que carga los controles actuales del archivo keybinds.cfg.
	 */
	public void loadControls() {
	    try {
	    	BufferedReader br = new BufferedReader(new FileReader("keybinds.cfg"));
	        String line = br.readLine();

	        while (line != null) {
	        	String[] parts = line.split("=");
	        	switch(parts[0]) {
	        	case "UP":
	        		Play.UP_KEY = Integer.valueOf(parts[1]);
	        		break;
	        	case "DOWN":
	        		Play.DOWN_KEY = Integer.valueOf(parts[1]);
	        		break;
	        	case "LEFT":
	        		Play.LEFT_KEY = Integer.valueOf(parts[1]);
	        		break;
	        	case "RIGHT":
	        		Play.RIGHT_KEY = Integer.valueOf(parts[1]);
	        		break;
	        	case "SHOT":
	        		Play.SHOT_KEY = Integer.valueOf(parts[1]);
	        		break;
	        	case "FOCUS":
	        		Play.FOCUS_KEY = Integer.valueOf(parts[1]);
	        		break;
	        	case "MENU":
	        		Play.MENU_KEY = Integer.valueOf(parts[1]);
	        		break;
	        	case "SPAWN1":
	        		Play.SPAWN1_KEY = Integer.valueOf(parts[1]);
	        		break;
	        	case "SPAWN2":
	        		Play.SPAWN2_KEY = Integer.valueOf(parts[1]);
	        		break;
	        	case "SPAWN3":
	        		Play.SPAWN3_KEY = Integer.valueOf(parts[1]);
	        		break;
	        	case "SPAWN4":
	        		Play.SPAWN4_KEY = Integer.valueOf(parts[1]);
	        		break;
	        	}
	            line = br.readLine();
	        }
	        br.close();
	    } catch (Exception e) {
			saveChanges();
		}
	}
	
	/**
	 * Cambia el control seleccionado en el momento a partir de un input.
	 * @param input
	 */
	public void changeControl(Input input) {
		for (int i = 0; i < 256; i++) {
			if (input.isKeyDown(i)) {
				switch(optionToChange) {
					case 1:
						Play.UP_KEY = i;
						waitForInput = false;
						lastKey = 0;
						break;
					case 2:
						Play.DOWN_KEY = i;
						waitForInput = false;
						lastKey = 0;
						break;
					case 3:
						Play.LEFT_KEY = i;
						waitForInput = false;
						lastKey = 0;
						break;
					case 4:
						Play.RIGHT_KEY = i;
						waitForInput = false;
						lastKey = 0;
						break;
					case 5:
						Play.SHOT_KEY = i;
						waitForInput = false;
						lastKey = 0;
						break;
					case 6:
						Play.FOCUS_KEY = i;
						waitForInput = false;
						lastKey = 0;
						break;
					case 7:
						Play.MENU_KEY = i;
						waitForInput = false;
						lastKey = 0;
						break;
					case 8:
						Play.SPAWN1_KEY = i;
						waitForInput = false;
						lastKey = 0;
						break;
					case 9:
						Play.SPAWN2_KEY = i;
						waitForInput = false;
						lastKey = 0;
						break;
					case 10:
						Play.SPAWN3_KEY = i;
						waitForInput = false;
						lastKey = 0;
						break;
					case 11:
						Play.SPAWN4_KEY = i;
						waitForInput = false;
						lastKey = 0;
						break;
				}
			}
		}
	}
 
	/**
	 * Update de la clase. Hace las funciones de controller.
	 */
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
    	Input input = gc.getInput();
    	lastKey += delta;
    	if (!waitForInput) {
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
		    	
		    	if (input.isKeyDown(Input.KEY_ESCAPE)) {
		    		saveChanges();
		    		lastKey = 0;
	    			sbg.enterState(Main.MAINMENU);
		    	}
		    	
		    	if (input.isKeyDown(Input.KEY_ENTER) || input.isKeyDown(Input.KEY_Z)) {
		    		lastKey = 0;
		    		switch(option) {
			    		case 0:
			    			saveChanges();
			    			sbg.enterState(Main.MAINMENU);
			    			break;
			    		// up, down, left, right, shot, focus, menu, spawn1, spawn2, spawn3
			    		default:
			    			optionToChange = option;
			    			waitForInput = true;
			    			break;
		    		}
		    	}
	    	}
    	} else if (lastKey >= Main.KEYDELAY) {
    			changeControl(input);
    	}
    }
    
    public void saveChanges() {
    	PrintWriter out;
		try {
			out = new PrintWriter("keybinds.cfg");
			out.write("UP="+String.valueOf(Play.UP_KEY)+"\n");
			out.write("DOWN="+String.valueOf(Play.DOWN_KEY)+"\n");
			out.write("LEFT="+String.valueOf(Play.LEFT_KEY)+"\n");
			out.write("RIGHT="+String.valueOf(Play.RIGHT_KEY)+"\n");
			out.write("SHOT="+String.valueOf(Play.SHOT_KEY)+"\n");
			out.write("FOCUS="+String.valueOf(Play.FOCUS_KEY)+"\n");
			out.write("MENU="+String.valueOf(Play.MENU_KEY)+"\n");
			out.write("SPAWN1="+String.valueOf(Play.SPAWN1_KEY)+"\n");
			out.write("SPAWN2="+String.valueOf(Play.SPAWN2_KEY)+"\n");
			out.write("SPAWN3="+String.valueOf(Play.SPAWN3_KEY)+"\n");
			out.write("SPAWN4="+String.valueOf(Play.SPAWN4_KEY));
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    }
 
    /**
     * Muestra todos los textos y gráficos por pantalla.
     */
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
	    	case 3:
	    		selectionY = MENU4_Y - 10.0f;
	    		break;
	    	case 4:
	    		selectionY = MENU5_Y - 10.0f;
	    		break;
	    	case 5:
	    		selectionY = MENU6_Y - 10.0f;
	    		break;
	    	case 6:
	    		selectionY = MENU7_Y - 10.0f;
	    		break;
	    	case 7:
	    		selectionY = MENU8_Y - 10.0f;
	    		break;
	    	case 8:
	    		selectionY = MENU9_Y - 10.0f;
	    		break;
	    	case 9:
	    		selectionY = MENU10_Y - 10.0f;
	    		break;
	    	case 10:
	    		selectionY = MENU11_Y - 10.0f;
	    		break;
	    	case 11:
	    		selectionY = MENU12_Y - 10.0f;
	    		break;
    	}
    	
    	g.setColor(new Color(1.0f, 0.0f, 0.0f));
    	g.fillRoundRect(selectionX, selectionY, 100.0f, 40.0f, 8);
    	g.setColor(new Color(0.2f, 0.0f, 0.9f));
    	g.fillRect(selectionX+10, selectionY+10, 80.0f, 20.0f);
    	g.setColor(new Color(1.0f, 1.0f, 1.0f));
    	g.drawString(MENU1, MENU_X, MENU1_Y);
    	g.drawString(MENU2, MENU_X, MENU2_Y);
    	g.drawString(MENU3, MENU_X, MENU3_Y);
    	g.drawString(MENU4, MENU_X, MENU4_Y);
    	g.drawString(MENU5, MENU_X, MENU5_Y);
    	g.drawString(MENU6, MENU_X, MENU6_Y);
    	g.drawString(MENU7, MENU_X, MENU7_Y);
    	g.drawString(MENU8, MENU_X, MENU8_Y);
    	g.drawString(MENU9, MENU_X, MENU9_Y);
    	g.drawString(MENU10, MENU_X, MENU10_Y);
    	g.drawString(MENU11, MENU_X, MENU11_Y);
    	g.drawString(MENU12, MENU_X, MENU12_Y);
    	
    	g.drawString(Input.getKeyName(Play.UP_KEY), MENU2_X, MENU2_Y);
    	g.drawString(Input.getKeyName(Play.DOWN_KEY), MENU2_X, MENU3_Y);
    	g.drawString(Input.getKeyName(Play.LEFT_KEY), MENU2_X, MENU4_Y);
    	g.drawString(Input.getKeyName(Play.RIGHT_KEY), MENU2_X, MENU5_Y);
    	g.drawString(Input.getKeyName(Play.SHOT_KEY), MENU2_X, MENU6_Y);
    	g.drawString(Input.getKeyName(Play.FOCUS_KEY), MENU2_X, MENU7_Y);
    	g.drawString(Input.getKeyName(Play.MENU_KEY), MENU2_X, MENU8_Y);
    	g.drawString(Input.getKeyName(Play.SPAWN1_KEY), MENU2_X, MENU9_Y);
    	g.drawString(Input.getKeyName(Play.SPAWN2_KEY), MENU2_X, MENU10_Y);
    	g.drawString(Input.getKeyName(Play.SPAWN3_KEY), MENU2_X, MENU11_Y);
    	g.drawString(Input.getKeyName(Play.SPAWN4_KEY), MENU2_X, MENU12_Y);
    	
    	if (waitForInput) {
    		g.drawString(WAIT, WAIT_X, WAIT_Y);
    	}
    }
    
    public int getID() {
    	return Main.CONTROLS;
    }
}
