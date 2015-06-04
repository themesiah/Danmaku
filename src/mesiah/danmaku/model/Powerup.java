package mesiah.danmaku.model;

import java.util.ArrayList;

import org.newdawn.slick.geom.Shape;

import mesiah.danmaku.Main;
import mesiah.danmaku.Play;
import mesiah.danmaku.view.AnimationManager;
import mesiah.danmaku.view.Drawable;

public class Powerup extends VisibleGameObject {
	public static int ACTIVE = 0;
	public static int DESTROYED = 1;
	public static int TYPE_POWER = 0;
	public static int TYPE_POINTS = 1;
	public static int TYPE_LIFE = 2;
	public static int TYPE_BOMB = 3;
	private int type;
	private int value;
	private boolean bounce;
	
	public Powerup(float x, float y, int type, int value, String key) {
		posx = x;
		posy = y;
		this.type = type;
		ds = new ArrayList<Drawable>();
		sounds = new ArrayList<String>();
		ss = new ArrayList<Shape>();
		relatives = new ArrayList<float[]>();
		for (int i = 0; i < DESTROYED+1; i++) {
			ds.add(null);
			sounds.add(null);
		}
		ds.set(ACTIVE, AnimationManager.get().getAnimation(key));
		d = ds.get(ACTIVE);
		collidable = true;
		state = "active";
		this.value = value;
		direction = (float) (Math.random()*360.0f);
		bounce = false;
		addHitbox();
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isCollidable() {
		return collidable;
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
	
	public void CheckEnemyCollisions() {
		
	}

	public void CheckPlayerCollisions() {
		
	}

	public void CheckBulletCollisions() {
		
	}

	public void CheckPowerupCollisions() {
		
	}

	public void update(int delta) {
		if(posx < Main.LIMITLEFT || posx+getSize()[0] > Main.LIMITRIGHT) {
			if (bounce) {
				direction = 180.0f - direction;
			} else {
				if (posx > Main.LIMITRIGHT) {
					Play.puc.addToRemove(this);
				}
			}
		}
		if(posy < Main.LIMITTOP || posy+getSize()[1] > Main.LIMITBOTTOM) {
			if (bounce) {
				direction = 360.0f - direction;
			} else {
				if (posy > Main.LIMITBOTTOM) {
					Play.puc.addToRemove(this);
				}
			}
		}
		move(delta);
	}

	public void move(int delta) {
		if (state == "active") {
			float movx = (float) (Math.cos(Math.toRadians(direction))*speed);
			float movy = (float) (Math.sin(Math.toRadians(direction))*speed);
			posx += movx;
			posy -= movy;
		}
	}

	public void draw() {
		if (state == "active") {
			d.draw(posx, posy);
		}		
	}
	
	public float[] getSize() {
		return d.getSize();
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
	
	public void addAnimation(Drawable d, int id) {
		ds.set(id, d);
	}

	public void addHitbox(Shape s) {
		s.setX(posx + s.getX());
		s.setY(posy + s.getY());
		ss.add(s);
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

	public boolean isBounce() {
		return bounce;
	}

	public void setBounce(boolean bounce) {
		this.bounce = bounce;
	}

}
