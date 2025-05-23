package aca.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class AlumActualizaUtil {
	
	public boolean insertReg(Connection conn, AlumActualiza alumActualiza ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_ACTUALIZA"+ 
				"(CODIGO_PERSONAL, CODIGO_EMPLEADO, FECHA, ESTADO ) "+
				"VALUES( ?, ?, TO_DATE(?,'DD/MM/YYYY'), ? )");
					
			ps.setString(1, alumActualiza.getCodigoPersonal());
			ps.setString(2, alumActualiza.getCodigoEmpleado());
			ps.setString(3, alumActualiza.getFecha());
			ps.setString(4, alumActualiza.getEstado());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumActualizaUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, AlumActualiza alumActualiza ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_ACTUALIZA "+ 
				"SET ESTADO = ?, CODIGO_EMPLEADO = ?, FECHA = TO_DATE(?, 'DD/MM/YYYY') "+
				"WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, alumActualiza.getEstado());
			ps.setString(2, alumActualiza.getCodigoEmpleado());
			ps.setString(3, alumActualiza.getFecha());
			ps.setString(4, alumActualiza.getCodigoPersonal());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;

		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumActualizaUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String codigoPersonal ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_ACTUALIZA "+ 
				"WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumActualizaUtil|deletetReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public AlumActualiza mapeaRegId( Connection conn, String codigoPersonal) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		AlumActualiza alumActualiza = new AlumActualiza();
		try{
			ps = conn.prepareStatement("SELECT "+
				"CODIGO_PERSONAL, CODIGO_EMPLEADO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO "+
				"FROM ENOC.ALUM_ACTUALIZA "+ 
				"WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);	
			
			rs = ps.executeQuery();
			if (rs.next()){
				alumActualiza.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumActualizaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return alumActualiza;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_ACTUALIZA "+ 
				"WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumActualizaUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getCodigoEmpleado(Connection conn, String codigoPersonal ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= " ";
		
		try{
			ps = conn.prepareStatement("SELECT CODIGO_EMPLEADO FROM ENOC.ALUM_ACTUALIZA" +
					" WHERE CODIGO_PERSONAL = ? " +
					" AND ESTADO = 'A' "); 
			ps.setString(1, codigoPersonal);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("CODIGO_EMPLEADO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumActualizaUtil|getCodigoEmpleado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static String getEstado(Connection conn, String codigoPersonal) throws SQLException{		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String solucion			= "";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(ESTADO,' ') AS SOLUCION "+
				"FROM ENOC.ALUM_ACTUALIZA WHERE CODIGO_PERSONAL= ? "); 
			ps.setString(1, codigoPersonal);
			rs= ps.executeQuery();		
			if(rs.next()){
				solucion = rs.getString("SOLUCION");
			}else{
				solucion = " ";
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumActualizaUtil|getEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return solucion;
	}
	
	public static ArrayList<AlumActualiza> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AlumActualiza> lisAlumno	= new ArrayList<AlumActualiza>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CODIGO_EMPLEADO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO " +
 				" FROM ENOC.ALUM_ACTUALIZA "+orden;			 
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlumActualiza alumno = new AlumActualiza();
				alumno.mapeaReg(rs);
				lisAlumno.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumActualizaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return lisAlumno;
	}
	
	public HashMap<String, String> mapAll(Connection conn) throws SQLException{
		HashMap<String, String> map	= new HashMap<String, String>();
		Statement st 								= conn.createStatement();
		ResultSet rs 								= null;
		String comando								= "";
		
				try{
			comando ="SELECT CODIGO_PERSONAL, CODIGO_EMPLEADO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO " +
	 				" FROM ENOC.ALUM_ACTUALIZA ";
			rs = st.executeQuery(comando);
			while (rs.next()){
				map.put(rs.getString("CODIGO_PERSONAL"),rs.getString("ESTADO"));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumActualizaUtil|mapActualiza|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		return map;
	}
	
}
