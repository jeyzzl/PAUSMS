
package credencial;

import java.io.File;
import java.io.FileInputStream;

public class Comprobador {
	
	public static void main(String[] args) throws Exception {
		File f = new File("y:\\compreso.prn");
		FileInputStream fi = new FileInputStream(f);
		int bytes=0;
		System.out.println(fi.available());
		byte a[] = new byte[fi.available()];
		fi.read(a);
		for (int i=0;i<a.length;i++){ 
			if ((a[i] & 0x80) == 128){
				bytes += a[i] & 0x7F;
				i++;
			}else bytes++;
		}
		System.out.println(bytes);
	}
}