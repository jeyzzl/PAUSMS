package aca.rotaciones.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class RotProgramacionDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public boolean insertReg(RotProgramacion rotProg ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ROT_PROGRAMACION(PROGRAMACION_ID, HOSPITAL_ID, ESPECIALIDAD_ID, CODIGO_PERSONAL, " + 
					" F_INICIO, F_FINAL, INSCRIPCION, PAGO, ANUAL, INTEGRADA) " +
					" VALUES(TO_NUMBER(?,'9999999'),TO_NUMBER(?,'999'),TO_NUMBER(?,'999'),?, TO_DATE(?,'DD/MM/YYYY')," +
					" TO_DATE(?,'DD/MM/YYYY'), TO_NUMBER(?,'99999.99'), TO_NUMBER(?,'99999.99'), TO_NUMBER(?,'999999.99'), TO_NUMBER(?,'999999.99'))";
			
			Object[] parametros = new Object[] {
					rotProg.getProgramacionId(), rotProg.getHospitalId(), rotProg.getEspecialidadId(), rotProg.getCodigoPersonal(), 
					rotProg.getfInicio(), rotProg.getfFinal(), rotProg.getInscripcion(), rotProg.getPago(), rotProg.getAnual(), 
					rotProg.getIntegrada()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacion|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg(RotProgramacion rotProg ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ROT_PROGRAMACION SET HOSPITAL_ID = TO_NUMBER(?,'999') , " +
					" ESPECIALIDAD_ID =  TO_NUMBER(?,'999'), CODIGO_PERSONAL = ?, F_INICIO = TO_DATE(?,'DD/MM/YYYY'), F_FINAL = TO_DATE(?,'DD/MM/YYYY')," +
					" INSCRIPCION = TO_NUMBER(?,'99999.99'), PAGO = TO_NUMBER(?,'99999.99'), ANUAL = TO_NUMBER(?,'999999.99')," +
					" INTEGRADA = TO_NUMBER(?,'999999.99') " +
					" WHERE PROGRAMACION_ID = TO_NUMBER(?,'9999999')  ";
			
			Object[] parametros = new Object[] {
					rotProg.getHospitalId(), rotProg.getEspecialidadId(), rotProg.getCodigoPersonal(), rotProg.getfInicio(), 
					rotProg.getfFinal(), rotProg.getInscripcion(), rotProg.getPago(), rotProg.getAnual(), rotProg.getIntegrada(),
					rotProg.getProgramacionId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}

		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacion|updateReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(String programacionId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.ROT_PROGRAMACION WHERE PROGRAMACION_ID = ? "; 
			
			Object[] parametros = new Object[] {programacionId};					
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacion|deletetReg|:"+ex);
		}
		return ok;
	}
	
	public RotProgramacion mapeaRegId(String programacionId) {
		RotProgramacion rotProg = new RotProgramacion();
		
		try{
			String comando = "SELECT PROGRAMACION_ID, HOSPITAL_ID, ESPECIALIDAD_ID, CODIGO_PERSONAL, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO," +
					" TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL, INSCRIPCION, PAGO, ANUAL, INTEGRADA" +
					" FROM ENOC.ROT_PROGRAMACION WHERE PROGRAMACION_ID = ? ";
			
			Object[] parametros = new Object[] {programacionId};
			rotProg = enocJdbc.queryForObject(comando, new RotProgramacionMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacion|mapeaRegId|:"+ex);
		}
		return rotProg;
	}
	
	public boolean existeReg(String especialidadId) {
		boolean ok 			= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ROT_PROGRAMACION WHERE PROGRAMACION_ID = ? ";
			
			Object[] parametros = new Object[] {especialidadId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacion|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg() {
		String maximo			= "1";
		
		try{
			String comando = "SELECT (MAX(PROGRAMACION_ID)+1) AS MAXIMO FROM ENOC.ROT_PROGRAMACION";
		    
			Object[] parametros = new Object[] {};
			maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacion|maximoReg|:"+ex);
		}
		return maximo;
	}	
	
	public boolean existeProgramacion( String hospitalId, String especialidadId) {
		boolean ok 				= false;
		
		try{
			String comando = "SELECT * FROM ENOC.ROT_PROGRAMACION WHERE HOSPITAL_ID = ? AND ESPECIALIDAD_ID = ? ";
			
			Object[] parametros = new Object[] {hospitalId, especialidadId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacion|existeProgramacion|:"+ex);
		}
		return ok;
	}
	
	public List<RotProgramacion> getListAll( String orden ) {
		List<RotProgramacion> list	= new ArrayList<RotProgramacion>();
		String comando				= "";
		
		try{
			comando = "SELECT PROGRAMACION_ID, HOSPITAL_ID, ESPECIALIDAD_ID, CODIGO_PERSONAL," +
					"	TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL, INSCRIPCION," +
					"	PAGO, ANUAL, INTEGRADA FROM ENOC.ROT_PROGRAMACION "+orden;
			
			list = enocJdbc.query(comando, new RotProgramacionMapper());
			
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacionUtil|getListAll|:"+ex);
		}
		return list;
	}
	
	public List<RotProgramacion> getListHospEsp( String hospitalId, String especialidadId, String orden ) {
		
		List<RotProgramacion> list	= new ArrayList<RotProgramacion>();
		String comando				= "";
		
		try{
			comando = "SELECT PROGRAMACION_ID, HOSPITAL_ID, ESPECIALIDAD_ID," +
					" CODIGO_PERSONAL, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL," +
					" INSCRIPCION, PAGO, ANUAL, INTEGRADA" +
					" FROM ENOC.ROT_PROGRAMACION WHERE HOSPITAL_ID = ? AND ESPECIALIDAD_ID = ? "+orden; 
			
			list = enocJdbc.query(comando, new RotProgramacionMapper(),hospitalId,especialidadId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacionUtil|getListAll|:"+ex);
		}
		return list;
	}
	
	public List<RotProgramacion> getListHosp( String hospitalId, String orden ) {
		List<RotProgramacion> list	= new ArrayList<RotProgramacion>();
		String comando				= "";
		
		try{
			comando = "SELECT PROGRAMACION_ID, HOSPITAL_ID, ESPECIALIDAD_ID," +
					" CODIGO_PERSONAL, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL," +
					" INSCRIPCION, PAGO, ANUAL, INTEGRADA" +
					" FROM ENOC.ROT_PROGRAMACION WHERE HOSPITAL_ID = ? "+orden; 
			
			list = enocJdbc.query(comando, new RotProgramacionMapper(),hospitalId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacionUtil|getListAll|:"+ex);
		}
		return list;
	}
	
	public List<RotProgramacion> getListHospSinPago( String orden ) {
		
		List<RotProgramacion> list	= new ArrayList<RotProgramacion>();
		String comando				= "";
		
		try{
			comando = "SELECT PROGRAMACION_ID, HOSPITAL_ID, ESPECIALIDAD_ID," +
					" CODIGO_PERSONAL, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL," +
					" INSCRIPCION, PAGO, ANUAL, INTEGRADA" +
					" FROM ENOC.ROT_PROGRAMACION WHERE PAGO = 0 "+orden; 
			
			list = enocJdbc.query(comando, new RotProgramacionMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacionUtil|getListAll|:"+ex);
		}
		return list;
	}
	
	
	public List<RotProgramacion> getListHospEspConAlumnos( String hospitalId, String especialidadId, String orden ) {
		
		List<RotProgramacion> list	= new ArrayList<RotProgramacion>();
		String comando				= "";
		
		try{
			comando = "SELECT PROGRAMACION_ID, HOSPITAL_ID, ESPECIALIDAD_ID," +
					" CODIGO_PERSONAL, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL," +
					" INSCRIPCION, PAGO, ANUAL, INTEGRADA" +
					" FROM ENOC.ROT_PROGRAMACION WHERE HOSPITAL_ID = ? AND ESPECIALIDAD_ID = ? " +
					" AND CODIGO_PERSONAL IS NOT NULL "+orden; 
			
			list = enocJdbc.query(comando, new RotProgramacionMapper(),hospitalId,especialidadId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacionUtil|getListAll|:"+ex);
		}
		return list;
	}
	
	public List<RotProgramacion> getListHospEspConAlumnosFecha( String hospitalId, String especialidadId, String fecha, String orden ) {
		
		List<RotProgramacion> list	= new ArrayList<RotProgramacion>();
		String comando				= "";
		
		try{
			comando = "SELECT PROGRAMACION_ID, HOSPITAL_ID, ESPECIALIDAD_ID," +
					" CODIGO_PERSONAL, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL," +
					" INSCRIPCION, PAGO, ANUAL, INTEGRADA" +
					" FROM ENOC.ROT_PROGRAMACION WHERE HOSPITAL_ID = ? AND ESPECIALIDAD_ID = ? " +
					" AND CODIGO_PERSONAL IS NOT NULL AND TO_CHAR(F_INICIO,'DD/MM/YYYY') = ? "+orden; 
			
			Object[] parametros = new Object[] {
				hospitalId,especialidadId,fecha
			};
			
			list = enocJdbc.query(comando, new RotProgramacionMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacionUtil|getListAll|:"+ex);
		}
		return list;
	}
	
	public List<RotProgramacion> getListALumnos( String orden ) {
		
		List<RotProgramacion> list	= new ArrayList<RotProgramacion>();
		String comando				= "";
		
		try{
			comando = "SELECT * FROM ENOC.ROT_PROGRAMACION WHERE CODIGO_PERSONAL IS NOT NULL "+orden; 
			
			list = enocJdbc.query(comando, new RotProgramacionMapper());
			
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacionUtil|getListAll|:"+ex);
		}
		return list;
	}
	
	public List<String> getMatriculas( String orden ) {
		
		List<String> list	= new ArrayList<String>();
		String comando				= "";
		
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.ROT_PROGRAMACION WHERE CODIGO_PERSONAL IS NOT NULL "+orden; 
			
			list = enocJdbc.queryForList(comando, String.class);
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacionUtil|getMatriculas|:"+ex);
		}
		return list;
	}
	
	public List<RotProgramacion> getListInstitucion( String institucionId, String orden ) {
		
		List<RotProgramacion> list	= new ArrayList<RotProgramacion>();
		String comando				= "";
		
		try{
			comando = "SELECT PROGRAMACION_ID, HOSPITAL_ID, ESPECIALIDAD_ID," +
					" CODIGO_PERSONAL, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL," +
					" INSCRIPCION, PAGO, ANUAL, INTEGRADA" +
					" FROM ENOC.ROT_PROGRAMACION WHERE HOSPITAL_ID IN(SELECT HOSPITAL_ID FROM ENOC.ROT_HOSPITAL WHERE INSTITUCION_ID = "+institucionId+" )" +
					" AND CODIGO_PERSONAL IS NOT NULL "+orden; 
			
			list = enocJdbc.query(comando, new RotProgramacionMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacionUtil|getListInstitucion|:"+ex);
		}
		return list;
	}
	
	public List<RotProgramacion> getListInstitucionFechas( String institucionId, String fechaInicio, String fechaFinal, String orden ) {
		
		List<RotProgramacion> list	= new ArrayList<RotProgramacion>();
		String comando				= "";
		
		try{
			comando = "SELECT PROGRAMACION_ID, HOSPITAL_ID, ESPECIALIDAD_ID," +
					" CODIGO_PERSONAL, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL," +
					" INSCRIPCION, PAGO, ANUAL, INTEGRADA" +
					" FROM ENOC.ROT_PROGRAMACION WHERE HOSPITAL_ID IN(SELECT HOSPITAL_ID FROM ENOC.ROT_HOSPITAL WHERE INSTITUCION_ID = "+institucionId+" )" +
					" AND CODIGO_PERSONAL IS NOT NULL " +
					" AND F_INICIO >= TO_DATE(?, 'DD/MM/YYYY')" +
					" AND F_FINAL <= TO_DATE(?, 'DD/MM/YYYY')"+orden; 
			
			list = enocJdbc.query(comando, new RotProgramacionMapper(),fechaInicio,fechaFinal);
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacionUtil|getListInstitucion|:"+ex);
		}
		return list;
	}
}
