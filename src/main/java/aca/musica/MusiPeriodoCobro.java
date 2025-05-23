package aca.musica;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MusiPeriodoCobro {

	private String periodoId;
 	private String cuentaId;
 	private String cantidad;
 	private String comentario;
 	private String clases;
 	
 	public MusiPeriodoCobro(){
 		periodoId			= "";
 		cuentaId			= "";
 		cantidad			= "";
 		comentario			= "";
 		clases				= "";
 	} 	

	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getCuentaId() {
		return cuentaId;
	}

	public void setCuentaId(String cuentaId) {
		this.cuentaId = cuentaId;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	public String getClases() {
		return clases;
	}

	public void setClases(String clases) {
		this.clases = clases;
	}

	public boolean insertReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("INSERT INTO ENOC.MUSI_PERIODO_COBRO"+ 
 				"(PERIODO_ID, CUENTA_ID, CANTIDAD, COMENTARIO, CLASES) "+
 				"VALUES( ?, TO_NUMBER(?,'999'), TO_NUMBER(?,'99999.99'), ?, TO_NUMBER(?,'99'))");
 			ps.setString(1, periodoId);
 			ps.setString(2, cuentaId);
 			ps.setString(3, cantidad);
 			ps.setString(4, comentario);
 			ps.setString(5, clases);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiPeriodoCobro|insertReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("UPDATE ENOC.MUSI_PERIODO_COBRO "+ 
 				"SET CANTIDAD = TO_NUMBER(?, '99999.99'), "
 				+ " COMENTARIO = ?, "
 				+ " CLASES = TO_NUMBER(?,'99') "
 				+ " WHERE PERIODO_ID = ? "
 				+ " AND CUENTA_ID = TO_NUMBER(?,'999')");
 				
 			ps.setString(1, cantidad);
 			ps.setString(2, comentario);
 			ps.setString(3, clases);
 			ps.setString(4, periodoId);
 			ps.setString(5, cuentaId); 					
 			 			
 			if (ps.executeUpdate()== 1)
 				ok = true;	
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiPeriodoCobro|updateReg|:"+ex);		
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	} 
 	
 	public boolean deleteReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("DELETE FROM ENOC.MUSI_PERIODO_COBRO "+ 
 				"WHERE PERIODO_ID = ? " +
 				"AND CUENTA_ID = TO_NUMBER(?,'999')");
 			ps.setString(1, periodoId);
 			ps.setString(2, cuentaId);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiPeriodoCobro|deleteReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}
 	
 	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		periodoId 			= rs.getString("PERIODO_ID");
 		cuentaId 			= rs.getString("CUENTA_ID");
 		cantidad			= rs.getString("CANTIDAD");
 		comentario			= rs.getString("COMENTARIO");
 		clases				= rs.getString("CLASES");
 	}
  	
 	public void mapeaRegId( Connection conn, String periodoId, String cuentaId ) throws SQLException, IOException{
 		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement("SELECT "+
	 			"PERIODO_ID, CUENTA_ID, CANTIDAD, COMENTARIO, CLASES "+
	 			"FROM ENOC.MUSI_PERIODO_COBRO WHERE PERIODO_ID = ? " + 
	 			"AND CUENTA_ID = TO_NUMBER(?,'999')");
	 		
	 		ps.setString(1, periodoId);
	 		ps.setString(2, cuentaId);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiPeriodoCobro|mapeaRegId|:"+ex);
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
 			ps = conn.prepareStatement("SELECT * FROM ENOC.MUSI_PERIODO_COBRO "+ 
 				"WHERE PERIODO_ID = ?" +
 				"AND CUENTA_ID = TO_NUMBER(?,'999')");
 			ps.setString(1, periodoId);
 			ps.setString(2, cuentaId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiPeriodoCobro|existeReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	
 	public static String getCantidad(Connection conn, String periodoId, String cuentaId) throws SQLException{
 		PreparedStatement ps	= null;
 		ResultSet 		rs		= null;
 		String 	cantidad		= "0";	
 		
 		try{
 			ps = conn.prepareStatement("SELECT CANTIDAD FROM ENOC.MUSI_PERIODO_COBRO "+ 
 				"WHERE PERIODO_ID = ?" +
 				"AND CUENTA_ID = TO_NUMBER(?,'999')");
 			ps.setString(1, periodoId);
 			ps.setString(2, cuentaId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				cantidad = rs.getString("CANTIDAD");			
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiPeriodoCobro|getCantidad|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return cantidad;
 	}
	
}