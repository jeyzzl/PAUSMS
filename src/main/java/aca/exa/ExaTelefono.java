package  aca.exa;

import java.sql.*;

public class ExaTelefono{
	private String telefonoId;	
	private String matricula;
	private String tipo;
	private String telefono;	
	private String fechaAct;
	private String eliminado;
	private String idTelefono;
	
	public ExaTelefono(){
		telefonoId 			= "";
		matricula			= "";
		tipo 				= "";
		telefono			= "";
		fechaAct			= "";
		eliminado			= "";
		idTelefono			= "";
	}
	

	public String getTelefonoId() {
		return telefonoId;
	}


	public void setTelefonoId(String telefonoId) {
		this.telefonoId = telefonoId;
	}


	public String getMatricula() {
		return matricula;
	}


	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getFechaAct() {
		return fechaAct;
	}


	public void setFechaAct(String fechaAct) {
		this.fechaAct = fechaAct;
	}


	public String getEliminado() {
		return eliminado;
	}


	public void setEliminado(String eliminado) {
		this.eliminado = eliminado;
	}


	public String getIdTelefono() {
		return idTelefono;
	}


	public void setIdTelefono(String idTelefono) {
		this.idTelefono = idTelefono;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		telefonoId 			= rs.getString("TELEFONO_ID");
		matricula 			= rs.getString("MATRICULA");
		tipo				= rs.getString("TIPO");
		telefono			= rs.getString("TELEFONO");
		fechaAct 			= rs.getString("FECHAACTUALIZACION");
		eliminado 			= rs.getString("ELIMINADO");
		idTelefono		 	= rs.getString("IDTELEFONO");
	}
	
	public void mapeaRegIdEstudio( Connection conn, String telefonoId) throws SQLException{
		
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT TELEFONO_ID, MATRICULA, TIPO, TELEFONO, " +
					" FECHAACTUALIZACION, ELIMINADO, IDTELEFONO "+
				"FROM ENOC.EXA_TELEFONO WHERE TELEFONO_ID = TO_NUMBER(?,'99999999')"); 
			ps.setString(1,telefonoId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaTelefonoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
	public void mapeaRegId( Connection conn, String matricula) throws SQLException{
		
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT TELEFONO_ID, MATRICULA, TIPO, TELEFONO,  " +
					" FECHAACTUALIZACION, ELIMINADO, IDTELEFONO "+
				"FROM ENOC.EXA_TELEFONO WHERE MATRICULA = ?"); 
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaTelefonoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}