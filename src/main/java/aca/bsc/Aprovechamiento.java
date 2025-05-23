/*
 * Created on 24-feb-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.bsc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Carlos
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Aprovechamiento {
	public static ArrayList<String> getCargas(Connection conn) throws SQLException{
		ArrayList<String> listor = new ArrayList<String>();
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando="";
		try {
			comando = "SELECT CARGA_ID, NOMBRE_CARGA "+ 
					"FROM ENOC.CARGA "+ 
					"WHERE CARGA_ID IN "+ 
						"(SELECT DISTINCT(SUBSTR(CURSO_CARGA_ID,1,6)) FROM ENOC.KRDX_CURSO_ACT WHERE TIPOCAL_ID != 'M') "+ 
					"ORDER BY 1 DESC";
			rs = st.executeQuery (comando);
			while (rs.next()){
				listor.add(rs.getString(1));
				listor.add(rs.getString(2));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bsc.Aprolishamiento|getCargas|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return listor;
		
	}
	
	public static double promedioCarga(Connection conn, String cargaId) throws SQLException{
		double total=0;
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando="";
		try {
			// CONSIDERA SOLO TIPO CALIFICACIONES AC, NA Y RA.
			comando = "SELECT" +
					" CASE COALESCE(A.NOTA_EXTRA,0) WHEN 0 THEN COALESCE(A.NOTA,0) ELSE COALESCE(A.NOTA_EXTRA,0) END," +
					" CASE B.CREDITOS WHEN 0 THEN 1 ELSE B.CREDITOS END "+  
				" FROM ENOC.KRDX_CURSO_ACT A, ENOC.MAPA_CURSO B"+ 
				" WHERE SUBSTR(CURSO_CARGA_ID,1,6)='"+cargaId+"'"+
				" AND A.CURSO_ID = B.CURSO_ID"+
				" AND TIPOCAL_ID IN('1','2','4')" +
				" AND B.TIPOCURSO_ID IN (1,2,7)";
			rs = st.executeQuery (comando);
			int cr=0, n=0,sc=0,sn=0;
			while (rs.next()){
				cr = rs.getInt(2);
				n = rs.getInt(1);
				sc+=cr;
				sn+= cr*n;
			}
			if (sc>0){
				total = ((double)sn )/((double)sc);
				total = ((double)(int)((total+0.005)*100))/100;
			}else 
				total = -1;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.bsc.Aprolishamiento|promedioCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return total;
	}
	
	public static double promedioFacultad(Connection conn, String cargaId, String facultadId) throws SQLException{
		double total=0;
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando="";
		try {
			comando = "SELECT" +
					" CASE COALESCE(A.NOTA_EXTRA,0) WHEN 0 THEN COALESCE(A.NOTA,0) ELSE COALESCE(A.NOTA_EXTRA,0) END," +
					" CASE B.CREDITOS WHEN 0 THEN 1 ELSE B.CREDITOS END "+ 
				" FROM ENOC.KRDX_CURSO_ACT A, ENOC.MAPA_CURSO B"+ 
				" WHERE SUBSTR(CURSO_CARGA_ID,1,6)='"+cargaId+"'"+
				" AND A.CURSO_ID = B.CURSO_ID"+
				" AND ENOC.FACULTAD(ENOC.CARRERA(B.PLAN_ID))= '"+facultadId+"'"+
				" AND TIPOCAL_ID IN('1','2','4')" +
				" AND B.TIPOCURSO_ID IN (1,2,7)";
			rs = st.executeQuery (comando);
			int cr=0, n=0,sc=0,sn=0;
			while (rs.next()){
				cr = rs.getInt(2);
				n = rs.getInt(1);
				sc+=cr;
				sn+= cr*n;
			}
			if (sc>0){
				total = ((double)sn )/((double)sc);
				total = ((double)(int)((total+0.005)*100))/100;
			}else total = -1;
			//System.out.println("Facultad:"+facultadId+"-SN:"+sn+"-SC:"+sc+"-Prom:"+total);
		}catch(Exception ex){
			System.out.println("Error - aca.bsc.Aprolishamiento|promedioFacultad|:"+ex);
		}finally{	
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}
	public static double promedioCarrera(Connection conn, String cargaId,String carreraId) throws SQLException{
		double total=0;
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando="";
		try {
			comando = "SELECT" +
					" CASE COALESCE(A.NOTA_EXTRA,0) WHEN 0 THEN COALESCE(A.NOTA,0) ELSE COALESCE(A.NOTA_EXTRA,0) END," +
					" CASE B.CREDITOS WHEN 0 TEHN 1 ELSE B.CREDITOS END "+ 
				" FROM ENOC.KRDX_CURSO_ACT A, ENOC.MAPA_CURSO B"+ 
				" WHERE SUBSTR(A.CURSO_CARGA_ID,1,6)='"+cargaId+"'"+
				" AND A.CURSO_ID = B.CURSO_ID"+
				" AND ENOC.CARRERA(B.PLAN_ID)= '"+carreraId+"'"+
				" AND TIPOCAL_ID IN('1','2','4')" +					
				" AND B.TIPOCURSO_ID IN (1,2,7)"; 
			rs = st.executeQuery (comando);
			int cr=0, n=0,sc=0,sn=0;
			while (rs.next()){
				cr = rs.getInt(2);
				n = rs.getInt(1);
				sc+=cr;
				sn+= cr*n;
			}
			if (sc>0){
				total = ((double)sn )/((double)sc);
				total = ((double)(int)((total+0.005)*100))/100;
			}else total = -1;
		}catch(Exception ex){
			System.out.println("Error - aca.bsc.Aprovechamiento|promedioCarrera|:"+ex);
		}finally{	
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}
	public static double promedioMaestro(Connection conn, String cargaId, String carreraId, String maestro) throws SQLException{
		double total=0;
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando="";
		try {
			comando = "SELECT" +
					" CASE COALESCE(A.NOTA_EXTRA,0) WHEN 0 THEN COALESCE(A.NOTA,0) ELSE COALESCE(A.NOTA_EXTRA,0) END," +
					" CASE B.CREDITOS WHEN 0 THEN 1 ELSE B.CREDITOS END "+ 
				" FROM ENOC.KRDX_CURSO_ACT A, ENOC.MAPA_CURSO B, ENOC.CARGA_GRUPO C "+ 
				" WHERE SUBSTR(A.CURSO_CARGA_ID,1,6)='"+cargaId+"'"+
				" AND A.CURSO_ID = B.CURSO_ID"+
				" AND ENOC.CARRERA(B.PLAN_ID)= '"+carreraId+"'"+
				" AND A.CURSO_CARGA_ID = C.CURSO_CARGA_ID "+ 
				" AND C.CODIGO_PERSONAL = '"+maestro+"' "+
				" AND TIPOCAL_ID IN('1','2','4')" +					
				" AND B.TIPOCURSO_ID IN (1,2,7)";			
			
			rs = st.executeQuery (comando);
			int cr=0, n=0,sc=0,sn=0;
			while (rs.next()){
				cr = rs.getInt(2);
				n = rs.getInt(1);
				sc+=cr;
				sn+= cr*n;
			}
			if (sc>0){
				total = ((double)sn )/((double)sc);
				total = ((double)(int)((total+0.005)*100))/100;
			}else total = -1;
		}catch(Exception ex){
			System.out.println("Error - aca.bsc.Aprovechamiento|promedioMaestro|:"+ex);
			total = -1;
		}finally{	
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}
	public static double promedioMateria(Connection conn, String cargaId,String carreraId, String cursoCargaId) throws SQLException{
		double total=0;
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando="";
		try {
			comando = "SELECT" +
					" CASE COALESCE(A.NOTA_EXTRA,0) WHEN 0 THEN COALESCE(A.NOTA,0) ELSE COALESCE(A.NOTA_EXTRA,0) END," +
					" CASE B.CREDITOS WHEN 0 THEN 1 ELSE B.CREDITOS END "+					
				" FROM ENOC.KRDX_CURSO_ACT A, ENOC.MAPA_CURSO B, ENOC.CARGA_GRUPO C"+ 
				" WHERE SUBSTR(A.CURSO_CARGA_ID,1,6)='"+cargaId+"'"+
				" AND A.CURSO_ID = B.CURSO_ID"+
				" AND ENOC.CARRERA(B.PLAN_ID)= '"+carreraId+"'"+
				" AND A.CURSO_CARGA_ID = C.CURSO_CARGA_ID"+ 
				" AND C.CURSO_CARGA_ID = '"+cursoCargaId+"'"+
				" AND TIPOCAL_ID IN('1','2','4')" +					
				" AND B.TIPOCURSO_ID IN (1,2,7)";			
			rs = st.executeQuery (comando);
			int cr=0, n=0,sc=0,sn=0;
			while (rs.next()){
				cr = rs.getInt(2);
				n = rs.getInt(1);
				sc+=cr;
				sn+= cr*n;
			}
			if (sc>0){
				total = ((double)sn )/((double)sc);
				total = ((double)(int)((total+0.005)*100))/100;
			}else total = -1;
		}catch(Exception ex){
			System.out.println("Error - aca.bsc.Aprovechamiento|promedioMateria|:"+ex);
			total = -1;
		}finally{	
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}
	public static ArrayList<String> getFCarreras(Connection conn, String cargaId) throws SQLException{
		ArrayList<String> listor = new ArrayList<String>();
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando="",facultad="",fac="";
		try {
			comando = "SELECT FACULTAD_ID, CARRERA_ID FROM ENOC.CAT_CARRERA"+ 
					" WHERE CARRERA_ID IN"+ 
						" (SELECT ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID))"+ 
						" FROM ENOC.KRDX_CURSO_ACT"+ 
						" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"'"+
						" AND TIPOCAL_ID IN ('1','2','4')"+
						" AND CURSO_ID IN"+ 
				  	  		" (SELECT CURSO_ID"+ 
							" FROM ENOC.MAPA_CURSO"+ 
							" WHERE CURSO_ID = KRDX_CURSO_ACT.CURSO_ID"+
							" AND TIPOCURSO_ID IN ('1','2','7')))"+
					" ORDER BY 1";			
			rs = st.executeQuery (comando);
			while (rs.next()){		
				facultad = rs.getString(1); 
				if (facultad.equals(fac))
					listor.add(rs.getString(2));
				else{
					listor.add(facultad);
					listor.add(rs.getString(2));
					fac = facultad;
				}
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bsc.Aprovechamiento|getFCarreras|:"+ex);
		}finally{	
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return listor;
	}
	public static ArrayList<String> getMaterias(Connection conn, String cargaId, String carreraId) throws SQLException{
		ArrayList<String> listor = new ArrayList<String>();
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando="";
		try {
			comando = "SELECT  " +
			" DISTINCT (A.CODIGO_PERSONAL), " +
			" A.CURSO_CARGA_ID, " +
			" B.CURSO_ID, " +
			" C.NOMBRE_CURSO " +
			" FROM ENOC.CARGA_GRUPO A, ENOC.CARGA_GRUPO_CURSO B, ENOC.MAPA_CURSO C" +
			" WHERE B.CURSO_CARGA_ID = A.CURSO_CARGA_ID" +
			" AND A.CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"' AND TIPOCAL_ID NOT IN ('M','I')) " +
			" AND A.CARRERA_ID = '"+carreraId+"'" +
			" AND B.ORIGEN = 'O'" +
			" AND C.CURSO_ID = B.CURSO_ID" +
			" ORDER BY 1";
			rs = st.executeQuery (comando);
			while (rs.next()){
				listor.add(rs.getString(1));
				listor.add(rs.getString(3));
				listor.add(rs.getString(2));
				listor.add(rs.getString(4));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bsc.Aprovechamiento|getMaterias|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return listor;
	}
	public static ArrayList<String> getMateriasDeAlumno(Connection conn, String cargaId, String carreraId, String codigoPersonal) throws SQLException{
		ArrayList<String> listor = new ArrayList<String>();
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando="";
		try {
			comando = "SELECT " +
				" A.CURSO_CARGA_ID," +
				" A.CURSO_ID," +
				" C.NOMBRE_CURSO,"+
				" CASE COALESCE(A.NOTA_EXTRA,0) WHEN 0 THEN COALESCE(A.NOTA,0) ELSE COALESCE(A.NOTA_EXTRA,0) END," +
				" A.TIPOCAL_ID"+  
		" FROM ENOC.KRDX_CURSO_ACT A, ENOC.CARGA_GRUPO B, ENOC.MAPA_CURSO C"+ 
		" WHERE A.CURSO_CARGA_ID = B.CURSO_CARGA_ID"+ 
		" AND A.CURSO_ID = C.CURSO_ID" +
		" AND A.TIPOCAL_ID!='M'"+ 
		" AND SUBSTR(A.CURSO_CARGA_ID,1,6) = '"+cargaId+"'"+ 
		" AND ENOC.CARRERA(C.PLAN_ID) = '"+carreraId+"'"+
		" AND A.CODIGO_PERSONAL = '"+codigoPersonal+"' " +
		" ORDER BY 5,1";

			rs = st.executeQuery (comando);
			while (rs.next()){
				listor.add(rs.getString(1));
				listor.add(rs.getString(2));
				listor.add(rs.getString(3));
				listor.add(rs.getString(4));
				listor.add(rs.getString(5));
			}
		}catch(Exception ex){
			System.out.println("Erro - aca.bsc.Aprovechamiento|getMateriasDeAlumno|:"+ex);
		}finally{	
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return listor;
	}
	public static int getTotalMateriasDeAlumno(Connection conn, String cargaId,String carreraId, String codigoPersonal) throws SQLException{
		int total = 0;
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando="";
		try {
			comando = "SELECT "+
					" COUNT(A.CURSO_CARGA_ID)"+
			" FROM ENOC.KRDX_CURSO_ACT A, ENOC.CARGA_GRUPO B, ENOC.MAPA_CURSO C"+ 
			" WHERE A.CURSO_CARGA_ID = B.CURSO_CARGA_ID"+
			" AND A.CURSO_ID = C.CURSO_ID" +
			" AND A.TIPOCAL_ID!='M'"+ 
			" AND SUBSTR(A.CURSO_CARGA_ID,1,6) = '"+cargaId+"'"+
			" AND ENOC.CARRERA(C.PLAN_ID) = '"+carreraId+"'"+
			" AND A.CODIGO_PERSONAL = '"+codigoPersonal+"'" +
			" ORDER BY 1";
			rs = st.executeQuery (comando);
			while (rs.next()){
				total = rs.getInt(1);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bsc.Aprovechamiento|getTotalMateriasDeAlumno|:"+ex);
		}finally{	
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return total;
	}
	public static ArrayList<String> getAlumnosMateria(Connection conn,String cargaId,String carreraId, String cursoCargaId) throws SQLException{
		ArrayList<String> listor = new ArrayList<String>();
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando="";
		try {
			comando = "SELECT" +
					" A.CODIGO_PERSONAL,COALESCE(A.NOTA,0)," +
					" COALESCE(A.NOTA_EXTRA,0)," +
					" A.TIPOCAL_ID," +
					" CASE B.CREDITOS WHEN 0 THEN 1 ELSE B.CREDITOS END "+
			" FROM ENOC.KRDX_CURSO_ACT A, ENOC.MAPA_CURSO B, ENOC.CARGA_GRUPO C"+ 
			" WHERE SUBSTR(A.CURSO_CARGA_ID,1,6)='"+cargaId+"'" +
			" AND ENOC.CARRERA(PLAN_ID)='"+carreraId+"'"+
			" AND A.CURSO_ID = B.CURSO_ID " +
			" AND A.TIPOCAL_ID!='M'"+ 
			" AND A.CURSO_CARGA_ID = C.CURSO_CARGA_ID" +
			" AND A.CURSO_CARGA_ID = '"+cursoCargaId+"'";
			rs = st.executeQuery (comando);
			while (rs.next()){
				listor.add(rs.getString(1));
				listor.add(rs.getString(2));
				listor.add(rs.getString(3));
				listor.add(rs.getString(4));
				listor.add(rs.getString(5));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bsc.Aprovechamiento|getAlumnosMateria|:"+ex);
		}finally{	
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return listor;
	}

	public static int numeroAlumnosMaestro(Connection conn, String cargaId, String carreraId, String maestro) throws SQLException{
		int total=0;
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando="";
		try {
			comando = "SELECT" +
				" COUNT(A.CODIGO_PERSONAL)"+
			" FROM ENOC.KRDX_CURSO_ACT A, ENOC.MAPA_CURSO B, ENOC.CARGA_GRUPO C"+ 
			" WHERE SUBSTR(A.CURSO_CARGA_ID,1,6)='"+cargaId+"'" +
			" AND ENOC.CARRERA(PLAN_ID)='"+carreraId+"'"+
			" AND A.CURSO_ID = B.CURSO_ID" +
			" AND A.TIPOCAL_ID!='M' "+ 
			" AND A.CURSO_CARGA_ID = C.CURSO_CARGA_ID" +
			" AND C.CODIGO_PERSONAL = '"+maestro+"'";
			rs = st.executeQuery (comando);
			while (rs.next()){
				total = rs.getInt(1);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bsc.Aprovechamiento|numeroAlumnosMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}
	public static int numeroAlumnosMateria(Connection conn, String cargaId, String carreraId, String cursoCargaId) throws SQLException{
		int total=0;
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando="";
		try {
			comando = "SELECT " +
				"COUNT(A.CURSO_CARGA_ID)"+
			" FROM ENOC.KRDX_CURSO_ACT A, ENOC.MAPA_CURSO B, ENOC.CARGA_GRUPO C"+ 
			" WHERE SUBSTR(A.CURSO_CARGA_ID,1,6)='"+cargaId+"'" +
			" AND ENOC.CARRERA(PLAN_ID)='"+carreraId+"'"+
			" AND A.CURSO_ID = B.CURSO_ID" +
			" AND A.TIPOCAL_ID!='M'"+ 
			" AND A.CURSO_CARGA_ID = C.CURSO_CARGA_ID" +
			" AND A.CURSO_CARGA_ID = '"+cursoCargaId+"'";
			rs = st.executeQuery (comando);
			while (rs.next()){
				total = rs.getInt(1);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bsc.Aprovechamiento|numeroAlumnosMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}
	public static int numeroTipoCalMateria(Connection conn, String cargaId, String carreraId, String cursoCargaId,String tipo) throws SQLException{
		int total=0;
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando="";
		try {
			comando = "SELECT " +
				"COUNT(A.CURSO_CARGA_ID) "+
			" FROM ENOC.KRDX_CURSO_ACT A, ENOC.MAPA_CURSO B, ENOC.CARGA_GRUPO C"+ 
			" WHERE SUBSTR(A.CURSO_CARGA_ID,1,6)='"+cargaId+"'" +
			" AND ENOC.CARRERA(PLAN_ID)='"+carreraId+"' "+
			" AND A.CURSO_ID = B.CURSO_ID" +
			" AND A.TIPOCAL_ID!='M'"+ 
			" AND A.CURSO_CARGA_ID = C.CURSO_CARGA_ID" +
			" AND A.CURSO_CARGA_ID = '"+cursoCargaId+"'" +
			" AND A.TIPOCAL_ID = '"+tipo+"'";
			rs = st.executeQuery (comando);
			while (rs.next()){
				total = rs.getInt(1);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bsc.Aprovechamiento|numeroTipoCalMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}
	public static int numeroTipoCalMaestro(Connection conn, String cargaId, String carreraId, String codigoPersonal,String tipo) throws SQLException{
		int total=0;
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando="";
		try {
			comando = "SELECT " +
				"COUNT(A.CODIGO_PERSONAL) "+
			" FROM ENOC.KRDX_CURSO_ACT A, ENOC.MAPA_CURSO B, ENOC.CARGA_GRUPO C"+ 
			" WHERE SUBSTR(A.CURSO_CARGA_ID,1,6)='"+cargaId+"'" +
			" AND ENOC.CARRERA(PLAN_ID)='"+carreraId+"'"+
			" AND A.CURSO_ID = B.CURSO_ID" +
			" AND A.TIPOCAL_ID!='M'"+ 
			" AND A.CURSO_CARGA_ID = C.CURSO_CARGA_ID" +
			" AND C.CODIGO_PERSONAL = '"+codigoPersonal+"'" +
			" AND A.TIPOCAL_ID = '"+tipo+"'";
			rs = st.executeQuery (comando);
			while (rs.next()){
				total = rs.getInt(1);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bsc.Aprovechamiento|numeroTipoCalMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}
	
	public static int numeroTipoCalAlumno(Connection conn, String cargaId, String carreraId, String codigoPersonal,String tipo) throws SQLException{
		int total=0;
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando="";
		try {
			comando = "SELECT COUNT(A.CURSO_CARGA_ID)"+
			" FROM ENOC.KRDX_CURSO_ACT A, ENOC.MAPA_CURSO B"+ 
			" WHERE SUBSTR(A.CURSO_CARGA_ID,1,6)='"+cargaId+"'" +
			" AND ENOC.CARRERA(PLAN_ID)='"+carreraId+"'"+
			" AND A.CURSO_ID = B.CURSO_ID" +
			" AND A.TIPOCAL_ID!='M'"+ 
			" AND A.CODIGO_PERSONAL = '"+codigoPersonal+"'" +
			" AND A.TIPOCAL_ID = '"+tipo+"'";
			rs = st.executeQuery (comando);
			while (rs.next()){
				total = rs.getInt(1);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bsc.Aprovechamiento|numeroTipoCalAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}
}