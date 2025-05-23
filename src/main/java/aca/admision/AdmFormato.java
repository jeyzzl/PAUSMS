// Bean de AdmFormato 
package  aca.admision;

import java.sql.*;

public class AdmFormato{
	private String formatoId;
	private String formatoNombre;
	private String archivo;
		
	
	public AdmFormato(){
		formatoId 		= "";
		formatoNombre 	= "";
		archivo			= "";
	}

	public String getFormatoId() {
		return formatoId;
	}


	public void setFormatoId(String formatoId) {
		this.formatoId = formatoId;
	}

	public String getFormatoNombre() {
		return formatoNombre;
	}

	public void setFormatoNombre(String formatoNombre) {
		this.formatoNombre = formatoNombre;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}


	public boolean insertReg(Connection Conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = Conn.prepareStatement("INSERT INTO SALOMON.ADM_FORMATO "+
				" (FORMATO_ID, FORMATO_NOMBRE, ARCHIVO)"+
				" VALUES(TO_NUMBER(?,'99') ?, ?)");
			
			ps.setString(1, formatoId);
			ps.setString(2, formatoNombre);
			ps.setString(3, archivo);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmFormato|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
			
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE SALOMON.ADM_FORMATO" +
				" SET FORMATO_NOMBRE = ?, " +
				" ARCHIVO = ? "+
				" WHERE FORMATO_ID = TO_NUMBER(?,'99')");
			
			ps.setString(1, formatoNombre);
			ps.setString(2, archivo);
			ps.setString(3, formatoId);			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmFormato|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM SALOMON.ADM_FORMATO WHERE FORMATO_ID = TO_NUMBER(?,'99')");
			
			ps.setString(1, formatoId);		
			
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmFormato|deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
				
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		formatoId 		= rs.getString("FORMATO_ID");
		formatoNombre 	= rs.getString("FORMATO_NOMBRE");
		archivo		 	= rs.getString("ARCHIVO");
	}
	
	public void mapeaRegId( Connection conn, String formatoId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT * FROM SALOMON.ADM_FORMATO" +
				" WHERE FORMATO_ID = TO_NUMBER(?,'99')");
			
			ps.setString(1,formatoId);			
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmFormato|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM SALOMON.ADM_FORMATO "+
							" WHERE FORMATO_ID = TO_NUMBER(?,'99') ");
			
			ps.setString(1, formatoId);		
			
			rs = ps.executeQuery();
			if (rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmFormato|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
}