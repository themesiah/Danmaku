package mesiah.danmaku;

import java.util.ArrayList;

import mesiah.danmaku.audio.AudioManager;
import mesiah.danmaku.controller.EnemySpawn;
import mesiah.danmaku.controller.Level;
import mesiah.danmaku.controller.LevelsManager;
import mesiah.danmaku.model.Boss;
import mesiah.danmaku.model.BossContainer;
import mesiah.danmaku.model.BossesManager;
import mesiah.danmaku.model.BulletContainer;
import mesiah.danmaku.model.EnemiesManager;
import mesiah.danmaku.model.Enemy;
import mesiah.danmaku.model.EnemyContainer;
import mesiah.danmaku.model.GameObjectContainer;
import mesiah.danmaku.model.Player;
import mesiah.danmaku.model.PlayerContainer;
import mesiah.danmaku.model.PowerupContainer;
import mesiah.danmaku.util.Signals;
import mesiah.danmaku.xml.XMLLoader;

import org.newdawn.slick.Color;
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
	public static BossContainer bssc;
	private static GameObjectContainer goc;
	private static int timer;
	private static int lastKey;
	private static int lastEnemy;
	private static int enemyDelay = 500;
	
	private static Level currentLevel = null;
	private static boolean won = false;

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
	
	public static float POINTS_X = 10.0f;
	public static float POINTS_Y = 658.0f;
	public static float BOMBS_X = 10.0f;
	public static float BOMBS_Y = 678.0f;
	public static float GRAZE_X = 10.0f;
	public static float GRAZE_Y = 698.0f;
	public static float LIVES_X = 10.0f;
	public static float LIVES_Y = 718.0f;
	public static float POWER_X = 10.0f;
	public static float POWER_Y = 738.0f;
	
	public static boolean CANGRAZE = true;
	public static boolean CANFOCUS = true;

	public Play(int code) {
	}

	/**
	 * Inicializa los containers de game objects del juego.
	 * Finalmente, inicializa el container de containers de game objects, que es
	 * la base para el ciclo del juego.
	 * @throws SlickException
	 */
	private static void containerInit() throws SlickException {
		// Inicializamos containers
		pc = new PlayerContainer();
		bc = new BulletContainer();
		ec = new EnemyContainer();
		puc = new PowerupContainer();
		bssc = new BossContainer();
		goc = new GameObjectContainer(pc, bc, ec, puc, bssc);

	}

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		newGame();
	}

	/**
	 * Se llama cada vez que se empieza un nuevo juego. Carga todos los containers y recursos
	 * del juego.
	 * @throws SlickException
	 */
	public static void newGame() throws SlickException {
		currentLevel = null;
		containerInit();

		try {
			XMLLoader.get().resourcesLoader("Resources.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		timer = 0;
	}

	/**
	 * FUNCI�N DE DEBUG
	 * Spawnea un enemigo en un punto aleatorio X por la zona de arriba de la pantalla.
	 * @param type Nombre del enemigo en el hashmap de enemigos.
	 * @throws SlickException
	 */
	public void spawnEnemy(String type) throws SlickException {
		if (lastEnemy >= enemyDelay) {
			Enemy e = null;
			float x;
			try {
				e = EnemiesManager.get().newEnemy(type);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			x = ((float) Math.random() * (Main.GAMEWIDTH / 2 - e.getSize()[0] / 2))
					+ Main.GAMEWIDTH / 4;
			e.setPosX(x);
			lastEnemy = 0;
			ec.add(e);
		}
	}

	/**
	 * FUNCI�N DE DEBUG
	 * Spawnea un boss en el centro X por la zona de arriba de la pantalla.
	 * @param type Nombre del boss en el hashmap de bosses.
	 * @throws SlickException
	 */
	public void spawnBoss(String type) throws SlickException {
		if (lastEnemy >= enemyDelay) {
			Boss e = null;
			float x;
			float y;
			try {
				e = BossesManager.get().newBoss(type);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			y = 0 - e.getSize()[1] / 2;
			x = Main.GAMEWIDTH / 2 - e.getSize()[0] / 2;
			e.setPosX(x);
			e.setPosY(y);
			lastEnemy = 0;
			bssc.add(e);
		}
	}
	
	/**
	 * Funci�n que saca a los enemigos que el update ha recogido.
	 * Esos enemigos corresponden al tiempo (timer) actual.
	 * Tambi�n contempla el caso de sacar un boss o incluso de terminar la partida.
	 * @param es Una lista de spawns de enemigos.
	 */
	public void spawnEnemies(ArrayList<EnemySpawn> es) {
		for (EnemySpawn e : es) {
			if (e.getType().equals("enemy")) {
				ec.add(e.composeEnemy());
			} else if (e.getType().equals("boss")) {
				bssc.add((Boss) e.composeEnemy()); 
			} else if (e.getType().equals("win")) {
				won = true;
			}
		}
	}

	/**
	 * Ciclo principal del juego.
	 * Actualiza el temporizador (si no hay un boss activo).
	 * Gestiona inputs del usuario.
	 * Comprueba que el juego o el nivel no haya terminado.
	 * Hace update general del juego (goc.update()).
	 * Actualiza las se�ales (temporizadores globales).
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		if (bssc.size() <= 0) {
			timer += delta;
		}
		lastKey += delta;
		lastEnemy += delta;
		
		if (currentLevel == null || won) {
			if (LevelsManager.get().finished()) {
				sbg.enterState(Main.MAINMENU);
			} else {
				currentLevel = LevelsManager.get().getLevel();
				goc.setBg(currentLevel.getBg());
				goc.setTemplate(currentLevel.getTemplate());
				AudioManager.get().playMusic(currentLevel.getMusic());
				won = false;
			}
		}
		
		if (!currentLevel.finished()) {
			spawnEnemies(currentLevel.getSpawns(timer));
		}
		
		if (pc.get(Main.PLAYERNUM).getState() == "dead") {
			sbg.enterState(Main.MAINMENU);
		}
		goc.update(delta);
		

		Signals s = Signals.getSignals();
		for (String key : s.getIntegerSignals().keySet()) {
			if (s.getIntegerSignal(key) < 0) {
				s.resetIntegerSignal(key);
			}
			if (s.getIntegerSignal(key) >= 0) {
				s.changeIntegerSignal(key, -delta);
			}
		}

		/*
		 * if (lastEnemy >= enemyDelay) { Enemy e = new Enemy(); float x =
		 * (float) Math.random() * (Main.GAMEWIDTH-e.getSize()[0]);
		 * e.setPosX(x); ec.add(e); lastEnemy = 0; }
		 */

		// Inputs del usuario
		Input input = gc.getInput();

		if (input.isKeyDown(MENU_KEY) && lastKey >= Main.KEYDELAY) {
			lastKey = 0;
			sbg.enterState(Main.MENU);
		}

		if (input.isKeyDown(SPAWN1_KEY) && lastKey >= Main.KEYDELAY) {
			lastKey = 0;
			spawnEnemy("enemy1");
		}

		if (input.isKeyDown(SPAWN2_KEY) && lastKey >= Main.KEYDELAY) {
			lastKey = 0;
			spawnEnemy("enemy2");
		}

		if (input.isKeyDown(SPAWN3_KEY) && lastKey >= Main.KEYDELAY) {
			lastKey = 0;
			spawnEnemy("enemy3");
		}

		if (input.isKeyDown(SPAWN4_KEY) && lastKey >= Main.KEYDELAY) {
			lastKey = 0;
			spawnBoss("boss1");
		}

		if (input.isKeyDown(FOCUS_KEY)) {
			pc.get(Main.PLAYERNUM).setFocused(true);
		} else {
			pc.get(Main.PLAYERNUM).setFocused(false);
		}

		if (input.isKeyDown(LEFT_KEY)) {
			pc.get(Main.PLAYERNUM).moveLeft();
		}

		if (input.isKeyDown(RIGHT_KEY)) {
			pc.get(Main.PLAYERNUM).moveRight();
		}

		if (input.isKeyDown(UP_KEY)) {
			pc.get(Main.PLAYERNUM).moveUp();
		}

		if (input.isKeyDown(DOWN_KEY)) {
			pc.get(Main.PLAYERNUM).moveDown();
		}

		if (input.isKeyDown(SHOT_KEY)) {
			pc.get(Main.PLAYERNUM).shot(delta);
		}

	}

	/**
	 * Muestra textos varios por pantalla.
	 */
	private void renderDebugs(GameContainer gc, StateBasedGame sbg, Graphics g) {
		// Debugs
		Color c = new Color(1.0f, 1.0f, 1.0f, 1.0f);
		g.setColor(c);
		
		if (Main.DEBUG) {
			g.drawString("Time: " + timer / 1000, 10.0f, 30.0f);
			g.drawString("Bullets: " + bc.size(), 10.0f, 50.0f);
			//g.drawString("Patterns: " + bc.patterns(), 10.0f, 70.0f);
			//g.drawString("Press " + Input.getKeyName(SPAWN1_KEY) + " to spawn Enemy 1", 10.0f, 110.0f);
			//g.drawString("Press " + Input.getKeyName(SPAWN2_KEY) + " to spawn Enemy 2", 10.0f, 130.0f);
			//g.drawString("Press " + Input.getKeyName(SPAWN3_KEY) + " to spawn Enemy 3", 10.0f, 150.0f);
			//g.drawString("Press " + Input.getKeyName(SPAWN4_KEY) + " to spawn Enemy 4", 10.0f, 170.0f);
		}

		g.drawString("Lives: " + Player.LIVES, LIVES_X, LIVES_Y);
		//g.drawString("Bombs: " + Player.BOMBS, BOMBS_X, BOMBS_Y);
		g.drawString("Power: " + Player.POWER, POWER_X, POWER_Y);
		g.drawString("Points: " + Player.POINTS, POINTS_X, POINTS_Y);
		g.drawString("Graze: " + Player.GRAZE, GRAZE_X, GRAZE_Y);
	}

	/**
	 * Muestra por pantalla todos los objetos del juego y los debugs.
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// Todos los objetos del juego
		goc.draw(g);
		renderDebugs(gc, sbg, g);
	}

	public int getID() {
		return Main.PLAY;
	}
}
