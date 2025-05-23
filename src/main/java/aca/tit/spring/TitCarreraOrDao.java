package aca.tit.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TitCarreraOrDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(TitCarreraOr carrera ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.TIT_CARRERA2 (CVE_INSTITUCION, NOMBRE_INSTITUCION, TIPO_SOSTENIMIENTO,"
					+ " TIPO_EDUCATIVO, NIVEL_ESTUDIOS, CVE_CARRERA, CARRERA) VALUES( TO_NUMBER(?,'999999'), ?, ?, ?, ?,"
					+ " TO_NUMBER(?,'999999'), ?) ";
			
			Object[] parametros = new Object[] {carrera.getCveInstitucion(),carrera.getNombreInstitucion(),carrera.getTipoSostenimiento(),
					carrera.getTipoEducativo(),carrera.getNivelEstudios(),carrera.getCveCarrera(),carrera.getCarrera()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitCarreraDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(TitCarreraOr carrera) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.TIT_CARRERA2 SET NOMBRE_INSTITUCION = ?,"
					+ " TIPO_SOSTENIMIENTO = ?,"
					+ " TIPO_EDUCATIVO = ?,"
					+ " NIVEL_ESTUDIO = ? "
					+ " WHERE CVE_INSTITUCION = TO_NUMBER(?,'999999')"
					+ " AND CVE_CARRERA = TO_NUMBER(?,'999999')";
			
			Object[] parametros = new Object[] {carrera.getNombreInstitucion(),carrera.getTipoSostenimiento(),carrera.getTipoEducativo(),
					carrera.getNivelEstudios(),carrera.getCveInstitucion(),carrera.getCveCarrera()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitCarreraDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg(String carreraId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.TIT_CARRERA2 WHERE CVE_INSTITUCION = TO_NUMBER(?,'999999') AND CVE_CARRERA = TO_NUMBER(?,'999999')";
			
			Object[] parametros = new Object[] {carreraId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitCarreraDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public TitCarreraOr mapeaRegId(  String carreraId) {
		TitCarreraOr carrera = new TitCarreraOr();
		 
		try{
			String comando = "SELECT CVE_INSTITUCION, NOMBRE_INSTITUCION, TIPO_SOSTENIMIENTO, TIPO_EDUCATIVO, NIVEL_ESTUDIOS, CVE_CARRERA, CARRERA FROM ENOC.TIT_CARRERA2 WHERE CVE_INSTITUCION = TO_NUMBER(?,'999999') AND CVE_CARRERA = TO_NUMBER(?,'999999')";
			
			Object[] parametros = new Object[] {carreraId};
			carrera = enocJdbc.queryForObject(comando, new TitCarreraOrMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitCarreraDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return carrera;
		
	}	
	
	public boolean existeReg(String carreraId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_CARRERA2 WHERE CVE_INSTITUCION = TO_NUMBER(?,'999999') AND CVE_CARRERA = TO_NUMBER(?,'999999')"; 
			
			Object[] parametros = new Object[] {carreraId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitCarreraDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public List<TitCarreraOr> listAll( String orden) {
		List<TitCarreraOr> lista		= new ArrayList<TitCarreraOr>();
		String comando		= "";
		
		try{
			comando = " SELECT CVE_INSTITUCION, NOMBRE_INSTITUCION, TIPO_SOSTENIMIENTO,"
					+ " TIPO_EDUCATIVO, NIVEL_ESTUDIOS, CVE_CARRERA, CARRERA FROM ENOC.TIT_CARRERA2"+orden;	 
			
			lista = enocJdbc.query(comando, new TitCarreraOrMapper());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitCarreraDao|listAll|:"+ex);
		}
		return lista;
	}
	
}