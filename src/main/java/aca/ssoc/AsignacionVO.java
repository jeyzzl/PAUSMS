/*
 * Created on 14/03/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.ssoc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Pedro
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AsignacionVO {
	
    String codigoPersonal;
    String asignacionId;
    String dependencia;
    String direccion;
    String telefono;
    String responsable;
    String estado;
    String fechaInicio;
    String sector;
    
    public AsignacionVO() {
    	
    	codigoPersonal 	= "";
    	dependencia		= "";
    	direccion		= "";
    	telefono		= "";
    	responsable		= "";
    	estado			= "";
    	asignacionId	= "";
    	fechaInicio		= "";
    	sector			= "";
    }
    
    
    public String getCodigoPersonal() {
        return codigoPersonal;
    }
    public void setCodigoPersonal(String codigoPersonal) {
        this.codigoPersonal = codigoPersonal;
    }
    public String getDependencia() {
        return dependencia;
    }
    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public String getAsignacionId() {
        return asignacionId;
    }
    public void setAsignacionId(String asignacionId) {
        this.asignacionId = asignacionId;
    }
    public String getResponsable() {
        return responsable;
    }
    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}

	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.SSOC_ASIGNACION  "+ 
				"WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.AsignacionVO|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
    
    public boolean insertReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.SSOC_ASIGNACION"+ 
				"(CODIGO_PERSONAL, "+
				"DEPENDENCIA, DIRECCION, "+
				"TELEFONO, RESPONSABLE, F_INICIO, ESTADO, ASIGNACION_ID,SECTOR) "+
				"VALUES(?, ?, ?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?)");
				
			ps.setString(1, codigoPersonal);
			ps.setString(2, dependencia);
			ps.setString(3, direccion);
			ps.setString(4, telefono);
			ps.setString(5, responsable);
			ps.setString(6, fechaInicio);
			ps.setString(7, "A");
			ps.setString(8, asignacionId);
			ps.setString(9, sector);
		
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.AsignacionVO|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	} 
    
    public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.SSOC_ASIGNACION "+ 
				"SET "+				
				"DEPENDENCIA = ?, "+
				"DIRECCION = ?, "+
				"TELEFONO = ?, "+
				"RESPONSABLE = ?, "+
				"F_INICIO = TO_DATE(?, 'DD/MM/YYYY'), "+
				"SECTOR = ? "+
				"WHERE CODIGO_PERSONAL = ? AND ASIGNACION_ID = ?");			
			ps.setString(1, dependencia);
			ps.setString(2, direccion);
			ps.setString(3, telefono);
			ps.setString(4, responsable);
			ps.setString(5, fechaInicio);
			ps.setString(6, sector);
			ps.setString(7, codigoPersonal);
			ps.setString(8, asignacionId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.AsignacionVO|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
    
    public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.SSOC_ASIGNACION "+ 
				"WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.AsignacionVO|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
    
    public String MaximoReg(Connection conn, String codigoPersonal) throws SQLException{
    	
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement ("SELECT COALESCE(MAX(ASIGNACION_ID)+1,1) AS MAXIMO FROM ENOC.SSOC_ASIGNACION WHERE CODIGO_PERSONAL = ?"); 
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
				
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.AsignacionVO|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;
		
	}
    
    public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		asignacionId		= rs.getString("ASIGNACION_ID");
		dependencia			= rs.getString("DEPENDENCIA");
		direccion			= rs.getString("DIRECCION");
		telefono			= rs.getString("TELEFONO");
		responsable			= rs.getString("RESPONSABLE");
		fechaInicio			= rs.getString("F_INICIO");
		estado				= rs.getString("ESTADO");
		sector				= rs.getString("SECTOR");
	}
    
    public void mapeaRegId( Connection conn, String codigoPersonal, String asignacionId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, ASIGNACION_ID, DEPENDENCIA, DIRECCION, "+
				"TELEFONO, RESPONSABLE, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO, ESTADO, SECTOR FROM ENOC.SSOC_ASIGNACION WHERE CODIGO_PERSONAL = ? AND ASIGNACION_ID = ?"); 
			ps.setString(1, codigoPersonal);
			ps.setString(2, asignacionId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.AsignacionVO|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
}