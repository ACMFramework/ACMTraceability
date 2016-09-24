package utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/** Functionality specific to parsing Relations.xml files
 *
 */
public class RelationsXmlParser extends XmlParser
{
	/** The text content of the XML element
	 */
	private static final String XML_RELATIONS_ELEMENT = "Relation";
	private static final String XML_SOURCE_ELEMENT = "SourceNode";
	private static final String XML_TARGET_ELEMENT = "TargetNode";
	
	/** Get a list of relations contained in the XML file
	 * @param path
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public List<Relation> getRelsListFromXML(String path) throws ParserConfigurationException, SAXException, IOException
	{
		List<Relation> listOfRels = new ArrayList<Relation>();
		Element root = super.parseXMLFile(new File(path)).getDocumentElement();
		NodeList relations = root.getElementsByTagName(XML_RELATIONS_ELEMENT);
		
		if(relations != null && relations.getLength() > 0) 
		{
			for(int i = 0 ; i < relations.getLength(); i++) 
			{
				Element relationsElement = (Element)relations.item(i);
				Relation rels = getRelation(relationsElement);
				listOfRels.add(rels);
			}
		}
		return listOfRels;
	}

	/** Return a relation object with both ends of the relation
	 * @param Element
	 */
	private Relation getRelation(Element relation) 
	{
		String oneEnd = returnNodeTextValue(relation,XML_SOURCE_ELEMENT);
		String otherEnd = returnNodeTextValue(relation, XML_TARGET_ELEMENT);
		Relation newRelation = new Relation(oneEnd, otherEnd);
		return newRelation;
	}
}
