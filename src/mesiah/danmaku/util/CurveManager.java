package mesiah.danmaku.util;

import java.util.HashMap;

import mesiah.danmaku.Main;
import mesiah.danmaku.Play;
import mesiah.danmaku.model.Player;
import mesiah.danmaku.model.VisibleGameObject;

import org.newdawn.slick.geom.Curve;
import org.newdawn.slick.geom.Vector2f;

public class CurveManager {

	public CurveManager() {
		curves = new HashMap<String, CustomCurve>();
	}
	
	private static CurveManager cm = null;
	private HashMap<String, CustomCurve> curves;
	
	public static CurveManager get() {
		if (cm == null) {
			cm = new CurveManager();
		}
		return cm;
	}
	
	public CustomCurve newCurve(String key) {
		return curves.get(key);
	}
	
	public void addCurve(CustomCurve c) {
		curves.put(c.getId(), c);
	}
	
	public Curve compose(String key, VisibleGameObject parent) {
		float tempX = 0, tempY = 0;
		float posx = parent.getPosX() + parent.getSize()[0]/2;
		float posy = parent.getPosY() + parent.getSize()[1];
		float[] pos = {posx, posy};
		Player p = Play.pc.get(Main.PLAYERNUM);
		float pposx = p.getPosX() + p.getSize()[0]/2;
		float pposy = p.getPosY() + p.getSize()[1]/2;
		float[] ppos = {pposx, pposy};
		CustomCurve c = newCurve(key);
		String[][] points = c.getPoints();
		Vector2f[] composed = new Vector2f[4];
		for (int i = 0; i < points.length; i++) {
			composed[i] = new Vector2f();
		}
		
		for (int i = 0; i < points.length; i++) {
			for (int j = 0; j < 2; j++) {
				float value = 0;
				String[] parts = points[i][j].split("\\+");
				for (int k = 0; k < parts.length; k++) {
					String s = parts[k];
					switch (s) {
						case "parent":
							value += pos[j];
							break;
						case "player":
							value += ppos[j];
							break;
						case "limitbottom":
							value += Main.LIMITBOTTOM;
							break;
						case "limitleft":
							value += Main.LIMITLEFT;
							break;
						case "limittop":
							value += Main.LIMITTOP;
							break;
						case "limitright":
							value += Main.LIMITRIGHT;
							break;
						case "auto":
							String temp = points[3][j];
							String[] tempParts = temp.split("\\+");
							float tempValue = 0;
							for (int z = 0; z < tempParts.length; z++) {
								String st = tempParts[z];
								switch (st) {
									case "parent":
										tempValue += pos[j];
										break;
									case "player":
										tempValue += ppos[j];
										break;
									case "limitbottom":
										tempValue += Main.LIMITBOTTOM;
										break;
									case "limitleft":
										tempValue += Main.LIMITLEFT;
										break;
									case "limittop":
										tempValue += Main.LIMITTOP;
										break;
									case "limitright":
										tempValue += Main.LIMITRIGHT;
										break;
									case "last":
										if (i != 0) {
											if (j == 0) {
												tempValue += composed[i-1].getX();
											} else {
												tempValue += composed[i-1].getY();
											}
										}
										break;
									default:
										
										if (s.contains("%")) {
											float percentPos;
											if (j == 0) {
												percentPos = (Float.valueOf(s.split("%")[0])/100.0f*(Main.LIMITRIGHT - Main.LIMITLEFT) + Main.LIMITLEFT);
											} else {
												percentPos = (Float.valueOf(s.split("%")[0])/100.0f*(Main.LIMITBOTTOM - Main.LIMITTOP) + Main.LIMITTOP);
											}
											tempValue += percentPos;
										} else {
											tempValue += Float.valueOf(st);
										}
										break;
								}
							}
							float dif = tempValue - pos[j];
							value += dif/3*(i) + pos[j];
							break;
						case "last":
							if (i != 0) {
								if (j == 0) {
									value += composed[i-1].getX();
								} else {
									value += composed[i-1].getY();
								}
							}
							break;
						default:
							if (s.contains("%")) {
								float percentPos;
								if (j == 0) {
									percentPos = (Float.valueOf(s.split("%")[0])/100.0f*(Main.LIMITRIGHT - Main.LIMITLEFT) + Main.LIMITLEFT);
								} else {
									percentPos = (Float.valueOf(s.split("%")[0])/100.0f*(Main.LIMITBOTTOM - Main.LIMITTOP) + Main.LIMITTOP);
								}
								value += percentPos;
							} else {
								value += Float.valueOf(s);
							}
							break;
					}
				}
				
				if (j == 0) {
					tempX = Float.valueOf(value);
				} else {
					tempY = Float.valueOf(value);
				}
				value = 0;
			}
			composed[i].set(tempX, tempY);
		}
		Curve finalCurve = new Curve(composed[0], composed[1], composed[2], composed[3]);
		
		return finalCurve;
	}

}
