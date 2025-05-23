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
public class AllAllTables {
	private String owner;
	private String tableName;
	private String tablespaceName;
	private String clusterName;
	private String iotName;
	private String status;
	private String pctFree;
	private String pctUsed;
	private String iniTrans;
	private String maxTrans;
	private String initialExtent;
	private String nextExtent;
	private String minExtents;
	private String maxExtents;
	private String pctIncrease;
	private String freelists;
	private String freelistGroups;
	private String logging;
	private String backedUp;
	private String numRows;
	private String blocks;
	private String emptyBlocks;
	private String avgSpace;
	private String chainCnt;
	private String avgRowLen;
	private String avgSpaceFreelistBlocks;
	private String numFreelistBlocks;
	private String degree;
	private String instances;
	private String cache;
	private String tableLock;
	private String sampleSize;
	private String lastAnalyzed;
	private String partitioned;
	private String iotType;
	private String objectIdType;
	private String tableTypeOwner;
	private String tableType;
	private String temporary;
	private String secondary;
	private String nested;
	private String bufferPool;
	private String rowMovement;
	private String globalStats;
	private String userStats;
	private String duration;
	private String skipCorrupt;
	private String monitoring;
	private String clusterOwner;
	private String dependencies;
	private String compression;
	private String dropped;
	
	public AllAllTables(){
		owner						= "";
		tableName					= "";
		tablespaceName				= "";
		clusterName					= "";
		iotName						= "";
		status						= "";
		pctFree						= "";
		pctUsed						= "";
		iniTrans					= "";
		maxTrans					= "";
		initialExtent				= "";
		nextExtent					= "";
		minExtents					= "";
		maxExtents					= "";
		pctIncrease					= "";
		freelists					= "";
		freelistGroups				= "";
		logging						= "";
		backedUp					= "";
		numRows						= "";
		blocks						= "";
		emptyBlocks					= "";
		avgSpace					= "";
		chainCnt					= "";
		avgRowLen					= "";
		avgSpaceFreelistBlocks		= "";
		numFreelistBlocks			= "";
		degree						= "";
		instances					= "";
		cache						= "";
		tableLock					= "";
		sampleSize					= "";
		lastAnalyzed				= "";
		partitioned					= "";
		iotType						= "";
		objectIdType				= "";
		tableTypeOwner				= "";
		tableType					= "";
		temporary					= "";
		secondary					= "";
		nested						= "";
		bufferPool					= "";
		rowMovement					= "";
		globalStats					= "";
		userStats					= "";
		duration					= "";
		skipCorrupt					= "";
		monitoring					= "";
		clusterOwner				= "";
		dependencies				= "";
		compression					= "";
		dropped					= "";
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @return the tablespaceName
	 */
	public String getTablespaceName() {
		return tablespaceName;
	}

	/**
	 * @return the clusterName
	 */
	public String getClusterName() {
		return clusterName;
	}

	/**
	 * @return the iotName
	 */
	public String getIotName() {
		return iotName;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the pctFree
	 */
	public String getPctFree() {
		return pctFree;
	}

	/**
	 * @return the pctUsed
	 */
	public String getPctUsed() {
		return pctUsed;
	}

	/**
	 * @return the iniTrans
	 */
	public String getIniTrans() {
		return iniTrans;
	}

	/**
	 * @return the maxTrans
	 */
	public String getMaxTrans() {
		return maxTrans;
	}

	/**
	 * @return the initialExtent
	 */
	public String getInitialExtent() {
		return initialExtent;
	}

	/**
	 * @return the nextExtent
	 */
	public String getNextExtent() {
		return nextExtent;
	}

	/**
	 * @return the minExtents
	 */
	public String getMinExtents() {
		return minExtents;
	}

	/**
	 * @return the maxExtents
	 */
	public String getMaxExtents() {
		return maxExtents;
	}

	/**
	 * @return the pctIncrease
	 */
	public String getPctIncrease() {
		return pctIncrease;
	}

	/**
	 * @return the freelists
	 */
	public String getFreelists() {
		return freelists;
	}

	/**
	 * @return the freelistGroups
	 */
	public String getFreelistGroups() {
		return freelistGroups;
	}

	/**
	 * @return the logging
	 */
	public String getLogging() {
		return logging;
	}

	/**
	 * @return the backedUp
	 */
	public String getBackedUp() {
		return backedUp;
	}

	/**
	 * @return the numRows
	 */
	public String getNumRows() {
		return numRows;
	}

	/**
	 * @return the blocks
	 */
	public String getBlocks() {
		return blocks;
	}

	/**
	 * @return the emptyBlocks
	 */
	public String getEmptyBlocks() {
		return emptyBlocks;
	}

	/**
	 * @return the avgSpace
	 */
	public String getAvgSpace() {
		return avgSpace;
	}

	/**
	 * @return the chainCnt
	 */
	public String getChainCnt() {
		return chainCnt;
	}

	/**
	 * @return the avgRowLen
	 */
	public String getAvgRowLen() {
		return avgRowLen;
	}

	/**
	 * @return the avgSpaceFreelistBlocks
	 */
	public String getAvgSpaceFreelistBlocks() {
		return avgSpaceFreelistBlocks;
	}

	/**
	 * @return the numFreelistBlocks
	 */
	public String getNumFreelistBlocks() {
		return numFreelistBlocks;
	}

	/**
	 * @return the degree
	 */
	public String getDegree() {
		return degree;
	}

	/**
	 * @return the instances
	 */
	public String getInstances() {
		return instances;
	}

	/**
	 * @return the cache
	 */
	public String getCache() {
		return cache;
	}

	/**
	 * @return the tableLock
	 */
	public String getTableLock() {
		return tableLock;
	}

	/**
	 * @return the sampleSize
	 */
	public String getSampleSize() {
		return sampleSize;
	}

	/**
	 * @return the lastAnalyzed
	 */
	public String getLastAnalyzed() {
		return lastAnalyzed;
	}

	/**
	 * @return the partitioned
	 */
	public String getPartitioned() {
		return partitioned;
	}

	/**
	 * @return the iotType
	 */
	public String getIotType() {
		return iotType;
	}

	/**
	 * @return the objectIdType
	 */
	public String getObjectIdType() {
		return objectIdType;
	}

	/**
	 * @return the tableTypeOwner
	 */
	public String getTableTypeOwner() {
		return tableTypeOwner;
	}

	/**
	 * @return the tableType
	 */
	public String getTableType() {
		return tableType;
	}

	/**
	 * @return the temporary
	 */
	public String getTemporary() {
		return temporary;
	}

	/**
	 * @return the secondary
	 */
	public String getSecondary() {
		return secondary;
	}

	/**
	 * @return the nested
	 */
	public String getNested() {
		return nested;
	}

	/**
	 * @return the bufferPool
	 */
	public String getBufferPool() {
		return bufferPool;
	}

	/**
	 * @return the rowMovement
	 */
	public String getRowMovement() {
		return rowMovement;
	}

	/**
	 * @return the globalStats
	 */
	public String getGlobalStats() {
		return globalStats;
	}

	/**
	 * @return the userStats
	 */
	public String getUserStats() {
		return userStats;
	}

	/**
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * @return the skipCorrupt
	 */
	public String getSkipCorrupt() {
		return skipCorrupt;
	}

	/**
	 * @return the monitoring
	 */
	public String getMonitoring() {
		return monitoring;
	}

	/**
	 * @return the clusterOwner
	 */
	public String getClusterOwner() {
		return clusterOwner;
	}

	/**
	 * @return the dependencies
	 */
	public String getDependencies() {
		return dependencies;
	}

	/**
	 * @return the compression
	 */
	public String getCompression() {
		return compression;
	}

	/**
	 * @return the dropperd
	 */
	public String getDropped() {
		return dropped;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		owner						= rs.getString("OWNER");
		tableName					= rs.getString("TABLE_NAME");
		tablespaceName				= rs.getString("TABLESPACE_NAME");
		clusterName					= rs.getString("CLUSTER_NAME");
		iotName						= rs.getString("IOT_NAME");
		status						= rs.getString("STATUS");
		pctFree						= rs.getString("PCT_FREE");
		pctUsed						= rs.getString("PCT_USED");
		iniTrans					= rs.getString("INI_TRANS");
		maxTrans					= rs.getString("MAX_TRANS");
		initialExtent				= rs.getString("INITIAL_EXTENT");
		nextExtent					= rs.getString("NEXT_EXTENT");
		minExtents					= rs.getString("MIN_EXTENTS");
		maxExtents					= rs.getString("MAX_EXTENTS");
		pctIncrease					= rs.getString("PCT_INCREASE");
		freelists					= rs.getString("FREELISTS");
		freelistGroups				= rs.getString("FREELIST_GROUPS");
		logging						= rs.getString("LOGGING");
		backedUp					= rs.getString("BACKED_UP");
		numRows						= rs.getString("NUM_ROWS");
		blocks						= rs.getString("BLOCKS");
		emptyBlocks					= rs.getString("EMPTY_BLOCKS");
		avgSpace					= rs.getString("AVG_SPACE");
		chainCnt					= rs.getString("CHAIN_CNT");
		avgRowLen					= rs.getString("AVG_ROW_LEN");
		avgSpaceFreelistBlocks		= rs.getString("AVG_SPACE_FREELIST_BLOCKS");
		numFreelistBlocks			= rs.getString("NUM_FREELIST_BLOCKS");
		degree						= rs.getString("DEGREE");
		instances					= rs.getString("INSTANCES");
		cache						= rs.getString("CACHE");
		tableLock					= rs.getString("TABLE_LOCK");
		sampleSize					= rs.getString("SAMPLE_SIZE");
		lastAnalyzed				= rs.getString("LAST_ANALYZED");
		partitioned					= rs.getString("PARTITIONED");
		iotType						= rs.getString("IOT_TYPE");
		objectIdType				= rs.getString("OBJECT_ID_TYPE");
		tableTypeOwner				= rs.getString("TABLE_TYPE_OWNER");
		tableType					= rs.getString("TABLE_TYPE");
		temporary					= rs.getString("TEMPORARY");
		secondary					= rs.getString("SECONDARY");
		nested						= rs.getString("NESTED");
		bufferPool					= rs.getString("BUFFER_POOL");
		rowMovement					= rs.getString("ROW_MOVEMENT");
		globalStats					= rs.getString("GLOBAL_STATS");
		userStats					= rs.getString("USER_STATS");
		duration					= rs.getString("DURATION");
		skipCorrupt					= rs.getString("SKIP_CORRUPT");
		monitoring					= rs.getString("MONITORING");
		clusterOwner				= rs.getString("CLUSTER_OWNER");
		dependencies				= rs.getString("DEPENDENCIES");
		compression					= rs.getString("COMPRESSION");
		dropped						= rs.getString("DROPPED");
 	}
 	
 	public void mapeaRegId( Connection conn, String condiciones) throws SQLException, IOException{
 		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement("SELECT"+
	 			" OWNER, TABLE_NAME, TABLESPACE_NAME, CLUSTER_NAME, IOT_NAME," +
	 			" STATUS, PCT_FREE, PCT_USED, INI_TRANS, MAX_TRANS," +
	 			" INITIAL_EXTENT, NEXT_EXTENT, MIN_EXTENTS, MAX_EXTENTS, PCT_INCREASE," +
	 			" FREELISTS, FREELIST_GROUPS, LOGGING, BACKED_UP, NUM_ROWS," +
	 			" BLOCKS, EMPTY_BLOCKS, AVG_SPACE, CHAIN_CNT, AVG_ROW_LEN," +
	 			" AVG_SPACE_FREELIST_BLOCKS, NUM_FREELIST_BLOCKS, DEGREE, INSTANCES, CACHE," +
	 			" TABLE_LOCK, SAMPLE_SIZE, LAST_ANALYZED, PARTITIONED, IOT_TYPE," +
	 			" OBJECT_ID_TYPE, TABLE_TYPE_OWNER, TABLE_TYPE, TEMPORARY, SECONDARY," +
	 			" NESTED, BUFFER_POOL, ROW_MOVEMENT, GLOBAL_STATS, USER_STATS," +
	 			" DURATION, SKIP_CORRUPT, MONITORING, CLUSTER_OWNER, DEPENDENCIES," +
	 			" COMPRESSION, DROPPED"+
	 			" FROM SYS.ALL_ALL_TABLES WHERE OWNER = '"+Config.OWNER+"' "+condiciones);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - sys.views.AllAllTables|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 	}
}