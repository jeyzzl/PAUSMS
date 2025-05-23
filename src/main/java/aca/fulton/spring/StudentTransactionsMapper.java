package aca.fulton.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class StudentTransactionsMapper implements RowMapper<StudentTransactions>{

	public StudentTransactions mapRow(ResultSet rs, int arg1) throws SQLException {
		StudentTransactions objeto = new StudentTransactions();
		
        objeto.setStudentId(rs.getString("STUDENT_ID"));
        objeto.setPeriod(rs.getString("PERIOD"));
        objeto.setDate(rs.getString("TRAN_DATE"));
        objeto.setType(rs.getString("TYPE"));
        objeto.setReference(rs.getString("REFERENCE"));
        objeto.setAmount(rs.getBigDecimal("AMOUNT"));
        objeto.setDc(rs.getString("DC"));
        objeto.setDescription(rs.getString("DESCRIPTION"));

		return objeto;
	}

}
