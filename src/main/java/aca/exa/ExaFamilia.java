package  aca.exa;

import java.sql.*;

public class ExaFamilia{
	private String familiaId;	
	private String matricula;
	private String nombre;
	private String relacion;	
	private String fechaNac;
	private String fechaAct;
	private String eliminado;
	private String correo;	
	private String fechaAniv;
	private String idFamilia;
	
	public ExaFamilia(){
		familiaId 			= "";
		matricula			= "";
		nombre 				= "";
		relacion			= "";
		fechaNac	 		= "";
		fechaAct			= "";
		eliminado			= "";
		correo				= "";
		fechaAniv	 		= "";
		idFamilia			= "";
	}

	public String getFamiliaId() {
		return familiaId;
	}

	public void setFamiliaId(String familiaId) {
		this.familiaId = familiaId;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRelacion() {
		return relacion;
	}

	public void setRelacion(String relacion) {
		this.relacion = relacion;
	}

	public String getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(String fechaNac) {
		this.fechaNac = fechaNac;
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

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getFechaAniv() {
		return fechaAniv;
	}

	public void setFechaAniv(String fechaAniv) {
		this.fechaAniv = fechaAniv;
	}

	public String getIdFamilia() {
		return idFamilia;
	}

	public void setIdFamilia(String idFamilia) {
		this.idFamilia = idFamilia;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		familiaId 			= rs.getString("FAMILIA_ID");
		matricula 			= rs.getString("MATRICULA");
		nombre 				= rs.getString("NOMBRE");
		relacion 			= rs.getString("RELACION");
		fechaNac 			= rs.getString("FECHANACIMIENTO");
		fechaAct 			= rs.getString("FECHAACTUALIZACION");
		eliminado 			= rs.getString("ELIMINADO");
		correo 				= rs.getString("CORREO");
		fechaAniv 			= rs.getString("FECHAANIVERSARIO");
		idFamilia 			= rs.getString("IDFAMILIA");
	}
	
	public void mapeaRegIdFam( Connection conn, String idFamilia) throws SQLException{
		
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT FAMILIA_ID, MATRICULA, NOMBRE, RELACION, " +
					" FECHANACIMIENTO, FECHAACTUALIZACION, ELIMINADO, CORREO, FECHAANIVERSARIO, IDFAMILIA "+
				"FROM ENOC.EXA_FAMILIA WHERE FAMILIA_ID = TO_NUMBER(?,'99999999')"); 
			ps.setString(1,idFamilia);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaFamiliaUtil|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT FAMILIA_ID, MATRICULA, NOMBRE, RELACION, " +
					" FECHANACIMIENTO, FECHAACTUALIZACION, ELIMINADO, CORREO, FECHAANIVERSARIO, IDFAMILIA "+
				"FROM ENOC.EXA_FAMILIA WHERE MATRICULA = ?"); 
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaFamiliaUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}