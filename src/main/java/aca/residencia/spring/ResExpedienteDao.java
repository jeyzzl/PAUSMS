package aca.residencia.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ResExpedienteDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(ResExpediente objeto){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.RES_EXPEDIENTE (FOLIO, DESCRIPCION, ESTADO, FECHA, PLAN_ID, CODIGO_PERSONAL,COMENTARIO) "
					+ " VALUES(TO_NUMBER(?,'999999'), ?, ?, NOW(), ?, ?, ?)";
			Object[] parametros = new Object[] {
				objeto.getFolio(), objeto.getDescripcion(), objeto.getEstado(),objeto.getPlanId(),objeto.getCodigoPersonal(),objeto.getComentario()
			};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResExpedienteDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(ResExpediente objeto ){
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.RES_EXPEDIENTE" 
				+ " SET DESCRIPCION = ?,"
				+ " PLAN_ID = ?,"
				+ " COMENTARIO = ?,"
				+ " FECHA = NOW()"
				+ " WHERE FOLIO = TO_NUMBER(?,'999999')";
			Object[] parametros = new Object[] {
				objeto.getDescripcion(),objeto.getPlanId(),objeto.getComentario(),objeto.getFolio()
			};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResExpedienteDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean updateEstado(String folio, String estado){
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.RES_EXPEDIENTE SET ESTADO = ? WHERE FOLIO = TO_NUMBER(?,'999999')";
			Object[] parametros = new Object[] {estado,folio};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResExpedienteDao|updateEstado|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg(String folio){
		boolean ok = false;
		try{
			String comando = "DELETE ENOC.RES_EXPEDIENTE WHERE FOLIO = TO_NUMBER(?,'999999')";
			Object[] parametros = new Object[] {
				folio
			};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResExpedienteDao|deleteReg|:"+ex);		
		}
		
		return ok;
	}
	
	public ResExpediente mapeaRegId(String folio) {
		ResExpediente objeto = new ResExpediente();
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.RES_EXPEDIENTE WHERE FOLIO = TO_NUMBER(?,'999999')";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT FOLIO, DESCRIPCION, ESTADO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, PLAN_ID, CODIGO_PERSONAL, COMENTARIO"
						+ " FROM ENOC.RES_EXPEDIENTE WHERE FOLIO = TO_NUMBER(?,'999999')";
				objeto = enocJdbc.queryForObject(comando, new ResExpedienteMapper(), parametros);
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.ResExpedienteDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String folio){
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.RES_EXPEDIENTE WHERE FOLIO = TO_NUMBER(?,'999999')";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResExpedienteDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() {
		String maximo = "1";		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) MAXIMO FROM ENOC.RES_EXPEDIENTE";			
			maximo = enocJdbc.queryForObject(comando, String.class);
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResExpedienteDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public List<ResExpediente> listaPorCodigoPersonal(String codigoPersonal){
		List<ResExpediente> lista = new ArrayList<ResExpediente>();	
		try{
			String comando = "SELECT FOLIO, DESCRIPCION, ESTADO, TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, PLAN_ID, CODIGO_PERSONAL, COMENTARIO"
					+ " FROM ENOC.RES_EXPEDIENTE WHERE CODIGO_PERSONAL = ?"; 
			Object[] parametros = new Object[] {codigoPersonal};			
			lista = enocJdbc.query(comando, new ResExpedienteMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResExpedienteDao|listaPorCodigoPersonal|:"+ex);
		}
		
		return lista;
	}
	
	public List<ResExpediente> listaExpedientes(String estados, String orden){
		List<ResExpediente> lista = new ArrayList<ResExpediente>();	
		try{
			String comando = "SELECT FOLIO, DESCRIPCION, ESTADO, TO_CHAR(FECHA,'YYYY-MM-DD') FECHA, PLAN_ID, CODIGO_PERSONAL, COMENTARIO"
					+ " FROM ENOC.RES_EXPEDIENTE WHERE ESTADO IN ("+estados+") "+orden;			
			lista = enocJdbc.query(comando, new ResExpedienteMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResExpedienteDao|listaExpedientes|:"+ex);
		}
		
		return lista;
	}
	
	public List<ResExpediente> listaExpedientesPorFechas(String estados, String fechaIni, String fechaFin, String orden){
		List<ResExpediente> lista = new ArrayList<ResExpediente>();	
		try{
			String comando = "SELECT FOLIO, DESCRIPCION, ESTADO, TO_CHAR(FECHA,'YYYY-MM-DD') FECHA, PLAN_ID, CODIGO_PERSONAL, COMENTARIO "
					+ " FROM ENOC.RES_EXPEDIENTE"
					+ " WHERE ESTADO IN ("+estados+")"
					+ " AND TO_DATE(TO_CHAR(FECHA,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') "+orden;			
			lista = enocJdbc.query(comando, new ResExpedienteMapper(), fechaIni, fechaFin);			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResExpedienteDao|listaExpedientesPorFechas|:"+ex);
		}
		
		return lista;
	}
}