package mesiah.danmaku.model.patterns;

import org.newdawn.slick.geom.Curve;
import mesiah.danmaku.model.GameObject;
import mesiah.danmaku.model.VisibleGameObject;
import mesiah.danmaku.model.bullets.Bullet;
import mesiah.danmaku.model.bullets.CurveBulletD;
import mesiah.danmaku.model.bullets.Shootable;
import mesiah.danmaku.util.CurveManager;

public class EnemyFirePattern2 extends FirePattern {
	
	public EnemyFirePattern2(VisibleGameObject parent) {
		super(parent);
		try {
			int delay = 100;
			posx += parent.getSize()[0]/2;
			posy += parent.getSize()[1];
			
			Curve c = CurveManager.get().compose("curve1", parent);
			Curve c2 = CurveManager.get().compose("curve2", parent);
			
			for (int i = 0; i < 3; i++) {
				Bullet b1 = new Bullet(posx, posy, false, "enemybullet2");
				CurveBulletD cb1 = new CurveBulletD(b1);
				Bullet b2 = new Bullet(posx, posy, false, "enemybullet3");
				CurveBulletD cb2 = new CurveBulletD(b2);
				cb1.setOnlyCurve(true);
				cb2.setOnlyCurve(true);
				cb1.setSpeed(5.0f);
				cb2.setSpeed(5.0f);
				cb1.setPosX(c.pointAt(0.0f).getX() - cb1.getSize()[0]/2);
				cb2.setPosX(c.pointAt(0.0f).getY() - cb1.getSize()[1]/2);
				cb1.add(c, 2000);
				cb2.setPosX(c2.pointAt(0.0f).getX() - cb2.getSize()[0]/2);
				cb2.setPosY(c2.pointAt(0.0f).getY() - cb2.getSize()[1]/2);
				cb2.add(c2, 2000);
				cb1.setDelay(delay*i);
				cb2.setDelay(delay*i);
				add((Shootable) cb1);
				add((Shootable) cb2);
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
