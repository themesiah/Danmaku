package mesiah.danmaku.view;

import java.util.HashMap;
import java.util.Map;

public class AnimationManager {
	private static AnimationManager am = null;
	Map<String, Animation> animations;
	
	private AnimationManager() {
		animations = new HashMap<String, Animation>();
	}
	
	public static AnimationManager get() {
		if (am == null) {
			am = new AnimationManager();
		}
		return am;
	}
	
	public void addAnimation(Animation a, String key) {
		animations.put(key, a);
	}
	
	public void playAnimation(String key, float x, float y) {
		animations.get(key).play(x, y);
	}
	
	public Animation getAnimation(String key) {
		return animations.get(key).copy();
	}
}
