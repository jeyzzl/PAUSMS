package aca.alumno.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.alerta.spring.AlertaDocAlum;
import aca.alerta.spring.AlertaDocAlumMapperCorto;

@Component
public class AlumCovidDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AlumCovid objeto){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.ALUM_COVID(CODIGO_PERSONAL, PERIODO_ID, TIPO, FECHA_LLEGADA, VACUNA, FECHA_VACUNA, POSITIVO_COVID, FECHA_POSITIVO, SOSPECHOSO, FECHA_SOSPECHOSO, "
					+ " AISLAMIENTO, FIN_AISLAMIENTO, USUARIO_ALTA, FECHA_ALTA, COMENTARIO, VALIDADO)"
					+ " VALUES(?, ?, ?, TO_DATE(?,'DD/MM/YYYY'), ?, TO_DATE(?,'DD/MM/YYYY'), ?, TO_DATE(?,'DD/MM/YYYY'), ?, TO_DATE(?,'DD/MM/YYYY'),"
					+ " ?, TO_DATE(?,'DD/MM/YYYY'), ?, TO_DATE(?,'DD/MM/YYYY'), ?, ?)";			
			Object[] parametros = new Object[]{ 
				objeto.getCodigoPersonal(), objeto.getPeriodoId(), objeto.getTipo(), objeto.getFechaLlegada(),objeto.getVacuna(), objeto.getFechaVacuna(), objeto.getPositivoCovid(), objeto.getFechaPositivo(), objeto.getSospechoso(), objeto.getFechaSospechoso(), 
				objeto.getAislamiento(), objeto.getFinAislamiento(), objeto.getUsuarioAlta(), objeto.getFechaAlta(), objeto.getComentario(), objeto.getValidado()  
			};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumCovidDao|insertReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean updateReg(AlumCovid objeto){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ALUM_COVID"
					+ " SET FECHA_LLEGADA = TO_DATE(?,'DD/MM/YYYY'),"
					+ " VACUNA = ?,"
					+ " FECHA_VACUNA = TO_DATE(?,'DD/MM/YYYY'),"
					+ " POSITIVO_COVID = ?,"
					+ " FECHA_POSITIVO = TO_DATE(?,'DD/MM/YYYY'),"
					+ " SOSPECHOSO = ?,"
					+ " FECHA_SOSPECHOSO = TO_DATE(?,'DD/MM/YYYY'),"
					+ " AISLAMIENTO = ?, "
					+ " FIN_AISLAMIENTO = ?, "
					+ " USUARIO_ALTA = ?, "
					+ " FECHA_ALTA = TO_DATE(?,'DD/MM/YYYY'),"
					+ " COMENTARIO = ?,"
					+ " TIPO = ?,"
					+ " VALIDADO = ?"				
					+ " WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ?";
			Object[] parametros = new Object[] {
				objeto.getFechaLlegada(),objeto.getVacuna(), objeto.getFechaVacuna(), objeto.getPositivoCovid(), objeto.getFechaPositivo(), objeto.getSospechoso(), objeto.getFechaSospechoso(), 
				objeto.getAislamiento() ,objeto.getFinAislamiento(), objeto.getUsuarioAlta(), objeto.getFechaAlta(), objeto.getComentario(), objeto.getTipo(), objeto.getValidado(),
				objeto.getCodigoPersonal(), objeto.getPeriodoId()
			};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumCovidDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg(String codigoPersonal, String periodoId){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ALUM_COVID WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal,periodoId};
 			if (enocJdbc.update(comando, parametros)==1){
 				ok = true;
 			}		
		}catch(Exception ex){
			System.out.println("Error  - aca.alumno.spring.AlumCovidDao|deleteReg|:"+ex);		
		}
		return ok;
	}
	
	public AlumCovid mapeaRegId(String codigoPersonal, String periodoId){		
		AlumCovid color = new AlumCovid();
		try{
			String comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, TIPO, TO_CHAR(FECHA_LLEGADA, 'DD/MM/YYYY') AS FECHA_LLEGADA, VACUNA, TO_CHAR(FECHA_VACUNA, 'DD/MM/YYYY') AS FECHA_VACUNA,"
					+ " POSITIVO_COVID, TO_CHAR(FECHA_POSITIVO, 'DD/MM/YYYY') AS FECHA_POSITIVO,"
					+ " SOSPECHOSO, TO_CHAR(FECHA_SOSPECHOSO, 'DD/MM/YYYY') AS FECHA_SOSPECHOSO,"
					+ " AISLAMIENTO, TO_CHAR(FIN_AISLAMIENTO, 'DD/MM/YYYY') AS FIN_AISLAMIENTO,"
					+ " USUARIO_ALTA, TO_CHAR(FECHA_ALTA, 'DD/MM/YYYY') AS FECHA_ALTA,"
					+ "	COMENTARIO, VALIDADO "
					+ " FROM ENOC.ALUM_COVID "
					+ " WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal,periodoId};
			color = enocJdbc.queryForObject(comando,  new AlumCovidMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumCovidDao|mapeaRegId|:"+ex);		
		}		
		return color;
	}

	public boolean existeReg(String codigoPersonal, String periodoId){
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_COVID WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal,periodoId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumCovidDao|existeReg|:"+ex);		
		}		
		return ok;
	}		
	
	public List<AlumCovid> getListAll(String orden ){		
		List<AlumCovid> lista= new ArrayList<AlumCovid>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, TIPO, TO_CHAR(FECHA_LLEGADA, 'DD/MM/YYYY') AS FECHA_LLEGADA, VACUNA, TO_CHAR(FECHA_VACUNA, 'DD/MM/YYYY') AS FECHA_VACUNA,"
				+ " POSITIVO_COVID, TO_CHAR(FECHA_POSITIVO, 'DD/MM/YYYY') AS FECHA_POSITIVO,"
				+ " SOSPECHOSO, TO_CHAR(FECHA_SOSPECHOSO, 'DD/MM/YYYY') AS FECHA_SOSPECHOSO,"
				+ " AISLAMIENTO, TO_CHAR(FIN_AISLAMIENTO, 'DD/MM/YYYY') AS FIN_AISLAMIENTO,"
				+ " USUARIO_ALTA, TO_CHAR(FECHA_ALTA, 'DD/MM/YYYY') AS FECHA_ALTA,"
				+ "	COMENTARIO, VALIDADO"
				+ " FROM ENOC.ALUM_COVID "+orden; 
			lista = enocJdbc.query(comando, new AlumCovidMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumCovidDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<AlumCovid> lisPorPeriodo(String periodoId, String orden ){
		
		List<AlumCovid> lista= new ArrayList<AlumCovid>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, TIPO, TO_CHAR(FECHA_LLEGADA, 'DD/MM/YYYY') AS FECHA_LLEGADA, VACUNA, TO_CHAR(FECHA_VACUNA, 'DD/MM/YYYY') AS FECHA_VACUNA,"
				+ " POSITIVO_COVID, TO_CHAR(FECHA_POSITIVO, 'DD/MM/YYYY') AS FECHA_POSITIVO,"
				+ " SOSPECHOSO, TO_CHAR(FECHA_SOSPECHOSO, 'DD/MM/YYYY') AS FECHA_SOSPECHOSO,"
				+ " AISLAMIENTO, TO_CHAR(FIN_AISLAMIENTO, 'DD/MM/YYYY') AS FIN_AISLAMIENTO,"
				+ " USUARIO_ALTA, TO_CHAR(FECHA_ALTA, 'DD/MM/YYYY') AS FECHA_ALTA,"
				+ "	COMENTARIO, VALIDADO"
				+ " FROM ENOC.ALUM_COVID"
				+ " WHERE PERIODO_ID = ? "+orden; 
			lista = enocJdbc.query(comando, new AlumCovidMapper(), periodoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumCovidDao|lisPorPeriodo|:"+ex);	
		}		
		return lista;
	}	
	
	public HashMap<String,String> mapaPorPeriodo(String periodoId){
		HashMap<String,String> mapa		= new HashMap<String, String>();
		List<aca.Mapa>	lista		= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, USUARIO_ALTA AS VALOR FROM ENOC.ALUM_COVID WHERE PERIODO_ID = ?";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodoId);
			for (aca.Mapa objeto : lista){
				mapa.put(objeto.getLlave(), objeto.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumCovidDao|mapaPorPeriodo|:"+ex);
		}		
		return mapa;
	}
	
}