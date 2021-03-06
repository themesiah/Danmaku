package mesiah.danmaku.util;

import java.util.HashMap;

public class Signals {
	private static Signals s = null;
	private HashMap<String, Integer> integers;
	private HashMap<String, Integer> iintegers;
	
	private Signals() {
		integers = new HashMap<String, Integer>();
		iintegers = new HashMap<String, Integer>();
	}
	
	public static Signals getSignals() {
		if (s == null) {
			s = new Signals();
		}
		return s;
	}
	
	public void addIntegerSignal(String key, int i) {
		if (!integers.containsKey(key)) {
			integers.put(key, i);
			iintegers.put(key, i);
		}
	}
	
	public void removeIntegerSignal(String key) {
		integers.remove(key);
	}
	
	public void changeIntegerSignal(String key, int change) {
		int signal = integers.get(key);
		signal += change;
		integers.replace(key, signal);
	}
	
	public int getIntegerSignal(String key) {
		return integers.get(key);
	}
	
	public void resetIntegerSignal(String key) {
		integers.replace(key, iintegers.get(key));
	}
	
	public boolean hasIntegerSignal(String key) {
		return integers.containsKey(key);
	}

	public HashMap<String, Integer> getIntegerSignals() {
		return integers;
	}

	public void setIntegerSignals(HashMap<String, Integer> integers) {
		this.integers = integers;
	}
	
	
}
