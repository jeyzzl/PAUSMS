package aca.pron.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PronSemanaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(PronSemana pronSemana) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.PRON_SEMANA(CURSO_CARGA_ID, UNIDAD_ID, SEMANA_ID, SEMANA_NOMBRE, CONTENIDO, ACTIVIDADES, EVIDENCIAS, ORDEN)"
					+ " VALUES(?,TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),?,?,?,?,TO_NUMBER(?,'9999.99'))";

			Object[] parametros = new Object[] {
				pronSemana.getCursoCargaId(),pronSemana.getUnidadId(),pronSemana.getSemanaId(),pronSemana.getSemanaNombre(),pronSemana.getContenido(),
				pronSemana.getActividades(),pronSemana.getEvidencias(),pronSemana.getOrden()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronSemanaDao|insertReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean updateReg(PronSemana pronSemana) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.PRON_SEMANA SET SEMANA_NOMBRE = ?, CONTENIDO = ?, ACTIVIDADES = ?, EVIDENCIAS = ?, ORDEN = TO_NUMBER(?,'9999.99') "
					+ " WHERE CURSO_CARGA_ID = ? AND UNIDAD_ID = TO_NUMBER(?,'99') AND SEMANA_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {
				pronSemana.getSemanaNombre(),pronSemana.getContenido(),pronSemana.getActividades(),pronSemana.getEvidencias(),pronSemana.getOrden(),
				pronSemana.getCursoCargaId(),pronSemana.getUnidadId(),pronSemana.getSemanaId()
			};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronSemanaDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg(String cursoCargaId, String unidadId, String semanaId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.PRON_SEMANA WHERE CURSO_CARGA_ID = ? AND UNIDAD_ID = TO_NUMBER(?,'99') AND SEMANA_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {cursoCargaId,unidadId,semanaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronSemanaDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public PronSemana mapeaRegId(String cursoCargaId, String unidadId, String semanaId) {
		PronSemana pronSemana = new PronSemana();
		try{
			String comando = "SELECT CURSO_CARGA_ID, UNIDAD_ID, SEMANA_ID, SEMANA_NOMBRE, CONTENIDO, ACTIVIDADES, EVIDENCIAS, ORDEN FROM ENOC.PRON_SEMANA "
					+ " WHERE CURSO_CARGA_ID = ? AND UNIDAD_ID = TO_NUMBER(?,'99') AND SEMANA_ID = TO_NUMBER(?,'99')";
			
				Object[] parametros = new Object[] {cursoCargaId,unidadId,semanaId};
				pronSemana = enocJdbc.queryForObject(comando, new PronSemanaMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronSemanaDao|mapeaRegId|:"+ex);
		}
		
		return pronSemana;		
	}	

	public boolean existeReg(String cursoCargaId, String unidadId, String semanaId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.PRON_SEMANA WHERE CURSO_CARGA_ID = ? AND UNIDAD_ID = TO_NUMBER(?,'99') AND SEMANA_ID = TO_NUMBER(?,'99')"; 
			
			Object[] parametros = new Object[] {cursoCargaId,unidadId,semanaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronSemanaDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String getSemanaId(String cursoCargaId, String unidadId ) {
		String semana = "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(SEMANA_ID)+1,1) SEMANA FROM ENOC.PRON_SEMANA WHERE CURSO_CARGA_ID = ? AND UNIDAD_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId,unidadId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				semana = enocJdbc.queryForObject(comando, String.class, parametros);
			}
 			
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronSemanaDao|getSemanaId|:"+ex);
		}
		return semana;
	}

	public List<aca.pron.spring.PronSemana> listaSemanasUnidad(String cursoCargaId, String unidadId ) {
		List<aca.pron.spring.PronSemana> lista = new ArrayList<aca.pron.spring.PronSemana>();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, UNIDAD_ID, SEMANA_ID, SEMANA_NOMBRE, CONTENIDO, ACTIVIDADES, EVIDENCIAS, ORDEN "
					+ " FROM ENOC.PRON_SEMANA WHERE CURSO_CARGA_ID = ? AND UNIDAD_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId,unidadId};
			lista = enocJdbc.query(comando, new PronSemanaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronSemanaDao|listaSemanasUnidad|:"+ex);
		}
		return lista;
	}

	public List<aca.pron.spring.PronSemana> listaSemanasUnidad(String cursoCargaId) {
		List<aca.pron.spring.PronSemana> lista = new ArrayList<aca.pron.spring.PronSemana>();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, UNIDAD_ID, SEMANA_ID, SEMANA_NOMBRE, CONTENIDO, ACTIVIDADES, EVIDENCIAS, ORDEN "
					+ " FROM ENOC.PRON_SEMANA WHERE CURSO_CARGA_ID = ? ORDER BY UNIDAD_ID, SEMANA_ID";
			Object[] parametros = new Object[] {cursoCargaId};
			lista = enocJdbc.query(comando, new PronSemanaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronSemanaDao|mapaSemanasUnidad|:"+ex);
		}
		return lista;
	}

}
