package aca.log;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class LogAlumnoUtil{
	
	public boolean insertReg(Connection conn, LogAlumno logAlumno ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;		
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.LOG_ALUMNO(ID, TABLA, OPERACION, IP, FECHA, USUARIO, DATOS)" +
			" VALUES(TO_NUMBER(?, '9999999'), ?, ?, ?, now(), ?, ?)");
			
			ps.setString(1, logAlumno.getId());
			ps.setString(2, logAlumno.getTabla());
			ps.setString(3, logAlumno.getOperacion());
			ps.setString(4, logAlumno.getIp());			
			ps.setString(5, logAlumno.getUsuario());			
			ps.setString(6, logAlumno.getDatos());
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.log.LogAlumnoUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public LogAlumno mapeaRegId( Connection conn, String id ) throws SQLException{
		
		LogAlumno logAlumno = new LogAlumno();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT ID, TABLA, OPERACION, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, USUARIO, IP, DATOS FROM LOG_ALUMNO"
					+ " WHERE ID = ?"); 
			
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			if (rs.next()){
				
				logAlumno.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.log.LogAlumnoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return logAlumno;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(ID)+1,1) AS MAXIMO FROM ENOC.LOG_ALUMNO"); 
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
				if(maximo == null){
					maximo = "1";
				}
		}catch(Exception ex){
			System.out.println("Error - aca.log.LogAlumnoUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return maximo;		
	}
	
	public String maximoId(Connection conn, String tabla, String codigoAlumno) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String maximo 			= "0";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(ID),0) AS MAXIMO FROM ENOC.LOG_ALUMNO WHERE TABLA = ? AND DATOS = ?");
			
			ps.setString(1, tabla);
			ps.setString(2, codigoAlumno);
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.log.LogAlumnoUtil|maximoId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return maximo;		
	}	
	
	public ArrayList<LogAlumno> getListAll(Connection conn, String matricula) throws SQLException{
		
		ArrayList<LogAlumno> lis		= new ArrayList<LogAlumno>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT ID, TABLA, OPERACION, FECHA, USUARIO, IP, DATOS FROM ENOC.LOG_ALUMNO WHERE DATOS LIKE '%"+matricula+"%'";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				LogAlumno obj = new LogAlumno();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.log.LogAlumnoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return lis;
	}
	
	public ArrayList<String> lisActualizadosPorFecha(Connection conn, String fechaIni, String fechaFin, String tablas) throws SQLException{
		
		ArrayList<String> lis		= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT DISTINCT (DATOS) AS CODIGO_PERSONAL FROM LOG_ALUMNO WHERE FECHA BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') AND TABLA IN ("+tablas+")";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				lis.add(rs.getString("CODIGO_PERSONAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.log.LogAlumnoUtil|lisActualizadosPorFecha|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lis;
	}
	
	public HashMap<String, String> mapaModificaTabla(Connection conn, String fechaIni, String fechaFin, String tabla) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{
			comando = " SELECT DISTINCT(DATOS) AS CODIGO_PERSONAL FROM ENOC.LOG_ALUMNO"
					+ " WHERE FECHA BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"
					+ " AND TABLA = '"+tabla+"'";
		
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL"), rs.getString("CODIGO_PERSONAL"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.log.LogAlumnoUtil|mapaModificaTabla|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
}