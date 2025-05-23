//Clase para la tabla de Alum_Academico
package aca.alumno.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumAsesorDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AlumAsesor alumAsesor ){
		boolean ok = false;	
		try{
			String comando = "INSERT INTO ENOC.ALUM_ASESOR(CODIGO_PERSONAL, PLAN_ID, ASESOR_ID) VALUES( ?, ?, ?)";
			Object[] parametros = new Object[] {alumAsesor.getCodigoPersonal(),alumAsesor.getPlanId(), alumAsesor.getAsesorId()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAsesorDao|insertReg|:"+ex);			

		}
		return ok;
	}	
	
	public boolean updateReg(AlumAsesor alumAsesor ){
		boolean ok = false;
	
		try{
			String comando = "UPDATE ENOC.ALUM_ASESOR SET ASESOR_ID = ? WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {
					alumAsesor.getAsesorId(),alumAsesor.getCodigoPersonal(), alumAsesor.getPlanId()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAgendaDao|updateReg|:"+ex);		
	
		}
		return ok;
	}
	
	public boolean deleteReg(String codigoPersonal, String planId ){
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.ALUM_ASESOR WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
 			if (enocJdbc.update(comando, parametros)==1){
 				ok = true;
 			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAgendaDao|deleteReg|:"+ex);		
		}
		return ok;
	}
	
	public AlumAsesor mapeaRegId(String codigoPersonal, String planId){		
		AlumAsesor alumAsesor = new AlumAsesor();
		try{
			String comando = "SELECT CODIGO_PERSONAL, PLAN_ID, ASESOR_ID FROM ENOC.ALUM_ASESOR"
					+ " WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
			alumAsesor = enocJdbc.queryForObject(comando, new AlumAsesorMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAsesorDao|mapeaRegId|:"+ex);	
		}
		return alumAsesor;
	}
	
	public boolean existeReg(String codigoPersonal, String planId){
		boolean 		ok 		= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_ASESOR WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
 				ok = true;
 			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAsesorDao|existeReg|:"+ex);	
		}
		return ok;
	}	
	
	public String getAsesor(String codigoPersonal, String planId){
		String asesor	= "0";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_ASESOR WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
 				comando = "SELECT ASESOR_ID FROM ENOC.ALUM_ASESOR WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
 				asesor = enocJdbc.queryForObject(comando, String.class, parametros);
 			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAsesorDao|getAsesor|:"+ex);	
		}
		return asesor;
	}
	
	public List<AlumAsesor> getLisAlumno(String codigoPersonal, String orden ){
		
		List<AlumAsesor> lista= new ArrayList<AlumAsesor>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PLAN_ID, ASESOR_ID FROM ENOC.ALUM_ASESOR WHERE CODIGO_PERSONAL = ? "+orden;
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new AlumAsesorMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAsesorDao|getLisAlumno|:"+ex);		
		}				
		return lista;
	}
	
	public HashMap<String, AlumAsesor> mapaTodos(){
		HashMap<String, AlumAsesor> mapa = new HashMap<String, AlumAsesor>();
		List<AlumAsesor> lista= new ArrayList<AlumAsesor>();

		try{
			String comando = "SELECT CODIGO_PERSONAL, PLAN_ID, ASESOR_ID FROM ENOC.ALUM_ASESOR"; 
			lista = enocJdbc.query(comando, new AlumAsesorMapper());
			for (AlumAsesor asesor : lista){
				mapa.put(asesor.getCodigoPersonal()+asesor.getPlanId(), asesor);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAsesorDao|mapaTodos|:"+ex);	
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaAsesores(String codigoPersonal){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL||PLAN_ID AS LLAVE, ASESOR_ID AS VALOR FROM ENOC.ALUM_ASESOR WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAsesorDao|mapaAsesores|:"+ex);		
		}
		return mapa;
	}
}