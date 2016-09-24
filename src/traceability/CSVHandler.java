package traceability;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

/** 
 * 
 *
 */
public class CSVHandler 
{
	/** 
	 * Write contents of list to csv file
	 * @param path - path of the csv to be created
	 * 
	 */
	public static void writeToCSV(List<String> data, String path) throws IOException 
	{
		CSVWriter writer = null;
		try 
		{
			writer = new CSVWriter(new FileWriter(path));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		String[] stringArray = data.toArray(new String[data.size()]);
		writer.writeNext(stringArray);
		writer.close();
	}

	/** Delete unwanted characters from csv file
	 * @throws IOException 
	 * 
	 */
	public static void postProcessCSV(String csvPath) throws IOException 
	{
		Path p = Paths.get(csvPath);
		byte[] b = Files.readAllBytes(p);
		String fileAsString = new String(b, Charset.defaultCharset());
		
		fileAsString = fileAsString.replaceAll("\",\"", "\r\n");
		fileAsString = fileAsString.replaceAll("\"", "");
		Files.write(p, fileAsString.getBytes());
	}
	
	/** Ref: http://www.simplecodestuffs.com/read-write-csv-file-in-java-using-opencsv-library/
	 * @param fileName
	 * 
	 */
	public String[][] parseCSVIntoArray(String filename) 
	{
		String[][] dataRows = null;
		CSVReader reader;
		try 
		{
			reader = new CSVReader(new FileReader(filename));
			List<?> content = reader.readAll();
			dataRows = new String[content.size()][];

			for (int i = 0; i < content.size(); i++) 
			{
				dataRows[i] = (String[]) content.get(i);
			}
			System.out.println("Data rows length: " + dataRows.length);
			reader.close();
		}
		catch (FileNotFoundException e) 
		{
			System.err.println(e.getMessage());
		}
		catch (IOException e) 
		{
			System.err.println(e.getMessage());
		}
		for(int i = 0; i < dataRows.length; i++) 
		{
		}
		return dataRows;
	}
}
