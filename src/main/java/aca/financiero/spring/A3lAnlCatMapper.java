package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class A3lAnlCatMapper implements RowMapper<A3lAnlCat> {
	public A3lAnlCat mapRow(ResultSet rs, int arg1) throws SQLException {
		
		A3lAnlCat objeto = new A3lAnlCat();
		
		objeto.setAnlCatId(rs.getString("ANL_CAT_ID"));
		objeto.setUpdateCount(rs.getString("UPDATE_COUNT"));
		objeto.setLastChangeUserId(rs.getString("LAST_CHANGE_USER_ID"));
		objeto.setLastChangeDatetime(rs.getString("LAST_CHANGE_DATETIME"));
		objeto.setStatus(rs.getString("STATUS"));
		objeto.setLookup(rs.getString("LOOKUP"));
		objeto.setUseableAnlEntId(rs.getString("USEABLE_ANL_ENT_ID"));
		objeto.setsHead(rs.getString("S_HEAD"));
		objeto.setDescr(rs.getString("DESCR"));
		objeto.setDagCode(rs.getString("DAG_CODE"));
		objeto.setAmendCode(rs.getString("AMEND_CODE"));
		objeto.setValidateInd(rs.getString("VALIDATE_IND"));
		objeto.setLngth(rs.getString("LNGTH"));
		objeto.setLinked(rs.getString("LINKED"));
		objeto.setAnlCodeFrom(rs.getString("ANL_CODE_FROM"));
		
		return objeto;
	}
}