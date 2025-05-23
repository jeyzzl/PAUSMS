package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class A3lAsalfldgMapper implements RowMapper<A3lAsalfldg> {

	public A3lAsalfldg mapRow(ResultSet rs, int arg1) throws SQLException {
		A3lAsalfldg objeto = new A3lAsalfldg();
		
		objeto.setAccntCode(rs.getString("ACCNT_CODE"));
		objeto.setPeriod(rs.getString("PERIOD"));		
		objeto.setTransDatetime(rs.getString("TRANS_DATETIME"));		
		objeto.setJrnalNo(rs.getString("JRNAL_NO"));
		objeto.setJrnalLine(rs.getString("JRNAL_LINE"));
		objeto.setAmount(rs.getString("AMOUNT"));
		objeto.setdC(rs.getString("D_C"));		
		objeto.setAllocation(rs.getString("ALLOCATION"));
		objeto.setJrnalType(rs.getString("JRNAL_TYPE"));
		objeto.setJrnalSrce(rs.getString("JRNAL_SRCE"));
		objeto.setTreference(rs.getString("TREFERENCE"));
		objeto.setDescriptn(rs.getString("DESCRIPTN"));
		objeto.setDeuDatetime(rs.getString("DUE_DATETIME"));
		objeto.setAssetInd(rs.getString("ASSET_IND"));
		objeto.setAssetCode(rs.getString("ASSET_CODE"));		
		objeto.setAnalT0(rs.getString("ANAL_T0"));
		objeto.setAnalT1(rs.getString("ANAL_T1"));
		objeto.setAnalT2(rs.getString("ANAL_T2"));
		objeto.setAnalT3(rs.getString("ANAL_T3"));
		objeto.setAnalT4(rs.getString("ANAL_T4"));
		objeto.setAnalT5(rs.getString("ANAL_T5"));		
		objeto.setAnalT6(rs.getString("ANAL_T6"));
		objeto.setAnalT7(rs.getString("ANAL_T7"));
		objeto.setAnalT8(rs.getString("ANAL_T8"));
		objeto.setAnalT9(rs.getString("ANAL_T9"));		
		objeto.setPostingDatetime(rs.getString("POSTING_DATETIME"));
		objeto.setAllocInProgress(rs.getString("ALLOC_IN_PROGRESS"));
		objeto.setVchrNum(rs.getString("VCHR_NUM"));
		objeto.setOriginatedDatetime(rs.getString("ORIGINATED_DATETIME"));

		return objeto;
	}

}
