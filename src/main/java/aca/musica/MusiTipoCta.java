package aca.musica;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MusiTipoCta {

	private String tipoCtaId;
 	private String tipoCtaNombre;
 	
 	public MusiTipoCta(){
 		tipoCtaId			= "";
 		tipoCtaNombre		= "";
 	} 	


	/**
	 * @return the tipoCtaId
	 */
	public String getTipoCtaId() {
		return tipoCtaId;
	}


	/**
	 * @param tipoCtaId the tipoCtaId to set
	 */
	public void setTipoCtaId(String tipoCtaId) {
		this.tipoCtaId = tipoCtaId;
	}


	/**
	 * @return the tipoCtaNombre
	 */
	public String getTipoCtaNombre() {
		return tipoCtaNombre;
	}


	/**
	 * @param tipoCtaNombre the tipoCtaNombre to set
	 */
	public void setTipoCtaNombre(String tipoCtaNombre) {
		this.tipoCtaNombre = tipoCtaNombre;
	}



	public boolean insertReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("INSERT INTO ENOC.MUSI_TIPOCTA"+ 
 				"(TIPOCTA_ID, TIPOCTA_NOMBRE ) "+
 				"VALUES(TO_NUMBER(?,'99'),? )");
 			ps.setString(1, tipoCtaId);
 			ps.setString(2, tipoCtaNombre);
 			
 		
 			
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiTipoCta|insertReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("UPDATE ENOC.MUSI_TIPOCTA "+ 
 				"SET TIPOCTA_NOMBRE = ? "+				
 				"WHERE TIPOCTA_ID = TO_NUMBER(?,'99')");
 				
 			ps.setString(1, tipoCtaNombre);
 			ps.setString(2, tipoCtaId);
 			
 					
 			 			
 			if (ps.executeUpdate()== 1)
 				ok = true;	
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiTipoCta|updateReg|:"+ex);		
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	} 
 	
 	public boolean deleteReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("DELETE FROM ENOC.MUSI_TIPOCTA "+ 
 				"WHERE TIPOCTA_ID = TO_NUMBER(?,'99') ");
 			ps.setString(1, tipoCtaId);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiTipoCta|deleteReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}
 	
 	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		tipoCtaId 			= rs.getString("TIPOCTA_ID");
 		tipoCtaNombre 		= rs.getString("TIPOCTA_NOMBRE");
 	}
  	
 	public void mapeaRegId( Connection conn, String Codigo_personal ) throws SQLException, IOException{
 		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement("SELECT "+
	 			"TIPOCTA_ID, TIPOCTA_NOMBRE "+
	 			"FROM ENOC.MUSI_TIPOCTA WHERE TIPOCTA_ID = TO_NUMBER(?,'99') "); 
	 		ps.setString(1, tipoCtaId);	
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiTipoCta|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 	}	


 	public boolean existeReg(Connection conn) throws SQLException{
 		boolean 		ok 	= false;
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT * FROM ENOC.MUSI_TIPOCTA "+ 
 				"WHERE TIPOCTA_ID = TO_NUMBER(?,'99')");
 			ps.setString(1, tipoCtaId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiTipoCta|existeReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	
 	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(TIPOCTA_ID)+1 MAXIMO FROM ENOC.MUSI_TIPOCTA"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiTipoCta|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getNombre(Connection conn, String tipoCtaId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String nombre			= "";
		
		try{
			ps = conn.prepareStatement("SELECT TIPOCTA_NOMBRE FROM ENOC.MUSI_TIPOCTA WHERE TIPOCTA_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1, tipoCtaId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("TIPOCTA_NOMBRE");
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.getNombre|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
		
}