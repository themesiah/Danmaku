package mesiah.danmaku.model.patterns;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import mesiah.danmaku.model.GameObject;
import mesiah.danmaku.model.VisibleGameObject;
import mesiah.danmaku.model.bullets.Bullet;
import mesiah.danmaku.model.bullets.DivisibleBullet;
import mesiah.danmaku.util.GetDirection;

public class EnemyFirePattern3 extends FirePattern {
	ArrayList<DivisibleBullet> gol;
	ArrayList<DivisibleBullet> toRemove;
	
	public EnemyFirePattern3(VisibleGameObject parent) {
		super(parent);
		gol = new ArrayList<DivisibleBullet>();
		toRemove = new ArrayList<DivisibleBullet>();
		
		try {
			posx += parent.getSize()[0]/2;
			posy += parent.getSize()[1];
			DivisibleBullet b = new DivisibleBullet(posx, posy, false, "enemybullet2", 2);
			b.setSpeed(1.0f);
			b.setDirection(GetDirection.getDirectionToPlayer(posx, posy));
			b.addPattern(FirePatternsManager.BASICFIREPATTERN);
			add(b);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void addToRemove(Bullet b) {
		toRemove.add((DivisibleBullet) b);
	}

	public void add(GameObject b) {
		gol.add((DivisibleBullet) b);
		((DivisibleBullet) b).setParent(this);
	}

	public GameObject get(int n) {
		return gol.get(n);
	}

	public void remove(int n) {
		gol.remove(n);
	}

	public void remove(GameObject go) {
		gol.remove(go);
	}

	public int size() {
		return gol.size();
	}

	public void update(int delta) {
		for (DivisibleBullet b:gol) {
			b.update(delta);
		}
		for (DivisibleBullet b:toRemove) {
			gol.remove(b);
			b = null;
		}
		toRemove.clear();
	}

	public void move() {

	}

	public void draw() {
		for (DivisibleBullet b:gol) {
			b.draw();
		}
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

}
