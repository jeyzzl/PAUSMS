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
public class ArchGrupoDocumentoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( ArchGrupoDocumento grupo){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.ARCH_GRUPO_DOCUMENTO(GRUPO_ID, DOCUMENTO_ID) VALUES(TO_NUMBER(?,'99'), TO_NUMBER(?,'999'))";
			Object[] parametros = new Object[] {grupo.getGrupoId(),grupo.getDocumentoId()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGrupoDocumentoDao|insertReg|:"+ex);
		}
		return ok;
	}	

	public boolean deleteReg( String grupoId, String documentoId ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ARCH_GRUPO_DOCUMENTO WHERE GRUPO_ID = TO_NUMBER(?,'99') AND DOCUMENTO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {grupoId, documentoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGrupoDocumentoDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public ArchGrupoDocumento mapeaRegId( String grupoId, String documentoId) {
		ArchGrupoDocumento archGrup = new ArchGrupoDocumento();				
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ARCH_GRUPO_DOCUMENTO WHERE GRUPO_ID = TO_NUMBER(?,'99') AND DOCUMENTO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {grupoId, documentoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				comando = "SELECT GRUPO_ID, DOCUMENTO_ID FROM ENOC.ARCH_GRUPO_DOCUMENTO WHERE WHERE GRUPO_ID = TO_NUMBER(?,'99') AND DOCUMENTO_ID = TO_NUMBER(?,'999')";			
				archGrup = enocJdbc.queryForObject(comando, new ArchGrupoDocumentoMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGrupoDocumentoDao|mapeaRegId|:"+ex);
		}
		return archGrup;
	}

	public boolean existeReg( String grupoId, String documentoId ) {
		boolean ok 			= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ARCH_GRUPO_DOCUMENTO WHERE GRUPO_ID = TO_NUMBER(?,'99') AND DOCUMENTO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {grupoId, documentoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGrupoDocumentoDao|existeReg|:"+ex);
		}
		return ok;
	}	

	public List<ArchGrupoDocumento> listTodos( String orden ) {
		List<ArchGrupoDocumento> lista 	= new ArrayList<ArchGrupoDocumento>();
		try{
			String comando = "SELECT GRUPO_ID, DOCUMENTO_ID FROM ENOC.ARCH_GRUPO_DOCUMENTO "+orden;			
			lista = enocJdbc.query(comando, new ArchGrupoDocumentoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGrupoDocumentoDao|lisTodos|:"+ex);
		}
		return lista;
	}	
	
	public List<ArchGrupoDocumento> lisPorGrupo(String grupoId, String orden) {
		List<ArchGrupoDocumento> lista 	= new ArrayList<ArchGrupoDocumento>();
		try{
			String comando = "SELECT GRUPO_ID, DOCUMENTO_ID FROM ENOC.ARCH_GRUPO_DOCUMENTO WHERE GRUPO_ID = TO_NUMBER(?,'99') "+orden;			
			lista = enocJdbc.query(comando, new ArchGrupoDocumentoMapper(), new Object[] {grupoId});
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGrupoDocumentoDao|lisPorGrupo|:"+ex);
		}
		return lista;
	}	
	
	public HashMap<String,ArchGrupoDocumento> mapaArchGrupoDocumento(String grupoId) {
		List<ArchGrupoDocumento> lista 	= new ArrayList<ArchGrupoDocumento>();
		HashMap<String,ArchGrupoDocumento> mapa = new HashMap<String,ArchGrupoDocumento>();
		
		try{
			String comando = "SELECT GRUPO_ID, DOCUMENTO_ID FROM ENOC.ARCH_GRUPO_DOCUMENTO WHERE GRUPO_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {grupoId};
			lista = enocJdbc.query(comando, new ArchGrupoDocumentoMapper(), parametros);
			
			for(ArchGrupoDocumento grupo : lista) {
				mapa.put(grupo.getDocumentoId(), grupo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGrupoDocumentoDao|mapaArchGrupoDocumento|:"+ex);
		}
		return mapa;
	}	
	
	public HashMap<String,String> mapaArchGrupoDocumentoPorGrupo() {
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();
		
		try{
			String comando = "SELECT GRUPO_ID AS LLAVE, COUNT(DOCUMENTO_ID) AS VALOR FROM ENOC.ARCH_GRUPO_DOCUMENTO GROUP BY GRUPO_ID";			
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGrupoDocumentoDao|mapaArchGrupoDocumentoPorGrupo|:"+ex);
		}
		return mapa;
	}	

}