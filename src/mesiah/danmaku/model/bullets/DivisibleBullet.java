package mesiah.danmaku.model.bullets;

import java.util.ArrayList;

import mesiah.danmaku.Main;
import mesiah.danmaku.Play;
import mesiah.danmaku.model.BulletEmitter;
import mesiah.danmaku.model.patterns.FirePattern;
import mesiah.danmaku.model.patterns.FirePatternsManager;

import org.newdawn.slick.SlickException;

public class DivisibleBullet extends Bullet implements BulletEmitter {
	private ArrayList<String> fps;
	int lifeTime;
	public static int density = 5;
	public static int INFINITE = -1000;
	int depth;
	float angleOffset;

	public DivisibleBullet(float x, float y, boolean ally, String key, int d) throws SlickException {
		super(x, y, ally, key);
		fps = new ArrayList<String>();
		lifeTime = 1000;
		speed = 3;
		depth = d;
		angleOffset = 0;
	}
	
	public DivisibleBullet(float x, float y, boolean ally, String key, int d, int lt) throws SlickException {
		super(x, y, ally, key);
		fps = new ArrayList<String>();
		lifeTime = lt;
		speed = 3;
		depth = d;
		angleOffset = 0;
	}
	
	public DivisibleBullet(float x, float y, boolean ally, String key, int d, int lt, float aos) throws SlickException {
		super(x, y, ally, key);
		fps = new ArrayList<String>();
		lifeTime = lt;
		speed = 3;
		depth = d;
		angleOffset = aos;
	}

	public void shot(int delta) {
		if (depth > 0) {
			FirePattern[] fp = new FirePattern[fps.size()];
			int i = 0;
			for (String id : fps) {
				fp[i] = FirePatternsManager.get().newPattern(id, posx, posy, this);
				i++;
			}
			for (i = 0; i < fp.length; i++) {
				Play.bc.addToAdd(fp[i]);
			}
			try {
				int newLifeTime;
				if (depth == 1) {
					newLifeTime = INFINITE;
				} else {
					newLifeTime = 1000;
				}
				float aos = (float) (Math.random()*360);
				for (i = 0; i < DivisibleBullet.density; i++) {
					String newbullet = "enemybullet";
					if (depth == 1) {
						newbullet = "enemybullet3";
					}
					DivisibleBullet b = new DivisibleBullet(posx, posy, false, newbullet, depth-1, newLifeTime, aos);
					b.setDirection(((360.0f / density) * i) + angleOffset);
					b.addPattern(FirePatternsManager.BASICFIREPATTERN);
					b.setSpeed(3);
					fp[0].add(b);
				}
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void update(int delta) {
		if (lifeTime <= 0 && lifeTime != INFINITE) {
			shot(delta);
			parent.addToRemove(this);
			collidable = false;
		} else {
			if (lifeTime != INFINITE) {
				lifeTime -= delta;
			}
			if (delay <= 0) {
				move();
				CheckEnemyCollisions();
				CheckPlayerCollisions();
				if (posx > Main.GAMEWIDTH+20 ||	posx < -20 || posy < -20 ||	posy > Main.GAMEHEIGHT+20) {
					parent.addToRemove(this);
				}
			} else {
				state = "not";
				delay -= delta;
				if (delay <= 0) {
					state = "active";
				}
			}
		}
	}

	public void addPattern(String id) {
		fps.add(id);
	}

}
