package aca.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AlumConstanciaUtil {
	
	public boolean insertReg(Connection conn, AlumConstancia alumConstancia ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_CONSTANCIA"+ 
				"(CONSTANCIA_ID, CONSTANCIA_NOMBRE, CONSTANCIA, USUARIOS, TIPO) "+
				"VALUES( TO_NUMBER(?,'9999'), ?, ?, ?, ? )");
					
			ps.setString(1, alumConstancia.getConstanciaId());
			ps.setString(2, alumConstancia.getConstanciaNombre());
			ps.setString(3, alumConstancia.getConstancia());
			ps.setString(4, alumConstancia.getUsuarios());
			ps.setString(5, alumConstancia.getTipo());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumConstanciaUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, AlumConstancia alumConstancia ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_CONSTANCIA "+ 
				"SET CONSTANCIA_NOMBRE = ?, CONSTANCIA = ?, USUARIOS = ?, TIPO = ? "+
				"WHERE CONSTANCIA_ID = TO_NUMBER(?,'9999') ");
			ps.setString(1, alumConstancia.getConstanciaNombre());
			ps.setString(2, alumConstancia.getConstancia());
			ps.setString(3, alumConstancia.getUsuarios());
			ps.setString(4, alumConstancia.getTipo());
			ps.setString(5, alumConstancia.getConstanciaId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;

		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumConstanciaUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String constanciaId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_CONSTANCIA "+ 
				"WHERE CONSTANCIA_ID = TO_NUMBER(?,'9999') ");
			ps.setString(1, constanciaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumConstanciaUtil|deletetReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public AlumConstancia mapeaRegId( Connection conn, String constanciaId) throws SQLException{
		
		AlumConstancia alumConstancia = new AlumConstancia();		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_CONSTANCIA "+ 
				"WHERE CONSTANCIA_ID = ?");
			ps.setString(1, constanciaId);	
			
			rs = ps.executeQuery();
			if (rs.next()){
				
				alumConstancia.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumConstanciaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return alumConstancia;
	}
	
	public boolean existeReg(Connection conn, String constanciaId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_CONSTANCIA "+ 
				"WHERE CONSTANCIA_ID = ? ");
			ps.setString(1, constanciaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumConstanciaUtil|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT COALESCE( MAX(CONSTANCIA_ID)+1 , 1 ) AS MAXIMO FROM ENOC.ALUM_CONSTANCIA"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumConstanciaUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;
	}
	
	public ArrayList<AlumConstancia> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AlumConstancia> list	= new ArrayList<AlumConstancia>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT * " +
 				" FROM ENOC.ALUM_CONSTANCIA "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlumConstancia obj = new AlumConstancia();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumConstanciaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return list;
	}
	
	public ArrayList<AlumConstancia> getListUsuario(Connection conn, String usuario, String orden ) throws SQLException{
		
		ArrayList<AlumConstancia> list	= new ArrayList<AlumConstancia>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT * " +
 				" FROM ENOC.ALUM_CONSTANCIA WHERE USUARIOS LIKE '%-"+usuario+"-%' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlumConstancia obj = new AlumConstancia();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumConstanciaUtil|getListUsuario|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return list;
	}
	
}
