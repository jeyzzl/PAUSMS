package aca.investiga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InvResultado {
	private String proyectoId;
	private String infraestructura;
	private String bibliografia;
	
	public InvResultado(){
		proyectoId		= "";
		infraestructura = "-";
		bibliografia	= "-";
	}

	public String getProyectoId() {
		return proyectoId;
	}

	public void setProyectoId(String proyectoId) {
		this.proyectoId = proyectoId;
	}

	public String getInfraestructura() {
		return infraestructura;
	}

	public void setInfraestructura(String infraestructura) {
		this.infraestructura = infraestructura;
	}

	public String getBibliografia() {
		return bibliografia;
	}

	public void setBibliografia(String bibliografia) {
		this.bibliografia = bibliografia;
	}	
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		proyectoId			= rs.getString("PROYECTO_ID");
		infraestructura		= rs.getString("INFRAESTRUCTURA");
		bibliografia		= rs.getString("BIBLIOGRAFIA");
	}
	
	public void mapeaRegId(Connection conn, String proyectoId) throws SQLException{	
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT PROYECTO_ID, INFRAESTRUCTURA, BIBLIOGRAFIA"
					+ " FROM ENOC.INV_RESULTADO"
					+ " WHERE PROYECTO_ID = ?");
			ps.setString(1, proyectoId);
			
			rs = ps.executeQuery();
			
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvResultado|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}
}
