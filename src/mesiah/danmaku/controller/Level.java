package mesiah.danmaku.controller;

import java.util.ArrayList;

/**
 * Contiene un nivel completo del juego, lo que significa que tiene spawns de enemigos para cada tiempo
 * correspondiente. Además, tiene el fondo, template y música de nivel.
 * @author Mesiah
 *
 */
public class Level {
	private ArrayList<ArrayList<EnemySpawn>> enemySpawn;
	private ArrayList<Integer> spawnTimes;
	private String bg;
	private String template;
	private String music;
	
	public String getMusic() {
		return music;
	}

	public void setMusic(String music) {
		this.music = music;
	}

	public Level() {
		enemySpawn = new ArrayList<ArrayList<EnemySpawn>>();
		spawnTimes = new ArrayList<Integer>();
	}

	/**
	 * Añade los spawns de enemigos para un tiempo concreto.
	 * @param es Spawns de enemigos.
	 * @param spawnTime Tiempo en milisegundos en el que aparecerán los enemigos.
	 */
	public void addStep(ArrayList<EnemySpawn> es, int spawnTime) {
		enemySpawn.add(es);
		spawnTimes.add(spawnTime);
	}
	
	public ArrayList<ArrayList<EnemySpawn>> getEnemySpawn() {
		return enemySpawn;
	}

	public void setEnemySpawn(ArrayList<ArrayList<EnemySpawn>> enemySpawn) {
		this.enemySpawn = enemySpawn;
	}

	public String getBg() {
		return bg;
	}

	public void setBg(String bg) {
		this.bg = bg;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
	public boolean finished() {
		return enemySpawn.size() <= 0;
	}
	
	/**
	 * Devuelve todos los spawns correspondientes al tiempo pasado por parámetro, y todos los anteriores
	 * no devueltos ya.
	 * @param timer Tiempo pasado desde el inicio del nivel.
	 * @return Un ArrayList de spawns de enemigos.
	 */
	public ArrayList<EnemySpawn> getSpawns(int timer) {
		ArrayList<EnemySpawn> es = new ArrayList<EnemySpawn>();
		while (spawnTimes.get(0) <= timer) {
			for (EnemySpawn e : enemySpawn.get(0)) {
				es.add(e);
			}
			enemySpawn.remove(0);
			spawnTimes.remove(0);
			if (spawnTimes.size() <= 0) {
				break;
			}
		}
		return es;
	}
}
