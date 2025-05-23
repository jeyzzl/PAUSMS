//Clase  para la tabla Materias_Insc
package aca.reportes;

import java.sql.*;
import java.util.ArrayList;

import aca.reportes.MateriasInsc;

public class MateriasInscUtil{
	
	public ArrayList<MateriasInsc> getListOne(Connection conn, String orden ) throws SQLException{
			
		ArrayList<MateriasInsc> lisOne		= new ArrayList<MateriasInsc>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, '1' AS NIVEL, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||', '||NOMBRE AS NOMBRE,"
					+ " CASE CLAS_FIN WHEN 1 THEN 'ACFE' WHEN 2 THEN 'NO ACFE' ELSE '-' END, PLAN_ID, ENOC.ALUM_NUM_MAT(CODIGO_PERSONAL,CARGA_ID), "
					+ " ENOC.ALUM_CRED_CARGA(CODIGO_PERSONAL,CARGA_ID), APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS ORDEN"
					+ " FROM ENOC.INSCRITOS"
					+ " WHERE MODALIDAD_ID = 7 "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MateriasInsc catalogo = new MateriasInsc();
				catalogo.mapeaReg(rs);
				lisOne.add(catalogo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.reportes.MateriasInscUtil|getListOne|:"+ex);
		}
		
		try { rs.close(); } catch (Exception ignore) { }
		try { st.close(); } catch (Exception ignore) { }		
		
		return lisOne;
	}
	
	public ArrayList<MateriasInsc> getListTwo(Connection conn, String orden ) throws SQLException{
		
		ArrayList<MateriasInsc> lisTwo		= new ArrayList<MateriasInsc>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, '2' AS NIVEL, ENOC.NOMBRE_MATERIA(CURSO_ID), ENOC.CREDITOS(CURSO_ID),	ENOC.CURSO_PLAN(CURSO_ID), ENOC.ALUM_APELLIDO(CODIGO_PERSONAL) FROM ENOC.KRDX_CURSO_ACT WHERE SUBSTR(CURSO_CARGA_ID,1,6) LIKE '05065D%' AND TIPOCAL_ID NOT IN ('M')"+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MateriasInsc catalogo = new MateriasInsc();
				catalogo.mapeaReg(rs);
				lisTwo.add(catalogo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.reportes.MateriasInscUtil|getListTwo|:"+ex);
		}
		
		try { rs.close(); } catch (Exception ignore) { }
		try { st.close(); } catch (Exception ignore) { }		
		
		return lisTwo;
	}
	
	
	
}