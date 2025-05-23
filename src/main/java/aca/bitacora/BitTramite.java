package aca.bitacora;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BitTramite {

	private String tramiteId;
	private String nombre;
	private String areaId;
	private String minimo;
	private String maximo;
	private String promedio;
	private String requisitos;
	private String tipo;
	
	public BitTramite(){
		tramiteId 	= "";
		nombre 		= "";
		areaId		= "";
		minimo 		= "0";
		maximo 		= "0";
		promedio 	= "0";
		requisitos	= "";
		tipo 		= "";
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getTramiteId() {
		return tramiteId;
	}

	public void setTramiteId(String tramiteId) {
		this.tramiteId = tramiteId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMinimo() {
		return minimo;
	}

	public void setMinimo(String minimo) {
		this.minimo = minimo;
	}

	public String getMaximo() {
		return maximo;
	}

	public void setMaximo(String maximo) {
		this.maximo = maximo;
	}

	public String getPromedio() {
		return promedio;
	}

	public void setPromedio(String promedio) {
		this.promedio = promedio;
	}
	
	public String getRequisitos() {
		return requisitos;
	}

	public void setRequisitos(String requisitos) {
		this.requisitos = requisitos;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void mapeaReg(ResultSet rs) throws SQLException{
		tramiteId		= rs.getString("TRAMITE_ID");
		nombre			= rs.getString("NOMBRE");
		areaId			= rs.getString("AREA_ID");
		minimo			= rs.getString("MINIMO");
		maximo			= rs.getString("MAXIMO");
		promedio		= rs.getString("PROMEDIO");
		requisitos		= rs.getString("REQUISITOS");
		tipo			= rs.getString("TIPO");
	}
	
	public void mapeaRegId( Connection conn, String tramiteId) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps 	= null; 
		
		try{
			ps = conn.prepareStatement(" SELECT TRAMITE_ID, NOMBRE, MINIMO, MAXIMO, PROMEDIO, AREA_ID, REQUISITOS, TIPO"
									 + " FROM ENOC.BIT_TRAMITE WHERE TRAMITE_ID = "+tramiteId); 
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.TramiteUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
}
