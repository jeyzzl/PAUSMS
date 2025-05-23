/*
 * Created on 06-abr-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.investiga.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

/**
 * @author etorres
 *
 */
@Controller
public class InvProyectoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( InvProyecto proy) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.INV_PROYECTO(PROYECTO_ID, PROYECTO_NOMBRE, CODIGO_PERSONAL, TIPO, LINEA, "
					+ " CARRERA_ID, DEPARTAMENTO, FECHA_INICIO, FECHA_FINAL, RESUMEN, ESTADO_ARTE, DOCUMENTO, ESTADO, FOLIO, ANTECEDENTES,"
					+ " JUSTIFICACION, RES_DOCENTE, RES_ALUMNO, INVESTIGADORES,TIPO_DOCUMENTO) "
					+ " VALUES(?, ?, ?, TO_NUMBER(?,'99'), ?, ?, ?, TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			Object[] parametros = new Object[] { 
				proy.getProyectoId(), proy.getProyectoNombre(), proy.getCodigoPersonal(), proy.getTipo(),
				proy.getLinea(), proy.getCarreraId(), proy.getDepartamento(), proy.getFechaInicio(),
				proy.getFechaFinal(), proy.getResumen(), proy.getEstadoArte(), proy.getDocumento(), proy.getEstado(),
				proy.getFolio(), proy.getAntecedentes(), proy.getJustificacion(), proy.getResDocente(), 
				proy.getResAlumno(), proy.getInvestigadores(), proy.getTipoDocumento()		
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvProyectoDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg( InvProyecto proy) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.INV_PROYECTO "
					+ " SET PROYECTO_NOMBRE = ?,"
					+ " CODIGO_PERSONAL = ?,"
					+ " TIPO = TO_NUMBER(?,'99'),"
					+ " LINEA = ? ,"
					+ " CARRERA_ID = ?,"
					+ " DEPARTAMENTO = ?,"
					+ " FECHA_INICIO = TO_DATE(?,'DD/MM/YYYY'),"
					+ " FECHA_FINAL = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " RESUMEN = ?,"
					+ " ESTADO_ARTE = ?,"
					+ " DOCUMENTO = ?,"
					+ " ESTADO = ?, "
					+ " FOLIO = ?, "	
					+ " ANTECEDENTES = ?, "
					+ " JUSTIFICACION = ?, "
					+ " RES_DOCENTE = ?, "
					+ " RES_ALUMNO = ?,"
					+ " INVESTIGADORES = ?,"
					+ " TIPO_DOCUMENTO = ?"
					+ " WHERE PROYECTO_ID = ? ";			
			
			Object[] parametros = new Object[] { 
				proy.getProyectoNombre(), proy.getCodigoPersonal(), proy.getTipo(),
				proy.getLinea(), proy.getCarreraId(), proy.getDepartamento(), proy.getFechaInicio(),
				proy.getFechaFinal(), proy.getResumen(), proy.getEstadoArte(), proy.getDocumento(), proy.getEstado(),
				proy.getFolio(), proy.getAntecedentes(), proy.getJustificacion(), proy.getResDocente(), 
				proy.getResAlumno(), proy.getInvestigadores(),proy.getTipoDocumento(),
				proy.getProyectoId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvProyectoDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateDocumento( String proyectoId, String documento ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.INV_PROYECTO SET DOCUMENTO = ? WHERE PROYECTO_ID = ? ";
			Object[] parametros = new Object[] { documento, proyectoId };			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvProyectoDao|updateDocumento|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateEstado( String proyectoId, String estado ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.INV_PROYECTO "
					+ " SET ESTADO = ?"					
					+ " WHERE PROYECTO_ID = ? ";
			Object[] parametros = new Object[] { estado, proyectoId };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvProyectoDao|updateEstado|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg( String proyectoId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.INV_PROYECTO WHERE PROYECTO_ID = ? ";
			Object[] parametros = new Object[] { proyectoId };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvProyectoDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public InvProyecto mapeaRegId( String proyectoId) {
		
		InvProyecto inv = new InvProyecto();		
		try{ 
			String comando = "SELECT PROYECTO_ID, PROYECTO_NOMBRE, CODIGO_PERSONAL, TIPO, LINEA,"
					+ " CARRERA_ID, DEPARTAMENTO, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, "
					+ " TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, RESUMEN, ESTADO_ARTE, DOCUMENTO, ESTADO, FOLIO, "
					+ " ANTECEDENTES, JUSTIFICACION, RES_DOCENTE, RES_ALUMNO, INVESTIGADORES,TIPO_DOCUMENTO"
					+ " FROM ENOC.INV_PROYECTO"
					+ " WHERE PROYECTO_ID = ? "; 
			Object[] parametros = new Object[] { proyectoId };
			inv = enocJdbc.queryForObject(comando, new InvProyectoMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvProyectoDao|mapeaRegId|:"+ex);
		}
		
		return inv;
	}
	
	public String maxReg( String year) {
		
		String maximo 	= year+"-001"; 
		try{ 
			String comando = " SELECT COUNT(*) FROM ENOC.INV_PROYECTO WHERE SUBSTR(PROYECTO_ID,1,4)  = ?";
			Object[] parametros = new Object[] { year };
			if(enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				comando = " SELECT COALESCE(TRIM(MAX(SUBSTR(PROYECTO_ID,6,3)+1)), '1') AS MAXIMO FROM ENOC.INV_PROYECTO WHERE SUBSTR(PROYECTO_ID,1,4) = ?";
				maximo = enocJdbc.queryForObject(comando, String.class,parametros);				
				if (maximo.length()==1){ 
					maximo = year+"-00"+maximo;
				}else if (maximo.length()==2){
					maximo = year+"-0"+maximo;
				}else if(maximo.length()==3){
					maximo = year+"-"+maximo;
				}else{
					maximo = year+maximo;
				}				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvProyectoDao|maxReg|:"+ex);
		}
		
		return maximo;
	}
	
	public boolean existeReg( String proyectoId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.INV_PROYECTO WHERE PROYECTO_ID = ? "; 
			if(enocJdbc.queryForObject(comando, Integer.class,proyectoId) >= 1){			
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvProyectoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String getEstado( String proyectoId) {
		
		String estado 			= "-";
		
		try{
			String comando = "SELECT COUNT(ESTADO) FROM ENOC.INV_PROYECTO WHERE PROYECTO_ID = ? ";
			Object[] parametros = new Object[] { proyectoId };
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				comando = "SELECT ESTADO FROM ENOC.INV_PROYECTO WHERE PROYECTO_ID = ? ";
				estado = enocJdbc.queryForObject(comando, String.class);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvProyectoDao|getEstado|:"+ex);
		}
		
		return estado;
	}
	
	public String getNombreProyecto( String proyectoId) {
				
		String estado 			= "-";
		
		try{
			String comando = "SELECT COUNT(PROYECTO_NOMBRE) FROM ENOC.INV_PROYECTO WHERE PROYECTO_ID = ? ";
			Object[] parametros = new Object[] { proyectoId };
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				comando = "SELECT PROYECTO_NOMBRE FROM ENOC.INV_PROYECTO WHERE PROYECTO_ID = ? ";
				estado = enocJdbc.queryForObject(comando, String.class, parametros);
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvProyectoDao|getNombreProyecto|:"+ex);
		}
		
		return estado;
	}

	public List<InvProyecto> lisAll( String orden ) {
		List<InvProyecto> lista	= new ArrayList<InvProyecto>();
		String comando	= "";
		
		try{
			comando = " SELECT PROYECTO_ID, PROYECTO_NOMBRE, CODIGO_PERSONAL, TIPO, LINEA, CARRERA_ID, DEPARTAMENTO,"
					+ " TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL,"
					+ " RESUMEN, ESTADO_ARTE, DOCUMENTO, ESTADO, FOLIO, ANTECEDENTES, JUSTIFICACION, RES_DOCENTE, RES_ALUMNO, INVESTIGADORES, TIPO_DOCUMENTO"
					+ " FROM ENOC.INV_PROYECTO "+orden;
			lista = enocJdbc.query(comando, new InvProyectoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvProyectoDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<InvProyecto> getListProyectosEmpleado( String codigoPersonal, String orden ) {
		
		List<InvProyecto> lista	= new ArrayList<InvProyecto>();	
		String comando	= "";
		
		try{
			comando = " SELECT PROYECTO_ID, PROYECTO_NOMBRE, CODIGO_PERSONAL, TIPO, LINEA, CARRERA_ID, DEPARTAMENTO,"
					+ " TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL,"
					+ " RESUMEN, ESTADO_ARTE, DOCUMENTO, ESTADO, FOLIO, ANTECEDENTES, JUSTIFICACION, RES_DOCENTE, RES_ALUMNO, INVESTIGADORES, TIPO_DOCUMENTO"
					+ " FROM ENOC.INV_PROYECTO WHERE CODIGO_PERSONAL = ? "+orden;
			lista = enocJdbc.query(comando, new InvProyectoMapper(), codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvProyectoDao|getListProyectosEmpleado|:"+ex);
		}		
		return lista;
	}
	
	// Filtra los proyectos que pertenezcan a las facultades del referente
	public List<InvProyecto> getListProyectosReferente( String facultades, String orden ) {
		
		List<InvProyecto> lista	= new ArrayList<InvProyecto>();		
		String comando	= "";
		
		try{
			comando = " SELECT PROYECTO_ID, PROYECTO_NOMBRE, CODIGO_PERSONAL, TIPO, LINEA, CARRERA_ID, DEPARTAMENTO,"
					+ " TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL,"
					+ " RESUMEN, ESTADO_ARTE, DOCUMENTO, ESTADO, FOLIO, ANTECEDENTES, JUSTIFICACION, RES_DOCENTE, RES_ALUMNO, INVESTIGADORES, TIPO_DOCUMENTO"
					+ " FROM ENOC.INV_PROYECTO WHERE ENOC.FACULTAD(CARRERA_ID) IN("+facultades+") "+orden;
			lista = enocJdbc.query(comando, new InvProyectoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvProyectoDao|getListProyectosReferente|:"+ex);
		}
		
		return lista;
	}
	
	// Filtra los proyectos que pertenezcan a las carreras del refrente
	public List<InvProyecto> lisProyectosReferente( String referente, String orden ) {
		
		List<InvProyecto> lista	= new ArrayList<InvProyecto>();		
		String comando	= "";
		
		try{
			comando = " SELECT PROYECTO_ID, PROYECTO_NOMBRE, CODIGO_PERSONAL, TIPO, LINEA, CARRERA_ID, DEPARTAMENTO,"
					+ " TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL,"
					+ " RESUMEN, ESTADO_ARTE, DOCUMENTO, ESTADO, FOLIO, ANTECEDENTES, JUSTIFICACION, RES_DOCENTE, RES_ALUMNO, INVESTIGADORES, TIPO_DOCUMENTO"
					+ " FROM ENOC.INV_PROYECTO WHERE CARRERA_ID IN(SELECT CARRERA_ID FROM INV_REFERENTE WHERE CODIGO_ID =  "+referente+") "+orden;
			lista = enocJdbc.query(comando, new InvProyectoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvProyectoDao|getListProyectosReferente|:"+ex);
		}
		
		return lista;
	}
	
	public List<InvProyecto> getListProyectosCarrera( String carreraId, String codigoId, String orden ) {		
		List<InvProyecto> lista	= new ArrayList<InvProyecto>();	
		try{
			String comando = " SELECT PROYECTO_ID, PROYECTO_NOMBRE, CODIGO_PERSONAL, TIPO, LINEA, CARRERA_ID, DEPARTAMENTO,"
					+ " TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL,"
					+ " RESUMEN, ESTADO_ARTE, DOCUMENTO, ESTADO, FOLIO, ANTECEDENTES, JUSTIFICACION, RES_DOCENTE, RES_ALUMNO, INVESTIGADORES, TIPO_DOCUMENTO"
					+ " FROM ENOC.INV_PROYECTO WHERE CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.INV_REFERENTE WHERE CARRERA_ID = ?"
					+ " AND CODIGO_ID = ? ) "+orden;
			lista = enocJdbc.query(comando, new InvProyectoMapper(), carreraId, codigoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvProyectoDao|getListProyectosCarrera|:"+ex);
		}		
		return lista;
	}
	
}