// Clase para la tabla de Acceso
package aca.afe;

import java.sql.*;
import java.util.ArrayList;

public class PuestoUtil{
		
	public ArrayList<AfeCCostoPuesto> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AfeCCostoPuesto> lisAcceso 	= new ArrayList<AfeCCostoPuesto>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{			
			comando = "SELECT ID,EJERCICIO_ID, CCOSTO_ID, PUESTO_ID, TURNO,DIAS," +
					" REQUISITOS, EMAIL, MAXIMO_HORAS, CLAVE, STATUS" +
					" FROM NOE.AFE_CCOSTO_PUESTO "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AfeCCostoPuesto acceso = new AfeCCostoPuesto();
				acceso.mapeaReg(rs);
				lisAcceso.add(acceso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.afe.PuestoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAcceso;
	}	
	
	public ArrayList<AfeCCostoPuesto> getListVacantes(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AfeCCostoPuesto> lisAcceso 	= new ArrayList<AfeCCostoPuesto>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{			
			comando = "SELECT ID,EJERCICIO_ID, CCOSTO_ID, PUESTO_ID, TURNO, DIAS," +
					" REQUISITOS, EMAIL, MAXIMO_HORAS, CLAVE, STATUS" +
					" FROM NOE.AFE_CCOSTO_PUESTO " +
					" WHERE ID NOT IN (SELECT CCOSTO_PUESTO_ID FROM NOE.AFE_CONTRATO_ALUMNO WHERE STATUS = 'A') "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AfeCCostoPuesto acceso = new AfeCCostoPuesto();
				acceso.mapeaReg(rs);
				lisAcceso.add(acceso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.afe.PuestoUtil|getListVacantes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAcceso;
	}
	
	public ArrayList<AfeCCostoPuesto> getListAsignados(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AfeCCostoPuesto> lisAcceso 	= new ArrayList<AfeCCostoPuesto>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{			
			comando = "SELECT ID,EJERCICIO_ID, CCOSTO_ID, PUESTO_ID, TURNO,DIAS," +
					" REQUISITOS, EMAIL, MAXIMO_HORAS, CLAVE, STATUS" +
					" FROM NOE.AFE_CCOSTO_PUESTO " +
					" WHERE ID IN (SELECT CCOSTO_PUESTO_ID FROM NOE.AFE_CONTRATO_ALUMNO WHERE STATUS = 'A') "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AfeCCostoPuesto acceso = new AfeCCostoPuesto();
				acceso.mapeaReg(rs);
				lisAcceso.add(acceso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.afe.PuestoUtil|getListAsignados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAcceso;
	}
	
}