package mesiah.danmaku.model;

import java.util.HashMap;

import org.newdawn.slick.SlickException;

public class BackgroundManager {
	private static BackgroundManager bm = null;
	private HashMap<String, Background> bgs;
	private HashMap<String, Template> tems;
	
	private BackgroundManager() {
		bgs = new HashMap<String, Background>();
		tems = new HashMap<String, Template>();
	}
	
	public static BackgroundManager get() {
		if (bm == null) {
			bm = new BackgroundManager();
		}
		return bm;
	}
	
	public void addBackground(String key) {
		Background bg;
		try {
			bg = new Background(key);
			bgs.put(key, bg);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}
	
	public void addTemplate(String key) {
		Template tem;
		try {
			tem = new Template(key);
			tems.put(key, tem);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public Background getBackground(String key) {
		return bgs.get(key);
	}
	
	public Template getTemplate(String key) {
		return tems.get(key);
	}
}
