package aca.alumno.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumFotografiaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AlumFotografia foto){
		boolean ok 				= false;
		
		try{
			String comando = "INSERT INTO ENOC.ALUM_FOTOGRAFIA"
					+ " (CODIGO_PERSONAL, RESOLUCION)"
					+ " VALUES( ?, ?)";
			
			Object[] parametros = new Object[] {
				foto.getCodigoPersonal(),foto.getResolucion()
			};
				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumFotografia|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean updateReg(AlumFotografia foto){
		boolean ok 				= false;
		
		try{
			String comando = "UPDATE ENOC.ALUM_FOTOGRAFIA "+ 
				"SET RESOLUCION = ? "+				
				"WHERE CODIGO_PERSONAL = ?";
			
			Object[] parametros = new Object[] {
				foto.getResolucion(),foto.getCodigoPersonal()
			};
				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumFotografia|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg(String codigoPersonal){
		boolean ok 				= false;
		try{
			String comando = "DELETE FROM ENOC.ALUM_FOTOGRAFIA "+ 
				"WHERE CODIGO_PERSONAL = ?";

			Object[] parametros = new Object[] {
				codigoPersonal
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumFotografia|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public AlumFotografia mapeaRegId(String codigoPersonal){
		AlumFotografia foto = new AlumFotografia();
		
		try{
			String comando = "SELECT"+
				" CODIGO_PERSONAL, RESOLUCION FROM ENOC.ALUM_FOTOGRAFIA"+ 
				" WHERE CODIGO_PERSONAL = ?";
			
			Object[] parametros = new Object[] {
				codigoPersonal
			};
				
			foto = enocJdbc.queryForObject(comando, new AlumFotografiaMapper(), parametros);	
		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumFotografia|mapeaRegId|:"+ex);
		}
		
		return foto;
	}

	public boolean existeReg(String codigoPersonal){
		boolean ok 		= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_FOTOGRAFIA "+ 
				"WHERE CODIGO_PERSONAL = ? ";
			
			Object[] parametros = new Object[] {
				codigoPersonal
			};
			
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumFotografia|existeReg|:"+ex);
		}
		
		return ok;
	}
}
