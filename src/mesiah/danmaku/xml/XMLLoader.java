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

import mesiah.danmaku.model.Enemy;
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
	
	public Enemy getEnemyFromXML(String fileName) throws Exception {
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
		return e;
	}
}
