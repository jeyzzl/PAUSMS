package aca.emp.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public Empleado mapeaRegId( String id){
		Empleado empleado = new Empleado();
	
		try{
			String comando = "SELECT "+
				"TO_NUMBER(ID,'9999999') AS ID, CLAVE, NOMBRE, APPATERNO, APMATERNO,  " +
				"TO_CHAR(FECHANACIMIENTO, 'DD/MM/YYYY') AS FECHANACIMIENTO, DIRECCION, GENERO, STATUS, " +
				"TO_NUMBER(NACIONALIDAD,'999') AS NACIONALIDAD "+   
				"FROM ARON.EMPLEADO "+
				"WHERE ID = ?";
			Object[] parametros = new Object[] {id};
			empleado = enocJdbc.queryForObject(comando, new EmpleadoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|mapeaRegId|:"+ex);
		
		}
		return empleado;
	}
	
	public Empleado mapeaRegIdNomina( String clave){
		Empleado empleado = new Empleado();
	
		try{
			String comando = "SELECT "+
				"TO_NUMBER(ID,'9999999') AS ID, CLAVE, NOMBRE, APPATERNO, APMATERNO,  " +
				"TO_CHAR(FECHANACIMIENTO, 'DD/MM/YYYY') AS FECHANACIMIENTO, DIRECCION, GENERO, STATUS, " +
				"TO_NUMBER(NACIONALIDAD,'999') AS NACIONALIDAD "+   
				"FROM ARON.EMPLEADO "+
				"WHERE CLAVE = ?";
			Object[] parametros = new Object[] {clave};
			empleado = enocJdbc.queryForObject(comando, new EmpleadoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|mapeaRegIdNomina|:"+ex);
		
		}
		return empleado;
	}
	
	public boolean updateReg(Empleado empleado){
		boolean ok = false;
		try{
			String comando = "UPDATE ARON.EMPLEADO SET NOMBRE = ?, APPATERNO = ?, APMATERNO = ?"
					+ " WHERE CLAVE = ?";
			Object[] parametros = new Object[] {
				empleado.getNombre(),empleado.getAppaterno(),empleado.getApmaterno(),empleado.getClave()
			};
			if (enocJdbc.update(comando,parametros) == 1){
				ok = true;
			}
		}catch( Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|updateReg:"+ex);
		}
		
		return ok;		
	}
	
	public Empleado mapeaRegClave( String clave){
		Empleado empleado = new Empleado();

		try{
			String comando = "SELECT "+
				"TO_NUMBER(ID,'9999999') AS ID, CLAVE, NOMBRE, APPATERNO, APMATERNO,  " +
				"TO_CHAR(FECHANACIMIENTO, 'DD/MM/YYYY') AS FECHANACIMIENTO, DIRECCION, GENERO, STATUS, " +
				"TO_NUMBER(NACIONALIDAD,'999') AS NACIONALIDAD "+
				"FROM ARON.EMPLEADO "+
				"WHERE CLAVE = ?";
			Object[] parametros = new Object[] {clave};
			empleado = enocJdbc.queryForObject(comando, new EmpleadoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|mapeaRegClave|:"+ex);
		
		}
		return empleado;
	}
	
	public boolean existeReg( String id){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT (*) FROM ARON.EMPLEADO WHERE ID = ?";
			Object[] parametros = new Object[] {id};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|existeReg|:"+ex);

		}
		return ok;
	}
	
	public boolean existeRegId( String id){
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) ID FROM ARON.EMPLEADO WHERE ID = ? ";
			Object[] parametros = new Object[] {id};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|existeRegId|:"+ex);		
		}
		return ok;
	}
	
	public boolean existeRegClave( String clave){
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT (*) CLAVE FROM ARON.EMPLEADO WHERE CLAVE = ? ";
			Object[] parametros = new Object[] {clave};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|existeRegClave|:"+ex);	
		}
		return ok;
	}
	
	public String creditosEmp( String codigoPersonal){
		String 		creditos 	= "";		
		try{
			String comando = "SELECT COUNT(*) FROM NOE.PER_EMPESTUDIOS WHERE MATRICULA=? AND STATUS='A'";
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				comando = "SELECT CREDITOS_AUTORIZADOS FROM NOE.PER_EMPESTUDIOS WHERE MATRICULA=? AND STATUS='A'";
 				creditos = enocJdbc.queryForObject(comando,String.class,parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|creditosEmp|:"+ex);
		
		}
		return creditos;
	}
	
	public String getPuesto( String codigoPersonal){		
		String puesto		= "0";		
		try{
			String comando 	= "SELECT COUNT(ID) FROM ARON.EMPLEADO WHERE CLAVE = ?";
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				comando 	= "SELECT ENOC.EMP_PUESTO2(ID) FROM ARON.EMPLEADO WHERE CLAVE = ?"; 				
 				puesto 		= enocJdbc.queryForObject(comando,String.class,parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|getPuesto|:"+ex);		
		}
		return puesto;
	}
	
	public String getDepartamentoRH( String codigoPersonal){		
		String depto		= "0";		
		try{
			String comando 	= "SELECT COUNT(ID) FROM ARON.EMPLEADO WHERE CLAVE = ?";
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				comando 	= "SELECT ENOC.EMP_CCOSTO(ID) FROM ARON.EMPLEADO WHERE CLAVE = ?";
 				depto 		= enocJdbc.queryForObject(comando,String.class,parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|getPuesto|:"+ex);		
		}
		return depto;
	}	
	
	public String getNombreEmpleado( String codigoPersonal, String opcion){
 		
 		String nombre	= "";
 		String comando 	= "";
 		
 		try{		
 			comando = "SELECT COUNT(*) FROM ARON.EMPLEADO WHERE CLAVE = ?";
 			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				if (opcion.equals("NOMBRE")){
 	 				comando = "SELECT NOMBRE||' '||APPATERNO||' '||APMATERNO AS NOMBRE FROM ARON.EMPLEADO "+
 	 					"WHERE CLAVE = ? ";
 	 			}else if (opcion.equals("APELLIDO")){
 	 				comando = "SELECT APPATERNO||' '||APMATERNO||' '||NOMBRE AS NOMBRE FROM ARON.EMPLEADO "+
 	 					"WHERE CLAVE = ? ";
 	 			}else{
 	 				comando = "SELECT NOMBRE||' '||APPATERNO||' '||APMATERNO AS NOMBRE FROM ARON.EMPLEADO "+
 	 					"WHERE CLAVE = ? ";
 	 			}
 				nombre = enocJdbc.queryForObject(comando,String.class,parametros);
 			}
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.emp.spring.EmpleadoDao|getNombreEmpleado|:"+ex);
 		
 		}
 		return nombre;
 	}
	
	public String getNombreCorto( String codigoPersonal, String opcion){
 		
 		String nombre		= "";
 		String comando		= "";
 		
 		try{ 	
 			comando = "SELECT COUNT(*) FROM ARON.EMPLEADO WHERE CLAVE = ?";	
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				if (opcion.equals("NOMBRE")){
 	 				 comando = "SELECT NOMBRE||' '||APPATERNO AS NOMBRE FROM ARON.EMPLEADO WHERE CLAVE = ? ";
 	 			}else if (opcion.equals("APELLIDO")){
 	 				 comando = "SELECT APPATERNO||' '||NOMBRE AS NOMBRE FROM ARON.EMPLEADO WHERE CLAVE = ? ";
 	 			}else{
 	 				 comando = "SELECT NOMBRE||' '||APPATERNO AS NOMBRE FROM ARON.EMPLEADO WHERE CLAVE = ? ";				
 				}
 				nombre = enocJdbc.queryForObject(comando,String.class,parametros);
 			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.emp.spring.EmpleadoDao|getNombreCorto|:"+ex);
 		
 		}
 		return nombre;
 	}

	public List<Empleado> getListAll( String orden ){
			
			List<Empleado> lista	    	    = new ArrayList<Empleado>();
	
			try{
				String comando = "SELECT TO_NUMBER(ID,'9999999999999999999') AS ID, CLAVE, NOMBRE, APPATERNO," +
						"APMATERNO, " +
						"TO_CHAR(FECHANACIMIENTO, 'DD/MM/YYYY') AS FECHANACIMIENTO, DIRECCION, GENERO, " +
						" STATUS, TO_NUMBER(NACIONALIDAD,'9999999999999999999') AS NACIONALIDAD FROM ARON.EMPLEADO "+ orden;
				lista = enocJdbc.query(comando, new EmpleadoMapper());
				
			}catch(Exception ex){
				System.out.println("Error - aca.emp.spring.EmpleadoDao|getListAll|:"+ex);
			
			}
			
			return lista;
	}
		
	public List<Empleado> getLista( String id, String orden ){
	
		List<Empleado> lista	    = new ArrayList<Empleado>();

		try{
			String comando = "SELECT TO_NUMBER(ID,'9999999999999999999') AS ID, CLAVE, NOMBRE, APPATERNO, APMATERNO, " +
					" TO_CHAR(FECHANACIMIENTO, 'DD/MM/YYYY') AS FECHANACIMIENTO, DIRECCION, GENERO, STATUS, " +
					"TO_NUMBER(NACIONALIDAD,'9999999999999999999') AS NACIONALIDAD FROM ARON.EMPLEADO "+
					" WHERE ID = ? " + orden;
			lista = enocJdbc.query(comando, new EmpleadoMapper(), id);
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|getLista|:"+ex);
		
		}
		
		return lista;
	}
	
	
	
	public String getNacionalidad( String codigoPersonal){
		
		String nacionalidad		= "0";
		
		try{
			String comando = "SELECT NACIONALIDAD FROM EMPLEADO WHERE CLAVE = ? ";
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				nacionalidad = enocJdbc.queryForObject(comando,String.class,parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|getNacionalidad|:"+ex);
		
		}
		
		return nacionalidad;
	}		
	
	public String getTipoEmpleado( String id){
		
		String tipo				= "0";		
		try{
			String comando = "SELECT ID_TIPOEMPLEADO AS TIPO FROM ARON.EMPLEADOLABORALES WHERE ID = TO_NUMBER(?,'9999')";
			Object[] parametros = new Object[] {id};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				tipo = enocJdbc.queryForObject(comando,String.class,parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|getTipoEmpleado|:"+ex);
		
		}
		
		return tipo;
	}
	
	public String getRFC( String codigoPersonal ){
 			
		String rfc 				= "X";
		try{
			String comando = "SELECT COUNT(RFC) FROM ARON.EMPLEADOLABORALES B" +
					" WHERE B.ID = (SELECT ID FROM ARON.EMPLEADO C" +
								  " WHERE C.CLAVE = ?)";
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				comando = "SELECT RFC FROM ARON.EMPLEADOLABORALES B WHERE B.ID = (SELECT ID FROM ARON.EMPLEADO C WHERE C.CLAVE = ?)";
 				rfc = enocJdbc.queryForObject(comando,String.class,parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|getRFC|:"+ex);
		
		}
		
		return rfc;
	}
	
	public String getIMSS( String codigoPersonal ){
 		
		String imss 				= "X";
		try{
			String comando = "SELECT COUNT(IMMS) FROM ARON.EMPLEADOLABORALES B" +
					" WHERE B.ID = (SELECT ID FROM ARON.EMPLEADO C" +
								  " WHERE C.CLAVE = ?)";
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				comando = "SELECT IMMS FROM ARON.EMPLEADOLABORALES B WHERE B.ID = (SELECT ID FROM ARON.EMPLEADO C WHERE C.CLAVE = ?)";
 				imss = enocJdbc.queryForObject(comando,String.class,parametros);
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|getIMSS|:"+ex);
		
		}
		
		return imss;
	}
	
	public String getFechaAlta( String codigoPersonal ){
 			
		String fecha 				= "X";
		try{
			String comando = "SELECT COUNT(*) FROM ARON.EMPLEADOLABORALES B" +
					" WHERE B.ID = (SELECT ID FROM ARON.EMPLEADO C" +
								  " WHERE C.CLAVE = ?)";
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				comando = "SELECT TO_CHAR(FECHA_ALTA,'DD/MM/YYYY') AS FECHA_ALTA FROM ARON.EMPLEADOLABORALES B" +
						" WHERE B.ID = (SELECT ID FROM ARON.EMPLEADO C" +
									  " WHERE C.CLAVE = ?)";
 				fecha = enocJdbc.queryForObject(comando,String.class,parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|getFechaAlta|:"+ex);
		
		}
		
		return fecha;
	}
	
	public String getCurp( String codigoPersonal ){

		String curp				= "X";
		try{
			String comando = "SELECT COUNT(CURP) FROM ARON.EMPLEADOLABORALES" +
					" WHERE ID = (SELECT ID FROM ARON.EMPLEADO WHERE CLAVE = ?)";
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
 				comando = "SELECT CURP FROM ARON.EMPLEADOLABORALES WHERE ID = (SELECT ID FROM ARON.EMPLEADO WHERE CLAVE = ?)";	 				
 				curp 	= enocJdbc.queryForObject(comando,String.class,parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|getCurp|:"+ex);			
		}
		
		return curp;
	}
	
	public String getAronPais( String codigoPersonal ){
 			
		String rfc 				= "X";
		try{
			String comando = "SELECT COUNT(*) FROM ARON.PAIS " +
					"WHERE ID = (SELECT ID_PAIS FROM ARON.ESTADO " +
					"	WHERE ID = (SELECT C.ID_ESTADO FROM ARON.CIUDAD C " +
					"		WHERE ID = (SELECT ID_CIUDAD FROM ARON.EMPLEADOPERSONALES " +
					"			WHERE ID = (SELECT ID FROM ARON.EMPLEADO WHERE CLAVE = ?))))";
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				comando = "SELECT COALESCE(NOMBRE,'X') AS NOMBRE FROM ARON.PAIS " +
						"WHERE ID = (SELECT ID_PAIS FROM ARON.ESTADO " +
						"	WHERE ID = (SELECT C.ID_ESTADO FROM ARON.CIUDAD C " +
						"		WHERE ID = (SELECT ID_CIUDAD FROM ARON.EMPLEADOPERSONALES " +
						"			WHERE ID = (SELECT ID FROM ARON.EMPLEADO WHERE CLAVE = ?))))";
 				rfc = enocJdbc.queryForObject(comando,String.class,parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|getAronPais|:"+ex);
		
		}
		
		return rfc;
	}
	
	public String getDepartamento( String codigoPersonal ){
 		
		String rfc 				= "X";
		try{
			String comando = "SELECT DEPARTAMENTO FROM ENOC.EMP_DATOS WHERE ID_EMPLEADO = ? ";
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				rfc = enocJdbc.queryForObject(comando,String.class,parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|getDepartamento|:"+ex);		
		}
		
		return rfc;
	}
	
	public List<String> getListEmpleadosNombre( String orden){
		
		List<String> lista		= new ArrayList<String>();		
		try{
			String comando = "SELECT CLAVE||'@@'||NOMBRE||' '||APPATERNO||' '||APMATERNO FROM ARON.EMPLEADO WHERE STATUS = 'A' "+ orden;
			lista = enocJdbc.queryForList(comando, String.class);
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|getListEmpleadosNombre|:"+ex);		
		}
		
		return lista;
	}
	
	/* Map de empleados con materias diferidas */
	public HashMap<String, Empleado> mapEmpleadosDiferidos(){
		
		HashMap<String,Empleado> mapa		= new HashMap<String,Empleado>();
		List<Empleado> lista	    = new ArrayList<Empleado>();
				
		try{				
			String comando = " SELECT ID, CLAVE, NOMBRE, APPATERNO, APMATERNO, " 
					+ " TO_CHAR(FECHANACIMIENTO, 'DD/MM/YYYY') AS FECHANACIMIENTO, DIRECCION, GENERO, STATUS, " 
					+ " NACIONALIDAD FROM ARON.EMPLEADO "
					+ " WHERE CLAVE IN ( SELECT CODIGO_PERSONAL FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT WHERE TIPOCAL_ID IN ('5','6')) )";
			lista = enocJdbc.query(comando, new EmpleadoMapper());		
			for(Empleado emp : lista){				
				mapa.put(emp.getClave(), emp);					
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|mapEmpleadosDiferidos|:"+ex);	
		}
		
		return mapa;
	}		
	
	public HashMap<String, String> mapEmpleado(){
		
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista	   		= new ArrayList<aca.Mapa>();
				
		try{
			String comando = "SELECT CLAVE AS LLAVE, NOMBRE||' '||APPATERNO||' '||APMATERNO AS VALOR FROM ARON.EMPLEADO";	
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());					
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|mapEmpleado|:"+ex);
		
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapEmpleadoPermiso(){
		
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista	   		= new ArrayList<aca.Mapa>();
				
		try{
			String comando = "SELECT CLAVE AS LLAVE, NOMBRE||' '||APPATERNO||' '||APMATERNO AS VALOR FROM ARON.EMPLEADO"
					+ " WHERE CLAVE IN (SELECT USUARIO FROM ENOC.FIN_PERMISO)";	
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());					
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|mapEmpleadoPermiso|:"+ex);
		
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapEmpleadoCorto(){			
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista	   		= new ArrayList<aca.Mapa>();					
		try{
			String comando = "SELECT CLAVE AS LLAVE, NOMBRE||' '||APPATERNO AS VALOR FROM ARON.EMPLEADO";	
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|mapEmpleado|:"+ex);
		
		}			
		return mapa;
	}
	
	public HashMap<String, String> mapaAsesoresAdmision(){			
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista	   		= new ArrayList<aca.Mapa>();					
		try{
			String comando = "SELECT CLAVE AS LLAVE, NOMBRE||' '||APPATERNO||' '||APMATERNO AS VALOR FROM ARON.EMPLEADO WHERE CLAVE IN (SELECT ASESOR_ID FROM SALOMON.ADM_ASESOR)";	
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|mapaAsesoresAdmision|:"+ex);
		
		}			
		return mapa;
	}
	
	public HashMap<String, String> mapaEmpleadosEnAlerta(){			
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista	   		= new ArrayList<aca.Mapa>();					
		try{
			String comando = "SELECT CLAVE AS LLAVE, NOMBRE||' '||APPATERNO||' '||APMATERNO AS VALOR FROM ARON.EMPLEADO"
					+ " WHERE CLAVE IN (SELECT USUARIO FROM ENOC.ALERTA_DATOS)";	
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|mapaEmpleadosEnAlerta|:"+ex);
		
		}			
		return mapa;
	}
	
	public HashMap<String, String> mapaEmpleadosEnInvestigacion(String opcion){	
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista	   		= new ArrayList<aca.Mapa>();					
		try{
			String comando = "";
			if (opcion.equals("NOMBRE")) {
				comando = "SELECT CLAVE AS LLAVE, NOMBRE||' '||APPATERNO||' '||APMATERNO AS VALOR FROM ARON.EMPLEADO"
					+ " WHERE CLAVE IN (SELECT CODIGO_PERSONAL FROM ENOC.INV_PROYECTO)";
			}else {
				comando = "SELECT CLAVE AS LLAVE, APPATERNO||' '||APMATERNO||' '||NOMBRE AS VALOR FROM ARON.EMPLEADO"
					+ " WHERE CLAVE IN (SELECT CODIGO_PERSONAL FROM ENOC.INV_PROYECTO)";
			}			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|mapaEmpleadosEnInvestigacion|:"+ex);
		
		}			
		return mapa;
	}
	
	public HashMap<String, String> mapaEmpleadosAsesores(String opcion){	
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista	   		= new ArrayList<aca.Mapa>();					
		try{
			String comando = "";
			if (opcion.equals("NOMBRE")) {
				comando = "SELECT CLAVE AS LLAVE, NOMBRE||' '||APPATERNO||' '||APMATERNO AS VALOR FROM ARON.EMPLEADO"
					+ " WHERE CLAVE IN (SELECT ASESOR_ID FROM SALOMON.ADM_ASESOR)";
			}else {
				comando = "SELECT CLAVE AS LLAVE, APPATERNO||' '||APMATERNO||' '||NOMBRE AS VALOR FROM ARON.EMPLEADO"
					+ " WHERE CLAVE IN (SELECT ASESOR_ID FROM SALOMON.ADM_ASESOR)";
			}			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDao|mapaEmpleadosAsesores|:"+ex);
		
		}			
		return mapa;
	}
}