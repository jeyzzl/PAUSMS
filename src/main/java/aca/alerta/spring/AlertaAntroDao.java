package aca.alerta.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlertaAntroDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public AlertaAntro mapeaRegId(String periodoId, String codigoPersonal ){
		
		AlertaAntro alertaDatos = new AlertaAntro();
		
		try{
			String comando = "SELECT PERIODO_ID, CODIGO_PERSONAL, PESO, TALLA, CINTURA, IMC, GRASA, MUSCULO, PRESION "
					+ " FROM ENOC.ALERTA_ANTRO WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ? ";
			
			Object[] parametros = new Object[] {periodoId, codigoPersonal};
			alertaDatos = enocJdbc.queryForObject(comando, new AlertaAntroMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaAntroDao|mapeaRegId|:"+ex);
		}
		
		return alertaDatos;
	}
	
	public boolean existeReg( String periodoId, String codigoPersonal){
		boolean ok 	= false;		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.ALERTA_ANTRO WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {periodoId, codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaAntroDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public HashMap<String, AlertaAntro> mapAntro(String periodoId){		
		HashMap<String, AlertaAntro> mapa	= new HashMap<String,AlertaAntro>();
		List<AlertaAntro> lista 			= new ArrayList<AlertaAntro>();		
		try{
			String comando = " SELECT PERIODO_ID, CODIGO_PERSONAL, PESO, TALLA, CINTURA, IMC, GRASA, MUSCULO, PRESION FROM ENOC.ALERTA_ANTRO"
					+ " WHERE PERIODO_ID = ?";			
			lista = enocJdbc.query(comando,new AlertaAntroMapper(), periodoId);			
			for(AlertaAntro objeto : lista){				
				mapa.put(objeto.getPeriodoId()+objeto.getCodigoPersonal(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaHistorialDao|mapHistorial|:"+ex);
		}		
		return mapa;
	}

}
