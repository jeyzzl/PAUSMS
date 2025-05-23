package aca.alumno;
import java.sql.*;

public class AlumDescuento{
	
	private String codigoPersonal	= "";
	private String cargaId			= "";
	private String descuentoId		= "";
	private String fecha			= "";
	private String matricula		= "";
	private String tipoMatricula	= "";
	private String ensenanza		= "";
	private String tipoEnsenanza	= "";
	private String internado		= "";
	private String tipoInternado	= "";
	private String total			= "";
	private String observaciones	= "";
	private String usuario			= "";
	private String aplicado			= "";
	
	public AlumDescuento(){
		
		codigoPersonal		= "";
		cargaId				= "";
		descuentoId			= ""; 
		fecha				= "";
		matricula			= "";
		tipoMatricula		= "";
		ensenanza			= "";
		tipoEnsenanza		= "";
		internado			= "";
		tipoInternado		= "";
		total				= "";
		observaciones		= "";
		usuario      		= "";
		aplicado 			= "N";  
	}
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getDescuentoId() {
		return descuentoId;
	}

	public void setDescuentoId(String descuentoId) {
		this.descuentoId = descuentoId;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getTipoMatricula() {
		return tipoMatricula;
	}

	public void setTipoMatricula(String tipoMatricula) {
		this.tipoMatricula = tipoMatricula;
	}

	public String getEnsenanza() {
		return ensenanza;
	}

	public void setEnsenanza(String ensenanza) {
		this.ensenanza = ensenanza;
	}

	public String getTipoEnsenanza() {
		return tipoEnsenanza;
	}

	public void setTipoEnsenanza(String tipoEnsenanza) {
		this.tipoEnsenanza = tipoEnsenanza;
	}

	public String getInternado() {
		return internado;
	}

	public void setInternado(String internado) {
		this.internado = internado;
	}

	public String getTipoInternado() {
		return tipoInternado;
	}

	public void setTipoInternado(String tipoInternado) {
		this.tipoInternado = tipoInternado;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}	

	/**
	 * @return the aplicado
	 */
	public String getAplicado() {
		return aplicado;
	}

	/**
	 * @param aplicado the aplicado to set
	 */
	public void setAplicado(String aplicado) {
		this.aplicado = aplicado;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal  	= rs.getString("CODIGO_PERSONAL");
		cargaId         	= rs.getString("CARGA_ID");
		descuentoId     	= rs.getString("DESCUENTO_ID"); 
		fecha           	= rs.getString("FECHA");
		matricula    		= rs.getString("MATRICULA");
		tipoMatricula 		= rs.getString("TIPO_MATRICULA");
		ensenanza       	= rs.getString("ENSENANZA");
		tipoEnsenanza   	= rs.getString("TIPO_ENSENANZA");
		internado       	= rs.getString("INTERNADO");
		tipoInternado     	= rs.getString("TIPO_INTERNADO");
		total              	= rs.getString("TOTAL");
		observaciones    	= rs.getString("OBSERVACIONES");
		usuario         	= rs.getString("USUARIO");
		aplicado			= rs.getString("APLICADO");
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal, String cargaId, String descuentoId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, CARGA_ID, DESCUENTO_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA ,"
					+ " MATRICULA, TIPO_MATRICULA, ENSENANZA, TIPO_ENSENANZA, INTERNADO, TIPO_INTERNADO, TOTAL, OBSERVACIONES, USUARIO, APLICADO"
					+ " FROM ENOC.ALUM_DESCUENTO"
					+ " WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND DESCUENTO_ID = TO_NUMBER(?, '99') ");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			ps.setString(3, descuentoId);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumDescuentoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
}