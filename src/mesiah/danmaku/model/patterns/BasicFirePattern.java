package mesiah.danmaku.model.patterns;

import java.util.ArrayList;

import mesiah.danmaku.model.GameObject;
import mesiah.danmaku.model.VisibleGameObject;
import mesiah.danmaku.model.bullets.Shootable;

public class BasicFirePattern extends FirePattern {
	ArrayList<Shootable> gol;
	ArrayList<Shootable> toRemove;
	
	public BasicFirePattern(VisibleGameObject parent) {
		super(parent);
		gol = new ArrayList<Shootable>();
		toRemove = new ArrayList<Shootable>();
	}

	public void addToRemove(Shootable b) {
		toRemove.add(b);
	}

	public void add(Shootable b) {
		gol.add(b);
		(b).setParent(this);
	}
	
	public void add(GameObject b) {
		gol.add((Shootable) b);
		((Shootable) b).setParent(this);
	}

	public GameObject get(int n) {
		return (GameObject) gol.get(n);
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
		for (Shootable b:gol) {
			b.update(delta);
		}
		for (Shootable b:toRemove) {
			gol.remove(b);
			b = null;
		}
		toRemove.clear();
	}

	public void move(int delta) {

	}

	public void draw() {
		for (Shootable b:gol) {
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
	
	public ArrayList<Shootable> getBullets() {
		return gol;
	}
	
	public void changeDirection(float d) {
		for (Shootable s:gol) {
			s.setDirection(d);
		}
	}

}
