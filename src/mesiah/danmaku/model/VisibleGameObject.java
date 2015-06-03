package mesiah.danmaku.model;

import java.util.ArrayList;

import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import mesiah.danmaku.view.Drawable;

public abstract class VisibleGameObject extends GameObject implements Collidable {
	protected Drawable d;
	protected boolean collidable;
	protected int delay;
	
	protected ArrayList<Drawable> ds;
	protected ArrayList<String> sounds;
	protected ArrayList<Shape> ss;
	protected ArrayList<float[]> relatives;
	
	protected boolean canMove;
	
	public boolean isCanMove() {
		return canMove;
	}
	
	public void setCanMove(boolean cm) {
		canMove = cm;
	}
	
	
	public int getDelay() {
		return delay;
	}
	
	public void setDelay(int d) {
		delay = d;
	}

	public abstract float[] getSize();

	public boolean isCollidable() {
		return collidable;
	}
	
	public void addAnimation(Drawable d, int id) {
		ds.set(id, d);
	}
	
	public void addSound(String key, int id) {
		sounds.set(id, key);
	}
	
	public ArrayList<Shape> getHitBoxes() {
		for (int i = 0; i < ss.size(); i++) {
			Shape shape = ss.get(i);
			float[] rel = getRelatives(i);
			if (shape instanceof Ellipse) {
				shape.setCenterX(posx + getSize()[0]/2 + rel[0]);
				shape.setCenterY(posy + getSize()[1]/2 + rel[1]);
			} else {
				shape.setX(posx + rel[0]);
				shape.setY(posy + rel[1]);
			}
			ss.set(i, shape);
		}
		return ss;
	}
	
	public void addHitbox(Shape s) {
		float[] rel = {s.getX(), s.getY()};
		addRelative(rel);
		ss.add(s);
	}

	public void addHitbox() {
		Rectangle r = new Rectangle(posx, posy, getSize()[0], getSize()[1]);
		float[] rel = {0.0f, 0.0f};
		addRelative(rel);
		ss.add(r);
	}
	
	public float[] getRelatives(int n) {
		return relatives.get(n);
	}

	public void addRelative(float[] r) {
		relatives.add(r);
	}

	public void setRelatives(ArrayList<float[]> rel) {
		this.relatives = rel;
	}

	public void setHitboxes(ArrayList<Shape> ss) {
		this.ss = ss;
	}
	
	public boolean collides(Shape s, Shape s2) {
		if (s.intersects(s2) || s.contains(s2)) {
			return true;
		} else {
			return false;
		}
	}
}
