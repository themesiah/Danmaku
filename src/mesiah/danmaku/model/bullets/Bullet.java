package mesiah.danmaku.model.bullets;

import java.util.ArrayList;
import java.util.List;

import mesiah.danmaku.Main;
import mesiah.danmaku.Play;
import mesiah.danmaku.audio.AudioManager;
import mesiah.danmaku.model.Boss;
import mesiah.danmaku.model.Enemy;
import mesiah.danmaku.model.GameObject;
import mesiah.danmaku.model.Player;
import mesiah.danmaku.model.VisibleGameObject;
import mesiah.danmaku.model.patterns.FirePattern;
import mesiah.danmaku.util.GetDirection;
import mesiah.danmaku.view.AnimationManager;
import mesiah.danmaku.view.Drawable;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

public class Bullet extends VisibleGameObject implements Shootable {
	protected boolean ally;
	protected int damage;
	protected int delay;
	protected boolean grazed;
	public static int ACTIVE = 0;
	public static int DESTROYED = 1;
	protected FirePattern parent;
	protected float acceleration;
	protected float maxSpeed;
	protected float minSpeed;
	protected boolean wasRelative;
	protected String sound;
	protected String relativeDirection;
	
	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}
	
	public String getShotSound() {
		return sound;
	}



	public Bullet(float x, float y, boolean ally, String key) throws SlickException {
		initBullet(x, y, key);
		this.ally = ally;
		posx -= getSize()[0]/2;
		posy -= getSize()[1]/2;
		damage = 10;
		delay = 0;
		state = "active";
		grazed = false;
		canMove = true;
		addHitbox();
		acceleration = 0.0f;
		maxSpeed = 1000.0f;
		minSpeed = -1000.0f;
		relativeDirection = "";
	}
	
	
	
	public boolean isWasRelative() {
		return wasRelative;
	}



	public void setWasRelative(boolean wasRelative) {
		this.wasRelative = wasRelative;
	}



	public void setDelay(int d) {
		delay = d;
	}
	
	public boolean isCollidable() {
		return collidable;
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
	
	private void initBullet(float x, float y, String key) throws SlickException {
		ds = new ArrayList<Drawable>();
		sounds = new ArrayList<String>();
		ss = new ArrayList<Shape>();
		relatives = new ArrayList<float[]>();
		for (int i = 0; i < DESTROYED+1; i++) {
			ds.add(null);
			sounds.add(null);
		}
		ds.set(ACTIVE, AnimationManager.get().getAnimation(key));
		d = ds.get(ACTIVE);
		posx = x;
		posy = y;
		direction = 90.0f;
		speed = 10;
		collidable = true;
		wasRelative = false;
	}
	
	public ArrayList<Shape> getHitBoxes() {
		for (int i = 0; i < ss.size(); i++) {
			Shape shape = ss.get(i);
			float[] rel = getRelatives(i);
			shape.setX(posx+rel[0]);
			shape.setY(posy+rel[1]);
			ss.set(i, shape);
		}
		return ss;
	}
	
	public void onDestroyed() {
		state = "dead";
		collidable = false;
		collidable = false;
		parent.addToRemove(this);
	}
	
	public void CheckBossCollisions() {
		if (this.collidable && ally) {
			List<Boss> blist = Play.bssc.getEnemies();
			for (Boss b: blist) {
				for (Shape s:getHitBoxes()) {
					for (Shape se:b.getHitBoxes()) {
						if (collides(s, se) && b.isCollidable()) {
							b.onHit(damage);
							onDestroyed();
						}
					}
				}
			}
		}
	}

	public void CheckEnemyCollisions() {
		if (this.collidable && ally) {
			List<Enemy> elist = Play.ec.getEnemies();
			for (Enemy e: elist) {
				for (Shape s:getHitBoxes()) {
					for (Shape se:e.getHitBoxes()) {
						if (collides(s, se) && e.isCollidable()) {
							e.onHit(damage);
							onDestroyed();
						}
					}
				}
			}
		}
	}

	public void CheckPlayerCollisions() {
		if (this.collidable && !ally) {
			List<Player> plist = Play.pc.getPlayers();
			for (Player p: plist) {
				if (p.isCollidable()) {
					for (Shape s : getHitBoxes()) {
						for (Shape sp : p.getHitBoxes()) {
							if (collides(s, sp)) {
								p.onHit(damage);
								onDestroyed();
							} else if (Play.CANGRAZE) {
								if (collides(s, p.getGrazeHitBox()) && p.isCollidable() && !grazed) {
									grazed = true;
									Player.GRAZE += 1;
								}
							}
						}
					}
				}
			}
		}
	}

	public void CheckBulletCollisions() {

	}

	public void CheckPowerupCollisions() {

	}

	public boolean checkCollision(GameObject go) {
		return false;
	}
	
	public void addToRemove(Shootable s) {
		parent.addToRemove(s);
	}

	public void update(int delta) {
		if (state != "dead") {
			if (delay <= 0) {
				speed += acceleration*( (float) delta / 1000.0f);
				if (speed > maxSpeed) {
					speed = maxSpeed;
				}
				if (speed < minSpeed) {
					speed = minSpeed;
				}
				CheckEnemyCollisions();
				CheckPlayerCollisions();
				CheckBossCollisions();
				if (posx > Main.LIMITRIGHT+50 || posx < Main.LIMITLEFT-50 || posy < Main.LIMITTOP-50 || posy > Main.LIMITBOTTOM+50) {
					state = "dead";
					parent.addToRemove(this);
				}
				move(delta);
			} else {
				state = "not";
				delay -= delta;
				if (delay <= 0) {
					if (parent.parentActive()) {
						state = "active";
						AudioManager.get().playSound(sound);
					}
				}
				if (wasRelative) {
					posx = parent.getParentPosX();
					posy = parent.getParentPosY();
				}
				if (relativeDirection.equals("player")) {
					direction = GetDirection.getDirectionToPlayer(posx+getSize()[0]/2, posy+getSize()[1]/2);
				}
			}
		}
	}

	public void move(int delta) {
		if (canMove && state == "active") {
			float movx = (float) (Math.cos(Math.toRadians(direction))*speed);
			float movy = (float) (Math.sin(Math.toRadians(direction))*speed);
			posx += movx;
			posy -= movy;
		}
	}

	public void draw() {
		if (state == "active") {
			d.draw(posx, posy);
		}
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

	public float[] getSize() {
		return d.getSize();
	}
	
	public FirePattern getParent() {
		return parent;
	}
	
	public void setParent(FirePattern fp) {
		parent = fp;
	}
	
	public void setCanMove(boolean cm) {
		canMove = cm;
	}
	
	public int getDelay() {
		return delay;
	}
	
	public void setDamage(int d) {
		damage = d;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public boolean isAlly() {
		return ally;
	}



	public float getAcceleration() {
		return acceleration;
	}



	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}



	public float getMaxSpeed() {
		return maxSpeed;
	}



	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}



	public float getMinSpeed() {
		return minSpeed;
	}



	public void setMinSpeed(float minSpeed) {
		this.minSpeed = minSpeed;
	}

	public String getRelativeDirection() {
		return relativeDirection;
	}

	public void setRelativeDirection(String relativeDirection) {
		this.relativeDirection = relativeDirection;
	}
	

}
