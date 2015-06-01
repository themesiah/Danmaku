package mesiah.danmaku.model;

import java.util.HashMap;

public class EnemiesManager {
	private static EnemiesManager em = null;
	private HashMap<String, Enemy> enemies;
	
	private EnemiesManager() {
		enemies = new HashMap<String, Enemy>();
	}
	
	public static EnemiesManager get() {
		if (em == null) {
			em = new EnemiesManager();
		}
		return em;
	}
	
	public Enemy newEnemy(String key) {
		return enemies.get(key).copy();
	}
	
	public void addEnemy(Enemy e) {
		enemies.put(e.getEnemyID(), e);
	}
}
