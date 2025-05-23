package aca.musica;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MusiSalon {

	private String salonId;
 	private String salonNombre;
 	private String cupo; 
 	
 	public MusiSalon(){
 		salonId			= "";
 		salonNombre		= ""; 
 		cupo			= "";
 	}	
	
	public String getSalonId() {
		return salonId;
	}

	public void setSalonId(String salonId) {
		this.salonId = salonId;
	}

	public String getSalonNombre() {
		return salonNombre;
	}

	public void setSalonNombre(String salonNombre) {
		this.salonNombre = salonNombre;
	}

	public String getCupo() {
		return cupo;
	}

	public void setCupo(String cupo) {
		this.cupo = cupo;
	}

	public boolean insertReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			//System.out.println("");
 			ps = conn.prepareStatement("INSERT INTO ENOC.MUSI_SALON "+ 
 				"(SALON_ID, SALON_NOMBRE, CUPO)"+
 				" VALUES(TO_NUMBER(?,'99'), ?, TO_NUMBER(?,'99') )");
 			ps.setString(1, salonId);
 			ps.setString(2, salonNombre);
 			ps.setString(3, cupo);
 			
 			
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiInstrumento|insertReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("UPDATE ENOC.MUSI_SALON"+ 
 				" SET SALON_NOMBRE = ?,"+
 				" CUPO = TO_NUMBER(?,'99') "+
 				" WHERE SALON_ID = TO_NUMBER(?,'99')");
 				
 			ps.setString(1, salonNombre);
 			ps.setString(2, cupo);
 			ps.setString(3, salonId);
 			
 					
 			 			
 			if (ps.executeUpdate()== 1)
 				ok = true;	
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiInstrumento|updateReg|:"+ex);		
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	} 
 	
 	public boolean deleteReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("DELETE FROM ENOC.MUSI_SALON WHERE SALON_ID = TO_NUMBER(?,'99')"); 
 			ps.setString(1, salonId);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiInstrumento|deleteReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}
 	
 	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		salonId 			= rs.getString("SALON_ID");
 		salonNombre 		= rs.getString("SALON_NOMBRE");
 		cupo			    = rs.getString("CUPO");
 	}
  	
 	public void mapeaRegId( Connection conn, String salonId) throws SQLException, IOException{
 		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement("SELECT SALON_ID, SALON_NOMBRE , CUPO "+
	 			"FROM ENOC.MUSI_SALON WHERE SALON_ID = TO_NUMBER(?,'99')"); 
	 		ps.setString(1, salonId);
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiSalon|mapeaRegId|:"+ex);
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
 			ps = conn.prepareStatement("SELECT * FROM ENOC.MUSI_SALON WHERE SALON_ID = TO_NUMBER(?,'99')"); 
 			ps.setString(1,salonId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiInstrumento|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT COALESCE(MAX(SALON_ID)+1,1) MAXIMO FROM ENOC.MUSI_SALON"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiInstrumento|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	
}