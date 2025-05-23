package aca.tit.spring;
/*
import java.security.*;
import java.security.interfaces.*;
import java.security.spec.*;
import javax.crypto.*;
import javax.crypto.interfaces.*;
import javax.crypto.spec.*;
*/
import java.io.*;
import java.security.cert.*;
//import com.envoisolutions.sxc.util.Base64;
import java.math.BigInteger;

public class Certificado {
	private String certificado;
	private String noCertificado;
	
	//Constructores
	public Certificado (){
		this.certificado = "";
		this.noCertificado = "";
	}
	public Certificado (String certificado, String noCertificado){
		this.certificado = certificado;
		this.noCertificado = noCertificado;
	}
	
	//Setters
	public void setCertificado (String certificado){
		this.certificado = certificado;
	}
	public void setNoCertificado (String noCertificado){
		this.noCertificado = noCertificado;
	}
	
	//Getters
	public String getCertificado (){
		return this.certificado;
	}
	public String getNoCertificado (){
		return this.noCertificado;
	}
	
	//Metodos que reciben el path del archivo
	public void CertificadoNoCertificado(String pathCer, String pathKey) throws CertificateException,IOException {
		//Traer archivo .key y.cer
		File cer = new File(pathCer);
		//File key = new File(pathKey);
				
		X509Certificate x509Certificado = getX509Certificate(cer);
		this.certificado = getCertificadoBase64 (x509Certificado);
		this.noCertificado = getNoCertificado (x509Certificado);
	}
	
	private X509Certificate getX509Certificate (final File certificateFile) throws CertificateException,IOException{
		FileInputStream is = null;
		try{
			is = new FileInputStream (certificateFile);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			return (X509Certificate) cf.generateCertificate(is);
		}
		finally{
			if (is != null){
				is.close();
			}
		}
	}
	
	//Metodos que reciben el archivo
	public void getCertificadoYNumero(InputStream is) throws CertificateException,IOException {
		
		X509Certificate x509Certificado = getX509Certificate(is);
		this.certificado = getCertificadoBase64 (x509Certificado);
		this.noCertificado = getNoCertificado (x509Certificado);
	}
	
	private X509Certificate getX509Certificate (final InputStream is) throws CertificateException,IOException{
		try{
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			return (X509Certificate) cf.generateCertificate(is);
		}
		finally{
			if (is != null){
				is.close();
			}
		}
	}
	
	private String getCertificadoBase64 (final X509Certificate cert) throws CertificateEncodingException{
		//return new String (Base64.encode(cert.getEncoded() ));
		return java.util.Base64.getEncoder().encodeToString(cert.getEncoded());
	}
	
	private String getNoCertificado (final X509Certificate cert) {
		BigInteger serial = cert.getSerialNumber();
		byte [] sArr = serial.toByteArray();
		StringBuilder buffer = new StringBuilder();
		for(int i=0; i<sArr.length; i++){
			buffer.append((char)sArr[i]);
		}
		return buffer.toString();
	}
}