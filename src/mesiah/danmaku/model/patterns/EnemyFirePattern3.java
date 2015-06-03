package mesiah.danmaku.model.patterns;

import mesiah.danmaku.model.GameObject;
import mesiah.danmaku.model.VisibleGameObject;
import mesiah.danmaku.model.bullets.Bullet;
import mesiah.danmaku.model.bullets.DivisibleBulletD;
import mesiah.danmaku.model.bullets.Shootable;
import mesiah.danmaku.util.GetDirection;

public class EnemyFirePattern3 extends FirePattern {
	
	public EnemyFirePattern3(VisibleGameObject parent) {
		super(parent);
		try {
			posx += parent.getSize()[0]/2;
			posy += parent.getSize()[1];
			
			Bullet b = new Bullet(posx, posy, false, "enemybullet2");
			DivisibleBulletD db = new DivisibleBulletD(b);
			db.setAngleOffset(0);
			db.setLifeTime(3000);
			db.setSpeed(1.0f);
			db.setDepth(2);
			db.setDirection(GetDirection.getDirectionToPlayer(posx, posy));
			db.addPattern(FirePatternsManager.BASICFIREPATTERN);
			add((Shootable) db);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw() {
		for (Shootable b:gol) {
			b.draw();
		}
	}
	
	public void update(int delta) {
		for (Shootable b:gol) {
			b.update(delta);
		}
		for (Shootable b:toRemove) {
			gol.remove(b);
			b = null;
		}
		toRemove.clear();
	}
	
	public void addToRemove(Shootable b) {
		toRemove.add(b);
	}
	
	public void add(Shootable b) {
		gol.add(b);
		b.setParent(this);
	}
	
	public void add(GameObject b) {
		gol.add((Shootable) b);
		((Shootable) b).setParent(this);
	}
	
	public GameObject get(int n) {
		return (GameObject) gol.get(n);
	}
	
	public void remove(int n) {
		gol.remove(n);
	}
	
	public void remove(GameObject go) {
		gol.remove(go);
	}
	
	public int size() {
		return gol.size();
	}
	
	public void move(int delta) {

	}
}
