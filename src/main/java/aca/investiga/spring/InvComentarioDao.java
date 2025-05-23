package aca.investiga.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class InvComentarioDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public boolean insertReg(InvComentario invComentario ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.INV_COMENTARIO(PROYECTO_ID, FOLIO, FECHA, CODIGO_PERSONAL, TIPO, COMENTARIO) "
					+ "VALUES(?, TO_NUMBER(?,'999'), TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?)";
			
			Object[] parametros = new Object[] {
				invComentario.getProyectoId(),invComentario.getFolio(),invComentario.getFecha(),invComentario.getCodigoPersonal(),
				invComentario.getTipo(),invComentario.getComentario()
			};
				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvComentarioDao|insertReg|:"+ex);
		}

		return ok;
	}
	
	public boolean updateReg(InvComentario invComentario) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.INV_COMENTARIO "
					+ " SET FECHA = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " CODIGO_PERSONAL = ?, "
					+ " TIPO = ?, "
					+ " COMENTARIO = ? "
					+ " WHERE PROYECTO_ID = ? "
					+ " AND FOLIO = TO_NUMBER(?,'999') ";
			
			Object[] parametros = new Object[] {
				invComentario.getFecha(),invComentario.getCodigoPersonal(),invComentario.getTipo(),invComentario.getComentario(),
				invComentario.getProyectoId(),invComentario.getFolio()
			};
				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvComentarioDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg(String proyectoId, String folio ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.INV_COMENTARIO WHERE PROYECTO_ID = ? AND FOLIO = TO_NUMBER(?,'999')"; 
			
			Object[] parametros = new Object[] {proyectoId, folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvComentarioDao|deleteReg|:"+ex);
		}

		return ok;
	}
	
	public InvComentario mapeaRegId(String proyectoId, String folio) {	
		InvComentario objeto = new InvComentario();
		
		try{ 
			String comando = "SELECT PROYECTO_ID, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, CODIGO_PERSONAL, TIPO, COMENTARIO"
					+ " FROM ENOC.INV_COMENTARIO WHERE PROYECTO_ID = ? AND FOLIO = TO_NUMBER(?,'999')"; 
			
			Object[] parametros = new Object[] { proyectoId, folio };
			objeto = enocJdbc.queryForObject(comando, new InvComentarioMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvComentarioDao|mapeaRegId|:"+ex);
		}

		return objeto;
	}
	
	public boolean existeReg(String proyectoId, String folio) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT * FROM ENOC.INV_COMENTARIO WHERE PROYECTO_ID = ? AND FOLIO = TO_NUMBER(?,'999') "; 
			
			Object[] parametros = new Object[] {proyectoId, folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvComentarioDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg(String proyectoId) {
		String maximo = "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.INV_COMENTARIO WHERE PROYECTO_ID = ?"; 

			Object[] parametros = new Object[] {proyectoId};
			maximo = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvComentarioDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}	

	public List<InvComentario> getListAll(String orden ) {
		List<InvComentario> lista	= new ArrayList<InvComentario>();
		
		try{
			String comando = "SELECT PROYECTO_ID, FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, CODIGO_PERSONAL, TIPO, COMENTARIO FROM ENOC.INV_COMENTARIO "+orden; 
			
			lista = enocJdbc.query(comando, new InvComentarioMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvComentarioDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<InvComentario> getListProyecto(String proyectoId, String orden ){
		List<InvComentario> lista	= new ArrayList<InvComentario>();		
		try{
			String comando = " SELECT PROYECTO_ID, FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, CODIGO_PERSONAL, TIPO, COMENTARIO"
					+ " FROM ENOC.INV_COMENTARIO"
					+ " WHERE PROYECTO_ID = ? "+orden;			
			lista = enocJdbc.query(comando, new InvComentarioMapper(), proyectoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvComentarioDao|getListProyecto|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, String> getComentarios(){
		HashMap<String, String> map		= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT PROYECTO_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.INV_COMENTARIO GROUP BY PROYECTO_ID ";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for(aca.Mapa mapa : lista){
				map.put(mapa.getLlave(), mapa.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvComentarioDao|getComentarios|:"+ex);
		}
		
		return map;
	}

}
