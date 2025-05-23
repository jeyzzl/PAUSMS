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
public class StudentTransactionsDao {

    @Autowired	
	@Qualifier("jdbcEnoc")	
	private JdbcTemplate enocJdbc;

	public boolean insertTransaction(StudentTransactions transaction) {
        boolean ok = false;
        try {
            String comando = "INSERT INTO ENOC.FULTON_STUDENT_TRANSACTIONS(STUDENT_ID, TRAN_DATE, AMOUNT, DC, DESCRIPTION, PERIOD, TYPE, REFERENCE) VALUES(?, TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), TO_NUMBER(?,'999999999999999999D999999999999999999'), ?, ?, ?, ?, ?)";
            Object[] parametros = new Object[] { transaction.getStudentId(), transaction.getDate(), transaction.getAmount().toString(), transaction.getDc(), transaction.getDescription(), transaction.getPeriod(), transaction.getType(), transaction.getReference() };
            if (enocJdbc.update(comando, parametros) == 1) {
                ok = true;
            }
        } catch (Exception ex) {
            System.out.println("Error - aca.opcion.spring.StudentTransactionsDao|insertTransaction|:" + ex);
        }
        return ok;
    }

	public boolean updateReg(StudentTransactions transaction) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.FULTON_STUDENT_TRANSACTIONS SET TRAN_DATE = TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), AMOUNT = TO_NUMBER(?,'999999999999999999D999999999999999999'), DC = ?, DESCRIPTION = ?, PERIOD = ?, TYPE = ?, REFERENCE = ? WHERE STUDENT_ID = ?";
			Object[] parametros = new Object[] { transaction.getDate(), transaction.getAmount(), transaction.getDc(), transaction.getDescription(), transaction.getPeriod(), transaction.getType(), transaction.getReference(), transaction.getStudentId() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentTransactionsDao|updateReg|:"+ex);		 
		}
		
		return ok;
	}	
	
	public boolean deleteReg(String studentId, String tranDate) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.FULTON_STUDENT_TRANSACTIONS WHERE STUDENT_ID = ? AND TRAN_DATE = TO_DATE(?,'DD/MM/YYYY HH24:MI:SS')";		
			Object[] parametros = new Object[] { studentId, tranDate };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentTransactionsDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public Student mapeaRegId(String studentId, String tranDate){
		Student opcion = new Student();
		try{ 
			String comando = "SELECT COUNT(STUDENT_ID) FROM ENOC.FULTON_STUDENT_TRANSACTIONS WHERE STUDENT_ID = ? AND TRAN_DATE = TO_DATE(?,'DD/MM/YYYY HH24:MI:SS')";
			Object[] parametros = new Object[] { studentId, tranDate};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){				
				comando = "SELECT STUDENT_ID, TRAN_DATE, AMOUNT, DC, DESCRIPTION, PERIOD, TYPE, REFERENCE FROM ENOC.FULTON_STUDENT_TRANSACTIONS WHERE STUDENT_ID = ? AND TRAN_DATE = TO_DATE(?,'DD/MM/YYYY HH24:MI:SS')";							
				opcion = enocJdbc.queryForObject(comando, new StudentMapper(), parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentTransactionsDao|mapeaRegId|:"+ex);
		}
		
		return opcion;
	}

    public List<StudentTransactions> getListAll( String codigoPersonal, String orden ) {
		
		List<StudentTransactions> lista = new ArrayList<StudentTransactions>();
		
		try{
			String comando = "SELECT "+
				" STUDENT_ID, TRAN_DATE, AMOUNT, DC, DESCRIPTION, PERIOD, TYPE, REFERENCE"+
				" FROM ENOC.FULTON_STUDENT_TRANSACTIONS "+
                " WHERE STUDENT_ID IN (SELECT ID FROM FULTON_STUDENT WHERE (LENGTH(STUDENT_CODE) = 7 AND STUDENT_CODE = ?) OR (LENGTH(STUDENT_CODE) = 6 AND '1' || STUDENT_CODE = ?))"+orden;
			lista = enocJdbc.query(comando, new StudentTransactionsMapper(), codigoPersonal, codigoPersonal);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.StudentTransactionsDao|getListAll|:"+ex);
		}
		
		return lista;
	}

	public List<StudentTransactions> getListPorFecha( String codigoPersonal, String fechaInicio, String fechaFin, String orden ) {
		
		List<StudentTransactions> lista = new ArrayList<StudentTransactions>();
		
		try{
			String comando = "SELECT "+
				" STUDENT_ID, TRAN_DATE, AMOUNT, DC, DESCRIPTION, PERIOD, TYPE, REFERENCE"+
				" FROM ENOC.FULTON_STUDENT_TRANSACTIONS "+
                " WHERE STUDENT_ID IN (SELECT ID FROM FULTON_STUDENT WHERE (LENGTH(STUDENT_CODE) = 7 AND STUDENT_CODE = ?) OR (LENGTH(STUDENT_CODE) = 6 AND '1' || STUDENT_CODE = ?))"+
				" AND TRAN_DATE BETWEEN TO_DATE(?,'YYYY/MM/DD') AND TO_DATE(?,'YYYY/MM/DD')"+orden;
			lista = enocJdbc.query(comando, new StudentTransactionsMapper(), codigoPersonal, codigoPersonal, fechaInicio, fechaFin);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.StudentTransactionsDao|getListPorFecha|:"+ex);
		}
		
		return lista;
	}

	public List<StudentTransactions> getListPorPeriodo( String codigoPersonal, String periodo, String orden ) {
		
		List<StudentTransactions> lista = new ArrayList<StudentTransactions>();
		
		try{
			String comando = "SELECT "+
				" STUDENT_ID, TRAN_DATE, AMOUNT, DC, DESCRIPTION, PERIOD, TYPE, REFERENCE"+
				" FROM ENOC.FULTON_STUDENT_TRANSACTIONS "+
                " WHERE STUDENT_ID IN (SELECT ID FROM FULTON_STUDENT WHERE (LENGTH(STUDENT_CODE) = 7 AND STUDENT_CODE = ?) OR (LENGTH(STUDENT_CODE) = 6 AND '1' || STUDENT_CODE = ?))"+
				" AND PERIOD = TRIM(?) "+orden;
			lista = enocJdbc.query(comando, new StudentTransactionsMapper(), codigoPersonal, codigoPersonal, periodo);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.StudentTransactionsDao|getListPorFecha|:"+ex);
		}
		
		return lista;
	}

	public List<String> getListPeriodosAlumno( String codigoPersonal, String orden ) {
		
		List<String> lista = new ArrayList<String>();
		
		try{
			String comando = "SELECT "+
				" DISTINCT(PERIOD)"+
				" FROM ENOC.FULTON_STUDENT_TRANSACTIONS "+
                " WHERE STUDENT_ID IN (SELECT ID FROM FULTON_STUDENT WHERE (LENGTH(STUDENT_CODE) = 7 AND STUDENT_CODE = ?) OR (LENGTH(STUDENT_CODE) = 6 AND '1' || STUDENT_CODE = ?))"+orden;
			lista = enocJdbc.queryForList(comando, String.class, codigoPersonal, codigoPersonal);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.StudentTransactionsDao|getListPeriodosAlumno|:"+ex);
		}
		
		return lista;
	}

	public boolean vaciarTabla() {
		boolean ok = false;
		try{
			String comando = "DELETE FROM FULTON_STUDENT_TRANSACTIONS";
			Object[] parametros = new Object[] {};
			if(enocJdbc.update(comando, parametros) >= 1){
				ok = true;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentTransactionsDao|vaciarTabla|:" + ex);
		}
		return ok;
	}

	public boolean copiarTabla() {
		boolean ok = false;
		try{
			String comando = "INSERT INTO FULTON_STUDENT_TRANSACTIONS SELECT * FROM FULTON_STUDENT_TRANSACTIONS_TEMP";
			Object[] parametros = new Object[] {};
			if(enocJdbc.update(comando, parametros) >= 1){
				ok = true;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentTransactionsDao|copiarTabla|:" + ex);
		}
		return ok;
	}

	public String getPreviousBalanceByPeriod(String codigoPersonal, String period) {
		String balance			= "0";		
		try{
			String comando = "SELECT" +
							" SUM(CASE" +
							" WHEN DC = 'DR' THEN AMOUNT" +
							" WHEN DC = 'CR' THEN -AMOUNT" +
							" ELSE 0" +
							" END) AS IB" +
							" FROM ENOC.FULTON_STUDENT_TRANSACTIONS" +
							" WHERE STUDENT_ID IN (SELECT ID FROM FULTON_STUDENT WHERE (LENGTH(STUDENT_CODE) = 7 AND STUDENT_CODE = ?) OR (LENGTH(STUDENT_CODE) = 6 AND '1' || STUDENT_CODE = ?))" +
							" AND TO_DATE(PERIOD, 'YYYY/MM') < TO_DATE(TRIM(?), 'YYYY/MM')";	
			balance = enocJdbc.queryForObject(comando, String.class, codigoPersonal, codigoPersonal, period);			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentTransactionsDao|getPreviousBalanceByPeriod|:"+ex);
		}		
		return balance;
	}

	public String getPreviousBalanceByDate(String codigoPersonal, String fechaInico) {
		String balance			= "0";		
		try{
			String comando = "SELECT" +
							" SUM(CASE" +
							" WHEN DC = 'DR' THEN AMOUNT" +
							" WHEN DC = 'CR' THEN -AMOUNT" +
							" ELSE 0" +
							" END) AS IB" +
							" FROM ENOC.FULTON_STUDENT_TRANSACTIONS" +
							" WHERE STUDENT_ID IN (SELECT ID FROM FULTON_STUDENT WHERE (LENGTH(STUDENT_CODE) = 7 AND STUDENT_CODE = ?) OR (LENGTH(STUDENT_CODE) = 6 AND '1' || STUDENT_CODE = ?))" +
							" AND TRAN_DATE < TO_DATE(?, 'YYYY/MM/DD')";	
			balance = enocJdbc.queryForObject(comando, String.class, codigoPersonal, codigoPersonal, fechaInico);			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentTransactionsDao|getPreviousBalanceByDate|:"+ex);
		}		
		return balance;
	}
}