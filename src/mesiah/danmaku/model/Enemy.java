package mesiah.danmaku.model;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Curve;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import mesiah.danmaku.Main;
import mesiah.danmaku.Play;
import mesiah.danmaku.audio.AudioManager;
import mesiah.danmaku.model.patterns.FirePattern;
import mesiah.danmaku.model.patterns.FirePatternManager;
import mesiah.danmaku.view.Drawable;

public class Enemy extends VisibleGameObject implements BulletEmitter {
	protected ArrayList<String> fps;
	protected ArrayList<Integer> shotDelays;
	protected ArrayList<Integer> shotTimers;
	protected ArrayList<String> powerups;
	
	protected ArrayList<Curve> route;
	protected ArrayList<Integer> routeTime;
	protected int curveTimer;
	protected boolean onlyCurve;
	
	protected float finalPosX;
	protected float finalPosY;
	protected int transitionTime;
	protected int transitionTimer;
	protected float initialPosX;
	protected float initialPosY;

	protected int health;
	protected int damageTimer;
	protected int damageDelay;
	protected String enemyID;
	protected float[] lastSize;
	
	public static int ACTIVE = 0;
	public static int SHOT = 1;
	public static int DESTROYED = 2;
	
	/**
	 * Función para copiar un enemigo entero.
	 * Al contrario que otras clases, Enemy no usa una clase proxy de la
	 * que componer el enemigo, así que se crea uno que no se renderiza y luego
	 * se copia.
	 */
	@SuppressWarnings("unchecked")
	public Enemy copy() {
		Enemy e = null;
		try {
			e = new Enemy();
			e.setFirePatterns((ArrayList<String>) this.fps.clone());
			e.setHealth(health);
			e.setShotDelays((ArrayList<Integer>) this.shotDelays.clone());
			e.setShotTimers((ArrayList<Integer>) this.shotTimers.clone());
			e.setPosX(posx);
			e.setPosY(posy);
			e.setSpeed(speed);
			e.setFacing(facing);
			e.setDirection(direction);
			e.setCollidable(collidable);
			e.setPowerups((ArrayList<String>) this.powerups.clone());
			for (Drawable d:ds) {
				if (d != null) {
					e.addAnimation(d.copy(), ds.indexOf(d));
				}
			}
			e.setSounds((ArrayList<String>) this.sounds.clone());
			e.setHitboxes((ArrayList<Shape>) this.ss.clone());
			e.setRelatives((ArrayList<float[]>) this.relatives.clone());
			e.setD(e.getFromDs(ACTIVE));
			e.setRoute((ArrayList<Curve>) this.route.clone());
			e.setRouteTime((ArrayList<Integer>) this.routeTime.clone());
			e.setTransitionTime(transitionTime);
			e.setFinalPosX(finalPosX);
			e.setFinalPosY(finalPosY);
		} catch (SlickException e1) {
			e1.printStackTrace();
		}
		return e;		
	}
	
	public Enemy() throws SlickException {
		init();
	}
	
	/**
	 * Inicializa todos los arrays del enemigo y algunas variables.
	 */
	public void init() {
		ds = new ArrayList<Drawable>();
		fps = new ArrayList<String>();
		shotDelays = new ArrayList<Integer>();
		shotTimers = new ArrayList<Integer>();
		sounds = new ArrayList<String>();
		ss = new ArrayList<Shape>();
		relatives = new ArrayList<float[]>();
		powerups = new ArrayList<String>();
		
		route = new ArrayList<Curve>();
		routeTime = new ArrayList<Integer>();
		for (int i = 0; i < DESTROYED+1; i++) {
			ds.add(null);
			sounds.add(null);
		}
		//ds.set(ACTIVE, AnimationManager.get().getAnimation("enemy1"));
		//ds.set(DESTROYED, AnimationManager.get().getAnimation("enemydestroyed"));
		//d = ds.get(ACTIVE);
		//sounds.set(DESTROYED, "destroyed");
		
		posx = Main.GAMEWIDTH/2;
		posy = Main.GAMEHEIGHT/4;
		state = "active";
		collidable = true;
		health = 100;
		damageTimer = 0;
		damageDelay = 200;
		lastSize = new float[2];
		
		curveTimer = 0;
		onlyCurve = false;
		
		transitionTime = 0;
		finalPosX = 0;
		finalPosY = 0;
		transitionTimer = 0;
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
		initialPosX = x;
	}
	
	public void setPosY(float y) {
		posy = y;
		initialPosY = y;
	}

	public void CheckEnemyCollisions() {

	}

	public void CheckPlayerCollisions() {

	}

	public void CheckBulletCollisions() {

	}

	public void CheckPowerupCollisions() {

	}

	public boolean checkCollision(GameObject go) {
		return false;
	}

	/**
	 * Actualiza todos los datos del enemigo. Controla lo siguiente:
	 * Si el enemigo ha sido destruido.
	 * Si debe moverse.
	 * Si colisiona con el player.
	 * Si debe estar rojo porque se le ha causado daño.
	 * Dispara.
	 */
	public void update(int delta) {
		if (state == "beingdestroyed") {
			d = ds.get(DESTROYED);
			state = "destroyed";
		}
		if (!d.isPlaying() && state == "destroyed") {
			Play.ec.addToRemove(this);
		} else {
			if (state == "active") {
				move(delta);
				CheckPlayerCollisions();
			}
		}
		if (damageTimer <= 0) {
			d.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			damageTimer = 0;
		} else {
			damageTimer -= delta;
		}
		shot(delta);
	}
	
	/**
	 * Gestiona los disparos del enemigo.
	 * Solo dispara cuando toca.
	 * Tiene temporizadores propios para cada patrón que tenga.
	 * Suena un disparo por cada patrón.
	 */
	public void shot(int delta) {
		FirePattern fp = null;
		if (state == "active") {
			for (int i = 0; i < fps.size(); i++) {
				if (shotTimers.get(i) >= shotDelays.get(i)) {
					fp = FirePatternManager.get().compose(fps.get(i), this);
					shotTimers.set(i, 0);
					Play.bc.add(fp);
					//AudioManager.get().playSound(sounds.get(SHOT));
				} else {
					shotTimers.set(i, shotTimers.get(i) + delta);
				}
			}
		}
	}
	
	public String getShotSound() {
		return sounds.get(SHOT);
	}
	
	/**
	 * Comprueba si aún quedan curvas de la ruta del enemigo antes de desaparecer.
	 */
	public boolean moreCurves() {
		return route.size() > 0;
	}
	
	public void pop() {
		route.remove(0);
		routeTime.remove(0);
	}
	
	public Vector2f nextPoint(float t) {
		return route.get(0).pointAt(t);
	}

	/**
	 * Movimiento del enemigo.
	 * Si no es destruido, desaparecerá del mapa y no volverá.
	 */
	public void move(int delta) {
		if (transitionTime > transitionTimer) {
			float movx = (finalPosX - initialPosX) * (float) ((float) transitionTimer / (float) transitionTime);
			float movy = (finalPosY - initialPosY) * (float) ((float) transitionTimer / (float) transitionTime);
			posx = movx + initialPosX;
			posy = movy + initialPosY;
			transitionTimer += delta;
		}
		if (moreCurves()) {
			if (super.getDelay() <= 0) {
				if (curveTimer <= routeTime.get(0)) {
					float t = (float) ((float) curveTimer / (float) routeTime.get(0));
					Vector2f point = nextPoint(t);
					super.setPosX(point.getX());
					super.setPosY(point.getY());
					curveTimer += delta;
				} else {
					curveTimer = 0;
					pop();
				}
			}
		} else if (!onlyCurve) {
			setCanMove(true);
		} else {
			//setState("dead");
			Play.ec.addToRemove(this);
		}
	}

	/**
	 * Muestra el enemigo si sigue vivo, o la animación de muerte.
	 * @param g
	 */
	public void draw() {
		if (state == "active") {
			d.draw(posx, posy, facing);
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
	
	public float[] getSize() {
		return d.getSize();
	}
	
	/**
	 * Función que se ejecuta al bajar la vida del enemigo a 0 o menos.
	 * Reproduce un sonido y suelta los powerups que hagan falta.
	 */
	public void onDestroyed() {
		state = "beingdestroyed";
		AudioManager.get().playSound(sounds.get(DESTROYED));
		collidable = false;
		for (String s : powerups) {
			for (int i = 0; i <  PowerupManager.get().getPowerup(s).getQty(); i++) {
				Powerup p = PowerupManager.get().composer(s, this);
				Play.puc.add(p);
			}
		}
	}
	
	/**
	 * Función que se ejecuta al perder vida el enemigo.
	 * Cambia de color al recibir daño.
	 * Cuando su vida baja a 0, se destruye.
	 **/
	public void onHit(int damage) {
		health -= damage;
		damageTimer = damageDelay;
		d.setColor(0.8f, 0.0f, 0.0f, 1.0f);
		if (health <= 0) {
			onDestroyed();
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
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int h) {
		health = h;
	}
	
	public void addAnimation(Drawable d, int id) {
		ds.set(id, d);
		if (id == ACTIVE) {
			this.d = ds.get(ACTIVE);
		}
	}
	
	public void addPattern(String id) {
		fps.add(id);
	}
	
	public void addSound(String key, int id) {
		sounds.set(id, key);
	}
	
	public void setFirePatterns(ArrayList<String> fps) {
		this.fps = fps;
	}
	
	public void setDrawables(ArrayList<Drawable> ds) {
		this.ds = ds;
	}
	
	public void setSounds(ArrayList<String> sounds) {
		this.sounds = sounds;
	}
	
	public String getEnemyID() {
		return enemyID;
	}
	
	public void setEnemyID(String id) {
		enemyID = id;
	}
	
	public Drawable getD() {
		return d;
	}
	
	public void setD(Drawable d) {
		this.d = d;
	}
	
	public Drawable getFromDs(int code) {
		return ds.get(code);
	}

	/**
	 * Obtiene la hitbox o hitboxes del enemigo.
	 * Puesto que las hitboxes tienen una posición y un tamaño,
	 * se tiene que actualizar la posición antes de devolveras.
	 */
	public ArrayList<Shape> getHitBoxes() {
		for (int i = 0; i < ss.size(); i++) {
			Shape shape = ss.get(i);
			float[] rel = getRelatives(i);
			shape.setX(posx+rel[0]);
			shape.setY(posy+rel[1]);
			ss.set(i, shape);
		}
		return ss;
	}
	
	/**
	 * Añade una hitbox a la lista y su posición relativa.
	 */
	public void addHitbox(Shape s) {
		float[] rel = {s.getX(), s.getY()};
		addRelative(rel);
		ss.add(s);
	}

	/**
	 * Añade la hitbox básica (rectángulo de sprite).
	 */
	public void addHitbox() {
		Rectangle r = new Rectangle(posx, posy, getSize()[0], getSize()[1]);
		float[] rel = {0.0f, 0.0f};
		addRelative(rel);
		ss.add(r);
	}

	public float[] getRelatives(int n) {
		return relatives.get(n);
	}

	public void addRelative(float[] r) {
		relatives.add(r);
	}
	
	public void setHitboxes(ArrayList<Shape> ss) {
		this.ss = ss;
	}
	
	public void setRelatives(ArrayList<float[]> rel) {
		this.relatives = rel;
	}

	public ArrayList<Integer> getShotDelays() {
		return shotDelays;
	}

	public void setShotDelays(ArrayList<Integer> shotDelays) {
		this.shotDelays = shotDelays;
	}

	public ArrayList<Integer> getShotTimers() {
		return shotTimers;
	}

	public void setShotTimers(ArrayList<Integer> shotTimers) {
		this.shotTimers = shotTimers;
	}
	
	public void addShotDelay(int delay) {
		shotDelays.add(delay);
		shotTimers.add(delay);
	}

	public ArrayList<String> getPowerup() {
		return powerups;
	}
	
	public void setPowerups(ArrayList<String> powerup) {
		this.powerups = powerup;
	}
	
	public void addPowerup(String p) {
		powerups.add(p);
	}

	public ArrayList<Curve> getRoute() {
		return route;
	}

	public void setRoute(ArrayList<Curve> route) {
		this.route = route;
	}

	public ArrayList<Integer> getRouteTime() {
		return routeTime;
	}

	public void setRouteTime(ArrayList<Integer> routeTime) {
		this.routeTime = routeTime;
	}

	public boolean isOnlyCurve() {
		return onlyCurve;
	}

	public void setOnlyCurve(boolean onlyCurve) {
		this.onlyCurve = onlyCurve;
	}

	public float getFinalPosX() {
		return finalPosX;
	}

	public void setFinalPosX(float finalPosX) {
		this.finalPosX = finalPosX;
	}

	public float getFinalPosY() {
		return finalPosY;
	}

	public void setFinalPosY(float finalPosY) {
		this.finalPosY = finalPosY;
	}

	public int getTransitionTime() {
		return transitionTime;
	}

	public void setTransitionTime(int transitionTime) {
		this.transitionTime = transitionTime;
	}

}
