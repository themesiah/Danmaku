package mesiah.danmaku.xml;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Rectangle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import mesiah.danmaku.Main;
import mesiah.danmaku.Play;
import mesiah.danmaku.audio.AudioManager;
import mesiah.danmaku.controller.EnemySpawn;
import mesiah.danmaku.controller.Level;
import mesiah.danmaku.controller.LevelsManager;
import mesiah.danmaku.model.BackgroundManager;
import mesiah.danmaku.model.Boss;
import mesiah.danmaku.model.BossesManager;
import mesiah.danmaku.model.CustomPowerup;
import mesiah.danmaku.model.EnemiesManager;
import mesiah.danmaku.model.Enemy;
import mesiah.danmaku.model.Player;
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
					case "player":
						getPlayerFromXML(content);
						break;
					case "boss":
						getBossFromXML(content);
						break;
					case "level":
						getLevelFromXML(content);
						break;
				}
			}
		}
	}
	
	public void getConfigFromXML(String fileName) throws Exception {
		File f = new File("res/xml/" + fileName);
		Document document = db.parse(f);
		int i;
		String content;
		String[] parts;
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		for (i = 0; i < nodeList.getLength(); i++) {
			Node game = nodeList.item(i);
			if (game instanceof Element) {
				content = game.getLastChild().getTextContent().trim();
				switch (game.getNodeName()) {
					case "gamename":
						Main.GAMENAME = content;
						break;
					case "gamewidth":
						Main.GAMEWIDTH = Integer.valueOf(content);
						break;
					case "gameheight":
						Main.GAMEHEIGHT = Integer.valueOf(content);
						break;
					case "fullscreen":
						Main.FULLSCREEN = Boolean.valueOf(content);
						break;
					case "limitleft":
						Main.LIMITLEFT = Integer.valueOf(content);
						break;
					case "limitright":
						Main.LIMITRIGHT = Integer.valueOf(content);
						break;
					case "limitbottom":
						Main.LIMITBOTTOM = Integer.valueOf(content);
						break;
					case "limittop":
						Main.LIMITTOP = Integer.valueOf(content);
						break;
					case "pointspos":
						parts = content.split(",");
						Play.POINTS_X = Integer.valueOf(parts[0]);
						Play.POINTS_Y = Integer.valueOf(parts[1]);
						break;
					case "bombspos":
						parts = content.split(",");
						Play.BOMBS_X = Integer.valueOf(parts[0]);
						Play.BOMBS_Y = Integer.valueOf(parts[1]);
						break;
					case "grazepos":
						parts = content.split(",");
						Play.GRAZE_X = Integer.valueOf(parts[0]);
						Play.GRAZE_Y = Integer.valueOf(parts[1]);
						break;
					case "livespos":
						parts = content.split(",");
						Play.LIVES_X = Integer.valueOf(parts[0]);
						Play.LIVES_Y = Integer.valueOf(parts[1]);
						break;
					case "powerpos":
						parts = content.split(",");
						Play.POWER_X = Integer.valueOf(parts[0]);
						Play.POWER_Y = Integer.valueOf(parts[1]);
						break;
					case "debug":
						Main.DEBUG = Boolean.valueOf(content);
						break;
				}
			}
		}
	}
	
	public void getLevelFromXML(String fileName) throws Exception {
		File f = new File("res/xml/levels/" + fileName);
		Document document = db.parse(f);
		int i, j, k, z;
		String content;
		String[] parts;
		Level lv = new Level();
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		for (i = 0; i < nodeList.getLength(); i++) {
			Node level = nodeList.item(i);
			if (level instanceof Element) {
				//levelID = ((Element) level).getAttributes().getNamedItem("id").getNodeValue();
				NodeList levelElements = level.getChildNodes();
				for (j = 0; j < levelElements.getLength(); j++) {
					Node levelElement = levelElements.item(j);
					if (levelElement instanceof Element) {
						// Cada elemento
						String nodeName = levelElement.getNodeName();
						switch (nodeName) {
							case "music":
								content = levelElement.getLastChild().getTextContent().trim();
								lv.setMusic(content);
								break;
							case "bg":
								content = levelElement.getLastChild().getTextContent().trim();
								BackgroundManager.get().addBackground(content);
								lv.setBg(content);
								break;
							case "template":
								content = levelElement.getLastChild().getTextContent().trim();
								BackgroundManager.get().addTemplate(content);
								lv.setTemplate(content);
								break;
							case "canFocus":
								content = levelElement.getLastChild().getTextContent().trim();
								if (content == "false") {
									Play.CANFOCUS = false;
								} else {
									Play.CANFOCUS = true;
								}
								break;
							case "canGraze":
								content = levelElement.getLastChild().getTextContent().trim();
								if (content == "false") {
									Play.CANGRAZE = false;
								} else {
									Play.CANGRAZE = true;
								}
								break;
							case "step":
								ArrayList<EnemySpawn> es = new ArrayList<EnemySpawn>();
								int spawnTime = Integer.valueOf(((Element) levelElement).getAttributes().getNamedItem("time").getNodeValue());
								
								NodeList enemies = levelElement.getChildNodes();
								for (k = 0; k < enemies.getLength(); k++) {
									// DIFERENCIAR ENEMIGOS DE BOSSES!!!
									Node enemy = enemies.item(k);
									if (enemy instanceof Element) {
										EnemySpawn enemySpawn = new EnemySpawn();
										NodeList enemyElements = enemy.getChildNodes();
										for (z = 0; z < enemyElements.getLength(); z++) {
											Node enemyElement = enemyElements.item(z);
											if (enemyElement instanceof Element) {
												switch (enemyElement.getNodeName()) {
													case "type":
														content = enemyElement.getLastChild().getTextContent().trim();
														enemySpawn.setEnemy(content);
														break;
													case "route":
														content = enemyElement.getLastChild().getTextContent().trim();
														parts = content.split(",");
														enemySpawn.addCurve(parts[0], Integer.valueOf(parts[1]));
														break;
													case "pos":
														content = enemyElement.getLastChild().getTextContent().trim();
														parts = content.split(",");
														enemySpawn.setPosX(parts[0]);
														enemySpawn.setPosY(parts[1]);
													case "finalPos":
														content = enemyElement.getLastChild().getTextContent().trim();
														parts = content.split(",");
														enemySpawn.setFinalPosX(parts[0]);
														enemySpawn.setFinalPosY(parts[1]);
														break;
													case "transitionTime":
														content = enemyElement.getLastChild().getTextContent().trim();
														enemySpawn.setTransitionTime(content);
														break;
												}
											}
										}
										es.add(enemySpawn);
										switch(enemy.getNodeName()) {
											case "enemy":
												enemySpawn.setType("enemy");
												break;
											case "boss":
												enemySpawn.setType("boss");
												break;
											case "win":
												enemySpawn.setType("win");
												break;
										}
									}
								}
								
								lv.addStep(es, spawnTime);
								break;
						}
					}
				}
			}
		}
		LevelsManager.get().addLevel(lv);
	}
	
	public void getPlayerFromXML(String fileName) throws Exception {
		File f = new File("res/xml/" + fileName);
		Document document = db.parse(f);
		int i, j, k, z;
		float posx = 0, posy = 0;
		String content;
		String[] parts;
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		Player p = new Player();
		for (i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node instanceof Element) {
				NodeList player = node.getChildNodes();
				for (j = 0; j < player.getLength(); j++) {
					Node element = player.item(j);
					if (element instanceof Element) {
						switch(element.getNodeName()) {
						case "bombs":
							content = element.getLastChild().getTextContent().trim();
							Player.BOMBS = Integer.valueOf(content);
							break;
						case "lives":
							content = element.getLastChild().getTextContent().trim();
							Player.LIVES = Integer.valueOf(content);
							break;
						case "power":
							content = element.getLastChild().getTextContent().trim();
							Player.POWER = Integer.valueOf(content);
							break;
						case "hitboxRadius":
							content = element.getLastChild().getTextContent().trim();
							p.setHITBOX_RADIUS(Float.valueOf(content));
							break;
						case "grazeHitboxRadius":
							content = element.getLastChild().getTextContent().trim();
							p.setGRAZE_HITBOX_RADIUS(Float.valueOf(content));
							break;
						case "powerupHitboxRadius":
							content = element.getLastChild().getTextContent().trim();
							p.setPOWERUP_HITBOX_RADIUS(Float.valueOf(content));
							break;
						case "extraPowerupHitboxRadius":
							content = element.getLastChild().getTextContent().trim();
							p.setEXTRA_POWERUP_HITBOX_RADIUS(Float.valueOf(content));
							break;
						case "posx":
							content = element.getLastChild().getTextContent().trim();
							parts = content.split("\\+");
							for (String s : parts) {
								switch(s) {
									case "center":
										posx += (Main.LIMITRIGHT-Main.LIMITLEFT)/2 + Main.LIMITLEFT;
										break;
									case "limitbottom":
										break;
									case "limittop":
										break;
									case "limitright":
										posx += Main.LIMITRIGHT;
										break;
									case "limitleft":
										posx += Main.LIMITLEFT;
										break;
									default:
										posx += Float.valueOf(s);
										break;
								}
							}
							p.setPosX(posx);
							break;
						case "posy":
							content = element.getLastChild().getTextContent().trim();
							parts = content.split("\\+");
							for (String s : parts) {
								switch(s) {
									case "center":
										posy += (Main.LIMITBOTTOM-Main.LIMITTOP)/2 + Main.LIMITTOP;
										break;
									case "limitbottom":
										posy += Main.LIMITBOTTOM;
										break;
									case "limittop":
										posy += Main.LIMITTOP;
										break;
									case "limitright":
										break;
									case "limitleft":
										break;
									default:
										posy += Float.valueOf(s);
										break;
								}
							}
							p.setPosY(posy);
							break;
						case "speed":
							content = element.getLastChild().getTextContent().trim();
							p.setSpeed(Float.valueOf(content));
							break;
						case "maxPower":
							content = element.getLastChild().getTextContent().trim();
							p.setMaxPower(Integer.valueOf(content));
							break;
						case "animations":
							NodeList animations = element.getChildNodes();
							for (k = 0; k < animations.getLength(); k++) {
								Node animation = animations.item(k);
								if (animation instanceof Element) {
									content = animation.getLastChild().getTextContent().trim();
									Drawable d = AnimationManager.get().getAnimation(content);
									switch (animation.getNodeName()) {
										case "normal":
											p.addAnimation(d, Player.ACTIVE);
											break;
										case "focused":
											p.addAnimation(d, Player.FOCUSED);
											break;
										case "destroyed":
											p.addAnimation(d, Player.DESTROYED);
											break;
										}
								}
							}
							break;
						case "firePatterns":
							NodeList patterns = element.getChildNodes();
							for (k = 0; k < patterns.getLength(); k++) {
								Node pattern = patterns.item(k);
								if (pattern instanceof Element) {
									String powerS = ((Element) pattern).getAttributes().getNamedItem("power").getNodeValue();
									int power = Integer.valueOf(powerS);
									NodeList patternElements = pattern.getChildNodes();
									for (z = 0; z < patternElements.getLength(); z++) {
										Node patternElement = patternElements.item(z);
										if (patternElement instanceof Element) {
											content = patternElement.getLastChild().getTextContent().trim();
											switch(patternElement.getNodeName()) {
												case "fp":
													p.addFirePattern(power, content);
													break;
												case "shotDelay":
													p.addShotDelay(power, Integer.valueOf(content));
													break;
											}
										}
									}
								}
							}
							break;
						case "sounds":
							NodeList sounds = element.getChildNodes();
							for (k = 0; k < sounds.getLength(); k++) {
								Node sound = sounds.item(k);
								if (sound instanceof Element) {
									content = sound.getLastChild().getTextContent().trim();
									switch(sound.getNodeName()) {
									case "shot":
										p.addSound(content, Player.SHOT);
										break;
									case "destroyed":
										p.addSound(content, Player.DESTROYED);
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
		Play.pc.add(p);
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
									case "acceleration":
										cb.setAcceleration(content);
										break;
									case "maxSpeed":
										cb.setMaxSpeed(content);
										break;
									case "minSpeed":
										cb.setMinSpeed(content);
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
									case "trickyTime":
										cb.setTrickyTime(content);
										break;
									case "secondaryDirection":
										cb.setSecondaryDirection(content);
										break;
									case "secondarySpeed":
										cb.setSecondarySpeed(content);
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
													case "circle":
														parts = content.split(",");
														posx = Float.valueOf(parts[0]);
														posy = Float.valueOf(parts[1]);
														float radius = Float.valueOf(parts[2]);
														Circle cir = new Circle(posx, posy, radius);
														cb.addHitbox(cir);
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
	
	public void getBossFromXML(String fileName) throws Exception {
		File f = new File("res/xml/bosses/" + fileName);
		Document document = db.parse(f);
		Boss e = new Boss();
		String enemyID;
		String content;
		int k, j, i, z, i2, j2;
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
							case "name":
								content = cNode.getLastChild().getTextContent().trim();
								e.setName(content);
								break;
							case "invulnerableTime":
								content = cNode.getLastChild().getTextContent().trim();
								e.setInvulnerableDelay(Integer.valueOf(content));
							case "posx":
								content = cNode.getLastChild().getTextContent().trim();
								e.setPosX(Integer.parseInt(content));
								break;
							case "posy":
								content = cNode.getLastChild().getTextContent().trim();
								e.setPosY(Integer.parseInt(content));
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
							case "phases":
								NodeList phases = cNode.getChildNodes();
								for (k = 0; k < phases.getLength(); k++) {
									Node phase = phases.item(k);
									if (phase instanceof Element) {
										String phaseID = phase.getAttributes().getNamedItem("id").getNodeValue();
										e.addPhase(phaseID);
										NodeList phaseElements = phase.getChildNodes();
										for (z = 0; z < phaseElements.getLength(); z++) {
											Node phaseElement = phaseElements.item(z);
											if (phaseElement instanceof Element) {
												switch(phaseElement.getNodeName()) {
													case "music":
														content = phaseElement.getLastChild().getTextContent().trim();
														e.addMusicPhase(phaseID, content);
														break;
													case "health":
														content = phaseElement.getLastChild().getTextContent().trim();
														e.addHealthPhase(phaseID, Integer.valueOf(content));
														break;
													case "firePatterns":
														NodeList fireChilds = phaseElement.getChildNodes();
														for (i2 = 0; i2 < fireChilds.getLength(); i2++) {
															// Single fire pattern
															Node fpsNode = fireChilds.item(i2);
															if (fpsNode instanceof Element) {
																NodeList fpsChildNodes = fpsNode.getChildNodes();
																for (j2 = 0; j2 < fpsChildNodes.getLength(); j2++) {
																	Node fpNode = fpsChildNodes.item(j2);
																	if (fpNode instanceof Element) {
																		switch(fpNode.getNodeName()) {
																			case "fp":
																				content = fpNode.getLastChild().getTextContent().trim();
																				e.addPatternPhase(phaseID, content);
																				break;
																			case "shotDelay":
																				content = fpNode.getLastChild().getTextContent().trim();
																				e.addShotDelayPhase(phaseID, Integer.parseInt(content));
																				break;
																		}
																	}
																}
															}
														}
														break;
												}
											}
										}
									}
								}
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
										case "circle":
											parts = content.split(",");
											x = Float.valueOf(parts[0]);
											y = Float.valueOf(parts[1]);
											float radius = Float.valueOf(parts[2]);
											Circle cir = new Circle(x, y, radius);
											e.addHitbox(cir);
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
		BossesManager.get().addBoss(e);
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
										case "circle":
											parts = content.split(",");
											x = Float.valueOf(parts[0]);
											y = Float.valueOf(parts[1]);
											float radius = Float.valueOf(parts[2]);
											Circle cir = new Circle(x, y, radius);
											e.addHitbox(cir);
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
