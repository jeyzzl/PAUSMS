package aca.carga.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalMapper;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanMapper;

@Component
public class CargaFinancieroDao {	
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CargaFinanciero financiero ) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.CARGA_FINANCIERO "
					+ " (CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, FECHA, STATUS, COMENTARIO, USUARIO) "
					+ " VALUES( ?, ?, TO_NUMBER(?,'99'), TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), ?, ?, ?)";
			Object[] parametros = new Object[] {
						financiero.getCodigoPersonal(), financiero.getCargaId(), financiero.getBloqueId(), 
						financiero.getFecha(), financiero.getStatus(), financiero.getComentario(), financiero.getUsuario()
					};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargFinancieroDao|insertReg|:"+ex);
		}		
		return ok;
	}	
	
	public boolean updateReg( CargaFinanciero financiero ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CARGA_FINANCIERO"
					+ " SET FECHA = TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'),"
					+ " STATUS = ?,"					
					+ " COMENTARIO = ?, "
					+ " USUARIO = ?"	
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID = ?"
					+ " AND BLOQUE_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {
						financiero.getFecha(), financiero.getStatus(), financiero.getComentario(), financiero.getUsuario(), 
						financiero.getCodigoPersonal(), financiero.getCargaId(), financiero.getBloqueId()
					};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargFinancieroDao|updateReg|:"+ex);		 
		}
		return ok;
	}
	
	public boolean deleteReg( String codigoPersonal, String cargaId, String bloqueId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_FINANCIERO "
					+ " WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, cargaId, bloqueId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargFinancieroDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public CargaFinanciero mapeaRegId( String codigoPersonal, String cargaId, String bloqueId ) {
		
		CargaFinanciero objeto = new CargaFinanciero();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_FINANCIERO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, cargaId, bloqueId};			
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, STATUS, COMENTARIO, USUARIO"
						+ " FROM ENOC.CARGA_FINANCIERO"
						+ " WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";
				objeto = enocJdbc.queryForObject(comando, new CargaFinancieroMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargFinancieroDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg( String codigoPersonal, String cargaId, String bloqueId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_FINANCIERO"
					+ " WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, cargaId, bloqueId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargFinancieroDao|existeReg|:"+ex);
		}
		return ok;
	}	
	
	public List<CargaFinanciero> getListAll( String orden ) {
		
		List<CargaFinanciero> lista = new ArrayList<CargaFinanciero>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, STATUS, COMENTARIO, USUARIO"
					+ " FROM ENOC.CARGA_FINANCIERO "+orden;
			lista = enocJdbc.query(comando, new CargaFinancieroMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargFinancieroDao|getListAll|:"+ex);
		}
		return lista;
	}
	
public List<CargaFinanciero> getListPorCargayBloque( String cargaId, String bloqueId, String orden ) {
		
		List<CargaFinanciero> lista = new ArrayList<CargaFinanciero>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, STATUS, COMENTARIO, USUARIO "
					+ " FROM ENOC.CARGA_FINANCIERO WHERE CARGA_ID = ? AND BLOQUE_ID = ? "+orden;
			Object[] parametros = new Object[] {cargaId, bloqueId};
			lista = enocJdbc.query(comando, new CargaFinancieroMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargFinancieroDao|getListPorCargayBloque|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, CargaFinanciero> mapaAlumnoCarga(String orden){
		HashMap<String, CargaFinanciero> map		= new HashMap<String,CargaFinanciero>();
		List<CargaFinanciero> list = new ArrayList<CargaFinanciero>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, STATUS, COMENTARIO, USUARIO"
					+ " FROM ENOC.CARGA_FINANCIERO "+orden;	
			list = enocJdbc.query(comando, new CargaFinancieroMapper());
			for (CargaFinanciero alumno : list ) {
				map.put(alumno.getCodigoPersonal(), alumno );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnoCarga|:"+ex);
		}
		
		return map;
	}

	public HashMap<String, CargaFinanciero> mapaFinancieroAlumnoCarga(String orden){
		HashMap<String, CargaFinanciero> map		= new HashMap<String,CargaFinanciero>();
		List<CargaFinanciero> list = new ArrayList<CargaFinanciero>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, STATUS, COMENTARIO, USUARIO"
					+ " FROM ENOC.CARGA_FINANCIERO "+orden;	
			list = enocJdbc.query(comando, new CargaFinancieroMapper());
			for (CargaFinanciero alumno : list ) {
				map.put(alumno.getCodigoPersonal()+alumno.getCargaId(), alumno );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnoCarga|:"+ex);
		}
		
		return map;
	}

	public HashMap<String, CargaFinanciero> mapaCargaFinancieroPorCarga(String cargaId, String orden){
		HashMap<String, CargaFinanciero> map		= new HashMap<String,CargaFinanciero>();
		List<CargaFinanciero> list = new ArrayList<CargaFinanciero>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, STATUS, COMENTARIO, USUARIO"
					+ " FROM ENOC.CARGA_FINANCIERO "
					+ " WHERE CARGA_ID = ? "
					+orden;	
			list = enocJdbc.query(comando, new CargaFinancieroMapper(), cargaId);
			for (CargaFinanciero alumno : list ) {
				map.put(alumno.getCodigoPersonal(), alumno );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaCargaFinancieroPorCarga|:"+ex);
		}
		
		return map;
	}
	
	public HashMap<String, String> mapaAlumnosEnCargaPagada(String cargaId, String bloques) {		
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();		
		try{
			String comando = "SELECT ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)) AS LLAVE, COUNT(CONFIRMAR) AS VALOR FROM ENOC.CARGA_ALUMNO"
				+ " WHERE CARGA_ID = ?"
				+ " AND BLOQUE_ID IN ("+bloques+")"
				+ " AND CONFIRMAR = 'S'"
				+ " AND PAGO = 'S'"
				+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CCOBRO WHERE CARGA_ID = ? AND INSCRITO = 'N')"
				+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ENOC.CARGA_ALUMNO.CODIGO_PERSONAL AND CARGA_ID = ?)"			
				+ " GROUP BY ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID))";			
			Object[] parametros = new Object[] {cargaId, cargaId, cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);			
			for (aca.Mapa map:lista){		
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAlumnoDao|mapaAlumnosEnCargaPagada|:"+ex);
		}
		
		return mapa;
	}
	
}