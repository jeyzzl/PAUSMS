/**
 * 
 */
package aca.cert.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Nio
 *
 */
@Component
public class CertCursoDao {	
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CertCurso curso) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CERT_CURSO"+ 
				"(CURSO_ID, PLAN_ID, CLAVE, CICLO_ID," +
				" CURSO_NOMBRE, FST, FSP, CREDITOS," +
				" ORDEN, TIPOCURSO_ID, CREDITOS2)"+
				" VALUES(?, ?, ?, TO_NUMBER(?, '99')," +
				" ?, ?, ?, ?," +
				" TO_NUMBER(?, '999')," +
				" TO_NUMBER(?,'99'),? )";
			Object[] parametros = new Object[] { curso.getCursoId(), curso.getPlanId(), curso.getClave(), curso.getCicloId(), curso.getCursoNombre(), curso.getFst(),curso.getFsp(),
					curso.getCreditos(), curso.getOrden(), curso.getTipoCursoId(), curso.getCreditos2()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertCursoDao|insertReg|:"+ex);			
			
		}
		
		return ok;
	}	
	
	public boolean updateReg( CertCurso curso) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CERT_CURSO"
					+ " SET PLAN_ID = ?,"
					+ " CLAVE = ?,"
					+ " CICLO_ID = TO_NUMBER(?, '99'),"
					+ " CURSO_NOMBRE = ?,"
					+ " FST = ?,"
					+ " FSP = ?,"
					+ " CREDITOS = ?,"
					+ " ORDEN = TO_NUMBER(?, '999'),"
					+ " TIPOCURSO_ID = TO_NUMBER(?, '99'),"
					+ " CREDITOS2 = ?"
					+ " WHERE CURSO_ID = ?";
			Object[] parametros = new Object[] { curso.getPlanId(), curso.getClave(), curso.getCicloId(), curso.getCursoNombre(), curso.getFst(), curso.getFsp(),curso.getCreditos(),
					curso.getOrden(), curso.getTipoCursoId(), curso.getCreditos2(), curso.getCursoId()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertCursoDao|updateReg|:"+ex);		

		}
		
		return ok;
	}
	
	public boolean deleteReg( String cursoId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CERT_CURSO"+ 
				" WHERE CURSO_ID = ?";
			Object[] parametros = new Object[] {cursoId};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertCursoDao|deleteReg|:"+ex);			

		}
		return ok;
	}
	
	public CertCurso mapeaRegId( String cursoId) {
		CertCurso curso = new CertCurso();		
		try{
			String comando = "SELECT COUNT(CURSO_ID) FROM ENOC.CERT_CURSO WHERE CURSO_ID = ?";
			Object[] parametros = new Object[] {cursoId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				comando = "SELECT CURSO_ID, PLAN_ID, CLAVE, CICLO_ID," +
					" CURSO_NOMBRE, FST, FSP, CREDITOS, ORDEN, TIPOCURSO_ID, CREDITOS2" +
					" FROM ENOC.CERT_CURSO" + 
					" WHERE CURSO_ID = ?";
				curso = enocJdbc.queryForObject(comando, new CertCursoMapper(),parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertCursoDao|mapeaRegId|:"+ex);
				
		}
		
		return curso;		
	}
	
	public boolean existeReg( String cursoId) {
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(PLAN_ID) FROM ENOC.CERT_CURSO"+ 
				" WHERE CURSO_ID = ?";
			Object[] parametros = new Object[] {cursoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertCursoDao|existeReg|:"+ex);

		}
		
		return ok;
	}
	
	public String maximoCurso( String planId) {		

		String  s_maximo 		= "001";		
		try{
			String comando = "SELECT LTRIM(COALESCE(TO_CHAR(MAX(SUBSTR(CURSO_ID,10,3)+1),'000'),'000'))AS MAXIMO FROM ENOC.CERT_CURSO WHERE PLAN_ID = ?"; 
			Object[] parametros = new Object[] {planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				s_maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertCursoDao|maximoCurso|:"+ex);

		}		
		return s_maximo;
	}

	
	public String numeroALetra(String nota) {		
		String  texto 	= "";		
		try{
			for(int i = 0; i < nota.length(); i++){
				switch(nota.charAt(i)){
					case '0':{texto += "cero ";}break;
					case '1':{texto += "uno ";}break;
					case '2':{texto += "dos ";}break;
					case '3':{texto += "tres ";}break;
					case '4':{texto += "cuatro ";}break;
					case '5':{texto += "cinco ";}break;
					case '6':{texto += "seis ";}break;
					case '7':{texto += "siete ";}break;
					case '8':{texto += "ocho ";}break;
					case '9':{texto += "nueve ";}break;
					case 'A':{texto += "* Acreditada";}break;
					case '.':{texto += "punto ";}break;
					case 'C':{texto += "";}break;
					case 'c':{texto += "";}break;
					case '*':{texto += "";}break;
					default:{						
						texto = "Error";					
					}
					break;
				}
			}
			if (nota.equals("10.0")) texto = "diez punto cero";
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertCursoDao|numeroALetra|:"+ex);
		}	
		return texto;
	}
	
	public ArrayList<CertCurso> getListPlan( String planId, String orden ) {
		
		List<CertCurso> lista	= new ArrayList<CertCurso>();
		String comando	= "";
		
		try{
			comando = "SELECT PLAN_ID, CURSO_ID, CLAVE, CICLO_ID, CURSO_NOMBRE,"+
				" FST, FSP, CREDITOS, ORDEN, TIPOCURSO_ID, CREDITOS2" +
				" FROM ENOC.CERT_CURSO WHERE PLAN_ID = ? "+ orden;		
			lista = enocJdbc.query(comando, new CertCursoMapper(), planId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertCursoDao|getListPlan|:"+ex);
		}
		
		return (ArrayList<CertCurso>) lista;
	}
	
	public HashMap<String,Integer> mapCursosPorPlan(String planId) {
		HashMap<String,Integer> mapa = new HashMap<String,Integer>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CICLO_ID AS LLAVE,COUNT(CICLO_ID) AS VALOR FROM ENOC.CERT_CURSO WHERE PLAN_ID = ? GROUP BY CICLO_ID";	

			lista = enocJdbc.query(comando, new aca.MapaMapper(), new Object[] {planId});
			
			for(aca.Mapa datos : lista) {
				mapa.put(datos.getLlave(), Integer.parseInt(datos.getValor()));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertCursoDao|mapCursosPorPlan|:"+ex);			
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapNumCursos(List<CertPlan> lisPlan) {
		HashMap<String,String> mapa = new HashMap<String,String>();
		try{
			
			for(CertPlan certPlan : lisPlan) {
				
				String comando = "SELECT COUNT(*) AS CURSOS FROM CERT_CURSO WHERE PLAN_ID = ?";
				
				String cursos = enocJdbc.queryForObject(comando, String.class, new Object[] {certPlan.getPlanId()});
				
				mapa.put(certPlan.getPlanId(), cursos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertCursoDao|mapNumCursos|:"+ex);			
		}

		return mapa;
	}
}