package mesiah.danmaku.model.patterns;

import java.util.HashMap;

import org.newdawn.slick.SlickException;

import mesiah.danmaku.Main;
import mesiah.danmaku.model.VisibleGameObject;
import mesiah.danmaku.model.bullets.Bullet;
import mesiah.danmaku.model.bullets.CurveBulletD;
import mesiah.danmaku.model.bullets.CustomBullet;
import mesiah.danmaku.model.bullets.DivisibleBulletD;
import mesiah.danmaku.model.bullets.Shootable;
import mesiah.danmaku.model.bullets.TrickyBulletD;
import mesiah.danmaku.util.CurveManager;
import mesiah.danmaku.util.GetDirection;
import mesiah.danmaku.util.Signals;

public class FirePatternManager {

	public FirePatternManager() {
		cfp = new HashMap<String, CustomFirePattern>();
	}
	
	private static FirePatternManager fpm = null;
	private HashMap<String, CustomFirePattern> cfp;
	
	public static FirePatternManager get() {
		if (fpm == null) {
			fpm = new FirePatternManager();
		}
		return fpm;
	}
	
	public CustomFirePattern getFirePattern(String key) {
		return cfp.get(key);
	}
	
	public void addFirePattern(CustomFirePattern c) {
		this.cfp.put(c.getId(), c);
	}
	
	public FirePattern compose(String key, VisibleGameObject parent) {
		FirePattern fp = new BasicFirePattern(parent);
		try {
			CustomFirePattern cfp = getFirePattern(key);
			boolean wasRelative = false;
			float random = (float) (Math.random()*360);
			for (int i = 0; i < cfp.size(); i++) {
				CustomBullet cb = cfp.getCustomBullet(i);
				float posx = 0;
				float posy = 0;
				boolean ally = false;
				String anim = cb.getAnimation();
				if (!cb.getPosx().equals("")) {
					String[] parts = cb.getPosx().split("\\+");
					for (int j = 0; j < parts.length; j++) {
						switch(parts[j]) {
							case "parent":
								wasRelative = true;
								posx += parent.getPosX() + parent.getSize()[0]/2;
								break;
							default:
								if (parts[j].contains("%")) {
									posx += (Float.valueOf(parts[j].split("%")[0])/100.0f*(Main.LIMITRIGHT - Main.LIMITLEFT) + Main.LIMITLEFT);
								} else {
									posx += Float.valueOf(parts[j]);
								}
								break;
							}
					}
				}
				if (!cb.getPosy().equals("")) {
					String[] parts = cb.getPosy().split("\\+");
					for (int j = 0; j < parts.length; j++) {
						switch(parts[j]) {
							case "parent":
								wasRelative = true;
								posy += parent.getPosY() + parent.getSize()[1]/2;
								break;
							default:
								if (parts[j].contains("%")) {
									posy += (Float.valueOf(parts[j].split("%")[0])/100.0f*(Main.LIMITBOTTOM - Main.LIMITTOP) + Main.LIMITTOP);
								} else {
									posy += Float.valueOf(parts[j]);
								}
								break;
							}
					}
				}
				if (cb.getAlly().equals("true")) {
					ally = true;
				}
				Shootable s;
				Bullet b = new Bullet(posx, posy, ally, anim);
				b.setWasRelative(wasRelative);
				b.setSound(parent.getShotSound());
				b.setPosX(b.getPosX() + b.getSize()[0]/2);
				b.setPosY(b.getPosY() + b.getSize()[1]/2);
				s = b;
				b.setDelay(Integer.valueOf(cb.getDelay()));
				if (cb.getDirection().contains("player")) {
					b.setRelativeDirection("player");
					if (cb.getDirection().contains("\\+")) {
						String[] parts = cb.getDirection().split("\\+");
						float xOffset = 0;
						float yOffset = 0;
						for (int j = 0; j < parts.length; j++) {
							if (parts[j].contains("x")) {
								xOffset += Float.valueOf(parts[j].split("x")[0]);
							}
							if (parts[j].contains("y")) {
								yOffset += Float.valueOf(parts[j].split("y")[0]);
							}
						}
						b.setDirection(GetDirection.getDirectionToPlayer(posx, posy));
						posx += xOffset;
						posy += yOffset;
					} else {
						b.setDirection(GetDirection.getDirectionToPlayer(posx, posy));
					}
					
				} else if (cb.getDirection().equals("random")) {
					float random2 = (float) (Math.random()*360);
					b.setDirection(random2);
				} else {
					b.setDirection(Float.valueOf(cb.getDirection()));
				}
				
				b.setParent(fp);
				if (!cb.getSpeed().equals("")) {
					b.setSpeed(Float.valueOf(cb.getSpeed()));
				}
				if (!cb.getDamage().equals("")) {
					b.setDamage(Integer.valueOf(cb.getDamage()));
				}
				if (!cb.getAcceleration().equals("")) {
					b.setAcceleration(Float.valueOf(cb.getAcceleration()));
				}
				if (!cb.getMaxSpeed().equals("")) {
					b.setMaxSpeed(Float.valueOf(cb.getMaxSpeed()));
				}
				if (!cb.getMinSpeed().equals("")) {
					b.setMinSpeed(Float.valueOf(cb.getMinSpeed()));
				}
				b.setHitboxes(cb.getHitboxes());
				b.setRelatives(cb.getRelatives());
				
				// ------------------
				// Divisible bullets
				// ------------------
				if (cb.haveProperty("divisible")) {
					DivisibleBulletD db = new DivisibleBulletD(b);
					
					if (cb.getAngleOffSet().equals("random")) {
						db.setAngleOffset(random);
					} else if (cb.getAngleOffSet().equals("totalRandom")){
						db.setAngleOffset(-1);
					} else {
						db.setAngleOffset(Float.valueOf(cb.getAngleOffSet()));
					}
					
					if (cb.getLifeTime().equals("infinite")) {
						db.setLifeTime(DivisibleBulletD.INFINITE);
					} else {
						db.setLifeTime(Integer.valueOf(cb.getLifeTime()));
					}
					
					db.setDensity(Integer.valueOf(cb.getDensity()));
					for (int k = 0; k < cb.firePatternsSize(); k++) {
						db.addPattern(cb.getFirePattern(k));
					}
					s = db;
				}
				
				// ------------------
				// Curve bullets
				// ------------------
				if (cb.haveProperty("curve")) {
					CurveBulletD cbd = new CurveBulletD(s);
					s = cbd;
					
					if (cb.getOnlyCurve().equals("true")) {
						cbd.setOnlyCurve(true);
					} else {
						cbd.setOnlyCurve(false);
					}
					
					for (int k = 0; k < cb.getCurves().size(); k++) {
						cbd.add(CurveManager.get().compose(cb.getCurve(k), parent), cb.getCurveTime(k));
					}
					s.setPosX(cbd.getCurve(0).getPoint(0)[0]);
					s.setPosY(cbd.getCurve(0).getPoint(0)[1]);
				}
				
				// ------------------
				// Tricky bullets
				// ------------------
				if (cb.haveProperty("tricky")) {
					
					TrickyBulletD tbd = new TrickyBulletD(s);
					s = tbd;
					
					if (cb.getTrickyTime().contains("patternBind")) {
						String signalKey = cb.getTrickyTime().split(",")[2];
						tbd.setBeforeTime(TrickyBulletD.PATTERNBINDVALUE);
						tbd.setPatternKey(signalKey);
						Signals sign = Signals.getSignals();
						sign.addIntegerSignal(signalKey, Integer.valueOf(cb.getTrickyTime().split(",")[1]));
					} else if (cb.getTrickyTime().contains("absolute")) {
						tbd.setBeforeTime(Integer.valueOf(cb.getTrickyTime().split(",")[1]));
					}
					tbd.setSecondarySpeed(cb.getSecondarySpeed());
					
					tbd.setSecondaryDirection(cb.getSecondaryDirection());
				}
				
				fp.add((Shootable) s);
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return fp;
	}

}
