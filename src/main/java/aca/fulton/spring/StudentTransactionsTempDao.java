package aca.fulton.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.alumno.spring.AlumAcademicoMapper;

@Component
public class StudentTransactionsTempDao {

    @Autowired	
	@Qualifier("jdbcEnoc")	
	private JdbcTemplate enocJdbc;

	public boolean insertTransaction(StudentTransactionsTemp transaction) {
        boolean ok = false;
        try {
            String comando = "INSERT INTO ENOC.FULTON_STUDENT_TRANSACTIONS_TEMP(STUDENT_ID, TRAN_DATE, AMOUNT, DC, DESCRIPTION, PERIOD, TYPE, REFERENCE) VALUES(?, TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), TO_NUMBER(?,'999999999999999999D999999999999999999'), ?, ?, ?, ?, ?)";
            Object[] parametros = new Object[] { transaction.getStudentId(), transaction.getDate(), transaction.getAmount().toString(), transaction.getDc(), transaction.getDescription(), transaction.getPeriod(), transaction.getType(), transaction.getReference() };
            if (enocJdbc.update(comando, parametros) == 1) {
                ok = true;
            }
        } catch (Exception ex) {
            System.out.println("Error - aca.opcion.spring.StudentTransactionsTempDao|insertTransaction|:" + ex);
        }
        return ok;
    }

	public boolean updateReg(StudentTransactionsTemp transaction) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.FULTON_STUDENT_TRANSACTIONS_TEMP SET TRAN_DATE = TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), AMOUNT = TO_NUMBER(?,'999999999999999999D999999999999999999'), DC = ?, DESCRIPTION = ?, PERIOD = ?, TYPE = ?, REFERENCE = ? WHERE STUDENT_ID = ?";
			Object[] parametros = new Object[] { transaction.getDate(), transaction.getAmount(), transaction.getDc(), transaction.getDescription(), transaction.getPeriod(), transaction.getType(), transaction.getReference(), transaction.getStudentId() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentTransactionsTempDao|updateReg|:"+ex);		 
		}
		
		return ok;
	}	
	
	public boolean deleteReg(String studentId, String tranDate) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.FULTON_STUDENT_TRANSACTIONS_TEMP WHERE STUDENT_ID = ? AND TRAN_DATE = TO_DATE(?,'DD/MM/YYYY HH24:MI:SS')";		
			Object[] parametros = new Object[] { studentId, tranDate };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentTransactionsTempDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public Student mapeaRegId(String studentId, String tranDate){
		Student opcion = new Student();
		try{ 
			String comando = "SELECT COUNT(STUDENT_ID) FROM ENOC.FULTON_STUDENT_TRANSACTIONS_TEMP WHERE STUDENT_ID = ? AND TRAN_DATE = TO_DATE(?,'DD/MM/YYYY HH24:MI:SS')";
			Object[] parametros = new Object[] { studentId, tranDate};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){				
				comando = "SELECT STUDENT_ID, TRAN_DATE, AMOUNT, DC, DESCRIPTION, PERIOD, TYPE, REFERENCE FROM ENOC.FULTON_STUDENT_TRANSACTIONS_TEMP WHERE STUDENT_ID = ? AND TRAN_DATE = TO_DATE(?,'DD/MM/YYYY HH24:MI:SS')";							
				opcion = enocJdbc.queryForObject(comando, new StudentMapper(), parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentTransactionsTempDao|mapeaRegId|:"+ex);
		}
		
		return opcion;
	}

    public List<StudentTransactionsTemp> getListAll( String codigoPersonal, String orden ) {
		
		List<StudentTransactionsTemp> lista = new ArrayList<StudentTransactionsTemp>();
		
		try{
			String comando = "SELECT "+
				" STUDENT_ID, TRAN_DATE, AMOUNT, DC, DESCRIPTION, PERIOD, TYPE, REFERENCE"+
				" FROM ENOC.FULTON_STUDENT_TRANSACTIONS_TEMP "+
                " WHERE STUDENT_ID IN (SELECT ID FROM FULTON_STUDENT_TEMP WHERE '1'||STUDENT_CODE = ?)"+orden;
			lista = enocJdbc.query(comando, new StudentTransactionsTempMapper(), codigoPersonal);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.StudentTransactionsTempDao|getListAll|:"+ex);
		}
		
		return lista;
	}

	public boolean vaciarTabla() {
		boolean ok = false;
		try{
			String comando = "DELETE FROM FULTON_STUDENT_TRANSACTIONS_TEMP";
			Object[] parametros = new Object[] {};
			if(enocJdbc.update(comando, parametros) >= 1){
				ok = true;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentTransactionsTempDao|estaSincronizado|:" + ex);
		}
		return ok;
	}
}