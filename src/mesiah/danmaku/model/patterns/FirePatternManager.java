package mesiah.danmaku.model.patterns;

import java.util.HashMap;

import org.newdawn.slick.SlickException;

import mesiah.danmaku.model.VisibleGameObject;
import mesiah.danmaku.model.bullets.Bullet;
import mesiah.danmaku.model.bullets.CustomBullet;
import mesiah.danmaku.model.bullets.Shootable;

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
			
			for (int i = 0; i < cfp.size(); i++) {
				CustomBullet cb = cfp.getCustomBullet(i);
				float posx = 0;
				float posy = 0;
				boolean ally = false;
				String anim = cb.getAnimation();
				String[] parts = cb.getPosx().split("\\+");
				for (int j = 0; j < parts.length; j++) {
					switch(parts[j]) {
						case "parent":
							posx += parent.getPosX() + parent.getSize()[0]/2;
							break;
						default:
							posx += Float.valueOf(parts[j]);
							break;
						}
				}
				parts = cb.getPosy().split("\\+");
				for (int j = 0; j < parts.length; j++) {
					switch(parts[j]) {
						case "parent":
							posy += parent.getPosY() + parent.getSize()[1]/2;
							break;
						default:
							posy += Float.valueOf(parts[j]);
							break;
						}
				}
				if (cb.getAlly().equals("true")) {
					ally = true;
				}
				Bullet b = new Bullet(posx, posy, ally, anim);
				b.setDelay(Integer.valueOf(cb.getDelay()));
				b.setDirection(Float.valueOf(cb.getDirection()));
				b.setParent(fp);
				b.setSpeed(Float.valueOf(cb.getSpeed()));
				b.setDamage(Integer.valueOf(cb.getDamage()));
				b.setHitboxes(cb.getHitboxes());
				b.setRelatives(cb.getRelatives());
				fp.add((Shootable) b);
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return fp;
	}

}
