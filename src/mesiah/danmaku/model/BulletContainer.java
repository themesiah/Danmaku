package mesiah.danmaku.model;

import java.util.ArrayList;
import java.util.List;

import mesiah.danmaku.model.patterns.FirePattern;

public class BulletContainer {
	List<FirePattern> blist;
	List<Object> toRemove;
	List<FirePattern> toAdd;
	
	public void addToRemoveEnemies() {
		for (FirePattern fp : blist) {
			if (fp.size() > 0) {
				if (!(fp.getBullet(0).isAlly())) {
					if (!toRemove.contains(fp)) {
						addToRemove(fp);
					}
				}
			} else {
				if (!toRemove.contains(fp)) {
					addToRemove(fp);
				}
			}
		}
	}
	
	public void addToRemove(Object o) {
		toRemove.add(o);
	}
	
	public BulletContainer() {
		this.blist = new ArrayList<FirePattern>();
		this.toRemove = new ArrayList<Object>();
		this.toAdd = new ArrayList<FirePattern>();
	}
	
	public void addToAdd(FirePattern b) {
		toAdd.add(b);
	}
	
	public void add(FirePattern b) {
		blist.add(b);
	}

	public FirePattern get(int n) {
		return blist.get(n);
	}

	public void remove(FirePattern b) {
		blist.remove(b);
	}

	public void remove(int n) {
		blist.remove(n);
	}

	public int size() {
		int size = 0;
		for (FirePattern fp:blist) {
			size += fp.size();
		}
		return size;
	}
	
	public int patterns() {
		return blist.size();
	}
	
	public void update(int delta) {
		for (FirePattern fp:blist) {
			fp.update(delta);
			if (fp.size() == 0) {
				if (!toRemove.contains(fp)) {
					addToRemove(fp);
				}
			}
		}
		for (Object o:toRemove) {
			blist.remove(o);
			o = null;
		}
		toRemove.clear();
		for (FirePattern fp:toAdd) {
			blist.add(fp);
		}
		toAdd.clear();
	}
	
	public void draw() {
		for (FirePattern fp:blist) {
			fp.draw();
		}
	}
}
