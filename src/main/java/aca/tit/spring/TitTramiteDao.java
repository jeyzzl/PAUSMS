package aca.tit.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TitTramiteDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(TitTramite titTramite ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.TIT_TRAMITE (TRAMITE_ID, FECHA, DESCRIPCION, ESTADO, INSTITUCION, RECIBO) VALUES(TO_NUMBER(?,'9999999'), TO_DATE(?,'YYYY/MM/DD'), ?, ?, ?, ?) ";
			
			Object[] parametros = new Object[] {
				titTramite.getTramiteId(),titTramite.getFecha(),titTramite.getDescripcion(),titTramite.getEstado(), titTramite.getInstitucion(), titTramite.getRecibo()
			};
			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitTramiteDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(TitTramite titTramite) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.TIT_TRAMITE SET DESCRIPCION = ?, FECHA = TO_DATE(?,'YYYY/MM/DD'), ESTADO = ?, INSTITUCION = ?, RECIBO = ?  WHERE TRAMITE_ID = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {
				titTramite.getDescripcion(),titTramite.getFecha(), titTramite.getEstado(),titTramite.getInstitucion(), titTramite.getRecibo(), titTramite.getTramiteId()
			};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitTramiteDao|updateReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean deleteReg(String tramite) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.TIT_TRAMITE WHERE TRAMITE_ID = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {tramite};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitTramiteDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public TitTramite mapeaRegId(String tramite) {
		TitTramite titTramite = new TitTramite();
		 
		try{
			String comando = "SELECT TRAMITE_ID, TO_CHAR(FECHA,'YYYY/MM/DD') AS FECHA, DESCRIPCION, ESTADO, INSTITUCION, RECIBO FROM ENOC.TIT_TRAMITE WHERE TRAMITE_ID = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {tramite};
			titTramite = enocJdbc.queryForObject(comando, new TitTramiteMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitTramiteDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return titTramite;
		
	}	
	
	public boolean existeReg(String tramite) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_TRAMITE WHERE TRAMITE_ID = TO_NUMBER(?,'9999999')"; 
			
			Object[] parametros = new Object[] {tramite};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitTramiteDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String getInstitucion(String tramite) {
		String institucion = "";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_TRAMITE WHERE TRAMITE_ID = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {tramite};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){
				comando = "SELECT INSTITUCION FROM ENOC.TIT_TRAMITE WHERE TRAMITE_ID = ?";
				institucion = enocJdbc.queryForObject(comando, String.class, parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitTramiteDao|getInstitucion|:"+ex);
		}
		return institucion;
	}
	
	public String getNumDoc(String tramite) {
		int total = 0;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_TRAMITE_DOC WHERE TRAMITE_ID = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {tramite};
			total = enocJdbc.queryForObject(comando, Integer.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitTramiteDao|getNumDoc|:"+ex);
		}
		return String.valueOf(total);
	}
	
	public String maximoFolio() {		
		String folio 	= "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(TRAMITE_ID)+1, 1) FROM ENOC.TIT_TRAMITE"; 
					
			Object[] parametros = new Object[] {};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				folio = enocJdbc.queryForObject(comando, String.class, parametros);
			}		
 			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitTramiteDao|maximoFolio|:"+ex);
		}
		
		return folio;
	}
	
	public List<TitTramite> lisInstitucion( String institucion, String estado, String orden) {
		List<TitTramite> lista		= new ArrayList<TitTramite>();
		try{
			String comando = " SELECT TRAMITE_ID, TO_CHAR(FECHA,'YYYY/MM/DD') AS FECHA, DESCRIPCION, ESTADO, INSTITUCION, RECIBO FROM ENOC.TIT_TRAMITE"
					+ " WHERE INSTITUCION = ? AND ESTADO = ? "+orden;
			Object[] parametros = new Object[] {institucion, estado};
			lista = enocJdbc.query(comando, new TitTramiteMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitTramiteDao|lisInstitucion|:"+ex);
		}
		return lista;
	}

}
