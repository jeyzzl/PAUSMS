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
public class CatTipoCursoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public List<CatTipoCurso> getListAll( String orden ) {
		
		List<CatTipoCurso> lista		= new ArrayList<CatTipoCurso>();
		
		try{
			String comando = "SELECT TIPOCURSO_ID, NOMBRE_TIPOCURSO, CORTO FROM ENOC.CAT_TIPOCURSO "+ orden;
			lista = enocJdbc.query(comando, new CatTipoCursoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.TipoCursoUtil|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatTipoCurso> lisCursosEnPlan( String planId, String orden ) {
		
		List<CatTipoCurso> lista		= new ArrayList<CatTipoCurso>();		
		try{
			String comando = "SELECT TIPOCURSO_ID, NOMBRE_TIPOCURSO, CORTO FROM ENOC.CAT_TIPOCURSO"
					+ " WHERE TIPOCURSO_ID IN (SELECT TIPOCURSO_ID FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ?) "+ orden;
			Object[] parametros = new Object[] {planId};
			lista = enocJdbc.query(comando, new CatTipoCursoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.TipoCursoUtil|lisCursosEnPlan|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,CatTipoCurso> getMapAll( String orden ) {
		
		HashMap<String,CatTipoCurso> mapa 	= new HashMap<String,CatTipoCurso>();		
		List<CatTipoCurso> lista			= new ArrayList<CatTipoCurso>();
		
		try{
			String comando = "SELECT TIPOCURSO_ID, NOMBRE_TIPOCURSO, CORTO FROM ENOC.CAT_TIPOCURSO "+ orden;
			lista = enocJdbc.query(comando, new CatTipoCursoMapper());
			for (CatTipoCurso tipo : lista){
				mapa.put(tipo.getTipoCursoId(), tipo);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.TipoCursoUtil|getMapAll|:"+ex);
		}
		
		return mapa;
	}

	public String getNombreTipoCurso( String id ) {
		
		String nombre = "";		
		try{
			String comando = "SELECT NOMBRE_TIPOCURSO FROM ENOC.CAT_TIPOCURSO WHERE TIPOCURSO_ID = TO_NUMBER(?,'99')";
			nombre = enocJdbc.queryForObject(comando, String.class, id);	
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.TipoCursoUtil|getNombreTipoCurso|:"+ex);
		}
		
		return nombre;
	}
}