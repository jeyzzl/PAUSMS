// Bean de ActivoImagen
package  aca.pg.archivo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

@Component
public class AlumFoto{
	private String codigoPersonal;
	private String tipo;
	private byte[] foto;	
	private String fecha;
	private String usuario;
	private String rechazada;
	private String comentario;
	
	public AlumFoto(){
		codigoPersonal	= "";
		tipo			= "O";
		foto			= null;		
		fecha 			= "";
		usuario 		= "-";
		rechazada 		= "N";
		comentario 		= "-";
	}
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getRechazada() {
		return rechazada;
	}

	public void setRechazada(String rechazada) {
		this.rechazada = rechazada;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		tipo				= rs.getString("TIPO");
		foto				= rs.getBytes("FOTO");		
		fecha				= rs.getString("FECHA");
		usuario				= rs.getString("USUARIO");
		rechazada			= rs.getString("RECHAZADA");
		comentario			= rs.getString("COMENTARIO");
	}
	
	public void mapeaRegCorto(ResultSet rs ) throws SQLException{
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		tipo				= rs.getString("TIPO");
		fecha				= rs.getString("FECHA");
		usuario				= rs.getString("USUARIO");
		rechazada			= rs.getString("RECHAZADA");
		comentario			= rs.getString("COMENTARIO");
	}
	
}