package utilities;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/** 
 * Access and manipulate a GraphML file
 */
public class GraphMLProcessor
{
	/** GraphML specific constants
	 * 
	 */
	public final static String NODE_ELEMENT = "node";
	public final static String EDGE_ELEMENT = "edge";
	public final static String GRAPH_ELEMENT = "graph";
	public final static String DATA_ELEMENT = "data";
	public final static String KEY_ATTRIBUTE = "key";
	public final static String ID_DATA_KEY_VALUE_SHORT = "d8";
	public final static String NAME_DATA_KEY_VALUE_SHORT = "d0";
	public final static String TYPE_DATA_KEY_VALUE_SHORT = "d6";
	public final static String CONTENT_DATA_KEY_VALUE_SHORT = "d3";
	public final static String PARAM_DATA_KEY_VALUE_SHORT = "d4";
	public final static String RETURNTYPE_DATA_KEY_VALUE_SHORT = "d5";
	public final static String VISIBILITY_DATA_KEY_VALUE_SHORT = "d1";
	public final static String VARIABLETYPE_DATA_KEY_VALUE_SHORT = "d2";
	public final static String RELTYPE_DATA_KEY_VALUE_SHORT = "d7";
	public final static String IMPEXT_DATA_KEY_VALUE_SHORT = "d9";
	public final static String TITLE_DATA_KEY_VALUE_SHORT = "d11";
	public final static String PRIORITY_DATA_KEY_VALUE_SHORT = "d12";
	public final static String REQTYPE_DATA_KEY_VALUE_SHORT = "d13";
	public final static String ID_DATA_KEY_VALUE = "key=\"d8\"";
	public final static String NAME_DATA_KEY_VALUE = "key=\"d0\"";
	public final static String TYPE_DATA_KEY_VALUE = "key=\"d6\"";
	public final static String PARAM_DATA_KEY_VALUE = "key=\"d4\"";
	public final static String IMPEXT_DATA_KEY_VALUE = "key=\"d9\"";
	public static final String EDGE_ID_PREFIX = "Intra_Link";
	public static final String REQ_ID_PREFIX = "rq";
	public static final String DESIGN_ID_PREFIX = "di";
	public static final String SC_ID_PREFIX = "sc";
	public static final String UNIT_TEST_ID_PREFIX = "ut";
	public static final String ARCHITECTURE_ID_PREFIX = "ar";
	public static final String USE_CASE_ID_PREFIX = "uc";
	public static final String SEQUENC_DIAGRAM_ID_PREFIX = "sd";
	public static final String DOCUMENTATION_ID_PREFIX = "dc";
	public static final String API_ID_PREFIX = "ap";
	public static final String CONFIG_FILE_ID_PREFIX = "ap";
	public static final String TYPE_CLASS = "class";
	public static final String TYPE_METHOD = "method";
	public static final String TYPE_FIELD = "field";
	public static final String TYPE_INTERFACE = "interface";
	public static final String TYPE_REQUIREMENT = "requirement";
	public static final String TYPE_USE_CASE = "usecase";
	public static final String TYPE_USE_CASE_OBJECT_CLASS = "UseCase_Object_Class";
	public static final String TYPE_COMPONENT = "Component";
	public static final String TYPE_MODULE = "Module";
	public static final String TYPE_UML_ATT = "UMLAttribute";
	public static final String TYPE_UML_OP = "UMLOperation";

	/** The max unique id value in a graphml file
	 * 
	 */
	private String maxUniqueId;

	/**
	 * @return the maximum unique id from the given GraphML file
	 */
	public String getMaxUniqueId() 
	{
		return returnMaxUniqueId();
	}

	/** 
	 * 
	 */
	private int numberOfNodes;

	/** Get number of node elements
	 * 
	 */
	public int getNumberOfNodes() 
	{
		return returnNodeNodeList().getLength();
	}

	/** 
	 * 
	 */
	private int numberOfEdges;

	/**
	 * @return the number of edges
	 */
	public int getNumberOfEdges() 
	{
		return returnEdgeNodeList().getLength();
	}

	/**
	 * 
	 */
	private String path = ""; 

	/**
	 * @return the path
	 */
	public String getPath() 
	{
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) 
	{
		this.path = path;
	}

	/** XML Parsing
	 * 
	 */
	private static XmlParser parser = new XmlParser();

	/** DOM Document representation of the GraphML file
	 * 
	 */
	Document doc;

	/** TODO: delete?
	 * 
	 */
	public GraphMLProcessor() 
	{
	}

	/** Constructor sets the file path of the graphml file and parses it to a DOM document
	 * 
	 */
	public GraphMLProcessor(String filePath) 
	{
		setPath(filePath);
		doc = parser.readXmlFile(new File(path));
	}

	int counter = 0;

	/** Parse XML file and return a NodeList of Node elements
	 * 
	 */
	public NodeList returnNodeNodeList() 
	{
		counter++;
		NodeList allNodesList = doc.getElementsByTagName(NODE_ELEMENT);
		return allNodesList;
	}

	/** Return a NodeList of Edge elements
	 * 
	 */
	public NodeList returnEdgeNodeList() 
	{
		NodeList allEdgesList = doc.getElementsByTagName(EDGE_ELEMENT);
		return allEdgesList;
	}

	/** Return a NodeList of Data elements
	 * 
	 */
	public NodeList returnDataNodeList() 
	{
		NodeList datakeyList = null;
		for(int i = 0; i < returnNodeNodeList().getLength(); i++) 
		{
			Element nodes = (Element) returnNodeNodeList().item(i);
			datakeyList = nodes.getElementsByTagName(DATA_ELEMENT);
		}
		return datakeyList;
	}

	/** Return a NodeList of Data elements
	 * 
	 */
	public NodeList returnDataElementNodeList() 
	{
		NodeList nodeList = doc.getElementsByTagName(DATA_ELEMENT);
		return nodeList;
	}

	/** Return node based on unique id
	 * 
	 */
	public Node getNodeBasedOnUniqueId(String property, String value) 
	{
		return getSpecificNode(ID_DATA_KEY_VALUE, value);
	}

	/** Method returning the matching node based on
	 * @param path
	 * @param unique id
	 */
	public Node getNodeFromUniqueId(String path, String uniqueId) 
	{
		Document doc = parser.readXmlFile(new File(path));
		NodeList allNodesList = doc.getElementsByTagName(NODE_ELEMENT);
		Node nodeToReturn = null;

		try {
			for(int i = 0; i < allNodesList.getLength(); i++) 
			{
				Element nodes = (Element) allNodesList.item(i);

				NodeList datakeyList = nodes.getElementsByTagName(DATA_ELEMENT);
				for (int j = 0; j < datakeyList.getLength(); j++)
				{
					Element data = (Element) datakeyList.item(j);
					if(data.getAttributes().item(0).toString().equals(ID_DATA_KEY_VALUE)
							&& datakeyList.item(j).getTextContent().toString().equals(uniqueId)) 
					{
						nodeToReturn = allNodesList.item(i);
					}
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			e.getMessage();
			System.out.println("Check unique id exists in graphml file");
		}

		return nodeToReturn;
	}

	/** Return node based on name - note: there can be multiple results
	 * 
	 */
	public Node getNodeBasedOnNameAndType(String value, String value2) 
	{
		Node node = null;
		NodeList nodes = returnNodeNodeList();
		try 
		{
			for(int i = 0; i < nodes.getLength(); i++) 
			{
				Element el = (Element) nodes.item(i);
				NodeList datakeyList = el.getElementsByTagName(DATA_ELEMENT);
				for (int j = 0; j < datakeyList.getLength(); j++)
				{
					Element data = (Element) datakeyList.item(j);
					if(data.getAttributes().item(0).toString().equals(NAME_DATA_KEY_VALUE)
							&& datakeyList.item(j).getTextContent().toString().equals(value)) 
					{
						System.out.println("NAME: " + datakeyList.item(j).getTextContent());
						if(data.getAttributes().item(0).toString().equals(TYPE_DATA_KEY_VALUE)
								&& datakeyList.item(j).getTextContent().toString().equalsIgnoreCase(value2)) 
						{
							System.out.println("TYPE: " + datakeyList.item(j).getTextContent());
							node = nodes.item(i);
						}
					}
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			e.getMessage();
			System.out.println("Check attribute exists in graphml file");
		}

		return node;
	}

	/** Return node based on implements / extends property - if it is not empty
	 * 
	 */
	public Node getNodeBasedOnExtendsImplements() 
	{
		Node node = null;
		NodeList nodes = returnNodeNodeList();
		try 
		{
			for(int i = 0; i < nodes.getLength(); i++) 
			{
				Element el = (Element) nodes.item(i);
				NodeList datakeyList = el.getElementsByTagName(DATA_ELEMENT);
				for (int j = 0; j < datakeyList.getLength(); j++)
				{
					Element data = (Element) datakeyList.item(j);
					if(data.getAttributes().item(0).toString().equals(IMPEXT_DATA_KEY_VALUE)
							&& !datakeyList.item(j).getTextContent().toString().isEmpty()) 
					{
						node= nodes.item(i);
					}
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			e.getMessage();
			System.out.println("Check attribute exists in graphml file");
		}
		return node;
	}

	/** Return text content of a name and implementsExtends properties of a node
	 * 
	 * @param node
	 * @return
	 */
	public String getNodeNameAndImpExt(Node node) 
	{
		String properties = null;
		if(node!=null) 
		{
			if(node.getChildNodes().item(9) != null) 
			{
				properties = node.getChildNodes().item(1).getTextContent() + "/" + node.getChildNodes().item(9).getTextContent();
			}
		}

		return properties;
	}

	/** Return node where the specified data key attribute's value matches value passed in
	 * Note: the only unique attribute value of each node is the unique id. Queries using other
	 * attribute values are not guaranteed to return a single node
	 * 
	 */
	public Node getSpecificNode(String property, String value) 
	{
		Node result = null;
		NodeList nodes = returnNodeNodeList();
		try 
		{
			for(int i = 0; i < nodes.getLength(); i++) 
			{
				Element el = (Element) nodes.item(i);
				NodeList datakeyList = el.getElementsByTagName(DATA_ELEMENT);
				for (int j = 0; j < datakeyList.getLength(); j++)
				{
					Element data = (Element) datakeyList.item(j);
					if(data.getAttributes().item(0).toString().equals(property)
							&& datakeyList.item(j).getTextContent().toString().equals(value)) 
					{
						result = nodes.item(i);
					}
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			e.getMessage();
			System.out.println("Check attribute exists in graphml file");
		}

		return result;
	}

	/** Find a node based on the vaues of its name and type data key elements
	 * 
	 * @param nameValue
	 * @param typeValue
	 * @return
	 */
	public Node findNodeBasedOnNameAndType(String nameValue, String typeValue) 
	{
		Node node = null;
		NodeList nodes = returnNodeNodeList();
		try 
		{
			for(int i = 0; i < nodes.getLength(); i++) 
			{
				boolean nameMatch = false;
				boolean typeMatch = false;
				Element el = (Element) nodes.item(i);
				NodeList datakeyList = el.getElementsByTagName(DATA_ELEMENT);
				for (int j = 0; j < datakeyList.getLength(); j++)
				{
					Element data = (Element) datakeyList.item(j);
					
					if(data.getAttributes().item(0).toString().equals(NAME_DATA_KEY_VALUE)
							&& datakeyList.item(j).getTextContent().toString().equals(nameValue))
					{
						nameMatch = true;
					}
					if(data.getAttributes().item(0).toString().equals(TYPE_DATA_KEY_VALUE)
							&& datakeyList.item(j).getTextContent().toString().equalsIgnoreCase(typeValue))
					{
						typeMatch = true;
					}
					if(typeMatch && nameMatch)
					{
						node = nodes.item(i);
					}
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return node;
	}

	/** Set unique id of specific node where the name and type data key values match values passed in
	 * Since in source code, UML and unit test artefacts this may return duplicates (constructors) - 
	 * for these, types parameters are also checked
	 * 
	 */
	public Node setUniqueIdBasedOnNameAndType(String nameValue, String typeValue, String optionalParamValue, String uniqueId) 
	{
		// First find specific node
		// Then set unique id to value passed in
		Node node = null;
		NodeList nodes = returnNodeNodeList();
		try 
		{
			for(int i = 0; i < nodes.getLength(); i++) 
			{
				boolean nameMatch = false;
				boolean typeMatch = false;
				boolean paramMatch = false;
				Element el = (Element) nodes.item(i);
				NodeList datakeyList = el.getElementsByTagName(DATA_ELEMENT);
				for (int j = 0; j < datakeyList.getLength(); j++)
				{
					Element data = (Element) datakeyList.item(j);

					if(data.getAttributes().item(0).toString().equals(NAME_DATA_KEY_VALUE)
							&& datakeyList.item(j).getTextContent().toString().equals(nameValue))
					{
						nameMatch = true;
					}
					if(data.getAttributes().item(0).toString().equals(TYPE_DATA_KEY_VALUE)
							&& datakeyList.item(j).getTextContent().toString().equalsIgnoreCase(typeValue))
					{
						typeMatch = true;
					}
					if(typeMatch && nameMatch)
					{
						node = nodes.item(i);
						setUniqueIdOfNode(node, uniqueId);
					}
				}

			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return node;
	}
	
	/** Get the first node of the nodeList
 	 * 
	 * @return
	 */
	public Node getFirstNodeInFile() 
	{
		NodeList nodes = returnNodeNodeList();
		Node firstNode = nodes.item(0);
		return firstNode;
	}

	/** Method returning the value of a data key element of a single node
	 * @param node - the node passed in
	 */
	public String getDataKeyValue(Node node, String dataKey) 
	{
		String value = null;
		NodeList children = node.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) 
		{
			// Not all child nodes have attributes
			if(children.item(i).hasAttributes()) 
			{
				if(children.item(i).getAttributes().getNamedItem(KEY_ATTRIBUTE).toString().equals(dataKey))
				{	
					value = children.item(i).getTextContent();
				}
			}
		}
		return value;
	}

	/** Get the number of empty unique id data key values
	 * 
	 * @return
	 */
	public int getNoOfNodesWithEmptyId() 
	{
		NodeList nodes = returnNodeNodeList();
		int counter = 0;
		try 
		{
			for(int i = 0; i < nodes.getLength(); i++) 
			{
				Element el = (Element) nodes.item(i);
				NodeList datakeyList = el.getElementsByTagName(DATA_ELEMENT);
				for (int j = 0; j < datakeyList.getLength(); j++)
				{
					Element data = (Element) datakeyList.item(j);
					if(data.getAttributes().item(0).toString().equals(ID_DATA_KEY_VALUE)
							&& datakeyList.item(j).getTextContent().toString().equals("")) 
					{
						counter++;
					}
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return counter;
	}

	/** Set empty unique id to new value
	 * @param new value the empty unique id is set to
	 */
	public Node setEmptyUniqueIdToNewValue(String newUniqueId) 
	{
		// First find node with empty unique id
		// Second, update it with new unique id passed in

		Node node = null;
		NodeList nodes = returnNodeNodeList();
		try 
		{
			for(int i = 0; i < nodes.getLength(); i++) 
			{
				Element el = (Element) nodes.item(i);
				NodeList datakeyList = el.getElementsByTagName(DATA_ELEMENT);
				for (int j = 0; j < datakeyList.getLength(); j++)
				{
					Element data = (Element) datakeyList.item(j);
					if(data.getAttributes().item(0).toString().equals(ID_DATA_KEY_VALUE)
							&& datakeyList.item(j).getTextContent().toString().equals("")) 
					{
						node = nodes.item(i);
						setUniqueIdOfNode(node, newUniqueId);
					}
				}
			}
			//parser.saveFile(doc, path);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return node;
	}

	/** Method returning the value of a data key="d8" element of a single node
	 * @param node - the node passed in
	 */
	public String getUniqueIdOfNode(Node node) 
	{
		return getDataKeyValue(node, ID_DATA_KEY_VALUE);
	}

	/** Set the value of the data key="d8" element of a single node
	 * 
	 * @param node
	 * @param uniqueId
	 */
	public void setUniqueIdOfNode(Node node, String uniqueId) 
	{
		NodeList children = node.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) 
		{
			// Not all child nodes have attributes
			if(children.item(i).hasAttributes()) 
			{
				if(children.item(i).getAttributes().getNamedItem(KEY_ATTRIBUTE).toString().equals(ID_DATA_KEY_VALUE))
				{
					children.item(i).setTextContent(uniqueId);
				}
			}
		}

		parser.saveFile(doc, path);
	}

	
	/** Return all the nodes in the given graphml file as an arraylist
	 * 
	 */
	public ArrayList<Node> getAllNodesAsList() 
	{
		ArrayList<Node> nodesToReturn = new ArrayList<Node>();
		for(int i = 0; i < returnNodeNodeList().getLength(); i++) 
		{
			nodesToReturn.add(returnNodeNodeList().item(i));
		}
		return nodesToReturn;
	}

	/** Method returning all the nodes in the given graphml file
	 * @param path - the file path
	 */
	public ArrayList<Node> getAllNodes(String path) 
	{
		Document doc = parser.readXmlFile(new File(path));
		NodeList allNodesList = doc.getElementsByTagName(NODE_ELEMENT);
		ArrayList<Node> nodesToReturn = new ArrayList<Node>();

		for(int i = 0; i < allNodesList.getLength(); i++) 
		{
			nodesToReturn.add(allNodesList.item(i));
		}
		return nodesToReturn;
	}

	/** Populate a list with node id attribute values
	 */
	public ArrayList<String> getNodeIds()
	{
		ArrayList<String> ids = new ArrayList<String>();
		for(int i = 0; i < returnNodeNodeList().getLength(); i++)
		{
			ids.add(returnNodeNodeList().item(i).getAttributes().item(0).getTextContent());
		}
		return ids;
	}

	/**
	 * Populate a list with unique ids found in GraphML file
	 */
	public ArrayList<String> getUniqueIds() 
	{
		ArrayList<String> uniqueIds = new ArrayList<String>();
		ArrayList<Node> nodes = getAllNodesAsList();

		for(int i = 0; i < nodes.size(); i++) 
		{
			uniqueIds.add(getUniqueIdOfNode(nodes.get(i)));
		}

		// Remove empty ids
		uniqueIds.removeAll(Collections.singleton(null));
		uniqueIds.removeAll(Collections.singleton(""));
		return uniqueIds;
	}

	/**
	 * Return id attribute text content from the graph node
	 */
	public String getGraphNodeIdAttribute() 
	{
		NodeList nodeList = doc.getElementsByTagName(GraphMLProcessor.GRAPH_ELEMENT);
		nodeList.item(0).getAttributes();
		return nodeList.item(0).getAttributes().item(1).getTextContent(); 
	}

	/**Method to set the unique id of all nodes in a graphml file
	 * @param listOfUniqueIds
	 * List containing unique id values
	 */
	public void updateUniqueIdOfAllNodes(ArrayList<String> listOfUniqueIds)
	{
		ArrayList<Node> listOfDataKeyNodes = new ArrayList<Node>();
		NodeList dataNodes = returnDataElementNodeList();
		for(int i = 0; i < dataNodes.getLength(); i++)
		{
			// The first item in the NamedNodeMap collection returned by getAttributes is the key attribute
			// In graphml files the data key value is d8
			if(dataNodes.item(i).getAttributes().item(0).toString().equals(ID_DATA_KEY_VALUE))
			{
				Node dataKeyNode = dataNodes.item(i);
				listOfDataKeyNodes.add(dataKeyNode);
			}
		}

		for(int i = 0; i < listOfDataKeyNodes.size(); i++)
		{
			for(int j = 0; j < listOfUniqueIds.size(); j++)
			{
				// Set the value of the data key attribute to the value extracted from the list of generated unique ids
				listOfDataKeyNodes.get(i).setTextContent(listOfUniqueIds.get(i));
			}
		}
		parser.saveFile(doc, path);
	}

	/** Method to set the id attribute of all node elements to unique id values
	 * @param listOfUniqueIds
	 * List containing the generated unique ids
	 */
	public void updateNodeIds(ArrayList<String> listOfUniqueIds)
	{
		NodeList listOfNodes = returnNodeNodeList();

		for(int i = 0; i < listOfNodes.getLength(); i++)
		{
			for(int j = 0; j < listOfUniqueIds.size(); j++)
			{
				// Set the value of the id attribute to the value extracted from the list of generated unique ids
				listOfNodes.item(i).getAttributes().item(0).setTextContent(listOfUniqueIds.get(i));
			}
		}
		parser.saveFile(doc, path);
	}


	/**Method to set the target id of edges in a graphml file
	 * @param listOfEdgeIds
	 * List of edge id values
	 */
	public void updateEdgeIds(ArrayList<String> listOfEdgeIds)
	{
		NodeList edgeList = returnEdgeNodeList();
		for(int i = 0; i < edgeList.getLength(); i++)
		{
			for(int j = 0; j < listOfEdgeIds.size(); j++)
			{
				// Set the value of the data key attribute to the value extracted from the list of generated unique ids
				edgeList.item(i).getAttributes().item(0).setTextContent(listOfEdgeIds.get(i));
			}
		}
		parser.saveFile(doc, path);
	}

	/**Method to set the source id of edges in a graphml file to a single value
	 * @param value
	 * 
	 */
	public void updateEdgeSourceAttribute(String value)
	{
		NodeList edgeList = returnEdgeNodeList();
		for(int i = 0; i < edgeList.getLength(); i++)
		{
			// Set the source attribute to specific value
			edgeList.item(i).getAttributes().item(1).setTextContent(value);
		}
		parser.saveFile(doc, path);
	}

	/**Method to set the source and target id of edge elements
	 * @param values
	 * The id attribute values of all but the first node in the graphml file
	 */
	public void setEdgeSourceAndTargetAttributes(ArrayList<String> values)
	{
		NodeList edgeList = returnEdgeNodeList();
		for(int i = 0; i < edgeList.getLength(); i++)
		{
			// Set the source attribute to specific value
			edgeList.item(i).getAttributes().item(1).setTextContent(values.get(0));

			for(int j = 1; j < values.size(); j++)
			{
				// Set the value of the target attribute to the id values of nodes extracted from 
				// the graphml file starting from node 2
				edgeList.item(i).getAttributes().item(2).setTextContent(values.get(i+1));
			}
		}
		parser.saveFile(doc, path);
	}


	//****************************************************************
	// Utility methods
	//****************************************************************

	

	/** Method to return the index of the last digit character in the given string
	 * @param theString - the string which is checked
	 */
	private static int getLastDigitIndex(String theString) 
	{
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		//Split string into characters
		String[] stringArray = theString.split("(?!^)");

		if(!theString.isEmpty()) 
		{
			// Check if each individual char is a digit
			// if it is, add its array index to a list so then the max index can be returned
			for(int i = 0; i < stringArray.length; i++) 
			{
				if (Character.isDigit(stringArray[i].charAt(0))) 
				{
					indexes.add(i);
				}
			}
			return Collections.max(indexes);
		}
		else
			return 0;
	}

	/** Method to return digits from a given string, e.g. di0Path
	 * @param theString - the string
	 * @param lastDigitIndex - the index number of the last digit in the string
	 */
	private static String getDigitsSubstring(String theString, int lastDigitIndex) 
	{
		if(!theString.isEmpty())
			return theString.substring(2, lastDigitIndex+1);
		else
			return null;
	}

	/** Method to sort items of a String ArrayList
	 *  - items are first parsed to integer and sorting is achieved using the integer values
	 *  - to achieve this, a custom comparator is used
	 * @param ids - a string ArrayList containing the items to be sorted
	 */
	private static ArrayList<String> sortStringCollection(ArrayList<String> ids) 
	{
		Collections.sort(ids, new Comparator<String>() 
				{
			public int compare(String a, String b) 
			{
				return Integer.signum(fixString(a) - fixString(b));
			}
			private int fixString(String in) 
			{
				return Integer.parseInt(in);
			}
				});
		return ids;
	}

	/** Return the max unique id from a given graphml file
	 *
	 */
	public String returnMaxUniqueId() 
	{
		String maxId = null;
		ArrayList<String> ids = new ArrayList<String>();
		ArrayList<String> digitPartOfIds = new ArrayList<String>();
		ids =  getUniqueIds();
		if(!ids.isEmpty())
		{
			for(String el : ids) 
			{
				digitPartOfIds.add(getDigitsSubstring(el, getLastDigitIndex(el)));
			}
			//Max id will be the last element of the sorted collection
			maxId = sortStringCollection(digitPartOfIds).get(digitPartOfIds.size()-1);
		}
		return maxId;
	}


	// Well formedness checks: are all unique ids unique, are all node ids unique and they match the unique id of the same
	// node, are all edges unique, 

	/** Method to append nodes and edges to a graphml file from another graphml file
	 * @param importDocPath - the path of the graphml file from where the node originates
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public void addNewNodesAndEdges(String importDocPath) 
			throws ParserConfigurationException, SAXException, IOException 
			{
		GraphMLProcessor processor = new GraphMLProcessor(importDocPath);
		NodeList allNodesList = processor.returnNodeNodeList();
		NodeList allEdgesList = processor.returnEdgeNodeList();
		Node importedNode = null;
		try 
		{
			Element root = doc.getDocumentElement();
			Element graphTag =  (Element) root.getElementsByTagName(GRAPH_ELEMENT).item(0);
			for(int i = 0; i < allNodesList.getLength(); i ++) 
			{
				importedNode = allNodesList.item(i);
				Node firstDocImportedNode = doc.adoptNode(importedNode);
				graphTag.appendChild(firstDocImportedNode);
			}

			for(int i = 0; i < allEdgesList.getLength(); i ++) 
			{
				importedNode = allEdgesList.item(i);
				Node firstDocImportedNode = doc.adoptNode(importedNode);
				graphTag.appendChild(firstDocImportedNode);
			}

			parser.saveFile(doc, path);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			e.getMessage();
		}
	}
}
