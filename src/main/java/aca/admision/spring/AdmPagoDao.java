package aca.admision.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmPagoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AdmPago objeto) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO SALOMON.ADM_PAGO"
					+ " (FOLIO, CANTIDAD, FECHA, COMENTARIO, METODO, RECIBO, ARCHIVO, NOMBRE)"
					+ " VALUES(TO_NUMBER(?,'99999999'), ?, TO_DATE(?,'DD/MM/YYYY'), ?, ?, ?, ?, ?)";
			
			Object[] parametros = new Object[] {
				objeto.getFolio(),objeto.getCantidad(),objeto.getFecha(),objeto.getComentario(),objeto.getMetodo(),objeto.getRecibo(),objeto.getArchivo(),objeto.getNombre()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPagoDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg(AdmPago objeto) {
		boolean ok = false;		
		try{
			String comando = "UPDATE SALOMON.ADM_PAGO"
					+ " SET CANTIDAD = ?,"
					+ " FECHA = ?,"
					+ " COMENTARIO = ?,"
					+ " METODO = ?,"
					+ " RECIBO = ?,"
					+ " ARCHIVO = ?,"
					+ " NOMBRE = ?"
					+ " WHERE FOLIO = TO_NUMBER(?,'99999999')";			
			Object[] parametros = new Object[] {
				objeto.getCantidad(), objeto.getFecha(),objeto.getComentario(),objeto.getMetodo(),objeto.getRecibo(),objeto.getArchivo(),objeto.getNombre(),objeto.getFolio()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPagoDao|updateReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(String folio) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM SALOMON.ADM_PAGO WHERE FOLIO = TO_NUMBER(?,'99999999')";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPagoDao|deleteReg|:"+ex);
		}		
		return ok;
	}
	
	public AdmPago mapeaRegId(String folio) {
		AdmPago objeto = new AdmPago();		
		try {
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_PAGO WHERE FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = " SELECT FOLIO, CANTIDAD, FECHA, COMENTARIO, METODO, RECIBO, ARCHIVO, NOMBRE"
						+ " FROM SALOMON.ADM_PAGO WHERE FOLIO = TO_NUMBER(?,'9999999')";				
				objeto = enocJdbc.queryForObject(comando, new AdmPagoMapper(),parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPagoDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String folio){
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_PAGO WHERE FOLIO = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPagoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<AdmPago> listPagos (String folio){
		List<AdmPago> lista = new ArrayList<AdmPago>();		
		try {
			String comando = "SELECT FOLIO, CANTIDAD, TO_CHAR(FECHA,'YYYY-MM-DD HH24:MI:SS') AS FECHA, METODO, COMENTARIO, RECIBO, ARCHIVO, NOMBRE"
				+ " FROM SALOMON.ADM_PAGO WHERE FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {folio};
			lista = enocJdbc.query(comando, new AdmPagoMapper(), parametros);
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmPagoDao|listPago|:"+ex);
		}
		return lista;
	}

	public HashMap<String, AdmPago> mapaTodos (){
		HashMap<String, AdmPago> mapa = new HashMap<String, AdmPago>();
		List<AdmPago> lista = new ArrayList<AdmPago>();		
		try {
			String comando = "SELECT FOLIO, CANTIDAD, FECHA, COMENTARIO, METODO, RECIBO, ARCHIVO, NOMBRE FROM SALOMON.ADM_PAGO";
			lista = enocJdbc.query(comando, new AdmPagoMapper());
			for (AdmPago aca : lista ){
				mapa.put(aca.getFolio(), aca);
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmPagoDao|mapaTodos|:"+ex);
		}
		return mapa;
	}

	public HashMap<String, String> mapaExiste (){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try {
			String comando = "SELECT FOLIO AS LLAVE, NOMBRE AS VALOR FROM SALOMON.ADM_PAGO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ){
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmPagoDao|mapaExiste|:"+ex);
		}
		return mapa;
	}
	
}