/*
    The GUFF - The GNU Ultimate Framework Facility
    Copyright (C) Simeosoft di Carlo Simeone
	
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA	
 */
package com.simeosoft.xml;

import com.simeosoft.string.StringUtils;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/** 
 * Xml utilities
 * @author Simeosoft
 * $Id: XmlUtils.java 28 2007-09-07 17:50:28Z simeo $
 *
 */
public abstract class XmlUtils {
    /** 
     * Writes a document to an xml file
     * @param fileName
     * @param doc 
     * @throws XmlException if errors
     */
    public static void saveXMLDocumentToFile(String fileName, org.w3c.dom.Document doc) throws XmlException {
        try {
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            Properties props = new Properties();
            props.put(OutputKeys.STANDALONE,"yes");
            props.put(OutputKeys.INDENT,"yes");
            props.put(OutputKeys.DOCTYPE_PUBLIC,"PUBLIC '-//simeosoft.com//TheGuff Project DTD//EN'");
            props.put(OutputKeys.DOCTYPE_SYSTEM,"project.dtd");
            props.put("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperties(props);
            DOMSource source = new DOMSource(doc);
            FileOutputStream fos = new FileOutputStream(fileName);
            StreamResult result = new StreamResult(fos);
            transformer.transform(source, result);            
            fos.flush();
            fos.close();             
        } catch (Exception e) {
            throw new XmlException(StringUtils.getStackTraceAsString(e));
        }          
    }
    
    /** 
     * Loads an xml file into a Document
     * @return Xml Document 
     * @throws XmlException if file or parse error found
     */
    public static Document loadXMLDocumentFromFile(String fileName) throws XmlException {
        Document doc = null;
        DocumentBuilderFactory factory =
        DocumentBuilderFactory.newInstance();
        try {        
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(fileName);
        } catch (SAXParseException e) {
            throw new XmlException(e.getMessage());
        } catch (SAXException e) {
            throw new XmlException(e.getMessage());
        } catch (ParserConfigurationException e) {
            throw new XmlException(e.getMessage());
        } catch (IOException e) {
            throw new XmlException(e.getMessage());
        }
        return doc;
    }
}
