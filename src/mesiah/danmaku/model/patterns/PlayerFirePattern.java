package mesiah.danmaku.model.patterns;

import org.newdawn.slick.SlickException;

import mesiah.danmaku.model.VisibleGameObject;
import mesiah.danmaku.model.bullets.Bullet;
import mesiah.danmaku.model.bullets.Shootable;

public class PlayerFirePattern extends FirePattern {
	
	public PlayerFirePattern(VisibleGameObject parent) {
		super(parent);
		
		try {
			add((Shootable) new Bullet(posx+37, posy-12, true, "playerbullet"));
			add((Shootable) new Bullet(posx+12, posy-12, true, "playerbullet"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}
}
