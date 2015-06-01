package mesiah.danmaku.model;

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
	public boolean checkCollision(GameObject go) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move() {
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

}
