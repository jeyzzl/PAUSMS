package aca.acceso.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AccesoConfirmarDao {
	
	@Autowired	
	@Qualifier("jdbcEnoc")	
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AccesoConfirmar objeto){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.ACCESO_CONFIRMAR(CODIGO_PERSONAL, MODALIDAD_ID)" +
					" VALUES(?,TO_NUMBER(?,'99'))";
			Object[] parametros = new Object[] {objeto.getCodigoPersonal(), objeto.getModalidadId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoConfirmarDao|insertReg|:"+ex);
		}
		
		return ok;
	}

	public boolean updateReg(AccesoConfirmar objeto, String moduloId){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ACCESO_CONFIRMAR SET MODALIDAD_ID = TO_NUMBER(?,'99') WHERE CODIGO_PERSONAL = ? AND MODULO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {moduloId, objeto.getCodigoPersonal(),objeto.getModalidadId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoConfirmarDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg(String codigoPersonal, String moduloId){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ACCESO_CONFIRMAR WHERE CODIGO_PERSONAL = ? AND MODULO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, moduloId};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoConfirmarDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public AccesoConfirmar mapeaRegId(String codigoPersonal, String moduloId){
		
		AccesoConfirmar objeto = new AccesoConfirmar();
		try{
			String comando ="SELECT CODIGO_PERSONAL, MODALIDAD_ID FROM ENOC.ACCESO_CONFIRMAR"
					+ " WHERE CODIGO_PERSONAL = ? AND MODULO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, moduloId};
			objeto = enocJdbc.queryForObject(comando, new AccesoConfirmarMapper(), parametros);			
		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AccesoConfirmarDao|mapeaRegId|:"+ex);
 		}
		return objeto;
	}
	
	public boolean existeReg(String codigoPersonal, String moduloId){
		
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO_CONFIRMAR WHERE CODIGO_PERSONAL = ? AND MODULO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, moduloId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoConfirmarDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<AccesoConfirmar> lisPorEmpleado( String periodoId, String orden ){
		List<AccesoConfirmar> lista = new ArrayList<AccesoConfirmar>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, MODALIDAD_ID FROM ENOC.ACCESO_CONFIRMAR "+orden;
			lista = enocJdbc.query(comando, new AccesoConfirmarMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiGrupoDao|lisPorEmpleado|:"+ex);
		}
		return lista;
	}
	
}
