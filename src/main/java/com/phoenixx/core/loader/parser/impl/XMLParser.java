package com.phoenixx.core.loader.parser.impl;

import com.phoenixx.core.loader.parser.AbstractParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 9:44 a.m. [09-05-2023]
 */
public class XMLParser extends AbstractParser<Document> {

    private Document doc;

    public XMLParser() {
        super();
    }

    @Override
    public void parse(InputStream stream) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            this.doc = dBuilder.parse(stream);
            this.doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    // Retrieve a list of elements by tag name
    public NodeList getElementsByTag(String tagName) {
        return doc.getElementsByTagName(tagName);
    }

    // Retrieve a map of elements by tag name and its text content
    public Map<String, String> getElementsMapByTag(String tagName) {
        Map<String, String> map = new HashMap<>();
        NodeList nodeList = doc.getElementsByTagName(tagName);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            map.put(element.getTagName(), element.getTextContent());
        }
        return map;
    }

    @Override
    public Document getDataObject() {
        return this.doc;
    }
}
