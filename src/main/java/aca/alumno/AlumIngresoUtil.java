package aca.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AlumIngresoUtil {
	
	public boolean insertReg(Connection conn, AlumIngreso alumIngreso ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_INGRESO"+ 
				"(CODIGO_PERSONAL, PLAN_ID, CARGA_ID, CARRERA_ID, NEWUM, "+
				"NEWPLAN) "+
				"VALUES( ?, ?, ?, ?, ?, ? )");
			
			ps.setString(1, alumIngreso.getCodigoPersonal());
			ps.setString(2, alumIngreso.getPlanId());
			ps.setString(3, alumIngreso.getCargaId());
			ps.setString(4, alumIngreso.getCarreraId());
			ps.setString(5, alumIngreso.getNewum());
			ps.setString(6, alumIngreso.getNewplan());
						
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumIngresoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, AlumIngreso alumIngreso ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_INGRESO"+ 
				" SET "+
				" CARGA_ID = ?,"+
				" CARRERA_ID = ?,"+
				" NEWUM = ?,"+
				" NEWPLAN = ?"+
				" WHERE CODIGO_PERSONAL = ?" +
				" AND PLAN_ID = ?");	
			
			ps.setString(1, alumIngreso.getCargaId());
			ps.setString(2, alumIngreso.getCarreraId());
			ps.setString(3, alumIngreso.getNewum());
			ps.setString(4, alumIngreso.getNewplan());
			ps.setString(5, alumIngreso.getCodigoPersonal());
			ps.setString(6, alumIngreso.getPlanId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumIngresoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
		
	public boolean deleteReg(Connection conn, String codigoPersonal, String planId ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_INGRESO "+ 
				"WHERE CODIGO_PERSONAL = ? " +
				"AND PLAN_ID = ? ");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumIngresoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public AlumIngreso mapeaRegId( Connection conn, String codigoPersonal, String planId ) throws SQLException{
		AlumIngreso alumIngreso = new AlumIngreso();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT"+
				" CODIGO_PERSONAL, "+
				" PLAN_ID, CARGA_ID, " +
				" CARRERA_ID, NEWUM, NEWPLAN, " +
				" WHERE CODIGO_PERSONAL = ?" +
				" AND PLAN_ID = ? ");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				alumIngreso.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumIngresoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return alumIngreso;
	}	
	
	public boolean existeReg(Connection conn, String codigoPersonal) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_INGRESO "+ 
				" WHERE CODIGO_PERSONAL = ? " +
				" AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumIngresoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}

	public ArrayList<AlumIngreso> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AlumIngreso> lisIngreso		= new ArrayList<AlumIngreso>();
		Statement st 		                		= conn.createStatement();
		ResultSet rs 				                = null;
		String comando                     			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, PLAN_ID, CARGA_ID, " +
					" CARRERA_ID, NEWUM, NEWPLAN FROM ENOC.ALUM_INGRESO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumIngreso aingreso = new AlumIngreso();
				aingreso.mapeaReg(rs);
				lisIngreso.add(aingreso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumIngresoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisIngreso;
	}
	
	public ArrayList<AlumIngreso> getLista(Connection conn, String codigoPersonal,String planId, String orden ) throws SQLException{
		
		ArrayList<AlumIngreso> lisAlum	= new ArrayList<AlumIngreso>();
		Statement st 					    = conn.createStatement();
		ResultSet rs 					    = null;
		String comando	             		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, PLAN_ID, CARGA_ID," +
					" CARRERA_ID, NEWUM, NEWPLAN FROM ENOC.ALUM_INGRESO "+ 
					" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'" +
					" AND PLAN_ID = '"+planId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumIngreso alumno = new AlumIngreso();
				alumno.mapeaReg(rs);
				lisAlum.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumIngresoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlum;
	}



}