//Bean del Catalogo de Peridos

package aca.catalogo;

import java.sql.*;

public class CatPeriodo {

	private String periodoId;
	private String nombre;
	private String fechaIni;
	private String fechaFin;
	private String estado;
	
	public CatPeriodo(){
		periodoId 	= "";
		nombre		= "";
		fechaIni	= "";
		fechaFin	= "";
		estado		= "";
	}
	
	public String getPeriodoId(){
		return periodoId;
	}
	
	public void setPeriodoId( String periodoId){
		this.periodoId = periodoId;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public void setNombre( String nombre){
		this.nombre = nombre;
	}
	
	public String getFechaIni(){
		return fechaIni;
	}
	
	public void setFechaIni( String fechaIni){
		this.fechaIni = fechaIni;
	}
	
	public String getFechaFin(){
		return fechaFin;
	}
	
	public void setFechaFin( String fechaFin){
		this.fechaFin = fechaFin;
	}
	
	public String getEstado(){
		return estado;
	}
	
	public void setEstado( String estado){
		this.estado = estado;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		periodoId 	= rs.getString("PERIODO_ID");
		nombre 		= rs.getString("NOMBRE_PERIODO");
		fechaIni 	= rs.getString("F_INICIO");
		fechaFin 	= rs.getString("F_FINAL");
		estado 		= rs.getString("ESTADO");
	}
}