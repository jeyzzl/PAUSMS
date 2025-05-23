/**
 * 
 */
package aca.alumno.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author elifo
 *
 */
@Component
public class AlumDatosDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( AlumDatos alumDatos ) {
 		boolean ok = false;
 		
 		try{
 			String comando = "INSERT INTO ENOC.ALUM_DATOS"+ 
 				" (CODIGO_PERSONAL, FOLIO, FECHA, SINTOMAS," +
 				" MODULO, VISITA, CONOCES, NOMBRE," +
 				" TELEFONO, CORREO, CAMBIO, FIEBRE," +
 				" FEBRICULA, TOS, CEFALEA, RINORREA," +
 				" CORIZA, ARTRALGIAS, MIALGIAS, ABDOMINAL," +
 				" TORACICO, CONGESTION, LETARGIA, PERMANENCIA," +
 				" VIAJE, EXACTAMENTE, CONTACTO, CONTACTO_NOMBRE," +
 				" CONTACTO_COMUNIDAD, CONTACTO_RESIDENCIA, NECESIDAD, MUSCULAR," +
 				" AGOTAMIENTO, INICIO, ATAQUES, DIARREA," +
 				" NAUSEA, AMIGO, ESTRES, INF_INFLUENZA," +
 				" INF_CLASES, INF_E42, MANOS, ESCUPIR," +
 				" ALREDEDOR)"+
 				" VALUES(?, TO_NUMBER(?, '99999'), TO_DATE(?, 'DD/MM/YYYY'), TO_NUMBER(?, '99')," +
 				" ?, ?, ?, ?," +
 				" ?, ?, ?, ?," +
 				" ?, ?, ?, ?," +
 				" ?, ?, ?, ?," +
 				" ?, ?, ?, ?," +
 				" ?, ?, ?, ?," +
 				" ?, ?, ?, ?," +
 				" ?, ?, ?, ?," +
 				" ?, ?, ?, ?," +
 				" ?, ?, ?, ?," +
 				" ?)";
 			Object[] parametros = new Object[] {alumDatos.getCodigoPersonal(), alumDatos.getFolio(), alumDatos.getFecha(), alumDatos.getSintomas(),
 					alumDatos.getModulo(), alumDatos.getVisita(), alumDatos.getConoces(), alumDatos.getNombre(), alumDatos.getTelefono(),
 					alumDatos.getCorreo(), alumDatos.getCambio(), alumDatos.getFiebre(), alumDatos.getFebricula(), alumDatos.getTos(),
 					alumDatos.getCefalea(), alumDatos.getRinorrea(), alumDatos.getCoriza(), alumDatos.getArtralgias(), alumDatos.getMialgias(),
 					alumDatos.getAbdominal(), alumDatos.getToracico(), alumDatos.getCongestion(), alumDatos.getLetargia(), alumDatos.getPermanencia(),
 					alumDatos.getViaje(), alumDatos.getExactamente(), alumDatos.getContacto(), alumDatos.getContactoNombre(), alumDatos.getContactoComunidad(),
 					alumDatos.getContactoResidencia(), alumDatos.getNecesidad(), alumDatos.getMuscular(), alumDatos.getAgotamiento(), alumDatos.getInicio(),
 					alumDatos.getAtaques(), alumDatos.getDiarrea(), alumDatos.getNauseas(), alumDatos.getAmigo(), alumDatos.getEstres(), alumDatos.getInfInfluenza(),
 					alumDatos.getInfClases(), alumDatos.getInfE42(), alumDatos.getManos(), alumDatos.getEscupir(), alumDatos .getAlrededor()};
 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumDatosUtil|insertReg|:"+ex);			
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg( AlumDatos alumDatos ) {
 		boolean ok = false;
 		
 		try{
 			String comando = "UPDATE ENOC.ALUM_DATOS "+ 
 				"SET FECHA = TO_DATE(?, 'DD/MM/YYYY')," +
 				" SINTOMAS = TO_NUMBER(?, '99')," +
 				" MODULO = ?," +
 				" VISITA = ?," +
 				" CONOCES = ?," +
 				" NOMBRE = ?," +
 				" TELEFONO = ?," +
 				" CORREO = ?," +
 				" CAMBIO = ?," +
 				" FIEBRE = ?," +
 				" FEBRICULA = ?," +
 				" TOS = ?," +
 				" CEFALEA = ?," +
 				" RINORREA = ?," +
 				" CORIZA = ?," +
 				" ARTRALGIAS = ?," +
 				" MIALGIAS = ?," +
 				" ABDOMINAL = ?," +
 				" TORACICO = ?," +
 				" CONGESTION = ?," +
 				" LETARGIA = ?," +
 				" PERMANENCIA = ?," +
 				" VIAJE = ?," +
 				" EXACTAMENTE = ?," +
 				" CONTACTO = ?," +
 				" CONTACTO_NOMBRE = ?," +
 				" CONTACTO_COMUNIDAD = ?," +
 				" CONTACTO_RESIDENCIA = ?," +
 				" NECESIDAD = ?," +
 				" MUSCULAR = ?," +
 				" AGOTAMIENTO = ?," +
 				" INICIO = ?," +
 				" ATAQUES = ?," +
 				" DIARREA = ?," +
 				" NAUSEA = ?," +
 				" AMIGO = ?," +
 				" ESTRES = ?," +
 				" INF_INFLUENZA = ?," +
 				" INF_CLASES = ?," +
 				" INF_E42 = ?," +
 				" MANOS = ?," +
 				" ESCUPIR = ?," +
 				" ALREDEDOR = ?"+
 				" WHERE CODIGO_PERSONAL = ?" +
 				" AND FOLIO = TO_NUMBER(?, '99999')";
 			Object[] parametros = new Object[] {alumDatos.getFecha(), alumDatos.getSintomas(), alumDatos.getModulo(), alumDatos.getVisita(),
 			alumDatos.getConoces(), alumDatos.getNombre(), alumDatos.getTelefono(), alumDatos.getCorreo(), alumDatos.getCambio(),
 			alumDatos.getFiebre(), alumDatos.getFebricula(), alumDatos.getTos(), alumDatos.getCefalea(), alumDatos.getRinorrea(),
 			alumDatos.getCoriza(), alumDatos.getArtralgias(), alumDatos.getMialgias(), alumDatos.getAbdominal(),
 			alumDatos.getToracico(), alumDatos.getCongestion(), alumDatos.getLetargia(), alumDatos.getPermanencia(), alumDatos.getViaje(),
 			alumDatos.getExactamente(), alumDatos.getContacto(), alumDatos.getContactoNombre(), alumDatos.getContactoComunidad(),
 			alumDatos.getContactoResidencia(), alumDatos.getNecesidad(), alumDatos.getMuscular(), alumDatos.getAgotamiento(), alumDatos.getInicio(),
 			alumDatos.getAtaques(), alumDatos.getDiarrea(), alumDatos.getNauseas(), alumDatos.getAmigo(), alumDatos.getEstres(),
 			alumDatos.getInfInfluenza(), alumDatos.getInfClases(), alumDatos.getInfE42(), alumDatos.getManos(), alumDatos.getEscupir(),
 			alumDatos .getAlrededor(), alumDatos.getCodigoPersonal(), alumDatos.getFolio()};
 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumDatosUtil|updateReg|:"+ex);		
 		}
 		
 		return ok;
 	} 	
 	
 	
 	public boolean deleteReg( String codigoPersonal, String folio ) {
 		boolean ok = false;
 		
 		try{
 			String comando = "DELETE FROM ENOC.ALUM_DATOS"+ 
 				" WHERE CODIGO_PERSONAL = ?" +
 				" AND FOLIO = TO_NUMBER(?, '99999')";
 				Object[] parametros = new Object[] {codigoPersonal, folio};
 				if (enocJdbc.update(comando,parametros)==1){
 					ok = true;
 				}
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumDatos|deleteReg|:"+ex);			
 		}
 		
 		return ok;
 	}
 	
 	public AlumDatos mapeaRegId(  String codigoPersonal, String folio  ) {
 		
 		AlumDatos objeto = new AlumDatos();
 		
 		try{
	 		String comando = "SELECT"+
	 			" CODIGO_PERSONAL, FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY'), SINTOMAS," +
	 			" MODULO, VISITA, CONOCES, NOMBRE," +
	 			" TELEFONO, CORREO, CAMBIO, FIEBRE," +
				" FEBRICULA, TOS, CEFALEA, RINORREA," +
				" CORIZA, ARTRALGIAS, MIALGIAS, ABDOMINAL," +
				" TORACICO, CONGESTION, LETARGIA, PERMANENCIA," +
				" VIAJE, EXACTAMENTE, CONTACTO, CONTACTO_NOMBRE," +
				" CONTACTO_COMUNIDAD, CONTACTO_RESIDENCIA, NECESIDAD, MUSCULAR," +
				" AGOTAMIENTO, INICIO, ATAQUES, DIARREA," +
				" NAUSEA, AMIGO, ESTRES, INF_INFLUENZA," +
				" INF_CLASES, INF_E42, MANOS, ESCUPIR," +
				" ALREDEDOR"+
	 			" FROM ENOC.ALUM_DATOS" + 
	 			" WHERE CODIGO_PERSONAL = ?" +
	 			" AND FOLIO = TO_NUMBER(?, '99999')";
	 		
	 		Object[] parametros = new Object[] {codigoPersonal, folio};
			objeto = enocJdbc.queryForObject(comando, new AlumDatosMapper(), parametros);
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumDatosUtil|mapeaRegId|:"+ex);
 		}
 		
 		return objeto;
 	}
	
 	public boolean existeReg( String codigoPersonal, String folio) {
 		boolean ok = false;
 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_DATOS"+ 
 				" WHERE CODIGO_PERSONAL = ?" +
 				" AND FOLIO = TO_NUMBER(?, '99999')";
 			
 			Object[] parametros = new Object[] {codigoPersonal, folio};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumDatosUtil|existeReg|:"+ex);
 		}
 		
 		return ok;
 	}
 	
 	public String maximoReg( String codigoPersonal) {
 		String maximo = "1";
 		
 		try{
 			String comando = "SELECT COALESCE (MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.ALUM_DATOS"+ 
 				" WHERE CODIGO_PERSONAL = ?";
 			
 			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
 				maximo = enocJdbc.queryForObject(comando,String.class, parametros);
			}
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumDatosUtil|maximoReg|:"+ex);
 		}
 		
 		return maximo;
 	}
 	
 	public boolean fechaLlena( String codigoPersonal, String fecha) {
 		boolean ok = false;
 		
 		try{
 			String comando = "SELECT COUNT(*) AS CANTIDAD FROM ENOC.ALUM_DATOS"+ 
 				" WHERE CODIGO_PERSONAL = ?" +
 				" AND FECHA = TO_DATE(?, 'DD/MM/YYYY')";
 			Object[] parametros = new Object[] {codigoPersonal, fecha};
 			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumDatosUtil|fechaLlena|:"+ex);
 		}
 		
 		return ok;
 	}
 	
 	public String getCambioSintomas() {
 		String cantidad = "0";
 		
 		try{
 			String comando = "SELECT COUNT(*) AS CANTIDAD FROM ENOC.ALUM_DATOS" + 
 					" WHERE FIEBRE = '2'" +
 					" OR FEBRICULA = '2'" +
 					" OR TOS = '2'" +
 					" OR CEFALEA = '2'" +
 					" OR RINORREA = '2'" +
 					" OR CORIZA = '2'" +
 					" OR ARTRALGIAS = '2'" +
 					" OR MIALGIAS = '2'" +
 					" OR ABDOMINAL = '2'" +
 					" OR TORACICO = '2'" +
 					" OR CONGESTION = '2'" +
 					" OR LETARGIA = '2'";				
			if (enocJdbc.queryForObject(comando,Integer.class) >=1 ){
				cantidad = "1";
			}
			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumDatosUtil|getCambioSintomas|:"+ex);
 		}
 		
 		return cantidad;
 	}
 	
 	public String getFueronAModulo() {
 		String cantidad = "0";
 		
 		try{
 			String comando = "SELECT COUNT(DISTINCT(CODIGO_PERSONAL)) AS CANTIDAD FROM ENOC.ALUM_DATOS" + 
 					" WHERE MODULO = 'S'";
 			
 			if (enocJdbc.queryForObject(comando,Integer.class) >=1 ){
				cantidad = "1";
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumDatosUtil|getFueronAModulo|:"+ex);
 		}
 		
 		return cantidad;
 	}
 	
 	public String getVisitadas() {
 		String cantidad = "0";
 		
 		try{
 			String comando = "SELECT COUNT(DISTINCT(CODIGO_PERSONAL)) AS CANTIDAD FROM ENOC.ALUM_DATOS" + 
 					" WHERE VISITA = 'S'";
 			
 			if (enocJdbc.queryForObject(comando,Integer.class) >=1 ){
				cantidad = "1";
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumDatosUtil|getVisitadas|:"+ex);
 		}
 		
 		return cantidad;
 	}
 	
 	public String getConoces() {
 		String cantidad = "0";
 		
 		try{
 			String comando = "SELECT COUNT(DISTINCT(CODIGO_PERSONAL)) AS CANTIDAD FROM ENOC.ALUM_DATOS" + 
 					" WHERE CONOCES = 'S'";
 			
 			if (enocJdbc.queryForObject(comando,Integer.class) >=1 ){
				cantidad = "1";
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumDatosUtil|getConoces|:"+ex);
 		}
 		
 		return cantidad;
 	}
 	
 	public String getNecesidad() {
 		String cantidad = "0";
 		
 		try{
 			String comando = "SELECT COUNT(DISTINCT(CODIGO_PERSONAL)) AS CANTIDAD FROM ENOC.ALUM_DATOS" + 
 					" WHERE NECESIDAD = 'S'";
 			
 			if (enocJdbc.queryForObject(comando,Integer.class) >=1 ){
				cantidad = "1";
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumDatosUtil|getNecesidad|:"+ex);
 		}
 		
 		return cantidad;
 	}
	
	public List<AlumDatos> getListFecha( String fecha, String orden ) {
		
		List<AlumDatos> lista = new ArrayList<AlumDatos>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, SINTOMAS," +
 				" MODULO, VISITA, CONOCES, NOMBRE," +
 				" TELEFONO, CORREO, CAMBIO, FIEBRE," +
 				" FEBRICULA, TOS, CEFALEA, RINORREA," +
 				" CORIZA, ARTRALGIAS, MIALGIAS, ABDOMINAL," +
 				" TORACICO, CONGESTION, LETARGIA, PERMANENCIA," +
 				" VIAJE, EXACTAMENTE, CONTACTO, CONTACTO_NOMBRE," +
 				" CONTACTO_COMUNIDAD, CONTACTO_RESIDENCIA, NECESIDAD, MUSCULAR," +
 				" AGOTAMIENTO, INICIO, ATAQUES, DIARREA," +
 				" NAUSEA, AMIGO, ESTRES, INF_INFLUENZA," +
 				" INF_CLASES, INF_E42, MANOS, ESCUPIR," +
 				" ALREDEDOR " +
 				" FROM ENOC.ALUM_DATOS" + 
 				" WHERE FECHA = TO_DATE(?, 'DD/MM/YYYY') "+orden;			
			lista = enocJdbc.query(comando, new AlumDatosMapper(), fecha);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumDatosUtil|getListFecha|:"+ex);
		}
		
		return lista;
	}
}