package aca.vista.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FinTablaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public List<FinTabla> listCarga( String cargaId, String orden){		
		List<FinTabla> lista		= new ArrayList<FinTabla>();
		try{
			String comando = " SELECT TABLA_ID, CARGA_ID, CARRERA_ID, MODALIDAD_ID, MATRICULA, PORMATRICULA,"
				+ " LEGALES, PORLEGALES, INTERNADO, PORINTERNADO, ACFE, NOACFE, PORCREDITO, STATUS"
				+ " FROM ENOC.FIN_TABLA"
				+ " WHERE CARGA_ID = ? "+orden;
			lista = enocJdbc.query(comando, new FinTablaMapper(),cargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.FinTablaDao|listCarga|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String, String> mapTablaCosto( String cargaId){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CARRERA_ID||MODALIDAD_ID AS LLAVE, PCCREDITO AS VALOR FROM NOE.FES_TFINANCIERA_DET "+
					  " WHERE TFINANCIERA_ID IN (SELECT TFINANCIERA_ID FROM NOE.FES_TFINANCIERA_ENC WHERE CARGA_ID = '"+cargaId+"')";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());		
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.FinTablaDao|mapTablaCosto|:"+ex);
		}
		return mapa;
	}	
	
	public HashMap<String, FinTabla> mapTablaCargas( String cargas){
		HashMap<String,FinTabla> mapa = new HashMap<String, FinTabla>();
		List<FinTabla> lista		   = new ArrayList<FinTabla>();		
		try{
			String comando = " SELECT TABLA_ID,CARGA_ID, CARRERA_ID, MODALIDAD_ID, MATRICULA, PORMATRICULA,"
					+ " LEGALES, PORLEGALES, INTERNADO, PORINTERNADO, ACFE, NOACFE, PORCREDITO, STATUS"
					+ " FROM ENOC.FIN_TABLA"
					+ " WHERE TABLA_ID IN (SELECT TFINANCIERA_ID FROM NOE.FES_TFINANCIERA_ENC WHERE CARGA_ID IN ("+cargas+") )";
			lista = enocJdbc.query(comando, new FinTablaMapper());
			for(FinTabla tabla : lista){				
				mapa.put(tabla.getCargaId()+tabla.getCarreraId()+tabla.getModalidadId(), tabla);					
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.FinTablaDao|mapTablaCargas|:"+ex);
		}
		
		return mapa;
	}
}
