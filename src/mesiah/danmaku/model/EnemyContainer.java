package mesiah.danmaku.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que contiene todos los enemigos que hay actualmente vivos.
 * @author Mesiah
 *
 */
public class EnemyContainer {
	List<Enemy> elist;
	List<Object> toRemove;
	
	public void addToRemove(Object o) {
		toRemove.add(o);
	}
	
	public EnemyContainer() {
		this.elist = new ArrayList<Enemy>();
		this.toRemove = new ArrayList<Object>();
	}
	
	public List<Enemy> getEnemies() {
		return elist;
	}
	
	public void add(Enemy e) {
		elist.add(e);
	}

	public Enemy get(int n) {
		return elist.get(n);
	}

	public void remove(Enemy e) {
		elist.remove(e);
	}

	public void remove(int n) {
		elist.remove(n);
	}

	public int size() {
		return elist.size();
	}
	
	/**
	 * Actualiza los enemigos y elimina los que toca.
	 * @see BulletContainer
	 * @param delta Tiempo que ha pasado desde el último update.
	 */
	public void update(int delta) {
		for (Enemy e:elist) {
			e.update(delta);
		}
		for (Object o:toRemove) {
			elist.remove(o);
			o = null;
		}
		toRemove.clear();
	}
	
	/**
	 * Dibuja todos los enemigos.
	 */
	public void draw() {
		for (Enemy e:elist) {
			e.draw();
		}
	}
}
