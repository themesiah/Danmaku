package mesiah.danmaku.util;

import mesiah.danmaku.Main;
import mesiah.danmaku.Play;

public class GetDirection {
	public static float getDirectionToPlayer(float posx, float posy) {
		float playerX = Play.pc.get(Main.PLAYERNUM).getPosX()+Play.pc.get(Main.PLAYERNUM).getSize()[0]/2;
		float playerY = Play.pc.get(Main.PLAYERNUM).getPosY()+Play.pc.get(Main.PLAYERNUM).getSize()[1]/4;
		float difX = playerX - posx;
		float difY = posy - playerY;
		float dir = (float) Math.toDegrees(Math.atan2(difY, difX));
		return dir;
	}
}
