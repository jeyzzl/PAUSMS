package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class A3lAnlCodeMapper implements RowMapper<A3lAnlCode> {
	public A3lAnlCode mapRow(ResultSet rs, int arg1) throws SQLException {
		
		A3lAnlCode objeto = new A3lAnlCode();
		
		objeto.setAnlCatId(rs.getString("ANL_CAT_ID"));
		objeto.setAnlCode(rs.getString("ANL_CODE"));
		objeto.setUpdateCount(rs.getString("UPDATE_COUNT"));
		objeto.setLastChangeUserId(rs.getString("LAST_CHANGE_USER_ID"));
		objeto.setLastChangeDateTime(rs.getString("LAST_CHANGE_DATETIME"));
		objeto.setStatus(rs.getString("STATUS"));
		objeto.setLookup(rs.getString("LOOKUP"));
		objeto.setName(rs.getString("NAME"));
		objeto.setDagCode(rs.getString("DAG_CODE"));
		objeto.setBdgtCheck(rs.getString("BDGT_CHECK"));
		objeto.setBdgtStop(rs.getString("BDGT_STOP"));
		objeto.setProhibitPosting(rs.getString("PROHIBIT_POSTING"));
		objeto.setNavigationOption(rs.getString("NAVIGATION_OPTION"));
		objeto.setCombinedBdgtChck(rs.getString("COMBINED_BGDT_CHECK"));
		
		return objeto;
	}
}
