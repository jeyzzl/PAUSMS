package aca.attache.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AttacheInvoiceHeaderDao {

    @Autowired	
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;

    public boolean insertReg(AttacheInvoiceHeader invoice){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.ATTACHE_INVOICE_HEADER_TEMP(DOCTYPE, INTERNALDOCNUM, DOCDATE, CODE, DOCNUM, CAT, GLSET) VALUES(?, ?, ?, ?, ?, ?, ?)";
			Object[] parametros = new Object[] {invoice.getDoctype(), invoice.getInternaldocnum(), invoice.getDocdate(), invoice.getCode(), invoice.getDocnum(), invoice.getCat(), invoice.getGlset()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheInvoiceHeaderDao|insertReg|:"+ex);			
		}
		
		return ok;
	}

	public boolean insertBatch(List<AttacheInvoiceHeader> customers){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.ATTACHE_INVOICE_HEADER_TEMP(DOCTYPE, INTERNALDOCNUM, DOCDATE, CODE, DOCNUM, CAT, GLSET) VALUES(?, ?, ?, ?, ?, ?, ?)";

			enocJdbc.batchUpdate(comando, customers, customers.size(),
				(ps, customer) -> {
					ps.setInt(1, customer.getDoctype());
					ps.setInt(2, customer.getInternaldocnum());
					ps.setDate(3, customer.getDocdate());
					ps.setString(4, customer.getCode());
					ps.setString(5, customer.getDocnum());
					ps.setString(6, customer.getCat());
					ps.setString(7, customer.getGlset());
				}	
			);

			ok = true;
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheInvoiceHeaderDao|insertBatch|:"+ex);			
		}
		
		return ok;
	}

    public boolean updateReg(AttacheInvoiceHeader invoice) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ATTACHE_INVOICE_HEADER_TEMP SET DOCDATE = ?, DOCNUM = ?, CAT = ?, GLSET = ? WHERE DOCTYPE = ? AND INTERNALDOCNUM = ? AND CODE = ?";
			Object[] parametros = new Object[] {invoice.getDocdate(), invoice.getDocnum(), invoice.getCat(), invoice.getGlset(), invoice.getDoctype(), invoice.getInternaldocnum(), invoice.getCode()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheInvoiceHeaderDao|updateReg|:"+ex);		 
		}
		
		return ok;
	}

    public boolean deleteReg(Integer doctype, Integer internaldocnum, String code) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ATTACHE_INVOICE_HEADER_TEMP WHERE DOCTYPE = ? AND INTERNALDOCNUM = ? AND CODE = ?";		
			Object[] parametros = new Object[] { doctype, internaldocnum, code };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheInvoiceHeaderDao|deleteReg|:"+ex);			
		}
		return ok;
	}

	public boolean emptyTable() {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ATTACHE_INVOICE_HEADER";		
			if (enocJdbc.update(comando)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheInvoiceHeaderDao|emptyTable|:"+ex);			
		}
		return ok;
	}

	public boolean emptyTempTable() {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ATTACHE_INVOICE_HEADER_TEMP";		
			if (enocJdbc.update(comando)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheInvoiceHeaderDao|emptyTempTable|:"+ex);			
		}
		return ok;
	}

	public AttacheInvoiceHeader mapeaRegId(Integer doctype, Integer internaldocnum, String code){
		AttacheInvoiceHeader opcion = new AttacheInvoiceHeader();
		try{ 
			String comando = "SELECT COUNT(CODE) FROM ENOC.ATTACHE_INVOICE_HEADER_TEMP WHERE DOCTYPE = ? AND INTERNALDOCNUM = ? AND CODE = ?";
			Object[] parametros = new Object[] { doctype, internaldocnum, code };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){				
				comando = "SELECT DOCTYPE, INTERNALDOCNUM, DOCDATE, CODE, DOCNUM, CAT, GLSET FROM ENOC.ATTACHE_INVOICE_HEADER_TEMP WHERE DOCTYPE = ? AND INTERNALDOCNUM = ? AND CODE = ?";							
				opcion = enocJdbc.queryForObject(comando, new AttacheInvoiceHeaderMapper(), parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheInvoiceHeaderDao|mapeaRegId|:"+ex);
		}
		
		return opcion;
	}

    public boolean existeReg(Integer doctype, Integer internaldocnum, String code){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ATTACHE_INVOICE_HEADER_TEMP WHERE DOCTYPE = ? AND INTERNALDOCNUM = ? AND CODE = ?";
			Object[] parametros = new Object[] { doctype, internaldocnum, code };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){											
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheInvoiceHeaderDao|existeReg|:"+ex);
		}
		
		return ok;
	}

	public boolean copiarTabla() {
		boolean ok = false;
		try{
			String comando = "INSERT INTO ATTACHE_INVOICE_HEADER SELECT * FROM ATTACHE_INVOICE_HEADER_TEMP";
			if(enocJdbc.update(comando) >= 1){
				ok = true;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheInvoiceHeaderDao|copiarTabla|:" + ex);
		}
		return ok;
	}
    
}
