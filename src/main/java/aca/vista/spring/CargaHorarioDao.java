package aca.vista.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.carga.spring.CargaGrupoHora;
import aca.carga.spring.CargaGrupoHoraMapper;

@Component
public class CargaHorarioDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public CargaHorario mapeaRegId(String folio) {
		CargaHorario objeto = new CargaHorario();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, SALON_ID,"
					+ " TO_NUMBER(DIA,'9') AS DIA,"
					+ " HORA_INICIO, MINUTO_INICIO,"
					+ " HORA_FINAL, MINUTO_FINAL"
					+ " TO_NUMBER(HORARIO_ID,'9999999') AS HORARIO_ID,"
					+ " TO_NUMBER(PERIODO,'99') AS PERIODO,"
					+ " TO_NUMBER(BLOQUE_ID,'99') AS BLOQUE_ID,"
					+ " FOLIO"
					+ " FROM ENOC.CARGA_HORARIO"
					+ " WHERE FOLIO = ?";
			
			Object[] parametros = new Object[] {folio};
			objeto = enocJdbc.queryForObject(comando, new CargaHorarioMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaHorarioDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg(String folio) {
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_HORARIO WHERE FOLIO = ?";
			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaHorarioDao|existeReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean estaOcupado(String cursoCargaId, String salonId, String dia, String bloqueId, String inicia, String termina) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM CARGA_HORARIO WHERE CURSO_CARGA_ID = ? AND SALON_ID = ? AND DIA = ? AND BLOQUE_ID = ? AND HORA_INICIO||MINUTO_INICIO >= ? AND HORA_FINAL||MINUTO_FINAL <= ?";
			
			Object[] parametros = new Object[] {cursoCargaId,salonId,dia,bloqueId,inicia,termina};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaHorarioDao|estaOcupado|:"+ex);
		}
		return ok;
	}	
		
	public List<CargaHorario> getLista(String cursoCargaId, String orden) {
		List<CargaHorario> lista		= new ArrayList<CargaHorario>();
		String comando		= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, SALON_ID, DIA,"
					+ " HORA_INICIO, MINUTO_INICIO,"
					+ " HORA_FINAL, MINUTO_FINAL,"
					+ " HORARIO_ID, PERIODO, BLOQUE_ID, FOLIO"
					+ " FROM ENOC.CARGA_HORARIO"
					+ " WHERE CURSO_CARGA_ID = ? "+orden; 
			
			Object[] parametros = new Object[] {cursoCargaId};
			lista = enocJdbc.query(comando, new CargaHorarioMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaHorarioDao|getLista|:"+ex);
		}
		return lista;
	}
	
	public List<CargaHorario> getListCarga(String codigoPersonal, String cargaId, String orden) {
		List<CargaHorario> lista		= new ArrayList<CargaHorario>();
		String comando		= "";
		
		try{
			comando = "SELECT * FROM CARGA_HORARIO"
					+ " WHERE CURSO_CARGA_ID IN" 
					+ " (SELECT CURSO_CARGA_ID FROM KRDX_CURSO_ACT WHERE CODIGO_PERSONAL = ? AND SUBSTR(CURSO_CARGA_ID,1,6) = ?) "+orden; 
			
			Object[] parametros = new Object[] {codigoPersonal, cargaId};
			lista = enocJdbc.query(comando, new CargaHorarioMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaHorarioDao|getListCarga|:"+ex);
		}
		return lista;
	}
	
	public List<CargaHorario> getListCarga(String codigoPersonal, String cargaId, String bloqueId, String orden) {
		List<CargaHorario> lista		= new ArrayList<CargaHorario>();
		String comando		= "";
		
		try{
			comando = "SELECT * FROM ENOC.CARGA_HORARIO"
					+ " WHERE BLOQUE_ID = ?"
					+ " AND CURSO_CARGA_ID IN" 
					+ " (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT WHERE CODIGO_PERSONAL = ? AND SUBSTR(CURSO_CARGA_ID,1,6) = ?) "
					+ " AND CURSO_CARGA_ID IN "
					+ " (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? AND RESTRICCION = 'S') "+orden; 
			
			Object[] parametros = new Object[] {bloqueId, codigoPersonal, cargaId, cargaId};
			lista = enocJdbc.query(comando, new CargaHorarioMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaHorarioDao|getListCarga|:"+ex);
		}
		return lista;
	}
	
	public List<CargaHorario> lisHorarioAlumno(String codigoPersonal, String cargaId, String bloqueId, String orden) {
		List<CargaHorario> lista		= new ArrayList<CargaHorario>();
		String comando		= "";
		
		try{
			comando = "SELECT * FROM CARGA_HORARIO"
					+ " WHERE CURSO_CARGA_ID IN" 
					+ " (SELECT CURSO_CARGA_ID FROM KRDX_CURSO_ACT WHERE CODIGO_PERSONAL = ? AND CARGA_DEL_GRUPO(CURSO_CARGA_ID) = ?)"
					+ " AND BLOQUE_ID = ? "+orden; 
			
			Object[] parametros = new Object[] {codigoPersonal, cargaId, bloqueId};
			lista = enocJdbc.query(comando, new CargaHorarioMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaHorarioDao|getListCarga|:"+ex);
		}
		
		return lista;
	}

	public List<CargaHorario> lisHorarioCargaSalonBloque(String cursoCargaId, String cargaId, String salonId, String bloqueId) {
		List<CargaHorario> lista = new ArrayList<CargaHorario>();
		try{
			String comando = "SELECT * FROM CARGA_HORARIO WHERE CURSO_CARGA_ID != ? AND SUBSTR(CURSO_CARGA_ID,1,6) = ? AND SALON_ID = ? AND BLOQUE_ID = ?";			
			Object[] parametros = new Object[] {cursoCargaId, cargaId, salonId, bloqueId};
			lista = enocJdbc.query(comando, new CargaHorarioMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaHorarioDao|lisHorarioCargaSalonBloque|:"+ex);
		}
		
		return lista;
	}
	
	public List<CargaHorario> lisHorariosPorCarga( String cargaId, String orden ) {
		List<CargaHorario> lista	= new ArrayList<CargaHorario>();		
		try{
			String comando = "SELECT * FROM ENOC.CARGA_HORARIO WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? "+ orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CargaHorarioMapper(), parametros);	
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaHorarioDao|lisHorariosPorCargaBloqueySalon|:"+ex);
		}
		return lista;
	}
	
public List<CargaHorario> lisHorarioPorCiclo(String cargaId, String planId, String ciclo){
		
		List<CargaHorario> lista	= new ArrayList<CargaHorario>();		
		try{			 
			String comando = "SELECT CURSO_CARGA_ID, SALON_ID, DIA, HORA_INICIO, MINUTO_INICIO, HORA_FINAL, MINUTO_FINAL, HORARIO_ID, PERIODO, BLOQUE_ID, FOLIO"
					+ " FROM ENOC.CARGA_HORARIO WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_ACADEMICA WHERE CARGA_ID = ? AND PLAN_ID = ? AND CICLO = ?)";
			Object[] parametros = new Object[] {cargaId, planId, ciclo};
			lista = enocJdbc.query(comando, new CargaHorarioMapper(), parametros);
					
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaHorarioDao|lisHorarioPorCiclo|:"+ex);
		}
		return lista;
	}	
	
	
	
	
}