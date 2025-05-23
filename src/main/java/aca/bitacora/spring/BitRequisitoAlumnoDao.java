package aca.bitacora.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BitRequisitoAlumnoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( BitRequisitoAlumno objeto) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.BIT_REQUISITO_ALUMNO (CODIGO_PERSONAL, TRAMITE_ID, REQUISITO_ID, FECHA, CODIGO_EMPLEADO, ESTADO, FOLIO) VALUES(?,TO_NUMBER(?, '9999'),TO_NUMBER(?, '9999'), ?, ?, ?, ?)";
			Object[] parametros = new Object[] {
				objeto.getCodigoPersonal(), objeto.getTramiteId(), objeto.getRequisitoId(), objeto.getFecha(), objeto.getCodigoEmpleado(),objeto.getEstado(),objeto.getFolio()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitRequisitoAlumnoDao|insertReg|:"+ex);	
		}
		return ok;
	}	
	
	public boolean updateReg( BitRequisitoAlumno objeto) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.BIT_REQUISITO_ALUMNO"
					+ " SET FECHA = ?, CODIGO_EMPLEADO = ?, ESTADO = ?"
					+ " WHERE CODIGO_PERSONAL = ? AND TRAMITE_ID = TO_NUMBER(?, '9999') AND REQUISITO_ID = TO_NUMBER(?, '9999') AND FOLIO = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {
				objeto.getFecha(), objeto.getCodigoEmpleado(), objeto.getEstado() ,objeto.getCodigoPersonal(), objeto.getTramiteId(), objeto.getRequisitoId(),objeto.getFolio()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitRequisitoAlumnoDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg(String codigoPersonal, String tramiteId, String requisitoId, String folio) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.BIT_REQUISITO_ALUMNO WHERE CODIGO_PERSONAL = ? AND TRAMITE_ID = TO_NUMBER(?, '9999') AND REQUISITO_ID = TO_NUMBER(?, '9999') AND FOLIO = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {codigoPersonal,tramiteId,requisitoId,folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitRequisitoAlumnoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean existeReg(String codigoPersonal, String tramiteId, String requisitoId, String folio) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BIT_REQUISITO_ALUMNO WHERE CODIGO_PERSONAL = ? AND TRAMITE_ID = TO_NUMBER(?, '9999') AND REQUISITO_ID = TO_NUMBER(?, '9999') AND FOLIO = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {codigoPersonal,tramiteId,requisitoId,folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitRequisitoAlumnoDao|existeReg|:"+ex);
			ex.printStackTrace();
		}
		return ok;
	}
	
	public boolean existeReg(String codigoPersonal, String tramiteId) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BIT_REQUISITO_ALUMNO WHERE CODIGO_PERSONAL = ? AND TRAMITE_ID = TO_NUMBER(?, '9999')";
			Object[] parametros = new Object[] {codigoPersonal,tramiteId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitRequisitoAlumnoDao|existeReg|:"+ex);
			ex.printStackTrace();
		}
		return ok;
	}
	
	public boolean existeReg(String codigoPersonal, String tramiteId, String folio) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BIT_REQUISITO_ALUMNO WHERE CODIGO_PERSONAL = ? AND TRAMITE_ID = TO_NUMBER(?, '9999') AND FOLIO = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {codigoPersonal,tramiteId,folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitRequisitoAlumnoDao|existeReg|:"+ex);
			ex.printStackTrace();
		}
		return ok;
	}
	
	public int numAutorizados(String codigoPersonal, String tramiteId, String folio){
		int total = 0;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BIT_REQUISITO_ALUMNO WHERE CODIGO_PERSONAL = ? AND TRAMITE_ID = TO_NUMBER(?, '9999') AND FOLIO = TO_NUMBER(?, '99') AND ESTADO = 'A'";
			Object[] parametros = new Object[] {codigoPersonal,tramiteId, folio};
			total = enocJdbc.queryForObject(comando,Integer.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitRequisitoAlumnoDao|numAutorizados|:"+ex);
			ex.printStackTrace();
		}
		return total;
	}
	
	public BitRequisitoAlumno mapeaRegId(String codigoPersonal, String tramiteId, String requisitoId, String folio) {
		BitRequisitoAlumno objeto = new BitRequisitoAlumno();		
		try{
			String comando = " SELECT CODIGO_PERSONAL,TRAMITE_ID,REQUISITO_ID, FECHA, CODIGO_EMPLEADO, ESTADO, FOLIO"
						+ " FROM ENOC.BIT_REQUISITO_ALUMNO WHERE CODIGO_PERSONAL = ? AND TRAMITE_ID = TO_NUMBER(?, '9999') AND REQUISITO_ID = TO_NUMBER(?, '9999') AND FOLIO = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {codigoPersonal,tramiteId,requisitoId,folio};
			objeto = enocJdbc.queryForObject(comando, new BitRequisitoAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitRequisitoAlumnoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return objeto;
	}
	
	public List<BitRequisitoAlumno> lisRequisitos( String orden) {		
		List<BitRequisitoAlumno> lista = new ArrayList<BitRequisitoAlumno>();		
		try{
			String comando = " SELECT CODIGO_PERSONAL, TRAMITE_ID, REQUISITO_ID, FECHA, CODIGO_EMPLEADO, ESTADO, FOLIO"
					+ " FROM BIT_REQUISITO_ALUMNO "+orden;
			lista = enocJdbc.query(comando, new BitRequisitoAlumnoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitRequisitoAlumnoDao|lisRequisitos|:"+ex);
		}
		return lista;
	}	
	
	public HashMap<String, BitRequisitoAlumno> mapaBitRequisitoAlumno(String codigoPersonal, String tramiteId){
		HashMap<String, BitRequisitoAlumno> mapa = new HashMap<String, BitRequisitoAlumno>();
		List<BitRequisitoAlumno> lista			 = new ArrayList<BitRequisitoAlumno>();		
		try{
			String comando = " SELECT CODIGO_PERSONAL, TRAMITE_ID, REQUISITO_ID, FECHA, CODIGO_EMPLEADO, ESTADO, FOLIO"
					+ " FROM ENOC.BIT_REQUISITO_ALUMNO WHERE CODIGO_PERSONAL = ? AND TRAMITE_ID = TO_NUMBER(?, '9999')";
			Object[] parametros = new Object[] {codigoPersonal,tramiteId};
			lista = enocJdbc.query(comando,new BitRequisitoAlumnoMapper(),parametros);
			for(BitRequisitoAlumno objeto : lista){				
				mapa.put(objeto.getCodigoPersonal()+objeto.getTramiteId()+objeto.getRequisitoId()+objeto.getFolio(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitRequisitoAlumnoDao|mapaBitRequisitoAlumno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaBitRequisitosCumpleAlumno(String codigoPersonal, String estado){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT TRAMITE_ID||'-'||FOLIO AS LLAVE, COUNT(TRAMITE_ID) AS VALOR FROM ENOC.BIT_REQUISITO_ALUMNO WHERE CODIGO_PERSONAL = ? AND ESTADO = ?"
					+ " AND TRAMITE_ID||REQUISITO_ID IN (SELECT TRAMITE_ID||REQUISITO_ID FROM ENOC.BIT_TRAMITE_REQUISITO WHERE TRAMITE_ID IN(SELECT TRAMITE_ID FROM ENOC.BIT_SOLICITUD WHERE CODIGO_PERSONAL = ?))"
					+ " GROUP BY TRAMITE_ID, FOLIO";
			Object[] parametros = new Object[] {codigoPersonal,estado,codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(),parametros);
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitRequisitoAlumnoDao|mapaBitRequisitosCumpleAlumno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaBitRequisitosCumple(String estado){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT CODIGO_PERSONAL||TRAMITE_ID||FOLIO AS LLAVE, COUNT(TRAMITE_ID) AS VALOR FROM ENOC.BIT_REQUISITO_ALUMNO WHERE ESTADO = ?"
					+ " AND TRAMITE_ID||REQUISITO_ID IN (SELECT TRAMITE_ID||REQUISITO_ID FROM ENOC.BIT_TRAMITE_REQUISITO WHERE TRAMITE_ID IN(SELECT TRAMITE_ID FROM ENOC.BIT_SOLICITUD))"
					+ " GROUP BY CODIGO_PERSONAL,TRAMITE_ID,FOLIO";
			Object[] parametros = new Object[] {estado};
			lista = enocJdbc.query(comando, new aca.MapaMapper(),parametros);
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitRequisitoAlumnoDao|mapaBitRequisitosCumple|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaBitRequisitosPorAlumno(String codigoPersonal, String estado) {		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL||TRAMITE_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.BIT_REQUISITO_ALUMNO WHERE CODIGO_PERSONAL = ? AND ESTADO = ? GROUP BY CODIGO_PERSONAL,TRAMITE_ID";
			Object[] parametros = new Object[] {codigoPersonal,estado};
			lista = enocJdbc.query(comando, new aca.MapaMapper(),parametros);
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitRequisitoAlumnoDao|mapaBitRequisitosPorAlumno|:"+ex);
		}
		
		return mapa;
	}
}
