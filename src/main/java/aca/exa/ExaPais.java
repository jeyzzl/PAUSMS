// Bean del Catalogo de Paises
package  aca.exa;

import java.sql.*;

public class ExaPais{
	private String paisId;	
	private String nombrePais;
	
	public ExaPais(){
		paisId 			= "";
		nombrePais 		= "";
	}
	
	public String getPaisId(){
		return paisId;
	}
	
	public void setPaisId( String paisId){
		this.paisId = paisId;
	}
	
	public String getNombrePais(){
		return nombrePais;
	}
	
	public void setNombrePais( String nombrePais){
		this.nombrePais = nombrePais;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		paisId 			= rs.getString("PAIS_ID");
		nombrePais 		= rs.getString("PAIS_NOMBRE");
	}
	
	public void mapeaRegId( Connection conn, String paisId) throws SQLException{
		
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT PAIS_ID, PAIS_NOMBRE "+
				"FROM ENOC.EXA_PAIS WHERE PAIS_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1,paisId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaPaisUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}