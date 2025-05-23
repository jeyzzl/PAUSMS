/*
 * Created on May 19, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package aca.kardex;
import java.sql.*;
import java.util.ArrayList;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ConvalidarUtil {
	
		
	// Planes de estudio del alumno que ya ha cursado alguna vez
	
	public ArrayList<ConvalidarPlan> getListPlan(Connection con, String matricula) throws SQLException{
		
		ArrayList<ConvalidarPlan> lis				= new ArrayList<ConvalidarPlan>();
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String comando			= "";
		
		try{
			
			comando = "SELECT ALP.PLAN_ID AS PLAN_ID, ACP.NOMBRE_PLAN AS NOMBRE_PLAN " +
  					  "FROM ENOC.ALUM_PLAN ALP, ENOC.MAPA_PLAN ACP  " + 
 					  "WHERE CODIGO_PERSONAL = ?" +
					  "AND ALP.ESTADO = '0'  " +
					  "AND ALP.PLAN_ID = ACP.PLAN_ID " + 
					  "ORDER BY 2 ";
			
			ps = con.prepareStatement(comando);
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			while (rs.next()){
				lis.add(new ConvalidarPlan(rs.getString("PLAN_ID"), rs.getString("NOMBRE_PLAN")));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ConvalidarUtil|getListPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }	
			if (ps!=null) rs.close();
		}
		
		return lis;
		}
	
	
	public ArrayList<String> getListClaveComprar(Connection con, String matricula, String planId) throws SQLException{
		
		ArrayList<String> lis				= new ArrayList<String>();
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String comando			= "";
		
		try{
			
			comando = "SELECT SUBSTR(CURSO_ID,9,7) CLAVE " +
					"FROM ENOC.ALUMNO_CURSO  " +
					"WHERE CODIGO_PERSONAL = ? " + 
					"AND ENOC.CURSO_PLAN(CURSO_ID) = ? " + 
					"AND TIPOCAL_ID = '1' " +
					"AND (NOTA > 69 OR NOTA_EXTRA>69) " +
					"ORDER BY CICLO , NOMBRE_CURSO";
								
			ps = con.prepareStatement(comando);
			ps.setString(1,matricula);
			ps.setString(2,planId);
			rs = ps.executeQuery();
			while (rs.next()){
				lis.add(rs.getString("CLAVE"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ConvalidarUtil|getListClaveComprar|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }	
			if (ps!=null) rs.close();
		}
		
		return lis;
	}
	
	//Materias que el alumno ya curso en otro plan de estudio y que tiene calicacion aprovatoria
	
	public ArrayList<ConvalidarMaterias> getListMateriasCursadas(Connection con, String matricula, String planId) throws SQLException{
		
		ArrayList<ConvalidarMaterias> lis				= new ArrayList<ConvalidarMaterias>();
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String comando			= "";
		
		try{
			
			comando ="SELECT CURSO_ID ,SUBSTR(CURSO_ID,9,7) AS CLAVE," +
				" NOMBRE_CURSO, TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_CREADA," +
				" COALESCE((CASE WHEN NOTA>69 THEN NOTA ELSE NOTA_EXTRA END),0) AS CAL, CICLO, CONVALIDACION" +
				" FROM ENOC.ALUMNO_CURSO" +
				" WHERE CODIGO_PERSONAL = ?" + 
				" AND TIPOCAL_ID = '1' " +
				" AND (NOTA > 69 OR NOTA_EXTRA>69)"+
				" AND ENOC.CURSO_PLAN(CURSO_ID) = ?" + 
				" ORDER BY CICLO,NOMBRE_CURSO ";
			
			ps = con.prepareStatement(comando);
			ps.setString(1,matricula);
			ps.setString(2,planId);
			rs = ps.executeQuery();
			while (rs.next()){				
				lis.add(new ConvalidarMaterias(rs.getString("CURSO_ID"), rs.getString("CLAVE"), rs.getString("NOMBRE_CURSO"), rs.getString("F_CREADA"), rs.getInt("CICLO"),rs.getInt("CAL"), rs.getString("CONVALIDACION")));				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ConvalidarUtil|getListMateriasCursadas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }	
			if (ps!=null) rs.close();
		}
		
		return lis;
	}
	
	// materias actuales del plan de estudios del alumno que no a aprovado de estudios actual 
	
	public ArrayList<ConvalidarMaterias> getListMateriasActuales(Connection con, String matricula, String planId) throws SQLException{
		
		ArrayList<ConvalidarMaterias> lis				= new ArrayList<ConvalidarMaterias>();
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String comando			= "";
		
		try{
			
			comando = "SELECT CURSO_ID, SUBSTR(CURSO_ID,9,7) AS CLAVE, NOMBRE_CURSO, CICLO" +
					" FROM ENOC.MAPA_CURSO" + 
					" WHERE PLAN_ID = ?" +
					" AND CURSO_ID NOT IN (SELECT CURSO_ID" +
					" FROM ENOC.ALUMNO_CURSO" +
					" WHERE CODIGO_PERSONAL = ?" +
					" AND TIPOCAL_ID = '1' " +
					" AND (NOTA > 69 OR NOTA_EXTRA>69))" +
					" ORDER BY 4,3";
			
			ps = con.prepareStatement(comando);
			ps.setString(1,planId);
			ps.setString(2,matricula);			
			rs = ps.executeQuery();
			while (rs.next()){				
				lis.add(new ConvalidarMaterias(rs.getString("CURSO_ID"), rs.getString("CLAVE"), rs.getString("NOMBRE_CURSO"), "01/01/1950", rs.getInt("CICLO"),0,""));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ConvalidarUtil|getListMateriasActuales|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }	
			if (ps!=null) rs.close();
		}
		
		return lis;
	}
	
/*	
	public static void main(String args[]){
		try{
			
			Connection con = null;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.14:1521:ora1", "enoc", "caminacondios");
			
			ArrayList lis = new ArrayList();
			ConvalidarUtil cUtil = new ConvalidarUtil();
			
			//lis = cUtil.getListPlan(con, "0981091");
			
			lis = cUtil.getListClaveComprar( con,  "0981091", "ISC1992*");
			
			System.out.println("Tamano del listor " + lis.size());
			for(int i=0; i < lis.size(); i++){
				//Convalidar conv = (Convalidar) lis.get(i);
				//System.out.println(conv.planId + " " + conv.nombre);
				
				System.out.println("clave " + lis.get(i));
			}
			
			lis = null;
			lis = cUtil.getListMateriasActuales(con, "0981091", "LASC2000");
			
			for(int i=0; i< lis.size(); i++){
				ConvalidarMaterias cMat = (ConvalidarMaterias) lis.get(i);
				System.out.println("cursoId " + cMat.cursoId + " -- Clave -- " + 
									cMat.clave +" -- Nombre -- "+ 
									cMat.nombre + " -- Ciclo -- "+ 
									cMat.ciclo + " -- Nota -- "+ cMat.nota );
				
			}
			
			
			lis = null;
			lis = cUtil.getListMateriasCursadas(con, "0981091", "ISC1992*");
			System.out.println("******************************************************************");
			for(int i=0; i< lis.size(); i++){
				ConvalidarMaterias cMat = (ConvalidarMaterias) lis.get(i);
				System.out.println("cursoId " + cMat.cursoId + " -- Clave -- " + 
						cMat.clave +" -- Nombre -- "+ 
						cMat.nombre + " -- Ciclo -- "+ 
						cMat.ciclo + " -- Nota -- "+ cMat.nota );
				
			}
			
			con.commit();
			con.close();
			
		}
		
		catch(Exception e){
			System.out.println(e);
		}
		
	}
*/
}