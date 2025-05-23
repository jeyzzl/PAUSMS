package aca.exa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class ExaCorreoUtil {
	
	public boolean insertReg(Connection conn, ExaCorreo exaCorreo ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"ENOC.EXA_CORREO(CORREO_ID, MATRICULA, CORREO, FECHAACTUALIZACION, " +
				"ELIMINADO, IDCORREO) "+
				"VALUES( TO_NUMBER(?,'99999999'), ?, ?, TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), " +
				"TO_NUMBER(?,'9'), ? )");
			
			ps.setString(1, exaCorreo.getCorreoId());
			ps.setString(2, exaCorreo.getMatricula());
			ps.setString(3, exaCorreo.getCorreo());
			ps.setString(4, exaCorreo.getFechaAct());
			ps.setString(5, exaCorreo.getEliminado());
			ps.setString(6, exaCorreo.getIdCorreo());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaCorreoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean eliminar(Connection conn, String correoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EXA_CORREO"+ 
				" SET ELIMINADO = '1' " +
				" WHERE CORREO_ID = TO_NUMBER(?,'99999999')");
			ps.setString(1, correoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaCorreoUtil|eliminar|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public ExaCorreo mapeaRegIdCorreo( Connection conn, String correoId) throws SQLException{
		ExaCorreo exaCorreo = new ExaCorreo();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT CORREO_ID, MATRICULA, CORREO, " +
					" FECHAACTUALIZACION, ELIMINADO, IDCORREO "+
				"FROM ENOC.EXA_CORREO WHERE CORREO_ID = TO_NUMBER(?,'99999999')"); 
			ps.setString(1,correoId);
			rs = ps.executeQuery();
			if (rs.next()){
				exaCorreo.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaCorreoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return exaCorreo;
	}
	
	public ExaCorreo mapeaRegId( Connection conn, String matricula) throws SQLException{
		ExaCorreo exaCorreo = new ExaCorreo();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT CORREO_ID, MATRICULA, CORREO,  " +
					" FECHAACTUALIZACION, ELIMINADO, IDCORREO "+
				"FROM ENOC.EXA_CORREO WHERE MATRICULA = ?"); 
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if (rs.next()){
				exaCorreo.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaCorreoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return exaCorreo;
	}
	
	public boolean existeReg(Connection conn, String correoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EXA_CORREO WHERE CORREO_ID = TO_NUMBER(?,'99999999') "); 
			ps.setString(1,correoId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaCorreoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean existeAlumno(Connection conn, String matricula) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EXA_CORREO WHERE MATRICULA = ? AND ELIMINADO!=1 "); 
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaCorreoUtil|existeAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String matricula) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(CORREO_ID) AS MAXIMO FROM EXA_CORREO WHERE ELIMINADO !=1 AND MATRICULA = ?"); 
			ps.setString(1, matricula);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaCorreoUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(CORREO_ID)+1 AS MAXIMO FROM ENOC.EXA_CORREO");
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaCorreoUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getCantidadGenerico(Connection conn, String tabla) throws SQLException{
		String res 				= "0";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(DISTINCT(MATRICULA)) AS CANTIDAD FROM "+tabla+" WHERE ELIMINADO != 1 "); 
			rs = ps.executeQuery();
			if (rs.next())
				res = rs.getString("CANTIDAD");
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaCorreoUtil|getCantidadGenerico|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return res;
	}
	
	public ArrayList<ExaCorreo> getCorreo(Connection conn, String matricula, String orden) throws SQLException{
		
		ArrayList<ExaCorreo> list		= new ArrayList<ExaCorreo>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT CORREO_ID, MATRICULA, CORREO," +
					" FECHAACTUALIZACION, ELIMINADO, IDCORREO FROM ENOC.EXA_DIRECCION" +
					" WHERE MATRICULA = '"+matricula+"' AND ELIMINADO != 1 "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ExaCorreo obj = new ExaCorreo();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaCorreoUtil|getCorreo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public static HashMap<String,ExaCorreo> getMapCorreo(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,ExaCorreo> mapCorreo = new HashMap<String,ExaCorreo>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT * FROM ENOC.EXA_CORREO WHERE ELIMINADO != 1 "+ orden;
			
			rs = st.executeQuery(comando);
			String matricula = "";
			while (rs.next()){	
				
			if(!matricula.equals(rs.getString("MATRICULA"))){
				matricula = rs.getString("MATRICULA");
				ExaCorreo correo = new ExaCorreo();
				correo.mapeaReg(rs);
				llave = correo.getMatricula();
				mapCorreo.put(llave, correo);
				
			}	
				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaCorreoUtil|getMapCorreo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapCorreo;
	}
	
	public ArrayList<aca.alumno.AlumPersonal> getAlumnos(Connection conn, String orden) throws SQLException{
		
		ArrayList<aca.alumno.AlumPersonal> list		= new ArrayList<aca.alumno.AlumPersonal>();
		Statement st 								= conn.createStatement();
		ResultSet rs 								= null;
		String comando								= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, "+
				"NOMBRE_LEGAL, TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, "+
				"PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD, EMAIL, "+
				"CURP, ESTADO, COTEJADO, CODIGO_SE, TELEFONO" +
				" FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN( SELECT MATRICULA FROM ENOC.EXA_CORREO WHERE ELIMINADO != 1 ) "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				aca.alumno.AlumPersonal obj = new aca.alumno.AlumPersonal();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaCorreoUtil|getAlumnos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
}
