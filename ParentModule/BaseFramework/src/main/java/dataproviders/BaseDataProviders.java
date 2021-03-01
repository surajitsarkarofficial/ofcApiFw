package dataproviders;

import java.util.LinkedList;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utils.Utilities;
import utils.xml.XMLParser;

public class BaseDataProviders {

	/**
	 * This method will extract the Data from the XML data file
	 * 
	 * @param document
	 * @param xpathExpression
	 * @return
	 * @throws Exception
	 */
	private static Object[][] extractDataFromXMLDataProvider(Document document, String xpathExpression) throws Exception {
		Object[][] dataObject = null;
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList nodeList = (NodeList) xPath.compile(xpathExpression).evaluate(document, XPathConstants.NODESET);
		int noOfDataSet = nodeList.getLength();
		int childNodes = nodeList.item(0).getChildNodes().getLength();

		for (int dataSetIndex = 0; dataSetIndex < noOfDataSet; dataSetIndex++) {
			NodeList dataSet = nodeList.item(dataSetIndex).getChildNodes();
			LinkedList<String> paramlist = new LinkedList<String>();
			for (int paramIndex = 0; paramIndex < childNodes; paramIndex++) {
				// Additional nodes gets added such as introduction to Whiteline space, new line
				// in the xml
				// Hence the line of code will only fetch data from the actual element nodes
				// ignoring junk nodes
				// and store them in a list.
				if (dataSet.item(paramIndex).getNodeType() == Node.ELEMENT_NODE) {
					paramlist.add(dataSet.item(paramIndex).getTextContent());
				}
			}
			// initialise the object array if not initialised (initiated in first iteration)
			if (dataObject == null) {
				dataObject = new Object[noOfDataSet][paramlist.size()];
			}
			// iterate the list and store the elements in the object array to return as data
			// provider object.
			int index = 0;
			for (String param : paramlist) {
				dataObject[dataSetIndex][index] = param;
				index++;
			}
		}
		return dataObject;
	}

	/**
	 * This method will extract and build the data provider object from the
	 * specified xml data file.
	 * 
	 * @param xmlFilePath
	 * @param xpathExpression
	 * @return
	 * @throws Exception
	 */
	public static Object[][] buildDataProviderObjectFromXML(String xmlFilePath, String xpathExpression) throws Exception {
		Document document = Utilities.loadXML(xmlFilePath);
		return extractDataFromXMLDataProvider(document, xpathExpression);
	}
	/**
	 * This method will extract each data set as a Map and build the data provider object from the
	 * specified xml data file.
	 * 
	 * @param xmlFilePath
	 * @param xpathExpression
	 * @return
	 * @throws Exception
	 */
	public static Object[][] buildDataProviderObjectFromXmlAsMap(String xmlFilePath, String xpathExpression) throws Exception {
		Document document = Utilities.loadXML(xmlFilePath);
		return extractDataFromXMLDataProviderWithTags(document, xpathExpression);
	}
	
	/**
	 * This method will extract the Data from the XML data file
	 * 
	 * @param document
	 * @param xpathExpression
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private static Object[][] extractDataFromXMLDataProviderWithTags(Document document, String xpathExpression) throws Exception {
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList nodeList = (NodeList) xPath.compile(xpathExpression).evaluate(document, XPathConstants.NODESET);
		int noOfDataSet = nodeList.getLength();
		//initialise the object array
		Object[][] dataObject = new Object[noOfDataSet][1];
		for (int dataSetIndex = 0; dataSetIndex < noOfDataSet; dataSetIndex++) {
			XMLParser xmlParser = new XMLParser();
			Map dataMap = xmlParser.parseNode(nodeList.item(dataSetIndex));
			dataObject[dataSetIndex][0] = dataMap;
		}
		return dataObject;
	}
}
