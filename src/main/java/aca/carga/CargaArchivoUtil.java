package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CargaArchivoUtil {
	
	/**
	 * @param conn
	 * @return boolean
	 */
	public boolean insertReg(Connection conn, CargaArchivo archivo ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CARGA_GRUPO_ARCHIVO_GRUPO_ARCHIVO"+ 
				"(CURSO_CARGA_ID, FOLIO, FECHA, NOMBRE, EVALUACION_ID, ACTIVIDAD_ID, "+
				"USUARIO_ORIGEN, USUARIO_DESTINO, COMENTARIO, RUTA, RUTA) "+
				"VALUES( ?,"+
				"TO_NUMBER(?,'99'), "+
				"TO_DATE(?,'DD/MM/YYYY'), "+
				"?, "+
				"TO_NUMBER(?,'99'), "+
				"TO_NUMBER(?,'99'), "+
				"?,?,?,?,?");
					
			ps.setString(1,  archivo.getCursoCargaId());
			ps.setString(2,  archivo.getFolio());
			ps.setString(3,  archivo.getFecha());
			ps.setString(4,  archivo.getNombre());
			ps.setString(5,  archivo.getEvaluacionId());
			ps.setString(6,  archivo.getActividadId());
			ps.setString(7,  archivo.getUsuarioOrigen());
			ps.setString(8,  archivo.getUsuarioDestino());
			ps.setString(9,  archivo.getComentario());
			ps.setString(10, archivo.getRuta());
			ps.setString(11, archivo.getEstado());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaArchivoArchivo|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CargaArchivo archivo ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_GRUPO_ARCHIVO "+ 
				"SET FOLIO 				= ?, "+
				"FECHA 					= TO_DATE(?,'DD/MM/YYYY'), "+
				"NOMRE 					= ?, "+
				"EVALUCION_ID 			= TO_NUMBER(?,'99'), "+
				"ACTIVIDAD_ID 			= TO_NUMBER(?,'99'), "+
				"USUARIO_ORIGEN 		= ?, "+
				"USUARIO_DESTINO 		= ?, "+				
				"COMENTARIO 			= ?, "+
				"RUTA 					= ?, " +
				"ESTADO 				= ?," +
				"WHERE CURSO_CARGA_ID 	= ? ");	
			
			ps.setString(1,  archivo.getFolio());
			ps.setString(2,  archivo.getFecha());
			ps.setString(3,  archivo.getNombre());
			ps.setString(4,  archivo.getEvaluacionId());
			ps.setString(5,  archivo.getActividadId());
			ps.setString(6,  archivo.getUsuarioOrigen());
			ps.setString(7,  archivo.getUsuarioDestino());
			ps.setString(8,  archivo.getComentario());
			ps.setString(9, archivo.getRuta());
			ps.setString(10, archivo.getEstado());
			ps.setString(11,  archivo.getCursoCargaId());	
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaArchivo|updateReg|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String cursoCargaId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_GRUPO_ARCHIVO "+ 
				"WHERE CURSO_CARGA_ID = ? ");
			ps.setString(1, cursoCargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaArchivo|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CargaArchivo mapeaRegId( Connection conn, String cursoCargaId ) throws SQLException{
		
		CargaArchivo archivo = new CargaArchivo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID, FOLIO, "+
				"TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, NOMRE, EVALUCION_ID, "+
				"ACTIVIDAD_ID, USUARIO_ORIGEN,USUARIO_DESTINO, COMENTARIO, RUTA, ESTADO "+
				"FROM ENOC.CARGA_GRUPO_ARCHIVO WHERE CURSO_CARGA_ID = ? "); 
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				archivo.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaArchivo|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return archivo;
	}
	
	public boolean existeReg(Connection conn, String cursoCargaId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_GRUPO_ARCHIVO "+ 
				"WHERE CURSO_CARGA_ID = ?");
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaArchivo|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public ArrayList<CargaArchivo> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CargaArchivo> lis	= new ArrayList<CargaArchivo>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT * FROM ENOC.CARGA_GRUPO_ARCHIVO "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaArchivo c = new CargaArchivo();
				c.mapeaReg(rs);
				lis.add(c);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaArchivoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<CargaArchivo> getList(Connection conn, String nombre ) throws SQLException{
		
		ArrayList<CargaArchivo> lis		= new ArrayList<CargaArchivo>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = 	" SELECT * FROM ENOC.CARGA_ENLINEA WHERE nombre = nombre "; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaArchivo c = new CargaArchivo();
				c.mapeaReg(rs);
				lis.add(c);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaArchivoUtil|getList|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
}