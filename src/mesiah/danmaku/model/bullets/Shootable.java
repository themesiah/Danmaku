package mesiah.danmaku.model.bullets;

import mesiah.danmaku.model.patterns.FirePattern;

public interface Shootable {
	public void CheckEnemyCollisions();
	public void CheckPlayerCollisions();
	public void CheckPowerupCollisions();
	public void CheckBulletCollisions();
	public void update(int delta);
	public void move(int delta);
	public void draw();
	public FirePattern getParent();
	public float[] getSize();
	public void setParent(FirePattern fp);
	public void setDelay(int delay);
	public void setPosX(float x);
	public void setPosY(float y);
	public void setSpeed(float s);
	public void setCanMove(boolean cm);
	public void setDirection(float d);
	public void setState(String s);
	
	public int getDelay();
	public float getPosX();
	public float getPosY();
	public float getSpeed();
	public float getDirection();
	
	public String getState();
	
	public void addToRemove(Shootable s);
}
