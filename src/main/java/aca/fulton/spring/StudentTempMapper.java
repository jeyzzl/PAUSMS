package aca.fulton.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class StudentTempMapper implements RowMapper<StudentTemp>{

	public StudentTemp mapRow(ResultSet rs, int arg1) throws SQLException {
		StudentTemp objeto = new StudentTemp();
		
		objeto.setId(rs.getString("ID"));
		objeto.setStudentCode(rs.getString("STUDENT_CODE"));	
        objeto.setName(rs.getString("NAME"));
        objeto.setEmail(rs.getString("EMAIL"));
		objeto.setSponsor(rs.getString("SPONSOR"));
        objeto.setInactive(rs.getBoolean("INACTIVE"));
		objeto.setBalance(rs.getBigDecimal("BALANCE"));	
		objeto.setDc(rs.getString("DC"));

		return objeto;
	}

}