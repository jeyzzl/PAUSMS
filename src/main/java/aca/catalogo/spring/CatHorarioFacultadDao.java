package aca.catalogo.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatHorarioFacultadDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(CatHorarioFacultad horario){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.CAT_HORARIOFACULTAD "+ 
				"(HORARIO_ID, PERIODO, TURNO, HORA_INICIO, HORA_FINAL, MINUTO_INICIO, MINUTO_FINAL, NOMBRE, TIPO) "+
				"VALUES( TO_NUMBER(?,'9999999'), TO_NUMBER(?,'99'), ?, ?, ?,?,?,?,?)";
			Object[] parametros = new Object[] {horario.getHorarioId(), horario.getPeriodo(), horario.getTurno(), horario.getHoraInicio(), horario.getHoraFinal(),
					horario.getMinutoInicio(), horario.getMinutoFinal(), horario.getNombre(), horario.getTipo()};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatHorarioFacultadDao|insertReg|:"+ex);			
		}		
		return ok;
	}
	
	public boolean updateReg(CatHorarioFacultad horario ){
		boolean ok = false;		
		try{			
			String comando = "UPDATE ENOC.CAT_HORARIOFACULTAD"+ 
				" SET TURNO = ?, HORA_INICIO = ?," +
				" HORA_FINAL = ?," +
				" MINUTO_INICIO = ?,"+
				" MINUTO_FINAL = ?,"+				
				" NOMBRE = ?,"+
				" TIPO = ?"+
				" WHERE HORARIO_ID = TO_NUMBER(?,'999999999') AND PERIODO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {horario.getTurno(), horario.getHoraInicio(), horario.getHoraFinal(), horario.getMinutoInicio(), horario.getMinutoFinal(),
					horario.getNombre(), horario.getTipo(), horario.getHorarioId(), horario.getPeriodo()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatHorarioFacultadDao|updateReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean deleteReg(String horarioId, String periodo){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CAT_HORARIOFACULTAD WHERE HORARIO_ID = TO_NUMBER(?,'9999999') AND PERIODO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {horarioId, periodo};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatHorarioFacultadDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public CatHorarioFacultad mapeaRegId( String periodo, String horarioId){
		CatHorarioFacultad catHorarioFacultad = new CatHorarioFacultad(); 
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_HORARIOFACULTAD WHERE PERIODO = TO_NUMBER(?,'99') AND HORARIO_ID = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {horarioId, periodo};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT * FROM ENOC.CAT_HORARIOFACULTAD WHERE PERIODO = TO_NUMBER(?,'99') AND HORARIO_ID = TO_NUMBER(?,'9999999')";
				catHorarioFacultad = enocJdbc.queryForObject(comando, new CatHorarioFacultadMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatHorarioFacultadDao|mapeaRegId|:"+ex);			
		}
		return catHorarioFacultad;
	}
	
	public boolean existeReg( String horarioId, String periodo) {
		boolean 		ok 	= false;
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_HORARIOFACULTAD WHERE HORARIO_ID = TO_NUMBER(?,'9999999') AND PERIODO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {horarioId, periodo};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatHorarioFacultadDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public List<CatHorarioFacultad> getLista( String horarioId, String orden ) {
		
		List<CatHorarioFacultad> lista		= new ArrayList<CatHorarioFacultad>();		
		try{
			String comando = "SELECT PERIODO, TURNO, HORA_INICIO, HORA_FINAL, MINUTO_INICIO, MINUTO_FINAL, HORARIO_ID, NOMBRE, TIPO "
					+ " FROM ENOC.CAT_HORARIOFACULTAD "
					+ " WHERE HORARIO_ID = ? "+ orden;
			lista = enocJdbc.query(comando, new CatHorarioFacultadMapper(), horarioId);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacultadDao|getLista|:"+ex);
		}		
		return lista;
	}
	
	public List<CatHorarioFacultad> getListaTurno( String horarioId, String turno, String orden) {
		
		List<CatHorarioFacultad> lista		= new ArrayList<CatHorarioFacultad>();		
		try{
			String comando = "SELECT PERIODO, TURNO, HORA_INICIO, HORA_FINAL, MINUTO_INICIO, MINUTO_FINAL, HORARIO_ID, NOMBRE, TIPO "
					+ " FROM ENOC.CAT_HORARIOFACULTAD"
					+ " WHERE HORARIO_ID = ? AND TURNO = ? "+ orden;
			Object[] parametros = new Object[] {horarioId, turno};
			lista = enocJdbc.query(comando, new CatHorarioFacultadMapper(), parametros);	
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacultadDao|getListaTurno|:"+ex);
		}		
		return lista;
	}
	
	public List<String> getTurno( String horarioId, String orden) {
		
		List<String> lista		= new ArrayList<String>();		
		try{
			String comando = "SELECT DISTINCT(TURNO) AS TURNO FROM ENOC.CAT_HORARIOFACULTAD WHERE HORARIO_ID = ? "+ orden;
			Object[] parametros = new Object[] {horarioId};
			lista = enocJdbc.queryForList(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacultadDao|getTurno|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<Integer, CatHorarioFacultad> getListaTurnoHashMap( String horarioId, String turno, String orden) {
		
		HashMap<Integer, CatHorarioFacultad> mapa		= new HashMap<Integer,CatHorarioFacultad>();
		List<CatHorarioFacultad> lista		= new ArrayList<CatHorarioFacultad>();
		
		try{
			String comando = "SELECT PERIODO, TURNO, HORA_INICIO, HORA_FINAL, MINUTO_INICIO, MINUTO_FINAL, HORARIO_ID, NOMBRE, TIPO FROM ENOC.CAT_HORARIOFACULTAD "+ 
				"WHERE HORARIO_ID = ? AND TURNO = ? "+ orden;
			Object[] parametros = new Object[] {horarioId, turno};
			lista = enocJdbc.query(comando, new CatHorarioFacultadMapper(), parametros);
			for (CatHorarioFacultad horario : lista) {
				mapa.put(Integer.parseInt(horario.getPeriodo()), horario);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacultadDao|getListaTurnoHashMap|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<Integer, CatHorarioFacultad> getListaAllHashMap( String horarioId, String orden) {
		
		HashMap<Integer, CatHorarioFacultad> mapa		= new HashMap<Integer, CatHorarioFacultad>();
		List<CatHorarioFacultad> lista		= new ArrayList<CatHorarioFacultad>();
		
		try{
			String comando = "SELECT * FROM ENOC.CAT_HORARIOFACULTAD WHERE HORARIO_ID = ? "+ orden;
			Object[] parametros = new Object[] {horarioId};
			lista = enocJdbc.query(comando, new CatHorarioFacultadMapper(), parametros);
			for (CatHorarioFacultad horario : lista) {
				mapa.put(Integer.parseInt(horario.getPeriodo()), horario);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacultadDao|getListaAllHashMap|:"+ex);
		}
		
		return mapa;
	}
	
	public List<CatHorarioFacultad> getListAll( String orden ) {
		
		List<CatHorarioFacultad> lista		= new ArrayList<CatHorarioFacultad>();
		
		try{
			String comando = "SELECT PERIODO, TURNO, HORA_INICIO, HORA_FINAL, MINUTO_INICIO, MINUTO_FINAL, HORARIO_ID, NOMBRE, TIPO FROM ENOC.CAT_HORARIOFACULTAD "+ orden;
			lista = enocJdbc.query(comando, new CatHorarioFacultadMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacultadDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatHorarioFacultad> getPeriodos( String horarioId, String orden ) {
		
		List<CatHorarioFacultad> lista		= new ArrayList<CatHorarioFacultad>();
		
		try{
			String comando = "SELECT * FROM ENOC.CAT_HORARIOFACULTAD WHERE HORARIO_ID = ? "+ orden;
			Object[] parametros = new Object[] {horarioId};
			lista = enocJdbc.query(comando, new CatHorarioFacultadMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacultadDao|getPeriodos|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,CatHorarioFacultad> getMapAll( String orden ) {
		
		HashMap<String,CatHorarioFacultad> mapa = new HashMap<String,CatHorarioFacultad>();
		List<CatHorarioFacultad> lista		= new ArrayList<CatHorarioFacultad>();
		
		try{
			String comando  = "SELECT HORARIO_ID, PERIODO, TURNO, HORA_INICIO, HORA_FINAL, MINUTO_INICIO, MINUTO_FINAL, NOMBRE, TIPO FROM ENOC.CAT_HORARIOFACULTAD "+ orden;
			lista = enocJdbc.query(comando, new CatHorarioFacultadMapper());
			for (CatHorarioFacultad horario : lista) {
				mapa.put(horario.getHorarioId()+horario.getPeriodo(), horario);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacultadDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}

	public HashMap<String,CatHorarioFacultad> getMapHoras( String orden ) {
		
		HashMap<String,CatHorarioFacultad> mapa = new HashMap<String,CatHorarioFacultad>();
		List<CatHorarioFacultad> lista		= new ArrayList<CatHorarioFacultad>();
		
		try{
			String comando = "SELECT HORARIO_ID, PERIODO, TURNO, HORA_INICIO, HORA_FINAL, MINUTO_INICIO, MINUTO_FINAL, NOMBRE, TIPO" +
					" FROM ENOC.CAT_HORARIOFACULTAD " +orden;
			lista = enocJdbc.query(comando, new CatHorarioFacultadMapper());
			for (CatHorarioFacultad horario : lista) {
				mapa.put(horario.getHorarioId()+horario.getPeriodo(), horario);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacultadDao|getMapHoras|:"+ex);
		}
		
		return mapa;
	}	
	
	public HashMap<String,String> mapaHorarioConPeriodos( ) {		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT HORARIO_ID AS LLAVE, COUNT(PERIODO) AS VALOR FROM ENOC.CAT_HORARIOFACULTAD GROUP BY HORARIO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacultadDao|mapaHorarioConPeriodos|:"+ex);
		}		
		return mapa;
	}
	
}
