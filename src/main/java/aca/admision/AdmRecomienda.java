package aca.admision;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdmRecomienda {
	private String folio;
	private String recomendacionId;
	private String nombre;
	private String puesto;
	private String email;
	private String telefono;
	private String estado;
		
	public AdmRecomienda(){
		folio 			= "";
		recomendacionId = "";
		nombre 			= "";
		puesto			= "";
		email 			= "";
		telefono 		= "";
		estado 			= "";
	}
	

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getRecomendacionId() {
		return recomendacionId;
	}

	public void setRecomendacionId(String recomendacionId) {
		this.recomendacionId = recomendacionId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean insertReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO SALOMON.ADM_RECOMIENDA"+ 
				" (FOLIO, RECOMENDACION_ID, NOMBRE, PUESTO, EMAIL, TELEFONO, ESTADO ) "+
				" VALUES( TO_NUMBER(?,'99999999'), TO_NUMBER(?,'99'), ?, ?, ?, ?, ? )");
			ps.setString(1, folio);
			ps.setString(2, recomendacionId);
			ps.setString(3, nombre);
			ps.setString(4, puesto);
			ps.setString(5, email);
			ps.setString(6, telefono);
			ps.setString(7, estado);
	
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmRecomienda|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE SALOMON.ADM_RECOMIENDA " + 
					" SET NOMBRE = ?, " +
					" PUESTO = ?, " +
					" EMAIL = ?, " +
					" TELEFONO = ?, ESTADO = ? " +				
					" WHERE FOLIO = TO_NUMBER(?,'99999999') AND RECOMENDACION_ID = TO_NUMBER(?,'99') ");
			
			ps.setString(1,  nombre);
			ps.setString(2,  puesto);
			ps.setString(3,  email);
			ps.setString(4,  telefono);
			ps.setString(5,  estado);
			ps.setString(6,  folio);
			ps.setString(7,  recomendacionId);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
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
			ps = conn.prepareStatement("DELETE FROM SALOMON.ADM_RECOMIENDA "+ 
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
	
	public boolean deleteAlumRec(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM SALOMON.ADM_RECOMIENDA "+ 
					" WHERE FOLIO = TO_NUMBER(?,'99999999')");
			ps.setString(1, folio);			
			
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
		nombre	 		= rs.getString("NOMBRE");
		puesto			= rs.getString("PUESTO");
		email			= rs.getString("EMAIL");
		telefono		= rs.getString("TELEFONO");
		estado			= rs.getString("ESTADO");
	}
	
	public void mapeaRegId( Connection conn, String folio, String recomendacionId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT FOLIO, RECOMENDACION_ID, NOMBRE, PUESTO, EMAIL, TELEFONO, ESTADO " +
				" FROM SALOMON.ADM_RECOMIENDA "+ 
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
			ps = conn.prepareStatement("SELECT * FROM SALOMON.ADM_RECOMIENDA "+ 
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