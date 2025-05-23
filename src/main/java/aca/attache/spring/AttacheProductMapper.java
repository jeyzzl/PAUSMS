package aca.attache.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AttacheProductMapper implements RowMapper<AttacheProduct>{
    
    public AttacheProduct mapRow(ResultSet rs, int arg1) throws SQLException {
        AttacheProduct objeto = new AttacheProduct();

        objeto.setLocation(rs.getString("LOCATION"));
        objeto.setProductgroup(rs.getString("PRODUCTGROUP"));
        objeto.setCode(rs.getString("CODE"));
        objeto.setDescription(rs.getString("DESCRIPTION"));
        objeto.setGlset(rs.getString("GLSET"));
        objeto.setComment(rs.getString("COMMENT_"));
        objeto.setSalesprice1(rs.getDouble("SALESPRICE1"));
        objeto.setUnitcost(rs.getDouble("UNITCOST"));

        return objeto;
    }
}
