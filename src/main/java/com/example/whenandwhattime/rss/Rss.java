package com.example.whenandwhattime.rss;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Rss {

    public static void main(String[] args) {
        String path = "https://www.youtube.com/feeds/videos.xml?channel_id=UCCHH0nWYXFZmtDS_4tvMxHQ";
        parseXML(path);
    }

    public static void parseXML(String path) {
    	
        try {
            DocumentBuilderFactory  factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder         builder = factory.newDocumentBuilder();
            Document                document = builder.parse(path);
            Element                 root = document.getDocumentElement();

            /* Get and print Title of RSS Feed. */
            NodeList                channel = root.getElementsByTagName("author");
            NodeList                title = ((Element)channel.item(0)).getElementsByTagName("name");
            System.out.println("\nTitle: " + title.item(0).getFirstChild().getNodeValue() + "\n");

            /* Get Node list of RSS items */
            NodeList                item_list = root.getElementsByTagName("entry");
            for (int i = 0; i <5; i++) {
                Element  element = (Element)item_list.item(i);
                NodeList item_title = element.getElementsByTagName("yt:videoId");
                NodeList item_link  = element.getElementsByTagName("published");
                System.out.println(" videoId: " + item_title.item(0).getFirstChild().getNodeValue());
                System.out.println(" 配信予定:  " + item_link.item(0).getFirstChild().getNodeValue() + "\n");
            }
        } catch (IOException e) {
            System.out.println("IO Exception");
        } catch (ParserConfigurationException e) {
            System.out.println("Parser Configuration Exception");
        } catch (SAXException e) {
            System.out.println("SAX Exception");
        }
        return;
    }
}