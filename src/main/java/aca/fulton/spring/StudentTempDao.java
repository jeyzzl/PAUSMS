package aca.fulton.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class StudentTempDao {

    @Autowired	
	@Qualifier("jdbcEnoc")	
	private JdbcTemplate enocJdbc;

    public boolean insertReg(StudentTemp student){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.FULTON_STUDENT_TEMP(ID, STUDENT_CODE, NAME, EMAIL, SPONSOR, INACTIVE, BALANCE, DC, SYNC) VALUES(?, ?, ?, ?, ?, TO_NUMBER(?, '9'), ?, ?, TO_NUMBER(?, '9'))";
			Object[] parametros = new Object[] { student.getId(), student.getStudentCode(), student.getName(), student.getEmail(), student.getSponsor(), student.isInactive()?"1":"0", student.getBalance(), student.getDc(), student.isSync()?"1":"0"};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentTempDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(StudentTemp student) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.FULTON_STUDENT_TEMP SET STUDENT_CODE = ?, NAME = ?, EMAIL = ?, SPONSOR = ?, INACTIVE = TO_NUMBER(?, '9'), BALANCE = ?, DC = ?, SYNC = TO_NUMBER(?,'9') WHERE ID = ?";
			Object[] parametros = new Object[] { student.getStudentCode(), student.getName(), student.getEmail(), student.getSponsor(), student.isInactive()?"1":"0", student.getBalance(), student.getDc(), student.isSync()?"1":"0", student.getId() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentTempDao|updateReg|:"+ex);		 
		}
		
		return ok;
	}	
	
	public boolean deleteReg(String id) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.FULTON_STUDENT_TEMP WHERE ID = ?";		
			Object[] parametros = new Object[] { id };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentTempDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public StudentTemp mapeaRegId(String id){
		StudentTemp opcion = new StudentTemp();
		try{ 
			String comando = "SELECT COUNT(ID) FROM ENOC.FULTON_STUDENT_TEMP WHERE ID = ?";
			Object[] parametros = new Object[] { id };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){				
				comando = "SELECT ID, STUDENT_CODE, NAME, EMAIL, SPONSOR, INACTIVE, BALANCE, DC, SYNC FROM ENOC.FULTON_STUDENT_TEMP WHERE ID = ?";							
				opcion = enocJdbc.queryForObject(comando, new StudentTempMapper(), parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentTempDao|mapeaRegId|:"+ex);
		}
		
		return opcion;
	}

	public StudentTemp mapeaRegIdMatricula(String matricula){
		StudentTemp opcion = new StudentTemp();
		try{ 
			String comando = "SELECT COUNT(ID) FROM ENOC.FULTON_STUDENT_TEMP WHERE '1'||STUDENT_CODE = ?";
			Object[] parametros = new Object[] { matricula };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){				
				comando = "SELECT ID, STUDENT_CODE, NAME, EMAIL, SPONSOR, INACTIVE, BALANCE, DC, SYNC FROM ENOC.FULTON_STUDENT_TEMP WHERE '1'||STUDENT_CODE = ?";							
				opcion = enocJdbc.queryForObject(comando, new StudentTempMapper(), parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentTempDao|mapeaRegId|:"+ex);
		}
		
		return opcion;
	}
	
	public boolean existeReg(String id){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.FULTON_STUDENT_TEMP WHERE ID = ?";
			Object[] parametros = new Object[] { id };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){											
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentTempDao|existeReg|:"+ex);
		}
		
		return ok;
	}

	public boolean existeRegMatricula(String matricula){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.FULTON_STUDENT_TEMP WHERE '1'||STUDENT_CODE = ?";
			Object[] parametros = new Object[] { matricula };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){											
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentTempDao|existeRegMatricula|:"+ex);
		}
		
		return ok;
	}
    
	public boolean insertTransaction(String studentId, StudentTransactionsTemp transaction) {
        boolean ok = false;
        try {
            String comando = "INSERT INTO ENOC.FULTON_STUDENT_TRANSACTIONS_TEMP(STUDENT_ID, TRAN_DATE, AMOUNT, DC, DESCRIPTION, PERIOD, TYPE, REFERENCE) VALUES(?, TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), TO_NUMBER(?,'999999999999999999D999999999999999999'), ?, ?, ?, ?, ?)";
            Object[] parametros = new Object[] { studentId, transaction.getDate(), transaction.getAmount().toString(), transaction.getDc(), transaction.getDescription(), transaction.getPeriod(), transaction.getType(), transaction.getReference() };
            if (enocJdbc.update(comando, parametros) == 1) {
                ok = true;
            }
        } catch (Exception ex) {
            System.out.println("Error - aca.opcion.spring.StudentTempDao|insertTransaction|:" + ex);
        }
        return ok;
    }

	public boolean estaSincronizado(String studentCode){
		 boolean ok = false;
		 try{
			String comando = "SELECT * FROM FULTON_STUDENT_TEMP WHERE STUDENT_CODE = ? AND '1' || STUDENT_CODE IN (SELECT CODIGO_PERSONAL FROM ALUM_PERSONAL)";
			Object[] parametros = new Object[] {studentCode};
			if(enocJdbc.update(comando, parametros) == 1){
				ok = true;
			}
		 }catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentTempDao|estaSincronizado|:" + ex);
		 }
		 return ok;
	}

	public boolean vaciarTabla() {
		boolean ok = false;
		try{
			String comando = "DELETE FROM FULTON_STUDENT_TEMP";
			if(enocJdbc.update(comando) >= 1){
				ok = true;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentTempDao|estaSincronizado|:" + ex);
		}
		return ok;
	}

}
