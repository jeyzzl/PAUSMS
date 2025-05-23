// Bean del Catalogo de Estados
package  aca.exa;

import java.sql.*;

public class ExaEstado{
	private String paisId;
	private String estadoId;
	private String nombreEstado;
	
	public ExaEstado(){
		paisId 		= "";
		estadoId 		= "";
		nombreEstado	= "";
	}
	
	public String getPaisId(){
		return paisId;
	}
	
	public void setPaisId( String paisId){
		this.paisId = paisId;
	}
	
	public String getEstadoId(){
		return estadoId;
	}
	
	public void setEstadoId( String estadoId){
		this.estadoId = estadoId;
	}
	
	public String getNombreEstado(){
		return nombreEstado;
	}
	
	public void setNombreEstado( String nombreEstado){
		this.nombreEstado = nombreEstado;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		paisId 		= rs.getString("PAIS_ID");
		estadoId	 	= rs.getString("ESTADO_ID");
		nombreEstado 	= rs.getString("ESTADO_NOMBRE");
	}
	
	public void mapeaRegId( Connection conn, String paisId, String estadoId ) throws SQLException{
		
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT PAIS_ID, ESTADO_ID, ESTADO_NOMBRE "+
				"FROM ENOC.EXA_ESTADO WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?, '9999')"); 
			ps.setString(1,paisId);
			ps.setString(2,estadoId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEstadoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}