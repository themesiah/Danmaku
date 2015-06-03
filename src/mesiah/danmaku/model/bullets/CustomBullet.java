package mesiah.danmaku.model.bullets;

import java.util.ArrayList;

import mesiah.danmaku.view.AnimationManager;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;


public class CustomBullet {
	// Normal
	protected String posx, posy, speed, direction, delay, damage, animation, type, ally;
	protected ArrayList<String> properties;
	protected ArrayList<Shape> ss;
	protected ArrayList<float[]> relatives;
	
	// Divisible
	protected ArrayList<String> fps;
	protected String lifeTime, density, angleOffSet;
	
	// Curve
	protected ArrayList<String> curves;
	protected ArrayList<Integer> curveTimes;
	protected String onlyCurve;
	

	public CustomBullet() {
		properties = new ArrayList<String>();
		fps = new ArrayList<String>();
		curves = new ArrayList<String>();
		curveTimes = new ArrayList<Integer>();
		ss = new ArrayList<Shape>();
		relatives = new ArrayList<float[]>();
	}


	public String getPosx() {
		return posx;
	}


	public void setPosx(String posx) {
		this.posx = posx;
	}


	public String getPosy() {
		return posy;
	}


	public void setPosy(String posy) {
		this.posy = posy;
	}


	public String getSpeed() {
		return speed;
	}


	public void setSpeed(String speed) {
		this.speed = speed;
	}


	public String getDirection() {
		return direction;
	}


	public void setDirection(String direction) {
		this.direction = direction;
	}


	public String getDelay() {
		return delay;
	}


	public void setDelay(String delay) {
		this.delay = delay;
	}


	public String getDamage() {
		return damage;
	}


	public void setDamage(String damage) {
		this.damage = damage;
	}


	public String getAnimation() {
		return animation;
	}


	public void setAnimation(String animation) {
		this.animation = animation;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public ArrayList<String> getProperties() {
		return properties;
	}


	public void setProperties(ArrayList<String> properties) {
		this.properties = properties;
	}
	
	public void addProperty(String p) {
		this.properties.add(p);
	}
	
	public String getProperty(int n) {
		return this.properties.get(n);
	}
	
	public boolean haveProperty(String p) {
		return this.properties.contains(p);
	}


	public ArrayList<String> getFps() {
		return fps;
	}


	public void setFps(ArrayList<String> fps) {
		this.fps = fps;
	}


	public String getLifeTime() {
		return lifeTime;
	}


	public void setLifeTime(String lifeTime) {
		this.lifeTime = lifeTime;
	}


	public String getDensity() {
		return density;
	}


	public void setDensity(String density) {
		this.density = density;
	}


	public String getAngleOffSet() {
		return angleOffSet;
	}


	public void setAngleOffSet(String angleOffSet) {
		this.angleOffSet = angleOffSet;
	}


	public ArrayList<String> getCurves() {
		return curves;
	}


	public void setCurves(ArrayList<String> curves) {
		this.curves = curves;
	}


	public ArrayList<Integer> getCurveTimes() {
		return curveTimes;
	}


	public void setCurveTimes(ArrayList<Integer> curveTimes) {
		this.curveTimes = curveTimes;
	}


	public String getOnlyCurve() {
		return onlyCurve;
	}


	public void setOnlyCurve(String onlyCurve) {
		this.onlyCurve = onlyCurve;
	}
	
	
	public void addHitbox(Shape s) {
		float[] rel = {s.getX(), s.getY()};
		addRelative(rel);
		ss.add(s);
	}
	
	public float[] getSize() {
		return AnimationManager.get().getAnimation(animation).getSize();
	}
	
	
	public void addHitbox() {
		Rectangle r = new Rectangle(0, 0, getSize()[0], getSize()[1]);
		float[] rel = {0.0f, 0.0f};
		addRelative(rel);
		ss.add(r);
	}
	
	public float[] getRelatives(int n) {
		return relatives.get(n);
	}

	public void addRelative(float[] r) {
		relatives.add(r);
	}


	public String getAlly() {
		return ally;
	}


	public void setAlly(String ally) {
		this.ally = ally;
	}
	
	public ArrayList<Shape> getHitboxes() {
		return ss;
	}
	
	public ArrayList<float[]> getRelatives() {
		return relatives;
	}
	
	public void addFirePattern(String fp) {
		fps.add(fp);
	}
	
	public String getFirePattern(int n) {
		return fps.get(n);
	}
	
	public int firePatternsSize() {
		return fps.size();
	}

}
