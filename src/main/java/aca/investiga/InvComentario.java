package aca.investiga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @author Alvin
 *
 */
public class InvComentario {
	private String proyectoId;
	private String folio;
	private String fecha;
	private String codigoPersonal;
	private String tipo;
	private String comentario;

	public InvComentario(){
		proyectoId 		= "";
		folio			= "";
		fecha			= "";
		codigoPersonal	= "";
		tipo			= "";
		comentario		= "";
	}
	public String getProyectoId() {
		return proyectoId;
	}

	public void setProyectoId(String proyectoId) {
		this.proyectoId = proyectoId;
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

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
		
	public void mapeaReg(ResultSet rs ) throws SQLException{
		proyectoId		= rs.getString("PROYECTO_ID");
		folio			= rs.getString("FOLIO");
		fecha			= rs.getString("FECHA");
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		tipo			= rs.getString("TIPO");
		comentario		= rs.getString("COMENTARIO");
		
	}
	
	public void mapeaRegId(Connection con, String proyectoId, String folio) throws SQLException{	
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT PROYECTO_ID, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, CODIGO_PERSONAL, TIPO, COMENTARIO"
					+ " FROM ENOC.INV_COMENTARIO WHERE PROYECTO_ID = ? AND FOLIO = TO_NUMBER(?,'999')"); 
			ps.setString(1, proyectoId);
			ps.setString(2, folio);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvComentario|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}

}