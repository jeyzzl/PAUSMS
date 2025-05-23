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
 * @author Jose Torres
 *
 */
@Component
public class AlumAptitudDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( AlumAptitud alumAptitud ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO"+
				" ENOC.ALUM_APTITUD(CODIGO_PERSONAL, CARGA_ID, FUERZA, FLEXIBILIDAD," +
				" RESISTENCIA, CARDIO, PESO, TALLA," +
				" IMC, GRASA, ABDOMEN, DIETA)"+
				" VALUES( ?, ?, TO_NUMBER(?, '99'), TO_NUMBER(?, '99')," +
				" TO_NUMBER(?, '99'), TO_NUMBER(?, '99.99'), TO_NUMBER(?, '999.99'), TO_NUMBER(?, '999.99')," +
				" TO_NUMBER(?, '99.9'), TO_NUMBER(?, '99.9'), TO_NUMBER(?, '999.99'), ? ) ";
			Object[] parametros = new Object[] {alumAptitud.getCodigoPersonal(), alumAptitud.getCargaId(), alumAptitud.getFuerza(),
					alumAptitud.getFlexibilidad(), alumAptitud.getResistencia(), alumAptitud.getCardio(), alumAptitud.getPeso(),
					alumAptitud.getTalla(), alumAptitud.getImc(), alumAptitud.getGrasa(), alumAptitud.getAbdomen(), alumAptitud.getDieta()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumAptitudUtil|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( AlumAptitud alumAptitud ) {
		boolean ok = false;
		
		try{
			String comando = ("UPDATE ENOC.ALUM_APTITUD"+ 
				" SET FUERZA = TO_NUMBER(?, '99')," +
				" FLEXIBILIDAD = TO_NUMBER(?, '99')," +
				" RESISTENCIA = TO_NUMBER(?, '99')," +
				" CARDIO = TO_NUMBER(?, '99.99')," +
				" PESO = TO_NUMBER(?, '999.99')," +
				" TALLA = TO_NUMBER(?, '999.99')," +
				" IMC = TO_NUMBER(?, '99.9')," +
				" GRASA = TO_NUMBER(?, '99.9')," +
				" ABDOMEN = TO_NUMBER(?, '999.99'),"+
				" DIETA = ? "+
				" WHERE CODIGO_PERSONAL = ?" +
				" AND CARGA_ID = ?");
			Object[] parametros = new Object[] {alumAptitud.getFuerza(), alumAptitud.getFlexibilidad(), alumAptitud.getResistencia(),
			alumAptitud.getCardio(), alumAptitud.getPeso(), alumAptitud.getTalla(), alumAptitud.getImc(), alumAptitud.getGrasa(),
			alumAptitud.getAbdomen(), alumAptitud.getDieta(), alumAptitud.getCodigoPersonal(), alumAptitud.getCargaId()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumAptitudUtil|updateReg|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String codigoPersonal, String cargaId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.ALUM_APTITUD"+ 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND CARGA_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, cargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumAptitudUtil|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public AlumAptitud mapeaRegId(  String codigoPersonal, String cargaId) {
		
		AlumAptitud objeto = new AlumAptitud();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, FUERZA, FLEXIBILIDAD," +
					" RESISTENCIA, CARDIO, PESO, TALLA," +
					" IMC, GRASA, ABDOMEN, DIETA "+
					" FROM ENOC.ALUM_APTITUD WHERE CODIGO_PERSONAL = ?" + 
					" AND CARGA_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, cargaId};
			objeto = enocJdbc.queryForObject(comando, new AlumAptitudMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumAptitudUtil|mapeaRegId|:"+ex);
		}	
		
		return objeto;
	}
	
	public boolean existeReg( String codigoPersonal, String cargaId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_APTITUD" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CARGA_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, cargaId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumAptitudUtil|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<AlumAptitud> getListPeriodo( String cargaId, String orden ) {
		
		List<AlumAptitud> lista	= new ArrayList<AlumAptitud>();
		
		try{
			String comando = " SELECT CODIGO_PERSONAL, CARGA_ID, FUERZA, FLEXIBILIDAD," +
				" RESISTENCIA, CARDIO, PESO, TALLA," +
				" IMC, GRASA, ABDOMEN, DIETA"+
 				" FROM ENOC.ALUM_APTITUD " + 
 				" WHERE CARGA_ID = ? "+ orden;
			lista = enocJdbc.query(comando, new AlumAptitudMapper(), cargaId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumAptitudUtil|getListPeriodo|:"+ex);
		}			
		
		return lista;
	}
}