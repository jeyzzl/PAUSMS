package aca.bec.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BecAccesoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( BecAcceso becAcceso) {
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.BEC_ACCESO"+ 
				"(CODIGO_PERSONAL, ID_EJERCICIO, ID_CCOSTO," +
				" FECHA, USUARIO)"+
				" VALUES( ?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'), ? )";
			Object[] parametros = new Object[] {becAcceso.getCodigoPersonal(), becAcceso.getIdEjercicio(), becAcceso.getIdCcosto(), becAcceso.getFecha(), becAcceso.getUsuario()};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAccesoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean updateReg( BecAcceso becAcceso) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.BEC_ACCESO"+ 
				" SET FECHA = TO_DATE(?, 'DD/MM/YYYY')," +
				" USUARIO = ? "+				
				" WHERE CODIGO_PERSONAL = ? AND ID_EJERCICIO = ? AND ID_CCOSTO = ? ";
			Object[] parametros = new Object[] {becAcceso.getFecha(), becAcceso.getUsuario(), becAcceso.getCodigoPersonal(), becAcceso.getIdEjercicio(), becAcceso.getIdCcosto()};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAccesoDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	
	public boolean deleteReg( String codigoPersonal, String idEjercicio, String idCcosto) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.BEC_ACCESO"
					+ " WHERE CODIGO_PERSONAL = ? AND ID_EJERCICIO = ? AND ID_CCOSTO = ? ";
			Object[] parametros = new Object[] {codigoPersonal, idEjercicio, idCcosto};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAccesoDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean existeReg( String codigoPersonal, String idEjercicio, String idCcosto) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_ACCESO WHERE CODIGO_PERSONAL = ? AND ID_EJERCICIO = ? AND ID_CCOSTO = ? ";
			Object[] parametros = new Object[] {codigoPersonal, idEjercicio, idCcosto};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAccesoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public BecAcceso mapeaRegId( String idEjercicio, String idCcosto, String codigoPersonal ) {
		
		BecAcceso becAcceso = new BecAcceso();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, ID_EJERCICIO, ID_CCOSTO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO " +
					" FROM ENOC.BEC_ACCESO WHERE CODIGO_PERSONAL = ? AND ID_EJERCICIO = ? AND ID_CCOSTO = ? "; 
			Object[] parametros = new Object[] {codigoPersonal, idEjercicio, idCcosto};
			becAcceso = enocJdbc.queryForObject(comando, new BecAccesoMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAccesoDao|mapeaRegId|:"+ex);
		}
		
		return becAcceso;
	}
	
	public List<BecAcceso> lisUsuarioAccesos( String idEjercicio, String codigoPersonal) {
		
		List<BecAcceso> lista 			= new ArrayList<BecAcceso>();	
		try{
			String comando = "SELECT CODIGO_PERSONAL, ID_EJERCICIO, ID_CCOSTO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO"
					+ " FROM ENOC.BEC_ACCESO"
					+ " WHERE ID_EJERCICIO = ? AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {idEjercicio, codigoPersonal};
			lista = enocJdbc.query(comando, new BecAccesoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAccesoDao|lisUsuariosAccesos|:"+ex);
		}
		
		return lista;
	}
	
	public String getUsuarioCentrosCosto( String idEjercicio, String codigoPersonal) {
		
		List<String> lista 			= new ArrayList<String>();
		StringBuilder centrosCosto	= new StringBuilder();
		
		try{
			String comando = "SELECT ID_CCOSTO FROM ENOC.BEC_ACCESO WHERE CODIGO_PERSONAL = ? AND ID_EJERCICIO = ?";
			Object[] parametros = new Object[] {codigoPersonal, idEjercicio};
			lista = enocJdbc.queryForList(comando, String.class,parametros);
			for (String carrera : lista){
				centrosCosto.append("-"+carrera);
			}
			centrosCosto.append("-");
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAccesoDao|getUsuarioCentrosCosto|:"+ex);
		}
		
		return centrosCosto.toString();
	}
	
	public boolean esUsuarioBecas( String idEjercicio, String codigoPersonal) {
		
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_ACCESO WHERE ID_EJERCICIO = ? AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {idEjercicio, codigoPersonal};			
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAccesoDao|esUsuarioBecas|:"+ex);
		}
		
		return ok;
	}
	
	public ArrayList<BecAcceso> getListAll( String orden) {
		
		List<BecAcceso> lista = new ArrayList<BecAcceso>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, ID_EJERCICIO, ID_CCOSTO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO FROM ENOC.BEC_ACCESO "+orden;	
			lista = enocJdbc.query(comando, new BecAccesoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAccesoDao|getListAll|:"+ex);
		}
		
		return (ArrayList<BecAcceso>)lista;
	}
	
	public ArrayList<BecAcceso> getListUsuario( String codigo, String orden) {
		
		List<BecAcceso> lista = new ArrayList<BecAcceso>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, ID_EJERCICIO, ID_CCOSTO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO FROM ENOC.BEC_ACCESO WHERE CODIGO_PERSONAL = ? "+orden;
			lista = enocJdbc.query(comando, new BecAccesoMapper(), codigo);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAccesoDao|getListUsuario|:"+ex);
		}
		
		return (ArrayList<BecAcceso>)lista;
	}
	
	public List<String> lisPorUsuario( String codigo ) {
		
		List<String> lista = new ArrayList<String>();		
		try{
			String comando = "SELECT ID_CCOSTO FROM ENOC.BEC_ACCESO WHERE CODIGO_PERSONAL = ? ORDER BY ID_CCOSTO";
			lista = enocJdbc.queryForList(comando, String.class, codigo);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAccesoDao|lisPorUsuario|:"+ex);
		}
		
		return lista;
	}
		
	public ArrayList<BecAcceso> getListDepartamento( String ejercicioId, String ccosto, String orden) {
		
		List<BecAcceso> lista = new ArrayList<BecAcceso>();			
		try{
			String comando = "SELECT CODIGO_PERSONAL, ID_EJERCICIO, ID_CCOSTO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO"
					+ " FROM ENOC.BEC_ACCESO"
					+ " WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ? " +orden;
			lista = enocJdbc.query(comando, new BecAccesoMapper(), ejercicioId, ccosto);				
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecAccesoDao|getListUsuario|:"+ex);
		}
		
		return (ArrayList<BecAcceso>)lista;
	}
		
	public HashMap<String, String> getAccesoDepto( String ejercicioId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<BecAcceso> lista 	= new ArrayList<BecAcceso>();
		StringBuffer usuarios	= new StringBuffer();
		String depto 			= "x";
		int row 				= 0;
		try{
			String comando = "SELECT CODIGO_PERSONAL, ID_EJERCICIO, ID_CCOSTO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO FROM ENOC.BEC_ACCESO"
					+ " WHERE ID_EJERCICIO = ? ORDER BY ID_CCOSTO";
			Object[] parametros = new Object[] {ejercicioId};
			lista = enocJdbc.query(comando, new BecAccesoMapper(),parametros);
			for( BecAcceso acceso : lista){
				if ( !depto.equals(acceso.getIdCcosto()) && !depto.equals("x")){
					// grabar elemento en el mapa
					mapa.put(depto, usuarios.toString());						
					row = 0;
					usuarios.delete(0,usuarios.length());					
				}
				if (row>0) usuarios.append(",");
				usuarios.append(acceso.getCodigoPersonal());
				depto = acceso.getIdCcosto();					
				row++;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.BecAccesoDao|getAccesoDepto|:"+ex);
		}
		
		return mapa;
	}	
		
}
