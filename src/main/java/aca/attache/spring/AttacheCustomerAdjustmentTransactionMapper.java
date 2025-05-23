package aca.attache.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AttacheCustomerAdjustmentTransactionMapper implements RowMapper<AttacheCustomerAdjustmentTransaction>{
    
    public AttacheCustomerAdjustmentTransaction mapRow(ResultSet rs, int arg1) throws SQLException {
        AttacheCustomerAdjustmentTransaction objeto = new AttacheCustomerAdjustmentTransaction();

        objeto.setCode(rs.getString("CODE"));
        objeto.setInvnum(rs.getString("INVNUM"));
        objeto.setDocnum(rs.getString("DOCNUM"));
        objeto.setRefer(rs.getString("REFER"));
        objeto.setTrantype(rs.getInt("TRANTYPE"));
        objeto.setTrandate(rs.getDate("TRANDATE"));
        objeto.setInvamt(rs.getDouble("INVAMT"));
        objeto.setTotaladjustamt(rs.getDouble("TOTALADJUSTAMT"));
        objeto.setInvdate(rs.getDate("INVDATE"));
        objeto.setAgecode(rs.getInt("AGECODE"));
        objeto.setComment(rs.getString("COMMENT_"));
        
        return objeto;
    }
}
