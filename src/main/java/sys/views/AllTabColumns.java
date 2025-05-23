/**
 * 
 */
package sys.views;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sys.general.Config;

/**
 * @author Elifo
 *
 */
public class AllTabColumns {
	private String owner;
	private String tableName;
	private String columnName;
	private String dataType;
	private String dataTypeMod;
	private String dataTypeOwner;
	private String dataLength;
	private String dataPrecision;
	private String dataScale;
	private String nullable;
	private String columnId;
	private String defaultLength;
	private String dataDefault;
	private String numDistinct;
	private String lowValue;
	private String highValue;
	private String density;
	private String numNulls;
	private String numBuckets;
	private String lastAnalyzed;
	private String sampleSize;
	private String characterSetName;
	private String charColDeclLength;
	private String globalStats;
	private String userStats;
	private String avgColLen;
	private String charLength;
	private String charUsed;
	private String v80FmtImage;
	private String dataUpgraded;
	private String histogram;
	
	public AllTabColumns(){
		owner				= "";
		tableName			= "";
		columnName			= "";
		dataType			= "";
		dataTypeMod			= "";
		dataTypeOwner		= "";
		dataLength			= "";
		dataPrecision		= "";
		dataScale			= "";
		nullable			= "";
		columnId			= "";
		defaultLength		= "";
		dataDefault			= "";
		numDistinct			= "";
		lowValue			= "";
		highValue			= "";
		density				= "";
		numNulls			= "";
		numBuckets			= "";
		lastAnalyzed		= "";
		sampleSize			= "";
		characterSetName		= "";
		charColDeclLength	= "";
		globalStats		= "";
		userStats			= "";
		avgColLen			= "";
		charLength			= "";
		charUsed			= "";
		v80FmtImage			= "";
		dataUpgraded		= "";
		histogram			= "";
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return the dataTypeMod
	 */
	public String getDataTypeMod() {
		return dataTypeMod;
	}

	/**
	 * @param dataTypeMod the dataTypeMod to set
	 */
	public void setDataTypeMod(String dataTypeMod) {
		this.dataTypeMod = dataTypeMod;
	}

	/**
	 * @return the dataTypeOwner
	 */
	public String getDataTypeOwner() {
		return dataTypeOwner;
	}

	/**
	 * @param dataTypeOwner the dataTypeOwner to set
	 */
	public void setDataTypeOwner(String dataTypeOwner) {
		this.dataTypeOwner = dataTypeOwner;
	}

	/**
	 * @return the dataLength
	 */
	public String getDataLength() {
		return dataLength;
	}

	/**
	 * @param dataLength the dataLength to set
	 */
	public void setDataLength(String dataLength) {
		this.dataLength = dataLength;
	}

	/**
	 * @return the dataPrecision
	 */
	public String getDataPrecision() {
		return dataPrecision;
	}

	/**
	 * @param dataPrecision the dataPrecision to set
	 */
	public void setDataPrecision(String dataPrecision) {
		this.dataPrecision = dataPrecision;
	}

	/**
	 * @return the dataScale
	 */
	public String getDataScale() {
		return dataScale;
	}

	/**
	 * @param dataScale the dataScale to set
	 */
	public void setDataScale(String dataScale) {
		this.dataScale = dataScale;
	}

	/**
	 * @return the nullable
	 */
	public String getNullable() {
		return nullable;
	}

	/**
	 * @param nullable the nullable to set
	 */
	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	/**
	 * @return the columnId
	 */
	public String getColumnId() {
		return columnId;
	}

	/**
	 * @param columnId the columnId to set
	 */
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	/**
	 * @return the defaultLength
	 */
	public String getDefaultLength() {
		return defaultLength;
	}

	/**
	 * @param defaultLength the defaultLength to set
	 */
	public void setDefaultLength(String defaultLength) {
		this.defaultLength = defaultLength;
	}

	/**
	 * @return the dataDefault
	 */
	public String getDataDefault() {
		return dataDefault;
	}

	/**
	 * @param dataDefault the dataDefault to set
	 */
	public void setDataDefault(String dataDefault) {
		this.dataDefault = dataDefault;
	}

	/**
	 * @return the numDistinct
	 */
	public String getNumDistinct() {
		return numDistinct;
	}

	/**
	 * @param numDistinct the numDistinct to set
	 */
	public void setNumDistinct(String numDistinct) {
		this.numDistinct = numDistinct;
	}

	/**
	 * @return the lowValue
	 */
	public String getLowValue() {
		return lowValue;
	}

	/**
	 * @param lowValue the lowValue to set
	 */
	public void setLowValue(String lowValue) {
		this.lowValue = lowValue;
	}

	/**
	 * @return the highValue
	 */
	public String getHighValue() {
		return highValue;
	}

	/**
	 * @param highValue the highValue to set
	 */
	public void setHighValue(String highValue) {
		this.highValue = highValue;
	}

	/**
	 * @return the density
	 */
	public String getDensity() {
		return density;
	}

	/**
	 * @param density the density to set
	 */
	public void setDensity(String density) {
		this.density = density;
	}

	/**
	 * @return the numNulls
	 */
	public String getNumNulls() {
		return numNulls;
	}

	/**
	 * @param numNulls the numNulls to set
	 */
	public void setNumNulls(String numNulls) {
		this.numNulls = numNulls;
	}

	/**
	 * @return the numBuckets
	 */
	public String getNumBuckets() {
		return numBuckets;
	}

	/**
	 * @param numBuckets the numBuckets to set
	 */
	public void setNumBuckets(String numBuckets) {
		this.numBuckets = numBuckets;
	}

	/**
	 * @return the lastAnalyzed
	 */
	public String getLastAnalyzed() {
		return lastAnalyzed;
	}

	/**
	 * @param lastAnalyzed the lastAnalyzed to set
	 */
	public void setLastAnalyzed(String lastAnalyzed) {
		this.lastAnalyzed = lastAnalyzed;
	}

	/**
	 * @return the sampleSize
	 */
	public String getSampleSize() {
		return sampleSize;
	}

	/**
	 * @param sampleSize the sampleSize to set
	 */
	public void setSampleSize(String sampleSize) {
		this.sampleSize = sampleSize;
	}

	/**
	 * @return the characterSetName
	 */
	public String getCharacterSetName() {
		return characterSetName;
	}

	/**
	 * @param characterSetName the characterSetName to set
	 */
	public void setCharacterSetName(String characterSetName) {
		this.characterSetName = characterSetName;
	}

	/**
	 * @return the charColDeclLength
	 */
	public String getCharColDeclLength() {
		return charColDeclLength;
	}

	/**
	 * @param charColDeclLength the charColDeclLength to set
	 */
	public void setCharColDeclLength(String charColDeclLength) {
		this.charColDeclLength = charColDeclLength;
	}

	/**
	 * @return the globalStats
	 */
	public String getGlobalStats() {
		return globalStats;
	}

	/**
	 * @param globalStats the globalStats to set
	 */
	public void setGlobalStats(String globalStats) {
		this.globalStats = globalStats;
	}

	/**
	 * @return the userStats
	 */
	public String getUserStats() {
		return userStats;
	}

	/**
	 * @param userStats the userStats to set
	 */
	public void setUserStats(String userStats) {
		this.userStats = userStats;
	}

	/**
	 * @return the avgColLen
	 */
	public String getAvgColLen() {
		return avgColLen;
	}

	/**
	 * @param avgColLen the avgColLen to set
	 */
	public void setAvgColLen(String avgColLen) {
		this.avgColLen = avgColLen;
	}

	/**
	 * @return the charLength
	 */
	public String getCharLength() {
		return charLength;
	}

	/**
	 * @param charLength the charLength to set
	 */
	public void setCharLength(String charLength) {
		this.charLength = charLength;
	}

	/**
	 * @return the charUsed
	 */
	public String getCharUsed() {
		return charUsed;
	}

	/**
	 * @param charUsed the charUsed to set
	 */
	public void setCharUsed(String charUsed) {
		this.charUsed = charUsed;
	}

	/**
	 * @return the v80FmtImage
	 */
	public String getV80FmtImage() {
		return v80FmtImage;
	}

	/**
	 * @param fmtImg the v80FmtImage to set
	 */
	public void setV80FmtImage(String fmtImg) {
		v80FmtImage = fmtImg;
	}

	/**
	 * @return the dataUpgraded
	 */
	public String getDataUpgraded() {
		return dataUpgraded;
	}

	/**
	 * @param dataUpgraded the dataUpgraded to set
	 */
	public void setDataUpgraded(String dataUpgraded) {
		this.dataUpgraded = dataUpgraded;
	}

	/**
	 * @return the histogram
	 */
	public String getHistogram() {
		return histogram;
	}

	/**
	 * @param histogram the histogram to set
	 */
	public void setHistogram(String histogram) {
		this.histogram = histogram;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		owner				= rs.getString("OWNER");
		tableName			= rs.getString("TABLE_NAME");
		columnName			= rs.getString("COLUMN_NAME");
		dataType			= rs.getString("DATA_TYPE");
		dataTypeMod			= rs.getString("DATA_TYPE_MOD");
		dataTypeOwner		= rs.getString("DATA_TYPE_OWNER");
		dataLength			= rs.getString("DATA_LENGTH");
		dataPrecision		= rs.getString("DATA_PRECISION");
		dataScale			= rs.getString("DATA_SCALE")==null?"0":rs.getString("DATA_SCALE");
		nullable			= rs.getString("NULLABLE");
		columnId			= rs.getString("COLUMN_ID");
		defaultLength		= rs.getString("DEFAULT_LENGTH");
		dataDefault			= rs.getString("DATA_DEFAULT");
		numDistinct			= rs.getString("NUM_DISTINCT");
		lowValue			= rs.getString("LOW_VALUE");
		highValue			= rs.getString("HIGH_VALUE");
		density				= rs.getString("DENSITY");
		numNulls			= rs.getString("NUM_NULLS");
		numBuckets			= rs.getString("NUM_BUCKETS");
		lastAnalyzed		= rs.getString("LAST_ANALYZED");
		sampleSize			= rs.getString("SAMPLE_SIZE");
		characterSetName	= rs.getString("CHARACTER_SET_NAME");
		charColDeclLength	= rs.getString("CHAR_COL_DECL_LENGTH");
		globalStats			= rs.getString("GLOBAL_STATS");
		userStats			= rs.getString("USER_STATS");
		avgColLen			= rs.getString("AVG_COL_LEN");
		charLength			= rs.getString("CHAR_LENGTH");
		charUsed			= rs.getString("CHAR_USED");
		v80FmtImage			= rs.getString("V80_FMT_IMAGE");
		dataUpgraded		= rs.getString("DATA_UPGRADED");
		histogram			= rs.getString("HISTOGRAM");
 	}
 	
 	public void mapeaRegId( Connection conn, String condiciones) throws SQLException, IOException{
 		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement("SELECT"+
	 			" OWNER, TABLE_NAME, COLUMN_NAME, DATA_TYPE, DATA_TYPE_MOD, DATA_TYPE_OWNER, DATA_LENGTH, DATA_PRECISION," +
	 			" DATA_SCALE, NULLABLE, COLUMN_ID, DEFAULT_LENGTH, DATA_DEFAULT, NUM_DISTINCT, LOW_VALUE, HIGH_VALUE," +
	 			" DENSITY, NUM_NULLS, NUM_BUCKETS, LAST_ANALYZED, SAMPLE_SIZE, CHARACTER_SET_NAME, CHAR_COL_DECL_LENGTH," +
	 			" GLOBAL_STATS, USER_STATS, AVG_COL_LEN, CHAR_LENGTH, CHAR_USED, V80_FMT_IMAGE, DATA_UPGRADED, HISTOGRAM"+
	 			" FROM SYS.ALL_TAB_COLUMNS WHERE OWNER = '"+Config.OWNER+"' "+condiciones);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - sys.views.AllTabColumns|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 	}
}