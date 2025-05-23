package aca.catalogo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatCriterioDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatCriterio criterio ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CAT_CRITERIO(CRITERIO_ID, DESCRIPCION )"
					+ " VALUES(?, ?) ";
			Object[] parametros = new Object[] {criterio.getCriterioId(),criterio.getDescripcion()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCriterioDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( CatCriterio criterio ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CAT_CRITERIO SET DESCRIPCION = ? WHERE CRITERIO_ID = ?";
			Object[] parametros = new Object[] {criterio.getDescripcion(), criterio.getCriterioId()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCriterioDao|updateReg|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String criterioId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CAT_CRITERIO "+ 
				" WHERE CRITERIO_ID = ? ";
			Object[] parametros = new Object[] {criterioId};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCriterioDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public CatCriterio mapeaRegId(  String criterioId) {
		
		CatCriterio criterio	= new CatCriterio();
		
		try{
			String comando = "SELECT CRITERIO_ID, DESCRIPCION"
					+ " FROM ENOC.CAT_CRITERIO WHERE CRITERIO_ID = ?";		
			Object[] parametros = new Object[] {criterioId};		
			criterio = enocJdbc.queryForObject(comando, new CatCriterioMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCriterioDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return criterio;
	}
	
	public boolean existeReg( String criterioId) {
		boolean 		ok 	= false;	
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_CRITERIO WHERE CRITERIO_ID = ?";
			Object[] parametros = new Object[] {criterioId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCriterioDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<CatCriterio> getListAll( String orden ) {
		
		List<CatCriterio> lista = new ArrayList<CatCriterio>();	
		
		try{
			String comando = "SELECT CRITERIO_ID, DESCRIPCION FROM ENOC.CAT_CRITERIO "+ orden;		
			lista = enocJdbc.query(comando, new CatCriterioMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCriterioDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	

	public HashMap<String,CatCriterio> getMapAll( String orden ) {
	
		HashMap<String,CatCriterio> mapa 	= new HashMap<String,CatCriterio>();
		List<CatCriterio> lista 			= new ArrayList<CatCriterio>();		
		try{
			String comando = "SELECT CRITERIO_ID, DESCRIPCION FROM ENOC.CAT_CRITERIO "+ orden;
			lista = enocJdbc.query(comando, new CatCriterioMapper());
			for(CatCriterio criterio : lista){
				mapa.put(criterio.getCriterioId(), criterio);
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCriterioDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}
}