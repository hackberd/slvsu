package slivisu.data.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JFileChooser;


public class DataReader{
	/**
	 * chooseData(): dialog for choosing a file
	 * 
	 * @return File fileToRead
	 */
	public static File chooseData(String folder, String text){
		
		JFileChooser chooser = new JFileChooser();
		if (folder != null){
			chooser = new JFileChooser(folder);
		}
		if (text != null){
			chooser.setDialogTitle(text);
		}
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.showOpenDialog(null);

		return chooser.getSelectedFile();
	}


	/**
	 * readData(): for reading and saving the file
	 * 
	 * @param File fileToRead
	 * @return ArrayList<String> data
	 */
	public static List<String> readData(File fileToRead){

		List<String> data = null;

		if (fileToRead != null){

			FileReader fr = null;

			try{
				fr = new FileReader(fileToRead);
			}
			catch (FileNotFoundException e){
				System.out.println("File not found.");
			}

			if (fr != null){
				LineNumberReader lnr = new LineNumberReader(fr);

				try{
					data = new Vector<String>();

					String line = lnr.readLine();
					while (line != null){
						data.add(line);	
						line = lnr.readLine();
					}

					lnr.close();
				}

				catch(IOException ex){
					System.out.println("Fehler beim Einlesen");
				}
			}
		}
		return data;
	}  

	public static String readDataAsString(File fileToRead){

		String data = null;

		if (fileToRead != null){

			FileReader fr = null;

			try{
				fr = new FileReader(fileToRead);
			}
			catch (FileNotFoundException e){
				System.out.println("File not found.");
			}

			if (fr != null){
				LineNumberReader lnr = new LineNumberReader(fr);

				try{
					data =  new String();

					String line = lnr.readLine();
					while (line != null){
						data += line +"\n";	
						line = lnr.readLine();
					}

					lnr.close();

				}

				catch(IOException ex){
					System.out.println("Fehler beim Einlesen");
				}
			}
		}
		return data;
	}  


	/**
	 * getName(): saves the name of the VegaModel from the original path (for table_column "Name")
	 * 
	 * @param File fileToRead
	 * @return String name
	 */
	public static String getName(File fileToRead)
	{
		String st = fileToRead.toString();
		String path = st.substring(0,st.length()-9);

		File f = new File(path);
		String name = f.getName();

		return name;
	}

	/**
	 * getName(): saves the name of the VegaModel from the original path (for table_column "Name")
	 * 
	 * @param File fileToRead
	 * @return String name
	 */
	public static String getType(File fileToRead)
	{
		String st = fileToRead.toString();
		String path = st.substring(0,st.length()-9);

		File f = new File(path);
		String name = f.getName();

		if (name.contains("-C")) return "C";
		else if (name.contains("-NI")) return "NI";
		return "I";
	}


	/**
	 * getFile(): saves the paths of all subdirectories till vega.lis
	 * 
	 * @param File fileToRead
	 * @return ArrayList<File> lisFiles
	 */
	public static List<File> getFiles(File modelFolder, String suffix){

		List<File> files = new ArrayList<File>();

		File [] fileList = new File [1];
		fileList[0] = modelFolder;

		files.addAll(searchForFileType(fileList, suffix));

		return files;
	}

	public static List<File> searchForFileType(File [] fileList, String suffix){

		List<File> files = new ArrayList<File>();

		for (File f : fileList){

			if (f.isDirectory()){
				files.addAll(searchForFileType(f.listFiles(), suffix));
			}
			else if (f.isFile() && f.getName().endsWith("."+suffix)){
				files.add(f);
			}
		}
		return files;
	}


	/**
	 * getOutFile(): saves the path of an .out-file from a VegaModel if there is any link in vega.lis
	 * 
	 * @param File fileToRead
	 * @return File outLink
	 */
	public static File getOutFile(File fileToRead){

		String fileString = fileToRead.toString();
		String pathOut = fileString.substring(0, fileString.length()-9)+ "/SLI_data.out";

		File outLink = new File(pathOut);

		return outLink;
	}

}