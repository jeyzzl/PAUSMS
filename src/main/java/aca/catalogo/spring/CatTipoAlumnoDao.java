// Clase para la tabla de Modulo
package aca.catalogo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatTipoAlumnoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(CatTipoAlumno catTipoAlumno ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CAT_TIPOALUMNO(TIPO_ID, NOMBRE_TIPO)"
					+ " VALUES( ?, ?)";
			Object[] parametros = new Object[] {catTipoAlumno.getTipoId(),catTipoAlumno.getNombreTipo()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatTipoAlumnoDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg(CatTipoAlumno catTipoAlumno ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CAT_TIPOALUMNO SET NOMBRE_TIPO = ? WHERE TIPO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {catTipoAlumno.getNombreTipo(), catTipoAlumno.getTipoId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatTipoAlumnoDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg(String tipoId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CAT_TIPOALUMNO WHERE TIPO_ID = ?";
			Object[] parametros = new Object[] {tipoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatTipoAlumnoDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public CatTipoAlumno mapeaRegId(String tipoId) {
		
		CatTipoAlumno catTipoAlumno = new CatTipoAlumno();
		
		try{
			String comando = "SELECT TIPO_ID, NOMBRE_TIPO FROM ENOC.CAT_TIPOALUMNO WHERE TIPO_ID = ?";
			Object[] parametros = new Object[] {tipoId};
			catTipoAlumno = enocJdbc.queryForObject(comando, new CatTipoAlumnoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatTipoAlumnoDaomapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return catTipoAlumno;
	}
	
	public boolean existeReg(String tipoId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_TIPOALUMNO WHERE TIPO_ID = ?";
			Object[] parametros = new Object[] {tipoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatTipoAlumnoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg(){
		String maximo = "1";		
		try{
			String comando = "SELECT COALESCE(MAX(TIPO_ID)+1,1) FROM ENOC.CAT_TIPOALUMNO";			
			maximo = enocJdbc.queryForObject(comando,String.class);	
			if(maximo.length()==1){
				maximo = "0"+maximo;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatTipoAlumnoDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	
		
	public List<CatTipoAlumno> getListAll( String orden ) {
		
		List<CatTipoAlumno> lista = new ArrayList<CatTipoAlumno>();
		
		try{
			String comando = "SELECT TIPO_ID, NOMBRE_TIPO FROM ENOC.CAT_TIPOALUMNO "+ orden; 
			lista = enocJdbc.query(comando, new CatTipoAlumnoMapper());

		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatTipoAlumnoDao|getListAll|:"+ex);

		}
		
		return lista;
	}
	
	public String getNombreTipo(String tipoId) {
		String nombre			= "-";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_TIPOALUMNO WHERE TIPO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {tipoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT NOMBRE_TIPO FROM ENOC.CAT_TIPOALUMNO WHERE TIPO_ID = TO_NUMBER(?,'99')";
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatTipoAlumnoDao|getNombreTipo|:"+ex);
		}		
		return nombre;
	}
	
	public HashMap<String,CatTipoAlumno> getMapAll( String orden ) {
		
		HashMap<String,CatTipoAlumno> mapa = new HashMap<String,CatTipoAlumno>();
		List<CatTipoAlumno> lista = new ArrayList<CatTipoAlumno>();
		
		try{
			String comando = "SELECT TIPO_ID, NOMBRE_TIPO FROM ENOC.CAT_TIPOALUMNO "+ orden;
			lista = enocJdbc.query(comando, new CatTipoAlumnoMapper());
			for(CatTipoAlumno tipo:lista){
				mapa.put(tipo.getTipoId(), tipo);
			}

		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatTipoAlumnoDao|getMapAll|:"+ex);

		}
		
		return mapa;
	}
	
	public HashMap<String,String> getMapNombreTipo() {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<CatTipoAlumno> lista = new ArrayList<CatTipoAlumno>();
		
		try{
			String comando = "SELECT TIPO_ID, NOMBRE_TIPO FROM ENOC.CAT_TIPOALUMNO ";
			lista = enocJdbc.query(comando, new CatTipoAlumnoMapper());
			for(CatTipoAlumno tipo:lista){
				mapa.put(tipo.getTipoId(), tipo.getNombreTipo());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatTipoAlumnoDao|getMapNombreTipo|:"+ex);
			
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaUsados() {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT TIPO_ALUMNO AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ALUM_ACADEMICO GROUP BY TIPO_ALUMNO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa obj : lista){
				mapa.put(obj.getLlave(), obj.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatTipoAlumnoDao|mapaUsados|:"+ex);
		}
		
		return mapa;
	}
}