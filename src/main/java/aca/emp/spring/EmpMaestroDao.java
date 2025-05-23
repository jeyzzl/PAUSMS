package aca.emp.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class EmpMaestroDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( EmpMaestro empMaestro ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.EMP_MAESTRO"
				+ "(CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,  F_NACIMIENTO, "
				+ "GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO ) "
 				+ "VALUES( ?, UPPER(?), UPPER(?), UPPER(?),  TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?, ?, ?)";
			
			Object[] parametros = new Object[] {empMaestro.getCodigoPersonal(),empMaestro.getNombre(),empMaestro.getApellidoPaterno(),empMaestro.getApellidoMaterno(),empMaestro.getFNacimiento(),empMaestro.getGenero(),empMaestro.getEstadoCivil(),empMaestro.getTelefono(),empMaestro.getEmail(),empMaestro.getEstado()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpMaestroDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( EmpMaestro empMaestro ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EMP_MAESTRO "
					+ "SET F_NACIMIENTO = TO_DATE(?, 'DD/MM/YYYY') , "
					+ "NOMBRE = UPPER(?), "
					+ "APELLIDO_PATERNO = UPPER(?), "
					+ "APELLIDO_MATERNO = UPPER(?), "
					+ "GENERO = ?, "
					+ "ESTADOCIVIL = ?, "
					+ "TELEFONO = ?, "
					+ "EMAIL = ?, "
					+ "ESTADO = ? "
					+ "WHERE CODIGO_PERSONAL = ? ";
			
			Object[] parametros = new Object[] {empMaestro.getFNacimiento(),empMaestro.getNombre(),empMaestro.getApellidoPaterno(),empMaestro.getApellidoMaterno(),empMaestro.getGenero(),empMaestro.getEstadoCivil(),empMaestro.getTelefono(),empMaestro.getEmail(),empMaestro.getEstado(),empMaestro.getCodigoPersonal()};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpMaestroDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	
	public boolean deleteReg( String codigoPersonal ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.EMP_MAESTRO "
					+ "WHERE CODIGO_PERSONAL = ? ";
			
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpMaestroDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public EmpMaestro mapeaRegId(  String codigoPersonal) {
		EmpMaestro empMaestro = new EmpMaestro();
		
		try{
			String comando = "SELECT "+
				" CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO," +
				" TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO "+  
				" FROM ENOC.EMP_MAESTRO "+ 
				" WHERE CODIGO_PERSONAL = ? ";
			
				Object[] parametros = new Object[] {codigoPersonal};
				empMaestro = enocJdbc.queryForObject(comando, new EmpMaestroMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpMaestroDao|mapeaRegId|:"+ex);
		}
		return empMaestro;
	}
	
	public boolean existeReg( String codigoPersonal) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EMP_MAESTRO "+ 
				"WHERE CODIGO_PERSONAL = ? ";
			
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpMaestroDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean existeRegId( String codigoPersonal) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EMP_MAESTRO "+ 
				"WHERE CODIGO_PERSONAL = ? ";
			
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpMaestroDao|existeRegId|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg() {
		String maximo 			= "1";
			
		try{
			String comando = "SELECT COALESCE(MAX(CODIGO_PERSONAL)+1,1) MAXIMO FROM ENOC.EMP_MAESTRO"; 
			
			Object[] parametros = new Object[] {};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpMaestroDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public String ultimoReg() {
		String maximo 			= "1";		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.EMP_MAESTRO";			
 			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
 				comando = "SELECT COALESCE(MAX(CODIGO_PERSONAL),'1') MAXIMO FROM ENOC.EMP_MAESTRO";
 				maximo = enocJdbc.queryForObject(comando, String.class);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpMaestroDao|ultimoReg|:"+ex);
		}
		return maximo;
	}
	
	public boolean esMaestro( String codigoPersonal) {
		boolean ok 				= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EMP_MAESTRO WHERE CODIGO_PERSONAL = ?";
			
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpMaestroDao|esMaestro|:"+ex);
		}
		return ok;
	}

	public List<EmpMaestro> getListAll( String orden ) {
		List<EmpMaestro> lista	= new ArrayList<EmpMaestro>();
		String comando                     	= "";
			
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO," +
					"APELLIDO_MATERNO, " +
					"TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL, " +
					" TELEFONO, EMAIL, ESTADO FROM ENOC.EMP_MAESTRO "+ orden; 
				
			lista = enocJdbc.query(comando, new EmpMaestroMapper());
				
		}catch(Exception ex){
				System.out.println("Error - aca.emp.spring.EmpMaestroDao|getListAll|:"+ex);
		}	
		return lista;
	}
		
	public TreeMap<String,EmpMaestro> getTreeAll( String orden ) {
		List<EmpMaestro> lista	= new ArrayList<EmpMaestro>();		
		TreeMap<String,EmpMaestro> mapa	= new TreeMap<String, EmpMaestro>();
		String comando	             		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, " +
				" TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL," +
				" TELEFONO, EMAIL, ESTADO FROM ENOC.EMP_MAESTRO "+ orden; 
			
			lista = enocJdbc.query(comando, new EmpMaestroMapper());
			
			for (EmpMaestro estado : lista){			
				mapa.put(estado.getCodigoPersonal(), estado);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpMaestro|getTreeAll|:"+ex);
		}
		return mapa;
	}	
	public HashMap<String, String> mapaMtrosConMaterias() {
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa		= new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.CARGA_GRUPO GROUP BY CODIGO_PERSONAL";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapaMtrosConMaterias|:"+ex);
		}
		
		return mapa;
	}	
	
	public HashMap<String, EmpMaestro> mapaEmpMaestro(){
		List<EmpMaestro> lista 			= new ArrayList<EmpMaestro>();
		HashMap<String, EmpMaestro> mapa 	= new HashMap<String,EmpMaestro>();
		try {
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, F_NACIMIENTO, GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO, RFID, RFID_TAG FROM ENOC.EMP_MAESTRO";
			lista = enocJdbc.query(comando, new EmpMaestroMapper());
			for(EmpMaestro maestro : lista) {
				mapa.put(maestro.getCodigoPersonal(), maestro);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapaNombres|:"+ex);
		}
		
		return mapa;
	}

}