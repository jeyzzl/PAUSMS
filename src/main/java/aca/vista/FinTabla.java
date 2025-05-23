package aca.vista;
import java.sql.*;

public class FinTabla {
	private String tablaId;	
	private String cargaId;
	private String carreraId;
	private String modalidadId;
	private String matricula;
	private String porMatricula;
	private String legales;
	private String porLegales;
	private String internado;
	private String porInternado;
	private String acfe;
	private String noAcfe;
	private String porCredito;
	private String status;
	
	public FinTabla(){
		tablaId			= "0";	
		cargaId			= "";		
		carreraId		= "";
		modalidadId		= "0";
		matricula		= "0";
		porMatricula	= "0";
		legales			= "0";
		porLegales		= "0";
		internado 		= "";
		porInternado 	= "";
		acfe 			= "";
		noAcfe 			= "";
		porCredito 		= "";
		status 			= "";
	}
	
	public String getTablaId() {
		return tablaId;
	}

	public void setTablaId(String tablaId) {
		this.tablaId = tablaId;
	}

	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	public String getModalidadId() {
		return modalidadId;
	}

	public void setModalidadId(String modalidadId) {
		this.modalidadId = modalidadId;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getPorMatricula() {
		return porMatricula;
	}

	public void setPorMatricula(String porMatricula) {
		this.porMatricula = porMatricula;
	}

	public String getLegales() {
		return legales;
	}

	public void setLegales(String legales) {
		this.legales = legales;
	}

	public String getPorLegales() {
		return porLegales;
	}

	public void setPorLegales(String porLegales) {
		this.porLegales = porLegales;
	}

	public String getInternado() {
		return internado;
	}

	public void setInternado(String internado) {
		this.internado = internado;
	}

	public String getPorInternado() {
		return porInternado;
	}

	public void setPorInternado(String porInternado) {
		this.porInternado = porInternado;
	}

	public String getAcfe() {
		return acfe;
	}

	public void setAcfe(String acfe) {
		this.acfe = acfe;
	}

	public String getNoAcfe() {
		return noAcfe;
	}

	public void setNoAcfe(String noAcfe) {
		this.noAcfe = noAcfe;
	}

	public String getPorCredito() {
		return porCredito;
	}

	public void setPorCredito(String porCredito) {
		this.porCredito = porCredito;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		tablaId			= rs.getString("TABLA_ID");
		cargaId			= rs.getString("CARGA_ID");
		carreraId		= rs.getString("CARRERA_ID");		
		modalidadId		= rs.getString("MODALIDAD_ID");
		matricula		= rs.getString("MATRICULA");
		porMatricula	= rs.getString("PORMATRICULA");		
		legales			= rs.getString("LEGALES");
		porLegales		= rs.getString("PORLEGALES");
		internado 		= rs.getString("INTERNADO");
		porInternado 	= rs.getString("PORINTERNADO");		
		acfe 			= rs.getString("ACFE");
		noAcfe 			= rs.getString("NOACFE");
		porCredito 		= rs.getString("PORCREDITO");
		status 			= rs.getString("STATUS");
	}
	
	public void mapeaRegId( Connection conn, String mentorId ) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		
		try{
			ps = conn.prepareStatement("SELECT TABLA_ID, CARGA_ID, CARRERA_ID, MODALIDAD_ID, MATRICULA, PORMATRICULA,"
					+ " LEGALES, PORLEGALES, INTERNADO, PORINTERNADO, ACFE, NOACFE, PORCREDITO, STATUS"
					+ " FROM ENOC.FIN_TABLA");
			ps.setString(1, mentorId);		
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.FinTabla|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
	}
}