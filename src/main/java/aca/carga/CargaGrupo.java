// Bean del Catalogo de Grupos
package  aca.carga;

import java.sql.*;

public class CargaGrupo{
	private String cursoCargaId;
	private String cargaId;
	private String bloqueId;
	private String carreraId;
	private String codigoPersonal;
	private String grupo;
	private String modalidadId;
	private String fInicio;
	private String fFinal;
	private String fEntrega;
	private String restriccion;
	private String estado;
	private String horario;
	private String fEvaluacion;
	private String valeucas;
	private String numAlum;
	private String semanas;
	private String optativa;
	private String comentario;
	private String codigoOtro;
	private String precio;
	
	public CargaGrupo(){
		cursoCargaId	= "";
		cargaId			= "";
		bloqueId		= "";
		carreraId		= "";
		codigoPersonal	= "";
		grupo			= "";
		modalidadId		= "";
		fInicio			= "";
		fFinal			= "";
		fEntrega		= "";
		restriccion		= "";
		estado			= "";
		horario			= "";
		fEvaluacion		= "";
		valeucas		= "";
		numAlum			= "";
		semanas			= "";
		optativa		= "";
		comentario		= "-";
		codigoOtro		= "0";
		precio 			= "0";
	}
	
	public String getBloqueId() {
		return bloqueId;
	}

	public void setBloqueId(String bloqueId) {
		this.bloqueId = bloqueId;
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

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFEntrega() {
		return fEntrega;
	}

	public void setFEntrega(String entrega) {
		fEntrega = entrega;
	}

	public String getFEvaluacion() {
		return fEvaluacion;
	}

	public void setFEvaluacion(String evaluacion) {
		fEvaluacion = evaluacion;
	}

	public String getFFinal() {
		return fFinal;
	}

	public void setFFinal(String final1) {
		fFinal = final1;
	}

	public String getFInicio() {
		return fInicio;
	}

	public void setFInicio(String inicio) {
		fInicio = inicio;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getSemanas() {
		return semanas;
	}

	public void setSemanas(String semanas) {
		this.semanas = semanas;
	}

	public String getModalidadId() {
		return modalidadId;
	}

	public void setModalidadId(String modalidadId) {
		this.modalidadId = modalidadId;
	}

	public String getNumAlum() {
		return numAlum;
	}

	public void setNumAlum(String numAlum) {
		this.numAlum = numAlum;
	}

	public String getRestriccion() {
		return restriccion;
	}

	public void setRestriccion(String restriccion) {
		this.restriccion = restriccion;
	}

	public String getValeucas() {
		return valeucas;
	}

	public void setValeucas(String valeucas) {
		this.valeucas = valeucas;
	}	

	public String getOptativa() {
		return optativa;
	}

	public void setOptativa(String optativa) {
		this.optativa = optativa;
	}

	public String getComentario() {
		return comentario;
	}
	
	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}
	
	public String getCodigoOtro() {
		return codigoOtro;
	}

	public void setCodigoOtro(String codigoOtro) {
		this.codigoOtro = codigoOtro;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		cursoCargaId	= rs.getString("CURSO_CARGA_ID");
		cargaId 		= rs.getString("CARGA_ID");
		bloqueId		= rs.getString("BLOQUE_ID");
		carreraId	 	= rs.getString("CARRERA_ID");
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		grupo			= rs.getString("GRUPO");
		modalidadId		= rs.getString("MODALIDAD_ID");
		fInicio 		= rs.getString("F_INICIO");
		fFinal			= rs.getString("F_FINAL");
		fEntrega 		= rs.getString("F_ENTREGA");
		restriccion		= rs.getString("RESTRICCION");
		estado 			= rs.getString("ESTADO");
		horario			= rs.getString("HORARIO");
		fEvaluacion		= rs.getString("F_EVALUACION");
		valeucas		= rs.getString("VALEUCAS");
		numAlum			= rs.getString("NUM_ALUM");
		semanas			= rs.getString("SEMANAS");
		optativa		= rs.getString("OPTATIVA");
		comentario		= rs.getString("COMENTARIO");
		codigoOtro		= rs.getString("CODIGO_OTRO");
		precio			= rs.getString("PRECIO");
	}
	
	public void mapeaRegId( Connection conn, String cursoCargaId ) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT"+
				" CURSO_CARGA_ID,"+
				" CARGA_ID,"+
				" TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID,"+
				" CARRERA_ID,"+
				" COALESCE(CODIGO_PERSONAL,' ') AS CODIGO_PERSONAL,"+
				" COALESCE(GRUPO,' ') AS GRUPO,"+
				" TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID,"+
				" TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"+
				" TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"+
				" TO_CHAR(F_ENTREGA,'DD/MM/YYYY') AS F_ENTREGA,"+
				" RESTRICCION,"+
				" ESTADO,"+
				" HORARIO,"+
				" TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION," +
				" VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, COMENTARIO, CODIGO_OTRO, PRECIO"+
				" FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = ? "); 
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|mapeaRegId++|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
}