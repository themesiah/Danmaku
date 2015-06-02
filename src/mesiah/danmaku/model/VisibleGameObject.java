package mesiah.danmaku.model;

import java.util.ArrayList;

import org.newdawn.slick.geom.Shape;

import mesiah.danmaku.view.Drawable;

public abstract class VisibleGameObject extends GameObject implements Collidable {
	protected Drawable d;
	protected boolean collidable;
	protected ArrayList<Drawable> ds;
	protected ArrayList<String> sounds;
	protected ArrayList<Shape> ss;
	protected ArrayList<float[]> relatives;
	
	public abstract boolean checkCollision (GameObject go);
	public abstract float[] getSize();
	public abstract boolean isCollidable();
	public abstract void addAnimation(Drawable d, int id);
	public abstract void addSound(String key, int id);
	public abstract ArrayList<Shape> getHitBoxes();
	public abstract void addHitbox(Shape s);
	public abstract void addHitbox();
	public abstract float[] getRelatives(int n);
	public abstract void addRelative(float[] r);
	public abstract void setRelatives(ArrayList<float[]> rel);
	public abstract void setHitboxes(ArrayList<Shape> ss);
	
	public boolean collides(Shape s, Shape s2) {
		if (s.intersects(s2) || s.contains(s2)) {
			return true;
		} else {
			return false;
		}
	}
}
