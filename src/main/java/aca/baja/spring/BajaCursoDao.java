package aca.baja.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BajaCursoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( BajaCurso bajaCurso) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.BAJA_CURSO"+ 
				"(BAJA_ID, CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CREDITOS)"+
				" VALUES(TO_NUMBER(?, '9999999'), ?, ?, ?, TO_NUMBER(?, '9999999'))";
								
			Object[] parametros = new Object[] {bajaCurso.getBajaId(),bajaCurso.getCodigoPersonal(),bajaCurso.getCursoCargaId(),bajaCurso.getCursoId(),bajaCurso.getCreditos()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.baja.spring.BajaCursoDao|insertReg|:"+ex);			
		}
		return ok;
	}
	
	public BajaCurso mapeaRegId( String bajaId) {
		BajaCurso bajaCurso = new BajaCurso();
		
		try{
			String comando = "SELECT BAJA_ID, CODIGO_PERSONAL," +
					" CURSO_CARGA_ID, CURSO_ID, CREDITOS" +
					" FROM ENOC.BAJA_CURSO WHERE BAJA_ID = ? "; 
			
			Object[] parametros = new Object[] {bajaId};
			bajaCurso = enocJdbc.queryForObject(comando, new BajaCursoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.baja.spring.BajaCursoDao|mapeaRegId|:"+ex);
		}
		return bajaCurso;
	}
	
	public List<BajaCurso> getListAlumno( String bajaId, String codigoPersonal, String orden) {
		List<BajaCurso> lista 	= new ArrayList<BajaCurso>();
		String comando				= "";
		
		try{
			comando = "SELECT BAJA_ID, CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CREDITOS" +
					" FROM ENOC.BAJA_CURSO" + 
					" WHERE BAJA_ID = " + bajaId +
					" AND CODIGO_PERSONAL = ? "+orden;
			
			lista = enocJdbc.query(comando, new BajaCursoMapper(), codigoPersonal);
			
		}catch(Exception ex){
			System.out.println("Error - aca.baja.spring.BajaCursoDao|getListAlumno|:"+ex);
		}
		return lista;
	}
}