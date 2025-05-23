package aca.residencia.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ResComentarioDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( ResComentario com ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.RES_COMENTARIO(FOLIO, CODIGO_PERSONAL, RESIDENCIA_ID, COMENTARIO, COMENTARIO_B) VALUES(TO_NUMBER(?,'99'),?,?,?,?)";
			Object[] parametros = new Object[] {com.getFolio(), com.getCodigoPersonal(), com.getResidenciaId(), com.getComentario(), com.getComentarioB()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResComentario|insertReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean updateReg( ResComentario com ){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.RES_COMENTARIO SET COMENTARIO = ?, COMENTARIO_B = ?, RESIDENCIA_ID = ? " + 
					"WHERE FOLIO= TO_NUMBER(?,'99') AND CODIGO_PERSONAL = ?";	
			Object[] parametros = new Object[] {com.getComentario(), com.getComentarioB(), com.getResidenciaId(), com.getFolio(), com.getCodigoPersonal()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResComentario|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg( String codigoPersonal ){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.RES_COMENTARIO WHERE CODIGO_PERSONAL= ?"; 
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.update(comando, parametros)==1){
				ok = true;
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResComentario|deletetReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeReg( String codigoPersonal, String folio){
		boolean ok 			= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.RES_COMENTARIO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'99')"; 		
			Object[] parametros = new Object[] {codigoPersonal, folio};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)==1){
				ok = true;
			}
	
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResComentario|existeReg|:"+ex);
		}		
		return ok;
	}

	public String maximoReg( String codigoPersonal ){

		String maximo 			= "0";		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO),0)+1 AS MAXIMO FROM ENOC.RES_COMENTARIO WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {codigoPersonal}; 			
 			maximo = enocJdbc.queryForObject(comando,String.class, parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResComentario|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public ResComentario mapeaRegId(String codigoPersonal, String residenciaId){
		ResComentario resComentario = new ResComentario();
		try{
			String comando = "SELECT FOLIO, CODIGO_PERSONAL, RESIDENCIA_ID, COMENTARIO, COMENTARIO_B"
					+ " FROM ENOC.RES_COMENTARIO WHERE CODIGO_PERSONAL = ? AND RESIDENCIA_ID = ?";
			resComentario = enocJdbc.queryForObject(comando,new ResComentarioMapper(), codigoPersonal, residenciaId);
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResComentario|mapeaRegId|:"+ex);
		}
		return resComentario;
	}
	
	public ResComentario mapeaRegFolio(String codigoPersonal, String folio){
		ResComentario resComentario = new ResComentario();
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.RES_COMENTARIO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'99')"; 		
			Object[] parametros = new Object[] {codigoPersonal, folio};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)==1){
				comando = "SELECT FOLIO, CODIGO_PERSONAL, RESIDENCIA_ID, COMENTARIO, COMENTARIO_B"
						+ " FROM ENOC.RES_COMENTARIO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'99')";
				resComentario = enocJdbc.queryForObject(comando,new ResComentarioMapper(), codigoPersonal, folio);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResComentario|mapeaRegFolio|:"+ex);
		}
		return resComentario;
	}
	
	public String getComenta(String codigoPersonal, String residenciaId){		
		String comenta = "";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.RES_COMENTARIO WHERE CODIGO_PERSONAL = ? AND RESIDENCIA_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, residenciaId};
 			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
 				comando = "SELECT COMENTARIO FROM ENOC.RES_COMENTARIO WHERE CODIGO_PERSONAL = ? AND RESIDENCIA_ID = ?";
 				comenta = enocJdbc.queryForObject(comando,String.class, parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResComentario|getComenta|:"+ex);
		}
		
		return comenta;
	}	
	
	public String maximoRegi( String codigoPersonal, String residenciaId ){

		String maximo 			= "0";		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO),0) AS MAXIMO FROM ENOC.RES_COMENTARIO " + 
					" WHERE CODIGO_PERSONAL = ? " +
					" AND RESIDENCIA_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, residenciaId};
 			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
 				maximo = enocJdbc.queryForObject(comando,String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResComentario|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public List<ResComentario> getListAll( String codigoPersonal, String residencia, String orden ){
		
		List<ResComentario> lista 	= new ArrayList<ResComentario>();
		
		try{
			String comando = "SELECT FOLIO, CODIGO_PERSONAL, RESIDENCIA_ID, COMENTARIO, COMENTARIO_B " +
					"FROM ENOC.RES_COMENTARIO WHERE CODIGO_PERSONAL = ? " + 
					"AND RESIDENCIA_ID = ? "+orden;
			lista = enocJdbc.query(comando, new ResComentarioMapper(),codigoPersonal,residencia);
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ComentarioUtil|getListAll|:"+ex);
		}
		
		return lista;
	}

	public HashMap<String, ResComentario> mapaComentario(){
		HashMap<String, ResComentario> mapa = new HashMap<String, ResComentario>();
		List<ResComentario> lista	    	= new ArrayList<ResComentario>();		
		try{
			String comando = "SELECT t.* FROM RES_COMENTARIO t "
							+"JOIN ( SELECT codigo_personal, MAX(folio) AS max_folio FROM RES_COMENTARIO GROUP BY codigo_personal ) m "
							+"ON t.codigo_personal = m.codigo_personal AND t.folio = m.max_folio ";
			lista = enocJdbc.query(comando, new ResComentarioMapper());			
			for(ResComentario comentario : lista){				
				mapa.put(comentario.getCodigoPersonal(), comentario);					
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResComentario|mapaComentario|:"+ex);
		}
		
		return mapa;
	}

	public HashMap<String, ResComentario> mapMaxiComentario(){
		HashMap<String, ResComentario> mapa = new HashMap<String, ResComentario>();
		List<ResComentario> lista	    	= new ArrayList<ResComentario>();		
		try{
			String comando = "SELECT rc.* FROM ENOC.RES_COMENTARIO rc "
							+"INNER JOIN ( SELECT CODIGO_PERSONAL, COALESCE(MAX(FOLIO), 0) AS MAX_FOLIO FROM ENOC.RES_COMENTARIO GROUP BY CODIGO_PERSONAL) agg "
							+"ON rc.CODIGO_PERSONAL = agg.CODIGO_PERSONAL AND rc.FOLIO = agg.MAX_FOLIO";
			lista = enocJdbc.query(comando, new ResComentarioMapper());			
			for(ResComentario comentario : lista){				
				mapa.put(comentario.getCodigoPersonal(), comentario);					
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResComentario|mapMaxiComentario|:"+ex);
		}
		
		return mapa;
	}

	public HashMap<String, String> mapMaxiComentario( String orden) {
		
		HashMap<String,String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COALESCE(MAX(FOLIO),0) AS VALOR FROM ENOC.RES_COMENTARIO GROUP BY CODIGO_PERSONAL"+orden;
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResComentario|mapMaxiComentario|:"+ex);
		}
		
		return mapa;
	}
}