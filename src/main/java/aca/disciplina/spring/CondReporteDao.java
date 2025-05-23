package aca.disciplina.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CondReporteDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CondReporte reporte ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO "+
				"ENOC.COND_REPORTE(IDREPORTE, NOMBRE, TIPO ) "+
				"VALUES( TO_NUMBER(?,'999'), ?, ? ) ";
						
			Object[] parametros = new Object[] {reporte.getIdReporte(),reporte.getNombre(),reporte.getTipo()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondReporteDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( CondReporte reporte ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.COND_REPORTE "+ 
				"SET NOMBRE = ?, TIPO = ? "+
				"WHERE IDREPORTE = TO_NUMBER(?,'999')";
			
			Object[] parametros = new Object[] {reporte.getNombre(),reporte.getTipo(),reporte.getIdReporte()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondReporteDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg( String idReporte ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.COND_REPORTE "+ 
				"WHERE IDREPORTE = TO_NUMBER(?,'999')";
			
			Object[] parametros = new Object[] {idReporte};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondReporteDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public CondReporte mapeaRegId( String idReporte) {
		CondReporte reporte = new CondReporte();				
		try{
			String comando = "SELECT IDREPORTE, NOMBRE, TIPO FROM ENOC.COND_REPORTE WHERE IDREPORTE = TO_NUMBER(?,'999') ";			
			Object[] parametros = new Object[] {idReporte};
			reporte = enocJdbc.queryForObject(comando, new CondReporteMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondReporteDao|mapeaRegId|:"+ex);
		}
		return reporte;
	}
	
	public boolean existeReg( String idReporte) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.COND_REPORTE WHERE IDREPORTE = TO_NUMBER(?,'999') ";			
			Object[] parametros = new Object[] {idReporte};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondReporteDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg() {
		String maximo 			= "1";		
		try{
			String comando = "SELECT MAX(IDREPORTE)+1 MAXIMO FROM ENOC.COND_REPORTE";			
			Object[] parametros = new Object[] {};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondReporteDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public String nombreReporte( String idReporte) {
		String nombre 			= "X";		
		try{
			String comando = "SELECT NOMBRE FROM ENOC.COND_REPORTE WHERE IDREPORTE = TO_NUMBER(?,'999')";			
			Object[] parametros = new Object[] {idReporte};
			nombre = enocJdbc.queryForObject(comando,String.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondReporteDao|nombreReporte|:"+ex);
		}
		return nombre;
	}
	
	public String tipoReporte( String idReporte) {
		String tipo 			= "X";		
		try{
			String comando = "SELECT TIPO FROM ENOC.COND_REPORTE WHERE IDREPORTE = TO_NUMBER(?,'999')";			
			Object[] parametros = new Object[] {idReporte};
			tipo = enocJdbc.queryForObject(comando,String.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondReporteDao|tipoReporte|:"+ex);
		}
		return tipo;
	}
	
	public int getElogios( String codigoPersonal) {
		int numExtras			= 0;		
		try{
			String comando = "SELECT COUNT(IDREPORTE) AS IDREPORTE FROM ENOC.COND_REPORTE WHERE IDREPORTE IN"
					+ " (SELECT IDREPORTE FROM ENOC.COND_ALUMNO WHERE MATRICULA = ?) AND TIPO = 'C'";			
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				numExtras = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondReporteDao|getElogios|:"+ex);
		}
		return numExtras;
	}
	
	public int getUnidades( String codigoPersonal) {
		int numExtras			= 0;		
		try{
			String comando = "SELECT COUNT(IDREPORTE) AS IDREPORTE FROM ENOC.COND_REPORTE WHERE IDREPORTE IN"
					+ " (SELECT IDREPORTE FROM ENOC.COND_ALUMNO WHERE MATRICULA = ?) AND TIPO = 'D'";			
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				numExtras = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondReporteDao|getUnidades|:"+ex);
		}
		return numExtras;
	}
	
	public List<CondReporte> getListAll( String orden ) {
		List<CondReporte> lista		= new ArrayList<CondReporte>();
		try{
			String comando = "SELECT IDREPORTE, NOMBRE, TIPO FROM ENOC.COND_REPORTE "+ orden;			
			lista = enocJdbc.query(comando, new CondReporteMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondReporteDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<CondReporte> getLista( String idReporte, String orden ) {
		List<CondReporte> lista 			= new ArrayList<CondReporte>();
		try{
			String comando = "SELECT IDREPORTE, NOMBRE, TIPO FROM ENOC.COND_REPORTE WHERE IDREPORTE= TO_NUMBER(?,'999') "+ orden;			
			lista = enocJdbc.query(comando, new CondReporteMapper(), idReporte);			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondReporteDao|getLista|:"+ex);
		}
		return lista;
	}
	
	public List<CondReporte> getListTipo( String orden ) {
		List<CondReporte> lista 			= new ArrayList<CondReporte>();
		try{
			String comando = "SELECT DISTINCT(R.IDREPORTE), R.NOMBRE, R.TIPO FROM ENOC.COND_REPORTE R, " + 
					"ENOC.COND_ALUMNO A WHERE R.IDREPORTE = A.IDREPORTE AND R.TIPO IN('D','C') "+orden;			
			lista = enocJdbc.query(comando, new CondReporteMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondReporteDao|getListTipo|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, CondReporte> mapaReportes( ) {
		HashMap<String, CondReporte> mapa = new HashMap<String, CondReporte>();
		List<CondReporte>	lista 		= new ArrayList<CondReporte>();
		try{
			String comando = "SELECT IDREPORTE, NOMBRE, TIPO FROM ENOC.COND_REPORTE";			
			lista = enocJdbc.query(comando, new CondReporteMapper());
			for (CondReporte repo : lista){
				mapa.put(repo.getIdReporte(), repo);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondReporteDao|mapaReportes|:"+ex);
		}		
		return mapa;
	}
	
}