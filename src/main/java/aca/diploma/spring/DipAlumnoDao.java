package aca.diploma.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DipAlumnoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(DipAlumno objeto ){
		boolean ok = false;	
		try{
			String comando = "INSERT INTO ENOC.DIP_ALUMNO(DIPLOMA_ID, CODIGO_PERSONAL, NOMBRE, IMAGEN_QR)"+
				" VALUES(TO_NUMBER(?,'9999'), ?, ?, ?)";
			Object[] parametros = new Object[] {
				objeto.getDiplomaId(), objeto.getCodigoPersonal(), objeto.getNombre(), objeto.getImagenQr() 
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.diploma.spring.DipAlumnoDao|insertReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean updateReg( DipAlumno objeto ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.DIP_ALUMNO SET NOMBRE = ? WHERE DIPLOMA_ID = TO_NUMBER(?,'9999') AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {
				objeto.getNombre(), objeto.getDiplomaId(), objeto.getCodigoPersonal()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.diploma.spring.DipAlumnoDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean updateImagen( String diplomaId, String codigoPersonal, byte[] imagen ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.DIP_ALUMNO SET IMAGEN_QR = ? WHERE DIPLOMA_ID = TO_NUMBER(?,'9999') AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {
				imagen, diplomaId, codigoPersonal
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.diploma.spring.DipAlumnoDao|updateImagen|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg(String diplomaId, String codigoPersonal) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.DIP_ALUMNO WHERE DIPLOMA_ID = TO_NUMBER(?,'9999') AND CODIGO_PERSONAL = ?";			
			Object[] parametros = new Object[] {diplomaId, codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.diploma.spring.DipAlumnoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean existeReg(String diplomaId, String codigoPersonal){
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.DIP_ALUMNO WHERE DIPLOMA_ID = TO_NUMBER(?,'9999') AND CODIGO_PERSONAL = ?";			
			Object[] parametros = new Object[] {diplomaId, codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.diploma.spring.DipAlumnoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public DipAlumno mapeaRegId(String diplomaId, String codigoPersonal) {
		DipAlumno objeto = new DipAlumno();		
		try{
			String comando = "SELECT COUNT(DIPLOMA_ID) FROM ENOC.DIP_ALUMNO WHERE DIPLOMA_ID = TO_NUMBER(?,'9999') AND CODIGO_PERSONAL = ?";			
			Object[] parametros = new Object[] {diplomaId, codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = "SELECT DIPLOMA_ID, CODIGO_PERSONAL, NOMBRE, IMAGEN_QR FROM ENOC.DIP_ALUMNO WHERE DIPLOMA_ID = TO_NUMBER(?,'9999') AND CODIGO_PERSONAL = ?";				
				objeto = enocJdbc.queryForObject(comando, new DipAlumnoMapperLargo(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.diploma.spring.DipAlumnoDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}

	public String maxReg(){
		String max = "0";		
		try{
			String comando = "SELECT COALESCE(MAX(DIPLOMA_ID)+1,1) FROM ENOC.DIP_ALUMNO";
			max = enocJdbc.queryForObject(comando,String.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.diploma.spring.DipAlumnoDao|maxReg|:"+ex);
		}
		return max;
	}
	
	public List<DipAlumno> lisPorDiploma( String diplomaId, String orden ){
		List<DipAlumno> lista = new ArrayList<DipAlumno>();
		try{
			String comando = "SELECT DIPLOMA_ID, CODIGO_PERSONAL, NOMBRE"
					+ " FROM ENOC.DIP_ALUMNO"
					+ " WHERE DIPLOMA_ID = TO_NUMBER(?,'9999') "+orden;
			lista = enocJdbc.query(comando, new DipAlumnoMapper(), diplomaId);		
		}catch(Exception ex){
			System.out.println("Error - aca.diploma.spring.DipAlumnoDao|lisPorDiploma|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String,String> mapaAlumnos(){
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();		
		try {
			String comando = "SELECT DIPLOMA_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.DIP_ALUMNO GROUP BY DIPLOMA_ID"; 
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for(aca.Mapa opcion : lista) {				
				mapa.put(opcion.getLlave(), opcion.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.diploma.spring.DipAlumnoDao|mapaAlumnos|:"+ex);
		}		
		return mapa;
	}
}