package mesiah.danmaku.model.patterns;

import java.util.ArrayList;

import mesiah.danmaku.model.bullets.CustomBullet;

public class CustomFirePattern {
	protected String id;
	protected ArrayList<CustomBullet> cbs;
	protected int delay;

	public CustomFirePattern(String id) {
		cbs = new ArrayList<CustomBullet>();
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void addCustomBullet(CustomBullet cb) {
		cbs.add(cb);
	}
	
	public int size() {
		return cbs.size();
	}
	
	public CustomBullet getCustomBullet(int n) {
		return cbs.get(n);
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

}
