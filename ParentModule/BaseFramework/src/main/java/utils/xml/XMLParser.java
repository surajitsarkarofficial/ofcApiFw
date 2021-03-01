package utils.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser {

	/**
	 * This method will parse a node and convert it into map.
	 * 
	 * @param resultNode
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public Map parseNode(Node resultNode) {
		try {
			HashMap result = new HashMap();
			XMLParser.MyNodeList tempNodeList = new XMLParser.MyNodeList();

			String emptyNodeName = null, emptyNodeValue = null;
			// Node resultNode;
			NodeList dataSet = resultNode.getChildNodes();

			for (int index = 0; index < dataSet.getLength(); index++) {

				Node tempNode = dataSet.item(index);

				String nName = tempNode.getNodeName();
				if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
					tempNodeList.addNode(tempNode);
				}
				emptyNodeName = tempNode.getNodeName();
				emptyNodeValue = tempNode.getNodeValue();
			}
			if (tempNodeList.getLength() == 0 && emptyNodeName != null && emptyNodeValue != null) {
				result.put(emptyNodeName, emptyNodeValue);
				return result;
			}

			this.parseXMLNode(tempNodeList, result);
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Parse the node
	 * 
	 * @param nList
	 * @param result
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void parseXMLNode(NodeList nList, HashMap result) {
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE && nNode.hasChildNodes() && nNode.getFirstChild() != null
					&& (nNode.getFirstChild().getNextSibling() != null || nNode.getFirstChild().hasChildNodes())) {
				NodeList childNodes = nNode.getChildNodes();
				XMLParser.MyNodeList tempNodeList = new XMLParser.MyNodeList();
				for (int index = 0; index < childNodes.getLength(); index++) {
					Node tempNode = childNodes.item(index);
					if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
						tempNodeList.addNode(tempNode);
					}
				}
				HashMap dataHashMap = new HashMap();
				if (result.containsKey(nNode.getNodeName()) && result.get(nNode.getNodeName()) instanceof List) {
					List mapExisting = (List) result.get(nNode.getNodeName());
					mapExisting.add(dataHashMap);
				} else if (result.containsKey(nNode.getNodeName())) {
					List counterList = new ArrayList();
					counterList.add(result.get(nNode.getNodeName()));
					counterList.add(dataHashMap);
					result.put(nNode.getNodeName(), counterList);
				} else {
					result.put(nNode.getNodeName(), dataHashMap);
				}
				if (nNode.getAttributes().getLength() > 0) {
					Map attributeMap = new HashMap();
					for (int attributeCounter = 0; attributeCounter < nNode.getAttributes()
							.getLength(); attributeCounter++) {
						attributeMap.put(nNode.getAttributes().item(attributeCounter).getNodeName(),
								nNode.getAttributes().item(attributeCounter).getNodeValue());
					}
					dataHashMap.put("<<attributes>>", attributeMap);
				}
				this.parseXMLNode(tempNodeList, dataHashMap);
			} else if (nNode.getNodeType() == Node.ELEMENT_NODE && nNode.hasChildNodes()
					&& nNode.getFirstChild() != null && nNode.getFirstChild().getNextSibling() == null) {
				this.putValue(result, nNode);
			} else if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				this.putValue(result, nNode);
			}
		}
	}

	/**
	 * Put the value in the map
	 * 
	 * @param result
	 * @param nNode
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void putValue(HashMap result, Node nNode) {
		HashMap attributeMap = new HashMap();
		Object nodeValue = null;
		if (nNode.getFirstChild() != null) {
			nodeValue = nNode.getFirstChild().getNodeValue();
			if (nodeValue != null) {
				nodeValue = nodeValue.toString().trim();
			}
		}
		HashMap nodeMap = new HashMap();
		nodeMap.put("<<value>>", nodeValue);
		Object putNode = nodeValue;
		if (nNode.getAttributes().getLength() > 0) {
			for (int attributeCounter = 0; attributeCounter < nNode.getAttributes().getLength(); attributeCounter++) {
				attributeMap.put(nNode.getAttributes().item(attributeCounter).getNodeName(),
						nNode.getAttributes().item(attributeCounter).getNodeValue());
			}
			nodeMap.put("<<attributes>>", attributeMap);
			putNode = nodeMap;
		}
		if (result.containsKey(nNode.getNodeName()) && result.get(nNode.getNodeName()) instanceof List) {
			List mapExisting = (List) result.get(nNode.getNodeName());
			mapExisting.add(putNode);
		} else if (result.containsKey(nNode.getNodeName())) {
			List counterList = new ArrayList();
			counterList.add(result.get(nNode.getNodeName()));
			counterList.add(putNode);
			result.put(nNode.getNodeName(), counterList);
		} else {
			result.put(nNode.getNodeName(), putNode);
		}
	}
	/***
	 * Class MyNode
	 * @author surajit.sarkar
	 *
	 */
	class MyNodeList implements NodeList {
		List<Node> nodes = new ArrayList<Node>();
		int length = 0;

		public MyNodeList() {
		}

		public void addNode(Node node) {
			nodes.add(node);
			length++;
		}

		@Override
		public Node item(int index) {
			try {
				return nodes.get(index);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return null;
		}

		@Override
		public int getLength() {
			return length;
		}
	}

}
