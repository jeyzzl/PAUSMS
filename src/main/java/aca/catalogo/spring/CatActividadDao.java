package aca.catalogo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatActividadDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
		
	public boolean insertReg( CatActividad objeto){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CAT_ACTIVIDAD(ACTIVIDAD_ID, DESCRIPCION )"
					+ " VALUES(?, ?)";
			Object[] parametros = new Object[] {objeto.getActividadId(), objeto.getDescripcion()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.dao.CatActividadDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( CatActividad objeto ) throws Exception{
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CAT_ACTIVIDAD SET DESCRIPCION = ? WHERE ACTIVIDAD_ID = ? ";
			Object[] parametros = new Object[] {objeto.getDescripcion(), objeto.getActividadId()};			
			if (enocJdbc.update(comando,parametros)==1){			
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.dao.CatActividadDao|updateReg|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String actividadId) throws Exception{
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CAT_ACTIVIDAD WHERE ACTIVIDAD_ID = ?";
			Object[] parametros = new Object[] {actividadId};
			if (enocJdbc.update(comando,parametros)==1){			
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.dao.CatActividadDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public CatActividad mapeaRegId( String actividadId) {
		CatActividad objeto = new CatActividad();
		
		try{
			String comando = "SELECT ACTIVIDAD_ID, DESCRIPCION FROM ENOC.CAT_ACTIVIDAD WHERE ACTIVIDAD_ID = ?";		 
			Object[] parametros = new Object[] { actividadId };
			objeto = enocJdbc.queryForObject(comando, new CatActividadMapper(), parametros);		
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.dao.CatActividadDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return objeto;
	}
	
	public boolean existeReg( String actividadId ) {
		boolean 		ok 	= false;			
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_ACTIVIDAD WHERE ACTIVIDAD_ID = ?"; 
			Object[] parametros = new Object[] { actividadId };	
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){	
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.dao.CatActividadDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String getDescripcion( String actividadId) {		
		
		String descripcion 		= "x";
		
		try{
			String comando = "SELECT COALESCE(DESCRIPCION,'x') AS DESCRIPCION FROM ENOC.CAT_ACTIVIDAD WHERE ACTIVIDAD_ID = ?"; 
			Object[] parametros = new Object[] { actividadId };	
			descripcion = enocJdbc.queryForObject(comando,String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.dao.CatActividadDao|getDescripcion|:"+ex);
		}
		
		return descripcion;
	}		
		
	public List<CatActividad> getListAll( String orden ){
		
		List<CatActividad> lista = new ArrayList<CatActividad>();
		
		try{
			String comando = "SELECT ACTIVIDAD_ID, DESCRIPCION FROM ENOC.CAT_ACTIVIDAD "+ orden;
			lista = enocJdbc.query(comando, new CatActividadMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.dao.CatActividadDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,CatActividad> getMapAll( String orden ) {
		
		HashMap<String,CatActividad> mapa = new HashMap<String,CatActividad>();
		List<CatActividad> lista = new ArrayList<CatActividad>();
		
		try{
			String comando = "SELECT ACTIVIDAD_ID, DESCRIPCION FROM ENOC.CAT_ACTIVIDAD "+ orden;
			lista = enocJdbc.query(comando,new CatActividadMapper());
			for(CatActividad actividad : lista){				
				mapa.put(actividad.getActividadId(), actividad);
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.dao.CatActividadDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}
}