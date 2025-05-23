package aca.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class LeerUrl{ 
  
  	public static String getConexiones( String urlString ) throws Exception{	
		String dato = "0";
		// create the url
	    URL url = new URL(urlString);
	    
	    // open the url stream, wrap it an a few "readers"
	    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
	    
	    String line;
	    while ((line = reader.readLine()) != null){
	    	String arregloDatos[] = line.split(",");
	    	for (int i=0; i<arregloDatos.length;i++) {
	    		System.out.println(line);
	    		if (arregloDatos[i].contains("jdbc.connections.active")) 
	    			dato = arregloDatos[i].replace("\"jdbc.connections.active\":", "");
	    	}
	    	//System.out.println(line);
	    }
	    
	    reader.close();
	    
		return dato;
	}
}