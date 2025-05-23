package aca.alumno.spring;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.catalogo.spring.CatBanco;

@Component
public class AlumBancoDao {
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	
	public boolean insertReg( AlumBanco banco) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ALUM_BANCO(BANCO_ID, CODIGO_PERSONAL, BANCO_RAMA, CUENTA_NOMBRE, CUENTA_NUMERO, CUENTA_TIPO, NUMERO_BBS, CODIGO_SWIFT)"
					+ " VALUES( ?, ?, ?, ?, ? ,? ,?, ? )";
			Object[] parametros = new Object[] {banco.getBancoId(),banco.getCodigoPersonal(), 
					banco.getBancoRama(), banco.getCuentaNombre(), banco.getCuentaNumero(), banco.getCuentaTipo(),
					banco.getNumeroBBS(), banco.getCodigoSwift()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.AlumBancoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean updateReg( AlumBanco banco ) {
		boolean ok = false;		
		
		try{
			String comando = "UPDATE ENOC.ALUM_BANCO SET BANCO_RAMA = ?, CUENTA_NOMBRE = ?, CUENTA_NUMERO = ?, NUMERO_BBS = ?, CUENTA_TIPO = ?, CODIGO_SWIFT = ? WHERE BANCO_ID = ? AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {banco.getBancoRama(), banco.getCuentaNombre(), banco.getCuentaNumero(), banco.getNumeroBBS(), banco.getCuentaTipo(), banco.getCodigoSwift(), banco.getBancoId(), banco.getCodigoPersonal()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			 
			System.out.println("Error - aca.catalogo.spring.AlumBancoDao|updateReg|:"+ex);
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String codigoAlumno ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ALUM_BANCO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoAlumno};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.AlumBancoDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public AlumBanco mapeaRegId(String codigoAlumno) {
		
		AlumBanco banco 	= new AlumBanco();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_BANCO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoAlumno};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT BANCO_ID, CODIGO_PERSONAL, BANCO, BANCO_RAMA, CUENTA_NOMBRE, CUENTA_NUMERO, NUMERO_BBS, CUENTA_TIPO, CODIGO_SWIFT FROM ENOC.ALUM_BANCO WHERE CODIGO_PERSONAL = ?";
				banco = enocJdbc.queryForObject(comando, new AlumBancoMapper(), parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.AlumBancoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return banco;
	}
	
	public boolean existeReg( String codigoAlumno) {
		boolean 		ok 		= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_BANCO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoAlumno};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.AlumBancoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() {
		String maximo 			= "01";		
		try{
			String comando = "SELECT COALESCE(MAX(BANCO_ID)+1,1) AS MAXIMO FROM ENOC.ALUM_BANCO";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class);
				if (maximo.length()==1) maximo = "0"+maximo;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.AlumBancoDao|maximoReg|:"+ex);
		}
		
		return maximo;		
	}
	
	// Llena el listor con todos los elementos de la tabla	
	public List<AlumBanco> lisTodos( String orden ) {
		
		List<AlumBanco> lista = new ArrayList<AlumBanco>();		
		try{
			String comando = "SELECT BANCO_ID, CODIGO_PERSONAL, BANCO, BANCO_RAMA, CUENTA_NOMBRE, NUMERO_BBS, CUENTA_TIPO, CODIGO_SWIFT FROM ENOC.ALUM_BANCO "+ orden;				
			lista = enocJdbc.query(comando, new AlumBancoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.AlumBancoDao|lisTodos|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String, AlumBanco> mapaAlumBanco(){
		List<AlumBanco> lista				= new ArrayList<AlumBanco>();
		HashMap<String, AlumBanco> mapa 	= new HashMap<String, AlumBanco>();
		try {
			String comando = "SELECT BANCO_ID, CODIGO_PERSONAL, BANCO, BANCO_RAMA, CUENTA_NOMBRE,"
					+ " CUENTA_NUMERO, NUMERO_BBS, CUENTA_TIPO, CODIGO_SWIFT"
					+ " FROM ENOC.ALUM_BANCO"
					+ " WHERE CODIGO_PERSONAL IN"
					+ " (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)";
			lista = enocJdbc.query(comando,new AlumBancoMapper());
			for(AlumBanco objeto : lista){				
				mapa.put(objeto.getCodigoPersonal(), objeto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumBancoDao|mapaAlumBanco|:"+ex);
		}		
		return mapa;
	} 
}
