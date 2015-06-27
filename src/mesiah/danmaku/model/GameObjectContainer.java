package mesiah.danmaku.model;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class GameObjectContainer {
	PlayerContainer pc;
	BulletContainer bc;
	EnemyContainer ec;
	PowerupContainer puc;
	BossContainer bssc;
	Background bg;
	Template template;
	
	public GameObjectContainer(PlayerContainer pc, BulletContainer bc, EnemyContainer ec, PowerupContainer puc, BossContainer bssc) throws SlickException {
		this.pc = pc;
		this.bc = bc;
		this.ec = ec;
		this.puc = puc;
		this.bssc = bssc;
		//this.bg = new Background("bg.jpg");
		//this.template = new Template("template.png");
	}
	
	public void update(int delta) {
		pc.update(delta);
		bc.update(delta);
		ec.update(delta);
		bssc.update(delta);
		puc.update(delta);
	}
	
	public void draw(Graphics g) {
		bg.draw();
		pc.draw();
		ec.draw();
		puc.draw();
		bc.draw();
		bssc.draw(g);
		template.draw();
	}
	
	public void setBg(String bg) {
		this.bg = BackgroundManager.get().getBackground(bg);
	}
	
	public void setTemplate(String t) {
		this.template = BackgroundManager.get().getTemplate(t);
	}
	
	
}
