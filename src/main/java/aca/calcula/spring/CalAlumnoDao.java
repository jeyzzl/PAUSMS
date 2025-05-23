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
public class CalAlumnoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	
	public boolean insertReg(CalAlumno calculo){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.CAL_ALUMNO(CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, NUM_PAGARE, CONFIRMAR, FECHA, COBRO_MATRICULA, SALDO, PORCENTAJE)"
					+ " VALUES( ?, ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'9'), ?, SYSDATE, ?, TO_NUMBER(?,'99999999.99'), TO_NUMBER(?,'999.99'))";
			Object[] parametros = new Object[] {calculo.getCodigoPersonal(), calculo.getCargaId(), calculo.getBloqueId(), calculo.getNumPagare(),calculo.getConfirmar(), calculo.getCobroMatricula(), calculo.getSaldo(), calculo.getPorcentaje() };	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalAlumnoDao|insertReg|:"+ex);
		}		
		return ok;
	}	
	
	public boolean updateReg(CalAlumno calculo) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CAL_ALUMNO SET NUM_PAGARE = TO_NUMBER(?,'9'), CONFIRMAR = ?, FECHA = SYSDATE, COBRO_MATRICULA = ?, SALDO = TO_NUMBER(?,'999999999.99'), PORCENTAJE = TO_NUMBER(?,'99999.99')"
					+ " WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {calculo.getNumPagare(), calculo.getConfirmar(), calculo.getCobroMatricula(), calculo.getSaldo(), calculo.getPorcentaje(), calculo.getCodigoPersonal(), calculo.getCargaId(), calculo.getBloqueId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalAlumnoDao|updateReg|:"+ex);
		}		
		return ok;
	}	
	
	public boolean updateConfirmar(String codigoPersonal, String cargaId, String bloqueId, String confirmar){
		boolean ok = false;
		
		try{
			
			String comando = "UPDATE ENOC.CAL_ALUMNO SET CONFIRMAR = ? WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";
			
 			if (enocJdbc.update(comando,confirmar,codigoPersonal,cargaId,bloqueId)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.calcula.spring.CalAlumnoDao|updateConfirmar|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg(String codigoPersonal, String cargaId, String bloqueId){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CAL_ALUMNO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, cargaId, bloqueId};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalAlumnoDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public CalAlumno mapeaRegId(String codigoPersonal, String cargaId, String bloqueId) {
		
		CalAlumno calculo = new CalAlumno();
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, NUM_PAGARE, CONFIRMAR, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, COBRO_MATRICULA, SALDO, PORCENTAJE FROM ENOC.CAL_ALUMNO "
					+ " WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, cargaId, bloqueId};		
			calculo = enocJdbc.queryForObject(comando, new CalAlumnoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalAlumnoDao|mapeaRegId|:"+ex);						
		}		
		return calculo;
	}
	
	public boolean existeReg(String codigoPersonal, String cargaId, String bloqueId) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAL_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, cargaId, bloqueId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalAlumnoDao|existeReg|:"+ex);
		}		
		return ok;
	}	
		
	public List<CalAlumno> lisTodos(String orden ) {
		
		List<CalAlumno> lista		= new ArrayList<CalAlumno>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, NUM_PAGARE, CONFIRMAR, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, COBRO_MATRICULA, SALDO, PORCENTAJE FROM ENOC.CAL_ALUMNO "+ orden; 
			lista = enocJdbc.query(comando, new CalAlumnoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalAlumnoDao|lisTodos|:"+ex);	
		}		
		return lista;
	}
	
	public HashMap<String,CalAlumno> mapaTodos(String orden ) {
		
		List<CalAlumno> lista		= new ArrayList<CalAlumno>();
		HashMap<String,CalAlumno> mapa = new HashMap<String,CalAlumno>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, NUM_PAGARE, CONFIRMAR, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, COBRO_MATRICULA, SALDO, PORCENTAJE FROM ENOC.CAL_ALUMNO "+ orden;
			lista = enocJdbc.query(comando, new CalAlumnoMapper());
			for(CalAlumno calculo: lista){
				mapa.put(calculo.getCodigoPersonal()+calculo.getCargaId()+calculo.getBloqueId(), calculo);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalAlumnoDao|mapaTodos|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaPagares(String cargaId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista		 = new ArrayList<aca.Mapa>();	
		
		try{
			String comando = "SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID AS LLAVE, NUM_PAGARE VALOR FROM ENOC.CAL_ALUMNO WHERE CARGA_ID = ?";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalAlumnoDao|mapaPagares|:"+ex);
		}		
		return mapa;
	}

}