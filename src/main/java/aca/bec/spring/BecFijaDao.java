package aca.bec.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BecFijaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;

	public boolean insertReg( BecFija becFija) {
		boolean ok = false;	
		try{
			String comando = "INSERT INTO ENOC.BEC_FIJA(ID_EJERCICIO, ID_CCOSTO, FECHA, USUARIO) VALUES( ?, ?, TO_DATE(?, 'DD/MM/YYYY'), ?)";					
			Object[] parametros = new Object[] {becFija.getIdEjercicio(),becFija.getIdCcosto(),becFija.getFecha(),becFija.getUsuario()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecFijaDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg( BecFija becFija) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.BEC_FIJA SET FECHA = TO_DATE(?,'DD/MM/YYYY'), USUARIO = ? WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ?";			
			Object[] parametros = new Object[] {becFija.getFecha(),becFija.getUsuario(),becFija.getIdEjercicio(),becFija.getIdCcosto()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecFijaDao|updateReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg( String idEjercicio, String idCcosto) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.BEC_FIJA"+ 
				" WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ?";
			
			Object[] parametros = new Object[] {idEjercicio,idCcosto};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecFijaDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean existeReg( String idEjercicio, String idCcosto) {
		boolean ok 			= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_FIJA WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ?"; 
				
			Object[] parametros = new Object[] {idEjercicio,idCcosto};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecFijaDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean existeRegCcosto( String idEjercicio, String ccosto) {
		boolean ok 			= false;
			
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_FIJA WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ?";			
			Object[] parametros = new Object[] {idEjercicio, ccosto};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecFijaDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public BecFija mapeaRegId( String idEjercicio, String idCcosto) {
		BecFija becFija = new BecFija();
		
		try{
			String comando = "SELECT  * " +
					" FROM ENOC.BEC_FIJA WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ? "; 

			Object[] parametros = new Object[] {idEjercicio,idCcosto};
			becFija = enocJdbc.queryForObject(comando, new BecFijaMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecFijaDao|mapeaRegId|:"+ex);
		}
		return becFija;
	}
	
	public List<BecFija> getListAll( String orden) {
		List<BecFija> lista 		= new ArrayList<BecFija>();
		String comando					= "";
		
		try{
			comando = "SELECT ID_EJERCICIO, ID_CCOSTO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO FROM ENOC.BEC_FIJA "+orden;
			
			lista = enocJdbc.query(comando, new BecFijaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecFijaDao|getListAll|:"+ex);
		}
		return lista;
	}
	
public HashMap <String,BecFija> getMapAll( String orden ) {
		List<BecFija> lista	= new ArrayList<BecFija>();
		HashMap<String,BecFija> map = new HashMap<String,BecFija>();
		
		try{
			String comando = "SELECT * FROM ENOC.BEC_FIJA "+ orden;

			lista = enocJdbc.query(comando,new BecFijaMapper());
			for(BecFija obj : lista){				
				map.put(obj.getIdCcosto() + obj.getIdEjercicio(), obj);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecaFijaUtil|getMapAll|:"+ex);
		}
		return map;
	}
	
	public List<BecFija> getListEjercicio( String ejercicioId, String orden) {
		List<BecFija> lista 			= new ArrayList<BecFija>();
		try{
			String comando = "SELECT ID_EJERCICIO, ID_CCOSTO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO FROM ENOC.BEC_FIJA WHERE ID_EJERCICIO = ? "+orden;			
			lista = enocJdbc.query(comando, new BecFijaMapper(), ejercicioId);			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecFijaDao|getListEjercicio|:"+ex);
		}
		return lista;
	}
	
	public List<String> getListCentros( String ejercicioId, String orden) {
		List<String> lista 			= new ArrayList<String>();
		try{
			String comando = "SELECT ID_CCOSTO FROM ENOC.BEC_FIJA WHERE ID_EJERCICIO = ? "+orden;			
			lista = enocJdbc.queryForList(comando, String.class, ejercicioId);				
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecFijaDao|getListCentros|:"+ex);
		}
		return lista;
	}

}
