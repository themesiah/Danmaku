package mesiah.danmaku.view;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Sprite implements Drawable {

	Image img;
	
	public Sprite(String path) throws SlickException {
		this.img = new Image(path);
	}
	
	public Sprite(Image i) {
		this.img = i;
	}
	
	public void draw(float x, float y) {
		this.img.draw(x, y);
	}
	
	public void draw(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2) {
		this.img.draw(x, y, x2, y2, srcx, srcy, srcx2, srcy2);
	}
	
	public void draw(float x, float y, float facing) {
		this.img.setRotation(facing);
		this.img.draw(x, y);
	}
	
	public Image getImage() {
		return img;
	}
	
	public void setColor(float r, float g, float b) {
		img.setImageColor(r, g, b);
	}
	
	public void setColor(float r, float g, float b, float a) {
		img.setImageColor(r, g, b, a);
	}

	public float[] getSize() {
		float[] rect = new float[2];
		rect[0] = this.img.getWidth();
		rect[1] = this.img.getHeight();
		return rect;
	}
	
	public boolean isPlaying() {
		return true;
	}
	
	public void play(float x, float y) {
		draw(x, y);
	}
	
	public void play(float x, float y, float facing) {
		draw(x, y, facing);
	}
	
	public void destroy() {
		try {
			this.img.destroy();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public Sprite copy() {
		return new Sprite(img.copy());
	}

	public float[] getSizeOf(int n) {
		float[] rect = new float[2];
		rect[0] = this.img.getWidth();
		rect[1] = this.img.getHeight();
		return rect;
	}

	public int getFrame() {
		return 0;
	}
	
}
