package aca.alumno.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumDocumentoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AlumDocumento objeto) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ALUM_DOCUMENTO"
					+ " (CODIGO_PERSONAL, FOLIO, PLAN_ID, ARCHIVO, FECHA, ESTADO, NOMBRE, TIPO, FECHA_CREA)"
					+ " VALUES( ?, TO_NUMBER(?,'999'), ?, ?, NOW(), ?, ?, TO_NUMBER(?,'99'), NOW())";
			Object[] parametros = new Object[] {
				objeto.getCodigoPersonal(),objeto.getFolio(),objeto.getPlanId(),objeto.getArchivo(),objeto.getEstado(),objeto.getNombre(),objeto.getTipo()
			};

			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDocumentoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateEstado(String codigoPersonal, String folio, String estado) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ALUM_DOCUMENTO SET ESTADO = ?, FECHA = NOW() WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {estado, codigoPersonal, folio};
			if (enocJdbc.update(comando,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDocumentoDao|updateEstado|:"+ex);
		}		
		return ok;
	}
	
	public boolean deleteReg(String codigoPersonal, String folio) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ALUM_DOCUMENTO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {codigoPersonal, folio};
			if (enocJdbc.update(comando,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDocumentoDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public AlumDocumento mapeaRegId(String codigoPersonal, String folio) {
		AlumDocumento objeto = new AlumDocumento();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, PLAN_ID, ARCHIVO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO, NOMBRE, TIPO, FECHA_CREA"
					+ " FROM ENOC.ALUM_DOCUMENTO"
					+ " WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {codigoPersonal, folio};
			objeto = enocJdbc.queryForObject(comando, new AlumDocumentoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDocumentoDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String codigoPersonal, String folio) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_DOCUMENTO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {codigoPersonal, folio};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDocumentoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg(String codigoPersonal) {
		String maximo = "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) MAXIMO FROM ENOC.ALUM_DOCUMENTO WHERE CODIGO_PERSONAL = ? ";
			
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDocumentoDao|maximoReg|:"+ex);
		}
		return maximo;		
	}
	
	public List<AlumDocumento> lisTodos(String orden) {
		
		List<AlumDocumento> lista = new ArrayList<AlumDocumento>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, PLAN_ID, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA, ESTADO, NOMBRE, TIPO, TO_CHAR(FECHA_CREA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_CREA"
				+ " FROM ENOC.ALUM_DOCUMENTO "+orden;
			lista = enocJdbc.query(comando, new AlumDocumentoMapperCorto());			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDocumentoDao|lisTodos|:"+ex);
		}		
		return lista;
	}

	public List<AlumDocumento> lisTodosPorTipo(String tipo, String orden) {
		
		List<AlumDocumento> lista = new ArrayList<AlumDocumento>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, PLAN_ID, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA, ESTADO, NOMBRE, TIPO, TO_CHAR(FECHA_CREA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_CREA"
					+ " FROM ENOC.ALUM_DOCUMENTO WHERE TIPO = TO_NUMBER(?,'99') "+orden;
			Object[] parametros = new Object[] {tipo};
			lista = enocJdbc.query(comando, new AlumDocumentoMapperCorto(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDocumentoDao|lisTodosPorTipo|:"+ex);
		}		
		return lista;
	}
	
	public List<AlumDocumento> lisTodosPorTipo(String tipo, String estados, String fechaIni, String fechaFin, String orden) {
		
		List<AlumDocumento> lista = new ArrayList<AlumDocumento>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, PLAN_ID, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA, ESTADO, NOMBRE, TIPO, TO_CHAR(FECHA_CREA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_CREA"
					+ " FROM ENOC.ALUM_DOCUMENTO "
					+ " WHERE TIPO = TO_NUMBER(?,'99') "
					+ " AND ESTADO IN ("+estados+")"
					+ " AND ENOC.ALUM_DOCUMENTO.FECHA_CREA BETWEEN TO_DATE(?,'DD/MM/YYYY HH24:MI:SS') AND TO_DATE(?,'DD/MM/YYYY HH24:MI:SS')"+ orden;
			Object[] parametros = new Object[] {tipo, fechaIni, fechaFin};
			lista = enocJdbc.query(comando, new AlumDocumentoMapperCorto(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDocumentoDao|lisTodosPorTipo|:"+ex);
		}		
		return lista;
	}
	
	public List<AlumDocumento> lisSolicitudesSeguro(String tipos) {
		
		List<AlumDocumento> lista = new ArrayList<AlumDocumento>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, PLAN_ID, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA, ESTADO, NOMBRE, TIPO, TO_CHAR(FECHA_CREA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_CREA"
				+ " FROM ENOC.ALUM_DOCUMENTO WHERE TIPO IN ("+tipos+")";
			
			lista = enocJdbc.query(comando, new AlumDocumentoMapperCorto());			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDocumentoDao|lisSolicitudesSeguro|:"+ex);
		}		
		return lista;
	}
	
	public List<AlumDocumento> lisSolicitudesSeguro(String fechaIni, String fechaFin, String tipos) {
		
		List<AlumDocumento> lista = new ArrayList<AlumDocumento>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, PLAN_ID, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA, ESTADO, NOMBRE, TIPO, TO_CHAR(FECHA_CREA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_CREA"
				+ " FROM ENOC.ALUM_DOCUMENTO"
				+ " WHERE FECHA_CREA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"
				+ " AND TIPO IN ("+tipos+")";
			
			lista = enocJdbc.query(comando, new AlumDocumentoMapperCorto(), fechaIni, fechaFin);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDocumentoDao|lisSolicitudesSeguro|:"+ex);
		}		
		return lista;
	}

	public List<AlumDocumento> lisAlumDocumento(String codigoPersonal) {
		
		List<AlumDocumento> lista = new ArrayList<AlumDocumento>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, PLAN_ID, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, ESTADO, NOMBRE, TIPO, TO_CHAR(FECHA_CREA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_CREA"
					+ " FROM ENOC.ALUM_DOCUMENTO WHERE CODIGO_PERSONAL = ? AND TIPO = 1";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new AlumDocumentoMapperCorto(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDocumentoDao|lisAlumDocumento|:"+ex);
		}		
		return lista;
	}

	public List<AlumDocumento> lisAlumDocumentoFormato(String codigoPersonal) {
		
		List<AlumDocumento> lista = new ArrayList<AlumDocumento>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, PLAN_ID, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, ESTADO, NOMBRE, TIPO, TO_CHAR(FECHA_CREA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_CREA"
					+ " FROM ENOC.ALUM_DOCUMENTO WHERE CODIGO_PERSONAL = ? AND TIPO != 1";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new AlumDocumentoMapperCorto(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDocumentoDao|lisAlumDocumentoFormato|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String, AlumDocumento> mapaAlumDocumento() {
		HashMap<String,AlumDocumento> mapa	= new HashMap<String, AlumDocumento>();
		List<AlumDocumento>	lista		= new ArrayList<AlumDocumento>();		
		try{
			String comando ="SELECT CODIGO_PERSONAL, FOLIO, PLAN_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO, NOMBRE, TIPO, TO_CHAR(FECHA_CREA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_CREA"
					+ " FROM ENOC.ALUM_DOCUMENTO ";
			lista = enocJdbc.query(comando, new AlumDocumentoMapperCorto());
			for (AlumDocumento objeto : lista){
				mapa.put(objeto.getCodigoPersonal(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDocumentoDao|mapaAlumDocumento|:"+ex);
		}		
		return mapa;
	}
	
}
