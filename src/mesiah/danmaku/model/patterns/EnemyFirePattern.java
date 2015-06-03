package mesiah.danmaku.model.patterns;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import mesiah.danmaku.model.VisibleGameObject;
import mesiah.danmaku.model.bullets.Bullet;
import mesiah.danmaku.model.bullets.Shootable;
import mesiah.danmaku.util.GetDirection;

public class EnemyFirePattern extends FirePattern {
	ArrayList<Shootable> gol;
	ArrayList<Shootable> toRemove;
	
	public EnemyFirePattern(VisibleGameObject parent) {
		super(parent);
		gol = new ArrayList<Shootable>();
		toRemove = new ArrayList<Shootable>();
		
		try {
			posx += parent.getSize()[0]/2;
			posy += parent.getSize()[1];
			Bullet b = new Bullet(posx, posy, false, "enemybullet");
			
			b.setDirection(GetDirection.getDirectionToPlayer(posx, posy));
			add((Shootable) b);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
