package aca.alerta.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlertaHistorialDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public AlertaHistorial mapeaRegId(String periodoId, String codigoPersonal, String pregunta ){
		
		AlertaHistorial historial = new AlertaHistorial();
		
		try{
			String comando = "SELECT PERIODO_ID, CODIGO_PERSONAL, PREGUNTA, RESPUESTA, COMENTARIO1, COMENTARIO2"
					+ " FROM ENOC.ALERTA_HISTORIAL WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ? AND PREGUNTA = ? ";
			
			Object[] parametros = new Object[] {
				periodoId, codigoPersonal, pregunta 
			};

			historial = enocJdbc.queryForObject(comando, new AlertaHistorialMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaHistorialDao|mapeaRegId|:"+ex);
		}
		
		return historial;
	}
	
	public boolean existeReg(String periodoId, String codigoPersonal, String pregunta){
		boolean ok 	= false;
		
		try{
			String comando = "SELECT * FROM ENOC.ALERTA_HISTORIAL "+ 
				"WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ? AND PREGUNTA = ?";
			
			Object[] parametros = new Object[] {periodoId,codigoPersonal,pregunta};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaHistorialDao|existeReg|:"+ex);
		}
		
		return ok;
	}

	public List<AlertaHistorial> listAll(String orden){
		
		List<AlertaHistorial> lista	= new ArrayList<AlertaHistorial>();
		
		try{
			String comando = " SELECT PERIODO_ID, CODIGO_PERSONAL, PREGUNTA, RESPUESTA, COMENTARIO1, COMENTARIO2 FROM ENOC.ALERTA_HISTORIAL "+ orden;
			
			lista = enocJdbc.query(comando, new AlertaHistorialMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaHistorialDao|listAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, AlertaHistorial> mapHistorial(String periodoId){
		
		HashMap<String, AlertaHistorial> mapa	= new HashMap<String,AlertaHistorial>();
		List<AlertaHistorial> lista 			= new ArrayList<AlertaHistorial>();		
		try{
			String comando = " SELECT PERIODO_ID, CODIGO_PERSONAL, PREGUNTA, RESPUESTA, COMENTARIO1, COMENTARIO2 FROM ENOC.ALERTA_HISTORIAL"
					+ " WHERE PERIODO_ID = ?";			
			lista = enocJdbc.query(comando,new AlertaHistorialMapper(), periodoId);			
			for(AlertaHistorial objeto : lista){				
				mapa.put(objeto.getPeriodoId()+objeto.getCodigoPersonal(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaHistorialDao|mapHistorial|:"+ex);
		}
		
		return mapa;
	}

}