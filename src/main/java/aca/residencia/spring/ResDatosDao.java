package aca.residencia.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanMapper;

@Component
public class ResDatosDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( ResDatos dat){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.RES_DATOS"+ 
				"(MATRICULA, PERIODO_ID, CALLE, COLONIA, MUNICIPIO, TEL_AREA, " +
				"TEL_NUMERO, TUT_NOMBRE, TUT_APELLIDOS, RAZON, USUARIO, " +
				"FECHA, NUMERO) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
				"TO_NUMBER(?,'999'), ?, TO_DATE(?,'DD/MM/YYYY'), ?)";
			Object[] parametros = new Object[] {dat.getMatricula(),dat.getPeriodoId(),dat.getCalle(),dat.getColonia(),
					dat.getMpio(),dat.getTelArea(),dat.getTelNum(),dat.getNombreTut(),dat.getApellidoTut(),dat.getRazon(),
					dat.getUsuario(),dat.getFecha(),dat.getNumero()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResDatosDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( ResDatos dat ){
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.RES_DATOS" 
				+ " SET PERIODO_ID = ?,"
				+ " CALLE = ?,"
				+ " COLONIA = ?,"
				+ " MUNICIPIO = ?,"
				+ " TEL_AREA = ?,"
				+ " TEL_NUMERO = ?,"
				+ " TUT_NOMBRE = ?,"
				+ " TUT_APELLIDOS = ?,"
				+ " RAZON = TO_NUMBER(?,'999'),"
				+ " USUARIO = ?,"
				+ " FECHA = TO_DATE(?,'DD/MM/YYYY'),"
				+ " NUMERO = ?"+				
				"WHERE MATRICULA = ? ";
			Object[] parametros = new Object[] {dat.getPeriodoId(),dat.getCalle(),dat.getColonia(),dat.getMpio(),
					dat.getTelArea(),dat.getTelNum(),dat.getNombreTut(),dat.getApellidoTut(),dat.getRazon(),dat.getUsuario(),
					dat.getFecha(),dat.getNumero(),dat.getMatricula()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
	
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResDatosDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	
	public boolean deleteReg( String matricula ){
		boolean ok = false;

		try{
			String comando = "DELETE FROM ENOC.RES_DATOS "+ 
				"WHERE MATRICULA = ? ";
			Object[] parametros = new Object[] {matricula};
			if (enocJdbc.update(comando, parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResDatosDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public ResDatos mapeaRegId(  String codigoPersonal) {
		
		ResDatos datos = new ResDatos();
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.RES_DATOS WHERE MATRICULA = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT MATRICULA, PERIODO_ID, CALLE, COLONIA, MUNICIPIO, TEL_AREA, TEL_NUMERO, TUT_NOMBRE, TUT_APELLIDOS, RAZON, USUARIO,"
						+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, NUMERO"
						+ " FROM ENOC.RES_DATOS"
						+ " WHERE MATRICULA = ?";
				datos = enocJdbc.queryForObject(comando, new ResDatosMapper(), parametros);
			}
				
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.ResDatosDao|mapeaRegId|:"+ex);
		}
		
		return datos;
	}
	
	public AlumPlan mapeaRegIdE(  String codigoPersonal, String planId) {
		
		AlumPlan plan = new AlumPlan();		
		try{
			String comando = "SELECT * FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ? ";			
			Object[] parametros = new Object[] {codigoPersonal,planId};
			plan = enocJdbc.queryForObject(comando, new AlumPlanMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.ResDatosDao|mapeaRegIdE|:"+ex);
		}
		
		return plan;
	}
	
	public boolean existeReg( String matricula ){
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.RES_DATOS "+ 
				"WHERE MATRICULA = ?";
			Object[] parametros = new Object[] {matricula};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResDatosDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String getRazon( String matricula){	
	
		String razon 			= "0";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.RES_DATOS WHERE MATRICULA = ?";
			Object[] parametros = new Object[] {matricula};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)==1){
				comando = "SELECT RAZON FROM ENOC.RES_DATOS WHERE MATRICULA = ?";
				razon = enocJdbc.queryForObject(comando,String.class, parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResDatosDao|getRazon|:"+ex);
		}
		
		return razon;
	}
	
	public List<ResDatos> getListAll( String orden ){
		
		List<ResDatos> lista	= new ArrayList<ResDatos>();
	
		try{
			String comando = "SELECT MATRICULA, PERIODO_ID, CALLE, COLONIA, " +
					"MUNICIPIO, TEL_AREA, TEL_NUMERO, TUT_NOMBRE, " +
					"TUT_APELLIDOS, RAZON, USUARIO, " +
					"TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, NUMERO " +
					" FROM ENOC.RES_DATOS "+orden; 
			lista = enocJdbc.query(comando, new ResDatosMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResDatos|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<ResDatos> getLista( String matricula, String orden ){
		
		List<ResDatos> lista	= new ArrayList<ResDatos>();
		
		try{
			String comando = "SELECT MATRICULA, PERIODO_ID, CALLE, COLONIA, " +
					"MUNICIPIO, TEL_AREA, TEL_NUMERO, TUT_NOMBRE, " +
					"TUT_APELLIDOS, RAZON, USUARIO, " +
					"TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, NUMERO " +
					"FROM ENOC.RES_DATOS WHERE MATRICULA = ? "+orden; 
			lista = enocJdbc.query(comando, new ResDatosMapper(),matricula);
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResDatos|getLista|:"+ex);
		}
		
		return lista;
	}

	
	public List<ResDatos> getExternos( String orden ){
		
		List<ResDatos> lista	= new ArrayList<ResDatos>();
		
		try{
			String comando = "SELECT ENOC.ALUM_APELLIDO(MATRICULA) AS NOMBRE, " +			
			"CALLE||' '|| CASE NUMERO WHEN 's/n' THEN NUMERO ELSE '# '||NUMERO END AS CALLE, "+
			"COLONIA, "+
			"MUNICIPIO, TEL_AREA, " +
			"TEL_NUMERO AS TELEFONO, "+
			"TUT_NOMBRE||' '||TUT_APELLIDOS AS TUTOR, "+
			"RAZON, "+
			"USUARIO, FECHA, NUMERO, PERIODO_ID "+			
		 	"FROM ENOC.RES_DATOS A, ENOC.INSCRITOS B "+ 
			"WHERE B.CODIGO_PERSONAL = A.MATRICULA ORDER BY FACULTAD, CARRERA, NOMBRE ";
			lista = enocJdbc.query(comando, new ResDatosMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResDatos|getExternos|:"+ex);
		}
		
		return lista;
	}

	/**
	 * @author Elifo
	 * @param conn Conexixn a la base de datos
	 * @param orden Orden en el que se traerxn los datos
	 * @return ArrayList de ResDatos de los alumnos externos inscritos
	 * */
	public List<ResDatos> getListExtInscritos( String orden ){
		
		List<ResDatos> lista	= new ArrayList<ResDatos>();
		
		try{
			String comando = "SELECT MATRICULA, PERIODO_ID, CALLE, COLONIA," +
					" MUNICIPIO, TEL_AREA, TEL_NUMERO, TUT_NOMBRE," +
					" TUT_APELLIDOS, RAZON, USUARIO," +
					" TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, NUMERO" +
					" FROM ENOC.RES_DATOS" + 
					" WHERE MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS" +
										" WHERE RESIDENCIA_ID = 'E') "+orden;
			lista = enocJdbc.query(comando, new ResDatosMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResDatos|getLista|:"+ex);
		}
		
		return lista;
	}
	
	public List<ResDatos> getListExtInscritosModalidad( String modalidades, String fechaIni, String fechaFin, String orden ){
		
		List<ResDatos> lista	= new ArrayList<ResDatos>();		
		try{
			String comando = "SELECT MATRICULA, PERIODO_ID, CALLE, COLONIA," +
					" MUNICIPIO, TEL_AREA, TEL_NUMERO, TUT_NOMBRE," +
					" TUT_APELLIDOS, RAZON, USUARIO," +
					" TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, NUMERO" +
					" FROM ENOC.RES_DATOS" + 
					" WHERE MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS" +
										" WHERE RESIDENCIA_ID = 'E' AND MODALIDAD_ID IN ("+modalidades+")) "+
					" AND FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') "+orden;
			
			Object[] parametros = new Object[] {
				fechaIni,fechaFin
			};
			
			lista = enocJdbc.query(comando, new ResDatosMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResDatosDao|getListExtInscritosModalidad|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, ResDatos> mapaDatosInscritos() {
		
		HashMap<String, ResDatos> mapa	= new HashMap<String, ResDatos>();
		List<ResDatos> lista				= new ArrayList<ResDatos>();		
		try{
			String comando = "SELECT MATRICULA, PERIODO_ID, CALLE, COLONIA, MUNICIPIO, TEL_AREA, TEL_NUMERO, TUT_NOMBRE," 
					+ " TUT_APELLIDOS, RAZON, USUARIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, NUMERO"
					+ " FROM ENOC.RES_DATOS"
					+ " WHERE MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)";
			lista = enocJdbc.query(comando, new ResDatosMapper());	
			for(ResDatos datos : lista){
				mapa.put(datos.getMatricula(), datos);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResDatosDao|mapaDatosInscritos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, ResDatos> mapaDatosInscritosPorFechas(String fechaIni, String fechaFin){
		
		HashMap<String, ResDatos> mapa	= new HashMap<String, ResDatos>();
		List<ResDatos> lista				= new ArrayList<ResDatos>();		
		try{
			String comando = "SELECT MATRICULA, PERIODO_ID, CALLE, COLONIA, MUNICIPIO, TEL_AREA, TEL_NUMERO, TUT_NOMBRE," 
					+ " TUT_APELLIDOS, RAZON, USUARIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, NUMERO"
					+ " FROM ENOC.RES_DATOS"
					+ " WHERE MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.ESTADISTICA WHERE F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY'))";
			Object[] parametros = new Object[] {fechaIni, fechaFin};
			lista = enocJdbc.query(comando, new ResDatosMapper(), parametros);	
			for(ResDatos datos : lista){
				mapa.put(datos.getMatricula(), datos);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResDatosDao|mapaDatosInscritos|:"+ex);
		}
		
		return mapa;
	}

}