package mesiah.danmaku.audio;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import mesiah.danmaku.Options;

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
	private String currentlyPlaying;
	
	private AudioManager() {
		musics = new HashMap<String, Music>();
		sounds = new HashMap<String, Sound>();
		musicVolume = 1.0f;
		soundVolume = 1.0f;
		musicPitch = 1.0f;
		soundPitch = 1.0f;
	}
	
	public void musicVolumeUp() {
		float qty = Options.MAX_MUSIC_VOLUME/50;
		if (musicVolume <= Options.MAX_MUSIC_VOLUME) {
			musicVolume += qty;
		}
		if (musicVolume > Options.MAX_MUSIC_VOLUME) {
			musicVolume = Options.MAX_MUSIC_VOLUME;
		}
		musics.get(currentlyPlaying).setVolume(musicVolume);
	}
	
	public void musicVolumeDown() {
		float qty = Options.MAX_MUSIC_VOLUME/50;
		if (musicVolume >= 0) {
			musicVolume -= qty;
		}
		if (musicVolume < 0) {
			musicVolume = 0;
		}
		musics.get(currentlyPlaying).setVolume(musicVolume);
	}
	
	public void soundVolumeUp() {
		float qty = Options.MAX_SOUND_VOLUME/50;
		if (soundVolume <= Options.MAX_SOUND_VOLUME) {
			soundVolume += qty;
		}
		if (soundVolume > Options.MAX_SOUND_VOLUME) {
			soundVolume = Options.MAX_SOUND_VOLUME;
		}
	}
	
	public void soundVolumeDown() {
		float qty = Options.MAX_SOUND_VOLUME/50;
		if (soundVolume >= 0) {
			soundVolume -= qty;
		}
		if (soundVolume < 0) {
			soundVolume = 0;
		}
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

	public String getCurrentlyPlaying() {
		return currentlyPlaying;
	}

	public void setCurrentlyPlaying(String currentlyPlaying) {
		this.currentlyPlaying = currentlyPlaying;
	}

	public float getMusicVolume() {
		return musicVolume;
	}

	public float getSoundVolume() {
		return soundVolume;
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
		if (musics.get(key).playing()) {
			musics.get(key).resume();
		} else {
			musics.get(key).loop(musicPitch, musicVolume);
		}
		currentlyPlaying = key;
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
