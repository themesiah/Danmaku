package mesiah.danmaku.model;

/**
 * Interface uqe implementan los objetos que pueden colisionar con otros.
 * @author Mesiah
 *
 */
public interface Collidable {
	public void CheckEnemyCollisions();
	public void CheckPlayerCollisions();
	public void CheckBulletCollisions();
	public void CheckPowerupCollisions();
}
