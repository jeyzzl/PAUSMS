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

//import aca.bsc.CertCurso;

/**
 * @author Nio
 *
 */
public class CertCursoUtil {	
	
	public boolean insertReg(Connection conn, CertCurso curso) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CERT_CURSO"+ 
				"(CURSO_ID, PLAN_ID, CLAVE, CICLO_ID," +
				" CURSO_NOMBRE, FST, FSP, CREDITOS," +
				" ORDEN, TIPOCURSO_ID, CREDITOS2)"+
				" VALUES(?, ?, ?, TO_NUMBER(?, '99')," +
				" ?, ?, ?, ?," +
				" TO_NUMBER(?, '999')," +
				" TO_NUMBER(?,'99'),? )");
					
			ps.setString(1, curso.getCursoId());
			ps.setString(2, curso.getPlanId());
			ps.setString(3, curso.getClave());
			ps.setString(4, curso.getCicloId());
			ps.setString(5, curso.getCursoNombre());
			ps.setString(6, curso.getFst());
			ps.setString(7, curso.getFsp());
			ps.setString(8, curso.getCreditos());
			ps.setString(9, curso.getOrden());
			ps.setString(10, curso.getTipoCursoId());
			ps.setString(11, curso.getCreditos2());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertCurso|insertReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CertCurso curso) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CERT_CURSO"
					+ " SET PLAN_ID = ?,"
					+ " CLAVE = ?,"
					+ " CICLO_ID = TO_NUMBER(?, '99'),"
					+ " CURSO_NOMBRE = ?,"
					+ " FST = ?,"
					+ " FSP = ?,"
					+ " CREDITOS = ?,"
					+ " ORDEN = TO_NUMBER(?, '999'),"
					+ " TIPOCURSO_ID = TO_NUMBER(?, '99'),"
					+ " CREDITOS2 = ?"
					+ " WHERE CURSO_ID = ?");
			
			ps.setString(1, curso.getPlanId());
			ps.setString(2, curso.getClave());
			ps.setString(3, curso.getCicloId());
			ps.setString(4, curso.getCursoNombre());
			ps.setString(5, curso.getFst());
			ps.setString(6, curso.getFsp());
			ps.setString(7, curso.getCreditos());
			ps.setString(8, curso.getOrden());
			ps.setString(9, curso.getTipoCursoId());
			ps.setString(10, curso.getCreditos2());			
			ps.setString(11, curso.getCursoId());			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertCurso|updateReg|:"+ex);		
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String cursoId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CERT_CURSO"+ 
				" WHERE CURSO_ID = ?");
			
			ps.setString(1, cursoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertCurso|deleteReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}
	
	public String numCursos(Connection conn, String planId) throws SQLException{
		String cursos = "0";
		PreparedStatement ps = null;
		ResultSet rs		 = null;
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) AS CURSOS FROM CERT_CURSO WHERE PLAN_ID = ?");
			
			ps.setString(1, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				cursos = rs.getString("CURSOS");
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertCursoUtil|numCursos|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }			
		}
		return cursos;
	}
	
	public CertCurso mapeaRegId(Connection conn, String cursoId) throws SQLException{
		CertCurso curso = new CertCurso();
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT CURSO_ID, PLAN_ID, CLAVE, CICLO_ID," +
				" CURSO_NOMBRE, FST, FSP, CREDITOS, ORDEN, TIPOCURSO_ID, CREDITOS2" +
				" FROM ENOC.CERT_CURSO" + 
				" WHERE CURSO_ID = ?");
		
			ps.setString(1, cursoId);
		
			rs = ps.executeQuery();
			if (rs.next()){
				curso.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertCurso|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		
		return curso;		
	}
	
	public boolean existeReg(Connection conn, String cursoId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT PLAN_ID FROM ENOC.CERT_CURSO"+ 
				" WHERE CURSO_ID = ?");
			
			ps.setString(1, cursoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertCurso|existeReg|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		
		return ok;
	}
	
	public String maximoCurso(Connection conn, String planId) throws SQLException{		
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		String  s_maximo 		= "";
		
		try{
			ps = conn.prepareStatement("SELECT LTRIM(COALESCE(TO_CHAR(MAX(SUBSTR(CURSO_ID,10,3)+1),'000'),'000'))AS MAXIMO FROM ENOC.CERT_CURSO WHERE PLAN_ID = ?"); 
			ps.setString(1, planId);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				s_maximo = planId+"-"+rs.getString("MAXIMO").substring(0,3);
			}else{				
				s_maximo = planId + "-001";
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertCurso|maximoCurso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return s_maximo;
	}

	
	public static String numeroALetra(String nota) throws SQLException{		
		String  texto 	= "";		
		try{
			for(int i = 0; i < nota.length(); i++){
				switch(nota.charAt(i)){
					case '0':{texto += "cero ";}break;
					case '1':{texto += "uno ";}break;
					case '2':{texto += "dos ";}break;
					case '3':{texto += "tres ";}break;
					case '4':{texto += "cuatro ";}break;
					case '5':{texto += "cinco ";}break;
					case '6':{texto += "seis ";}break;
					case '7':{texto += "siete ";}break;
					case '8':{texto += "ocho ";}break;
					case '9':{texto += "nueve ";}break;
					case 'A':{texto += "* Acreditada";}break;
					case '.':{texto += "punto ";}break;
					case 'C':{texto += "";}break;
					case 'c':{texto += "";}break;
					case '*':{texto += "";}break;
					default:{						
						texto = "Error";					
					}
					break;
				}
			}
			if (nota.equals("10.0")) texto = "diez punto cero";
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertCurso|maximoCurso|:"+ex);
		}	
		return texto;
	}
	
	public ArrayList<CertCurso> getListPlan(Connection conn, String planId, String orden ) throws SQLException{
		
		ArrayList<CertCurso> lisCursos	= new ArrayList<CertCurso>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando	= "";
		
		try{
			comando = "SELECT PLAN_ID, CURSO_ID, CLAVE, CICLO_ID, CURSO_NOMBRE,"+
				" FST, FSP, CREDITOS, ORDEN, TIPOCURSO_ID, CREDITOS2" +
				" FROM ENOC.CERT_CURSO WHERE PLAN_ID = '"+planId+"' "+ orden;			 
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CertCurso cp = new CertCurso();
				cp.mapeaReg(rs);
				lisCursos.add(cp);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertCursoUtil|getListPlan|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(st!=null) st.close();
		}
		
		return lisCursos;
	}
}