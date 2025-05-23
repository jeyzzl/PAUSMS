package aca.traspaso.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TraspasoMapper implements RowMapper<Traspaso>{
    @Override
    public Traspaso mapRow(ResultSet rs, int arg1) throws SQLException{

        Traspaso objeto = new Traspaso();

        objeto.setIdNumber(rs.getString("ID_NUMBER"));
        objeto.setCourseGrade(rs.getString("COURSE_GRADE"));
        objeto.setCourseGradeDate(rs.getString("GRADE_DATE"));
        objeto.setSubjectCode(rs.getString("SUBJECT_CODE"));
        objeto.setSubjectName(rs.getString("SUBJECT_NAME"));
        objeto.setSemester(rs.getString("SEMESTER"));
        objeto.setSemesterName(rs.getString("SEMESTER_NAME"));
        objeto.setPlanId(rs.getString("PLAN_ID"));

        return objeto;
        
    }    
}
