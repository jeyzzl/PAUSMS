import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
 * Created on 13/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Pedro
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Md5 {
    
    public void corre() throws NoSuchAlgorithmException, UnsupportedEncodingException{
        MessageDigest md = MessageDigest.getInstance("MD5");
        String clave = "pedro";
        md.update(clave.getBytes("UTF-8"));
        byte claveByte[] = md.digest(); 
        System.out.println(java.util.Base64.getEncoder().encodeToString(claveByte));
    }   
    
    public static void main(String[] a) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        Md5 m = new Md5();
        m.corre();
    }
}