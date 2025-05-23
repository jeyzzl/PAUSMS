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
public class AlumInfluenzaUtil {
	
	public boolean insertReg(Connection conn, AlumInfluenza alumInfluenza ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;	
 		try{ 			
 			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_INFLUENZA"+ 
 				"(CODIGO_PERSONAL," +
 				" FACULTAD," +
 				" CARRERA," +
 				" RESIDENCIA," +
 				" GENERO," +
 				" EDAD," +
 				" PERSONAS," +
 				" PERMANENCIA," +
 				" VIAJE," +
 				" DONDE," +
 				" FECHA," +
 				" VISITA_DOCTOR," +
 				" CONOCES," +
 				" CONTACTO," +
 				" TELEFONO," +
 				" SINTOMAS," +
 				" FECHA_ALTA, " +
 				" EXACTAMENTE, " +
 				" CONTACTO_COMUNIDAD, " +
 				" CONTACTO_RESIDENCIA," +
 				" FIEBRE, " +
 				" FEBRICULA, " +
 				" TOS, " +
 				" CEFALEA," +
 				" RINORREA, " +
 				" CORIZA, " +
 				" ARTRALGIAS, " +
 				" MIALGIAS," +
 				" ABDOMINAL, " +
 				" TORACICO, " +
 				" CONGESTION, " +
 				" LETARGIA," +
 				" MUSCULAR, AGOTAMIENTO, INICIO, ATAQUES," +
 				" DIARREA, NAUSEA, DORMITORIO, CUARTO) "+
 				"VALUES( ?, ?, ?, ?, ?, " +
 				" TO_NUMBER(?,'99'), TO_NUMBER(?,'99')," +
 				" ?, ?, ?, " +
 				" TO_DATE(?,'DD/MM/YYYY')," +
 				" ?,?, ?, ?," +
 				" TO_NUMBER(?,'99')," +
 				" TO_DATE(?,'DD/MM/YYYY')," +
 				" ?, ?, ?," +
 				" ?, ?, ?, ?," +
 				" ?, ?, ?, ?," +
 				" ?, ?, ?, ?," +
 				" ?, ?, ?, ?," +
 				" ?, ?, ?, ?)");
 			
 			ps.setString(1, alumInfluenza.getCodigoPersonal());
 			ps.setString(2, alumInfluenza.getFacultad());
 			ps.setString(3, alumInfluenza.getCarrera());
 			ps.setString(4, alumInfluenza.getResidencia());
 			ps.setString(5, alumInfluenza.getGenero());
 			ps.setString(6, alumInfluenza.getEdad());
 			ps.setString(7, alumInfluenza.getPersonas());
 			ps.setString(8, alumInfluenza.getPermanencia());
 			ps.setString(9, alumInfluenza.getViaje());
 			ps.setString(10, alumInfluenza.getDonde());
 			ps.setString(11, alumInfluenza.getFecha());
 			ps.setString(12, alumInfluenza.getVisitaDoctor());
 			ps.setString(13, alumInfluenza.getConoces());
 			ps.setString(14, alumInfluenza.getContacto());
 			ps.setString(15, alumInfluenza.getTelefono());
 			ps.setString(16, alumInfluenza.getSintomas());
 			ps.setString(17, alumInfluenza.getFechaAlta());
 			ps.setString(18, alumInfluenza.getExactamente());
 			ps.setString(19, alumInfluenza.getContactoComunidad());
 			ps.setString(20, alumInfluenza.getContactoResidencia());
 			ps.setString(21, alumInfluenza.getFiebre());
 			ps.setString(22, alumInfluenza.getFebricula());
 			ps.setString(23, alumInfluenza.getTos());
 			ps.setString(24, alumInfluenza.getCefalea());
 			ps.setString(25, alumInfluenza.getRinorrea());
 			ps.setString(26, alumInfluenza.getCoriza());
 			ps.setString(27, alumInfluenza.getArtralgias());
 			ps.setString(28, alumInfluenza.getMialgias());
 			ps.setString(29, alumInfluenza.getAbdominal());
 			ps.setString(30, alumInfluenza.getToracico());
 			ps.setString(31, alumInfluenza.getCongestion());
 			ps.setString(32, alumInfluenza.getLetargia());
 			ps.setString(33, alumInfluenza.getMuscular());
 			ps.setString(34, alumInfluenza.getAgotamiento());
 			ps.setString(35, alumInfluenza.getInicio());
 			ps.setString(36, alumInfluenza.getAtaques());
 			ps.setString(37, alumInfluenza.getDiarrea());
 			ps.setString(38, alumInfluenza.getNausea());
 			ps.setString(39, alumInfluenza.getDormitorio());
 			ps.setString(40, alumInfluenza.getCuarto());
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumInfluenzaUtil|insertReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}	
 	
 	public boolean updateReg(Connection conn, AlumInfluenza alumInfluenza ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("UPDATE ENOC.ALUM_INFLUENZA "+ 
 				"SET "+				
 				"FACULTAD = ?, "+
 				"CARRERA = ?, "+
 				"RESIDENCIA = ?, "+
 				"GENERO = ?, "+
 				"EDAD = TO_NUMBER(?,'99'), "+
 				"PERSONAS = TO_NUMBER(?,'99'), "+
 				"PERMANENCIA = ?, "+
 				"VIAJE = ?, "+
 				"DONDE = ?, "+
 				"FECHA = TO_DATE(?,'DD/MM/YYYY'), "+
 				"VISITA_DOCTOR = ?, "+
 				"CONOCES = ?, "+ 				
 				"CORREO = ?, "+ 				
 				"TELEFONO = ?, "+ 				
 				"SINTOMAS = TO_NUMBER(?,'99')," +
 				" FECHA_ALTA = TO_DATE(?,'DD/MM/YYYY')," +
 				" EXACTAMENTE = ?," +
 				" CONTACTO_COMUNIDAD = ?," +
 				" CONTACTO_RESIDENCIA = ?," +
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
 				" MUSCULAR = ?," +
 				" AGOTAMIENTO = ?," +
 				" INICIO = ?," +
 				" ATAQUES = ?," +
 				" DIARREA = ?," +
 				" NAUSEA = ?," +
 				" DORMITORIO = ?," +
 				" CUARTO = ?"+
 				" WHERE CODIGO_PERSONAL = ? ");
 	
 			ps.setString(1, alumInfluenza.getFacultad());
 			ps.setString(2, alumInfluenza.getCarrera());
 			ps.setString(3, alumInfluenza.getResidencia());
 			ps.setString(4, alumInfluenza.getGenero());
 			ps.setString(5, alumInfluenza.getEdad());
 			ps.setString(6, alumInfluenza.getPersonas());
 			ps.setString(7, alumInfluenza.getPermanencia());
 			ps.setString(8, alumInfluenza.getViaje());
 			ps.setString(9, alumInfluenza.getDonde());
 			ps.setString(10, alumInfluenza.getFecha());
 			ps.setString(11, alumInfluenza.getVisitaDoctor());
 			ps.setString(12, alumInfluenza.getConoces());
 			ps.setString(13, alumInfluenza.getContacto());
 			ps.setString(14, alumInfluenza.getTelefono()); 			
 			ps.setString(15, alumInfluenza.getSintomas());
 			ps.setString(16, alumInfluenza.getFechaAlta());
 			ps.setString(17, alumInfluenza.getExactamente());
 			ps.setString(18, alumInfluenza.getContactoComunidad());
 			ps.setString(19, alumInfluenza.getContactoResidencia());
 			ps.setString(20, alumInfluenza.getFiebre());
 			ps.setString(21, alumInfluenza.getFebricula());
 			ps.setString(22, alumInfluenza.getTos());
 			ps.setString(23, alumInfluenza.getCefalea());
 			ps.setString(24, alumInfluenza.getRinorrea());
 			ps.setString(25, alumInfluenza.getCoriza());
 			ps.setString(26, alumInfluenza.getArtralgias());
 			ps.setString(27, alumInfluenza.getMialgias());
 			ps.setString(28, alumInfluenza.getAbdominal());
 			ps.setString(29, alumInfluenza.getToracico());
 			ps.setString(30, alumInfluenza.getCongestion());
 			ps.setString(31, alumInfluenza.getLetargia());
 			ps.setString(32, alumInfluenza.getMuscular());
 			ps.setString(33, alumInfluenza.getAgotamiento());
 			ps.setString(34, alumInfluenza.getInicio());
 			ps.setString(35, alumInfluenza.getAtaques());
 			ps.setString(36, alumInfluenza.getDiarrea());
 			ps.setString(37, alumInfluenza.getNausea());
 			ps.setString(38, alumInfluenza.getDormitorio());
 			ps.setString(39, alumInfluenza.getCuarto());
 			ps.setString(40, alumInfluenza.getCodigoPersonal());
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;	
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumInfluenzaUtil|updateReg|:"+ex);		
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	} 	
 	
 	public boolean deleteReg(Connection conn, String codigoPersonal ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_INFLUENZA "+ 
 				"WHERE CODIGO_PERSONAL = ? ");
 			ps.setString(1, codigoPersonal);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumInfluenzaUtil|deleteReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}
 	
 	public AlumInfluenza mapeaRegId( Connection conn, String codigoPersonal ) throws SQLException, IOException{
 		AlumInfluenza alumInfluenza = new AlumInfluenza();
 		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try{
	 		ps = conn.prepareStatement("SELECT"+
	 			" CODIGO_PERSONAL, FACULTAD, CARRERA, RESIDENCIA, GENERO, EDAD, " +
	 			" PERSONAS, PERMANENCIA, VIAJE, DONDE, FECHA, VISITA_DOCTOR, " +
	 			" CONOCES, CORREO, TELEFONO, SINTOMAS," +
	 			" FECHA_ALTA, EXACTAMENTE, CONTACTO_COMUNIDAD, CONTACTO_RESIDENCIA," +
				" FIEBRE, FEBRICULA, TOS, CEFALEA," +
				" RINORREA, CORIZA, ARTRALGIAS, MIALGIAS," +
				" ABDOMINAL, TORACICO, CONGESTION, LETARGIA," +
				" MUSCULAR, AGOTAMIENTO, INICIO, ATAQUES," +
	 			" DIARREA, NAUSEA, DORMITORIO, CUARTO"+
	 			" FROM ENOC.ALUM_INFLUENZA WHERE CODIGO_PERSONAL = ? "); 
	 		ps.setString(1, codigoPersonal);	
	 		rs = ps.executeQuery();
	 		if (rs.next()){	 			
	 			alumInfluenza.mapeaReg(rs);
	 		}
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumInfluenzaUtil|mapeaRegId|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		return alumInfluenza;
 	}
	
 	public boolean existeReg(Connection conn, String codigoPersonal ) throws SQLException{
 		boolean 		ok 	= false;
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_INFLUENZA "+ 
 				"WHERE CODIGO_PERSONAL = ?");
 			ps.setString(1, codigoPersonal);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumInfluenzaUtil|existeReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}
 	
 	public static String getResidencia(Connection conn, String tipoResidencia) throws SQLException{
 		String cantidad			= "0";
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT COUNT(*) AS CANTIDAD FROM ENOC.ALUM_INFLUENZA "+ 
 				"WHERE RESIDENCIA = ?");
 			ps.setString(1, tipoResidencia);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				cantidad = rs.getString("CANTIDAD");
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumInfluenzaUtil|getResidencia|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		return cantidad;
 	}
 	
 	public static String getPermanencia(Connection conn, String tipoPermanencia) throws SQLException{
 		String cantidad			= "0";
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT COUNT(*) AS CANTIDAD FROM ENOC.ALUM_INFLUENZA "+ 
 				"WHERE PERMANENCIA = ?");
 			ps.setString(1, tipoPermanencia);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				cantidad = rs.getString("CANTIDAD");
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumInfluenzaUtil|getPermanencia|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		return cantidad;
 	}
 	
 	public static String getSintomasGraves(Connection conn) throws SQLException{
 		String cantidad			= "0";
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT COUNT(*) AS CANTIDAD FROM ENOC.ALUM_INFLUENZA"+ 
 				" WHERE FIEBRE IN ('1', '2')" +
 				" AND TOS IN ('1', '2')" +
 				" AND CEFALEA IN ('1', '2')");
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				cantidad = rs.getString("CANTIDAD");
 			if(cantidad == null)
 				cantidad = "0";
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumInfluenzaUtil|getSintomasGraves|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		} 		
 		return cantidad;
 	}
 	
 	public static String getViajeFueraDeNuevoLeon(Connection conn) throws SQLException{
 		String cantidad			= "0";
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT COUNT(*) AS CANTIDAD FROM ENOC.ALUM_INFLUENZA"+ 
 				" WHERE VIAJE != '1'" +
 				" OR EXACTAMENTE IS NOT NULL");
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				cantidad = rs.getString("CANTIDAD");
 			if(cantidad == null)
 				cantidad = "0";
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumInfluenzaUtil|getViajeFueraDeNuevoLeon|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		return cantidad;
 	}
 	
 	public static String getContacto(Connection conn) throws SQLException{
 		String cantidad			= "0";
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT COUNT(*) AS CANTIDAD FROM ENOC.ALUM_INFLUENZA"+ 
 				" WHERE CONTACTO = 'S'");
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				cantidad = rs.getString("CANTIDAD");
 			if(cantidad == null)
 				cantidad = "0";
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumInfluenzaUtil|getContacto|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}		
 		return cantidad;
 	}
	
	public ArrayList<String> getListDias(Connection conn, String orden ) throws SQLException{
		
		ArrayList<String> listFechas	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT TO_CHAR(FECHA_ALTA, 'DD/MM/YYYY') AS FECHA, COUNT(FECHA_ALTA) AS CANTIDAD " +
 				" FROM ENOC.ALUM_INFLUENZA" + 
 				" GROUP BY FECHA_ALTA "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				listFechas.add(rs.getString("FECHA"));
				listFechas.add(rs.getString("CANTIDAD"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumInfluenzaUtil|getListDias|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		return listFechas;
	}
}