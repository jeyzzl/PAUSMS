package aca.attache.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AttacheCustomerInvoiceTransactionMapper implements RowMapper<AttacheCustomerInvoiceTransaction>{
    
    public AttacheCustomerInvoiceTransaction mapRow(ResultSet rs, int arg1) throws SQLException {
        AttacheCustomerInvoiceTransaction objeto = new AttacheCustomerInvoiceTransaction();

        objeto.setCode(rs.getString("CODE"));
        objeto.setInvnum(rs.getString("INVNUM"));
        objeto.setDocnum(rs.getString("DOCNUM"));
        objeto.setRefer(rs.getString("REFER"));
        objeto.setCat(rs.getString("CAT"));
        objeto.setTrantype(rs.getInt("TRANTYPE"));
        objeto.setTrandate(rs.getDate("TRANDATE"));
        objeto.setInvamt(rs.getDouble("INVAMT"));
        objeto.setAgecode(rs.getInt("AGECODE"));
        objeto.setInvbal(rs.getDouble("INVBAL"));
        objeto.setInvdate(rs.getDate("INVDATE"));
        objeto.setComment(rs.getString("COMMENT_"));

        return objeto;
    }
}
