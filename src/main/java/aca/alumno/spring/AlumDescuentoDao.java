/**
 * 
 */
package aca.alumno.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumDescuentoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( AlumDescuento alumDescuento) {
		boolean ok = false;
		
		try{
			String  comando = "INSERT INTO ENOC.ALUM_DESCUENTO"
					+ "CODIGO_PERSONAL,CARGA_ID,DESCUENTO_ID,FECHA,MATRICULA,TIPO_MATRICULA,ENSENANZA,TIPO_ENSENANZA,INTERNADO,TIPO_INTERNADO,TOTAL,OBSERVACIONES,USUARIO, APLICADO) "
					+ "VALUES(?, ?, TO_NUMBER(?, '99'), TO_DATE(?, 'DD/MM/YYYY'), TO_NUMBER(?, '999999.99'), ?, TO_NUMBER(?, '999999.99'), ?, TO_NUMBER(?, '999999.99'), ?, TO_NUMBER(?, '999999.99'), ?, ?, ? )";
			Object[] parametros = new Object[] {alumDescuento.getCodigoPersonal(),alumDescuento.getCargaId(),alumDescuento.getDescuentoId(),alumDescuento.getFecha(),alumDescuento.getMatricula(),alumDescuento.getTipoMatricula(),alumDescuento.getEnsenanza(),alumDescuento.getTipoEnsenanza(),alumDescuento.getInternado(),alumDescuento.getTipoInternado(),alumDescuento.getTotal(),alumDescuento.getObservaciones(),alumDescuento.getUsuario(),alumDescuento.getAplicado()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDescuentoDao|insertReg|:"+ex);			
		}
		
		return ok;
	} 
	
	public boolean updateReg( AlumDescuento descuento ) {
		boolean ok = false;
		
		try{
			String  comando = "UPDATE ENOC.ALUM_DESCUENTO" 
				+ " SET FECHA = TO_DATE(?, 'DD/MM/YYYY'),"
				+ " MATRICULA = TO_NUMBER(?, '999999.99'),"
				+ " TIPO_MATRICULA = ?, "
				+ " ENSENANZA = TO_NUMBER(?, '999999.99'),"
				+ " TIPO_ENSENANZA = ?, "
				+ " INTERNADO = TO_NUMBER(?, '999999.99'),"
				+ " TIPO_INTERNADO = ?,"
				+ " TOTAL = TO_NUMBER(?, '999999.99'),"
				+ " OBSERVACIONES = ?,"
				+ " USUARIO = ?,"
				+ " APLICADO = ?"
				+ " WHERE CODIGO_PERSONAL = ?"
				+ " AND CARGA_ID = ?"								
				+ " AND DESCUENTO_ID = TO_NUMBER(?, '99')";
			
			Object[] parametros = new Object[] {
					descuento.getFecha(),descuento.getMatricula(),descuento.getTipoMatricula(), 	  
					descuento.getEnsenanza(),descuento.getTipoEnsenanza(),descuento.getInternado(),         
					descuento.getTipoInternado(),descuento.getTotal(),descuento.getObservaciones(),     
					descuento.getUsuario(),descuento.getAplicado(),descuento.getCodigoPersonal(),
					descuento.getCargaId(),descuento.getDescuentoId()	
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDescuentoDao|updateReg|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String codigoPersonal, String cargaId, String descuentoId ) {
		boolean ok = false;
		
		try{
			String  comando = "DELETE FROM ENOC.ALUM_DESCUENTO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID = ?"
					+ " AND DESCUENTO_ID = TO_NUMBER(?, '99') ";
			Object[] parametros = new Object[] {
					codigoPersonal, cargaId, descuentoId
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDescuentoDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public AlumDescuento mapeaRegId(  String codigoPersonal, String cargaId, String descuentoId) {
		AlumDescuento descuento = new AlumDescuento();
		
		try{
			String  comando = "SELECT CODIGO_PERSONAL, CARGA_ID, DESCUENTO_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA ,"
					+ " MATRICULA, TIPO_MATRICULA, ENSENANZA, TIPO_ENSENANZA, INTERNADO, TIPO_INTERNADO, TOTAL, OBSERVACIONES, USUARIO, APLICADO"
					+ " FROM ENOC.ALUM_DESCUENTO"
					+ " WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND DESCUENTO_ID = TO_NUMBER(?, '99') ";
			Object[] parametros = new Object[] {codigoPersonal, cargaId, descuentoId};
			descuento = enocJdbc.queryForObject(comando, new AlumDescuentoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDescuentoDao|mapeaRegId|:"+ex);
		}
		
		return descuento;
	}
	
	public boolean existeReg( String codigoPersonal, String cargaId, String descuentoId) {
		boolean 		ok 	= false;
		
		try{
			String  comando = "SELECT COUNT(*) FROM ENOC.ALUM_DESCUENTO "+ 
				"WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND DESCUENTO_ID = TO_NUMBER(?, '99')";
				Object[] parametros = new Object[] {codigoPersonal, cargaId, descuentoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDescuentoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg( String cargaId) {
		String maximo 			= "1";		
		try{
			String  comando = "SELECT MAX(DESCUENTO_ID)+1 MAXIMO FROM ENOC.ALUM_DESCUENTO WHERE CARGA_ID = ? ";
			if (enocJdbc.queryForObject(comando, Integer.class, cargaId) >= 1) {
				maximo = enocJdbc.queryForObject(comando, String.class, cargaId);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDescuentoDao|maximoReg|:"+ex);
		}
		
		return maximo;		
	}
	
	public String getDescuentoId( String codigoPersonal, String cargaId) {
		String tipo 			= "none";		
		try{
			String  comando = "SELECT DESCUENTO_ID FROM ENOC.ALUM_DESCUENTO WHERE CODIGO_PERSONAL= ? AND CARGA_ID = ? "; 
			tipo = enocJdbc.queryForObject(comando, String.class, codigoPersonal, cargaId);				
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDescuentoDao|getDescuentoId|:"+ex);
		}		
		return tipo;		
	}
	
	public List<AlumDescuento> getAll( String orden) {
		
		List<AlumDescuento> lista	= new ArrayList<AlumDescuento>();
				
		try{
			String comando = " SELECT CODIGO_PERSONAL,CARGA_ID,DESCUENTO_ID,TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA,"
					+ " MATRICULA,TIPO_MATRICULA,ENSENANZA,TIPO_ENSENANZA,INTERNADO,TIPO_INTERNADO,TOTAL,OBSERVACIONES,USUARIO,APLICADO"
					+ " FROM ENOC.ALUM_DESCUENTO "+ orden;
			lista = enocJdbc.query(comando, new AlumDescuentoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDescuentoDao|getAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<AlumDescuento> getAllCarga( String cargaId, String orden) {
		
		List<AlumDescuento> lista	= new ArrayList<AlumDescuento>();
		try{
			String comando = " SELECT CODIGO_PERSONAL, CARGA_ID, DESCUENTO_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, "
					+ " MATRICULA, TIPO_MATRICULA, ENSENANZA, TIPO_ENSENANZA, INTERNADO, TIPO_INTERNADO, TOTAL, "
					+ " OBSERVACIONES, USUARIO, APLICADO"
					+ " FROM ENOC.ALUM_DESCUENTO"
					+ " WHERE CARGA_ID = ? "+ orden;
			lista = enocJdbc.query(comando, new AlumDescuentoMapper(), cargaId);
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDescuentoDao|getAllCarga|:"+ex);
		}
		
		return lista;
	}
	
	public List<AlumDescuento> lisPorFechas( String fechaIni, String fechaFin, String orden) {
		
		List<AlumDescuento> lista	= new ArrayList<AlumDescuento>();		
		try{
			String comando = " SELECT CODIGO_PERSONAL, CARGA_ID, DESCUENTO_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA,"
					+ " MATRICULA, TIPO_MATRICULA, ENSENANZA, TIPO_ENSENANZA, INTERNADO, TIPO_INTERNADO, TOTAL,"
					+ " OBSERVACIONES, USUARIO, APLICADO"
					+ " FROM ENOC.ALUM_DESCUENTO"
					+ " WHERE FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') "+ orden;
			lista = enocJdbc.query(comando, new AlumDescuentoMapper(), fechaIni, fechaFin);
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDescuentoDao|lisPorFechas|:"+ex);
		}
		
		return lista;
	}
	
	// Map que obtiene los importes cobrados en los conceptos del calculo de cobro
	public HashMap<String, String> mapImporteConcepto( String tipoMov) {
		
		HashMap<String, String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
				
		try{
			String comando = "SELECT MATRICULA||CARGA_ID||TIPOMOV AS LLAVE, IMPORTE AS VALOR FROM MATEO.FES_CC_MOVIMIENTO"+
					" WHERE TIPOMOV  IN ("+tipoMov+") "+
					" AND MATRICULA||CARGA_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID FROM ENOC.ALUM_DESCUENTO)";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDescuentoDao|mapImporteConcepto|:"+ex);
		}
		
		return mapa;
	}
	
	// Map que obtiene los importes cobrados en los conceptos del calculo de cobro
	public HashMap<String,String> mapaDescuentos() {
		
		HashMap<String, String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();				
		try{
			String comando = "SELECT DESCUENTO_ID AS LLAVE, COUNT(DESCUENTO_ID) AS VALOR FROM ENOC.ALUM_DESCUENTO GROUP BY DESCUENTO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumDescuentoDao|mapaDescuentos|:"+ex);
		}
		
		return mapa;
	}
	
}