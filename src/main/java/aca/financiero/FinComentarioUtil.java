package aca.financiero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FinComentarioUtil {
	
	public boolean insertReg(Connection conn, FinComentario com ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.FIN_COMENTARIO" + 
					" (CODIGO_PERSONAL, FOLIO, COMENTARIO, FECHA, USUARIO, TIPO)" +
					" VALUES(?, TO_NUMBER(?, '999'), ?, TO_DATE(?,'DD/MM/YYYY'), ?, ? )");
			
			ps.setString(1, com.getCodigoPersonal());
			ps.setString(2, com.getFolio());
			ps.setString(3, com.getComentario());
			ps.setString(4, com.getFecha());
			ps.setString(5, com.getUsuario());
			ps.setString(6, com.getTipo());
			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinComentario|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, FinComentario com ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.FIN_COMENTARIO"
					+ " SET COMENTARIO = ?,"
					+ " FECHA = TO_DATE(?,'DD/MM/YYYY'),"
					+ " USUARIO = ?,"
					+ " TIPO = ?"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND FOLIO = TO_NUMBER(?, '999')");
			
			ps.setString(1, com.getComentario());
			ps.setString(2, com.getFecha());
			ps.setString(3, com.getUsuario());
			ps.setString(4, com.getTipo());
			ps.setString(5, com.getCodigoPersonal());
			ps.setString(6, com.getFolio());
			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinComentario|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String codigoPersonal, String folio ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.FIN_COMENTARIO" + 
					" WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?, '99')");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);
		
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinComentario|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public FinComentario mapeaRegId(Connection con, String codigoPersonal, String folio) throws SQLException{
		
		FinComentario com = new FinComentario();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT CODIGO_PERSONAL, FOLIO, COMENTARIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, TIPO"
					+ " FROM ENOC.FIN_COMENTARIO"
					+ " WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				com.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinComentario|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return com;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal, String folio) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.FIN_COMENTARIO"
					+ " WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?, '999')");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);
		
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinComentario|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getComentario(Connection conn, String codigoPersonal, String folio) throws SQLException{
		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String comentario		= "-";
		
		try{
			ps = conn.prepareStatement("SELECT COMENTARIO FROM ENOC.FIN_COMENTARIO" + 
					" WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?, '999')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);			
		
			rs= ps.executeQuery();		
			if(rs.next()){
				comentario = rs.getString("COMENTARIO");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinComentario|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return comentario;
	}
	
	public static boolean tieneComentario(Connection conn, String codigoPersonal, String fechaIni, String fechaFin) throws SQLException{
		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		boolean ok				= false;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.FIN_COMENTARIO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND TO_DATE(TO_CHAR(FECHA,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, fechaIni);			
			ps.setString(3, fechaFin);
		
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinComentario|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String codigoPersonal) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.FIN_COMENTARIO"
					+ " WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinComentario|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public ArrayList<FinComentario> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<FinComentario> lista = new ArrayList<FinComentario>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, FOLIO, COMENTARIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, TIPO FROM ENOC.FIN_COMENTARIO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				FinComentario comentario = new FinComentario();
				comentario.mapeaReg(rs);
				lista.add(comentario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinComentarioUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lista;
	}
	
	public ArrayList<FinComentario> lisAlumnoComentarios(Connection conn, String codigoPersonal, String year, String orden ) throws SQLException{
		
		ArrayList<FinComentario> lista = new ArrayList<FinComentario>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, FOLIO, COMENTARIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, TIPO FROM ENOC.FIN_COMENTARIO"
					+ " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"
					+ " AND TO_CHAR(FECHA,'YYYY') = '"+year+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				FinComentario comentario = new FinComentario();
				comentario.mapeaReg(rs);
				lista.add(comentario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinComentarioUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lista;
	}
	
}
