//Clase para la tabla de Modulo
package aca.graduacion;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlumEgresoUtil{
	
	
	public boolean insertReg(Connection conn, AlumEgreso egreso ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_EGRESO"+ 
 				"(CODIGO_PERSONAL, PLAN_ID, EVENTO_ID, PROMEDIO, FECHA,"+
 				" TITULADO, F_INGRESO, F_EGRESO)"+
 				"VALUES( ?, ?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'),"+
 				" ?, TO_DATE(?,'DD/MM/YYYY'),"+
 				" TO_DATE(?, 'DD/MM/YYYY'))");			
 			ps.setString(1, egreso.getCodigoPersonal());
 			ps.setString(2, egreso.getPlanId());
 			ps.setInt(3, Integer.parseInt(egreso.getEventoId()));
 			ps.setInt(4, Integer.parseInt(egreso.getPromedio()));
 			ps.setString(5, egreso.getFecha());
 			ps.setString(6, egreso.getTitulado());
 			ps.setString(7, egreso.getFIngreso());
 			ps.setString(8, egreso.getFEgreso());
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.graduacion.AlumEgreso|insertReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(Connection conn, AlumEgreso egreso ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("UPDATE ENOC.ALUM_EGRESO"+ 
 				" SET"+				
 				" CODIGO_PERSONAL = ?,"+
 				" PLAN_ID = ?,"+
 				" EVENTO_ID = ?,"+
 				" PROMEDIO = ?,"+
 				" FECHA = TO_DATE(?,'DD/MM/YYYY'),"+
 				" TITULADO = ?,"+
 				" F_INGRESO = TO_DATE(?,'DD/MM/YYYY'),"+
 				" F_EGRESO = TO_DATE(?,'DD/MM/YYYY')"+
 				" WHERE CODIGO_PERSONAL = ?" +
 				" AND PLAN_ID = ?");
 				
 			ps.setString(1, egreso.getCodigoPersonal());
 			ps.setString(2, egreso.getPlanId());
 			ps.setInt(3, Integer.parseInt(egreso.getEventoId()));
 			ps.setInt(4, Integer.parseInt(egreso.getPromedio()));
 			ps.setString(5, egreso.getFecha());
 			ps.setString(6, egreso.getTitulado());
 			ps.setString(7, egreso.getFIngreso());
 			ps.setString(8, egreso.getFEgreso());
 			ps.setString(9, egreso.getCodigoPersonal());
 			ps.setString(10, egreso.getPlanId());
 			 			
 			if (ps.executeUpdate()== 1)
 				ok = true;	
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.graduacion.AlumEgreso|updateReg|:"+ex);		
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	 	
 	public boolean deleteReg(Connection conn, String codigoPersonal, String planId ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_EGRESO"+ 
 				" WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?");
 			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.graduacion.AlumEgreso|deleteReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}
 	
 	public AlumEgreso mapeaRegId( Connection conn, String Codigo_Personal, String Plan_Id ) throws SQLException, IOException{
 		
 		AlumEgreso egreso = new AlumEgreso();
 		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement("SELECT"+
	 			" CODIGO_PERSONAL, PLAN_ID, EVENTO_ID, PROMEDIO, FECHA,"+
	 			" TITULADO, F_INGRESO, F_EGRESO"+
	 			" FROM ENOC.ALUM_EGRESO WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?"); 
	 		ps.setString(1, Codigo_Personal);
			ps.setString(2, Plan_Id);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			egreso.mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.AlumEgreso|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 		
 		return egreso;
 	}
	
 	public boolean existeReg(Connection conn, String codigoPersonal, String planId) throws SQLException{
 		boolean 		ok 	= false;
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT EVENTO_ID FROM ENOC.ALUM_EGRESO"+ 
 				" WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?");
 			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.graduacion.AlumEgreso|existeReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
	
	public ArrayList<AlumEgreso> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AlumEgreso> lisAlumno	= new ArrayList<AlumEgreso>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, PLAN_ID, EVENTO_ID, PROMEDIO," +
				" FECHA, TITULADO, F_INGRESO, F_EGRESO" +
				" FROM ENOC.ALUM_EGRESO "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumEgreso alumno = new AlumEgreso();
				alumno.mapeaReg(rs);
				lisAlumno.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.AlumEgresoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumno;
	}
	
	public ArrayList<AlumEgreso> getListaEvento(Connection conn, String Evento_Id, String Orden ) throws SQLException{
		
		ArrayList<AlumEgreso> lisAlumno	= new ArrayList<AlumEgreso>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, PLAN_ID, EVENTO_ID, PROMEDIO, FECHA,"+
			" TITULADO, F_INGRESO, F_EGRESO"+
			" FROM ENOC.ALUM_EGRESO"+ 
			" WHERE EVENTO_ID = "+Evento_Id+" "+Orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumEgreso Alumno = new AlumEgreso();
				Alumno.mapeaReg(rs);
				lisAlumno.add(Alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.AlumEgresoUtil|getListaEvento|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumno;
	}
	
	public ArrayList<AlumEgreso> getListAlum(Connection conn, String Codigo_Personal, String Orden ) throws SQLException{
		
		ArrayList<AlumEgreso> lisPersonal	= new ArrayList<AlumEgreso>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, PLAN_ID, EVENTO_ID, PROMEDIO, FECHA,"+
			" TITULADO, F_INGRESO, F_EGRESO"+
			" FROM ENOC.ALUM_EGRESO"+ 
			" WHERE CODIGO_PERSONAL = '"+Codigo_Personal+"' "+Orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumEgreso Alumno = new AlumEgreso();
				Alumno.mapeaReg(rs);
				lisPersonal.add(Alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.AlumEgresoUtil|getListAlum|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPersonal;
	}
	
	public HashMap<String, String> mapaAlumGraduados(Connection conn) throws SQLException{
		
		HashMap<String, String> map = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;	
		try{			
			String comando = "SELECT CODIGO_PERSONAL||PLAN_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.ALUM_EGRESO GROUP BY CODIGO_PERSONAL,PLAN_ID";			
			rs = st.executeQuery(comando);
			while(rs.next()){
				map.put(rs.getString("LLAVE"), rs.getString("VALOR"));
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.AlumEgresoUtil|mapaAlumGraduados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return map;
	}
}