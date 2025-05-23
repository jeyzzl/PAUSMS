package aca.fulton.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class StudentDao {

    @Autowired	
	@Qualifier("jdbcEnoc")	
	private JdbcTemplate enocJdbc;

    public boolean insertReg(Student student){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.FULTON_STUDENT(ID, STUDENT_CODE, NAME, EMAIL, SPONSOR, INACTIVE, BALANCE, DC, SYNC) VALUES(?, ?, ?, ?, ?, TO_NUMBER(?, '9'), ?, ?, TO_NUMBER(?, '9'))";
			Object[] parametros = new Object[] { student.getId(), student.getStudentCode(), student.getName(), student.getEmail(), student.getSponsor(), student.isInactive()?"1":"0", student.getBalance(), student.getDc(), student.isSync()?"1":"0"};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(Student student) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.FULTON_STUDENT SET STUDENT_CODE = ?, NAME = ?, EMAIL = ?, SPONSOR = ?, INACTIVE = TO_NUMBER(?, '9'), BALANCE = ?, DC = ?, SYNC = TO_NUMBER(?,'9') WHERE ID = ?";
			Object[] parametros = new Object[] { student.getStudentCode(), student.getName(), student.getEmail(), student.getSponsor(), student.isInactive()?"1":"0", student.getBalance(), student.getDc(), student.isSync()?"1":"0", student.getId() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentDao|updateReg|:"+ex);		 
		}
		
		return ok;
	}	
	
	public boolean deleteReg(String id) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.FULTON_STUDENT WHERE ID = ?";		
			Object[] parametros = new Object[] { id };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public Student mapeaRegId(String id){
		Student opcion = new Student();
		try{ 
			String comando = "SELECT COUNT(ID) FROM ENOC.FULTON_STUDENT WHERE ID = ?";
			Object[] parametros = new Object[] { id };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){				
				comando = "SELECT ID, STUDENT_CODE, NAME, EMAIL, SPONSOR, INACTIVE, BALANCE, DC, SYNC FROM ENOC.FULTON_STUDENT WHERE ID = ?";							
				opcion = enocJdbc.queryForObject(comando, new StudentMapper(), parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentDao|mapeaRegId|:"+ex);
		}
		
		return opcion;
	}

	public Student mapeaRegIdMatricula(String matricula){
		Student opcion = new Student();
		try{ 
			String comando = "SELECT COUNT(*) FROM ENOC.FULTON_STUDENT WHERE (LENGTH(STUDENT_CODE) = 7 AND STUDENT_CODE = ?) OR (LENGTH(STUDENT_CODE) = 6 AND '1' || STUDENT_CODE = ? AND NOT EXISTS (SELECT 1 FROM ENOC.FULTON_STUDENT WHERE STUDENT_CODE = ?))";
			Object[] parametros = new Object[] { matricula, matricula, matricula };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){				
				comando = "SELECT ID, STUDENT_CODE, NAME, EMAIL, SPONSOR, INACTIVE, BALANCE, DC, SYNC FROM ENOC.FULTON_STUDENT WHERE (LENGTH(STUDENT_CODE) = 7 AND STUDENT_CODE = ?) OR (LENGTH(STUDENT_CODE) = 6 AND '1' || STUDENT_CODE = ? AND NOT EXISTS (SELECT 1 FROM ENOC.FULTON_STUDENT WHERE STUDENT_CODE = ?))";							
				opcion = enocJdbc.queryForObject(comando, new StudentMapper(), parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentDao|mapeaRegId|:"+ex);
		}
		
		return opcion;
	}
	
	public boolean existeReg(String id){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.FULTON_STUDENT WHERE ID = ?";
			Object[] parametros = new Object[] { id };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){											
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentDao|existeReg|:"+ex);
		}
		
		return ok;
	}

	public boolean existeRegMatricula(String matricula){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.FULTON_STUDENT WHERE (LENGTH(STUDENT_CODE) = 7 AND STUDENT_CODE = ?) OR (LENGTH(STUDENT_CODE) = 6 AND '1' || STUDENT_CODE = ?)";
			Object[] parametros = new Object[] { matricula, matricula };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){											
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentDao|existeRegMatricula|:"+ex);
		}
		
		return ok;
	}
    
	public boolean insertTransaction(String studentId, StudentTransactions transaction) {
        boolean ok = false;
        try {
            String comando = "INSERT INTO ENOC.FULTON_STUDENT_TRANSACTIONS(STUDENT_ID, TRAN_DATE, AMOUNT, DC, DESCRIPTION, PERIOD, TYPE, REFERENCE) VALUES(?, TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), TO_NUMBER(?,'999999999999999999D999999999999999999'), ?, ?, ?, ?, ?)";
            Object[] parametros = new Object[] { studentId, transaction.getDate(), transaction.getAmount().toString(), transaction.getDc(), transaction.getDescription(), transaction.getPeriod(), transaction.getType(), transaction.getReference() };
            if (enocJdbc.update(comando, parametros) == 1) {
                ok = true;
            }
        } catch (Exception ex) {
            System.out.println("Error - aca.opcion.spring.StudentDao|insertTransaction|:" + ex);
        }
        return ok;
    }

	public boolean estaSincronizado(String studentCode){
		 boolean ok = false;
		 try{
			String comando = "SELECT * FROM FULTON_STUDENT"
			+ " WHERE STUDENT_CODE = ?"
			+ " AND ((LENGTH(STUDENT_CODE) = 7 AND STUDENT_CODE IN (SELECT CODIGO_PERSONAL FROM ALUM_PERSONAL))"
			+ " OR (LENGTH(STUDENT_CODE) = 6 AND '1' || STUDENT_CODE IN (SELECT CODIGO_PERSONAL FROM ALUM_PERSONAL)))";
			Object[] parametros = new Object[] {studentCode};
			if(enocJdbc.update(comando, parametros) == 1){
				ok = true;
			}

		 }catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentDao|estaSincronizado|:" + ex);
		 }
		 return ok;
	}

	public boolean vaciarTabla() {
		boolean ok = false;
		try{
			String comando = "DELETE FROM FULTON_STUDENT";
			Object[] parametros = new Object[] {};
			if(enocJdbc.update(comando, parametros) >= 1){
				ok = true;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentDao|vaciarTabla|:" + ex);
		}
		return ok;
	}

	public boolean copiarTabla() {
		boolean ok = false;
		try{
			String comando = "INSERT INTO FULTON_STUDENT SELECT * FROM FULTON_STUDENT_TEMP";
			Object[] parametros = new Object[] {};
			if(enocJdbc.update(comando, parametros) >= 1){
				ok = true;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.opcion.spring.StudentDao|copiarTabla|:" + ex);
		}
		return ok;
	}

}
