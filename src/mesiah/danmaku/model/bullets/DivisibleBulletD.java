package mesiah.danmaku.model.bullets;


import java.util.ArrayList;


import mesiah.danmaku.Play;
import mesiah.danmaku.model.BulletEmitter;
import mesiah.danmaku.model.patterns.FirePattern;
import mesiah.danmaku.model.patterns.FirePatternManager;

public class DivisibleBulletD extends BulletDecorator implements BulletEmitter {
	private ArrayList<String> fps;
	protected FirePattern parent;
	protected int lifeTime;
	protected int depth;
	public int density = 5;
	protected float angleOffset;
	public static int INFINITE = -1000;
	
	public DivisibleBulletD (Shootable b) {
		super(b);
		fps = new ArrayList<String>();
		super.setCanMove(true);
		lifeTime = 0;
		depth = 0;
		angleOffset = 0;
	}
	
	public int getDensity() {
		return density;
	}
	
	public int getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(int lifeTime) {
		this.lifeTime = lifeTime;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public float getAngleOffset() {
		return angleOffset;
	}

	public void setAngleOffset(float angleOffset) {
		this.angleOffset = angleOffset;
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
		FirePattern fp;
		for (int i = 0; i < density; i++) {
			for (String id : fps) {
				fp = FirePatternManager.get().compose(id, this);
				if (angleOffset != Float.valueOf(-1)) {
					fp.changeDirection(360/density*i + angleOffset);
				} else {
					float random = (float) (Math.random()*360);
					fp.changeDirection(random);
				}
				Play.bc.addToAdd(fp);
			}
		}		
	}
	
	public void update(int delta) {
		if (lifeTime <= 0 && lifeTime != INFINITE && super.getState() == "active") {
			shot(delta);
			super.addToRemove(this);
			collidable = false;
			super.setState("dead");
		} else {
			if (lifeTime != INFINITE && super.getDelay() <= 0) {
				lifeTime -= delta;
			}
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

	public void addPattern(String id) {
		fps.add(id);
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

	public void setDensity(int density) {
		this.density = density;
	}
	
	public boolean isAlly() {
		return super.isAlly();
	}

}
