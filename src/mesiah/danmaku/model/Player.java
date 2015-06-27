package mesiah.danmaku.model;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import mesiah.danmaku.Main;
import mesiah.danmaku.Play;
import mesiah.danmaku.audio.AudioManager;
import mesiah.danmaku.model.patterns.FirePattern;
import mesiah.danmaku.model.patterns.FirePatternManager;
import mesiah.danmaku.util.GetDirection;
import mesiah.danmaku.view.Drawable;

/**
 * Clase que contiene al jugador en sí y todos sus parámetros y variables.
 * @author Mesiah
 *
 */
public class Player extends VisibleGameObject implements BulletEmitter {
	private ArrayList<String> fps;
	private ArrayList<ArrayList<String>> firePatterns;
	private ArrayList<ArrayList<Integer>> shotDelays;
	private ArrayList<ArrayList<Integer>> shotTimers;
	
	int lastShoot = 0;
	int delay = 100;
	int maxPower;
	boolean focused;
	public static int ACTIVE = 0;
	public static int FOCUSED = 1;
	public static int SHOT = 2;
	public static int DESTROYED = 3;
	
	
	public static int GRAZE;
	public static int POINTS;
	public static int LIVES;
	public static int POWER;
	public static int BOMBS;
	
	private float HITBOX_RADIUS = 0.1f;
	private float GRAZE_HITBOX_RADIUS = 20.0f;
	private float POWERUP_HITBOX_RADIUS = 10.0f;
	private float EXTRA_POWERUP_HITBOX_RADIUS = 40.0f;
	
	
	
	float[] lastSize;
	
	public Player() throws SlickException {
		initPlayer();
	}
	
	public boolean isCollidable() {
		return collidable;
	}
	
	public float getPosX() {
		return posx;
	}
	
	public float getPosY() {
		return posy;
	}
	
	public void setPosX(float x) {
		posx = x;
	}
	
	public void setPosY(float y) {
		posy = y;
	}
	
	/**
	 * Inicializa la lista de fire patterns que tendrá el jugador dependiendo del poder.
	 */
	public void initFirePatterns() {
		for (int i = 0; i < maxPower+1; i++) {
			firePatterns.add(new ArrayList<String>());
			shotDelays.add(new ArrayList<Integer>());
			shotTimers.add(new ArrayList<Integer>());
		}
	}

	/**
	 * Inicializa todas las variables del jugador.
	 * @throws SlickException
	 */
	public void initPlayer() throws SlickException {
		firePatterns = new ArrayList<ArrayList<String>>();
		shotDelays = new ArrayList<ArrayList<Integer>>();
		shotTimers = new ArrayList<ArrayList<Integer>>();
		ds = new ArrayList<Drawable>();
		sounds = new ArrayList<String>();
		fps = new ArrayList<String>();
		ss = new ArrayList<Shape>();
		relatives = new ArrayList<float[]>();
		for (int i = 0; i < DESTROYED+1; i++) {
			ds.add(null);
			sounds.add(null);
		}
		POWER = 0;
		maxPower = 1;
		posx = Main.GAMEWIDTH/2;
		posy = Main.GAMEHEIGHT/2;
		direction = 0;
		speed = 5;
		focused = false;
		collidable = true;
		state = "active";
		GRAZE = 0;
		POINTS = 0;
		LIVES = 3;
		BOMBS = 2;
		lastSize = new float[2];
	}

	public int getMaxPower() {
		return maxPower;
	}

	public void setMaxPower(int maxPower) {
		this.maxPower = maxPower;
		initFirePatterns();
	}
	
	/** 
	 * Comprueba si colisiona con un boss y resulta destruido en ese caso.
	 */
	public void CheckBossCollisions() {
		if (this.collidable) {
			List<Boss> blist = Play.bssc.getEnemies();
			for (Boss b: blist) {
				for (Shape s: ss) {
					for (Shape se: b.getHitBoxes()) {
						if (collides(se, s) && b.isCollidable()) {
							onDestroyed();
						}
					}
				}
			}
		}
	}
	
	/**
	 * Comprueba si colisiona con un enemigo y resulta destruido en ese caso.
	 */
	public void CheckEnemyCollisions() {
		if (this.collidable) {
			List<Enemy> elist = Play.ec.getEnemies();
			for (Enemy e: elist) {
				for (Shape s: ss) {
					for (Shape se: e.getHitBoxes()) {
						if (collides(se, s) && e.isCollidable()) {
							onDestroyed();
						}
					}
				}
			}
		}
	}

	public void CheckPlayerCollisions() {
		
	}

	public void CheckBulletCollisions() {
		
	}
	
	/**
	 * Obtiene los beneficios de un powerup.
	 * Sube el poder en caso de poder, aumenta los puntos,
	 * las vidas o las bombas en los casos correspondientes.
	 * En caso de puntos, obtiene más si el graze es grande.
	 * @param pu Powerup en cuestión.
	 */ 
	public void onPowerupWin(Powerup pu) {
		if (pu.getType() == Powerup.TYPE_POWER) {
			POWER += pu.getValue();
			if (POWER > maxPower) {
				POWER = maxPower;
			}
		}
		if (pu.getType() == Powerup.TYPE_POINTS) {
			POINTS += pu.getValue() + pu.getValue()*(float) (GRAZE/1000.0f);
		}
		if (pu.getType() == Powerup.TYPE_LIFE) {
			LIVES += pu.getValue();
		}
		if (pu.getType() == Powerup.TYPE_BOMB) {
			BOMBS += pu.getValue();
		}
	}

	/**
	 * Comprueba si está suficiente cerca de un powerup como para atraerlo o
	 * lo suficientemente cerca como para recogerlo.
	 */
	public void CheckPowerupCollisions() {
		if (this.collidable) {
			List<Powerup> pulist = Play.puc.getPowerups();
			for (Powerup pu: pulist) {
				for (Shape se: pu.getHitBoxes()) {
					if (collides(se, getPowerupHitBox()) && pu.isCollidable()) {
						onPowerupWin(pu);
						Play.puc.addToRemove(pu);
					} else if (collides (se, getExtraPowerupHitBox()) && pu.isCollidable()) {
						pu.setDirection(GetDirection.getDirectionToPlayer(pu.getPosX(), pu.getPosY()));
						pu.setSpeed(speed*2);
					}
				}
			}
		}
	}
	
	/**
	 * Destrucción del jugador.
	 */
	public void onDestroyed() {
		state = "beingdestroyed";
		AudioManager.get().playSound(sounds.get(DESTROYED));
		collidable = false;
	}
	
	/**
	 * En caso de recibir daño.
	 * Actualmente, la única vida a considerar es 1.
	 * @param damage Daño causado. Actualmente es indiferente.
	 */
	public void onHit(int damage) {
		onDestroyed();
	}

	/**
	 * Actualización del jugador.
	 * Comprueba colisiones y destruye al player si ha sido destruido.
	 * @param delta Tiempo pasado desde el último update.
	 */
	public void update(int delta) {
		if (state == "active") {
			CheckEnemyCollisions();
			CheckBossCollisions();
			CheckPowerupCollisions();
		}
		if (state == "beingdestroyed") {
			d = ds.get(DESTROYED);
			state = "destroyed";
		}
		if (!d.isPlaying()) {
			state = "dead";
		}
	}

	public void move(int delta) {
	}
	
	/**
	 * Mueve al jugador hacia arriba.
	 * Contempla la posibilidad de tener focus activado y
	 * contempla que no pueda moverse más por los límites del
	 * escenario.
	 */
	public void moveUp() {
		if (posy > Main.LIMITTOP && state == "active") {
			if (focused && Play.CANFOCUS) {
				posy -= speed/2;
			} else {
				posy -= speed;
			}
		}
	}
	
	/**
	 * Mueve al jugador hacia abajo.
	 * Contempla la posibilidad de tener focus activado y
	 * contempla que no pueda moverse más por los límites del
	 * escenario.
	 */
	public void moveDown() {
		if (posy < Main.LIMITBOTTOM - d.getSize()[1] && state == "active") {
			if (focused && Play.CANFOCUS) {
				posy += speed/2;
			} else {
				posy += speed;
			}
		}
	}
	
	/**
	 * Mueve al jugador hacia la izquierda.
	 * Contempla la posibilidad de tener focus activado y
	 * contempla que no pueda moverse más por los límites del
	 * escenario.
	 */
	public void moveLeft() {
		if (posx > Main.LIMITLEFT && state == "active") {
			if (focused && Play.CANFOCUS) {
				posx -= speed/2;
			} else {
				posx -= speed;
			}
		}
	}
	
	/**
	 * Mueve al jugador hacia la derecha.
	 * Contempla la posibilidad de tener focus activado y
	 * contempla que no pueda moverse más por los límites del
	 * escenario.
	 */
	public void moveRight() {
		if (posx < Main.LIMITRIGHT - d.getSize()[0] && state == "active") {
			if (focused && Play.CANFOCUS) {
				posx += speed/2;
			} else {
				posx += speed;
			}
		}
	}

	/**
	 * Dibuja al jugador o su destrucción, si es el caso.
	 */
	public void draw() {
		if (state == "active") {
			d.draw(posx, posy);
			lastSize = d.getSize();
		} else if (state == "destroyed") {
			float[] size1 = lastSize;
			float[] size2 = d.getSizeOf(d.getFrame());
			if (lastSize != size2) {
				posx -= ((size2[0] - size1[0])/2);
				posy -= ((size2[1] - size1[1])/2);
			}
			lastSize = d.getSizeOf(d.getFrame());
			d.play(posx, posy);
		}
	}

	public boolean checkCollision(GameObject go) {
		return false;
	}
	
	/**
	 * Disparo del jugador. Puede tener varios patrones a la vez.
	 * Dispara un patrón u otro dependiendo de su poder.
	 * Solo resulta activado al pulsar la tecla
	 * correspondiente.
	 * @param delta Tiempo desde el último update.
	 */
	public void shot(int delta) {
		FirePattern fp = null;
		ArrayList<String> tempfps = firePatterns.get(Player.POWER);
		ArrayList<Integer> tempdelays = shotDelays.get(Player.POWER);
		ArrayList<Integer> temptimers = shotTimers.get(Player.POWER);
		if (state == "active") {
			for (int i = 0; i < tempfps.size(); i++) {
				if (temptimers.get(i) >= tempdelays.get(i)) {
					fp = FirePatternManager.get().compose(tempfps.get(i), this);
					temptimers.set(i, 0);
					Play.bc.add(fp);
					AudioManager.get().playSound(sounds.get(SHOT));
				} else {
					temptimers.set(i, temptimers.get(i) + delta);
				}
			}
		}
		
	}
	
	public float[] getSize() {
		return d.getSize();
	}
	
	/**
	 * Activa o desactiva el focus.
	 * Esto implica no solamente cambiar el estado de focus,
	 * si no cambiar la animación a focus.
	 * @param f Focuseado o no.
	 */
	public void setFocused(boolean f) {
		if (state == "active") {
			focused = f;
			if (focused) {
				d = ds.get(FOCUSED);
			} else {
				d = ds.get(ACTIVE);
			}
		}
	}

	public float getFacing() {
		return facing;
	}

	public float getDirection() {
		return direction;
	}

	public float getSpeed() {
		return speed;
	}

	public String getState() {
		return state;
	}

	public void setFacing(float f) {
		facing = f;
	}

	public void setDirection(float dir) {
		direction = dir;
	}

	public void setSpeed(float s) {
		speed = s;
	}

	public void setState(String s) {
		state = s;
	}
	
	public void addAnimation(Drawable d, int id) {
		ds.set(id, d);
		if (id == ACTIVE) {
			this.d = ds.get(ACTIVE);
			addHitbox();
		}
	}

	public void addSound(String key, int id) {
		sounds.set(id, key);
	}

	public void addPattern(String id) {
		fps.add(id);
	}

	public void addFirePattern(int power, String pattern) {
		for (int i = power; i < firePatterns.size(); i++) {
			firePatterns.get(i).add(pattern);
		}
	}
	
	public ArrayList<String> getFirePattern(int power) {
		return firePatterns.get(power);
	}
	
	public void addShotDelay(int power, int timer) {
		for (int i = power; i < shotDelays.size(); i++) {
			shotDelays.get(i).add(timer);
			shotTimers.get(i).add(timer);
		}
	}
	
	/**
	 * Devuelve la hitbox que utilizará el jugador para determinar
	 * el graze (balas que se acercan mucho pero no dan).
	 * @return La forma geométrica que hará de hitbox.
	 */
	public Shape getGrazeHitBox() {
		Circle el = new Circle(posx + getSize()[0]/2, posy + getSize()[1]/2 - 1, GRAZE_HITBOX_RADIUS);
		return el;
	}
	
	/**
	 * Devuelve la hitbox que utilizará el jugador para determinar
	 * si obtiene un powerup.
	 * @return La forma geométrica que hará de hitbox.
	 */
	public Shape getPowerupHitBox() {
		Circle el = new Circle(posx + getSize()[0]/2, posy + getSize()[1]/2 - 1, POWERUP_HITBOX_RADIUS);
		return el;
	}
	
	/**
	 * Devuelve la hitbox que utilizará el jugador para determinar
	 * si los powerups se acercan a él.
	 * @return La forma geométrica que hará de hitbox.
	 */
	public Shape getExtraPowerupHitBox() {
		Circle el = new Circle(posx + getSize()[0]/2, posy + getSize()[1]/2 - 1, EXTRA_POWERUP_HITBOX_RADIUS);
		return el;
	}

	/**
	 * Añade una hitbox relativa al jugador.
	 */
	public void addHitbox(Shape s) {
		float[] rel = {s.getX(), s.getY()};
		addRelative(rel);
		ss.add(s);
	}

	/**
	 * Añade la hitbox básica al jugador.
	 */
	public void addHitbox() {
		Circle el = new Circle(posx + getSize()[0]/2, posy + getSize()[1]/2, HITBOX_RADIUS);
		float[] rel = {0.0f, 0.0f};
		addRelative(rel);
		ss.add(el);
	}

	public float[] getRelatives(int n) {
		return relatives.get(n);
	}

	public void addRelative(float[] r) {
		relatives.add(r);
	}
	
	public void setRelatives(ArrayList<float[]> rel) {
		this.relatives = rel;
	}

	public void setHitboxes(ArrayList<Shape> ss) {
		this.ss = ss;
	}

	public float getHITBOX_RADIUS() {
		return HITBOX_RADIUS;
	}

	public void setHITBOX_RADIUS(float hITBOX_RADIUS) {
		HITBOX_RADIUS = hITBOX_RADIUS;
	}

	public float getGRAZE_HITBOX_RADIUS() {
		return GRAZE_HITBOX_RADIUS;
	}

	public void setGRAZE_HITBOX_RADIUS(float gRAZE_HITBOX_RADIUS) {
		GRAZE_HITBOX_RADIUS = gRAZE_HITBOX_RADIUS;
	}

	public float getPOWERUP_HITBOX_RADIUS() {
		return POWERUP_HITBOX_RADIUS;
	}

	public void setPOWERUP_HITBOX_RADIUS(float pOWERUP_HITBOX_RADIUS) {
		POWERUP_HITBOX_RADIUS = pOWERUP_HITBOX_RADIUS;
	}
	
	public float getEXTRA_POWERUP_HITBOX_RADIUS() {
		return EXTRA_POWERUP_HITBOX_RADIUS;
	}

	public void setEXTRA_POWERUP_HITBOX_RADIUS(float pOWERUP_HITBOX_RADIUS) {
		EXTRA_POWERUP_HITBOX_RADIUS = pOWERUP_HITBOX_RADIUS;
	}

}
