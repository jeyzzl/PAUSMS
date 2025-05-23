package aca.investiga.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class InvArchivoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( InvArchivo archivo) {
		boolean ok = false;		
		
		try{
			String comando = "INSERT INTO ENOC.INV_ARCHIVO(PROYECTO_ID, FOLIO, NOMBRE, ARCHIVO)"
					+ " VALUES (?, TO_NUMBER(?,'99'), ?, ?)";
			Object[] parametros = new Object[] { archivo.getProyectoId(), archivo.getFolio(), archivo.getNombre(), archivo.getArchivo() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch (Exception ex){
			System.out.println("Error - aca.investiga.spring.InvArchivoDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg( InvArchivo archivo) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.INV_ARCHIVO SET NOMBRE = ?, ARCHIVO = ?"		
					+ " WHERE PROYECTO_ID = ? AND FOLIO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] { archivo.getNombre(), archivo.getArchivo(), archivo.getProyectoId(), archivo.getFolio() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch (Exception ex){
			System.out.println("Error - aca.investiga.spring.InvArchivoDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg( String proyectoId, String folio ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.INV_ARCHIVO WHERE PROYECTO_ID = ? AND FOLIO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] { proyectoId, folio };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvArchivoDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public InvArchivo mapeaRegId( String proyectoId, String folio) {
		
		InvArchivo res = new InvArchivo();		
		try{
			String comando = "SELECT PROYECTO_ID, FOLIO, NOMBRE, ARCHIVO FROM ENOC.INV_ARCHIVO"
					+ " WHERE PROYECTO_ID = ? AND FOLIO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] { proyectoId, folio };
			res = enocJdbc.queryForObject(comando, new InvArchivoMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvArchivoDao|mapeaRegId|:"+ex);
		}
		
		return res;
	}
	
	public boolean existeReg( String proyectoId, String folio) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.INV_ARCHIVO WHERE PROYECTO_ID = ? AND FOLIO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] { proyectoId, folio };
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvArchivoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String getNombre( String proyectoId, String folio) {
		String nombre = "-";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.INV_ARCHIVO WHERE PROYECTO_ID = ? AND FOLIO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] { proyectoId, folio };
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				comando = "SELECT NOMBRE FROM ENOC.INV_ARCHIVO WHERE PROYECTO_ID = ? AND FOLIO = TO_NUMBER(?,'99')";
				nombre 	= enocJdbc.queryForObject(comando,String.class,parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvArchivoDao|existeReg|:"+ex);
		}
		
		return nombre;
	}
	
	public HashMap<String,String> mapArchivoLight() {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT PROYECTO_ID||FOLIO AS LLAVE, NOMBRE AS VALOR FROM INV_ARCHIVO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvArchivoDao|mapArchivoLight|:"+ex);
		}
		
		return mapa;
	}
	
}
