package aca.matricula.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CambioCarreraDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(CambioCarrera objeto){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.CAMBIO_CARRERA"
					+ " (SOLICITUD_ID, CODIGO_PERSONAL, FECHA, CARRERA_BAJA, CARRERA_ALTA, USUARIO)"
					+ " VALUES(TO_NUMBER(?,'99999'), ?, TO_DATE(?,'DD/MM/YYYY'), ?, ?, ?)";
			Object[] parametros = new Object[] {
				objeto.getSolicitudId(), objeto.getCodigoPersonal(), objeto.getFecha(), objeto.getCarreraBaja(), objeto.getCarreraAlta(), objeto.getUsuario()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.CambioCarreraDao|insertReg|:"+ex);			
		}
		return ok;
	}	

	public CambioCarrera mapeaRegId(String solicitudId){
		CambioCarrera objeto = new CambioCarrera();		
		try{
			String comando = "SELECT SOLICITUD_ID, CODIGO_PERSONAL, FECHA, CARRERA_BAJA, CARRERA_ALTA, USUARIO"
					+ " FROM ENOC.CAMBIO_CARRERA WHERE SOLICITUD_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {solicitudId};			
			objeto = enocJdbc.queryForObject(comando, new CambioCarreraMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.CambioCarreraDao|mapeaRegId|:"+ex);
		
		}
		return objeto;
	}	
	
	public boolean deleteReg(String solicitudId ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CAMBIO_CARRERA WHERE SOLICITUD_ID = TO_NUMBER(?,'99999')";			
			Object[] parametros = new Object[] {solicitudId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error -  aca.matricula.spring.CambioCarreraDa|deleteReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean existeReg(String solicitudId){
		boolean 			ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAMBIO_CARRERA WHERE SOLICITUD_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {solicitudId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.CambioCarreraDao|existeReg|:"+ex);

		}
		return ok;
	}

	public String getSolitidudId(){
		String id = "";
		
		try{
			String comando = "SELECT COALESCE(MAX(SOLICITUD_ID)+1,1) AS SOLICITUD_ID FROM ENOC.CAMBIO_CARRERA";
			id = enocJdbc.queryForObject(comando, String.class);
			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.CambioCarreraDao|solitidudId|:"+ex);
			
		}
		return id;
	}
	
	public List<CambioCarrera> lisCambioCarreraAlumno(String codigoPersonal){
		List<CambioCarrera> lista	= new ArrayList<CambioCarrera>();		
		try{
			String comando = "SELECT SOLICITUD_ID, CODIGO_PERSONAL, TO_CHAR (FECHA, 'DD/MM/YYYY') AS FECHA, CARRERA_BAJA, CARRERA_ALTA, USUARIO"
					+ " FROM ENOC.CAMBIO_CARRERA WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new CambioCarreraMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.spring.CambioCarreraDao|lisCambioCarreraAlumno|:"+ex);
		}		
		return lista;
	}	

}
