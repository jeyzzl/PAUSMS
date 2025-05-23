package aca.util;

import java.io.IOException;
import java.io.InputStream;

public class Imagen {
	
	public static byte[] inputStreamToByte(InputStream is) throws IOException {
	 
		byte[] bytes = null;
		if(is != null){
			bytes = new byte[is.available()];
			is.read(bytes);
	  }
	 
	  return bytes;
	 }	
	
}
