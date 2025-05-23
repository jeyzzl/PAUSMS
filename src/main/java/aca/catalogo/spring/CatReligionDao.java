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
public class CatReligionDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatReligion religion ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CAT_RELIGION(RELIGION_ID, NOMBRE_RELIGION, NOMBRE_CORTO )"
					+ " VALUES( TO_NUMBER(?,'999'), ?, ? ) ";
			Object[] parametros = new Object[] {religion.getReligionId(),religion.getNombreReligion(),religion.getNombreCorto()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}					
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatReligionDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( CatReligion religion ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CAT_RELIGION"
					+ " SET NOMBRE_RELIGION = ?,"
					+ " NOMBRE_CORTO = ?"
					+ " WHERE RELIGION_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {religion.getNombreReligion(),religion.getNombreCorto(), religion.getReligionId()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatReligionDao|updateReg|:"+ex);
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String religionId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CAT_RELIGION WHERE RELIGION_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {religionId};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatReligionDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public CatReligion mapeaRegId(  String religionId) {
		
		CatReligion religion 	= new CatReligion();		
		try{
			String comando = "SELECT RELIGION_ID, NOMBRE_RELIGION, NOMBRE_CORTO"
					+ " FROM ENOC.CAT_RELIGION WHERE RELIGION_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {religionId};
			religion = enocJdbc.queryForObject(comando, new CatReligionMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatReligionDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return religion;
	}
	
	
	public boolean existeReg( String religionId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_RELIGION WHERE RELIGION_ID = TO_NUMBER(?,'999')";	
			Object[] parametros = new Object[] {religionId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatReligionDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() {
		String maximo 			= "1";
		
		try{
			String comando = "SELECT MAX(RELIGION_ID)+1 MAXIMO FROM ENOC.CAT_RELIGION";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatReligionDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String getNombreReligion( String religionId ) {
		
		String nombre			= "empty";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_RELIGION WHERE RELIGION_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {religionId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT NOMBRE_RELIGION FROM ENOC.CAT_RELIGION WHERE RELIGION_ID = TO_NUMBER(?,'999')";			
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatReligionDao|getNombreReligion|:"+ex);
		}
		
		return nombre;
	}
	
	public String getNombreCorto( String religionId ) {
		
		String nombre			= "vacío";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_RELIGION WHERE RELIGION_ID = TO_NUMBER(?,'999')";			
			Object[] parametros = new Object[] {religionId};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT NOMBRE_CORTO FROM ENOC.CAT_RELIGION WHERE RELIGION_ID = TO_NUMBER(?,'999')";				
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatReligionDao|getNombreCorto|:"+ex);
		}
		
		return nombre;
	}
	
		
	public List<CatReligion> getListAll( String orden ) {
		
		List<CatReligion> lista = new ArrayList<CatReligion>();
		
		try{
			String comando = "SELECT RELIGION_ID, NOMBRE_RELIGION, NOMBRE_CORTO FROM ENOC.CAT_RELIGION "+ orden;
			lista = enocJdbc.query(comando, new CatReligionMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatReligionDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,CatReligion> getMapAll( String orden ) {
		
		HashMap<String,CatReligion> mapa 	= new HashMap<String,CatReligion>();
		List<CatReligion> lista 			= new ArrayList<CatReligion>();
		
		try{
			String comando = "SELECT RELIGION_ID, NOMBRE_RELIGION, NOMBRE_CORTO FROM ENOC.CAT_RELIGION "+ orden;
			lista = enocJdbc.query(comando, new CatReligionMapper());
			for(CatReligion religion : lista){
				mapa.put(religion.getReligionId(), religion);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatReligionDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}	
	
	public HashMap<String,String> mapaUsados() {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT RELIGION_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.ALUM_PERSONAL GROUP BY RELIGION_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa obj : lista){
				mapa.put(obj.getLlave(), obj.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatReligionDao|mapaUsados|:"+ex);
		}
		
		return mapa;
	}
	
}