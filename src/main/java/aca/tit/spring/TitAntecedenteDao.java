package aca.tit.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TitAntecedenteDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(TitAntecedente antecedente) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.TIT_ANTECEDENTE (FOLIO, INSTITUCION, ESTUDIOID, ESTUDIO, ENTIDADID, ENTIDAD, FECHAINICIO, FECHATERMINACION, CEDULA)"
					+ " VALUES( ?, ?, TO_NUMBER(?,'99'), ?, ?, ?, TO_DATE(?,'YYYY-MM-DD'), TO_DATE(?,'YYYY-MM-DD'), ? ) ";
			
			Object[] parametros = new Object[] {antecedente.getFolio(), antecedente.getInstitucion(), antecedente.getEstudioId(),
					antecedente.getEstudio(), antecedente.getEntidadId(), antecedente.getEntidad(),
					antecedente.getFechaInicio(), antecedente.getFechaTerminacion(), antecedente.getCedula()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAntecedenteDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg(TitAntecedente antecedente) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.TIT_ANTECEDENTE"
					+ " SET INSTITUCION = ?,"
					+ " ESTUDIOID = TO_NUMBER(?,'99'),"
					+ " ESTUDIO = ?,"
					+ " ENTIDADID = ?,"
					+ " ENTIDAD = ?,"
					+ " FECHAINICIO = TO_DATE(?,'YYYY-MM-DD'),"
					+ " FECHATERMINACION = TO_DATE(?,'YYYY-MM-DD'),"
					+ " CEDULA = ?"
					+ " WHERE FOLIO = ?";			
			Object[] parametros = new Object[] {antecedente.getInstitucion(), antecedente.getEstudioId(),
					antecedente.getEstudio(), antecedente.getEntidadId(), antecedente.getEntidad(),
					antecedente.getFechaInicio(), antecedente.getFechaTerminacion(),antecedente.getCedula(), antecedente.getFolio()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAntecedenteDao|updateReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean deleteReg(String folio ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.TIT_ANTECEDENTE WHERE FOLIO = ?";
			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAntecedenteDao|deleteReg|:"+ex);			
		}
		return ok;
	}	
	
	public TitAntecedente mapeaRegId(  String folio) {
		TitAntecedente antecedente = new TitAntecedente();
		 
		try{
			String comando = "SELECT FOLIO, INSTITUCION, ESTUDIOID, ESTUDIO, ENTIDADID, ENTIDAD,"
					+ " TO_CHAR(FECHAINICIO, 'YYYY-MM-DD') AS FECHAINICIO, TO_CHAR(FECHATERMINACION, 'YYYY-MM-DD') AS FECHATERMINACION, CEDULA"
					+ " FROM ENOC.TIT_ANTECEDENTE WHERE FOLIO = ?";
			
			Object[] parametros = new Object[] {folio};
			antecedente = enocJdbc.queryForObject(comando, new TitAntecedenteMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAntecedenteDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return antecedente;
		
	}
	
	public boolean existeReg(String folio) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_ANTECEDENTE WHERE FOLIO = ?"; 
			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAntecedenteDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public List<TitAntecedente> listAll( String orden) {
		List<TitAntecedente> lista		= new ArrayList<TitAntecedente>();
		String comando		= "";
		
		try{
			comando = " SELECT FOLIO, INSTITUCION, ESTUDIOID, ESTUDIO, ENTIDADID, ENTIDAD, TO_CHAR(FECHAINICIO,'YYYY-MM-DD') AS FECHAINICIO, TO_CHAR(FECHATERMINACION,'YYYY-MM-DD') AS FECHATERMINACION, CEDULA"
					+ " FROM ENOC.TIT_ANTECEDENTE "+orden;	 
			
			lista = enocJdbc.query(comando, new TitAntecedenteMapper());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.TitAntecedenteDao|listAll|:"+ex);
		}
		return lista;
	}
	
	public String maximoReg() {
		String maximo 	= "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1, 1) AS MAXIMO FROM ENOC.TIT_ANTECEDENTE"; 
					
			Object[] parametros = new Object[] {};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAntecedenteDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public HashMap<String, TitAntecedente> mapaAll( ) {
		List<TitAntecedente> lista			= new ArrayList<TitAntecedente>();
		HashMap<String,TitAntecedente> mapa	= new HashMap<String,TitAntecedente>();	
		
		try{
			String comando	= "SELECT FOLIO, INSTITUCION, ESTUDIOID, ESTUDIO, ENTIDADID, ENTIDAD, TO_CHAR(FECHAINICIO,'YYYY-MM-DD') AS FECHAINICIO, TO_CHAR(FECHATERMINACION,'YYYY-MM-DD') AS FECHATERMINACION, CEDULA"					
					+ " FROM ENOC.TIT_ANTECEDENTE";							
			lista = enocJdbc.query(comando, new TitAntecedenteMapper());
			
			for (TitAntecedente ant : lista){
				mapa.put(ant.getFolio(), ant);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAntecedenteDao|mapaAll|:"+ex);
		}
		return mapa;
	}
	
}