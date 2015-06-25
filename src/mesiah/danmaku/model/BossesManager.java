package mesiah.danmaku.model;

import java.util.HashMap;

public class BossesManager {
	private static BossesManager em = null;
	private HashMap<String, Boss> bosses;
	
	private BossesManager() {
		bosses = new HashMap<String, Boss>();
	}
	
	public static BossesManager get() {
		if (em == null) {
			em = new BossesManager();
		}
		return em;
	}
	
	public Boss newBoss(String key) {
		Boss b = bosses.get(key).copy();
		b.initBoss();
		return b;
	}
	
	public void addBoss(Boss e) {
		bosses.put(e.getEnemyID(), e);
	}
}
