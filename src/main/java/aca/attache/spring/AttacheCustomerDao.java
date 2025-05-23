package aca.attache.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.archivo.spring.ArchDocAlumMapper;

@Component
public class AttacheCustomerDao {

    @Autowired	
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;

    public boolean insertReg(AttacheCustomer customer){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.ATTACHE_CUSTOMER(CODE, NAME, ACCTYPE, GLSET, CAT, OPENBAL, CURRENTBAL, PERIOD1BAL, PERIOD2BAL, PERIOD3BAL, UNALLOCBAL, POSTDATEBAL, SORT, DISCPERC) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			Object[] parametros = new Object[] {customer.getCode(), customer.getName(), customer.getAcctype(), customer.getGlset(), customer.getCat(), customer.getOpenbal(), customer.getCurrentbal(), 
												customer.getPeriod1bal(), customer.getPeriod2bal(), customer.getPeriod3bal(), customer.getUnallocbal(), customer.getPostdatebal(), customer.getSort(), customer.getDiscperc()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerDao|insertReg|:"+ex);			
		}
		
		return ok;
	}

	public boolean insertBatch(List<AttacheCustomer> customers){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.ATTACHE_CUSTOMER(CODE, NAME, ACCTYPE, GLSET, CAT, OPENBAL, CURRENTBAL, PERIOD1BAL, PERIOD2BAL, PERIOD3BAL, UNALLOCBAL, POSTDATEBAL, SORT, DISCPERC) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			enocJdbc.batchUpdate(comando, customers, customers.size(),
				(ps, customer) -> {
					ps.setString(1, customer.getCode());
					ps.setString(2, customer.getName());
					ps.setString(3, customer.getAcctype());
					ps.setString(4, customer.getGlset());
					ps.setString(5, customer.getCat());
					ps.setDouble(6, customer.getOpenbal());
					ps.setDouble(7, customer.getCurrentbal());
					ps.setDouble(8, customer.getPeriod1bal());
					ps.setDouble(9, customer.getPeriod2bal());
					ps.setDouble(10, customer.getPeriod3bal());
					ps.setDouble(11, customer.getUnallocbal());
					ps.setDouble(12, customer.getPostdatebal());
					ps.setString(13, customer.getSort());
					ps.setDouble(14, customer.getDiscperc());
				}	
			);

			ok = true;
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerDao|insertBatch|:"+ex);			
		}
		
		return ok;
	}

    public boolean updateReg(AttacheCustomer customer) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ATTACHE_CUSTOMER SET NAME = ?, ACCTYPE = ?, GLSET = ?, CAT = ?, OPENBAL = ?, CURRENTBAL = ?, PERIOD1BAL = ?, PERIOD2BAL = ?, PERIOD3BAL = ?, UNALLOCBAL = ?, POSTDATEBAL = ?, SORT = ?, DISCPERC = ? WHERE CODE = ?";
			Object[] parametros = new Object[] {customer.getName(), customer.getAcctype(), customer.getGlset(), customer.getCat(), customer.getOpenbal(), customer.getCurrentbal(), customer.getPeriod1bal(), customer.getPeriod2bal(), customer.getPeriod3bal(), customer.getUnallocbal(), customer.getPostdatebal(), customer.getSort(), customer.getDiscperc(), customer.getCode()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerDao|updateReg|:"+ex);		 
		}
		
		return ok;
	}

    public boolean deleteReg(String code) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ATTACHE_CUSTOMER WHERE CODE = ?";		
			Object[] parametros = new Object[] { code };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerDao|deleteReg|:"+ex);			
		}
		return ok;
	}

	public boolean emptyTempTable() {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ATTACHE_CUSTOMER";		
			if (enocJdbc.update(comando)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerDao|emptyTempTable|:"+ex);			
		}
		return ok;
	}

	public boolean emptyTable() {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ATTACHE_CUSTOMER";		
			if (enocJdbc.update(comando)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerDao|emptyTable|:"+ex);			
		}
		return ok;
	}

	public AttacheCustomer mapeaRegId(String matricula){
		AttacheCustomer opcion = new AttacheCustomer();
		try{ 
			String comando = "SELECT COUNT(CODE) FROM ENOC.ATTACHE_CUSTOMER WHERE '1'||TRIM(CODE) IN (?) OR TRIM(CODE) IN (?)";
			Object[] parametros = new Object[] { matricula, matricula };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){				
				comando = "SELECT CODE, NAME, ACCTYPE, GLSET, CAT, OPENBAL, CURRENTBAL, PERIOD1BAL, PERIOD2BAL, PERIOD3BAL, UNALLOCBAL, POSTDATEBAL, SORT, DISCPERC FROM ENOC.ATTACHE_CUSTOMER WHERE '1'||TRIM(CODE) IN (?) OR TRIM(CODE) IN (?)";							
				opcion = enocJdbc.queryForObject(comando, new AttacheCustomerMapper(), parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerDao|mapeaRegId|:"+ex);
		}
		
		return opcion;
	}

    public boolean existeReg(String matricula){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ATTACHE_CUSTOMER WHERE '1'||TRIM(CODE) IN (?) OR TRIM(CODE) IN (?)";
			Object[] parametros = new Object[] { matricula, matricula };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){											
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerDao|existeReg|:"+ex);
		}
		
		return ok;
	}

	public boolean copiarTabla() {
		boolean ok = false;
		try{
			String comando = "INSERT INTO ATTACHE_CUSTOMER SELECT * FROM ATTACHE_CUSTOMER";
			if(enocJdbc.update(comando) >= 1){
				ok = true;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerDao|copiarTabla|:" + ex);
		}
		return ok;
	}

	public HashMap<String, AttacheCustomer> mapaAttacheCustomer() {
		HashMap<String, AttacheCustomer> mapa = new HashMap<String, AttacheCustomer>();
		List<AttacheCustomer> lista 		 = new ArrayList<AttacheCustomer>();
		String comando		= "";
		try{
			comando = "SELECT AP.CODIGO_PERSONAL AS CODE, AC.NAME, AC.ACCTYPE, AC.GLSET, AC.CAT, AC.OPENBAL, AC.CURRENTBAL, AC.PERIOD1BAL, AC.PERIOD2BAL, AC.PERIOD3BAL, AC.UNALLOCBAL, AC.POSTDATEBAL, AC.SORT, AC.DISCPERC "
					+ "FROM ENOC.ATTACHE_CUSTOMER AC "
					+ "INNER JOIN ALUM_PERSONAL AP ON '1' || TRIM(AC.CODE) = AP.CODIGO_PERSONAL OR TRIM(AC.CODE) = AP.CODIGO_PERSONAL";
			lista = enocJdbc.query(comando, new AttacheCustomerMapper());
			for(AttacheCustomer m : lista){
				mapa.put(m.getCode(), m);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerDao|mapaAttacheCustomer|:"+ex);
		}
		return mapa;
	}

	public HashMap<String,String> mapaAttacheBalance() {		
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa		= new HashMap<String,String>();		
		try{
			String comando =  "SELECT AP.CODIGO_PERSONAL AS LLAVE, SUM(AC.PERIOD1BAL) + SUM(AC.PERIOD2BAL) + SUM(AC.PERIOD3BAL) + SUM(AC.UNALLOCBAL) AS VALOR"
							+ " FROM ENOC.ATTACHE_CUSTOMER AC"
							+ " INNER JOIN ALUM_PERSONAL AP ON '1' || TRIM(AC.CODE) = AP.CODIGO_PERSONAL OR TRIM(AC.CODE) = AP.CODIGO_PERSONAL"
							+ " GROUP BY AP.CODIGO_PERSONAL";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandadoPermisoDao|mapaPermisosPorTipo|:"+ex);
		}		
		return mapa;
	}
    
}
