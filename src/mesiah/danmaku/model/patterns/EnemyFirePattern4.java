package mesiah.danmaku.model.patterns;

import org.newdawn.slick.geom.Curve;
import org.newdawn.slick.geom.Vector2f;

import mesiah.danmaku.Main;
import mesiah.danmaku.model.GameObject;
import mesiah.danmaku.model.VisibleGameObject;
import mesiah.danmaku.model.bullets.Bullet;
import mesiah.danmaku.model.bullets.CurveBulletD;
import mesiah.danmaku.model.bullets.DivisibleBulletD;
import mesiah.danmaku.model.bullets.Shootable;
import mesiah.danmaku.util.CurveManager;

public class EnemyFirePattern4 extends FirePattern {

	public EnemyFirePattern4(VisibleGameObject parent) {
		super(parent);
		try {
			posx += parent.getSize()[0]/2;
			posy += parent.getSize()[1];
			int delay = 50;
			
			Vector2f v1 = new Vector2f(posx, posy);
			Vector2f v4 = new Vector2f(posx, Main.GAMEHEIGHT - 50);
			float difY = Main.GAMEHEIGHT+50 - posy;
			Vector2f v2 = new Vector2f(posx-150, difY/3 + posy);
			Vector2f v3 = new Vector2f(posx+150, difY*2/3 + posy);
			Curve c = new Curve(v1, v2, v3, v4);
			
			Vector2f v22 = new Vector2f(posx-150, difY*2/3 + posy);
			Vector2f v32 = new Vector2f(posx+150, difY/3 + posy);
			Curve c2 = new Curve(v1, v32, v22, v4);
			
			c = CurveManager.get().compose("curve3", parent);
			c2 = CurveManager.get().compose("curve4", parent);
			
			for (int i = 0; i < 5; i++) {
				Bullet b = new Bullet(posx, posy, false, "enemybullet2");
				
				DivisibleBulletD db = new DivisibleBulletD(b);
				db.setAngleOffset(0);
				db.setLifeTime(3000);
				db.setDepth(2);
				db.addPattern(FirePatternsManager.BASICFIREPATTERN);
				CurveBulletD cb = new CurveBulletD(db);
				cb.add(c, 3000);
				cb.setOnlyCurve(true);
				cb.setSpeed(1.0f);
				cb.setDelay(delay*i);
				add((Shootable) cb);
				
				Bullet b2 = new Bullet(posx, posy, false, "enemybullet2");
				CurveBulletD cb2 = new CurveBulletD(b2);
				cb2.add(c2, 3000);
				cb2.setOnlyCurve(true);
				DivisibleBulletD db2 = new DivisibleBulletD(cb2);
				db2.setAngleOffset(0);
				db2.setLifeTime(3000);
				db2.setSpeed(1.0f);
				db2.setDepth(2);
				db2.addPattern(FirePatternsManager.BASICFIREPATTERN);
				db2.setDelay(delay*i);
				add((Shootable) db2);
			}
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
