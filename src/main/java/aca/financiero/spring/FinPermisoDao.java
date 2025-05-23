package aca.financiero.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FinPermisoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( FinPermiso permiso ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.FIN_PERMISO (CODIGO_PERSONAL, FOLIO, F_INICIO, F_LIMITE, USUARIO, COMENTARIO)" +
					" VALUES(?, TO_NUMBER(?, '99'), TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'), ?, ?)";
			
			Object[] parametros = new Object[] {
				permiso.getCodigoPersonal(),permiso.getFolio(),permiso.getFInicio(),permiso.getFLimite(),permiso.getUsuario(),permiso.getComentario()	
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPermiso|insertReg|:"+ex);
		}

		return ok;
	}
	
	public boolean updateReg( FinPermiso permiso ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.FIN_PERMISO" + 
					" SET F_INICIO = TO_DATE(?, 'DD/MM/YYYY')," +
					" F_LIMITE = TO_DATE(?, 'DD/MM/YYYY')," +
					" USUARIO = ?," +
					" COMENTARIO = ?" +
					" WHERE CODIGO_PERSONAL = ?" +
					" AND FOLIO = TO_NUMBER(?, 'DD/MM/YYYY')";
			
			Object[] parametros = new Object[] {
				permiso.getFInicio(),permiso.getFLimite(),permiso.getUsuario(),permiso.getComentario(),permiso.getCodigoPersonal(),permiso.getFolio()	
			};
				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	

		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPermiso|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg( String codigoPersonal, String folio ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.FIN_PERMISO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?, '99')";
			
			Object[] parametros = new Object[] {codigoPersonal,folio};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPermiso|deleteReg|:"+ex);
		}

		return ok;
	}
	
	public FinPermiso mapeaRegId(String codigoPersonal, String folio) {
		
		FinPermiso permiso = new FinPermiso();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO," +
					" TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO," +
					" TO_CHAR(F_LIMITE, 'DD/MM/YYYY') AS F_LIMITE," +
					" USUARIO, COMENTARIO"+				
					" FROM ENOC.FIN_PERMISO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND FOLIO = TO_NUMBER(?, 'DD/MM/YYYY')";
			
			Object[] parametros = new Object[] {codigoPersonal,folio};
			permiso = enocJdbc.queryForObject(comando, new FinPermisoMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPermiso|mapeaRegId|:"+ex);
		}
		
		return permiso;
	}
	
	public boolean existeReg( String codigoPersonal, String folio) {
		boolean ok 		= false;
		
		try{
			String comando = "SELECT * FROM ENOC.FIN_PERMISO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND FOLIO = TO_NUMBER(?, 'DD/MM/YYYY')";
			
			Object[] parametros = new Object[] {codigoPersonal,folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPermiso|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String getNuevoFolio( String codigoPersonal) {
		String folio 		= "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) AS FOLIO FROM ENOC.FIN_PERMISO" + 
					" WHERE CODIGO_PERSONAL = ?";
			
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
 				folio = enocJdbc.queryForObject(comando, String.class,parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPermiso|getNuevoFolio|:"+ex);
		}
		
		return folio;
	}
	
	public boolean tienePermiso( String codigoPersonal) {
		boolean tienePermiso	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.FIN_PERMISO WHERE CODIGO_PERSONAL = ?  AND now() BETWEEN F_INICIO AND F_LIMITE";
			
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				tienePermiso = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPermiso|tienePermiso|:"+ex);
		}
		
		return tienePermiso;
	}
	
	public boolean tienePermiso( String codigoPersonal, String fecha) {
		boolean tienePermiso	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.FIN_PERMISO WHERE CODIGO_PERSONAL = ?  AND TO_DATE(?,'DD/MM/YYYY') BETWEEN F_INICIO AND F_LIMITE";			
			Object[] parametros = new Object[] {codigoPersonal, fecha};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				tienePermiso = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPermiso|tienePermiso|:"+ex);
		}
		
		return tienePermiso;
	}
	
	public boolean tienePermisoCarga( String codigoPersonal, String cargaId) {
		boolean tienePermiso	= false;
		
		try{
			String comando = "SELECT * FROM ENOC.FIN_PERMISO WHERE CODIGO_PERSONAL = ? AND F_INICIO BETWEEN "
								+ "(SELECT F_INICIO FROM ENOC.CARGA WHERE CARGA_ID = ?) AND (SELECT F_EXTRA FROM ENOC.CARGA WHERE CARGA_ID = ?)";
			
			Object[] parametros = new Object[] {codigoPersonal,cargaId,cargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				tienePermiso = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPermiso|tienePermisoCarga|:"+ex);
		}
		
		return tienePermiso;
	}
	
	public String getUsuarioePermisoCarga( String codigoPersonal, String cargaId) {
		String usuario	= "X";
		
		try{
			String comando = "SELECT USUARIO FROM ENOC.FIN_PERMISO WHERE CODIGO_PERSONAL = ? AND F_INICIO BETWEEN (SELECT F_INICIO FROM ENOC.CARGA" + 
										  " WHERE CARGA_ID = ?) AND (SELECT F_EXTRA FROM ENOC.CARGA WHERE CARGA_ID = ?)";
			
			Object[] parametros = new Object[] {codigoPersonal,cargaId,cargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				usuario = enocJdbc.queryForObject(comando, String.class,parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPermiso|getUsuarioePermisoCarga|:"+ex);
		}
		
		return usuario;
	}
	
	public String getAutorizacion(String codigoPersonal) {		
		
		double saldo = 0;
		String respuesta = "Alumno con Saldo Deudor!";
		FinSaldo finSaldo 	= new FinSaldo();
		
		try{
			RestTemplate restTemplate = new RestTemplate();
			finSaldo = restTemplate.getForObject("http://172.16.251.110:8080/infor/reportes/api/fe/saldo/"+codigoPersonal, FinSaldo.class);
			saldo = Double.parseDouble(finSaldo.getSaldoVencido())*-1; 
			
			if (saldo >= 0){
				respuesta = "Autorizado..!(Cuenta Saldada)";
			}else{
				Object[] parametros = new Object[] {codigoPersonal};
				String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.FIN_PERMISO"
						+ " WHERE CODIGO_PERSONAL = ?"
						+ " AND TO_DATE(now(),'DD-MM-YY') BETWEEN F_INICIO AND F_LIMITE";
				if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
					comando = "SELECT USUARIO FROM ENOC.FIN_PERMISO"
							+ " WHERE CODIGO_PERSONAL = ?"
							+ " AND TO_DATE(now(),'DD-MM-YY') BETWEEN F_INICIO AND F_LIMITE";
					String usuario = enocJdbc.queryForObject(comando,String.class,parametros);
					respuesta= "Autorizado..! (Permiso otrogado por: "+usuario+")";
				}							
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPermiso|getNuevoFolio|:"+ex);
		}
		
		return respuesta;
	}
	
	public List<FinPermiso> getListAlumno( String codigoPersonal, String orden ) {
		List<FinPermiso> lisAlumno	= new ArrayList<FinPermiso>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO," +
					" TO_CHAR(F_LIMITE, 'DD/MM/YYYY') AS F_LIMITE,"+
					" USUARIO, COMENTARIO FROM ENOC.FIN_PERMISO " + 
					" WHERE CODIGO_PERSONAL = ? "+orden;			
			lisAlumno = enocJdbc.query(comando, new FinPermisoMapper(), codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPermisoUtil|getListAlumno|:"+ex);
		}
		
		return lisAlumno;
	}

	public List<FinPermiso> getListAll( String orden ) {
		List<FinPermiso> lisAlumno	= new ArrayList<FinPermiso>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO," +
					" TO_CHAR(F_LIMITE, 'DD/MM/YYYY') AS F_LIMITE,"+
					" USUARIO, COMENTARIO FROM ENOC.FIN_PERMISO "+orden; 
			
			lisAlumno = enocJdbc.query(comando, new FinPermisoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPermisoUtil|getListAll|:"+ex);
		}
		
		return lisAlumno;
	}
	
	public List<FinPermiso> lisDelYear( String year, String orden ) {
		List<FinPermiso> lisAlumno	= new ArrayList<FinPermiso>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_LIMITE, 'DD/MM/YYYY') AS F_LIMITE,"
					+ " USUARIO, COMENTARIO FROM ENOC.FIN_PERMISO"
					+ " WHERE TO_CHAR(ENOC.FIN_PERMISO.F_INICIO,'YYYY') = ? "+orden;
			Object[] parametros = new Object[] {year};
			lisAlumno = enocJdbc.query(comando, new FinPermisoMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPermisoUtil|lisDelYear|:"+ex);
		}
		
		return lisAlumno;
	}
	
	// Map de alumnos con permiso financiero
	public HashMap<String,String> mapAlumnoPermiso( String condicion) {
		HashMap<String,String> map 	= new HashMap<String,String>();	
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, CODIGO_PERSONAL AS VALOR FROM ENOC.FIN_PERMISO "+condicion+"";		
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for(aca.Mapa mapa : lista){
				map.put(mapa.getLlave(), mapa.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinPermisoUtil|mapAlumnoPermiso|:"+ex);
		}
		
		return map;
	}

}
