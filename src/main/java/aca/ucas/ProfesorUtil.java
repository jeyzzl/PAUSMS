package aca.ucas;

import java.sql.*;
import java.util.*;

	
public class ProfesorUtil {
	
	public ArrayList<Profesor> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<Profesor> lisProfesor = new ArrayList<Profesor>();
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando = "";
		try{
			comando="SELECT CODIGO_PERSONAL, FOLIO, FACULTAD_ID, CARRERA_ID, "+
					"STATUS FROM ENOC.UCA_PROFESOR "+orden; 
			rs= st.executeQuery(comando);
			while(rs.next()){
				Profesor profe = new Profesor();
				profe.mapeaReg(rs);
				lisProfesor.add(profe);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ucas.ProfesorUtil|getListAll|:"+ex);
		}finally{
			if(rs!=null)rs.close();
			if(st!=null)st.close();
		}
		return lisProfesor;
	}

	public ArrayList<Profesor> getEmpleados(Connection conn, String orden) throws SQLException{
		
		ArrayList<Profesor> lisProfesor = new ArrayList<Profesor>();
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando = "";
		try{
			comando="select codigo_personal "+
			"from ENOC.ALUM_PERSONAL where codigo_personal like '98%' "+orden; 
			rs= st.executeQuery(comando);
			while(rs.next()){
				Profesor profe = new Profesor();
				profe.setCodigoPersonal(rs.getString(1));
				lisProfesor.add(profe);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ucas.ProfesorUtil|getEmpleados|:"+ex);
		}finally{
			if(rs!=null)rs.close();
			if(st!=null)st.close();
		}
		return lisProfesor;
	}

	public ArrayList<Profesor> getListAll(Connection conn, String status, String orden) throws SQLException{
		
		ArrayList<Profesor> lisProfesor = new ArrayList<Profesor>();
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando = "";
		try{
			comando="SELECT CODIGO_PERSONAL, FOLIO, FACULTAD_ID, CARRERA_ID, "+
					"STATUS FROM ENOC.UCA_PROFESOR WHERE STATUS = '"+status+"' "+orden; 
			rs= st.executeQuery(comando);
			while(rs.next()){
				Profesor profe = new Profesor();
				profe.mapeaReg(rs);
				lisProfesor.add(profe);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ucas.ProfesorUtil|getListAll|:"+ex);
		}finally{
			if(rs!=null)rs.close();
			if(st!=null)st.close();
		}
		return lisProfesor;
	}
	
	public ArrayList<Profesor> getLista(Connection conn, String codigoPersonal, String orden) throws SQLException{
		
		ArrayList<Profesor> lisProfesor = new ArrayList<Profesor>();
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando = "";
		try{
			comando="SELECT CODIGO_PERSONAL, FOLIO, FACULTAD_ID, CARRERA_ID, "+
					"STATUS FROM ENOC.UCA_PROFESOR WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+orden; 
			rs= st.executeQuery(comando);
			while(rs.next()){
				Profesor profe = new Profesor();
				profe.mapeaReg(rs);
				lisProfesor.add(profe);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ucas.ProfesorUtil|getLista|:"+ex);
		}finally{
			if(rs!=null)rs.close();
			if(st!=null)st.close();
		}
		return lisProfesor;
	}
	
	public ArrayList<Profesor> getListCarrera(Connection conn, String carreraId, String orden) throws SQLException{
		
		ArrayList<Profesor> lisProfesor = new ArrayList<Profesor>();
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando = "";
		try{
			comando="SELECT CODIGO_PERSONAL, FOLIO, FACULTAD_ID, CARRERA_ID, "+
					"STATUS FROM ENOC.UCA_PROFESOR WHERE CARRERA_ID = '"+carreraId+"' "+ 
					"AND STATUS = 'P'"+orden;
			rs= st.executeQuery(comando);
			while(rs.next()){
				Profesor profe = new Profesor();
				profe.mapeaReg(rs);
				lisProfesor.add(profe);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ucas.ProfesorUtil|getListCarrera|:"+ex);
		}finally{
			if(rs!=null)rs.close();
			if(st!=null)st.close();
		}
		return lisProfesor;
	}
	
	public ArrayList<Profesor> getAcceso(Connection conn, String codigoPersonal) throws SQLException{
		
		ResultSet rs 		= null;
		Statement st 		= conn.createStatement();
		ArrayList<Profesor> lisProfesor 	= new ArrayList<Profesor>();
		String ac 			= "";
		String carrera		= "'";
		String Comando		= "";
		
		try{
			Comando="SELECT ACCESOS FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"; 
			rs= st.executeQuery(Comando);		
			if(rs.next()){
				ac = rs.getString("ACCESOS");
				StringTokenizer tok = new StringTokenizer(ac," ");
				while(tok.hasMoreTokens()){
					carrera=tok.nextToken();
					Comando="SELECT CODIGO_PERSONAL, FOLIO, FACULTAD_ID, CARRERA_ID, "+
					"STATUS FROM ENOC.UCA_PROFESOR WHERE CARRERA_ID = '"+carrera+"' "+ 
					"AND STATUS = 'P' ORDER BY EMP_NOMBRE(CODIGO_PERSONAL) ";
					rs=st.executeQuery(Comando);
					while(rs.next()){
						Profesor profe = new Profesor();
						profe.mapeaReg(rs);
						lisProfesor.add(profe);
					}
				}
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ucas.ProfesorUtil|getAcceso|:"+ex);
		}finally{
			if(rs!=null)rs.close();
			if(st!=null)st.close();
		}
		
		return lisProfesor;
	}
		
/*
	public ArrayList getListAcceso(Connection conn, String codigoPersonal) throws SQLException{
		String ac 				= "";
		String st2				= "'";
		String Comando			= "";
		ResultSet rs 			= null;
		ResultSet rs2 			= null;
		PreparedStatement ps	= null;
		Statement sx = conn.createStatement();
		ArrayList Listprofe = new ArrayList();
		
		
		
		try{
			ps = conn.prepareStatement("SELECT ACCESOS FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ?"); 
			ps.setString(1, codigoPersonal);
			rs= ps.executeQuery();		
			if(rs.next()){
				ac = rs.getString("ACCESOS");
				StringTokenizer st = new StringTokenizer(ac," ");
				while(st.hasMoreTokens()){
					st2=st.nextToken();
					Comando= "SELECT CODIGO_PERSONAL, FOLIO, FACULTAD_ID, CARRERA_ID, "+
					"STATUS FROM ENOC.UCA_PROFESOR WHERE CARRERA_ID = '"+st2+"' "+ 
					"AND STATUS = 'P' ORDER BY 3,4,1 ";
					rs2=sx.executeQuery(Comando);
					while(rs2.next()){
						Profesor profe = new Profesor();
						profe.mapeaReg(rs2);
						Listprofe.add(profe);
					}
				}
			}
		}catch(Exception ex){
			System.out.println("Error: "+ex);
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }
		
		return Listprofe;
	}
		
*/
}