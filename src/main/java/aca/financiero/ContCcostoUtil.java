package aca.financiero;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class ContCcostoUtil {
	
	public ContCcosto mapeaRegId(Connection conn, String idEjercicio, String idCcosto) throws SQLException, IOException{
		
		ContCcosto costo = new ContCcosto();
 		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try{
	 		ps = conn.prepareStatement("SELECT ID_EJERCICIO, ID_CCOSTO, NOMBRE, DETALLE, INICIALES" +	 					
	 				" FROM MATEO.CONT_CCOSTO WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ?");
	 		ps.setString(1, idEjercicio);
	 		ps.setString(2, idCcosto);	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			costo.mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContCcosto|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 		
 		return costo;
 	}
	
	public boolean existeReg(Connection conn, String idEjercicio, String idCcosto) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE FROM MATEO.FES_CCOBRO WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ?");
			ps.setString(1, idEjercicio);
	 		ps.setString(2, idCcosto);
	 		
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContCcosto|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getNombre( Connection conn, String idEjercicio, String idCcosto) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		String nombre 			= "";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE FROM MATEO.CONT_CCOSTO WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ?");
			ps.setString(1, idEjercicio);
	 		ps.setString(2, idCcosto);	 		
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE");
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContCcosto|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static String getNombreJefe( Connection conn, String idEjercicio, String idCcosto) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		String nombreJefe 		= "";		
		try{
			ps = conn.prepareStatement("SELECT (NOMBRE||' '||APPATERNO||' '||APMATERNO) AS NOMBREJEFE"
					+ " FROM ARON.EMPLEADO"
					+ " WHERE ID IN (SELECT DISTINCT(JEFE_ID) FROM ARON.CAT_JEFES WHERE EJERCICIO_ID = '"+idEjercicio+"' AND CCOSTO_ID = '"+idCcosto+"' AND STATUS='A')");	 		
			rs = ps.executeQuery();
			if (rs.next())
				nombreJefe = rs.getString("NOMBREJEFE");
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContCcosto|getNombreJefe|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombreJefe;
	}	
	
	
	public static String getEjercicioActual( Connection conn, String empresaId) throws SQLException{
				
		String ejercicio 	= empresaId+"-"+aca.util.Fecha.getHoy().substring(6, 10);
		
		return ejercicio;
	}
	
	public ArrayList<ContCcosto> getListCentrosCosto(Connection conn, String idEjercicio, String orden) throws SQLException{
		ArrayList<ContCcosto> lista 	= new ArrayList<ContCcosto>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{			
			comando = "SELECT ID_EJERCICIO, ID_CCOSTO, NOMBRE, DETALLE, INICIALES FROM MATEO.CONT_CCOSTO WHERE ID_EJERCICIO = '"+idEjercicio+"' "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				ContCcosto depto = new ContCcosto();
				depto.mapeaReg(rs);
				lista.add(depto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContCcostoUtil|getListCentrosCosto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public ArrayList<ContCcosto> getListCentrosCosto(Connection conn, String idEjercicio, String detalle, String orden) throws SQLException{
		ArrayList<ContCcosto> lista 	= new ArrayList<ContCcosto>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{			
			comando = "SELECT ID_EJERCICIO, ID_CCOSTO, NOMBRE, DETALLE, INICIALES FROM MATEO.CONT_CCOSTO WHERE ID_EJERCICIO = '"+idEjercicio+"' AND DETALLE IN("+detalle+")"+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				ContCcosto depto = new ContCcosto();
				depto.mapeaReg(rs);
				lista.add(depto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContCcostoUtil|getListCentrosCosto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public ArrayList<ContCcosto> getListCentrosCostoPuestos(Connection conn, String idEjercicio, String detalle, String orden) throws SQLException{
		ArrayList<ContCcosto> lista 	= new ArrayList<ContCcosto>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{			
			comando = "SELECT ID_EJERCICIO, ID_CCOSTO, NOMBRE, DETALLE, INICIALES FROM MATEO.CONT_CCOSTO" +
					" WHERE ID_EJERCICIO = '"+idEjercicio+"' " +
					" AND DETALLE IN("+detalle+") " +
					" AND ID_CCOSTO IN (SELECT ID_CCOSTO FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = '"+idEjercicio+"') "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				ContCcosto depto = new ContCcosto();
				depto.mapeaReg(rs);
				lista.add(depto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContCcostoUtil|getListCentrosCosto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public ArrayList<ContCcosto> getListDepartamentos(Connection conn, String idEjercicio, String condicion, String orden) throws SQLException{
		ArrayList<ContCcosto> lista 	= new ArrayList<ContCcosto>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{			
			comando = "SELECT ID_EJERCICIO, ID_CCOSTO, NOMBRE, DETALLE, INICIALES FROM MATEO.CONT_CCOSTO WHERE ID_EJERCICIO = '"+idEjercicio+"' "+condicion+" "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				ContCcosto depto = new ContCcosto();
				depto.mapeaReg(rs);
				lista.add(depto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContCcostoUtil|getListDepartamentos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public ArrayList<ContCcosto> getListCentrosCostoVacantes(Connection conn, String idEjercicio, String detalle, String orden) throws SQLException{
		ArrayList<ContCcosto> lista 	= new ArrayList<ContCcosto>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{			
			comando = "SELECT ID_EJERCICIO, ID_CCOSTO, NOMBRE, DETALLE, INICIALES FROM MATEO.CONT_CCOSTO WHERE ID_EJERCICIO = '"+idEjercicio+"' " +
					"AND DETALLE IN("+detalle+") AND ID_CCOSTO IN (SELECT ID_CCOSTO FROM ENOC.BEC_PLAZAS WHERE (NUM_PLAZAS+NUM_TEMPORALES" +
							"+NUM_POSGRADO+NUM_INDUSTRIALES+NUM_PREINDUSTRIALES)>0) "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				ContCcosto depto = new ContCcosto();
				depto.mapeaReg(rs);
				lista.add(depto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContCcostoUtil|getListCentrosCosto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public ArrayList<ContCcosto> getListCentrosCostoVacantes(Connection conn, String idEjercicio, String periodoId, String detalle, String orden) throws SQLException{
		ArrayList<ContCcosto> lista 	= new ArrayList<ContCcosto>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{			
			comando = " SELECT ID_EJERCICIO, ID_CCOSTO, NOMBRE, DETALLE, INICIALES FROM MATEO.CONT_CCOSTO"
					+ " WHERE ID_EJERCICIO = '"+idEjercicio+"'"
					+ " AND DETALLE IN("+detalle+")"							
					+ " AND ID_CCOSTO IN "
					+ "		(SELECT ID_CCOSTO FROM ENOC.BEC_PLAZAS "
					+ "		WHERE (NUM_PLAZAS+NUM_TEMPORALES+NUM_POSGRADO+NUM_INDUSTRIALES+NUM_PREINDUSTRIALES)>0"
					+ "		AND PERIODO_ID = '"+periodoId+"') "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				ContCcosto depto = new ContCcosto();
				depto.mapeaReg(rs);
				lista.add(depto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContCcostoUtil|getListCentrosCosto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public ArrayList<ContCcosto> getListCentrosCostoVacantesFiltro(Connection conn, String idEjercicio, String periodoId, String detalle, String orden, String filtra) throws SQLException{
		ArrayList<ContCcosto> lista 	= new ArrayList<ContCcosto>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{			
			comando = " SELECT ID_EJERCICIO, ID_CCOSTO, NOMBRE, DETALLE, INICIALES FROM MATEO.CONT_CCOSTO"
					+ " WHERE NOMBRE LIKE '%"+filtra+"%' AND ID_EJERCICIO = '"+idEjercicio+"'"
					+ " AND DETALLE IN("+detalle+")"							
					+ " AND ID_CCOSTO IN "
					+ "		(SELECT ID_CCOSTO FROM ENOC.BEC_PLAZAS "
					+ "		WHERE (NUM_PLAZAS+NUM_TEMPORALES+NUM_POSGRADO+NUM_INDUSTRIALES+NUM_PREINDUSTRIALES)>0"
					+ "		AND PERIODO_ID = '"+periodoId+"') "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				ContCcosto depto = new ContCcosto();
				depto.mapeaReg(rs);
				lista.add(depto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContCcostoUtil|getListCentrosCosto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	// Obtiene la lista de departamentos a la que no tiene acceso un usuario
	public ArrayList<ContCcosto> getListAcceso(Connection conn, String idEjercicio, String codigo, String filtro, String orden) throws SQLException{
		ArrayList<ContCcosto> lista 	= new ArrayList<ContCcosto>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{			
			comando = "SELECT ID_EJERCICIO, ID_CCOSTO, NOMBRE, DETALLE, INICIALES FROM MATEO.CONT_CCOSTO" +
					" WHERE ID_EJERCICIO = '"+idEjercicio+"' " +
					" AND DETALLE = 'S'"+
					" AND ID_CCOSTO LIKE '"+filtro+"%' " +orden;					
			rs = st.executeQuery(comando);
			while (rs.next()){
				ContCcosto depto = new ContCcosto();
				depto.mapeaReg(rs);
				lista.add(depto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContCcostoUtil|getListAcceso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public static HashMap<String, ContCcosto> getMapCentrosCosto(Connection conn, String idEjercicio) throws SQLException{
		HashMap<String, ContCcosto> mapCcosto = new HashMap<String, ContCcosto>();		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;	
		try{			
			String comando = "SELECT ID_EJERCICIO, ID_CCOSTO, NOMBRE, DETALLE, INICIALES FROM MATEO.CONT_CCOSTO WHERE ID_EJERCICIO = '"+idEjercicio+"'";			
			rs = st.executeQuery(comando);
			while(rs.next()){
				ContCcosto depto = new ContCcosto();
				depto.mapeaReg(rs);
				mapCcosto.put(depto.getIdCcosto(), depto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcobroUtil|getMapCentrosCosto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return mapCcosto;
	}	
}