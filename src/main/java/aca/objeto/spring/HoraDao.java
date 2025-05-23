package aca.objeto.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class HoraDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public Hora mapeaReg(String codigoPersonal, String cargaId, String bloqueId) {
		
		Hora objeto = new Hora();
		
		try{
			String comando =	"SELECT CGH.CURSO_CARGA_ID, CGH.SALON_ID, CGH.DIA, CGH.HORARIO_ID, CGH.PERIODO, DIA||CHF.HORA_INICIO||CHF.MINUTO_INICIO AS INICIO, DIA||CHF.HORA_FINAL||CHF.MINUTO_FINAL AS FIN"
					+ " FROM ENOC.CARGA_GRUPO_HORA CGH, ENOC.CAT_HORARIOFACULTAD CHF"
					+ " WHERE CURSO_CARGA_ID IN"
					+ " (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_DEL_GRUPO(CURSO_CARGA_ID) = ?"
					+ " AND GRUPO_BLOQUE(CURSO_CARGA_ID) = ?)"
					+ " AND CHF.HORARIO_ID = CGH.HORARIO_ID"
					+ " AND CHF.PERIODO = CGH.PERIODO";
			Object[] parametros = new Object[] {codigoPersonal, cargaId, bloqueId};			
			objeto = enocJdbc.queryForObject(comando, new HoraMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.spring.HoraDao|mapeaReg|:"+ex);
		
		}
		return objeto;
	}
	
	public List<Hora> listaHorasDelAlumno(String codigoPersonal, String cargaId, String tipoCal, String bloqueId ) {		
		List<Hora> lista = new ArrayList<Hora>();
		
		try{
			String comando =	"SELECT"
					+ " CGH.CURSO_CARGA_ID, CGH.SALON_ID, CGH.DIA, CGH.HORARIO_ID, CGH.PERIODO, DIA||CHF.HORA_INICIO||CHF.MINUTO_INICIO AS INICIO, DIA||CHF.HORA_FINAL||CHF.MINUTO_FINAL AS FIN"
					+ " FROM ENOC.CARGA_GRUPO_HORA CGH, ENOC.CAT_HORARIOFACULTAD CHF"
					+ " WHERE CURSO_CARGA_ID IN"
					+ " (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CODIGO_PERSONAL = ? "
					+ " AND CARGA_DEL_GRUPO(CURSO_CARGA_ID) = ? AND TIPOCAL_ID IN (?)"
					+ " AND GRUPO_BLOQUE(CURSO_CARGA_ID) = ?)"
					+ " AND CHF.HORARIO_ID = CGH.HORARIO_ID"
					+ " AND CHF.PERIODO = CGH.PERIODO";
			
			Object[] parametros = new Object[] {codigoPersonal,cargaId,tipoCal,bloqueId};
			lista = enocJdbc.query(comando, new HoraMapper(), parametros);		
			
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.spring.Hora|listaHorasDelAlumno|:"+ex);
		}
		
		return lista;
	}
	
	public List<Hora> listaHorasDelAlumno(String codigoPersonal, String cargaId, String tipoCal, String bloqueId, String horarioId ) {		
		List<Hora> lista = new ArrayList<Hora>();
		
		try{
			String comando = "SELECT"
					+ " CGH.CURSO_CARGA_ID, CGH.SALON_ID, CGH.DIA, CGH.HORARIO_ID, CGH.PERIODO, DIA||CHF.HORA_INICIO||CHF.MINUTO_INICIO AS INICIO, DIA||CHF.HORA_FINAL||CHF.MINUTO_FINAL AS FIN"
					+ " FROM ENOC.CARGA_GRUPO_HORA CGH, ENOC.CAT_HORARIOFACULTAD CHF"
					+ " WHERE CGH.HORARIO_ID != ?"
					+ " AND CURSO_CARGA_ID IN"
					+ " (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_DEL_GRUPO(CURSO_CARGA_ID) = ? AND TIPOCAL_ID IN (?)"
					+ " AND GRUPO_BLOQUE(CURSO_CARGA_ID) = ?)"
					+ " AND CHF.HORARIO_ID = CGH.HORARIO_ID"
					+ " AND CHF.PERIODO = CGH.PERIODO";			
			Object[] parametros = new Object[] {horarioId,codigoPersonal,cargaId,tipoCal,bloqueId};
			lista = enocJdbc.query(comando, new HoraMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.spring.Hora|listaHorasDelAlumno|:"+ex);
		}
		
		return lista;
	}
	
	public List<Hora> listaHorasDelAlumno(String codigoPersonal, String cargaId, String bloqueId) {
		List<Hora> lista = new ArrayList<Hora>();		
		try{
			String comando = "SELECT"
					+ " CGH.CURSO_CARGA_ID, CGH.SALON_ID, CGH.DIA, CGH.HORARIO_ID, CGH.PERIODO, DIA||CHF.HORA_INICIO||CHF.MINUTO_INICIO AS INICIO, DIA||CHF.HORA_FINAL||CHF.MINUTO_FINAL AS FIN"
					+ " FROM ENOC.CARGA_GRUPO_HORA CGH, ENOC.CAT_HORARIOFACULTAD CHF"
					+ " WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_DEL_GRUPO(CURSO_CARGA_ID) = ?"
					+ " AND GRUPO_BLOQUE(CURSO_CARGA_ID) = ?)"
					+ " AND CHF.HORARIO_ID = CGH.HORARIO_ID"
					+ " AND CHF.PERIODO = CGH.PERIODO";			
			Object[] parametros = new Object[] {codigoPersonal,cargaId,bloqueId};
			lista = enocJdbc.query(comando, new HoraMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.spring.Hora|listaHorasDelAlumno|:"+ex);
		}
		
		return lista;
	}
	
	public List<Hora> listaHorasPorCarga(String cargaId, String carreraId) {
		List<Hora> lista = new ArrayList<Hora>();		
		try{
			String comando = "SELECT"
					+ " CGH.CURSO_CARGA_ID, CGH.SALON_ID, CGH.DIA, CGH.HORARIO_ID, CGH.PERIODO, DIA||CHF.HORA_INICIO||CHF.MINUTO_INICIO AS INICIO, DIA||CHF.HORA_FINAL||CHF.MINUTO_FINAL AS FIN"
					+ " FROM ENOC.CARGA_GRUPO_HORA CGH, ENOC.CAT_HORARIOFACULTAD CHF"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?"
					+ " AND CURSO_CARGA_ID IN(SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? AND CARRERA_ID = ?)"
					+ " AND CHF.HORARIO_ID = CGH.HORARIO_ID"
					+ " AND CHF.PERIODO = CGH.PERIODO";			
			Object[] parametros = new Object[] {cargaId,cargaId,carreraId};
			lista = enocJdbc.query(comando, new HoraMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.spring.Hora|listaHorasDelAlumno|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, Hora> mapaHorasDelAlumno(String codigoPersonal, String cargaId, String tipoCal, String bloqueId, String horarioId) {
		HashMap<String, Hora> mapa 	= new HashMap<String, Hora>();
		List<Hora> lista 			= new ArrayList<Hora>();
	
		try{			
			String comando = "SELECT"
					+ " CGH.CURSO_CARGA_ID, CGH.SALON_ID, CGH.DIA, CGH.HORARIO_ID, CGH.PERIODO, DIA||CHF.HORA_INICIO||CHF.MINUTO_INICIO AS INICIO, DIA||CHF.HORA_FINAL||CHF.MINUTO_FINAL AS FIN"
					+ " FROM ENOC.CARGA_GRUPO_HORA CGH, ENOC.CAT_HORARIOFACULTAD CHF"
					+ " WHERE CGH.HORARIO_ID = ?"
					+ " AND CGH.CURSO_CARGA_ID IN"
					+ " (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_DEL_GRUPO(CURSO_CARGA_ID) = ? AND TIPOCAL_ID IN ("+tipoCal+")"
					+ " AND GRUPO_BLOQUE(CURSO_CARGA_ID) = ?)"
					+ " AND CHF.HORARIO_ID = CGH.HORARIO_ID"
					+ " AND CHF.PERIODO = CGH.PERIODO";			
			lista = enocJdbc.query(comando, new HoraMapper(), horarioId, codigoPersonal, cargaId, bloqueId);
			for(Hora hora : lista) {
				mapa.put(hora.getDia()+hora.getPeriodo(), hora);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.spring.Hora|mapaHorasDelAlumno|:"+ex);
			ex.printStackTrace();
		}
		return mapa;
	}
	
	public HashMap<String, Hora> mapaHorasAdicionalesDelAlumno(String codigoPersonal, String cargaId, String bloqueId, String horarioId) {
		HashMap<String, Hora> mapa 	= new HashMap<String, Hora>();
		List<Hora> lista 			= new ArrayList<Hora>();	
		try{			
			String comando = "SELECT"
					+ " CGH.CURSO_CARGA_ID, CGH.SALON_ID, CGH.DIA, CGH.HORARIO_ID, CGH.PERIODO, DIA||CHF.HORA_INICIO||CHF.MINUTO_INICIO AS INICIO, DIA||CHF.HORA_FINAL||CHF.MINUTO_FINAL AS FIN"
					+ " FROM ENOC.CARGA_GRUPO_HORA CGH, ENOC.CAT_HORARIOFACULTAD CHF"
					+ " WHERE CGH.HORARIO_ID != "+horarioId
					+ " AND CGH.CURSO_CARGA_ID IN"
					+ " (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_DEL_GRUPO(CURSO_CARGA_ID) = ?"
					+ " AND GRUPO_BLOQUE(CURSO_CARGA_ID) = "+bloqueId+")"
					+ " AND CHF.HORARIO_ID = CGH.HORARIO_ID"
					+ " AND CHF.PERIODO = CGH.PERIODO";			
			lista = enocJdbc.query(comando, new HoraMapper(), codigoPersonal, cargaId);
			for(Hora hora : lista) {
				mapa.put(hora.getDia()+hora.getPeriodo(), hora);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.spring.Hora|mapaHorasDelAlumno|:"+ex);
			ex.printStackTrace();
		}
		return mapa;
	}
	
	public List<Hora> getListaAlumnoHorario(String codigoPersonal, String cargaId, String bloqueId, String tipoCal, String horarioId ) {
		List<Hora> lista = new ArrayList<Hora>();
		
		try{
			String comando = "SELECT"
					+ " CGH.CURSO_CARGA_ID, CGH.SALON_ID, CGH.DIA, CGH.HORARIO_ID, CGH.PERIODO, DIA||CHF.HORA_INICIO||CHF.MINUTO_INICIO AS INICIO, DIA||CHF.HORA_FINAL||CHF.MINUTO_FINAL AS FIN"
					+ " FROM ENOC.CARGA_GRUPO_HORA CGH, ENOC.CAT_HORARIOFACULTAD CHF"
					+ " WHERE CGH.HORARIO_ID = ?"
					+ " AND CURSO_CARGA_ID IN"
					+ " (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_DEL_GRUPO(CURSO_CARGA_ID) = ? AND TIPOCAL_ID IN ("+tipoCal+")"
					+ " AND GRUPO_BLOQUE(CURSO_CARGA_ID) = ?)"
					+ " AND CHF.HORARIO_ID = CGH.HORARIO_ID"
					+ " AND CHF.PERIODO = CGH.PERIODO";			
			Object[] parametros = new Object[] {horarioId, codigoPersonal,cargaId,bloqueId};
			lista = enocJdbc.query(comando, new HoraMapper(), parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.spring.Hora|getListaAlumnoHorario|:"+ex);
		}
		
		return lista;
	}
	
	public List<Hora> getListaHorasDeMateria(String cursoCargaId ) {
		List<Hora> lista = new ArrayList<Hora>();
		
		try{
			String comando =	"SELECT"
					+ " CGH.CURSO_CARGA_ID, CGH.SALON_ID, CGH.DIA, CGH.HORARIO_ID, CGH.PERIODO, DIA||CHF.HORA_INICIO||CHF.MINUTO_INICIO AS INICIO, DIA||CHF.HORA_FINAL||CHF.MINUTO_FINAL AS FIN"
					+ " FROM ENOC.CARGA_GRUPO_HORA CGH, ENOC.CAT_HORARIOFACULTAD CHF"
					+ " WHERE CURSO_CARGA_ID = ?"
					+ " AND CHF.HORARIO_ID = CGH.HORARIO_ID"
					+ " AND CHF.PERIODO = CGH.PERIODO";

			Object[] parametros = new Object[] {cursoCargaId};
			lista = enocJdbc.query(comando, new HoraMapper(), parametros);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.spring.Hora|getListaHorasDeMateria|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, String> mapaCursosDelAlumnos(String codigoPersonal, String cargaId, String tipo) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();	
		try{			
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, CURSO_NOMBRE(CURSO_ID) AS VALOR FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_DEL_GRUPO(CURSO_CARGA_ID) = ?"
					+ " AND TIPOCAL_ID IN ("+tipo+")";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), codigoPersonal, cargaId);
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.spring.Hora|mapaCursosDelAlumnos|:"+ex);
			ex.printStackTrace();
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaCursosDelMaestro(String codigoPersonal, String cargaId){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();	
		try{			
			String comando = " SELECT CURSO_CARGA_ID AS LLAVE, CURSO_NOMBRE(CURSO_ID) AS VALOR FROM ENOC.CARGA_GRUPO_CURSO"
					+ " WHERE ORIGEN = 'O'"
					+ " AND CURSO_CARGA_ID IN"
					+ " (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?)";			
			Object[] parametros = new Object[] {codigoPersonal,cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);			
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.spring.Hora|mapaCursosDelMaestro|:"+ex);
			ex.printStackTrace();
		}
		return mapa;
	}
	
	public HashMap<String, List<Hora>> mapaHorasDelMaestroPortalMaestro(String codigoPersonal, String cargaId, String bloqueId) {
		HashMap<String, List<Hora>> mapa = new HashMap<String, List<Hora>>();
		List<Hora> lista = new ArrayList<Hora>();
		try{			
			String comando = "SELECT"
					+ " CGH.CURSO_CARGA_ID, CGH.SALON_ID, CGH.DIA, CGH.HORARIO_ID, CGH.PERIODO, DIA||CHF.HORA_INICIO||CHF.MINUTO_INICIO AS INICIO, DIA||CHF.HORA_FINAL||CHF.MINUTO_FINAL AS FIN"
					+ " FROM ENOC.CARGA_GRUPO_HORA CGH, ENOC.CAT_HORARIOFACULTAD CHF"
					+ " WHERE CGH.CURSO_CARGA_ID IN"
					+ " (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = ?)"
					+ " AND CHF.HORARIO_ID = CGH.HORARIO_ID"
					+ " AND CHF.PERIODO = CGH.PERIODO";
			
			Object[] parametros = new Object[] {codigoPersonal,cargaId,bloqueId};
			lista = enocJdbc.query(comando, new HoraMapper(), parametros);
			
			for (Hora hora : lista) {
				List<Hora> tmp = new ArrayList<Hora>();
				if(!mapa.containsKey(hora.getHorarioId()+hora.getDia()+hora.getPeriodo())) {
					tmp.add(hora);
					mapa.put(hora.getHorarioId()+hora.getDia()+hora.getPeriodo(), tmp);
				}else {
					tmp = mapa.get(hora.getHorarioId()+hora.getDia()+hora.getPeriodo());
					tmp.add(hora);
					mapa.put(hora.getHorarioId()+hora.getDia()+hora.getPeriodo(), tmp);
				}
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.spring.Hora|mapaHorasDelMaestroPortalMaestro|:"+ex);
			ex.printStackTrace();
		}
		
		return mapa;
	}
	public HashMap<String, Hora> mapaHorasDelMaestro(String codigoPersonal, String cargaId, String bloqueId) {
		HashMap<String, Hora> mapa = new HashMap<String, Hora>();
		List<Hora> lista = new ArrayList<Hora>();
	
		try{			
			String comando = "SELECT"
					+ " CGH.CURSO_CARGA_ID, CGH.SALON_ID, CGH.DIA, CGH.HORARIO_ID, CGH.PERIODO, DIA||CHF.HORA_INICIO||CHF.MINUTO_INICIO AS INICIO, DIA||CHF.HORA_FINAL||CHF.MINUTO_FINAL AS FIN"
					+ " FROM ENOC.CARGA_GRUPO_HORA CGH, ENOC.CAT_HORARIOFACULTAD CHF"
					+ " WHERE CGH.CURSO_CARGA_ID IN"
					+ " (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = ?)"
					+ " AND CHF.HORARIO_ID = CGH.HORARIO_ID"
					+ " AND CHF.PERIODO = CGH.PERIODO";
			
			Object[] parametros = new Object[] {codigoPersonal,cargaId,bloqueId};
			lista = enocJdbc.query(comando, new HoraMapper(), parametros);
			
			for (Hora hora : lista) {
				mapa.put(hora.getHorarioId()+hora.getDia()+hora.getPeriodo(), hora);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.spring.Hora|mapaHorasDelMaestro|:"+ex);
			ex.printStackTrace();
		}
		
		return mapa;
	}
	
	public List<String> listaHorariosDelMaestro(String codigoPersonal, String cargaId, String bloqueId ) {
		List<String> lista = new ArrayList<String>();		
		try{
			String comando = "SELECT"
					+ " DISTINCT(HORARIO_ID) AS HORARIO"
					+ " FROM ENOC.CARGA_GRUPO_HORA"
					+ " WHERE CURSO_CARGA_ID IN"
					+ " (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = ?)";			
			Object[] parametros = new Object[] {codigoPersonal,cargaId,bloqueId};
			lista = enocJdbc.queryForList(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.spring.Hora|listaHorariosDelMaestro|:"+ex);
		}
		
		return lista;
	}
	
	public boolean maestroHorarioOcupado(String codigoPersonal, String cargaId, String bloqueId, String dia, String periodoId) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*)"
					+ " FROM ENOC.CARGA_GRUPO_HORA CGH, ENOC.CAT_HORARIOFACULTAD CHF"
					+ " WHERE CGH.CURSO_CARGA_ID IN"
					+ " (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = ?)"
					+ " AND CHF.HORARIO_ID = CGH.HORARIO_ID"
					+ " AND CHF.PERIODO = CGH.PERIODO AND DIA = ? AND CGH.PERIODO = ?";			
			Object[] parametros = new Object[] {codigoPersonal,cargaId,bloqueId,dia,periodoId};			
			if(enocJdbc.queryForObject(comando, Integer.class, parametros) > 1) {
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.spring.Hora|maestroHorarioOcupado|:"+ex);
		}		
		return ok;
	}
}
