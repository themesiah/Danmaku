package mesiah.danmaku.model;

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
	
	public abstract void move();
	
	public abstract void draw();
	
	public abstract float getPosX();
	public abstract float getPosY();
	public abstract float getFacing();
	public abstract float getDirection();
	public abstract float getSpeed();
	public abstract String getState();
	public abstract void setPosX(float x);
	public abstract void setPosY(float y);
	public abstract void setFacing(float f);
	public abstract void setDirection(float dir);
	public abstract void setSpeed(float s);
	public abstract void setState(String s);
}
