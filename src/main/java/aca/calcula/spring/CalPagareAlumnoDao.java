// Clase Util para la tabla de Cat_Area
package aca.calcula.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CalPagareAlumnoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	
	public boolean insertReg(CalPagareAlumno pagare){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.CAL_PAGARE_ALUMNO(CODIGO_PERSONAL, PAGARE_ID, PAGARE_NOMBRE, FECHA, CARGA_ID, BLOQUE_ID, IMPORTE)"
					+ " VALUES( ?, TO_NUMBER(?,'9999'), ?, TO_DATE(?,'DD/MM/YYYY'), ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'99999999.99'))";
			Object[] parametros = new Object[] {pagare.getCodigoPersonal(), pagare.getPagareId(), pagare.getPagareNombre(), pagare.getFecha(), pagare.getCargaId(), pagare.getBloqueId(), pagare.getImporte()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalPagareAlumnoDao|insertReg|:"+ex);
		}
		
		return ok;
	}	
	
	public boolean updateReg(CalPagareAlumno pagare) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CAL_PAGARE_ALUMNO SET PAGARE_NOMBRE = ?, FECHA = TO_DATE(?,'DD/MM/YYYY'), CARGA_ID = ?, BLOQUE_ID = TO_NUMBER(?,'99'), IMPORTE = TO_NUMBER(?,'99999999.99')"
					+ " WHERE CODIGO_PERSONAL = ? AND PAGARE_ID = TO_NUMBER(?,'9999')";
			Object[] parametros = new Object[] {pagare.getPagareNombre(), pagare.getFecha(), pagare.getCargaId(), pagare.getBloqueId(), pagare.getImporte(), pagare.getCodigoPersonal(), pagare.getPagareId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalPagareAlumnoDao|updateReg|:"+ex);
		}
		
		return ok;
	}	
	
	public boolean deleteReg(String codigoPersonal, String cargaId, String bloqueId){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CAL_PAGARE_ALUMNO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal,cargaId,bloqueId};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalPagareAlumnoDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public CalPagareAlumno mapeaRegId(String codigoPersonal, String pagareId) {
		
		CalPagareAlumno pagare = new CalPagareAlumno();
		try{
			String comando = "SELECT CODIGO_PERSONAL, PAGARE_ID, PAGARE_NOMBRE, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, CARGA_ID, BLOQUE_ID, IMPORTE"
					+ " FROM ENOC.CAL_PAGARE_ALUMNO "
					+ " WHERE CODIGO_PERSONAL = ? AND PAGARE_ID = TO_NUMBER(?,'9999')";
			Object[] parametros = new Object[] {codigoPersonal, pagareId};		
			pagare = enocJdbc.queryForObject(comando, new CalPagareAlumnoMapper(), parametros);	
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalPagareAlumnoDao|mapeaRegId|:"+ex);
		}
		
		return pagare;
	}
	
	public boolean existeReg(String codigoPersonal, String cargaId, String bloqueId){
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAL_PAGARE_ALUMNO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal,cargaId,bloqueId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalPagareAlumnoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existePagareReg(String codigoPersonal, String pagareId) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAL_PAGARE_ALUMNO WHERE CODIGO_PERSONAL = ? AND PAGARE_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal,pagareId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalPagareAlumnoDao|existePagareReg|:"+ex);
		}
		
		return ok;
	}
	
	public String getPagareNombre(String codigoPersonal, String pagareId) {		
		String nombre			= "X";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAL_PAGARE_ALUMNO WHERE CODIGO_PERSONAL = ? AND PAGARE_ID = TO_NUMBER(?,'99')"; 
			Object[] parametros = new Object[] {codigoPersonal, pagareId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				comando = "SELECT PAGARE_NOMBRE FROM ENOC.CAL_PAGARE_ALUMNO WHERE CODIGO_PERSONAL = ? AND PAGARE_ID = TO_NUMBER(?,'99')";
				nombre = enocJdbc.queryForObject(comando, String.class,parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalPagareAlumnoDao|getNivelNombre|:"+ex);
		}		
		return nombre;
	}
		
	public List<CalPagareAlumno> lisTodos(String orden ) {
		
		List<CalPagareAlumno> lista		= new ArrayList<CalPagareAlumno>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PAGARE_ID, PAGARE_NOMBRE, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, CARGA_ID, BLOQUE_ID, IMPORTE FROM ENOC.CAL_PAGARE_ALUMNO "+ orden; 
			lista = enocJdbc.query(comando, new CalPagareAlumnoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalPagareAlumnoDao|lisTodos|:"+ex);	
		}		
		return lista;
	}
	
	public List<CalPagareAlumno> lisPorCarga(String cargaId, String bloqueId, String orden ) {		
		List<CalPagareAlumno> lista		= new ArrayList<CalPagareAlumno>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PAGARE_ID, PAGARE_NOMBRE, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, CARGA_ID, BLOQUE_ID, IMPORTE FROM ENOC.CAL_PAGARE_ALUMNO"
					+ " WHERE CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99') "+ orden;
			lista = enocJdbc.query(comando, new CalPagareAlumnoMapper(), cargaId, bloqueId);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalPagareAlumnoDao|lisPorCarga|:"+ex);	
		}		
		return lista;
	}
	
	public HashMap<String,CalPagareAlumno> mapaTodos(String orden ) {
		
		List<CalPagareAlumno> lista		= new ArrayList<CalPagareAlumno>();
		HashMap<String,CalPagareAlumno> mapa = new HashMap<String,CalPagareAlumno>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PAGARE_ID, PAGARE_NOMBRE, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, CARGA_ID, BLOQUE_ID, IMPORTE FROM ENOC.CAL_PAGARE_ALUMNO "+ orden;
			lista = enocJdbc.query(comando, new CalPagareAlumnoMapper());
			for(CalPagareAlumno pagare: lista){
				mapa.put(pagare.getPagareId(), pagare);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalPagareAlumnoDao|mapaTodos|:"+ex);
		}
		
		return mapa;
	}

}