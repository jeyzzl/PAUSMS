package aca.dherst.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.alumno.spring.AlumAcademicoMapper;

@Component
public class DherstAlumnoDao {

    @Autowired	
	@Qualifier("jdbcEnoc")	
	private JdbcTemplate enocJdbc;

    public boolean insertReg(DherstAlumno alumno){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.DHERST_ALUMNO(FOLIO, CODIGO_PERSONAL, SLF_NO, NOMBRE, APELLIDO, DIRECCION, EMAIL, TELEFONO, CELULAR, GPA, SEXO, RESIDENCIA, RES_ESTADO_ID, ESTADO_ID, RELIGION_ID, PREF_AEROPUERTO, ESTADO_CIVIL, RESIDENCIA_TIPO, PLAN_ID, STATUS) "
                           + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, TO_NUMBER(?, '999.99'), ?, ?, TO_NUMBER(?, '999'), TO_NUMBER(?, '999'), TO_NUMBER(?, '999'), ?, ?, ?, ?, ?)";
			Object[] parametros = new Object[] {alumno.getFolio(), alumno.getCodigoPersonal(), alumno.getSlfNo(), alumno.getNombre(), alumno.getApellido(), alumno.getDireccion(), alumno.getEmail(), alumno.getTelefono(), alumno.getCelular(), 
                                                alumno.getGpa(), alumno.getSexo(), alumno.getResidencia(), alumno.getResEstadoId(), alumno.getEstadoId(), alumno.getReligionId(), alumno.getPrefAeropuerto(), alumno.getEstadoCivil(), 
                                                alumno.getResidenciaTipo(), alumno.getPlanId(), alumno.getStatus()
                                            };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.dherst.spring.DherstAlumnoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(DherstAlumno alumno) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.DHERST_ALUMNO SET FOLIO = ?, CODIGO_PERSONAL = ?, NOMBRE = ?, APELLIDO = ?, DIRECCION = ?, EMAIL = ?, TELEFONO = ?, CELULAR = ?, GPA = TO_NUMBER(?,'999.99'), SEXO = ?, RESIDENCIA = ?, RES_ESTADO_ID = TO_NUMBER(?,'999'), ESTADO_ID = TO_NUMBER(?,'999'), RELIGION_ID = TO_NUMBER(?,'999'), PREF_AEROPUERTO = ?, ESTADO_CIVIL = ?, RESIDENCIA_TIPO = ?, PLAN_ID = ?, STATUS = ? WHERE SLF_NO = ?";
			Object[] parametros = new Object[] {alumno.getFolio(), alumno.getCodigoPersonal(), alumno.getNombre(), alumno.getApellido(), alumno.getDireccion(), alumno.getEmail(), alumno.getTelefono(), alumno.getCelular(), 
                                                alumno.getGpa(), alumno.getSexo(), alumno.getResidencia(), alumno.getResEstadoId(), alumno.getEstadoId(), alumno.getReligionId(), alumno.getPrefAeropuerto(), alumno.getEstadoCivil(), 
                                                alumno.getResidenciaTipo(), alumno.getPlanId(), alumno.getStatus(), alumno.getSlfNo()
            };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.dherst.spring.DherstAlumnoDao|updateReg|:"+ex);		 
		}
		
		return ok;
	}	
	
	public boolean deleteReg(String slfNo) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.DHERST_ALUMNO WHERE SLF_NO = ?";		
			Object[] parametros = new Object[] { slfNo };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.dherst.spring.DherstAlumnoDao|deleteReg|:"+ex);			
		}
		return ok;
	}

	public boolean deleteStudents(String folio) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.DHERST_ALUMNO WHERE FOLIO = ?";		
			Object[] parametros = new Object[] { folio };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.dherst.spring.DherstAlumnoDao|deleteStudents|:"+ex);			
		}
		return ok;
	}
	
	public DherstAlumno mapeaRegId(String slfNo){
		DherstAlumno dherst = new DherstAlumno();
		try{ 
			String comando = "SELECT COUNT(SLF_NO) FROM ENOC.DHERST_ALUMNO WHERE SLF_NO = ?";
			Object[] parametros = new Object[] { slfNo };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){				
				comando = "SELECT FOLIO, CODIGO_PERSONAL, SLF_NO, NOMBRE, APELLIDO, DIRECCION, EMAIL, TELEFONO, CELULAR, GPA, SEXO, RESIDENCIA, RES_ESTADO_ID, ESTADO_ID, RELIGION_ID, PREF_AEROPUERTO, ESTADO_CIVIL, RESIDENCIA_TIPO, PLAN_ID, STATUS FROM ENOC.DHERST_ALUMNO WHERE SLF_NO = ?";							
				dherst = enocJdbc.queryForObject(comando, new DherstAlumnoMapper(), parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.dherst.spring.DherstAlumnoDao|mapeaRegId|:"+ex);
		}
		
		return dherst;
	}

	public boolean existeReg( String slfNo) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.DHERST_ALUMNO"
					+ " WHERE SLF_NO = ?";
			Object[] parametros = new Object[] {slfNo};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.dherst.spring.DherstAlumnoDao|existeReg|:"+ex);
		}
		return ok;
	}

	public String validarResidencia(String residencia){
		String resId = "0";
		try{
			if(!residencia.isEmpty()){
				if(residencia.equals("Non-residential") || residencia.equals("Non-Residential")) resId = "E";
				if(residencia.equals("Residential")) resId = "I";
			}
		}catch(Exception ex){
			System.out.println("Error - aca.dherst.spring.DherstAlumnoDao|validarResidencia|:"+ex);
		}
		return resId;
	}

	public String validarEstado(String estado) {
		String resEstadoId			= "0";		
		try{
			String comando = "SELECT ESTADO_ID FROM CAT_ESTADO WHERE NOMBRE_ESTADO LIKE(?)";	
			resEstadoId = enocJdbc.queryForObject(comando, String.class, estado.trim());			
		}catch(Exception ex){
			System.out.println("Error - aca.dherst.spring.DherstAlumnoDao|validarEstado|:"+ex);
		}		
		return resEstadoId;
	}

	public String validarReligion(String religion) {
		String religionId			= "0";	

		if(religion.trim().equals("Seventh Day Adventist")) religion = "SDA";

		try{
			String comando = "SELECT RELIGION_ID FROM CAT_RELIGION WHERE NOMBRE_RELIGION LIKE(?)";	
			religionId = enocJdbc.queryForObject(comando, String.class, religion.trim());			
		}catch(Exception ex){
			System.out.println("Error - aca.dherst.spring.DherstAlumnoDao|validarReligion|:"+ex);
		}		
		return religionId;
	}

	public String validarEstadoCivil(String estado){
		String estadoCivil = "-";
		try{
			if(!estado.isEmpty()){
				if(estado.equals("Single") || estado.equals("Single Parent")) estadoCivil = "S";
				if(estado.equals("Married")) estadoCivil = "C";
				if(estado.equals("Divorced")) estadoCivil = "D";
			}
		}catch(Exception ex){
			System.out.println("Error - aca.dherst.spring.DherstAlumnoDao|validarEstadoCivil|:"+ex);
		}
		return estadoCivil;
	}

	public String validarGPA(String gpa){
		String vGPA = "-";
		try{
			if(!gpa.isEmpty()){
				double gpaValue = Double.parseDouble(gpa);
				vGPA = String.format("%.1f", gpaValue);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.dherst.spring.DherstAlumnoDao|validarGPA|:"+ex);
		}
		return vGPA;
	}

	public String validarPlanId(String planId) {
		String resPlanId			= "0";		
		try{
			String comando = "SELECT PLAN_ID FROM MAPA_PLAN WHERE PLAN_ID = ?";	
			resPlanId = enocJdbc.queryForObject(comando, String.class, planId.trim());			
		}catch(Exception ex){
			System.out.println("Error - aca.dherst.spring.DherstAlumnoDao|validarPlanId|:"+ex);
		}		
		return resPlanId;
	}

	public List<DherstAlumno> getListaArchivo( String folio, String orden ) {
		
		List<DherstAlumno> lista = new ArrayList<DherstAlumno>();		
		try{
			String comando = "SELECT FOLIO, CODIGO_PERSONAL, SLF_NO, NOMBRE, APELLIDO, DIRECCION, EMAIL, TELEFONO, CELULAR, GPA, SEXO, RESIDENCIA, RES_ESTADO_ID, ESTADO_ID, RELIGION_ID, PREF_AEROPUERTO, ESTADO_CIVIL, RESIDENCIA_TIPO, PLAN_ID, STATUS"
					+ " FROM ENOC.DHERST_ALUMNO"
					+ " WHERE FOLIO = ? "+orden;
			lista = enocJdbc.query(comando, new DherstAlumnoMapper(), folio);			
		}catch(Exception ex){
			System.out.println("Error - aca.dherst.spring.DherstAlumnoDao|getListaArchivo|:"+ex);
		}
		return lista;
	}

	public HashMap<String, DherstAlumno> mapaAlumnos() {
		
		HashMap<String,DherstAlumno> mapa	= new HashMap<String,DherstAlumno>();
		List<DherstAlumno> lista	 		= new ArrayList<DherstAlumno>();		
		try{
			String comando = "SELECT DA.FOLIO, DA.CODIGO_PERSONAL, DA.SLF_NO, DA.NOMBRE, DA.APELLIDO, DA.DIRECCION, DA.EMAIL, DA.TELEFONO, DA.CELULAR, DA.GPA, DA.SEXO,"
					+ " DA.RESIDENCIA, DA.RES_ESTADO_ID, DA.ESTADO_ID, DA.RELIGION_ID, DA.PREF_AEROPUERTO, DA.ESTADO_CIVIL, DA.RESIDENCIA_TIPO, DA.PLAN_ID, DA.STATUS"
					+ " FROM DHERST_ALUMNO DA JOIN ALUM_PERSONAL AP ON DA.CODIGO_PERSONAL = AP.CODIGO_PERSONAL";
			lista = enocJdbc.query(comando,new DherstAlumnoMapper());
			for(DherstAlumno objeto : lista){				
				mapa.put(objeto.getCodigoPersonal(), objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.DherstAlumnoDao|mapaAlumnos|:"+ex);
		}
		
		return mapa;
	}
    
}
