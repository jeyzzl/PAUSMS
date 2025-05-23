package aca.attache.spring;

import java.beans.Customizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.archivo.spring.ArchDocAlumMapper;
import aca.archivos.spring.ArchivosAlumnoMapper;

@Component
public class AttacheCustomerInvoiceTransactionDao {

    @Autowired	
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;

    public boolean insertReg(AttacheCustomerInvoiceTransaction customer){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.ATTACHE_CUSTOMER_INVOICE_TRANSACTION(CODE, INVNUM, DOCNUM, REFER, CAT, TRANTYPE, TRANDATE, INVAMT, AGECODE, INVBAL, INVDATE, COMMENT_) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			Object[] parametros = new Object[] {customer.getCode(), customer.getInvnum(), customer.getDocnum(), customer.getRefer(), customer.getCat(), customer.getTrantype(), customer.getTrandate(), customer.getInvamt(),
												customer.getAgecode(), customer.getInvbal(), customer.getInvdate(), customer.getComment()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerInvoiceTransactionDao|insertReg|:"+ex);			
		}
		
		return ok;
	}

	public boolean insertBatch(List<AttacheCustomerInvoiceTransaction> customers){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.ATTACHE_CUSTOMER_INVOICE_TRANSACTION(CODE, INVNUM, DOCNUM, REFER, CAT, TRANTYPE, TRANDATE, INVAMT, AGECODE, INVBAL, INVDATE, COMMENT_) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			enocJdbc.batchUpdate(comando, customers, customers.size(),
				(ps, customer) -> {
					ps.setString(1, customer.getCode());
					ps.setString(2, customer.getInvnum());
					ps.setString(3, customer.getDocnum());
					ps.setString(4, customer.getRefer());
					ps.setString(5, customer.getCat());
					ps.setInt(6, customer.getTrantype());
					ps.setDate(7, customer.getTrandate());
					ps.setDouble(8, customer.getInvamt());
					ps.setDouble(9, customer.getAgecode());
					ps.setDouble(10, customer.getInvbal());
					ps.setDate(11, customer.getInvdate());
					ps.setString(12, customer.getComment());
				}	
			);

			ok = true;
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerInvoiceTransactionDao|insertBatch|:"+ex);			
		}
		
		return ok;
	}

    public boolean updateReg(AttacheCustomerInvoiceTransaction customer) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ATTACHE_CUSTOMER_INVOICE_TRANSACTION SET INVNUM = ?, INVAMT = ?, REFER = ?, CAT = ?, TRANDATE = ?, AGECODE = ?, INVBAL = ?, INVDATE = ?, COMMENT_ = ? WHERE CODE = ? AND DOCNUM = ? AND TRANTYPE = ?";
			Object[] parametros = new Object[] {customer.getInvnum(), customer.getInvamt(), customer.getRefer(), customer.getCat(), customer.getTrandate(), customer.getAgecode(), customer.getInvbal(), customer.getInvdate(), 
												customer.getComment(), customer.getCode(), customer.getDocnum(), customer.getTrantype()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerDaoInvoiceTransaction|updateReg|:"+ex);		 
		}
		
		return ok;
	}

    public boolean deleteReg(String code, String docnum, Integer trantype) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ATTACHE_CUSTOMER_INVOICE_TRANSACTION WHERE CODE = ? AND DOCNUM = ? AND TRANTYPE = ?";		
			Object[] parametros = new Object[] { code, docnum, trantype };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerDaoInvoiceTransaction|deleteReg|:"+ex);			
		}
		return ok;
	}

	public AttacheCustomerInvoiceTransaction mapeaRegId(String matricula, String invnum){
		AttacheCustomerInvoiceTransaction opcion = new AttacheCustomerInvoiceTransaction();
		try{ 
			String comando = "SELECT COUNT(CODE) FROM ENOC.ATTACHE_CUSTOMER_INVOICE_TRANSACTION WHERE ('1'||TRIM(CODE) IN (?) OR TRIM(CODE) IN (?)) AND INVNUM = ? AND TRANTYPE = 1";
			Object[] parametros = new Object[] { matricula, matricula, invnum };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){				
				comando = "SELECT * FROM ATTACHE_CUSTOMER_INVOICE_TRANSACTION WHERE ('1'||TRIM(CODE) IN (?) OR TRIM(CODE) IN (?)) AND INVNUM = ? AND TRANTYPE = 1";							
				opcion = enocJdbc.queryForObject(comando, new AttacheCustomerInvoiceTransactionMapper(), parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerDaoInvoiceTransaction|mapeaRegId|:"+ex);
		}
		
		return opcion;
	}

    public boolean existeReg(String code, String docnum, Integer trantype){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ATTACHE_CUSTOMER_INVOICE_TRANSACTION WHERE CODE = ? AND DOCNUM = ? AND TRANTYPE = ?";
			Object[] parametros = new Object[] { code, docnum, trantype };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){											
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerDaoInvoiceTransaction|existeReg|:"+ex);
		}
		
		return ok;
	}

    public boolean emptyTempTable() {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ATTACHE_CUSTOMER_INVOICE_TRANSACTION";		
			if (enocJdbc.update(comando)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerDaoInvoiceTransaction|emptyTempTable|:"+ex);			
		}
		return ok;
	}

	public boolean emptyTable() {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ATTACHE_CUSTOMER_INVOICE_TRANSACTION";		
			if (enocJdbc.update(comando)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerDaoInvoiceTransaction|emptyTable|:"+ex);			
		}
		return ok;
	}

	public boolean copiarTabla() {
		boolean ok = false;
		try{
			String comando = "INSERT INTO ATTACHE_CUSTOMER_INVOICE_TRANSACTION SELECT * FROM ATTACHE_CUSTOMER_INVOICE_TRANSACTION";
			if(enocJdbc.update(comando) >= 1){
				ok = true;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerDaoInvoiceTransaction|copiarTabla|:" + ex);
		}
		return ok;
	}

	public String pagoInvoiceTotal(String matricula, String invnum, String orden) {
		String pago 			= "0";
		
		try{
			String comando = "SELECT SUM(ACPT.INVAMT) AS VALOR"
						+ " FROM ATTACHE_CUSTOMER_PAYMENT_TRANSACTION ACPT"
						+ " INNER JOIN ATTACHE_CUSTOMER_INVOICE_TRANSACTION ACIT"
						+ " ON ACIT.INVNUM = ACPT.INVNUM"
						+ " WHERE ('1'||TRIM(ACPT.CODE) IN (?) OR TRIM(ACPT.CODE) IN (?)) AND ACPT.INVNUM = ?"
						+ " GROUP BY ACIT.INVNUM"+orden; 
			
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
 				pago = enocJdbc.queryForObject(comando, String.class);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerDaoInvoiceTransaction|pagoInvoiceTotal|:"+ex);
		}
		return pago;
	}

	public List<AttacheCustomerInvoiceTransaction> lisInvoices(String matricula, String orden){
		List<AttacheCustomerInvoiceTransaction> lista	= new ArrayList<AttacheCustomerInvoiceTransaction>();	

		try{
			String comando ="SELECT *"
		    		+ " FROM ATTACHE_CUSTOMER_INVOICE_TRANSACTION"
					+ " WHERE '1'||TRIM(CODE) IN (?)"
					+ " OR TRIM(CODE) IN (?) "
					+ " AND TRANTYPE = 1"+orden;
			Object[] parametros = new Object[]{ matricula, matricula };		
			lista = enocJdbc.query(comando, new AttacheCustomerInvoiceTransactionMapper(), parametros);	
		}
		catch(Exception e){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerDaoInvoiceTransaction|lisInvoices|:"+e);
		}

		return lista;
	}

	public List<AttacheCustomerInvoiceTransaction> lisInvoices(String matricula, String invnum, String orden){
		List<AttacheCustomerInvoiceTransaction> lista	= new ArrayList<AttacheCustomerInvoiceTransaction>();	

		try{
			String comando ="SELECT *"
		    		+ " FROM ATTACHE_CUSTOMER_INVOICE_TRANSACTION"
					+ " WHERE '1'||TRIM(CODE) IN (?)"
					+ " OR TRIM(CODE) IN (?)"
					+ " AND INVNUM = ?"
					+ " AND TRANTYPE = 1"+orden;
			Object[] parametros = new Object[]{ matricula, matricula, invnum};		
			lista = enocJdbc.query(comando, new AttacheCustomerInvoiceTransactionMapper(), parametros);	
		}
		catch(Exception e){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerDaoInvoiceTransaction|lisInvoices|:"+e);
		}

		return lista;
	}

	public HashMap<String, AttacheCustomerInvoiceTransaction> mapaInvoices(String matricula) {
		HashMap<String, AttacheCustomerInvoiceTransaction> mapa = new HashMap<String, AttacheCustomerInvoiceTransaction>();
		List<AttacheCustomerInvoiceTransaction> lista 		 = new ArrayList<AttacheCustomerInvoiceTransaction>();
		try{
			String comando ="SELECT *"
		    		+ " FROM ATTACHE_CUSTOMER_INVOICE_TRANSACTION"
					+ " WHERE '1'||TRIM(CODE) IN (?)"
					+ " OR TRIM(CODE) IN (?) ";
			Object[] parametros = new Object[] {matricula, matricula};
			lista = enocJdbc.query(comando, new AttacheCustomerInvoiceTransactionMapper(), parametros);
			for(AttacheCustomerInvoiceTransaction m : lista){
				mapa.put(m.getInvnum(), m);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerDaoInvoiceTransaction|mapaInvoices|:"+ex);
		}
		return mapa;
	}

	public HashMap<String, String> mapaPagoPorInvoice(String matricula, String orden) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		String comando		= "";
		try{
			comando = " SELECT ACIT.INVNUM AS LLAVE, SUM(ACPT.INVAMT) AS VALOR"
					+ " FROM ATTACHE_CUSTOMER_PAYMENT_TRANSACTION ACPT"
					+ " INNER JOIN ATTACHE_CUSTOMER_INVOICE_TRANSACTION ACIT"
					+ " ON ACIT.INVNUM = ACPT.INVNUM"
					+ " WHERE '1'||TRIM(ACPT.CODE) IN (?) OR TRIM(ACPT.CODE) IN (?)"
					+ " AND ACIT.TRANTYPE = 1"
					+ " GROUP BY ACIT.INVNUM"+orden;
			Object[] parametros = new Object[] {matricula, matricula};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerDaoInvoiceTransaction|mapaPagoPorInvoice|:"+ex);
		}
		return mapa;
	}
    
}
