package aca.catalogo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class CatPeriodoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatPeriodo periodo ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO "+
				"ENOC.CAT_PERIODO( PERIODO_ID, NOMBRE_PERIODO, F_INICIO, F_FINAL, ESTADO, EXCEPTO_PRON) "+
				"VALUES( ?, ?, TO_DATE(?,'YYYY-MM-DD'), TO_DATE(?,'YYYY-MM-DD'), ?, ?)";
			Object[] parametros = new Object[] {periodo.getPeriodoId(), periodo.getNombre(),
					periodo.getFechaIni(), periodo.getFechaFin(), periodo.getEstado(),  periodo.getExceptoPron()};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPeriodoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	public boolean updateReg( CatPeriodo periodo ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CAT_PERIODO "+ 
				"SET NOMBRE_PERIODO = ?, F_INICIO = TO_DATE(?,'YYYY-MM-DD'), F_FINAL = TO_DATE(?,'YYYY-MM-DD'), ESTADO = ?, EXCEPTO_PRON = ? WHERE PERIODO_ID = ? ";
			Object[] parametros = new Object[] {periodo.getNombre(), periodo.getFechaIni(),
			periodo.getFechaFin(), periodo.getEstado(), periodo.getExceptoPron(), periodo.getPeriodoId()};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPeriodoDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	
	public boolean deleteReg( String periodoId ) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = ?";
			Object[] parametros = new Object[] {periodoId};			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPeriodoDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public CatPeriodo mapeaRegId(String periodoId) {
		
		CatPeriodo periodo = new CatPeriodo();
		
		try{
			String comando = "SELECT PERIODO_ID, NOMBRE_PERIODO, TO_CHAR(F_INICIO,'YYYY-MM-DD') AS F_INICIO, TO_CHAR(F_FINAL,'YYYY-MM-DD') AS F_FINAL, ESTADO, EXCEPTO_PRON "
					+ " FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = ?";
			Object[] parametros = new Object[] {periodoId};
			periodo = enocJdbc.queryForObject(comando, new CatPeriodoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPeriodoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return periodo;
	}
	
	public boolean existeReg( String periodoId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = ?";
			Object[] parametros = new Object[] {periodoId};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPeriodoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String getPeriodo() {		
		String periodo = "0000";
		List<CatPeriodo> lisPeriodos = new ArrayList<CatPeriodo>();
		try{
			String comando = "SELECT COUNT(PERIODO_ID) FROM ENOC.CAT_PERIODO " + 
					"WHERE TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD-MM-YY') BETWEEN F_INICIO AND F_FINAL";
			if (enocJdbc.queryForObject(comando, Integer.class)>=1){
				comando = "SELECT PERIODO_ID AS PERIODO FROM ENOC.CAT_PERIODO " + 
						"WHERE TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD-MM-YY') BETWEEN F_INICIO AND F_FINAL";
				periodo = enocJdbc.queryForObject(comando, String.class);
			}else{
				lisPeriodos = this.getListAll("ORDER BY PERIODO_ID");
				if (lisPeriodos.size() >= 1) periodo = lisPeriodos.get(0).getPeriodoId();  
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPeriodoDao|getPeriodo|:"+ex);
		}		
		return periodo;
	}
	
	public String getPeriodoActivo() {
		
		String periodo = "0000";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_PERIODO WHERE ESTADO = 'A'";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
				comando = "SELECT PERIODO_ID FROM ENOC.CAT_PERIODO WHERE ESTADO = 'A'";
				periodo = enocJdbc.queryForObject(comando, String.class);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPeriodoDao|getPeriodoActivo|:"+ex);
		}		
		return periodo;
	}
	
	public String getPeriodo( String fecha) {
		
		String periodo = "x";		
		try{
			String comando = "SELECT PERIODO_ID AS PERIODO FROM ENOC.CAT_PERIODO WHERE TO_DATE(?,'DD-MM-YY') BETWEEN F_INICIO AND F_FINAL";
			periodo = enocJdbc.queryForObject(comando, String.class, fecha);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPeriodoDao|getPeriodo|:"+ex);
		}		
		return periodo;
	}
	
	public String getNombre( String periodo) {
		
		String nombre = "x";		
		try{
			String comando = "SELECT NOMBRE_PERIODO FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = ?";
			nombre = enocJdbc.queryForObject(comando, String.class, periodo);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPeriodoDao|getNombre|:"+ex);
		}		
		return nombre;
	}
	
	public String getExceptoPron( String periodo) {
		
		String excepto = "-";		
		try{
			String comando = "SELECT EXCEPTO_PRON FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = ? ";
			excepto = enocJdbc.queryForObject(comando, String.class, periodo);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPeriodoDao|getExceptoPron|:"+ex);
		}
		
		return excepto;
	}

	public String getSigPeriodo(String periodo) {
		
		String sigPeriodo	= periodo;
		String temp			= "0";
		
		try{
			temp 		=  String.valueOf(Integer.parseInt(periodo)+101);
			sigPeriodo 	=  temp.length()==3?"0"+temp:temp;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPeriodoDao|getNombre|:"+ex);
		}		
		return sigPeriodo;
	}
	
	public boolean existeFechaBetween( String periodoId, String fecha) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_PERIODO " +
						" WHERE PERIODO_ID = ? " +
						" AND TO_DATE(?, 'DD/MM/YYYY') >= F_INICIO" +
						" AND TO_DATE(?, 'DD/MM/YYYY') <= F_FINAL";
			if (enocJdbc.queryForObject(comando, Integer.class, periodoId, fecha, fecha)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumno|existeFechaBetween|:"+ex);
		}
		
		return ok;
	}
	
	public List<CatPeriodo> getListAll( String orden ) {
	
		List<CatPeriodo> lista = new ArrayList<CatPeriodo>();
		
		try{
			String comando = "SELECT PERIODO_ID, NOMBRE_PERIODO, TO_CHAR(F_INICIO,'YYYY-MM-DD') AS F_INICIO, TO_CHAR(F_FINAL,'YYYY-MM-DD') AS F_FINAL, ESTADO, EXCEPTO_PRON "+
					"FROM ENOC.CAT_PERIODO "+orden;
			lista = enocJdbc.query(comando, new CatPeriodoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPeriodoDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,CatPeriodo> getMapAll( String orden ) {
		
		HashMap<String,CatPeriodo> mapa = new HashMap<String,CatPeriodo>();
		List<CatPeriodo> lista = new ArrayList<CatPeriodo>();
		
		try{
			String comando = "SELECT PERIODO_ID, NOMBRE_PERIODO, TO_CHAR(F_INICIO,'YYYY-MM-DD') AS F_INICIO, TO_CHAR(F_FINAL,'YYYY-MM-DD') AS F_FINAL, ESTADO, EXCEPTO_PRON "+
					"FROM ENOC.CAT_PERIODO "+ orden;
			lista = enocJdbc.query(comando, new CatPeriodoMapper());
			
			for(CatPeriodo objeto:lista){
				mapa.put(objeto.getPeriodoId(), objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPeriodoDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}
	
	public List<CatPeriodo> getLista( String sPeriodo, String orden) {
		
		List<CatPeriodo> lista = new ArrayList<CatPeriodo>();
		
		try{
			String comando = "SELECT PERIODO_ID, NOMBRE_PERIODO, TO_CHAR(F_INICIO,'YYYY-MM-DD') AS F_INICIO, TO_CHAR(F_FINAL,'YYYY-MM-DD') AS F_FINAL, ESTADO, EXCEPTO_PRON"
					+ " FROM ENOC.CAT_PERIODO"
					+ " WHERE PERDIODO_ID IN (SELECT PERIODO FROM ENOC.CARGA WHERE CARGA_ID IN (SELECT DISTINCT(CARGA_ID) FROM ENOC.CARGA_BLOQUE)) "+orden;
			lista = enocJdbc.query(comando, new CatPeriodoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPeriodoDao|getLista|:"+ex);
		}		
		return lista;
	}
	
	public List<CatPeriodo> getListPeriodosMaestro( String codigoMaestro, String orden) {
		
		List<CatPeriodo> lista = new ArrayList<CatPeriodo>();
		
		try{
			String comando = "SELECT PERIODO_ID, NOMBRE_PERIODO, TO_CHAR(F_INICIO,'YYYY-MM-DD') AS F_INICIO, TO_CHAR(F_FINAL,'YYYY-MM-DD') AS F_FINAL, ESTADO, EXCEPTO_PRON"
					+ " FROM ENOC.CAT_PERIODO"
					+ " WHERE PERIODO_ID IN (SELECT PERIODO FROM ENOC.CARGA WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ? OR CODIGO_OTRO = ?)) "+orden;
			lista = enocJdbc.query(comando, new CatPeriodoMapper(), codigoMaestro, codigoMaestro);
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPeriodoDao|getListPeriodosMaestro|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, String> getCaragasEnPeriodo( String orden ) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT PERIODO AS LLAVE, COUNT(CARGA_ID) AS VALOR FROM ENOC.CARGA GROUP BY PERIODO " + orden;
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			
			for(aca.Mapa objeto:lista){
				mapa.put(objeto.getLlave(), objeto.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPeriodoDao|getCaragasEnPeriodo|:"+ex);
		}
		
		return mapa;
	}
}