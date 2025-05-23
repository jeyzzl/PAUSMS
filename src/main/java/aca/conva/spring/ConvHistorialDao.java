package aca.conva.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConvHistorialDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( ConvHistorial objeto ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CONV_HISTORIAL"
					+ " (CONVALIDACION_ID, FOLIO, FECHA, USUARIO, ESTADO) "
					+ " VALUES( TO_NUMBER(?,'9999999'),TO_NUMBER(?,'99'),TO_DATE(?,'DD/MM/YYYY'),?,?)";
			
			Object[] parametros = new Object[] {objeto.getConvalidacionId(),objeto.getFolio(),objeto.getFecha(),objeto.getUsuario(),objeto.getEstado()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvHistorialDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( ConvHistorial objeto ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CONV_HISTORIAL "
					+ " SET "
					+ " FECHA = TO_DATE(?,'DD/MM/YYYY'), "
					+ " USUARIO = ?, "
					+ " ESTADO = ?"
					+ " WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') "
					+ " AND FOLIO = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {objeto.getFecha(),objeto.getUsuario(),objeto.getEstado(),objeto.getConvalidacionId(),objeto.getFolio()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvHistorialDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg( String convalidacionId, String folio ){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CONV_HISTORIAL "
					+ " WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') "
					+ " AND FOLIO = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {convalidacionId,folio};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvHistorialDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean deleteAllRegs( String convalidacionId ){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CONV_HISTORIAL "
					+ " WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {convalidacionId};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvHistorialDao|deleteAllRegs|:"+ex);			
		}

		return ok;
	}
	
	
	public ConvHistorial mapeaRegId(  String convalidacionId, String estado ) {
		ConvHistorial objeto = new ConvHistorial();
		
		try{
			String comando = "SELECT CONVALIDACION_ID, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, USUARIO, ESTADO "
					+ " FROM ENOC.CONV_HISTORIAL WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') AND ESTADO = ?";
			
			Object[] parametros = new Object[] { convalidacionId,estado };
			objeto = enocJdbc.queryForObject(comando, new ConvHistorialMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvHistorialDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeFolio( String convalidacionId, String folio) {		
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CONV_HISTORIAL "
					+ " WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') "
					+ " AND FOLIO = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] { convalidacionId,folio };	
			
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){	
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvHistorialDao|existeFolio|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeEstado( String convalidacionId, String estado) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CONV_HISTORIAL "+ 
				"WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') " +
				"AND ESTADO = ?";

			Object[] parametros = new Object[] { convalidacionId,estado };	
			
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){	
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvHistorialDao|existeEstado|:"+ex);
		}
		
		return ok;
	}
	
	public String getMaxReg( String convalidacionId) {
		String 		folio 	= "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) AS FOLIO FROM ENOC.CONV_HISTORIAL "+ 
				"WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') ";
			
			Object[] parametros = new Object[] { convalidacionId };
			folio = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvHistorialDao|getMaxReg|:"+ex);
		}
		
		return folio;
	}
	
	public ArrayList<ConvHistorial> getListAll( String orden ) {
		List<ConvHistorial> lista = new ArrayList<ConvHistorial>();
		
		try{
			String comando = "SELECT CONVALIDACION_ID, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, ESTADO "
					+ " FROM ENOC.CONV_HISTORIAL "+orden; 
			
			lista = enocJdbc.query(comando, new ConvHistorialMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvHistorialDao|getListAll|:"+ex);
		}
		
		return (ArrayList<ConvHistorial>) lista;
	}
	
	public ArrayList<ConvHistorial> getList( String convalidacionId, String orden ) {
		List<ConvHistorial> lista = new ArrayList<ConvHistorial>();
		
		try{
			String comando = "SELECT CONVALIDACION_ID, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, ESTADO "
					+ " FROM ENOC.CONV_HISTORIAL WHERE CONVALIDACION_ID = ? "+orden; 
			
			Object[] parametros = new Object[] { convalidacionId };
			lista = enocJdbc.query(comando,new ConvHistorialMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvHistorialDao|getList|:"+ex);
		}
		
		return (ArrayList<ConvHistorial>) lista;
	}
	
	public ArrayList<ConvHistorial> lisPorAlumno( String codigoPersonal, String orden ) {
		List<ConvHistorial> lista = new ArrayList<ConvHistorial>();
		
		try{
			String comando = "SELECT CONVALIDACION_ID, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, ESTADO "
					+ " FROM ENOC.CONV_HISTORIAL "
					+ " WHERE CONVALIDACION_ID IN (SELECT CONVALIDACION_ID FROM ENOC.CONV_EVENTO WHERE CODIGO_PERSONAL = ? ) "+orden; 
			
			Object[] parametros = new Object[] { codigoPersonal };
			lista = enocJdbc.query(comando,new ConvHistorialMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvHistorialDao|getListAll|:"+ex);
		}
		
		return (ArrayList<ConvHistorial>) lista;
	}
}