package aca.bitacora;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class BitEtiquetaUtil {
	
	public boolean insertReg(Connection conn, BitEtiqueta etiqueta) throws Exception{
		boolean ok 				= false;
		PreparedStatement ps 	= null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.BIT_ETIQUETA"+ 
				"(FOLIO, ETIQUETA_ID, AREA_ID, COMENTARIO, FECHA, USUARIO, TURNADO)"+
				" VALUES(?, TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), ?, NOW(), ?, ?)");
					
			ps.setString(1,	etiqueta.getFolio());
			ps.setString(2, etiqueta.getEtiquetaId());
			ps.setString(3, etiqueta.getAreaId());
			ps.setString(4, etiqueta.getComentario());
			ps.setString(5, etiqueta.getUsuario());
			ps.setString(6, etiqueta.getTurnado());

			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.EtiquetaUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, BitEtiqueta etiqueta) throws Exception{
		boolean ok 				= false;
		PreparedStatement ps 	= null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BIT_ETIQUETA "
					+ " SET AREA_ID = TO_NUMBER(?,'99'), "
					+ " COMENTARIO = ?, "
					+ " FECHA = NOW(),"			
					+ " USUARIO = ?,"
					+ " TURNADO = ?"
					+ " WHERE FOLIO = ?"
					+ " AND ETIQUETA_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, etiqueta.getAreaId());
			ps.setString(2, etiqueta.getComentario());			
			ps.setString(3, etiqueta.getUsuario());
			ps.setString(4, etiqueta.getTurnado());
			ps.setString(5, etiqueta.getFolio());
			ps.setString(6, etiqueta.getEtiquetaId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.EtiquetaUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateComentario(Connection conn, String folio, String etiquetaId, String comentario) throws Exception{
		boolean ok 				= false;
		PreparedStatement ps 	= null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BIT_ETIQUETA "
					+ " SET COMENTARIO = ? "					
					+ " WHERE FOLIO = ?"
					+ " AND ETIQUETA_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, comentario);			
			ps.setString(2, folio);
			ps.setString(3, etiquetaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.EtiquetaUtil|updateComentario|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String folio, String etiquetaId) throws Exception{
		boolean ok 				= false;
		PreparedStatement ps 	= null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.BIT_ETIQUETA"
					+ " WHERE FOLIO = ?"
					+ " AND ETIQUETA_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, folio);
			ps.setString(2, etiquetaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.EtiquetaUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteAllReg(Connection conn, String folio ) throws Exception{
		boolean ok 				= false;
		PreparedStatement ps 	= null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.BIT_ETIQUETA WHERE FOLIO = ?");
			
			ps.setString(1, folio);
			
			if (ps.executeUpdate() >= 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.EtiquetaUtil|deleteAllReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public BitEtiqueta mapeaRegId( Connection conn, String folio, String etiquetaId) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps 	= null; 
		BitEtiqueta etiqueta 	= new BitEtiqueta();
		try{
			ps = conn.prepareStatement(" SELECT FOLIO, ETIQUETA_ID, AREA_ID, COMENTARIO, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, USUARIO, TURNADO "
									 + " FROM ENOC.BIT_ETIQUETA WHERE FOLIO = ? AND ETIQUETA_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1, folio);
			ps.setString(2, etiquetaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				etiqueta.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.EtiquetaUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return etiqueta;
	}
	
	public boolean existeRegId( Connection conn, String folio, String etiquetaId) throws SQLException{
		
		PreparedStatement ps 	= null; 
		ResultSet rs 			= null;
		boolean ok				= false; 
		try{
			ps = conn.prepareStatement(" SELECT FOLIO, ETIQUETA_ID, AREA_ID, COMENTARIO, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, USUARIO, TURNADO"
									 + " FROM ENOC.BIT_ETIQUETA WHERE FOLIO = ? AND ETIQUETA_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1, folio);
			ps.setString(2, etiquetaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.EtiquetaUtil|existeRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public static String getComentario( Connection conn, String folio, String etiquetaId) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		String nombre 			= "-"; 
		
		try{
			ps = conn.prepareStatement(" SELECT COMENTARIO FROM ENOC.BIT_ETIQUETA WHERE FOLIO = ? AND ETIQUETA_ID = TO_NUMBER(?,'99')");
			ps.setString(1, folio);
			ps.setString(2, etiquetaId);
			
			rs = ps.executeQuery();			
			if (rs.next()){
				nombre = rs.getString("NOMBRE");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.EtiquetaUtil|getNombre|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return nombre;
	}
	
	public static int numEtiquetas( Connection conn, String folio ) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		int total				= 0; 
		
		try{
			ps = conn.prepareStatement(" SELECT COUNT(ETIQUETA_ID) AS TOTAL FROM ENOC.BIT_ETIQUETA WHERE FOLIO = ?");
			ps.setString(1, folio);			
			
			rs = ps.executeQuery();			
			if (rs.next()){
				total = rs.getInt("TOTAL");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.EtiquetaUtil|numEtiquetas|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return total;
	}
	
	public String maximoReg(Connection conn, String folio) throws SQLException{
		
		String maximo			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement ("SELECT COALESCE(MAX(ETIQUETA_ID)+1,1) AS MAXIMO FROM ENOC.BIT_ETIQUETA"
					+ " WHERE FOLIO = ?");
			ps.setString (1, folio);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
				
		}catch(Exception ex){
			System.out.println("Error -  aca.bitacora.EtiquetaUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;
	}

	
	public ArrayList<BitEtiqueta> listEtiquetas(Connection conn, String folio, String orden) throws SQLException{
		ArrayList<BitEtiqueta> etiquetas = new ArrayList<BitEtiqueta>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT FOLIO, ETIQUETA_ID, AREA_ID, COMENTARIO, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, USUARIO, TURNADO"
					+ " FROM ENOC.BIT_ETIQUETA"
					+ " WHERE FOLIO = '"+folio+"' "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				BitEtiqueta etiqueta = new BitEtiqueta();
				etiqueta.mapeaReg(rs);
				etiquetas.add(etiqueta);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.EtiquetaUtil|getEtiquetaList|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return etiquetas;
	}
	
	

}
