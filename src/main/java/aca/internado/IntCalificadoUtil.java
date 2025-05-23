package aca.internado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IntCalificadoUtil {
	
	public boolean insertReg(Connection conn, IntCalificado intCalif ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.INT_CALIFICADO(CODIGO_PERSONAL, CARGA_ID," + 
														 " BLOQUE_ID, NUM_COMIDAS, TIPO_COMIDA, LUGAR_ID, COMENTARIO)" +
														 " VALUES(?,?,?,?,?,?,?)");
			ps.setString(1, intCalif.getCodigoPersonal());
			ps.setString(2, intCalif.getCargaId());
			ps.setInt(3, Integer.parseInt(intCalif.getBloqueId()));
			ps.setInt(4, Integer.parseInt(intCalif.getNumComidas()));
			ps.setString(5, intCalif.getTipoComida());
			ps.setInt(6, Integer.parseInt(intCalif.getLugarId()));
			ps.setString(7, intCalif.getComentario());
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.IntCalificadoUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public boolean updateReg(Connection conn, IntCalificado intCalif ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.INT_CALIFICADO SET" + 
														 " CODIGO_PERSONAL = ?," +
														 " CARGA_ID = ?," +
														 " BLOQUE_ID = ?," +
														 " NUM_COMIDAS = ?," +
														 " TIPO_COMIDA = ?," +
														 " LUGAR_ID = ?," +
														 " COMENTARIO = ?" +
														 " WHERE CODIGO_PERSONAL = ?" +
														 " AND CARGA_ID = ?" +
														 " AND BLOQUE_ID = ?");			
			
			ps.setString(1, intCalif.getCodigoPersonal());
			ps.setString(2, intCalif.getCargaId());
			ps.setInt(3, Integer.parseInt(intCalif.getBloqueId()));
			ps.setInt(4, Integer.parseInt(intCalif.getNumComidas()));
			ps.setString(5, intCalif.getTipoComida());
			ps.setInt(6, Integer.parseInt(intCalif.getLugarId()));
			ps.setString(7, intCalif.getComentario());
			ps.setString(8, intCalif.getCodigoPersonal());
			ps.setString(9, intCalif.getCargaId());
			ps.setInt(10, Integer.parseInt(intCalif.getBloqueId()));
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.IntCalificadoUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn, String codigoPersonal, String cargaId, String bloqueId ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.INT_CALIFICADO" + 
														 " WHERE CODIGO_PERSONAL = ?" +
														 " AND CARGA_ID = ?" +
														 " AND BLOQUE_ID = ?");
			ps.setString(1,codigoPersonal);
			ps.setString(2,cargaId);
			ps.setInt(3,Integer.parseInt(bloqueId));			
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.IntCalificadoUtil|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public IntCalificado mapeaRegId(Connection con, String CodigoPersonal, String CargaId, String BloqueId) throws SQLException{
		IntCalificado intCalif = new IntCalificado();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT * FROM ENOC.INT_CALIFICADO " + 
														" WHERE CODIGO_PERSONAL = ?" +
														" AND CARGA_ID = ?" +
														" AND BLOQUE_ID = ?");
			 
			ps.setString(1,CodigoPersonal);
			ps.setString(2,CargaId);
			ps.setInt(3,Integer.parseInt(BloqueId));
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				intCalif.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.IntCalificadoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return intCalif;
	}
	
	public boolean existeReg(Connection con, String CodigoPersonal, String CargaId, String BloqueId) throws SQLException{
		PreparedStatement ps = null;
		boolean ok = false;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT * FROM ENOC.INT_CALIFICADO " + 
														" WHERE CODIGO_PERSONAL = ?" +
														" AND CARGA_ID = ?" +
														" AND BLOQUE_ID = ?");
			 
			ps.setString(1,CodigoPersonal);
			ps.setString(2,CargaId);
			ps.setInt(3,Integer.parseInt(BloqueId));
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.IntCalificadoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
}