//Clase  para la tabla Materias_Insc
package aca.catalogo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatGradePointDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatGradePoint grade ) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.CAT_GRADEPOINT(GP_ID, GP_NOMBRE, INICIO, FIN, PUNTOS, TITULO)"+
				" VALUES( ?, ?, TO_NUMBER(?,'999.99'), TO_NUMBER(?,'999.99'), TO_NUMBER(?,'9.9'), ? ) ";
			Object[] parametros = new Object[] {grade.getGpId(), grade.getGpNombre(), grade.getInicio(), grade.getFin(), grade.getPuntos(), grade.getTitulo()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatGradePointDao|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean updateReg( CatGradePoint grade ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CAT_GRADEPOINT"
					+ " SET GP_NOMBRE= ?,"
					+ " INICIO = TO_NUMBER(?,'999.99'),"
					+ " FIN = TO_NUMBER(?,'999.99'),"
					+ " PUNTOS = TO_NUMBER(?,'9.9'),"
					+ " TITULO = ?"
					+ " WHERE GP_ID = ?";
			Object[] parametros = new Object[] {grade.getGpNombre(), grade.getInicio(), grade.getFin(), grade.getPuntos(), grade.getTitulo(), grade.getGpId()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatGradePointDao|updateReg|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String gradeId ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CAT_GRADEPOINT WHERE GP_ID = ?";
			Object[] parametros = new Object[] {gradeId};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatGradePointDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	
	public boolean existeReg( String gradeId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_GRADEPOINT WHERE GP_ID = ? ";			
			Object[] parametros = new Object[] {gradeId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatGradePointDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	public String maximoReg() {
		String maximo 			= "1";		
		try{
			String comando = "SELECT COALESCE(MAX(GP_ID)+1,1) AS MAXIMO FROM ENOC.CAT_GRADEPOINT";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatGradePointDao|maximoReg|:"+ex);
		}
		
		return maximo;		
	}
	
	public CatGradePoint mapeaRegId(  String gradeId) {		
		CatGradePoint grade 		= new CatGradePoint();
		
		try{
			String comando = "SELECT GP_ID, GP_NOMBRE, INICIO, FIN, PUNTOS, TITULO FROM ENOC.CAT_GRADEPOINT WHERE GP_ID = ?";
			Object[] parametros = new Object[] {gradeId};
			grade = enocJdbc.queryForObject(comando, new CatGradePointMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatGradePointDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return grade;
	}
	
	public List<CatGradePoint> lisTodos( String orden) {
		
		List<CatGradePoint> lista		= new ArrayList<CatGradePoint>();				
		try{
			String comando = "SELECT GP_ID, GP_NOMBRE, INICIO, FIN, PUNTOS, TITULO FROM ENOC.CAT_GRADEPOINT "+orden;	 
			lista = enocJdbc.query(comando, new CatGradePointMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatGradePointDao|lisTodos|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,CatGradePoint> getMapAll( String orden ) {
		
		HashMap<String,CatGradePoint> mapa = new HashMap<String,CatGradePoint>();
		List<CatGradePoint> lista		= new ArrayList<CatGradePoint>();
		
		try{
			String comando = "SELECT GP_ID, GP_NOMBRE, INICIO, FIN, PUNTOS, TITULO FROM ENOC.CAT_GRADEPOINT "+ orden;
			lista =  enocJdbc.query(comando, new CatGradePointMapper());
			for (CatGradePoint grade : lista ) {
				mapa.put(grade.getGpId(), grade);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatGradePointDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}	
	
	public HashMap<String,String> mapaLetrasDeNotas( String orden ) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<CatGradePoint> lista		= this.lisTodos("ORDER BY INICIO");		
		try{
			for (CatGradePoint grade : lista ) {
				for (float i = Float.parseFloat(grade.getInicio());i<= Float.parseFloat(grade.getFin()); i++){
					mapa.put(String.valueOf((int)i), grade.getGpNombre());
				}
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatGradePointDao|mapaLetrasDeNotas|:"+ex);
		}
		
		return mapa;
	}
	
}