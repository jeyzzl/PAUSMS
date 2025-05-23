package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CargaGrupoProgramacion {

	private String cursoCargaId;
	private String folio;
	private String fecha;
	private String orden;
	
	public CargaGrupoProgramacion(){
		cursoCargaId	= "";
		folio 			= "";
		fecha			= "";
		orden			= "";
	}

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		cursoCargaId 	= rs.getString("CURSO_CARGA_ID");
		folio 			= rs.getString("FOLIO");
		fecha 			= rs.getString("FECHA");
		orden 			= rs.getString("ORDEN");
	}
	
	public void mapeaRegId( Connection conn, String cursoCargaId, String folio) throws SQLException{		
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ORDEN FROM ENOC.CARGA_GRUPO_PROGRAMACION" +
					" WHERE CURSO_CARGA_ID = ? AND FOLIO = TO_NUMBER(?,'999')"); 
			ps.setString(1, cursoCargaId);
			ps.setString(2, folio);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoProgramacion|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}
	
}
