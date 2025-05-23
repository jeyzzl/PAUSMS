package aca.attache.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AttacheInvoiceDetailProductMapper implements RowMapper<AttacheInvoiceDetailProduct>{
    
    public AttacheInvoiceDetailProduct mapRow(ResultSet rs, int arg1) throws SQLException {
        AttacheInvoiceDetailProduct objeto = new AttacheInvoiceDetailProduct();

        objeto.setDoctype(rs.getInt("DOCTYPE"));
        objeto.setInternaldocnum(rs.getInt("INTERNALDOCNUM"));
        objeto.setStatus(rs.getInt("STATUS"));
        objeto.setPriceamt(rs.getDouble("PRICEAMT"));
        objeto.setUnitcost(rs.getDouble("UNITCOST"));
        objeto.setCode(rs.getString("CODE"));
        objeto.setDescription(rs.getString("DESCRIPTION"));
        objeto.setCat(rs.getString("CAT"));
        objeto.setGlset(rs.getString("GLSET"));
        objeto.setDetailnum(rs.getInt("DETAILNUM"));

        return objeto;
    }
}
