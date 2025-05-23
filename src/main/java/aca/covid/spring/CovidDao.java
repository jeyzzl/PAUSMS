package aca.covid.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CovidDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(Covid objeto) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.COVID"
					+ " (CODIGO_PERSONAL, PERIODO_ID, HIPERTENSION, DIABETES, CORAZON, PULMON, CANCER, OBESIDAD, ESTRES, DEPRESION, IMSS, PASE, ISSTE, HLC, PRIVADO, NINGUNO, OTRO) "
					+ " VALUES(?,TO_NUMBER(?,'99'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			Object[] parametros = new Object[] {objeto.getCodigoPersonal(),objeto.getPeriodoId(),objeto.getHipertension(),objeto.getDiabetes(),objeto.getCorazon(),objeto.getPulmon(),
					objeto.getCancer(),objeto.getObesidad(),objeto.getEstres(),objeto.getDepresion(),objeto.getImss(),objeto.getPase(),objeto.getIsste(),objeto.getHlc(),
					objeto.getPrivado(),objeto.getNinguno(),objeto.getOtro()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.CovidDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(Covid objeto) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.COVID"
					+ " SET HIPERTENSION = ?, DIABETES = ?, CORAZON = ?, PULMON = ?, CANCER = ?, OBESIDAD = ?, ESTRES = ?, DEPRESION = ?, "
					+ " IMSS = ?, PASE = ?, ISSTE = ?, HLC = ?, PRIVADO = ?, NINGUNO = ?, OTRO = ? "
					+ " WHERE PERIODO_ID = TO_NUMBER(?,'99') AND CODIGO_PERSONAL = ?";			
			Object[] parametros = new Object[] {objeto.getHipertension(),objeto.getDiabetes(),objeto.getCorazon(),objeto.getPulmon(),
					objeto.getCancer(),	objeto.getObesidad(),objeto.getEstres(),objeto.getDepresion(),
					objeto.getImss(),objeto.getPase(),objeto.getIsste(),objeto.getHlc(),objeto.getPrivado(),objeto.getNinguno(),objeto.getOtro(), 
					objeto.getPeriodoId(), objeto.getCodigoPersonal()};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.CovidDao|updateReg|:"+ex);
		}		
		return ok;
	}
	
	public Covid mapeaRegId(String codigoPersonal, String periodoId) {
		Covid objeto = new Covid();		
		try{
			String comando = "SELECT "
					+ " CODIGO_PERSONAL, PERIODO_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, HIPERTENSION, DIABETES, CORAZON, PULMON, CANCER, OBESIDAD, ESTRES, DEPRESION, IMSS, PASE, ISSTE, HLC, PRIVADO, NINGUNO, OTRO"
					+ " FROM ENOC.COVID WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {codigoPersonal,periodoId};
			objeto = enocJdbc.queryForObject(comando, new CovidMapper(), parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.CovidDao|mapeaRegId|:"+ex);
		}
		
		return objeto;		
	}
	
	public boolean existe(String codigoPersonal, String periodoId) {
		boolean 		ok 		= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.COVID WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {codigoPersonal,periodoId};			
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){	
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.CovidDao|existe|:"+ex);
		}
		
		return ok;
	}
	
	public ArrayList<Covid> getLista(String tipos, String orden ){
		List<Covid> lista = new ArrayList<Covid>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, HIPERTENSION, DIABETES, CORAZON, PULMON, CANCER, OBESIDAD, ESTRES, DEPRESION, IMSS, PASE, ISSTE, HLC, PRIVADO, NINGUNO, OTRO"
				+ " FROM ENOC.COVID "
				+ " WHERE SUBSTR(CODIGO_PERSONAL,1,1) IN ("+tipos+") "+orden;
			lista = enocJdbc.query(comando, new CovidMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.CovidDao|getLista|:"+ex);
		}
		
		return (ArrayList<Covid>)lista;
	}

}
