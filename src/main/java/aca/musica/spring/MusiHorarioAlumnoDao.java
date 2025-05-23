package aca.musica.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MusiHorarioAlumnoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MusiHorarioAlumno musiHorarioAlum) {
 		boolean ok = false; 		
 		try{
 			String comando = "INSERT INTO ENOC.MUSI_HORARIO_ALUMNO(CODIGO_PERSONAL,FOLIO,CURSO_CARGA_ID) "+
 				"VALUES(?, TO_NUMBER(?,'9999999'), ?)";
 			
 			Object[] parametros = new Object[] {
				musiHorarioAlum.getCodigoPersonal(),musiHorarioAlum.getFolio(), musiHorarioAlum.getCursoCargaId()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.spring.MusiHorarioAlumnoDao|insertReg|:"+ex);			
 		} 		
 		return ok;
 	}	
 	
 	public boolean updateReg(MusiHorarioAlumno musiHorarioAlum) {
 		boolean ok = false; 		
 		try{
 			String comando = "UPDATE ENOC.MUSI_HORARIO_ALUMNO"		 
 				+ " SET CURSO_CARGA_ID = ?"
 				+ " WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'9999999')";
 			Object[] parametros = new Object[] {
				musiHorarioAlum.getCursoCargaId(),musiHorarioAlum.getCodigoPersonal(),musiHorarioAlum.getFolio()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.spring.MusiHorarioAlumnoDao|updateReg|:"+ex);		
 		} 		
 		return ok;
 	} 
 	
 	public boolean deleteReg(String codigoPersonal, String folio){
 		boolean ok = false; 		
 		try{
 			String comando = "DELETE FROM ENOC.MUSI_HORARIO_ALUMNO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'9999999')";
 			Object[] parametros = new Object[] {codigoPersonal, folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.spring.MusiHorarioAlumnoDao|deleteReg|:"+ex);			
 		}
 		return ok;
 	}
 	
 	public MusiHorarioAlumno mapeaRegId(String codigoPersonal, String folio){
 		MusiHorarioAlumno objeto = new MusiHorarioAlumno();
 		
 		try{
	 		String comando = "SELECT CODIGO_PERSONAL,FOLIO,CURSO_CARGA_ID FROM ENOC.MUSI_HORARIO_ALUMNO"
	 				+ " WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'9999999')"; 

	 		Object[] parametros = new Object[] {codigoPersonal, folio};
			objeto = enocJdbc.queryForObject(comando, new MusiHorarioAlumnoMapper(), parametros);
			
 		}catch(Exception ex){
			System.out.println("Error - aca.musica.spring.MusiHorarioAlumnoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
 		
 		return objeto;
 	}

 	public boolean existeReg(String codigoPersonal, String folio) {
 		boolean ok 	= false;

 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.MUSI_HORARIO_ALUMNO WHERE CODIGO_PERSONAL = ? AND FOLIO = ?";

 			Object[] parametros = new Object[] {codigoPersonal, folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.spring.MusiHorarioAlumnoDao|existeReg|:"+ex);
 		}
 		
 		return ok;
 	}
 	
 	public int getOcupadosPorMateria(String codigoPersonal, String cursoCargaId) {
 		int total = 0;
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.MUSI_HORARIO_ALUMNO WHERE CODIGO_PERSONAL = ? AND CURSO_CARGA_ID = ?";
 			Object[] parametros = new Object[] {codigoPersonal, cursoCargaId};
			total = enocJdbc.queryForObject(comando,Integer.class,parametros);			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.spring.MusiHorarioAlumnoDao|existeReg|:"+ex);
 		} 		
 		return total;
 	}	
 	
 	public List<MusiHorarioAlumno> getListAll(String orden) {
		List<MusiHorarioAlumno> lista	= new ArrayList<MusiHorarioAlumno>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL,FOLIO,CURSO_CARGA_ID FROM ENOC.MUSI_HORARIO_ALUMNO "+orden;
			
			lista = enocJdbc.query(comando, new MusiHorarioAlumnoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.spring.MusiHorarioAlumnoDao|getListAll|:"+ex);
		}
		
		return lista;
	}
 	
 	public HashMap<String,String> mapaOcupados(String cargaId) {
 		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
 		HashMap<String, String> mapa	= new HashMap<String,String>(); 		
 		try{
 			String comando = "SELECT FOLIO AS LLAVE, COUNT(*) AS VALOR FROM MUSI_HORARIO_ALUMNO WHERE FOLIO IN (SELECT FOLIO FROM MUSI_HORARIO WHERE CARGA_ID = ?) GROUP BY FOLIO";		
 			Object[] parametros = new Object[] {cargaId};
 			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
 			for(aca.Mapa horario : lista) {
 				mapa.put(horario.getLlave(), horario.getValor());
 			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.spring.MusiHorarioAlumnoDao|mapaOcupados|:"+ex);
 		}
 		
 		return mapa;
 	}
 	
 	public HashMap<String,String> mapaOcupadosAlumno(String codigoPersonal) {
 		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
 		HashMap<String, String> mapa	= new HashMap<String,String>(); 		
 		try{
 			String comando = "SELECT FOLIO AS LLAVE, FOLIO AS VALOR FROM MUSI_HORARIO_ALUMNO WHERE CODIGO_PERSONAL = ?";
 			Object[] parametros = new Object[] {codigoPersonal};
 			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
 			for(aca.Mapa horario : lista) {
 				mapa.put(horario.getLlave(), horario.getValor());
 			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.spring.MusiHorarioAlumnoDao|mapaOcupadosAlumno|:"+ex);
 		}
 		
 		return mapa;
 	}

 	public HashMap<String,MusiHorarioAlumno> mapaHorarioAlumno(String cargaId) {
 		List<MusiHorarioAlumno> lista			= new ArrayList<MusiHorarioAlumno>();
 		HashMap<String, MusiHorarioAlumno> mapa	= new HashMap<String,MusiHorarioAlumno>(); 		
 		try{
 			String comando = "SELECT FOLIO, CODIGO_PERSONAL, CURSO_CARGA_ID FROM MUSI_HORARIO_ALUMNO WHERE CURSO_CARGA_ID = ?";
 			Object[] parametros = new Object[] {cargaId};
 			lista = enocJdbc.query(comando, new MusiHorarioAlumnoMapper(), parametros);
 			for(MusiHorarioAlumno horario : lista) {
 				mapa.put(horario.getFolio(), horario);
 			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.spring.MusiHorarioAlumnoDao|mapaHorarioAlumno|:"+ex);
 		}
 		
 		return mapa;
 	}

}
