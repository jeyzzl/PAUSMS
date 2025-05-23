package aca.util;

import java.math.BigInteger;
import java.security.MessageDigest;

//import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;

public class Encriptar {
	
	/* Retorna un hash a partir de un tipo y un texto */
	/* Datos válidos para hashType(MD2, MD5, SHA-1, SHA-256, SHA-348, SHA-512) */
    public static String getHash(String txt, String hashType){
    	// Datos válidos para hashType(MD2, MD5, SHA-1, SHA-256, SHA-348, SHA-512)
    	String claveEncriptada = "";
        try {        	
        	MessageDigest md = MessageDigest.getInstance(hashType);
        	md.update(txt.getBytes("UTF-8"));
        	byte[] mb = md.digest();
        	claveEncriptada = java.util.Base64.getEncoder().encodeToString(mb);
     	    //claveEncriptada = String.valueOf(Hex.encodeHex(mb));        	
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return claveEncriptada;
    }
    
    public static String getSHA256(String input){

    	String toReturn = null;
    	try {
    	    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    	    digest.reset();
    	    digest.update(input.getBytes("utf8"));
    	    toReturn = String.format("%064x", new BigInteger(1, digest.digest()));
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
    	
    	return toReturn;
        }

    public static String getSHA512(String input){
    	String toReturn = null;
    	try {
    	    MessageDigest digest = MessageDigest.getInstance("SHA-512");
    	    digest.reset();
    	    digest.update(input.getBytes("utf8"));
    	    toReturn = String.format("%0128x", new BigInteger(1, digest.digest()));
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}    	
    	return toReturn;
    }	
 
    /* Retorna un hash MD5 a partir de un texto */
    public static String md5ConBase64(String txt) {
        return Encriptar.getHash(txt, "MD5");
    }
 
    /* Retorna un hash SHA-1 a partir de un texto */
    public static String sha1ConBase64(String txt) {    	
        return Encriptar.getHash(txt, "SHA-1");
    }
    
    /* Retorna un hash SHA-256 a partir de un texto */
    public static String sha256ConBase64(String txt) {    	
        return Encriptar.getHash(txt, "SHA-256");
    }
    
    /* Retorna un hash SHA-512 a partir de un texto */
    public static String sha512ConBase64(String txt) {
        return Encriptar.getHash(txt, "SHA-512");
    }
    /* uqahUkMoCZqDL6xtIPJcnwefPv4TZIY+tn5GKea7yp8I7FDx2f994UTNzecGLVPTq3T9WYw/dbaoXc0qmRZKzg==  */
    public static void main(String[] args) {
    	//System.out.println(sha512ConBase64("jete17"));
    	try {        	
        	MessageDigest md = MessageDigest.getInstance("SHA-256");
        	md.update("1170212shareDiploma".getBytes("UTF-8"));
        	byte[] mb = md.digest();
        	System.out.println(mb.toString());        	
        	String claveEncriptada = java.util.Base64.getEncoder().encodeToString(mb);        	
        	System.out.println("Base64:"+claveEncriptada);
        	System.out.println("SinBase64:"+java.util.Base64.getDecoder().decode(claveEncriptada));
     	    String claveHexa = String.valueOf(Hex.encodeHex(mb));
     	    System.out.println("Hexa Apache:"+claveHexa);
     	    System.out.println("Hexa Java  :"+getSHA512("jete17"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }   	
    }   
}
