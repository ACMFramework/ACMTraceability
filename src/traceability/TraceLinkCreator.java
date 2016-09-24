package traceability;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;
import utilities.XmlTransformer;

/** Initiate trace link creation
 *
 */
public class TraceLinkCreator
{	
	/** Path of the input XML file specified by the user
	 * 
	 */
	private String inputXMLPath = null;
	
	/** Output path specified by user, currently value is set here
	 * 
	 */
	final String outputPath = System.getProperty("user.home") + "/ACMTraceability" + "/finalOutput.xml";
	
	/** XSLT file path
	 * 
	 */
	final String xsltPath = System.getProperty("user.home") + "/ACMTraceability" + "/relationsTransformer.xslt";
	

	/** Path of the generated CSV file
	 * 
	 */
	private String csvPath = System.getProperty("user.home") + "/ACMTraceability" + "/2804.csv";;
	
	/** XML transformer to transform arff file to xml relations file format
	 * 
	 */
	XmlTransformer transformer = new XmlTransformer();
	
	/** CSV data generation functionality
	 * 
	 */
	MLDataGenerator gen = new MLDataGenerator();
	
	/** Classification functionality
	 * 
	 */
	Classification classification = new Classification();
	
	/** Path to the classified data - arff file
	 * 
	 */
	private String classifiedTestDataOutput = null;
	
	/**
	 * 
	 * @return
	 */
	public String getXMLInputPath() 
	{
		return inputXMLPath;
	}
	
	/**
	 * 
	 * @return
	 */
	public void setXMLInputPath(String path) 
	{
		inputXMLPath = path;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getClassifiedTestDataOutput() 
	{
		return classifiedTestDataOutput;
	}
	
	/**
	 * 
	 * @param classifiedTestDataOutput
	 */
	public void setClassifiedTestDataOutput(String classifiedTestDataOutput) 
	{
		this.classifiedTestDataOutput = classifiedTestDataOutput;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getCsvPath() 
	{
		return csvPath;
	}

	/**
	 * 
	 * @param manager
	 */
	public TraceLinkCreator(String inputPath) 
	{
		// Grab user's input file path
		setXMLInputPath(inputPath);
	}

	/**
	 * 
	 */
	public void execute() 
	{
		// Generate CSV test data from specified input.
		try 
		{
			gen.generateTestData(getXMLInputPath(), getCsvPath());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		// Create arff representation
		try 
		{
			classification.createArffFromCsv(getCsvPath());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		try 
		{
			classification.classifyJ48();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		// Classify test data and get output path of arff file
		setClassifiedTestDataOutput(classification.getClassifiedJ48());
		
		// Generate xrff file from arff file and then xml using XSLT
		try 
		{
			classification.saveArffToXrff(getClassifiedTestDataOutput());
			transformer.genericTransformXmlFile(classification.getXrffPath(), xsltPath, outputPath);
		} 
		catch (IOException | ParserConfigurationException | SAXException | TransformerException e) 
		{
			e.printStackTrace();
		}
		System.out.println("Suggested traceability links saved to: " + outputPath);
	}
}
