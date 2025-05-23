package aca.cultural.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CompAsistenciaAlumnoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(CompAsistenciaAlumno objeto ) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.COMP_ASISTENCIA_ALUMNO(EVENTO_ID, CODIGO_PERSONAL, ENTRADA, SALIDA, ENTRADA_HORA, SALIDA_HORA, PLAN_ID)"
					+ " VALUES(TO_NUMBER(?,'9999'), ?, ?, ?, ?, ?, ?)";
			Object[] parametros = new Object[] {
				objeto.getEventoId(), objeto.getCodigoPersonal(), objeto.getEntrada(), objeto.getSalida(), objeto.getEntradaHora(), objeto.getSalidaHora(), objeto.getPlanId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompAsistenciaAlumnoDao|insertReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean updateReg( CompAsistenciaAlumno objeto ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.COMP_ASISTENCIA_ALUMNO SET ENTRADA = ?, SALIDA = ?, ENTRADA_HORA = ?, SALIDA_HORA = ?, PLAN_ID = ?"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'9999') AND CODIGO_PERSONAL = ?";			
			Object[] parametros = new Object[] {
				objeto.getEntrada(), objeto.getSalida(), objeto.getEntradaHora(), objeto.getSalidaHora(), objeto.getPlanId(), objeto.getEventoId(), objeto.getCodigoPersonal()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompAsistenciaAlumnoDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg(String eventoId, String codigoPersonal) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.COMP_ASISTENCIA_ALUMNO WHERE EVENTO_ID = TO_NUMBER(?,'9999') AND CODIGO_PERSONAL = ?";			
			Object[] parametros = new Object[] {eventoId,codigoPersonal};					
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompAsistenciaAlumnoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public CompAsistenciaAlumno mapeaRegId(String eventoId, String codigoPersonal) {
		CompAsistenciaAlumno objeto = new CompAsistenciaAlumno();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.COMP_ASISTENCIA_ALUMNO WHERE EVENTO_ID = TO_NUMBER(?,'9999') AND CODIGO_PERSONAL = ?";			
			Object[] parametros = new Object[] {eventoId,codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = "SELECT EVENTO_ID, CODIGO_PERSONAL, ENTRADA, SALIDA, ENTRADA_HORA, SALIDA_HORA, PLAN_ID FROM ENOC.COMP_ASISTENCIA_ALUMNO"
						+ " WHERE EVENTO_ID = TO_NUMBER(?,'9999') AND CODIGO_PERSONAL = ?";				
				objeto = enocJdbc.queryForObject(comando, new CompAsistenciaAlumnoMapper(), parametros);
			}					
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompAsistenciaAlumnoDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg(String eventoId, String codigoPersonal) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.COMP_ASISTENCIA_ALUMNO WHERE EVENTO_ID = TO_NUMBER(?,'9999') AND CODIGO_PERSONAL = ?";			
			Object[] parametros = new Object[] {eventoId,codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompAsistenciaAlumnoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String getUltimo(String eventoId, String tipo) {
		String comando 	= ""; 
		String codigo 	= "0";
		String hora		= "0";
		
		try{
			if (tipo.equals("E")){
				comando = "SELECT COUNT(*) FROM ENOC.COMP_ASISTENCIA_ALUMNO WHERE EVENTO_ID = TO_NUMBER(?,'9999') AND ENTRADA = 'S'";
			}else{
				comando = "SELECT COUNT(*) FROM ENOC.COMP_ASISTENCIA_ALUMNO WHERE EVENTO_ID = TO_NUMBER(?,'9999') AND SALIDA = 'S'";
			}
			if (enocJdbc.queryForObject(comando,Integer.class,eventoId)>=1){
				if (tipo.equals("E")) {
					comando = "SELECT MAX(ENTRADA_HORA) FROM ENOC.COMP_ASISTENCIA_ALUMNO WHERE EVENTO_ID = TO_NUMBER(?,'9999')";
					hora = enocJdbc.queryForObject(comando,String.class,eventoId);
					comando = "SELECT CODIGO_PERSONAL FROM ENOC.COMP_ASISTENCIA_ALUMNO WHERE EVENTO_ID = TO_NUMBER(?,'9999') AND ENTRADA_HORA = ?";
					codigo = enocJdbc.queryForObject(comando,String.class,eventoId,hora);
					
				}else{
					comando = "SELECT MAX(SALIDA_HORA) FROM ENOC.COMP_ASISTENCIA_ALUMNO WHERE EVENTO_ID = TO_NUMBER(?,'9999')";
					hora = enocJdbc.queryForObject(comando,String.class,eventoId);
					comando = "SELECT CODIGO_PERSONAL FROM ENOC.COMP_ASISTENCIA_ALUMNO WHERE EVENTO_ID = TO_NUMBER(?,'9999') AND SALIDA_HORA = ?";
					codigo = enocJdbc.queryForObject(comando,String.class,eventoId,hora);
				}				
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompAsistenciaAlumnoDao|getUltimo|:"+ex);
		}
		return codigo;
	}
	
	public List<CompAsistenciaAlumno> lisPorEvento(String eventoId, String orden) {
		List<CompAsistenciaAlumno> lista = new ArrayList<CompAsistenciaAlumno>();		
		try{
			String comando = "SELECT EVENTO_ID, CODIGO_PERSONAL, ENTRADA, SALIDA, ENTRADA_HORA, SALIDA_HORA, PLAN_ID FROM ENOC.COMP_ASISTENCIA_ALUMNO"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'9999') "+orden;			
			lista = enocJdbc.query(comando, new CompAsistenciaAlumnoMapper(), eventoId);		
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompAsistenciaAlumnoDao|lisPorEvento|:"+ex);
		}
		return lista;
	}
	
	public List<CompAsistenciaAlumno> lisPorEventoyRegistro(String eventoId, String tipo, String orden) {
		List<CompAsistenciaAlumno> lista = new ArrayList<CompAsistenciaAlumno>();		
		try{
			String comando = "SELECT EVENTO_ID, CODIGO_PERSONAL, ENTRADA, SALIDA, ENTRADA_HORA, SALIDA_HORA, PLAN_ID FROM ENOC.COMP_ASISTENCIA_ALUMNO"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'9999') AND ENTRADA = 'S' AND SALIDA = 'S' "+orden;
			if (tipo.equals("1")) {
				comando = "SELECT EVENTO_ID, CODIGO_PERSONAL, ENTRADA, SALIDA, ENTRADA_HORA, SALIDA_HORA, PLAN_ID FROM ENOC.COMP_ASISTENCIA_ALUMNO"
						+ " WHERE EVENTO_ID = TO_NUMBER(?,'9999') AND ENTRADA = 'S' "+orden;
			}else if (tipo.equals("2")) {
				comando = "SELECT EVENTO_ID, CODIGO_PERSONAL, ENTRADA, SALIDA, ENTRADA_HORA, SALIDA_HORA, PLAN_ID FROM ENOC.COMP_ASISTENCIA_ALUMNO"
						+ " WHERE EVENTO_ID = TO_NUMBER(?,'9999') AND SALIDA = 'S' "+orden;
			}
			lista = enocJdbc.query(comando, new CompAsistenciaAlumnoMapper(), eventoId);		
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompAsistenciaAlumnoDao|lisPorEventoyRegistro|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String,String> mapaAlumnos(){
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();		
		try {
			String comando = "SELECT EVENTO_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.COMP_ASISTENCIA_ALUMNO GROUP BY EVENTO_ID"; 
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for(aca.Mapa opcion : lista) {				
				mapa.put(opcion.getLlave(), opcion.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompAsistenciaAlumnoDao|mapaAlumnos|:"+ex);
		}		
		return mapa;
	}
	
}