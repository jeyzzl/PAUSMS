package aca.salida;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalBitacora {

	private String salidaId;
	private String estado;
	private String fecha;
	
	public SalBitacora(){
		salidaId     	= "";
		estado		 	= "";
		fecha  			= "";
				
	}

	public String getSalidaId() {
		return salidaId;
	}



	public void setSalidaId(String salidaId) {
		this.salidaId = salidaId;
	}



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}



	public String getFecha() {
		return fecha;
	}



	public void setFecha(String fecha) {
		this.fecha = fecha;
	}



	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{			
			ps = conn.prepareStatement("INSERT INTO ENOC.SAL_BITACORA " + 
					"(SALIDA_ID, ESTADO, FECHA) " +
					" VALUES(TO_NUMBER(?,'99999'), ?, TO_TIMESTAMP(?,'DD/MM/YYYY HH24:MI:SS')) ");
			ps.setString(1, salidaId);
			ps.setString(2, estado);
			ps.setString(3, fecha);
									
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalBitacora|insertReg|:"+ex+":"+salidaId);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.SAL_BITACORA "+ 
				" SET FECHA = TO_TIMESTAMP(?,'DD/MM/YYYY HH24:MI:SS') " +
				" WHERE SALIDA_ID = TO_NUMBER(?,'99999') AND ESTADO = ?");
			ps.setString(1, fecha);
			ps.setString(2, salidaId);
			ps.setString(3, estado);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalBitacora|updateReg|:"+ex);
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
                    " DELETE FROM ENOC.SAL_BITACORA" + 
                    " WHERE SALIDA_ID = TO_NUMBER(?, '99999')" +
            		" AND ESTADO = ?");
            ps.setString(1, salidaId);
            ps.setString(2, estado);
          
            if(ps.executeUpdate() == 1){
                ok = true;
            }
        }catch (Exception ex){
            System.out.println("Error - aca.salida.SalBitacora|deleteReg|:" + ex);
        }finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
        return ok;
    }
    
    public boolean deleteAll(Connection conn, String salidaId) throws SQLException{
        PreparedStatement ps = null;
    	boolean ok = false;

        try {
             ps = conn.prepareStatement(
                    " DELETE FROM ENOC.SAL_BITACORA" + 
                    " WHERE SALIDA_ID = TO_NUMBER(?, '99999')");
            ps.setString(1, salidaId);
          
            if(ps.executeUpdate() == 1){
                ok = true;
            }
        }catch (Exception ex){
            System.out.println("Error - aca.salida.SalBitacora|deleteAll|:" + ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.SAL_BITACORA" + 
					" WHERE SALIDA_ID = TO_NUMBER(?, '99999') AND ESTADO = ?");
					
					
			ps.setString(1, salidaId);
			ps.setString(2, estado);
						
			rs= ps.executeQuery();
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalBitacora|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
    
    public void mapeaReg(ResultSet rs) throws SQLException {
        salidaId	        = rs.getString("SALIDA_ID");
		estado				= rs.getString("ESTADO");
		fecha				= rs.getString("FECHA");
		
    }
    
	
    public void mapeaRegId( Connection conn, String salidaId, String estado) throws SQLException{
		PreparedStatement ps = null;
    	ResultSet rs = null;
		try{ 
	    	ps = conn.prepareStatement("SELECT SALIDA_ID, ESTADO, FECHA "+
				"FROM ENOC.SAL_BITACORA WHERE SALIDA_ID = TO_NUMBER(?,'99999') AND ESTADO = ?"); 
			ps.setString(1,salidaId);
			ps.setString(2,estado);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalBitacora|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
	}
	
}