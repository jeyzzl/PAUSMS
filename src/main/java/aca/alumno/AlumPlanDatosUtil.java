package aca.alumno;

import java.sql.*;
import java.util.ArrayList;

public class AlumPlanDatosUtil{
	
	public boolean insertReg(Connection conn, AlumPlanDatos alumPlanDatos ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_PLAN_DATOS"+ 
				"(CODIGO_PERSONAL, PLAN_ID, PROMEDIO, CREDITOS)"+
				" VALUES( ?, ?, TO_NUMBER(?,'99999'),TO_NUMBER(?,'99999'))");
					
			ps.setString(1, alumPlanDatos.getCodigoPersonal());
			ps.setString(2, alumPlanDatos.getPlanId());
			ps.setString(3, alumPlanDatos.getPromedio());
			ps.setString(4, alumPlanDatos.getCreditos());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumPlanDatosUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, AlumPlanDatos alumPlanDatos ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_PLAN_DATOS "+ 
				"SET"+
				" PROMEDIO = TO_NUMBER(?,'99999')," +
				" CREDITOS = TO_NUMBER(?,'99999')"+
				" WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?");
			
			ps.setString(1, alumPlanDatos.getPromedio());
			ps.setString(2, alumPlanDatos.getCreditos());
			ps.setString(3, alumPlanDatos.getCodigoPersonal());
			ps.setString(4, alumPlanDatos.getPlanId());
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumPlanDatosUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String codigoPersonal, String planId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_PLAN_DATOS"+ 
				" WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumPlanDatosUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public AlumPlanDatos mapeaRegId( Connection conn, String codigoPersonal, String planId) throws SQLException{
		AlumPlanDatos alumPlanDatos = new AlumPlanDatos();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT"+
				" CODIGO_PERSONAL, PLAN_ID, PROMEDIO, CREDITOS"+
				" FROM ENOC.ALUM_PLAN_DATOS WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ? "); 
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				alumPlanDatos.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumPlanDatosUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return alumPlanDatos;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal, String planId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_PLAN_DATOS "+ 
				"WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumPlanDatosUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
		
	public ArrayList<AlumPlanDatos> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AlumPlanDatos> listaAlumnos= new ArrayList<AlumPlanDatos>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, PLAN_ID, PROMEDIO, CREDITOS"+
				" FROM ENOC.ALUM_PLAN_DATOS "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlumPlanDatos alumno = new AlumPlanDatos();
				alumno.mapeaReg(rs);
				listaAlumnos.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumPlanDatosUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}				
		return listaAlumnos;
	}
}