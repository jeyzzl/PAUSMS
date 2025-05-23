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

@Component
public class AttacheCustomerPaymentTransactionDao {

    @Autowired	
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;

    public boolean insertReg(AttacheCustomerPaymentTransaction customer){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.ATTACHE_CUSTOMER_PAYMENT_TRANSACTION(CODE, INVNUM, DOCNUM, REFER, TRANTYPE, TRANDATE, INVAMT, TOTALPAYAMT, INVDATE, AGECODE, COMMENT_) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			Object[] parametros = new Object[] {customer.getCode(), customer.getInvnum(), customer.getDocnum(), customer.getRefer(), customer.getTrantype(), customer.getTrandate(), customer.getInvamt(), customer.getTotalpayamt(), customer.getInvdate(), customer.getAgecode(),
												customer.getComment()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerPaymentTransactionDao|insertReg|:"+ex);			
		}
		
		return ok;
	}

	public boolean insertBatch(List<AttacheCustomerPaymentTransaction> customers){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.ATTACHE_CUSTOMER_PAYMENT_TRANSACTION(CODE, INVNUM, DOCNUM, REFER, TRANTYPE, TRANDATE, INVAMT, TOTALPAYAMT, INVDATE, AGECODE, COMMENT_) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			enocJdbc.batchUpdate(comando, customers, customers.size(),
				(ps, customer) -> {
					ps.setString(1, customer.getCode());
					ps.setString(2, customer.getInvnum());
					ps.setString(3, customer.getDocnum());
					ps.setString(4, customer.getRefer());
					ps.setInt(5, customer.getTrantype());
					ps.setDate(6, customer.getTrandate());
					ps.setDouble(7, customer.getInvamt());
					ps.setDouble(8, customer.getTotalpayamt());
					ps.setDate(9, customer.getInvdate());
					ps.setDouble(10, customer.getAgecode());
					ps.setString(11, customer.getComment());
				}	
			);

			ok = true;
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerPaymentTransactionDao|insertBatch|:"+ex);			
		}
		
		return ok;
	}

    public boolean updateReg(AttacheCustomerPaymentTransaction customer) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ATTACHE_CUSTOMER_PAYMENT_TRANSACTION SET INVNUM = ?, INVAMT = ?, REFER = ?, TRANDATE = ?, INVAMT = ?, TOTALPAYAMT = ?, INVDATE = ?, AGECODE = ?, COMMENT_ = ? WHERE CODE = ? AND DOCNUM = ? AND TRANTYPE = ?";
			Object[] parametros = new Object[] {customer.getInvnum(), customer.getInvamt(), customer.getRefer(), customer.getTrandate(), customer.getInvamt(), customer.getTotalpayamt(), customer.getInvdate(), customer.getAgecode(), customer.getComment(), customer.getCode(), customer.getDocnum(), customer.getTrantype()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerPaymentTransactionDao|updateReg|:"+ex);		 
		}
		
		return ok;
	}

    public boolean deleteReg(String code, String docnum, Integer trantype) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ATTACHE_CUSTOMER_PAYMENT_TRANSACTION WHERE CODE = ? AND DOCNUM = ? AND TRANTYPE = ?";		
			Object[] parametros = new Object[] { code, docnum, trantype };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerPaymentTransactionDao|deleteReg|:"+ex);			
		}
		return ok;
	}

	public boolean emptyTempTable() {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ATTACHE_CUSTOMER_PAYMENT_TRANSACTION";		
			if (enocJdbc.update(comando)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerPaymentTransactionDao|emptyTempTable|:"+ex);			
		}
		return ok;
	}

	public boolean emptyTable() {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ATTACHE_CUSTOMER_PAYMENT_TRANSACTION";		
			if (enocJdbc.update(comando)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerPaymentTransactionDao|emptyTable|:"+ex);			
		}
		return ok;
	}

	public AttacheCustomerPaymentTransaction mapeaRegId(String code, String docnum, Integer trantype){
		AttacheCustomerPaymentTransaction opcion = new AttacheCustomerPaymentTransaction();
		try{ 
			String comando = "SELECT COUNT(CODE) FROM ENOC.ATTACHE_CUSTOMER_PAYMENT_TRANSACTION WHERE CODE = ? AND DOCNUM = ? AND TRANTYPE = ?";
			Object[] parametros = new Object[] { code, docnum, trantype };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){				
				comando = "SELECT CODE, INVNUM, DOCNUM, REFER, TRANTYPE, TRANDATE, INVAMT, TOTALPAYAMT, INVDATE, AGECODE FROM ENOC.ATTACHE_CUSTOMER_PAYMENT_TRANSACTION WHERE CODE = ? AND DOCNUM = ? AND TRANTYPE = ?";							
				opcion = enocJdbc.queryForObject(comando, new AttacheCustomerPaymentTransactionMapper(), parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerPaymentTransactionDao|mapeaRegId|:"+ex);
		}
		
		return opcion;
	}

    public boolean existeReg(String code, String docnum, Integer trantype){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ATTACHE_CUSTOMER_PAYMENT_TRANSACTION WHERE CODE = ? AND DOCNUM = ? AND TRANTYPE = ?";
			Object[] parametros = new Object[] { code, docnum, trantype };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){											
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerPaymentTransactionDao|existeReg|:"+ex);
		}
		
		return ok;
	}

	public boolean copiarTabla() {
		boolean ok = false;
		try{
			String comando = "INSERT INTO ATTACHE_CUSTOMER_PAYMENT_TRANSACTION SELECT * FROM ATTACHE_CUSTOMER_PAYMENT_TRANSACTION";
			if(enocJdbc.update(comando) >= 1){
				ok = true;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerPaymentTransactionDao|copiarTabla|:" + ex);
		}
		return ok;
	}

	public List<AttacheCustomerPaymentTransaction> lisPagos( String matricula, String orden ) {
		List<AttacheCustomerPaymentTransaction> lista 	= new ArrayList<AttacheCustomerPaymentTransaction>();
		String comando	= "";
		
		try{
			comando = " SELECT ACPT.*"
					+ " FROM ATTACHE_CUSTOMER_PAYMENT_TRANSACTION ACPT"
					+ " INNER JOIN ATTACHE_CUSTOMER_INVOICE_TRANSACTION ACIT"
					+ " ON ACIT.INVNUM = ACPT.INVNUM"
					+ " WHERE ('1'||TRIM(ACPT.CODE) IN (?) OR TRIM(ACPT.CODE) IN (?))"
					+ " AND ACIT.TRANTYPE = 1"+orden;		 
			
			Object[] parametros = new Object[] {matricula, matricula};
			lista = enocJdbc.query(comando, new AttacheCustomerPaymentTransactionMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerPaymentTransactionDao|lisPagos|:"+ex);
		}
		return lista;
	}

	public List<AttacheCustomerPaymentTransaction> lisPagosPorInvoice( String matricula, String invnum, String orden ) {
		List<AttacheCustomerPaymentTransaction> lista 	= new ArrayList<AttacheCustomerPaymentTransaction>();
		String comando	= "";
		
		try{
			comando = " SELECT ACPT.*"
					+ " FROM ATTACHE_CUSTOMER_PAYMENT_TRANSACTION ACPT"
					+ " INNER JOIN ATTACHE_CUSTOMER_INVOICE_TRANSACTION ACIT"
					+ " ON ACIT.INVNUM = ACPT.INVNUM"
					+ " WHERE ('1'||TRIM(ACPT.CODE) IN (?) OR TRIM(ACPT.CODE) IN (?))"
					+ " AND ACPT.INVNUM = ?"
					+ " AND ACIT.TRANTYPE = 1"+orden;		 
			
			Object[] parametros = new Object[] {matricula, matricula, invnum};
			lista = enocJdbc.query(comando, new AttacheCustomerPaymentTransactionMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerPaymentTransactionDao|lisPagosPorInvoice|:"+ex);
		}
		return lista;
	}

	public HashMap<String, AttacheCustomerPaymentTransaction> mapaPagosPorInvoice( String matricula, String invnum ) {
		HashMap<String, AttacheCustomerPaymentTransaction> mapa = new HashMap<String, AttacheCustomerPaymentTransaction>();
		List<AttacheCustomerPaymentTransaction> lista 		 = new ArrayList<AttacheCustomerPaymentTransaction>();
		String comando		= "";
		try{
			comando = " SELECT ACPT.*"
					+ " FROM ATTACHE_CUSTOMER_PAYMENT_TRANSACTION ACPT"
					+ " INNER JOIN ATTACHE_CUSTOMER_INVOICE_TRANSACTION ACIT"
					+ " ON ACIT.INVNUM = ACPT.INVNUM"
					+ " WHERE ('1'||TRIM(ACPT.CODE) IN (?) OR TRIM(ACPT.CODE) IN (?))"
					+ " AND ACPT.INVNUM = ?";
			
			Object[] parametros = new Object[] {matricula, matricula, invnum};
			lista = enocJdbc.query(comando, new AttacheCustomerPaymentTransactionMapper(), parametros);
			for(AttacheCustomerPaymentTransaction payment : lista){
				mapa.put(payment.getInvnum(), payment);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.AttacheCustomerPaymentTransactionDao|mapAttacheCustomerPaymentTransactionno|:"+ex);
		}
		return mapa;
	}
    
}
