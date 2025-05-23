/**
 * 
 */
package aca.alumno;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author elifo
 *
 */
public class AlumDatosUtil {
	
	public boolean insertReg(Connection conn, AlumDatos alumDatos ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_DATOS"+ 
 				" (CODIGO_PERSONAL, FOLIO, FECHA, SINTOMAS," +
 				" MODULO, VISITA, CONOCES, NOMBRE," +
 				" TELEFONO, CORREO, CAMBIO, FIEBRE," +
 				" FEBRICULA, TOS, CEFALEA, RINORREA," +
 				" CORIZA, ARTRALGIAS, MIALGIAS, ABDOMINAL," +
 				" TORACICO, CONGESTION, LETARGIA, PERMANENCIA," +
 				" VIAJE, EXACTAMENTE, CONTACTO, CONTACTO_NOMBRE," +
 				" CONTACTO_COMUNIDAD, CONTACTO_RESIDENCIA, NECESIDAD, MUSCULAR," +
 				" AGOTAMIENTO, INICIO, ATAQUES, DIARREA," +
 				" NAUSEA, AMIGO, ESTRES, INF_INFLUENZA," +
 				" INF_CLASES, INF_E42, MANOS, ESCUPIR," +
 				" ALREDEDOR)"+
 				" VALUES(?, TO_NUMBER(?, '99999'), TO_DATE(?, 'DD/MM/YYYY'), TO_NUMBER(?, '99')," +
 				" ?, ?, ?, ?," +
 				" ?, ?, ?, ?," +
 				" ?, ?, ?, ?," +
 				" ?, ?, ?, ?," +
 				" ?, ?, ?, ?," +
 				" ?, ?, ?, ?," +
 				" ?, ?, ?, ?," +
 				" ?, ?, ?, ?," +
 				" ?, ?, ?, ?," +
 				" ?, ?, ?, ?," +
 				" ?)");
 			
 			ps.setString(1, alumDatos.getCodigoPersonal());
 			ps.setString(2, alumDatos.getFolio());
 			ps.setString(3, alumDatos.getFecha());
 			ps.setString(4, alumDatos.getSintomas());
 			ps.setString(5, alumDatos.getModulo());
 			ps.setString(6, alumDatos.getVisita());
 			ps.setString(7, alumDatos.getConoces());
 			ps.setString(8, alumDatos.getNombre());
 			ps.setString(9, alumDatos.getTelefono());
 			ps.setString(10, alumDatos.getCorreo());
 			ps.setString(11, alumDatos.getCambio());
 			ps.setString(12, alumDatos.getFiebre());
 			ps.setString(13, alumDatos.getFebricula());
 			ps.setString(14, alumDatos.getTos());
 			ps.setString(15, alumDatos.getCefalea());
 			ps.setString(16, alumDatos.getRinorrea());
 			ps.setString(17, alumDatos.getCoriza());
 			ps.setString(18, alumDatos.getArtralgias());
 			ps.setString(19, alumDatos.getMialgias());
 			ps.setString(20, alumDatos.getAbdominal());
 			ps.setString(21, alumDatos.getToracico());
 			ps.setString(22, alumDatos.getCongestion());
 			ps.setString(23, alumDatos.getLetargia());
 			ps.setString(24, alumDatos.getPermanencia());
 			ps.setString(25, alumDatos.getViaje());
 			ps.setString(26, alumDatos.getExactamente());
 			ps.setString(27, alumDatos.getContacto());
 			ps.setString(28, alumDatos.getContactoNombre());
 			ps.setString(29, alumDatos.getContactoComunidad());
 			ps.setString(30, alumDatos.getContactoResidencia());
 			ps.setString(31, alumDatos.getNecesidad());
 			ps.setString(32, alumDatos.getMuscular());
 			ps.setString(33, alumDatos.getAgotamiento());
 			ps.setString(34, alumDatos.getInicio());
 			ps.setString(35, alumDatos.getAtaques());
 			ps.setString(36, alumDatos.getDiarrea());
 			ps.setString(37, alumDatos.getNauseas());
 			ps.setString(38, alumDatos.getAmigo());
 			ps.setString(39, alumDatos.getEstres());
 			ps.setString(40, alumDatos.getInfInfluenza());
 			ps.setString(41, alumDatos.getInfClases());
 			ps.setString(42, alumDatos.getInfE42());
 			ps.setString(43, alumDatos.getManos());
 			ps.setString(44, alumDatos.getEscupir());
 			ps.setString(45, alumDatos .getAlrededor());
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumDatosUtil|insertReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(Connection conn, AlumDatos alumDatos ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("UPDATE ENOC.ALUM_DATOS "+ 
 				"SET FECHA = TO_DATE(?, 'DD/MM/YYYY')," +
 				" SINTOMAS = TO_NUMBER(?, '99')," +
 				" MODULO = ?," +
 				" VISITA = ?," +
 				" CONOCES = ?," +
 				" NOMBRE = ?," +
 				" TELEFONO = ?," +
 				" CORREO = ?," +
 				" CAMBIO = ?," +
 				" FIEBRE = ?," +
 				" FEBRICULA = ?," +
 				" TOS = ?," +
 				" CEFALEA = ?," +
 				" RINORREA = ?," +
 				" CORIZA = ?," +
 				" ARTRALGIAS = ?," +
 				" MIALGIAS = ?," +
 				" ABDOMINAL = ?," +
 				" TORACICO = ?," +
 				" CONGESTION = ?," +
 				" LETARGIA = ?," +
 				" PERMANENCIA = ?," +
 				" VIAJE = ?," +
 				" EXACTAMENTE = ?," +
 				" CONTACTO = ?," +
 				" CONTACTO_NOMBRE = ?," +
 				" CONTACTO_COMUNIDAD = ?," +
 				" CONTACTO_RESIDENCIA = ?," +
 				" NECESIDAD = ?," +
 				" MUSCULAR = ?," +
 				" AGOTAMIENTO = ?," +
 				" INICIO = ?," +
 				" ATAQUES = ?," +
 				" DIARREA = ?," +
 				" NAUSEA = ?," +
 				" AMIGO = ?," +
 				" ESTRES = ?," +
 				" INF_INFLUENZA = ?," +
 				" INF_CLASES = ?," +
 				" INF_E42 = ?," +
 				" MANOS = ?," +
 				" ESCUPIR = ?," +
 				" ALREDEDOR = ?"+
 				" WHERE CODIGO_PERSONAL = ?" +
 				" AND FOLIO = TO_NUMBER(?, '99999')");
 				
 			ps.setString(1, alumDatos.getFecha());
 			ps.setString(2, alumDatos.getSintomas());
 			ps.setString(3, alumDatos.getModulo());
 			ps.setString(4, alumDatos.getVisita());
 			ps.setString(5, alumDatos.getConoces());
 			ps.setString(6, alumDatos.getNombre());
 			ps.setString(7, alumDatos.getTelefono());
 			ps.setString(8, alumDatos.getCorreo());
 			ps.setString(9, alumDatos.getCambio());
 			ps.setString(10, alumDatos.getFiebre());
 			ps.setString(11, alumDatos.getFebricula());
 			ps.setString(12, alumDatos.getTos());
 			ps.setString(13, alumDatos.getCefalea());
 			ps.setString(14, alumDatos.getRinorrea());
 			ps.setString(15, alumDatos.getCoriza());
 			ps.setString(16, alumDatos.getArtralgias());
 			ps.setString(17, alumDatos.getMialgias());
 			ps.setString(18, alumDatos.getAbdominal());
 			ps.setString(19, alumDatos.getToracico());
 			ps.setString(20, alumDatos.getCongestion());
 			ps.setString(21, alumDatos.getLetargia());
 			ps.setString(22, alumDatos.getPermanencia());
 			ps.setString(23, alumDatos.getViaje());
 			ps.setString(24, alumDatos.getExactamente());
 			ps.setString(25, alumDatos.getContacto());
 			ps.setString(26, alumDatos.getContactoNombre());
 			ps.setString(27, alumDatos.getContactoComunidad());
 			ps.setString(28, alumDatos.getContactoResidencia());
 			ps.setString(29, alumDatos.getNecesidad());
 			ps.setString(30, alumDatos.getMuscular());
 			ps.setString(31, alumDatos.getAgotamiento());
 			ps.setString(32, alumDatos.getInicio());
 			ps.setString(33, alumDatos.getAtaques());
 			ps.setString(34, alumDatos.getDiarrea());
 			ps.setString(35, alumDatos.getNauseas());
 			ps.setString(36, alumDatos.getAmigo());
 			ps.setString(37, alumDatos.getEstres());
 			ps.setString(38, alumDatos.getInfInfluenza());
 			ps.setString(39, alumDatos.getInfClases());
 			ps.setString(40, alumDatos.getInfE42());
 			ps.setString(41, alumDatos.getManos());
 			ps.setString(42, alumDatos.getEscupir());
 			ps.setString(43, alumDatos .getAlrededor());
 			ps.setString(44, alumDatos.getCodigoPersonal());
 			ps.setString(45, alumDatos.getFolio());
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;	
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumDatosUtil|updateReg|:"+ex);		
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	} 	
 	
 	
 	public boolean deleteReg(Connection conn, String codigoPersonal, String folio ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_DATOS"+ 
 				" WHERE CODIGO_PERSONAL = ?" +
 				" AND FOLIO = TO_NUMBER(?, '99999')");
 			
 			ps.setString(1, codigoPersonal);
 			ps.setString(2, folio);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumDatos|deleteReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}
 	
 	public AlumDatos mapeaRegId( Connection conn, String codigoPersonal, String folio  ) throws SQLException, IOException{
 		
 		AlumDatos alumDatos = new AlumDatos();
 		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try{
	 		ps = conn.prepareStatement("SELECT"+
	 			" CODIGO_PERSONAL, FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY'), SINTOMAS," +
	 			" MODULO, VISITA, CONOCES, NOMBRE," +
	 			" TELEFONO, CORREO, CAMBIO, FIEBRE," +
				" FEBRICULA, TOS, CEFALEA, RINORREA," +
				" CORIZA, ARTRALGIAS, MIALGIAS, ABDOMINAL," +
				" TORACICO, CONGESTION, LETARGIA, PERMANENCIA," +
				" VIAJE, EXACTAMENTE, CONTACTO, CONTACTO_NOMBRE," +
				" CONTACTO_COMUNIDAD, CONTACTO_RESIDENCIA, NECESIDAD, MUSCULAR," +
				" AGOTAMIENTO, INICIO, ATAQUES, DIARREA," +
				" NAUSEA, AMIGO, ESTRES, INF_INFLUENZA," +
				" INF_CLASES, INF_E42, MANOS, ESCUPIR," +
				" ALREDEDOR"+
	 			" FROM ENOC.ALUM_DATOS" + 
	 			" WHERE CODIGO_PERSONAL = ?" +
	 			" AND FOLIO = TO_NUMBER(?, '99999')");
	 		
	 		ps.setString(1, codigoPersonal);
	 		ps.setString(2, folio);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			alumDatos.mapeaReg(rs);
	 		}
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumDatosUtil|mapeaRegId|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		return alumDatos;
 	}
	
 	public boolean existeReg(Connection conn, String codigoPersonal, String folio) throws SQLException{
 		boolean 		ok 	= false;
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_DATOS"+ 
 				" WHERE CODIGO_PERSONAL = ?" +
 				" AND FOLIO = TO_NUMBER(?, '99999')");
 			
 			ps.setString(1, codigoPersonal);
 	 		ps.setString(2, folio);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumDatosUtil|existeReg|:"+ex);
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
 			ps = conn.prepareStatement("SELECT MAX(FOLIO)+1 AS MAXIMO FROM ENOC.ALUM_DATOS"+ 
 				" WHERE CODIGO_PERSONAL = ?");
 			
 			ps.setString(1, codigoPersonal);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				maximo = rs.getString("MAXIMO");
 			if(maximo == null)
 				maximo = "1";
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumDatosUtil|maximoReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return maximo;
 	}
 	
 	public static boolean fechaLlena(Connection conn, String codigoPersonal, String fecha) throws SQLException{
 		boolean 		ok 	= false;
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT COUNT(*) AS CANTIDAD FROM ENOC.ALUM_DATOS"+ 
 				" WHERE CODIGO_PERSONAL = ?" +
 				" AND FECHA = TO_DATE(?, 'DD/MM/YYYY')");
 			
 			ps.setString(1, codigoPersonal);
 	 		ps.setString(2, fecha);
 			
 			rs = ps.executeQuery();
 			if (rs.next()){
 				String cantidad = rs.getString("CANTIDAD")==null?"0":rs.getString("CANTIDAD");
 				if(Integer.parseInt(cantidad.trim()) >= 2)
 					ok = true;
 				else
 					ok = false;
 			}else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumDatosUtil|fechaLlena|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	
 	public static String getCambioSintomas(Connection conn) throws SQLException{
 		String 		cantidad 		= "0";
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT COUNT(*) AS CANTIDAD FROM ENOC.ALUM_DATOS" + 
 					" WHERE FIEBRE = '2'" +
 					" OR FEBRICULA = '2'" +
 					" OR TOS = '2'" +
 					" OR CEFALEA = '2'" +
 					" OR RINORREA = '2'" +
 					" OR CORIZA = '2'" +
 					" OR ARTRALGIAS = '2'" +
 					" OR MIALGIAS = '2'" +
 					" OR ABDOMINAL = '2'" +
 					" OR TORACICO = '2'" +
 					" OR CONGESTION = '2'" +
 					" OR LETARGIA = '2'");
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				cantidad = rs.getString("CANTIDAD");
 			if(cantidad == null)
 				cantidad = "0";
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumDatosUtil|getCambioSintomas|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return cantidad;
 	}
 	
 	public static String getFueronAModulo(Connection conn) throws SQLException{
 		String 		cantidad 		= "0";
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT COUNT(DISTINCT(CODIGO_PERSONAL)) AS CANTIDAD FROM ENOC.ALUM_DATOS" + 
 					" WHERE MODULO = 'S'");
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				cantidad = rs.getString("CANTIDAD");
 			if(cantidad == null)
 				cantidad = "0";
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumDatosUtil|getFueronAModulo|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return cantidad;
 	}
 	
 	public static String getVisitadas(Connection conn) throws SQLException{
 		String 		cantidad 		= "0";
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT COUNT(DISTINCT(CODIGO_PERSONAL)) AS CANTIDAD FROM ENOC.ALUM_DATOS" + 
 					" WHERE VISITA = 'S'");
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				cantidad = rs.getString("CANTIDAD");
 			if(cantidad == null)
 				cantidad = "0";
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumDatosUtil|getVisitadas|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return cantidad;
 	}
 	
 	public static String getConoces(Connection conn) throws SQLException{
 		String 		cantidad 		= "0";
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT COUNT(DISTINCT(CODIGO_PERSONAL)) AS CANTIDAD FROM ENOC.ALUM_DATOS" + 
 					" WHERE CONOCES = 'S'");
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				cantidad = rs.getString("CANTIDAD");
 			if(cantidad == null)
 				cantidad = "0";
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumDatosUtil|getConoces|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return cantidad;
 	}
 	
 	public static String getNecesidad(Connection conn) throws SQLException{
 		String 		cantidad 		= "0";
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT COUNT(DISTINCT(CODIGO_PERSONAL)) AS CANTIDAD FROM ENOC.ALUM_DATOS" + 
 					" WHERE NECESIDAD = 'S'");
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				cantidad = rs.getString("CANTIDAD");
 			if(cantidad == null)
 				cantidad = "0";
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumDatosUtil|getNecesidad|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return cantidad;
 	}
	
	public ArrayList<AlumDatos> getListFecha(Connection conn, String fecha, String orden ) throws SQLException{
		
		ArrayList<AlumDatos> listAlumno	= new ArrayList<AlumDatos>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, SINTOMAS," +
 				" MODULO, VISITA, CONOCES, NOMBRE," +
 				" TELEFONO, CORREO, CAMBIO, FIEBRE," +
 				" FEBRICULA, TOS, CEFALEA, RINORREA," +
 				" CORIZA, ARTRALGIAS, MIALGIAS, ABDOMINAL," +
 				" TORACICO, CONGESTION, LETARGIA, PERMANENCIA," +
 				" VIAJE, EXACTAMENTE, CONTACTO, CONTACTO_NOMBRE," +
 				" CONTACTO_COMUNIDAD, CONTACTO_RESIDENCIA, NECESIDAD, MUSCULAR," +
 				" AGOTAMIENTO, INICIO, ATAQUES, DIARREA," +
 				" NAUSEA, AMIGO, ESTRES, INF_INFLUENZA," +
 				" INF_CLASES, INF_E42, MANOS, ESCUPIR," +
 				" ALREDEDOR " +
 				" FROM ENOC.ALUM_DATOS" + 
 				" WHERE FECHA = TO_DATE('"+fecha+"', 'DD/MM/YYYY') "+orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumDatos alumno = new AlumDatos();
				alumno.mapeaReg(rs);
				listAlumno.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumDatosUtil|getListFecha|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return listAlumno;
	}
}