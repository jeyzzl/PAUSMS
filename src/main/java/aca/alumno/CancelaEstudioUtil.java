// Clase para la tabla de Modulo
package aca.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CancelaEstudioUtil{
	
	public boolean insertReg(Connection conn, CancelaEstudio cancelaEstudio ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CANCELA_ESTUDIO"+ 
				"(CODIGO_PERSONAL, PLAN_ID, USUARIO, FECHA, COMENTARIO, ESTADO) "+
				"VALUES( ?, ?, ?, now(), ?, ?)");
					
			ps.setString(1, cancelaEstudio.getCodigoPersonal());
			ps.setString(2, cancelaEstudio.getPlanId());
			ps.setString(3, cancelaEstudio.getUsuario());
			ps.setString(4, cancelaEstudio.getComentario());
			ps.setString(5, cancelaEstudio.getEstado());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.CancelaEstudioUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CancelaEstudio cancelaEstudio ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CANCELA_ESTUDIO "+ 
				"SET FECHA = now() , " +
				"COMENTARIO = ?, "+
				"USUARIO = ?, "+
				"ESTADO = ? "+
				"WHERE CODIGO_PERSONAL = ? " +
				"AND PLAN_ID = ?");
			
			ps.setString(1, cancelaEstudio.getComentario());
			ps.setString(2, cancelaEstudio.getUsuario());
			ps.setString(3, cancelaEstudio.getEstado());
			ps.setString(4, cancelaEstudio.getCodigoPersonal());
			ps.setString(5, cancelaEstudio.getPlanId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.CancelaEstudioUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String codigoPersonal, String planId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CANCELA_ESTUDIO "+ 
				"WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.CancelaEstudioUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CancelaEstudio mapeaRegId( Connection conn, String codigoPersonal, String planId) throws SQLException{
		
		CancelaEstudio cancelaEstudio = new CancelaEstudio();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT "+
				"CODIGO_PERSONAL, PLAN_ID, USUARIO, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM') AS FECHA, COMENTARIO, ESTADO "+
				"FROM ENOC.CANCELA_ESTUDIO "+ 
				"WHERE CODIGO_PERSONAL = ? " +
				"AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				
				cancelaEstudio.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.CancelaEstudioUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return cancelaEstudio;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal, String planId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL FROM ENOC.CANCELA_ESTUDIO "+ 
				"WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.CancelaEstudioUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean existeRegId(Connection conn, String codigoPersonal, String planId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL FROM ENOC.CANCELA_ESTUDIO "+ 
				"WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.CancelaEstudioUtil|existeRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public String getEstado(Connection conn, String codigoPersonal, String planId) throws SQLException{
		String 		estado 	= "";
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT ESTADO FROM ENOC.CANCELA_ESTUDIO "+ 
				"WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				estado = rs.getString("ESTADO");
						
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.CancelaEstudioUtil|getEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return estado;
	}
	
	public ArrayList<CancelaEstudio> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CancelaEstudio> lisCancela	= new ArrayList<CancelaEstudio>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, PLAN_ID, USUARIO, " +
					"TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM') AS FECHA, COMENTARIO, ESTADO "+
			"FROM ENOC.CANCELA_ESTUDIO "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CancelaEstudio cancela = new CancelaEstudio();
				cancela.mapeaReg(rs);
				lisCancela.add(cancela);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.CancelaEstudioUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCancela;
	}	
		
}