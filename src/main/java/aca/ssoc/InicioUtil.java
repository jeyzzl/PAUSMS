//Clase  para la tabla SSOC_INICIO
package aca.ssoc;

import java.sql.*;
import java.util.ArrayList;

public class InicioUtil {
	
	public ArrayList<Inicio> getListAll(Connection conn, String orden ) throws SQLException{
		ArrayList<Inicio> lisInicio 			= new ArrayList<Inicio>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, PLAN_ID, FECHA , PORCENTAJE, SEMESTRE FROM ENOC.SSOC_INICIO " +orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				Inicio inicio = new Inicio();
				inicio.mapeaReg(rs);
				lisInicio.add(inicio);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.InicioUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInicio;
	}
	
	public ArrayList<Inicio> getListAlumConDoc(Connection conn, String documentoId, String orden ) throws SQLException{
		ArrayList<Inicio> lisInicio 			= new ArrayList<Inicio>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, PLAN_ID, TO_CHAR(FECHA ,'DD/MM/YYYY') AS FECHA, PORCENTAJE, SEMESTRE" +
					  " FROM ENOC.SSOC_INICIO " + 
					  " WHERE CODIGO_PERSONAL||PLAN_ID IN " +
				 	  "    (SELECT CODIGO_PERSONAL||PLAN_ID " +
				 	  "		FROM ENOC.SSOC_DOCALUM WHERE DOCUMENTO_ID = TO_NUMBER('"+documentoId+"','99') " + 
				 	  "		AND ENTREGADO = 'S' ) "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				Inicio inicio = new Inicio();
				inicio.mapeaReg(rs);
				lisInicio.add(inicio);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.InicioUtil|getListAlumConDoc|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInicio;
	}
	
	public ArrayList<Inicio> getListInscrito(Connection conn, String orden ) throws SQLException{
		ArrayList<Inicio> lisInicio 			= new ArrayList<Inicio>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = " SELECT DISTINCT(CODIGO_PERSONAL), PLAN_ID , TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA , PORCENTAJE, SEMESTRE  " +
					  " FROM ENOC.SSOC_INICIO WHERE CODIGO_PERSONAL IN " + 
					  "(SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS ) " +orden;
			
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				Inicio inicio = new Inicio();
				inicio.mapeaReg(rs);
				lisInicio.add(inicio);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.InicioUtil|getListInscrito|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInicio;
	}
	
	

	public ArrayList<Inicio> getListActivo(Connection conn, String orden ) throws SQLException{
		ArrayList<Inicio> lisAlumAct = new ArrayList<Inicio>();
		Statement st 				 = conn.createStatement();
		ResultSet rs 				 = null;
		String comando				 = "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, PLAN_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, PORCENTAJE, SEMESTRE " +
					  " FROM ENOC.SSOC_INICIO WHERE CODIGO_PERSONAL||PLAN_ID IN " + 
						" (SELECT CODIGO_PERSONAL||PLAN_ID FROM ENOC.SSOC_DOCALUM)" + 
						" AND CODIGO_PERSONAL||PLAN_ID NOT IN" +
						"   (SELECT CODIGO_PERSONAL||PLAN_ID FROM ENOC.SSOC_DOCALUM " + 
						"    WHERE DOCUMENTO_ID IN (21,22,23)) " +orden;
			
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				Inicio inicio = new Inicio();
				inicio.mapeaReg(rs);
				lisAlumAct.add(inicio);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.InicioUtil|getListActivo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumAct;
	}
}