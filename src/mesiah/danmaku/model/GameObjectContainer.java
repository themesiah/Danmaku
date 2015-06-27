package mesiah.danmaku.model;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Clase que contiene todos los contenedores de diferentes
 * tipos de objetos del juego.
 * Contiene:
 * Jugadores
 * Patrones de disparo
 * Enemigos
 * Powerups
 * Bosses
 * Fondos
 * Templates
 * Además, actualiza y dibuja todos en un orden específico.
 * @author Mesiah
 *
 */
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
	
	/**
	 * Actualiza todos los objetos del juego en un orden específico.
	 * @param delta Tiempo pasado desde el último update.
	 */
	public void update(int delta) {
		pc.update(delta);
		ec.update(delta);
		puc.update(delta);
		bssc.update(delta);
		bc.update(delta);
	}
	
	/**
	 * Dibuja todos los objetos del juego en un orden específico.
	 * Aquí es realmente importante, pues determinará qué objetos se
	 * ven y qué objetos no. Por ello, el fondo está el primero,
	 * puesto que todo se verá por encima, y el template por debajo,
	 * puesto que no se verá nada por encima suyo.
	 * @param g Gráficos de Slick2D, para los bosses.
	 */
	public void draw(Graphics g) {
		bg.draw();
		pc.draw();
		ec.draw();
		puc.draw();
		bssc.draw(g);
		bc.draw();
		template.draw();
	}
	
	public void setBg(String bg) {
		this.bg = BackgroundManager.get().getBackground(bg);
	}
	
	public void setTemplate(String t) {
		this.template = BackgroundManager.get().getTemplate(t);
	}
	
	
}
