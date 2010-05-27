package model;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class Mapfactory {
	public static void getMap(String mapsource) {
		try {

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File("maps/" + mapsource + ".tmx"));
            doc.getDocumentElement ().normalize ();
            NodeList listOfMaps = doc.getElementsByTagName("map");
            int totalMaps = listOfMaps.getLength();

            for(int s=0; s<listOfMaps.getLength() ; s++){


                Node firstMapNode = listOfMaps.item(s);
                if(firstMapNode.getNodeType() == Node.ELEMENT_NODE){
                	Element firstMapElement = (Element)firstMapNode;
                	
                	boolean solid = Boolean.valueOf(firstMapElement.getAttribute("solid")).booleanValue();
                	
                    NodeList firstNameList = firstMapElement.getElementsByTagName("data");
                    Element firstNameElement = (Element)firstNameList.item(0);

                    NodeList textFNList = firstNameElement.getChildNodes();
                    String encodedBytes = ((Node)textFNList.item(0)).getNodeValue().trim();
                }
            }
		} catch (SAXParseException err) {
        System.out.println ("** Parsing error" + ", line " 
             + err.getLineNumber () + ", uri " + err.getSystemId ());
        System.out.println(" " + err.getMessage ());

        }catch (SAXException e) {
        Exception x = e.getException ();
        ((x == null) ? e : x).printStackTrace ();

        }catch (Throwable t) {
        t.printStackTrace ();
        }
	}
}
