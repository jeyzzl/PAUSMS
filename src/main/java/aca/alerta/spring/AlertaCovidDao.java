package aca.alerta.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import aca.notifica.spring.NotiCovid;

@Component
public class AlertaCovidDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public boolean insertReg(AlertaCovid alertaCovid){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ALERTA_COVID"+ 
				"(CODIGO_PERSONAL,FECHA_UNO,FECHA_DOS,PAIS_UNO,PAIS_DOS,CIUDAD_UNO,CIUDAD_DOS,CONTACTO,CONTACTO_FECHA,FIEBRE,TOS,CABEZA,RESPIRAR,GARGANTA,ESCURRIMIENTO,OLFATO,GUSTO,CUERPO,FOLIO)"
				+ " VALUES(?, TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), ?, ?, ?, TO_DATE(?,'DD/MM/YYYY'), ?, ?, ?, ?, ?, ?, ?, ?, ?, TO_NUMBER(?,'999'))";
			Object[] parametros = new Object[] {
				alertaCovid.getCodigoPersonal(),alertaCovid.getFechaUno(),alertaCovid.getFechaDos(),alertaCovid.getPaisUno(),alertaCovid.getPaisDos(),alertaCovid.getCiudadUno(),
				alertaCovid.getCiudadDos(),alertaCovid.getContacto(),alertaCovid.getContactoFecha(),alertaCovid.getFiebre(),alertaCovid.getTos(),alertaCovid.getCabeza(),
				alertaCovid.getRespirar(),alertaCovid.getGarganta(), alertaCovid.getEscurrimiento(), alertaCovid.getOlfato(), alertaCovid.getGusto(), alertaCovid.getCuerpo(), alertaCovid.getFolio() 
			};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}	
				
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaCovidDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(AlertaCovid alertaCovid){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ALERTA_COVID SET FECHA = SYSDATE, FECHA_UNO = TO_DATE(?,'DD/MM/YYYY'), FECHA_DOS = TO_DATE(?,'DD/MM/YYYY'), PAIS_UNO = ?, PAIS_DOS = ?, CIUDAD_UNO = ?, CIUDAD_DOS = ?, CONTACTO = ?, CONTACTO_FECHA = ?, "
					+ " FIEBRE = ?, TOS = ?, CABEZA = ?, RESPIRAR = ?, GARGANTA = ?, ESCURRIMIENTO = ?, OLFATO = ?, GUSTO = ?, CUERPO = ?"
					+ " WHERE CODIGO_PERSONAL = ? ";			
			Object[] parametros = new Object[] {
				alertaCovid.getFechaUno(), alertaCovid.getFechaDos(),alertaCovid.getPaisUno(),alertaCovid.getPaisDos(),alertaCovid.getCiudadUno(),alertaCovid.getCiudadDos(),
				alertaCovid.getContacto(),alertaCovid.getContactoFecha(),alertaCovid.getFiebre(),alertaCovid.getTos(),alertaCovid.getCabeza(),alertaCovid.getRespirar(),
				alertaCovid.getGarganta(),alertaCovid.getEscurrimiento(), alertaCovid.getOlfato(), alertaCovid.getGusto(), alertaCovid.getCuerpo(),alertaCovid.getCodigoPersonal()
			};
			
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaCovidDao|updateReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean deleteReg(String codigoPersonal ){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ALERTA_COVID WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaCovidDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public AlertaCovid mapeaRegId(String codigoPersonal ){
		
		AlertaCovid alertaCovid = new AlertaCovid();		
		try{
			String comando = "SELECT CODIGO_PERSONAL,FECHA,TO_CHAR(FECHA_UNO,'DD/MM/YYYY') AS FECHA_UNO,TO_CHAR(FECHA_DOS,'DD/MM/YYYY') AS FECHA_DOS,PAIS_UNO,PAIS_DOS,CIUDAD_UNO,CIUDAD_DOS,CONTACTO,CONTACTO_FECHA,FIEBRE,TOS,CABEZA,RESPIRAR,GARGANTA,ESCURRIMIENTO,OLFATO,GUSTO,CUERPO,FOLIO"
				+ " FROM ENOC.ALERTA_COVID"
				+ " WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {codigoPersonal};
			alertaCovid = enocJdbc.queryForObject(comando, new AlertaCovidMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaCovidDao|mapeaRegId|:"+ex);
		}
		
		return alertaCovid;
	}
	
	public AlertaCovid mapeaRegIdFolio(String codigoPersonal, String folio){
		
		AlertaCovid alertaCovid = new AlertaCovid();		
		try{
			String comando = "SELECT CODIGO_PERSONAL,FECHA,TO_CHAR(FECHA_UNO,'DD/MM/YYYY') AS FECHA_UNO,TO_CHAR(FECHA_DOS,'DD/MM/YYYY') AS FECHA_DOS,PAIS_UNO,PAIS_DOS,CIUDAD_UNO,CIUDAD_DOS,CONTACTO,CONTACTO_FECHA,FIEBRE,TOS,CABEZA,RESPIRAR,GARGANTA,ESCURRIMIENTO,OLFATO,GUSTO,CUERPO,FOLIO"
				+ " FROM ENOC.ALERTA_COVID"
				+ " WHERE CODIGO_PERSONAL = ? "
				+ " AND FOLIO = ?";
			Object[] parametros = new Object[] {codigoPersonal, folio};
			alertaCovid = enocJdbc.queryForObject(comando, new AlertaCovidMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaCovidDao|mapeaRegIdFolio|:"+ex);
		}
		
		return alertaCovid;
	}
	
	public boolean existeReg(String codigoPersonal){
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALERTA_COVID WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaCovidDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public NotiCovid evaluaRespuesta( String codigoPersonal, String folio){		
		NotiCovid covid 		= new NotiCovid();
		AlertaCovid	alertaCovid	= new AlertaCovid(); 
		boolean graves 			= false;
		boolean leves			= false;
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALERTA_COVID WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){				
				comando = "SELECT CODIGO_PERSONAL,FECHA,PAIS_UNO,PAIS_DOS,CIUDAD_UNO,CIUDAD_DOS,CONTACTO,CONTACTO_FECHA,FIEBRE,TOS,CABEZA,RESPIRAR,GARGANTA,ESCURRIMIENTO,OLFATO,GUSTO,CUERPO,FOLIO "
						+ "FROM ENOC.ALERTA_COVID WHERE CODIGO_PERSONAL = ?";
				alertaCovid = this.mapeaRegIdFolio(codigoPersonal, folio);
				if (alertaCovid.getFiebre().equals("S")|| alertaCovid.getTos().equals("S") || alertaCovid.getCabeza().equals("S") || alertaCovid.getRespirar().equals("S")) {
					graves = true;
				}
				if (alertaCovid.getGarganta().equals("S")|| alertaCovid.getEscurrimiento().equals("S") || alertaCovid.getOlfato().equals("S") || alertaCovid.getGusto().equals("S") || alertaCovid.getCuerpo().equals("S")) {
					leves = true;
				}
				covid.setMatricula(codigoPersonal);
				covid.setFecha(alertaCovid.getFecha());
				if (graves && leves) {					
					covid.setRespuesta("NO");
				}else {
					covid.setRespuesta("SI");
				}
			}else {
				covid.setMatricula(codigoPersonal);
				covid.setFecha("01/01/2000");
				covid.setRespuesta("NO");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaCovidDao|evaluaRespuesta|:"+ex);
		}		
		return covid;
	}
	
	public List<AlertaCovid> lisTodos(String orden){
		
		List<AlertaCovid> lista	= new ArrayList<AlertaCovid>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL,FECHA,TO_CHAR(FECHA_UNO,'DD/MM/YYYY') AS FECHA_UNO,TO_CHAR(FECHA_DOS,'DD/MM/YYYY') AS FECHA_DOS,PAIS_UNO,PAIS_DOS,CIUDAD_UNO,CIUDAD_DOS,CONTACTO,CONTACTO_FECHA,FIEBRE,TOS,CABEZA,RESPIRAR,GARGANTA,ESCURRIMIENTO,OLFATO,GUSTO,CUERPO,FOLIO"
					+ " FROM ENOC.ALERTA_COVID "+ orden;
			lista = enocJdbc.query(comando, new AlertaCovidMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaCovidDao|lisTodos|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, String> mapaTodos(){
		
		HashMap<String, String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, FECHA AS VALOR FROM ENOC.ALERTA_COVID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaCovidDao|getMapResidenciaAlerta|:"+ex);
		}		
		return mapa;
	}
	
	public String maximoFolio(String codigoPersonal){
		String maximo = "1";		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) FROM ENOC.ALERTA_COVID WHERE CODIGO_PERSONAL = ?";		
			Object[] parametros = new Object[] {codigoPersonal};
			maximo = enocJdbc.queryForObject(comando,String.class, parametros);	
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmComentarioDao|maximoFolio|:"+ex);
		}
		
		return maximo;
	}
	
	public String ultimoFolio(String codigoPersonal){
		String maximo = "1";		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO),1) FROM ENOC.ALERTA_COVID WHERE CODIGO_PERSONAL = ?";		
			Object[] parametros = new Object[] {codigoPersonal};
			maximo = enocJdbc.queryForObject(comando,String.class, parametros);	
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmComentarioDao|ultimoFolio|:"+ex);
		}
		
		return maximo;
	}
}