package mesiah.danmaku.view;

public interface Drawable {
	public void draw(float x, float y);
	public void draw(float x, float y, float facing);
	public float[] getSize();
	public boolean isPlaying();
	public void play(float x, float y);
	public void play(float x, float y, float facing);
	public void destroy();
	public void setColor(float r, float g, float b);
	public void setColor(float r, float g, float b, float a);
	public Drawable copy();
	public float[] getSizeOf(int n);
	public int getFrame();
	public void draw(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2);
}
