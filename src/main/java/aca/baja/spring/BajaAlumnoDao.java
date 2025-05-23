package aca.baja.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BajaAlumnoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( BajaAlumno bajaAlum ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.BAJA_ALUMNO"+ 
				"(BAJA_ID, CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, CARRERA_ID, PLAN_ID, F_INICIO, F_BAJA, COMENTARIO, RAZON_ID, CREDITOS)"+
				" VALUES(TO_NUMBER(?, '9999999'), ?, ?, TO_NUMBER(?,'99'), ?, ?, TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'), ?, TO_NUMBER(?, '99'), TO_NUMBER(?, '99.99'))";					
			Object[] parametros = new Object[] {bajaAlum.getBajaId(),bajaAlum.getCodigoPersonal(),bajaAlum.getCargaId(),bajaAlum.getBloqueId(),bajaAlum.getCarreraId(),
					bajaAlum.getPlanId(),bajaAlum.getfInicio(),bajaAlum.getfBaja(),bajaAlum.getComentario(),bajaAlum.getRazonId(),bajaAlum.getCreditos()};
 			if (enocJdbc.update(comando,parametros) == 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.baja.spring.BajaAlumnoDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( BajaAlumno bajaAlum ) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.BAJA_ALUMNO"
					+ " SET CODIGO_PERSONAL = ?,"
					+ " CARGA_ID = ?,"
					+ " BLOQUE_ID = ?,"
					+ " CARRERA_ID = ?,"
					+ " PLAN_ID = ?,"
					+ " F_INICIO = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " F_BAJA = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " COMENTARIO = ?,"
					+ " RAZON_ID = ?,"
					+ " CREDITOS = ?"
					+ " WHERE BAJA_ID = TO_NUMBER(?, '9999999')";
			Object[] parametros = new Object[] {bajaAlum.getCodigoPersonal(),bajaAlum.getCargaId(), bajaAlum.getBloqueId(), bajaAlum.getCarreraId(),
					bajaAlum.getPlanId(),bajaAlum.getfInicio(),bajaAlum.getfBaja(),bajaAlum.getComentario(),bajaAlum.getRazonId(),
					bajaAlum.getCreditos(), bajaAlum.getBajaId()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.baja.spring.BajaAlumnoDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg( String bajaId){
		boolean ok = false;	
		try{
			String comando = "DELETE FROM ENOC.BAJA_ALUMNO WHERE BAJA_ID = TO_NUMBER(?, '9999999')";	
			Object[] parametros = new Object[] {bajaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.baja.spring.BajaAlumnoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public BajaAlumno mapeaRegId( String bajaId){
		BajaAlumno bajaAlum = new BajaAlumno();	
		try{
			String comando = "SELECT BAJA_ID, CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, CARRERA_ID, PLAN_ID, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_BAJA, 'DD/MM/YYYY') AS F_BAJA, COMENTARIO, RAZON_ID, CREDITOS"
					+ " FROM ENOC.BAJA_ALUMNO WHERE BAJA_ID = ?";
				Object[] parametros = new Object[] {bajaId};
				bajaAlum = enocJdbc.queryForObject(comando, new BajaAlumnoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.baja.spring.BajaAlumnoDao|mapeaRegId|:"+ex);
		}
		return bajaAlum;
	}
	
	public BajaAlumno mapeaSolicitud( String codigoPersonal, String cargaId, String bloqueId){
		BajaAlumno bajaAlum = new BajaAlumno();		
		try{
			String comando = "SELECT BAJA_ID, CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, CARRERA_ID, PLAN_ID,"
					+ " TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_BAJA, 'DD/MM/YYYY') AS F_BAJA, COMENTARIO, RAZON_ID, CREDITOS"
					+ " FROM ENOC.BAJA_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID = ?"
					+ " AND BLOQUE_ID = TO_NUMBER(?,'99')";						
			Object[] parametros = new Object[] {codigoPersonal,cargaId,bloqueId};
			bajaAlum = enocJdbc.queryForObject(comando, new BajaAlumnoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.baja.spring.BajaAlumnoDao|mapeaRegSolicitado|:"+ex);
		}
		return bajaAlum;
	}
	
	public BajaAlumno mapeaRegSolicitado( String codigoPersonal) {
		BajaAlumno bajaAlum = new BajaAlumno();
		try{
			String comando = "SELECT BAJA_ID, CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, CARRERA_ID, PLAN_ID,"
				+ " TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_BAJA, 'DD/MM/YYYY') AS F_BAJA, COMENTARIO, RAZON_ID, CREDITOS"
				+ " FROM ENOC.BAJA_ALUMNO "
				+ " WHERE CODIGO_PERSONAL = ?"
				+ " AND F_BAJA IS NULL";						
			Object[] parametros = new Object[] {codigoPersonal};
			bajaAlum = enocJdbc.queryForObject(comando, new BajaAlumnoMapper(), parametros);	
		}catch(Exception ex){
			System.out.println("Error - aca.baja.spring.BajaAlumnoDao|mapeaRegSolicitado|:"+ex);
		}
		return bajaAlum;
	}
	
	public boolean existeReg( String bajaId) {
		boolean 			ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) BAJA_ID FROM ENOC.BAJA_ALUMNO"
					+ " WHERE BAJA_ID = TO_NUMBER(?, '9999999')";			
			Object[] parametros = new Object[] {bajaId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.baja.spring.BajaAlumnoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean existeReg( String codigoPersonal, String cargaId, String carreraId) {
		boolean 			ok 	= false;				
		try{
			String comando = "SELECT COUNT(*) BAJA_ID FROM ENOC.BAJA_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID = ?"
					+ " AND CARRERA_ID = ?";			
			Object[] parametros = new Object[] {codigoPersonal,cargaId,carreraId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.baja.spring.BajaAlumnoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean existeSolicitud( String codigoPersonal, String cargaId, String bloqueId) {
		boolean 			ok 	= false;				
		try{
			String comando = "SELECT COUNT(*) BAJA_ID FROM ENOC.BAJA_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID = ?"
					+ " AND BLOQUE_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {codigoPersonal,cargaId,bloqueId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >=1 ){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.baja.spring.BajaAlumnoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String getBajaId( String codigoPersonal, String cargaId, String bloqueId) {
		String bajaId = "0";				
		try{
			String comando = "SELECT COUNT(*) BAJA_ID FROM ENOC.BAJA_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID = ?"
					+ " AND BLOQUE_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal,cargaId,bloqueId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >=1 ){
				comando = "SELECT BAJA_ID FROM ENOC.BAJA_ALUMNO"
						+ " WHERE CODIGO_PERSONAL = ?"
						+ " AND CARGA_ID = ?"
						+ " AND BLOQUE_ID = TO_NUMBER(?,'99')";
				bajaId = enocJdbc.queryForObject(comando,String.class, parametros);				
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.baja.spring.BajaAlumnoDao|existeReg|:"+ex);
		}
		return bajaId;
	}
	
	public String maximo(){
		String  maximo 		= "";
		try{
			String comando = "SELECT COALESCE(MAX(BAJA_ID+1), 1) AS MAXIMO FROM ENOC.BAJA_ALUMNO";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
				maximo = enocJdbc.queryForObject(comando, String.class);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.baja.spring.BajaAlumnoDao|maximoCurso|:"+ex);
		}		
		return maximo;
	}
	
	public List<BajaAlumno> getListSolicitudes( String orden) {
		List<BajaAlumno> lista 	= new ArrayList<BajaAlumno>();				
		try{
			String comando = "SELECT BAJA_ID, CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, CARRERA_ID, PLAN_ID," +
					" TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO," +
					" TO_CHAR(F_BAJA, 'DD/MM/YYYY') AS F_BAJA, COMENTARIO, RAZON_ID, CREDITOS" +
					" FROM ENOC.BAJA_ALUMNO A" + 
					" WHERE F_BAJA IS NULL "+orden;			
			lista = enocJdbc.query(comando, new BajaAlumnoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.baja.spring.BajaAlumnoDao|getListSolicitudes|:"+ex);
		}
		return lista;
	}
}