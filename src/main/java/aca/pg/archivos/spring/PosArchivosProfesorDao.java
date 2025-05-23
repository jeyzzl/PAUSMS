/**
 * 
 */
package aca.pg.archivos.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class PosArchivosProfesorDao {	
	
	@Autowired
	@Qualifier("jdbcArchivo")
	private JdbcTemplate archivoJdbc;
	
	
	public PosArchivosProfesor mapeaRegId( String archivoId, String folio){
		
		PosArchivosProfesor archivo = new PosArchivosProfesor();		
		try{ 
			String comando = "SELECT ARCHIVO_ID, FOLIO, CODIGO_PERSONAL, FECHA, NOMBRE, COMENTARIO, AUTORIZACION, TAMANO, ARCHIVO"
					+ " FROM PORTAL.ARCHIVOS_PROFESOR"
					+ " WHERE ARCHIVO_ID = ?"
					+ " AND FOLIO = TO_NUMBER(?, '999999')";		
			Object[] parametros = new Object[] {archivoId, folio};
			archivo = archivoJdbc.queryForObject(comando, new PosArchivosProfesorMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivos.spring.PosArchivosProfesor|mapeaRegId|:"+ex);
		}
		
		return archivo;
	}
	
	public boolean tieneArchivos( String archivoId, String codigoAlumno){	
		boolean ok		= false;				
		try{ 
			String comando = "SELECT COUNT(*) FROM PORTAL.ARCHIVOS_PROFESOR WHERE ARCHIVO_ID =  ? AND POSITION(? IN AUTORIZACION) > 0";
			Object[] parametros = new Object[] {archivoId, codigoAlumno};
			if (archivoJdbc.queryForObject(comando, Integer.class, parametros)>=0){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivos.spring.ArchivosProfesor|tieneArchivos|:"+ex);
		}
		
		return ok;
	}	
}