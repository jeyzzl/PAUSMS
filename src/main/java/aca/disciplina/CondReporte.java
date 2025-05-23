//BEANS DE LA TABLA COND_REPORTE
package aca.disciplina;

import java.sql.*;

public class CondReporte {
	private String idReporte;	
	private String nombre;
	private String tipo;
	
	public CondReporte(){
		idReporte 	= "";
		nombre  	= "";
		tipo	  	= "";
	}
	
	public String getIdReporte(){
		return idReporte;
	}
	
	public void setIdReporte( String idReporte){
		this.idReporte = idReporte;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public void setNombre( String nombre){
		this.nombre = nombre;
	}
	
	public String getTipo(){
		return tipo;
	}
	
	public void setTipo( String tipo){
		this.tipo = tipo;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		idReporte 	= rs.getString("IDREPORTE");
		nombre	 	= rs.getString("NOMBRE");		
		tipo	 	= rs.getString("TIPO");
	}
	
	public void mapeaRegId(Connection con, String idReporte) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT IDREPORTE, NOMBRE, TIPO FROM ENOC.COND_REPORTE " + 
					"WHERE IDREPORTE = TO_NUMBER(?,'999') ");				
			ps.setString(1,idReporte);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondReporte|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}
	
}