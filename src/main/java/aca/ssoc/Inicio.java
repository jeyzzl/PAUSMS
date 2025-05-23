/**
 * 
 */
package aca.ssoc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Elifo
 *
 */
public class Inicio {
	private String codigoPersonal;
	private String planId;
	private String fecha;
	private String porcentaje;
	private String semestre;
	
	public Inicio(){
		codigoPersonal	= "";
		planId			= "";
		fecha			= "";
		porcentaje		= "";
		semestre		= "";
	}

	/**
	 * @return the codigoPersonal
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	/**
	 * @param codigoPersonal the codigoPersonal to set
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the planId
	 */
	public String getPlanId() {
		return planId;
	}

	/**
	 * @param planId the planId to set
	 */
	public void setPlanId(String planId) {
		this.planId = planId;
	}

	/**
	 * @return the porcentaje
	 */
	public String getPorcentaje() {
		return porcentaje;
	}

	/**
	 * @param porcentaje the porcentaje to set
	 */
	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}

	/**
	 * @return the semestre
	 */
	public String getSemestre() {
		return semestre;
	}

	/**
	 * @param semestre the semestre to set
	 */
	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}
	
	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.SSOC_INICIO" + 
					" (CODIGO_PERSONAL, PLAN_ID, FECHA, PORCENTAJE, SEMESTRE)" +
					" VALUES(?,?,TO_DATE(?,'DD/MM/YYYY'), TO_NUMBER(?,'999'), TO_NUMBER(?,'99'))");
			ps.setString(1,codigoPersonal);
			ps.setString(2,planId);
			ps.setString(3,fecha);
			ps.setString(4,porcentaje);
			ps.setString(5,semestre);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.Inicio|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.SSOC_INICIO" + 
					" SET FECHA = ?," +
					" PORCENTAJE = TO_NUMBER(?,'999')," +
					" SEMESTRE = TO_NUMBER(?,'99')" +
					" WHERE CODIGO_PERSONAL = ?" +
					" AND PLAN_ID = ?");
			ps.setString(1,fecha);
			ps.setString(2,porcentaje);
			ps.setString(3,semestre);
			ps.setString(4,codigoPersonal);
			ps.setString(5,planId);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.sector|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.SSOC_INICIO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.sector|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		planId			= rs.getString("PLAN_ID");
		fecha			= rs.getString("FECHA");
		porcentaje		= rs.getString("PORCENTAJE");
		semestre		= rs.getString("SEMESTRE");
	}
	
	public void mapeaRegId(Connection con, String codigoPersonal, String planId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT CODIGO_PERSONAL," +
					" PLAN_ID, TO_CHAR(FECHA,'DD/MM/YYY') AS FECHA, PORCENTAJE, SEMESTRE" +				
					" FROM ENOC.SSOC_INICIO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.Inicio|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.SSOC_INICIO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.Inicio|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getFecha(Connection conn, String codigoPersonal, String planId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		String fecha			= "01/01/1950"; 
		
		
		try{
			ps = conn.prepareStatement("SELECT TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA FROM ENOC.SSOC_INICIO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			rs= ps.executeQuery();		
			if(rs.next()){
				fecha = rs.getString("FECHA");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.Inicio|getFecha|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return fecha;
	}
	
	public static String getMinFecha(Connection conn, String codigoPersonal, String planId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		String fecha			= "01/01/1950"; 
		
		
		try{
			ps = conn.prepareStatement("SELECT TO_CHAR(MIN(FECHA),'DD/MM/YYYY') AS FECHA FROM ENOC.SSOC_DOCALUM" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			rs= ps.executeQuery();		
			if(rs.next()){
				fecha = rs.getString("FECHA");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.Inicio|getMinFecha|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return fecha;
	}
}