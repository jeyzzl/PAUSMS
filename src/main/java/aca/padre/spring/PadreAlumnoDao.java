//BEANS DE LA TABLA ALUM_PADRE

package aca.padre.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PadreAlumnoDao {	
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(PadreAlumno padreAlumno ){
 		boolean ok = false;
 		
 		try{
 			String comando = "INSERT INTO ENOC.PADRE_ALUMNO(PADRE_ID, CODIGO_PERSONAL, FECHA_ALTA, FECHA_AUTORIZA, ESTADO) "+
 				"VALUES( ?, ?, TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), ?)";
 			
 			Object[] parametros = new Object[] {
				padreAlumno.getPadreId(),padreAlumno.getCodigoPersonal(),padreAlumno.getFechaAlta(),padreAlumno.getFechaAutoriza(),padreAlumno.getEstado()
 			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
 		}catch(Exception ex){
 			System.out.println("Error - aca.padre.spring.PadreAlumno|insertReg|:"+ex);	
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(PadreAlumno padreAlumno ){
 		boolean ok = false;
 		
 		try{
 			String comando = "UPDATE ENOC.PADRE_ALUMNO SET FECHA_ALTA = TO_DATE(?,'DD/MM/YYYY'), FECHA_AUTORIZA = TO_DATE(?,'DD/MM/YYYY'), ESTADO = ? " 				 	
 				+ " WHERE PADRE_ID = ? AND CODIGO_PERSONAL = ?";
 				
 			Object[] parametros = new Object[] {
 					padreAlumno.getFechaAlta(),padreAlumno.getFechaAutoriza(),padreAlumno.getEstado(),padreAlumno.getPadreId(),padreAlumno.getCodigoPersonal()
 	 		};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
 		}catch(Exception ex){
 			System.out.println("Error - aca.padre.spring.PadreAlumno|updateReg|:"+ex);
 		}
 		
 		return ok;
 	}
 	
 	public boolean updateAutoriza(String padreId, String codigoPersonal, String fechaAutoriza, String estado ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.PADRE_ALUMNO SET FECHA_AUTORIZA = TO_DATE(?,'DD/MM/YYYY'), ESTADO = ?" +
				" WHERE PADRE_ID = ? AND CODIGO_PERSONAL = ? ";

 			Object[] parametros = new Object[] {
 					fechaAutoriza, estado, padreId, codigoPersonal
 	 		};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.padre.spring.PadreAlumno|updateReg|:"+ex);
		}
		
		return ok;
	} 	
 	
 	public PadreAlumno mapeaRegId( String padreId, String codigoPersonal ){
 		PadreAlumno padreAlumno = new PadreAlumno();
 		
 		try{
	 		String comando = "SELECT PADRE_ID, CODIGO_PERSONAL, TO_CHAR(FECHA_ALTA,'DD/MM/YYYY') AS FECHA_ALTA, TO_CHAR(FECHA_AUTORIZA,'DD/MM/YYYY') AS FECHA_AUTORIZA, ESTADO"
	 			+ " FROM ENOC.PADRE_ALUMNO WHERE PADRE_ID = ? AND CODIGO_PERSONAL = ?";
	 		
	 		Object[] parametros = new Object[] {padreId,codigoPersonal};
	 		padreAlumno = enocJdbc.queryForObject(comando, new PadreAlumnoMapper(), parametros);
 		}catch(Exception ex){
 			System.out.println("Error - aca.padre.spring.PadreAlumno|mapeaRegId|:"+ex);
 		}
 		
 		return padreAlumno;
 	}
 	
 	public boolean deleteReg(String padreId, String codigoPersonal  ){
 		boolean ok = false;
 		
 		try{
 			String comando = "DELETE FROM ENOC.PADRE_ALUMNO WHERE PADRE_ID = ? AND CODIGO_PERSONAL = ? ";
 			
 			Object[] parametros = new Object[] {padreId,codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.padre.spring.PadreAlumno|deleteReg|:"+ex);			
 		}

 		return ok;
 	}
 	
 	public boolean existeReg(String padreId, String codigoPersonal){
 		boolean 		ok 	= false;
 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.PADRE_ALUMNO WHERE PADRE_ID = ? AND CODIGO_PERSONAL = ?";
 			
 			Object[] parametros = new Object[] {padreId,codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.padre.spring.PadreAlumno|existeReg|:"+ex);
 		}
 		
 		return ok;
 	}
	
	public List<PadreAlumno> getLista(String codigoPadre, String orden ){
		List<PadreAlumno> lista			= new ArrayList<PadreAlumno>();
			
		try{
			String comando = "SELECT PADRE_ID, CODIGO_PERSONAL, FECHA_ALTA, FECHA_AUTORIZA, ESTADO FROM ENOC.PADRE_ALUMNO WHERE PADRE_ID = ? "+orden;			
			lista = enocJdbc.query(comando, new PadreAlumnoMapper(), codigoPadre );			
		}catch(Exception ex){
			System.out.println("Error - aca.padre.spring.PadreAlumnoUtil|getLista|:"+ex);
		}
			
		return lista;
	} 	
	
	public List<PadreAlumno> getListaPadres(String codigoAlumno, String estado, String orden ){
		List<PadreAlumno> lista			= new ArrayList<PadreAlumno>();
			
		try{
			String comando = "SELECT PADRE_ID, CODIGO_PERSONAL, FECHA_ALTA, FECHA_AUTORIZA, ESTADO FROM ENOC.PADRE_ALUMNO WHERE CODIGO_PERSONAL = ?"
					  + " AND ESTADO IN("+estado+") "+orden;
			lista = enocJdbc.query(comando, new PadreAlumnoMapper(), codigoAlumno);
				
		}catch(Exception ex){
			System.out.println("Error - aca.padre.spring.PadreAlumnoUtil|getLista|:"+ex);
		}
			
		return lista;
	}
	
}