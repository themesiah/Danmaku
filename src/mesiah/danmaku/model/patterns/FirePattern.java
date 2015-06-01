package mesiah.danmaku.model.patterns;

import mesiah.danmaku.model.GameObject;
import mesiah.danmaku.model.VisibleGameObject;
import mesiah.danmaku.model.bullets.Bullet;


public abstract class FirePattern extends Pattern {
	protected VisibleGameObject parent;
	public FirePattern(VisibleGameObject parent) {
		this.parent = parent;
		this.posx = parent.getPosX();
		this.posy = parent.getPosY();
	}
	public abstract void addToRemove(Bullet b);
	public abstract void add(GameObject b);
}
