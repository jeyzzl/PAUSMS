package aca.attache.spring;

import java.beans.Customizer;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AttacheCustomerAdjustmentTransactionDao {

    @Autowired	
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;

    public boolean insertReg(AttacheCustomerAdjustmentTransaction customer){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.ATTACHE_CUSTOMER_ADJUSTMENT_TRANSACTION(CODE, INVNUM, DOCNUM, REFER, TRANTYPE, TRANDATE, INVAMT, TOTALADJUSTAMT, INVDATE, AGECODE, COMMENT_) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			Object[] parametros = new Object[] {customer.getCode(), customer.getInvnum(), customer.getDocnum(), customer.getRefer(), customer.getTrantype(), customer.getTrandate(), customer.getInvamt(), customer.getTotaladjustamt(), customer.getInvdate(), customer.getAgecode(),
												customer.getComment()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerAdjustmentTransactionDao|insertReg|:"+ex);			
		}
		
		return ok;
	}

	public boolean insertBatch(List<AttacheCustomerAdjustmentTransaction> customers){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.ATTACHE_CUSTOMER_ADJUSTMENT_TRANSACTION(CODE, INVNUM, DOCNUM, REFER, TRANTYPE, TRANDATE, INVAMT, TOTALADJUSTAMT, INVDATE, AGECODE, COMMENT_) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			enocJdbc.batchUpdate(comando, customers, customers.size(),
				(ps, customer) -> {
					ps.setString(1, customer.getCode());
					ps.setString(2, customer.getInvnum());
					ps.setString(3, customer.getDocnum());
					ps.setString(4, customer.getRefer());
					ps.setInt(5, customer.getTrantype());
					ps.setDate(6, customer.getTrandate());
					ps.setDouble(7, customer.getInvamt());
					ps.setDouble(8, customer.getTotaladjustamt());
					ps.setDate(9, customer.getInvdate());
					ps.setDouble(10, customer.getAgecode());
					ps.setString(11, customer.getComment());
				}	
			);

			ok = true;
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerAdjustmentTransactionDao|insertBatch|:"+ex);			
		}
		
		return ok;
	}

    public boolean updateReg(AttacheCustomerAdjustmentTransaction customer) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ATTACHE_CUSTOMER_ADJUSTMENT_TRANSACTION SET INVNUM = ?, INVAMT = ?, REFER = ?, TRANDATE = ?, INVAMT = ?, TOTALADJUSTAMT = ?, INVDATE = ?, AGECODE = ?, COMMENT_ = ? WHERE CODE = ? AND DOCNUM = ? AND TRANTYPE = ?";
			Object[] parametros = new Object[] {customer.getInvnum(), customer.getInvamt(), customer.getRefer(), customer.getTrandate(), customer.getInvamt(), customer.getTotaladjustamt(), customer.getInvdate(), customer.getAgecode(), customer.getComment(), customer.getCode(), customer.getDocnum(), customer.getTrantype()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerAdjustmentTransactionDao|updateReg|:"+ex);		 
		}
		
		return ok;
	}

    public boolean deleteReg(String code, String docnum, Integer trantype) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ATTACHE_CUSTOMER_ADJUSTMENT_TRANSACTION WHERE CODE = ? AND DOCNUM = ? AND TRANTYPE = ?";		
			Object[] parametros = new Object[] { code, docnum, trantype };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerAdjustmentTransactionDao|deleteReg|:"+ex);			
		}
		return ok;
	}

	public boolean emptyTempTable() {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ATTACHE_CUSTOMER_ADJUSTMENT_TRANSACTION";		
			if (enocJdbc.update(comando)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerAdjustmentTransactionDao|emptyTempTable|:"+ex);			
		}
		return ok;
	}

	public boolean emptyTable() {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ATTACHE_CUSTOMER_ADJUSTMENT_TRANSACTION";		
			if (enocJdbc.update(comando)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerAdjustmentTransactionDao|emptyTable|:"+ex);			
		}
		return ok;
	}

	public boolean copiarTabla() {
		boolean ok = false;
		try{
			String comando = "INSERT INTO ATTACHE_CUSTOMER_ADJUSTMENT_TRANSACTION SELECT * FROM ATTACHE_CUSTOMER_ADJUSTMENT_TRANSACTION";
			if(enocJdbc.update(comando) >= 1){
				ok = true;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerAdjustmentTransactionDao|copiarTabla|:" + ex);
		}
		return ok;
	}

	public AttacheCustomerAdjustmentTransaction mapeaRegId(String code, String docnum, Integer trantype){
		AttacheCustomerAdjustmentTransaction opcion = new AttacheCustomerAdjustmentTransaction();
		try{ 
			String comando = "SELECT COUNT(CODE) FROM ENOC.ATTACHE_CUSTOMER_ADJUSTMENT_TRANSACTION WHERE CODE = ? AND DOCNUM = ? AND TRANTYPE = ?";
			Object[] parametros = new Object[] { code, docnum, trantype };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){				
				comando = "SELECT CODE, INVNUM, DOCNUM, REFER, TRANTYPE, TRANDATE, INVAMT, TOTALADJUSTAMT, INVDATE, AGECODE, COMMENT_ FROM ENOC.ATTACHE_CUSTOMER_ADJUSTMENT_TRANSACTION WHERE CODE = ? AND DOCNUM = ? AND TRANTYPE = ?";							
				opcion = enocJdbc.queryForObject(comando, new AttacheCustomerAdjustmentTransactionMapper(), parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerAdjustmentTransactionDao|mapeaRegId|:"+ex);
		}
		
		return opcion;
	}

    public boolean existeReg(String code, String docnum, Integer trantype){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ATTACHE_CUSTOMER_ADJUSTMENT_TRANSACTION WHERE CODE = ? AND DOCNUM = ? AND TRANTYPE = ?";
			Object[] parametros = new Object[] { code, docnum, trantype };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){											
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerAdjustmentTransactionDao|existeReg|:"+ex);
		}
		
		return ok;
	}

	public List<AttacheCustomerAdjustmentTransaction> lisAjustePorInvoice( String matricula, String invnum, String orden ) {
		List<AttacheCustomerAdjustmentTransaction> lista 	= new ArrayList<AttacheCustomerAdjustmentTransaction>();
		String comando	= "";
		
		try{
			comando = " SELECT ACAT.*"
					+ " FROM ATTACHE_CUSTOMER_ADJUSTMENT_TRANSACTION ACAT"
					+ " INNER JOIN ATTACHE_CUSTOMER_INVOICE_TRANSACTION ACIT"
					+ " ON ACIT.INVNUM = ACAT.INVNUM"
					+ " WHERE ('1'||TRIM(ACAT.CODE) IN (?) OR TRIM(ACAT.CODE) IN (?))"
					+ " AND ACAT.INVNUM = ?"
					+ " AND ACIT.TRANTYPE = 1"+orden;		 
			
			Object[] parametros = new Object[] {matricula, matricula, invnum};
			lista = enocJdbc.query(comando, new AttacheCustomerAdjustmentTransactionMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerAdjustmentTransactionDao|lisAjustePorInvoice|:"+ex);
		}
		return lista;
	}

	public List<AttacheCustomerAdjustmentTransaction> lisAjuste( String matricula, String orden ) {
		List<AttacheCustomerAdjustmentTransaction> lista 	= new ArrayList<AttacheCustomerAdjustmentTransaction>();
		String comando	= "";
		
		try{
			comando = " SELECT ACAT.*"
					+ " FROM ATTACHE_CUSTOMER_ADJUSTMENT_TRANSACTION ACAT"
					+ " INNER JOIN ATTACHE_CUSTOMER_INVOICE_TRANSACTION ACIT"
					+ " ON ACIT.INVNUM = ACAT.INVNUM"
					+ " WHERE ('1'||TRIM(ACAT.CODE) IN (?) OR TRIM(ACAT.CODE) IN (?))"
					+ " AND ACIT.TRANTYPE = 1"+orden;		 
			
			Object[] parametros = new Object[] {matricula, matricula};
			lista = enocJdbc.query(comando, new AttacheCustomerAdjustmentTransactionMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.AttacheCustomerAdjustmentTransactionDao|lisAjuste|:"+ex);
		}
		return lista;
	}
    
}
