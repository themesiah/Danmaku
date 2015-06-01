package mesiah.danmaku.view;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Animation implements Drawable {
	org.newdawn.slick.Animation ani;
	
	public Animation(Image[] s, int[] d) {
		ani = new org.newdawn.slick.Animation(s, d, true);
	}
	
	public Animation(Sprite[] s, int[] d) {
		ani = new org.newdawn.slick.Animation(true);
		int i = 0;
		for (Sprite sprite: s) {
			ani.addFrame(sprite.getImage(), d[i]);
			i++;
		}
	}
	
	public Animation(Sprite[] s, int[] d, boolean auto) {
		ani = new org.newdawn.slick.Animation(auto);
		int i = 0;
		for (Sprite sprite: s) {
			ani.addFrame(sprite.getImage(), d[i]);
			i++;
		}
	}
	
	public Animation(org.newdawn.slick.Animation a) {
		ani = a;
	}
	
	public void play(float x, float y) {
		ani.setLooping(false);
		ani.start();
		draw(x, y);
	}
	
	public void play(float x, float y, float facing) {
		ani.setLooping(false);
		ani.start();
		draw(x, y, facing);
	}
	
	public boolean isPlaying() {
		return !ani.isStopped();
	}
	
	public void draw(float x, float y) {
		this.ani.draw(x, y);
	}
	
	public void draw(float x, float y, float facing) {
		this.ani.getCurrentFrame().setRotation(facing);
		this.ani.draw(x, y);
	}
	
	public void setColor(float r, float g, float b) {
		for (int i = 0; i < ani.getFrameCount(); i++) {
			ani.getImage(i).setImageColor(r, g, b);
		}
	}
	
	public void setColor(float r, float g, float b, float a) {
		for (int i = 0; i < ani.getFrameCount(); i++) {
			ani.getImage(i).setImageColor(r, g, b, a);
		}
	}
	
	public float[] getSize() {
		float[] rect = new float[4];
		rect[0] = this.ani.getCurrentFrame().getWidth();
		rect[1] = this.ani.getCurrentFrame().getHeight();
		return rect;
	}
	
	public Animation copy() {
		Sprite[] s = new Sprite[ani.getFrameCount()];
		int[] t = new int[ani.getFrameCount()];
		t = ani.getDurations();
		for (int i = 0; i < ani.getFrameCount(); i++) {
			s[i] = new Sprite(ani.getImage(i).copy());
		}
		return new Animation(s, t);
	}
	
	public void destroy() {
		try {
			for (int i = 0; i < ani.getFrameCount(); i++) {
				ani.getImage(i).destroy();
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
