/**
 * 
 */
package aca.cert;

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
public class CertPlanUtil {
	
	
	public boolean insertReg(Connection conn, CertPlan plan) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CERT_PLAN"+ 
				"(PLAN_ID, FACULTAD, CARRERA, NUM_CURSOS," +
				" SEMANAS, T_INICIAL, T_FINAL, NOTA," +
				" PIE, CLAVE, FST, FSP," +
				" COMPONENTE, CURSO, RVOE, FECHARETRO, TITULO1, TITULO2, TITULO3, CREDITOS)"+
				" VALUES(?, ?, ?, TO_NUMBER(?, '999')," +
				" TO_NUMBER(?, '99'), ?, ?, ?," +
				" ?, ?, ?, ?," +
				" TO_NUMBER(?, '99'), ?, ?," +
				" TO_DATE(?,'DD/MM/YYYY'), ?, ?, ?, ?)");
					
			ps.setString(1,  plan.getPlanId());
			ps.setString(2,  plan.getFacultad());
			ps.setString(3,  plan.getCarrera());
			ps.setString(4,  plan.getNumCursos());
			ps.setString(5,  plan.getSemanas());
			ps.setString(6,  plan.getTInicial());
			ps.setString(7,  plan.getTFinal());
			ps.setString(8,  plan.getNota());
			ps.setString(9,  plan.getPie());
			ps.setString(10, plan.getClave());
			ps.setString(11, plan.getFst());
			ps.setString(12, plan.getFsp());
			ps.setString(13, plan.getComponente());
			ps.setString(14, plan.getCurso());
			ps.setString(15, plan.getRvoe());
			ps.setString(16, plan.getFechaRetro());
			ps.setString(17, plan.getTitulo1());
			ps.setString(18, plan.getTitulo2());
			ps.setString(19, plan.getTitulo3());
			ps.setString(20, plan.getCreditos());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertPlan|insertReg|:"+ex);
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CertPlan plan) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CERT_PLAN"+ 
				" SET FACULTAD = ?," +
				" CARRERA = ?," +
				" NUM_CURSOS = TO_NUMBER(?, '999')," +
				" SEMANAS = TO_NUMBER(?, '99')," +
				" T_INICIAL = ?," +
				" T_FINAL = ?," +
				" NOTA = ?," +
				" PIE = ?," +
				" CLAVE = ?," +
				" FST = ?," +
				" FSP = ?," +
				" COMPONENTE = TO_NUMBER(?, '99')," +
				" CURSO = ?," +
				" RVOE = ?," +
				" FECHARETRO = TO_DATE(?,'DD/MM/YYYY'), TITULO1=?, TITULO2=?, TITULO3=?, CREDITOS=? "+
				" WHERE PLAN_ID = ?");
			
			
			ps.setString(1,  plan.getFacultad());
			ps.setString(2,  plan.getCarrera());
			ps.setString(3,  plan.getNumCursos());
			ps.setString(4,  plan.getSemanas());
			ps.setString(5,  plan.getTInicial());
			ps.setString(6,  plan.getTFinal());
			ps.setString(7,  plan.getNota());
			ps.setString(8,  plan.getPie());
			ps.setString(9, plan.getClave());
			ps.setString(10, plan.getFst());
			ps.setString(11, plan.getFsp());
			ps.setString(12, plan.getComponente());
			ps.setString(13, plan.getCurso());
			ps.setString(14, plan.getRvoe());
			ps.setString(15, plan.getFechaRetro());
			ps.setString(16, plan.getTitulo1());
			ps.setString(17, plan.getTitulo2());
			ps.setString(18, plan.getTitulo3());
			ps.setString(19, plan.getCreditos());
			ps.setString(20,  plan.getPlanId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertPlan|updateReg|:"+ex);		
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String planId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CERT_PLAN"+ 
				" WHERE PLAN_ID = ?");
			
			ps.setString(1, planId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertPlan|deleteReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}
	
	public CertPlan mapeaRegId(Connection conn, String planId) throws SQLException{
		CertPlan plan = new CertPlan();
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT PLAN_ID, FACULTAD, CARRERA, NUM_CURSOS," +
				" SEMANAS, T_INICIAL, T_FINAL, NOTA," +
				" PIE, CLAVE, FST, FSP," +
				" COMPONENTE, CURSO, RVOE, TO_CHAR(FECHARETRO,'DD/MM/YYYY') AS FECHARETRO, TITULO1, TITULO2, TITULO3, CREDITOS" +
				" FROM ENOC.CERT_PLAN" + 
				" WHERE PLAN_ID = ?");
		
			ps.setString(1, planId);
		
			rs = ps.executeQuery();
			if (rs.next()){
				plan.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertPlan|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		
		return plan;
	}
	
	public boolean existeReg(Connection conn, String planId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps = null;
		
		try{
			ps = conn.prepareStatement("SELECT FACULTAD FROM ENOC.CERT_PLAN"+ 
				" WHERE PLAN_ID = ?");
			
			ps.setString(1, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertPlan|existeReg|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		
		return ok;
	}
	
	
	public ArrayList<CertPlan> getListAll(Connection conn, String facultad, String orden ) throws SQLException{
		
		ArrayList<CertPlan> lisPlan	= new ArrayList<CertPlan>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando	= "";
		
		try{
			comando = "SELECT PLAN_ID, FACULTAD, CARRERA, NUM_CURSOS," +
				" SEMANAS, T_INICIAL, T_FINAL, NOTA," +
				" PIE, CLAVE, FST, FSP, COMPONENTE, CURSO, RVOE, TO_CHAR(FECHARETRO,'DD/MM/YYYY') AS FECHARETRO, TITULO1, TITULO2, TITULO3, CREDITOS " +
				" FROM ENOC.CERT_PLAN WHERE FACULTAD='"+ facultad +"' "+ orden;	 
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CertPlan cp = new CertPlan();
				cp.mapeaReg(rs);
				lisPlan.add(cp);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertPlanUtil|getListAll|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(st!=null) st.close();
		}
		
		return lisPlan;
	}
	
	public ArrayList<CertPlan> listFacultad(Connection conn, String facultadId, String orden ) throws SQLException{
		
		ArrayList<CertPlan> lisPlan	= new ArrayList<CertPlan>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando	= "";
		
		try{
			comando = " SELECT PLAN_ID, FACULTAD, CARRERA, NUM_CURSOS,SEMANAS, T_INICIAL, T_FINAL, NOTA,PIE, CLAVE, FST, FSP,"
					+ " COMPONENTE, CURSO, RVOE, TO_CHAR(FECHARETRO,'DD/MM/YYYY') AS FECHARETRO, TITULO1, TITULO2, TITULO3, CREDITOS"
					+ " FROM ENOC.CERT_PLAN"
					+ " WHERE ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)) = '"+facultadId+"' "+ orden;	 
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CertPlan cp = new CertPlan();
				cp.mapeaReg(rs);
				lisPlan.add(cp);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertPlanUtil|listFacultad|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(st!=null) st.close();
		}
		
		return lisPlan;
	}
	
	public static String getRvoePlan(Connection conn, String planId) throws SQLException{
		PreparedStatement ps 	= null;
		ResultSet 		rs		= null;
		String rvoe				= "RVOE";		
		
		try{
			ps = conn.prepareStatement("SELECT RVOE FROM ENOC.CERT_PLAN WHERE PLAN_ID = ?"); 
			
			ps.setString(1, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				rvoe = rs.getString("RVOE");		
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertPlan|existeReg|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		
		return rvoe;
	}
	
	public static ArrayList<String> getFrases(String parrafo) throws SQLException{
		ArrayList<String> frases = new ArrayList<String>();
		
		try{
			String tmp = "";
			int contA = 0;
			int contB = 0;
			
			for(int i = 0; i < parrafo.length(); i++){//Recorre la parrafo completa
				if (i == parrafo.length()-1){
				   tmp += String.valueOf(parrafo.charAt(i));//concatena caracteres(uno por uno)
				   frases.add(tmp);
				}
				tmp += String.valueOf(parrafo.charAt(i));
				
				if(String.valueOf(parrafo.charAt(i)).equals("$")){//verifica cuando se llegue a un $
					frases.add(tmp.substring(0,tmp.length() - 1));//guarda el texto anterior al $ en la lista
					tmp = String.valueOf(parrafo.charAt(i)); //guarda solo el ultimo caracter leido
					
					for(int j = i + 1; j < parrafo.length(); j++){//recorre el texto que hay entre $
						tmp += String.valueOf(parrafo.charAt(j));//
						if(String.valueOf(parrafo.charAt(j)).equals("$")){
							frases.add(tmp);//agrega a la lista el texto entre $
							tmp = "";
							i = j;
							break;
						}
					}
				}
				
				if(String.valueOf(parrafo.charAt(i)).equals("<")){//verifica cuando llegue a un <
					if(contA == 0){
						frases.add(tmp.substring(0,tmp.length() - 1));//guarda el texto anterior al < en la lista
						tmp = String.valueOf(parrafo.charAt(i)); //guarda solo el ultimo caracter leido
					}
					contA++;
				}
				if(String.valueOf(parrafo.charAt(i)).equals(">")){//verifica cuando llegue a un <
					contB++;
					if(contA == 2 && contB == 2){
						frases.add(tmp);
						contA = 0;
						contB = 0;
						tmp = "";
					}
				}
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertPlan|getFrases|:"+ex);
		}
		
		return frases;
	}
	
}