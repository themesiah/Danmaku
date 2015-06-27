package mesiah.danmaku.model;

/**
 * Clase base para todos los objetos del juego.
 * Tiene posición, velocidad, dirección...
 * @author Mesiah
 *
 */
public abstract class GameObject {
	public static final int PLAYER = 0;
	public static final int ENEMY = 1;
	public static final int BULLET = 2;
	public static final int POWERUP = 3;
	
	public static final int PRIO_BACKGROUND = 0;
	public static final int PRIO_PARALLAX = 1;
	public static final int PRIO_PLAYER = 2;
	public static final int PRIO_ENEMY = 3;
	public static final int PRIO_POWERUP = 4;
	public static final int PRIO_BULLET = 5;
	public static final int MAX_PRIO = PRIO_BULLET;
	
	protected float posx;
	protected float posy;
	protected float direction;
	protected float facing;
	protected float speed;
	protected String state;
	
	public abstract void update(int delta);
	
	public abstract void move(int delta);
	
	public abstract void draw();
	
	public float getPosX() {
		return posx;
	}
	
	public float getPosY() {
		return posy;
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
	
	public void setPosX(float x) {
		posx = x;
	}
	
	public void setPosY(float y) {
		posy = y;
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
}
