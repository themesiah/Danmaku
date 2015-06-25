package mesiah.danmaku.model.bullets;

import mesiah.danmaku.model.patterns.FirePattern;
import mesiah.danmaku.util.GetDirection;
import mesiah.danmaku.util.Signals;

public class TrickyBulletD extends BulletDecorator {
	private int beforeTime;
	private String secondaryDirection;
	private String secondarySpeed;
	boolean changed;
	public static int PATTERNBINDVALUE = -1000;
	private String patternKey;

	public TrickyBulletD(Shootable b) {
		super(b);
		beforeTime = 0;
		secondaryDirection = "0";
		secondarySpeed = "1";
		changed = false;
		patternKey = "";
	}
	
	public String getPatternKey() {
		return patternKey;
	}

	public void setPatternKey(String patternKey) {
		this.patternKey = patternKey;
	}

	public String getSecondaryDirection() {
		return secondaryDirection;
	}

	public void setSecondaryDirection(String secondaryDirection) {
		this.secondaryDirection = secondaryDirection;
	}

	public String getSecondarySpeed() {
		return secondarySpeed;
	}

	public void setSecondarySpeed(String secondarySpeed) {
		this.secondarySpeed = secondarySpeed;
	}

	public int getBeforeTime() {
		return beforeTime;
	}

	public void setBeforeTime(int beforeTime) {
		this.beforeTime = beforeTime;
	}

	public void move(int delta) {
		if (super.getDelay() <= 0) {
			
		}
	}
	
	public void addToRemove(Shootable s) {
		super.addToRemove(s);
	}
	
	public String getState() {
		return super.getState();
	}
	
	public void shot(int delta) {
	
	}
	
	public void change() {
		float d = 0;
		float s = 0;
		if (secondaryDirection.equals("player")) {
			d = GetDirection.getDirectionToPlayer(getPosX(), getPosY());
		} else if (secondaryDirection.equals("random")) {
			d = (float) (Math.random()*360.0f);
		} else {
			d = Float.valueOf(secondaryDirection);
		}
		
		s = Float.valueOf(secondarySpeed);
		this.setDirection(d);
		this.setSpeed(s);
	}
	
	public void update(int delta) {
		if (beforeTime <= 0 && beforeTime != PATTERNBINDVALUE) {
			if (!changed) {
				change();
				changed = true;
			}
		} else if (beforeTime == PATTERNBINDVALUE) {
			Signals s = Signals.getSignals();
			if (!changed && s.getIntegerSignal(patternKey) <= 0) {
				change();
				changed = true;
			}
		} else {
			beforeTime -= delta;
		}
		super.update(delta);
		//move(delta);
		if (super.getState() == "dead") {
			super.addToRemove(this);
		}
	}
	
	public void setState(String s) {
		super.setState(s);
	}

	public void draw() {
		super.draw();
	}
	
	public float[] getSize() {
		return super.getSize();
	}
	
	public FirePattern getParent() {
		return parent;
	}
	
	public void setParent(FirePattern fp) {
		super.setParent(fp);
	}
	
	public void setPosX(float x) {
		super.setPosX(x);
	}
	
	public void setPosY(float y) {
		super.setPosY(y);
	}
	
	public void setDirection(float d) {
		super.setDirection(d);
	}
	
	public void setDelay(int delay) {
		super.setDelay(delay);
	}
	
	public int getDelay() {
		return super.getDelay();
	}
	
	public float getPosX() {
		return super.getPosX();
	}
	
	public float getPosY() {
		return super.getPosY();
	}
	
	public float getSpeed() {
		return super.getSpeed();
	}
	
	public float getDirection() {
		return super.getDirection();
	}


}
