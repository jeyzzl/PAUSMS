package aca.encuesta.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class EncPeriodoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( EncPeriodo periodo ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO "+
				"ENOC.ENC_PERIODO( PERIODO_ID, PERIODO_NOMBRE, FECHA_INI, FECHA_FIN, ESTADO) "+
				"VALUES( ?, ?, TO_DATE(?,'YYYY-MM-DD'), TO_DATE(?,'YYYY-MM-DD'), ?)";
			Object[] parametros = new Object[] {periodo.getPeriodoId(), periodo.getPeriodoNombre(),
					periodo.getFechaIni(), periodo.getFechaFin(), periodo.getEstado()};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.EncPeriodoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	public boolean updateReg( EncPeriodo periodo ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ENC_PERIODO "+ 
				"SET PERIODO_NOMBRE = ?, FECHA_INI = TO_DATE(?,'YYYY-MM-DD'), FECHA_FIN = TO_DATE(?,'YYYY-MM-DD'), ESTADO = ? WHERE PERIODO_ID = ?";
			Object[] parametros = new Object[] {periodo.getPeriodoNombre(), periodo.getFechaIni(),
			periodo.getFechaFin(), periodo.getEstado(), periodo.getPeriodoId()};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.EncPeriodoDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	
	public boolean deleteReg( String periodoId ) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.ENC_PERIODO WHERE PERIODO_ID = ?";
			Object[] parametros = new Object[] {periodoId};			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.EncPeriodoDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public EncPeriodo mapeaRegId(String periodoId) {
		
		EncPeriodo periodo = new EncPeriodo();
		
		try{
			String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI,'YYYY-MM-DD') AS FECHA_INI, TO_CHAR(FECHA_FIN,'YYYY-MM-DD') AS FECHA_FIN, ESTADO "
					+ " FROM ENOC.ENC_PERIODO WHERE PERIODO_ID = ?";
			Object[] parametros = new Object[] {periodoId};
			periodo = enocJdbc.queryForObject(comando, new EncPeriodoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.EncPeriodoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return periodo;
	}
	
	public boolean existeReg( String periodoId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ENC_PERIODO WHERE PERIODO_ID = ?";
			Object[] parametros = new Object[] {periodoId};
			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.EncPeriodoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean contestoEncuesta(String codigoPersonal, String periodoId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENC_PERIODO WHERE SYSDATE BETWEEN FECHA_INI AND FECHA_FIN AND ESTADO = 'A' AND"
					+ " PERIODO_ID = (SELECT PERIODO_ID FROM ENC_PERIODO_RES WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ?)";
			Object[] parametros = new Object[] {codigoPersonal,periodoId};
			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.EncPeriodoDao|contestoEncuesta|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() {
		int maximo = 1;		
		try{
			String comando = "SELECT COALESCE(MAX(PERIODO_ID)+1,1) FROM ENOC.ENC_PERIODO";	
			if (enocJdbc.queryForObject(comando, Integer.class)>=1){
				maximo = enocJdbc.queryForObject(comando, Integer.class);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.EncPeriodoDao|maximoReg|:"+ex);
		}		
		return String.valueOf(maximo);
	}
	
	public String getPeriodoActivo() {		
		String periodo = "0";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ENC_PERIODO WHERE ESTADO = 'A'";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
				comando = "SELECT PERIODO_ID FROM ENOC.ENC_PERIODO WHERE ESTADO = 'A'";
				periodo = enocJdbc.queryForObject(comando, String.class);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.EncPeriodoDao|getPeriodoActivo|:"+ex);
		}		
		return periodo;
	}
	
	public String getPeriodo( String fecha) {
		
		String periodo = "x";		
		try{
			String comando = "SELECT PERIODO_ID AS PERIODO FROM ENOC.ENC_PERIODO WHERE TO_DATE(?,'DD-MM-YY') BETWEEN FECHA_INI AND FECHA_FIN";
			periodo = enocJdbc.queryForObject(comando, String.class, fecha);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.EncPeriodoDao|getPeriodo|:"+ex);
		}		
		return periodo;
	}
	
	public String getNombre( String periodo) {
		
		String nombre = "x";		
		try{
			String comando = "SELECT NOMBRE_PERIODO FROM ENOC.ENC_PERIODO WHERE PERIODO_ID = ?";
			nombre = enocJdbc.queryForObject(comando, String.class, periodo);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.EncPeriodoDao|getNombre|:"+ex);
		}		
		return nombre;
	}
	
	
	public List<EncPeriodo> getListAll( String orden ) {
	
		List<EncPeriodo> lista = new ArrayList<EncPeriodo>();
		
		try{
			String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI,'YYYY-MM-DD') AS FECHA_INI, TO_CHAR(FECHA_FIN,'YYYY-MM-DD') AS FECHA_FIN, ESTADO " +
					"FROM ENOC.ENC_PERIODO "+orden;
			lista = enocJdbc.query(comando, new EncPeriodoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.EncPeriodoDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,EncPeriodo> getMapAll( String orden ) {
		
		HashMap<String,EncPeriodo> mapa = new HashMap<String,EncPeriodo>();
		List<EncPeriodo> lista = new ArrayList<EncPeriodo>();
		
		try{
			String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI,'YYYY-MM-DD') AS FECHA_INI, TO_CHAR(FECHA_FINAL,'YYYY-MM-DD') AS FECHA_FIN, ESTADO"+
					"FROM ENOC.ENC_PERIODO "+ orden;
			lista = enocJdbc.query(comando, new EncPeriodoMapper());
			
			for(EncPeriodo objeto:lista){
				mapa.put(objeto.getPeriodoId(), objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.EncPeriodoDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}
	
	
	
//	public List<EncPeriodo> getLista( String sPeriodo, String orden) {
//		
//		List<CatPeriodo> lista = new ArrayList<EncPeriodo>();
//		
//		try{
//			String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE , TO_CHAR(FECHA_INI,'YYYY-MM-DD') AS F_INICIO, TO_CHAR(FECHA_FIN,'YYYY-MM-DD') AS F_FINAL, ESTADO"
//					+ " FROM ENOC.CAT_PERIODO"
//					+ " WHERE PERDIODO_ID IN (SELECT PERIODO FROM ENOC.CARGA WHERE CARGA_ID IN (SELECT DISTINCT(CARGA_ID) FROM ENOC.CARGA_BLOQUE)) "+orden;
//			lista = enocJdbc.query(comando, new CatPeriodoMapper());			
//		}catch(Exception ex){
//			System.out.println("Error - aca.catalogo.spring.CatPeriodoDao|getLista|:"+ex);
//		}		
//		return lista;
//	}
	

}