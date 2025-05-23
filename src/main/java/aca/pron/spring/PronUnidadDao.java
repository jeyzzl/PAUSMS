package aca.pron.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class PronUnidadDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(PronUnidad pronUnidad) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.PRON_UNIDAD(CURSO_CARGA_ID, UNIDAD_ID, APORTE, ORDEN)"
					+ " VALUES(?,TO_NUMBER(?,'99'),?,TO_NUMBER(?,'9999.99'))";

			Object[] parametros = new Object[] {
				pronUnidad.getCursoCargaId(),pronUnidad.getUnidadId(),pronUnidad.getAporte(),pronUnidad.getOrden()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronUnidadDao|insertReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean updateReg(PronUnidad pronUnidad) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.PRON_UNIDAD SET APORTE = ?, ORDEN = TO_NUMBER(?,'999.99') WHERE CURSO_CARGA_ID = ? AND UNIDAD_ID = ?";
			
			Object[] parametros = new Object[] {
				pronUnidad.getAporte(),pronUnidad.getOrden(),pronUnidad.getCursoCargaId(),pronUnidad.getUnidadId()
			};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronUnidadDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg(String cursoCargaId, String unidadId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.PRON_UNIDAD WHERE CURSO_CARGA_ID = ? AND UNIDAD_ID = ?";
			
			Object[] parametros = new Object[] {cursoCargaId, unidadId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronUnidadDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public PronUnidad mapeaRegId(String cursoCargaId, String unidadId) {
		PronUnidad pronUnidad = new PronUnidad();
		try{
			String comando = "SELECT CURSO_CARGA_ID, UNIDAD_ID, APORTE, ORDEN FROM ENOC.PRON_UNIDAD WHERE CURSO_CARGA_ID = ? AND UNIDAD_ID = ?";
			
			Object[] parametros = new Object[] {cursoCargaId, unidadId};
			pronUnidad = enocJdbc.queryForObject(comando, new PronUnidadMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronUnidadDao|mapeaRegId|:"+ex);
		}
		
		return pronUnidad;		
	}	
	
	public boolean existeReg(String cursoCargaId, String unidadId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.PRON_UNIDAD WHERE CURSO_CARGA_ID = ? AND UNIDAD_ID = ?"; 
			
			Object[] parametros = new Object[] {cursoCargaId, unidadId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronUnidadDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public HashMap<String, PronUnidad> mapaUnidad(String cursoCargaId) {
		HashMap<String, PronUnidad> mapa 	= new HashMap<String, PronUnidad>();
		List<PronUnidad> lista			= new ArrayList<PronUnidad>();
		
		try{
			Object[] parametros = new Object[] {cursoCargaId};
			String comando = "SELECT CURSO_CARGA_ID, UNIDAD_ID, APORTE, ORDEN FROM PRON_UNIDAD WHERE CURSO_CARGA_ID = ?"; 
			lista = enocJdbc.query(comando, new PronUnidadMapper(), parametros);
			for (PronUnidad unidad : lista ) {
				mapa.put(unidad.getUnidadId(),unidad);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronUnidadDao|mapaUnidad|:"+ex);
		}
		
		return mapa;
	}

}
