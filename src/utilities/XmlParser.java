package utilities;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/** Basic XML parsing
 *
 */
public class XmlParser 
{
	/**
	 * DOM document
	 *
	 */
	Document doc;

	/**
	 * Read xml file and to return a DOM Document
	 *
	 */
	public Document readXmlFile(File file)
	{
		try 
		{
			File testFile = file;
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(testFile);
		}

		catch(ParserConfigurationException pce) 
		{
			pce.printStackTrace();
		}

		catch(SAXException se) 
		{
			se.printStackTrace();
		}

		catch(IOException ioe) 
		{
			ioe.printStackTrace();
		}

		return doc;
	}

	//Input xml element and tag name, return the text content 
	// E.g. This will return either 1 or 2 depending on the tagName
	// <Relation id="1">
	// <SourceNode>1</SourceNode>
	// <TargetNode>2</TargetNode>
	// </Relation>
	protected String returnNodeTextValue(Element element, String tagName) 
	{
		String textValueOfNode = null;
		NodeList nl = element.getElementsByTagName(tagName);

		if(nl != null && nl.getLength() > 0) 
		{
			Element el = (Element)nl.item(0);
			textValueOfNode = el.getFirstChild().getNodeValue();
		}
		return textValueOfNode;
	}
	
	/**
	 * Save parsed XML file
	 *
	 */
	public void saveFile(Document doc, String path) 
	{
		try 
		{
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(path));
			transformer.transform(source, result);
			System.out.println("File saved!");
		} 
		catch (TransformerException ex) 
		{
			ex.printStackTrace();
		}
	}	
	
	/**
	 * Create a new DOM Document
	 *
	 */
	public Document createNewDocument(String rootEl) throws ParserConfigurationException
	{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement(rootEl);
		doc.appendChild(rootElement);
		return doc;
	}
	
	/**
	 * Parse XML file
	 *
	 */
	public Document parseXMLFile(File fileToParse) throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
		Document doc = dbBuilder.parse(fileToParse);
		return doc;
	}
}
