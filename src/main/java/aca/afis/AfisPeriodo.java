package aca.afis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AfisPeriodo {
	
	private String periodoId		= "";
	private String periodoNombre	= "";
	private String fechaIni			= "";
	private String fechaFin			= "";
	private String estado			= "";
	private String filtro			= "";
	
	public AfisPeriodo(){
		periodoId 		= "";
		periodoNombre 	= "";
		fechaIni 		= "";
		fechaFin 		= "";
		estado 			= "";
		filtro			= "";
	}

	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getPeriodoNombre() {
		return periodoNombre;
	}

	public void setPeriodoNombre(String periodoNombre) {
		this.periodoNombre = periodoNombre;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		periodoId		= rs.getString("PERIODO_ID");
		periodoNombre	= rs.getString("PERIODO_NOMBRE");
		fechaIni		= rs.getString("FECHA_INI");
		fechaFin		= rs.getString("FECHA_FIN");
		estado			= rs.getString("ESTADO");
		filtro			= rs.getString("FILTRO");
	}
	
public void mapeaRegId( Connection conn, String periodoId ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT PERIODO_ID, PERIODO_NOMBRE,"
					+ " TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI,"
					+ " TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN,"
					+ " ESTADO, FILTRO"
					+ " FROM ENOC.AFIS_PERIODO WHERE PERIODO_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1, periodoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.afis.AfisPeriodoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
		
}
		
