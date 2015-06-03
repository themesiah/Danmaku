package mesiah.danmaku.model.patterns;

import java.util.ArrayList;

import mesiah.danmaku.model.bullets.CustomBullet;

public class CustomFirePattern {
	protected String id;
	protected ArrayList<CustomBullet> cb;

	public CustomFirePattern(String id) {
		cb = new ArrayList<CustomBullet>();
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

}
