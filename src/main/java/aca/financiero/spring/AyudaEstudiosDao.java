package aca.financiero.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AyudaEstudiosDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public AyudaEstudios mapeaRegId(String id) {		
		AyudaEstudios com = new AyudaEstudios();
		try{
			String comando = "SELECT ID, ALUMNO, MATRICULA, INTERNADO, ENSENANZA, STATUS, EMPLEADO_ID, DEPENDIENTE_ID, DATECAPTURA"
					+ " FROM ARON.AYUDA_ESTUDIOS"
					+ " WHERE ID = TO_NUMBER(?,'9999')";
			Object[] parametros = new Object[] { id };
			com = enocJdbc.queryForObject(comando, new AyudaEstudiosMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.AyudaEstudiosDao|mapeaRegId|:"+ex);
		}		
		return com;
	}
	
	public AyudaEstudios mapeaRegId(String alumno, String status) {		
		AyudaEstudios com = new AyudaEstudios();		
		try{
			String comando = "SELECT ID, ALUMNO, MATRICULA, INTERNADO, ENSENANZA, STATUS, EMPLEADO_ID, DEPENDIENTE_ID, DATECAPTURA"
					+ " FROM ARON.AYUDA_ESTUDIOS"
					+ " WHERE ALUMNO = ? AND STATUS = ?";
			Object[] parametros = new Object[] { alumno, status };
			com = enocJdbc.queryForObject(comando, new AyudaEstudiosMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.AyudaEstudiosDao|mapeaRegId|:"+ex);
		}		
		return com;
	}
	
	public boolean existeReg( String id) {
		boolean ok 			= false;		
		try{
			String comando ="SELECT COUNT(*) FROM ARON.AYUDA_ESTUDIOS WHERE ID = TO_NUMBER(?, '9999')";
			Object[] parametros = new Object[] { id };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.AyudaEstudiosDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean existeReg( String alumno, String status){
		boolean ok 			= false;		
		try{
			String comando ="SELECT COUNT(*) FROM ARON.AYUDA_ESTUDIOS WHERE ALUMNO = ? AND STATUS = ?";
			Object[] parametros = new Object[] { alumno, status };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.AyudaEstudiosDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public int becasRegistradas( String alumno, String year, String status) {
		int total	= 0;		
		try{
			String comando ="SELECT COUNT(*) FROM ARON.AYUDA_ESTUDIOS WHERE ALUMNO = ? AND TO_CHAR(DATECAPTURA,'YYYY') = ? AND STATUS = ?";
			Object[] parametros = new Object[] { alumno, year, status };	
			total = enocJdbc.queryForObject(comando, Integer.class, parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.AyudaEstudiosDao|existeReg|:"+ex);
		}		
		return total;
	}
	
	public List<FinComentario> getListAll( String orden ) {
		
		List<FinComentario> lista = new ArrayList<FinComentario>();		
		try{
			String comando = "SELECT ID, ALUMNO, MATRICULA, INTERNADO, ENSENANZA, STATUS, EMPLEADO_ID, DEPENDIENTE_ID, DATECAPTURA FROM ARON.AYUDA_ESTUDIOS "+ orden;
			lista = enocJdbc.query(comando, new FinComentarioMapper());		
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.AyudaEstudiosDao|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public List<FinComentario> lisAlumnoComentarios( String alumno, String orden ) {		
		List<FinComentario> lista = new ArrayList<FinComentario>();		
		try{
			String comando = " SELECT ID, ALUMNO, MATRICULA, INTERNADO, ENSENANZA, STATUS, EMPLEADO_ID, DEPENDIENTE_ID, DATECAPTURA"
					+ " FROM ARON.AYUDA_ESTUDIOS"
					+ " WHERE ALUMNO = ? "+orden;
			Object[] parametros = new Object[] { alumno };
			lista = enocJdbc.query(comando, new FinComentarioMapper(), parametros);	
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinComentarioDao|lisAlumnoComentarios|:"+ex);
		}		
		return lista;
	}
	
}
