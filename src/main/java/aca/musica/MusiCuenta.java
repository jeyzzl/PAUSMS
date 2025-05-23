package aca.musica;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MusiCuenta {

	private String cuentaId;
 	private String cuentaNombre;
 	private String tipo;
 	private String instrumentoId;
 
 	
 	public MusiCuenta(){
 		cuentaId			= "";
 		cuentaNombre		= "";
 		tipo				= "";
 		instrumentoId		= "";
 	
 	}
 	

	public String getCuentaId() {
		return cuentaId;
	}

	public void setCuentaId(String cuentaId) {
		this.cuentaId = cuentaId;
	}

	public String getCuentaNombre() {
		return cuentaNombre;
	}

	public void setCuentaNombre(String cuentaNombre) {
		this.cuentaNombre = cuentaNombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getInstrumentoId() {
		return instrumentoId;
	}

	public void setInstrumentoId(String instrumentoId) {
		this.instrumentoId = instrumentoId;
	}

	public boolean insertReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("INSERT INTO ENOC.MUSI_CUENTA"+ 
 				"(CUENTA_ID, CUENTA_NOMBRE, TIPO, INSTRUMENTO_ID) "+
 				"VALUES(TO_NUMBER(?,'999'),?,?,TO_NUMBER(?,'99') )");
 			ps.setString(1, cuentaId);
 			ps.setString(2, cuentaNombre);
 			ps.setString(3, tipo);
 			ps.setString(4, instrumentoId);
 		
 			
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiCuenta|insertReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("UPDATE ENOC.MUSI_CUENTA "+ 
 				"SET CUENTA_NOMBRE = ?, "+
 				"TIPO = ?, INSTRUMENTO_ID = ?  "+				
 				"WHERE CUENTA_ID = TO_NUMBER(?,'999') ");
 				
 			ps.setString(1, cuentaNombre);
 			ps.setString(2, tipo);
 			ps.setString(3, instrumentoId);
 			ps.setString(4, cuentaId);
 					
 			 			
 			if (ps.executeUpdate()== 1)
 				ok = true;	
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiCuenta|updateReg|:"+ex);		
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	} 
 	
 	public boolean deleteReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("DELETE FROM ENOC.MUSI_CUENTA "+ 
 				"WHERE CUENTA_ID = TO_NUMBER(?,'999') ");
 			ps.setString(1, cuentaId);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiAlumno|deleteReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}
 	
 	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		cuentaId 			= rs.getString("CUENTA_ID");
 		cuentaNombre 		= rs.getString("CUENTA_NOMBRE");
 		tipo 				= rs.getString("TIPO");
 		instrumentoId		= rs.getString("INSTRUMENTO_ID");
 		
 	}
  	
 	public void mapeaRegId( Connection conn, String cuentaId ) throws SQLException, IOException{
 		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement("SELECT "+
	 			"CUENTA_ID, CUENTA_NOMBRE, TIPO, INSTRUMENTO_ID "+
	 			"FROM ENOC.MUSI_CUENTA WHERE CUENTA_ID = TO_NUMBER(?,'999') "); 
	 		ps.setString(1, cuentaId);	
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiCuenta|mapeaRegId|:"+ex);
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
 			ps = conn.prepareStatement("SELECT * FROM ENOC.MUSI_CUENTA "+ 
 				"WHERE CUENTA_ID = TO_NUMBER(?,'999')");
 			ps.setString(1, cuentaId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiCuenta|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(CUENTA_ID)+1 MAXIMO FROM ENOC.MUSI_CUENTA"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiCuenta|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
 	
 	public static String getCuentaNombre(Connection conn, String cuentaId) throws SQLException{ 		
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		String cuenta			= "";
 		
 		try{
 			ps = conn.prepareStatement("SELECT CUENTA_NOMBRE FROM ENOC.MUSI_CUENTA "+ 
 				"WHERE CUENTA_ID = TO_NUMBER(?,'999')");
 			ps.setString(1, cuentaId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				cuenta = rs.getString("CUENTA_NOMBRE"); 			
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiCuenta|getCuentaNombre|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return cuenta;
 	}
 	
 	public static String getTipo(Connection conn, String cuentaId) throws SQLException{ 
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		String cuenta			= "";
 		
 		try{
 			ps = conn.prepareStatement("SELECT TIPO FROM ENOC.MUSI_CUENTA "+ 
 				"WHERE CUENTA_ID = TO_NUMBER(?,'999')");
 			ps.setString(1, cuentaId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				cuenta = rs.getString("TIPO");
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiCuenta|getTipo|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return cuenta;
 	}
 	
 	public static String getTipoNombre(Connection conn, String cuentaId) throws SQLException{ 		
 		PreparedStatement ps	= null;
 		ResultSet 		rs		= null; 		
 		String tipo				= "";
 		
 		try{
 			ps = conn.prepareStatement("SELECT MT.TIPOCTA_NOMBRE FROM ENOC.MUSI_CUENTA MC, ENOC.MUSI_TIPOCTA MT"+ 
 				" WHERE MC.CUENTA_ID = TO_NUMBER(?,'999')" +
 				" AND MT.TIPOCTA_ID = TO_NUMBER(MC.TIPO,'99')");
 			ps.setString(1, cuentaId);
 			
 			rs = ps.executeQuery();
 			if (rs.next()){
 				tipo = rs.getString("TIPOCTA_NOMBRE");
 			}	
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiCuenta|getTipoNombre|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return tipo;
 	} 	
 	
}