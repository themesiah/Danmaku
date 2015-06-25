package mesiah.danmaku.model.bullets;

import mesiah.danmaku.model.VisibleGameObject;
import mesiah.danmaku.model.patterns.FirePattern;

public abstract class BulletDecorator extends VisibleGameObject implements Shootable {
	protected Shootable b;
	protected FirePattern parent;
	
	public BulletDecorator(Shootable b) {
		this.b = b;
	}
	
	public Shootable getBullet() {
		return b;
	}

	public void CheckEnemyCollisions() {
		b.CheckEnemyCollisions();
	}

	public void CheckPlayerCollisions() {
		b.CheckPlayerCollisions();
	}

	public void CheckPowerupCollisions() {
		b.CheckPowerupCollisions();
	}

	public void CheckBulletCollisions() {
		b.CheckBulletCollisions();
	}

	public void update(int delta) {
		b.update(delta);
	}

	public void move(int delta) {
		b.move(delta);
	}
	
	public void draw() {
		b.draw();
	}
	
	public float[] getSize() {
		return b.getSize();
	}
	
	public void setDelay(int delay) {
		b.setDelay(delay);
	}
	
	public void setPosX(float x) {
		b.setPosX(x);
	}
	
	public void setPosY(float y) {
		b.setPosY(y);
	}
	
	public void setSpeed(float s) {
		b.setSpeed(s);
	}
	
	public void setParent(FirePattern fp) {
		b.setParent(fp);
	}
	
	public void setDirection(float d) {
		b.setDirection(d);
	}
	
	public void setCanMove(boolean cm) {
		b.setCanMove(cm);
	}
	
	public void setState(String s) {
		b.setState(s);
	}
	
	public int getDelay() {
		return b.getDelay();
	}
	
	public String getState() {
		return b.getState();
	}
	
	public float getPosX() {
		return b.getPosX();
	}
	
	public float getPosY() {
		return b.getPosY();
	}
	
	public float getSpeed() {
		return b.getSpeed();
	}
	
	public void addToRemove(Shootable s) {
		b.addToRemove(s);
	}
	
	public float getDirection() {
		return b.getDirection();
	}
	
	public boolean isAlly() {
		return b.isAlly();
	}

}
