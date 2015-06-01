package mesiah.danmaku.model.patterns;

import java.util.ArrayList;

import mesiah.danmaku.model.GameObject;

public abstract class Pattern extends GameObject {
	ArrayList<?> gol;
	
	public abstract void add(GameObject go);
	public abstract GameObject get(int n);
	public abstract void remove(int n);
	public abstract void remove(GameObject go);
	public abstract int size();
}
