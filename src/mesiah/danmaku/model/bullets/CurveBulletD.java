package mesiah.danmaku.model.bullets;

import java.util.ArrayList;

import mesiah.danmaku.model.patterns.FirePattern;

import org.newdawn.slick.geom.Curve;
import org.newdawn.slick.geom.Vector2f;

public class CurveBulletD extends BulletDecorator {
	protected FirePattern parent;
	private ArrayList<Curve> cs;
	private ArrayList<Integer> curveTime;
	private int curveTimer;
	private boolean onlyCurve;
	
	public CurveBulletD (Shootable b) {
		super(b);
		cs = new ArrayList<Curve>();
		curveTime = new ArrayList<Integer>();
		curveTimer = 0;
		onlyCurve = true;
		super.setCanMove(false);
	}
	
	public boolean isOnlyCurve() {
		return onlyCurve;
	}
	
	public void setOnlyCurve(boolean oc) {
		onlyCurve = oc;
	}
	
	public void add(Curve c, int time) {
		cs.add(c);
		curveTime.add(time);
	}
	
	public void pop() {
		cs.remove(0);
		curveTime.remove(0);
	}
	
	public Vector2f nextPoint(float t) {
		return cs.get(0).pointAt(t);
	}
	
	public boolean moreCurves() {
		return cs.size() > 0;
	}
	
	public void move(int delta) {
		if (moreCurves()) {
			if (super.getDelay() <= 0) {
				if (curveTimer <= curveTime.get(0)) {
					float t = (float) ((float) curveTimer / (float) curveTime.get(0));
					Vector2f point = nextPoint(t);
					super.setPosX(point.getX());
					super.setPosY(point.getY());
					curveTimer += delta;
				} else {
					curveTimer = 0;
					pop();
				}
			}
		} else if (!onlyCurve) {
			super.setCanMove(true);
		} else {
			super.setState("dead");
			super.addToRemove(this);
		}
	}
	
	public void addToRemove(Shootable s) {
		super.addToRemove(s);
	}
	
	public String getState() {
		return super.getState();
	}
	
	
	
	public void update(int delta) {
		super.update(delta);
		move(delta);
		if (super.getState() == "dead") {
			super.addToRemove(this);
		}
	}

	public void draw() {
		super.draw();
	}
	
	public float[] getSize() {
		return super.getSize();
	}
	
	public FirePattern getParent() {
		return parent;
	}
	
	public void setParent(FirePattern fp) {
		super.setParent(fp);
	}
	
	public void setPosX(float x) {
		super.setPosX(x);
	}
	
	public void setPosY(float y) {
		super.setPosY(y);
	}
	
	public void setDirection(float d) {
		super.setDirection(d);
	}
	
	public void setDelay(int delay) {
		super.setDelay(delay);
	}
	
	public int getDelay() {
		return super.getDelay();
	}
	
	public Curve getCurve(int n) {
		return cs.get(n);
	}
	
	public boolean isAlly() {
		return super.isAlly();
	}
	

}
