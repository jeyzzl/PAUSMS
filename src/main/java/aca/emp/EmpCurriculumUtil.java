/**
 * 
 */
package aca.emp;

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
public class EmpCurriculumUtil {
	
	public boolean insertReg(Connection conn, EmpCurriculum empCurriculum) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.EMP_CURRICULUM(ID_EMPLEADO, LUGAR_NAC, T_PROFESIONAL, G_POSGRADO, T_UNIVERSITARIO, RESP_ACTUAL, RESP_ANTERIOR," +
				" EXP_DOCENTE, F_NACIMIENTO, NACIONALIDAD, NIVEL_ID, REVISADO, T_DOCTORADO, FECHA_LIC, FECHA_MAE, FECHA_DOC, INST_LIC, INST_MAE, INST_DOC )" +
				" VALUES(?, ?, ?, ?, ?, ?, ?, ?, TO_DATE(?,'DD/MM/YYYY'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), ?, ?, TO_DATE(?,'DD/MM/YYYY')," +
				" TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), ?, ?, ? )");
			
			ps.setString(1, empCurriculum.getIdEmpleado());
			ps.setString(2, empCurriculum.getLugarNac());
			ps.setString(3, empCurriculum.getTProfesional());			
			ps.setString(4, empCurriculum.getGPosgrado());
			ps.setString(5, empCurriculum.getTUniversitario());
			ps.setString(6, empCurriculum.getRespActual());
			ps.setString(7, empCurriculum.getRespAnterior());
			ps.setString(8, empCurriculum.getExpDocente());
			ps.setString(9, empCurriculum.getFNacimiento());
			ps.setString(10, empCurriculum.getNacionalidad());
			ps.setString(11, empCurriculum.getNivelId());
			ps.setString(12, empCurriculum.getRevisado());
			ps.setString(13, empCurriculum.gettDoctorado());
			ps.setString(14, empCurriculum.getFechaLic());
			ps.setString(15, empCurriculum.getFechaMae());
			ps.setString(16, empCurriculum.getFechaDoc());
			ps.setString(17, empCurriculum.getInstLic());
			ps.setString(18, empCurriculum.getInstMae());
			ps.setString(19, empCurriculum.getInstDoc());
			
			if(ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpCurriculumUtil|insertReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, EmpCurriculum empCurriculum) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EMP_CURRICULUM" + 
				" SET LUGAR_NAC = ?," +
				" T_PROFESIONAL = ?," +
				" G_POSGRADO = ?," +
				" T_UNIVERSITARIO = ?," +
				" RESP_ACTUAL = ?," +
				" RESP_ANTERIOR = ?," +
				" EXP_DOCENTE = ?, " +
				" F_NACIMIENTO = TO_DATE(?, 'DD/MM/YYYY')," +
				" NACIONALIDAD = TO_NUMBER(?,'999')," +
				" NIVEL_ID = TO_NUMBER(?,'999'), " +
				" REVISADO = ?, T_DOCTORADO = ?, FECHA_LIC = TO_DATE(?, 'DD/MM/YYYY'), " +
				" FECHA_MAE = TO_DATE(?, 'DD/MM/YYYY'), FECHA_DOC = TO_DATE(?, 'DD/MM/YYYY'), INST_LIC = ?," +
				" INST_MAE = ?, INST_DOC = ? " +
				" WHERE ID_EMPLEADO = ?");
			
			ps.setString(1, empCurriculum.getLugarNac());
			ps.setString(2, empCurriculum.getTProfesional());			
			ps.setString(3, empCurriculum.getGPosgrado());
			ps.setString(4, empCurriculum.getTUniversitario());
			ps.setString(5, empCurriculum.getRespActual());
			ps.setString(6, empCurriculum.getRespAnterior());
			ps.setString(7, empCurriculum.getExpDocente());
			ps.setString(8, empCurriculum.getFNacimiento());
			ps.setString(9, empCurriculum.getNacionalidad());
			ps.setString(10, empCurriculum.getNivelId());
			ps.setString(11, empCurriculum.getRevisado());
			ps.setString(12, empCurriculum.gettDoctorado());
			ps.setString(13, empCurriculum.getFechaLic());
			ps.setString(14, empCurriculum.getFechaMae());
			ps.setString(15, empCurriculum.getFechaDoc());
			ps.setString(16, empCurriculum.getInstLic());
			ps.setString(17, empCurriculum.getInstMae());
			ps.setString(18, empCurriculum.getInstDoc());
			ps.setString(19, empCurriculum.getIdEmpleado());
						
			if(ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpCurriculumUtil|updateReg|:"+ex);		
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String idEmpleado) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.EMP_CURRICULUM"+ 
				" WHERE ID_EMPLEADO = ?");
			
			ps.setString(1, idEmpleado);
			
			if(ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpCurriculumUtil|deleteReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}
	
	public EmpCurriculum mapeaRegId(Connection con, String idEmpleado) throws SQLException{
		EmpCurriculum empCurriculum = new EmpCurriculum();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement("SELECT ID_EMPLEADO, LUGAR_NAC, T_PROFESIONAL, G_POSGRADO," +
					" T_UNIVERSITARIO, RESP_ACTUAL, RESP_ANTERIOR, EXP_DOCENTE, TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO," +
					" NACIONALIDAD, NIVEL_ID, REVISADO, T_DOCTORADO, " +
					" TO_CHAR(FECHA_LIC,'DD/MM/YYYY') AS FECHA_LIC, TO_CHAR(FECHA_MAE,'DD/MM/YYYY') AS FECHA_MAE, TO_CHAR(FECHA_DOC,'DD/MM/YYYY') AS FECHA_DOC," +
					" INST_LIC, INST_MAE, INST_DOC FROM ENOC.EMP_CURRICULUM " + 
					" WHERE ID_EMPLEADO = ?");
			
			ps.setString(1, idEmpleado);
			
			rs = ps.executeQuery();
			
			if(rs.next()){				
				empCurriculum.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpCurriculumUtil|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		return empCurriculum;
	}
	
	public boolean existeReg(Connection conn, String idEmpleado) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EMP_CURRICULUM" + 
					" WHERE ID_EMPLEADO = ?");
		
		ps.setString(1, idEmpleado);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpCurriculumUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return ok;
	}
	
	public ArrayList<EmpCurriculum> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<EmpCurriculum> listEmp		= new ArrayList<EmpCurriculum>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT ID_EMPLEADO, LUGAR_NAC, T_PROFESIONAL, G_POSGRADO," +
				" T_UNIVERSITARIO, RESP_ACTUAL, RESP_ANTERIOR, EXP_DOCENTE, TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO," +
				" COALESCE(NACIONALIDAD,91) AS NACIONALIDAD, NIVEL_ID, REVISADO, T_DOCTORADO, FECHA_LIC, FECHA_MAE, FECHA_DOC," +
				" INST_LIC, INST_MAE, INST_DOC " +					
				" FROM ENOC.EMP_CURRICULUM "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				EmpCurriculum emp = new EmpCurriculum();
				emp.mapeaReg(rs);
				listEmp.add(emp);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpCurriculumUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return listEmp;
	}
}