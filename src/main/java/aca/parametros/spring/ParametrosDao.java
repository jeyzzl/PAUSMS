package aca.parametros.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ParametrosDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(Parametros parametros) {
		boolean ok 		= false;
		try{
			String comando = "INSERT INTO ENOC.PARAMETROS" 
						+ "(INSTITUCION,CERTIFICADOS,CONSTANCIAS,CARDEX,MONITOR, MONITOR_RUTA, PAIS_ID)" 
						+ " VALUES(?,?,?,?,?,?,?)";
			Object[] parametros2 = new Object[] {parametros.getInstitucion(), parametros.getCertificados(), parametros.getConstancias(), parametros.getCardex(), parametros.getMonitor(), parametros.getMonitorRuta(), parametros.getPaisId()};
			if (enocJdbc.update(comando,parametros2)==1){
				ok = true;
			}							
		}catch(Exception ex){
			System.out.println("Error - aca.parametros.spring.ParametrosDao|insertReg|:"+ex);	
		}
		return ok;
	}
	
	public boolean updateReg(Parametros parametros){
		boolean ok			= false;
		try{
			String comando = "UPDATE ENOC.PARAMETROS" 
							+ " SET INSTITUCION = ?," 
							+ " CERTIFICADOS = ?," 
							+ " CONSTANCIAS = ?," 
							+ " CARDEX = ?,"
							+ " MONITOR = ?,"
							+ " MONITOR_RUTA = ?,"
							+ " PAIS_ID = TO_NUMBER(?,'99')"
							+ " WHERE ID = TO_NUMBER(?,'99')";
			Object[] parametros2 = new Object[] {parametros.getInstitucion(), parametros.getCertificados(), parametros.getConstancias(), 
					parametros.getCardex(), parametros.getMonitor(), parametros.getMonitorRuta(), parametros.getPaisId(), parametros.getId()};
			if (enocJdbc.update(comando,parametros2)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.parametros.spring.ParametrosDao|updateReg|:"+ex); 
			}	
		return ok;
	}
	
	public boolean deleteReg(String id){
		boolean ok	 = false;
		try{
			String comando = "DELETE FROM ENOC.PARAMETROS WHERE ID = TO_NUMBER(?,'99')";
			if (enocJdbc.update(comando,id)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.parametros.spring.ParametrosDao|deleteReg|:"+ex);
		}
		return ok;
	}
		
	public Parametros mapeaRegId(String id) {
		Parametros parametros = new Parametros();
		try{
			String comando 	= " SELECT ID, INSTITUCION, CERTIFICADOS, CONSTANCIAS, CARDEX, MONITOR, MONITOR_RUTA, PAIS_ID"
							+ " FROM ENOC.PARAMETROS WHERE ID = TO_NUMBER(?,'99')";
			Object[] parametros2 = new Object[] {id};
			parametros = enocJdbc.queryForObject(comando, new ParametrosMapper(), parametros2);	
	}catch(Exception ex){
		System.out.println("Error - aca.plan.spring.ParametrosDao|mapeaRegId|:"+ex);
	}
		return parametros;
	}
	
	public boolean existeReg(String id) {
		boolean	ok 		= false;
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.PARAMETROS WHERE ID = TO_NUMBER(?,'99')";
			Object[] parametros2 = new Object[] {id};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros2) >=1 ){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.parametros.spring.ParametrosDao|existeReg|:"+ex);
		}
			return ok;
		}
		
	public String getInstitucion(String id) {
		String institucion  		= ""; 
		
		try{
			String comando	= " SELECT COALESCE(INSTITUCION, 'UNIVERSIDAD') AS INST FROM ENOC.PARAMETROS"
							+ " WHERE ID = TO_NUMBER(?,'99')";			
			Object[] parametros2 = new Object[] {id};
			institucion = enocJdbc.queryForObject(comando,String.class,parametros2);			
		}catch(Exception ex){
			System.out.println("Error - aca.parametros.spring.ParametrosDao|getInstitucion|:"+ex);
		}
		return institucion;
	}
	
	public String getMonitor(String id) {
		String monitor 		= "S";		
		try{
			String comando	= "SELECT COALESCE(MONITOR,'S') FROM ENOC.PARAMETROS WHERE ID = TO_NUMBER(?,'99')";			
			monitor = enocJdbc.queryForObject(comando,String.class, id);			
		}catch(Exception ex){
			System.out.println("Error - aca.parametros.spring.ParametrosDao|getMonitor|:"+ex);
		}
		return monitor;
	}
	
	public String getMonitorRuta(String id) {
		String monitor 		= "S";		
		try{
			String comando	= "SELECT COALESCE(MONITOR_RUTA,'S') FROM ENOC.PARAMETROS WHERE ID = TO_NUMBER(?,'99')";			
			monitor = enocJdbc.queryForObject(comando,String.class, id);			
		}catch(Exception ex){
			System.out.println("Error - aca.parametros.spring.ParametrosDao|getMonitorRuta|:"+ex);
		}
		return monitor;
	}
	
	public String getPaisId(String id) {
		String pais 		= "0";		
		try{
			String comando	= "SELECT PAIS_ID FROM ENOC.PARAMETROS WHERE ID = TO_NUMBER(?,'99')";			
			pais = enocJdbc.queryForObject(comando,String.class, id);			
		}catch(Exception ex){
			System.out.println("Error - aca.parametros.spring.ParametrosDao|getPaisId|:"+ex);
		}
		return pais;
	}
	
	public List<Parametros> getLista() {
		List<Parametros> lista	= new ArrayList<Parametros>();
		try{
			String comando = " SELECT ID, INSTITUCION, CERTIFICADOS, CONSTANCIAS, CARDEX, MONITOR, MONITOR_RUTA, PAIS_ID"
					+ " FROM ENOC.PARAMETROS";			
			lista = enocJdbc.query(comando, new ParametrosMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.ParametrosDao|getLista|:"+ex);
		}		
		return lista;
	}
	
}