package aca.emp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class EmpleadoUtil {
	
	public Empleado mapeaRegId( Connection conn, String id) throws SQLException{
		Empleado empleado = new Empleado();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"TO_NUMBER(ID,'9999999') AS ID, CLAVE, NOMBRE, APPATERNO, APMATERNO,  " +
				"TO_CHAR(FECHANACIMIENTO, 'DD/MM/YYYY') AS FECHANACIMIENTO, DIRECCION, GENERO, STATUS, " +
				"TO_NUMBER(NACIONALIDAD,'999') AS NACIONALIDAD "+   
				"FROM ARON.EMPLEADO "+
				"WHERE ID = ?");
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				empleado.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpleadoUtil|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		return empleado;
	}
	
	public Empleado mapeaRegClave( Connection conn, String clave) throws SQLException{
		Empleado empleado = new Empleado();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"TO_NUMBER(ID,'9999999') AS ID, CLAVE, NOMBRE, APPATERNO, APMATERNO,  " +
				"TO_CHAR(FECHANACIMIENTO, 'DD/MM/YYYY') AS FECHANACIMIENTO, DIRECCION, GENERO, STATUS, " +
				"TO_NUMBER(NACIONALIDAD,'999') AS NACIONALIDAD "+
				"FROM ARON.EMPLEADO "+
				"WHERE ID = ?");
			
			ps.setString(1, clave);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				empleado.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpleadoUtil|mapeaRegClave|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		return empleado;
	}
	
	public boolean existeReg(Connection conn, String id) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs	= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT ID FROM EMPLEADO "+
				"WHERE EMPLEADO = ? ");
			
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpleadoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean existeRegId(Connection conn, String id) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT ID FROM ARON.EMPLEADO "+
				"WHERE ID = ? ");
			
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpleadoUtil|existeRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public static boolean existeRegClave(Connection conn, String clave) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT CLAVE FROM ARON.EMPLEADO "+
				"WHERE CLAVE = ? ");
			
			ps.setString(1, clave);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpleadoUtil|existeRegClave|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public static String creditosEmp(Connection conn, String codigoPersonal) throws SQLException{
		String 		creditos 	= "";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT CREDITOS_AUTORIZADOS FROM NOE.PER_EMPESTUDIOS "+
				"WHERE MATRICULA='"+codigoPersonal+"' AND STATUS='A'");
			
			rs = ps.executeQuery();
			if (rs.next()){
				creditos = rs.getInt("CREDITOS_AUTORIZADOS")+"";
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpleadoUtil|creditosEmp|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return creditos;
	}
	
	public static String getId(Connection conn, String codigoPersonal) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String id				= "0";
		
		try{
			ps = conn.prepareStatement("SELECT ID FROM ARON.EMPLEADO WHERE CLAVE='"+codigoPersonal+"'");
			
			rs = ps.executeQuery();
			if (rs.next()){
				id = rs.getString("ID");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpleadoUtil|getId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return id;
	}
	
	public static String getNombreEmpleado(Connection conn, String codigoPersonal, String opcion) throws SQLException{
 		
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		String nombre			= "";
 		
 		try{
 			if (opcion.equals("NOMBRE")){
 				ps = conn.prepareStatement("SELECT NOMBRE||' '||APPATERNO||' '||APMATERNO AS NOMBRE FROM ARON.EMPLEADO "+
 					"WHERE CLAVE = ? ");
 			}else if (opcion.equals("APELLIDO")){
 				ps = conn.prepareStatement("SELECT APPATERNO||' '||APMATERNO||' '||NOMBRE AS NOMBRE FROM ARON.EMPLEADO "+
 					"WHERE CLAVE = ? ");
 			}else{
 				ps = conn.prepareStatement("SELECT NOMBRE||' '||APPATERNO||' '||APMATERNO AS NOMBRE FROM ARON.EMPLEADO "+
 					"WHERE CLAVE = ? ");
 			}			
 			ps.setString(1,codigoPersonal); 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				nombre = rs.getString("NOMBRE");
 			else
 				nombre = "X";
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.emp.EmpleadoUtil|getNombreEmpleado|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { } 
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		return nombre;
 	}
	
	public static String getNombreCorto(Connection conn, String codigoPersonal, String opcion) throws SQLException{
 		
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		String nombre			= "";
 		
 		try{
 			if (opcion.equals("NOMBRE")){
 				ps = conn.prepareStatement("SELECT NOMBRE||' '||APPATERNO AS NOMBRE FROM ARON.EMPLEADO "+
 					"WHERE CLAVE = ? ");
 			}else if (opcion.equals("APELLIDO")){
 				ps = conn.prepareStatement("SELECT APPATERNO||' '||NOMBRE AS NOMBRE FROM ARON.EMPLEADO "+
 					"WHERE CLAVE = ? ");
 			}else{
 				ps = conn.prepareStatement("SELECT NOMBRE||' '||APPATERNO AS NOMBRE FROM ARON.EMPLEADO "+
 					"WHERE CLAVE = ? ");
 			}			
 			ps.setString(1,codigoPersonal); 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				nombre = rs.getString("NOMBRE");
 			else
 				nombre = "X";
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.emp.EmpleadoUtil|getNombreCorto|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { } 
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		return nombre;
 	}

	public ArrayList<Empleado> getListAll(Connection conn, String orden ) throws SQLException{
			
			ArrayList<Empleado> lisEmpleado	    	    = new ArrayList<Empleado>();
			Statement st 		                		= conn.createStatement();
			ResultSet rs 				                = null;
			String comando                     			= "";
			
			try{
				comando = "SELECT TO_NUMBER(ID,'9999999999999999999') AS ID, CLAVE, NOMBRE, APPATERNO," +
						"APMATERNO, " +
						"TO_CHAR(FECHANACIMIENTO, 'DD/MM/YYYY') AS FECHANACIMIENTO, DIRECCION, GENERO " +
						" STATUS, TO_NUMBER(NACIONALIDAD,'9999999999999999999') AS NACIONALIDAD FROM ARON.EMPLEADO "+ orden;
				
				rs = st.executeQuery(comando);
				while (rs.next()){
					
					Empleado eEmpleado = new Empleado();
					eEmpleado.mapeaReg(rs);
					lisEmpleado.add(eEmpleado);
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.emp.EmpleadoUtil|getListAll|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return lisEmpleado;
		}
		
		public ArrayList<Empleado> getLista(Connection conn, String id, String orden ) throws SQLException{
			
			ArrayList<Empleado> lisEmplea	    = new ArrayList<Empleado>();
			Statement st 					    = conn.createStatement();
			ResultSet rs 					    = null;
			String comando	             		= "";
			
			try{
				comando = "SELECT TO_NUMBER(ID,'9999999999999999999') AS ID, CLAVE, NOMBRE, APPATERNO, APMATERNO, " +
						" TO_CHAR(FECHANACIMIENTO, 'DD/MM/YYYY') AS FECHANACIMIENTO, DIRECCION, GENERO, STATUS, " +
						"TO_NUMBER(NACIONALIDAD,'9999999999999999999') AS NACIONALIDAD FROM ARON.EMPLEADO "+
						" WHERE ID = '"+id+"'" + orden;
				
				rs = st.executeQuery(comando);
				while (rs.next()){
					
					Empleado emp = new Empleado();
					emp.mapeaReg(rs);
					lisEmplea.add(emp);
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.emp.EmpleadoUtil|getLista|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return lisEmplea;
		}
		
		/* Map de empleados con materias diferidas */
		public static HashMap<String, Empleado> mapEmpleadosDiferidos(Connection conn) throws SQLException{
			
			HashMap<String,Empleado> map		= new HashMap<String,Empleado>();
			Statement st 							= conn.createStatement();
			ResultSet rs		 					= null;
			String comando							= "";
					
			try{				
				comando = " SELECT ID, CLAVE, NOMBRE, APPATERNO, APMATERNO, " 
						+ " TO_CHAR(FECHANACIMIENTO, 'DD/MM/YYYY') AS FECHANACIMIENTO, DIRECCION, GENERO, STATUS, " 
						+ " NACIONALIDAD FROM ARON.EMPLEADO "
						+ " WHERE CLAVE IN ( SELECT CODIGO_PERSONAL FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT WHERE TIPOCAL_ID IN ('5','6')) )";
				rs = st.executeQuery(comando);			
				while (rs.next()){
					
					Empleado emp = new Empleado();
					emp.mapeaReg(rs);
					map.put(emp.getClave(), emp);
					
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.emp.EmpleadoUtil|mapEmpleadosDiferidos|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return map;
		}
		
		public HashMap<String, String> mapEmpleado(Connection conn) throws SQLException{
			
			HashMap<String,String> map		= new HashMap<String,String>();
			Statement st 							= conn.createStatement();
			ResultSet rs		 					= null;
			String comando							= "";
					
			try{
				comando = "SELECT CLAVE, NOMBRE||' '||APPATERNO||' '||APMATERNO AS NOMBRE FROM ARON.EMPLEADO";			
				rs = st.executeQuery(comando);			
				while (rs.next()){				
					map.put(rs.getString("CLAVE"),rs.getString("NOMBRE"));
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.emp.EmpleadoUtil|mapEmpleado|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return map;
		}
		
		public HashMap<String, String> mapEmpleadoCorto(Connection conn) throws SQLException{
			
			HashMap<String,String> map		= new HashMap<String,String>();
			Statement st 							= conn.createStatement();
			ResultSet rs		 					= null;
			String comando							= "";
					
			try{
				comando = "SELECT CLAVE, NOMBRE||' '||APPATERNO AS NOMBRE FROM ARON.EMPLEADO";	
				rs = st.executeQuery(comando);			
				while (rs.next()){				
					map.put(rs.getString("CLAVE"),rs.getString("NOMBRE"));
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.emp.EmpleadoUtil|mapEmpleado|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return map;
		}
		
		public static String getNacionalidad(Connection conn, String codigoPersonal) throws SQLException{
			PreparedStatement ps	= null;
			ResultSet 		rs		= null;
			String nacionalidad		= "0";
			
			try{
				ps = conn.prepareStatement("SELECT NACIONALIDAD FROM EMPLEADO WHERE CLAVE = ? ");
				ps.setString(1, codigoPersonal);
				
				rs = ps.executeQuery();
				if (rs.next())
					nacionalidad = rs.getString("Nacionalidad");
				
			}catch(Exception ex){
				System.out.println("Error - aca.emp.EmpleadoUtil|getNacionalidad|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { ps.close(); } catch (Exception ignore) { }
			}
			
			return nacionalidad;
		}		
		
		public static String getTipoEmpleado(Connection conn, String id) throws SQLException{
			Statement st 			= conn.createStatement();			
			ResultSet 		rs		= null;
			String comando 			= ""; 
			String tipo				= "0";
			
			try{
				comando = "SELECT ID_TIPOEMPLEADO AS TIPO FROM ARON.EMPLEADOLABORALES WHERE ID = TO_NUMBER('"+id+"','9999')";
				
				rs = st.executeQuery(comando);
				if (rs.next())
					tipo = rs.getString("TIPO");
				
			}catch(Exception ex){
				System.out.println("Error - aca.emp.EmpleadoUtil|getTipoEmpleado|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return tipo;
		}
		
		public static String getRFC(Connection conn, String codigoPersonal ) throws Exception{
	 		PreparedStatement ps	= null;
	 		ResultSet rs			= null;		
			String rfc 				= "X";
			try{
				ps = conn.prepareStatement("SELECT RFC FROM ARON.EMPLEADOLABORALES B" +
						" WHERE B.ID = (SELECT ID FROM ARON.EMPLEADO C" +
									  " WHERE C.CLAVE = ?)");
				
				ps.setString(1, codigoPersonal);
				
				rs= ps.executeQuery();
				if(rs.next()){ 
					rfc = rs.getString("RFC");
				}			
			}catch(Exception ex){
				System.out.println("Error - aca.emp.EmpeadoUtil|getRFC|:"+ex);
			}finally{
				try { ps.close(); } catch (Exception ignore) { }
				try { rs.close(); } catch (Exception ignore) { }
			}
			
			return rfc;
		}
		
		public static String getIMSS(Connection conn, String codigoPersonal ) throws Exception{
	 		PreparedStatement ps	= null;
	 		ResultSet rs			= null;		
			String imss 				= "X";
			try{
				ps = conn.prepareStatement("SELECT IMMS FROM ARON.EMPLEADOLABORALES B" +
						" WHERE B.ID = (SELECT ID FROM ARON.EMPLEADO C" +
									  " WHERE C.CLAVE = ?)");
				
				ps.setString(1, codigoPersonal);
				
				rs= ps.executeQuery();
				if(rs.next()){ 
					imss = rs.getString("IMMS");
				}			
			}catch(Exception ex){
				System.out.println("Error - aca.emp.EmpeadoUtil|getIMSS|:"+ex);
			}finally{
				try { ps.close(); } catch (Exception ignore) { }
				try { rs.close(); } catch (Exception ignore) { }
			}
			
			return imss;
		}
		
		public static String getFechaAlta(Connection conn, String codigoPersonal ) throws Exception{
	 		PreparedStatement ps	= null;
	 		ResultSet rs			= null;		
			String fecha 				= "X";
			try{
				ps = conn.prepareStatement("SELECT TO_CHAR(FECHA_ALTA,'DD/MM/YYYY') AS FECHA_ALTA FROM ARON.EMPLEADOLABORALES B" +
						" WHERE B.ID = (SELECT ID FROM ARON.EMPLEADO C" +
									  " WHERE C.CLAVE = ?)");
				
				ps.setString(1, codigoPersonal);
				
				rs= ps.executeQuery();
				if(rs.next()){ 
					fecha = rs.getString("FECHA_ALTA");
				}			
			}catch(Exception ex){
				System.out.println("Error - aca.emp.EmpeadoUtil|getFechaAlta|:"+ex);
			}finally{
				try { ps.close(); } catch (Exception ignore) { }
				try { rs.close(); } catch (Exception ignore) { }
			}
			
			return fecha;
		}
		
		public static String getCurp(Connection conn, String codigoPersonal ) throws Exception{
	 		PreparedStatement ps	= null;
	 		ResultSet rs			= null;		
			String curp				= "X";
			try{
				ps = conn.prepareStatement("SELECT CURP FROM ARON.EMPLEADOLABORALES B" +
						" WHERE B.ID = (SELECT ID FROM ARON.EMPLEADO C" +
									  " WHERE C.CLAVE = ?)");
				
				ps.setString(1, codigoPersonal);
				
				rs= ps.executeQuery();
				if(rs.next()){ 
					curp = rs.getString("CURP");
				}			
			}catch(Exception ex){
				System.out.println("Error - aca.emp.EmpleadoUtil|getCurp|:"+ex);
			}finally{
				try { ps.close(); } catch (Exception ignore) { }
				try { rs.close(); } catch (Exception ignore) { }
			}
			
			return curp;
		}
		
		public static String getAronPais(Connection conn, String codigoPersonal ) throws Exception{
	 		PreparedStatement ps	= null;
	 		ResultSet rs			= null;		
			String rfc 				= "X";
			try{
				ps = conn.prepareStatement("SELECT COALESCE(NOMBRE,'X') AS NOMBRE FROM ARON.PAIS " +
						"WHERE ID = (SELECT ID_PAIS FROM ARON.ESTADO " +
						"	WHERE ID = (SELECT C.ID_ESTADO FROM ARON.CIUDAD C " +
						"		WHERE ID = (SELECT ID_CIUDAD FROM ARON.EMPLEADOPERSONALES " +
						"			WHERE ID = (SELECT ID FROM ARON.EMPLEADO WHERE CLAVE = ?))))");
				
				ps.setString(1, codigoPersonal);
				
				rs= ps.executeQuery();
				if(rs.next()){ 
					rfc = rs.getString("NOMBRE");
				}			
			}catch(Exception ex){
				System.out.println("Error - aca.emp.EmpleadoUtil|getAronPais|:"+ex);
			}finally{
				try { ps.close(); } catch (Exception ignore) { }
				try { rs.close(); } catch (Exception ignore) { }
			}
			
			return rfc;
		}
		
		public static String getDepartamento(Connection conn, String codigoPersonal ) throws Exception{
	 		PreparedStatement ps	= null;
	 		ResultSet rs			= null;		
			String rfc 				= "X";
			try{
				ps = conn.prepareStatement("SELECT DEPARTAMENTO FROM ENOC.EMP_DATOS WHERE ID_EMPLEADO = ? ");				
				ps.setString(1, codigoPersonal);				
				rs= ps.executeQuery();
				if(rs.next()){ 
					rfc = rs.getString("DEPARTAMENTO");
				}			
			}catch(Exception ex){
				System.out.println("Error - aca.vista.EmpleadoUtil|getDepartamento|:"+ex);
			}finally{
				try { ps.close(); } catch (Exception ignore) { }
				try { rs.close(); } catch (Exception ignore) { }
			}
			
			return rfc;
		}
		
		public ArrayList<String> getListEmpleadosNombre(Connection conn, String orden) throws SQLException{
			
			ArrayList<String> list		= new ArrayList<String>();
			Statement st 		        = conn.createStatement();
			ResultSet rs 				= null;
			String comando              = "";
			
			try{
				comando = "SELECT CLAVE, NOMBRE||' '||APPATERNO||' '||APMATERNO AS NOMBRE FROM ARON.EMPLEADO WHERE STATUS = 'A' "+ orden;
				
				rs = st.executeQuery(comando);
				while (rs.next()){
					list.add(rs.getString("CLAVE")+"@@"+rs.getString("NOMBRE"));
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.emp.EmpleadoUtil|getListEmpleadosNombre|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return list;
		}

}