// Clase para la tabla de Modulo
package aca.alumno.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CancelaEstudioDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CancelaEstudio cancelaEstudio ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CANCELA_ESTUDIO"+ 
				"(CODIGO_PERSONAL, PLAN_ID, USUARIO, FECHA, COMENTARIO, ESTADO) "+
				"VALUES( ?, ?, ?, now(), ?, ?)";
			Object[] parametros = new Object[] {cancelaEstudio.getCodigoPersonal(), cancelaEstudio.getPlanId(),
			cancelaEstudio.getUsuario(), cancelaEstudio.getComentario(), cancelaEstudio.getEstado()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.CancelaEstudioDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( CancelaEstudio cancelaEstudio ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CANCELA_ESTUDIO "+ 
				"SET FECHA = now() , " +
				"COMENTARIO = ?, "+
				"USUARIO = ?, "+
				"ESTADO = ? "+
				"WHERE CODIGO_PERSONAL = ? " +
				"AND PLAN_ID = ?";
			Object[] parametros = new Object[] {cancelaEstudio.getComentario(), cancelaEstudio.getUsuario(),
			cancelaEstudio.getEstado(), cancelaEstudio.getCodigoPersonal(), cancelaEstudio.getPlanId()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.CancelaEstudioDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg( String codigoPersonal, String planId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CANCELA_ESTUDIO "+ 
				"WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			
			Object[] parametros = new Object[] {codigoPersonal, planId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.CancelaEstudioDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public CancelaEstudio mapeaRegId(  String codigoPersonal, String planId) {
		
		CancelaEstudio objeto = new CancelaEstudio();
		
		try{ 
			String comando = "SELECT "+
				"CODIGO_PERSONAL, PLAN_ID, USUARIO, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM') AS FECHA, COMENTARIO, ESTADO "+
				"FROM ENOC.CANCELA_ESTUDIO "+ 
				"WHERE CODIGO_PERSONAL = ? " +
				"AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
			objeto = enocJdbc.queryForObject(comando, new CancelaEstudioMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.CancelaEstudioDao|mapeaRegId|:"+ex);
		}		
		return objeto;
	}
	
	public boolean existeReg( String codigoPersonal, String planId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.CANCELA_ESTUDIO WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.CancelaEstudioDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean existeRegId( String codigoPersonal, String planId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.CANCELA_ESTUDIO WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};			
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.CancelaEstudioDao|existeRegId|:"+ex);
		}
		return ok;
	}
	
	public String getEstado( String codigoPersonal, String planId) {
		String estado = "";
		
		try{
			String comando = "SELECT ESTADO FROM ENOC.CANCELA_ESTUDIO "+ 
				"WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
			estado = enocJdbc.queryForObject(comando,String.class, parametros);
						
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.CancelaEstudioDao|getEstado|:"+ex);
		}
		return estado;
	}
	
	public List<CancelaEstudio> getListAll( String orden ) {
		
		List<CancelaEstudio> lista = new ArrayList<CancelaEstudio>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PLAN_ID, USUARIO, " +
					"TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM') AS FECHA, COMENTARIO, ESTADO "+
			"FROM ENOC.CANCELA_ESTUDIO "+orden;
			lista = enocJdbc.query(comando, new CancelaEstudioMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.CancelaEstudioDao|getListAll|:"+ex);
		}
		return lista;
	}	
		
}