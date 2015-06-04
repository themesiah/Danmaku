package mesiah.danmaku;

import mesiah.danmaku.model.BulletContainer;
import mesiah.danmaku.model.EnemiesManager;
import mesiah.danmaku.model.Enemy;
import mesiah.danmaku.model.EnemyContainer;
import mesiah.danmaku.model.GameObjectContainer;
import mesiah.danmaku.model.Player;
import mesiah.danmaku.model.PlayerContainer;
import mesiah.danmaku.model.PowerupContainer;
import mesiah.danmaku.model.patterns.FirePatternsManager;
import mesiah.danmaku.xml.XMLLoader;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Play extends BasicGameState {
	public static PlayerContainer pc;
	public static BulletContainer bc;
	public static EnemyContainer ec;
	public static PowerupContainer puc;
	private static GameObjectContainer goc;
	private static int timer;
	private static int lastKey;
	private static int lastEnemy;
	private static int enemyDelay = 500;
	
	public static int UP_KEY = Input.KEY_UP;
	public static int DOWN_KEY = Input.KEY_DOWN;
	public static int RIGHT_KEY = Input.KEY_RIGHT;
	public static int LEFT_KEY = Input.KEY_LEFT;
	public static int SHOT_KEY = Input.KEY_Z;
	public static int FOCUS_KEY = Input.KEY_LSHIFT;
	public static int SPAWN1_KEY = Input.KEY_Q;
	public static int SPAWN2_KEY = Input.KEY_W;
	public static int SPAWN3_KEY = Input.KEY_E;
	public static int SPAWN4_KEY = Input.KEY_R;
	public static int MENU_KEY = Input.KEY_ESCAPE;
	
	
	public Play(int code) {
	}
	
	private static void containerInit() throws SlickException {
		// Inicializamos containers
		pc = new PlayerContainer();
		bc = new BulletContainer();
		ec = new EnemyContainer();
		puc = new PowerupContainer();
		goc = new GameObjectContainer(pc, bc, ec, puc);
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		newGame();
    }
	
	public static void newGame() throws SlickException {
		//Player p = new Player();
		containerInit();
		
		
		try {
			XMLLoader.get().resourcesLoader("Resources.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//p.initPlayer();
    	
    	
    	//p.addPattern(FirePatternsManager.PLAYERFIREPATTERN);
    	//pc.add(p);
    	
    	//enemiesInit();
    	timer = 0;
	}
 
	public void spawnEnemy(String type) throws SlickException {
		if (lastEnemy >= enemyDelay) {
    		Enemy e = null;
    		float x;
    		try {
    			e = EnemiesManager.get().newEnemy(type);    			
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			x = ((float) Math.random() * (Main.GAMEWIDTH/2-e.getSize()[0])) + Main.GAMEWIDTH/4;
			e.setPosX(x);
    		lastEnemy = 0;
    		ec.add(e);
    	}
	}
	
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
    	if (pc.get(Main.PLAYERNUM).getState() == "dead") {
    		sbg.enterState(Main.MAINMENU);
    	}
    	goc.update(delta);
    	timer += delta;
    	lastKey += delta;
    	lastEnemy += delta;
    	
    	/*if (lastEnemy >= enemyDelay) {
    		Enemy e = new Enemy();
    		float x = (float) Math.random() * (Main.GAMEWIDTH-e.getSize()[0]);
    		e.setPosX(x);
    		ec.add(e);
    		lastEnemy = 0;
    	}*/
    	
    	
    	// Inputs del usuario
    	Input input = gc.getInput();
    	
    	if(input.isKeyDown(MENU_KEY) && lastKey >= Main.KEYDELAY) {
    		lastKey = 0;
    		sbg.enterState(Main.MENU);
    	}
    	
    	if(input.isKeyDown(SPAWN1_KEY) && lastKey >= Main.KEYDELAY) {
    		lastKey = 0;
    		spawnEnemy("enemy1");
    	}
    	
    	if(input.isKeyDown(SPAWN2_KEY) && lastKey >= Main.KEYDELAY) {
    		lastKey = 0;
    		spawnEnemy("enemy2");
    	}
    	
    	if(input.isKeyDown(SPAWN3_KEY) && lastKey >= Main.KEYDELAY) {
    		lastKey = 0;
    		spawnEnemy("enemy3");
    	}
    	
    	if(input.isKeyDown(SPAWN4_KEY) && lastKey >= Main.KEYDELAY) {
    		lastKey = 0;
    		spawnEnemy("enemy4");
    	}
    	
    	if(input.isKeyDown(FOCUS_KEY)) {
    		pc.get(Main.PLAYERNUM).setFocused(true);
    	} else {
    		pc.get(Main.PLAYERNUM).setFocused(false);
    	}
    	
    	if(input.isKeyDown(LEFT_KEY)) {
    		pc.get(Main.PLAYERNUM).moveLeft();
    	}
    	
    	if(input.isKeyDown(RIGHT_KEY)) {
    		pc.get(Main.PLAYERNUM).moveRight();
    	}
    	
    	if(input.isKeyDown(UP_KEY)) {
    		pc.get(Main.PLAYERNUM).moveUp();
    	}
    	
    	if(input.isKeyDown(DOWN_KEY)) {
    		pc.get(Main.PLAYERNUM).moveDown();
    	}
    	
    	if(input.isKeyDown(SHOT_KEY)) {
			pc.get(Main.PLAYERNUM).shot(delta);
    	}
    	
    	
    }
    
    private void renderDebugs(GameContainer gc, StateBasedGame sbg, Graphics g) {
    	// Debugs
    	g.drawString("Time: " + timer/1000, 10.0f, 30.0f);
    	g.drawString("Bullets: " + bc.size(), 10.0f, 50.0f);
    	g.drawString("Patterns: " + bc.patterns(), 10.0f, 70.0f);
    	g.drawString("Press "+ Input.getKeyName(SPAWN1_KEY) +" to spawn Enemy 1", 10.0f, 110.0f);
    	g.drawString("Press "+ Input.getKeyName(SPAWN2_KEY) +" to spawn Enemy 2", 10.0f, 130.0f);
    	g.drawString("Press "+ Input.getKeyName(SPAWN3_KEY) +" to spawn Enemy 3", 10.0f, 150.0f);
    	g.drawString("Press "+ Input.getKeyName(SPAWN4_KEY) +" to spawn Enemy 4", 10.0f, 170.0f);
    	
    	g.drawString("Lives: " + Player.LIVES, 10.0f, Main.GAMEHEIGHT-110.0f);
    	g.drawString("Bombs: " + Player.BOMBS, 10.0f, Main.GAMEHEIGHT-90.0f);
    	g.drawString("Power: " + Player.POWER, 10.0f, Main.GAMEHEIGHT-70.0f);
    	g.drawString("Points: " + Player.POINTS, 10.0f, Main.GAMEHEIGHT-50.0f);
    	g.drawString("Graze: " + Player.GRAZE, 10.0f, Main.GAMEHEIGHT-30.0f);
    }
 
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
    	// Todos los objetos del juego
    	goc.draw();
    	renderDebugs(gc, sbg, g);
    }
    
    public int getID() {
    	return Main.PLAY;
    }
}
