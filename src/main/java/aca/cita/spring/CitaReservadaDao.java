package aca.cita.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CitaReservadaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CitaReservada objeto){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CITA_RESERVADA(CODIGO_PERSONAL, EVENTO_ID, DIA, PERIODO_ID, FECHA, ESTADO)"
					+ "VALUES(?,TO_NUMBER(?,'99999'),TO_NUMBER(?,'9'),TO_NUMBER(?,'99'),TO_DATE(?,'DD/MM/YYYY'),?)";
			Object[] parametros = new Object[] {objeto.getCodigoPersonal(), objeto.getEventoId(), objeto.getDia(),objeto.getPeriodoId(), objeto.getFecha(), objeto.getEstado()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.cita.spring.CitaReservada|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( CitaReservada objeto ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CITA_RESERVADA SET FECHA = TO_DATE(?,'DD/MM/YYYY'), ESTADO = ?"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'99999') AND DIA = TO_NUMBER(?,'9') AND PERIODO_ID = TO_NUMBER(?,'99') AND CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {
					objeto.getFecha(),objeto.getEstado(), objeto.getEventoId(), objeto.getDia(), objeto.getPeriodoId(), objeto.getCodigoPersonal()
					};			
			if (enocJdbc.update(comando,parametros)==1){			
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.cita.spring.CitaReservada|updateReg|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String eventoId, String dia, String periodoId, String codigoPersonal) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CITA_RESERVADA"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'99999') AND DIA = TO_NUMBER(?,'9') AND PERIODO_ID = TO_NUMBER(?,'99') AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {eventoId, dia, periodoId, codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){			
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.dao.CatActividadDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public CitaReservada mapeaRegId( String eventoId, String dia, String periodoId, String codigoPersonal) {
		CitaReservada objeto = new CitaReservada();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, DIA, PERIODO_ID, FECHA, ESTADO FROM ENOC.CITA_RESERVADA"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'99999') AND DIA = TO_NUMBER(?,'9') AND PERIODO_ID = TO_NUMBER(?,'99') AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {eventoId, dia, periodoId, codigoPersonal};			
			objeto = enocJdbc.queryForObject(comando, new CitaReservadaMapper(), parametros);		
			
		}catch(Exception ex){
			System.out.println("Error - aca.cita.spring.CitaReservada|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg( String eventoId, String dia, String periodoId, String codigoPersonal ) {
		boolean 		ok 	= false;			
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CITA_RESERVADA WHERE EVENTO_ID = TO_NUMBER(?,'99999') AND DIA = TO_NUMBER(?,'9') AND PERIODO_ID = TO_NUMBER(?,'99') AND CODIGO_PERSONAL = ?"; 
			Object[] parametros = new Object[] {eventoId, dia, periodoId, codigoPersonal};	
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){	
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.cita.spring.CitaReservada|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeReservacion( String eventoId, String codigoPersonal, String estados ) {
		boolean 		ok 	= false;			
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CITA_RESERVADA WHERE EVENTO_ID = TO_NUMBER(?,'99999') AND CODIGO_PERSONAL = ? AND ESTADO IN ("+estados+")";			
			Object[] parametros = new Object[] {eventoId, codigoPersonal};	
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){	
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.cita.spring.CitaReservada|existeReservacion|:"+ex);
		}
		
		return ok;
	}
	
	public int getOcupados( String eventoId, String dia, String periodoId, String estado ) {
		int ocupados = 0;			
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CITA_RESERVADA"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'99999')"
					+ " AND DIA = TO_NUMBER(?,'9')"
					+ " AND PERIODO_ID = TO_NUMBER(?,'99')"
					+ " AND ESTADO = ?"; 
			Object[] parametros = new Object[] {eventoId, dia, periodoId, estado};	
			ocupados = enocJdbc.queryForObject(comando,Integer.class,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.cita.spring.CitaReservada|ocupados|:"+ex);
		}
		
		return ocupados;
	}
	
	public List<CitaReservada> getListAll (){
		List<CitaReservada> lista = new ArrayList<CitaReservada>();
		
		try {
			String comando = "SELECT CODIGO_PERSONAL, EVENTO_ID, DIA, PERIODO_ID, FECHA, ESTADO FROM ENOC.CITA_RESERVADA";
			
			lista = enocJdbc.query(comando, new CitaReservadaMapper());
		}catch(Exception ex) {
			System.out.println("Error - aca.cita.spring.CitaReservada|getListAll|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, String> mapaOcupados (String estado){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		
		try {
			String comando = "SELECT EVENTO_ID||DIA||PERIODO_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR"
					+ " FROM ENOC.CITA_RESERVADA"
					+ " WHERE ESTADO = ?"					
					+ " GROUP BY EVENTO_ID||DIA||PERIODO_ID";	
			Object[] parametros = new Object[] {estado};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ){
				mapa.put(map.getLlave(), map.getValor());			
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.cita.spring.CitaReservada|mapaOcupados|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnoReservado (){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		
		try {
			String comando = "SELECT EVENTO_ID||DIA||PERIODO_ID||CODIGO_PERSONAL AS LLAVE, ESTADO AS VALOR"
					+ " FROM ENOC.CITA_RESERVADA";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ){
				mapa.put(map.getLlave(), map.getValor());	
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.cita.spring.CitaReservada|mapaAlumnoReservado|:"+ex);
		}
		return mapa;
	}
}
