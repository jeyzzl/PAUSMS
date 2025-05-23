package aca.internado.spring;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.alumno.spring.AlumBanco;
import aca.alumno.spring.AlumBancoMapper;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalMapper;
import aca.catalogo.spring.CatCiudad;
import aca.catalogo.spring.CatCiudadMapper;

@Component
public class IntDormitorioDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;		
	
	public boolean insertReg(IntDormitorio dormi ){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.INT_DORMITORIO VALUES(TO_NUMBER(?,'99'),?,?,?)"; 
			
			Object[] parametros = new Object[] {
			dormi.getDormitorioId(), dormi.getNombre(), dormi.getPreceptor(), dormi.getSexo()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntDormitorioDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg(IntDormitorio dormi ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.INT_DORMITORIO SET NOMBRE = ?, PRECEPTOR = ?, SEXO = ? "
						   + " WHERE DORMITORIO_ID = TO_NUMBER(?,'99')";			 
			Object[] parametros = new Object[] {
					dormi.getNombre(), dormi.getPreceptor(), dormi.getSexo(), dormi.getDormitorioId() 
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntDormitorioDao|updateReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(String dormitorioId ){
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.INT_DORMITORIO WHERE DORMITORIO_ID = TO_NUMBER(?,'99')"; 
			Object[] parametros = new Object[] { dormitorioId };			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntDormitorioDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public boolean existeReg( String dormitorioId) {
		boolean ok 			= false;
		
		try{
			String comando ="SELECT COUNT(*) FROM ENOC.INT_DORMITORIO WHERE DORMITORIO_ID = ? "; 
			Object[] parametros = new Object[] {dormitorioId};	
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntDormitorioDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean existePreceptor( String codigoPersonal) {
		boolean ok 			= false;
		
		try{
			String comando ="SELECT COUNT(*) FROM ENOC.INT_DORMITORIO WHERE PRECEPTOR = ? "; 
			Object[] parametros = new Object[] {codigoPersonal};	
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntDormitorioDao|existePreceptor|:"+ex);
		}		
		return ok;
	}
	
	public IntDormitorio mapeaRegId(String dormitorioId){
		IntDormitorio dormi = new IntDormitorio();
		
		try{ 
			String comando = "SELECT COUNT(*) FROM ENOC.INT_DORMITORIO WHERE DORMITORIO_ID = TO_NUMBER(?,'99') "; 
			
			Object[] parametros = new Object[] {dormitorioId};
			
			if(enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = "SELECT DORMITORIO_ID, NOMBRE, PRECEPTOR, SEXO"
						+ " FROM ENOC.INT_DORMITORIO"
						+ " WHERE DORMITORIO_ID = ?";
				
				dormi = enocJdbc.queryForObject(comando, new IntDormitorioMapper(),parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntDormitorioDao|mapeaRegId|:"+ex);
		}
		return dormi;
	}
	
	public String getNombre(String dormitorioId){
		String nombre = "-";		
		try{ 
			String comando = "SELECT NOMBRE FROM ENOC.INT_DORMITORIO WHERE DORMITORIO_ID = TO_NUMBER(?,'99') ";			
			Object[] parametros = new Object[] {dormitorioId};
			nombre = enocJdbc.queryForObject(comando, String.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntDormitorioDao|mapeaRegId|:"+ex);
		}
		return nombre;
	}
	
	public String getPreceptores(){
		String preceptores = "0";
		List<String> lista = new ArrayList<String>();
		try{ 
			String comando = "SELECT PRECEPTOR FROM ENOC.INT_DORMITORIO";
			lista = enocJdbc.queryForList(comando, String.class);
			for (String prece : lista) {
				if (preceptores.equals("0")) preceptores = prece; else preceptores += ","+prece; 
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntDormitorioDao|getPreceptores|:"+ex);
		}
		return preceptores;
	}
	
	public String getDormitorioAlumno(String codigoPersonal) {
		String dormi = "";
		try{
			String comando = "SELECT DORMITORIO_ID FROM ENOC.INT_ALUMNO WHERE CODIGO_PERSONAL = ?";
			dormi = enocJdbc.queryForObject(comando, String.class, codigoPersonal);
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.DormitorioDao|getDormitorioAlumno|:"+ex);
		}
		return dormi;
	}
	
	public String getCuartoAlumno(String codigoPersonal) {
		String dormi = "";
		try{
			String comando = "SELECT CUARTO_ID FROM ENOC.INT_ALUMNO WHERE CODIGO_PERSONAL = ?";
			dormi = enocJdbc.queryForObject(comando, String.class, codigoPersonal);
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.DormitorioDao|getCuartoAlumno|:"+ex);
		}
		return dormi;
	}
	
	public List<IntCuarto> getCuartos(String dormitorioId, String orden){
		
		List<IntCuarto> lista = new ArrayList<IntCuarto>();		
		try{
			String comando = "SELECT * FROM ENOC.INT_CUARTO WHERE DORMITORIO_ID = ? "+orden; 
			lista = enocJdbc.query(comando, new IntCuartoMapper(), dormitorioId);			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntDormitorioDao|getCuartos|:"+ex);
		}
		
		return lista;
	}
	
	public List<IntCuarto> getCuartos(String dormitorioId, String pasillo, String orden){
		List<IntCuarto> listor = new ArrayList<IntCuarto>();		
		try{
			String comando = "SELECT * FROM ENOC.INT_CUARTO WHERE DORMITORIO_ID = ? AND PASILLO = ? "+orden;
			listor = enocJdbc.query(comando, new IntCuartoMapper(), dormitorioId, pasillo);			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntDormitorioDao|getCuartos|:"+ex);
		}		
		return listor;
	}
	
	public String nextCuarto(String dormitorioId){
		
		String cuarto = "";		
		try{
			String comando = "SELECT COALESCE(MAX(TO_CHAR(CUARTO_ID,'000'))+1,1) FROM ENOC.INT_CUARTO WHERE DORMITORIO_ID=?"; 
			cuarto = enocJdbc.queryForObject(comando, String.class, dormitorioId);			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntDormitorioDao|nextCuarto|:"+ex);
		}		
		return cuarto;
	}
	
	public List<IntDormitorio> getListAll(String orden) {
		List<IntDormitorio> listor = new ArrayList<IntDormitorio>();
		
		try{
			String comando	=  "SELECT DORMITORIO_ID, NOMBRE, PRECEPTOR, SEXO FROM ENOC.INT_DORMITORIO "+orden;	 
			
			listor = enocJdbc.query(comando, new IntDormitorioMapper());

		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntDormitorioDao|getListAll|:"+ex);
		}
		return listor;
	}
	
	public List<IntDormitorio> getListPorPreceptor(String codigoPersonal, String orden) {
		List<IntDormitorio> listor = new ArrayList<IntDormitorio>();
		
		try{
			String comando	=  "SELECT DORMITORIO_ID, NOMBRE, PRECEPTOR, SEXO FROM ENOC.INT_DORMITORIO WHERE PRECEPTOR = ? "+orden;	 
			Object[] parametros = new Object[] {codigoPersonal};
			listor = enocJdbc.query(comando, new IntDormitorioMapper(), parametros);

		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntDormitorioDao|getListPorPreceptor|:"+ex);
		}
		return listor;
	}

	public List<IntDormitorio> getListPorAsistente(String codigoPersonal, String orden) {
		List<IntDormitorio> listor = new ArrayList<IntDormitorio>();
		
		try{
			String comando	=  "SELECT DORMITORIO_ID, NOMBRE, PRECEPTOR, SEXO FROM ENOC.INT_DORMITORIO WHERE DORMITORIO_ID IN (SELECT DORMITORIO_ID FROM INT_ACCESO WHERE CODIGO_PERSONAL = ? AND ROL = 'A') "+orden;	 
			Object[] parametros = new Object[] {codigoPersonal};
			listor = enocJdbc.query(comando, new IntDormitorioMapper(), parametros);

		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntDormitorioDao|getListPorPreceptor|:"+ex);
		}
		return listor;
	}
	
	public List<IntDormitorio> getListPorSexo(String sexo, String orden) {
		List<IntDormitorio> listor = new ArrayList<IntDormitorio>();
		
		try{
			String comando	=  "SELECT DORMITORIO_ID, NOMBRE, PRECEPTOR, SEXO FROM ENOC.INT_DORMITORIO WHERE SEXO = ? "+orden;	 
			Object[] parametros = new Object[] {sexo};
			listor = enocJdbc.query(comando, new IntDormitorioMapper(), parametros);

		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntDormitorioDao|getListAll|:"+ex);
		}
		return listor;
	}
	
	public List<IntAlumno> getAlumnos(String dormitorioId, String orden){
		List<IntAlumno> listor = new ArrayList<IntAlumno>();
		try{
			String comando	 = "SELECT * FROM ENOC.INT_ALUMNO WHERE DORMITORIO_ID = ? AND ESTADO = 'A'"+orden;			 
			listor = enocJdbc.query(comando,  new IntAlumnoMapper(), dormitorioId);
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntDormitorioDao|getAlumnos|:"+ex);
		}
		return listor;
	}

	public List<IntAlumno> getAlumnosEnCuarto(String dormitorioId, String cuartoId, String orden){
		List<IntAlumno> listor = new ArrayList<IntAlumno>();
		try{
			String comando	 = "SELECT * FROM ENOC.INT_ALUMNO WHERE DORMITORIO_ID = ? AND CUARTO_ID = ? "+orden;	
			Object[] parametros = new Object[] {dormitorioId, cuartoId};		 
			listor = enocJdbc.query(comando,  new IntAlumnoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntDormitorioDao|getAlumnosEnCuarto|:"+ex);
		}
		return listor;
	}

	public HashMap<String, String> mapaInternoCarrera() {
		List<AlumPersonal> lista = new ArrayList<AlumPersonal>();
		HashMap<String, String> mapa = new HashMap<String, String>();	
		
		String carrera = "x";
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
				+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
				+ " NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, TELEFONO, CREDENCIAL, US_ALTA"
				+ " FROM ENOC.ALUM_PERSONAL"
				+ " WHERE CODIGO_PERSONAL IN "
				+ "		(SELECT CODIGO_PERSONAL FROM ENOC.ARCH_REVALIDA)";
			lista = enocJdbc.query(comando, new AlumPersonalMapper());
			for (AlumPersonal alumno : lista ) {
				comando = "SELECT B.CARRERA_ID FROM ENOC.ALUM_PLAN A, ENOC.MAPA_PLAN B WHERE A.CODIGO_PERSONAL = ?"
						+ " AND A.ESTADO = '1' AND B.PLAN_ID = A.PLAN_ID";

				Object[] parametros = new Object[] {alumno.getCodigoPersonal()};
				carrera = enocJdbc.queryForObject(comando, String.class,parametros);
				
				mapa.put(alumno.getCodigoPersonal(), carrera);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntDormitorioDao|mapaInternoCarrera|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,IntDormitorio> getMapAll( String orden ) {
		
		HashMap<String,IntDormitorio> mapa 	= new HashMap<String,IntDormitorio>();
		List<IntDormitorio> lista 			= new ArrayList<IntDormitorio>();
		
		try{
			String comando = "SELECT DORMITORIO_ID, NOMBRE, PRECEPTOR, SEXO FROM ENOC.INT_DORMITORIO "+ orden;		
			lista = enocJdbc.query(comando, new IntDormitorioMapper());
			for(IntDormitorio dormitorio : lista){
				mapa.put(dormitorio.getDormitorioId(), dormitorio);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.IntDormitorioDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}
	
}
