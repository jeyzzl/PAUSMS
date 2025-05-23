// Clase Util para la tabla de Carga
package aca.carga.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaGrupoHoraDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CargaGrupoHora hora ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CARGA_GRUPO_HORA(FOLIO, CURSO_CARGA_ID, SALON_ID, HORARIO_ID, DIA, PERIODO, BLOQUE_ID)"
					+ " VALUES( TO_NUMBER(?,'9999999'), ?, ?, TO_NUMBER(?,'9999999'), TO_NUMBER(?,'9'), TO_NUMBER(?,'99'), TO_NUMBER(?,'99'))";			
			Object[] parametros = new Object[] {hora.getFolio(), hora.getCursoCargaId(),hora.getSalonId(),hora.getHorarioId(),hora.getDia(),hora.getPeriodo(),hora.getBloqueId()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			} 			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|insertReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean updateReg(String cursoCargaId, String bloqueId ){
		
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CARGA_GRUPO_HORA SET BLOQUE_ID = TO_NUMBER(?,'99') WHERE CURSO_CARGA_ID = ?";			
			Object[] parametros = new Object[] {bloqueId, cursoCargaId};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHora|updateReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean deleteReg( String folio ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_GRUPO_HORA WHERE FOLIO = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.update(comando,parametros) == 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteRegCursoCargaId(String cursoCargaId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_GRUPO_HORA WHERE CURSO_CARGA_ID = ?";			
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.update(comando,parametros) == 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|deleteRegCursoCargaId|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteReg( String cursoCargaId, String horarioId, String dia, String periodo ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_GRUPO_HORA WHERE CURSO_CARGA_ID = ? AND HORARIO_ID = TO_NUMBER(?,'99') AND DIA = TO_NUMBER(?,'99') AND PERIODO = TO_NUMBER(?,'99')";	
			Object[] parametros = new Object[] {cursoCargaId,horarioId,dia,periodo};
			if (enocJdbc.update(comando,parametros) == 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteMateria( String cursoCargaId ){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_GRUPO_HORA WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|deleteMateria|:"+ex);
		}
		return ok;
	}
	
	public boolean updateSalon( String folio, String salonId ){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CARGA_GRUPO_HORA SET SALON_ID = ? WHERE FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {salonId, folio};
			if (enocJdbc.update(comando,parametros) == 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|updateSalon|:"+ex);
		}
		return ok;
	}
	
	public CargaGrupoHora mapeaRegId(  String folio ) {
		
		CargaGrupoHora hora = new CargaGrupoHora();		
		try{
			String comando = "SELECT FOLIO, CURSO_CARGA_ID, SALON_ID, HORARIO_ID, DIA, PERIODO, BLOQUE_ID FROM ENOC.CARGA_GRUPO_HORA FOLIO = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {folio};
			hora = enocJdbc.queryForObject(comando, new CargaGrupoHoraMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|mapeaRegId|:"+ex);
		}		
		return hora;
	}
	
	public boolean existeReg( String folio) {
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO_HORA WHERE FOLIO = TO_NUMBER(?,'9999999')";	
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean existeReg( String cursoCargaId, String horarioId, String dia, String periodo){
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO_HORA WHERE CURSO_CARGA_ID = ? AND HORARIO_ID = TO_NUMBER(?,'99') AND DIA = TO_NUMBER(?,'99') AND PERIODO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {cursoCargaId,horarioId,dia,periodo};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) == 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg( ) {
		int maximo = 0;		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) FROM ENOC.CARGA_GRUPO_HORA";
			maximo = enocJdbc.queryForObject(comando,Integer.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|maximoReg|:"+ex);
		}
		return String.valueOf(maximo);
	}
	
	public boolean existeSalon( String salonId) {
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO_HORA WHERE SALON_ID = ? ";			
			Object[] parametros = new Object[] {salonId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|existeSalon|:"+ex);
		}		
		return ok;
	}
	
	public boolean existeCargaGrupo( String cursoCargaId) {
		boolean 			ok 		= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO_HORA WHERE CURSO_CARGA_ID = ?";				
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|existeCargaGrupo|:"+ex);
		}
		return ok;
	}
	
	public String numPeriodosMateria( String cursoCargaId) {
		String horas				= "0";
		
		try{			
			String comando = "SELECT COALESCE(COUNT(CURSO_CARGA_ID),0) AS NUMHORAS FROM ENOC.CARGA_GRUPO_HORA WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			horas = enocJdbc.queryForObject(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|numPeriodosMateria|:"+ex);
		}		
		return horas;
	}
		
	public List<CargaGrupoHora> getListAll( String orden ) {
		List<CargaGrupoHora> lista	= new ArrayList<CargaGrupoHora>();		
		try{
			String comando = "SELECT FOLIO, CURSO_CARGA_ID, SALON_ID, HORARIO_ID, PERIODO, DIA, BLOQUE_ID FROM ENOC.CARGA_GRUPO_HORA "+ orden;			
			lista = enocJdbc.query(comando, new CargaGrupoHoraMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<CargaGrupoHora> lisHorariosActas( String actas, String orden ) {
		List<CargaGrupoHora> lista	= new ArrayList<CargaGrupoHora>();		
		try{
			String comando = "SELECT FOLIO, CURSO_CARGA_ID, SALON_ID, HORARIO_ID, PERIODO, DIA, BLOQUE_ID FROM ENOC.CARGA_GRUPO_HORA"
					+ " WHERE CURSO_CARGA_ID IN ("+actas+") "+ orden;			
			lista = enocJdbc.query(comando, new CargaGrupoHoraMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|lisHorariosActas|:"+ex);
		}
		return lista;
	}
	
	public List<CargaGrupoHora> lisHorariosMateria( String cursoCargaId, String orden ) {
		List<CargaGrupoHora> lista	= new ArrayList<CargaGrupoHora>();		
		try{
			String comando = "SELECT FOLIO, CURSO_CARGA_ID, SALON_ID, HORARIO_ID, PERIODO, DIA, BLOQUE_ID FROM ENOC.CARGA_GRUPO_HORA"
					+ " WHERE CURSO_CARGA_ID = ? "+ orden;
			Object[] parametros = new Object[] {cursoCargaId};
			lista = enocJdbc.query(comando, new CargaGrupoHoraMapper(), parametros);	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|lisHorariosMateria|:"+ex);
		}
		return lista;
	}
	
	public List<CargaGrupoHora> lisHorariosPorCargaBloqueySalon( String cargaId, String bloqueId, String salonId, String orden ) {
		List<CargaGrupoHora> lista	= new ArrayList<CargaGrupoHora>();		
		try{
			String comando = "SELECT FOLIO, CURSO_CARGA_ID, SALON_ID, HORARIO_ID, PERIODO, DIA, BLOQUE_ID FROM ENOC.CARGA_GRUPO_HORA"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?  AND BLOQUE_ID = TO_NUMBER(?,'99') AND SALON_ID = ? "+ orden;
			Object[] parametros = new Object[] {cargaId, bloqueId, salonId};
			lista = enocJdbc.query(comando, new CargaGrupoHoraMapper(), parametros);	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|lisHorariosPorCargaBloqueySalon|:"+ex);
		}
		return lista;
	}
	
	public List<CargaGrupoHora> lisHorariosPorCarga( String cargaId, String orden ) {
		List<CargaGrupoHora> lista	= new ArrayList<CargaGrupoHora>();		
		try{
			String comando = "SELECT FOLIO, CURSO_CARGA_ID, SALON_ID, HORARIO_ID, PERIODO, DIA, BLOQUE_ID FROM ENOC.CARGA_GRUPO_HORA"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?"+ orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CargaGrupoHoraMapper(), parametros);	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|lisHorariosPorCargaBloqueySalon|:"+ex);
		}
		return lista;
	}
	
	public List<CargaGrupoHora> horarioSalonFacultad( String cargaId, String horarioId, String salonId, String orden ) {
		List<CargaGrupoHora> lisHorario	= new ArrayList<CargaGrupoHora>();
		
		try{
			String comando = " SELECT COUNT(*) FROM ENOC.CARGA_GRUPO_HORA CGH"
					+ " WHERE (SELECT CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = CGH.CURSO_CARGA_ID)= ? AND SALON_ID = ?"
					+ " AND HORARIO_ID = ? "+ orden;			
			Object[] parametros = new Object[] {cargaId, salonId, horarioId};	
			lisHorario = enocJdbc.query(comando, new CargaGrupoHoraMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|horarioSalonFacultad|:"+ex);
		}
		return lisHorario;
	}
	
	public HashMap<String,String> mapaMateriasDelGrupo( String actas ){
		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String comando	             		= "";	
		
		try{			
			comando = "SELECT DIA||PERIODO AS LLAVE, CURSO_CARGA_ID AS VALOR FROM CARGA_GRUPO_HORA WHERE CURSO_CARGA_ID IN ("+actas+")";
			//Object[] parametros = new Object[] {cargaId};	
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|mapaMateriasDelGrupo|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaSalones( ){
		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String comando	             		= "";	
		
		try{			
			comando = "SELECT SALON_ID  AS LLAVE, COUNT(*) AS VALOR FROM CARGA_GRUPO_HORA GROUP BY SALON_ID";
			//Object[] parametros = new Object[] {cargaId};	
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|mapaSalones|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaHorasPorMateria( String cargaId ){
		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String comando	             		= "";	
		
		try{			
			comando = "SELECT CURSO_CARGA_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.CARGA_GRUPO_HORA WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? GROUP BY CURSO_CARGA_ID";
			Object[] parametros = new Object[] {cargaId};	
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|mapaHorasPorMateria|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaSalonesPorMateria( String cargaId ){		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{			
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR FROM ENOC.CARGA_GRUPO_HORA"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? AND SALON_ID != '0'"
					+ " GROUP BY CURSO_CARGA_ID";
			Object[] parametros = new Object[] {cargaId};	
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|mapaSalonesPorMateria|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaSalonesFaltan( String cargaId ){
		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try{			
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.CARGA_GRUPO_HORA WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? AND SALON_ID = '0' GROUP BY CURSO_CARGA_ID";
			Object[] parametros = new Object[] {cargaId};	
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|mapaSalonesFaltan|:"+ex);
		}
		
		return mapa;		
	}
	
	public HashMap<String,String> mapaSalonesOcupados( String cargaId, String bloqueId){
		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try{			
			String comando = "SELECT SALON_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.CARGA_GRUPO_HORA WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? AND BLOQUE_ID = TO_NUMBER(?,'99') GROUP BY SALON_ID";
			Object[] parametros = new Object[] {cargaId, bloqueId};	
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|mapaSalonesFaltan|:"+ex);
		}
		
		return mapa;		
	}
	
	public HashMap<String, CargaGrupoHora> mapaHorasPorSalon( String cargaId, String horarioId, String salonId ) {
		HashMap<String, CargaGrupoHora> map		= new HashMap<String, CargaGrupoHora>();
		List<CargaGrupoHora> lista	= new ArrayList<CargaGrupoHora>();
		String comando			= "";
		try{
			comando = " SELECT * FROM ENOC.CARGA_GRUPO_HORA CGH" +
					" WHERE (SELECT CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = CGH.CURSO_CARGA_ID)= ? AND HORARIO_ID = ?";			
			lista = enocJdbc.query(comando, new CargaGrupoHoraMapper(), cargaId, horarioId);
			for (CargaGrupoHora hora : lista){
				map.put(hora.getCursoCargaId(), hora);
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CargaGrupoHoraUtil|mapaHorasPorSalon|:"+ex);
		}
		return map;
	}	
	
	public List<String> horariosSalonFacultades( String cargaId, String horarioId, String salonId, String orden ) {
		List<String> lista	= new ArrayList<String>();				
		try{
			String comando = " SELECT DISTINCT(HORARIO_ID) AS HORARIO_ID FROM ENOC.CARGA_GRUPO_HORA CGH" +
					  " WHERE (SELECT CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = CGH.CURSO_CARGA_ID)= ? AND SALON_ID = ? "+ orden;	
			lista = enocJdbc.queryForList(comando, String.class, cargaId, salonId);					
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|horariosSalonFacultades|:"+ex);
		}		
		return lista;
	}
	
	public List<String> horariosChocan( String cargaId, String salonId ) {
		List<String> lista	= new ArrayList<String>();
		try{
			String comando = " SELECT " +
					  " CURSO_CARGA_ID||','||DIA||CHF.HORA_INICIO||CHF.MINUTO_INICIO||','||DIA||CHF.HORA_FINAL||CHF.MINUTO_FINAL" +
					  " FROM ENOC.CARGA_GRUPO_HORA CGH, ENOC.CAT_HORARIOFACULTAD CHF " +
					  " WHERE (SELECT CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = CGH.CURSO_CARGA_ID) = ? " +
					  " AND SALON_ID = ? " +
					  " AND CHF.HORARIO_ID = CGH.HORARIO_ID " +
					  " AND CHF.PERIODO = CGH.PERIODO ORDER BY INICIO, FINAL ";			
			lista = enocJdbc.queryForList(comando, String.class, cargaId, salonId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|horariosChocan|:"+ex);
		}
		return lista;
	}
	
	public List<String> horariosSalon( String cargaId, String salonId, String bloqueId ) {
		List<String> lista	= new ArrayList<String>();
		String comando		= "";
		
		try{
			comando = " SELECT " +
					  " CURSO_CARGA_ID||','||DIA||CHF.HORA_INICIO||CHF.MINUTO_INICIO||','||DIA||CHF.HORA_FINAL||CHF.MINUTO_FINAL" +
					  " FROM ENOC.CARGA_GRUPO_HORA CGH, ENOC.CAT_HORARIOFACULTAD CHF " +
					  " WHERE (SELECT CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = CGH.CURSO_CARGA_ID) = ? " +
					  " AND SALON_ID = ?" +
					  "	AND BLOQUE_ID = ?"+
					  " AND CHF.HORARIO_ID = CGH.HORARIO_ID " +
					  " AND CHF.PERIODO = CGH.PERIODO ORDER BY INICIO, FINAL "; 
			lista = enocJdbc.queryForList(comando, String.class, cargaId, salonId, bloqueId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|horariosSalon|:"+ex);
		}		
		return lista;
	}
	
	public List<String> MateriasChocanInscripcion( String cargaId, String cursoCargaId ) {
		List<String> lista	= new ArrayList<String>();
		String comando		= "";			
		try{
			comando = " SELECT " +
					  " CURSO_CARGA_ID||','||DIA||CHF.HORA_INICIO||CHF.MINUTO_INICIO||','||DIA||CHF.HORA_FINAL||CHF.MINUTO_FINAL" +
					  " FROM ENOC.CARGA_GRUPO_HORA CGH, ENOC.CAT_HORARIOFACULTAD CHF " +
					  " WHERE (SELECT CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = CGH.CURSO_CARGA_ID) = ? " +
					  " AND CGH.CURSO_CARGA_ID = ? " +
					  " AND CHF.HORARIO_ID = CGH.HORARIO_ID " +
					  " AND CHF.PERIODO = CGH.PERIODO ORDER BY INICIO, FINAL "; 
			lista = enocJdbc.queryForList(comando, String.class, cargaId, cursoCargaId);				
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|horariosChocan|:"+ex);
		}
		
		return lista;
	}
	
	public List<String> salonesDelGrupo( String cursoCargaId, String orden ){
		List<String> lista	= new ArrayList<String>();		
		try{
			String comando = " SELECT DISTINCT(SALON_ID) AS SALON FROM ENOC.CARGA_GRUPO_HORA WHERE CURSO_CARGA_ID = ? "+ orden;
			lista = enocJdbc.queryForList(comando, String.class, cursoCargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|salonesDelGrupo|:"+ex);
		}
		return lista;
	}
	
	public String getInicioFinal( String horarioId, String periodo ) {
		String nombre			= "1";		
		try{
			String comando =" SELECT HORA_INICIO||MINUTO_INICIO AS INICIO, HORA_FINAL||MINUTO_FINAL AS FINAL " +
							" FROM ENOC.CAT_HORARIOFACULTAD WHERE PERIODO = ? AND HORARIO_ID = ? "; 
			Object[] parametros = new Object[] {horarioId,periodo};
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|getInicioFinal|:"+ex);
		}
		return nombre;
	}
	
	public String getHorarioPrincipal( String codigoPersonal, String cargaId ){
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String horario	= "0";
		int maximo		= 0;		
		try{
			String comando = " SELECT HORARIO_ID AS LLAVE, COALESCE(COUNT(HORARIO_ID),0) AS VALOR FROM ENOC.CARGA_GRUPO_HORA"+ 
					" WHERE CURSO_CARGA_ID IN"+
					" (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT " +
					" WHERE CODIGO_PERSONAL = ? " +
					" AND CARGA_DEL_GRUPO(CURSO_CARGA_ID)= ?" +
					" AND TIPOCAL_ID IN ('M','I'))" +
					" GROUP BY HORARIO_ID"; 
			Object[] parametros = new Object[] {codigoPersonal, cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);			
			for (aca.Mapa map: lista){	
				if (Integer.parseInt(map.getValor()) > maximo){
					horario 	= map.getLlave();
					maximo 		= Integer.parseInt(map.getValor());
				}
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|getHorarioPrincipal|:"+ex);
		}	
		return horario;
	}
	
	public List<String> horariosAlumno( String codigoPersonal, String cargaId ) {
		List<String> lista	= new ArrayList<String>();
		String comando		= "";		
		try{
			comando = " SELECT DISTINCT(HORARIO_ID) FROM ENOC.CARGA_GRUPO_HORA"+ 
					" WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT WHERE CODIGO_PERSONAL = ? AND CARGA_DEL_GRUPO(CURSO_CARGA_ID) = ?)";
			Object[] parametros = new Object[] {codigoPersonal, cargaId};
			lista = enocJdbc.queryForList(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|horariosAlumno|:"+ex);
		}		
		return lista;
	}
	
	public String getHorarioPrincipalMaestro( String codigoPersonal, String cargaId ) {
		String horario 	= "0";
		int mayor		= 0;
		
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT HORARIO_ID AS LLAVE, COALESCE(COUNT(HORARIO_ID),0) AS VALOR FROM ENOC.CARGA_GRUPO_HORA"
					+ " WHERE CURSO_CARGA_ID IN"
					+ " (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?)"
					+ " GROUP BY HORARIO_ID";			
			Object[] parametros = new Object[] {codigoPersonal,cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);			
			for (aca.Mapa map : lista) {
				if (Integer.parseInt(map.getValor()) >= mayor) {
					mayor = Integer.parseInt(map.getValor());
					horario = map.getLlave();
				}
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|getHorarioPrincipalMaestro|:"+ex);
		}		
		return horario;
	}	
	
	public HashMap<String,String> mapaHorasEnSalon( String cargaId, String bloqueId, String planId){
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{			
			String comando = "SELECT SALON_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.CARGA_GRUPO_HORA"
					+ "	WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_ACADEMICA WHERE CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99') AND PLAN_ID = ?)"
					+ "	GROUP BY SALON_ID";
			Object[] parametros = new Object[] {cargaId, bloqueId, planId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|mapaSalonesFaltan|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaHorasEnEdificio( String cargaId, String bloqueId, String planId){
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{			
			String comando = "SELECT EDIFICIO_SALON(SALON_ID) AS LLAVE, COUNT(*) AS VALOR FROM ENOC.CARGA_GRUPO_HORA"
					+ "	WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_ACADEMICA WHERE CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99') AND PLAN_ID = ?)"
					+ "	GROUP BY EDIFICIO_SALON(SALON_ID)";
			Object[] parametros = new Object[] {cargaId, bloqueId, planId};	
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoHoraDao|mapaSalonesFaltan|:"+ex);
		}
		
		return mapa;
	}	
}