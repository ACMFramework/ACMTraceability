package traceability;

import java.io.File;
import org.apache.commons.io.FileUtils;
import utilities.FileUtilities;

/**
 * 
 *
 */
public class Traceability 
{
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception 
	{
		//Get user input
		TraceLinkCreator creator = new TraceLinkCreator(args[0]);
		// Create ACMTraceability folder in user's home directory
		String localFolder = FileUtilities.createACMDir();
		File source = new File(System.getProperty("user.dir") + "/data");
		for(File f : source.listFiles()) 
		{
			FileUtils.copyFileToDirectory(f, new File(localFolder));
		}
		creator.execute();
	}
}
