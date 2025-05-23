package aca.emp.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmpContratoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( EmpContrato empContrato) {
		boolean ok 				= false;
		try{
			String comando = "INSERT INTO ENOC.EMP_CONTRATO(CONTRATO_ID, CODIGO_PERSONAL, FECHA, COSTO, COMENTARIO, ESTADO, FECHA_INI, FECHA_FIN, INSTITUCION, FIRMA)"
					+ " VALUES(?, ?, TO_DATE(?,'DD/MM/YYYY'), ?, ?, ?, TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), ?, ?)";			
			Object[] parametros = new Object[] {empContrato.getContratoId(), empContrato.getCodigoPersonal(), empContrato.getFecha(), empContrato.getCosto(),
					empContrato.getComentario(), empContrato.getEstado(), empContrato.getFechaIni(), empContrato.getFechaFin(), empContrato.getInstitucion(), empContrato.getFirma()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpContratoDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( EmpContrato empContrato) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.EMP_CONTRATO"
					+ " SET CODIGO_PERSONAL = ?,"
					+ " FECHA = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " COSTO = ?,"
					+ " COMENTARIO = ?,"
					+ " ESTADO = ?,"
					+ " FECHA_INI = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " FECHA_FIN = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " INSTITUCION = ?,"
					+ " FIRMA = ? "
					+ " WHERE CONTRATO_ID = ?";

			Object[] parametros = new Object[] {empContrato.getCodigoPersonal(), empContrato.getFecha(), empContrato.getCosto(), empContrato.getComentario(),
					empContrato.getEstado(), empContrato.getFechaIni(), empContrato.getFechaFin(), empContrato.getInstitucion(), empContrato.getFirma(), 
					empContrato.getContratoId()};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpContratoDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg( String contratoId) {		
		boolean ok 				= false;
		try{
			String comando = "DELETE FROM ENOC.EMP_CONTRATO WHERE CONTRATO_ID = ?";	
				Object[] parametros = new Object[] {contratoId};
				if (enocJdbc.update(comando,parametros)==1){
					ok = true;
				}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpContratoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public EmpContrato mapeaRegId( String contratoId) {
		EmpContrato empContrato = new EmpContrato();		
		try{
			String comando = "SELECT CONTRATO_ID, CODIGO_PERSONAL, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, COSTO, COMENTARIO, ESTADO, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI,"
					+ " TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, INSTITUCION, FIRMA"
					+ " FROM ENOC.EMP_CONTRATO"
					+ " WHERE CONTRATO_ID = ?";			
				Object[] parametros = new Object[] {contratoId};
				empContrato = enocJdbc.queryForObject(comando, new EmpContratoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpContratoDao|mapeaRegId|:"+ex);
		}
		return empContrato;
	}
	
	public boolean existeReg( String contratoId) {
		boolean 		ok 		= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EMP_CONTRATO WHERE CONTRATO_ID = ?";		
			Object[] parametros = new Object[] {contratoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpContratoDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public List<EmpContrato> lisContratos( String year, String orden){
		List<EmpContrato> lista		= new ArrayList<EmpContrato>();
		try{
			String comando = "SELECT CONTRATO_ID, CODIGO_PERSONAL, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, COSTO, COMENTARIO, ESTADO,"
					+ " TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, INSTITUCION, FIRMA"
				+ " FROM ENOC.EMP_CONTRATO "
				+ " WHERE CONTRATO_ID LIKE '"+year+"%' "+orden;			
			lista = enocJdbc.query(comando, new EmpContratoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpContratoDao|lisContratos|:"+ex);
		}
		
		return lista;
	}
	
	public List<EmpContrato> lisContratosEmpleado( String empleadoId, String year, String orden){
		List<EmpContrato> lista		= new ArrayList<EmpContrato>();
		try{
			String comando = "SELECT CONTRATO_ID, CODIGO_PERSONAL, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, COSTO, COMENTARIO, ESTADO,"
				+ " TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, INSTITUCION, FIRMA"
				+ " FROM ENOC.EMP_CONTRATO "
				+ " WHERE CODIGO_PERSONAL = ? "
				+ " AND CONTRATO_ID LIKE '"+year+"%' "+orden;		
			Object[] parametros = new Object[] {empleadoId};
			lista = enocJdbc.query(comando, new EmpContratoMapper(), parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpContratoDao|lisContratos|:"+ex);
		}		
		return lista;
	}

	public String siguienteContrato(String year) {
		String contrato = "0001";
		try{
			String comando = " SELECT TRIM(COALESCE(MAX(SUBSTR(CONTRATO_ID,6,4)),'0000')) AS ACTUAL"
					+ " FROM ENOC.EMP_CONTRATO "
					+ " WHERE CONTRATO_ID LIKE '"+year+"%' ";			
			contrato = enocJdbc.queryForObject(comando, String.class);					
			int siguiente = Integer.parseInt(contrato)+1;
			contrato = String.valueOf(siguiente);			
			while(contrato.length() < 4) {				
				contrato = "0"+contrato;				
			}			
			contrato = year+"-"+contrato;
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpContratoDao|siguienteContrato|:"+ex);
		}
		
		return contrato;
	}	
	
	public HashMap<String, EmpContrato> mapaContratos(){
		HashMap <String, EmpContrato> mapa = new HashMap<String, EmpContrato>();
		List<EmpContrato> lista = new ArrayList<EmpContrato>();
		try{
			String comando = "SELECT CONTRATO_ID, CODIGO_PERSONAL, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, COSTO, COMENTARIO, ESTADO,"
					+ " TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, INSTITUCION, FIRMA"
					+ " FROM ENOC.EMP_CONTRATO";			
			lista = enocJdbc.query(comando, new EmpContratoMapper());
			for ( EmpContrato contrato : lista ) {
				mapa.put(contrato.getContratoId(), contrato);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpContratoDao|mapaContratos|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaNumContratos(String year){
		HashMap <String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(CONTRATO_ID) AS VALOR FROM ENOC.EMP_CONTRATO "
					+ " WHERE TO_CHAR(FECHA,'YYYY') = ? "
					+ " GROUP BY CODIGO_PERSONAL";
			Object[] parametros = new Object[] {year};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpContratoDao|mapaNumContratos|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaFirmados(String year){
		HashMap <String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(CONTRATO_ID) AS VALOR FROM ENOC.EMP_CONTRATO "
					+ " WHERE TO_CHAR(FECHA,'YYYY') = ?"
					+ " AND FIRMA = 'S'"
					+ " GROUP BY CODIGO_PERSONAL";
			Object[] parametros = new Object[] {year};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpContratoDao|mapaFirmados|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaImporteContratos(String year){
		HashMap <String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, "
					+ " SUM(COSTO) AS VALOR "
					+ " FROM ENOC.EMP_CONTRATO "
					+ " WHERE SUBSTR(CONTRATO_ID,1,4) = ? "
					+ " GROUP BY CODIGO_PERSONAL ";
			Object[] parametros = new Object[] {year};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpContratoDao|mapaImporteContratos|:"+ex);
		}		
		return mapa;
	}	
}