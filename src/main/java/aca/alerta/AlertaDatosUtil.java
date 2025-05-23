/**
 * 
 */
package aca.alerta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class AlertaDatosUtil {
	
	public boolean insertReg(Connection conn, AlertaDatos alertaDatos) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ALERTA_DATOS"+ 
				"(PERIODO_ID,  CODIGO_PERSONAL, FECHA, DIRECCION, "+
				"PROCEDENCIA, CORREO, CELULAR, SINTOMAS, "+
				"USUARIO, LUGAR1, LUGAR2, ESTADO, OTRO, REFERENTE) "+
				"VALUES(?, ?, TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
				
			ps.setString(1, alertaDatos.getPeriodoId());
			ps.setString(2, alertaDatos.getCodigoPersonal());
			ps.setString(3, alertaDatos.getFecha());
			ps.setString(4, alertaDatos.getDireccion());
			ps.setString(5, alertaDatos.getProcedencia());
			ps.setString(6, alertaDatos.getCorreo());
			ps.setString(7, alertaDatos.getCelular());
			ps.setString(8, alertaDatos.getSintomas());
			ps.setString(9, alertaDatos.getUsuario());
			ps.setString(10, alertaDatos.getLugar1());
			ps.setString(11, alertaDatos.getLugar2());
			ps.setString(12, alertaDatos.getEstado());
			ps.setString(13, alertaDatos.getOtro());
			ps.setString(14, alertaDatos.getReferente());

			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaDatosUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	} 
	public boolean updateReg(Connection conn, AlertaDatos alertaDatos ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALERTA_DATOS "+ 
				"SET "+
				"FECHA = TO_DATE(?, 'DD/MM/YYYY'), "+
				"DIRECCION = ?, "+
				"PROCEDENCIA = ?, "+
				"CORREO = ?, "+
				"CELULAR = ?, "+
				"SINTOMAS = ?, "+
				"USUARIO = ?, "+
				"LUGAR1 = ?, "+
				"LUGAR2 = ?, "+
				"ESTADO = ?, "+
				"OTRO = ?, "+
				"REFERENTE = ? "+
				"WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ? ");
			
			ps.setString(1, alertaDatos.getFecha());
			ps.setString(2, alertaDatos.getDireccion());
			ps.setString(3, alertaDatos.getProcedencia());
			ps.setString(4, alertaDatos.getCorreo());
			ps.setString(5, alertaDatos.getCelular());
			ps.setString(6, alertaDatos.getSintomas());
			ps.setString(7, alertaDatos.getUsuario());
			ps.setString(8, alertaDatos.getLugar1());
			ps.setString(9, alertaDatos.getLugar2());
			ps.setString(10, alertaDatos.getEstado());
			ps.setString(11, alertaDatos.getOtro());
			ps.setString(12, alertaDatos.getReferente());
			ps.setString(13, alertaDatos.getPeriodoId());
			ps.setString(14, alertaDatos.getCodigoPersonal());

			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaDatosUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String periodoId, String codigoPersonal ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALERTA_DATOS "+ 
				"WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ? ");
			ps.setString(1, periodoId);
			ps.setString(2, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaDatosUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public AlertaDatos mapeaRegId( Connection conn, String periodoId, String codigoPersonal ) throws SQLException{
		
		AlertaDatos alerta = new AlertaDatos();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"PERIODO_ID, FOLIO, CODIGO_PERSONAL, TO_DATE(FECHA, 'DD/MM/YYYY') AS FECHA, DIRECCION, "+
				"PROCEDENCIA, CORREO, CELULAR, SINTOMAS, "+
				"USUARIO, LUGAR1, LUGAR2, ESTADO, OTRO, REFERENTE "+
				"FROM ENOC.ALERTA_DATOS WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ? ");
			
			ps.setString(1, periodoId);
			ps.setString(2, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				alerta.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaDatosUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return alerta;
	}
	
	public boolean existeReg(Connection conn, String periodoId, String codigoPersonal) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALERTA_DATOS "+ 
				"WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ?");
			ps.setString(1, periodoId);
			ps.setString(2, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaDatosUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}

	public static boolean existeAlumno(Connection conn, String periodoId, String codigoPersonal) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALERTA_DATOS "+ 
				"WHERE PERIODO_ID = '"+periodoId+"' AND CODIGO_PERSONAL = '"+codigoPersonal+"' ");
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaDatosUtil|existeAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean alertaActivaEnModalidadActual(Connection conn, String modalidadId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement(" SELECT * FROM ENOC.ALERTA_PERIODO WHERE MODALIDADES LIKE '%-"+modalidadId+"-%' AND ESTADO = 'A' AND now() BETWEEN FECHA_INI AND FECHA_FIN " );
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaDatosUtil|tieneDatos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean autorizado(Connection conn, String codigoPersonal, String periodoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement(" SELECT CODIGO_PERSONAL FROM ENOC.ALERTA_DATOS WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND PERIODO_ID ='"+periodoId+"' "
					+ " AND ESTADO = 'A'  " );
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaDatosUtil|tieneDatos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public ArrayList<AlertaDatos> getAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<AlertaDatos> lisPre	= new ArrayList<AlertaDatos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT PERIODO_ID, FOLIO, CODIGO_PERSONAL, FECHA, DIRECCION, "+
					  " PROCEDENCIA, CORREO, CELULAR, SINTOMAS, "+
					  " USUARIO, LUGAR1, LUGAR2, ESTADO, OTRO, REFERENTE FROM ENOC.ALERTA_DATOS "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlertaDatos alumPre = new AlertaDatos();
				alumPre.mapeaReg(rs);
				lisPre.add(alumPre);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaDatosUtil|getAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPre;
	}
	
	public static HashMap<String, String> getMapEdadAlerta(Connection conn, String periodo) throws SQLException{
		
		HashMap<String, String> mapa		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, FLOOR(MONTHS_BETWEEN( TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') ,F_NACIMIENTO)/12) AS EDAD " +
					" FROM ENOC.ALUM_PERSONAL" +
					" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALERTA_DATOS WHERE PERIODO_ID = '"+periodo+"')";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				mapa.put(rs.getString("CODIGO_PERSONAL"), rs.getString("EDAD"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaDatosUtil|getMapEdadAlerta|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}
	
	public static HashMap<String, String> getMapResidenciaAlerta(Connection conn, String periodo) throws SQLException{
		
		HashMap<String, String> mapa		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, RESIDENCIA_ID " +
					" FROM ENOC.ALUM_ACADEMICO" +
					" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALERTA_DATOS WHERE PERIODO_ID = '"+periodo+"' )";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				mapa.put(rs.getString("CODIGO_PERSONAL"), rs.getString("RESIDENCIA_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaDatosUtil|getMapResidenciaAlerta|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}
	
	public static HashMap<String, String> mapDormitorioAlerta(Connection conn, String periodo) throws SQLException{
		
		HashMap<String, String> mapa		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, DORMITORIO "
					+ " FROM ENOC.ALUM_ACADEMICO"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALERTA_DATOS WHERE PERIODO_ID = '"+periodo+"')";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				mapa.put(rs.getString("CODIGO_PERSONAL"), rs.getString("DORMITORIO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaDatosUtil|mapDormitorioAlerta|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}
	
	public static HashMap<String, String> mapCicloAlerta(Connection conn, String periodo) throws SQLException{
		
		HashMap<String, String> mapa		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, CICLO "
					+ " FROM ENOC.ALUM_PLAN"
					+ " WHERE ESTADO = '1'"
					+ " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALERTA_DATOS WHERE PERIODO_ID = '"+periodo+"')";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				mapa.put(rs.getString("CODIGO_PERSONAL"), rs.getString("CICLO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaDatosUtil|mapCicloAlerta|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}
	
	public static HashMap<String, String> mapCarreraActual(Connection conn, String periodo) throws SQLException{
		
		HashMap<String, String> mapa		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, ENOC.CARRERA(PLAN_ID) AS CARRERA"
					+ " FROM ENOC.ALUM_PLAN"
					+ " WHERE ESTADO = '1'"
					+ " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALERTA_DATOS WHERE PERIODO_ID = '"+periodo+"')";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				mapa.put(rs.getString("CODIGO_PERSONAL"), rs.getString("CARRERA"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaDatosUtil|mapCicloAlerta|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}

}