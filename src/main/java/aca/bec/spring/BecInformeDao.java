package aca.bec.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BecInformeDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( BecInforme becInforme) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.BEC_INFORME(INFORME_ID, INFORME_NOMBRE, FECHA_INI, FECHA_FIN, NIVEL, ORDEN, VERSION, ID_EJERCICIO, ESTADO)"+
				" VALUES(TO_NUMBER(?,'9999999'), ?, TO_DATE(?,'DD/MM/YYYY')," +
				" TO_DATE(?,'DD/MM/YYYY'), ?, TO_NUMBER(?,'99'),TO_NUMBER(?,'999'), ?, ?)";
			
			Object[] parametros = new Object[] {becInforme.getInformeId(),becInforme.getInformeNombre(),becInforme.getFechaIni(),becInforme.getFechaFin(),becInforme.getNivel(),becInforme.getOrden(),becInforme.getVersion(),becInforme.getIdEjercicio(),becInforme.getEstado()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeDao|insertReg|:"+ex);			
		}		
		return ok;
	}	
	
	public boolean updateReg( BecInforme becInforme) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.BEC_INFORME"+ 
				" SET INFORME_NOMBRE = ?," +
				" FECHA_INI = TO_DATE(?,'DD/MM/YYYY')," +
				" FECHA_FIN = TO_DATE(?,'DD/MM/YYYY'),"+
				" NIVEL =  ?, " +
				" ORDEN = TO_NUMBER(?,'99'), "+
				" ID_EJERCICIO = ?, " +
				" ESTADO = ? " +
				" WHERE INFORME_ID = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {becInforme.getInformeNombre(),becInforme.getFechaIni(),becInforme.getFechaFin(),becInforme.getNivel(),becInforme.getOrden(),becInforme.getIdEjercicio(),becInforme.getEstado(),becInforme.getInformeId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeDao|updateReg|:"+ex);		
		}
		return ok;
	}
		
	public boolean deleteReg( String informeId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.BEC_INFORME"+ 
				" WHERE INFORME_ID = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {informeId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public BecInforme mapeaRegId( String informeId) {
		BecInforme becInforme = new BecInforme();

		try{
			String comando = "SELECT" +
					" INFORME_ID, INFORME_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI," +
					" TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, NIVEL, ORDEN, VERSION, ID_EJERCICIO, ESTADO" +
					" FROM ENOC.BEC_INFORME " +
					" WHERE INFORME_ID = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {informeId};
			becInforme = enocJdbc.queryForObject(comando, new BecInformeMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeDao|mapeaRegId|:"+ex);
		}
		return becInforme;
	}
	
	public boolean existeReg( String informeId) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_INFORME WHERE INFORME_ID = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {informeId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String getNombreInforme( String informeId) {
		String nombre			= "X";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_INFORME WHERE INFORME_ID = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {informeId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				comando = "SELECT INFORME_NOMBRE FROM ENOC.BEC_INFORME WHERE INFORME_ID = TO_NUMBER(?,'9999999')";
				nombre = enocJdbc.queryForObject(comando,String.class,parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeDao|getNombreInforme|:"+ex);
		}
		return nombre;
	}
	
	public String maximoReg() {
		String maximo 			= "1";		
		try{
			String comando = "SELECT MAX(INFORME_ID)+1 MAXIMO FROM ENOC.BEC_INFORME";
			Object[] parametros = new Object[] {};
 			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class,parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public String getNivel( String informeId) {
		String nivel 			= "0";
		
		try{
			String comando = "SELECT NIVEL FROM ENOC.BEC_INFORME WHERE INFORME_ID = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {informeId};
			nivel = enocJdbc.queryForObject(comando,String.class,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeDao|getNivel|:"+ex);
		}		
		return nivel;		
	}

	public List<BecInforme> getListAll( String orden) {
		List<BecInforme> lista 		= new ArrayList<BecInforme>();
		String comando					= "";
		
		try{
				//comando = "SELECT * FROM ENOC.BEC_INFORME";
				comando = "SELECT INFORME_ID, INFORME_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') " +
						  "AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, NIVEL, " +
						  "ORDEN, ID_EJERCICIO, VERSION, ESTADO FROM ENOC.BEC_INFORME " + orden;
			
			
				lista = enocJdbc.query(comando, new BecInformeMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<BecInforme> getListActivos( String orden) {
		List<BecInforme> lista 		= new ArrayList<BecInforme>();
		String comando					= "";
		
		try{
			comando = "SELECT INFORME_ID, INFORME_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI," +
					" TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, NIVEL, ORDEN, ID_EJERCICIO, VERSION, ESTADO" +
					" FROM ENOC.BEC_INFORME WHERE now() BETWEEN FECHA_INI AND FECHA_FIN " + orden;
			
			lista = enocJdbc.query(comando, new BecInformeMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeDao|getListActivos|:"+ex);
		}
		return lista;
	}
	
	public List<BecInforme> getListActivos( String ejercicioId, String orden) {
		List<BecInforme> lista 		= new ArrayList<BecInforme>();
		try{
			String comando = " SELECT INFORME_ID, INFORME_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI,"
					+ " TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, NIVEL, ORDEN, ID_EJERCICIO, VERSION, ESTADO"
					+ " FROM ENOC.BEC_INFORME"
					+ " WHERE ID_EJERCICIO = ?"
					+ " AND TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN " + orden;			
			lista = enocJdbc.query(comando, new BecInformeMapper(), ejercicioId);			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeDao|getListActivos|:"+ex);
		}
		return lista;
	}
	
	public List<BecInforme> getListEjercicio( String ejercicioId, String orden) {
		List<BecInforme> lista 		= new ArrayList<BecInforme>();
		String comando					= "";
		
		try{
			comando = "SELECT INFORME_ID, INFORME_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI," +
					" TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, NIVEL, ORDEN, ID_EJERCICIO, VERSION, ESTADO" +
					" FROM ENOC.BEC_INFORME WHERE ID_EJERCICIO = ? " + orden;		
			lista = enocJdbc.query(comando, new BecInformeMapper(), ejercicioId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeDao|getListActivos|:"+ex);
		}	
		return lista;
	}
	
	public List<BecInforme> getListAnteriores( String ccostoId, String nivel, String orden) {
		List<BecInforme> lista 		= new ArrayList<BecInforme>();
		try{
			String comando = "SELECT INFORME_ID, INFORME_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI," +
					" TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, NIVEL, ORDEN, ID_EJERCICIO, VERSION, ESTADO" +
					" FROM ENOC.BEC_INFORME " +
					" WHERE INFORME_ID IN ("+
					"	SELECT DISTINCT(INFORME_ID) FROM ENOC.BEC_INFORME_ALUMNO"+
					"	WHERE CODIGO_PERSONAL||PUESTO_ID IN (" +
					"		SELECT CODIGO_PERSONAL||PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO" +
					" 		WHERE ID_CCOSTO = ? " +
					"		AND TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN" +
					"	)" +					
					" AND NIVEL = ?"+
					" ) " + orden;			
			lista = enocJdbc.query(comando, new BecInformeMapper(), ccostoId, nivel);			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeDao|getListAnteriores|:"+ex);
		}
		return lista;
	}
	
	public List<BecInforme> getListPuestoAlumno( String codigoPersonal, String puestoId, String orden) {
		List<BecInforme> lista 		= new ArrayList<BecInforme>();
		try{
			String comando = "SELECT INFORME_ID, INFORME_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI," +
					" TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, NIVEL, ORDEN, ID_EJERCICIO, VERSION, ESTADO" +
					" FROM ENOC.BEC_INFORME "+
					" WHERE INFORME_ID IN ("+
					" SELECT DISTINCT(INFORME_ID) FROM ENOC.BEC_INFORME_ALUMNO"+
					" WHERE CODIGO_PERSONAL = ? " +
					" AND PUESTO_ID = ?) " + orden;			
			lista = enocJdbc.query(comando, new BecInformeMapper(), codigoPersonal, puestoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeDao|getListPuestoAlumno|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, BecInforme> getMapInforme( String informeid) {
		HashMap<String, BecInforme> mapa = new HashMap<String, BecInforme>();
		List<BecInforme> lista	 		= new ArrayList<BecInforme>();
		try{			
			String comando = "SELECT * FROM ENOC.BEC_INFORME WHERE INFORME_ID = ?";			
			lista = enocJdbc.query(comando,new BecInformeMapper(), informeid);
			for(BecInforme informe : lista){				
				mapa.put(informe.getInformeId(), informe);
			}			
		}catch(Exception ex){
			System.out.println("Error -  aca.bec.spring.BecInformeDao|getMapInforme|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, BecInforme> mapaInformes( ) {
		HashMap<String, BecInforme> mapa = new HashMap<String, BecInforme>();
		List<BecInforme> lista	 		= new ArrayList<BecInforme>();
		try{			
			String comando = "SELECT * FROM ENOC.BEC_INFORME";	
			lista = enocJdbc.query(comando,new BecInformeMapper());
			for(BecInforme informe : lista){				
				mapa.put(informe.getInformeId(), informe);
			}			
		}catch(Exception ex){
			System.out.println("Error -  aca.bec.spring.BecInformeDao|mapaInformes|:"+ex);
		}
		return mapa;
	}
	
	public List<String> listMesesEnInforme( String ejercicioId, String fecha, String orden) {
		List<String> lista 		= new ArrayList<String>();
		try{
			String comando = " SELECT DISTINCT(ORDEN) AS MES FROM ENOC.BEC_INFORME"
					+ " WHERE INFORME_ID IN"
					+ "("
					+ "SELECT INFORME_ID FROM ENOC.BEC_INFORME_ALUMNO WHERE PUESTO_ID IN (SELECT PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN AND ID_EJERCICIO = ?)"
					+ ") " + orden;		
			lista = enocJdbc.queryForList(comando,String.class, fecha, ejercicioId);			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeDao|listMesesEnInforme|:"+ex);
		}		
		return lista;
	}
	
	public List<BecInforme> getInfomesConAlumnosContabilizados( String ejercicioId, String orden) {
		List<BecInforme> lista 		= new ArrayList<BecInforme>();	
		try{
			String comando = " SELECT INFORME_ID, INFORME_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') " +
					  " AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, NIVEL, " +
					  " ORDEN, ID_EJERCICIO, VERSION, ESTADO FROM ENOC.BEC_INFORME " +
					  " WHERE ID_EJERCICIO = ? "+
					  " AND (SELECT COUNT(*) FROM ENOC.BEC_INFORME_ALUMNO WHERE INFORME_ID = ENOC.BEC_INFORME.INFORME_ID AND ESTADO = '3') > 0 "+ orden;						
			lista = enocJdbc.query(comando, new BecInformeMapper(), ejercicioId);			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecInformeDao|getListAll|:"+ex);
		}
		return lista;
	}
	
}
