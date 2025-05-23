package aca.alumno.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumRemedialDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AlumRemedial alumRemedial){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ALUM_REMEDIAL (CODIGO_PERSONAL, CURSO_ID, ESTADO,FECHA) VALUES(?,?,?,SYSDATE)";
			
			Object[] parametros = new Object[] {
				alumRemedial.getCodigoPersonal(),alumRemedial.getCursoId(),alumRemedial.getEstado()
			};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumRemedialDao|insertReg|:"+ex);			
		
		}
		return ok;
	}	
	
	public boolean deleteReg(String codigoPersonal){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.ALUM_REMEDIAL WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.update(comando, parametros)==1){
 				ok = true;
 			} 	
			
		}catch(Exception ex){
			System.out.println("Error  - aca.alumno.spring.AlumRemedialDao|deleteReg|:"+ex);			
		
		}
		return ok;
	}
	
	public boolean updateEstadoReg(String codigoPersonal, String estado){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ALUM_REMEDIAL SET ESTADO = ? WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {estado,codigoPersonal};
			
			if (enocJdbc.update(comando, parametros)==1){
				ok = true;
			} 	
			
		}catch(Exception ex){
			System.out.println("Error  - aca.alumno.spring.AlumRemedialDao|updateEstadoReg|:"+ex);			
			
		}
		return ok;
	}
	
	public boolean updateReg(String codigoPersonal, String cursoId){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ALUM_REMEDIAL SET ESTADO = 'A' WHERE CODIGO_PERSONAL = ? AND CURSO_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal,cursoId};
			if (enocJdbc.update(comando, parametros)==1){
				ok = true;
			} 	
			
		}catch(Exception ex){
			System.out.println("Error  - aca.alumno.spring.AlumRemedialDao|updateReg|:"+ex);			
			
		}
		return ok;
	}
	
	public AlumRemedial mapeaRegId(String codigoPersonal, String cursoId){
		
		AlumRemedial objeto = new AlumRemedial();

		try{
			String comando = "SELECT "
					+ " CODIGO_PERSONAL, CURSO_ID, ESTADO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA"
					+ " FROM ENOC.ALUM_REMEDIAL WHERE CODIGO_PERSONAL = ? AND CURSO_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, cursoId};
			objeto = enocJdbc.queryForObject(comando, new AlumRemedialMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumRemedialDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String codigoPersonal, String cursoId){
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_REMEDIAL WHERE CODIGO_PERSONAL = ? AND CURSO_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, cursoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumRemedialDao|existeReg|:"+ex);		
		}		
		return ok;
	}	
	
	public List<AlumRemedial> lista(String orden){
		List<AlumRemedial> lista = new ArrayList<AlumRemedial>();
		try{
			String comando = "SELECT CODIGO_PERSONAL,CURSO_ID,ESTADO,FECHA FROM ENOC.ALUM_REMEDIAL "+orden;
			lista = enocJdbc.query(comando, new AlumRemedialMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumRemedialDao|lista|:"+ex);		
		}		
		return lista;
	}	
	
	public List<String> listaMatriculas(){
		List<String> lista = new ArrayList<String>();
		try{
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.ALUM_REMEDIAL";
			lista = enocJdbc.queryForList(comando, String.class);
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumRemedialDao|listaMatriculas|:"+ex);		
		}		
		return lista;
	}	
	
	public List<AlumRemedial> listaAlumno(String codigoPersonal){
		List<AlumRemedial> lista = new ArrayList<AlumRemedial>();
		try{
			String comando = "SELECT CODIGO_PERSONAL,CURSO_ID,ESTADO,FECHA FROM ENOC.ALUM_REMEDIAL WHERE CODIGO_PERSONAL = ?";
			lista = enocJdbc.query(comando, new AlumRemedialMapper(), codigoPersonal);
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumRemedialDao|listaAlumno|:"+ex);		
		}		
		return lista;
	}
	
	public HashMap<String, String> mapaRemedial(String info) {
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa 		= new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL||CURSO_ID AS LLAVE, ESTADO AS VALOR FROM ENOC.ALUM_REMEDIAL "+info;
			list = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa alumno : list ) {
				mapa.put(alumno.getLlave(), alumno.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumRemedialDao|mapaRemedial|:"+ex);
		}		
		return mapa;
	}	
	
}
