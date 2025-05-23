package aca.bitacora.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BitTramiteDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( BitTramite tramite) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.BIT_TRAMITE"+ 
				"(TRAMITE_ID, NOMBRE, MINIMO, MAXIMO, PROMEDIO, AREA_ID, REQUISITOS, TIPO, SOL_ALUMNO, IMPORTE)"+
				" VALUES(TO_NUMBER(?, '9999'), ?, TO_NUMBER(?,'9999.99'), TO_NUMBER(?,'9999.99'), TO_NUMBER(?,'9999.99'), TO_NUMBER(?,'999'), ?, ?, ?, ?)";
			Object[] parametros = new Object[] {tramite.getTramiteId(), tramite.getNombre(),
			tramite.getMinimo(), tramite.getMaximo(), tramite.getPromedio(),
			tramite.getAreaId(), tramite.getRequisitos(), tramite.getTipo(),tramite.getSolAlumno(), tramite.getImporte()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteDao|insertReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean updateReg( BitTramite tramite) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.BIT_TRAMITE "
					+ " SET NOMBRE = ?, "
					+ " MINIMO = TO_NUMBER(?,'9999.99'), "
					+ " MAXIMO = TO_NUMBER(?,'9999.99'), "
					+ " PROMEDIO = TO_NUMBER(?,'9999.99'), "
					+ " AREA_ID = TO_NUMBER(?, '999'),"
					+ " REQUISITOS = ?,"
					+ " TIPO = ?,"		
					+ " SOL_ALUMNO = ?,"
					+ " IMPORTE = ? "
					+ " WHERE TRAMITE_ID = TO_NUMBER(?, '9999')";
			Object[] parametros = new Object[] {tramite.getNombre(), tramite.getMinimo(),
			tramite.getMaximo(), tramite.getPromedio(), tramite.getAreaId(),
			tramite.getRequisitos(), tramite.getTipo(), tramite.getSolAlumno(), tramite.getImporte(), tramite.getTramiteId()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean existeReg(String tramiteId) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BIT_TRAMITE WHERE TRAMITE_ID = TO_NUMBER(?, '9999')";
			Object[] parametros = new Object[] {tramiteId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return ok;
	}
	
	public boolean actualizaTiempos( String minimo, String maximo, String promedio, String tramiteId) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.BIT_TRAMITE"
					+ " SET MINIMO = TO_NUMBER(?,'9999.99'), "
					+ " MAXIMO = TO_NUMBER(?,'9999.99'), "
					+ " PROMEDIO = TO_NUMBER(?,'9999.99')"
					+ " WHERE TRAMITE_ID = TO_NUMBER(?, '9999')";
			Object[] parametros = new Object[] {minimo, maximo, promedio, tramiteId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteDao|actualizaTiempos|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg( String tramiteId) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.BIT_TRAMITE WHERE TRAMITE_ID = TO_NUMBER(?, '9999')";
			Object[] parametros = new Object[] {tramiteId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public BitTramite mapeaRegId(  String tramiteId) {
		
		BitTramite objeto = new BitTramite();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BIT_TRAMITE WHERE TRAMITE_ID = TO_NUMBER(?,'9999')";
			Object[] parametros = new Object[] {tramiteId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = " SELECT TRAMITE_ID, NOMBRE, MINIMO, MAXIMO, PROMEDIO, AREA_ID, REQUISITOS, TIPO, SOL_ALUMNO, COSTO, IMPORTE"
						+ " FROM ENOC.BIT_TRAMITE WHERE TRAMITE_ID = TO_NUMBER(?,'9999')";				
				objeto = enocJdbc.queryForObject(comando, new BitTramiteMapper(),parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return objeto;
	}
	
	public String getNombre(  String tramiteId) {
		String nombre = "-";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BIT_TRAMITE WHERE TRAMITE_ID = TO_NUMBER(?,'9999')";
			Object[] parametros = new Object[] {tramiteId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = " SELECT NOMBRE FROM ENOC.BIT_TRAMITE WHERE TRAMITE_ID = TO_NUMBER(?,'9999')";				
				nombre = enocJdbc.queryForObject(comando, String.class,parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteDao|getNombre|:"+ex);
			ex.printStackTrace();
		}
		return nombre;
	}
	
	public String getAreaId(  String tramiteId) {
		String area = "0";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BIT_TRAMITE WHERE TRAMITE_ID = TO_NUMBER(?,'9999')";
			Object[] parametros = new Object[] {tramiteId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = " SELECT AREA_ID FROM ENOC.BIT_TRAMITE WHERE TRAMITE_ID = TO_NUMBER(?,'9999')";			
				area = enocJdbc.queryForObject(comando, String.class,parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteDao|getAreaId|:"+ex);
			ex.printStackTrace();
		}
		return area;
	}
	
	public List<BitTramite> lisTramites( String orden) {
		
		List<BitTramite> lista = new ArrayList<BitTramite>();		
		try{
			String comando = " SELECT TRAMITE_ID, NOMBRE, MINIMO, MAXIMO, PROMEDIO, AREA_ID, REQUISITOS, TIPO, SOL_ALUMNO, COSTO, IMPORTE"
					+ " FROM ENOC.BIT_TRAMITE "+orden;
			lista = enocJdbc.query(comando, new BitTramiteMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteDao|getTramiteList|:"+ex);
		}
		return lista;
	}
	
	public List<BitTramite> lisTramitesPorTipos( String tipos, String orden) {
		
		List<BitTramite> lista = new ArrayList<BitTramite>();		
		try{
			String comando = " SELECT TRAMITE_ID, NOMBRE, MINIMO, MAXIMO, PROMEDIO, AREA_ID, REQUISITOS, TIPO, SOL_ALUMNO, COSTO, IMPORTE"
					+ " FROM ENOC.BIT_TRAMITE "
					+ " WHERE TIPO IN ("+tipos+") "+orden;
			lista = enocJdbc.query(comando, new BitTramiteMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteDao|getTramiteList|:"+ex);
		}
		return lista;
	}
	
	public List<BitTramite> lisTramites( String areaId, String orden) {
		
		List<BitTramite> lista = new ArrayList<BitTramite>();		
		try{
			String comando = " SELECT TRAMITE_ID, NOMBRE, MINIMO, MAXIMO, PROMEDIO, AREA_ID, REQUISITOS, TIPO, SOL_ALUMNO, COSTO, IMPORTE"
					+ " FROM ENOC.BIT_TRAMITE "
					+ " WHERE AREA_ID = TO_NUMBER(?,'999') "+orden;
			lista = enocJdbc.query(comando, new BitTramiteMapper(), areaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteDao|lisTramites|:"+ex);
		}
		return lista;
	}
	
	public String maximoReg() {
		String maximo = "1";		
		try{
			String comando =  "SELECT COALESCE(MAX(TRAMITE_ID)+1,1) FROM ENOC.BIT_TRAMITE";
 			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class);
			} 			
		}catch(Exception ex){
			System.out.println("Error -  aca.bitacora.spring.BitTramiteDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public String minTiempoTramite( String tramiteId, String estados) {
		String minimo = "1";		
		try{
			String comando =  "SELECT COALESCE(MIN(FECHA_FINAL-FECHA_INICIO),0) AS MINIMO"
					+ " FROM ENOC.BIT_TRAMITE_ALUMNO"
					+ " WHERE TRAMITE_ID = TO_NUMBER(?,'9999')"
					+ " AND FECHA_FINAL IS NOT NULL"
					+ " AND ESTADO IN ("+estados+") ";
			if (enocJdbc.queryForObject(comando, Integer.class, tramiteId) >= 0) {
 				minimo = enocJdbc.queryForObject(comando, String.class, tramiteId);
			}			
		}catch(Exception ex){
			System.out.println("Error -  aca.bitacora.spring.BitTramiteDao|minTiempoTramite|:"+ex);
		}
		return minimo;
	}
	
	public String maxTiempoTramite( String tramiteId, String estados){
		String promedio	= "0";
		try{
			String comando =  "SELECT COALESCE(MAX(FECHA_FINAL-FECHA_INICIO),0) AS MAXIMO"
					+ " FROM ENOC.BIT_TRAMITE_ALUMNO"
					+ " WHERE TRAMITE_ID = TO_NUMBER(?,'9999')"
					+ " AND FECHA_FINAL IS NOT NULL"
					+ " AND ESTADO IN ("+estados+")";
			if (enocJdbc.queryForObject(comando, Integer.class, tramiteId) >= 0) {
 				promedio = enocJdbc.queryForObject(comando, String.class, tramiteId);
			}			
		}catch(Exception ex){
			System.out.println("Error -  aca.bitacora.spring.BitTramiteDao|maxTiempoTramite|:"+ex);
		}
		return promedio;
	}
	
	public String promedioTiempoTramite( String tramiteId, String estados){
		String maximo = "1";		
		try{
			String comando =  "SELECT ROUND(COALESCE(AVG(FECHA_FINAL-FECHA_INICIO),0),2) AS PROMEDIO"
					+ " FROM ENOC.BIT_TRAMITE_ALUMNO"
					+ " WHERE TRAMITE_ID = TO_NUMBER(?,'9999')"
					+ " AND FECHA_FINAL IS NOT NULL"
					+ " AND ESTADO IN ("+estados+")";
			if (enocJdbc.queryForObject(comando, Integer.class, tramiteId) >= 0) {
 				maximo = enocJdbc.queryForObject(comando, String.class, tramiteId);
			}			
		}catch(Exception ex){
			System.out.println("Error -  aca.bitacora.spring.BitTramiteDao|promedioTiempoTramite|:"+ex);
		}
		return maximo;
	}
	
	public HashMap<String, BitTramite> mapTramites(){
		HashMap<String, BitTramite> mapa = new HashMap<String, BitTramite>();
		List<BitTramite> lista			 = new ArrayList<BitTramite>();		
		try{
			String comando = " SELECT TRAMITE_ID, NOMBRE, MINIMO, MAXIMO, PROMEDIO, AREA_ID, REQUISITOS, TIPO, SOL_ALUMNO, COSTO, IMPORTE "
					+ " FROM ENOC.BIT_TRAMITE";
			lista = enocJdbc.query(comando,new BitTramiteMapper());
			for(BitTramite objeto : lista){				
				mapa.put(objeto.getTramiteId(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteDao|mapTramites|:"+ex);
		}
		return mapa;
	}
}
