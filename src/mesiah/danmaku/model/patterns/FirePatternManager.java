package mesiah.danmaku.model.patterns;

import java.util.HashMap;

import mesiah.danmaku.model.VisibleGameObject;

public class FirePatternManager {

	public FirePatternManager() {
		cfp = new HashMap<String, CustomFirePattern>();
	}
	
	private static FirePatternManager fpm = null;
	private HashMap<String, CustomFirePattern> cfp;
	
	public static FirePatternManager get() {
		if (fpm == null) {
			fpm = new FirePatternManager();
		}
		return fpm;
	}
	
	public CustomFirePattern newFirePattern(String key) {
		return cfp.get(key);
	}
	
	public void addFirePattern(CustomFirePattern c) {
		this.cfp.put(c.getId(), c);
	}
	
	public FirePattern compose(String key, VisibleGameObject parent) {
		return null;
	}

}
