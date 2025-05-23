package aca.acceso.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AccesoValidaDao {
	
	@Autowired	
	@Qualifier("jdbcEnoc")	
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AccesoValida valida){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.ACCESO_VALIDA(DOCUMENTO_ID, CODIGO_PERSONAL, FECHA, LLAVE, CODIGO, TIPO) VALUES(?, ?, TO_DATE(?,'DD/MM/YYYY'), ?, ?, ?)";			
			Object[] parametros = new Object[] {valida.getDocumentoId(), valida.getCodigoPersonal(), valida.getFecha(), valida.getLlave(), valida.getCodigo(), valida.getTipo()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoValidaDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public AccesoValida mapeaRegId(String documentoId){
		AccesoValida valida = new AccesoValida();
		try{
			String comando = "SELECT DOCUMENTO_ID, CODIGO_PERSONAL, TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, LLAVE, CODIGO, TIPO"
					+ " FROM ENOC.ACCESO_VALIDA WHERE DOCUMENTO_ID = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {documentoId};
			valida = enocJdbc.queryForObject(comando, new AccesoValidaMapper(), parametros);			
		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AccesoValidaDao|mapeaRegId|:"+ex);
 		}
		return valida;
	}

	public AccesoValida mapeaRegId(String codigoPersonal, String llave){
		AccesoValida valida = new AccesoValida();
		try{
			String comando = "SELECT DOCUMENTO_ID, CODIGO_PERSONAL, TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, LLAVE, CODIGO, TIPO"
					+ " FROM ENOC.ACCESO_VALIDA WHERE CODIGO_PERSONAL = ? AND LLAVE = ?";
			Object[] parametros = new Object[] {codigoPersonal, llave};
			valida = enocJdbc.queryForObject(comando, new AccesoValidaMapper(), parametros);			
		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AccesoValidaDao|mapeaRegId|:"+ex);
 		}
		return valida;
	}
	
	public AccesoValida mapeaPorCodigo( String matricula, String codigo ){
		AccesoValida valida = new AccesoValida();
		try{
			String comando = "SELECT DOCUMENTO_ID, CODIGO_PERSONAL, TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, LLAVE, CODIGO, TIPO"
					+ " FROM ENOC.ACCESO_VALIDA WHERE CODIGO_PERSONAL = ? AND CODIGO = ?";
			Object[] parametros = new Object[] {matricula, codigo};
			valida = enocJdbc.queryForObject(comando, new AccesoValidaMapper(), parametros);
		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AccesoValidaDao|mapeaPorCodigo|:"+ex);
 		}
		return valida;
	}
	
	public AccesoValida mapeaPorCodigo( String codigo ){
		AccesoValida valida = new AccesoValida();
		try{
			String comando = "SELECT DOCUMENTO_ID, CODIGO_PERSONAL, TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, LLAVE, CODIGO, TIPO"
					+ " FROM ENOC.ACCESO_VALIDA WHERE CODIGO = ?";
			Object[] parametros = new Object[] {codigo};
			valida = enocJdbc.queryForObject(comando, new AccesoValidaMapper(), parametros);
		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AccesoValidaDao|mapeaPorCodigo|:"+ex);
 		}
		return valida;
	}
	
	public boolean existeDocumento(String documentoId){
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO_VALIDA WHERE DOCUMENTO_ID = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {documentoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoValidaDao|existeDocumento|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeReg(String documentoId, String codigoPersonal, String llave){
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO_VALIDA WHERE DOCUMENTO_ID = ? AND CODIGO_PERSONAL = ? AND LLAVE = ?";
			Object[] parametros = new Object[] {documentoId,codigoPersonal, llave};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoValidaDao|existeReg|:"+ex);
		}
		
		return ok;
	}	
	
	public boolean existeCodigo(String matricula, String codigo){
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO_VALIDA WHERE CODIGO_PERSONAL = ? AND CODIGO = ?";
			Object[] parametros = new Object[] {matricula, codigo};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoValidaDao|existeCodigo|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeCodigo(String codigo){
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO_VALIDA WHERE CODIGO = ?";
			Object[] parametros = new Object[] {codigo};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoValidaDao|existeCodigo|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeCredencialReg(String codigoPersonal, String llave){
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO_VALIDA WHERE CODIGO_PERSONAL = ? AND LLAVE = ?";
			Object[] parametros = new Object[] {codigoPersonal, llave};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoValidaDao|existeCredencialReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maxDocId(String codigoPersonal){
		String folio = "0";
		
		try{
			String comando = "SELECT COALESCE(MAX(DOCUMENTO_ID)+1,1) AS DOCUMENTO_ID FROM ENOC.ACCESO_VALIDA WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				folio = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoValidaDao|maxDocId|:"+ex);
		}
		
		return folio;
	}
	
	public HashMap<String, String> mapaCredencialCodigo() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT LLAVE AS LLAVE, CODIGO AS VALOR FROM ENOC.ACCESO_VALIDA";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoValidaDao|mapaCredencialCodigo|:"+ex);
		}
		return mapa;
	}
	
}
