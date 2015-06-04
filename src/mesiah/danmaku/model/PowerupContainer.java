package mesiah.danmaku.model;

import java.util.ArrayList;
import java.util.List;

public class PowerupContainer {
	List<Powerup> pulist;
	List<Object> toRemove;
	
	public PowerupContainer() {
		this.pulist = new ArrayList<Powerup>();
		this.toRemove = new ArrayList<Object>();
	}
	
	public void add(Powerup pu) {
		pulist.add(pu);
	}

	public Powerup get(int n) {
		return pulist.get(n);
	}

	public void remove(Powerup pu) {
		pulist.remove(pu);
	}

	public void remove(int n) {
		pulist.remove(n);
	}

	public int size() {
		return pulist.size();
	}
	
	public void update(int delta) {
		for (Powerup pu:pulist) {
			pu.update(delta);
		}
		for (Object o:toRemove) {
			pulist.remove(o);
			o = null;
		}
		toRemove.clear();
	}
	
	public void draw() {
		for (Powerup pu:pulist) {
			pu.draw();
		}
	}
	
	public List<Powerup> getPowerups() {
		return pulist;
	}
	
	public void addToRemove(Object o) {
		toRemove.add(o);
	}
}
