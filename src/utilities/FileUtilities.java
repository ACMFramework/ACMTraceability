package utilities;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilities class responsible for file and folder handling
 *
 */
public class FileUtilities 
{
	/**
	 * 
	 */
	public static final String GRAPHML_EXTENSION = ".graphml";

	/**
	 * 
	 */
	public static final String XML_EXTENSION = ".java.xml";

	/**
	 * 
	 */
	public static final String JAVA_EXTENSION = ".java";

	/**
	 * 
	 */
	public static final String DIA_EXTENSION = ".vdx";

	/**
	 * 
	 */
	public static final String SIMPLE_XML_EXTENSION = ".xml";

	/**
	 * 
	 */
	public static final String SUFFIX = "_previous";

	/**
	 * 
	 */
	public static final String LEFT = "&lt;";

	/**
	 * 
	 */
	public static final String RIGHT = "&gt;";

	/**
	 * 
	 */
	public static final String SMALLER_THAN = "<";

	/**
	 * 
	 */
	public static final String GREATER_THAN = ">";

	/** Return the user's home folder
	 * 
	 * @return
	 */
	public static String getUserHome() 
	{
		return System.getProperty("user.home");
	}
	
	/** Return current directory
	 * 
	 * @return
	 */
	public static String getCurrentDirectory() 
	{
		return System.getProperty("user.dir");
	}
	
	/** Create the ACMTraceability folder in the user's home directory
	 * 
	 */
	public static String createACMDir()
	{
		File dir = new File(System.getProperty("user.home") + "/ACMTraceability");
		dir.mkdir();
		return dir.getPath();
	}
	
	/** Copy files from one folder to another
	 * 
	 * @param sourceDir
	 * @param targetDir
	 */
	public static void copyFilesToDir(Path sourceDir, Path targetDir) throws AccessDeniedException 
	{
		try 
		{
			Files.copy(sourceDir, targetDir, StandardCopyOption.REPLACE_EXISTING);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/** Get filename from path
	 * @param path
	 */
	public static String getFileNameWithoutExtension(String path) 
	{
		Path p = Paths.get(path);
		String fileNameAndExtension = p.getFileName().toString();
		String fileName = fileNameAndExtension.split("\\.",2)[0];
		return fileName;
	}

	/** Return .java files from a folder
	 * Source: http://stackoverflow.com/questions/1384947/java-find-txt-files-in-specified-folder
	 * @param dirName
	 * @return
	 */
	public static List<String> getJavaFilesFromFolder(String folder) 
	{
		List<String> javaFiles = new ArrayList<String>();
		File dir = new File(folder);
		for (File file : dir.listFiles()) 
		{
			if (file.getName().toLowerCase().endsWith((JAVA_EXTENSION))) 
			{
				javaFiles.add(file.getName());
			}
		}
		return javaFiles;
	}

	/** Return .graphml files from a folder
	 * Source: http://stackoverflow.com/questions/1384947/java-find-txt-files-in-specified-folder
	 * @param dirName
	 * @return
	 */
	public static List<String> getGraphMLFilesFromFolder(String folder) 
	{
		List<String> graphMLFiles = new ArrayList<String>();
		File dir = new File(folder);
		for (File file : dir.listFiles()) 
		{
			if (file.getName().toLowerCase().endsWith((GRAPHML_EXTENSION))) 
			{
				graphMLFiles.add(file.getName());
			}
			else 
			{
				System.out.println("No GraphML files found in folder.");
			}
		}
		return graphMLFiles;
	}

	/** Return .graphml files from a folder and their full path
	 * Source: http://stackoverflow.com/questions/1384947/java-find-txt-files-in-specified-folder
	 * @param dirName
	 * @return
	 */
	public static List<String> getGraphMLFilesFullPathFromFolder(String folder) 
	{
		List<String> graphMLFiles = new ArrayList<String>();
		File dir = new File(folder);
		for (File file : dir.listFiles()) 
		{
			if (file.getName().toLowerCase().endsWith((GRAPHML_EXTENSION))) 
			{
				graphMLFiles.add(folder + "\\" + file.getName());
			}
			else 
			{
				System.out.println("No GraphML files found in folder.");
			}
		}
		return graphMLFiles;
	}

	/** Return .java files from a folder
	 * Source: http://stackoverflow.com/questions/1384947/java-find-txt-files-in-specified-folder
	 * @param dirName
	 * @return
	 */
	public static List<String> getDiaXmlFilesFromFolder(String folder) 
	{
		List<String> javaFiles = new ArrayList<String>();
		File dir = new File(folder);
		for (File file : dir.listFiles()) 
		{
			if (file.getName().toLowerCase().endsWith((DIA_EXTENSION))) 
			{
				javaFiles.add(file.getName());
			}
		}
		return javaFiles;
	}

	/** Utility method to return a list of file paths ending ".java.xml" from the selected folder
	 * @param folder The selected folder
	 */
	public static List<String> getXMLFilesFromFolder(File folder) 
	{
		List<String> paths = new ArrayList<String>();
		try
		{
			for (int i = 0; i < folder.listFiles().length; i ++)
			{
				if (folder.listFiles()[i].isFile()) 
				{
					if(folder.listFiles()[i].getPath().contains(XML_EXTENSION)) 
					{
						paths.add(folder.listFiles()[i].getPath());
					}
				}
			}
		}

		catch(Exception e) 
		{
			System.out.println("Files or folder not found");
		}

		return paths;
	}

	/** Return .xml or .vdx files from a folder
	 * Source: http://stackoverflow.com/questions/1384947/java-find-txt-files-in-specified-folder
	 * @param dirName
	 * @return
	 */
	public static List<String> getFilesFromFolder(File folder) 
	{
		List<String> paths = new ArrayList<String>();
		try
		{
			for (int i = 0; i < folder.listFiles().length; i ++)
			{
				if (folder.listFiles()[i].isFile()) 
				{
					if(folder.listFiles()[i].getPath().contains(XML_EXTENSION) 
							|| folder.listFiles()[i].getPath().contains(DIA_EXTENSION)) 
					{
						paths.add(folder.listFiles()[i].getPath());
					}
				}
			}
		}

		catch(Exception e) 
		{
			System.out.println("Files or folder not found");
		}

		return paths;
	}

	/** Get subdirectories of folder
	 * Source: http://stackoverflow.com/questions/5125242/java-list-only-subdirectories-from-a-directory-not-files
	 * @param folder
	 */
	public static String[] getSubFolders(String folder)
	{
		File file = new File(folder);
		String[] directories = file.list(new FilenameFilter() 
		{
			@Override
			public boolean accept(File current, String name) 
			{
				return new File(current, name).isDirectory();
			}
		});

		for(int i = 0; i < directories.length; i++) 
		{
			System.out.println(directories[i]);	
		}
		return directories;
	}

	/** Check if folder exists
	 * 
	 * @return
	 */
	public static boolean folderExist(String path) 
	{
		boolean exists = false;
		File file = new File(path);
		if (file.exists() && file.isDirectory()) 
		{
			exists = true;
		}
		System.out.println(exists);
		return exists;
	}

	/** Check if file exists
	 * 
	 * @return
	 */
	public static boolean fileExists(String path) 
	{
		boolean exists = false;
		File file = new File(path);
		if (file.exists()) 
		{
			exists = true;
		}
		return exists;
	}

	/** Check if given folder is empty
	 * 
	 * @param folder
	 * @return
	 */
	public static boolean isFolderEmpty(String folder)
	{
		boolean empty = true;
		File file = new File(folder);
		if(file.isDirectory())
		{
			if(file.list().length > 0)
			{
				empty = false;		
			}		
		}
		System.out.println(empty);
		return empty;
	}

	/** Get number of files in a directory
	 * @param path
	 */
	public static int getNumberOfFilesInFolder(String path) 
	{
		int length = new File(path).listFiles().length;
		return length;
	}

	/** Rename existing file to contain specified suffix
	 * 
	 */
	public static void renameFile(String path, String suffix) 
	{
		boolean renamed = false;
		try
		{
			if(FileUtilities.fileExists(path + suffix + GRAPHML_EXTENSION)) 
			{
				FileUtilities.deleteFile(path + suffix + GRAPHML_EXTENSION);
				renamed = new File(path).renameTo(new File(path + suffix + GRAPHML_EXTENSION));
			}
			else 
			{
				renamed = new File(path).renameTo(new File(path + suffix + GRAPHML_EXTENSION));
			}
			System.out.print(renamed);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/** Get filename from path
	 * @param path
	 */
	public static String getFileNameFromPath(String path) 
	{
		Path p = Paths.get(path);
		String fileNameAndExtension = p.getFileName().toString();
		String fileName = fileNameAndExtension.split("\\.",2)[0];
		return fileName;
	}

	/** Get filename and extension from path
	 * @param path
	 */
	public static String getFileNameAndExtensionFromPath(String path) 
	{
		Path p = Paths.get(path);
		String fileNameAndExtension = p.getFileName().toString();
		return fileNameAndExtension;
	}

	/** Return the path without file extension
	 * 
	 * @param path
	 * @return
	 */
	public static String getPathWithoutExtension(String path) 
	{
		String noExtension = path.split("\\.",2)[0];
		return noExtension;
	}

	/** Get path without filename
	 * 
	 * @param path
	 * @return
	 */
	public static String getPathWithoutFileName(String path) 
	{
		String pathOnly = path.substring(0,path.lastIndexOf("\\"));
		return pathOnly;
	}

	/** Delete a specific file
	 * 
	 * @param path
	 */
	public static void deleteFile(String path) 
	{
		File file = new File(path);
		file.delete();
	}

	/**
	 * 
	 * @param path
	 * @throws IOException
	 */
	public static void replaceCharactersInFile(String stringPath, String oldContents, String newContents) throws IOException
	{
		Path path = Paths.get(stringPath);
		Charset charset = StandardCharsets.UTF_8;

		String content = new String(Files.readAllBytes(path), charset);
		content = content.replaceAll(oldContents, newContents);
		Files.write(path, content.getBytes(charset));
	}

	/**
	 * 
	 * @param dirPath
	 * @throws IOException 
	 */
	public static void createFrameworkDirAndSubdirs(String dirPath) throws IOException 
	{
		Path p1 = Paths.get(dirPath+"\\ArchitectureConceptual");
		Path p2 = Paths.get(dirPath+"\\ArchitectureModuleView");
		Path p3 = Paths.get(dirPath+"\\Requirement");
		Path p4 = Paths.get(dirPath+"\\SourceCode");
		Path p5 = Paths.get(dirPath+"\\UMLClass");
		Path p6 = Paths.get(dirPath+"\\UMLSequence");
		Path p7 = Paths.get(dirPath+"\\UMLUseCase");
		Path p8 = Paths.get(dirPath+"\\UnitTests");
		
		Files.createDirectories(p1);
		Files.createDirectories(p2);
		Files.createDirectories(p3);
		Files.createDirectories(p4);
		Files.createDirectories(p5);
		Files.createDirectories(p6);
		Files.createDirectories(p7);
		Files.createDirectories(p8);
	}
}
