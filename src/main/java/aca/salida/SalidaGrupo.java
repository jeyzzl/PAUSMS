package aca.salida;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalidaGrupo {
	private String grupoId;
	private String grupoNombre;
	private String responsable;
	private String correo;
	private String usuarios;
	
	public SalidaGrupo(){
		grupoId     	= "0";
		grupoNombre 	= "-";
		responsable 	= "-";
		correo			= "-";
		usuarios		= "0";				
	}

	/**
	 * @return the grupoId
	 */
	public String getGrupoId() {
		return grupoId;
	}

	/**
	 * @param grupoId the grupoId to set
	 */
	public void setGrupoId(String grupoId) {
		this.grupoId = grupoId;
	}

	/**
	 * @return the grupoNombre
	 */
	public String getGrupoNombre() {
		return grupoNombre;
	}

	/**
	 * @param grupoNombre the grupoNombre to set
	 */
	public void setGrupoNombre(String grupoNombre) {
		this.grupoNombre = grupoNombre;
	}

	/**
	 * @return the responsable
	 */
	public String getResponsable() {
		return responsable;
	}

	/**
	 * @param responsable the responsable to set
	 */
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	

	/** MARY
	
	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	
	
	/**
	 * @return the usuarios
	 */
	public String getUsuarios() {
		return usuarios;
	}

	/**
	 * @param usuarios the usuarios to set
	 */
	public void setUsuarios(String usuarios) {
		this.usuarios = usuarios;
	}

	
	
	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{			
			ps = conn.prepareStatement("INSERT INTO ENOC.SAL_GRUPO" + 
					"(GRUPO_ID, GRUPO_NOMBRE, RESPONSABLE, CORREO, USUARIOS) " +
					"VALUES(TO_NUMBER(?,'9999'), ?, ?, ?, ?) ");
			ps.setString(1, grupoId);
			ps.setString(2, grupoNombre);
			ps.setString(3, responsable);
			ps.setString(4, correo);
			ps.setString(5, usuarios);
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaGrupo|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn) throws SQLException{
        PreparedStatement ps = null;
		boolean ok = false;

        try{
             ps = conn.prepareStatement(
                    "UPDATE ENOC.SAL_GRUPO" + 
                    " SET GRUPO_NOMBRE = ?," +
                    " RESPONSABLE = ?," +
                    " CORREO = ?," +
                    " USUARIOS = ?" +
                    " WHERE GRUPO_ID = TO_NUMBER(?, '9999')");
                    
            
            ps.setString(1, grupoNombre);
            ps.setString(2, responsable);
            ps.setString(3, correo);
            ps.setString(4, usuarios);
            ps.setString(5, grupoId);
            
           
            if(ps.executeUpdate() == 1){
                ok = true;
            }           
        }catch (Exception ex){
            System.out.println("Error - aca.salida.SalidaGrupo|updateReg|:" + ex);
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
                    "DELETE FROM ENOC.SAL_GRUPO" + 
                    " WHERE GRUPO_ID = TO_NUMBER(?, '9999')");
                    
            
            ps.setString(1, grupoId);            
          
            if(ps.executeUpdate() == 1){
                ok = true;
            }
        }catch (Exception ex){
            System.out.println("Error - aca.salida.SalidaGrupo|deleteReg|:" + ex);
        }finally{
       	 if(ps != null){
             ps.close();
         }
    }

        return ok;
    }

    public void mapeaReg(ResultSet rs) throws SQLException {
        grupoId	            = rs.getString("GRUPO_ID");
		grupoNombre			= rs.getString("GRUPO_NOMBRE");
		responsable			= rs.getString("RESPONSABLE");
		correo				= rs.getString("CORREO");
		usuarios			= rs.getString("USUARIOS");
				
    }
    
	
	public void mapeaRegId(Connection con, String grupoId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = con.prepareStatement("SELECT GRUPO_ID, GRUPO_NOMBRE, RESPONSABLE, CORREO, USUARIOS " +
					" FROM 	ENOC.SAL_GRUPO" + 
					" WHERE GRUPO_ID = ?" );
			
							
			ps.setString(1, grupoId);
			
			
			rs = ps.executeQuery();
		
			if(rs.next()){		
				mapeaReg(rs);		
			}
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaGrupo|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.SAL_GRUPO" + 
					" WHERE GRUPO_ID = TO_NUMBER(?, '9999')");
					
					
			ps.setString(1, grupoId);
						
			rs= ps.executeQuery();
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaGrupo|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String grupoId) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(GRUPO_ID)+1, 1) AS MAXIMO FROM ENOC.SAL_GRUPO" ); 
					
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