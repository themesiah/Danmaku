package mesiah.danmaku.model;

/**
 * Interface que tienen los objetos que disparan balas,
 * sean enemigos, players u otras balas.
 * @author Mesiah
 *
 */
public interface BulletEmitter {
	public void shot(int delta);
	public void addPattern(String id);
}
