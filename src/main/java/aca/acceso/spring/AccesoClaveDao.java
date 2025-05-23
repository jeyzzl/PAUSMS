package aca.acceso.spring;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AccesoClaveDao {
	
	@Autowired	
	@Qualifier("jdbcEnoc")	
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( AccesoClave clave){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.ACCESO_CLAVE(CODIGO_PERSONAL, FECHA, CLAVE, IP, FOLIO, FECHA_RECUPERA)" +
					" VALUES(?,TO_DATE(?,'DD/MM/YYYY'),?,?,?,?)";
			Object[] parametros = new Object[] {clave.getCodigoPersonal(), clave.getFecha(), clave.getClave(), clave.getIp(), clave.getFolio(), clave.getFechaRecupera()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoClaveDao|insertReg|:"+ex);
		}
		
		return ok;
	}

	public boolean updateReg(AccesoClave clave){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ACCESO_CLAVE SET FECHA = TO_DATE(?,'DD/MM/YYYY'), CLAVE = ?, IP = ?, FECHA_RECUPERA = SYSDATE WHERE CODIGO_PERSONAL = ? AND FOLIO = ?";
			Object[] parametros = new Object[] {clave.getFecha(), clave.getClave(), clave.getIp(), clave.getCodigoPersonal(), clave.getFolio()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoClaveDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg(String codigoPersonal, int folio ){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ACCESO_CLAVE WHERE CODIGO_PERSONAL = ? AND FOLIO = ?";
			Object[] parametros = new Object[] {codigoPersonal, folio};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoClaveDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public AccesoClave mapeaRegId( String codigoPersonal){		
		AccesoClave clave = new AccesoClave();
		try{
			String comando ="SELECT CODIGO_PERSONAL, COALESCE(TO_CHAR(FECHA,'DD/MM/YYYY'),'01/01/1900') FECHA, CLAVE, IP, FOLIO, FECHA_RECUPERA"
					+ " FROM ENOC.ACCESO_CLAVE"
					+ " WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			clave = enocJdbc.queryForObject(comando, new AccesoClaveMapper(), parametros);
			
		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AccesoClaveDao|mapeaRegId|:"+ex);
 		}
		return clave;
	}
	
	public AccesoClave mapeaRegIdFolio( String codigoPersonal, int folio){		
		AccesoClave clave = new AccesoClave();		
		try{
			String comando ="SELECT CODIGO_PERSONAL, COALESCE(TO_CHAR(FECHA,'DD/MM/YYYY'),'01/01/2000') AS FECHA, CLAVE, IP, FOLIO,"
					+ " COALESCE(TO_CHAR(FECHA_RECUPERA,'YYYY/MM/DD HH24:MI:SS'),'2000/01/01 00:00:00') AS FECHA_RECUPERA"
					+ " FROM ENOC.ACCESO_CLAVE"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND FOLIO = ?";
			Object[] parametros = new Object[] {codigoPersonal, folio};
			clave = enocJdbc.queryForObject(comando, new AccesoClaveMapper(), parametros);
		}catch(Exception ex){
 			System.out.println("Error - aca.acceso.spring.AccesoClaveDao|mapeaRegIdFolio|:"+ex);
 		}		
		return clave;
	}	
	
	public boolean existeReg(String codigoPersonal, int folio){		
		boolean ok 			= false;		
		try{
			String comando = "SELECT COUNT(*) AS VALOR FROM ENOC.ACCESO_CLAVE WHERE CODIGO_PERSONAL = ? AND FOLIO = ?";
			Object[] parametros = new Object[] {codigoPersonal, folio};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoClaveDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public int ultimoFolio( String matricula){
		int folio				= 0;		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO),0) AS FOLIO FROM ENOC.ACCESO_CLAVE WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {matricula};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				folio = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoClaveDao|ultimoFolio|:"+ex);
		}
		
		return folio;
	}
	
}
