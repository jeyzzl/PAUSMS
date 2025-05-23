/**
 * 
 */
package aca.financiero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Elifo
 *
 */
public class FinPermisoUtil {
	
	
	public boolean insertReg(Connection conn, FinPermiso permiso ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.FIN_PERMISO" + 
					" (CODIGO_PERSONAL, FOLIO, F_INICIO, F_LIMITE, USUARIO, COMENTARIO)" +
					" VALUES(?, TO_NUMBER(?, '99'), TO_DATE(?, 'DD/MM/YYYY')," +
					" TO_DATE(?, 'DD/MM/YYYY'), ?, ?)");
			ps.setString(1, permiso.getCodigoPersonal());
			ps.setString(2, permiso.getFolio());
			ps.setString(3, permiso.getFInicio());
			ps.setString(4, permiso.getFLimite());
			ps.setString(5, permiso.getUsuario());
			ps.setString(6, permiso.getComentario());
			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPermiso|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, FinPermiso permiso ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.FIN_PERMISO" + 
					" SET F_INICIO = TO_DATE(?, 'DD/MM/YYYY')," +
					" F_LIMITE = TO_DATE(?, 'DD/MM/YYYY')," +
					" USUARIO = ?," +
					" COMENTARIO = ?" +
					" WHERE CODIGO_PERSONAL = ?" +
					" AND FOLIO = TO_NUMBER(?, 'DD/MM/YYYY')");
			
			ps.setString(1, permiso.getFInicio());
			ps.setString(2, permiso.getFLimite());
			ps.setString(3, permiso.getUsuario());
			ps.setString(4, permiso.getComentario());
			ps.setString(5, permiso.getCodigoPersonal());
			ps.setString(6, permiso.getFolio());			
			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPermiso|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String codigoPersonal, String folio ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.FIN_PERMISO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND FOLIO = TO_NUMBER(?, '99')");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPermiso|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public FinPermiso mapeaRegId(Connection con, String codigoPersonal, String folio) throws SQLException{
		
		FinPermiso permiso = new FinPermiso();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT CODIGO_PERSONAL, FOLIO," +
					" TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO," +
					" TO_CHAR(F_LIMITE, 'DD/MM/YYYY') AS F_LIMITE," +
					" USUARIO, COMENTARIO"+				
					" FROM ENOC.FIN_PERMISO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND FOLIO = TO_NUMBER(?, 'DD/MM/YYYY')");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);
			rs = ps.executeQuery();
			
			if(rs.next()){
				permiso.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPermiso|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return permiso;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal, String folio) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.FIN_PERMISO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND FOLIO = TO_NUMBER(?, 'DD/MM/YYYY')");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPermiso|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String getNuevoFolio(Connection conn, String codigoPersonal) throws SQLException{
		String folio 			= "";
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(FOLIO) AS FOLIO FROM ENOC.FIN_PERMISO" + 
					" WHERE CODIGO_PERSONAL = ?");
			
			ps.setString(1, codigoPersonal);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				folio = String.valueOf(rs.getInt("FOLIO")+1);
			}else{
				folio = "1";
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPermiso|getNuevoFolio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return folio;
	}
	
	public static boolean tienePermiso(Connection conn, String codigoPersonal) throws SQLException{
		boolean tienePermiso	= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.FIN_PERMISO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND now() BETWEEN F_INICIO AND F_LIMITE");
			
			ps.setString(1, codigoPersonal);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				tienePermiso = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPermiso|tienePermiso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return tienePermiso;
	}
	
	public static boolean tienePermisoCarga(Connection conn, String codigoPersonal, String cargaId) throws SQLException{
		boolean tienePermiso	= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.FIN_PERMISO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND F_INICIO BETWEEN (SELECT F_INICIO FROM ENOC.CARGA" + 
										  " WHERE CARGA_ID = ?)" +
								 " AND (SELECT F_EXTRA FROM ENOC.CARGA" + 
									  " WHERE CARGA_ID = ?)");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			ps.setString(3, cargaId);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				tienePermiso = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPermiso|tienePermisoCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return tienePermiso;
	}
	
	public static String getUsuarioePermisoCarga(Connection conn, String codigoPersonal, String cargaId) throws SQLException{
		String usuario			= "X";
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.FIN_PERMISO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND F_INICIO BETWEEN (SELECT F_INICIO FROM ENOC.CARGA" + 
										  " WHERE CARGA_ID = ?)" +
								 " AND (SELECT F_EXTRA FROM ENOC.CARGA" + 
									  " WHERE CARGA_ID = ?)");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			ps.setString(3, cargaId);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				usuario = rs.getString("USUARIO");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPermiso|getUsuarioePermisoCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return usuario;
	}
	
	public static String getAutorizacion(Connection conn, String codigoPersonal) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		aca.alumno.AlumUtil alumno = new aca.alumno.AlumUtil();
		double saldo = 0;
		String respuesta = "Alumno con Saldo Deudor!";
		
		try{
			saldo = alumno.getSaldo(conn, codigoPersonal);
			if (saldo >= 0){
				respuesta = "Autorizado..!(Cuenta Saldada)";
			}else{
				
				ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, USUARIO FROM ENOC.FIN_PERMISO" + 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND TO_DATE(now(),'DD-MM-YY') BETWEEN F_INICIO AND F_LIMITE");
				ps.setString(1, codigoPersonal);
		
				rs= ps.executeQuery();		
				if(rs.next()){
					respuesta= "Autorizado..! (Permiso otrogado por: "+rs.getString("USUARIO")+")";
				}				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPermiso|getNuevoFolio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
			alumno = null;
		}		
		
		return respuesta;
	}
	
	public ArrayList<FinPermiso> getListAlumno(Connection conn, String codigoPersonal, String orden ) throws SQLException{
		ArrayList<FinPermiso> lisAlumno	= new ArrayList<FinPermiso>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, FOLIO, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO," +
					" TO_CHAR(F_LIMITE, 'DD/MM/YYYY') AS F_LIMITE,"+
					" USUARIO, COMENTARIO FROM ENOC.FIN_PERMISO " + 
					" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				FinPermiso permiso = new FinPermiso();
				permiso.mapeaReg(rs);
				lisAlumno.add(permiso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPermisoUtil|getListAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumno;
	}

	public ArrayList<FinPermiso> getListAll(Connection conn, String orden ) throws SQLException{
		ArrayList<FinPermiso> lisAlumno	= new ArrayList<FinPermiso>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, FOLIO, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO," +
					" TO_CHAR(F_LIMITE, 'DD/MM/YYYY') AS F_LIMITE,"+
					" USUARIO, COMENTARIO FROM ENOC.FIN_PERMISO "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				FinPermiso permiso = new FinPermiso();
				permiso.mapeaReg(rs);
				lisAlumno.add(permiso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPermisoUtil|getListAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumno;
	}
	
	// Map de alumnos con permiso financiero
	public static HashMap<String,String> mapAlumnoPermiso(Connection conn, String condicion) throws SQLException{
		
		HashMap<String,String> map = new HashMap<String,String>();		
		Statement st 		= conn.createStatement();
		ResultSet rs 			= null;
		String comando = "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL FROM ENOC.FIN_PERMISO "+condicion+"";			
			rs= st.executeQuery(comando);		
			while(rs.next()){
				map.put(rs.getString("CODIGO_PERSONAL"), rs.getString("CODIGO_PERSONAL"));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPermisoUtil|mapAlumnoPermiso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
}