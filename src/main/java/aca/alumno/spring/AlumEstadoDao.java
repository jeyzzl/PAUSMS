// Clase para la tabla de Alum_ESTADO
package aca.alumno.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumEstadoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AlumEstado alumEstado ) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.ALUM_ESTADO"
					+ "(CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN ) "
					+ "VALUES( ?, ?, TO_NUMBER(?,'99'), ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), ?, ?, ?, TO_DATE(?,'DD/MM/YYYY'), ?, ?,TO_NUMBER(?,'99'), TO_NUMBER(?,'99'),TO_NUMBER(?,'99'))";			
			Object[] parametros = new Object[] {alumEstado.getCodigoPersonal(),alumEstado.getCargaId(),alumEstado.getBloqueId(),alumEstado.getEstado(),alumEstado.getModalidadId(),alumEstado.getTipoalumnoId(),alumEstado.getFacultadId(),alumEstado.getCarreraId(),alumEstado.getPlanId(),alumEstado.getFecha(),alumEstado.getResidenciaId(),alumEstado.getDormitorio(),alumEstado.getCiclo(),alumEstado.getGrado(),alumEstado.getClasFin()}; 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(AlumEstado alumEstado ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ALUM_ESTADO "
					+ " SET ESTADO = ?, "
					+ " MODALIDAD_ID =  TO_NUMBER(?,'99'),"
					+ " TIPOALUMNO_ID = TO_NUMBER(?,'99'),"
					+ " FACULTAD_ID = ?,"
					+ " CARRERA_ID = ?,"
					+ " PLAN_ID = ?,"
					+ " FECHA = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " RESIDENCIA_ID = ?,"
					+ " DORMITORIO = ?,"
					+ " CICLO = TO_NUMBER(?,'99'),"
					+ " GRADO = TO_NUMBER(?,'99'),"
					+ " CLAS_FIN = TO_NUMBER(?,'99')"
					+ " WHERE CODIGO_PERSONAL = ? "
					+ " AND CARGA_ID = ? "
					+ " AND BLOQUE_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {alumEstado.getEstado(),alumEstado.getModalidadId(),alumEstado.getTipoalumnoId(),alumEstado.getFacultadId(),alumEstado.getCarreraId(),alumEstado.getPlanId(),alumEstado.getFecha(),alumEstado.getResidenciaId(),alumEstado.getDormitorio(),alumEstado.getCiclo(),alumEstado.getGrado(),alumEstado.getClasFin(),alumEstado.getCodigoPersonal(),alumEstado.getCargaId(),alumEstado.getBloqueId()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean updateEstadoReg(AlumEstado alumEstado ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ALUM_ESTADO SET ESTADO = ?"					
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID = ?"
					+ " AND BLOQUE_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {alumEstado.getEstado(),alumEstado.getCodigoPersonal(),alumEstado.getCargaId(),alumEstado.getBloqueId()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|updateEstadoReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg(String codigoPersonal, String cargaId, String bloqueId ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ALUM_ESTADO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID = ?"
					+ " AND BLOQUE_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {codigoPersonal,cargaId,bloqueId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|deletetReg|:"+ex);			
		}
		return ok;
	}
	
	public AlumEstado mapeaRegId( String codigoPersonal, String cargaId, String bloqueId) {
		
		AlumEstado alumEstado = new AlumEstado();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, "
				+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN"
				+ " FROM ENOC.ALUM_ESTADO "
				+ " WHERE CODIGO_PERSONAL = ? "
				+ " AND CARGA_ID = ? "
				+ " AND BLOQUE_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {codigoPersonal,cargaId,bloqueId};
			alumEstado = enocJdbc.queryForObject(comando, new AlumEstadoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|mapeaRegId|:"+ex);
		}		
		return alumEstado;
	}
	
	public AlumEstado mapeaRegId( String codigoPersonal, String cargaId) {		
		AlumEstado alumEstado = new AlumEstado();		
		try{
			String comando = "SELECT "
					+ "CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, COALESCE(MODALIDAD_ID,0) AS MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN "
					+ "FROM ENOC.ALUM_ESTADO "
					+ "WHERE CODIGO_PERSONAL = ? "
					+ "AND CARGA_ID IN (?) ";
			
			Object[] parametros = new Object[] {codigoPersonal,cargaId};
			alumEstado = enocJdbc.queryForObject(comando, new AlumEstadoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|mapeaRegId|:"+ex);
		}		
		return alumEstado;
	}
	
	public boolean existeReg(String codigoPersonal, String cargaId, String bloqueId) {
		boolean 		ok 	= false;				
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.ALUM_ESTADO "
					+ "WHERE CODIGO_PERSONAL = ? "
					+ "AND CARGA_ID = ? "
					+ "AND BLOQUE_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {codigoPersonal,cargaId,bloqueId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|existeReg|:"+ex);
		}
		return ok;
	}	
	
	public String getCarga(String codigoPersonal) {
		
		String 	carga		= "";		
		try{
			String comando = "SELECT CARGA_ID FROM ENOC.ALUM_ESTADO "
					+ "WHERE CODIGO_PERSONAL = ? AND ESTADO = 'I' "
					+ "AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA "
					+ "WHERE TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YY') BETWEEN F_INICIO AND F_FINAL)";			
			Object[] parametros = new Object[] {codigoPersonal};
			carga = enocJdbc.queryForObject(comando,String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|getCarga|:"+ex);
		}
		return carga;
	}
	
	public String getCarreraEnFecha(String codigoPersonal, String fecha) {				
		String 	carga		= "x";		
		try{
			String comando = " SELECT DISTINCT(CARRERA_ID) FROM ENOC.ALUM_ESTADO "
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID IN "
					+ "		(SELECT CARGA_ID FROM ENOC.CARGA "
					+ "		WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL"
					+ "		AND CARRERA_ID != '00000')";			
			Object[] parametros = new Object[] {codigoPersonal, fecha};
			carga = enocJdbc.queryForObject(comando,String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|getCarreraEnFecha|:"+ex);
		}		
		return carga;
	}
	
	public String getAlumComidas(String codigo, String carga, int bloque) {
		
		String 	cantAlum	= "x";		
		try{
			String comando = "SELECT ALUM_COMIDAS('"+codigo+"','"+carga+"',"+bloque+") AS COMIDAS"
					+ " FROM ENOC.ALUM_ESTADO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID = ?"
				    + " AND BLOQUE_ID = ?";			
			Object[] parametros = new Object[] {codigo,carga,bloque};
			cantAlum = enocJdbc.queryForObject(comando,String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|getAlumComidas|:"+ex);
		}		
		return cantAlum;
	}
	
	public TreeMap<String,AlumEstado> getMapAllInscritos(String orden ) {		
		TreeMap<String,AlumEstado> mapa	= new TreeMap<String,AlumEstado>();
		List<AlumEstado> lista	= new ArrayList<AlumEstado>();		
		try{
			String comando = "SELECT * FROM ENOC.ALUM_ESTADO"
					+ " WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL)  "+ orden; 
			lista = enocJdbc.query(comando, new AlumEstadoMapper());			
			for (AlumEstado estado : lista){			
				mapa.put(estado.getCodigoPersonal(), estado);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|getMapAllInscritos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,AlumEstado> mapaInscritos(String orden) {		
		HashMap<String,AlumEstado> mapa	= new HashMap<String,AlumEstado>();
		List<AlumEstado> lista	= new ArrayList<AlumEstado>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, "
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN"
					+ " FROM ENOC.ALUM_ESTADO"
					+ " WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL)  "+ orden; 
			lista = enocJdbc.query(comando, new AlumEstadoMapper());			
			for (AlumEstado estado : lista){			
				mapa.put(estado.getCodigoPersonal(), estado);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|getMapAllInscritos|:"+ex);
		}
		
		return mapa;
	}
	
	public String getInterno(String codigoPersonal, String cargaId) {
		
		String 	residencia	= "x";		
		try{
			String comando = "SELECT RESIDENCIA_ID FROM ENOC.ALUM_ESTADO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal,cargaId};
			residencia = enocJdbc.queryForObject(comando,String.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|getInterno|:"+ex);
		}		
		return residencia;
	}
	
	public String cargaCreditos(String carga, String modalidades, String fechaIni, String fechaFin) {
		
		String 	cantAlum	= "";
		
		try{
			String comando = " SELECT COALESCE(SUM(ENOC.ALUM_CRED_CARGA_MOD(CODIGO_PERSONAL,CARGA_ID, MODALIDAD_ID)),0) AS TOTAL FROM ENOC.ALUM_ESTADO"
					+ " WHERE CARGA_ID = ?"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND ESTADO = 'I'"
					+ " AND FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') "
					+ " AND TO_DATE(?,'DD/MM/YYYY')";			
			Object[] parametros = new Object[] {carga,fechaIni,fechaFin};
			cantAlum = enocJdbc.queryForObject(comando,String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|cargaCreditos|:"+ex);
		}		
		return cantAlum;
	}
	
	public String calculoCreditos(String carga, String modalidades, String fechaIni, String fechaFin) {
		
		String 	cantAlum	= "0";
		
		try{
			String comando = " SELECT COALESCE(SUM(CREDITOS),0) AS TOTAL FROM MATEO.FES_CC_MATERIA"
					+ " WHERE CARGA_ID = ?"
					+ " AND MATRICULA||CARGA_ID||BLOQUE IN"
					+ " 	(SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CCOBRO "
					+ "		WHERE CARGA_ID = ? AND INSCRITO = 'S'"
					+ " 	AND MODALIDAD_ID IN ("+modalidades+")"
					+ "		AND FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') "
					+ " 	AND TO_DATE(?,'DD/MM/YYYY') )";			
			Object[] parametros = new Object[] {carga,carga,fechaIni,fechaFin};
			cantAlum = enocJdbc.queryForObject(comando,String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|calculoCreditos|:"+ex);
		}
		return cantAlum;
	}
	
	public String numAlumEst(String carga, String modalidades, String fechaIni, String fechaFin) {

		String 	cantAlum	= "";
		
		try{
			String comando = " SELECT COUNT(*) AS TOTAL FROM ENOC.ESTADISTICA"
					+ " WHERE CARGA_ID = ?"					
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') "
					+ " AND TO_DATE(?,'DD/MM/YYYY')";			
			Object[] parametros = new Object[] {carga,fechaIni,fechaFin};
			cantAlum = enocJdbc.queryForObject(comando,String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|numAlumEst|:"+ex);
		} 
		return cantAlum;
	}

	public String numAlumCalculo(String carga, String modalidades, String fechaIni, String fechaFin) {

		String 	cantAlum	= "0";		
		try{
			String comando = " SELECT COUNT(*) AS TOTAL FROM MATEO.FES_CCOBRO"
					+ " WHERE CARGA_ID = ? "
					+ " AND INSCRITO = 'S'"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') "
					+ " AND TO_DATE(?,'DD/MM/YYYY')";			
			Object[] parametros = new Object[] {carga,fechaIni,fechaFin};
			cantAlum = enocJdbc.queryForObject(comando,String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|numAlumCalculo|:"+ex);
		}		
		return cantAlum;
	}
	
	public boolean getAlumnoInscrito(String matricula, String carga) {
		boolean	encontro		= false;	
		try{
			String comando = " SELECT * FROM ENOC.ALUM_ESTADO"
					+ " WHERE CODIGO_PERSONAL = ? "
					+ " AND CARGA_ID = ? "
					+ " AND ESTADO = 'I'"; 
			
			Object[] parametros = new Object[] {matricula,carga};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				encontro = true;
			}
				
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|getAlumnoInscrito|:"+ex);
		}
		return encontro;
	}
	
	public String getAlumEstado(String matricula, String carga, String bloque) {		
		String estado = "-";
		
		try{
			String comando = " SELECT COALESCE(ESTADO,'M') AS ESTADO FROM ENOC.ALUM_ESTADO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID = ?"
					+ " AND BLOQUE_ID = ?"; 
			
			Object[] parametros = new Object[] {matricula,carga,bloque};
			estado = enocJdbc.queryForObject(comando,String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|getAlumEstado|:"+ex);
		}
		return estado;
	}
	
	public String getCreditosPorCarga(String matricula, String carga, String estado) {	
		
		String total 			= "0";
		
		try{
			String comando = "SELECT COALESCE(SUM(CREDITOS),0) AS TOTAL FROM MATEO.FES_CC_MATERIA "
					+ " WHERE MATRICULA||CARGA_ID IN "
					+ " (SELECT CODIGO_PERSONAL||CARGA_ID FROM ENOC.ALUM_ESTADO WHERE CODIGO_PERSONAL = ? AND CARGA_ID IN (?) AND ESTADO IN(?)) "
					+ " GROUP BY MATRICULA, CARGA_ID ";
			Object[] parametros = new Object[] {matricula,carga,estado};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				total = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|getCreditosPorCarga|:"+ex);
		}
		
		return total;
	}
	
	public boolean tienePlan(String codigoPersonal, String planId, String estado) {
		boolean ok 	= false;				
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_ESTADO WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ? AND ESTADO = ?";			
			Object[] parametros = new Object[] {codigoPersonal,planId,estado};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|tienePlan|:"+ex);
		}
		return ok;
	}	
	
		
	public List<AlumEstado> getLista(String cargas, String orden ) {
		
		List<AlumEstado> lista 		= new ArrayList<AlumEstado>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN"+
				" FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN("+cargas+") "+orden;
			lista = enocJdbc.query(comando, new AlumEstadoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|getLista|:"+ex);
		}
		
		return lista;
	}
	
	public List<AlumEstado> lisHistorico(String estados, String orden ) {		
		List<AlumEstado> lista 		= new ArrayList<AlumEstado>();	
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN"
					+ " FROM ENOC.ALUM_ESTADO"
					+ " WHERE FACULTAD_ID != '000' AND ESTADO IN ("+estados+") "+orden;			
			lista = enocJdbc.query(comando, new AlumEstadoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|lisHistorico|:"+ex);
		}		
		return lista;
	}
	
	public List<AlumEstado> lisPorCargayEstado(String cargaId, String estados, String orden ) {		
		List<AlumEstado> lista 		= new ArrayList<AlumEstado>();	
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN"
					+ " FROM ENOC.ALUM_ESTADO"
					+ " WHERE CARGA_ID = ?"
					+ " AND ESTADO IN ("+estados+") "+orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new AlumEstadoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|getLista|:"+ex);
		}		
		return lista;
	}
	
	public List<aca.Mapa> listaPorCargaIdYcarreraId(String cargaId, String carreraId, String orden) {		
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, ALUM_APELLIDO(CODIGO_PERSONAL) AS VALOR FROM ALUM_ESTADO WHERE CARGA_ID = ? AND CARRERA_ID = ? AND ESTADO = 'I' "+orden;
			Object[] parametros = new Object[] {cargaId,carreraId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|listaPorCargaIdYcarreraId|:"+ex);
		}		
		return lista;
	}
	
	public List<AlumEstado> getLista(String cargas, String modalidades, String estado,String fechaIni, String fechaFin, String orden ) {
		
		List<AlumEstado> lista 		= new ArrayList<AlumEstado>();		
		String comando		= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN"
					+ " FROM ENOC.ALUM_ESTADO"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND ESTADO = ? "+orden;
			lista = enocJdbc.query(comando, new AlumEstadoMapper(), fechaIni, fechaFin, estado);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|getLista|:"+ex);
		}
		
		return lista;
	}
	
	public List<AlumEstado> getLista(String cargas, String modalidades, String estado, String orden ) {
		
		List<AlumEstado> lista 		= new ArrayList<AlumEstado>();	
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN"
					+ " FROM ENOC.ALUM_ESTADO"
					+ " WHERE CARGA_ID IN ("+cargas+")"					
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND ESTADO = ? "+orden;			
			lista = enocJdbc.query(comando, new AlumEstadoMapper(), estado);		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|getLista|:"+ex);
		}
		
		return lista;
	}

	public List<AlumEstado> getListaPorFecha(String cargas, String modalidades, String fechaIni, String fechaFin, String estado, String orden ) {
		
		List<AlumEstado> lista 		= new ArrayList<AlumEstado>();		
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN"
					+ " FROM ENOC.ALUM_ESTADO"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"				
					+ " AND ESTADO = ? "+orden;
			lista = enocJdbc.query(comando, new AlumEstadoMapper(), fechaIni, fechaFin, estado);
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|getListaPorFecha|:"+ex);
		}
		return lista;
	}
	
	public List<AlumEstado> lisHistoricoDeBajas(String orden ) {
		
		List<AlumEstado> lista 		= new ArrayList<AlumEstado>();	
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN"+
				" FROM ENOC.ALUM_ESTADO WHERE ESTADO = '3' "+orden; 
			lista = enocJdbc.query(comando, new AlumEstadoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|lisHistoricoDeBajas|:"+ex);
		}
		
		return lista;
	}	
	
	public List<AlumEstado> filtroAlumnos(String modalidad, String facultad, String carrera_id, String residencia, String dormitorio, String grado){
		
		List<AlumEstado> lisEstado = new ArrayList<AlumEstado>();
		
		try{
			String comando = " SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN FROM ENOC.ALUM_ESTADO"
					+ " WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE now() BETWEEN F_INICIO AND F_FINAL) AND ESTADO ='I'";
			if (!modalidad.equals("0")){
				comando = comando + " AND MODALIDAD_ID IN ("+modalidad+")";
			}
			
			if (!facultad.equals("000")){
				comando = comando + " AND FACULTAD_ID = '"+facultad+"'";
			}
			
			if (!carrera_id.equals("00000")){
				comando = comando + " AND CARRERA_ID = '"+carrera_id+"'";
			}
			
			if (!residencia.equals("0")){
				comando = comando + " AND RESIDENCIA_ID = '"+residencia+"'";
			}
			
			if (!dormitorio.equals("0")){
				comando = comando + " AND DORMITORIO = '"+dormitorio+"'";
			}
			
			if (!grado.equals("0")){
				comando = comando + " AND GRADO = "+grado+" ";
			}
			
			lisEstado = enocJdbc.query(comando, new AlumEstadoMapper());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|filtroAlumnos|:"+ex);
		}
		
		return lisEstado;
	}
	
	public List<String> getListCargasNoVigentes(String codigoPersonal, String fechaInicio, String orden ) {
		
		List<String> lista = new ArrayList<String>();		
		String comando		= "";		
		try{
			comando = " SELECT CARGA_ID FROM ENOC.ALUM_ESTADO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND (SELECT F_INICIO FROM ENOC.CARGA WHERE CARGA_ID = ENOC.ALUM_ESTADO.CARGA_ID) > TO_DATE(?,'DD/MM/YYYY')"
					+ " AND ESTADO = 'I' "+orden; 
			lista = enocJdbc.queryForList(comando, String.class, codigoPersonal, fechaInicio);
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|getListCargasNoVigentes|:"+ex);
		}
		
		return lista;
	}
	
	public List<AlumEstado> getLista(String codigoPersonal) {
		
		List<AlumEstado> lista 		= new ArrayList<AlumEstado>();	
		try{
			String comando = " SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID,"
					+ " TO_CHAR(FECHA,'YYYY/MM/YY') AS FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN"
					+ " FROM ENOC.ALUM_ESTADO WHERE CODIGO_PERSONAL = ? ORDER BY FECHA";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new AlumEstadoMapper(), parametros);				
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|getLista|:"+ex);
		}
		
		return lista;
	}
	
	public List<AlumEstado> getLista(String cargaId, String planId, String orden ) {		
		List<AlumEstado> lista 		= new ArrayList<AlumEstado>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID,"
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN"
					+ " FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = ?"
					+ " AND ALUM_PLAN_HISTORICO(CODIGO_PERSONAL,CARGA_ID,BLOQUE_ID) = ? "+orden;
			Object[] parametros = new Object[] {cargaId, planId};
			lista = enocJdbc.query(comando, new AlumEstadoMapper(), parametros);	
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|getLista|:"+ex);
		}
		
		return lista;
	}
	
	public List<AlumEstado> getListAll(String orden ) {
		
		List<AlumEstado> lista		= new ArrayList<AlumEstado>();	
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN"
					+ " FROM ENOC.ALUM_ESTADO "+orden; 			
			lista = enocJdbc.query(comando, new AlumEstadoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, AlumEstado> mapaAlumEstado(String codigoPersonal){
		HashMap<String,AlumEstado> mapa		= new HashMap<String,AlumEstado>();
		List<AlumEstado> lista = new ArrayList<AlumEstado>();
		try{
			String comando = " SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, "
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN"
					+ " FROM ENOC.ALUM_ESTADO"
					+ " WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new AlumEstadoMapper(), parametros);
			for(AlumEstado alumno : lista){				
				mapa.put(alumno.getCargaId()+alumno.getBloqueId(), alumno);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|mapaInscritosEnCarga|:"+ex);
		}		
		return mapa;
	}

	public HashMap<String, AlumEstado> mapaAlumEstado(String cargaId, String orden){
		HashMap<String,AlumEstado> mapa		= new HashMap<String,AlumEstado>();
		List<AlumEstado> lista = new ArrayList<AlumEstado>();
		try{
			String comando = " SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, "
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN"
					+ " FROM ENOC.ALUM_ESTADO"
					+ " WHERE CARGA_ID = ? " + orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new AlumEstadoMapper(), parametros);
			for(AlumEstado alumno : lista){				
				mapa.put(alumno.getCodigoPersonal(), alumno);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|mapaAlumEstado|:"+ex);
		}		
		return mapa;
	}

	
	public HashMap<String,String> getMapInscritos(String cargaId, String orden ) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, CARRERA_ID AS VALOR" +
					" FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = ? AND ESTADO = 'I' "+ orden;
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|getMapInscritos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> getMapInscritosActualmente(){
		
		HashMap<String,String> mapPlan = new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
	
		try{
			String comando = "SELECT " +
					" CODIGO_PERSONAL AS LLAVE, CARRERA_ID AS VALOR" +
					" FROM ENOC.ALUM_ESTADO WHERE ESTADO = 'I' AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL) ";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapPlan.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|getMapInscritosActualmente|:"+ex);
		}
		
		return mapPlan;
	}
	
	public HashMap<String,String> getMapInscritosCargaBloque(String cargaId) {		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID AS LLAVE, CODIGO_PERSONAL AS VALOR" +
					" FROM ENOC.ALUM_ESTADO WHERE ESTADO = 'I' AND CARGA_ID = ?";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), new Object[] {cargaId});
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|getMapInscritosCargaBloque|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaInscripcionesAlumno(String codigoPersonal) {		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();	
		try{
			String comando = "SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID AS LLAVE, CODIGO_PERSONAL AS VALOR" +
					" FROM ENOC.ALUM_ESTADO WHERE CODIGO_PERSONAL = ? AND ESTADO = 'I'";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), new Object[] {codigoPersonal});
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|mapaInscripcionesAlumno|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> getMapInscritoFecha(String fecha) {
	
		HashMap<String,String> mapa 	= new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		String comando							= "";
				
		try{
			comando = " SELECT CODIGO_PERSONAL AS LLAVE, CODIGO_PERSONAL AS VALOR FROM ENOC.ALUM_ESTADO WHERE ESTADO = 'I' "
					+ " AND CARGA_ID IN ( SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL)";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), fecha);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|getMapInscritoFecha|:"+ex);
		}
		
		return mapa;
	}	
	
	public HashMap<String, String> mapTipoAlumno(String fecha) {
		
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String comando							= "";
				
		try{
			comando = " SELECT CODIGO_PERSONAL AS LLAVE, TIPOALUMNO_ID AS VALOR FROM ENOC.ALUM_ESTADO"
					+ " WHERE ESTADO = 'I' "
					+ " AND CARGA_ID IN ( SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), fecha);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|mapTipoAlumno|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapAlumCargaCreditos( ) {
		
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String comando							= "";
				
		try{
			comando = " SELECT"
					+ " CODIGO_PERSONAL AS LLAVE, CARGA_ID AS VALOR, ENOC.ALUM_CRED_CARGA_MOD(CODIGO_PERSONAL,CARGA_ID, MODALIDAD_ID) AS TOTAL_CREDITOS"
					+ " FROM ENOC.ALUM_ESTADO"
					+ " WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL)"
					+ " AND ESTADO = 'I'";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|mapAlumCargaCreditos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapAlumCargaModalidadCreditos(String cargas) {
		
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		String comando							= "";
				
		try{
			comando = " SELECT"
					+ " CODIGO_PERSONAL||CARGA_ID||MODALIDAD_ID AS LLAVE, ENOC.ALUM_CRED_CARGA_MOD(CODIGO_PERSONAL,CARGA_ID, MODALIDAD_ID) AS VALOR"
					+ " FROM ENOC.ALUM_ESTADO"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND ESTADO = 'I'";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|mapAlumCargaCreditos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapAlumPromCargasyModalidades(String cargas, String modalidades, String estado) {
		
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String comando							= "";
				
		try{
			comando = " SELECT CARGA_ID||CODIGO_PERSONAL AS LLAVE, ENOC.ALUM_PROMEDIO_CARGA(CODIGO_PERSONAL,CARGA_ID,PLAN_ID) AS VALOR"
					+ " FROM ENOC.ALUM_ESTADO"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND ESTADO = ?";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), estado);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|mapAlumPromCargasyModalidades|:"+ex);
		}
		
		return mapa;
	}
	
	public List<AlumEstado> getListaCargaInscrito(String codigoPersonal) {
		
		List<AlumEstado> lista 		= new ArrayList<AlumEstado>();	
		String comando		= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN"
					+ " FROM ENOC.ALUM_ESTADO WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE now() BETWEEN F_INICIO AND F_FINAL) AND ESTADO ='I'"; 
			lista = enocJdbc.query(comando, new AlumEstadoMapper(), codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|getListaCargaInscrito|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, String> mapGradoyCiclo(String cargas, String estado) {
		
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String comando							= "";
				
		try{
			comando = " SELECT CARGA_ID||CODIGO_PERSONAL AS LLAVE, GRADO||','||CICLO AS VALOR FROM ENOC.ALUM_ESTADO"
					+ " WHERE CARGA_ID IN ("+cargas+")"	
					+ " AND ESTADO = ?";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), estado);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|mapGradoyCiclo|:"+ex);
		}		
		
		return mapa;
	}
	
	public HashMap<String, String> mapaGradoyCiclo(String cargas, String estado) {
		
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT CARGA_ID||CODIGO_PERSONAL||BLOQUE_ID AS LLAVE, GRADO||','||CICLO AS VALOR FROM ENOC.ALUM_ESTADO"
					+ " WHERE CARGA_ID IN ("+cargas+")"	
					+ " AND ESTADO = ?";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), estado);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|mapGradoyCiclo|:"+ex);
		}		
		
		return mapa;
	}
	
	public HashMap<String, AlumEstado> mapaInscritosEnCarga(String cargaId) {
		HashMap<String,AlumEstado> mapa		= new HashMap<String,AlumEstado>();
		List<AlumEstado> lista = new ArrayList<AlumEstado>();
		try{
			String comando = " SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, "
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN"
					+ " FROM ENOC.ALUM_ESTADO"
					+ " WHERE CARGA_ID = ?";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new AlumEstadoMapper(), parametros);
			for(AlumEstado alumno : lista){				
				mapa.put(alumno.getCodigoPersonal()+alumno.getBloqueId(), alumno);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|mapaInscritosEnCarga|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaModalidadesEnCarga(String cargaId, String orden) {
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<AlumEstado> lista = new ArrayList<AlumEstado>();
		try{
			String comando = " SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, "
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN"
					+ " FROM ENOC.ALUM_ESTADO"
					+ " WHERE CARGA_ID = ? "+orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new AlumEstadoMapper(), parametros);
			AlumEstado tmp = lista.get(0);
			String modalidad = tmp.getModalidadId();
			int cont = 1;
			for(AlumEstado alumno : lista){		
				if(tmp.getCodigoPersonal().equals(alumno.getCodigoPersonal())) {
					if(!tmp.getModalidadId().equals(alumno.getModalidadId())) {
						modalidad += ","+alumno.getModalidadId();
					}
				}else {
					mapa.put(tmp.getCodigoPersonal(), modalidad);
					tmp = alumno;
					modalidad = tmp.getModalidadId();
				}
				if(cont == lista.size()) {
					mapa.put(alumno.getCodigoPersonal(), alumno.getModalidadId());
				}
				cont++;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|mapaModalidadesEnCarga|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaInscritosPorCarrera(String cargaId) {
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CARRERA_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = ? AND ESTADO = 'I' GROUP BY CARRERA_ID";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|mapaInscritosPorCarrera|:"+ex);
		}		
		return mapa;
	}

	public HashMap<String,String> mapaInscritosPorFecha(String fechaIni, String fechaFin) {
		
		HashMap<String,String> mapa 	= new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL||CARRERA_ID AS LLAVE, FECHA AS VALOR"
					+ " FROM ALUM_ESTADO WHERE FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"
					+ " AND ESTADO = 'I'";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), fechaIni, fechaFin);	
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|mapaInscritosPorFecha|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaEdad(String cargas, String estado) {
		
		HashMap<String,String> mapa 	= new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, ALUM_EDAD(CODIGO_PERSONAL) AS VALOR  FROM ALUM_ESTADO WHERE CARGA_ID IN("+cargas+") AND ESTADO = ?";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), estado);	
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumEstadoDao|mapaEdad|:"+ex);
		}
		
		return mapa;
	}

}