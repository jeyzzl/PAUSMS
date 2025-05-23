package aca.alumno.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumGraduaMatDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AlumGraduaMat alumGradMat){
		boolean ok = false;
	
		try{
			String comando = "INSERT INTO ENOC.ALUM_GRADUA_MAT"+ 
					"(CODIGO_PERSONAL, CURSO_ID, PROGRAMADA, COMENTARIO) "+
					"VALUES( ?, ?, ?,?)";
			Object[] parametros = new Object[] {alumGradMat.getCodigoPersonal(),alumGradMat.getCursoId(), alumGradMat.getProgramada(), alumGradMat.getComentario()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumGraduaMatDao|insertReg|:"+ex);			

		}
		return ok;
	}	
		
	public boolean updateReg(AlumGraduaMat alumGradMat){
		boolean ok = false;
	
		try{
			String comando = "UPDATE ENOC.ALUM_GRADUA_MAT"+ 
				" SET "+
				" PROGRAMADA =?,"+
				" COMENTARIO = ?"+
				" WHERE CODIGO_PERSONAL = ?"+
				" AND CURSO_ID = ?";	
			Object[] parametros = new Object[] {
					alumGradMat.getProgramada(),alumGradMat.getComentario(), alumGradMat.getCodigoPersonal(), alumGradMat.getCursoId()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumGraduaMatDao|updateReg|:"+ex);		
	
		}
		return ok;
	}
	
	public boolean deleteReg(String codigoPersonal, String cursoId){
		boolean ok = false;

		try{
			String comando = "DELETE FROM ENOC.ALUM_GRADUA_MAT "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, cursoId};
 			if (enocJdbc.update(comando, parametros)==1){
 				ok = true;
 			} 
		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumGraduaMatDao|deleteReg|:"+ex);			
	
		}
		return ok;
	}
	
	public boolean deleteMateriasDelPlan(String codigoPersonal, String planId){			
		boolean ok 				= false;
		
		try{
			String comando = "DELETE FROM ENOC.ALUM_GRADUA_MAT"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND SUBSTR(CURSO_ID,1,8) = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
 			if (enocJdbc.update(comando, parametros)==1){
 				ok = true;
 			} 
	
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumGraduaMatDao|deleteMateriasDelPlan|:"+ex);			
	
		}
		return ok;
	}
	
	public AlumGraduaMat mapeaRegId(  String codigoPersonal, String cursoId ){
		AlumGraduaMat alumGradMat = new AlumGraduaMat();

		try{
			String comando = "SELECT"+
				" CODIGO_PERSONAL, CURSO_ID,"+
				" PROGRAMADA, " +
				" COMENTARIO " +
				" FROM ENOC.ALUM_GRADUA_MAT "+ 
				" WHERE CODIGO_PERSONAL = ?"+
				" AND CURSO_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, cursoId};
			alumGradMat = enocJdbc.queryForObject(comando, new AlumGraduaMatMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumGraduaMatDao|mapeaRegId|:"+ex);
	
		}
		return alumGradMat;
	}
	
	public boolean existeReg( String codigoPersonal, String cursoId){
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_GRADUA_MAT "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, cursoId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumGraduaMatDao|existeReg|:"+ex);
		
		}
		return ok;
	}
	
	public boolean tieneReg( String codigoPersonal, String planId){
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_GRADUA_MAT"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND SUBSTR(CURSO_ID,1,8) = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumGraduaMatDao|tieneReg|:"+ex);
		
		}
		return ok;
	}
	
	public int numCredFaltan( String codigoPersonal, String planId){
	
		int creditos			= 0;
		
		try{
			String comando = "SELECT COALESCE(SUM(ENOC.CREDITOS(CURSO_ID)),0) AS CRED FROM ENOC.ALUM_GRADUA_MAT "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND SUBSTR(CURSO_ID,1,8) = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
 			creditos = enocJdbc.queryForObject(comando, Integer.class, parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumGraduaMatDao|numCredFaltan|:"+ex);
		}
		
		return creditos;
	}
	
	public ArrayList<AlumGraduaMat> getListAll( String orden ){
		
		List<AlumGraduaMat> lista		= new ArrayList<AlumGraduaMat>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_ID, PROGRAMADA, COMENTARIO"
					+ " FROM ENOC.ALUM_GRADUA_MAT "+ orden; 
			lista = enocJdbc.query(comando, new AlumGraduaMatMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumGraduaMatDao|getListAll|:"+ex);
	
		}
		
		return (ArrayList<AlumGraduaMat>) lista;
	}
	
	public ArrayList<AlumGraduaMat> getLista( String codigoPersonal, String cursoId, String orden ){
		
		List<AlumGraduaMat> actividad	= new ArrayList<AlumGraduaMat>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_ID, PROGRAMADA, COMENTARIO "+
					"FROM ENOC.ALUM_GRADUA_MAT "+ 
					"WHERE CODIGO_PERSONAL = ? "+
					"AND CURSO_ID = ? "+ orden;
			actividad = enocJdbc.query(comando, new AlumGraduaMatMapper(), codigoPersonal, cursoId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumGraduaMatDao|getLista|:"+ex);

		}
		
		return (ArrayList<AlumGraduaMat>) actividad;
	}
	
	public HashMap<String, AlumGraduaMat> mapaAlumnoMaterias(String codigoAlumno){
		HashMap<String, AlumGraduaMat> mapa				= new HashMap<String,AlumGraduaMat>();
		List<AlumGraduaMat> lista						= new ArrayList<AlumGraduaMat>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_ID, PROGRAMADA, COMENTARIO FROM ENOC.ALUM_GRADUA_MAT WHERE CODIGO_PERSONAL = ?";
			lista = enocJdbc.query(comando, new AlumGraduaMatMapper(), codigoAlumno);
			for (AlumGraduaMat alumno : lista){
				mapa.put(alumno.getCodigoPersonal()+alumno.getCursoId(),alumno);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumGraduaMatDao|mapaAlumnoMaterias|:"+ex);
		}
		return mapa;
	}

}