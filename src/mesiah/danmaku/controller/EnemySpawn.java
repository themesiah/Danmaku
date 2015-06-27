package mesiah.danmaku.controller;

import java.util.ArrayList;

import mesiah.danmaku.Main;
import mesiah.danmaku.model.BossesManager;
import mesiah.danmaku.model.EnemiesManager;
import mesiah.danmaku.model.Enemy;
import mesiah.danmaku.util.CurveManager;
import mesiah.danmaku.util.CustomCurve;

import org.newdawn.slick.geom.Curve;

/**
 * Clase que controla un spawn singular de enemigos.
 * Esto se reduce a: un enemigo y la posición / ruta que tendrá.
 * Además, tiene diferentes tipos: enemigo, boss y win, donde este último indica fin de un nivel.
 * @author Mesiah
 *
 */
public class EnemySpawn {
	private String enemy;
	private String type;
	private ArrayList<CustomCurve> curve;
	private ArrayList<Integer> curveTime;
	private String posX;
	private String posY;
	private String finalPosX;
	private String finalPosY;
	private String transitionTime;
	
	public String getTransitionTime() {
		return transitionTime;
	}

	public void setTransitionTime(String transitionTime) {
		this.transitionTime = transitionTime;
	}

	public String getFinalPosX() {
		return finalPosX;
	}

	public void setFinalPosX(String finalPosX) {
		this.finalPosX = finalPosX;
	}

	public String getFinalPosY() {
		return finalPosY;
	}

	public void setFinalPosY(String finalPosY) {
		this.finalPosY = finalPosY;
	}

	public EnemySpawn() {
		curve = new ArrayList<CustomCurve>();
		curveTime = new ArrayList<Integer>();
		posX = "0";
		posY = "0";
		finalPosX = "0";
		finalPosY = "0";
		transitionTime = "0";
	}

	public String getPosX() {
		return posX;
	}

	public void setPosX(String posX) {
		this.posX = posX;
	}

	public String getPosY() {
		return posY;
	}

	public void setPosY(String posY) {
		this.posY = posY;
	}

	public String getEnemy() {
		return enemy;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setEnemy(String enemy) {
		this.enemy = enemy;
	}
	
	public ArrayList<CustomCurve> getCurve() {
		return curve;
	}

	public void setCurve(ArrayList<CustomCurve> curve) {
		this.curve = curve;
	}

	public ArrayList<Integer> getCurveTime() {
		return curveTime;
	}

	public void setCurveTime(ArrayList<Integer> curveTime) {
		this.curveTime = curveTime;
	}
	
	public void addCurve(String c, int ct) {
		curve.add(CurveManager.get().newCurve(c));
		curveTime.add(ct);
	}

	/**
	 * Devuelve el enemigo que contiene la clase con su ruta incluida.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Enemy composeEnemy() {
		Enemy e = null;
		if (type.equals("enemy")) {
			e = EnemiesManager.get().newEnemy(enemy);
			if (curve.size() > 0) {
				ArrayList<Curve> curves = new ArrayList<Curve>();
				for (CustomCurve cc : curve) {
					curves.add(CurveManager.get().compose(cc.getId(), e));
					e.setOnlyCurve(true);
				}
				e.setRoute((ArrayList<Curve>) curves.clone());
				e.setRouteTime((ArrayList<Integer>) curveTime.clone());
			} else {
				
			}
		} else if (type.equals("boss")) {
			e = BossesManager.get().newBoss(enemy);
		}
		
		float posx = 0;
		float posy = 0;
		if (posX.contains("%")) {
			posx = (Float.valueOf(posX.split("%")[0])/100.0f*(Main.LIMITRIGHT - Main.LIMITLEFT) + Main.LIMITLEFT) - e.getSize()[0]/2;
		} else {
			posx = Float.valueOf(posX) - e.getSize()[0]/2;
		}
		if (posY.contains("%")) {
			posy = (Float.valueOf(posY.split("%")[0])/100.0f*(Main.LIMITBOTTOM - Main.LIMITTOP) + Main.LIMITTOP) - e.getSize()[1]/2;
		} else {
			posy = Float.valueOf(posY) - e.getSize()[1]/2;
		}
		
		float finalposx = 0;
		float finalposy = 0;
		if (finalPosX.contains("%")) {
			finalposx = (Float.valueOf(finalPosX.split("%")[0])/100.0f*(Main.LIMITRIGHT - Main.LIMITLEFT) + Main.LIMITLEFT) - e.getSize()[0]/2;
		} else {
			finalposx = Float.valueOf(finalPosX) - e.getSize()[0]/2;
		}
		if (finalPosY.contains("%")) {
			finalposy = (Float.valueOf(finalPosY.split("%")[0])/100.0f*(Main.LIMITBOTTOM - Main.LIMITTOP) + Main.LIMITTOP) - e.getSize()[1]/2;
		} else {
			finalposy = Float.valueOf(finalPosY) - e.getSize()[1]/2;
		}
		
		int tt = Integer.valueOf(transitionTime);
		
		e.setPosX(posx);
		e.setPosY(posy);
		e.setFinalPosX(finalposx);
		e.setFinalPosY(finalposy);
		e.setTransitionTime(tt);
		
		return e;
	}
	
	
}
