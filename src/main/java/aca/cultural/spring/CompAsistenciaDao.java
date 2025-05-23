package aca.cultural.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CompAsistenciaDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(CompAsistencia objeto ) {
		boolean ok = false;	
		try{
			String comando = "INSERT INTO ENOC.COMP_ASISTENCIA"+ 
				"(EVENTO_ID,  NOMBRE, ESTADO, FECHA, HORA) "+
				"VALUES(TO_NUMBER(?,'9999'), ?, ?, TO_DATE(?,'YYYY/MM/DD'), ?)";			
			Object[] parametros = new Object[] {
				objeto.getEventoId(), objeto.getNombre(), objeto.getEstado(), objeto.getFecha(), objeto.getHora()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompAsistenciaDao|insertReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean updateReg( CompAsistencia objeto ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.COMP_ASISTENCIA"
				+ " SET NOMBRE = ?,"
				+ " ESTADO = ?,"
				+ " FECHA = TO_DATE(?,'YYYY/MM/DD'),"
				+ " HORA = ?"
				+ " WHERE EVENTO_ID = TO_NUMBER(?,'9999')";			
			Object[] parametros = new Object[] {
				objeto.getNombre(), objeto.getEstado(),objeto.getFecha(), objeto.getHora(), objeto.getEventoId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompAsistenciaDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg(String eventoId) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.COMP_ASISTENCIA WHERE EVENTO_ID = TO_NUMBER(?,'9999')";			
			Object[] parametros = new Object[] {eventoId};					
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompAsistenciaDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean existeReg(String eventoId) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.COMP_ASISTENCIA WHERE EVENTO_ID = TO_NUMBER(?,'9999')";			
			Object[] parametros = new Object[] {eventoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompAsistenciaDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public CompAsistencia mapeaRegId(String eventoId) {
		CompAsistencia objeto = new CompAsistencia();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.COMP_ASISTENCIA WHERE EVENTO_ID = TO_NUMBER(?,'9999')";			
			Object[] parametros = new Object[] {eventoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = "SELECT EVENTO_ID, NOMBRE, ESTADO, TO_CHAR(FECHA,'YYYY/MM/DD') AS FECHA, HORA FROM ENOC.COMP_ASISTENCIA WHERE EVENTO_ID = TO_NUMBER(?,'9999')";				
				objeto = enocJdbc.queryForObject(comando, new CompAsistenciaMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompAsistenciaDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}

	public String maxReg(){
		String max = "0";		
		try{
			String comando = "SELECT COALESCE(MAX(EVENTO_ID)+1,1) FROM ENOC.COMP_ASISTENCIA";
			max = enocJdbc.queryForObject(comando,String.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompAsistenciaDao|maxReg|:"+ex);
		}
		return max;
	}
	
	public List<CompAsistencia> lisTodos( String orden ) {
		List<CompAsistencia> lista = new ArrayList<CompAsistencia>();		
		try{
			String comando = "SELECT EVENTO_ID, NOMBRE, ESTADO, TO_CHAR(FECHA,'YYYY/MM/DD') AS FECHA, HORA "
					+ " FROM ENOC.COMP_ASISTENCIA "+orden;			
			lista = enocJdbc.query(comando, new CompAsistenciaMapper());		
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompAsistenciaDao|lisTodos|:"+ex);
		}
		return lista;
	}

	public List<CompAsistencia> listaActivos( String orden) {
		List<CompAsistencia> lista = new ArrayList<CompAsistencia>();		
		try{
			String comando = "SELECT EVENTO_ID,  NOMBRE, ESTADO, TO_CHAR(FECHA,'YYYY/MM/DD') AS FECHA, HORA"
					+ " FROM ENOC.COMP_ASISTENCIA WHERE ESTADO = 'A' "+ orden;			
			lista = enocJdbc.query(comando, new CompAsistenciaMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompAsistenciaDao|listaActivos|:"+ex);
		}
		return lista;
	}
}