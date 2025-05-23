package aca.financiero.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FinMaratumDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( FinMaratum objeto ) {
		boolean ok = false;		
		try{
			String comando ="INSERT INTO ENOC.FIN_MARATUM" + 
					" (ID, CODIGO_PERSONAL, PLAN_ID, FECHA, ARCHIVO, NOMBRE, USUARIO, ESTADO)" +
					" VALUES(TO_NUMBER(?, '9999999'), ?, ?, SYSDATE, ?, ?, ?, ?)";
			Object[] parametros = new Object[] { objeto.getId(), objeto.getCodigoPersonal(), objeto.getPlanId(), objeto.getArchivo(), objeto.getNombre(), objeto.getUsuario(),objeto.getEstado() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinMaratumDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg( FinMaratum objeto ) {
		boolean ok = false;		
		try{
			String comando ="UPDATE ENOC.FIN_MARATUM"
				+ " SET CODIGO_PERSONAL = ?,"
				+ " PLAN_ID = ?,"
				+ " FECHA = SYSDATE,"
				+ " ARCHIVO = ?,"
				+ " NOMBRE = ?,"
				+ " USUARIO = ?,"
				+ " ESTADO 	= ?"
				+ " WHERE ID = TO_NUMBER(?, '9999999')";
			Object[] parametros = new Object[] { objeto.getCodigoPersonal(), objeto.getPlanId(), objeto.getArchivo(),objeto.getNombre(),objeto.getUsuario(),objeto.getEstado(),objeto.getId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinMaratumDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateEstado(String id, String estado) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.FIN_MARATUM SET ESTADO = ? WHERE ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {estado, id};
			if (enocJdbc.update(comando,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinMaratumDao|updateEstado|:"+ex);
		}		
		return ok;
	}
	
	public boolean deleteReg( String id) {
		
		boolean ok = false;		
		try{
			String comando ="DELETE FROM ENOC.FIN_MARATUM WHERE ID = TO_NUMBER(?, '9999999')";
			Object[] parametros = new Object[] { id};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinMaratumDao|deleteReg|:"+ex);
		}		
		return ok;
	}
	
	public FinMaratum mapeaRegId(String id) {
		
		FinMaratum com = new FinMaratum();		
		try{
			String comando = "SELECT COUNT(ID) FROM ENOC.FIN_MARATUM WHERE ID = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] { id };
			if (enocJdbc.queryForObject(comando, Integer.class,parametros)>=1){
				comando = " SELECT ID, CODIGO_PERSONAL, PLAN_ID, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, ARCHIVO, NOMBRE, USUARIO, ESTADO"
						+ " FROM ENOC.FIN_MARATUM"
						+ " WHERE ID = TO_NUMBER(?,'9999999')";				
				com = enocJdbc.queryForObject(comando, new FinMaratumMapper(),parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinMaratumDao|mapeaRegId|:"+ex);
		}
		
		return com;
	}
	
	public boolean existeReg( String id) {
		boolean ok 			= false;		
		try{
			String comando ="SELECT COUNT(ID) FROM ENOC.FIN_MARATUM WHERE ID = TO_NUMBER(?, '9999999')";
			Object[] parametros = new Object[] { id };	
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinMaratumDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg( ) {
		int maximo 	= 1;		
		try{
			String comando ="SELECT COALESCE(MAX(ID)+1,1) AS MAXIMO FROM ENOC.FIN_MARATUM";					
			maximo = enocJdbc.queryForObject(comando, Integer.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinMaratumDao|maximoReg|:"+ex);
		}		
		return String.valueOf(maximo);
	}	
	
	public List<FinMaratum> lisPorFechas( String fechaIni, String fechaFin, String estado, String orden ) {
		
		List<FinMaratum> lista = new ArrayList<FinMaratum>();		
		try{
			String comando = " SELECT ID, CODIGO_PERSONAL, PLAN_ID, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, NOMBRE, USUARIO, ESTADO"
					+ " FROM ENOC.FIN_MARATUM"
					+ " WHERE FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') "+ orden;
			Object[] parametros = new Object[] { fechaIni, fechaFin };
			lista = enocJdbc.query(comando, new FinMaratumMapperCorto(),parametros);	
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinMaratumDao|lisPorUsuario|:"+ex);
		}
		
		return lista;
	}
	
	public List<FinMaratum> lisPorEstados( String estado, String orden ) {		
		List<FinMaratum> lista = new ArrayList<FinMaratum>();		
		try{
			String comando = " SELECT ID, CODIGO_PERSONAL, PLAN_ID, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, NOMBRE, USUARIO, ESTADO"
					+ " FROM ENOC.FIN_MARATUM"
					+ " WHERE ESTADO IN ("+estado+") "+ orden;			
			lista = enocJdbc.query(comando, new FinMaratumMapperCorto());	
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinMaratumDao|lisPorEstados|:"+ex);
		}
		
		return lista;
	}
	
	public List<FinMaratum> lisPorUsuario( String usuario, String estado, String orden ) {
		
		List<FinMaratum> lista = new ArrayList<FinMaratum>();		
		try{
			String comando = " SELECT ID, CODIGO_PERSONAL, PLAN_ID, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, NOMBRE, USUARIO, ESTADO"
					+ " FROM ENOC.FIN_MARATUM"
					+ " WHERE USUARIO = ?"
					+ " AND ESTADO IN ("+estado+") "+ orden;
			Object[] parametros = new Object[] { usuario };
			lista = enocJdbc.query(comando, new FinMaratumMapperCorto(),parametros);	
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinMaratumDao|lisPorUsuario|:"+ex);
		}
		
		return lista;
	}
	
	public List<FinMaratum> lisPorAccesos( String carreras, String estado, String orden ) {		
		List<FinMaratum> lista = new ArrayList<FinMaratum>();		
		try{
			String comando = " SELECT ID, CODIGO_PERSONAL, PLAN_ID, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, NOMBRE, USUARIO, ESTADO"
					+ " FROM ENOC.FIN_MARATUM"
					+ " WHERE INSTR(?,ENOC.CARRERA(PLAN_ID)) >= 1"
					+ " AND ESTADO IN ("+estado+") "+ orden;
			Object[] parametros = new Object[] { carreras };
			lista = enocJdbc.query(comando, new FinMaratumMapperCorto(),parametros);	
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinMaratumDao|lisPorAccesos|:"+ex);
		}		
		return lista;
	}
	
}
