package aca.investiga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Alvin
 *
 */
public class InvComentarioUtil {
	
	public boolean insertReg(Connection conn, InvComentario com ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.INV_COMENTARIO(PROYECTO_ID, FOLIO, FECHA, CODIGO_PERSONAL, TIPO, COMENTARIO) "
					+ "VALUES(?, TO_NUMBER(?,'999'), TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?)");
			
			ps.setString(1, com.getProyectoId());
			ps.setString(2, com.getFolio());
			ps.setString(3, com.getFecha());
			ps.setString(4, com.getCodigoPersonal());
			ps.setString(5, com.getTipo());
			ps.setString(6, com.getComentario());
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvComentario|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	public boolean updateReg(Connection conn, InvComentario com ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.INV_COMENTARIO "
					+ " SET FECHA = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " CODIGO_PERSONAL = ?, "
					+ " TIPO = ?, "
					+ " COMENTARIO = ? "
					+ " WHERE PROYECTO_ID = ? "
					+ " AND FOLIO = TO_NUMBER(?,'999') ");
			
			ps.setString(1, com.getFecha());
			ps.setString(2, com.getCodigoPersonal());
			ps.setString(3, com.getTipo());
			ps.setString(4, com.getComentario());
			ps.setString(5, com.getProyectoId());
			ps.setString(6, com.getFolio());			
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvComentario|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String proyectoId, String folio ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.INV_COMENTARIO WHERE PROYECTO_ID = ? AND FOLIO = TO_NUMBER(?,'999')"); 
			ps.setString(1, proyectoId);
			ps.setString(2, folio);
		
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvComentario|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public InvComentario mapeaRegId(Connection con, String proyectoId, String folio) throws SQLException{
		
		InvComentario comentario = new InvComentario();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT PROYECTO_ID, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, CODIGO_PERSONAL, TIPO, COMENTARIO"
					+ " FROM ENOC.INV_COMENTARIO WHERE PROYECTO_ID = ? AND FOLIO = TO_NUMBER(?,'999')"); 
			ps.setString(1, proyectoId);
			ps.setString(2, folio);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				comentario.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvComentario|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return comentario;
	}
	
	public boolean existeReg(Connection conn, String proyectoId, String folio) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.INV_COMENTARIO WHERE PROYECTO_ID = ? AND FOLIO = TO_NUMBER(?,'999') "); 
			ps.setString(1,proyectoId);
			ps.setString(2,folio);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvComentario|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String proyectoId) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.INV_COMENTARIO WHERE PROYECTO_ID = ?"); 
			ps.setString(1,proyectoId);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvComentario|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}	

	public ArrayList<InvComentario> getListAll(Connection conn, String orden ) throws SQLException{
		ArrayList<InvComentario> listProyectos	= new ArrayList<InvComentario>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT PROYECTO_ID, FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, CODIGO_PERSONAL, TIPO, COMENTARIO FROM ENOC.INV_COMENTARIO "+orden; 
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				InvComentario proyecto = new InvComentario();				
				proyecto.mapeaReg(rs);
				listProyectos.add(proyecto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvComentarioUtil|getListAll|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }	
		}
		
		return listProyectos;
	}
	
	public ArrayList<InvComentario> getListProyecto(Connection conn, String proyectoId, String orden ) throws SQLException{
		ArrayList<InvComentario> listProyectos	= new ArrayList<InvComentario>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = " SELECT PROYECTO_ID, FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, CODIGO_PERSONAL, TIPO, COMENTARIO"
					+ " FROM ENOC.INV_COMENTARIO"
					+ " WHERE PROYECTO_ID = '"+proyectoId+"' "+orden; 
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				InvComentario proyecto = new InvComentario();				
				proyecto.mapeaReg(rs);
				listProyectos.add(proyecto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvComentarioUtil|getListProyecto|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }	
		}
		
		return listProyectos;
	}
	
	public HashMap<String, String> getComentarios(Connection conn) throws SQLException{
		
		HashMap<String, String> list		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT PROYECTO_ID, COUNT(*) AS TOTAL FROM ENOC.INV_COMENTARIO GROUP BY PROYECTO_ID ";
			
			rs = st.executeQuery(comando);
			while (rs.next()){	
				list.put(rs.getString("PROYECTO_ID"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvComentarioUtil|getComentarios|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
}