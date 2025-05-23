// Clase para la tabla de Modulo
package aca.federacion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.*;

public class FedMoodies{	
	private String eventoId;
	private String eventoNombre;
	private String eventoDescripcion;
	private String fechaIni;
	private String fechaFin;
	private String amigable;
	private String influenciaproactiva;
	private String honesto;
	private String responsable;
	private String proaccionmisionera;
	private String consagrado;
	private String atletico;
	private String actitudfitness;
	private String generoso;
	private String altruista;
	private String amigableH;
	private String influenciaproactivaH;
	private String honestoH;
	private String responsableH;
	private String proaccionmisioneraH;
	private String consagradoH;
	private String atleticoH;
	private String actitudfitnessH;
	private String generosoH;
	private String altruistaH;
	
	
	public FedMoodies(){
		eventoId			= "";
		eventoNombre		= "";
		eventoDescripcion	= "";
		fechaIni			= "";
		fechaFin			= "";
		amigable 			= "";
		influenciaproactiva = "";
		honesto 			= "";
		responsable 		= "";
		proaccionmisionera 	= "";
		consagrado 			= "";
		atletico 			= ""; 
		actitudfitness 		= "";
		generoso 			= "";
		altruista 			= "";	
		amigableH 			= "";
		influenciaproactivaH = "";
		honestoH 			= "";
		responsableH 		= "";
		proaccionmisioneraH 	= "";
		consagradoH 			= "";
		atleticoH 			= ""; 
		actitudfitnessH 		= "";
		generosoH 			= "";
		altruistaH			= "";	
	}
	
	public String getEventoId() {
		return eventoId;
	}

	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}

	public String getEventoNombre() {
		return eventoNombre;
	}

	public void setEventoNombre(String eventoNombre) {
		this.eventoNombre = eventoNombre;
	}

	public String getEventoDescripcion() {
		return eventoDescripcion;
	}

	public void setEventoDescripcion(String eventoDescripcion) {
		this.eventoDescripcion = eventoDescripcion;
	}

	public String getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getAmigableH() {
		return amigableH;
	}

	public void setAmigableH(String amigableH) {
		this.amigableH = amigableH;
	}

	public String getInfluenciaproactivaH() {
		return influenciaproactivaH;
	}

	public void setInfluenciaproactivaH(String influenciaproactivaH) {
		this.influenciaproactivaH = influenciaproactivaH;
	}

	public String getHonestoH() {
		return honestoH;
	}

	public void setHonestoH(String honestoH) {
		this.honestoH = honestoH;
	}

	public String getResponsableH() {
		return responsableH;
	}

	public void setResponsableH(String responsableH) {
		this.responsableH = responsableH;
	}

	public String getProaccionmisioneraH() {
		return proaccionmisioneraH;
	}

	public void setProaccionmisioneraH(String proaccionmisioneraH) {
		this.proaccionmisioneraH = proaccionmisioneraH;
	}

	public String getConsagradoH() {
		return consagradoH;
	}

	public void setConsagradoH(String consagradoH) {
		this.consagradoH = consagradoH;
	}

	public String getAtleticoH() {
		return atleticoH;
	}

	public void setAtleticoH(String atleticoH) {
		this.atleticoH = atleticoH;
	}

	public String getActitudfitnessH() {
		return actitudfitnessH;
	}

	public void setActitudfitnessH(String actitudfitnessH) {
		this.actitudfitnessH = actitudfitnessH;
	}

	public String getGenerosoH() {
		return generosoH;
	}

	public void setGenerosoH(String generosoH) {
		this.generosoH = generosoH;
	}

	public String getAltruistaH() {
		return altruistaH;
	}

	public void setAltruistaH(String altruistaH) {
		this.altruistaH = altruistaH;
	}
	
	public String getAmigable() {
		return amigable;
	}

	public void setAmigable(String amigable) {
		this.amigable = amigable;
	}

	public String getInfluenciaproactiva() {
		return influenciaproactiva;
	}

	public void setInfluenciaproactiva(String influenciaproactiva) {
		this.influenciaproactiva = influenciaproactiva;
	}

	public String getHonesto() {
		return honesto;
	}

	public void setHonesto(String honesto) {
		this.honesto = honesto;
	}

	
	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getProaccionmisionera() {
		return proaccionmisionera;
	}

	public void setProaccionmisionera(String proaccionmisionera) {
		this.proaccionmisionera = proaccionmisionera;
	}

	public String getConsagrado() {
		return consagrado;
	}

	public void setConsagrado(String consagrado) {
		this.consagrado = consagrado;
	}

	public String getAtletico() {
		return atletico;
	}

	public void setAtletico(String atletico) {
		this.atletico = atletico;
	}

	public String getActitudfitness() {
		return actitudfitness;
	}

	public void setActitudfitness(String actitudfitness) {
		this.actitudfitness = actitudfitness;
	}

	public String getGeneroso() {
		return generoso;
	}

	public void setGeneroso(String generoso) {
		this.generoso = generoso;
	}

	public String getAltruista() {
		return altruista;
	}

	public void setAltruista(String altruista) {
		this.altruista = altruista;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		eventoId			= rs.getString("EVENTO_ID");
 		eventoNombre		= rs.getString("EVENTO_NOMBRE");
 		eventoDescripcion	= rs.getString("EVENTO_DESCRIPCION");
 		fechaIni	 		= rs.getString("FECHA_INI");
 		fechaFin			= rs.getString("FECHA_FIN");
 		amigable 			= rs.getString("AMIGABLE");
		influenciaproactiva = rs.getString("INFLUENCIAPROACTIVA");
		honesto 			= rs.getString("HONESTO");
		responsable 		= rs.getString("RESPONSABLE");
		proaccionmisionera	= rs.getString("PROACCIONMISIONERA");
		consagrado 			= rs.getString("CONSAGRADO");
		atletico 			= rs.getString("ATLETICO"); 
		actitudfitness 		= rs.getString("ACTITUDFITNESS");
		generoso 			= rs.getString("GENEROSO");
		altruista 			= rs.getString("ALTRUISTA");
		amigableH 			= rs.getString("AMIGABLEH");
		influenciaproactivaH = rs.getString("INFLUENCIAPROACTIVAH");
		honestoH 			= rs.getString("HONESTOH");
		responsableH 		= rs.getString("RESPONSABLEH");
		proaccionmisioneraH	= rs.getString("PROACCIONMISIONERAH");
		consagradoH 		= rs.getString("CONSAGRADOH");
		atleticoH 			= rs.getString("ATLETICOH"); 
		actitudfitnessH 	= rs.getString("ACTITUDFITNESSH");
		generosoH 			= rs.getString("GENEROSOH");
		altruistaH 			= rs.getString("ALTRUISTAH");	
	}
	
	public void mapeaRegId( Connection conn, String eventoId ) throws SQLException, IOException{
		
		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement("SELECT"+
	 			" EVENTO_ID, EVENTO_NOMBRE, EVENTO_DESCRIPCION, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN," +
	 			" AMIGABLE, INFLUENCIAPROACTIVA, HONESTO,  RESPONSABLE, PROACCIONMISIONERA, CONSAGRADO, ATLETICO, ACTITUDFITNESS, GENEROSO, ALTRUISTA,"+	
	 			" AMIGABLEH, INFLUENCIAPROACTIVAH, HONESTOH,  RESPONSABLEH, PROACCIONMISIONERAH, CONSAGRADOH, ATLETICOH, ACTITUDFITNESSH, GENEROSOH, ALTRUISTAH"+	
	 			" FROM ENOC.FED_MOODIES WHERE EVENTO_ID = ?"); 
	 		ps.setString(1, eventoId);			
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){	 			
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedMoodies|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 		
 	} 
 	
}