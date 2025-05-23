package aca.conva;
//Bean de datos academicos del alumno
import java.sql.*;

public class ConvEvento{
	
	private String convalidacionId;
	private String fecha;
	private String usuario;
	private String codigoPersonal;
	private String planId;	
	private String estado;
	private String comentario;
	private String institucion;
	private String programa;
	private String tipo;
	private String dictamen;
	private String tipoConv;
	private String periodo;
			
	public ConvEvento(){
		convalidacionId 	= "";
		fecha				= "";
		usuario				= "";
		codigoPersonal		= "";
		planId				= "";
		estado				= "";
		comentario			= "";
		institucion			= "UNIVERSIDAD DE MONTEMORELOS";
		programa			= "x";
		tipo				= "-";
		dictamen			= "-";
		tipoConv			= "-";
		periodo				= "-";
	}	

	/**
	 * @return Returns the codigoPersonal.
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	/**
	 * @param codigoPersonal The codigoPersonal to set.
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}	

	/**
	 * @return Returns the comentario.
	 */
	public String getComentario() {
		return comentario;
	}

	/**
	 * @param comentario The comentario to set.
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	/**
	 * @return Returns the convalidacionId.
	 */
	public String getConvalidacionId() {
		return convalidacionId;
	}

	/**
	 * @param convalidacionId The convalidacionId to set.
	 */
	public void setConvalidacionId(String convalidacionId) {
		this.convalidacionId = convalidacionId;
	}

	/**
	 * @return Returns the estado.
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado The estado to set.
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return Returns the fecha.
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha The fecha to set.
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return Returns the planId.
	 */
	public String getPlanId() {
		return planId;
	}

	/**
	 * @param planId The planId to set.
	 */
	public void setPlanId(String planId) {
		this.planId = planId;
	}

	/**
	 * @return Returns the usuario.
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario The usuario to set.
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	/**
	 * @return Returns the institucion.
	 */
	public String getInstitucion() {
		return institucion;
	}
	
	/**
	 * @param institucion The institucion to set.
	 */
	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}
	
	/**
	 * @return Returns the programa.
	 */
	public String getPrograma() {
		return programa;
	}
	
	/**
	 * @param programa The programa to set.
	 */
	public void setPrograma(String programa) {
		this.programa = programa;
	}
	
	/**
	 * @return Returns the tipo.
	 */
	public String getTipo() {
		return tipo;
	}
	
	/**
	 * @param programa The tipo to set.
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDictamen() {
		return dictamen;
	}

	public void setDictamen(String dictamen) {
		this.dictamen = dictamen;
	}

	public String getTipoConv() {
		return tipoConv;
	}

	public void setTipoConv(String tipo_conv) {
		this.tipoConv = tipo_conv;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		convalidacionId 	= rs.getString("CONVALIDACION_ID");
		fecha 				= rs.getString("FECHA");
		usuario 			= rs.getString("USUARIO");
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		planId 				= rs.getString("PLAN_ID");		
		estado				= rs.getString("ESTADO");
		comentario			= rs.getString("COMENTARIO");
		institucion			= rs.getString("INSTITUCION");
		programa			= rs.getString("PROGRAMA");
		tipo				= rs.getString("TIPO");
		dictamen			= rs.getString("DICTAMEN");
		tipoConv			= rs.getString("TIPO_CONV");
		periodo				= rs.getString("PERIODO");
	}
	
	public void mapeaRegId( Connection conn, String convalidacionId ) throws SQLException{	
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CONVALIDACION_ID, TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, USUARIO, CODIGO_PERSONAL, " +
				"PLAN_ID, ESTADO, COMENTARIO, INSTITUCION, PROGRAMA, TIPO, DICTAMEN, TIPO_CONV, PERIODO "+
				"FROM ENOC.CONV_EVENTO WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999')"); 
			ps.setString(1, convalidacionId);		
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEvento|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}	
				
	}
}