package aca.diploma.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DipCursoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(DipCurso objeto ){
		boolean ok = false;	
		try{
			String comando = "INSERT INTO ENOC.DIP_CURSO(DIPLOMA_ID, INSTITUCION, CURSO, TEMA, HORAS, PERIODO, FECHA, FIRMA_UNO, FIRMA_DOS)"+
				" VALUES(TO_NUMBER(?,'9999'), ?, ?, ?, ?, ?, ?, ?, ?)";			
			Object[] parametros = new Object[] {
				objeto.getDiplomaId(), objeto.getInstitucion(), objeto.getCurso(), objeto.getTema(), objeto.getHoras(), objeto.getPeriodo(), objeto.getFecha(), objeto.getFirmaUno(), objeto.getFirmaDos() 
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.diploma.spring.DipCurso|insertReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean updateReg( DipCurso objeto ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.DIP_CURSO"
				+ " SET INSTITUCION = ?,"
				+ " CURSO = ?,"
				+ " TEMA = ?,"
				+ " HORAS = ?,"
				+ " PERIODO = ?,"
				+ " FECHA = ?,"
				+ " FIRMA_UNO = ?,"
				+ " FIRMA_DOS = ?"
				+ " WHERE DIPLOMA_ID = TO_NUMBER(?,'9999')";			
			Object[] parametros = new Object[] {
				objeto.getInstitucion(), objeto.getCurso(),objeto.getTema(), objeto.getHoras(), objeto.getPeriodo(), objeto.getFecha(), objeto.getFirmaUno(), objeto.getFirmaDos(), objeto.getDiplomaId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.diploma.spring.DipCurso|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg(String diplomaId) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.DIP_CURSO WHERE DIPLOMA_ID = TO_NUMBER(?,'9999')";			
			Object[] parametros = new Object[] {diplomaId};					
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.diploma.spring.DipCurso|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean existeReg(String diplomaId) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.DIP_CURSO WHERE DIPLOMA_ID = TO_NUMBER(?,'9999')";			
			Object[] parametros = new Object[] {diplomaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.diploma.spring.DipCurso|existeReg|:"+ex);
		}
		return ok;
	}
	
	public DipCurso mapeaRegId(String diplomaId) {
		DipCurso objeto = new DipCurso();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.DIP_CURSO WHERE DIPLOMA_ID = TO_NUMBER(?,'9999')";			
			Object[] parametros = new Object[] {diplomaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = "SELECT DIPLOMA_ID, INSTITUCION, CURSO, TEMA, HORAS, PERIODO, FECHA, FIRMA_UNO, FIRMA_DOS FROM ENOC.DIP_CURSO WHERE DIPLOMA_ID = TO_NUMBER(?,'9999')";				
				objeto = enocJdbc.queryForObject(comando, new DipCursoMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.diploma.spring.DipCurso|mapeaRegId|:"+ex);
		}
		return objeto;
	}

	public String maxReg(){
		String max = "0";		
		try{
			String comando = "SELECT COALESCE(MAX(DIPLOMA_ID)+1,1) FROM ENOC.DIP_CURSO";
			max = enocJdbc.queryForObject(comando,String.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.diploma.spring.DipCurso|maxReg|:"+ex);
		}
		return max;
	}
	
	public List<DipCurso> lisTodos( String orden ) {
		List<DipCurso> lista = new ArrayList<DipCurso>();		
		try{
			String comando = "SELECT DIPLOMA_ID, INSTITUCION, CURSO, TEMA, HORAS, PERIODO, FECHA, FIRMA_UNO, FIRMA_DOS"
					+ " FROM ENOC.DIP_CURSO "+orden;
			lista = enocJdbc.query(comando, new DipCursoMapper());		
		}catch(Exception ex){
			System.out.println("Error - aca.diploma.spring.DipCurso|lisTodos|:"+ex);
		}
		return lista;
	}
}