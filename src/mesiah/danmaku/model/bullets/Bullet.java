package mesiah.danmaku.model.bullets;

import java.util.ArrayList;
import java.util.List;

import mesiah.danmaku.Main;
import mesiah.danmaku.Play;
import mesiah.danmaku.model.Enemy;
import mesiah.danmaku.model.GameObject;
import mesiah.danmaku.model.Player;
import mesiah.danmaku.model.VisibleGameObject;
import mesiah.danmaku.model.patterns.FirePattern;
import mesiah.danmaku.view.AnimationManager;
import mesiah.danmaku.view.Drawable;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Rectangle;

public class Bullet extends VisibleGameObject {
	boolean ally;
	FirePattern parent;
	int damage;
	int delay;
	public static int ACTIVE = 0;
	public static int DESTROYED = 1;
	
	public Bullet(float x, float y, boolean ally, String key) throws SlickException {
		initBullet(x, y, key);
		this.ally = ally;
		posx -= getSize()[0]/2;
		posy -= getSize()[1]/2;
		damage = 10;
		delay = 0;
		state = "active";
	}
	
	public void setDelay(int d) {
		delay = d;
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
	
	private void initBullet(float x, float y, String key) throws SlickException {
		ds = new ArrayList<Drawable>();
		sounds = new ArrayList<String>();
		for (int i = 0; i < DESTROYED+1; i++) {
			ds.add(null);
			sounds.add(null);
		}
		ds.set(ACTIVE, AnimationManager.get().getAnimation(key));
		d = ds.get(ACTIVE);
		posx = x;
		posy = y;
		direction = 90.0f;
		speed = 10;
		collidable = true;
	}
	
	public void setParent(FirePattern fp) {
		parent = fp;
	}

	public void CheckEnemyCollisions() {
		if (this.collidable) {
			Rectangle r = new Rectangle(posx, posy, getSize()[0], getSize()[1]);
			List<Enemy> elist = Play.ec.getEnemies();
			for (Enemy e: elist) {
				Rectangle re = new Rectangle(e.getPosX(), e.getPosY(), e.getSize()[0], e.getSize()[1]);
				if (r.intersects(re) && e.isCollidable() && ally) {
					e.onHit(damage);
					collidable = false;
					parent.addToRemove(this);
				}
			}
		}
	}

	public void CheckPlayerCollisions() {
		if (this.collidable) {
			Rectangle r = new Rectangle(posx, posy, getSize()[0], getSize()[1]);
			List<Player> plist = Play.pc.getPlayers();
			for (Player p: plist) {
				Ellipse el = new Ellipse(p.getPosX() + p.getSize()[0]/2, p.getPosY() + p.getSize()[1]/2 - 1, 3.0f, 3.0f);
				//Ellipse el = new Ellipse(p.getPosX() + p.getSize()[0]/2, p.getPosY() + p.getSize()[1]/4, 4.0f, 4.0f);
				if (r.intersects(el) && p.isCollidable() && !ally) {
					p.onHit(damage);
					collidable = false;
					parent.addToRemove(this);
				}
			}
		}
	}

	public void CheckBulletCollisions() {

	}

	public void CheckPowerupCollisions() {

	}

	public boolean checkCollision(GameObject go) {
		return false;
	}

	public void update(int delta) {
		if (delay <= 0) {
			move();
			CheckEnemyCollisions();
			CheckPlayerCollisions();
			if (posx > Main.GAMEWIDTH+20 ||	posx < -20 || posy < -20 ||	posy > Main.GAMEHEIGHT+20) {
				parent.addToRemove(this);
			}
		} else {
			state = "not";
			delay -= delta;
			if (delay <= 0) {
				state = "active";
			}
		}
	}

	public void move() {
		posx += Math.cos(Math.toRadians(direction))*speed;
		posy -= Math.sin(Math.toRadians(direction))*speed;
	}

	public void draw() {
		if (state == "active") {
			d.draw(posx, posy);
		}
	}
	
	public float[] getSize() {
		return d.getSize();
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

}
