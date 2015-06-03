package mesiah.danmaku.model;

import java.util.ArrayList;

import org.newdawn.slick.geom.Shape;

import mesiah.danmaku.view.Drawable;

public class Powerup extends VisibleGameObject {

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
	
	@Override
	public void CheckEnemyCollisions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void CheckPlayerCollisions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void CheckBulletCollisions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void CheckPowerupCollisions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(int delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
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

	@Override
	public void addSound(String key, int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Shape> getHitBoxes() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addHitbox(Shape s) {
		s.setX(posx + s.getX());
		s.setY(posy + s.getY());
		ss.add(s);
	}

	@Override
	public void addHitbox() {
		// TODO Auto-generated method stub
		
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

}
