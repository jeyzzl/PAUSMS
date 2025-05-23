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
public class HcaMaestroEstadoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( HcaMaestroEstado hcaMaestroEstado) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.HCA_MAESTRO_ESTADO" +
				"(CODIGO_PERSONAL, CARGA_ID, T_SEMANAL, T_SEMESTRAL, ESTADO)" +
				" VALUES(?, ?, TO_NUMBER(?, '9999.99'), TO_NUMBER(?, '9999.99'), TO_NUMBER(?, '9'))";
			Object[] parametros = new Object[] {
				hcaMaestroEstado.getCodigoPersonal(), hcaMaestroEstado.getCargaId(), hcaMaestroEstado.getSemanal(),
				hcaMaestroEstado.getSemestral(),hcaMaestroEstado.getEstado()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroEstadoDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( HcaMaestroEstado hcaMaestroEstado) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.HCA_MAESTRO_ESTADO" + 
				" SET T_SEMANAL = TO_NUMBER(?, '9999.99')," +
				" T_SEMESTRAL = TO_NUMBER(?, '9999.99')," +
				" ESTADO = TO_NUMBER(?, '9')" +
				" WHERE CODIGO_PERSONAL = ?" +
				" AND CARGA_ID = ?";
			Object[] parametros = new Object[] {hcaMaestroEstado.getSemanal(),hcaMaestroEstado.getSemestral(),hcaMaestroEstado.getEstado(),hcaMaestroEstado.getCodigoPersonal(),hcaMaestroEstado.getCargaId()};
					
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroEstadoDao|updateReg|:"+ex);		
		}		
		return ok;
	}	
	
	public boolean deleteReg( String codigoPersonal, String cargaId) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.HCA_MAESTRO_ESTADO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?";			
			Object[] parametros = new Object[] {codigoPersonal,cargaId};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroEstadoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
			
	public HcaMaestroEstado mapeaRegId(String codigoPersonal, String cargaId){
		HcaMaestroEstado hcaMaestroEstado = new HcaMaestroEstado();
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.HCA_MAESTRO_ESTADO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?";			
			Object[] parametros = new Object[] {codigoPersonal,cargaId};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = " SELECT CODIGO_PERSONAL, CARGA_ID, T_SEMANAL, T_SEMESTRAL, ESTADO"
						+ " FROM ENOC.HCA_MAESTRO_ESTADO"
						+ " WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?";			
				hcaMaestroEstado = enocJdbc.queryForObject(comando, new HcaMaestroEstadoMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroEstadoDao|mapeaRegId|:"+ex);
		}	
		return hcaMaestroEstado;
	}
	
	public boolean existeReg( String codigoPersonal, String cargaId){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.HCA_MAESTRO_ESTADO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CARGA_ID = ?";
			
			Object[] parametros = new Object[] {codigoPersonal,cargaId};
			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroEstadoDao|existeReg|:"+ex);
		}	
		return ok;
	}	
	
	public int getEstadoCargaDocente( String codigoPersonal, String cargaId){
		int estado 				= 1;		
		try{
			String comando = "SELECT COUNT(ESTADO) FROM ENOC.HCA_MAESTRO_ESTADO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?";			
			Object[] parametros = new Object[] {codigoPersonal,cargaId};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT COALESCE(ESTADO,1) AS EDO FROM ENOC.HCA_MAESTRO_ESTADO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?";
				estado = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroEstadoDao|getEstadoCargaDocente|:"+ex);
		}
		return estado;
	}
	
	public List<HcaMaestroEstado> getListAll( String orden ){
		List<HcaMaestroEstado> lisMaestro	= new ArrayList<HcaMaestroEstado>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, T_SEMANAL, T_SEMESTRAL, ESTADO " +
					"FROM ENOC.HCA_MAESTRO_ESTADO " +orden; 
			
			lisMaestro = enocJdbc.query(comando, new HcaMaestroEstadoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaMaestroEstadoDao|getListAll|:"+ex);
		}
		
		return lisMaestro;
	}
	
	public HashMap<String, HcaMaestroEstado> getMapAll( String cargaId ) {
		List<HcaMaestroEstado> lisMaestro	= new ArrayList<HcaMaestroEstado>();
		HashMap<String, HcaMaestroEstado> mapaMaestro	= new HashMap<String, HcaMaestroEstado>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, T_SEMANAL, T_SEMESTRAL, ESTADO " +
					"FROM ENOC.HCA_MAESTRO_ESTADO WHERE CARGA_ID = ? "; 
			
			lisMaestro = enocJdbc.query(comando, new HcaMaestroEstadoMapper(), cargaId);
			for(HcaMaestroEstado maestro : lisMaestro){
				mapaMaestro.put(maestro.getCodigoPersonal(), maestro);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.HcaMaestroEstadoDao|getMapAll|:"+ex);
		}
		return mapaMaestro;
	}
}