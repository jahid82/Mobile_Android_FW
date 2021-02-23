package utility;


import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;



import configuration.Constants;

public class XMLutils {

    public static Document readXmlFile(String XMLPath) throws Exception{
        
    	File fXmlFile = new File(XMLPath);
    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		
		return doc;

    }
    
    public static NodeList getTestSuite(Document doc){
    	
    	NodeList nListTestSuite = doc.getElementsByTagName("Component");
    	
    	System.out.println("Total Available Test cases are = "+ nListTestSuite.getLength());
    	
    	return nListTestSuite;
    	
    }
    
    public static NodeList getTestCaseDefined(Document doc) {
    
    	NodeList nListTestCase = doc.getElementsByTagName("TestCase");
    	    	
    	System.out.println("==========//==================//==================//==================//============");
    	return nListTestCase;
    }
    
    public static Element getTestCaseFromSuite(Node nTestSuite){
    	
    	Element eTestCase = (Element) nTestSuite;
    	
    	return eTestCase;
    	
    }

}
