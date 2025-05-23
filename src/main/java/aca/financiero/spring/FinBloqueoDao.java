package aca.financiero.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FinBloqueoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(FinBloqueo bloqueo){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.FIN_BLOQUEO" + 
					" (PERIODO_ID, CODIGO_PERSONAL, FECHA, ESTADO, USUARIO)" +
					" VALUES(TO_NUMBER(?, '99'), ?, SYSDATE, ?, ?)";			
			Object[] parametros = new Object[] {
				bloqueo.getPeriodoId(), bloqueo.getCodigoPersonal(), bloqueo.getEstado(), bloqueo.getUsuario()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinBloqueoDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg(FinBloqueo bloqueo ){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.FIN_BLOQUEO SET FECHA = SYSDATE , ESTADO = ?, USUARIO = ? WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = TO_NUMBER(?, '99')";
			
			Object[] parametros = new Object[] {
				bloqueo.getEstado(), bloqueo.getUsuario(), bloqueo.getCodigoPersonal(), bloqueo.getPeriodoId()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinBloqueoDao|updateReg|:"+ex);
		}

		return ok;
	}
	
	public boolean deleteReg(String codigoPersonal, String periodoId){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.FIN_BLOQUEO WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = TO_NUMBER(?, '99')";
			
			Object[] parametros = new Object[] {
					codigoPersonal,periodoId
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinBloqueoDao|deleteReg|:"+ex);
		}

		return ok;
	}
	
	public FinBloqueo mapeaRegId(String codigoPersonal, String periodoId){
		FinBloqueo periodo = new FinBloqueo(); 
		
		try{
			String comando = "SELECT PERIODO_ID, CODIGO_PERSONAL, FECHA, ESTADO, USUARIO FROM ENOC.FIN_BLOQUEO WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = TO_NUMBER(?, '99')";			
			Object[] parametros = new Object[] {codigoPersonal,periodoId};
			periodo = enocJdbc.queryForObject(comando, new FinBloqueoMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinBloqueoDao|mapeaRegId|:"+ex);
		}
		
		return periodo;
	}
	
	public boolean existeReg(String codigoPersonal, String periodoId){
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.FIN_BLOQUEO WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = TO_NUMBER(?, '99')";
			
			Object[] parametros = new Object[] {codigoPersonal, periodoId};	
			
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinBloqueoDao|existeReg|:"+ex);
		}
		
		return ok;
	}	

	public List<FinBloqueo> lisTodos(String orden ){
		
		List<FinBloqueo> lisAlumno	= new ArrayList<FinBloqueo>();		
		try{
			String comando = "SELECT PERIODO_ID, CODIGO_PERSONAL, FECHA, ESTADO, USUARIO FROM ENOC.FIN_BLOQUEO "+orden;			
			lisAlumno = enocJdbc.query(comando, new FinBloqueoMapper());		
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinBloqueoDao|getListAll|:"+ex);
		}
		
		return lisAlumno;
	}
	
	public HashMap<String, String> mapaBloqueados(){
		List<aca.Mapa> list 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa 	= new HashMap<String,String>();		
		try{
			String comando = "SELECT PERIODO_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.FIN_BLOQUEO GROUP BY PERIODO_ID";
			list = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa alumno : list ) {
				mapa.put(alumno.getLlave(), alumno.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinBloqueoDao|mapaBloqueados|:"+ex);
		}		
		return mapa;
	}
}
