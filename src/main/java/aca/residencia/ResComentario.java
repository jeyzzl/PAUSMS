//Beans para la tabla Res_Comentario
package aca.residencia;

import java.sql.*;

public class ResComentario {
	private String folio;
	private String codigoPersonal;
	private String residenciaId;
	private String comentario;
	
	public ResComentario(){		
		folio			= "";
		codigoPersonal	= "";
		residenciaId	= "";
		comentario 		= "";
	}
	
	public String getFolio() {
		return folio;
	}
	
	public void setFolio(String folio) {
		this.folio = folio;
	}
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	
	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	public String getResidenciaId() {
		return residenciaId;
	}
	
	public void setResidenciaId(String residenciaId) {
		this.residenciaId = residenciaId;
	}
	
	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.RES_COMENTARIO(FOLIO, CODIGO_PERSONAL, " + 
					"RESIDENCIA_ID, COMENTARIO) " +
					"VALUES(TO_NUMBER(?,'99'),?,?,?)");
			ps.setString(1,folio);
			ps.setString(2,codigoPersonal);
			ps.setString(3,residenciaId);		
			ps.setString(4,comentario);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResComentario|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.RES_COMENTARIO SET COMENTARIO = ? " + 
					"WHERE FOLIO= TO_NUMBER(?,'99') AND RESIDENCIA_ID = ? AND CODIGO_PERSONAL = ?");			
			ps.setString(1,comentario);
			ps.setString(2,folio);
			ps.setString(3,residenciaId);
			ps.setString(4,codigoPersonal);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResComentario|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.RES_COMENTARIO WHERE CODIGO_PERSONAL= ?"); 
			ps.setString(1,codigoPersonal);			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResComentario|deletetReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		folio 			= rs.getString("FOLIO");
		codigoPersonal  = rs.getString("CODIGO_PERSONAL");
		residenciaId	= rs.getString("RESIDENCIA_ID");				
		comentario		= rs.getString("COMENTARIO");
	}
	
	public void mapeaRegId(Connection con, String codigoPersonal, String residenciaId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement("SELECT FOLIO, CODIGO_PERSONAL, RESIDENCIA_ID, " +
					"COMENTARIO FROM ENOC.RES_COMENTARIO WHERE CODIGO_PERSONAL = ? AND RESIDENCIA_ID = ?"); 
			ps.setString(1,codigoPersonal);
			ps.setString(2,residenciaId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResComentario|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.RES_COMENTARIO WHERE CODIGO_PERSONAL = ? AND FOLIO = ?"); 
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResComentario|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}

	public String maximoReg(Connection conn, String codigoPersonal ) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String maximo 			= "0";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(FOLIO),0)+1 AS MAXIMO FROM ENOC.RES_COMENTARIO " + 
					"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' ");
			rs= ps.executeQuery();		
			if(rs.next()){
				maximo = rs.getString("MAXIMO");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResComentario|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getComenta(Connection conn, String codigoPersonal, String residenciaId) throws SQLException{		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String comenta 			= "";
		
		try{
			ps = conn.prepareStatement("SELECT COMENTARIO FROM ENOC.RES_COMENTARIO " + 
					"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND RESIDENCIA_ID = '"+residenciaId+"' ");
			rs= ps.executeQuery();		
			if(rs.next()){
				comenta = rs.getString("COMENTARIO");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResComentario|getComenta|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return comenta;
	}
	
	public String maximoReg(Connection conn, String codigoPersonal, String residenciaId ) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String maximo 			= "0";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(FOLIO),0) AS MAXIMO FROM ENOC.RES_COMENTARIO " + 
					"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' " +
					"AND RESIDENCIA_ID = '"+residenciaId+"'");
			rs= ps.executeQuery();		
			if(rs.next()){
				maximo = rs.getString("MAXIMO");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResComentario|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String maximoRegi(Connection conn, String codigoPersonal, String residenciaId ) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String maximo 			= "0";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(FOLIO),0) AS MAXIMO FROM ENOC.RES_COMENTARIO " + 
					"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' " +
					"AND RESIDENCIA_ID = '"+residenciaId+"'");
			rs= ps.executeQuery();		
			if(rs.next()){
				maximo = rs.getString("MAXIMO");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResComentario|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
}