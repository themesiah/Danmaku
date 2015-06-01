package mesiah.danmaku.model;

import org.newdawn.slick.SlickException;

public class GameObjectContainer {
	PlayerContainer pc;
	BulletContainer bc;
	EnemyContainer ec;
	PowerupContainer puc;
	Background bg;
	Template template;
	
	public GameObjectContainer(PlayerContainer pc, BulletContainer bc, EnemyContainer ec, PowerupContainer puc) throws SlickException {
		this.pc = pc;
		this.bc = bc;
		this.ec = ec;
		this.puc = puc;
		this.bg = new Background();
		this.template = new Template();
	}
	
	public void update(int delta) {
		pc.update(delta);
		bc.update(delta);
		ec.update(delta);
		puc.update(delta);
	}
	
	public void draw() {
		bg.draw();
		pc.draw();
		ec.draw();
		puc.draw();
		bc.draw();
		template.draw();
	}
	
	
}
