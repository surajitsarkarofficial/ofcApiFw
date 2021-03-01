package utils.xml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.testng.SkipException;

import utils.UtilsBase;
import utils.file.SaveFileHelper;

/**
 * @author german.massello
 *
 */
public class XMLHelper extends UtilsBase{

	/**
	 * 
	 * This method will update a xml file.
	 * @param child
	 * @param xmlPath
	 * @param xmlName
	 * @param xmlFieldName
	 * @param xmlFieldValue
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static void updateXML(String child, String xmlPath, String xmlName, String xmlFieldName, String xmlFieldValue) throws JDOMException, IOException {
		try {
			log(xmlFieldName+" :"+xmlFieldValue);
			String[] xmlFields = new String[1], xmlValues = new String[1];
			xmlFields[0] = xmlFieldName;
			xmlValues[0] = xmlFieldValue;
			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File(xmlPath + xmlName);
			Document doc = (Document) builder.build(xmlFile);
			Element rootNode = doc.getRootElement();
			Element dataObjects = rootNode.getChild(child);
			List<Element> dataObjectElements = dataObjects.getChildren();
			
			Element dataObjectElement;
			for (int i = 0; i < xmlFields.length; i++) {
				for (Iterator<Element> it = dataObjectElements.iterator(); it.hasNext();) {
					dataObjectElement = (Element) it.next();
					if (dataObjectElement.getQualifiedName().trim().equals(xmlFields[i]))
					dataObjectElement.setText(xmlValues[i]);
				}
			}
			SaveFileHelper.saveFile(doc, xmlName, xmlPath);
		} catch (Exception e) {
			throw new SkipException("XMLHelper.updateXML() is not working");
		}
	}
	
	/**
	 * This method will clone a file
	 * @param sourceXml
	 * @param destinationXml
	 * @return File
	 */
	public static File cloneXML(String sourceXml, String destinationXml ) {
		File sourceXmlFile = new File(sourceXml);
		File destinationXmlFile = new File(destinationXml);
		try {
			Files.copy(sourceXmlFile.toPath(), destinationXmlFile.toPath());
		} catch (Exception e) {
			throw new SkipException("XMLHelper.cloneXML() is not working");
		}
		return destinationXmlFile;
	}
}
