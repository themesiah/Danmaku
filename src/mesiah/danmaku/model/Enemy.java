package mesiah.danmaku.model;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import mesiah.danmaku.Main;
import mesiah.danmaku.Play;
import mesiah.danmaku.audio.AudioManager;
import mesiah.danmaku.model.patterns.FirePattern;
import mesiah.danmaku.model.patterns.FirePatternsManager;
import mesiah.danmaku.view.Drawable;

public class Enemy extends VisibleGameObject implements BulletEmitter {
	private ArrayList<String> fps;
	int health;
	int damageTimer;
	int damageDelay;
	int shotTimer;
	int shotDelay;
	String enemyID;
	
	public static int ACTIVE = 0;
	public static int SHOT = 1;
	public static int DESTROYED = 2;
	
	@SuppressWarnings("unchecked")
	public Enemy copy() {
		Enemy e = null;
		try {
			e = new Enemy();
			e.setFirePatterns((ArrayList<String>) this.fps.clone());
			e.setHealth(health);
			e.setShotDelay(shotDelay);
			e.setPosX(posx);
			e.setPosY(posy);
			e.setSpeed(speed);
			e.setFacing(facing);
			e.setDirection(direction);
			for (Drawable d:ds) {
				if (d != null) {
					e.addAnimation(d.copy(), ds.indexOf(d));
				}
			}
			e.setSounds((ArrayList<String>) this.sounds.clone());
			e.setD(e.getFromDs(ACTIVE));
		} catch (SlickException e1) {
			e1.printStackTrace();
		}
		return e;		
	}
	
	public Enemy() throws SlickException {
		init();
	}
	
	public void init() {
		ds = new ArrayList<Drawable>();
		fps = new ArrayList<String>();
		sounds = new ArrayList<String>();
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
		shotTimer = 0;
		shotDelay = 1000;
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
			Play.ec.addToRemove(this);
		} else {
			move();
		}
		if (damageTimer <= 0) {
			d.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			damageTimer = 0;
		} else {
			damageTimer -= delta;
		}
		shot(delta);
	}
	
	public void shot(int delta) {
		FirePattern fp = null;
		if (shotTimer >= shotDelay && state == "active") {
			for (String id : fps) {
				fp = FirePatternsManager.get().newPattern(id, posx, posy, this);
				Play.bc.add(fp);
			}
			AudioManager.get().playSound(sounds.get(SHOT));
			
			shotTimer = 0;
		} else {
			shotTimer += delta;
		}
			
	}

	public void move() {

	}

	public void draw() {
		if (state == "active") {
			d.draw(posx, posy, facing);
		} else if (state == "destroyed") {
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
	}
	
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
	
	public int getShotDelay() {
		return shotDelay;
	}
	
	public void setShotDelay(int sd) {
		shotDelay = sd;
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

}
