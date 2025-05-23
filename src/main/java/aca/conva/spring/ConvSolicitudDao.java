package aca.conva.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConvSolicitudDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( ConvSolicitud objeto ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO CONV_objetoICITUD "
					+ " (CODIGO_PERSONAL, CURSO_ID, USUARIO, FECHA, TIPO, PROCESO_ID, NOTA, MATERIA_O, CREDITOS_O, NOTA_O) "
					+ " VALUES( ?, ?, ?, TO_DATE(?,'DD/MM/YYYY'), ?, ?, ?, ?, TO_NUMBER(?,'99'), ? ";
			
			Object[] parametros = new Object[] {objeto.getCodigoPersonal(),objeto.getCursoId(),objeto.getUsuario(),objeto.getFecha(),objeto.getTipo(),Integer.parseInt(objeto.getProcesoId()),
					objeto.getNota(),objeto.getMateria_O(),objeto.getCreditos_O(),objeto.getNota_O()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvSolicitudDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( ConvSolicitud objeto ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CONV_SOLICITUD "
					+ " SET "
					+ " USUARIO = ?, "
					+ " FECHA = TO_DATE(?,'DD/MM/YYYY'), "
					+ " TIPO = ?, "
					+ " PROCESO_ID = ?, "
					+ " NOTA = ? "
					+ " MATERIA_O = ? "
					+ " CREDITOS_O = TO_NUMBER(?, '99') "
					+ " NOTA_O = ? "
					+ " WHERE CODIGO_PERSONAL = ? AND CURSO_ID = ? ";			
			Object[] parametros = new Object[] {objeto.getUsuario(),objeto.getFecha(),objeto.getTipo(),Integer.parseInt(objeto.getProcesoId()),objeto.getNota(),objeto.getMateria_O(),
					objeto.getCreditos_O(),objeto.getNota_O(),objeto.getCodigoPersonal(),objeto.getCursoId()};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvSolicitudDao|updateReg|:"+ex);		
		}

		return ok;
	}
	
	public boolean deleteReg( String codigoPersonal, String cursoId ) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.CONV_SOLICITUD WHERE CODIGO_PERSONAL = ?  AND CURSO_ID = ?";			
			Object[] parametros = new Object[] {codigoPersonal,cursoId};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvSolicitudDao|deleteReg|:"+ex);			
		}

		return ok;
	}
	
	public ConvSolicitud mapeaRegId(  String codigoPersonal, String cursoId ) {
		ConvSolicitud objeto = new ConvSolicitud();		
		try{
			String comando = "SELECT "
					+ " CODIGO_PERSONAL, CURSO_ID, USUARIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TIPO, PROCESO_ID, CARRERA, NOTA, MATERIA_O, CREDITOS_O, NOTA_O "
					+ " FROM ENOC.CONV_SOLICITUD WHERE CODIGO_PERSONAL = ? AND CURSO_ID = ? ";			
			Object[] parametros = new Object[] {codigoPersonal,cursoId};
			objeto = enocJdbc.queryForObject(comando, new ConvSolicitudMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvSolicitudDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg( String codigoPersonal, String cursoId ) {
		boolean 		ok 	= false;
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.CONV_SOLICITUD WHERE CODIGO_PERSONAL = ? AND CUROS_ID = ?";			
			Object[] parametros = new Object[] {codigoPersonal,cursoId};
			if(enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvSolicitudDao|existeReg|:"+ex);
		}		
		return ok;
	}

	public ArrayList<ConvSolicitud> getListAll( String orden ) {
		List<ConvSolicitud> lista		= new ArrayList<ConvSolicitud>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_ID, USUARIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TIPO, PROCESO_ID, NOTA, MATERIA_O, CREDITOS_O, NOTA_O "
					+ " FROM ENOC.CONV_SOLICITUD "+orden;			
			lista = enocJdbc.query(comando, new ConvSolicitudMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvSolicitudDao|getListAll|:"+ex);
		}
		
		return (ArrayList<ConvSolicitud>)lista;
	}
	
	public ArrayList<ConvSolicitud> getList( String codigoPersonal, String orden ) {
		List<ConvSolicitud> lista		= new ArrayList<ConvSolicitud>();

		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_ID, USUARIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TIPO, PROCESO_ID, NOTA, MATERIA_O, CREDITOS_O, NOTA_O "
					+ " FROM ENOC.CONV_SOLICITUD WHERE CODIGO_PERSONAL = ? "+orden;			
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando,new ConvSolicitudMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvSolicitudDao|getList|:"+ex);
		}
		
		return (ArrayList<ConvSolicitud>)lista;
	}
	
	public ArrayList<ConvSolicitud> getListDistinct( String orden) {
		List<ConvSolicitud> lista		= new ArrayList<ConvSolicitud>();
		
		try{
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) CODIGO_PERSONAL, CURSO_ID, USUARIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TIPO, PROCESO_ID, NOTA, MATERIA_O, CREDITOS_O, NOTA_O "
					+ " FROM ENOC.CONV_SOLICITUD "+orden;			
			lista = enocJdbc.query(comando,new ConvSolicitudMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvSolicitudDao|getListDisctinct|:"+ex);
		}
		
		return (ArrayList<ConvSolicitud>)lista;
	}	
}