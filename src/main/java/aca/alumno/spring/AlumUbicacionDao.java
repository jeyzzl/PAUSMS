// Clase para la tabla de Alum_Ubicacion
package aca.alumno.spring;

import java.util.ArrayList;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class AlumUbicacionDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( AlumUbicacion alumUbicacion ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ALUM_UBICACION"+ 
				"(CODIGO_PERSONAL, "+
				"P_NOMBRE, P_RELIGION, P_NACIONALIDAD, "+
				"M_NOMBRE, M_RELIGION, M_NACIONALIDAD, "+
				"T_NOMBRE, T_DIRECCION, T_COLONIA, T_CODIGO, T_APARTADO, T_TELEFONO, "+
				"T_EMAIL, T_PAIS, T_ESTADO, T_CIUDAD, T_CELULAR,T_COMUNICA, CODIGO_PADRE, CODIGO_MADRE, FECHA, VACUNA, DESCRIPCION_VACUNA, RECOGIDA_ID,"+
				"P_PAIS, P_ESTADO, P_CIUDAD, P_ORIGEN, M_PAIS, M_ESTADO, M_CIUDAD, M_ORIGEN)"+				
				"VALUES( ?, ?, "+
				"TO_NUMBER(?,'99'), "+
				"TO_NUMBER(?,'999'), "+
				" ?, "+
				"TO_NUMBER(?,'99'), "+
				"TO_NUMBER(?,'999'), "+
				" ?, ?, ?, ?, ?, ?, ?, "+
				"TO_NUMBER(?,'999'), "+
				"TO_NUMBER(?,'999'), "+
				"TO_NUMBER(?,'999'), ?, ?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'), ?, ?, TO_NUMBER(?,'999'),"+
				"TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), ?, TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), ?)";
			Object[] parametros = new Object[] {
					alumUbicacion.getCodigoPersonal(),alumUbicacion.getpNombre(),alumUbicacion.getpReligion(),alumUbicacion.getpNacionalidad(),
					alumUbicacion.getmNombre(),alumUbicacion.getmReligion(),alumUbicacion.getmNacionalidad(),alumUbicacion.gettNombre(),alumUbicacion.gettDireccion(),
					alumUbicacion.gettColonia(),alumUbicacion.gettCodigo(),alumUbicacion.gettApartado(),alumUbicacion.gettTelefono(),alumUbicacion.gettEmail(),
					alumUbicacion.gettPais(),alumUbicacion.gettEstado(),alumUbicacion.gettCiudad(),alumUbicacion.gettCelular(),alumUbicacion.gettComunica(),
					alumUbicacion.getCodigoPadre(),alumUbicacion.getCodigoMadre(),alumUbicacion.getFecha(),alumUbicacion.getVacuna(),alumUbicacion.getDescripcionVacuna(),
					alumUbicacion.getRecogidaId(),alumUbicacion.getpPais(),alumUbicacion.getpEstado(),alumUbicacion.getpCiudad(),alumUbicacion.getpOrigen(),
					alumUbicacion.getmPais(),alumUbicacion.getmEstado(),alumUbicacion.getmCiudad(),alumUbicacion.getmOrigen()
 		 	};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumUbicacionDao|insertReg|:"+ex);			
	
		}
		return ok;
	}	
	
	public boolean updateReg( AlumUbicacion alumUbicacion ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ALUM_UBICACION "+ 
				"SET "+				
				"P_NOMBRE = ?, "+
				"P_RELIGION = TO_NUMBER(?,'99'), "+
				"P_NACIONALIDAD = TO_NUMBER(?,'999'), "+
				"M_NOMBRE = ?, "+
				"M_RELIGION = TO_NUMBER(?,'99'), "+
				"M_NACIONALIDAD = TO_NUMBER(?,'999'), "+
				"T_NOMBRE = ?, "+
				"T_DIRECCION = ?, "+
				"T_COLONIA = ?, "+
				"T_CODIGO = ?, "+
				"T_APARTADO = ?, "+
				"T_TELEFONO = ?, "+
				"T_EMAIL = ?, "+				
				"T_PAIS = TO_NUMBER(?,'999'), "+
				"T_ESTADO = TO_NUMBER(?,'999'), "+
				"T_CIUDAD = TO_NUMBER(?,'999'), " +
				"T_CELULAR = ?, " +
				"T_COMUNICA = ?, "+
				"CODIGO_PADRE = ?, "+
				"CODIGO_MADRE = ?, "+
				"FECHA = TO_DATE(?,'DD/MM/YYYY'), "+
				"VACUNA = ?, "+
				"DESCRIPCION_VACUNA = ?, "+
				"RECOGIDA_ID = TO_NUMBER(?,'999'), "+
				"P_PAIS = TO_NUMBER(?,'999'), "+
				"P_ESTADO = TO_NUMBER(?,'999'), "+
				"P_CIUDAD = TO_NUMBER(?,'999'), "+
				"P_ORIGEN = ?, "+
				"M_PAIS = TO_NUMBER(?,'999'), "+
				"M_ESTADO = TO_NUMBER(?,'999'), "+
				"M_CIUDAD = TO_NUMBER(?,'999'), "+
				"M_ORIGEN = ? "+
				"WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {
					alumUbicacion.getpNombre(),alumUbicacion.getpReligion(),alumUbicacion.getpNacionalidad(),alumUbicacion.getmNombre(),
					alumUbicacion.getmReligion(),alumUbicacion.getmNacionalidad(),alumUbicacion.gettNombre(),alumUbicacion.gettDireccion(),
					alumUbicacion.gettColonia(),alumUbicacion.gettCodigo(),alumUbicacion.gettApartado(),alumUbicacion.gettTelefono(),
					alumUbicacion.gettEmail(),alumUbicacion.gettPais(),alumUbicacion.gettEstado(),alumUbicacion.gettCiudad(),
					alumUbicacion.gettCelular(),alumUbicacion.gettComunica(),alumUbicacion.getCodigoPadre(),alumUbicacion.getCodigoMadre(),
					alumUbicacion.getFecha(),alumUbicacion.getVacuna(),alumUbicacion.getDescripcionVacuna(),alumUbicacion.getRecogidaId(),
					alumUbicacion.getpPais(),alumUbicacion.getpEstado(),alumUbicacion.getpCiudad(),alumUbicacion.getpOrigen(),
					alumUbicacion.getmPais(),alumUbicacion.getmEstado(),alumUbicacion.getmCiudad(),alumUbicacion.getmOrigen(),
					alumUbicacion.getCodigoPersonal()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error  - aca.alumno.spring.AlumUbicacionDao|updateReg|:"+ex);		
		
		}
		return ok;
	}	
	
	public boolean updatePadres( String codigoPersonal, String padre, String madre){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ALUM_UBICACION SET P_NOMBRE = ?, M_NOMBRE = ? WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] { padre.toUpperCase(),madre.toUpperCase(), codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error  - aca.alumno.spring.AlumUbicacionDao|updatePadres|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateUbicacion( AlumUbicacion alumUbicacion ){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ALUM_UBICACION "+ 
				" SET "+				
				" T_DIRECCION = ?, "+
				" T_COLONIA = ?, "+
				" T_CODIGO = ?, "+
				" T_APARTADO = ?,"+
				" T_TELEFONO = ?,"+
				" T_EMAIL = ?, "+				
				" T_PAIS = TO_NUMBER(?,'999'),"+
				" T_ESTADO = TO_NUMBER(?,'999'),"+
				" T_CIUDAD = TO_NUMBER(?,'999')," +
				" T_CELULAR = ?," +
				" T_COMUNICA = ?,"+
				" RECOGIDA_ID = TO_NUMBER(?,'999'),"+
				" P_PAIS = TO_NUMBER(?,'999'),"+
				" P_ESTADO = TO_NUMBER(?,'999'),"+
				" P_CIUDAD = TO_NUMBER(?,'999'),"+
				" P_ORIGEN = TO_NUMBER(?,'999'),"+
				" M_PAIS = TO_NUMBER(?,'999'),"+
				" M_ESTADO = TO_NUMBER(?,'999'),"+
				" M_CIUDAD = TO_NUMBER(?,'999'),"+
				" M_ORIGEN = TO_NUMBER(?,'999')"+
				" WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {
					alumUbicacion.gettDireccion(),alumUbicacion.gettColonia(),alumUbicacion.gettCodigo(),
					alumUbicacion.gettApartado(),alumUbicacion.gettTelefono(),alumUbicacion.gettEmail(),
					alumUbicacion.gettPais(),alumUbicacion.gettEstado(),alumUbicacion.gettCiudad(),
					alumUbicacion.gettCelular(),alumUbicacion.gettComunica(),alumUbicacion.getRecogidaId(),
					alumUbicacion.getpPais(),alumUbicacion.getpEstado(),alumUbicacion.getpCiudad(),alumUbicacion.getpOrigen(),
					alumUbicacion.getmPais(),alumUbicacion.getmEstado(),alumUbicacion.getmCiudad(),alumUbicacion.getmOrigen(),
					alumUbicacion.getCodigoPersonal()		
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error  - aca.alumno.spring.AlumUbicacionDao|updateUbicacion|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg( String codigoPersonal ){
		boolean ok = false;

		try{
			String comando = "DELETE FROM ENOC.ALUM_UBICACION WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.update(comando, parametros)==1){
 				ok = true;
 			} 	
 			
		}catch(Exception ex){
			System.out.println("Error  - aca.alumno.spring.AlumUbicacionDao|deleteReg|:"+ex);			

		}
		return ok;
	}
	
	public AlumUbicacion mapeaRegId(  String codigoPersonal ){		
		AlumUbicacion ubicacion = new AlumUbicacion();	
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_UBICACION WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = " SELECT CODIGO_PERSONAL,"
						+ " P_NOMBRE, P_RELIGION, P_NACIONALIDAD,"
						+ " M_NOMBRE, M_RELIGION, M_NACIONALIDAD,"
						+ " T_NOMBRE, T_DIRECCION, T_COLONIA, T_CODIGO, T_APARTADO, T_TELEFONO,"
						+ " T_EMAIL, T_PAIS, T_ESTADO, T_CIUDAD, T_CELULAR, T_COMUNICA, T_EDOCD, CODIGO_PADRE, CODIGO_MADRE,"
						+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, VACUNA, DESCRIPCION_VACUNA, RECOGIDA_ID, P_PAIS, P_ESTADO, P_CIUDAD, P_ORIGEN, M_PAIS, M_ESTADO, M_CIUDAD, M_ORIGEN"
						+ " FROM ENOC.ALUM_UBICACION WHERE CODIGO_PERSONAL = ?";
				ubicacion = enocJdbc.queryForObject(comando, new AlumUbicacionMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumUbicacionDao|mapeaRegId|:"+ex);	
		}
		
		return ubicacion;
	}
	
	public boolean existeReg( String codigoPersonal){
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_UBICACION WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumUbicacionDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean tieneVacuna(String codigoPersonal){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_UBICACION WHERE VACUNA != 'X' AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 		
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumUbicacionDao|tieneVacuna|:"+ex);
			
		}
		return ok;
	}
	
	public String getTutorNombre( String codigoPersonal){		
		String tutor			= "x";
		
		try{
			String comando = "SELECT T_NOMBRE FROM ENOC.ALUM_UBICACION WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			tutor 	= enocJdbc.queryForObject(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumUbicacionDao|getTutorNombre|:"+ex);
		}
		
		return tutor;
	}
	
	public boolean updateOtroEdoCd( String codigoPersonal, String edocd){
		boolean ok = false;

		try{
			String comando = "UPDATE ENOC.ALUM_UBICACION SET T_EDOCD = ? WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {edocd,codigoPersonal};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error  - aca.alumno.spring.AlumUbicacionDao|updateOtroEdoCd|:"+ex);		
		}
		
		return ok;
	}
	
	public String getOtroEdoCd( String codigoPersonal){
		String otro			= "x";
		
		try{
			String comando = "SELECT T_EDOCD FROM ENOC.ALUM_UBICACION WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			otro = enocJdbc.queryForObject(comando, String.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumUbicacionDao|getOtroEdoCd|:"+ex);
		}
		
		return otro;
	}
		
	public List<AlumUbicacion> getListAll( String orden ){		
		List<AlumUbicacion> lista	= new ArrayList<AlumUbicacion>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL,"
				+ " P_NOMBRE, P_RELIGION, P_NACIONALIDAD,"
				+ " M_NOMBRE, M_RELIGION, M_NACIONALIDAD,"
				+ " T_NOMBRE, T_DIRECCION, T_COLONIA, T_CODIGO, T_APARTADO, T_TELEFONO,"
				+ " T_EMAIL, T_PAIS, T_ESTADO, T_CIUDAD, T_CELULAR, T_COMUNICA, T_EDOCD,"
				+ " CODIGO_PADRE, CODIGO_MADRE, FECHA, VACUNA, DESCRIPCION_VACUNA, RECOGIDA_ID, P_PAIS, P_ESTADO, P_CIUDAD, P_ORIGEN, M_PAIS, M_ESTADO, M_CIUDAD, M_ORIGEN"
				+ " FROM ENOC.ALUM_UBICACION "+orden;				
			lista = enocJdbc.query(comando, new AlumUbicacionMapper());
		}catch(Exception ex){
			System.out.println("Error  - aca.alumno.spring.AlumUbicacionDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<AlumUbicacion> getListEstadistica(String cargas, String fechaIni, String fechaFin, String orden){
		
		List<AlumUbicacion> lista	= new ArrayList<AlumUbicacion>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL,"
					+ " P_NOMBRE, P_RELIGION, P_NACIONALIDAD,"
					+ " M_NOMBRE, M_RELIGION, M_NACIONALIDAD,"
					+ " T_NOMBRE, T_DIRECCION, T_COLONIA, T_CODIGO, T_APARTADO, T_TELEFONO,"
					+ " T_EMAIL, T_PAIS, T_ESTADO, T_CIUDAD, T_CELULAR, T_COMUNICA, T_EDOCD, "
					+ " CODIGO_PADRE, CODIGO_MADRE, FECHA, VACUNA, DESCRIPCION_VACUNA, RECOGIDA_ID, P_PAIS, P_ESTADO, P_CIUDAD, P_ORIGEN, M_PAIS, M_ESTADO, M_CIUDAD, M_ORIGEN"
					+ " FROM ENOC.ALUM_UBICACION "
					+ " WHERE CODIGO_PERSONAL "
					+ " IN(SELECT CODIGO_PERSONAL FROM ESTADISTICA WHERE CARGA_ID IN ("+cargas+") "
					+ " AND F_INSCRIPCION BETWEEN TO_DATE(?, 'DD/MM/YYYY') AND TO_DATE(?, 'DD/MM/YYYY')) "
					+ " AND T_PAIS != 0 AND T_ESTADO != 0 "+orden;
			Object[] parametros = new Object[] {fechaIni, fechaFin};
			lista = enocJdbc.query(comando, new AlumUbicacionMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error  - aca.alumno.spring.AlumUbicacionDao|getListEstadistica|:"+ex);
		}
		
		return lista;
	}
	
	public List<AlumUbicacion> lisVacunados( String orden ){
		
		List<AlumUbicacion> lista	= new ArrayList<AlumUbicacion>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL,"
					+ " P_NOMBRE, P_RELIGION, P_NACIONALIDAD,"
					+ " M_NOMBRE, M_RELIGION, M_NACIONALIDAD,"
					+ " T_NOMBRE, T_DIRECCION, T_COLONIA, T_CODIGO, T_APARTADO, T_TELEFONO,"
					+ " T_EMAIL, T_PAIS, T_ESTADO, T_CIUDAD, T_CELULAR, T_COMUNICA, T_EDOCD, "
					+ " CODIGO_PADRE, CODIGO_MADRE, FECHA, VACUNA, DESCRIPCION_VACUNA, RECOGIDA_ID, P_PAIS, P_ESTADO, P_CIUDAD, P_ORIGEN, M_PAIS, M_ESTADO, M_CIUDAD, M_ORIGEN"
					+ " FROM ENOC.ALUM_UBICACION "
					+ " WHERE CODIGO_PERSONAL IN(SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)"
					+ " AND VACUNA = 'S' "+orden;			
			lista = enocJdbc.query(comando, new AlumUbicacionMapper());
		}catch(Exception ex){
			System.out.println("Error  - aca.alumno.spring.AlumUbicacionDao|lisVacunados|:"+ex);
		}		
		return lista;
	}

	public HashMap<String,String> mapaAlumPorEstados(String cargas, String modalidades, String fechaIni, String fechaFin){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT T_PAIS||','||T_ESTADO AS LLAVE, COUNT(T_ESTADO) AS VALOR FROM ENOC.ALUM_UBICACION"
				+ " WHERE CODIGO_PERSONAL IN"
				+ "		(SELECT CODIGO_PERSONAL FROM ESTADISTICA "
				+ "		WHERE CARGA_ID IN("+cargas+") "
				+ "		AND MODALIDAD_ID IN ("+modalidades+")"
				+ "		AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"
				+ "		)"
				+ " GROUP BY T_PAIS||','||T_ESTADO";
			Object[] parametros = new Object[] {fechaIni, fechaFin};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);			
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error  - aca.alumno.spring.AlumUbicacionDao|mapaAlumPorEstados|:"+ex);
		}		
		return mapa;
	}
	
	
	public TreeMap<String,AlumUbicacion> treeAll( String orden ){		
		TreeMap<String,AlumUbicacion> tree	= new TreeMap<String,AlumUbicacion>();
		List<AlumUbicacion> lista	= new ArrayList<AlumUbicacion>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, "
				+ " P_NOMBRE, P_RELIGION, P_NACIONALIDAD, "
				+ " M_NOMBRE, M_RELIGION, M_NACIONALIDAD, "
				+ " T_NOMBRE, T_DIRECCION, T_COLONIA, T_CODIGO, T_APARTADO, T_TELEFONO, "
				+ " T_EMAIL, T_PAIS, T_ESTADO, T_CIUDAD, T_CELULAR, T_COMUNICA, T_EDOCD,"
				+ " CODIGO_PADRE, CODIGO_MADRE, FECHA, VACUNA, DESCRIPCION_VACUNA, RECOGIDA_ID, P_PAIS, P_ESTADO, P_CIUDAD, P_ORIGEN, M_PAIS, M_ESTADO, M_CIUDAD, M_ORIGEN"
				+ " FROM ENOC.ALUM_UBICACION WHERE CODIGO_PERSONAL IN(SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS) "+ orden;				
			lista = enocJdbc.query(comando, new AlumUbicacionMapper());
			for (AlumUbicacion ubicacion : lista){
				tree.put(ubicacion.getCodigoPersonal(), ubicacion);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumUbicacionDao|getMapAll|:"+ex);
		}
		
		return tree;
	}
	
	public HashMap<String,AlumUbicacion> mapInscritos(){
		
		HashMap<String,AlumUbicacion> mapa	= new HashMap<String,AlumUbicacion>();
		List<AlumUbicacion> lista			= new ArrayList<AlumUbicacion>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, P_NOMBRE, P_RELIGION, P_NACIONALIDAD, M_NOMBRE, M_RELIGION, M_NACIONALIDAD,"+
				" T_NOMBRE, T_DIRECCION, T_COLONIA, T_CODIGO, T_APARTADO, T_TELEFONO, T_EMAIL, T_PAIS, T_ESTADO, T_CIUDAD, T_CELULAR, T_COMUNICA, T_EDOCD,"+
				" CODIGO_PADRE, CODIGO_MADRE, FECHA, VACUNA, DESCRIPCION_VACUNA, RECOGIDA_ID, P_PAIS, P_ESTADO, P_CIUDAD, P_ORIGEN, M_PAIS, M_ESTADO, M_CIUDAD, M_ORIGEN"+
				" FROM ENOC.ALUM_UBICACION"+
				" WHERE CODIGO_PERSONAL IN(SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)"; 
			lista = enocJdbc.query(comando, new AlumUbicacionMapper());
			for (AlumUbicacion ubicacion : lista){
				mapa.put(ubicacion.getCodigoPersonal(), ubicacion);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumUbicacionDao|mapInscritos|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, AlumUbicacion> mapAlumnosEnCargas(String cargas) {
		HashMap<String,AlumUbicacion> mapa	= new HashMap<String,AlumUbicacion>();
		List<AlumUbicacion> lista	= new ArrayList<AlumUbicacion>();
		
		try{
			String comando	= "SELECT CODIGO_PERSONAL, P_NOMBRE, P_RELIGION, P_NACIONALIDAD, M_NOMBRE, M_RELIGION, M_NACIONALIDAD,"
					+ " T_NOMBRE, T_DIRECCION, T_COLONIA, T_CODIGO, T_APARTADO, T_TELEFONO, T_EMAIL, T_PAIS, T_ESTADO, T_CIUDAD, T_CELULAR, T_COMUNICA, T_EDOCD,"
					+ " CODIGO_PADRE, CODIGO_MADRE, FECHA, VACUNA, DESCRIPCION_VACUNA, RECOGIDA_ID, P_PAIS, P_ESTADO, P_CIUDAD, P_ORIGEN, M_PAIS, M_ESTADO, M_CIUDAD, M_ORIGEN"
					+ " FROM ENOC.ALUM_UBICACION " +
					" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+cargas+"))";			
			lista = enocJdbc.query(comando, new AlumUbicacionMapper());
			for (AlumUbicacion ubicacion : lista){
				mapa.put(ubicacion.getCodigoPersonal(), ubicacion);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumUbicacionDao|mapAlumnosEnCargas|:"+ex);
		}
		
		return mapa;
	}
}