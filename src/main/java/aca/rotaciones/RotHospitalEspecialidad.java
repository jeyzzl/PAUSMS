package aca.rotaciones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RotHospitalEspecialidad {

	private String hospitalId;
	private String especialidadId;
	private String contactoPrincipal;
	private String puestoPrincipal;
	private String contactoSecundario;
	private String puestoSecundario;
	private String estado;
	
	public RotHospitalEspecialidad(){		
		hospitalId			= "";
		especialidadId		= "";
		contactoPrincipal	= "";
		puestoPrincipal 	= "";
		contactoSecundario	= "";
		puestoSecundario	= "";
		estado				= "A";
	}
	

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getEspecialidadId() {
		return especialidadId;
	}

	public void setEspecialidadId(String especialidadId) {
		this.especialidadId = especialidadId;
	}

	public String getContactoPrincipal() {
		return contactoPrincipal;
	}

	public void setContactoPrincipal(String contactoPrincipal) {
		this.contactoPrincipal = contactoPrincipal;
	}

	public String getPuestoPrincipal() {
		return puestoPrincipal;
	}

	public void setPuestoPrincipal(String puestoPrincipal) {
		this.puestoPrincipal = puestoPrincipal;
	}

	public String getContactoSecundario() {
		return contactoSecundario;
	}

	public void setContactoSecundario(String contactoSecundario) {
		this.contactoSecundario = contactoSecundario;
	}

	public String getPuestoSecundario() {
		return puestoSecundario;
	}

	public void setPuestoSecundario(String puestoSecundario) {
		this.puestoSecundario = puestoSecundario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}


	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ROT_HOSPITALESPECIALIDAD(HOSPITAL_ID, ESPECIALIDAD_ID, CONTACTO_PRINCIPAL, " + 
					" PUESTO_PRINCIPAL, CONTACTO_SECUNDARIO, PUESTO_SECUNDARIO, ESTADO) " +
					" VALUES(TO_NUMBER(?,'999'),TO_NUMBER(?,'999'),?,?,?,?,?)");
			ps.setString(1,hospitalId);
			ps.setString(2,especialidadId);
			ps.setString(3,contactoPrincipal);		
			ps.setString(4,puestoPrincipal);
			ps.setString(5,contactoSecundario);
			ps.setString(6,puestoSecundario);
			ps.setString(7,estado);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotHospitalesEspecilidad|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ROT_HOSPITALESPECIALIDAD SET CONTACTO_PRINCIPAL = ? , " +
					" PUESTO_PRINCIPAL =  ?, CONTACTO_SECUNDARIO = ?, PUESTO_SECUNDARIO = ?, ESTADO = ? " +
					" WHERE HOSPITAL_ID = ? AND ESPECIALIDAD_ID = ? ");			
			
			ps.setString(1,contactoPrincipal);
			ps.setString(2,puestoPrincipal);		
			ps.setString(3,contactoSecundario);
			ps.setString(4,puestoSecundario);
			ps.setString(5,estado);
			ps.setString(6,hospitalId);
			ps.setString(7,especialidadId);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotHospitalEspecialidad|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ROT_HOSPITALESPECIALIDAD WHERE HOSPITAL_ID = ? AND ESPECIALIDAD_ID = ? "); 
			ps.setString(1,hospitalId);		
			ps.setString(2,especialidadId);		
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotHospitalEspecialidad|deletetReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		hospitalId 			= rs.getString("HOSPITAL_ID");
		especialidadId  	= rs.getString("ESPECIALIDAD_ID");
		contactoPrincipal	= rs.getString("CONTACTO_PRINCIPAL");				
		puestoPrincipal		= rs.getString("PUESTO_PRINCIPAL");
		contactoSecundario	= rs.getString("CONTACTO_SECUNDARIO");
		puestoSecundario	= rs.getString("PUESTO_SECUNDARIO");
		estado				= rs.getString("ESTADO");
	}
	
	public void mapeaRegId(Connection con, String hospitalId, String especialidadId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement("SELECT HOSPITAL_ID, ESPECIALIDAD_ID, CONTACTO_PRINCIPAL, PUESTO_PRINCIPAL," +
					" CONTACTO_SECUNDARIO, PUESTO_SECUNDARIO, ESTADO " +
					" FROM ENOC.ROT_HOSPITALESPECIALIDAD WHERE HOSPITAL_ID = ? AND ESPECIALIDAD_ID = ? "); 
			ps.setString(1,hospitalId);
			ps.setString(2,especialidadId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotHospitalEspecialidad|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.ROT_HOSPITALESPECIALIDAD WHERE HOSPITAL_ID = ? AND ESPECIALIDAD_ID = ? "); 
			ps.setString(1, hospitalId);
			ps.setString(2, especialidadId);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotHospitalEspecialidad|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeHospital(Connection conn, String hospitalId) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ROT_HOSPITALESPECIALIDAD WHERE HOSPITAL_ID = ? "); 
			ps.setString(1, hospitalId);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotHospitalEspecialidad|existeHospital|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}

}
