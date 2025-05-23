package aca.attache.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AttacheInvoiceDetailProductDao {

    @Autowired	
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;

    public boolean insertReg(AttacheInvoiceDetailProduct details){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.ATTACHE_INVOICE_DETAIL_PRODUCT_TEMP(DOCTYPE, INTERNALDOCNUM, STATUS, PRICEAMT, UNITCOST, CODE, DESCRIPTION, CAT, GLSET) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			Object[] parametros = new Object[] {details.getDoctype(), details.getInternaldocnum(), details.getStatus(), details.getPriceamt(), details.getUnitcost(), details.getCode(), details.getDescription(), details.getCat(), details.getGlset()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheInvoiceDetailProductDao|insertReg|:"+ex);			
		}
		
		return ok;
	}

	public boolean insertBatch(List<AttacheInvoiceDetailProduct> customers){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.ATTACHE_INVOICE_DETAIL_PRODUCT_TEMP(DOCTYPE, INTERNALDOCNUM, STATUS, PRICEAMT, UNITCOST, CODE, DESCRIPTION, CAT, GLSET) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

			enocJdbc.batchUpdate(comando, customers, customers.size(),
				(ps, customer) -> {
					ps.setInt(1, customer.getDoctype());
					ps.setInt(2, customer.getInternaldocnum());
					ps.setInt(3, customer.getStatus());
					ps.setDouble(4, customer.getPriceamt());
					ps.setDouble(5, customer.getUnitcost());
					ps.setString(6, customer.getCode());
					ps.setString(7, customer.getDescription());
					ps.setString(8, customer.getCat());
					ps.setString(9, customer.getGlset());
				}	
			);

			ok = true;
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheInvoiceDetailProductDao|insertBatch|:"+ex);			
		}
		
		return ok;
	}

    public boolean updateReg(AttacheInvoiceDetailProduct details) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ATTACHE_INVOICE_DETAIL_PRODUCT_TEMP SET STATUS = ?, PRICEAMT = ?, UNITCOST = ?, CODE = ?, DESCRIPTION = ?, CAT = ?, GLSET = ? WHERE DOCTYPE = ? AND INTERNALDOCNUM = ?";
			Object[] parametros = new Object[] {details.getStatus(), details.getPriceamt(), details.getUnitcost(), details.getCode(), details.getDescription(), details.getCat(), details.getGlset(), details.getDoctype(), details.getInternaldocnum()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheInvoiceDetailProductDao|updateReg|:"+ex);		 
		}
		
		return ok;
	}

    public boolean deleteReg(Integer doctype, Integer internaldocnum, Integer detailnum) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ATTACHE_INVOICE_DETAIL_PRODUCT_TEMP WHERE DOCTYPE = ? AND INTERNALDOCNUM = ? AND DETAILNUM = ?";		
			Object[] parametros = new Object[] { doctype, internaldocnum, detailnum };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheInvoiceDetailProductDao|deleteReg|:"+ex);			
		}
		return ok;
	}

	public boolean emptyTempTable() {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ATTACHE_INVOICE_DETAIL_PRODUCT_TEMP";	
			if (enocJdbc.update(comando)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheInvoiceDetailProductDao|emptyTempTable|:"+ex);			
		}
		return ok;
	}

	public boolean emptyTable() {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ATTACHE_INVOICE_DETAIL_PRODUCT";	
			if (enocJdbc.update(comando)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheInvoiceDetailProductDao|emptyTable|:"+ex);			
		}
		return ok;
	}

	public AttacheInvoiceDetailProduct mapeaRegId(Integer doctype, Integer internaldocnum, Integer detailnum){
		AttacheInvoiceDetailProduct opcion = new AttacheInvoiceDetailProduct();
		try{ 
			String comando = "SELECT COUNT(CODE) FROM ENOC.ATTACHE_INVOICE_DETAIL_PRODUCT_TEMP WHERE DOCTYPE = ? AND INTERNALDOCNUM = ? AND DETAILNUM = ?";
			Object[] parametros = new Object[] { doctype, internaldocnum, detailnum };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){				
				comando = "SELECT DOCTYPE, INTERNALDOCNUM, DOCDATE, CODE, DOCNUM, CAT, GLSET FROM ENOC.ATTACHE_INVOICE_DETAIL_PRODUCT_TEMP WHERE DOCTYPE = ? AND INTERNALDOCNUM = ? AND DETAILNUM = ?";							
				opcion = enocJdbc.queryForObject(comando, new AttacheInvoiceDetailProductMapper(), parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheInvoiceDetailProductDao|mapeaRegId|:"+ex);
		}
		
		return opcion;
	}

    public boolean existeReg(Integer doctype, Integer internaldocnum, Integer detailnum){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ATTACHE_INVOICE_DETAIL_PRODUCT_TEMP WHERE DOCTYPE = ? AND INTERNALDOCNUM = ? AND DETAILNUM = ?";
			Object[] parametros = new Object[] { doctype, internaldocnum, detailnum };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){											
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheInvoiceDetailProductDao|existeReg|:"+ex);
		}
		
		return ok;
	}

	public String maximoReg() {
		String maximo 			= "01";		
		try{
			String comando = "SELECT COALESCE(MAX(DETAILNUM)+1,1) AS MAXIMO FROM ENOC.ATTACHE_INVOICE_DETAIL_PRODUCT_TEMP";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class);
				if (maximo.length()==1) maximo = "0"+maximo;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheInvoiceDetailProductDao|maximoReg|:"+ex);
		}
		
		return maximo;		
	}
    
	public boolean copiarTabla() {
		boolean ok = false;
		try{
			String comando = "INSERT INTO ATTACHE_INVOICE_DETAIL_PRODUCT SELECT * FROM ATTACHE_INVOICE_DETAIL_PRODUCT_TEMP";
			if(enocJdbc.update(comando) >= 1){
				ok = true;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheInvoiceDetailProductDao|copiarTabla|:" + ex);
		}
		return ok;
	}
}
