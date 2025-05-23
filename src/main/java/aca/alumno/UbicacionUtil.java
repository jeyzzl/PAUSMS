// Clase para la tabla de Alum_Ubicacion
package aca.alumno;

import java.sql.*;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.HashMap;

public class UbicacionUtil{
	
	public boolean insertReg(Connection conn, AlumUbicacion alumUbicacion ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_UBICACION"+ 
				"(CODIGO_PERSONAL, "+
				"P_NOMBRE, P_RELIGION, P_NACIONALIDAD, "+
				"M_NOMBRE, M_RELIGION, M_NACIONALIDAD, "+
				"T_NOMBRE, T_DIRECCION, T_COLONIA, T_CODIGO, T_APARTADO, T_TELEFONO, "+
				"T_EMAIL, T_PAIS, T_ESTADO, T_CIUDAD, T_CELULAR,T_COMUNICA, CODIGO_PADRE, CODIGO_MADRE, FECHA)"+				
				"VALUES( ?, ?, "+
				"TO_NUMBER(?,'99'), "+
				"TO_NUMBER(?,'999'), "+
				" ?, "+
				"TO_NUMBER(?,'99'), "+
				"TO_NUMBER(?,'999'), "+
				" ?, ?, ?, ?, ?, ?, ?, "+
				"TO_NUMBER(?,'999'), "+
				"TO_NUMBER(?,'999'), "+
				"TO_NUMBER(?,'999'), ?, ?, ?, ?, TO_DATE(?, 'DD/MM/YYYY') )");	
			
			ps.setString(1, alumUbicacion.getCodigoPersonal());
			ps.setString(2, alumUbicacion.getPNombre().toUpperCase());
			ps.setString(3, alumUbicacion.getPReligion());
			ps.setString(4, alumUbicacion.getPNacionalidad());
			ps.setString(5, alumUbicacion.getMNombre().toUpperCase());
			ps.setString(6, alumUbicacion.getMReligion());
			ps.setString(7, alumUbicacion.getMNacionalidad());
			ps.setString(8, alumUbicacion.getTNombre().toUpperCase());
			ps.setString(9, alumUbicacion.getTDireccion());
			ps.setString(10, alumUbicacion.getTColonia());
			ps.setString(11, alumUbicacion.getTCodigo());			
			ps.setString(12, alumUbicacion.getTApartado());
			ps.setString(13, alumUbicacion.getTTelefono());
			ps.setString(14, alumUbicacion.getTEmail());
			ps.setString(15, alumUbicacion.getTPais());
			ps.setString(16, alumUbicacion.getTEstado());
			ps.setString(17, alumUbicacion.getTCiudad());
			ps.setString(18, alumUbicacion.gettCelular());
			ps.setString(19, alumUbicacion.gettComunica());
			ps.setString(20, alumUbicacion.getCodigoPadre());
			ps.setString(21, alumUbicacion.getCodigoMadre());
			ps.setString(22, alumUbicacion.getFecha());
						
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.UbicacionUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, AlumUbicacion alumUbicacion ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_UBICACION "+ 
				"SET "+				
				"P_NOMBRE = ?, "+
				"P_RELIGION = TO_NUMBER(?,'99'), "+
				"P_NACIONALIDAD = TO_NUMBER(?,'999'), "+
				"M_NOMBRE = ?, "+
				"M_RELIGION = TO_NUMBER(?,'99'), "+
				"M_NACIONALIDAD = TO_NUMBER(?,'999'), "+
				"T_NOMBRE = ?, "+
				"T_DIRECCION = ?, "+
				"T_COLONIA = ?, "+
				"T_CODIGO = ?, "+
				"T_APARTADO = ?, "+
				"T_TELEFONO = ?, "+
				"T_EMAIL = ?, "+				
				"T_PAIS = TO_NUMBER(?,'999'), "+
				"T_ESTADO = TO_NUMBER(?,'999'), "+
				"T_CIUDAD = TO_NUMBER(?,'999'), " +
				"T_CELULAR = ?, " +
				"T_COMUNICA = ?, "+
				"CODIGO_PADRE = ?,"+
				" CODIGO_MADRE = ?,"+
				"FECHA = TO_DATE(?,'DD/MM/YYYY')"+
				"WHERE CODIGO_PERSONAL = ? ");
				
			ps.setString(1, alumUbicacion.getPNombre().toUpperCase());
			ps.setString(2, alumUbicacion.getPReligion());
			ps.setString(3, alumUbicacion.getPNacionalidad());
			ps.setString(4, alumUbicacion.getMNombre().toUpperCase());
			ps.setString(5, alumUbicacion.getMReligion());
			ps.setString(6, alumUbicacion.getMNacionalidad());
			ps.setString(7, alumUbicacion.getTNombre().toUpperCase());
			ps.setString(8, alumUbicacion.getTDireccion());
			ps.setString(9, alumUbicacion.getTColonia());
			ps.setString(10, alumUbicacion.getTCodigo());
			ps.setString(11, alumUbicacion.getTApartado());
			ps.setString(12, alumUbicacion.getTTelefono());
			ps.setString(13, alumUbicacion.getTEmail());
			ps.setString(14, alumUbicacion.getTPais());
			ps.setString(15, alumUbicacion.getTEstado());
			ps.setString(16, alumUbicacion.getTCiudad());
			ps.setString(17, alumUbicacion.gettCelular());
			ps.setString(18, alumUbicacion.gettComunica());
			ps.setString(19, alumUbicacion.getCodigoPadre());
			ps.setString(20, alumUbicacion.getCodigoMadre());
			ps.setString(21, alumUbicacion.getFecha());
			ps.setString(22, alumUbicacion.getCodigoPersonal());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error  - aca.alumno.UbicacionUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updatePadres(Connection conn, String codigoPersonal, String padre, String madre) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_UBICACION SET P_NOMBRE = ?, M_NOMBRE = ? WHERE CODIGO_PERSONAL = ? ");
			
			ps.setString(1, padre.toUpperCase());			
			ps.setString(2, madre.toUpperCase());
			ps.setString(3, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error  - aca.alumno.UbicacionUtil|updatePadres|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateUbicacion(Connection conn, AlumUbicacion alumUbicacion ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_UBICACION "+ 
				" SET "+				
				" T_DIRECCION = ?, "+
				" T_COLONIA = ?, "+
				" T_CODIGO = ?, "+
				" T_APARTADO = ?, "+
				" T_TELEFONO = ?, "+
				" T_EMAIL = ?, "+				
				" T_PAIS = TO_NUMBER(?,'999'), "+
				" T_ESTADO = TO_NUMBER(?,'999'), "+
				" T_CIUDAD = TO_NUMBER(?,'999')," +
				" T_CELULAR = ?, " +
				" T_COMUNICA = ? "+
				" WHERE CODIGO_PERSONAL = ? ");

			ps.setString(1, alumUbicacion.getTDireccion());
			ps.setString(2, alumUbicacion.getTColonia());
			ps.setString(3, alumUbicacion.getTCodigo());
			ps.setString(4, alumUbicacion.getTApartado());
			ps.setString(5, alumUbicacion.getTTelefono());
			ps.setString(6, alumUbicacion.getTEmail());
			ps.setString(7, alumUbicacion.getTPais());
			ps.setString(8, alumUbicacion.getTEstado());
			ps.setString(9, alumUbicacion.getTCiudad());
			ps.setString(10, alumUbicacion.gettCelular());
			ps.setString(11, alumUbicacion.gettComunica());	
			ps.setString(12, alumUbicacion.getCodigoPersonal());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error  - aca.alumno.UbicacionUtil|updateUbicacion|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String codigoPersonal ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_UBICACION "+ 
				"WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error  - aca.alumno.UbicacionUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public AlumUbicacion mapeaRegId( Connection conn, String codigoPersonal ) throws SQLException{
		
		AlumUbicacion ubicacion = new AlumUbicacion();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CODIGO_PERSONAL, "+
				"P_NOMBRE, P_RELIGION, P_NACIONALIDAD, "+
				"M_NOMBRE, M_RELIGION, M_NACIONALIDAD, "+
				"T_NOMBRE, T_DIRECCION, T_COLONIA, T_CODIGO, T_APARTADO, T_TELEFONO, "+
				"T_EMAIL, T_PAIS, T_ESTADO, T_CIUDAD, T_CELULAR, T_COMUNICA, CODIGO_PADRE, CODIGO_MADRE, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA "+
				"FROM ENOC.ALUM_UBICACION WHERE CODIGO_PERSONAL = ? "); 
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){
				
				ubicacion.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.UbicacionUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ubicacion;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_UBICACION "+ 
				"WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.UbicacionUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public static String getTutorNombre(Connection conn, String codigoPersonal) throws SQLException{		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String tutor			= "x";
		
		try{
			ps = conn.prepareStatement("SELECT T_NOMBRE FROM ENOC.ALUM_UBICACION "+ 
				"WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				tutor = rs.getString("T_NOMBRE");			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.UbicacionUtil|getTutorNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return tutor;
	}
	
	public boolean updateOtroEdoCd(Connection conn, String codigoPersonal, String edocd) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_UBICACION "+ 
				"SET "+				
				"T_EDOCD = ? "+
				"WHERE CODIGO_PERSONAL = ? ");
				
			ps.setString(1, edocd);
			ps.setString(2, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error  - aca.alumno.UbicacionUtil|updateOtroEdoCd|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public static String getOtroEdoCd(Connection conn, String codigoPersonal) throws SQLException{		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String otro			= "x";
		
		try{
			ps = conn.prepareStatement("SELECT T_EDOCD FROM ENOC.ALUM_UBICACION "+ 
				"WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				otro = rs.getString("T_EDOCD");			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.UbicacionUtil|getOtroEdoCd|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return otro;
	}
		
	public ArrayList<AlumUbicacion> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AlumUbicacion> lisUbicacion= new ArrayList<AlumUbicacion>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, "+
				"P_NOMBRE, P_RELIGION, P_NACIONALIDAD, "+
				"M_NOMBRE, M_RELIGION, M_NACIONALIDAD, "+
				"T_NOMBRE, T_DIRECCION, T_COLONIA, T_CODIGO, T_APARTADO, T_TELEFONO, "+
				"T_EMAIL, T_PAIS, T_ESTADO, T_CIUDAD, T_CELULAR, T_COMUNICA, CODIGO_PADRE, CODIGO_MADRE, FECHA "+
				"FROM ENOC.ALUM_UBICACION "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumUbicacion ubicacion = new AlumUbicacion();
				ubicacion.mapeaReg(rs);
				lisUbicacion.add(ubicacion);
			}
			
		}catch(Exception ex){
			System.out.println("Error  - aca.alumno.UbicacionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisUbicacion;
	}
	
	
	public TreeMap<String,AlumUbicacion> treeAll(Connection conn, String orden ) throws SQLException{
		
		TreeMap<String,AlumUbicacion> tree	= new TreeMap<String,AlumUbicacion>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, "+
				"P_NOMBRE, P_RELIGION, P_NACIONALIDAD, "+
				"M_NOMBRE, M_RELIGION, M_NACIONALIDAD, "+
				"T_NOMBRE, T_DIRECCION, T_COLONIA, T_CODIGO, T_APARTADO, T_TELEFONO, "+
				"T_EMAIL, T_PAIS, T_ESTADO, T_CIUDAD, T_CELULAR, T_COMUNICA, CODIGO_PADRE, CODIGO_MADRE, FECHA "+
				"FROM ENOC.ALUM_UBICACION WHERE CODIGO_PERSONAL IN(SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS) "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlumUbicacion ubic = new AlumUbicacion();
				ubic.mapeaReg(rs);
				llave = ubic.getCodigoPersonal();
				tree.put(llave, ubic);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.UbicacionUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return tree;
	}
	
	public HashMap<String,AlumUbicacion> mapInscritos(Connection conn) throws SQLException{
		
		HashMap<String,AlumUbicacion> map	= new HashMap<String,AlumUbicacion>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, P_NOMBRE, P_RELIGION, P_NACIONALIDAD, M_NOMBRE, M_RELIGION, M_NACIONALIDAD,"+
				" T_NOMBRE, T_DIRECCION, T_COLONIA, T_CODIGO, T_APARTADO, T_TELEFONO, T_EMAIL, T_PAIS, T_ESTADO, T_CIUDAD, T_CELULAR, T_COMUNICA,"+
				" CODIGO_PADRE, CODIGO_MADRE, FECHA"+
				" FROM ENOC.ALUM_UBICACION"+
				" WHERE CODIGO_PERSONAL IN(SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)"; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlumUbicacion ubicacion = new AlumUbicacion();
				ubicacion.mapeaReg(rs);
				llave = ubicacion.getCodigoPersonal();
				map.put(llave, ubicacion);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.UbicacionUtil|mapInscritos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String, AlumUbicacion> mapAlumnosEnCargas(Connection conn, String cargas) throws SQLException{
		
		HashMap<String, AlumUbicacion> map		= new HashMap<String,AlumUbicacion>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		try{
			String comando	= "SELECT * FROM ENOC.ALUM_UBICACION " +
					" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+cargas+"))";			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumUbicacion personal = new AlumUbicacion();
				personal.mapeaReg(rs);
				map.put(personal.getCodigoPersonal(), personal);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.UbicacionUtil|mapAlumnosEnCargas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
}