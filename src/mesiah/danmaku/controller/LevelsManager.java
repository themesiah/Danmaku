package mesiah.danmaku.controller;

import java.util.ArrayList;

public class LevelsManager {
	private static LevelsManager lm = null;
	private ArrayList<Level> levels;
	
	private LevelsManager() {
		levels = new ArrayList<Level>();
	}
	
	public static LevelsManager get() {
		if (lm == null) {
			lm = new LevelsManager();
		}
		return lm;
	}
	
	public void addLevel(Level l) {
		levels.add(l);
	}
	
	public Level getLevel() {
		Level l = levels.get(0);
		levels.remove(0);
		return l;
	}
	
	public boolean finished() {
		return levels.size() <= 0;
	}
}
