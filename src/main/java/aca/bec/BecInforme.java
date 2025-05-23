package aca.bec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BecInforme {
	
	
	private String informeId;
	private String informeNombre;
	private String fechaIni;
	private String fechaFin;
	private String nivel;
	private String orden;
	private String version;
	private String estado;
	private String idEjercicio;
	
	public BecInforme(){
		informeId			= "";
		informeNombre		= "";
		fechaIni			= "";
		fechaFin			= "";
		nivel				= "";
		orden 				= "";
		version				= "";
		estado 				= "";
		idEjercicio			= "";
	}
	
	
	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getInformeId() {
		return informeId;
	}
	
	public void setInformeId(String informeId) {
		this.informeId = informeId;
	}

	public String getInformeNombre() {
		return informeNombre;
	}

	public void setInformeNombre(String informeNombre) {
		this.informeNombre = informeNombre;
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

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}	
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getIdEjercicio() {
		return idEjercicio;
	}
	
	public void setIdEjercicio(String idEjercicio) {
		this.idEjercicio = idEjercicio;
	}

	public void mapeaReg(ResultSet rs) throws SQLException{
		informeId			= rs.getString("INFORME_ID");
		informeNombre		= rs.getString("INFORME_NOMBRE");
		fechaIni			= rs.getString("FECHA_INI");
		fechaFin			= rs.getString("FECHA_FIN");
		nivel 				= rs.getString("NIVEL");
		orden 				= rs.getString("ORDEN");
		version				= rs.getString("VERSION");
		idEjercicio			= rs.getString("ID_EJERCICIO");
		estado				= rs.getString("ESTADO");
	}
	
	public void mapeaRegId(Connection conn, String informeId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT" +
					" INFORME_ID, INFORME_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI," +
					" TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, NIVEL, ORDEN, VERSION, ID_EJERCICIO, ESTADO" +
					" FROM ENOC.BEC_INFORME " +
					" WHERE INFORME_ID = TO_NUMBER(?,'9999999')");
			
			ps.setString(1, informeId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInforme|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}

}
