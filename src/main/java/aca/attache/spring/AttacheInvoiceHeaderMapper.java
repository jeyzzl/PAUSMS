package aca.attache.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AttacheInvoiceHeaderMapper implements RowMapper<AttacheInvoiceHeader>{
    
    public AttacheInvoiceHeader mapRow(ResultSet rs, int arg1) throws SQLException {
        AttacheInvoiceHeader objeto = new AttacheInvoiceHeader();

        objeto.setDoctype(rs.getInt("DOCTYPE"));
        objeto.setInternaldocnum(rs.getInt("INTERNALDOCNUM"));
        objeto.setDocdate(rs.getDate("DOCDATE"));
        objeto.setCode(rs.getString("CODE"));
        objeto.setDocnum(rs.getString("DOCNUM"));
        objeto.setCat(rs.getString("CAT"));
        objeto.setGlset(rs.getString("GLSET"));

        return objeto;
    }
}
