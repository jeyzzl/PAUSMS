package adm.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdmEncuesta {
	private String folio;
	private String recomendacionId;
	private String R1;
	private String R2;
	private String R3;
	private String R4;
	private String R5;
	private String R6;
	private String R7;
	private String R8;
	private String R9;
	private String R10;
	private String R11;
	private String R12;
	private String R13;
	private String tiempo;
	private String conocer;
	private String relacion;
	private String conducta;
	private String opinion;
	private String adicional;
	private String censura;
	private String otra;

		
	public AdmEncuesta(){
		folio 			= "";
		recomendacionId = "";
		R1	 			= "";
		R2	 			= "";
		R3	 			= "";
		R4	 			= "";
		R5	 			= "";
		R6	 			= "";
		R7	 			= "";
		R8	 			= "";
		R9	 			= "";
		R10	 			= "";
		R11	 			= "";
		R12	 			= "";
		R13	 			= "";
		tiempo			= "";
		conocer			= "";
		relacion		= "";
		conducta 		= "";
		opinion 		= "";
		adicional		= "";
		censura			= "";
		otra			= "";
	}
	
	/**
	 * @return the otra
	 */
	public String getOtra() {
		return otra;
	}

	/**
	 * @param otra the otra to set
	 */
	public void setOtra(String otra) {
		this.otra = otra;
	}

	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * @return the recomendacionId
	 */
	public String getRecomendacionId() {
		return recomendacionId;
	}

	/**
	 * @param recomendacionId the recomendacionId to set
	 */
	public void setRecomendacionId(String recomendacionId) {
		this.recomendacionId = recomendacionId;
	}

	/**
	 * @return the r1
	 */
	public String getR1() {
		return R1;
	}

	/**
	 * @param r1 the r1 to set
	 */
	public void setR1(String r1) {
		R1 = r1;
	}

	/**
	 * @return the r2
	 */
	public String getR2() {
		return R2;
	}

	/**
	 * @param r2 the r2 to set
	 */
	public void setR2(String r2) {
		R2 = r2;
	}

	/**
	 * @return the r3
	 */
	public String getR3() {
		return R3;
	}

	/**
	 * @param r3 the r3 to set
	 */
	public void setR3(String r3) {
		R3 = r3;
	}

	/**
	 * @return the r4
	 */
	public String getR4() {
		return R4;
	}

	/**
	 * @param r4 the r4 to set
	 */
	public void setR4(String r4) {
		R4 = r4;
	}

	/**
	 * @return the r5
	 */
	public String getR5() {
		return R5;
	}

	/**
	 * @param r5 the r5 to set
	 */
	public void setR5(String r5) {
		R5 = r5;
	}

	/**
	 * @return the r6
	 */
	public String getR6() {
		return R6;
	}

	/**
	 * @param r6 the r6 to set
	 */
	public void setR6(String r6) {
		R6 = r6;
	}

	/**
	 * @return the r7
	 */
	public String getR7() {
		return R7;
	}

	/**
	 * @param r7 the r7 to set
	 */
	public void setR7(String r7) {
		R7 = r7;
	}

	/**
	 * @return the r8
	 */
	public String getR8() {
		return R8;
	}

	/**
	 * @param r8 the r8 to set
	 */
	public void setR8(String r8) {
		R8 = r8;
	}

	/**
	 * @return the r9
	 */
	public String getR9() {
		return R9;
	}

	/**
	 * @param r9 the r9 to set
	 */
	public void setR9(String r9) {
		R9 = r9;
	}

	/**
	 * @return the r10
	 */
	public String getR10() {
		return R10;
	}

	/**
	 * @param r10 the r10 to set
	 */
	public void setR10(String r10) {
		R10 = r10;
	}

	/**
	 * @return the r11
	 */
	public String getR11() {
		return R11;
	}

	/**
	 * @param r11 the r11 to set
	 */
	public void setR11(String r11) {
		R11 = r11;
	}

	/**
	 * @return the r12
	 */
	public String getR12() {
		return R12;
	}

	/**
	 * @param r12 the r12 to set
	 */
	public void setR12(String r12) {
		R12 = r12;
	}

	/**
	 * @return the r13
	 */
	public String getR13() {
		return R13;
	}

	/**
	 * @param r13 the r13 to set
	 */
	public void setR13(String r13) {
		R13 = r13;
	}

	/**
	 * @return the tiempo
	 */
	public String getTiempo() {
		return tiempo;
	}

	/**
	 * @param tiempo the tiempo to set
	 */
	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}

	/**
	 * @return the conocer
	 */
	public String getConocer() {
		return conocer;
	}

	/**
	 * @param conocer the conocer to set
	 */
	public void setConocer(String conocer) {
		this.conocer = conocer;
	}

	/**
	 * @return the relacion
	 */
	public String getRelacion() {
		return relacion;
	}

	/**
	 * @param relacion the relacion to set
	 */
	public void setRelacion(String relacion) {
		this.relacion = relacion;
	}

	/**
	 * @return the conducta
	 */
	public String getConducta() {
		return conducta;
	}

	/**
	 * @param conducta the conducta to set
	 */
	public void setConducta(String conducta) {
		this.conducta = conducta;
	}

	/**
	 * @return the opinion
	 */
	public String getOpinion() {
		return opinion;
	}

	/**
	 * @param opinion the opinion to set
	 */
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	

	/**
	 * @return the adicional
	 */
	public String getAdicional() {
		return adicional;
	}

	/**
	 * @param adicional the adicional to set
	 */
	public void setAdicional(String adicional) {
		this.adicional = adicional;
	}

	/**
	 * @return the censura
	 */
	public String getCensura() {
		return censura;
	}

	/**
	 * @param censura the censura to set
	 */
	public void setCensura(String censura) {
		this.censura = censura;
	}

	public boolean insertReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO SALOMON.ADM_ENCUESTA"+ 
				" (FOLIO, RECOMENDACION_ID, R1, R2, R3, R4, R5, " +
				" R6 ,R7 ,R8 ,R9, R10, R11, R12, R13, TIEMPO, " +
				" CONOCER, RELACION, CONDUCTA, OPINION, CENSURA, ADICIONAL, OTRA ) "+
				" VALUES( TO_NUMBER(?,'99999999'), TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), " +
				" TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),TO_NUMBER(?,'99'), ?, " +
				" TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), ?, TO_NUMBER(?,'99'), ?, ?, ?)");
			ps.setString(1, folio);
			ps.setString(2, recomendacionId);
			ps.setString(3, R1);
			ps.setString(4, R2);
			ps.setString(5, R3);
			ps.setString(6, R4);
			ps.setString(7, R5);
			ps.setString(8, R6);
			ps.setString(9, R7);
			ps.setString(10, R8);
			ps.setString(11, R9);
			ps.setString(12, R10);
			ps.setString(13, R11);
			ps.setString(14, R12);
			ps.setString(15, R13);
			ps.setString(16, tiempo);
			ps.setString(17, conocer);
			ps.setString(18, relacion);
			ps.setString(19, conducta);
			ps.setString(20, opinion);
			ps.setString(21, censura);
			ps.setString(22, adicional);
			ps.setString(23, otra);
	
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmEncuesta|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE SALOMON.ADM_ENCUESTA " + 
					" SET R1 = TO_NUMBER(?,'99')," +
					" R2 = TO_NUMBER(?,'99'), " +
					" R3 = TO_NUMBER(?,'99'), " +
					" R4 = TO_NUMBER(?,'99'), " +
					" R5 = TO_NUMBER(?,'99'), " +
					" R6 = TO_NUMBER(?,'99'), " +
					" R7 = TO_NUMBER(?,'99'), " +
					" R8 = TO_NUMBER(?,'99'), " +
					" R9 = TO_NUMBER(?,'99'), " +
					" R10 = TO_NUMBER(?,'99'), " +
					" R11 = TO_NUMBER(?,'99'), " +
					" R12 = TO_NUMBER(?,'99'), " +
					" R13 = TO_NUMBER(?,'99'), " +	
					" TIEMPO = ?, " +
					" CONOCER = TO_NUMBER(?,'99'), " +
					" RELACION = TO_NUMBER(?,'99'), CONDUCTA = ?, OPINION = TO_NUMBER(?,'99'), " +
					" CENSURA = ?, " +
					" ADICIONAL = ?, " +
					" OTRA = ? " +				
					" WHERE FOLIO = TO_NUMBER(?,'99999999') AND RECOMENDACION_ID = TO_NUMBER(?,'99') ");
			
			ps.setString(1, R1);
			ps.setString(2, R2);
			ps.setString(3, R3);
			ps.setString(4, R4);
			ps.setString(5, R5);
			ps.setString(6, R6);
			ps.setString(7, R7);
			ps.setString(8, R8);
			ps.setString(9, R9);
			ps.setString(10, R10);
			ps.setString(11, R11);
			ps.setString(12, R12);
			ps.setString(13, R13);
			ps.setString(14, tiempo);
			ps.setString(15, conocer);
			ps.setString(16, relacion);
			ps.setString(17, conducta);
			ps.setString(18, opinion);
			ps.setString(19, censura);
			ps.setString(20, adicional);
			ps.setString(21, otra);
			ps.setString(22, folio);
			ps.setString(23, recomendacionId);
			
			if ( ps.executeUpdate()== 1){
				ok = true;
				conn.commit();
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmRecomienda|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM SALOMON.ADM_ENCUESTA "+ 
					" WHERE FOLIO = TO_NUMBER(?,'99999999') AND RECOMENDACION_ID = TO_NUMBER(?, '99') ");
			ps.setString(1, folio);
			ps.setString(2, recomendacionId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmRecomienda|deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		folio			= rs.getString("FOLIO");
		recomendacionId = rs.getString("RECOMENDACION_ID");
		R1	 			= rs.getString("R1");
		R2	 			= rs.getString("R2");
		R3	 			= rs.getString("R3");
		R4	 			= rs.getString("R4");
		R5	 			= rs.getString("R5");
		R6	 			= rs.getString("R6");
		R7	 			= rs.getString("R7");
		R8	 			= rs.getString("R8");
		R9	 			= rs.getString("R9");
		R10	 			= rs.getString("R10");
		R11	 			= rs.getString("R11");
		R12	 			= rs.getString("R12");
		R13	 			= rs.getString("R13");
		tiempo			= rs.getString("TIEMPO");
		conocer			= rs.getString("CONOCER");
		relacion		= rs.getString("RELACION");
		conducta 		= rs.getString("CONDUCTA");
		opinion 		= rs.getString("OPINION");
		censura 		= rs.getString("CENSURA");
		adicional 		= rs.getString("ADICIONAL");
		otra 			= rs.getString("OTRA");
	}
	
	public void mapeaRegId( Connection conn, String folio, String recomendacionId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT FOLIO, RECOMENDACION_ID, R1, R2,R3, R4, R5," +
				" R6, R7, R8, R9, R10, R11, R12, R13, TIEMPO, CONOCER, RELACION, CONDUCTA, OPINION, CENSURA, ADICIONAL, OTRA " +
				" FROM SALOMON.ADM_ENCUESTA "+ 
				" WHERE FOLIO = TO_NUMBER(?,'9999999') AND RECOMENDACION_ID = TO_NUMBER(?,'99') ");
		ps.setString(1, folio);
		ps.setString(2, recomendacionId);
		
		rs = ps.executeQuery();
		if (rs.next()){
			mapeaReg(rs);
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }		
	}
	
	public boolean existeReg(Connection conn ) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM SALOMON.ADM_ENCUESTA "+ 
					" WHERE FOLIO = TO_NUMBER(?,'9999999') AND RECOMENDACION_ID = TO_NUMBER(?,'99')");
			ps.setString(1, folio);
			ps.setString(2, recomendacionId);
						
			rs = ps.executeQuery();
			if (rs.next()){
				ok = true;
			}else{			
				ok = false;
			}
		}catch(Exception ex){
		
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}

}