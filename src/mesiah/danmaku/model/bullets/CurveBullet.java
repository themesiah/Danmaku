package mesiah.danmaku.model.bullets;

import java.util.ArrayList;

import mesiah.danmaku.Main;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Curve;
import org.newdawn.slick.geom.Vector2f;

public class CurveBullet extends Bullet {
	private ArrayList<Curve> cs;
	private int curveTime;
	private int timer;
	
	public CurveBullet(float x, float y, boolean ally, String key) throws SlickException {
		super(x, y, ally, key);
		cs = new ArrayList<Curve>();
		timer = 0;
		curveTime = 1000;
	}
	
	public void add(Curve c) {
		cs.add(c);
	}
	
	public void pop() {
		cs.remove(0);
	}
	
	public Vector2f nextPoint(float t) {
		return cs.get(0).pointAt(t);
	}
	
	public boolean moreCurves() {
		return cs.size() > 0;
	}
	
	public void move(int delta) {
		if (moreCurves()) {
			if (timer <= curveTime) {
				float t = (float) ((float) timer / (float) curveTime);
				Vector2f point = nextPoint(t);
				posx = point.getX();
				posy = point.getY();
				timer += delta;
			} else {
				timer = 0;
				pop();
			}
		}
	}
	
	public void update(int delta) {
		if (delay <= 0) {
			move(delta);
			CheckEnemyCollisions();
			CheckPlayerCollisions();
			if (posx > Main.GAMEWIDTH+20 ||	posx < -20 || posy < -20 ||	posy > Main.GAMEHEIGHT+20 || !moreCurves()) {
				parent.addToRemove(this);
			}
		} else {
			state = "not";
			delay -= delta;
			if (delay <= 0) {
				state = "active";
			}
		}
	}
	
	
}
