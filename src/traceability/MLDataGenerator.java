package traceability;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import utilities.Element;
import utilities.ElementTypes;
import utilities.GraphMLProcessor;
import utilities.IDGenerator;
import utilities.Relation;
import utilities.RelationsXmlParser;

/**
 * Utility class for automating data creation for traceability from relationships and non relationships
 * originally stored in XML and GraphML files
 */
public class MLDataGenerator 
{
	/**
	 * 
	 */
	private RelationsXmlParser relProcessor = new RelationsXmlParser();
	
	/**
	 * 
	 */
	private static GraphMLProcessor handler = new GraphMLProcessor();
	
	/**
	 * 
	 */
	ElementTypes types = new ElementTypes();

	/**
	 * Return a list of relation objects from a relations xml file
	 * @param path - path of the relations xml file
	 * 
	 */
	public List<Relation> getRelationsFromFile(String path)
	{
		List<Relation> relationsList = null;
		try 
		{
			relationsList = relProcessor.getRelsListFromXML(path);
		} catch (ParserConfigurationException | SAXException | IOException e) 
		{
			e.printStackTrace();
		}
		return relationsList;
	}

	 /** Return the path part of a unique id
	 * @param uniqueId
	 * 
	 */
	public String returnPathFromUniqueId(String uniqueId)
	{
		String [] result = uniqueId.split("C:", 2);
		return result[1];
	}

	/** 
	 * Method to get the name, id and type of the individual elements making up a data instance
	 * @param path - path of the relations XML file
	 * In the graphml file the name comes from d0, which is always the second child of the node
	 * The rest of the children vary, hence type can be retrieved by specifying its data key value: d6
	 */
	public List<Relation> getRelationDataInstanceDetails(String path) 
	{
		List<Relation> list = getRelationsFromFile(path);
		try 
		{
			for(int i = 0; i < list.size(); i++) 
			{
				Node sourceNode = 
						handler.getNodeFromUniqueId(returnPathFromUniqueId(list.get(i).getSourceId()), list.get(i).getSourceId());
				Node targetNode = 
						handler.getNodeFromUniqueId(returnPathFromUniqueId(list.get(i).getTargetId()), list.get(i).getTargetId());

				// Obtain source node and target node name (d0) and type (d6) from node object and set these values 
				// in the relation object
				list.get(i).setSourceElement(new Element
						(sourceNode.getChildNodes().item(1).getTextContent(), "", 
								handler.getDataKeyValue(sourceNode, GraphMLProcessor.TYPE_DATA_KEY_VALUE)));

				list.get(i).setTargetElement(new Element
						(targetNode.getChildNodes().item(1).getTextContent(), "", handler.getDataKeyValue(sourceNode, GraphMLProcessor.TYPE_DATA_KEY_VALUE)));
			}
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			e.getMessage();
			System.out.println("Check unique id exists in graphml file");
		}
		return list;
	}

	/** 
	 * Method to calculate the features of a data instance irrespective of the data
	 * FEATURES: Levenshtein distance, isContainer element, abstraction level separation, 
	 * is source element requirement, is source element diagram, is source element source code,
	 * is source element unit test, is target element requirement, is target element diagram,
	 * is target element source code, is target element unit test
	 * related/non related flag, provenance flag
	 * @param rawData - the data extracted from graphml files
	 * @param relatedFlag - to specify if the given data instance depicts a relation or non-relation
	 */
	public List<String> buildTrainingData(List<Relation> rawData, boolean relatedFlag) 
	{
		List<String> data = new ArrayList<String>();
		int related = ((relatedFlag == true) ? 1 : 0);

		for(int i = 0; i < rawData.size(); i++) 
		{
			data.add(
					rawData.get(i).getSourceId()
					+ ","
					+ rawData.get(i).getTargetId()
					+ ","
					+ Levenshtein.returnDistanceInPercentage(rawData.get(i).getSourceElement().getName(), 
							rawData.get(i).getTargetElement().getName()) 
							+ ","
							+ isContainer(rawData.get(i).getSourceElement().getType())
							+ ","
							+ isContainer(rawData.get(i).getTargetElement().getType())
							+ ","
							+ enumerateAbstractionLevelSeparation(rawData.get(i).getSourceId(), rawData.get(i).getTargetId())
							+ ","
							+ isRequirement(rawData.get(i).getSourceId())
							+ ","
							+ isDesign(rawData.get(i).getSourceId())
							+ ","
							+ isArchitecture(rawData.get(i).getSourceId())
							+ ","
							+ isSourceCode(rawData.get(i).getSourceId())
							+ ","
							+ isUnitTest(rawData.get(i).getSourceId())
							+ ","
							+ isRequirement(rawData.get(i).getTargetId())
							+ ","
							+ isDesign(rawData.get(i).getTargetId())
							+ ","
							+ isArchitecture(rawData.get(i).getTargetId())
							+ ","
							+ isSourceCode(rawData.get(i).getTargetId())
							+ ","
							+ isUnitTest(rawData.get(i).getTargetId())
							+ ","
							+ related);
		}
		return data;
	}

	/** 
	 * Method to calculate the features of a data instance to build test data = data that does not contain
	 * a labelled feature
	 * FEATURES: Levenshtein distance, is source container, is target container, abstraction level separation,
	 * is source element requirement, is source element diagram, is source element source code,
	 * is source element unit test, is target element requirement, is target element diagram,
	 * is target element source code, is target element unit test
	 * @param rawData - the data extracted from graphml files
	 * 
	 */
	public List<String> buildTestData(List<Relation> rawData) 
	{
		List<String> data = new ArrayList<String>();
		for(int i = 0; i < rawData.size(); i++) 
		{
			data.add(
					rawData.get(i).getSourceId()
					+ ","
					+ rawData.get(i).getTargetId()
					+ ","
					+ Levenshtein.returnDistanceInPercentage(rawData.get(i).getSourceElement().getName(), 
							rawData.get(i).getTargetElement().getName()) 
							+ ","
							+ isContainer(rawData.get(i).getSourceElement().getType())
							+ ","
							+ isContainer(rawData.get(i).getTargetElement().getType())
							+ ","
							+ enumerateAbstractionLevelSeparation(rawData.get(i).getSourceId(), rawData.get(i).getTargetId())
							+ ","
							+ isRequirement(rawData.get(i).getSourceId())
							+ ","
							+ isDesign(rawData.get(i).getSourceId())
							+ ","
							+ isArchitecture(rawData.get(i).getSourceId())
							+ ","
							+ isSourceCode(rawData.get(i).getSourceId())
							+ ","
							+ isUnitTest(rawData.get(i).getSourceId())
							+ ","
							+ isRequirement(rawData.get(i).getTargetId())
							+ ","
							+ isDesign(rawData.get(i).getTargetId())
							+ ","
							+ isArchitecture(rawData.get(i).getTargetId())
							+ ","
							+ isSourceCode(rawData.get(i).getTargetId())
							+ ","
							+ isUnitTest(rawData.get(i).getTargetId()));
		}
		return data;
	}


	/** 
	 * Method to calculate the features of a data instance to build test data = data that does not contain
	 * a labelled feature
	 * FEATURES: Levenshtein distance, is source container, is target container, abstraction level separation,
	 * is source element requirement, is source element diagram, is source element source code,
	 * is source element unit test, is target element requirement, is target element diagram,
	 * is target element source code, is target element unit test
	 * @param rawData - the data extracted from graphml files
	 * 
	 */
	public List<String> buildTestDataQuestionMark(List<Relation> rawData) 
	{
		List<String> data = new ArrayList<String>();
		data.add("SourceID"+","+"TargetId"+","+"NameSimilarity"+","+"IsSourceContainer"+","+"IsTargetContainer"+
		"," + "AbstractionLevelSeparation"+","+"IsSourceRequirement"+","+"IsSourceDiagram"+","+"IsSourceArchitecture"+
				","+"IsSourceSourceCode"+","+"IsSourceUnitTest"+","+"IsTargetRequirement"+","+"IsTargetDiagram"+
		","+"IsTargetArchitecture"+","+"IsTargetSourceCode"+","+"IsTargetUnitTest"+","+"Related");
		
		for(int i = 0; i < rawData.size(); i++) 
		{
			data.add(
					rawData.get(i).getSourceId()
					+ ","
					+ rawData.get(i).getTargetId()
					+ ","
					+ Levenshtein.returnDistanceInPercentage(rawData.get(i).getSourceElement().getName(), 
							rawData.get(i).getTargetElement().getName()) 
							+ ","
							+ isContainer(rawData.get(i).getSourceElement().getType())
							+ ","
							+ isContainer(rawData.get(i).getTargetElement().getType())
							+ ","
							+ enumerateAbstractionLevelSeparation(rawData.get(i).getSourceId(), rawData.get(i).getTargetId())
							+ ","
							+ isRequirement(rawData.get(i).getSourceId())
							+ ","
							+ isDesign(rawData.get(i).getSourceId())
							+ ","
							+ isArchitecture(rawData.get(i).getSourceId())
							+ ","
							+ isSourceCode(rawData.get(i).getSourceId())
							+ ","
							+ isUnitTest(rawData.get(i).getSourceId())
							+ ","
							+ isRequirement(rawData.get(i).getTargetId())
							+ ","
							+ isDesign(rawData.get(i).getTargetId())
							+ ","
							+ isArchitecture(rawData.get(i).getTargetId())
							+ ","
							+ isSourceCode(rawData.get(i).getTargetId())
							+ ","
							+ isUnitTest(rawData.get(i).getTargetId())
							+ ","
							+ "NaN");
		}
		return data;
	}
	
	/** Method to generate data containing related pairs
	 * @throws IOException 
	 */
	public void generateRelatedDataAllFeatures(String inputPath, String csvOutputNameAndPath) throws IOException 
	{
		List<Relation> rawDataRelations = getRelationDataInstanceDetails(inputPath);
		// When building features the related flag has to be set to true
		List<String> processedData = buildTrainingData(rawDataRelations, true);
		CSVHandler.writeToCSV(processedData, csvOutputNameAndPath);
	}

	/** Method to generate data containing non related pairs
	 * @throws IOException 
	 */
	public void generateNonRelatedDataAllFeatures(String sourceFile1, String sourceFile2, String csvOutputNameAndPath) throws IOException 
	{
		List<Relation> rawDataNonRelations = generateNonRelations(sourceFile1, sourceFile2);
		for(int i = 0; i < rawDataNonRelations.size(); i++) 
		{
			System.out.println(i + ": " + rawDataNonRelations.get(i).getSourceElement().getId() + "|||||" 
					+ rawDataNonRelations.get(i).getTargetElement().getId());
		}
		// When building features the related flag has to be set to false
		List<String> processedData = buildTrainingData(rawDataNonRelations, false);
		CSVHandler.writeToCSV(processedData, csvOutputNameAndPath);
	}

	/** Method to generate data containing related pairs
	 * @throws IOException 
	 */
	public void generateTestData(String inputPath, String csvOutputNameAndPath) throws IOException 
	{
		List<Relation> rawDataRelations = getRelationDataInstanceDetails(inputPath);
		List<String> processedData = buildTestDataQuestionMark(rawDataRelations);
		CSVHandler.writeToCSV(processedData, csvOutputNameAndPath);
		CSVHandler.postProcessCSV(csvOutputNameAndPath);
	}
	
	/** 
	 * Method to enumerate the type based on being a container and non container element
	 * Container elements contain other element within, such as a class that can have member method and field 
	 * elements
	 * @param type - the type of element
	 * 
	 */
	public double isContainer(String type) 
	{
		double value = 0;
		if(type.equalsIgnoreCase(types.getMETHOD()) || type.equalsIgnoreCase(types.getFIELD()) || 
				type.equalsIgnoreCase(types.getATT())||type.equalsIgnoreCase(types.getUMLOP()) || type.equalsIgnoreCase(types.getREQ())
				|| type.equalsIgnoreCase(types.getSEQUENCE_DIAGRAM_MESSAGE()) || type.equalsIgnoreCase(types.getSEQUENCE_DIAGRAM_USECASE())
				|| type.equalsIgnoreCase(types.getUSE_CASE_UC()))
		{
			value = 0;
		}

		else value = 1;
		return value;
	}

	/** 
	 * Method to enumerate the relationship between elements based on their abstraction level
	 * @param sourceId - the id of the source element
	 * @param targetId - the of the target element
	 * Abstraction levels: high (requirements) = 0, medium (diagrams) = 1, low (source code, test cases) = 2
	 * The abstraction level can be extracted from the unique id using the unique id prefix
	 * The output expresses the separation between abstraction levels
	 */
	public double enumerateAbstractionLevelSeparation(String sourceId, String targetId) 
	{
		double value = 0;
		double sourceValue = 0;
		double targetValue = 0;
		String source = returnArtefactPrefixFromUniqueId(sourceId).toString();
		String target = returnArtefactPrefixFromUniqueId(targetId).toString();

		if(source.equalsIgnoreCase(GraphMLProcessor.REQ_ID_PREFIX) || source.equalsIgnoreCase(GraphMLProcessor.USE_CASE_ID_PREFIX)
				|| source.equalsIgnoreCase(GraphMLProcessor.ARCHITECTURE_ID_PREFIX)) 
		{
			sourceValue = 0;
		}
		else if(source.equalsIgnoreCase(GraphMLProcessor.DESIGN_ID_PREFIX) || source.equalsIgnoreCase(GraphMLProcessor.SEQUENC_DIAGRAM_ID_PREFIX)) 
		{
			sourceValue = 1;
		}
		else if(source.equalsIgnoreCase(GraphMLProcessor.SC_ID_PREFIX) || source.equalsIgnoreCase(GraphMLProcessor.UNIT_TEST_ID_PREFIX)) 
		{
			sourceValue = 2;
		}

		if(target.equalsIgnoreCase(GraphMLProcessor.REQ_ID_PREFIX) || target.equalsIgnoreCase(GraphMLProcessor.ARCHITECTURE_ID_PREFIX)
				|| target.equalsIgnoreCase(GraphMLProcessor.USE_CASE_ID_PREFIX)) 
		{
			targetValue = 0;
		}
		else if(target.equalsIgnoreCase(GraphMLProcessor.DESIGN_ID_PREFIX) || target.equalsIgnoreCase(GraphMLProcessor.SEQUENC_DIAGRAM_ID_PREFIX)) 
		{
			targetValue = 1;
		}
		else if(target.equalsIgnoreCase(GraphMLProcessor.SC_ID_PREFIX) || target.equalsIgnoreCase(GraphMLProcessor.UNIT_TEST_ID_PREFIX)) 
		{
			targetValue = 2;
		}

		value = Math.abs(sourceValue - targetValue);
		return value;
	}

	/** 
	 * Method to enumerate the type
	 * @param id - the unique id of the element
	 * 
	 */
	public double isRequirement(String id) 
	{
		// If it is a requirement return 1, otherwise return 0
		return (returnArtefactPrefixFromUniqueId(id).equals(GraphMLProcessor.REQ_ID_PREFIX)) 
				? 1.0 : 0.0;
	}

	/** 
	 * Method to enumerate the type
	 * Design artefacts: class diagram, sequence diagram, etc.
	 * @param id - the unique id of the element
	 * 
	 */
	public double isDesign(String id) 
	{
		if(returnArtefactPrefixFromUniqueId(id).equals(GraphMLProcessor.DESIGN_ID_PREFIX) ||
				returnArtefactPrefixFromUniqueId(id).equals(GraphMLProcessor.SEQUENC_DIAGRAM_ID_PREFIX)) 
		{
			return 1.0;
		}
			
		else 
		{
			return 0.0;
		}
	}
	
	/** 
	 * Method to enumerate the type
	 * Architecture artefacts: use cases, logical view, module view, process view, deployment view
	 * @param id - the unique id of the element
	 * 
	 */
	public double isArchitecture(String id) 
	{
		if(returnArtefactPrefixFromUniqueId(id).equals(GraphMLProcessor.USE_CASE_ID_PREFIX) ||
				returnArtefactPrefixFromUniqueId(id).equals(GraphMLProcessor.ARCHITECTURE_ID_PREFIX)) 
		{
			return 1.0;
		}
			
		else 
		{
			return 0.0;
		}
	}

	/** 
	 * Method to enumerate the type
	 * @param id - the unique id of the element
	 * 
	 */
	public double isSourceCode(String id) 
	{
		// If it is source code return 1, otherwise return 0
		return (returnArtefactPrefixFromUniqueId(id).equals(GraphMLProcessor.SC_ID_PREFIX)) 
				? 1.0 : 0.0;
	}

	/** 
	 * Method to enumerate the type
	 * @param id - the unique id of the element
	 * 
	 */
	public double isUnitTest(String id) 
	{
		// If it is a unit test return 1, otherwise return 0
		return (returnArtefactPrefixFromUniqueId(id).equals(GraphMLProcessor.UNIT_TEST_ID_PREFIX)) 
				? 1.0 : 0.0;
	}
	
	/** 
	 * Method to enumerate the type
	 * @param id - the unique id of the element
	 * 
	 */
	public double isDocumentation(String id) 
	{
		// If it is a unit test return 1, otherwise return 0
		return (returnArtefactPrefixFromUniqueId(id).equals(GraphMLProcessor.DOCUMENTATION_ID_PREFIX)) 
				? 1.0 : 0.0;
	}
	
	/** 
	 * Method to enumerate the type
	 * @param id - the unique id of the element
	 * 
	 */
	public double isAPI(String id) 
	{
		// If it is a unit test return 1, otherwise return 0
		return (returnArtefactPrefixFromUniqueId(id).equals(GraphMLProcessor.API_ID_PREFIX)) 
				? 1.0 : 0.0;
	}
	
	/** 
	 * Method to enumerate the type
	 * @param id - the unique id of the element
	 * 
	 */
	public double isConfigFile(String id) 
	{
		// If it is a unit test return 1, otherwise return 0
		return (returnArtefactPrefixFromUniqueId(id).equals(GraphMLProcessor.CONFIG_FILE_ID_PREFIX)) 
				? 1.0 : 0.0;
	}

	/** The unique id's first two letters indicate the artefact type
	 * Method returning the artefact prefix part of a unique id
	 * @param uniqueId
	 */
	public String returnArtefactPrefixFromUniqueId(String uniqueId)
	{
		IDGenerator gen = new IDGenerator();
		return gen.returnArtefactPrefixFromUniqueIdNew(uniqueId);
	}

	/** 
	 * Method for generating non relation (related=0) data instances from data stored in graphml files
	 * @param uniqueId
	 */
	public List<Relation> generateNonRelations(String sourcePath, String targetPath)
	{
		List<Relation> relations = new ArrayList<Relation>();
		List<Element> elements = new ArrayList<Element>();
		// Obtain source elements from a graphml file
		int sourceSize = handler.getAllNodes(sourcePath).size();
		int targetSize = handler.getAllNodes(targetPath).size();
		for(int i = 0; i < sourceSize; i++) 
		{
			Node node = handler.getAllNodes(sourcePath).get(i);
			Element el = new Element();
			el.setName(node.getChildNodes().item(1).getTextContent());
			el.setType(handler.getDataKeyValue(node, GraphMLProcessor.TYPE_DATA_KEY_VALUE));
			el.setId(handler.getUniqueIdOfNode(node));
			Relation rel = new Relation();
			rel.setSourceElement(el);
			rel.setSourceId(el.getId());
			rel.setTargetId(el.getId());
			relations.add(rel);
		}

		// Obtain target elements (of the same number) from another graphml file
		if(sourceSize<=targetSize) 
		{
			for(int i = 0; i < sourceSize; i++) 
			{
				Element el = new Element();
				Node node = handler.getAllNodes(targetPath).get(i);
				el.setName(node.getChildNodes().item(1).getTextContent());
				el.setType(handler.getDataKeyValue(node, GraphMLProcessor.TYPE_DATA_KEY_VALUE));
				el.setId(handler.getUniqueIdOfNode(node));
				elements.add(el);
			}
		}
		else 
		{
			System.out.println("Choose another file with more than " + sourceSize + " number of elements.");
		}
		// Set target elements in the relation list to the values obtained above
		for(int i = 0; i < relations.size(); i++) 
		{
			relations.get(i).setTargetElement(elements.get(i));
			relations.get(i).setTargetId(elements.get(i).getId());
		}
		return relations;
	}

	/** 
	 * Utility method to split every string item of an array to three items
	 * @param arrayToSplit - the array of strings to be split
	 */
	public void splitArray(String [] arrayToSplit) 
	{
		//String [] arrayToSplit = new String[] {"1,1,1","0,0,0","1,1,1"};
		String [] levensthein = new String [arrayToSplit.length];    
		String [] cont = new String [arrayToSplit.length];
		String [] rel = new String [arrayToSplit.length];

		for(int i = 0; i < arrayToSplit.length; i++) 
		{
			levensthein[i] = arrayToSplit[i].split(",")[0];
			cont[i] = arrayToSplit[i].split(",")[1];
			rel[i] = arrayToSplit[i].split(",")[2];
			System.out.println(levensthein[i] + "\t"+ cont[i] + "\t"+ rel[i]);
			System.out.println("*************************************");
		}
	}
}
