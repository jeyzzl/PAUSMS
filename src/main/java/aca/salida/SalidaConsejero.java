package aca.salida;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalidaConsejero {
	private String salidaId;
	private String folio;
	private String consejero;
	private String trabajo;
	private String clave;
	
	public SalidaConsejero(){
		salidaId     = "";
		folio		 = "";
		consejero	 = "";
		trabajo		 = "";
		clave		 = "";
				
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
	 * @return the consejero
	 */
	public String getConsejero() {
		return consejero;
	}

	/**
	 * @param consejero the consejero to set
	 */
	public void setConsejero(String consejero) {
		this.consejero = consejero;
	}

	/**
	 * @return the trabajo
	 */
	public String getTrabajo() {
		return trabajo;
	}

	/**
	 * @param trabajo the trabajo to set
	 */
	public void setTrabajo(String trabajo) {
		this.trabajo = trabajo;
	}

	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}


	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{			
			ps = conn.prepareStatement("INSERT INTO ENOC.SAL_CONSEJERO" + 
					"(SALIDA_ID, FOLIO, CONSEJERO, TRABAJO, CLAVE) " +
					"VALUES(TO_NUMBER(?,'99999'), TO_NUMBER(?,'99'), ?, ?, ?) ");
			ps.setString(1, salidaId);
			ps.setString(2, folio);
			ps.setString(3, consejero);
			ps.setString(4, trabajo);
			ps.setString(5, clave);
									
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaConsejero|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn) throws SQLException{
        boolean ok = false;
		PreparedStatement ps = null;
        try{
             ps = conn.prepareStatement(
                    "UPDATE ENOC.SAL_CONSEJERO" + 
                    " SET CONSEJERO = ?," +
                    " TRABAJO = ?," +
                    " CLAVE = ? " +                    
                    " WHERE SALIDA_ID = TO_NUMBER(?, '99999') AND FOLIO = TO_NUMBER(?, '99')");
                    
            
            ps.setString(1, consejero);
            ps.setString(2, trabajo);
            ps.setString(3, clave);
            ps.setString(4, salidaId);
            ps.setString(5, folio);
           
            if(ps.executeUpdate() == 1){
                ok = true;
            }

            
        }catch (Exception ex){
            System.out.println("Error - aca.salida.SalidaConsejero|updateReg|:" + ex);
        }finally{
        	if(ps != null){
                ps.close();
            }
        }

        return ok;
    }

    public boolean deleteReg(Connection conn) throws SQLException{
        PreparedStatement ps = null;
    	boolean ok = false;

        try {
             ps = conn.prepareStatement(
                    "DELETE FROM ENOC.SAL_CONSEJERO" + 
                    " WHERE SALIDA_ID = TO_NUMBER(?, '99999') AND FOLIO = TO_NUMBER(?, '99')");
                    
            
            ps.setString(1, salidaId);
            ps.setString(2, folio);
          
            if(ps.executeUpdate() == 1){
                ok = true;
            }
            
        }catch (Exception ex){
            System.out.println("Error - aca.salida.SalidaConsejero|deleteReg|:" + ex);
        }finally{
        	if(ps != null){
                ps.close();
            }
        }

        return ok;
    }

    public void mapeaReg(ResultSet rs) throws SQLException {
        salidaId            = rs.getString("SALIDA_ID");
		folio				= rs.getString("FOLIO");
		consejero			= rs.getString("CONSEJERO");
		trabajo				= rs.getString("TRABAJO");
		clave				= rs.getString("CLAVE");
		
    }
    
	
	public void mapeaRegId(Connection con, String salidaId, String folio) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = con.prepareStatement("SELECT SALIDA_ID, FOLIO, CONSEJERO, TRABAJO, CLAVE " +
					" FROM 	ENOC.SAL_CONSEJERO " + 
					" WHERE SALIDA_ID = ? AND FOLIO = ?" );
			
							
			ps.setString(1, salidaId);
			ps.setString(2, folio);
			
			
			rs = ps.executeQuery();
		
			if(rs.next()){		
				mapeaReg(rs);		
			}
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaConsejero|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean ok 				= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.SAL_CONSEJERO" + 
					" WHERE SALIDA_ID = TO_NUMBER(?, '99999') AND FOLIO = TO_NUMBER(?, '99')");
					
					
			ps.setString(1, salidaId);
			ps.setString(2, folio);
						
			rs= ps.executeQuery();
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaConsejero|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	public String maximoReg(Connection conn, String salidaId) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(FOLIO)+1, 1) AS MAXIMO FROM ENOC.SAL_CONSEJERO WHERE SALIDA_ID = ? " ); 
			ps.setString(1, salidaId);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaGrupo|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
}