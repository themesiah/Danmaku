package mesiah.danmaku.model;

import org.newdawn.slick.SlickException;

import mesiah.danmaku.view.Drawable;
import mesiah.danmaku.view.Sprite;

public class Template extends VisibleGameObject {
	Sprite s;
	
	public Template() throws SlickException {
		s = new Sprite("res/img/template.png");
		posx = 0;
		posy = 0;
	}
	
	public void addAnimation(Drawable d, int id) {
		
	}
	
	public boolean isCollidable() {
		return false;
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

	}

	public void move() {
		
	}

	public void draw() {
		s.draw(posx, posy);
	}

	public boolean checkCollision(GameObject go) {
		return false;
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

	public void addSound(String key, int id) {
		
	}
}