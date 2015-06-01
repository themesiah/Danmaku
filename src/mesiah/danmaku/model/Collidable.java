package mesiah.danmaku.model;

public interface Collidable {
	public void CheckEnemyCollisions();
	public void CheckPlayerCollisions();
	public void CheckBulletCollisions();
	public void CheckPowerupCollisions();
}
