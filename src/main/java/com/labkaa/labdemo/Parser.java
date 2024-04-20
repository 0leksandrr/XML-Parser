package com.labkaa.labdemo;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Parser {
    public static void parseXML(File file) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            System.out.println("Кореневий елемент: " + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getDocumentElement().getChildNodes();
            System.out.println("Кількість дочірніх вузлів: " + nList.getLength());
            System.out.println("----------------------------");
            for (int i = 0, l = nList.getLength(); i < l; i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    System.out.print("Child node #" + i + " - " + nNode.getNodeName() + ": ");
                    System.out.println(nNode.getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
