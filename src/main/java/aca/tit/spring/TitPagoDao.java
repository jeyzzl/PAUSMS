package aca.tit.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TitPagoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(TitPago titPago ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.TIT_PAGO (NOMBRE_PAGO VALUES(?) ";
			
			Object[] parametros = new Object[] {
				titPago.getNombrePago()
			};
			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitPagoDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(TitPago titPago) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.TIT_PAGO "
					+ " SET NOMBRE_PAGO = ?"
					+ " WHERE PAGO_ID = TO_NUMBER(?,'9')";
			
			Object[] parametros = new Object[] {
				titPago.getNombrePago(),titPago.getPagoId()
			};
			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitPagoDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg(String titPagoId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.TIT_PAGO WHERE PAGO_ID = TO_NUMBER(?,'9')";
			
			Object[] parametros = new Object[] {titPagoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitPagoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public TitPago mapeaRegId(  String folioId) {
		TitPago titPago = new TitPago();
		 
		try{
		    String comando = "SELECT PAGO_ID,NOMBRE_PAGO FROM ENOC.TIT_PAGO WHERE PAGO_ID = TO_NUMBER(?,'9')";
		 	
			Object[] parametros = new Object[] {folioId};
			titPago = enocJdbc.queryForObject(comando, new TitPagoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitPagoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return titPago;
	}	
	
	public boolean existeReg(String titPagoId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_PAGO WHERE FOLIO = TO_NUMBER(?,'9999999')"; 
			
			Object[] parametros = new Object[] {titPagoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitPagoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public List<TitPago> listAll( String orden) {
		List<TitPago> lista	= new ArrayList<TitPago>();
		
		try{
			String comando = " SELECT PAGO_ID,NOMBRE_PAGO FROM ENOC.TIT_PAGO "+orden;	 
			
			lista = enocJdbc.query(comando, new TitPagoMapper());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitPagoDao|listAll|:"+ex);
		}
		return lista;
	}
	
	public String maximoReg() {
		String maximo = "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(PAGO_ID)+1, 1) AS MAXIMO FROM ENOC.TIT_PAGO"; 
					
 			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitPagoDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public HashMap<String, TitPago> mapaAll( ) {
		HashMap<String,TitPago> mapa = new HashMap<String,TitPago>();	
		List<TitPago> lista			 = new ArrayList<TitPago>();
		
		try{
			String comando	= " SELECT PAGO_ID,NOMBRE_PAGO FROM ENOC.TIT_PAGO";					
			lista = enocJdbc.query(comando, new TitPagoMapper());
			
			for (TitPago titPago : lista){
				mapa.put(titPago.getPagoId(), titPago);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitPagoDao|mapaAll|:"+ex);
		}
		return mapa;
	}

}
