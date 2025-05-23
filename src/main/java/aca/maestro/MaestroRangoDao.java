package aca.maestro;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MaestroRangoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MaestroRango maestroRango ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.MAESTRO_RANGO"
				+ "(CODIGO_PERSONAL,YEAR,RANGO_ANTERIOR,RESPUESTA,RANGO_RECOMENDADO,AREA,NOMBRE) "
 				+ "VALUES( ?, TO_NUMBER(?,'9999'), ?, ?, ?, ?, ?)";
			
			Object[] parametros = new Object[] {
				maestroRango.getCodigoPersonal(),maestroRango.getYear(),maestroRango.getRangoAnterior(),maestroRango.getRespuesta(),maestroRango.getRangoRecomendado(),
				maestroRango.getArea(),maestroRango.getNombre()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.maestro.spring.MestroRangoDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(MaestroRango maestroRango ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.MAESTRO_RANGO SET YEAR = TO_NUMBER(?,'9999'),RANGO_ANTERIOR = ?,RESPUESTA = ?,RANGO_RECOMENDADO = ?,AREA = ?,NOMBRE = ?"
					+ " WHERE CODIGO_PERSONAL = ? ";
			
			Object[] parametros = new Object[] {
				maestroRango.getYear(),maestroRango.getRangoAnterior(),maestroRango.getRespuesta(),maestroRango.getRangoRecomendado(),
				maestroRango.getArea(),maestroRango.getNombre(),maestroRango.getCodigoPersonal()
			};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.maestro.spring.MestroRangoDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg(String codigoPersonal, String year) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.MAESTRO_RANGO WHERE CODIGO_PERSONAL = ? AND YEAR = ?";	
			Object[] parametros = new Object[] {codigoPersonal, year};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.maestro.spring.MestroRangoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public MaestroRango mapeaRegId(String codigoPersonal, String year) {
		MaestroRango maestroRango = new MaestroRango();		
		try{
			String comando = "SELECT CODIGO_PERSONAL,YEAR,RANGO_ANTERIOR,RESPUESTA,RANGO_RECOMENDADO,AREA,NOMBRE"
					+ " FROM ENOC.MAESTRO_RANGO "
					+ " WHERE CODIGO_PERSONAL = ? AND YEAR = ?";
				Object[] parametros = new Object[] {codigoPersonal, year};
				maestroRango = enocJdbc.queryForObject(comando, new MaestroRangoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.maestro.spring.MestroRangoDao|mapeaRegId|:"+ex);
		}
		return maestroRango;
	}
	
	public boolean existeReg( String codigoPersonal, String year) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAESTRO_RANGO WHERE CODIGO_PERSONAL = ? AND YEAR = ?";
			Object[] parametros = new Object[] {codigoPersonal, year};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.maestro.spring.MestroRangoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public List<MaestroRango> lisTodos( String orden ) {
		List<MaestroRango> lista	= new ArrayList<MaestroRango>();
		try{
			String comando = "SELECT CODIGO_PERSONAL,YEAR,RANGO_ANTERIOR,RESPUESTA,RANGO_RECOMENDADO,AREA,NOMBRE FROM ENOC.MAESTRO_RANGO "+ orden;				
			lista = enocJdbc.query(comando, new MaestroRangoMapper());				
		}catch(Exception ex){
				System.out.println("Error - aca.maestro.spring.MestroRangoDao|lisTodos|:"+ex);
		}	
		return lista;
	}
	
	public List<String> lisYears( String orden ){
		List<String> lista	= new ArrayList<String>();
		try{
			String comando = "SELECT DISTINCT(YEAR) FROM ENOC.MAESTRO_RANGO "+ orden;				
			lista = enocJdbc.queryForList(comando, String.class);				
		}catch(Exception ex){
			System.out.println("Error - aca.maestro.spring.MestroRangoDao|lisYears|:"+ex);
		}	
		return lista;
	}
	
	public List<MaestroRango> lisPorYear( String year, String orden ){
		List<MaestroRango> lista	= new ArrayList<MaestroRango>();
		try{
			String comando = "SELECT CODIGO_PERSONAL,YEAR,RANGO_ANTERIOR,RESPUESTA,RANGO_RECOMENDADO,AREA,NOMBRE"
					+ " FROM ENOC.MAESTRO_RANGO"
					+ " WHERE YEAR = ? "+ orden;				
			lista = enocJdbc.query(comando, new MaestroRangoMapper(), year);				
		}catch(Exception ex){
			System.out.println("Error - aca.maestro.spring.MestroRangoDao|lisPorYear|:"+ex);
		}	
		return lista;
	}
}