package aca.saum.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class SaumComidaDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(SaumComida comida ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.SAUM_COMIDA"
					+ " (FOLIO, FECHA, RECETA_ID, COMIDA, RENDIMIENTO) "
					+ " VALUES( TO_NUMBER(?,'9999999'), TO_DATE(?,'DD/MM/YYYY'), TO_NUMBER(?,'9999999'), ?, TO_NUMBER(?,'99999')) ";
			
			Object[] parametros = new Object[] {comida.getFolio(), comida.getFecha(), comida.getRecetaId(), comida.getComida(), comida.getRendimiento() };
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumComidaDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(SaumComida comida ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.SAUM_COMIDA"
				+ " SET FECHA = TO_DATE(?,'DD/MM/YYYY'),"
				+ " RECETA_ID = TO_NUMBER(?,'9999999'),"
				+ " COMIDA =  ?,"
				+ " RENDIMIENTO = TO_NUMBER(?,'99999')"
				+ " WHERE FOLIO = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {comida.getFecha(), comida.getRecetaId(), comida.getComida(), comida.getRendimiento(), comida.getFolio()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumComidaDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg(String folio ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.SAUM_COMIDA WHERE FOLIO = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumComidaDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public SaumComida mapeaRegId(  String folio) {
		SaumComida comida = new SaumComida();
		 
		try{
			String comando = "SELECT FOLIO, FECHA, RECETA_ID, COMIDA, RENDIMIENTO FROM ENOC.SAUM_COMIDA WHERE FOLIO = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {folio};
			comida = enocJdbc.queryForObject(comando, new SaumComidaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumComidaDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return comida;
		
	}	
	
	
	public boolean existeReg(String folio) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAUM_COMIDA WHERE FOLIO = TO_NUMBER(?,'9999999') ";			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumComidaDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg() {
		int maximo = 1;
		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) FROM ENOC.SAUM_COMIDA";
			
			if (enocJdbc.queryForObject(comando,Integer.class)>=1){
				maximo = enocJdbc.queryForObject(comando,Integer.class);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumComidaDao|maximoReg|:"+ex);
		}
		return String.valueOf(maximo);
	}
	
	public List<SaumComida> lisComidas( String fechaIni, String fechaFin, String orden) {
		List<SaumComida> lista		= new ArrayList<SaumComida>();
		String comando		= "";
		
		try{
			comando = " SELECT FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, RECETA_ID, COMIDA, RENDIMIENTO"
					+ " FROM ENOC.SAUM_COMIDA"
					+ " WHERE ENOC.SAUM_COMIDA.FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') "+orden;	 
			Object[] parametros = new Object[] {fechaIni, fechaFin};
			lista = enocJdbc.query(comando, new SaumComidaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumComidaDao|lisReceta|:"+ex);
		}
		return lista;
	}	
	
	public List<SaumComida> lisComidas(String recetaId) {
		List<SaumComida> lista		= new ArrayList<SaumComida>();
		String comando		= "";
		
		try{
			comando = " SELECT FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, RECETA_ID, COMIDA, RENDIMIENTO"
					+ " FROM ENOC.SAUM_COMIDA"
					+ " WHERE ENOC.SAUM_COMIDA.RECETA_ID = TO_NUMBER(?,'9999999')";	 
			Object[] parametros = new Object[] {recetaId};
			lista = enocJdbc.query(comando,  new SaumComidaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.SaumComidaDao|lisReceta|:"+ex);
		}
		return lista;
	}
}