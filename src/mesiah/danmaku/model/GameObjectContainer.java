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
 * Adem�s, actualiza y dibuja todos en un orden espec�fico.
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
	 * Actualiza todos los objetos del juego en un orden espec�fico.
	 * @param delta Tiempo pasado desde el �ltimo update.
	 */
	public void update(int delta) {
		pc.update(delta);
		ec.update(delta);
		puc.update(delta);
		bssc.update(delta);
		bc.update(delta);
	}
	
	/**
	 * Dibuja todos los objetos del juego en un orden espec�fico.
	 * Aqu� es realmente importante, pues determinar� qu� objetos se
	 * ven y qu� objetos no. Por ello, el fondo est� el primero,
	 * puesto que todo se ver� por encima, y el template por debajo,
	 * puesto que no se ver� nada por encima suyo.
	 * @param g Gr�ficos de Slick2D, para los bosses.
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
