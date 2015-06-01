package mesiah.danmaku.model.patterns;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Curve;
import org.newdawn.slick.geom.Vector2f;

import mesiah.danmaku.Main;
import mesiah.danmaku.model.GameObject;
import mesiah.danmaku.model.VisibleGameObject;
import mesiah.danmaku.model.bullets.Bullet;
import mesiah.danmaku.model.bullets.CurveBullet;
import mesiah.danmaku.model.bullets.DivisibleBullet;

public class EnemyFirePattern2 extends FirePattern {
	ArrayList<Bullet> gol;
	ArrayList<Bullet> toRemove;
	
	public EnemyFirePattern2(VisibleGameObject parent) {
		super(parent);
		gol = new ArrayList<Bullet>();
		toRemove = new ArrayList<Bullet>();
		
		try {
			//int patternDensity = 10;
			int delay = 50;
			posx += parent.getSize()[0]/2;
			posy += parent.getSize()[1];
			
			Vector2f v1 = new Vector2f(posx, posy);
			Vector2f v4 = new Vector2f(posx, Main.GAMEHEIGHT + 10);
			float difY = Main.GAMEHEIGHT+50 - posy;
			Vector2f v2 = new Vector2f(posx-150, difY/3 + posy);
			Vector2f v3 = new Vector2f(posx+150, difY*2/3 + posy);
			Curve c = new Curve(v1, v2, v3, v4);
			
			Vector2f v22 = new Vector2f(posx-150, difY*2/3 + posy);
			Vector2f v32 = new Vector2f(posx+150, difY/3 + posy);
			Curve c2 = new Curve(v1, v32, v22, v4);
			
			for (int i = 0; i < DivisibleBullet.density; i++) {
				CurveBullet cb1 = new CurveBullet(posx, posy, false, "enemybullet3");
				CurveBullet cb2 = new CurveBullet(posx, posy, false, "enemybullet2");
				cb1.setPosX(c.pointAt(0.0f).getX() - cb1.getSize()[0]/2);
				cb2.setPosX(c.pointAt(0.0f).getY() - cb1.getSize()[1]/2);
				cb1.add(c);
				cb2.setPosX(c2.pointAt(0.0f).getX() - cb2.getSize()[0]/2);
				cb2.setPosY(c2.pointAt(0.0f).getY() - cb2.getSize()[1]/2);
				cb2.add(c2);
				cb1.setDelay(delay*i);
				cb2.setDelay(delay*i);
				add(cb1);
				add(cb2);
			}
			
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void addToRemove(Bullet b) {
		toRemove.add(b);
	}

	public void add(GameObject b) {
		gol.add((Bullet) b);
		((Bullet) b).setParent(this);
	}

	public GameObject get(int n) {
		return gol.get(n);
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

	public void update(int delta) {
		for (Bullet b:gol) {
			b.update(delta);
		}
		for (Bullet b:toRemove) {
			gol.remove(b);
			b = null;
		}
		toRemove.clear();
	}

	public void move() {

	}

	public void draw() {
		for (Bullet b:gol) {
			b.draw();
		}
	}

	public float getPosX() {
		return posx;
	}
	
	public float getPosY() {
		return posy;
	}
	
	public void setPosX(float x) {
		posx = x;
	}
	
	public void setPosY(float y) {
		posy = y;
	}
	
	public float getFacing() {
		return facing;
	}

	public float getDirection() {
		return direction;
	}

	public float getSpeed() {
		return speed;
	}

	public String getState() {
		return state;
	}

	public void setFacing(float f) {
		facing = f;
	}

	public void setDirection(float dir) {
		direction = dir;
	}

	public void setSpeed(float s) {
		speed = s;
	}

	public void setState(String s) {
		state = s;
	}

}
