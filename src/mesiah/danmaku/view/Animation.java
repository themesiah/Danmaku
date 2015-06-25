package mesiah.danmaku.view;

import mesiah.danmaku.Main;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Animation implements Drawable {
	org.newdawn.slick.Animation ani;
	boolean looping;
	
	public Animation() {
		looping = false;
	}
	
	public void addAnimation(Sprite[] s, int[] d) {
		ani = new org.newdawn.slick.Animation(true);
		int i = 0;
		for (Sprite sprite: s) {
			ani.addFrame(sprite.getImage(), d[i]);
			i++;
		}
	}
	
	public Animation(Image[] s, int[] d) {
		ani = new org.newdawn.slick.Animation(s, d, true);
		looping = false;
	}
	
	public Animation(Sprite[] s, int[] d) {
		ani = new org.newdawn.slick.Animation(true);
		int i = 0;
		for (Sprite sprite: s) {
			ani.addFrame(sprite.getImage(), d[i]);
			i++;
		}
		looping = false;
	}
	
	public Animation(Sprite[] s, int[] d, boolean auto) {
		ani = new org.newdawn.slick.Animation(auto);
		int i = 0;
		for (Sprite sprite: s) {
			ani.addFrame(sprite.getImage(), d[i]);
			i++;
		}
		looping = false;
	}
	
	public void setLooping(boolean l) {
		this.looping = l;
	}
	
	public boolean getLooping() {
		return this.getLooping();
	}
	
	public Animation(org.newdawn.slick.Animation a) {
		ani = a;
	}
	
	public void play(float x, float y) {
		ani.setLooping(looping);
		ani.start();
		draw(x, y);
	}
	
	public void play(float x, float y, float facing) {
		ani.setLooping(looping);
		ani.start();
		draw(x, y, facing);
	}
	
	public boolean isPlaying() {
		return !ani.isStopped();
	}
	
	private boolean checkDraw(float x, float y) {
		float width = this.ani.getWidth();
		float height = this.ani.getHeight();
		if (x + width/2 < Main.LIMITLEFT ||
			x - width/2 > Main.LIMITRIGHT ||
			y + height/2 < Main.LIMITTOP ||
			y - height/2 > Main.LIMITBOTTOM) {
			return false;
		} else {
			return true;
		}
	}
	
	public void draw(float x, float y) {
		if (checkDraw(x, y)) {
			this.ani.draw(x, y);
		}
	}
	
	public void draw(float x, float y, float facing) {
		if (checkDraw(x, y)) {
			this.ani.getCurrentFrame().setRotation(facing);
			this.ani.draw(x, y);
		}
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
	
	public float[] getSizeOf(int n) {
		float[] rect = new float[4];
		rect[0] = this.ani.getImage(n).getWidth();
		rect[1] = this.ani.getImage(n).getWidth();
		return rect;
	}
	
	public int getFrame() {
		return this.ani.getFrame();
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
