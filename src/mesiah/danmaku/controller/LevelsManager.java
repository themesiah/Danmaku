package mesiah.danmaku.controller;

import java.util.ArrayList;

/**
 * Contiene todos los niveles del juego y controla que no se hayan terminado.
 * Te devuelve el nivel correspondiente en orden.
 * Es un singleton.
 * @author Mesiah
 *
 */
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
	
	/**
	 * Añade un nivel al array.
	 * @param l Nivel
	 */
	public void addLevel(Level l) {
		levels.add(l);
	}
	
	/** 
	 * Devuelve el nivel que toca en el momento.
	 * Después, lo borra de la lista.
	 * @return El nivel que toca.
	 */
	public Level getLevel() {
		Level l = levels.get(0);
		levels.remove(0);
		return l;
	}
	
	/**
	 * Comprueba si se han terminado los niveles.
	 * @return Un booleano.
	 */
	public boolean finished() {
		return levels.size() <= 0;
	}
}
