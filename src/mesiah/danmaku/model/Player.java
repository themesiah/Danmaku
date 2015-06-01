package mesiah.danmaku.model;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import mesiah.danmaku.Main;
import mesiah.danmaku.Play;
import mesiah.danmaku.audio.AudioManager;
import mesiah.danmaku.model.patterns.FirePattern;
import mesiah.danmaku.model.patterns.FirePatternsManager;
import mesiah.danmaku.view.AnimationManager;
import mesiah.danmaku.view.Drawable;

public class Player extends VisibleGameObject implements BulletEmitter{
	private ArrayList<String> fps;
	
	int lastShoot = 0;
	int delay = 100;
	boolean focused;
	public static int ACTIVE = 0;
	public static int FOCUSED = 1;
	public static int DESTROYED = 2;
	
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

	private void initPlayer() throws SlickException {
		ds = new ArrayList<Drawable>();
		sounds = new ArrayList<String>();
		fps = new ArrayList<String>();
		for (int i = 0; i < DESTROYED+1; i++) {
			ds.add(null);
			sounds.add(null);
		}
		ds.set(ACTIVE, AnimationManager.get().getAnimation("player"));
		ds.set(FOCUSED, AnimationManager.get().getAnimation("player-focused"));
		ds.set(DESTROYED, AnimationManager.get().getAnimation("enemydestroyed"));
		d = ds.get(ACTIVE);
		
		posx = Main.GAMEWIDTH/2;
		posy = Main.GAMEHEIGHT/2;
		direction = 0;
		speed = 5;
		focused = false;
		collidable = true;
		state = "active";
	}
	
	public void CheckEnemyCollisions() {
		
	}

	public void CheckPlayerCollisions() {
		
	}

	public void CheckBulletCollisions() {
		
	}

	public void CheckPowerupCollisions() {
		
	}
	
	public void onDestroyed() {
		state = "beingdestroyed";
		AudioManager.get().playSound("destroyed");
		collidable = false;
	}
	
	public void onHit(int damage) {
		onDestroyed();
	}

	public void update(int delta) {
		if (state == "beingdestroyed") {
			d = ds.get(DESTROYED);
			state = "destroyed";
		}
		if (!d.isPlaying()) {
			state = "dead";
		}
	}

	public void move() {
		posx += Math.cos(direction)*speed;
		posy += Math.sin(direction)*speed;
	}
	
	public void moveUp() {
		if (posy > 0 && state == "active") {
			if (!focused) {
				posy -= speed;
			} else {
				posy -= speed/2;
			}
		}
	}
	
	public void moveDown() {
		if (posy < Main.GAMEHEIGHT - d.getSize()[1] && state == "active") {
			if (!focused) {
				posy += speed;
			} else {
				posy += speed/2;
			}
		}
	}
	
	public void moveLeft() {
		if (posx > Main.LIMITLEFT && state == "active") {
			if (!focused) {
				posx -= speed;
			} else {
				posx -= speed/2;
			}
		}
	}
	
	public void moveRight() {
		if (posx < Main.LIMITRIGHT - d.getSize()[0] && state == "active") {
			if (!focused) {
				posx += speed;
			} else {
				posx += speed/2;
			}
		}
	}

	public void draw() {
		if (state == "active") {
			d.draw(posx, posy);
		} else if (state == "destroyed") {
			d.play(posx, posy);
		}
	}

	public boolean checkCollision(GameObject go) {
		return false;
	}
	
	public void shot(int delta) {
		lastShoot += delta;
		if (lastShoot >= delay && state == "active") {
			FirePattern fp;
			for (String id : fps) {
				fp = FirePatternsManager.get().newPattern(id, posx, posy, this);
				Play.bc.add(fp);
			}
			lastShoot = 0;
			AudioManager.get().playSound("playershot");
		}
	}
	
	public float[] getSize() {
		return d.getSize();
	}
	
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
	}

	public void addSound(String key, int id) {
		sounds.set(id, key);
	}

	public void addPattern(String id) {
		fps.add(id);
	}

}