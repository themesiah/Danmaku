package mesiah.danmaku.audio;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class AudioManager {
	private static AudioManager am = null;
	private Map<String, Music> musics;
	private Map<String, Sound> sounds;
	private float musicVolume;
	private float soundVolume;
	private float musicPitch;
	private float soundPitch;
	
	private AudioManager() {
		musics = new HashMap<String, Music>();
		sounds = new HashMap<String, Sound>();
		musicVolume = 1.0f;
		soundVolume = 1.0f;
		musicPitch = 1.0f;
		soundPitch = 1.0f;
	}
	
	public void setMusicVolume(float musicVolume) {
		this.musicVolume = musicVolume;
	}

	public void setSoundVolume(float soundVolume) {
		this.soundVolume = soundVolume;
	}

	public void setMusicPitch(float musicPitch) {
		this.musicPitch = musicPitch;
	}

	public void setSoundPitch(float soundPitch) {
		this.soundPitch = soundPitch;
	}

	public static AudioManager get() {
		if (am == null) {
			am = new AudioManager();
		}
		return am;
	}
	
	public void clear() {
		musics.clear();
		sounds.clear();
	}
	
	public void addMusic(String path, String key) {
		try {
			Music m = new Music(path);
			musics.put(key, m);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void addSound(String path, String key) {
		try {
			Sound m = new Sound(path);
			sounds.put(key, m);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void playMusic(String key) {
		musics.get(key).loop(musicPitch, musicVolume);
	}
	
	public void stopMusic(String key) {
		musics.get(key).stop();
	}
	
	public void pauseMusic(String key) {
		musics.get(key).pause();
	}
	
	public void resumeMusic(String key) {
		musics.get(key).resume();
	}
	
	public void stopMusic() {
		Set<String> keys = musics.keySet();
		for (String key:keys) {
			musics.get(key).stop();
		}
	}
	
	public void pauseMusic() {
		Set<String> keys = musics.keySet();
		for (String key:keys) {
			musics.get(key).pause();
		}
	}
	
	public void playSound(String key) {
		sounds.get(key).play(soundPitch, soundVolume);
	}

}
