package aca.admision.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class AdmProcesoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AdmProceso proceso){
		boolean ok = false;		
		try{
			String comando ="INSERT INTO SALOMON.ADM_PROCESO(FOLIO, F_REGISTRO, F_SOLICITUD, F_DOCUMENTOS, F_ADMISION, F_CARTA, F_EXAMEN, F_DIRECTO)"
					+ " VALUES(TO_NUMBER(?,'9999999'), NOW(), NULL, NULL, NULL, NULL, NULL, NULL)";
			Object[] parametros = new Object[] { proceso.getFolio()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmProcesoDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg(String folio ){
		boolean ok = false;
		try{
			String comando = "DELETE FROM SALOMON.ADM_PROCESO WHERE FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] { folio };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmProcesoDao|deleteReg|:"+ex);
			ok = false;
		}
		
		return ok;
	}	
	
	public AdmProceso mapeaRegId(  String folio) {
		
		AdmProceso admProceso = new AdmProceso();		
		try {
			String comando = "SELECT COUNT(FOLIO) FROM SALOMON.ADM_PROCESO WHERE FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT FOLIO, TO_CHAR(F_REGISTRO, 'DD/MM/YYYY') AS F_REGISTRO,"
					+ " TO_CHAR(F_SOLICITUD, 'DD/MM/YYYY') AS F_SOLICITUD, TO_CHAR(F_DOCUMENTOS, 'DD/MM/YYYY') AS F_DOCUMENTOS,"
					+ " TO_CHAR(F_ADMISION, 'DD/MM/YYYY') AS F_ADMISION, TO_CHAR(F_CARTA, 'DD/MM/YYYY') AS F_CARTA, TO_CHAR(F_EXAMEN, 'DD/MM/YYYY') AS F_EXAMEN,"
					+ " TO_CHAR(F_DIRECTO, 'DD/MM/YYYY') AS F_DIRECTO"
					+ " FROM SALOMON.ADM_PROCESO"
					+ " WHERE FOLIO = TO_NUMBER(?, '9999999')";			
				admProceso = enocJdbc.queryForObject(comando, new AdmProcesoMapper(), parametros);			
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmProcesoDao|mapeaRegId|:"+ex);		
		}
		return admProceso;
	}
	
	public AdmProceso mapeaRegConHoras(  String folio) {
		
		AdmProceso admProceso = new AdmProceso();
		try {
			String comando = "SELECT COUNT(FOLIO) FROM SALOMON.ADM_PROCESO WHERE FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){			
				comando = "SELECT FOLIO, TO_CHAR(F_REGISTRO, 'DD/MM/YYYY HH24:MI') AS F_REGISTRO,"
					+ " TO_CHAR(F_SOLICITUD, 'DD/MM/YYYY HH24:MI') AS F_SOLICITUD, TO_CHAR(F_DOCUMENTOS, 'DD/MM/YYYY HH24:MI') AS F_DOCUMENTOS,"
					+ " TO_CHAR(F_ADMISION, 'DD/MM/YYYY HH24:MI') AS F_ADMISION, TO_CHAR(F_CARTA, 'DD/MM/YYYY HH24:MI') AS F_CARTA, TO_CHAR(F_EXAMEN, 'DD/MM/YYYY HH24:MI') AS F_EXAMEN,"
					+ " TO_CHAR(F_DIRECTO, 'DD/MM/YYYY HH24:MI') AS F_DIRECTO"
					+ " FROM SALOMON.ADM_PROCESO"
					+ " WHERE FOLIO = TO_NUMBER(?, '9999999')";				
				admProceso = enocJdbc.queryForObject(comando, new AdmProcesoMapper(), parametros);			
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmProcesoDao|mapeaRegConHoras|:"+ex);
		}	
		return admProceso;
	}
	
	public boolean existeReg(String folio) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(FOLIO) FROM SALOMON.ADM_PROCESO WHERE FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmProcesoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateFecha(int numFecha, String folio){
		boolean ok 		= false;
		String comando 	= "";		
		Object[] parametros = new Object[] {folio};		
		try{
			comando = "SELECT COUNT(*) FROM SALOMON.ADM_PROCESO WHERE FOLIO = TO_NUMBER(?,'9999999')";
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){			
			
				if(numFecha==1){
					comando = "UPDATE SALOMON.ADM_PROCESO SET F_REGISTRO = NOW() WHERE FOLIO = TO_NUMBER(?,'9999999')";
				}else if(numFecha==2){
					comando = "UPDATE SALOMON.ADM_PROCESO SET F_SOLICITUD = NOW() WHERE FOLIO = TO_NUMBER(?,'9999999')";
				}else if(numFecha==3){
					comando = "UPDATE SALOMON.ADM_PROCESO SET F_DOCUMENTOS = NOW() WHERE FOLIO = TO_NUMBER(?,'9999999')";
				}else if(numFecha==4){
					comando = "UPDATE SALOMON.ADM_PROCESO SET F_ADMISION = NOW() WHERE FOLIO = TO_NUMBER(?,'9999999')";
				}else if(numFecha==5){
					comando = "UPDATE SALOMON.ADM_PROCESO SET F_CARTA = NOW() WHERE FOLIO = TO_NUMBER(?,'9999999')";
				}else if(numFecha==6){
					comando = "UPDATE SALOMON.ADM_PROCESO SET F_EXAMEN = NOW() WHERE FOLIO = TO_NUMBER(?,'9999999')";
				}else if(numFecha==7){
					comando = "UPDATE SALOMON.ADM_PROCESO SET F_DIRECTO = NOW() WHERE FOLIO = TO_NUMBER(?,'9999999')";
				}
				if (enocJdbc.update(comando,parametros)==1){
					ok = true;
				}				
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmProcesoDao|updateReg|:"+ex);			
		}		
		return ok;
	}
	
	public List<AdmProceso> getAll( String orden) {
		
		List<AdmProceso> lista	= new ArrayList<AdmProceso>();
		
		try{
			String comando = "SELECT FOLIO, TO_CHAR(F_REGISTRO, 'DD/MM/YYYY') AS F_REGISTRO, TO_CHAR(F_SOLICITUD, 'DD/MM/YYYY') AS F_SOLICITUD,"
					+ " TO_CHAR(F_DOCUMENTOS, 'DD/MM/YYYY') AS F_DOCUMENTOS, TO_CHAR(F_ADMISION, 'DD/MM/YYYY') AS F_ADMISION,"
					+ " TO_CHAR(F_CARTA, 'DD/MM/YYYY') AS F_CARTA, TO_CHAR(F_EXAMEN, 'DD/MM/YYYY') AS F_EXAMEN,"
					+ " TO_CHAR(F_DIRECTO, 'DD/MM/YYYY') AS F_DIRECTO"
					+ " FROM SALOMON.ADM_PROCESO " + orden;
			lista = enocJdbc.query(comando, new AdmProcesoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmProcesoDao|getAll|:"+ex);
		}
		
		return lista;
	}
	
	public int getCantidad(String orden) {
		
		int cantidad = 0;		
		try{
			String comando = "SELECT COUNT(FOLIO) AS CANTIDAD FROM SALOMON.ADM_PROCESO "+orden;		
			cantidad = enocJdbc.queryForObject(comando, Integer.class);
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmProcesoDao|getCantidad|:"+ex);
		}
		
		return cantidad;
	}
	
	public List<String> listaAnios( int columnaBase, String orden) {
		List<String> lista	= new ArrayList<String>();	
		
		String nombreColumna = "F_REGISTRO";
		if(columnaBase==3) nombreColumna = "F_SOLICITUD";
		if(columnaBase==4) nombreColumna = "F_DOCUMENTOS";
		if(columnaBase==5) nombreColumna = "F_ADMISION";
		if(columnaBase==6) nombreColumna = "F_CARTA";
		if(columnaBase==7) nombreColumna = "F_EXAMEN";
		if(columnaBase==8) nombreColumna = "F_DIRECTO";
		
		try{
			String comando = "SELECT DISTINCT(TO_CHAR("+nombreColumna+", 'YYYY')) FROM SALOMON.ADM_PROCESO WHERE TO_CHAR("+nombreColumna+", 'YYYY') IS NOT NULL" + orden; 
			lista = enocJdbc.queryForList(comando, String.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmProcesoDao|listaAnios|:"+ex);
		}
		
		return lista;
	}
	
	public List<String> listaMeses( int columnaBase, String anio, String orden) {
		List<String> lista	= new ArrayList<String>();
		String comando = "";
		try{			
			if(columnaBase==3){				
				comando = "SELECT DISTINCT(TO_CHAR(F_SOLICITUD,'MM')) FROM SALOMON.ADM_PROCESO WHERE F_SOLICITUD IS NOT NULL AND TO_CHAR(F_SOLICITUD,'YYYY') = ? " + orden;
			}else if(columnaBase==4){				
				comando = "SELECT DISTINCT(TO_CHAR(F_DOCUMENTOS,'MM')) FROM SALOMON.ADM_PROCESO WHERE F_DOCUMENTOS IS NOT NULL AND TO_CHAR(F_DOCUMENTOS,'YYYY') = ? " + orden;
			}else if(columnaBase==5){				
				comando = "SELECT DISTINCT(TO_CHAR(F_ADMISION,'MM')) FROM SALOMON.ADM_PROCESO WHERE F_ADMISION IS NOT NULL AND TO_CHAR(F_ADMISION,'YYYY') = ? " + orden;
			}else if(columnaBase==6){				
				comando = "SELECT DISTINCT(TO_CHAR(F_CARTA,'MM')) FROM SALOMON.ADM_PROCESO WHERE F_CARTA IS NOT NULL AND TO_CHAR(F_CARTA,'YYYY') = ? " + orden;
			}else if(columnaBase==7){				
				comando = "SELECT DISTINCT(TO_CHAR(F_EXAMEN,'MM')) FROM SALOMON.ADM_PROCESO WHERE F_EXAMEN IS NOT NULL AND TO_CHAR(F_EXAMEN,'YYYY') = ? " + orden;
			}else if(columnaBase==8){				
				comando = "SELECT DISTINCT(TO_CHAR(F_DIRECTO,'MM')) FROM SALOMON.ADM_PROCESO WHERE F_DIRECTO IS NOT NULL AND TO_CHAR(F_DIRECTO,'YYYY') = ? " + orden;
			}else{
				comando = "SELECT DISTINCT(TO_CHAR(F_REGISTRO,'MM')) FROM SALOMON.ADM_PROCESO WHERE F_REGISTRO IS NOT NULL AND TO_CHAR(F_REGISTRO,'YYYY') = ? " + orden;
			}			
			lista = enocJdbc.queryForList(comando, String.class, anio);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmProcesoDao|listaMeses|:"+ex);
		}		
		return lista;
	}
	
	public List<String> listaDias( String anio, String mes, String orden) {
		List<String> lista	= new ArrayList<String>();		
		try{
			String comando = " SELECT DISTINCT(TO_CHAR(F_REGISTRO, 'DD')) AS DIAS"
					+ " FROM SALOMON.ADM_PROCESO WHERE TO_CHAR(F_REGISTRO, 'YYYY') = ?"
					+ " AND TO_CHAR(F_REGISTRO, 'MM') = ? " + orden;			
			lista = enocJdbc.queryForList(comando, String.class, anio, mes);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmProcesoDao|listaDias|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,String> mapFechaRegistro(String year ){
		HashMap<String, String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT FOLIO AS LLAVE, TO_CHAR(F_REGISTRO,'DD/MM/YYYY') AS VALOR FROM SALOMON.ADM_PROCESO"
					+ " WHERE F_REGISTRO IS NOT NULL AND TO_CHAR(F_REGISTRO,'YYYY')= ?";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), new Object[] {year});
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmProcesoDao|mapFechaAceptado|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapFechaRegistro(){
		HashMap<String, String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT FOLIO AS LLAVE, TO_CHAR(F_REGISTRO,'DD/MM/YYYY') AS VALOR FROM SALOMON.ADM_PROCESO"
					+ " WHERE F_REGISTRO IS NOT NULL";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmProcesoDao|mapFechaAceptado|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapFechaSolicitud(String year ){
		HashMap<String, String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT FOLIO AS LLAVE, TO_CHAR(F_SOLICITUD,'DD/MM/YYYY') AS VALOR FROM SALOMON.ADM_PROCESO"
					+ " WHERE F_SOLICITUD IS NOT NULL AND TO_CHAR(F_SOLICITUD,'YYYY')= ?";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), new Object[] {year});
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmProcesoDao|mapFechaAceptado|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapFechaSolicitud(){
		HashMap<String, String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT FOLIO AS LLAVE, TO_CHAR(F_SOLICITUD,'DD/MM/YYYY') AS VALOR FROM SALOMON.ADM_PROCESO"
					+ " WHERE F_SOLICITUD IS NOT NULL";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmProcesoDao|mapFechaAceptado|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapFechaDocumentos(String year ){
		HashMap<String, String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT FOLIO AS LLAVE, TO_CHAR(F_DOCUMENTOS,'DD/MM/YYYY') AS VALOR FROM SALOMON.ADM_PROCESO"
					+ " WHERE F_DOCUMENTOS IS NOT NULL AND TO_CHAR(F_DOCUMENTOS,'YYYY')= ?";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), new Object[] {year});
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmProcesoDao|mapFechaAceptado|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapFechaDocumentos(){
		HashMap<String, String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT FOLIO AS LLAVE, TO_CHAR(F_DOCUMENTOS,'DD/MM/YYYY') AS VALOR FROM SALOMON.ADM_PROCESO"
					+ " WHERE F_DOCUMENTOS IS NOT NULL";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmProcesoDao|mapFechaAceptado|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapFechaAceptado(String year ){
		HashMap<String, String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT FOLIO AS LLAVE, TO_CHAR(F_ADMISION,'DD/MM/YYYY') AS VALOR FROM SALOMON.ADM_PROCESO"
					+ " WHERE F_ADMISION IS NOT NULL AND TO_CHAR(F_ADMISION,'YYYY')= ?";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), new Object[] {year});
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmProcesoDao|mapFechaAceptado|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapFechaAceptado(){
		HashMap<String, String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT FOLIO AS LLAVE, TO_CHAR(F_ADMISION,'DD/MM/YYYY') AS VALOR FROM SALOMON.ADM_PROCESO"
					+ " WHERE F_ADMISION IS NOT NULL";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmProcesoDao|mapFechaAceptado|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapFechaCarta(String year ){
		HashMap<String, String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT FOLIO AS LLAVE, TO_CHAR(F_CARTA,'DD/MM/YYYY') AS VALOR FROM SALOMON.ADM_PROCESO"
					+ " WHERE F_CARTA IS NOT NULL AND TO_CHAR(F_CARTA,'YYYY')= ?";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), new Object[] {year});
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmProcesoDao|mapFechaCarta|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapFechaCarta(){
		HashMap<String, String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT FOLIO AS LLAVE, TO_CHAR(F_CARTA,'DD/MM/YYYY') AS VALOR FROM SALOMON.ADM_PROCESO"
					+ " WHERE F_CARTA IS NOT NULL";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmProcesoDao|mapFechaCarta|:"+ex);
		}
		
		return mapa;
	}
}