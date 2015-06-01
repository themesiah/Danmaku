package mesiah.danmaku.model;

public interface BulletEmitter {
	public void shot(int delta);
	public void addPattern(String id);
}
