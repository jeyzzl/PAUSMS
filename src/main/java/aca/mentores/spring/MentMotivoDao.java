/**
 * 
 */
package aca.mentores.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author etorres
 *
 */

@Component
public class MentMotivoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MentMotivo motivo){
		boolean ok = true;
		
		try{
			String comando = "INSERT INTO ENOC.MENT_MOTIVO" + 
					" (MOTIVO_ID, MOTIVO_NOMBRE, COMENTARIO)" +
					" VALUES( TO_NUMBER(?, '99'), ?, ?)";
			Object[] parametros = new Object[] {motivo.getMotivoId(), motivo.getMotivoNombre(), motivo.getComentario()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentMotivo|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg(MentMotivo motivo){
		boolean ok = true;
		
		try{
			String comando = "UPDATE ENOC.MENT_MOTIVO"+ 
					" SET MOTIVO_NOMBRE = ?, COMENTARIO = ?" +
					" WHERE MOTIVO_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {motivo.getMotivoNombre(), motivo.getComentario(), motivo.getMotivoId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentMotivo|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg(String motivo){
		boolean ok = true;
	
		try{
			String comando = "DELETE FROM ENOC.MENT_MOTIVO"+ 
				" WHERE MOTIVO_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {motivo};
			if (enocJdbc.update(comando, parametros)==1){
				ok = true;
			}
		
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentMotivo|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public MentMotivo mapeaRegId( String idMotivo){
		
		MentMotivo motivo	= new MentMotivo();
		
		try{ 
			String comando = "SELECT MOTIVO_ID, MOTIVO_NOMBRE, COMENTARIO"+
					" FROM ENOC.MENT_MOTIVO" + 
					" WHERE MOTIVO_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {idMotivo};
			motivo = enocJdbc.queryForObject(comando, new MentMotivoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentMotivo|mapeaRegId|:"+ex);
		}		
		
		return motivo;
	}
	
	public boolean existeReg(String motivoId){
		boolean 		ok 		= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MENT_MOTIVO" + 
					" WHERE MOTIVO_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {motivoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentMotivo|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg( ){
		
		String maximo			= "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(MOTIVO_ID)+1,0) AS MAXIMO FROM ENOC.MENT_MOTIVO "; 
			Object[] parametros = new Object[]{};
 			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
 				maximo = enocJdbc.queryForObject(comando,String.class, parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentMotivo|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String getMotivoNombre( String idMotivo){
 		
 		String nombre			= ""; 		
 		try{
 			String comando = "SELECT MOTIVO_NOMBRE FROM ENOC.MENT_MOTIVO " + 
 					"WHERE MOTIVO_ID = TO_NUMBER(?, '99')";
 			Object[] parametros = new Object[]{idMotivo};
 			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
 				nombre = enocJdbc.queryForObject(comando,String.class, parametros);
			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.mentores.MentMotivo|getMotivo|:"+ex);
 		}		
 		
 		return nombre;
 	}
	
	public List<MentMotivo> getListAll(  String orden ){
		
		List<MentMotivo> lista	= new ArrayList<MentMotivo>();		
		try{
			String comando = "SELECT MOTIVO_ID, MOTIVO_NOMBRE, COMENTARIO FROM ENOC.MENT_MOTIVO "+ orden; 
			lista = enocJdbc.query(comando, new MentMotivoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentorMotivoUtil|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, MentMotivo> mapMotivo(){
		
		HashMap<String,MentMotivo> mapa = new HashMap<String,MentMotivo>();
		List<MentMotivo> lista	= new ArrayList<MentMotivo>();		
		try{
			String comando = "SELECT MOTIVO_ID, MOTIVO_NOMBRE, COMENTARIO FROM ENOC.MENT_MOTIVO";
			lista = enocJdbc.query(comando, new MentMotivoMapper());			
			for (MentMotivo objeto: lista){				
				mapa.put(objeto.getMotivoId(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentorMotivoUtil|mapMotivo|:"+ex);
		}
		
		return mapa;
	}
	
}