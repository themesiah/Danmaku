package mesiah.danmaku.xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Rectangle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import mesiah.danmaku.model.EnemiesManager;
import mesiah.danmaku.model.Enemy;
import mesiah.danmaku.model.patterns.CustomFirePattern;
import mesiah.danmaku.model.patterns.FirePattern;
import mesiah.danmaku.model.patterns.FirePatternManager;
import mesiah.danmaku.util.CurveManager;
import mesiah.danmaku.util.CustomCurve;
import mesiah.danmaku.view.AnimationManager;
import mesiah.danmaku.view.Drawable;

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
	
	public void getPatternFromXML(String fileName) throws Exception {
		File f = new File("res/xml/patterns/" + fileName);
		Document document = db.parse(f);
		CustomFirePattern fp = null;
		String patternID;
		String content;
		int i, j, k;
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
					Node bulletNode = nodeList.item(j);
					if (bulletNode instanceof Element) {
						NodeList bulletChildNodes = bulletNode.getChildNodes();
						for (k = 0; k < bulletChildNodes.getLength(); k++) {
							// Elementos de los bullets
						}
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
							case "shotDelay":
								content = cNode.getLastChild().getTextContent().trim();
								e.setShotDelay(Integer.parseInt(content));
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
													case "sound":
														content = fpNode.getLastChild().getTextContent().trim();
														e.addSound(content, Enemy.SHOT);
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
