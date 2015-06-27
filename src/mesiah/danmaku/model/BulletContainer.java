package mesiah.danmaku.model;

import java.util.ArrayList;
import java.util.List;

import mesiah.danmaku.model.patterns.FirePattern;

/**
 * Clase que contiene las listas de balas amigas y enemigas.
 * Además, gestiona que aparezcan y desaparezcan balas.
 * @author Mesiah
 *
 */
public class BulletContainer {
	List<FirePattern> blist;
	List<Object> toRemove;
	List<FirePattern> toAdd;
	
	/**
	 * Función que se utiliza para eliminar todas las balas enemigas de la pantalla (para bosses).
	 */
	public void addToRemoveEnemies() {
		for (FirePattern fp : blist) {
			if (fp.size() > 0) {
				if (!(fp.getBullet(0).isAlly())) {
					if (!toRemove.contains(fp)) {
						addToRemove(fp);
					}
				}
			} else {
				if (!toRemove.contains(fp)) {
					addToRemove(fp);
				}
			}
		}
	}
	
	/**
	 * Función que añade un objeto a la lista "para remover".
	 * No se remueven directamente porque la lista tiene un size cuando se
	 * empieza a recorrer, y debe mantenerla hasta el final del recorrido.
	 * @param o Objeto a remover.
	 */
	public void addToRemove(Object o) {
		toRemove.add(o);
	}
	
	public BulletContainer() {
		this.blist = new ArrayList<FirePattern>();
		this.toRemove = new ArrayList<Object>();
		this.toAdd = new ArrayList<FirePattern>();
	}
	
	/**
	 * Función que añade un FirePattern a la lista "para añadir".
	 * No se añaden directamente porque la lista tiene un size cuando se
	 * empieza a recorrer, y debe mantenerla hasta el final del recorrido.
	 * @param b Objeto a añadir.
	 */
	public void addToAdd(FirePattern b) {
		toAdd.add(b);
	}
	
	public void add(FirePattern b) {
		blist.add(b);
	}

	public FirePattern get(int n) {
		return blist.get(n);
	}

	public void remove(FirePattern b) {
		blist.remove(b);
	}

	public void remove(int n) {
		blist.remove(n);
	}

	public int size() {
		int size = 0;
		for (FirePattern fp:blist) {
			size += fp.size();
		}
		return size;
	}
	
	public int patterns() {
		return blist.size();
	}
	
	/**
	 * Función que actualiza los patrones contenidos en la clase.
	 * Primero actualiza los patrones.
	 * Luego añade para remover los patrones que ya no tengan balas.
	 * Luego remueve los patrones que tocan.
	 * Luego añade los patrones que tocan añadir.
	 * @param delta
	 */
	public void update(int delta) {
		for (FirePattern fp:blist) {
			fp.update(delta);
			if (fp.size() == 0) {
				if (!toRemove.contains(fp)) {
					addToRemove(fp);
				}
			}
		}
		for (Object o:toRemove) {
			blist.remove(o);
			o = null;
		}
		toRemove.clear();
		for (FirePattern fp:toAdd) {
			blist.add(fp);
		}
		toAdd.clear();
	}
	
	/**
	 * Dibuja todos los patrones.
	 */
	public void draw() {
		for (FirePattern fp:blist) {
			fp.draw();
		}
	}
}
