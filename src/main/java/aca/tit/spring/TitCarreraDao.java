package aca.tit.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TitCarreraDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(TitCarrera carrera ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.TIT_CARRERA (CVECARRERA, NOMBRECARRERA, FECHAINICIO, FECHATERMINACION, IDAUTORIZACION, AUTORIZACION, NUMERORVOE, FOLIO)"
					+ " VALUES( ?, ?, TO_DATE(?, 'YYYY-MM-DD'), TO_DATE(?, 'YYYY-MM-DD'), TO_NUMBER(?,'99'), ?, ?, ?) ";
			
			Object[] parametros = new Object[] {
					carrera.getCveCarrera(), carrera.getNombreCarrera(), carrera.getFechaInicio(), carrera.getFechaTerminacion(), 
					carrera.getIdAutorizacion(), carrera.getAutorizacion(), carrera.getNumeroRvoe(), carrera.getFolio()
					};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitCarreraDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(TitCarrera carrera) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.TIT_CARRERA "
					+ "SET CVECARRERA = ?, "
					+ "NOMBRECARRERA = ?, "
					+ "FECHAINICIO = TO_DATE(?, 'YYYY-MM-DD'), "
					+ "FECHATERMINACION = TO_DATE(?, 'YYYY-MM-DD'), "
					+ "IDAUTORIZACION = TO_NUMBER(?,'99'), "
					+ "AUTORIZACION = ?, "
					+ "NUMERORVOE = ? "
					+ "WHERE FOLIO = ?";
			
			Object[] parametros = new Object[] {
					carrera.getCveCarrera(), carrera.getNombreCarrera(), carrera.getFechaInicio(), carrera.getFechaTerminacion(), 
					carrera.getIdAutorizacion(), carrera.getAutorizacion(), carrera.getNumeroRvoe(), carrera.getFolio()
					};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitCarreraDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg(String folio ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.TIT_CARRERA WHERE FOLIO = ?";
			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitCarreraDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public TitCarrera mapeaRegId(String folio) {
		TitCarrera carrera = new TitCarrera();
		 
		try{
			String comando = "SELECT CVECARRERA, NOMBRECARRERA, TO_CHAR(FECHAINICIO,'YYYY-MM-DD') AS FECHAINICIO,"
					+ " TO_CHAR(FECHATERMINACION,'YYYY-MM-DD') AS FECHATERMINACION, IDAUTORIZACION, AUTORIZACION, NUMERORVOE, FOLIO"
					+ " FROM ENOC.TIT_CARRERA WHERE FOLIO = ?";
			
			Object[] parametros = new Object[] {folio};
			carrera = enocJdbc.queryForObject(comando, new TitCarreraMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitCarreraDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return carrera;
		
	}	
	
	public boolean existeReg(String folio) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_CARRERA WHERE FOLIO = ?"; 
			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitCarreraDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public List<TitCarrera> lisAll( String orden) {
		List<TitCarrera> lista		= new ArrayList<TitCarrera>();
		String comando		= "";
		
		try{
			comando = " SELECT CVECARRERA, NOMBRECARRERA, TO_CHAR(FECHAINICIO,'YYYY-MM-DD') AS FECHAINICIO, TO_CHAR(FECHATERMINACION,'YYYY-MM-DD') AS FECHATERMINACION, IDAUTORIZACION, AUTORIZACION, NUMERORVOE, FOLIO"
					+ " FROM ENOC.TIT_CARRERA "+orden;	 
			
			lista = enocJdbc.query(comando, new TitCarreraMapper());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitCarreraDao|listAll|:"+ex);
		}
		return lista;
	}
	
	public String maximoReg() {
		String maximo 	= "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1, 1) AS MAXIMO FROM ENOC.TIT_CARRERA"; 
					
			Object[] parametros = new Object[] {};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitCarreraDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public HashMap<String, TitCarrera> mapaAll( ) {
		List<TitCarrera> lista			= new ArrayList<TitCarrera>();
		HashMap<String,TitCarrera> mapa	= new HashMap<String,TitCarrera>();	
		
		try{
			String comando	= "SELECT CVECARRERA, NOMBRECARRERA, TO_CHAR(FECHAINICIO,'YYYY-MM-DD') AS FECHAINICIO, TO_CHAR(FECHATERMINACION,'YYYY-MM-DD') AS FECHATERMINACION, IDAUTORIZACION, AUTORIZACION, NUMERORVOE, FOLIO"
							+ " FROM ENOC.TIT_CARRERA";					
			lista = enocJdbc.query(comando, new TitCarreraMapper());
			
			for (TitCarrera carrera : lista){
				mapa.put(carrera.getFolio(), carrera);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitCarreraDao|mapaAll|:"+ex);
		}
		return mapa;
	}
	
}