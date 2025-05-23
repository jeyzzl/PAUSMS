package aca.traspaso.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TraspasoDao {
    
    @Autowired
    @Qualifier("jdbcEnoc")
    private JdbcTemplate enocJdbc; 

	public boolean insertReg( Traspaso traspaso ) {
		
        boolean ok = false;
        try{ 			
            String comando = "INSERT INTO KRDX_TRASPASO"+ 
                " (ID_NUMBER, COURSE_GRADE, GRADE_DATE,"+
				" SUBJECT_CODE, SUBJECT_NAME, SEMESTER, SEMESTER_NAME,"+
				" PLAN_ID)"+
                " VALUES( ?, ?, TO_DATE(?,'DD/MM/YYYY'),"+
                " ?, ?, ?, ?, ?"+
				")";
            Object[] parametros = new Object[] {
                    traspaso.getIdNumber(), traspaso.getCourseGrade(), traspaso.getCourseGradeDate(),
				    traspaso.getSubjectCode(), traspaso.getSubjectName(), traspaso.getSemester(), traspaso.getSemesterName(),
					traspaso.getPlanId()
             };		
           if (enocJdbc.update(comando,parametros)==1){
               ok = true;
           }			
            
        }catch(Exception ex){
            System.out.println("Error - aca.traspaso.spring.TaraspasoDao|insertReg|:"+ex);	
        }
        return ok;
    }
    
    public boolean updateReg(Traspaso traspaso) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE KRDX_TRASPASO"+
                " COURSE_GRADE = ?,"+
                " GRADE_DATE = TO_DATE(?,'DD/MM/YYYY')"+
                " SUBJECT_CODE = ?,"+
                " SUBJECT_NAME = ?,"+
				" SEMESTER_ = ?,"+
				" SEMESTER_NAME = ?,"+
				" PLAN_ID = ?"+
				" WHERE ID_NUMBER = ? ";
			
			Object[] parametros = new Object[] {
				traspaso.getCourseGrade(), traspaso.getCourseGradeDate(),
				traspaso.getSubjectCode(), traspaso.getSubjectName(), traspaso.getSemester(), traspaso.getSemesterName(),
				traspaso.getPlanId(), traspaso.getIdNumber(), 
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	

		}catch(Exception ex){
			System.out.println("Error - aca.traspaso.spring.TaraspasoDao|updateReg|:"+ex);
		}

		return ok;
	}

    public boolean deleteReg(String idNumber) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM KRDX_TRASPASO"+
				" WHERE ID_NUMBER = ?";
			
			Object[] parametros = new Object[]{idNumber};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}

		}catch(Exception ex){
			System.out.println("Error - aca.traspaso.spring.TaraspasoDao|deleteReg|:"+ex);
		}
		
		return ok;
	}

    public Traspaso mapeaRegId(String idNumber){
		Traspaso objeto = new Traspaso();
		
		try {
			String comando = "SELECT ID_NUMBER, COURSE_GRADE, GRADE_DATE,"+
					" SUBJECT_CODE, SUBJECT_NAME, SEMESTER, SEMESTER_NAME,"+
					" PLAN_ID"+
					" FROM KRDX_TRASPASO" +
					" WHERE ID_NUMBER = ?";			
			Object[] parametros = new Object[] {idNumber};
			objeto = enocJdbc.queryForObject(comando, new TraspasoMapper(), parametros);
		} catch (Exception ex) {
			System.out.println("Error - aca.traspaso.spring.TaraspasoDao|mapeaRegId|:"+ex);
		}

		return objeto;	
	}

    public boolean existeReg(String idNumber){
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM KRDX_TRASPASO"+
				" WHERE ID_NUMBER = ? ";
			
			Object[] parametros = new Object[] {idNumber};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}

		}catch(Exception ex){
			System.out.println("Error - aca.traspaso.spring.TaraspasoDao|existeReg|:"+ex);
		}
		
		return ok;
	}

    public HashMap<String, Traspaso> mapaTraspaso(String orden) {
		
		HashMap<String,Traspaso> mapa   = new HashMap<String,Traspaso>();
		List<Traspaso> lista		    = new ArrayList<Traspaso>();
		
		try{
			String comando = "SELECT ID_NUMBER, COURSE_GRADE, GRADE_DATE,"+
			" SUBJECT_CODE, SUBJECT_NAME, SEMESTER, SEMESTER_NAME,"+
			" PLAN_ID"+
			" FROM KRDX_TRASPASO"+orden;
			lista = enocJdbc.query(comando, new TraspasoMapper());
			for (Traspaso map : lista ) {
				mapa.put(map.getIdNumber(), map);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.traspaso.spring.TaraspasoDao|mapaTraspaso|:"+ex);
		}
		
		return mapa;
	}

	public List<Traspaso> getListAll( String orden ) {
		
		List<Traspaso> lisTraspaso	= new ArrayList<Traspaso>();
		String comando	= "";
		
		try{
			comando = "SELECT ID_NUMBER, COURSE_GRADE, GRADE_DATE,"+
			" SUBJECT_CODE, SUBJECT_NAME, SEMESTER, SEMESTER_NAME,"+
			" PLAN_ID"+
			" FROM KRDX_TRASPASO"+orden;
			
			lisTraspaso = enocJdbc.query(comando, new TraspasoMapper());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.traspaso.spring.TaraspasoDao|getListAll|:"+ex);
		}
		return lisTraspaso;
	}

	public List<Traspaso> getListAlumno(String codigoPersonal, String orden ) {
		
		List<Traspaso> lisTraspaso	= new ArrayList<Traspaso>();
		String comando	= "";
		
		try{
			comando = "SELECT"+
			" ID_NUMBER, COURSE_GRADE,"+
			" GRADE_DATE,SUBJECT_CODE,SUBJECT_NAME,SEMESTER,SEMESTER_NAME,"+
			" PLAN_ID"+
			" FROM KRDX_TRASPASO"+
			" WHERE ID_NUMBER IN (SELECT CODIGO_SE FROM ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?)" + orden;
			
			lisTraspaso = enocJdbc.query(comando, new TraspasoMapper(), codigoPersonal);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.traspaso.spring.TaraspasoDao|getListAlumno|:"+ex);
		}
		return lisTraspaso;
	}

	public HashMap<String, Traspaso> mapaTraspasoAlumno( String codigoPersonal ) {
		HashMap<String, Traspaso> lisCurso	= new HashMap<String, Traspaso>();
		List<Traspaso> lista	= new ArrayList<Traspaso>();	
		
		try{
			String comando = "SELECT"+
			" ID_NUMBER, COURSE_GRADE,"+
			" GRADE_DATE,SUBJECT_CODE,SUBJECT_NAME,SEMESTER,SEMESTER_NAME,"+
			" PLAN_ID"+
			" FROM KRDX_TRASPASO"+
			" WHERE ID_NUMBER IN (SELECT CODIGO_SE FROM ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?)";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new TraspasoMapper(), parametros);
			for(Traspaso mapa : lista){		
				lisCurso.put(mapa.getSubjectCode(), mapa);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.traspaso.spring.TaraspasoDao|mapaCursosEnCarrera|:"+ex);
		}
			
		return lisCurso;
	}

	public HashMap<String, String> mapaCicloPorCurso(String codigoPersonal){
		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();

		int year = 0;
		int semester = 0;
		int cycle = 0;
		try{
			String comando = "SELECT CONCAT('1',ID_NUMBER)||SUBJECT_CODE AS LLAVE, REGEXP_SUBSTR(SEMESTER_NAME, 'Y[0-9]+S[0-9]+') AS VALOR FROM KRDX_TRASPASO" 
					+ " WHERE ID_NUMBER IN (SELECT CODIGO_SE FROM ALUM_PERSONAL WHERE CODIGO_PERSONAL = ? )";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for ( aca.Mapa map : lista) {
				year = Character.getNumericValue(map.getValor().charAt(1));
				semester = Character.getNumericValue(map.getValor().charAt(3));
				cycle = (year - 1) * 2 + semester;
				mapa.put(map.getLlave(), String.valueOf(cycle));
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.traspaso.spring.TaraspasoDao|mapaCicloPorCurso|:"+ex);
		}		
		return mapa;
	}


	public HashMap<String, String> mapaGradePorCurso(String codigoPersonal){
		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();

		try{
			String comando = "SELECT CONCAT('1',ID_NUMBER)||SUBJECT_CODE AS LLAVE, COURSE_GRADE AS VALOR FROM KRDX_TRASPASO" 
					+ " WHERE ID_NUMBER IN (SELECT CODIGO_SE FROM ALUM_PERSONAL WHERE CODIGO_PERSONAL = ? )";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.traspaso.spring.TaraspasoDao|mapaGradePorCurso|:"+ex);
		}		
		return mapa;
	}

	public HashMap<String, String> mapaNumTraspasoPorAlumno(){
		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();

		try{
			String comando = "SELECT CONCAT('1',ID_NUMBER) AS LLAVE, COUNT(DISTINCT SUBJECT_CODE) AS VALOR FROM KRDX_TRASPASO" 
					+ " WHERE ID_NUMBER IN (SELECT CODIGO_SE FROM ALUM_PERSONAL)"
					+ " GROUP BY ID_NUMBER";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.traspaso.spring.TaraspasoDao|mapaNumTraspasoPorAlumno|:"+ex);
		}		
		return mapa;
	}

	public HashMap<String, String> mapaNumTraspasoFaltantesPorAlumno(){
		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();

		try{
			String comando = "SELECT CONCAT('1',ID_NUMBER) AS LLAVE, COUNT(DISTINCT SUBJECT_CODE) AS VALOR FROM KRDX_TRASPASO" 
					+ " WHERE SUBJECT_CODE NOT IN ("
					+ " SELECT CURSO_CLAVE FROM MAPA_CURSO WHERE CURSO_ID IN (SELECT CURSO_ID FROM ALUMNO_CURSO))"
					+ " GROUP BY ID_NUMBER";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.traspaso.spring.TaraspasoDao|mapaNumTraspasoFaltantesPorAlumno|:"+ex);
		}		
		return mapa;
	}

}
