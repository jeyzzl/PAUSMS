package aca.alumno.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumFotoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AlumFoto foto){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ALUM_FOTO"+ 
				"(CODIGO_PERSONAL, FOLIO,  FECHA, USUARIO, FOTO ) "+
				"VALUES( ?, " +
				"TO_NUMBER(?,'9'), "+
				"TO_DATE(?,'DD/MM/YYYY'), "+
				" ?, ?)";
			
			Object[] parametros = new Object[] {
				foto.getCodigoPersonal(),foto.getFolio(),foto.getFecha(),foto.getUsuario(),foto.getFoto()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumFotoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean updateReg(AlumFoto foto){
		boolean ok 				= false;
		
		try{
			String comando = "UPDATE ENOC.ALUM_FOTO "+ 
				"SET FECHA = TO_DATE(?,'DD/MM/YYYY'), "+
				"USUARIO = ?, "+
				"FOTO = ? "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND FOLIO = TO_NUMBER(?,'9')";
			
			Object[] parametros = new Object[] {
				foto.getFecha(),foto.getUsuario(),foto.getFoto(),foto.getCodigoPersonal(),foto.getFolio()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumFotoDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg(String codigoPersonal, String folio){
		boolean ok 				= false;
		try{
			String comando = "DELETE FROM ENOC.ALUM_FOTO "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND FOLIO = TO_NUMBER(?,'9')";
			
			Object[] parametros = new Object[] {
				codigoPersonal,folio
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumFotoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public AlumFoto mapeaRegId(String codigoPersonal, String folio){
		AlumFoto alumFoto = new AlumFoto();
		
		try{
			String comando = "SELECT"+
				" CODIGO_PERSONAL, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, FOTO"+
				" FROM ENOC.ALUM_FOTO"+ 
				" WHERE CODIGO_PERSONAL = ?"+
				" AND FOLIO = TO_NUMBER(?,'9')";
			
			Object[] parametros = new Object[] {
				codigoPersonal,folio
			};
			
			alumFoto = enocJdbc.queryForObject(comando, new AlumFotoMapper(), parametros);	
		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumFotoDao|mapeaRegId|:"+ex);
		}
		return alumFoto;
	}

	public boolean existeReg(String codigoPersonal, String folio){
		boolean ok 		= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_FOTO "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND FOLIO = TO_NUMBER(?,'9')";
			
			Object[] parametros = new Object[] {
				codigoPersonal,folio
			};
			
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumFotoDao|existeReg|:"+ex);
		}
		
		return ok;
	}

}
