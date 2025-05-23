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
public class HcaMaestroActividadDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( HcaMaestroActividad hcaMaestroActividad){ 
		boolean ok 				= false;
		try{
			
			// valor de horas a 1
			if (hcaMaestroActividad.getHoras().equals("")||hcaMaestroActividad.getHoras()==null||hcaMaestroActividad.getHoras().equals(" ")) hcaMaestroActividad.setHoras("1");
			
			String comando = "INSERT INTO ENOC.HCA_MAESTRO_ACTIVIDAD(CODIGO_PERSONAL, CARGA_ID, ACTIVIDAD_ID, SEMANAS, HORAS)" +
				" VALUES(?, ?, TO_NUMBER(?, '999'), TO_NUMBER(?, '99'), TO_NUMBER(?,'999.99'))";
			Object[] parametros = new Object[] {
					hcaMaestroActividad.getCodigoPersonal(),hcaMaestroActividad.getCargaId(),hcaMaestroActividad.getActividadId(),
					hcaMaestroActividad.getSemanas(),hcaMaestroActividad.getHoras()	
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroActividadDao|insertReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean updateReg( HcaMaestroActividad hcaMaestroActividad) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.HCA_MAESTRO_ACTIVIDAD" + 
				" SET SEMANAS = TO_NUMBER(?, '99')," +
				" HORAS = TO_NUMBER(?,'999.99')" +
				" WHERE CODIGO_PERSONAL = ?" +
				" AND CARGA_ID = ?" +
				" AND ACTIVIDAD_ID = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] {
					hcaMaestroActividad.getSemanas(),hcaMaestroActividad.getHoras(), hcaMaestroActividad.getCodigoPersonal(), hcaMaestroActividad.getCargaId(), 
					hcaMaestroActividad.getActividadId()						
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroActividadDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg( String codigoPersonal, String cargaId, String actividadId) {
		boolean ok 				= false;
		try{
			String comando = "DELETE FROM ENOC.HCA_MAESTRO_ACTIVIDAD"+ 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND CARGA_ID = ?" +
				" AND ACTIVIDAD_ID = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] { codigoPersonal,cargaId,actividadId };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroActividadDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public HcaMaestroActividad mapeaRegId(String codigoPersonal, String cargaId, String actividadId){
		HcaMaestroActividad hcaMaestroActividad = new HcaMaestroActividad();
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, ACTIVIDAD_ID, SEMANAS, HORAS FROM ENOC.HCA_MAESTRO_ACTIVIDAD"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID = ?"
					+ " AND ACTIVIDAD_ID = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] { codigoPersonal,cargaId,actividadId };
			hcaMaestroActividad = enocJdbc.queryForObject(comando, new HcaMaestroActividadMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroActividadDao|mapeaRegId|:"+ex);
		}
		return hcaMaestroActividad;
	}
	
	public boolean existeReg( String codigoPersonal, String cargaId, String actividadId) {
		boolean 		ok 		= false;
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.HCA_MAESTRO_ACTIVIDAD" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CARGA_ID = ?" +
					" AND ACTIVIDAD_ID = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] { codigoPersonal, cargaId,actividadId };		
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroActividadDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean existeActividad( String actividadId) {
		boolean ok		= false;		
		try{
			String comando = "SELECT COUNT(ACTIVIDAD_ID) FROM ENOC.HCA_MAESTRO_ACTIVIDAD WHERE ACTIVIDAD_ID = TO_NUMBER(?,'999')";					
			if (enocJdbc.queryForObject(comando,Integer.class,actividadId) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroActividadDao|existeActividad|:"+ex);
		}	
		return ok;
	}
	
	public List<HcaMaestroActividad> lisPorMaestro( String codigoPersonal, String orden ){
		List<HcaMaestroActividad> lista	= new ArrayList<HcaMaestroActividad>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, ACTIVIDAD_ID, SEMANAS, HORAS FROM ENOC.HCA_MAESTRO_ACTIVIDAD" + 
				" WHERE CODIGO_PERSONAL = ? " +orden;
			lista = enocJdbc.query(comando, new HcaMaestroActividadMapper(), codigoPersonal);
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroActividadDao|getListMaestroCarga|:"+ex);
		}		
		return lista;
	}
	
	public List<HcaMaestroActividad> getListMaestroCarga( String codigoPersonal, String cargaId, String orden ){
		List<HcaMaestroActividad> lista	= new ArrayList<HcaMaestroActividad>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, ACTIVIDAD_ID, SEMANAS, HORAS FROM ENOC.HCA_MAESTRO_ACTIVIDAD" + 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND CARGA_ID = ? " +orden;
			lista = enocJdbc.query(comando, new HcaMaestroActividadMapper(), codigoPersonal, cargaId);
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroActividadDao|getListMaestroCarga|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String, String> mapaTotalHoras( String cargaId){
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String, String>();		
		try{
			String comando = "SELECT A.CODIGO_PERSONAL||A.CARGA_ID||B.TIPO_ID AS LLAVE, SUM(SEMANAS*HORAS*B.VALOR) AS VALOR"
					+ " FROM ENOC.HCA_MAESTRO_ACTIVIDAD A, ENOC.HCA_ACTIVIDAD B "
					+ " WHERE A.CARGA_ID = ?"
					+ " AND B.ACTIVIDAD_ID = A.ACTIVIDAD_ID"
					+ " GROUP BY A.CODIGO_PERSONAL, A.CARGA_ID, B.TIPO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroActividadDao|mapaTotalHoras|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaActividades(){
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String, String>();		
		try{
			String comando = "SELECT ACTIVIDAD_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM HCA_MAESTRO_ACTIVIDAD GROUP BY ACTIVIDAD_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroActividadDao|mapaActividades|:"+ex);
		}		
		return mapa;
	}	
}