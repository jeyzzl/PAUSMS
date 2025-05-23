package aca.bec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class BecAcuerdoUtil {
	
	public boolean insertReg(Connection conn, BecAcuerdo becAcuerdo) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.BEC_ACUERDO"+ 
				"(FOLIO, CODIGO_PERSONAL, ID_EJERCICIO, TIPO, FECHA, MATRICULA, ENSENANZA, INTERNADO, VALOR, VIGENCIA, ESTADO, PROMESA, ID_CCOSTO, PUESTO_ID, HORAS, TIPOADICIONAL, USUARIO )"+
				" VALUES( TO_NUMBER(?, '999'), ?, ?, TO_NUMBER(?, '99'), TO_DATE(?, 'DD/MM/YYYY'), TO_NUMBER(?,'999999.99'), " +
				" TO_NUMBER(?,'999999.99'), TO_NUMBER(?,'999999.99'), ?, TO_DATE(?, 'DD/MM/YYYY'), ?, TO_NUMBER(?,'99999999.99'), ?, ?, TO_NUMBER(?, '9999'), ?, ? )");

			ps.setString(1,  becAcuerdo.getFolio());
			ps.setString(2,  becAcuerdo.getCodigoPersonal());
			ps.setString(3,  becAcuerdo.getIdEjercicio());
			ps.setString(4,  becAcuerdo.getTipo());
			ps.setString(5,  becAcuerdo.getFecha());
			ps.setString(6,  becAcuerdo.getMatricula());
			ps.setString(7,  becAcuerdo.getEnsenanza());
			ps.setString(8,  becAcuerdo.getInternado());
			ps.setString(9,  becAcuerdo.getValor());
			ps.setString(10, becAcuerdo.getVigencia());
			ps.setString(11, becAcuerdo.getEstado());
			ps.setString(12, becAcuerdo.getPromesa());
			ps.setString(13, becAcuerdo.getIdCcosto());
			ps.setString(14, becAcuerdo.getPuestoId());
			ps.setString(15, becAcuerdo.getHoras());
			ps.setString(16, becAcuerdo.getTipoadicional());
			ps.setString(17, becAcuerdo.getUsuario());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, BecAcuerdo becAcuerdo) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BEC_ACUERDO"+ 
				" SET ID_EJERCICIO = ?, " +
				" TIPO = TO_NUMBER(?, '99'), FECHA = TO_DATE(?, 'DD/MM/YYYY'), MATRICULA = TO_NUMBER(?,'999999.99'), ENSENANZA = TO_NUMBER(?,'999999.99')," +
				" INTERNADO = TO_NUMBER(?,'999999.99'), VALOR = ?, VIGENCIA = TO_DATE(?, 'DD/MM/YYYY'), ESTADO = ?, PROMESA = TO_NUMBER(?,'99999999.99')," +
				" ID_CCOSTO = ?, PUESTO_ID = ?, HORAS = TO_NUMBER(?, '9999'), TIPOADICIONAL = ?, USUARIO = ? " +
				" WHERE FOLIO = TO_NUMBER(?, '999') AND CODIGO_PERSONAL = ? ");
			
			ps.setString(1,  becAcuerdo.getIdEjercicio());
			ps.setString(2,  becAcuerdo.getTipo());
			ps.setString(3,  becAcuerdo.getFecha());
			ps.setString(4,  becAcuerdo.getMatricula());
			ps.setString(5,  becAcuerdo.getEnsenanza());
			ps.setString(6,  becAcuerdo.getInternado());
			ps.setString(7,  becAcuerdo.getValor());
			ps.setString(8,  becAcuerdo.getVigencia());
			ps.setString(9,  becAcuerdo.getEstado());
			ps.setString(10, becAcuerdo.getPromesa());
			ps.setString(11, becAcuerdo.getIdCcosto());
			ps.setString(12, becAcuerdo.getPuestoId());
			ps.setString(13, becAcuerdo.getHoras());
			ps.setString(14, becAcuerdo.getTipoadicional());
			ps.setString(15, becAcuerdo.getUsuario());
			ps.setString(16, becAcuerdo.getFolio());
			ps.setString(17, becAcuerdo.getCodigoPersonal());
			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateHoras(Connection conn, String horas, String folio, String codigoPersonal) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BEC_ACUERDO"+ 
				" SET  HORAS = ? " +
				" WHERE FOLIO = TO_NUMBER(?, '999') AND CODIGO_PERSONAL = ? ");
			
			ps.setString(1, horas);
			ps.setString(2, folio);
			ps.setString(3, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|updateHoras|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean updateHorasDelPuesto(Connection conn, String codigoPersonal, String puestoId, String horas) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BEC_ACUERDO"+ 
				" SET HORAS = '"+horas+"' " +
				" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND PUESTO_ID = '"+puestoId+"'");
						
			if (ps.executeUpdate() >= 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|updateHorasDelPuesto|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean updateEstadoDelAcuerdo(Connection conn, String codigoPersonal, String puestoId, String estado) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BEC_ACUERDO"+ 
				" SET ESTADO = '"+estado+"' " +
				" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND PUESTO_ID = '"+puestoId+"'");
						
			if (ps.executeUpdate() >= 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|updateEstadoDelAcuerdo|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updatePuestoId(Connection conn, String puestoId, String folio, String codigoPersonal) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BEC_ACUERDO"+ 
				" SET  PUESTO_ID = ? " +
				" WHERE FOLIO = TO_NUMBER(?, '999') AND CODIGO_PERSONAL = ? ");
			
			ps.setString(1, puestoId);
			ps.setString(2, folio);
			ps.setString(3, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|updatePuestoId|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean updateCcosto(Connection conn, String idCosto, String folio, String codigoPersonal) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BEC_ACUERDO"+ 
				" SET  ID_CCOSTO = ? " +
				" WHERE FOLIO = TO_NUMBER(?, '999') AND CODIGO_PERSONAL = ? ");
			
			ps.setString(1, idCosto);
			ps.setString(2, folio);
			ps.setString(3, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|updateCcosto|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String folio, String codigoPersonal) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.BEC_ACUERDO"+ 
				" WHERE FOLIO = TO_NUMBER(?, '999') AND CODIGO_PERSONAL = ? ");
			
			ps.setString(1,  folio);
			ps.setString(2,  codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public BecAcuerdo mapeaRegId(Connection conn, String folio, String codigoPersonal) throws SQLException{
		BecAcuerdo becAcuerdo = new BecAcuerdo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT FOLIO, CODIGO_PERSONAL, ID_EJERCICIO, TIPO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ENSENANZA, INTERNADO, VALOR, " +
					" TO_CHAR(VIGENCIA, 'DD/MM/YYYY') AS VIGENCIA, ESTADO, PROMESA, ID_CCOSTO, PUESTO_ID, HORAS, TIPOADICIONAL, USUARIO " +
					" FROM ENOC.BEC_ACUERDO WHERE FOLIO = TO_NUMBER(?, '999') AND CODIGO_PERSONAL = ? "); 
			
			ps.setString(1,  folio);
			ps.setString(2,  codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){
				becAcuerdo.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return becAcuerdo;
	}
	
	public BecAcuerdo mapeaRegPuestoId(Connection conn, String folio, String codigoPersonal, String puestoId) throws SQLException{
		BecAcuerdo becAcuerdo = new BecAcuerdo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT FOLIO, CODIGO_PERSONAL, ID_EJERCICIO, TIPO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ENSENANZA, INTERNADO, VALOR, " +
					" TO_CHAR(VIGENCIA, 'DD/MM/YYYY') AS VIGENCIA, ESTADO, PROMESA, ID_CCOSTO, PUESTO_ID, HORAS, TIPOADICIONAL, USUARIO " +
					" FROM ENOC.BEC_ACUERDO"
					+ " WHERE FOLIO = TO_NUMBER(?, '999')"
					+ " AND CODIGO_PERSONAL = ?"
					+ " AND PUESTO_ID = ?"); 
			
			ps.setString(1,  folio);
			ps.setString(2,  codigoPersonal);
			ps.setString(3,  puestoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				becAcuerdo.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return becAcuerdo;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal, String folio) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(TIPO,0) AS TIPO FROM ENOC.BEC_ACUERDO WHERE CODIGO_PERSONAL = ? AND FOLIO = ? "); 
			ps.setString(1,  codigoPersonal);
			ps.setString(2,  folio);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeTipo(Connection conn, String codigoPersonal, String idEjercicio, String tipo, String idCcosto, String puestoId) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(TIPO,0) AS TIPO FROM ENOC.BEC_ACUERDO WHERE CODIGO_PERSONAL = ? AND ID_EJERCICIO = ? AND TIPO = ? AND ID_CCOSTO = ? AND PUESTO_ID = ? "); 
			ps.setString(1,  codigoPersonal);
			ps.setString(2,  idEjercicio);
			ps.setString(3,  tipo);
			ps.setString(4,  idCcosto);
			ps.setString(5,  puestoId);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|existeTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public String maximoReg(Connection conn, String codigoPersonal) throws SQLException{
 		String 		maximo 		= "1";
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.BEC_ACUERDO WHERE CODIGO_PERSONAL = ?"); 			
 			ps.setString(1, codigoPersonal); 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				maximo = rs.getString("MAXIMO");
 			if(maximo == null)
 				maximo = "1";
 		}catch(Exception ex){
 			System.out.println("Error - aca.bec.BecAcuerdoUtil|maximoReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return maximo;
 	}
	
	public String getFolio(Connection conn, String codigoPersonal, String idEjercicio, String tipo, String idCcosto, String puestoId) throws SQLException{
		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String folio			= "0";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(FOLIO,0) AS FOLIO FROM ENOC.BEC_ACUERDO WHERE CODIGO_PERSONAL = ? AND ID_EJERCICIO = ? AND TIPO = TO_NUMBER(?,'99') AND ID_CCOSTO = ? AND PUESTO_ID = ? "); 
			ps.setString(1,  codigoPersonal);
			ps.setString(2,  idEjercicio);
			ps.setString(3,  tipo);
			ps.setString(4,  idCcosto);
			ps.setString(5,  puestoId);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				folio = rs.getString("FOLIO");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getFolio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return folio;
	}
 	
	public String getBecaBasicaUsado(Connection conn, String idEjercicio, String tipo, String tipoAdicional, String idCcosto, String fechaPuesto, String periodoId ) throws SQLException{
		String usado			= "0";
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(SUM(ENSENANZA*HORAS), 0) AS USADO"
					+ " FROM ENOC.BEC_ACUERDO A"
					+ " WHERE TIPO = "+tipo+" AND ID_EJERCICIO = '"+idEjercicio+"' AND ID_CCOSTO = '"+idCcosto+"'"
					+ " AND (SELECT COUNT(*) FROM ENOC.BEC_ACUERDO WHERE PUESTO_ID = A.PUESTO_ID AND TIPO NOT IN("+tipo+","+tipoAdicional+") ) = 0"
					+ " AND PUESTO_ID IN ( SELECT PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO"
					+ " 	WHERE ID_EJERCICIO = '"+idEjercicio+"' "
					+ "		AND TO_DATE('"+fechaPuesto+"', 'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN AND ID_CCOSTO = '"+idCcosto+"' AND TIPO = 'B' AND PERIODO_ID = '"+periodoId+"')");
			
			rs= ps.executeQuery();		
			if(rs.next()){
				usado = rs.getString("USADO");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getBecaBasicaUsado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return usado;
	}
	
	public String getBecaInstitucionalUsado(Connection conn, String idEjercicio, String tipo, String idCcosto, String fechaPuesto, String periodoId ) throws SQLException{
		String usado			= "0";
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(SUM(ENSENANZA*HORAS), 0) AS USADO"
					+ " FROM ENOC.BEC_ACUERDO A"
					+ " WHERE TIPO = "+tipo+" AND ID_EJERCICIO = '"+idEjercicio+"' AND ID_CCOSTO = '"+idCcosto+"'"					
					+ " AND PUESTO_ID IN ( SELECT PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO"
					+ " 	WHERE ID_EJERCICIO = '"+idEjercicio+"' "
					+ "		AND TO_DATE('"+fechaPuesto+"', 'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN AND ID_CCOSTO = '"+idCcosto+"' AND TIPO = 'I' AND PERIODO_ID = '"+periodoId+"')");
			
			rs= ps.executeQuery();		
			if(rs.next()){
				usado = rs.getString("USADO");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getBecaBasicaUsado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return usado;
	}
	
	public String getBecaAdicionalUsado(Connection conn, String idEjercicio, String tipo, String idCcosto, String fechaPuesto, String periodoId ) throws SQLException{
		String usado			= "0";
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement(" SELECT " +
					" COALESCE( SUM( " +
									" CASE" +
									" WHEN TIPOADICIONAL = 'D' THEN ENSENANZA" +
									" WHEN TIPOADICIONAL != 'D' THEN ((ENSENANZA/100)*PROMESA)" +
									" END " +
					" ), 0) AS USADO" +
					" FROM ENOC.BEC_ACUERDO WHERE TIPO = "+tipo+" AND ID_EJERCICIO = '"+idEjercicio+"' AND ID_CCOSTO = '"+idCcosto+"' " +
					" AND PUESTO_ID IN ( SELECT PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = '"+idEjercicio+"' AND TO_DATE('"+fechaPuesto+"', 'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN AND ID_CCOSTO = '"+idCcosto+"' AND PERIODO_ID = '"+periodoId+"' ) ");
			
			rs= ps.executeQuery();		
			if(rs.next()){
				usado = rs.getString("USADO");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getBecaBasicaUsado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return usado;
	}
	
	public static boolean existeAcuerdoMateo(Connection conn, String codigoPersonal, String folio ) throws SQLException{
		boolean ok 				= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM MATEO.FES_CC_AFE_ACUERDOS"
					+ " WHERE MATRICULA = ?"
					+ " AND ACUERDO_FOLIO = TO_NUMBER(?,'999')"); 
			ps.setString(1,  codigoPersonal);			
			ps.setString(2,  folio);
			
			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|existeAcuerdoMateo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean existeAcuerdoMateo(Connection conn, String codigoPersonal, String folio, String puestoId ) throws SQLException{
		boolean ok 				= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM MATEO.FES_CC_AFE_ACUERDOS"
					+ " WHERE MATRICULA = ?"
					+ " AND ACUERDO_FOLIO = ?"
					+ " AND ALPUESTO_PUESTO_ID = ?"); 
			ps.setString(1,  codigoPersonal);			
			ps.setString(2,  folio);
			ps.setString(3,  puestoId);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|existeAcuerdoMateo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static int getNumAcuerdosAsociados(Connection conn, String idEjercicio, String codigoPersonal, String puestoId, String tipos) throws SQLException{		
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		int total = 0;		
		try{
			comando = "SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS TOTAL FROM ENOC.BEC_ACUERDO" +
					" WHERE ID_EJERCICIO = '"+idEjercicio+"'" +
					" AND CODIGO_PERSONAL = '"+codigoPersonal+"'" +
					" AND PUESTO_ID = '"+puestoId+"'"+
					" AND TIPO NOT IN("+tipos+") " +
					" AND ESTADO IN('A','I') ";			
			rs = st.executeQuery(comando);
			while (rs.next()){			
				total = rs.getInt("TOTAL");		
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAccesoUtil|getNumAcuerdosAsociados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return total;
	}
	
	public static String getAcuerdoBasico(Connection conn, String ejercicioId, String codigo, String puesto) throws SQLException{
		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando 			= "";
		String tipo				= "0";			
		try{
			comando = "SELECT TIPO FROM ENOC.BEC_ACUERDO " +
					" WHERE CODIGO_PERSONAL = '"+codigo+"'" +
					" AND PUESTO_ID = '"+puesto+"'" +
					" AND TIPO IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = '"+ejercicioId+"' AND ACUERDO IN ('B','I'))";
			rs = st.executeQuery(comando);
			while (rs.next()){
				tipo = rs.getString("TIPO");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getAcuerdoBasico|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return tipo;
	}
	
	public static String getFolioAcuerdoBasico(Connection conn, String ejercicioId, String codigo, String puesto) throws SQLException{
		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando 			= "";
		String folio			= "0";			
		try{
			comando = "SELECT FOLIO FROM ENOC.BEC_ACUERDO " +
					" WHERE CODIGO_PERSONAL = '"+codigo+"'" +
					" AND PUESTO_ID = '"+puesto+"'" +
					" AND TIPO IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = '"+ejercicioId+"' AND ACUERDO IN ('B','I'))";
			rs = st.executeQuery(comando);
			while (rs.next()){
				folio = rs.getString("FOLIO");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getFolioAcuerdoBasico|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return folio;
	}
	
	public static String getPromesaAlumno(Connection conn, String codigo, String puesto) throws SQLException{
		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando 			= "";
		String tipo				= "0";			
		try{
			comando = "SELECT COALESCE(SUM(PROMESA), 0) AS PROMESA FROM ENOC.BEC_ACUERDO WHERE CODIGO_PERSONAL = '"+codigo+"' AND PUESTO_ID = '"+puesto+"'";
			rs = st.executeQuery(comando);
			while (rs.next()){
				tipo = rs.getString("PROMESA");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getPromesaAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return tipo;
	}
	
	public static String getPorMatricula(Connection conn, String codigo, String puesto) throws SQLException{
		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando 			= "";
		String tipo				= "0";			
		try{
			comando = "SELECT COALESCE(SUM(MATRICULA), 0) AS TOTAL FROM ENOC.BEC_ACUERDO WHERE CODIGO_PERSONAL = '"+codigo+"' AND PUESTO_ID = '"+puesto+"'";
			rs = st.executeQuery(comando);
			while (rs.next()){
				tipo = rs.getString("TOTAL");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getPorMatricula|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return tipo;
	}
	
	
	public ArrayList<BecAcuerdo> getListAll(Connection conn, String orden) throws SQLException{
			
		ArrayList<BecAcuerdo> lis 		= new ArrayList<BecAcuerdo>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT * FROM ENOC.BEC_ACUERDO "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecAcuerdo obj= new BecAcuerdo();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecAcuerdo> getList(Connection conn, String idEjercicio, String fecha, String estados, String orden) throws SQLException{
		
		ArrayList<BecAcuerdo> lis 		= new ArrayList<BecAcuerdo>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		try{
			String comando = "SELECT FOLIO, CODIGO_PERSONAL, ID_EJERCICIO, TIPO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ENSENANZA, INTERNADO, VALOR, " +
					" TO_CHAR(VIGENCIA, 'DD/MM/YYYY') AS VIGENCIA, ESTADO, PROMESA, ID_CCOSTO, COALESCE(PUESTO_ID,'-') AS PUESTO_ID, HORAS, TIPOADICIONAL, USUARIO " +
					" FROM ENOC.BEC_ACUERDO " +
					" WHERE ID_EJERCICIO = '"+idEjercicio+"' " +
					" AND ESTADO IN("+estados+") "+
					" AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.BEC_PUESTO_ALUMNO WHERE TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN)" +orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				BecAcuerdo obj= new BecAcuerdo();
				obj.mapeaReg(rs);
				lis.add(obj);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecAcuerdo> getListPuestoId(Connection conn, String idEjercicio, String puestoId, String orden) throws SQLException{
		
		ArrayList<BecAcuerdo> lis 		= new ArrayList<BecAcuerdo>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		try{
			String comando = "SELECT * FROM ENOC.BEC_ACUERDO " +
					" WHERE PUESTO_ID = '"+puestoId+"' " +
					" AND ID_EJERCICIO = '"+idEjercicio+"' "+orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				BecAcuerdo obj= new BecAcuerdo();
				obj.mapeaReg(rs);
				lis.add(obj);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getListPuestoId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return lis;
	}
	
	public ArrayList<BecAcuerdo> getListTipo(Connection conn, String idEjercicio, String fecha, String tipo, String estado, String orden) throws SQLException{
		
		ArrayList<BecAcuerdo> lis 		= new ArrayList<BecAcuerdo>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT FOLIO, CODIGO_PERSONAL, ID_EJERCICIO, TIPO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ENSENANZA, INTERNADO, VALOR, " +
					" TO_CHAR(VIGENCIA, 'DD/MM/YYYY') AS VIGENCIA, ESTADO, PROMESA, ID_CCOSTO, PUESTO_ID, HORAS, TIPOADICIONAL, USUARIO " +
					" FROM ENOC.BEC_ACUERDO " +
					" WHERE ID_EJERCICIO = '"+idEjercicio+"' " +
					" AND ESTADO = '"+estado+"' "+
					" AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.BEC_PUESTO_ALUMNO WHERE TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN)" +
					" AND TIPO = '"+tipo+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecAcuerdo obj= new BecAcuerdo();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getListTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecAcuerdo> getListTipo(Connection conn, String idEjercicio, String tipo, String orden) throws SQLException{
		
		ArrayList<BecAcuerdo> lis 		= new ArrayList<BecAcuerdo>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT FOLIO, CODIGO_PERSONAL, ID_EJERCICIO, TIPO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ENSENANZA, INTERNADO, VALOR, " +
					" TO_CHAR(VIGENCIA, 'DD/MM/YYYY') AS VIGENCIA, ESTADO, PROMESA, ID_CCOSTO, PUESTO_ID, HORAS, TIPOADICIONAL, USUARIO " +
					" FROM ENOC.BEC_ACUERDO " +
					" WHERE ID_EJERCICIO = '"+idEjercicio+"' " +					
					" AND TIPO = '"+tipo+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecAcuerdo obj= new BecAcuerdo();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getListTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecAcuerdo> getListTipoyEstado(Connection conn, String idEjercicio, String tipo, String estado, String orden) throws SQLException{
		
		ArrayList<BecAcuerdo> lis 		= new ArrayList<BecAcuerdo>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT FOLIO, CODIGO_PERSONAL, ID_EJERCICIO, TIPO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ENSENANZA, INTERNADO, VALOR, " +
					" TO_CHAR(VIGENCIA, 'DD/MM/YYYY') AS VIGENCIA, ESTADO, PROMESA, ID_CCOSTO, PUESTO_ID, HORAS, TIPOADICIONAL, USUARIO " +
					" FROM ENOC.BEC_ACUERDO " +
					" WHERE ID_EJERCICIO = '"+idEjercicio+"' "+			
					" AND TIPO = '"+tipo+"' " +
					" AND ESTADO = '"+estado+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecAcuerdo obj= new BecAcuerdo();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getListTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecAcuerdo> getAcuerdosAlumno(Connection conn, String idEjercicio, String codigoPersonal, String tipos, String orden) throws SQLException{
		
		ArrayList<BecAcuerdo> lis 		= new ArrayList<BecAcuerdo>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			
			comando = "SELECT FOLIO, CODIGO_PERSONAL, ID_EJERCICIO, TIPO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ENSENANZA, INTERNADO, VALOR, " +
					" TO_CHAR(VIGENCIA, 'DD/MM/YYYY') AS VIGENCIA, ESTADO, PROMESA, ID_CCOSTO, PUESTO_ID, HORAS, TIPOADICIONAL, USUARIO FROM ENOC.BEC_ACUERDO " +
					" WHERE ID_EJERCICIO = '"+idEjercicio+"' " +
					" AND CODIGO_PERSONAL = '"+codigoPersonal+"' " +
					" AND TIPO NOT IN("+tipos+") " +
					" AND ESTADO IN ('A','I') "+orden;	
			//System.out.println(comando);
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecAcuerdo obj= new BecAcuerdo();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getAcuerdosAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecAcuerdo> getAcuerdosVigentesAlumno(Connection conn, String idEjercicio, String codigoPersonal, String tipos, String orden) throws SQLException{
		
		ArrayList<BecAcuerdo> lis 		= new ArrayList<BecAcuerdo>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT FOLIO, CODIGO_PERSONAL, ID_EJERCICIO, TIPO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ENSENANZA, INTERNADO, VALOR, " +
					" TO_CHAR(VIGENCIA, 'DD/MM/YYYY') AS VIGENCIA, ESTADO, PROMESA, ID_CCOSTO, PUESTO_ID, HORAS, TIPOADICIONAL, USUARIO FROM ENOC.BEC_ACUERDO " +
					" WHERE ID_EJERCICIO = '"+idEjercicio+"' " +
					" AND CODIGO_PERSONAL = '"+codigoPersonal+"' " +
					" AND now() <= VIGENCIA" +
					" AND TIPO NOT IN("+tipos+") " +
					" AND ESTADO IN ('A','I') "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecAcuerdo obj= new BecAcuerdo();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getAcuerdosVigentesAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecAcuerdo> getAcuerdosAlumno(Connection conn, String idEjercicio, String codigoPersonal, String orden) throws SQLException{
		
		ArrayList<BecAcuerdo> lis 		= new ArrayList<BecAcuerdo>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT FOLIO, CODIGO_PERSONAL, ID_EJERCICIO, TIPO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ENSENANZA, INTERNADO, VALOR, " +
					" TO_CHAR(VIGENCIA, 'DD/MM/YYYY') AS VIGENCIA, ESTADO, PROMESA, ID_CCOSTO, PUESTO_ID, HORAS, TIPOADICIONAL, USUARIO FROM ENOC.BEC_ACUERDO " +
					" WHERE ID_EJERCICIO = '"+idEjercicio+"' AND CODIGO_PERSONAL = '"+codigoPersonal+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecAcuerdo obj= new BecAcuerdo();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getAcuerdosAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecAcuerdo> getAcuerdosAlumnoVigentes(Connection conn, String idEjercicio, String codigoPersonal, String orden) throws SQLException{
		
		ArrayList<BecAcuerdo> lis 		= new ArrayList<BecAcuerdo>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT FOLIO, CODIGO_PERSONAL, ID_EJERCICIO, TIPO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ENSENANZA, INTERNADO, VALOR, " +
					" TO_CHAR(VIGENCIA, 'DD/MM/YYYY') AS VIGENCIA, ESTADO, PROMESA, ID_CCOSTO, PUESTO_ID, HORAS, TIPOADICIONAL, USUARIO FROM ENOC.BEC_ACUERDO " +
					" WHERE ID_EJERCICIO = '"+idEjercicio+"' AND CODIGO_PERSONAL = '"+codigoPersonal+"' " +
					" AND (SELECT FECHA_INI FROM ENOC.BEC_PUESTO_ALUMNO WHERE PUESTO_ID = BEC_ACUERDO.PUESTO_ID) <= now()" +
					" AND (SELECT FECHA_FIN FROM ENOC.BEC_PUESTO_ALUMNO WHERE PUESTO_ID = BEC_ACUERDO.PUESTO_ID) >= now() "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecAcuerdo obj= new BecAcuerdo();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getAcuerdosAlumnoVigentes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecAcuerdo> getContratoAlumnosVigentes(Connection conn,String tipo, String idEjercicio, String orden) throws SQLException{
		
		ArrayList<BecAcuerdo> lis 		= new ArrayList<BecAcuerdo>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT * FROM ENOC.BEC_ACUERDO WHERE PUESTO_ID IN "
					+ "(SELECT PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO WHERE "
					+ "TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY')"
					+ " BETWEEN FECHA_INI AND FECHA_FIN AND ID_EJERCICIO = '"+idEjercicio+"' "
					+ "AND PUESTO_ID IN (SELECT PUESTO_ID FROM ENOC.BEC_ACUERDO WHERE TIPO = '"+tipo+"')) "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecAcuerdo obj= new BecAcuerdo();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getContratoAlumnosVigentes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecAcuerdo> getAcuerdosAlumnoPuesto(Connection conn, String idEjercicio, String codigoPersonal, String puestoId, String orden) throws SQLException{
		
		ArrayList<BecAcuerdo> lis 		= new ArrayList<BecAcuerdo>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT FOLIO, CODIGO_PERSONAL, ID_EJERCICIO, TIPO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ENSENANZA, INTERNADO, VALOR, " +
					" TO_CHAR(VIGENCIA, 'DD/MM/YYYY') AS VIGENCIA, ESTADO, PROMESA, ID_CCOSTO, PUESTO_ID, HORAS, TIPOADICIONAL, USUARIO FROM ENOC.BEC_ACUERDO " +
					" WHERE ID_EJERCICIO = '"+idEjercicio+"' " +
					" AND CODIGO_PERSONAL = '"+codigoPersonal+"' " +
					" AND PUESTO_ID = '"+puestoId+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecAcuerdo obj= new BecAcuerdo();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getAcuerdosAlumnoPuesto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public BecAcuerdo getAcuerdoAlumno(Connection conn, String codigo, String tipo, String puesto) throws SQLException{
		BecAcuerdo obj 					= new BecAcuerdo();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando = "";
		try{
			comando = "SELECT * FROM ENOC.BEC_ACUERDO WHERE CODIGO_PERSONAL = '"+codigo+"' AND TIPO = '"+tipo+"' AND PUESTO_ID = '"+puesto+"'";
			rs = st.executeQuery(comando);
			while (rs.next()){
				obj.mapeaReg(rs);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getAcuerdoAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return obj;
	}	
	
	public static String getCantidadAcuerdosFinanciero(Connection conn, String idEjercicio, String codigoPersonal, String puestoId, String tipoId, String orden) throws SQLException{
		
		String cantidad 				= "0";
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";		
		try{
			comando = "SELECT COUNT(*) AS CONT FROM MATEO.FES_CC_AFE_ACUERDOS " +
					" WHERE ACUERDO_EJERCICIO_ID = '"+idEjercicio+"'" +
					" AND MATRICULA = '"+codigoPersonal+"' " +
					" AND ALPUESTO_PUESTO_ID = '"+puestoId+"' " +
					" AND TIPO_ID = "+tipoId+" "+orden;
			//System.out.println(comando);
			rs = st.executeQuery(comando);
			while (rs.next()){
				cantidad = rs.getString("CONT");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getCantidadAcuerdosFinanciero|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return cantidad;
	}
	
	public ArrayList<String> getAcuerdosAlumnoFinanciero(Connection conn, String idEjercicio, String codigoPersonal, String puestoId, String orden) throws SQLException{
		
		ArrayList<String> lis 			= new ArrayList<String>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		// ACUERDO_IMP_MATRICULA+ACUERDO_IMP_ENSENANZA+ACUERDO_IMP_INTERNADO
		// EL CAMPPO: TOTAL_BECA_ADIC concentra la beca adicional disminuyendo la beca basica en los acuerdos
		try{
			comando = " SELECT TIPO_NOMBRE,"
					+ " CASE COALESCE(TOTAL_BECA_ADIC,0) WHEN 0 THEN ACUERDO_IMP_MATRICULA+ACUERDO_IMP_ENSENANZA+ACUERDO_IMP_INTERNADO ELSE TOTAL_BECA_ADIC END AS IMPORTE,"
					+ " TIPO_ID"
					+ " FROM MATEO.FES_CC_AFE_ACUERDOS"
					+ " WHERE ACUERDO_EJERCICIO_ID = '"+idEjercicio+"'"
					+ " AND MATRICULA = '"+codigoPersonal+"'"
					+ " AND ALPUESTO_PUESTO_ID = '"+puestoId+"'"
					+ " AND ACUERDO_FOLIO IN (SELECT FOLIO FROM BEC_ACUERDO WHERE PUESTO_ID  = '"+puestoId+"') " + orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				lis.add(rs.getString("TIPO_NOMBRE")+"@@"+rs.getString("IMPORTE")+"@@"+rs.getString("TIPO_ID"));
				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getAcuerdosAlumnoFinanciero|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<String> getAcuerdoDiscrepanciasEnHoras(Connection conn, String idEjercicio, String codigoPersonal, String puestoId, String orden) throws SQLException{
		
		ArrayList<String> lis 			= new ArrayList<String>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = " SELECT TIPO_NOMBRE,"
					+ " CASE COALESCE(TOTAL_BECA_ADIC,0) WHEN 0 THEN ACUERDO_IMP_MATRICULA+ACUERDO_IMP_ENSENANZA+ACUERDO_IMP_INTERNADO ELSE TOTAL_BECA_ADIC END AS IMPORTE,"
					+ " TIPO_ID"
					+ " FROM MATEO.FES_CC_AFE_ACUERDOS"
					+ " WHERE ACUERDO_EJERCICIO_ID = '"+idEjercicio+"'"
					+ " AND MATRICULA = '"+codigoPersonal+"'"
					+ " AND ALPUESTO_PUESTO_ID = '"+puestoId+"' " + orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				lis.add(rs.getString("TIPO_NOMBRE")+"@@"+rs.getString("IMPORTE")+"@@"+rs.getString("TIPO_ID"));
				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getAcuerdosAlumnoFinanciero|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public static String getHorasConv(Connection conn, String codigoPersonal, String puestoId) throws SQLException{
		
		String horas 				= "0";
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = " SELECT COALESCE(HORAS,0) AS HORAS FROM ENOC.BEC_ACUERDO "+
					  " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+
					  " AND PUESTO_ID = '"+puestoId+"' "+
					  " AND TIPO IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE ACUERDO IN ('B','I'))";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				horas = rs.getString("HORAS");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getHorasConv|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return horas;
	}
	
	public ArrayList<BecAcuerdo> getDiscrepanciasEnHoras(Connection conn, String idEjercicio, String orden) throws SQLException{
		
		ArrayList<BecAcuerdo> lis 		= new ArrayList<BecAcuerdo>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		/* LAS HORAS DE LA BECA BASICA SE PUSIERON EN EL CAMPO "VALOR" */
		/* EL TIPO DE LA BECA BASICA SE PUSO EN EL CAMPO DE "TIPOADICIONAL" */
		try{
			comando = " SELECT "
							+ "	FOLIO, "
							+ "	CODIGO_PERSONAL, "
							+ "	ID_EJERCICIO, "
							+ "	TIPO, "
							+ "	TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, "
							+ "	MATRICULA, "
							+ "	ENSENANZA, "
							+ "	INTERNADO, "
							+ " (SELECT HORAS FROM ENOC.BEC_ACUERDO WHERE ID_EJERCICIO = '"+idEjercicio+"' AND CODIGO_PERSONAL = BA.CODIGO_PERSONAL AND PUESTO_ID = BA.PUESTO_ID AND TIPO IN (1,3)) AS VALOR, "
							+ " TO_CHAR(VIGENCIA, 'DD/MM/YYYY') AS VIGENCIA, "
							+ "	ESTADO, "
							+ " PROMESA, "
							+ "	ID_CCOSTO, "
							+ "	PUESTO_ID, "
							+ "	HORAS, "
							+ "(SELECT TIPO FROM ENOC.BEC_ACUERDO WHERE ID_EJERCICIO = '"+idEjercicio+"' AND CODIGO_PERSONAL = BA.CODIGO_PERSONAL AND PUESTO_ID = BA.PUESTO_ID AND TIPO IN (1,3)) AS TIPOADICIONAL, "
							+ " USUARIO"
					+ " FROM ENOC.BEC_ACUERDO BA WHERE ID_EJERCICIO = '"+idEjercicio+"' AND TIPO NOT IN (1,3)"
					+ " AND HORAS != (SELECT HORAS FROM ENOC.BEC_ACUERDO WHERE ID_EJERCICIO = '"+idEjercicio+"' AND CODIGO_PERSONAL = BA.CODIGO_PERSONAL AND PUESTO_ID = BA.PUESTO_ID AND TIPO IN (1,3)) "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecAcuerdo obj= new BecAcuerdo();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getDiscrepanciasEnHoras|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public static ArrayList<BecAcuerdo> acuerdosInformesContabilizados(Connection conn, String idEjercicio, String informes, String orden) throws SQLException{
		
		ArrayList<BecAcuerdo> lis 		= new ArrayList<BecAcuerdo>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT * FROM ENOC.BEC_ACUERDO "
					+ " WHERE ID_EJERCICIO = '"+idEjercicio+"'"
					+ " AND CODIGO_PERSONAL||PUESTO_ID IN "
					+ " (SELECT CODIGO_PERSONAL||PUESTO_ID FROM ENOC.BEC_INFORME_ALUMNO WHERE INFORME_ID IN("+informes+") AND ESTADO = '3')"
					+ " AND ESTADO = 'I'"
					+ " AND CODIGO_PERSONAL||PUESTO_ID||FOLIO IN"
					+ " (SELECT MATRICULA||ALPUESTO_PUESTO_ID||ACUERDO_FOLIO FROM MATEO.FES_CC_AFE_ACUERDOS) "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecAcuerdo obj= new BecAcuerdo();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public static ArrayList<BecAcuerdo> acuerdosInformes(Connection conn, String idEjercicio, String informes, String estado, String orden) throws SQLException{
		
		ArrayList<BecAcuerdo> lis 		= new ArrayList<BecAcuerdo>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT * FROM ENOC.BEC_ACUERDO "
					+ " WHERE ID_EJERCICIO = '"+idEjercicio+"'"
					+ " AND CODIGO_PERSONAL||PUESTO_ID IN "
					+ " (SELECT CODIGO_PERSONAL||PUESTO_ID FROM ENOC.BEC_INFORME_ALUMNO WHERE INFORME_ID IN("+informes+") AND ESTADO = '"+estado+"')"
					+ " AND ESTADO = 'I'"
					+ " AND CODIGO_PERSONAL||PUESTO_ID||FOLIO IN"
					+ " (SELECT MATRICULA||ALPUESTO_PUESTO_ID||ACUERDO_FOLIO FROM MATEO.FES_CC_AFE_ACUERDOS) "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecAcuerdo obj= new BecAcuerdo();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public HashMap<String, String> mapPorMatricula(Connection conn, String ejercicioId, String tipo) throws SQLException{
		
		HashMap<String, String> map = new HashMap<String, String>();		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;		 
		String comando	= "";
		
		try{			
			comando = " SELECT CODIGO_PERSONAL, PUESTO_ID, SUM(MATRICULA) AS PORMATRICULA"
					+ " FROM ENOC.BEC_ACUERDO"
					+ " WHERE ID_EJERCICIO = '"+ejercicioId+"'"
					+ " AND PUESTO_ID||FOLIO IN (SELECT ALPUESTO_PUESTO_ID||ACUERDO_FOLIO FROM MATEO.FES_CC_AFE_ACUERDOS WHERE ACUERDO_EJERCICIO_ID = '"+ejercicioId+"')"
					+ " AND TIPO IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE ACUERDO IN ("+tipo+") AND ID_EJERCICIO = '"+ejercicioId+"')"
					+ " AND PUESTO_ID IS NOT NULL "
					+ " GROUP BY CODIGO_PERSONAL, PUESTO_ID";
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				map.put(rs.getString("CODIGO_PERSONAL")+rs.getString("PUESTO_ID"), rs.getString("PORMATRICULA"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|mapPorMatricula|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return map;
	}
	
	public HashMap<String, String> mapPorEnsenanza(Connection conn, String ejercicioId, String tipo) throws SQLException{
		
		HashMap<String, String> map = new HashMap<String, String>();		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;		 
		String comando	= "";
		
		try{			
			comando = " SELECT CODIGO_PERSONAL, PUESTO_ID, SUM(ENSENANZA) AS PORENSENANZA"
					+ " FROM ENOC.BEC_ACUERDO"
					+ " WHERE ID_EJERCICIO = '"+ejercicioId+"' "
					+ " AND PUESTO_ID||FOLIO IN (SELECT ALPUESTO_PUESTO_ID||ACUERDO_FOLIO FROM MATEO.FES_CC_AFE_ACUERDOS WHERE ACUERDO_EJERCICIO_ID = '"+ejercicioId+"')"					
					+ " AND TIPO IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE ACUERDO IN ("+tipo+") AND ID_EJERCICIO = '"+ejercicioId+"')"
					+ " AND PUESTO_ID IS NOT NULL "
					+ " GROUP BY CODIGO_PERSONAL, PUESTO_ID";
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				map.put(rs.getString("CODIGO_PERSONAL")+rs.getString("PUESTO_ID"), rs.getString("PORENSENANZA"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|mapPorEnsenanza|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return map;
	}

	public HashMap<String, String> mapPorInternado(Connection conn, String ejercicioId, String tipo) throws SQLException{
		
		HashMap<String, String> map = new HashMap<String, String>();		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;		 
		String comando	= "";
		
		try{			
			comando = " SELECT CODIGO_PERSONAL, PUESTO_ID, SUM(INTERNADO) AS PORINTERNADO"
					+ " FROM ENOC.BEC_ACUERDO"
					+ " WHERE ID_EJERCICIO = '"+ejercicioId+"' "
					+ " AND PUESTO_ID||FOLIO IN (SELECT ALPUESTO_PUESTO_ID||ACUERDO_FOLIO FROM MATEO.FES_CC_AFE_ACUERDOS WHERE ACUERDO_EJERCICIO_ID = '"+ejercicioId+"')"					
					+ " AND TIPO IN (SELECT TIPO FROM ENOC.BEC_TIPO WHERE ACUERDO IN ("+tipo+") AND ID_EJERCICIO = '"+ejercicioId+"')"
					+ " AND PUESTO_ID IS NOT NULL"
					+ " GROUP BY CODIGO_PERSONAL, PUESTO_ID";
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				map.put(rs.getString("CODIGO_PERSONAL")+rs.getString("PUESTO_ID"), rs.getString("PORINTERNADO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|mapPorInternado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return map;
	}

	public HashMap<String, Float> mapTotalPorPuesto(Connection conn, String ejercicioId, String periodoId) throws SQLException{
		
		HashMap<String, Float> map = new HashMap<String, Float>();		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;		 
		String comando	= "";
		
		try{			
			comando = " SELECT TIPO, SUM(ENSENANZA*HORAS) AS CANTIDAD "
					+ " FROM ENOC.BEC_ACUERDO "
					+ " WHERE ID_EJERCICIO = '"+ejercicioId+"' "
					+ " AND VALOR = 'C' "					
					+ " AND PUESTO_ID IN (SELECT PUESTO_ID FROM BEC_PUESTO_ALUMNO WHERE PERIODO_ID = '"+periodoId+"' AND ESTADO = 'C') "					
					+ " GROUP BY TIPO";
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				map.put(rs.getString("TIPO"), rs.getFloat("CANTIDAD"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdoUtil|mapTotalPorPueto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return map;
	}
	
}