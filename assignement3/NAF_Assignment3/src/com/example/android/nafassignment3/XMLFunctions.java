package com.example.android.nafassignment3;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


public class XMLFunctions {
	
	public static org.w3c.dom.Document stringToDocument(String result){
    	
    	
    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder parser;
    	org.w3c.dom.Document dest = null;
    	
    	try {
    		parser = dbFactory.newDocumentBuilder();
    		dest = parser.parse(new ByteArrayInputStream(result.getBytes()));
    		dest.getDocumentElement ().normalize ();
    	} catch (ParserConfigurationException e1) {
    		e1.printStackTrace();
    		System.err.println ("** Error: " + e1.toString());	
    	} catch (SAXException e) {
    		  Exception x = e.getException ();
    		  ((x == null) ? e : x).printStackTrace ();
    	} catch (IOException e) {
    		e.printStackTrace();
    		System.err.println ("** Error: " + e.toString());
    	}
    	
    	return dest;	
    }	 
}
