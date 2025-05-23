package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CargaGrupoBiblio {
	private String cursoCargaId;
	private String biblioId;
	private String bibliografia;
	private String orden;
	
	public CargaGrupoBiblio(){
		cursoCargaId    ="";
		biblioId		="";
		bibliografia    ="";
		orden           ="";		
	}	

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getBiblioId() {
		return biblioId;
	}

	public void setBiblioId(String biblioId) {
		this.biblioId = biblioId;
	}

	public String getBibliografia() {
		return bibliografia;
	}

	public void setBibliografia(String bibliografia) {
		this.bibliografia = bibliografia;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		cursoCargaId 	= rs.getString("CURSO_CARGA_ID");
		biblioId        = rs.getString("BIBLIO_ID");
		bibliografia    = rs.getString("BIBLIOGRAFIA");
		orden		    = rs.getString("ORDEN");
		
	}
	
	public void mapeaRegId( Connection conn, String cursoCargaId, String biblioId ) throws SQLException{		
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID, BIBLIO_ID, BIBLIOGRAFIA, ORDEN "+
				"FROM ENOC.CARGA_GRUPO_BIBLIO WHERE CURSO_CARGA_ID = ? AND BIBLIO_ID = TO_NUMBER(?,'99')");		 
			ps.setString(1,cursoCargaId);
			ps.setString(2,biblioId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoBiblio|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
	}

}