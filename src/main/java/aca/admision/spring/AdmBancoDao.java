package aca.admision.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmBancoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AdmBanco objeto) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO SALOMON.ADM_BANCO"
					+ " (FOLIO, BANCO, BANCO_RAMA, CUENTA_NOMBRE, CUENTA_NUMERO, NUMERO_BBS, CUENTA_TIPO, CODIGO_SWIFT)"
					+ " VALUES(TO_NUMBER(?,'99999999'), ?, ?, ?, ?, ?, ?, ?)";
			
			Object[] parametros = new Object[] {
				objeto.getFolio(),objeto.getBanco(),objeto.getBancoRama(),objeto.getCuentaNombre(),objeto.getCuentaNumero(),objeto.getNumeroBbs(),objeto.getCuentaTipo(),objeto.getCodigoSwift()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmBancoDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	
	public boolean updateReg(AdmBanco objeto) {
		boolean ok = false;		
		try{
			String comando = "UPDATE SALOMON.ADM_BANCO"
					+ " SET BANCO = ?,"
					+ " BANCO_RAMA = ?,"
					+ " CUENTA_NOMBRE = ?,"
					+ " CUENTA_NUMERO = ?,"
					+ " NUMERO_BBS = ?,"
					+ " CUENTA_TIPO = ?,"
					+ " CODIGO_SWIFT = ?"
					+ " WHERE FOLIO = TO_NUMBER(?,'99999999')";			
			Object[] parametros = new Object[] {
				objeto.getBanco(),objeto.getBancoRama(),objeto.getCuentaNombre(),objeto.getCuentaNumero(),objeto.getNumeroBbs(), objeto.getCuentaTipo(), objeto.getCodigoSwift(), objeto.getFolio()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmBancoDao|updateReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(String folio) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM SALOMON.ADM_BANCO WHERE FOLIO = TO_NUMBER(?,'99999999')";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmBancoDao|deleteReg|:"+ex);
		}		
		return ok;
	}
	
	public AdmBanco mapeaRegId(String folio) {
		AdmBanco objeto = new AdmBanco();		
		try {
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_BANCO WHERE FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = " SELECT FOLIO, BANCO, BANCO_RAMA, CUENTA_NOMBRE, CUENTA_NUMERO, NUMERO_BBS, CUENTA_TIPO, CODIGO_SWIFT"
						+ " FROM SALOMON.ADM_BANCO WHERE FOLIO = TO_NUMBER(?,'9999999')";
				objeto = enocJdbc.queryForObject(comando, new AdmBancoMapper(),parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmBancoDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String folio){
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_BANCO WHERE FOLIO = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmBancoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public HashMap<String, AdmBanco> mapaTodos (){
		HashMap<String, AdmBanco> mapa = new HashMap<String, AdmBanco>();
		List<AdmBanco> lista = new ArrayList<AdmBanco>();		
		try {
			String comando = "SELECT FOLIO, BANCO, BANCO_RAMA, CUENTA_NOMBRE, CUENTA_NUMERO, NUMERO_BBS, CUENTA_TIPO, CODIGO_SWIFT FROM SALOMON.ADM_BANCO";
			lista = enocJdbc.query(comando, new AdmBancoMapper());
			for (AdmBanco aca : lista ){
				mapa.put(aca.getFolio(), aca);
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmBancoDao|mapaTodos|:"+ex);
		}
		return mapa;
	}

}
