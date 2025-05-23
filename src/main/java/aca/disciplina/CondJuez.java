package aca.disciplina;

import java.sql.*;

public class CondJuez {
	private String idJuez;	
	private String nombre;
	
	public CondJuez(){
		idJuez 		= "";
		nombre  	= "";
	}
	
	public String getIdJuez(){
		return idJuez;
	}
	
	public void setIdJuez( String idJuez){
		this.idJuez = idJuez;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public void setNombre( String nombre){
		this.nombre = nombre;
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		idJuez 		= rs.getString("IDJUEZ");
		nombre	 	= rs.getString("NOMBRE");		
	}
	
	public void mapeaRegId(Connection con, String idJuez) throws SQLException{	
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT IDJUEZ, NOMBRE FROM ENOC.COND_JUEZ " + 
					"WHERE IDJUEZ = TO_NUMBER(?,'999') ");				
			ps.setString(1,idJuez);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondJuez|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
	}
}