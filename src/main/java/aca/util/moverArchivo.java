package aca.util;
import java.io.*;

public class moverArchivo {

	  public static void fileMove(String sourceFile, String destinationFile) {
		   //System.out.println("Desde: " + sourceFile);
		   //System.out.println("Hacia: " + destinationFile);
		
		  try {
			    File inFile = new File(sourceFile);
			    File outFile = new File(destinationFile);
			
			    FileInputStream in = new FileInputStream(inFile);
			    FileOutputStream out = new FileOutputStream(outFile);
			
			    int c;
			    while ((c = in.read()) != -1)
			     out.write(c);
			
			    in.close();
			    out.close();
			
			    File file = new File(sourceFile);
			    if (file.exists()) {
			    	file.delete();
			    }
		
		   } catch (IOException e) {
			   System.err.println("Hubo un error de entrada/salida!!!");
		   }
	  }
	
	  public static boolean validateDir(String path, boolean action) {
		   File file = new File(path);
		   
		   boolean isDirectory = file.isDirectory();
		   if (action)
		    file.mkdirs();
		   
		   return isDirectory;
	  }
	  
	  public static boolean validateFile(String path) {
		   File file = new File(path);
		   
		   boolean isDirectory = file.isFile();
		   
		   return isDirectory;
	  }
}
