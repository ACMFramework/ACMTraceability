package utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/** Transform from one XML schema to another using XSLT transformations
 *
 */
public class XmlTransformer
{
	/** File extension of xml files used in the framework
	 * 
	 */
	private static final String XML_PATH_EXTENSION = ".java.xml";

	/** Xml file extension part
	 * 
	 */
	private static final String XML_PATH_EXTENSION_JAVA = ".java";

	/** File extension of graphml files
	 * 
	 */
	private static final String GRAPHML_PATH_EXTENSION = ".graphml";

	/** Parser
	 * 
	 */
	private XmlParser parser = new XmlParser();

	/** DOM Document
	 * 
	 */
	private Document doc;

	
	/** The path to the XSLT file
	 * 
	 */
	public static String xsltFilePath = "";

	/** Transformation output folder path
	 * 
	 */
	public static String outputPath = "";


	/** The path to the XSLT file
	 * 
	 */
	public static String getXsltfilepath() 
	{
		return xsltFilePath;
	}

	/** The path to the XSLT file
	 * 
	 */
	public static void setXsltfilepath(String path) 
	{
		xsltFilePath = path;;
	}

	/** The path to the XSLT file
	 * 
	 */
	public static String getOutputpath() 
	{
		return outputPath;
	}

	/** The path to the XSLT file
	 * 
	 */
	public void setOutputpath(String path, String fileName) 
	{
		outputPath = path + "\\" + fileName + GRAPHML_PATH_EXTENSION;
	}

	public XmlTransformer() 
	{
	}

	/** Transform a single file according to the xslt transformation specified. The output path and name are set.
	 * @param inputFilePath The file path of the xml file to be transformed
	 * @param xsltFilePath The file path of the XSLT file used for transformation
	 */
	public void transformXmlFile(String inputFilePath,
			String xsltFilePath) throws ParserConfigurationException, SAXException, IOException, TransformerException 
			{
		doc = parser.parseXMLFile(new File(inputFilePath));

		// The output will have the same name as the input and will have a .graphml extension. 
		// It will be saved to the folder specified in the config file
		String fileName = FileUtilities.getFileNameFromPath(inputFilePath);
		//setOutputpath(propertyReader.getProperties()[0], fileName);
		setOutputpath(FileUtilities.getPathWithoutFileName(inputFilePath), fileName);

		// Create stream source - xslt source
		File xsltFile = new File(xsltFilePath);
		TransformerFactory tFactory = TransformerFactory.newInstance();
		StreamSource xsltStreamSource = new StreamSource(xsltFile);
		Transformer transformer = tFactory.newTransformer(xsltStreamSource);

		// Create DOM source and transform
		DOMSource domSource = new DOMSource(doc);
		StreamResult output = new StreamResult(System.out);
		transformer.transform(domSource, output);

		// Output
		PrintWriter outStream = new PrintWriter(new FileOutputStream(getOutputpath()));
		StreamResult fileOutput = new StreamResult(outStream);
		transformer.transform(domSource, fileOutput);
			}

	/** Transform multiple files in a directory
	 * @param inputFilePaths The file paths of the xml files found in the specified directory
	 * @param xsltFilePath The file path of the XSLT file used for transformation
	 */
	public void transformMultipleXmlFiles(String inputFolder, String xsltFilePath) 
			throws ParserConfigurationException, SAXException, IOException, TransformerException 
			{
		// Get all files to be transformed from specified folder
		File folder = new File(inputFolder);
		List<String> inputs = FileUtilities.getFilesFromFolder(folder);

		for(int i = 0; i < inputs.size(); i++)
		{
			doc = parser.parseXMLFile(new File(inputs.get(i)));

			File xsltFile = new File(xsltFilePath);
			TransformerFactory tFactory = TransformerFactory.newInstance();
			StreamSource xsltStreamSource = new StreamSource(xsltFile);
			Transformer transformer = tFactory.newTransformer(xsltStreamSource);

			DOMSource domSource = new DOMSource(doc);
			StreamResult output = new StreamResult(System.out);
			transformer.transform(domSource, output);

			// Output
			// The file name extension is graphml
			// It will be saved to the folder corresponding to the input
			String fileName = FileUtilities.getFileNameFromPath(inputs.get(i));
			setOutputpath(inputFolder, fileName);

			//PrintWriter outStream = new PrintWriter(new FileOutputStream(inputs.get(i).split(XML_PATH_EXTENSION_JAVA)[0]+GRAPHML_PATH_EXTENSION));
			PrintWriter outStream = new PrintWriter(new FileOutputStream(getOutputpath()));
			StreamResult fileOutput = new StreamResult(outStream);
			transformer.transform(domSource, fileOutput);
		}
			}
	
	/** Transform a single file according to the xslt transformation specified. The output path and name are set.
	 * @param inputFilePath The file path of the xml file to be transformed
	 * @param xsltFilePath The file path of the XSLT file used for transformation
	 */
	public void genericTransformXmlFile(String inputFilePath,
			String xsltFilePath, String outputPath) throws ParserConfigurationException, SAXException, IOException, TransformerException 
			{
		doc = parser.parseXMLFile(new File(inputFilePath));
		// Create stream source - xslt source
		File xsltFile = new File(xsltFilePath);
		TransformerFactory tFactory = TransformerFactory.newInstance();
		StreamSource xsltStreamSource = new StreamSource(xsltFile);
		Transformer transformer = tFactory.newTransformer(xsltStreamSource);

		// Create DOM source and transform
		DOMSource domSource = new DOMSource(doc);
		StreamResult output = new StreamResult(System.out);
		transformer.transform(domSource, output);

		// Output
		PrintWriter outStream = new PrintWriter(new FileOutputStream(outputPath));
		StreamResult fileOutput = new StreamResult(outStream);
		transformer.transform(domSource, fileOutput);
			}
}
