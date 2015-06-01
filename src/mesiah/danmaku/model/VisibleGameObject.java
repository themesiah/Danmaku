package mesiah.danmaku.model;

import java.util.ArrayList;

import org.newdawn.slick.geom.Shape;

import mesiah.danmaku.view.Drawable;

public abstract class VisibleGameObject extends GameObject implements Collidable {
	protected Drawable d;
	protected boolean collidable;
	protected ArrayList<Drawable> ds;
	protected ArrayList<String> sounds;
	
	public abstract boolean checkCollision (GameObject go);
	public abstract float[] getSize();
	public abstract boolean isCollidable();
	public abstract void addAnimation(Drawable d, int id);
	public abstract void addSound(String key, int id);
	public abstract Shape[] getHitBoxes();
}
