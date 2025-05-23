package aca.musica.spring;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MusiAlumnoDao {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	
	public boolean insertReg(MusiAlumno musiAlumno) {
 		boolean ok = false;
 		
 		try{
 			String comando = "INSERT INTO ENOC.MUSI_ALUMNO "
 				+ " (CODIGO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, FECHA_NAC,INSTITUCION_ID, SEGURO, TUTOR, TELEFONO, CELULAR, EMAIL, CODIGO_UM, COMENTARIO, "
 				+ " EMPLEADO, RELIGION_ID,CODIGO_USUARIO, CIUDAD_ID, ESTADO_ID, PAIS_ID, NACIONALIDAD, TEL_TRABAJO, ESTADO, GENERO) "+
 				"VALUES( ?, ?, ?, ?,TO_DATE(?,'DD/MM/YYYY'), "+
 				"TO_NUMBER(?,'99'), "+
 				"?, ?, ?, ?, ?, ?, ? , ?, TO_NUMBER(?,'99'),?,?)";
 			
 			Object[] parametros = new Object[] {
				musiAlumno.getCodigoId(),musiAlumno.getNombre().toUpperCase(),musiAlumno.getApellidoPaterno().toUpperCase(),musiAlumno.getApellidoMaterno().toUpperCase(),
				musiAlumno.getFechaNac(),musiAlumno.getInstitucionId(),musiAlumno.getSeguro(),musiAlumno.getTutor(),musiAlumno.getTelefono(),musiAlumno.getCelular(),
				musiAlumno.getEmail(),musiAlumno.getCodigoUM(),musiAlumno.getComentario(),musiAlumno.getEmpleado(),musiAlumno.getReligionId(),musiAlumno.getCodigoUsuario(),
				musiAlumno.getCiudadId(),musiAlumno.getEstadoId(),musiAlumno.getPaisId(),musiAlumno.getNacionalidad(),musiAlumno.getTelTrabajo(), musiAlumno.getEstado()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}

 		}catch(Exception ex){
 			System.out.println("Error - cimum.usuario.MusiAlumnoDao|insertReg|:"+ex);			
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(MusiAlumno musiAlumno) { 		
 		boolean ok = false;
 		
 		try{
 			String comando = "UPDATE ENOC.MUSI_ALUMNO"		 
 				+ " SET NOMBRE = ?,"
 				+ " APELLIDO_PATERNO = ?,"
 				+ " APELLIDO_MATERNO = ?,"
 				+ " FECHA_NAC = TO_DATE(?,'DD/MM/YYYY'),"
 				+ " INSTITUCION_ID = TO_NUMBER(?,'99'),"
 				+ " SEGURO = ?,"
 				+ " TUTOR = ?,"
 				+ " TELEFONO = ?,"
 				+ " CELULAR = ?,"
 				+ " EMAIL = ?,"
 				+ " CODIGO_UM = ?,"
 				+ " COMENTARIO = ?,"
 				+ " EMPLEADO = ?," 		
 				+ " RELIGION_ID = ?," 
 				+ " CIUDAD_ID = ?," 
 				+ " ESTADO_ID = ?," 
 				+ " PAIS_ID = ?," 
 				+ " NACIONALIDAD = ?," 
 				+ " TEL_TRABAJO = ?,"
 				+ " ESTADO = ?,"
 				+ " GENERO = ?" 
 				+ " WHERE CODIGO_ID = ?";

 			Object[] parametros = new Object[] {
				musiAlumno.getNombre().toUpperCase(),musiAlumno.getApellidoPaterno().toUpperCase(),musiAlumno.getApellidoMaterno().toUpperCase(),
				musiAlumno.getFechaNac(),musiAlumno.getInstitucionId(),musiAlumno.getSeguro(),musiAlumno.getTutor(),musiAlumno.getTelefono(),musiAlumno.getCelular(),
				musiAlumno.getEmail(),musiAlumno.getCodigoUM(),musiAlumno.getComentario(),musiAlumno.getEmpleado(),musiAlumno.getReligionId(),musiAlumno.getCiudadId(),
				musiAlumno.getEstadoId(),musiAlumno.getPaisId(),musiAlumno.getNacionalidad(),musiAlumno.getTelTrabajo(),musiAlumno.getEstado(),musiAlumno.getGenero(),
				musiAlumno.getCodigoId()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
 		}catch(Exception ex){
 			System.out.println("Error - cimum.usuario.MusiAlumnoDao|updateReg|:"+ex);		
 		}
 		
 		return ok;
 	} 
 	
 	public boolean deleteReg(String codigoId) {
 		boolean ok = false;
 		
 		try{
 			String comando = "DELETE FROM ENOC.MUSI_ALUMNO WHERE CODIGO_ID = ? ";
 			
 			Object[] parametros = new Object[] {codigoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
 		}catch(Exception ex){
 			System.out.println("Error - cimum.usuario.MusiAlumnoDao|deleteReg|:"+ex);			
 		}

 		return ok;
 	}
 	
 	public MusiAlumno mapeaRegId(String codigoId) {
 		MusiAlumno objeto = new MusiAlumno();
 		
 		try{
	 		String comando = "SELECT "
	 			+ " CODIGO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, TO_CHAR(FECHA_NAC,'DD/MM/YYYY') FECHA_NAC, "
	 			+ " INSTITUCION_ID,SEGURO, TUTOR, TELEFONO, CELULAR,EMAIL, CODIGO_UM, COMENTARIO, EMPLEADO, RELIGION_ID,"
	 			+ " CIUDAD_ID, ESTADO_ID, PAIS_ID, NACIONALIDAD, TEL_TRABAJO, ESTADO, GENERO "+
	 			" FROM ENOC.MUSI_ALUMNO WHERE CODIGO_ID = ? "; 

	 		Object[] parametros = new Object[] {codigoId};
			objeto = enocJdbc.queryForObject(comando, new MusiAlumnoMapper(), parametros);
			
 		}catch(Exception ex){
			System.out.println("Error - cimum.usuario.MusiAlumnoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
 		
 		return objeto;
 	}

 	public boolean existeReg(String codigoId) {
 		boolean ok 	= false;

 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.MUSI_ALUMNO WHERE CODIGO_ID = ?";

 			Object[] parametros = new Object[] {codigoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
 			
 		}catch(Exception ex){
 			System.out.println("Error - cimum.usuario.MusiAlumnoDao|existeReg|:"+ex);
 		}
 		
 		return ok;
 	} 	
 	
 	public String maximoReg() {		
		String year		= "";
		String maximo	= "";
		
		try{
			year = aca.util.Fecha.getHoy().substring(8,10);
			String comando = "SELECT COALESCE(MAX(SUBSTR(CODIGO_ID,3,3))+1,1) AS MAXIMO FROM ENOC.MUSI_ALUMNO"; 
			
			maximo = enocJdbc.queryForObject(comando,String.class);
			
			if (maximo.length()==1) maximo = year+"00"+maximo;
			if (maximo.length()==2) maximo = year+"0"+maximo;
			if (maximo.length()==3) maximo = year+maximo;
			if (maximo == null) maximo = year+"001";
			
		}catch(Exception ex){
			System.out.println("Error - cimum.usuario.MusiAlumnoDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
 	
 	public String maximoReg(String year) {		
		String maximo = "";
		
		try{			
			String comando = "SELECT COALESCE(MAX(SUBSTR(CODIGO_ID,3,3))+1,1) AS MAXIMO " +
					"FROM ENOC.MUSI_ALUMNO WHERE SUBSTR(CODIGO_ID,1,2) = ?"; 
			
			maximo = enocJdbc.queryForObject(comando,String.class);
			
			if (maximo.length()==1) maximo = year+"00"+maximo;
			if (maximo.length()==2) maximo = year+"0"+maximo;
			if (maximo.length()==3) maximo = year+maximo;
			if (maximo == null) maximo = year+"001";
			
		}catch(Exception ex){
			System.out.println("Error - cimum.usuario.MusiAlumnoDao|maximoReg(Year)|:"+ex);
		}
		
		return maximo;
	}
 	
	public String getNombre(String codigoId, String opcion) {
		String nombre 	= "x";
		String comando 	= "";		
		try{
			if ( opcion.equals("NOMBRE")){
				comando = "SELECT NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS NOMBRE "+
					"FROM ENOC.MUSI_ALUMNO WHERE CODIGO_ID = ?"; 
			}else{
				comando = "SELECT APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS NOMBRE "+
					"FROM ENOC.MUSI_ALUMNO WHERE CODIGO_ID = ?"; 
			}
			nombre = enocJdbc.queryForObject(comando,String.class, codigoId);			
		}catch(Exception ex){
			System.out.println("Error - cimum.usuario.MusiAlumnoDao|getNombre|:"+ex);
		}
		
		return nombre;
	}
	
	public String getNombreDividido(String codigoId) {
		String nombre		= "x";		
		try{
			String comando = "SELECT NOMBRE||','||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS NOMBRE FROM ENOC.MUSI_ALUMNO WHERE CODIGO_ID = ?"; 			
			nombre = enocJdbc.queryForObject(comando,String.class, codigoId);			
		}catch(Exception ex){
			System.out.println("Error - cimum.usuario.MusiAlumnoDao|getNombreDividido|:"+ex);
		}		
		return nombre;
	}
	
	public String getEdad(String codigoId) {
		String edad = "1";
		
		try{
			String comando = "SELECT FLOOR(MONTHS_BETWEEN(TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY'), FECHA_NAC)/12) AS EDAD" +
					" FROM ENOC.MUSI_ALUMNO" + 
					" WHERE CODIGO_ID = ?";			

			Object[] parametros = new Object[] {codigoId};
			edad = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - cimum.usuario.MusiAlumnoDao|getEdad|:"+ex);
		}
		
		return edad;
	}
	
	public String getInstitucion(String codigoId) {
		String institucion 	= "1";
		
		try{
			String comando = "SELECT INSTITUCION_ID FROM ENOC.MUSI_ALUMNO" + 
					" WHERE CODIGO_ID = ?";

			Object[] parametros = new Object[] {codigoId};
			institucion = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - cimum.usuario.MusiAlumnoDao|getInstituicion|:"+ex);
		}
		
		return institucion;
	}
	
	public String getTelefono(String codigoId) {
		String telefono = "";
		
		try{
			String comando = "SELECT COALESCE(TELEFONO,'-') AS TELEFONO FROM ENOC.MUSI_ALUMNO" + 
					" WHERE CODIGO_ID = ?";

			Object[] parametros = new Object[] {codigoId};
			telefono = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - cimum.usuario.MusiAlumnoDao|getTelefono|:"+ex);
		}
		
		return telefono;
	}
 	
	public String getCelular(String codigoId) {
		String celular 	= "";
		
		try{
			String comando = "SELECT COALESCE(CELULAR,'-') AS CELULAR FROM ENOC.MUSI_ALUMNO" + 
					" WHERE CODIGO_ID = ?";

			Object[] parametros = new Object[] {codigoId};
			celular = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - cimum.usuario.MusiAlumnoDao|getCelular|:"+ex);
		}
		
		return celular;
	}
	
	public List<MusiAlumno> getListAll(String orden) {
		List<MusiAlumno> lista	= new ArrayList<MusiAlumno>();
		
		try{
			String comando = "SELECT CODIGO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, TO_CHAR(FECHA_NAC,'DD/MM/YYYY') AS FECHA_NAC, RELIGION_ID,"
					+ " INSTITUCION_ID, SEGURO, TUTOR, TELEFONO, CELULAR, EMAIL, CODIGO_UM, COMENTARIO, EMPLEADO, CIUDAD_ID, ESTADO_ID, PAIS_ID, NACIONALIDAD, "
					+ " TEL_TRABAJO, ESTADO, GENERO"
					+ " FROM ENOC.MUSI_ALUMNO "+ orden; 
			
			lista = enocJdbc.query(comando, new MusiAlumnoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - cimum.usuario.MusiAlumnoDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<MusiAlumno> getLista(String nombre, String paterno, String materno, String orden) {
		List<MusiAlumno> lista	= new ArrayList<MusiAlumno>();
		
		try{
			String comando = "SELECT CODIGO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, FECHA_NAC, RELIGION_ID,"
					+ " INSTITUCION_ID, SEGURO, TUTOR, TELEFONO, CELULAR, EMAIL, CODIGO_UM, COMENTARIO, EMPLEADO, CIUDAD_ID, "
					+ " ESTADO_ID, PAIS_ID, NACIONALIDAD, TEL_TRABAJO, ESTADO, GENERO"
					+ " FROM ENOC.MUSI_ALUMNO"
					+ " WHERE NOMBRE LIKE UPPER('"+nombre+"%')"
					+ " AND APELLIDO_PATERNO LIKE UPPER('%"+paterno+"%')"
					+ " AND APELLIDO_MATERNO LIKE UPPER('%"+materno+"%') "+orden;
			
			lista = enocJdbc.query(comando, new MusiAlumnoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - cimum.usuario.MusiAlumnoDao|getLista|:"+ex);
		}
		
		return lista;
	}
	
	public List<MusiAlumno> lisAdmision(String estado, String orden) {
		List<MusiAlumno> lista	= new ArrayList<MusiAlumno>();
		String comando = "";
		try{
			comando = " SELECT CODIGO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, TO_CHAR(FECHA_NAC,'DD/MM/YYYY') AS FECHA_NAC, RELIGION_ID,"
					+ " INSTITUCION_ID, SEGURO, TUTOR, TELEFONO, CELULAR, EMAIL, CODIGO_UM, COMENTARIO, EMPLEADO,"
					+ " CIUDAD_ID, ESTADO_ID, PAIS_ID, NACIONALIDAD, TEL_TRABAJO, ESTADO, GENERO"
					+ " FROM ENOC.MUSI_ALUMNO"
					+ " WHERE ESTADO = ? "+orden;
			Object[] parametros = new Object[] {estado};
			lista = enocJdbc.query(comando, new MusiAlumnoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - cimum.usuario.MusiAlumnoDao|getLista|:"+ex);
		}
		
		return lista;
	}	
	
	public List<MusiAlumno> getListaAlumnosUsuario(String codigoUsuario) {
		List<MusiAlumno> lista	= new ArrayList<MusiAlumno>();
		
		try{
			String comando = "SELECT CODIGO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, FECHA_NAC, RELIGION_ID,"
					+ " INSTITUCION_ID, SEGURO, TUTOR, TELEFONO, CELULAR, EMAIL, CODIGO_UM, COMENTARIO, EMPLEADO,"
					+ " CODIGO_USUARIO, CIUDAD_ID, ESTADO_ID, PAIS_ID, NACIONALIDAD, TEL_TRABAJO, ESTADO, GENERO"
					+ " FROM ENOC.MUSI_ALUMNO WHERE CODIGO_USUARIO = ?";
			
			Object[] parametros = new Object[] {codigoUsuario};
			lista = enocJdbc.query(comando, new MusiAlumnoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - cimum.usuario.MusiAlumnoDao|getListaAlumnosUsuario|:"+ex);
		}
		
		return lista;
	}	
	
	public List<MusiAlumno> getListInscrito(String periodoId, String orden) {
		List<MusiAlumno> lista 	= new ArrayList<MusiAlumno>();		
		try{
			String comando = " SELECT CODIGO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, "
					+ " TO_CHAR(FECHA_NAC,'DD/MM/YYYY') AS FECHA_NAC, "
 					+ " INSTITUCION_ID, SEGURO, TUTOR, TELEFONO, CELULAR, "
 					+ " EMAIL, CODIGO_UM, COMENTARIO, EMPLEADO, RELIGION_ID, CIUDAD_ID, ESTADO_ID, PAIS_ID, "
 					+ " NACIONALIDAD, TEL_TRABAJO, ESTADO, GENERO "
					+ " FROM ENOC.MUSI_ALUMNO " 
					+ " WHERE CODIGO_ID IN "
					+ " (SELECT CODIGO_ID FROM ENOC.MUSI_CALCULO WHERE PERIODO_ID = ? AND ESTADO = 'I') "+orden;
			lista = enocJdbc.query(comando, new MusiAlumnoMapper(), periodoId);
		}catch(Exception ex){
			System.out.println("Error - cimum.usuario.MusiAlumnoDao|getListInscrito|:"+ex);
		}
		
		return lista;
	}
	
	public List<MusiAlumno> getListPendientes(String periodoId, String orden ) {
		List<MusiAlumno> lista	= new ArrayList<MusiAlumno>();
		
		try{
			String comando = " SELECT CODIGO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, " +
					" TO_CHAR(FECHA_NAC,'DD/MM/YYYY') AS FECHA_NAC, "+
 					" INSTITUCION_ID, SEGURO, TUTOR, TELEFONO, CELULAR, "+
 					" EMAIL, CODIGO_UM, COMENTARIO, EMPLEADO, RELIGION_ID, CIUDAD_ID, ESTADO_ID, PAIS_ID, NACIONALIDAD, TEL_TRABAJO, ESTADO, GENERO " +
					" FROM ENOC.MUSI_ALUMNO " + 
					" WHERE CODIGO_ID IN " +
					" (SELECT CODIGO_ID FROM ENOC.MUSI_CALCULO WHERE PERIODO_ID = ? AND ESTADO = 'C') "+orden;		
			lista = enocJdbc.query(comando, new MusiAlumnoMapper(), periodoId);			
		}catch(Exception ex){
			System.out.println("Error - cimum.usuario.MusiAlumnoDao|getListPendientes|:"+ex);
		}
		
		return lista;
	}
	
	public List<MusiAlumno> getListFormaPago(String periodoId, String formaPago,String orden ) {
		List<MusiAlumno> lista 	= new ArrayList<MusiAlumno>();
		
		try{
			String comando = " SELECT CODIGO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, " +
					" TO_CHAR(FECHA_NAC,'DD/MM/YYYY') AS FECHA_NAC, "+
 					" INSTITUCION_ID, SEGURO, TUTOR, TELEFONO, CELULAR, "+
 					" EMAIL, CODIGO_UM, COMENTARIO, EMPLEADO, RELIGION_ID, CIUDAD_ID, ESTADO_ID, PAIS_ID, NACIONALIDAD, TEL_TRABAJO, ESTADO, GENERO" +
					" FROM ENOC.MUSI_ALUMNO " + 
					" WHERE CODIGO_ID IN " +
					" (SELECT CODIGO_ID FROM ENOC.MUSI_CALCULO WHERE PERIODO_ID = ? AND ESTADO = 'I' AND FORMA_PAGO = 'C') "+orden;			
			lista = enocJdbc.query(comando, new MusiAlumnoMapper(), periodoId);			
		}catch(Exception ex){
			System.out.println("Error - cimum.usuario.MusiAlumnoDao|getListFormaPago|:"+ex);
		}
		
		return lista;
	}
	
	public List<MusiAlumno> getListFormaPagoPag(String periodoId, String formaPagoPag,String sobresueldo,String orden ) {
		List<MusiAlumno> lista 	= new ArrayList<MusiAlumno>();		
		try{
			String comando = " SELECT CODIGO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, " +
					" TO_CHAR(FECHA_NAC,'DD/MM/YYYY') AS FECHA_NAC, "+
 					" INSTITUCION_ID, SEGURO, TUTOR, TELEFONO, CELULAR, "+
 					" EMAIL, CODIGO_UM, COMENTARIO, EMPLEADO, RELIGION_ID, CIUDAD_ID, ESTADO_ID, PAIS_ID, NACIONALIDAD, TEL_TRABAJO, ESTADO, GENERO " +
					" FROM ENOC.MUSI_ALUMNO " + 
					" WHERE CODIGO_ID IN " +
					" (SELECT CODIGO_ID FROM ENOC.MUSI_CALCULO WHERE PERIODO_ID = '"+periodoId+"' AND ESTADO = 'I' AND FORMA_PAGO = ? AND SOBRESUELDO = ?) "+orden;			
			lista = enocJdbc.query(comando, new MusiAlumnoMapper(), formaPagoPag, sobresueldo);
		}catch(Exception ex){
			System.out.println("Error - cimum.usuario.MusiAlumnoDao|getListFormaPagoPag|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, MusiAlumno> getMapMusiAlum(Connection conn) {
		HashMap<String,MusiAlumno> mapa	= new HashMap<String,MusiAlumno>();
		List<MusiAlumno> 	lista 		= new ArrayList<MusiAlumno>();
				
		try{
			String comando = "SELECT CODIGO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO," +
					" TO_CHAR(FECHA_NAC,'DD/MM/YYYY') AS FECHA_NAC,"+
					" INSTITUCION_ID, SEGURO, TUTOR, COALESCE(TELEFONO,'-') AS TELEFONO, " +
					" COALESCE(CELULAR,'-') AS CELULAR,"+
 					" EMAIL, CODIGO_UM, COMENTARIO, EMPLEADO, RELIGION_ID, CIUDAD_ID, ESTADO_ID, PAIS_ID, NACIONALIDAD, TEL_TRABAJO, ESTADO, GENERO" +
 					" FROM ENOC.MUSI_ALUMNO"; 
			
			lista = enocJdbc.query(comando,new MusiAlumnoMapper());
			for(MusiAlumno objeto : lista){				
				mapa.put(objeto.getCodigoId(), objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - cimum.usuario.MusiAlumnoDao|getMapMusiAlum|:"+ex);
		}
		
		return mapa;
	}
	
}
