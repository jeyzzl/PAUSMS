/**
 * 
 */
package aca.hca.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class HcaActividadDao {	
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( HcaActividad hcaActividad){

		boolean ok 				= false;
		try{
			String comando = "INSERT INTO ENOC.HCA_ACTIVIDAD(TIPO_ID, ACTIVIDAD_ID, ACTIVIDAD_NOMBRE, VALOR, NIVEL_ID, MODIFICABLE)" +
				" VALUES(TO_NUMBER(?, '99'), TO_NUMBER(?, '999')," +
				" ?, TO_NUMBER(?, '999.99'), TO_NUMBER(?, '99'), ?)";			
			Object[] parametros = new Object[] { 
				hcaActividad.getTipoId(), hcaActividad.getActividadId(), hcaActividad.getActividadNombre(), hcaActividad.getValor(),
				hcaActividad.getNivelId(), hcaActividad.getModificable()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaActividadDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( HcaActividad hcaActividad){
		boolean ok = false;

		try{
			String comando = "UPDATE ENOC.HCA_ACTIVIDAD" + 
				" SET TIPO_ID = TO_NUMBER(?, '99')," +
				" ACTIVIDAD_NOMBRE = ?," +
				" VALOR = TO_NUMBER(?, '999.99')," +
				" NIVEL_ID = TO_NUMBER(?, '99')," +
				" MODIFICABLE = ?" +
				" WHERE ACTIVIDAD_ID = TO_NUMBER(?, '999')";
			
			Object[] parametros = new Object[] { hcaActividad.getTipoId(), hcaActividad.getActividadNombre(), hcaActividad.getValor(), hcaActividad.getNivelId(),
					hcaActividad.getModificable(), hcaActividad.getActividadId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaActividadDao|updateReg|:"+ex);		
		}

		return ok;
	}	
	
	public boolean deleteReg( String actividadId){

		boolean ok 				= false;
		try{
			String comando = "DELETE FROM ENOC.HCA_ACTIVIDAD"+ 
				" WHERE ACTIVIDAD_ID = TO_NUMBER(?, '999')";
			
			Object[] parametros = new Object[] {actividadId};
 			if (enocJdbc.update(comando, parametros)==1){
 				ok = true;
 			}	
			
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaActividadDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public HcaActividad mapeaRegId(String actividadId){
		HcaActividad hcaActividad = new HcaActividad();

		try{ 
			String comando = "SELECT TIPO_ID, ACTIVIDAD_ID," +
					" ACTIVIDAD_NOMBRE, VALOR, NIVEL_ID, MODIFICABLE " +
					" FROM ENOC.HCA_ACTIVIDAD" + 
					" WHERE ACTIVIDAD_ID = TO_NUMBER(?, '999')";			
			Object[] parametros = new Object[] {actividadId};			
			hcaActividad = enocJdbc.queryForObject(comando, new HcaActividadMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaActividadDao|mapeaRegId|:"+ex);
		}
		
		return hcaActividad;
	}
	
	public boolean existeReg( String actividadId){
		boolean 		ok 		= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.HCA_ACTIVIDAD WHERE ACTIVIDAD_ID = TO_NUMBER(?, '999')";			
			Object[] parametros = new Object[] {actividadId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaActividadDao|existeReg|:"+ex);
		}
	
		return ok;
	}
	
	public String maximoReg(){		
		String maximo			= "1";		
		try{
			String comando = "SELECT COALESCE(MAX(ACTIVIDAD_ID)+1,1) AS MAXIMO FROM ENOC.HCA_ACTIVIDAD";			
			maximo = enocJdbc.queryForObject(comando, String.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaActividadDao|maximoReg|:"+ex);
		}

		return maximo;
	}
	
	public String getModificable( String actividadId){
		
		String modificable		= "1";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.HCA_ACTIVIDAD WHERE ACTIVIDAD_ID = TO_NUMBER(?,'999')"; 
			Object[] parametros = new Object[] {actividadId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT MODIFICABLE FROM ENOC.HCA_ACTIVIDAD WHERE ACTIVIDAD_ID = TO_NUMBER(?,'999')";			
				modificable = enocJdbc.queryForObject(comando, String.class, parametros);
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaActividadDao|getModificable|:"+ex);
		}

		return modificable;
	}
	
	public List<HcaActividad> getListAll( String orden ){
		
		List<HcaActividad> lisActividad	= new ArrayList<HcaActividad>();
		
		try{
			String comando = "SELECT ACTIVIDAD_ID, TIPO_ID, ACTIVIDAD_NOMBRE, VALOR, NIVEL_ID, MODIFICABLE" +
					" FROM ENOC.HCA_ACTIVIDAD "+orden; 
			
			lisActividad = enocJdbc.query(comando, new HcaActividadMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaActividadDao|getListAll|:"+ex);
		}
		
		return lisActividad;
	}
	
	public List<aca.Mapa> getListModificable( String orden ){
		
		List<aca.Mapa> lisMod	= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT ACTIVIDAD_ID, MODIFICABLE FROM ENOC.HCA_ACTIVIDAD "+orden; 
			lisMod = enocJdbc.query(comando, new aca.MapaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaActividadDao|getListModificable|:"+ex);
		}
		
		return lisMod;
	}
	
	public HashMap<String, HcaActividad> mapaActividades(){
		HashMap<String, HcaActividad> mapa	= new HashMap<String, HcaActividad>();
		List<HcaActividad> lista	= new ArrayList<HcaActividad>();		
		try{
			String comando = "SELECT ACTIVIDAD_ID, TIPO_ID, ACTIVIDAD_NOMBRE, VALOR, NIVEL_ID, MODIFICABLE FROM ENOC.HCA_ACTIVIDAD";
			lista = enocJdbc.query(comando, new HcaActividadMapper());
			for(HcaActividad objeto : lista){				
				mapa.put(objeto.getActividadId(), objeto);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.HcaActividadDao|mapaCursosPorMaestro|:"+ex);
		}			
		return mapa;
	}
	
}