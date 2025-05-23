package aca.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


public class AlumGraduaUtil {
	
	public boolean insertReg(Connection conn, AlumGradua alumGradua ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_GRADUA"+ 
				"(CODIGO_PERSONAL, PLAN_ID, FECHA, FECHA_GRADUACION, EVENTO, "+
				"AVANCE, MAT_AC, MAT_INS, MAT_PEN, DIPLOMA) "+
				"VALUES( ?, ?, TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY')," +
				"?,?," +
				"TO_NUMBER(?,'999.99'), "+
				"TO_NUMBER(?,'999'), "+
				"TO_NUMBER(?,'999'), ? )");
			
			ps.setString(1, alumGradua.getCodigoPersonal());
			ps.setString(2, alumGradua.getPlanId());
			ps.setString(3, alumGradua.getFecha());
			ps.setString(4, alumGradua.getFechaGraduacion());
			ps.setString(5, alumGradua.getEvento());
			ps.setString(6, alumGradua.getAvance());
			ps.setString(7, alumGradua.getMatAc());
			ps.setString(8, alumGradua.getMatIns());
			ps.setString(9, alumGradua.getMatPen());
			ps.setString(10, alumGradua.getDiploma());
						
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumGraduaUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	

	public boolean updateReg(Connection conn, AlumGradua alumGradua ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_GRADUA"+ 
				" SET "+
				" FECHA = TO_DATE(?,'DD/MM/YYYY'),"+
				" FECHA_GRADUACION = TO_DATE(?,'DD/MM/YYYY'),"+
				" EVENTO  = ?,"+
				" AVANCE = ?,"+
				" MAT_AC = TO_NUMBER(?,'999.99'),"+
				" MAT_INS = TO_NUMBER(?,'999'),"+
				" MAT_PEN = TO_NUMBER(?,'999')," +
				" DIPLOMA = ? "+
				" WHERE CODIGO_PERSONAL = ?"+
				" AND PLAN_ID = ?");			
			ps.setString(1, alumGradua.getFecha());
			ps.setString(2, alumGradua.getFechaGraduacion());
			ps.setString(3, alumGradua.getEvento());
			ps.setString(4, alumGradua.getAvance());
			ps.setString(5, alumGradua.getMatAc());
			ps.setString(6, alumGradua.getMatIns());
			ps.setString(7, alumGradua.getMatPen());
			ps.setString(8, alumGradua.getDiploma());
			ps.setString(9, alumGradua.getCodigoPersonal());			
			ps.setString(10,alumGradua.getPlanId());			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumGraduaUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
		
	public boolean deleteReg(Connection conn, String codigoPersonal, String planId ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_GRADUA "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumGraduaUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public AlumGradua mapeaRegId( Connection conn, String codigoPersonal, String planId ) throws SQLException{
		AlumGradua alumGradua = new AlumGradua();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, PLAN_ID,"+
				" TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TO_CHAR(FECHA_GRADUACION,'DD/MM/YYYY') AS FECHA_GRADUACION," +
				" EVENTO, AVANCE," +
				" MAT_AC, MAT_INS, COALESCE(MAT_PEN,0) AS MAT_PEN, DIPLOMA " +
				" FROM ENOC.ALUM_GRADUA "+ 
				" WHERE CODIGO_PERSONAL = ?"+
				" AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				alumGradua.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumGraduaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return alumGradua;
	}	
	
	public boolean existeReg(Connection conn, String codigoPersonal, String planId ) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_GRADUA "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);	
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumGraduaUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public ArrayList<AlumGradua> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AlumGradua> lisGradua		= new ArrayList<AlumGradua>();
		Statement st 						= conn.createStatement();
		ResultSet rs 						= null;
		String comando						= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, PLAN_ID, TO_CHAR (FECHA, 'DD/MM/YYYY') AS FECHA, TO_CHAR (FECHA_GRADUACION, 'DD/MM/YYYY') AS FECHA_GRADUACION, " +
					"  EVENTO, AVANCE, MAT_AC, MAT_INS, MAT_PEN, DIPLOMA "+
				"FROM ENOC.ALUM_GRADUA "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumGradua actividad = new AlumGradua();
				actividad.mapeaReg(rs);
				lisGradua.add(actividad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumGraduaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisGradua;
	}
	
	public ArrayList<AlumGradua> getLista(Connection conn, String codigoPersonal, String planId, String orden ) throws SQLException{
		
		ArrayList<AlumGradua> lisActiv	= new ArrayList<AlumGradua>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, PLAN_ID, TO_CHAR (FECHA, 'DD/MM/YYYY') AS FECHA, TO_CHAR (FECHA_GRADUACION, 'DD/MM/YYYY') AS FECHA_GRADUACION," +
					" EVENTO, AVANCE, MAT_AC, MAT_INS, MAT_PEN, DIPLOMA "+
					"FROM ENOC.ALUM_GRADUA "+ 
					"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+
					"AND PLAN_ID = '"+planId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumGradua actividad = new AlumGradua();
				actividad.mapeaReg(rs);
				lisActiv.add(actividad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumGraduaUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisActiv;
	}
	
	
	public HashMap<String, String> getMapGraduados(Connection conn) throws SQLException{
		
		HashMap<String, String> map				= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, PLAN_ID FROM ENOC.ALUM_GRADUA";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				map.put(rs.getString("CODIGO_PERSONAL")+rs.getString("PLAN_ID"), rs.getString("CODIGO_PERSONAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumGraduaUtil|getMapGraduados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return map;
	}
}