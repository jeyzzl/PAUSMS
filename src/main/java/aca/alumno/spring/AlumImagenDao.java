package aca.alumno.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.carga.spring.CargaAlumno;
import aca.carga.spring.CargaAlumnoMapper;

@Component
public class AlumImagenDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AlumImagen imagen){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.ALUM_IMAGEN(CODIGO_PERSONAL, CONSENTIMIENTO,TIPO, FECHA)"
					+ " VALUES(?, ?, ?, SYSDATE)";			
			Object[] parametros = new Object[] { imagen.getCodigoPersonal(),imagen.getConsentimiento(), imagen.getTipo()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumImagenDao|insertReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean updateReg(AlumImagen imagen){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ALUM_IMAGEN SET CONSENTIMIENTO = ?, TIPO = ?, FECHA = SYSDATE WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {
				imagen.getConsentimiento(),imagen.getTipo(), imagen.getCodigoPersonal()
			};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumImagenDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg(String codigoPersonal){
		boolean ok = false;	
		try{
			String comando = "DELETE FROM ENOC.ALUM_IMAGEN WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.update(comando, parametros)==1){
 				ok = true;
 			}	
		}catch(Exception ex){
			System.out.println("Error  - aca.alumno.spring.AlumImagenDao|deleteReg|:"+ex);		
		}
		return ok;
	}
	
	public AlumImagen mapeaRegId( String codigoPersonal){
		AlumImagen imagen = new AlumImagen();
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_IMAGEN WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
 				comando = "SELECT CODIGO_PERSONAL, CONSENTIMIENTO, TIPO, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA "
 						+ "FROM ENOC.ALUM_IMAGEN WHERE CODIGO_PERSONAL = ?";
 				imagen = enocJdbc.queryForObject(comando,  new AlumImagenMapper(), parametros);
 			}					
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumImagenDao|mapeaRegId|:"+ex);	
		}	
		return imagen;
	}

	public boolean existeReg(String codigoPersonal){
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_IMAGEN WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumImagenDao|existeReg|:"+ex);		
		}
		
		return ok;
	}
	
	public ArrayList<AlumImagen> lisTodos(String orden ){
		
		List<AlumImagen> lista= new ArrayList<AlumImagen>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CONSENTIMIENTO, TIPO, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA FROM ENOC.ALUM_IMAGEN "+orden; 
			lista = enocJdbc.query(comando, new AlumImagenMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumImagenDao|lisTodos|:"+ex);
		}	
		return (ArrayList<AlumImagen>) lista;
	}
	
	public HashMap<String, String> mapaUsoImagen(String orden) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, CONSENTIMIENTO AS  VALOR FROM ENOC.ALUM_IMAGEN "+orden;			
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map:lista){		
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumImagenDao|mapaUsoImagen|:"+ex);
		}

		return mapa;
	}

}