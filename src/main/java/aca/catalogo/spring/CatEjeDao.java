package aca.catalogo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatEjeDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatEje eje ) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.CAT_EJE(EJE_ID, EJE_NOMBRE, DESCRIPCION,NIVEL_ID, ORDEN )"+
				" VALUES( ?, ?, ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'99') ) ";
			Object[] parametros = new Object[] {eje.getEjeId(), eje.getEjeNombre(), eje.getDescripcion(), eje.getNivelId(), eje.getOrden()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEjeDao|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean updateReg( CatEje eje ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CAT_EJE "+ 
				" SET EJE_NOMBRE= ?, " +
				" DESCRIPCION = ?, " +
				" NIVEL_ID = TO_NUMBER(?,'99') ," +
				" ORDEN = TO_NUMBER(?,'99') "+
				" WHERE EJE_ID = ?";
			Object[] parametros = new Object[] {eje.getEjeNombre(), eje.getDescripcion(), eje.getNivelId(), eje.getOrden(), eje.getEjeId()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEjeDao|updateReg|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String ejeId ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CAT_EJE WHERE EJE_ID = ? ";
			Object[] parametros = new Object[] {ejeId};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEjeDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public CatEje mapeaRegId(  String ejeId) {
		
		CatEje eje 				= new CatEje();
		
		try{
			String comando = "SELECT EJE_ID, EJE_NOMBRE, DESCRIPCION, NIVEL_ID, ORDEN FROM ENOC.CAT_EJE WHERE EJE_ID = ?";
			Object[] parametros = new Object[] {ejeId};
			eje = enocJdbc.queryForObject(comando, new CatEjeMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEjeDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return eje;
	}
	
	public boolean existeReg( String ejeId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_EJE WHERE EJE_ID = ? ";			
			Object[] parametros = new Object[] {ejeId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEjeDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String getEjeNombre( String ejeId ) {
		
		String nombre			= "x";
		
		try{
			String comando = "SELECT EJE_NOMBRE FROM ENOC.CAT_RELIGION WHERE EJE_ID = ?";
			Object[] parametros = new Object[] {ejeId};
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEjeDao|getNombreReligion|:"+ex);
		}
		
		return nombre;
	}
	
	public List<CatEje> getListAll( String orden ) {
		
		List<CatEje> lista = new ArrayList<CatEje>();		
		
		
		try{
			String comando = "SELECT EJE_ID, EJE_NOMBRE, DESCRIPCION, NIVEL_ID, ORDEN FROM ENOC.CAT_EJE "+ orden;		
			lista = enocJdbc.query(comando, new CatEjeMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEjeDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,CatEje> getMapAll( String orden ) {
		
		HashMap<String,CatEje> mapa	= new HashMap<String,CatEje>();
		List<CatEje> lista 			= new ArrayList<CatEje>();
		
		try{
			String comando = "SELECT EJE_ID, EJE_NOMBRE, DESCRIPCION, NIVEL_ID, ORDEN FROM ENOC.CAT_EJE "+ orden;
			lista = enocJdbc.query(comando, new CatEjeMapper());
			for(CatEje eje : lista){
				mapa.put(eje.getEjeId(), eje);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEjeDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}
	
	public List<CatEje> getListEjeNivel( String nivelId, String orden ) {
			
		List<CatEje> lista = new ArrayList<CatEje>();
		
		try{
			String comando = " SELECT EJE_ID, EJE_NOMBRE, DESCRIPCION, NIVEL_ID, ORDEN FROM ENOC.CAT_EJE WHERE NIVEL_ID = ? "+ orden;
			Object[] parametros = new Object[] {nivelId};
			lista = enocJdbc.query(comando, new CatEjeMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEjeDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	
}