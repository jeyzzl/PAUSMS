//BEANS DE LA TABLA COND_LUGAR

package aca.disciplina;

import java.sql.*;

public class CondLugar {
	private String idLugar;	
	private String nombre;
	
	public CondLugar(){
		idLugar 		= "";
		nombre  	= "";
	}
	
	public String getIdLugar(){
		return idLugar;
	}
	
	public void setIdLugar( String idLugar){
		this.idLugar = idLugar;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public void setNombre( String nombre){
		this.nombre = nombre;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		idLugar 	= rs.getString("IDLUGAR");
		nombre	 	= rs.getString("NOMBRE");		
	}
	
	public void mapeaRegId(Connection con, String idLugar) throws SQLException{
		
		PreparedStatement ps    = null;
		ResultSet rs 			= null;		
		try{
			ps = con.prepareStatement("SELECT IDLUGAR, NOMBRE FROM ENOC.COND_LUGAR " + 
					"WHERE IDLUGAR = TO_NUMBER(?,'999') ");				
			ps.setString(1,idLugar);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondLugar|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}