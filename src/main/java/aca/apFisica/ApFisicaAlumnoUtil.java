// Clase para la tabla de Acceso
package aca.apFisica;

import java.sql.*;
import java.util.ArrayList;

public class ApFisicaAlumnoUtil{
	
	public boolean insertReg(Connection conn, ApFisicaAlumno apFAlumno ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO" +
					" ENOC.APFISICA_ALUMNO(GRUPO_ID, CODIGO_PERSONAL, FECHA, ESTADO)" +
					" VALUES(TO_NUMBER(?,'9999'), ?, TO_DATE(?,'DD/MM/YYYY'), ?)");
			ps.setString(1, apFAlumno.getGrupoId());
			ps.setString(2, apFAlumno.getCodigoPersonal());
			ps.setString(3, apFAlumno.getFecha());
			ps.setString(4, apFAlumno.getEstado());
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaAlumnoUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, ApFisicaAlumno apFAlumno) throws SQLException{
		boolean ok = false;
		try{
			PreparedStatement ps = conn.prepareStatement("UPDATE ENOC.APFISICA_ALUMNO SET FECHA = TO_DATE(?,'DD/MM/YYYY'), ESTADO = ? WHERE GRUPO_ID = TO_NUMBER(?,'9999') AND CODIGO_PERSONAL = ?");
			ps.setString(1, apFAlumno.getFecha());
			ps.setString(2, apFAlumno.getEstado());
			ps.setString(3, apFAlumno.getGrupoId());
			ps.setString(4, apFAlumno.getCodigoPersonal());
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
			try { ps.close(); } catch (Exception ignore) { }
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaAlumnoUtil|updateReg|: "+ex);
		}
		return ok;
	}
	
	public boolean updateQuitarGrupo(Connection conn, String codigoPersonal, String clave ) throws SQLException{
		boolean ok = false;
		try{
			PreparedStatement ps = conn.prepareStatement("UPDATE ENOC.APFISICA_ALUMNO SET ESTADO = 'I' WHERE CODIGO_PERSONAL = ? AND GRUPO_ID IN (SELECT GRUPO_ID FROM ENOC.APFISICA_GRUPO WHERE CLAVE = ?)");
			ps.setString(1, codigoPersonal);
			ps.setString(2, clave);
			
			if ( ps.executeUpdate() >= 0){
				ok = true;
			}else{
				ok = false;
			}
			try { ps.close(); } catch (Exception ignore) { }
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaAlumnoUtil|updateQuitarGrupo|: "+ex);
		}
		return ok;
	}
	
	public boolean updateAll(Connection conn, ApFisicaAlumno apFAlumno) throws SQLException{
		boolean ok = false;
		try{
			PreparedStatement ps = conn.prepareStatement("UPDATE ENOC.APFISICA_ALUMNO SET  FECHA = TO_DATE(?,'DD/MM/YYYY'), ESTADO = ? WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, apFAlumno.getFecha());
			ps.setString(2, apFAlumno.getEstado());
			ps.setString(3, apFAlumno.getCodigoPersonal());
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
			try { ps.close(); } catch (Exception ignore) { }
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaAlumnoUtil|updateAll|: "+ex);
		}
		return ok;
	}
	
	public boolean deleteAlumno(Connection conn, String grupoId, String codigoPersonal) throws SQLException{
		
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.APFISICA_ALUMNO WHERE GRUPO_ID = TO_NUMBER(?, '9999') AND CODIGO_PERSONAL = ?");
			ps.setString(1, grupoId);
			ps.setString(2, codigoPersonal);
			if ( ps.executeUpdate()== 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaAlumnoUtil|deleteAlumno|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String grupoId) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.APFISICA_ALUMNO WHERE GRUPO_ID = TO_NUMBER(?, '9999')");
			ps.setString(1, grupoId);
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaAlumnoUtil|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public ApFisicaAlumno mapeaRegId(Connection con, String codigoPersonal, String grupoId) throws SQLException{
		ApFisicaAlumno apFAlumno = new ApFisicaAlumno();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement("SELECT GRUPO_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, ESTADO"
					+ " FROM ENOC.APFISICA_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"
					+ " AND GRUPO_ID = "+grupoId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				apFAlumno.mapeaReg(rs);
			}
		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.ApFisicaAlumno|mapeaRegId|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
		return apFAlumno;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal, String grupoId) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.APFISICA_ALUMNO WHERE CODIGO_PERSONAL = ? AND GRUPO_ID = TO_NUMBER(?, '9999')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, grupoId);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaAlumnoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static int maximo(Connection conn) throws SQLException{
		int folio				= 0;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(GRUPO_ID)+1,1) AS MAXIMO FROM ENOC.APFISICA_ALUMNO"); 
			rs= ps.executeQuery();
			if(rs.next()){
				folio = rs.getInt("MAXIMO");
			}else{
				folio = 1;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaAlumnoUtil|maximo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return folio;
	}

	// Busca el grupo que tiene asignado el alumno
	public static String grupoAlumnoActivo(Connection conn, String matricula, String clave) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		String folio			= "0";
		
		try{
			ps = conn.prepareStatement("SELECT GRUPO_ID FROM ENOC.APFISICA_ALUMNO WHERE CODIGO_PERSONAL = '"+matricula+"' AND ESTADO = 'A' AND GRUPO_ID IN (SELECT GRUPO_ID FROM ENOC.APFISICA_GRUPO WHERE CLAVE = '"+clave+"')");
			rs= ps.executeQuery();		
			if(rs.next()){
				folio = rs.getString("GRUPO_ID");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaAlumnoUtil|grupoAnterior|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return folio;
	}
	
	public static String grupoAnteriorInactivo(Connection conn, String matricula) throws SQLException{
		String folio				= "";
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT GRUPO_ID FROM ENOC.APFISICA_ALUMNO WHERE CODIGO_PERSONAL = '"+matricula+"' AND ESTADO = 'I' "); 
			rs= ps.executeQuery();		
			if(rs.next()){
				folio = rs.getString("GRUPO_ID");
			}else{
				folio = "-";
			}
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaAlumnoUtil|grupoAnteriorInactivo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return folio;
	}
	
	public static int registrados(Connection conn, String grupoId) throws SQLException{
		int folio				= 0;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(COUNT(GRUPO_ID),0) AS MAXIMO FROM ENOC.APFISICA_ALUMNO WHERE GRUPO_ID = '"+grupoId+"' AND ESTADO = 'A' "); 
			rs= ps.executeQuery();		
			if(rs.next()){
				folio = rs.getInt("MAXIMO");
			}else{
				folio = 1;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaAlumnoUtil|registrados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return folio;
	}
	
	public static boolean tieneGrupoEnCarga(Connection conn, String codigoPersonal, String cargaId, String clave) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.APFISICA_ALUMNO WHERE CODIGO_PERSONAL = ? AND GRUPO_ID IN (SELECT GRUPO_ID FROM ENOC.APFISICA_GRUPO WHERE INSTR(CARGAS,?) > 0 AND CLAVE = ?)");			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			ps.setString(3, clave);
			
			rs = ps.executeQuery();
			if(rs.next()){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaAlumnoUtil|tieneGrupoEnCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
		
	public ArrayList<ApFisicaAlumno> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<ApFisicaAlumno> lisAcceso 	= new ArrayList<ApFisicaAlumno>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	    = "";
		
		try{
			comando = "SELECT GRUPO_ID, CODIGO_PERSONAL, FECHA, ESTADO FROM ENOC.APFISICA_ALUMNO "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ApFisicaAlumno alumno = new ApFisicaAlumno();
				alumno.mapeaReg(rs);
				lisAcceso.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaAlumnoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAcceso;
	}
	
	public ArrayList<String> amigos(Connection conn,String grupoId, String orden) throws SQLException{
		
		ArrayList<String> lis		= new ArrayList<String>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = " SELECT "+
					  " AP.APELLIDO_PATERNO ||' '|| AP.APELLIDO_MATERNO||' '|| AP.NOMBRE AS NOMBRE, AF.CODIGO_PERSONAL AS CODIGO_PERSONAL"+ 
					  " FROM ENOC.APFISICA_ALUMNO AF, ENOC.ALUM_PERSONAL AP "+
					  " WHERE AP.CODIGO_PERSONAL = AF.CODIGO_PERSONAL"+
					  " AND AF.GRUPO_ID = '"+grupoId+"' AND AF.ESTADO = 'A' "+orden;	 
			rs = st.executeQuery(comando);
			while (rs.next()){
				lis.add(rs.getString("NOMBRE")+"@@"+rs.getString("CODIGO_PERSONAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaAlumnoUtil|amigos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<ApFisicaAlumno> getListAlum(Connection conn, String estado, String grupoId, String orden ) throws SQLException{
		
		ArrayList<ApFisicaAlumno> lisAcceso 	= new ArrayList<ApFisicaAlumno>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	    = "";
		
		try{
			comando = "SELECT GRUPO_ID, CODIGO_PERSONAL, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA , ESTADO FROM ENOC.APFISICA_ALUMNO WHERE ESTADO ='"+estado+"' AND  GRUPO_ID ='"+grupoId+"'"+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ApFisicaAlumno alumno = new ApFisicaAlumno();
				alumno.mapeaReg(rs);
				lisAcceso.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaAlumnoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAcceso;
	}
	
	public ArrayList<String> alumMateria(Connection conn, String clave, String fecha, String orden ) throws SQLException{
		
		ArrayList<String> lisAcceso 	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	    = "";
		
		try{
			comando = "SELECT ENOC.ALUM_APELLIDO(CODIGO_PERSONAL) AS NOMBRE, CODIGO_PERSONAL, GRUPO_ID FROM ENOC.APFISICA_ALUMNO "
					+ "WHERE GRUPO_ID IN (SELECT GRUPO_ID FROM ENOC.APFISICA_GRUPO "
					+ "WHERE CLAVE = '"+clave+"' AND F_INICIO <= TO_DATE('"+fecha+"', 'DD/MM/YYYY')  "
					+ "AND F_FINAL >= TO_DATE('"+fecha+"', 'DD/MM/YYYY')) AND ESTADO = 'A'" +orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				lisAcceso.add(rs.getString("NOMBRE")+"@@"+rs.getString("CODIGO_PERSONAL")+"@@"+rs.getString("GRUPO_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaAlumnoUtil|alumMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAcceso;
	}
	
	public ArrayList<String> alumSinRegistro(Connection conn, String cargaId, String clave, String modalidades, String orden ) throws SQLException{
		
		ArrayList<String> lisAcceso 	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	    = "";
		
		try{
			comando = "SELECT ENOC.ALUM_APELLIDO(CODIGO_PERSONAL) AS NOMBRE, CODIGO_PERSONAL FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CARGA_ID = '"+cargaId+"'"
					+ " AND TIPOCAL_ID IN ('I','1','2','4','5')"
					+ " AND CURSO_ID LIKE '%"+clave+"%'"
					+ " AND CODIGO_PERSONAL||SUBSTR(CURSO_ID,9,7) NOT IN "
					+ "		(SELECT CODIGO_PERSONAL||(SELECT CLAVE FROM ENOC.APFISICA_GRUPO WHERE GRUPO_ID = APFISICA_ALUMNO.GRUPO_ID) FROM ENOC.APFISICA_ALUMNO WHERE ESTADO = 'A')"
					+ " AND MODALIDAD_ID IN ("+modalidades+")" +orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				lisAcceso.add(rs.getString("NOMBRE")+"@@"+rs.getString("CODIGO_PERSONAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaAlumnoUtil|alumSinRegistro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAcceso;
	}
	
	public ArrayList<ApFisicaAlumno> getPorMatricula(Connection conn, String codigoId) throws SQLException{
		
//		ApFisicaAlumno regresaAlumno	= new ApFisicaAlumno();
		ArrayList<ApFisicaAlumno> lisAlumno 	= new ArrayList<ApFisicaAlumno>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	    = "";
		
		try{
			comando = "SELECT GRUPO_ID, CODIGO_PERSONAL, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA , ESTADO FROM ENOC.APFISICA_ALUMNO WHERE CODIGO_PERSONAL ='"+codigoId+"'";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				ApFisicaAlumno regresaAlumno	= new ApFisicaAlumno();
				regresaAlumno.mapeaReg(rs);
				lisAlumno.add(regresaAlumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaAlumnoUtil|getPorMatricula|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return lisAlumno;
	}
	
}