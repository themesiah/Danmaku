package mesiah.danmaku.model;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

import mesiah.danmaku.Main;
import mesiah.danmaku.view.AnimationManager;
import mesiah.danmaku.view.Drawable;

public class Background extends VisibleGameObject {
	Drawable d;
	
	/**
	 * Carga un fondo a partir del nombre de la animación.
	 * @param aniKey Nombre de la animación en el hashmap de animaciones.
	 * @throws SlickException
	 */
	public Background(String aniKey) throws SlickException {
		d = AnimationManager.get().getAnimation(aniKey);
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

	public void move(int delta) {
		
	}

	/**
	 * Dibuja el fondo ocupando toda la pantalla.
	 */
	public void draw() {
		d.draw(Main.GAMEWIDTH/4, posy, Main.GAMEWIDTH*3/4, Main.GAMEHEIGHT, 0, 0, Main.GAMEWIDTH/2, Main.GAMEHEIGHT);
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
