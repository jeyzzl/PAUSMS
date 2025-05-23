/**
 * 
 */
package aca.alumno.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumEstudioDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( AlumEstudio alumEstudio) {
		boolean ok = false;
		
		try{
			String  comando = "INSERT INTO ENOC.ALUM_ESTUDIO"+ 
					" (CODIGO_PERSONAL, ID, TITULO, INSTITUCION, COMPLETO, INICIO, FIN, DEPENDENCIA, CONVALIDA)"+
					" VALUES(?, TO_NUMBER(?, '99'), ?, ?, ?, ?, ?, ?, ?)";
			Object[] parametros = new Object[] {alumEstudio.getCodigoPersonal(),alumEstudio.getId(),alumEstudio.getTitulo(),alumEstudio.getInstitucion(),alumEstudio.getCompleto(),alumEstudio.getInicio(),alumEstudio.getFin(),alumEstudio.getDependencia(),alumEstudio.getConvalida()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstudioDao|insertReg|:"+ex);			
		}
		
		return ok;
	} 
	
	public boolean updateReg( AlumEstudio alumEstudio) {
		boolean ok = false;
		
		try{
			String  comando = "UPDATE ENOC.ALUM_ESTUDIO"
					+ " SET TITULO = ?,"
					+ " INSTITUCION = ?,"
					+ " COMPLETO = ?," 		
					+ " INICIO = ?,"
					+ " FIN = ?,"
					+ " DEPENDENCIA = ?," 		
					+ " CONVALIDA = ?"
					+ " WHERE CODIGO_PERSONAL = ? AND ID = TO_NUMBER(?, '99')";
			
			Object[] parametros = new Object[] {
					alumEstudio.getTitulo(),alumEstudio.getInstitucion(),alumEstudio.getCompleto(),
					alumEstudio.getCompleto(),alumEstudio.getFin(),alumEstudio.getDependencia(),
					alumEstudio.getConvalida(),alumEstudio.getCodigoPersonal(),alumEstudio.getId()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstudioDao|updateReg|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String codigoPersonal, String id) {
		boolean ok = false;		
		try{
			String  comando = "DELETE FROM ENOC.ALUM_ESTUDIO WHERE CODIGO_PERSONAL = ? AND ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {codigoPersonal, id};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstudioDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public AlumEstudio mapeaRegId(String codigoPersonal, String id){
		AlumEstudio alumEstudio = new AlumEstudio();		
		try{
			String  comando = " SELECT CODIGO_PERSONAL, ID, TITULO, INSTITUCION, COMPLETO, INICIO, FIN, DEPENDENCIA, CONVALIDA"
					 + " FROM ENOC.ALUM_ESTUDIO WHERE CODIGO_PERSONAL = ? AND ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {codigoPersonal, id};
			alumEstudio = enocJdbc.queryForObject(comando, new AlumEstudioMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstudioDao|mapeaRegId|:"+ex);
		}
		
		return alumEstudio;
	}
	
	public AlumEstudio mapeaReg(String codigoPersonal){
		AlumEstudio alumEstudio = new AlumEstudio();		
		try{
			String  comando = " SELECT CODIGO_PERSONAL, ID, TITULO, INSTITUCION, COMPLETO, INICIO, FIN, DEPENDENCIA, CONVALIDA"
					 + " FROM ENOC.ALUM_ESTUDIO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			alumEstudio = enocJdbc.queryForObject(comando, new AlumEstudioMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstudioDao|mapeaReg|:"+ex);
		}
		
		return alumEstudio;
	}
	
	public boolean existeReg(String codigoPersonal, String id) {
		boolean 		ok 	= false;		
		try{
			String  comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ALUM_ESTUDIO WHERE CODIGO_PERSONAL = ? AND ID = TO_NUMBER(?,'99')";
				Object[] parametros = new Object[] {codigoPersonal, id};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstudioDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() {
		String maximo 			= "1";		
		try{
			String  comando = "SELECT COALESCE(MAX(ID)+1,1) AS MAXIMO FROM ENOC.ALUM_ESTUDIO";
			if (enocJdbc.queryForObject(comando, Integer.class ) >= 1) {
				maximo = enocJdbc.queryForObject(comando, String.class);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstudioDao|maximoReg|:"+ex);
		}		
		return maximo;		
	}

	public String maximoReg(String codigoPersonal) {
		String maximo 			= "1";		
		try{
			String  comando = "SELECT COALESCE(MAX(ID)+1,1) AS MAXIMO FROM ENOC.ALUM_ESTUDIO WHERE CODIGO_PERSONAL = ?";
			if (enocJdbc.queryForObject(comando, Integer.class, codigoPersonal ) >= 1) {
				maximo = enocJdbc.queryForObject(comando, String.class, codigoPersonal);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstudioDao|maximoReg|:"+ex);
		}		
		return maximo;		
	}
	
	public List<AlumEstudio> lisAlumEstudio( String codigoPersonal) {
		
		List<AlumEstudio> lisEstudios	= new ArrayList<AlumEstudio>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, ID, TITULO, INSTITUCION, COMPLETO, INICIO, FIN, DEPENDENCIA, CONVALIDA"
					 + " FROM ENOC.ALUM_ESTUDIO WHERE CODIGO_PERSONAL = ?";
			lisEstudios = enocJdbc.query(comando, new AlumEstudioMapper(), codigoPersonal);
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstudioDao|lisAlumEstudio|:"+ex);
		}		
		return lisEstudios;
	}	
}