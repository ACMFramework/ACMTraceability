package utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for handling unique id generation for artefact elements in graphml files
 * A unique id takes the following format: a prefix denoting the artefact type (e.g. sc stands for source code type),
 * a sequential number, and the file path of the file graphml file in which the artefact element is located
 * Unique ids are represented by data keys with attribute value d8
 * Node ids and edge ids in the graphml file also have to be unique
 */
public class IDGenerator
{	
	/** GraphMLProcessor providing basic GraphML operations
	 *
	 */
	GraphMLProcessor graphmlOperations;
	
	/** GraphMLProcessor providing basic GraphML operations
	 *
	 */
	public GraphMLProcessor getGraphmlOperations() 
	{
		return graphmlOperations;
	}

	/** The file path of the GraphML file
	 *
	 */
	private String inputGraphml;

	/**
	 * @return the inputGraphml
	 */
	public String getInputGraphml() 
	{
		return inputGraphml;
	}

	/**
	 * @param inputGraphml the inputGraphml to set
	 */
	public void setInputGraphml(String inputGraphml) 
	{
		this.inputGraphml = inputGraphml;
	}
	
	/**
	 * The number of nodes in the GraphML file
	 */
	public int getNoOfNodes() 
	{
		int noOfNodes = getGraphmlOperations().getNumberOfNodes();
		return noOfNodes;
	}
	
	/**
	 * The number of edges in the GraphML file
	 */
	public int getNoOfEdges() 
	{
		int noOfEdges = getGraphmlOperations().getNumberOfEdges();
		return noOfEdges;
	}
	
	/**
	 * @param inputGraphmlFilePath
	 * The file path of the graphml file
	 */
	public IDGenerator(String inputGraphml) 
	{
		setInputGraphml(inputGraphml);
		graphmlOperations = new GraphMLProcessor(inputGraphml); 
	}
	
	/**
	 * @param inputGraphmlFilePath
	 * The file path of the graphml file
	 */
	public IDGenerator(GraphMLProcessor processor) 
	{
		setInputGraphml(processor.getPath());
		graphmlOperations = processor; 
	}
	
	/** Default constructor
	 *
	 */
	public IDGenerator() 
	{
	
	}
	
	/** Method to generate a single unique id that is larger than maxId
	 * @param prefix
	 */
	public String generateSingleUniqueId()
	{
		String maxId = getGraphmlOperations().getMaxUniqueId();
		String prefix = returnArtefactPrefixFromUniqueIdNew(getGraphmlOperations().getUniqueIds().get(0));
		
		int max = Integer.parseInt(maxId);
		int newId = max + 1;
		String maxStringId = String.valueOf(newId);
		String uniqueId = prefix + maxStringId + getInputGraphml();
		return uniqueId;
	}
	
	/** Method to generate a list holding unique ids 
	 * @param prefix
	 */
	public ArrayList<String> generateListOfUniqueIds(String prefix)
	{
		ArrayList<String> listOfIds = new ArrayList<String>();
		for(int i = 0; i < getNoOfNodes(); i++)
		{
			listOfIds.add(prefix + i + getInputGraphml());
		}
		return listOfIds;
	}

	/** Method to generate a list holding edge id attribute values
	 * @param prefix
	 */
	public ArrayList<String> generateListOfEdgeIds(String prefix)
	{
		ArrayList<String> listOfIds = new ArrayList<String>();
		for(int i = 0; i < getNoOfEdges(); i++)
		{
			listOfIds.add(prefix + i);
		}
		return listOfIds;
	}

	/** Method to generate a single unique id
	 * @param prefix
	 * @param maxID
	 */
	public String generateUniqueId(String prefix, String maxID)
	{
		int max = Integer.parseInt(maxID);
		int newId = max + 1;
		String maxStringId = String.valueOf(newId);
		String id = prefix + maxStringId + getInputGraphml();
		return id;
	}

	/**
	 * Method returning the path part of a unique id
	 * @param uniqueId
	 * 
	 */
	public String returnPathFromUniqueId(String uniqueId)
	{
		String [] result = uniqueId.split("/", 2);
		return result[1];
	}
	
	public static String returnPathFromUniqueIdUsingIndex(String uniqueId) 
	{
		// Get the character two characters earlier the first occurrence of the ":" character
		int index = uniqueId.indexOf(":")-2;
		String splitCharacter = Character.toString(uniqueId.charAt(index));
		String [] result = uniqueId.split(splitCharacter, 2);
		System.out.println(result[1]);
		return result[1];
	}
	/**
	 * Method returning the artefact prefix part of a unique id
	 * @param uniqueId
	 * 
	 */
	public String returnArtefactPrefixFromUniqueId(String uniqueId)
	{
		String [] result = uniqueId.split("/", 2);
		return result[0];
	}
	
	/**
	 * Method returning the artefact prefix part of a unique id - prefix is always 2 letters
	 * @param uniqueId
	 * 
	 */
	public String returnArtefactPrefixFromUniqueIdNew(String uniqueId)
	{
		return uniqueId.substring(0, 2);
	}

	
	/**
	 * Wrapper method to update graphml file with generated unique ids and node ids
	 * @param prefix
	 * 
	 */
	public void generateUniqueIdsForGraphmlFile(String prefix) 
	{
		ArrayList<String> listOfUniqueIds = generateListOfUniqueIds(prefix);
		graphmlOperations.updateUniqueIdOfAllNodes(listOfUniqueIds);
		graphmlOperations.updateNodeIds(listOfUniqueIds);
		generateParentChildIntraLink();
	}
	
	/**
	 * Wrapper method to update multiple graphml files with generated unique ids and node ids
	 * @param prefix
	 * 
	 */
	public void generateUniqueIdsForMultipleGraphmlFiles(String prefix, String folder) 
	{
		List<String> graphmlInputs = FileUtilities.getGraphMLFilesFromFolder(folder);
		for(int i = 0; i < graphmlInputs.size(); i++) 
		{
			IDGenerator gen2 = new IDGenerator(graphmlInputs.get(i));
			gen2.generateUniqueIdsForGraphmlFile(prefix);
		}
	}
	
	/**Method to generate intra links between a container element (such as class) 
	 * and its member elements (methods, etc.)
	 *
	 */
	public void generateParentChildIntraLink() 
	{
		// Update edge id values using a list
		ArrayList<String> edgeIds = generateListOfEdgeIds(GraphMLProcessor.EDGE_ID_PREFIX);
		graphmlOperations.updateEdgeIds(edgeIds);
		
		// Update the source and target attributes of edge elements using node ids
		ArrayList<String> nodeIds = graphmlOperations.getNodeIds();
		graphmlOperations.setEdgeSourceAndTargetAttributes(nodeIds);
	}


	//********************************************************
	// Utility methods
	//********************************************************
	
	/** 
	 * Return the file path of a given file
	 * @param file
	 * 
	 */
	private String extractFilePathName(File file)
	{
		return file.getPath();
	}
}
