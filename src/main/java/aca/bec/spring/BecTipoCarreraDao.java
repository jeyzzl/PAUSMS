package aca.bec.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanMapper;

@Component
public class BecTipoCarreraDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( BecTipoCarrera tipo){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.BEC_TIPO_CARRERA(ID_EJERCICIO, TIPO, CARRERA_ID) VALUES(?, TO_NUMBER(?,'99'),?)";
			Object[] parametros = new Object[] {tipo.getIdEjercicio(),tipo.getTipo(),tipo.getCarreraId()};			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoCarreraDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg( String idEjercicio, String tipo, String carreraId) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.BEC_TIPO_CARRERA WHERE ID_EJERCICIO = ? AND TIPO = TO_NUMBER(?,'99') AND CARRERA_ID = ? ";
			Object[] parametros = new Object[] {idEjercicio, tipo, carreraId};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoCarreraDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean existeReg( String idEjercicio, String tipo, String carreraId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_TIPO_CARRERA WHERE ID_EJERCICIO = ? AND TIPO = TO_NUMBER(?,'99') AND CARRERA_ID = ?";
			Object[] parametros = new Object[] {idEjercicio, tipo, carreraId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoCarreraDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public BecTipoCarrera mapeaRegId( String idEjercicio, String tipo, String carreraId ) {
		
		BecTipoCarrera becTipo = new BecTipoCarrera();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_TIPO_CARRERA WHERE ID_EJERCICIO = ? AND TIPO = TO_NUMBER(?,'99') AND CARRERA_ID = ? ";
			Object[] parametros = new Object[] {idEjercicio, tipo, carreraId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = "SELECT ID_EJERCICIO, TIPO, CARRERA_ID FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND TIPO = TO_NUMBER(?,'99') AND CARRERA_ID = ?";
				becTipo = enocJdbc.queryForObject(comando, new BecTipoCarreraMapper(),parametros);
			}							
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoCarreraDao|mapeaRegId|:"+ex);
		}
		
		return becTipo;
	}	
	
	public List<String> lisCarreras(String idEjercicio, String tipo, String orden){
		List<String> lista			= new ArrayList<String>();
		try{
			String comando ="SELECT CARRERA_ID FROM ENOC.BEC_TIPO_CARRERA WHERE ID_EJERCICIO = ? AND TIPO = ? "+orden;
			lista = enocJdbc.queryForList(comando, String.class, idEjercicio, tipo);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoCarreraDao|lisCarreras|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String,String> mapaCarrerasPorTipo( String ejercicioId ) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa	= new HashMap<String,String>();		
		try{
			String comando = "SELECT TIPO AS LLAVE, COUNT(CARRERA_ID) AS VALOR FROM ENOC.BEC_TIPO_CARRERA WHERE ID_EJERCICIO = ? GROUP BY TIPO";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), ejercicioId);			
			for (aca.Mapa objeto : lista){
				mapa.put(objeto.getLlave(), objeto.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecTipoCarreraDao|mapaCarrerasPorTipo|:"+ex);
		}
		return mapa;
	}
	
}	