package aca.salida;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalidaAlumno {

	private String salidaId;
	private String codigoPersonal;
	private String fecha;
	private String usuario;
	
	public SalidaAlumno(){
		salidaId     	= "";
		codigoPersonal 	= "";
		fecha  			= "";
		usuario  			= "";
				
	}

	/**
	 * @return the salidaId
	 */
	public String getSalidaId() {
		return salidaId;
	}

	/**
	 * @param salidaId the salidaId to set
	 */
	public void setSalidaId(String salidaId) {
		this.salidaId = salidaId;
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
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{			
			ps = conn.prepareStatement("INSERT INTO ENOC.SAL_ALUMNO " + 
					"(SALIDA_ID, CODIGO_PERSONAL, FECHA, USUARIO) " +
					" VALUES(TO_NUMBER(?,'99999'), ?, TO_DATE(?,'DD/MM/YYYY'),? ) ");
			ps.setString(1, salidaId);
			ps.setString(2, codigoPersonal);
			ps.setString(3, fecha);
			ps.setString(4, usuario);
									
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaAlumno|insertReg|:"+ex+":"+salidaId+":"+codigoPersonal);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.SAL_ALUMNO "+ 
				" SET FECHA = TO_DATE(?,'DD/MM/YYYY'), " +
				" USUARIO = ? " +
				" WHERE SALIDA_ID = TO_NUMBER(?,'99999')" +
				" AND  CODIGO_PERSONAL = ? ");
			ps.setString(1, fecha);
			ps.setString(2, usuario);
			ps.setString(3, salidaId);
			ps.setString(4, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaAlumno|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	

    public boolean deleteReg(Connection conn) throws SQLException{
        PreparedStatement ps = null;
    	boolean ok = false;

        try {
             ps = conn.prepareStatement(
                    " DELETE FROM ENOC.SAL_ALUMNO" + 
                    " WHERE SALIDA_ID = TO_NUMBER(?, '99999')" +
                    " AND CODIGO_PERSONAL = ?"); 
            ps.setString(1, salidaId);
            ps.setString(2, codigoPersonal);
          
            if(ps.executeUpdate() == 1){
                ok = true;
            }
        }catch (Exception ex){
            System.out.println("Error - aca.salida.SalidaAlumno|deleteReg|:" + ex);
        }finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
        return ok;
    }
    
    public boolean existeReg(Connection conn) throws SQLException{
		boolean ok 				= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.SAL_ALUMNO" + 
					" WHERE SALIDA_ID = TO_NUMBER(?, '99999') AND CODIGO_PERSONAL = ?");
					
					
			ps.setString(1, salidaId);
			ps.setString(2, codigoPersonal);
						
			rs= ps.executeQuery();
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaAlumno|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
    
    public void mapeaReg(ResultSet rs) throws SQLException {
        salidaId	        = rs.getString("SALIDA_ID");
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		fecha				= rs.getString("FECHA");
		usuario				= rs.getString("USUARIO");
		
    }
    
	
    public void mapeaRegId( Connection conn, String salidaId, String codigoPersonal) throws SQLException{
		PreparedStatement ps = null;
    	ResultSet rs = null;
		try{ 
	    	ps = conn.prepareStatement("SELECT SALIDA_ID, CODIGO_PERSONAL, FECHA, USUARIO "+
				"FROM ENOC.SAL_ALUMNO WHERE SALIDA_ID = TO_NUMBER(?,'99999') AND CODIGO_PERSONAL = ? "); 
			ps.setString(1,salidaId);
			ps.setString(2,codigoPersonal);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaAlumno|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
	}
	
}