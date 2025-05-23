package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CargaGrupoEje{
	private String cursoCargaId;
	private String ejeId;	
	private String descripcion;
	
	public CargaGrupoEje(){
		cursoCargaId = "";
		ejeId 		 = "";
		descripcion  = "";
	}	

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getEjeId() {
		return ejeId;
	}

	public void setEjeId(String ejeId) {
		this.ejeId = ejeId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		ejeId 			= rs.getString("EJE_ID");
		cursoCargaId 	= rs.getString("CURSO_CARGA_ID");
		descripcion		= rs.getString("DESCRIPCION");
	}
	
	public void mapeaRegId( Connection conn, String cursoCargaId, String ejeId) throws SQLException{	
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT EJE_ID, DESCRIPCION, CURSO_CARGA_ID "+
				"FROM ENOC.CARGA_GRUPO_EJE WHERE CURSO_CARGA_ID = ? AND EJE_ID = ?");		 
			ps.setString(1,cursoCargaId);
			ps.setString(2,ejeId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEje|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}
}