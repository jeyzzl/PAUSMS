package aca.attache.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AttacheCustomerMapper implements RowMapper<AttacheCustomer>{
    
    public AttacheCustomer mapRow(ResultSet rs, int arg1) throws SQLException {
        AttacheCustomer objeto = new AttacheCustomer();

        objeto.setCode(rs.getString("CODE"));
        objeto.setName(rs.getString("NAME"));
        objeto.setAcctype(rs.getString("ACCTYPE"));
        objeto.setGlset(rs.getString("GLSET"));
        objeto.setCat(rs.getString("CAT"));
        objeto.setOpenbal(rs.getDouble("OPENBAL"));
        objeto.setCurrentbal(rs.getDouble("CURRENTBAL"));
        objeto.setPeriod1bal(rs.getDouble("PERIOD1BAL"));
        objeto.setPeriod2bal(rs.getDouble("PERIOD2BAL"));
        objeto.setPeriod3bal(rs.getDouble("PERIOD3BAL"));
        objeto.setUnallocbal(rs.getDouble("UNALLOCBAL"));
        objeto.setPostdatebal(rs.getDouble("POSTDATEBAL"));
        objeto.setSort(rs.getString("SORT"));
        objeto.setDiscperc(rs.getDouble("DISCPERC"));

        return objeto;
    }
}
