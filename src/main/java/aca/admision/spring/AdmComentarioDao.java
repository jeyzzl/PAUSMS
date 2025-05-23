package aca.admision.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmComentarioDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AdmComentario admComentario) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO SALOMON.ADM_COMENTARIO"+ 
				"(FOLIO, COMENTARIO_ID, TIPO, USUARIO, FECHA, COMENTARIO, ESTADO ) "+
				"VALUES(TO_NUMBER(?,'9999999'), TO_NUMBER(?,'99'),TO_NUMBER(?,'99'), ?, TO_DATE(?,'DD/MM/YYYY'), ?, ?)";
			
			Object[] parametros = new Object[] {
				admComentario.getFolio(),admComentario.getComentarioId(),admComentario.getTipo(),admComentario.getUsuario(),admComentario.getFecha(),admComentario.getComentario(),admComentario.getEstado()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmComentarioDao|insertReg|:"+ex);
		}

		return ok;
	}
	
	public boolean updateReg(AdmComentario admComentario ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE SALOMON.ADM_COMENTARIO " + 
					" SET TIPO = TO_NUMBER(?,'99'), " +
					" USUARIO = ?, " +
					" FECHA = TO_DATE(?,'DD/MM/YYYY'), COMENTARIO = ?, ESTADO = ?" +				
					" WHERE FOLIO = TO_NUMBER(?,'9999999') AND COMENTARIO_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {
				admComentario.getTipo(),admComentario.getUsuario(),admComentario.getFecha(),admComentario.getComentario(),admComentario.getEstado(),admComentario.getFolio(),admComentario.getComentarioId()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmComentarioDao|updateReg|:"+ex);
		}

		return ok;
	}
	
	public boolean deleteReg(String folio, String comentarioId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM SALOMON.ADM_COMENTARIO "+ 
					" WHERE FOLIO = TO_NUMBER(?,'9999999') AND COMENTARIO_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {folio,comentarioId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmComentarioDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public AdmComentario mapeaRegId(String folio, String comentarioId){
		AdmComentario objeto = new AdmComentario();
		
		try {
			String comando = "SELECT FOLIO, COMENTARIO_ID, TIPO, USUARIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, COMENTARIO, ESTADO " +
					" FROM SALOMON.ADM_COMENTARIO "+ 
					" WHERE FOLIO = TO_NUMBER(?,'9999999') AND COMENTARIO_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {folio,comentarioId};
			objeto = enocJdbc.queryForObject(comando, new AdmComentarioMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmComentarioDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String folio, String comentarioId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_COMENTARIO "+ 
					" WHERE FOLIO = TO_NUMBER(?,'9999999') AND COMENTARIO_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {folio,comentarioId};
			
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmComentarioDao|existeReg|:"+ex);
		}
		
		return ok;
	}

	public boolean tieneComentarios(String folio) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_COMENTARIO "+ 
					" WHERE FOLIO = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmComentarioDao|tieneComentarios|:"+ex);
		}
		
		return ok;
	}

	public int cantidadComentarios(String folio, String estados) {
		int cantidad = 0;
		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_COMENTARIO WHERE FOLIO = TO_NUMBER(?,'9999999') AND ESTADO IN ("+estados+")";	
			Object[] parametros = new Object[] {folio};
			cantidad = enocJdbc.queryForObject(comando,Integer.class,parametros);	
			
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmComentarioDao|cantidadComentarios|:"+ex);
		}
		
		return cantidad;
	}
	
	public String maximoReg(){
		String maximo = "1";		
		try{
			String comando = "SELECT COALESCE(MAX(COMENTARIO_ID)+1,1) FROM SALOMON.ADM_COMENTARIO";			
			maximo = enocJdbc.queryForObject(comando,String.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmComentarioDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public List<AdmComentario> getAll(String folio, String orden) {
		List<AdmComentario> lista = new ArrayList<AdmComentario>();
		
		try{
			String comando = "SELECT FOLIO, COMENTARIO_ID, TIPO, USUARIO, FECHA, COMENTARIO, ESTADO " +
			" FROM SALOMON.ADM_COMENTARIO WHERE FOLIO = TO_NUMBER(?,'9999999')"+ orden; 
			
			Object[] parametros = new Object[] {folio};
			lista = enocJdbc.query(comando, new AdmComentarioMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmComentarioDao|getAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, String> mapaComentarios(String estados){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		
		try {
			String comando = "SELECT FOLIO AS LLAVE,COUNT(*) AS VALOR FROM SALOMON.ADM_COMENTARIO WHERE ESTADO IN ("+estados+") GROUP BY FOLIO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ){
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmComentarioDao|mapaComentarios|:"+ex);
		}
		return mapa;
	}

}
