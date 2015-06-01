package mesiah.danmaku.model;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

import mesiah.danmaku.Main;
import mesiah.danmaku.view.Drawable;
import mesiah.danmaku.view.Sprite;

public class Background extends VisibleGameObject {
	Sprite s;
	
	public Background() throws SlickException {
		s = new Sprite("res/img/bg.jpg");
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
		s.draw(Main.GAMEWIDTH/4, posy, Main.GAMEWIDTH*3/4, Main.GAMEHEIGHT, 0, 0, Main.GAMEWIDTH/2, Main.GAMEHEIGHT);
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

	public ArrayList<Shape> getHitBoxes() {
		return null;
	}

	public void addHitbox(Shape s) {
		
	}

	public void addHitbox() {
		
	}

	public float[] getRelatives(int n) {
		return null;
	}

	public void addRelative(float[] r) {
		
	}
	
	public void setRelatives(ArrayList<float[]> rel) {
		this.relatives = rel;
	}

	public void setHitboxes(ArrayList<Shape> ss) {
		this.ss = ss;
	}
}
