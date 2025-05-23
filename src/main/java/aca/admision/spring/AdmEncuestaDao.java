package aca.admision.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmEncuestaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AdmEncuesta admEncuesta) throws Exception{
		boolean ok = false;

		try{
			String comando = "INSERT INTO SALOMON.ADM_ENCUESTA (FOLIO, RECOMENDACION_ID,"+ 
				" R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14," +
				" TIEMPO, CONOCER, RELACION, CONDUCTA, OPINION, CENSURA, ADICIONAL, OTRA)" +
				" VALUES( TO_NUMBER(?,'99999999'), TO_NUMBER(?,'99'),"+
				" TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), TO_NUMBER(?,'99'),  TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),TO_NUMBER(?,'99')," +
				" ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), ?, TO_NUMBER(?,'99'), ?, ?, ?)";
			
			Object[] parametros = new Object[] {
				admEncuesta.getFolio(),admEncuesta.getRecomendacionId(),
				admEncuesta.getR1(),admEncuesta.getR2(),admEncuesta.getR3(),admEncuesta.getR4(),admEncuesta.getR5(),admEncuesta.getR6(),admEncuesta.getR7(),admEncuesta.getR8(),admEncuesta.getR9(),admEncuesta.getR10(),admEncuesta.getR11(),admEncuesta.getR12(),admEncuesta.getR13(), admEncuesta.getR14(),
				admEncuesta.getTiempo(),admEncuesta.getConocer(),admEncuesta.getRelacion(),admEncuesta.getConducta(),admEncuesta.getOpinion(),admEncuesta.getCensura(),admEncuesta.getAdicional(),admEncuesta.getOtra()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
	
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmEncuestaDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg(AdmEncuesta admEncuesta) {
		boolean ok = false;

		try{
			String comando = "UPDATE SALOMON.ADM_ENCUESTA " + 
					" SET R1 = TO_NUMBER(?,'99')," +
					" R2 = TO_NUMBER(?,'99'), " +
					" R3 = TO_NUMBER(?,'99'), " +
					" R4 = TO_NUMBER(?,'99'), " +
					" R5 = TO_NUMBER(?,'99'), " +
					" R6 = TO_NUMBER(?,'99'), " +
					" R7 = TO_NUMBER(?,'99'), " +
					" R8 = TO_NUMBER(?,'99'), " +
					" R9 = TO_NUMBER(?,'99'), " +
					" R10 = TO_NUMBER(?,'99'), " +
					" R11 = TO_NUMBER(?,'99'), " +
					" R12 = TO_NUMBER(?,'99'), " +
					" R13 = TO_NUMBER(?,'99'), " +
					" R14 = TO_NUMBER(?,'99'), " +
					" TIEMPO = ?, " +
					" CONOCER = TO_NUMBER(?,'99'), " +
					" RELACION = TO_NUMBER(?,'99'), CONDUCTA = ?, OPINION = TO_NUMBER(?,'99'), " +
					" CENSURA = ?, " +
					" ADICIONAL = ?, " +
					" OTRA = ? " +			
					" WHERE FOLIO = TO_NUMBER(?,'99999999') AND RECOMENDACION_ID = TO_NUMBER(?,'99') ";
			
			Object[] parametros = new Object[] {
				admEncuesta.getR1(),admEncuesta.getR2(),admEncuesta.getR3(),admEncuesta.getR4(),admEncuesta.getR5(),admEncuesta.getR6(),admEncuesta.getR7(),admEncuesta.getR8(),
				admEncuesta.getR9(),admEncuesta.getR10(),admEncuesta.getR11(),admEncuesta.getR12(),admEncuesta.getR13(),admEncuesta.getR14(),admEncuesta.getTiempo(),admEncuesta.getConocer(),
				admEncuesta.getRelacion(),admEncuesta.getConducta(),admEncuesta.getOpinion(),admEncuesta.getCensura(),admEncuesta.getAdicional(),admEncuesta.getOtra(),
				admEncuesta.getFolio(),admEncuesta.getRecomendacionId()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmEncuestaDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg(String folio, String recomendacionId ) throws Exception{
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM SALOMON.ADM_ENCUESTA "+ 
					" WHERE FOLIO = TO_NUMBER(?,'99999999') AND RECOMENDACION_ID = TO_NUMBER(?, '99') ";
			
			Object[] parametros = new Object[] {folio,recomendacionId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmEncuestaDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public AdmEncuesta mapeaRegId(String folio, String recomendacionId ) {
		AdmEncuesta objeto = new AdmEncuesta();
		
		try {
			String comando = "SELECT FOLIO, RECOMENDACION_ID, R1, R2,R3, R4, R5," +
					" R6, R7, R8, R9, R10, R11, R12, R13, R14, TIEMPO, CONOCER, RELACION, CONDUCTA, OPINION, CENSURA, ADICIONAL, OTRA " +
					" FROM SALOMON.ADM_ENCUESTA "+ 
					" WHERE FOLIO = TO_NUMBER(?,'9999999') AND RECOMENDACION_ID = TO_NUMBER(?,'99') ";
			
			Object[] parametros = new Object[] {folio,recomendacionId};
			objeto = enocJdbc.queryForObject(comando, new AdmEncuestaMapper(),parametros);
			
		} catch (Exception ex) {
			System.out.println("Error - adm.alumno.AdmEncuestaDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String folio, String recomendacionId) {
		boolean 		ok 		= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_ENCUESTA "+ 
					" WHERE FOLIO = TO_NUMBER(?,'9999999') AND RECOMENDACION_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {folio,recomendacionId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmEncuestaDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public int numEncuestasFolio(String folio){
		int total = 0;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_ENCUESTA WHERE FOLIO = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {folio};
			total = enocJdbc.queryForObject(comando,Integer.class,parametros);	
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmEncuestaDao|numEncuestasFolio|:"+ex);
		}
		
		return total;
	}
	
	public List<AdmEncuesta> getAll(String orden) {
		List<AdmEncuesta> lista	= new ArrayList<AdmEncuesta>();
		
		try{
			String comando = "SELECT FOLIO, RECOMENDACION_ID, R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14," +
			" TIEMPO, CONOCER, RELACION, CONDUCTA, OPINION, CENSURA, ADICIONAL, OTRA FROM SALOMON.ADM_ENCUESTA "+ orden; 
			
			lista = enocJdbc.query(comando, new AdmEncuestaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmEncuestaDao|getAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<AdmEncuesta> getListFolio(String folio ,String orden) {
		List<AdmEncuesta> lista	= new ArrayList<AdmEncuesta>();
		
		try{
			String comando = "SELECT FOLIO, RECOMENDACION_ID, R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14 " +
			" TIEMPO, CONOCER, RELACION, CONDUCTA, OPINION, CENSURA, ADICIONAL, OTRA FROM SALOMON.ADM_ENCUESTA WHERE FOLIO = TO_NUMBER(?,'9999999') "+ orden; 
			Object[] parametros = new Object[] {folio};
			
			lista = enocJdbc.query(comando, new AdmEncuestaMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmEncuestaDao|getListFolio|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,AdmEncuesta> mapaEncuestas(String folio) {		
		HashMap<String,AdmEncuesta> mapa = new HashMap<String,AdmEncuesta>();
		List<AdmEncuesta> lista		= new ArrayList<AdmEncuesta>();		
		try{
			String comando = "SELECT FOLIO, RECOMENDACION_ID, R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, TIEMPO, CONOCER, RELACION, CONDUCTA, OPINION, CENSURA, ADICIONAL, OTRA"
					+ " FROM SALOMON.ADM_ENCUESTA WHERE FOLIO = TO_NUMBER(?,'9999999')";					
			Object[] parametros = new Object[] {folio};
			lista = enocJdbc.query(comando, new AdmEncuestaMapper(), parametros);
			for (AdmEncuesta doc: lista ) {
				mapa.put(doc.getRecomendacionId(), doc);
			}			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.spring.AdmEncuestaDao|AdmEncuesta|:"+ex);
		}
		
		return mapa;
	}

}
