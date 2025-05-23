package aca.emp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import aca.emp.EmpEstadistica;

public class EmpEstadisticaUtil {
	
	public boolean insertReg(Connection conn, EmpEstadistica empEstadistica ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			
			ps = conn.prepareStatement("INSERT INTO ENOC.EMP_ESTADISTICA"+ 
				" (CODIGO_PERSONAL, CARGAS, MODALIDADES)"+
				" VALUES( ?, ?, ? )");
			
			ps.setString(1, empEstadistica.getCodigoPersonal());
			ps.setString(2, empEstadistica.getCargas());
			ps.setString(3, empEstadistica.getModalidades());
						
			if (ps.executeUpdate()== 1)
				ok = true;
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpEstadisticaUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	

	public boolean updateReg(Connection conn, EmpEstadistica empEstadistica ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EMP_ESTADISTICA"+ 
				" SET "+
				" CARGAS = ?,"+
				" MODALIDADES = ? "+
				" WHERE CODIGO_PERSONAL = ?");
			
			ps.setString(1, empEstadistica.getCargas());
			ps.setString(2, empEstadistica.getModalidades());
			ps.setString(3, empEstadistica.getCodigoPersonal());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpEstadisticaUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String codigoPersonal ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.EMP_ESTADISTICA"+ 
				"WHERE CODIGO_PERSONAL = ? ");
			
			ps.setString(1, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpEstadisticaUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public EmpEstadistica mapeaRegId( Connection conn, String codigoPersonal ) throws SQLException{
		EmpEstadistica empEstadistica = new EmpEstadistica();
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, CARGAS, MODALIDADES" +						
				" FROM ENOC.EMP_ESTADISTICA WHERE CODIGO_PERSONAL = ?"); 
			
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				empEstadistica.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpEstadisticaUtil|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		return empEstadistica;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal ) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs	= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EMP_ESTADISTICA "+ 
				"WHERE CODIGO_PERSONAL = ? ");
			
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpEstadisticaUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public ArrayList<EmpEstadistica> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<EmpEstadistica> lisEstadistica	= new ArrayList<EmpEstadistica>();
		Statement st 		                		= conn.createStatement();
		ResultSet rs 				                = null;
		String comando                     			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CARGAS, MODALIDADES FROM ENOC.EMP_ESTADISTICA "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				EmpEstadistica actividad = new EmpEstadistica();
				actividad.mapeaReg(rs);
				lisEstadistica.add(actividad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpEstadisticaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
}