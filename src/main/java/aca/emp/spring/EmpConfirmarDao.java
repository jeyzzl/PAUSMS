// Clase Util para la tabla de Carga
package aca.emp.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmpConfirmarDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( EmpConfirmar horas ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.EMP_CONFIRMAR(CODIGO_PERSONAL, FOLIO, USUARIO,FECHA)"
					+ " VALUES( ?, TO_NUMBER(?,'999'), ?, SYSDATE )";
			Object[] parametros = new Object[] {
					horas.getCodigoPersonal(), horas.getFolio(), horas.getUsuario()	
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpConfirmarDao|insertReg|:"+ex);			
		}		
		return ok;
	}
	
	public boolean updateReg( EmpConfirmar horas ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.EMP_CONFIRMAR SET "
				+ " USUARIO = ?,"
				+ " FECHA = SYSDATE"				
				+ " WHERE CODIGO_PERSONAL = ?"
				+ " AND FOLIO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {
					horas.getUsuario(),horas.getCodigoPersonal(), horas.getFolio()					
		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpConfirmarDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg( String codigoPersonal, String folio ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.EMP_CONFIRMAR WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {codigoPersonal, folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpConfirmarDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public EmpConfirmar mapeaRegId(  String codigoPersonal, String folio ) {
		
		EmpConfirmar objeto = new EmpConfirmar();
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, USUARIO, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA FROM ENOC.EMP_CONFIRMAR"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND FOLIO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {codigoPersonal, folio};
			objeto = enocJdbc.queryForObject(comando, new EmpConfirmarMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpConfirmarDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg( String codigoPersonal, String folio) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EMP_CONFIRMAR WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {codigoPersonal, folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpConfirmarDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<EmpConfirmar> lisTodos( String orden ) {
		
		List<EmpConfirmar> lista	= new ArrayList<EmpConfirmar>();	
		try{
			String comando = " SELECT CODIGO_PERSONAL, FOLIO, USUARIO, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA FROM ENOC.EMP_CONFIRMAR "+orden; 
			lista = enocJdbc.query(comando, new EmpConfirmarMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpConfirmarDao|listAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,EmpConfirmar> mapaPorEmpleado(String codigoEmpleado){
		
		HashMap<String,EmpConfirmar> mapa = new HashMap<String,EmpConfirmar>();
		List<EmpConfirmar> lista		= new ArrayList<EmpConfirmar>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, USUARIO, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA FROM ENOC.EMP_CONFIRMAR WHERE CODIGO_PERSONAL = ?";			
			lista = enocJdbc.query(comando, new EmpConfirmarMapper(), codigoEmpleado);
			for (EmpConfirmar map : lista ) {
				mapa.put(map.getCodigoPersonal()+map.getFolio(), map);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpConfirmarDao|mapaPorEmpleado|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,EmpConfirmar> mapaPorCarga(String cargaId){
		
		HashMap<String,EmpConfirmar> mapa = new HashMap<String,EmpConfirmar>();
		List<EmpConfirmar> lista		= new ArrayList<EmpConfirmar>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, USUARIO, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA FROM ENOC.EMP_CONFIRMAR"
					+ " WHERE CODIGO_PERSONAL||FOLIO IN (SELECT CODIGO_PERSONAL||FOLIO FROM ENOC.EMP_HORAS WHERE CARGA_ID = ?)";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new EmpConfirmarMapper(), parametros);
			for (EmpConfirmar map : lista ) {
				mapa.put(map.getCodigoPersonal()+map.getFolio(), map);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpConfirmarDao|mapaPorCarga|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaPorCargayTipo(String cargaId, String tipo){		
		HashMap<String,String> mapa 	= new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CARRERA_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.EMP_HORAS"
					+ " WHERE CARGA_ID = ? AND TIPO = ? AND CODIGO_PERSONAL||FOLIO IN (SELECT CODIGO_PERSONAL||FOLIO FROM ENOC.EMP_CONFIRMAR)"
					+ " GROUP BY CARRERA_ID";
			Object[] parametros = new Object[] {cargaId,tipo};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpConfirmarDao|mapaPorCargayTipo|:"+ex);
		}
		
		return mapa;
	}	
}