package mesiah.danmaku.xml;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Rectangle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import mesiah.danmaku.audio.AudioManager;
import mesiah.danmaku.model.CustomPowerup;
import mesiah.danmaku.model.EnemiesManager;
import mesiah.danmaku.model.Enemy;
import mesiah.danmaku.model.Powerup;
import mesiah.danmaku.model.PowerupManager;
import mesiah.danmaku.model.bullets.CustomBullet;
import mesiah.danmaku.model.patterns.CustomFirePattern;
import mesiah.danmaku.model.patterns.FirePatternManager;
import mesiah.danmaku.util.CurveManager;
import mesiah.danmaku.util.CustomCurve;
import mesiah.danmaku.view.Animation;
import mesiah.danmaku.view.AnimationManager;
import mesiah.danmaku.view.Drawable;
import mesiah.danmaku.view.Sprite;

public class XMLLoader {
	private DocumentBuilderFactory factory;
	private DocumentBuilder db;
	private static XMLLoader xl = null;
	
	private XMLLoader() {
		factory = DocumentBuilderFactory.newInstance();
		try {
			db = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public static XMLLoader get() {
		if (xl == null) {
			xl = new XMLLoader();
		}
		return xl;
	}
	
	public void resourcesLoader(String fileName) throws Exception {
		File f = new File("res/xml/" + fileName);
		Document document = db.parse(f);
		int i;
		String content;
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		for (i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node instanceof Element) {
				content = node.getLastChild().getTextContent().trim();
				switch (node.getNodeName()) {
					case "enemy":
						getEnemyFromXML(content);
						break;
					case "curve":
						getCurveFromXML(content);
						break;
					case "pattern":
						getPatternFromXML(content);
						break;
					case "animation":
						getAnimationFromXML(content);
						break;
					case "audio":
						getAudioFromXML(content);
						break;
					case "powerup":
						getPowerupFromXML(content);
						break;
				}
			}
		}
	}
	
	public void getPowerupFromXML(String fileName) throws Exception {
		File f = new File("res/xml/powerups/" + fileName);
		Document document = db.parse(f);
		int i, j;
		CustomPowerup c = null;
		String content, powerupID = "";
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		for (i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node instanceof Element) {
				powerupID = ((Element) node).getAttributes().getNamedItem("id").getNodeValue();
				c = new CustomPowerup(powerupID);
				NodeList childNodes = node.getChildNodes();
				for (j = 0; j < childNodes.getLength(); j++) {
					Node cNode = childNodes.item(j);
					if (cNode instanceof Element) {
						content = cNode.getLastChild().getTextContent().trim();
						switch(cNode.getNodeName()) {
							case "type":
								switch (content) {
									case "power":
										c.setType(Powerup.TYPE_POWER);
										break;
									case "bomb":
										c.setType(Powerup.TYPE_BOMB);
										break;
									case "life":
										c.setType(Powerup.TYPE_LIFE);
										break;
									case "points":
										c.setType(Powerup.TYPE_POINTS);
										break;
								}
								break;
							case "value":
								c.setValue(Integer.valueOf(content));
								break;
							case "animation":
								c.setAnimation(content);
								break;
							case "speed":
								c.setSpeed(Float.valueOf(content));
								break;
							case "direction":
								c.setDirection(content);
								break;
							case "bounce":
								if (content.equals("true")) {
									c.setBounce(true);
								} else {
									c.setBounce(false);
								}
								break;
							case "qty":
								c.setQty(Integer.valueOf(content));
								break;
						}
					}
				}
			}
		}
		PowerupManager.get().addPowerup(c);
	}
	
	public void getAudioFromXML(String fileName) throws Exception {
		File f = new File("res/xml/audio/" + fileName);
		Document document = db.parse(f);
		int i;
		AudioManager am = AudioManager.get();
		am.setMusicVolume(0.2f);
		String content, audioID = "";
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		for (i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node instanceof Element) {
				audioID = ((Element) node).getAttributes().getNamedItem("id").getNodeValue();
				content = node.getLastChild().getTextContent().trim();
				switch (node.getNodeName()) {
					case "sound":
						am.addSound("res/sfx/" + content, audioID);
						break;
					case "music":
						am.addMusic("res/music/" + content, audioID);
						break;
				}
			}
		}
	}
	
	public void getAnimationFromXML(String fileName) throws Exception {
		File f = new File("res/xml/animations/" + fileName);
		Document document = db.parse(f);
		int i, j, k;
		String content, animationID = "";
		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
		ArrayList<Integer> spritesTime = new ArrayList<Integer>();
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		Animation a = new Animation();
		for (i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node instanceof Element) {
				animationID = ((Element) node).getAttributes().getNamedItem("id").getNodeValue();
				NodeList animation = node.getChildNodes();
				for (j = 0; j < animation.getLength(); j++) {
					Node sprite = animation.item(j);
					if (sprite instanceof Element) {
						switch(sprite.getNodeName()) {
						case "sprite":
							NodeList elements = sprite.getChildNodes();
							for (k = 0; k < elements.getLength(); k++) {
								Node element = elements.item(k);
								if (element instanceof Element) {
									content = element.getLastChild().getTextContent().trim();
									switch (element.getNodeName()) {
										case "image":
											sprites.add(new Sprite("res/img/" + content));
											break;
										case "time":
											spritesTime.add(Integer.parseInt(content));
											break;
									}
								}
							}
							break;
						case "loop":
							content = sprite.getLastChild().getTextContent().trim();
							if (content.equals("true")) {
								a.setLooping(true);
							} else {
								a.setLooping(false);
							}
							break;
								
						}
						
							
					}
				}
			}
		}
		Sprite[] s = new Sprite[sprites.size()];
		s = sprites.toArray(s);
		int[] t = new int[spritesTime.size()];
		for (i = 0; i < spritesTime.size(); i++) {
			t[i] = spritesTime.get(i);
		}
		AnimationManager.get().addAnimation(new Animation(s, t), animationID);
	}
	
	public void getPatternFromXML(String fileName) throws Exception {
		File f = new File("res/xml/patterns/" + fileName);
		Document document = db.parse(f);
		CustomFirePattern fp = null;
		String patternID;
		String content;
		int i, j, k, z, y;
		int lastDelay = 0;
		String[] parts;
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		for (i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node instanceof Element) {
				// Pattern singular
				patternID = ((Element) node).getAttributes().getNamedItem("id").getNodeValue();
				fp = new CustomFirePattern(patternID);
				NodeList childNodes = node.getChildNodes();
				for (j = 0; j < childNodes.getLength(); j++) {
					// Bullet
					Node bulletNode = childNodes.item(j);
					if (bulletNode instanceof Element) {
						NodeList bulletChildNodes = bulletNode.getChildNodes();
						CustomBullet cb = new CustomBullet();
						for (k = 0; k < bulletChildNodes.getLength(); k++) {
							// Elementos de los bullets
							Node bulletElement = bulletChildNodes.item(k);
							if (bulletElement instanceof Element) {
								content = bulletElement.getLastChild().getTextContent().trim();
								switch(bulletElement.getNodeName()) {
									case "delay":
										parts = content.split("\\+");
										int tempDelay = 0;
										for (y = 0; y < parts.length; y++) {
											switch(parts[y]) {
											case "last":
												tempDelay += lastDelay;
												break;
											default:
												tempDelay += Integer.valueOf(parts[y]);
												lastDelay += Integer.valueOf(parts[y]);
												break;
											}
										}
										cb.setDelay(String.valueOf(tempDelay));
										break;
									case "speed":
										cb.setSpeed(content);
										break;
									case "posx":
										cb.setPosx(content);
										break;
									case "posy":
										cb.setPosy(content);
										break;
									case "damage":
										cb.setDamage(content);
										break;
									case "direction":
										cb.setDirection(content);
										break;
									case "animation":
										cb.setAnimation(content);
										break;
									case "ally":
										cb.setAlly(content);
										break;
									case "property":
										cb.addProperty(content);
										break;
									case "density":
										cb.setDensity(content);
										break;
									case "angleOffset":
										cb.setAngleOffSet(content);
										break;
									case "lifeTime":
										cb.setLifeTime(content);
										break;
									case "curves":
										NodeList curves = bulletElement.getChildNodes();
										for (z = 0; z < curves.getLength(); z++) {
											Node curve = curves.item(z);
											
											if (curve instanceof Element) {
												NodeList curveElements = curve.getChildNodes();
												for (y = 0; y < curveElements.getLength(); y++) {
													Node curveElement = curveElements.item(y);
													if (curveElement instanceof Element) {
														content = curveElement.getLastChild().getTextContent().trim();
														switch(curveElement.getNodeName()) {
														case "curveName":
															cb.addCurve(content);
															break;
														case "curveTime":
															cb.addCurveTime(content);
															break;
														}
													}
												}
											}
										}
										break;
									case "onlyCurve":
										cb.setOnlyCurve(content);
										break;
									case "firePatterns":
										NodeList patterns = bulletElement.getChildNodes();
										for (z = 0; z < patterns.getLength(); z++) {
											Node pattern = patterns.item(z);
											if (pattern instanceof Element) {
												content = pattern.getLastChild().getTextContent().trim();
												cb.addFirePattern(content);
											}
										}
										break;
									case "hitbox":
										NodeList hitboxes = bulletElement.getChildNodes();
										for (z = 0; z < hitboxes.getLength(); z++) {
											Node hitbox = hitboxes.item(z);
											if (hitbox instanceof Element) {
												content = hitbox.getLastChild().getTextContent().trim();
												switch(hitbox.getNodeName()) {
													case "rectangle":
														if (content.equals("sprite")) {
															cb.addHitbox();
														} else {
															parts = content.split(",");
															float posx = Float.valueOf(parts[0]);
															float posy = Float.valueOf(parts[1]);
															float width = Float.valueOf(parts[2]);
															float height = Float.valueOf(parts[3]);
															Rectangle r = new Rectangle(posx, posy, width, height);
															cb.addHitbox(r);
														}
														break;
													case "ellipse":
														parts = content.split(",");
														float posx = Float.valueOf(parts[0]);
														float posy = Float.valueOf(parts[1]);
														float radiusX = Float.valueOf(parts[2]);
														float radiusY = Float.valueOf(parts[3]);
														Ellipse el = new Ellipse(posx, posy, radiusX, radiusY);
														cb.addHitbox(el);
														break;
												}
											}
										}
										break;
									}
								}
							}
							fp.addCustomBullet(cb);						
					}
				}
			}
		}
		FirePatternManager.get().addFirePattern(fp);
	}
	
	public void getCurveFromXML(String fileName) throws Exception {
		File f = new File("res/xml/curves/" + fileName);
		Document document = db.parse(f);
		CustomCurve c = null;
		String curveID;
		String content;
		int i, j, index = -1;
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		for (i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node instanceof Element) {
				curveID = ((Element) node).getAttributes().getNamedItem("id").getNodeValue();
				c = new CustomCurve(curveID);
				NodeList childNodes = node.getChildNodes();
				for (j = 0; j < childNodes.getLength(); j++) {
					Node cNode = childNodes.item(j);
					if (cNode instanceof Element) {
						switch(cNode.getNodeName()) {
							case "point1":
								index = 0;
								break;
							case "point2":
								index = 1;
								break;
							case "point3":
								index = 2;
								break;
							case "point4":
								index = 3;
								break;
						}
						content = cNode.getLastChild().getTextContent().trim();
						String[] parts = content.split(",");
						c.addPoint(parts, index);
					}
				}
			}
		}
		CurveManager.get().addCurve(c);
	}
	
	public void getEnemyFromXML(String fileName) throws Exception {
		File f = new File("res/xml/enemies/" + fileName);
		Document document = db.parse(f);
		Enemy e = new Enemy();
		String enemyID;
		String content;
		int k, j, i, z;
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		for (i = 0; i < nodeList.getLength(); i++) {
			// Un enemigo concreto
			Node node = nodeList.item(i);
			if (node instanceof Element) {
				enemyID = node.getAttributes().getNamedItem("id").getNodeValue();
				e.setEnemyID(enemyID);
				// Lista de elementos
				NodeList childNodes = node.getChildNodes();
				for (j = 0; j < childNodes.getLength(); j++) {
					// Un elemento
					Node cNode = childNodes.item(j);
					if (cNode instanceof Element) {
						switch(cNode.getNodeName()) {
							case "posx":
								content = cNode.getLastChild().getTextContent().trim();
								e.setPosX(Integer.parseInt(content));
								break;
							case "posy":
								content = cNode.getLastChild().getTextContent().trim();
								e.setPosY(Integer.parseInt(content));
								break;
							case "health":
								content = cNode.getLastChild().getTextContent().trim();
								e.setHealth(Integer.parseInt(content));
								break;
							case "speed":
								content = cNode.getLastChild().getTextContent().trim();
								e.setSpeed(Float.parseFloat(content));
								break;
							case "facing":
								content = cNode.getLastChild().getTextContent().trim();
								e.setFacing(Float.parseFloat(content));
								break;
							case "direction":
								content = cNode.getLastChild().getTextContent().trim();
								e.setDirection(Float.parseFloat(content));
								break;
							case "powerup":
								content = cNode.getLastChild().getTextContent().trim();
								e.addPowerup(content);
								break;
							case "hitbox":
								NodeList hitboxChilds = cNode.getChildNodes();
								for (k = 0; k < hitboxChilds.getLength(); k++) {
									Node hbNode = hitboxChilds.item(k);
									if (hbNode instanceof Element) {
										content = hbNode.getLastChild().getTextContent().trim();
										switch(hbNode.getNodeName()) {
										case "rectangle":
											if (content.equals("sprite")) {
												e.addHitbox();
											} else {
												String[] parts = content.split(",");
												float x = Float.valueOf(parts[0]);
												float y = Float.valueOf(parts[1]);
												float width = Float.valueOf(parts[2]);
												float height = Float.valueOf(parts[3]);
												Rectangle r = new Rectangle(x, y, width, height);
												e.addHitbox(r);
											}
											break;
										case "ellipse":
											String[] parts = content.split(",");
											float x = Float.valueOf(parts[0]);
											float y = Float.valueOf(parts[1]);
											float radiusX = Float.valueOf(parts[2]);
											float radiusY = Float.valueOf(parts[3]);
											Ellipse el = new Ellipse(x, y, radiusX, radiusY);
											e.addHitbox(el);
											break;
										}
										
									}
								}
								break;
							case "animations":
								NodeList aniChilds = cNode.getChildNodes();
								for (k = 0; k < aniChilds.getLength(); k++) {
									Node aNode = aniChilds.item(k);
									if (aNode instanceof Element) {
										content = aNode.getLastChild().getTextContent().trim();
										Drawable d = AnimationManager.get().getAnimation(content);
										switch(aNode.getNodeName()) {
											case "active":
												e.addAnimation(d, Enemy.ACTIVE);
												break;
											case "destroyed":
												e.addAnimation(d, Enemy.DESTROYED);
												break;
										}
									}
								}
								break;
							case "firePatterns":
								NodeList fireChilds = cNode.getChildNodes();
								for (k = 0; k < fireChilds.getLength(); k++) {
									Node fpsNode = fireChilds.item(k);
									if (fpsNode instanceof Element) {
										NodeList fpsChildNodes = fpsNode.getChildNodes();
										for (z = 0; z < fpsChildNodes.getLength(); z++) {
											Node fpNode = fpsChildNodes.item(z);
											if (fpNode instanceof Element) {
												switch(fpNode.getNodeName()) {
													case "fp":
														content = fpNode.getLastChild().getTextContent().trim();
														e.addPattern(content);
														break;
													case "shotDelay":
														content = fpNode.getLastChild().getTextContent().trim();
														e.addShotDelay(Integer.parseInt(content));
														break;
												}
											}
										}
									}
								}
								
								break;
							case "sound":
								NodeList soundsList = cNode.getChildNodes();
								for (k = 0; k < soundsList.getLength(); k++) {
									Node sNode = soundsList.item(k);
									if (sNode instanceof Element) {
										content = sNode.getLastChild().getTextContent().trim();
										switch(sNode.getNodeName()) {
											case "destroyed":
												e.addSound(content, Enemy.DESTROYED);
												break;
											case "shot":
												e.addSound(content, Enemy.SHOT);
												break;
										}
									}
								}
								break;
						}
					}
				}
			}
		}
		EnemiesManager.get().addEnemy(e);
	}
}
