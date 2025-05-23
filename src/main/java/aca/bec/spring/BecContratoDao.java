package aca.bec.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BecContratoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	
	public boolean insertReg( BecContrato contrato) {
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.BEC_CONTRATO(CODIGO_PERSONAL, PUESTO_ID, ARCHIVO, FECHA, NOMBRE)"+
				" VALUES( ?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'), ? )";
			Object[] parametros = new Object[] {contrato.getCodigoPersonal(), contrato.getPuestoId(), contrato.getArchivo(), contrato.getFecha(), contrato.getNombre()};			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecContratoDao|insertReg|:"+ex);			
		}		
		return ok;
	}
	
	public boolean updateReg( BecContrato contrato) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.BEC_CONTRATO"
				+ " SET FECHA = TO_DATE(?, 'DD/MM/YYYY'),"
				+ " NOMBRE = ?,"
				+ " ARCHIVO = ?"
				+ " WHERE CODIGO_PERSONAL = ? AND PUESTO_ID = ?";
			Object[] parametros = new Object[] { contrato.getFecha(), contrato.getNombre(), contrato.getArchivo(), contrato.getCodigoPersonal(), contrato.getPuestoId() };			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecContratoDao|updateReg|:"+ex);		
		}		
		return ok;
	}
	
	public boolean updateAutorizado(String codigoPersonal, String puestoId) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.BEC_CONTRATO SET AUTORIZADO = SYSDATE WHERE CODIGO_PERSONAL = ? AND PUESTO_ID = ?";
			Object[] parametros = new Object[] { codigoPersonal, puestoId};			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecContratoDao|updateAutorizado|:"+ex);		
		}		
		return ok;
	}
	
	
	public boolean deleteReg( String codigoPersonal, String puestoId ) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.BEC_CONTRATO WHERE CODIGO_PERSONAL = ? AND PUESTO_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, puestoId};			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecContratoDao|deleteReg|:"+ex);			
		}		
		return ok;
	}
	
	public boolean existeReg( String codigoPersonal, String puestoId) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_CONTRATO WHERE CODIGO_PERSONAL = ? AND PUESTO_ID = ? ";
			Object[] parametros = new Object[] {codigoPersonal, puestoId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecContratoDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean existeAutorizacion( String codigoPersonal, String puestoId) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_CONTRATO WHERE CODIGO_PERSONAL = ? AND PUESTO_ID = ? AND AUTORIZADO IS NOT NULL";
			Object[] parametros = new Object[] {codigoPersonal, puestoId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecContratoDao|existeAutorizacion|:"+ex);
		}		
		return ok;
	}
	
	public BecContrato mapeaRegId( String codigoPersonal, String puestoId ) {		
		BecContrato contrato = new BecContrato();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PUESTO_ID, ARCHIVO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, NOMBRE, AUTORIZADO " +
					" FROM ENOC.BEC_CONTRATO WHERE CODIGO_PERSONAL = ? AND PUESTO_ID = ?"; 
			Object[] parametros = new Object[] {codigoPersonal, puestoId};
			contrato = enocJdbc.queryForObject(comando, new BecContratoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecContratoDao|mapeaRegId|:"+ex);
		}
		
		return contrato;
	}
	
	public List<BecContrato> lisTodos( String orden) {
		
		List<BecContrato> lista = new ArrayList<BecContrato>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PUESTO_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, NOMBRE, AUTORIZADO FROM ENOC.BEC_CONTRATO "+orden;	
			lista = enocJdbc.query(comando, new BecContratoMapperCorto());
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecContratoDao|lisTodos|:"+ex);
		}
		
		return lista;
	}
	
	public List<BecContrato> lisPorAlumno( String codigo, String orden) {
		
		List<BecContrato> lista = new ArrayList<BecContrato>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PUESTO_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, NOMBRE, AUTORIZADO FROM ENOC.BEC_CONTRATO"
					+ " WHERE CODIGO_PERSONAL = ? "+orden;
			lista = enocJdbc.query(comando, new BecContratoMapperCorto());
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecContratoDao|lisPorAlumno|:"+ex);
		}
		
		return lista;
	}		

	public HashMap<String,BecContrato> mapaBecContrato( String ejercicioId ) {
		HashMap<String,BecContrato> mapa = new HashMap<String,BecContrato>();
		List<BecContrato> lista = new ArrayList<BecContrato>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PUESTO_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, NOMBRE, AUTORIZADO FROM ENOC.BEC_CONTRATO"
					+ " WHERE PUESTO_ID IN (SELECT PUESTO_ID FROM BEC_PUESTO WHERE ID_EJERCICIO = ?) AND ARCHIVO IS NOT NULL";
			lista = enocJdbc.query(comando, new BecContratoMapperCorto(), ejercicioId);
			for(BecContrato contrato : lista) {
				mapa.put(contrato.getCodigoPersonal()+contrato.getPuestoId(), contrato);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecContratoDao|mapaBecContrato|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,BecContrato> mapaContratosAlumno( String codigoAlumno ) {
		HashMap<String,BecContrato> mapa = new HashMap<String,BecContrato>();
		List<BecContrato> lista = new ArrayList<BecContrato>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PUESTO_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, NOMBRE, AUTORIZADO FROM ENOC.BEC_CONTRATO"
					+ " WHERE CODIGO_PERSONAL = ? AND ARCHIVO IS NOT NULL";
			lista = enocJdbc.query(comando, new BecContratoMapperCorto(), codigoAlumno);
			for(BecContrato contrato : lista) {
				mapa.put(contrato.getPuestoId(), contrato);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecContratoDao|mapaBecContrato|:"+ex);
		}
		
		return mapa;
	}
}
