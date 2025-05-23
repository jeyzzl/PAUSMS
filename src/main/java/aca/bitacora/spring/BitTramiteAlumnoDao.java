package aca.bitacora.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.Mapa;


@Component
public class BitTramiteAlumnoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( BitTramiteAlumno tramite) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.BIT_TRAMITE_ALUMNO"
					+ " (FOLIO, CODIGO_PERSONAL, TRAMITE_ID, FECHA_INICIO, FECHA_FINAL, ESTADO, AREA_ID, AREA_ORIGEN, FOLIO_ORIGEN, USUARIO, COMENTARIO)"
					+ " VALUES(?, ?, TO_NUMBER(?,'9999'), TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), TO_NUMBER(?,'99'),TO_NUMBER(?,'999'), TO_NUMBER(?,'9'), ?, ?, ?)";
			Object[] parametros = new Object[] {tramite.getFolio(), tramite.getCodigoPersonal(),tramite.getTramiteId(),
			tramite.getFechaInicio(), tramite.getFechaFinal(), tramite.getEstado(), tramite.getAreaId(),
			tramite.getAreaOrigen(), tramite.getFolioOrigen(), tramite.getUsuario(), tramite.getComentario()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteAlumnoDao|insertReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean updateReg( BitTramiteAlumno tramite) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.BIT_TRAMITE_ALUMNO"
					+ " SET CODIGO_PERSONAL = ?,"
					+ " TRAMITE_ID = TO_NUMBER(?,'9999'),"
					+ " FECHA_INICIO = TO_DATE(?,'DD/MM/YYYY'),"
					+ " FECHA_FINAL =  TO_DATE(?,'DD/MM/YYYY'),"
					+ " ESTADO = TO_NUMBER(?,'99'),"
					+ " AREA_ID = TO_NUMBER(?,'999'),"
					+ " AREA_ORIGEN = TO_NUMBER(?,'999'),"
					+ " FOLIO_ORIGEN = ?,"
					+ " USUARIO = ?,"
					+ " COMENTARIO = ?"
					+ " WHERE FOLIO = ?";
			Object[] parametros = new Object[] {tramite.getCodigoPersonal(), tramite.getTramiteId(), tramite.getFechaInicio(),
			tramite.getFechaFinal(), tramite.getEstado(), tramite.getAreaId(), tramite.getAreaOrigen(),
			tramite.getFolioOrigen(), tramite.getUsuario(), tramite.getComentario(), tramite.getFolio()};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteAlumnoDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg( String folio) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.BIT_TRAMITE_ALUMNO WHERE FOLIO = ?";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteAlumnoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public BitTramiteAlumno mapeaRegId(  String folio) {
		
		BitTramiteAlumno objeto = new BitTramiteAlumno();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BIT_TRAMITE_ALUMNO WHERE FOLIO = ?";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = "SELECT FOLIO, CODIGO_PERSONAL, TRAMITE_ID, TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL,"
						+ " ESTADO, AREA_ID, AREA_ORIGEN, FOLIO_ORIGEN, USUARIO, COMENTARIO"
						+ " FROM ENOC.BIT_TRAMITE_ALUMNO"
						+ " WHERE FOLIO = ?";				
				objeto = enocJdbc.queryForObject(comando, new BitTramiteAlumnoMapper(),parametros);			
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteAlumnoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return objeto;
	}
	
	public boolean existeReg(  String folio) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BIT_TRAMITE_ALUMNO WHERE FOLIO = ?";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteAlumnoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return ok;
	}
	
	public String maximoReg( String year) {
		String maximo = year+"-0001";		
		try{
			String comando =  "SELECT COALESCE(TO_NUMBER(MAX(SUBSTR(FOLIO,6,4)),'9999')+1,1) AS MAXIMO FROM ENOC.BIT_TRAMITE_ALUMNO WHERE SUBSTR(FOLIO,1,4) = ?";
			if (enocJdbc.queryForObject(comando,Integer.class, year)>=1){
				maximo = enocJdbc.queryForObject(comando, String.class, year);
				if (maximo.length()==1) maximo = year+"-000"+maximo;
				if (maximo.length()==2) maximo = year+"-00"+maximo;
				if (maximo.length()==3) maximo = year+"-0"+maximo;
				if (maximo.length()==4) maximo = year+"-"+maximo;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteAlumnoDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public int getNumDependientes( String folio) {
		int dep	= 0;		
		try{
			String comando =  "SELECT COUNT(*) AS TOTAL FROM ENOC.BIT_TRAMITE_ALUMNO WHERE FOLIO_ORIGEN = ?";
			dep = enocJdbc.queryForObject(comando,Integer.class, folio);			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteAlumnoDao|getNumDependientes|:"+ex);
		}
		return dep;
	}
	
	public int getNumDependientesSinTerminar( String folio) {
		int dep	= 0;		
		try{
			String comando =  "SELECT COUNT(*) AS TOTAL FROM ENOC.BIT_TRAMITE_ALUMNO WHERE FOLIO_ORIGEN = ? AND ESTADO < 4";
			dep = enocJdbc.queryForObject(comando,Integer.class, folio);			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteAlumnoDao|getNumDependientes|:"+ex);
		}
		return dep;
	}
	
	public int getNumTerminados( String tramiteId) {
		int dep	= 0;		
		try{
			String comando =  "SELECT COUNT(*) AS TOTAL FROM ENOC.BIT_TRAMITE_ALUMNO WHERE TRAMITE_ID = TO_NUMBER(?,'9999') AND FECHA_FINAL IS NOT NULL";
			dep = enocJdbc.queryForObject(comando,Integer.class, tramiteId);			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteAlumnoDao|getNumTerminados|:"+ex);
		}
		return dep;
	}
	
	public String getDependientes( String folio) {
		String folios	= "";
		List<String> lista 	= new ArrayList<String>();		
		try{
			String comando =  "SELECT COUNT(FOLIO) FROM ENOC.BIT_TRAMITE_ALUMNO WHERE FOLIO_ORIGEN = ?";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros)>= 1){
				comando =  "SELECT FOLIO FROM ENOC.BIT_TRAMITE_ALUMNO WHERE FOLIO_ORIGEN = ?";
				lista =  enocJdbc.queryForList(comando, String.class,parametros);
				for (String dependiente : lista){
					if (folios.length()>=1){
						folios+=","+dependiente;
					}else{
						folios = dependiente;
					}
				}
			}					
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteAlumnoDao|getDependientes|:"+ex);
		}
		return folios;
	}
	
	public String getTiempoMinimoTramite( String tramiteId) {
		String minimo	= "0";
		int dias		= 0;
		try{
			String comando = ("SELECT COALESCE(MIN(FECHA_FINAL - FECHA_INICIO),0) AS MINIMO FROM ENOC.BIT_TRAMITE_ALUMNO WHERE TRAMITE_ID = TO_NUMBER(?,'9999') AND FECHA_FINAL IS NOT NULL");
			dias 	= enocJdbc.queryForObject(comando,Integer.class, tramiteId);
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteAlumnoDao|getTiempoMinimoTramite|:"+ex);
		}
		minimo = String.valueOf(dias);
		
		return minimo;
	}
	
	public String getTiempoMaximoTramite( String tramiteId) {
		String maximo			= "0";
		int dias				= 0;		
		try{
			String comando =  ("SELECT COALESCE(MAX(FECHA_FINAL - FECHA_INICIO),0) AS MAXIMO FROM ENOC.BIT_TRAMITE_ALUMNO WHERE TRAMITE_ID = TO_NUMBER(?,'9999') AND FECHA_FINAL IS NOT NULL");
			dias 	= enocJdbc.queryForObject(comando, Integer.class, tramiteId);							
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteAlumnoDao|getTiempoMaximoTramite|:"+ex);
		}
		
		maximo = String.valueOf(dias);
		return maximo;
	}
	
	public String getPromedioTramite( String tramiteId) {
		String promedio			= "0";
		int dias				= 0;		
		try{
			String comando =  ("SELECT COALESCE(AVG(FECHA_FINAL-FECHA_INICIO),0) AS PROMEDIO FROM BIT_TRAMITE_ALUMNO WHERE TRAMITE_ID = TO_NUMBER(?,'9999') AND FECHA_FINAL IS NOT NULL");
			dias 	= enocJdbc.queryForObject(comando, Integer.class, tramiteId);							
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteAlumnoDao|getPromedioTramite|:"+ex);
		}		
		
		promedio = String.valueOf(dias);
		
		return promedio;
	}
	
	public List<BitTramiteAlumno> lisTramite( String codigoAlumno, String orden) {		
		List<BitTramiteAlumno> lista = new ArrayList<BitTramiteAlumno>();		
		try{
			String comando = " SELECT FOLIO,CODIGO_PERSONAL,TRAMITE_ID, TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL,ESTADO,AREA_ID,AREA_ORIGEN,FOLIO_ORIGEN, USUARIO, COMENTARIO"
				+ " FROM ENOC.BIT_TRAMITE_ALUMNO"
				+ " WHERE CODIGO_PERSONAL = ? "+orden;
			lista = enocJdbc.query(comando, new BitTramiteAlumnoMapper(), codigoAlumno);			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteAlumnoDao|lisTramite|:"+ex);
		}
		return lista;
	}
	
	public List<BitTramiteAlumno> lisTramitePrincipal( String codigoAlumno, String orden){		
		List<BitTramiteAlumno> lista = new ArrayList<BitTramiteAlumno>();		
		try{
			String comando = " SELECT FOLIO,CODIGO_PERSONAL,TRAMITE_ID, TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL,ESTADO,AREA_ID,AREA_ORIGEN,FOLIO_ORIGEN, USUARIO, COMENTARIO"
					+ " FROM ENOC.BIT_TRAMITE_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND FOLIO_ORIGEN = '-' "+orden;
			lista = enocJdbc.query(comando, new BitTramiteAlumnoMapper(), codigoAlumno);			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteAlumnoDao|lisTramitePrincipal|:"+ex);
		}
		return lista;
	}
	
	public List<BitTramiteAlumno> lisTramiteOrigen( String codigoAlumno, String orden) {
		
		List<BitTramiteAlumno> lista = new ArrayList<BitTramiteAlumno>();		
		try{
			String comando = " SELECT FOLIO,CODIGO_PERSONAL,TRAMITE_ID, TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL,ESTADO,AREA_ID,AREA_ORIGEN,FOLIO_ORIGEN, USUARIO, COMENTARIO"
					+ " FROM ENOC.BIT_TRAMITE_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND AREA_ORIGEN = 0 "+orden;
			lista = enocJdbc.query(comando, new BitTramiteAlumnoMapper(), codigoAlumno);			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteAlumnoDao|lisTramite|:"+ex);
		}
		return lista;
	}
	
	public List<BitTramiteAlumno> lisTramitesDependientes( String folio, String orden) {
		
		List<BitTramiteAlumno> lista = new ArrayList<BitTramiteAlumno>();
		
		try{
			String comando = " SELECT FOLIO,CODIGO_PERSONAL,TRAMITE_ID, TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL,ESTADO,AREA_ID,AREA_ORIGEN,FOLIO_ORIGEN, USUARIO, COMENTARIO"
					+ " FROM ENOC.BIT_TRAMITE_ALUMNO"
					+ " WHERE FOLIO_ORIGEN = ? "+orden;
			lista = enocJdbc.query(comando, new BitTramiteAlumnoMapper(), folio);
			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteAlumnoDao|lisTramitesDependientes|:"+ex);
		}
		return lista;
	}
	
	public List<BitTramiteAlumno> lisTramiteFiltros( String areaId, String tramiteId, String estadoId, String fechaInicio, String fechaFinal, String orden){
		List<BitTramiteAlumno> lista = new ArrayList<BitTramiteAlumno>();
		String condicion = "";
		
		try{
			if (!tramiteId.equals("0")) {
				condicion = condicion + " AND TRAMITE_ID = TO_NUMBER('"+tramiteId+"','9999')";
			}
			if (!estadoId.equals("0")) {
				condicion = condicion + " AND ESTADO = TO_NUMBER('"+estadoId+"','99')";
			}
			if (fechaInicio!=null && fechaFinal != null && !fechaInicio.equals("null") && !fechaFinal.equals("null")){
				condicion = condicion + " AND FECHA_INICIO BETWEEN TO_DATE('"+fechaInicio+"','DD/MM/YYYY') AND TO_DATE('"+fechaFinal+"','DD/MM/YYYY')";
			}			
			String comando = "SELECT FOLIO,CODIGO_PERSONAL,TRAMITE_ID, TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL,ESTADO,AREA_ID,AREA_ORIGEN,FOLIO_ORIGEN, USUARIO, COMENTARIO"
				+ " FROM ENOC.BIT_TRAMITE_ALUMNO"
				+ " WHERE AREA_ID = TO_NUMBER(?,'999') "
				+ condicion + " "+ orden;
			lista = enocJdbc.query(comando, new BitTramiteAlumnoMapper(), areaId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteAlumnoDao|lisTramiteFiltros|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, String> mapCuentaTramites( String fechaIni, String fechaFin, String estados) {
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 			   = new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT TRAMITE_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.BIT_TRAMITE_ALUMNO"
					+ " WHERE FECHA_INICIO BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"
					+ " AND ESTADO IN ("+estados+")"
					+ " GROUP BY TRAMITE_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), fechaIni, fechaFin);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteAlumnoDao|mapCuentaTramites|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapCuentaHijos( String estados) {
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT FOLIO_ORIGEN AS LLAVE, COUNT(*) AS VALOR FROM ENOC.BIT_TRAMITE_ALUMNO"
					+ " WHERE ESTADO IN ("+estados+")"
					+ " GROUP BY FOLIO_ORIGEN";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteAlumnoDao|mapCuentaHijos|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapTramitesPorEstado( String fechaIni, String fechaFin) {
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT TRAMITE_ID||ESTADO AS LLAVE, COUNT(*) AS VALOR FROM ENOC.BIT_TRAMITE_ALUMNO"
					+ " WHERE FECHA_INICIO BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"
					+ " GROUP BY TRAMITE_ID, ESTADO";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), fechaIni, fechaFin);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteAlumnoDao|mapTramitesPorEstado|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapTramites() {
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<Mapa> lista 		 = new ArrayList<Mapa>();		
		try{
			String comando = " SELECT TRAMITE_ID AS LLAVE, FOLIO AS VALOR FROM ENOC.BIT_TRAMITE_ALUMNO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteAlumnoDao|mapTramites|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, BitTramiteAlumno> mapTramitesAlumno(String codigoPersonal) {
		
		HashMap<String, BitTramiteAlumno> mapa = new HashMap<String, BitTramiteAlumno>();
		List<BitTramiteAlumno> lista 		 = new ArrayList<BitTramiteAlumno>();		
		try{
			String comando = " SELECT FOLIO, CODIGO_PERSONAL,TRAMITE_ID, TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL,ESTADO,AREA_ID,AREA_ORIGEN,FOLIO_ORIGEN, USUARIO, COMENTARIO"
					+ " FROM ENOC.BIT_TRAMITE_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new BitTramiteAlumnoMapper(),parametros);
			for (BitTramiteAlumno tramite : lista){
				mapa.put(tramite.getFolio(), tramite);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteAlumnoDao|mapTramitesAlumno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapTramitesSinOrigenSinEtiquetas() {
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT FOLIO AS LLAVE, TRAMITE_ID AS VALOR FROM ENOC.BIT_TRAMITE_ALUMNO"
					+ " WHERE FOLIO NOT IN (SELECT DISTINCT(FOLIO_ORIGEN) FROM ENOC.BIT_TRAMITE_ALUMNO)"
					+ " AND FOLIO NOT IN(SELECT FOLIO FROM ENOC.BIT_ETIQUETA)";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteAlumnoDao|mapTramitesSinOrigenSinEtiquetas|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapTotalPorTramite() {		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT TRAMITE_ID AS LLAVE, COUNT(TRAMITE_ID) AS VALOR FROM ENOC.BIT_TRAMITE_ALUMNO WHERE FECHA_FINAL IS NOT NULL GROUP BY TRAMITE_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteAlumnoDao|mapTramitesTerminados|:"+ex);
		}
		return mapa;
	}
	
}
