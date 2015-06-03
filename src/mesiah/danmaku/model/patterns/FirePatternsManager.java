package mesiah.danmaku.model.patterns;

import mesiah.danmaku.model.VisibleGameObject;

public class FirePatternsManager {
	private static FirePatternsManager fpm = null;
	
	public static final String PLAYERFIREPATTERN = "playerFirePattern";
	public static final String ENEMYFIREPATTERN1 = "enemyFirePattern";
	public static final String ENEMYFIREPATTERN2 = "enemyFirePattern2";
	public static final String ENEMYFIREPATTERN3 = "enemyFirePattern3";
	public static final String ENEMYFIREPATTERN4 = "enemyFirePattern4";
	public static final String BASICFIREPATTERN = "basicFirePattern";
	
	private FirePatternsManager() {
	}
	
	public static FirePatternsManager get() {
		if (fpm == null) {
			fpm = new FirePatternsManager();
		}
		return fpm;
	}
	
	public FirePattern newPattern(String key, VisibleGameObject parent) {
		FirePattern fp;
		switch(key) {
			case PLAYERFIREPATTERN:
				//fp = new PlayerFirePattern(parent);
				fp = FirePatternManager.get().compose("playerfirepattern", parent);
				break;
			case ENEMYFIREPATTERN1:
				fp = new EnemyFirePattern(parent);
				break;
			case ENEMYFIREPATTERN2:
				fp = new EnemyFirePattern2(parent);
				break;
			case ENEMYFIREPATTERN3:
				fp = new EnemyFirePattern3(parent);
				break;
			case ENEMYFIREPATTERN4:
				fp = new EnemyFirePattern4(parent);
				break;
			case BASICFIREPATTERN:
				fp = new BasicFirePattern(parent);
				break;
			default:
				fp = new BasicFirePattern(parent);
		}
		return fp;
	}

}
