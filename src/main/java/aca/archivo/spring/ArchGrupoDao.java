//Clase  para la tabla ARCH_GRUPOS	
package aca.archivo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArchGrupoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( ArchGrupo grupo){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.ARCH_GRUPO(GRUPO_ID, GRUPO_NOMBRE, COMENTARIO) VALUES(TO_NUMBER(?,'99'), ?, ?)";
			Object[] parametros = new Object[] {grupo.getGrupoId(),grupo.getGrupoNombre(),grupo.getComentario()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGrupoDao|insertReg|:"+ex);
		}
		return ok;
	}

	public boolean updateReg( ArchGrupo grupo ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ARCH_GRUPO SET GRUPO_NOMBRE = ?, COMENTARIO = ? WHERE GRUPO_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {grupo.getGrupoNombre(), grupo.getComentario(), grupo.getGrupoId()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGrupoDao|updateReg|:"+ex);
		}
		return ok;
	}

	public boolean deleteReg( String grupoId ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ARCH_GRUPO WHERE GRUPO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {grupoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGrupoDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public ArchGrupo mapeaRegId( String grupoId) {
		ArchGrupo archGrup = new ArchGrupo();				
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ARCH_GRUPO WHERE GRUPO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {grupoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				comando = "SELECT GRUPO_ID, GRUPO_NOMBRE, COMENTARIO FROM ENOC.ARCH_GRUPO WHERE GRUPO_ID = TO_NUMBER(?,'99')";
				archGrup = enocJdbc.queryForObject(comando, new ArchGrupoMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGrupoDao|mapeaRegId|:"+ex);
		}
		return archGrup;
	}

	public boolean existeReg( String grupoId ) {
		boolean ok 			= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ARCH_GRUPO WHERE GRUPO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {grupoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGrupoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maxReg() {
		String maximo			= "0";
		try{
			String comando = "SELECT COALESCE(MAX(GRUPO_ID)+1, 1) AS MAXIMO FROM ENOC.ARCH_GRUPO";
			if (enocJdbc.queryForObject(comando,Integer.class)>=1){
				maximo = enocJdbc.queryForObject(comando,String.class);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGrupoDao|maxReg|:"+ex);
		}		
		return String.valueOf(maximo);
	}

	public List<ArchGrupo> listTodos( String orden ) {
		List<ArchGrupo> lista 	= new ArrayList<ArchGrupo>();
		try{
			String comando = "SELECT GRUPO_ID, GRUPO_NOMBRE, COMENTARIO FROM ENOC.ARCH_GRUPO "+orden;
			lista = enocJdbc.query(comando, new ArchGrupoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGrupoDao|lisTodos|:"+ex);
		}
		return lista;
	}	

	public HashMap<String,ArchGrupo> mapaArchGrupo() {
		List<ArchGrupo> lista 	= new ArrayList<ArchGrupo>();
		HashMap<String,ArchGrupo> mapa = new HashMap<String,ArchGrupo>();
		try{
			String comando = "SELECT GRUPO_ID, GRUPO_NOMBRE, COMENTARIO FROM ENOC.ARCH_GRUPO";
			lista = enocJdbc.query(comando, new ArchGrupoMapper());
			
			for(ArchGrupo grupo : lista) {
				mapa.put(grupo.getGrupoId(), grupo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGrupoDao|mapaArchGrupo|:"+ex);
		}
		return mapa;
	}	

}