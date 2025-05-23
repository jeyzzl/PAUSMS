//Bean del Catalogo Cargas
package  aca.carga;

import java.sql.*;

public class CargaArchivo{
	private String cursoCargaId;
	private String folio;
	private String fecha;
	private String nombre;
	private String evaluacionId;
	private String actividadId;
	private String usuarioOrigen;
	private String usuarioDestino;
	private String comentario;
	private String ruta;
	private String estado;
	
	public CargaArchivo(){
		cursoCargaId	= "";
		folio			= "";
		fecha			= "";
		nombre			= "";
		evaluacionId	= "0";
		actividadId		= "0";
		usuarioOrigen	= "";
		usuarioDestino	= "";
		comentario		= "";
		ruta			= "";
		estado			= "";
	}
	
	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEvaluacionId() {
		return evaluacionId;
	}

	public void setEvaluacionId(String evaluacionId) {
		this.evaluacionId = evaluacionId;
	}

	public String getActividadId() {
		return actividadId;
	}

	public void setActividadId(String actividadId) {
		this.actividadId = actividadId;
	}

	public String getUsuarioOrigen() {
		return usuarioOrigen;
	}

	public void setUsuarioOrigen(String usuarioOrigen) {
		this.usuarioOrigen = usuarioOrigen;
	}

	public String getUsuarioDestino() {
		return usuarioDestino;
	}

	public void setUsuarioDestino(String usuarioDestino) {
		this.usuarioDestino = usuarioDestino;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		cursoCargaId 	= rs.getString("CURSO_CARGA_ID");
		folio		 	= rs.getString("FOLIO");
		fecha			= rs.getString("FECHA");
		nombre	 		= rs.getString("NOMBRE");
		evaluacionId	= rs.getString("EVALUCION_ID");
		actividadId		= rs.getString("ACTIVIDAD_ID");
		usuarioOrigen 	= rs.getString("USUARIO_ORIGEN");
		usuarioDestino 	= rs.getString("USUARIO_DESTINO");
		comentario		= rs.getString("COMENTARIO");
		ruta 			= rs.getString("RUTA");
		estado 			= rs.getString("ESTADO");
	}
	
	public void mapeaRegId( Connection conn, String cursoCargaId ) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID, FOLIO, "+
				"TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, NOMRE, EVALUCION_ID, "+
				"ACTIVIDAD_ID, USUARIO_ORIGEN,USUARIO_DESTINO, COMENTARIO, RUTA, ESTADO "+
				"FROM ENOC.CARGA_GRUPO_ARCHIVO WHERE CURSO_CARGA_ID = ? "); 
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaArchivo|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}

}
