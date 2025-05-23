package aca.musica;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MusiInstrumento {

	private String instrumentoId;
 	private String instrumentoNombre;
 	private String tipo; 
 	
 	public MusiInstrumento(){
 		instrumentoId			= "";
 		instrumentoNombre		= ""; 
 		tipo					= "";
 	}
 	

	/**
	 * @return the instrumentoId
	 */
	public String getInstrumentoId() {
		return instrumentoId;
	}

	/**
	 * @param instrumentoId the instrumentoId to set
	 */
	public void setInstrumentoId(String instrumentoId) {
		this.instrumentoId = instrumentoId;
	}

	/**
	 * @return the instrumentoNombre
	 */
	public String getInstrumentoNombre() {
		return instrumentoNombre;
	}

	/**
	 * @param instrumentoNombre the instrumentoNombre to set
	 */
	public void setInstrumentoNombre(String instrumentoNombre) {
		this.instrumentoNombre = instrumentoNombre;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	public boolean insertReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("INSERT INTO ENOC.MUSI_INSTRUMENTO "+ 
 				"(INSTRUMENTO_ID, INSTRUMENTO_NOMBRE, TIPO)"+
 				" VALUES(TO_NUMBER(?,'99'), ?, ? )");
 			ps.setString(1, instrumentoId);
 			ps.setString(2, instrumentoNombre);
 			ps.setString(3, tipo);
 			
 			
 			
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
 			ps = conn.prepareStatement("UPDATE ENOC.MUSI_INSTRUMENTO"+ 
 				" SET INSTRUMENTO_NOMBRE = ?,"+
 				" TIPO = ? "+
 				" WHERE INSTRUMENTO_ID = TO_NUMBER(?,'99')");
 				
 			ps.setString(1, instrumentoNombre);
 			ps.setString(2, tipo);
 			ps.setString(3, instrumentoId);
 			
 					
 			 			
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
 			ps = conn.prepareStatement("DELETE FROM ENOC.MUSI_INSTRUMENTO WHERE INSTRUMENTO_ID = TO_NUMBER(?,'99')"); 
 			ps.setString(1, instrumentoId);
 			
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
 		instrumentoId 			= rs.getString("INSTRUMENTO_ID");
 		instrumentoNombre 		= rs.getString("INSTRUMENTO_NOMBRE");
 		tipo			 		= rs.getString("TIPO");
 	}
  	
 	public void mapeaRegId( Connection conn, String instrumentoId) throws SQLException, IOException{
 		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement("SELECT INSTRUMENTO_ID, INSTRUMENTO_NOMBRE , TIPO "+
	 			"FROM ENOC.MUSI_INSTRUMENTO WHERE INSTRUMENTO_ID = TO_NUMBER(?,'99')"); 
	 		ps.setString(1, instrumentoId);
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiInstrumento|mapeaRegId|:"+ex);
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
 			ps = conn.prepareStatement("SELECT * FROM ENOC.MUSI_INSTRUMENTO WHERE INSTRUMENTO_ID = TO_NUMBER(?,'99')"); 
 			ps.setString(1, instrumentoId);
 			
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
			ps = conn.prepareStatement("SELECT COALESCE(MAX(INSTRUMENTO_ID)+1,1) MAXIMO FROM ENOC.MUSI_INSTRUMENTO"); 
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
	
	public static String getNombreInstrumento(Connection conn, String instrumentoId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "null";
		
		try{
			ps = conn.prepareStatement("SELECT INSTRUMENTO_NOMBRE FROM ENOC.MUSI_INSTRUMENTO WHERE INSTRUMENTO_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1, instrumentoId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("INSTRUMENTO_NOMBRE");
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiInstrumento|getNombreInstrumento|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
}