package mesiah.danmaku.model.patterns;

import java.util.ArrayList;

import mesiah.danmaku.Play;
import mesiah.danmaku.model.GameObject;
import mesiah.danmaku.model.VisibleGameObject;
import mesiah.danmaku.model.bullets.Shootable;


public abstract class FirePattern extends Pattern {
	protected ArrayList<Shootable> gol;
	protected ArrayList<Shootable> toRemove;
	protected VisibleGameObject parent;
	protected int delay;
	protected int timer;
	
	public FirePattern(VisibleGameObject parent) {
		this.parent = parent;
		this.posx = parent.getPosX();
		this.posy = parent.getPosY();
		gol = new ArrayList<Shootable>();
		toRemove = new ArrayList<Shootable>();
	}
	
	public boolean parentActive() {
		return parent.getState() == "active";
	}
	
	public float getParentPosX() {
		return parent.getPosX() - parent.getSize()[0]/2;
	}
	
	public float getParentPosY() {
		return parent.getPosY() - parent.getSize()[0]/2;
	}
	
	public void draw() {
		for (Shootable b:gol) {
			b.draw();
		}
	}
	
	public void update(int delta) {
		for (Shootable b:gol) {
			b.update(delta);
		}
		for (Shootable b:toRemove) {
			gol.remove(b);
			b = null;
		}
		if (gol.size() <= 0) {
			Play.bc.addToRemove(this);
		}
		toRemove.clear();
	}
	
	public void addToRemove(Shootable b) {
		toRemove.add(b);
	}
	
	public void add(Shootable b) {
		gol.add(b);
		b.setParent(this);
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
	
	public void move(int delta) {

	}
	
	public ArrayList<Shootable> getBullets() {
		return gol;
	}
	
	public void changeDirection(float direction) {
		for (Shootable s:gol) {
			s.setDirection(direction);
		}
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}
	
	public Shootable getBullet(int n) {
		return gol.get(n);
	}

}
