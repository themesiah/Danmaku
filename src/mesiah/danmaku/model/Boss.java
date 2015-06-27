package mesiah.danmaku.model;

import java.util.ArrayList;
import java.util.HashMap;

import mesiah.danmaku.Main;
import mesiah.danmaku.Play;
import mesiah.danmaku.audio.AudioManager;
import mesiah.danmaku.model.patterns.FirePattern;
import mesiah.danmaku.model.patterns.FirePatternManager;
import mesiah.danmaku.view.Drawable;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Curve;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Boss extends Enemy {
	private String name;
	private int maxHealth;
	private boolean initOnce;
	
	private HashMap<String, ArrayList<String>> fpsPhases;
	private HashMap<String, ArrayList<Integer>> shotDelaysPhases;
	private HashMap<String, ArrayList<Integer>> shotTimersPhases;
	private HashMap<String, Integer> healthPhases;
	private HashMap<String, String> musicPhases;
	private ArrayList<String> phases;
	int invulnerableDelay;
	int invulnerableTimer;
	private String lastSong;
	
	protected ArrayList<Curve> route;
	protected ArrayList<Integer> routeTime;
	protected int curveTimer;
	protected boolean onlyCurve;

	protected float finalPosX;
	protected float finalPosY;
	protected float initialPosX;
	protected float initialPosY;
	protected int transitionTime;
	protected int transitionTimer;
	
	@SuppressWarnings("unchecked")
	public Boss copy() {
		Boss e = null;
		try {
			e = new Boss();
			e.setHealth(health);
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
			e.setName(name);
			e.setFpsPhases((HashMap<String, ArrayList<String>>) this.getFpsPhases().clone());
			e.setShotDelaysPhases((HashMap<String, ArrayList<Integer>>) this.getShotDelaysPhases().clone());
			e.setShotTimersPhases((HashMap<String, ArrayList<Integer>>) this.getShotTimersPhases().clone());
			e.setHealthPhases((HashMap<String, Integer>) this.getHealthPhases().clone());
			e.setPhases((ArrayList<String>) this.phases.clone());
			e.setMusicPhases((HashMap<String, String>) this.musicPhases.clone());
			e.setInvulnerableDelay(invulnerableDelay);
			e.setRoute((ArrayList<Curve>) this.route.clone());
			e.setRouteTime((ArrayList<Integer>) this.routeTime.clone());
		} catch (SlickException e1) {
			e1.printStackTrace();
		}
		return e;		
	}

	public Boss() throws SlickException {
		init();
	}
	
	public void setHealth(int h) {
		health = h;
		maxHealth = h;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public float getHealthPercent() {
		return (float) ((float) health / (float) maxHealth);
	}
	
	public void drawBossInterface(Graphics g) {
		float horizontalM = 5;
		float verticalM = 5;
		float healthPercent = getHealthPercent();
		float posx = Main.LIMITLEFT + horizontalM;
		float posy = Main.LIMITTOP + verticalM;
		float maxWidth = (Main.LIMITRIGHT-Main.LIMITLEFT)-(horizontalM*2);
		float healthWidth = maxWidth*healthPercent;
		float height = 10;
		
		// Full rect
		Color c = new Color(0.0f, 0.0f, 0.0f, 1.0f);
		g.setColor(c);
		g.fillRect(posx, posy, maxWidth, height);
		
		// Name
		c = new Color(1.0f, 1.0f, 1.0f, 1.0f);
		g.setColor(c);
		g.drawString(name, posx, posy+height);
		
		// Life rect
		c = new Color(1.0f, 0.0f, 0.0f, 1.0f);
		g.setColor(c);
		g.fillRect(posx, posy, healthWidth, height);
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

	public String getName() {
		return name;
	}

	public void draw(Graphics g) {
		if (state == "active") {
			d.draw(posx, posy, facing);
			lastSize = d.getSize();
			drawBossInterface(g);
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
	
	public void init() {
		ds = new ArrayList<Drawable>();
		fps = new ArrayList<String>();
		shotDelays = new ArrayList<Integer>();
		shotTimers = new ArrayList<Integer>();
		sounds = new ArrayList<String>();
		ss = new ArrayList<Shape>();
		relatives = new ArrayList<float[]>();
		powerups = new ArrayList<String>();
		
		fpsPhases = new HashMap<String, ArrayList<String>>();
		shotDelaysPhases = new HashMap<String, ArrayList<Integer>>();
		shotTimersPhases = new HashMap<String, ArrayList<Integer>>();
		healthPhases = new HashMap<String, Integer>();
		musicPhases = new HashMap<String, String>();
		phases = new ArrayList<String>();
		
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
		
		state = "active";
		collidable = false;
		health = 100;
		damageTimer = 0;
		damageDelay = 200;
		invulnerableDelay = 0;
		lastSize = new float[2];
		lastSong = AudioManager.get().getCurrentlyPlaying();
		
		finalPosX = 0;
		finalPosY = 0;
		transitionTime = 0;
		transitionTimer = 0;
		initOnce = false;
	}
	
	public void initBoss() {
		if (!initOnce) {
			collidable = true;
			changePhase();
			initOnce = true;
		}
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

	public void update(int delta) {
		if (state == "beingdestroyed") {
			d = ds.get(DESTROYED);
			state = "destroyed";
		}
		if (!d.isPlaying() && state == "destroyed") {
			Play.bssc.addToRemove(this);
		} else {
			move(delta);
			CheckPlayerCollisions();
		}
		if (damageTimer <= 0) {
			d.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			damageTimer = 0;
		} else {
			damageTimer -= delta;
		}
		
		if (invulnerableTimer >= 0) {
			invulnerableTimer -= delta;
		}
		shot(delta);
	}
	
	public ArrayList<String> getPhases() {
		return phases;
	}

	public void setPhases(ArrayList<String> phases) {
		this.phases = phases;
	}

	public int getInvulnerableDelay() {
		return invulnerableDelay;
	}

	public void setInvulnerableDelay(int invulnerableDelay) {
		this.invulnerableDelay = invulnerableDelay;
	}

	public int getInvulnerableTimer() {
		return invulnerableTimer;
	}

	public void setInvulnerableTimer(int invulnerableTimer) {
		this.invulnerableTimer = invulnerableTimer;
	}

	public void shot(int delta) {
		if (invulnerableTimer <= 0) {
			FirePattern fp = null;
			if (state == "active") {
				for (int i = 0; i < fps.size(); i++) {
					if (shotTimers.get(i) >= shotDelays.get(i)) {
						fp = FirePatternManager.get().compose(fps.get(i), this);
						shotTimers.set(i, 0);
						Play.bc.add(fp);
						AudioManager.get().playSound(sounds.get(SHOT));
					} else {
						shotTimers.set(i, shotTimers.get(i) + delta);
					}
				}
			}
		}
	}

	public void move(int delta) {
		if (transitionTime > transitionTimer) {
			float movx = (finalPosX - initialPosX) * (float) ((float) transitionTimer / (float) transitionTime);
			float movy = (finalPosY - initialPosY) * (float) ((float) transitionTimer / (float) transitionTime);
			posx = movx + initialPosX;
			posy = movy + initialPosY;
			transitionTimer += delta;
		} else if (transitionTime != 0) {
			initBoss();
		} else {
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
			} else {
				initBoss();
			}
		}
	}
	
	public boolean moreCurves() {
		return route.size() > 0;
	}

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
		AudioManager.get().playMusic(lastSong);
	}
	
	public void onHit(int damage) {
		if (invulnerableTimer <= 0) {
			health -= damage;
			damageTimer = damageDelay;
			d.setColor(0.8f, 0.0f, 0.0f, 1.0f);
			if (health <= 0) {
				changePhase();
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
	
	public int getHealth() {
		return health;
	}
	
	public void addAnimation(Drawable d, int id) {
		ds.set(id, d);
		if (id == ACTIVE) {
			this.d = ds.get(ACTIVE);
		}
	}
	
	public void addPatternPhase(String phase, String id) {
		fpsPhases.get(phase).add(id);
	}
	
	public void addSound(String key, int id) {
		sounds.set(id, key);
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
	
	public void addHitbox(Shape s) {
		float[] rel = {s.getX(), s.getY()};
		addRelative(rel);
		ss.add(s);
	}

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
	
	public void addShotDelayPhase(String key, int delay) {
		this.getShotDelaysPhases().get(key).add(delay);
		this.getShotTimersPhases().get(key).add(delay);
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

	public HashMap<String, ArrayList<String>> getFpsPhases() {
		return fpsPhases;
	}

	public void setFpsPhases(HashMap<String, ArrayList<String>> fpsPhases) {
		this.fpsPhases = fpsPhases;
	}

	public HashMap<String, ArrayList<Integer>> getShotDelaysPhases() {
		return shotDelaysPhases;
	}

	public void setShotDelaysPhases(
			HashMap<String, ArrayList<Integer>> shotDelaysPhases) {
		this.shotDelaysPhases = shotDelaysPhases;
	}

	public HashMap<String, ArrayList<Integer>> getShotTimersPhases() {
		return shotTimersPhases;
	}

	public void setShotTimersPhases(
			HashMap<String, ArrayList<Integer>> shotTimersPhases) {
		this.shotTimersPhases = shotTimersPhases;
	}

	public HashMap<String, Integer> getHealthPhases() {
		return healthPhases;
	}

	public void setHealthPhases(HashMap<String, Integer> healthPhases) {
		this.healthPhases = healthPhases;
	}
	
	
	private void changePhase() {
		Play.bc.addToRemoveEnemies();
		if (phases.size() > 0) {
			String phase = phases.get(0);
			invulnerableTimer = invulnerableDelay;
			health = healthPhases.get(phase);
			maxHealth = health;
			copyArrays(phase);
			if (musicPhases.containsKey(phase)) {
				AudioManager.get().playMusic(musicPhases.get(phase));
			}
			phases.remove(0);
		} else {
			onDestroyed();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void copyArrays(String phase) {
		fps = (ArrayList<String>) fpsPhases.get(phase).clone();
		shotDelays = (ArrayList<Integer>) shotDelaysPhases.get(phase).clone();
		shotTimers = (ArrayList<Integer>) shotTimersPhases.get(phase).clone();
	}
	
	public void addPhase(String phase) {
		phases.add(phase);
		fpsPhases.put(phase, new ArrayList<String>());
		shotDelaysPhases.put(phase, new ArrayList<Integer>());
		shotTimersPhases.put(phase, new ArrayList<Integer>());
	}
	
	public void addHealthPhase(String phase, int health) {
		healthPhases.put(phase, health);
	}
	
	public void addMusicPhase(String phase, String audio) {
		musicPhases.put(phase,  audio);
	}

	public HashMap<String, String> getMusicPhases() {
		return musicPhases;
	}

	public void setMusicPhases(HashMap<String, String> musicPhases) {
		this.musicPhases = musicPhases;
	}

}
