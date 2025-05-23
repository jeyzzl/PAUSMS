package aca.emp.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmpCurriculumDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( EmpCurriculum empCurriculum) {
		boolean ok 				= false;
		try{
			String comando = "INSERT INTO ENOC.EMP_CURRICULUM(ID_EMPLEADO, LUGAR_NAC, T_PROFESIONAL, G_POSGRADO, T_UNIVERSITARIO, RESP_ACTUAL, RESP_ANTERIOR,"
					+ "EXP_DOCENTE, F_NACIMIENTO, NACIONALIDAD, NIVEL_ID, REVISADO, T_DOCTORADO, FECHA_LIC, FECHA_MAE, FECHA_DOC, INST_LIC, INST_MAE, INST_DOC )"
					+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, TO_DATE(?,'DD/MM/YYYY'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), ?, ?, TO_DATE(?,'DD/MM/YYYY'),"
					+ "TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), ?, ?, ? )";
			
			Object[] parametros = new Object[] {empCurriculum.getIdEmpleado(),empCurriculum.getLugarNac(),empCurriculum.getTProfesional(),empCurriculum.getGPosgrado(),empCurriculum.getTUniversitario(),empCurriculum.getRespActual(),empCurriculum.getRespAnterior(),empCurriculum.getExpDocente(),empCurriculum.getFNacimiento(),empCurriculum.getNacionalidad(),empCurriculum.getNivelId(),empCurriculum.getRevisado(),empCurriculum.gettDoctorado(),empCurriculum.getFechaLic(),empCurriculum.getFechaMae(),empCurriculum.getFechaDoc(),empCurriculum.getInstLic(),empCurriculum.getInstMae(),empCurriculum.getInstDoc()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpCurriculumDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( EmpCurriculum empCurriculum) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.EMP_CURRICULUM"
					+ " SET LUGAR_NAC = ?,"
					+ " T_PROFESIONAL = ?,"
					+ " G_POSGRADO = ?,"
					+ " T_UNIVERSITARIO = ?,"
					+ " RESP_ACTUAL = ?,"
					+ " RESP_ANTERIOR = ?,"
					+ " EXP_DOCENTE = ?, "
					+ " F_NACIMIENTO = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " NACIONALIDAD = TO_NUMBER(?,'999'),"
					+ " NIVEL_ID = TO_NUMBER(?,'999'), "
					+ " REVISADO = ?,"
					+ " T_DOCTORADO = ?,"
					+ " FECHA_LIC = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " FECHA_MAE = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " FECHA_DOC = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " INST_LIC = ?,"
					+ " INST_MAE = ?,"
					+ " INST_DOC = ?"
					+ " WHERE ID_EMPLEADO = ?";			
			Object[] parametros = new Object[] {
					empCurriculum.getLugarNac(),empCurriculum.getTProfesional(),empCurriculum.getGPosgrado(),empCurriculum.getTUniversitario(),empCurriculum.getRespActual(),
					empCurriculum.getRespAnterior(),empCurriculum.getExpDocente(),empCurriculum.getFNacimiento(),empCurriculum.getNacionalidad(),empCurriculum.getNivelId(),empCurriculum.getRevisado(),empCurriculum.gettDoctorado(),empCurriculum.getFechaLic(),
					empCurriculum.getFechaMae(),empCurriculum.getFechaDoc(),empCurriculum.getInstLic(),empCurriculum.getInstMae(),empCurriculum.getInstDoc(),empCurriculum.getIdEmpleado()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				//System.out.println("Despues de update");
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpCurriculumDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg( String idEmpleado) {
		
		boolean ok 				= false;
		try{
			String comando = "DELETE FROM ENOC.EMP_CURRICULUM"+ 
				" WHERE ID_EMPLEADO = ?";
			
				Object[] parametros = new Object[] {idEmpleado};
				if (enocJdbc.update(comando,parametros)==1){
					ok = true;
				}
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpCurriculumDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public EmpCurriculum mapeaRegId( String idEmpleado) {
		EmpCurriculum empCurriculum = new EmpCurriculum();
		
		try{
			String comando = "SELECT ID_EMPLEADO, LUGAR_NAC, T_PROFESIONAL, G_POSGRADO,"
					+ "T_UNIVERSITARIO, RESP_ACTUAL, RESP_ANTERIOR, EXP_DOCENTE, TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ "NACIONALIDAD, NIVEL_ID, REVISADO, T_DOCTORADO, "
					+ "TO_CHAR(FECHA_LIC,'DD/MM/YYYY') AS FECHA_LIC, TO_CHAR(FECHA_MAE,'DD/MM/YYYY') AS FECHA_MAE, TO_CHAR(FECHA_DOC,'DD/MM/YYYY') AS FECHA_DOC,"
					+ "INST_LIC, INST_MAE, INST_DOC FROM ENOC.EMP_CURRICULUM "
					+ "WHERE ID_EMPLEADO = ?";
			
				Object[] parametros = new Object[] {idEmpleado};
				empCurriculum = enocJdbc.queryForObject(comando, new EmpCurriculumMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpCurriculumDao|mapeaRegId|:"+ex);
		}
		return empCurriculum;
	}
	
	public boolean existeReg( String idEmpleado) {
		boolean 		ok 		= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EMP_CURRICULUM WHERE ID_EMPLEADO = ?";		
			Object[] parametros = new Object[] {idEmpleado};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpCurriculumDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public List<EmpCurriculum> getListAll( String orden) {
		List<EmpCurriculum> lista		= new ArrayList<EmpCurriculum>();
		String comando	= "";
		
		try{
			comando = "SELECT ID_EMPLEADO, LUGAR_NAC, T_PROFESIONAL, G_POSGRADO," +
				" T_UNIVERSITARIO, RESP_ACTUAL, RESP_ANTERIOR, EXP_DOCENTE, TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO," +
				" COALESCE(NACIONALIDAD,91) AS NACIONALIDAD, NIVEL_ID, REVISADO, T_DOCTORADO, FECHA_LIC, FECHA_MAE, FECHA_DOC," +
				" INST_LIC, INST_MAE, INST_DOC " +					
				" FROM ENOC.EMP_CURRICULUM "+orden; 
			
			lista = enocJdbc.query(comando, new EmpCurriculumMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpCurriculumDao|getListAll|:"+ex);
		}
		return lista;
	}
}