package aca.admision.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmReservadaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( AdmReservada objeto){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO SALOMON.ADM_RESERVADA(FOLIO, EVENTO_ID, DIA, PERIODO_ID, FECHA, ESTADO)"
				+ " VALUES(TO_NUMBER(?,'9999999'),TO_NUMBER(?,'99999'),TO_NUMBER(?,'9'),TO_NUMBER(?,'99'),SYSDATE,?)";
			Object[] parametros = new Object[] {objeto.getFolio(), objeto.getEventoId(), objeto.getDia(),objeto.getPeriodoId(), objeto.getEstado()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmReservadaDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( AdmReservada objeto ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE SALOMON.ADM_RESERVADA SET FECHA = SYSDATE, ESTADO = ?"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'99999') AND DIA = TO_NUMBER(?,'9') AND PERIODO_ID = TO_NUMBER(?,'99') AND FOLIO = TO_NUMBER(?,'9999999') ";
			Object[] parametros = new Object[] {
				objeto.getEstado(), objeto.getEventoId(), objeto.getDia(), objeto.getPeriodoId(), objeto.getFolio()
			};			
			if (enocJdbc.update(comando,parametros)==1){			
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmReservadaDao|updateReg|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String folio, String eventoId, String periodoId ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM SALOMON.ADM_RESERVADA WHERE FOLIO = TO_NUMBER(?,'9999999') AND EVENTO_ID = TO_NUMBER(?,'99999') AND PERIODO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {folio, eventoId, periodoId};
			if (enocJdbc.update(comando,parametros)==1){			
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmReservadaDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public AdmReservada mapeaRegId( String folio, String eventoId, String periodoId) {
		AdmReservada objeto = new AdmReservada();		
		try{
			String comando = "SELECT FOLIO, DIA, PERIODO_ID, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, ESTADO, EVENTO_ID FROM SALOMON.ADM_RESERVADA"
					+ " WHERE FOLIO = TO_NUMBER(?,'9999999') AND EVENTO_ID = TO_NUMBER(?,'99999') AND PERIODO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {folio, eventoId, periodoId };			
			objeto = enocJdbc.queryForObject(comando, new AdmReservadaMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmReservadaDao|mapeaRegId|:"+ex);
		}		
		return objeto;
	}
	
	public AdmReservada mapeaRegId( String folio, String estado) {
		AdmReservada objeto = new AdmReservada();		
		try{
			String comando = "SELECT FOLIO, DIA, PERIODO_ID, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, ESTADO, EVENTO_ID FROM SALOMON.ADM_RESERVADA"
					+ " WHERE FOLIO = TO_NUMBER(?,'9999999') AND ESTADO = ?";
			Object[] parametros = new Object[] {folio, estado};			
			objeto = enocJdbc.queryForObject(comando, new AdmReservadaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmReservadaDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public AdmReservada mapeaRegId( String folio) {
		AdmReservada objeto = new AdmReservada();
		
		try{
			String comando = "SELECT FOLIO, DIA, PERIODO_ID, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, ESTADO, EVENTO_ID FROM SALOMON.ADM_RESERVADA"
					+ " WHERE FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {folio};			
			objeto = enocJdbc.queryForObject(comando, new AdmReservadaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmReservadaDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg( String folio, String eventoId, String periodoId ) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_RESERVADA WHERE FOLIO = TO_NUMBER(?,'9999999') AND EVENTO_ID = TO_NUMBER(?,'99999') AND PERIODO_ID = TO_NUMBER(?,'99')"; 
			Object[] parametros = new Object[] {folio, eventoId, periodoId};	
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){	
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmReservadaDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String getEventoReservado( String folio ) {
		
		int eventoId		= 0;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_RESERVADA WHERE FOLIO = TO_NUMBER(?,'9999999')"; 
			Object[] parametros = new Object[] {folio};	
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				comando = "SELECT EVENTO_ID FROM SALOMON.ADM_RESERVADA WHERE FOLIO = TO_NUMBER(?,'9999999')";
				eventoId = enocJdbc.queryForObject(comando,Integer.class, parametros);				
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmReservadaDao|getEventoReservado|:"+ex);
		}
		
		return String.valueOf(eventoId);
	}
	
	public boolean existeReservacion( String folio ) {
		boolean 		ok 	= false;			
		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_RESERVADA WHERE FOLIO = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {folio};	
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){	
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmReservadaDao|existeReservacion|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeReservacion( String folio, String estados ) {
		boolean 		ok 	= false;			
		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_RESERVADA WHERE FOLIO = TO_NUMBER(?,'9999999') AND ESTADO IN ("+estados+")";			
			Object[] parametros = new Object[] {folio};	
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){	
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmReservadaDao|existeReservacion|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeReservacion( String folio, String eventoId, String estado ) {
		boolean 		ok 	= false;
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_RESERVADA WHERE FOLIO = TO_NUMBER(?,'9999999') AND EVENTO_ID = TO_NUMBER(?,'99999') AND ESTADO IN (?)";			
			Object[] parametros = new Object[] {folio, eventoId, estado};	
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){	
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmReservadaDao|existeReservacion|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeReservacion( String folio, String eventoId, String periodo, String estado ) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_RESERVADA WHERE FOLIO = TO_NUMBER(?,'9999999') AND EVENTO_ID = TO_NUMBER(?,'99999') AND PERIODO_ID = TO_NUMBER(?,'99') AND ESTADO IN (?)";			
			Object[] parametros = new Object[] {folio, eventoId, periodo, estado};	
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){	
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmReservadaDao|existeReservacion|:"+ex);
		}
		
		return ok;
	}
	
	public int getOcupados( String eventoId, String periodoId, String estado ) {
		int ocupados = 0;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_RESERVADA"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'99999')"					
					+ " AND PERIODO_ID = TO_NUMBER(?,'99')"
					+ " AND ESTADO = ?"; 
			Object[] parametros = new Object[] {eventoId, periodoId, estado};	
			ocupados = enocJdbc.queryForObject(comando,Integer.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmReservadaDao|ocupados|:"+ex);
		}
		
		return ocupados;
	}
	
	public List<AdmReservada> getListAll (){
		List<AdmReservada> lista = new ArrayList<AdmReservada>();
		
		try {
			String comando = "SELECT FOLIO, EVENTO_ID, DIA, PERIODO_ID, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, ESTADO FROM SALOMON.ADM_RESERVADA";
			
			lista = enocJdbc.query(comando, new AdmReservadaMapper());
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmReservadaDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<AdmReservada> lisEvento (String eventoId){
		List<AdmReservada> lista = new ArrayList<AdmReservada>();
		
		try {
			String comando = "SELECT FOLIO, EVENTO_ID, DIA, PERIODO_ID, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, ESTADO FROM SALOMON.ADM_RESERVADA WHERE EVENTO_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {eventoId};	
			lista = enocJdbc.query(comando, new AdmReservadaMapper(), parametros);
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmReservadaDao|lisEvento|:"+ex);
		}
		return lista;
	}
	
	public List<AdmReservada> lisEvento(String eventoId, String estados, String orden){
		List<AdmReservada> lista = new ArrayList<AdmReservada>();
		
		try {
			String comando = "SELECT FOLIO, EVENTO_ID, DIA, PERIODO_ID, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, ESTADO"
					+ " FROM SALOMON.ADM_RESERVADA WHERE EVENTO_ID = TO_NUMBER(?,'99999')"
					+ " AND ESTADO IN ("+estados+") "+orden;
			Object[] parametros = new Object[] {eventoId};	
			lista = enocJdbc.query(comando, new AdmReservadaMapper(), parametros);
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmReservadaDao|lisEvento|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, String> mapaOcupados (String estado){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		
		try {
			String comando = "SELECT EVENTO_ID||DIA||PERIODO_ID AS LLAVE, COUNT(FOLIO) AS VALOR"
					+ " FROM SALOMON.ADM_RESERVADA"
					+ " WHERE ESTADO = ?"
					+ " GROUP BY EVENTO_ID||DIA||PERIODO_ID";	
			Object[] parametros = new Object[] {estado};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ){
				mapa.put(map.getLlave(), map.getValor());			
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmReservadaDao|mapaOcupados|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaOcupadosPorEvento (String estados){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		
		try {
			String comando = "SELECT EVENTO_ID AS LLAVE, COUNT(FOLIO) AS VALOR"
					+ " FROM SALOMON.ADM_RESERVADA"
					+ " WHERE ESTADO IN ("+estados+")"
					+ " GROUP BY EVENTO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ){
				mapa.put(map.getLlave(), map.getValor());			
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmReservadaDao|mapaOcupadosPorEvento|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnoReservado (){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		
		try {
			String comando = "SELECT EVENTO_ID||DIA||PERIODO_ID||FOLIO AS LLAVE, ESTADO AS VALOR"
					+ " FROM SALOMON.ADM_RESERVADA";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ){
				mapa.put(map.getLlave(), map.getValor());	
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmReservadaDao|mapaAlumnoReservado|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaReservados (String estados){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		
		try {
			String comando = "SELECT FOLIO AS LLAVE, COUNT(*) AS VALOR FROM SALOMON.ADM_RESERVADA WHERE ESTADO IN ("+estados+") GROUP BY FOLIO";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ){
				mapa.put(map.getLlave(), map.getValor());	
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmReservadaDao|mapaReservados|:"+ex);
		}
		return mapa;
	}
}
