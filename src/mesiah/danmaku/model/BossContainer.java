package mesiah.danmaku.model;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;

/**
 * Clase que contiene todos los bosses que están actualmente en juego.
 * Normalmente será 1, pero nunca se sabe...
 * @author Mesiah
 *
 */
public class BossContainer {
	List<Boss> elist;
	List<Object> toRemove;
	
	public void addToRemove(Object o) {
		toRemove.add(o);
	}
	
	public BossContainer() {
		this.elist = new ArrayList<Boss>();
		this.toRemove = new ArrayList<Object>();
	}
	
	public List<Boss> getEnemies() {
		return elist;
	}
	
	public void add(Boss e) {
		elist.add(e);
	}

	public Boss get(int n) {
		return elist.get(n);
	}

	public void remove(Boss e) {
		elist.remove(e);
	}

	public void remove(int n) {
		elist.remove(n);
	}

	public int size() {
		return elist.size();
	}
	
	/**
	 * Hace el update de todos los bosses a la vez.
	 * @param delta Tiempo pasado desde el último update.
	 */
	public void update(int delta) {
		for (Boss e:elist) {
			e.update(delta);
		}
		for (Object o:toRemove) {
			elist.remove(o);
			o = null;
		}
		toRemove.clear();
	}
	
	/**
	 * Dibuja todos los bosses.
	 * Además, pasa como parámetro los gráficos de Slick2D para poder dibujar la interfaz del boss.
	 * @param g
	 */
	public void draw(Graphics g) {
		for (Boss e:elist) {
			e.draw(g);
		}
	}
}
