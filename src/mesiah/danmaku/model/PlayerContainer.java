package mesiah.danmaku.model;

import java.util.ArrayList;
import java.util.List;

public class PlayerContainer {
	List<Player> plist;
	
	public PlayerContainer() {
		this.plist = new ArrayList<Player>();
	}
	
	public List<Player> getPlayers() {
		return plist;
	}
	
	public void add(Player p) {
		plist.add(p);
	}

	public Player get(int n) {
		return plist.get(n);
	}

	public void remove(Player p) {
		plist.remove(p);
	}

	public void remove(int n) {
		plist.remove(n);
	}

	public int size() {
		return plist.size();
	}
	
	public void update(int delta) {
		for (Player p:plist) {
			p.update(delta);
		}
	}
	
	public void draw() {
		for (Player p:plist) {
			p.draw();
		}
	}
}
