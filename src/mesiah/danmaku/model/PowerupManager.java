package mesiah.danmaku.model;

import java.util.HashMap;

public class PowerupManager {
	HashMap<String, CustomPowerup> pu;
	private static PowerupManager pum = null;

	public PowerupManager() {
		pu = new HashMap<String, CustomPowerup>();
	}
	
	public static PowerupManager get() {
		if (pum == null) {
			pum = new PowerupManager();
		}
		return pum;
	}
	
	public CustomPowerup getPowerup(String key) {
		return pu.get(key);
	}
	
	public void addPowerup(CustomPowerup c) {
		this.pu.put(c.getId(), c);
	}
	
	public Powerup composer(String key, VisibleGameObject parent) {
		Powerup p = null;
		CustomPowerup cp = getPowerup(key);
		float posx, posy;
		posx = (float) (parent.getPosX() + Math.random()*parent.getSize()[0]);
		posy = (float) (parent.getPosY() + Math.random()*parent.getSize()[1]);
		p = new Powerup(posx, posy, cp.getType(), cp.getValue(), cp.getAnimation());
		p.setSpeed(cp.getSpeed());
		if (!cp.getDirection().equals("")) {
			if (cp.getDirection().equals("random")) {
				float r = (float) (Math.random()*360.0f);
				p.setDirection(r);
			} else if (cp.getDirection().equals("gravity")) {
				p.setDirection(270.0f);
			} else {
				p.setDirection(Float.valueOf(cp.getDirection()));
			}
		}
		p.setBounce(cp.isBounce());
		return p;
	}
}
