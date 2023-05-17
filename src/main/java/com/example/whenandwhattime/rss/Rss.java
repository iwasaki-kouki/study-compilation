package com.example.whenandwhattime.rss;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import lombok.Data;

public class Rss {

    public static String path = "https://www.youtube.com/feeds/videos.xml?channel_id=";
	

    public static List<String> video_id = new ArrayList<>();
    public static List<String> title = new ArrayList<>();
    
    
    public static void parseXML(String path) {
    	
        try {
            DocumentBuilderFactory  factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder         builder = factory.newDocumentBuilder();
            Document                document = builder.parse(path);
            Element                 root = document.getDocumentElement();
            /* Get and print Title of RSS Feed. */
            NodeList                channel = root.getElementsByTagName("author");

            /* Get Node list of RSS items */
            NodeList                item_list = root.getElementsByTagName("entry");
			video_id.clear();
			title.clear();
            for (int i = 0; i <5; i++) {

                Element  element = (Element)item_list.item(i);
                NodeList item_id = element.getElementsByTagName("yt:videoId");
                video_id.add(item_id.item(0).getFirstChild().getNodeValue());
                NodeList item_title = element.getElementsByTagName("media:title");
                title.add(item_title.item(0).getFirstChild().getNodeValue());
                
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