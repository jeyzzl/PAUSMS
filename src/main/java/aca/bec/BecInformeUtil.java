package aca.bec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class BecInformeUtil {
	
	public boolean insertReg(Connection conn, BecInforme becInforme) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.BEC_INFORME(INFORME_ID, INFORME_NOMBRE, FECHA_INI, FECHA_FIN, NIVEL, ORDEN, VERSION, ID_EJERCICIO, ESTADO)"+
				" VALUES(TO_NUMBER(?,'9999999'), ?, TO_DATE(?,'DD/MM/YYYY')," +
				" TO_DATE(?,'DD/MM/YYYY'), ?, TO_NUMBER(?,'99'),TO_NUMBER(?,'999'), ?, ?)");
					
			ps.setString(1, becInforme.getInformeId());
			ps.setString(2, becInforme.getInformeNombre());
			ps.setString(3, becInforme.getFechaIni());
			ps.setString(4, becInforme.getFechaFin());
			ps.setString(5, becInforme.getNivel());
			ps.setString(6, becInforme.getOrden());
			ps.setString(7, becInforme.getVersion());
			ps.setString(8, becInforme.getIdEjercicio());
			ps.setString(9, becInforme.getEstado());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, BecInforme becInforme) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BEC_INFORME"+ 
				" SET INFORME_NOMBRE = ?," +
				" FECHA_INI = TO_DATE(?,'DD/MM/YYYY')," +
				" FECHA_FIN = TO_DATE(?,'DD/MM/YYYY'),"+
				" NIVEL =  ?, " +
				" ORDEN = TO_NUMBER(?,'99'), "+
				" ID_EJERCICIO = ?, " +
				" ESTADO = ? " +
				" WHERE INFORME_ID = TO_NUMBER(?,'9999999')");
			
			ps.setString(1, becInforme.getInformeNombre());
			ps.setString(2, becInforme.getFechaIni());
			ps.setString(3, becInforme.getFechaFin());
			ps.setString(4, becInforme.getNivel());
			ps.setString(5, becInforme.getOrden());
			ps.setString(6, becInforme.getIdEjercicio());
			ps.setString(7, becInforme.getEstado());
			ps.setString(8, becInforme.getInformeId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
		
	public boolean deleteReg(Connection conn, String informeId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.BEC_INFORME"+ 
				" WHERE INFORME_ID = TO_NUMBER(?,'9999999')");
			
			ps.setString(1, informeId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeUtil|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public BecInforme mapeaRegId(Connection conn, String informeId) throws SQLException{
		BecInforme becInforme = new BecInforme();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT" +
					" INFORME_ID, INFORME_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI," +
					" TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, NIVEL, ORDEN, VERSION, ID_EJERCICIO, ESTADO" +
					" FROM ENOC.BEC_INFORME " +
					" WHERE INFORME_ID = TO_NUMBER(?,'9999999')");
			
			ps.setString(1, informeId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				becInforme.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return becInforme;
	}
	
	public boolean existeReg(Connection conn, String informeId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.BEC_INFORME WHERE INFORME_ID = TO_NUMBER(?,'9999999')"); 
			ps.setString(1, informeId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getNombreInforme(Connection conn, String informeId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String nombre			= "X";
		
		try{
			ps = conn.prepareStatement("SELECT INFORME_NOMBRE FROM ENOC.BEC_INFORME WHERE INFORME_ID = TO_NUMBER(?,'9999999')"); 
			ps.setString(1, informeId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("INFORME_NOMBRE");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeUtil|getNombreInforme|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(INFORME_ID)+1 MAXIMO FROM ENOC.BEC_INFORME");			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
				if(maximo == null){
					maximo = "1";
				}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;		
	}
	
	public static String getNivel(Connection conn, String informeId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String nivel 			= "0";
		
		try{
			ps = conn.prepareStatement("SELECT NIVEL FROM ENOC.BEC_INFORME WHERE INFORME_ID = TO_NUMBER(?,'9999999')");
			ps.setString(1, informeId);
			rs = ps.executeQuery();
			if (rs.next())
				nivel = rs.getString("NIVEL");
				
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeUtil|getNivel|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nivel;		
	}

	public ArrayList<BecInforme> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<BecInforme> lis 		= new ArrayList<BecInforme>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
				//comando = "SELECT * FROM ENOC.BEC_INFORME";
				comando = "SELECT INFORME_ID, INFORME_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') " +
						  "AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, NIVEL, " +
						  "ORDEN, ID_EJERCICIO, VERSION, ESTADO FROM ENOC.BEC_INFORME " + orden;
			
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecInforme obj= new BecInforme();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecInforme> getListActivos(Connection conn, String orden) throws SQLException{
		
		ArrayList<BecInforme> lis 		= new ArrayList<BecInforme>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT INFORME_ID, INFORME_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI," +
					" TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, NIVEL, ORDEN, ID_EJERCICIO, VERSION, ESTADO" +
					" FROM ENOC.BEC_INFORME WHERE now() BETWEEN FECHA_INI AND FECHA_FIN " + orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecInforme obj= new BecInforme();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeUtil|getListActivos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecInforme> getListActivos(Connection conn, String ejercicioId, String orden) throws SQLException{
		
		ArrayList<BecInforme> lis 		= new ArrayList<BecInforme>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = " SELECT INFORME_ID, INFORME_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI,"
					+ " TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, NIVEL, ORDEN, ID_EJERCICIO, VERSION, ESTADO"
					+ " FROM ENOC.BEC_INFORME"
					+ " WHERE ID_EJERCICIO = '"+ejercicioId+"'"
					+ " AND TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN " + orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecInforme obj= new BecInforme();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeUtil|getListActivos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecInforme> getListEjercicio(Connection conn, String ejercicioId, String orden) throws SQLException{
		
		ArrayList<BecInforme> lis 		= new ArrayList<BecInforme>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT INFORME_ID, INFORME_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI," +
					" TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, NIVEL, ORDEN, ID_EJERCICIO, VERSION, ESTADO" +
					" FROM ENOC.BEC_INFORME WHERE ID_EJERCICIO = '"+ejercicioId+"' " + orden;		
			rs = st.executeQuery(comando);
			while (rs.next()){				
				BecInforme obj= new BecInforme();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeUtil|getListActivos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecInforme> getListAnteriores(Connection conn, String ccostoId, String nivel, String orden) throws SQLException{
		
		ArrayList<BecInforme> lis 		= new ArrayList<BecInforme>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT INFORME_ID, INFORME_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI," +
					" TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, NIVEL, ORDEN, ID_EJERCICIO, VERSION, ESTADO" +
					" FROM ENOC.BEC_INFORME " +
					" WHERE INFORME_ID IN ("+
					"	SELECT DISTINCT(INFORME_ID) FROM ENOC.BEC_INFORME_ALUMNO"+
					"	WHERE CODIGO_PERSONAL||PUESTO_ID IN (" +
					"		SELECT CODIGO_PERSONAL||PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO" +
					" 		WHERE ID_CCOSTO = '"+ccostoId+"' " +
					"		AND TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN" +
					"	)" +					
					" AND NIVEL='"+nivel+"'"+
					" ) " + orden;		
			rs = st.executeQuery(comando);
			while (rs.next()){				
				BecInforme obj= new BecInforme();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeUtil|getListAnteriores|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecInforme> getListPuestoAlumno(Connection conn, String codigoPersonal, String puestoId, String orden) throws SQLException{
		
		ArrayList<BecInforme> lis 		= new ArrayList<BecInforme>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT INFORME_ID, INFORME_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI," +
					" TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, NIVEL, ORDEN, ID_EJERCICIO, VERSION, ESTADO" +
					" FROM ENOC.BEC_INFORME "+
					" WHERE INFORME_ID IN ("+
					" SELECT DISTINCT(INFORME_ID) FROM ENOC.BEC_INFORME_ALUMNO"+
					" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' " +
					" AND PUESTO_ID = '"+puestoId+"')" + orden;		
			rs = st.executeQuery(comando);
			while (rs.next()){				
				BecInforme obj= new BecInforme();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeUtil|getListPuestoAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public static HashMap<String, BecInforme> getMapInforme(Connection conn, String informeid) throws SQLException{
		HashMap<String, BecInforme> mapInforme = new HashMap<String, BecInforme>();
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;		 
		String comando	= "";
		
		try{			
			comando = "SELECT * FROM ENOC.BEC_INFORME WHERE INFORME_ID = '"+informeid+"'";
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				BecInforme informe = new BecInforme();
				informe.mapeaReg(rs);
				mapInforme.put(informe.getInformeId(), informe);
			}
			
		}catch(Exception ex){
			System.out.println("Error -  aca.bec.BecInformeUtil|getMapInforme|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return mapInforme;
	}
	
	public ArrayList<String> listMesesEnInforme(Connection conn, String ejercicioId, String fecha, String orden) throws SQLException{
		
		ArrayList<String> lis 		= new ArrayList<String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = " SELECT DISTINCT(ORDEN) AS MES FROM ENOC.BEC_INFORME"
					+ " WHERE INFORME_ID IN"
					+ "("
					+ "SELECT INFORME_ID FROM ENOC.BEC_INFORME_ALUMNO WHERE PUESTO_ID IN (SELECT PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO WHERE TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN AND ID_EJERCICIO = '"+ejercicioId+"')"
					+ ") " + orden;		
			rs = st.executeQuery(comando);
			while (rs.next()){				
				lis.add(rs.getString("MES"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeUtil|listMesesEnInforme|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecInforme> getInfomesConAlumnosContabilizados(Connection conn, String ejercicioId, String orden) throws SQLException{
		
		ArrayList<BecInforme> lis 		= new ArrayList<BecInforme>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
				comando = " SELECT INFORME_ID, INFORME_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') " +
						  " AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, NIVEL, " +
						  " ORDEN, ID_EJERCICIO, VERSION, ESTADO FROM ENOC.BEC_INFORME " +
						  " WHERE ID_EJERCICIO = '"+ejercicioId+"' "+
						  " AND (SELECT COUNT(*) FROM ENOC.BEC_INFORME_ALUMNO WHERE INFORME_ID = ENOC.BEC_INFORME.INFORME_ID AND ESTADO = '3') > 0 "+ orden;
						
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecInforme obj= new BecInforme();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecInforme> getInfomesConAlumnos(Connection conn, String ejercicioId, String estado, String orden) throws SQLException{
		
		ArrayList<BecInforme> lis 		= new ArrayList<BecInforme>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
				comando = " SELECT INFORME_ID, INFORME_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') " +
						  " AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, NIVEL, " +
						  " ORDEN, ID_EJERCICIO, VERSION, ESTADO FROM ENOC.BEC_INFORME " +
						  " WHERE ID_EJERCICIO = '"+ejercicioId+"' "+
						  " AND (SELECT COUNT(*) FROM ENOC.BEC_INFORME_ALUMNO WHERE INFORME_ID = ENOC.BEC_INFORME.INFORME_ID AND ESTADO = '"+estado+"') > 0 "+ orden;
						
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecInforme obj= new BecInforme();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
}
