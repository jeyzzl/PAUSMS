package aca.musica;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MusiPagare {

	private String periodoId;
 	private String pagare1;
 	private String pagare2;
 	private String pagare3;
 	private String comentario;
 
 	
 	public MusiPagare(){
 		periodoId		= "";
 		pagare1			= "";
 		pagare2			= "";
 		pagare3			= "";
 		comentario		= "";
 	} 	
	
 	

	/**
	 * @return the comentario
	 */
	public String getComentario() {
		return comentario;
	}



	/**
	 * @param comentario the comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}



	/**
	 * @return the periodoId
	 */
	public String getPeriodoId() {
		return periodoId;
	}


	/**
	 * @param periodoId the periodoId to set
	 */
	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}


	/**
	 * @return the pagare1
	 */
	public String getPagare1() {
		return pagare1;
	}


	/**
	 * @param pagare1 the pagare1 to set
	 */
	public void setPagare1(String pagare1) {
		this.pagare1 = pagare1;
	}


	/**
	 * @return the pagare2
	 */
	public String getPagare2() {
		return pagare2;
	}


	/**
	 * @param pagare2 the pagare2 to set
	 */
	public void setPagare2(String pagare2) {
		this.pagare2 = pagare2;
	}


	/**
	 * @return the pagare3
	 */
	public String getPagare3() {
		return pagare3;
	}


	/**
	 * @param pagare3 the pagare3 to set
	 */
	public void setPagare3(String pagare3) {
		this.pagare3 = pagare3;
	}



	public boolean insertReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("INSERT INTO ENOC.MUSI_PAGARE"+ 
 				"(PERIODO_ID, PAGARE1, PAGARE2, PAGARE3, COMENTARIO) "+
 				"VALUES( ?,TO_DATE(?,'DD/MM/YYYY'),TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), ?)");
 			ps.setString(1, periodoId);
 			ps.setString(2, pagare1);
 			ps.setString(3, pagare2);
 			ps.setString(4, pagare3);
 			ps.setString(5, comentario);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiPagare|insertReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("UPDATE ENOC.MUSI_PAGARE"+ 
 				" SET PAGARE1 = TO_DATE(?,'DD/MM/YYYY')," +
 				" PAGARE2 = TO_DATE(?,'DD/MM/YYYY')," +
 				" PAGARE3 = TO_DATE(?,'DD/MM/YYYY'), " +
 				" COMENTARIO = ? "+				
 				" WHERE PERIODO_ID = ?");
 				
 			ps.setString(1, pagare1);
 			ps.setString(2, pagare2);
 			ps.setString(3, pagare3);
 			ps.setString(4, comentario);
 			ps.setString(5, periodoId); 					
 			 			
 			if (ps.executeUpdate()== 1)
 				ok = true;	
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiPagare|updateReg|:"+ex);		
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	} 
 	
 	public boolean deleteReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("DELETE FROM ENOC.MUSI_PAGARE "+ 
 				"WHERE PERIODO_ID = ?");
 			ps.setString(1, periodoId); 			
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiPagare|deleteReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}
 	
 	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		periodoId 			= rs.getString("PERIODO_ID");
 		pagare1 			= rs.getString("PAGARE1");
 		pagare2 			= rs.getString("PAGARE2");
 		pagare3 			= rs.getString("PAGARE3");
 		comentario 			= rs.getString("COMENTARIO");
 	}
  	
 	public void mapeaRegId( Connection conn, String periodoId ) throws SQLException, IOException{
 		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
 		ps = conn.prepareStatement("SELECT"+
	 			" PERIODO_ID, TO_CHAR(PAGARE1,'DD/MM/YYYY') AS PAGARE1," +
	 			" TO_CHAR(PAGARE2,'DD/MM/YYYY') AS PAGARE2," +
	 			" TO_CHAR(PAGARE3,'DD/MM/YYYY') AS PAGARE3, COMENTARIO "+
	 			" FROM ENOC.MUSI_PAGARE WHERE PERIODO_ID = ?"); 
	 		ps.setString(1, periodoId);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiPagare|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 	}

 	public boolean existeReg(Connection conn) throws SQLException{
 		boolean 		ok 	= false;
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT * FROM ENOC.MUSI_PAGARE "+ 
 				"WHERE PERIODO_ID = ?");
 			ps.setString(1, periodoId); 			
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiPagare|existeReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}	
 	
}