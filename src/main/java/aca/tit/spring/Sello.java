package aca.tit.spring;

import java.security.*;
import java.security.interfaces.*;
import java.security.spec.*;
import javax.crypto.*;
import javax.crypto.interfaces.*;
import javax.crypto.spec.*;
import java.io.*;
import java.security.cert.*;
//import com.envoisolutions.sxc.util.Base64;
import org.apache.commons.ssl.PKCS8Key;

public class Sello {
	private String signature;
	
	//Constructor
	public Sello(){
		this.signature = "";
	}
	public Sello(String signature){
		this.signature = signature;
	}
	
	//Setters
	public void setSignature (String signature){
		this.signature = signature;
	}
	
	//Getters
	public String getSignature(){
		return signature;
	}
	
	//Metodos
	
	/*
	public void Sellado (String pathKey, String password, String cadenaOriginal) throws GeneralSecurityException, IOException{
		File key = new File(pathKey);
		PrivateKey llavePrivada = getPrivateKey(key,password);
		this.signature = getSelloDigital (llavePrivada, cadenaOriginal);
	}
	
	private PrivateKey getPrivateKey (final File keyFile, final String password) throws GeneralSecurityException, IOException{
		FileInputStream in = new FileInputStream (keyFile);
		org.apache.commons.ssl.PKCS8Key pkcs8 = new org.apache.commons.ssl.PKCS8Key (in, password.toCharArray());
		
		byte[] decrypted = pkcs8.getDecryptedBytes();
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decrypted);
		PrivateKey pk = null;
		
		if(pkcs8.isDSA()){
			pk = KeyFactory.getInstance("DSA").generatePrivate(spec);
		}
		else if(pkcs8.isRSA()){
			pk = KeyFactory.getInstance("RSA").generatePrivate(spec);
		}
		
		pk = pkcs8.getPrivateKey();
		return pk;
	}
	*/
	public boolean Sellado (InputStream in, String password, String cadenaOriginal) throws GeneralSecurityException, IOException{
		boolean ok = false;
		try {
			PrivateKey llavePrivada = getPrivateKey(in,password);
			this.signature = getSelloDigital (llavePrivada, cadenaOriginal);
			ok = true;
		}catch(Exception ex) {
			System.out.println("Error al sellar:"+ex);
		}		
		return ok;
	}
	
	private PrivateKey getPrivateKey ( InputStream in, String password) throws GeneralSecurityException, IOException{
		
		org.apache.commons.ssl.PKCS8Key pkcs8 = new org.apache.commons.ssl.PKCS8Key (in, password.toCharArray());
		
		byte[] decrypted = pkcs8.getDecryptedBytes();
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decrypted);
		PrivateKey pk = null;
		
		if(pkcs8.isDSA()){
			pk = KeyFactory.getInstance("DSA").generatePrivate(spec);
		}else if(pkcs8.isRSA()){
			pk = KeyFactory.getInstance("RSA").generatePrivate(spec);
		}
		
		pk = pkcs8.getPrivateKey();
		return pk;
	}
	
	private String getSelloDigital (PrivateKey key, String cadenaOriginal) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException{
		
		Signature sign = Signature.getInstance("SHA256withRSA");
		sign.initSign(key,new SecureRandom());
		
		try{
			sign.update(cadenaOriginal.getBytes("UTF-8"));
		}
		catch (UnsupportedEncodingException ex){
			System.out.println("Error de encoding");
		}
		
		byte[] signature = sign.sign();
		//return new String (Base64.encode(signature));
		return java.util.Base64.getEncoder().encodeToString(signature);
	}
}