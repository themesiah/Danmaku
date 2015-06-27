package mesiah.danmaku.model;

import java.util.HashMap;

/**
 * Clase que contiene un hashmap de todos los bosses.
 * Los devuelve cuando toca y se pueden añadir nuevos.
 * @author Mesiah
 *
 */
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
	
	/**
	 * Devuelve una copia de un boss del hashmap.
	 * @param key Nombre del boss en el hashmap.
	 * @return La copia del boss.
	 */
	public Boss newBoss(String key) {
		Boss b = bosses.get(key).copy();
		//b.initBoss();
		return b;
	}
	
	/**
	 * Añade un boss al hashmap.
	 * @param b Boss a añadir.
	 */
	public void addBoss(Boss b) {
		bosses.put(b.getEnemyID(), b);
	}
}
