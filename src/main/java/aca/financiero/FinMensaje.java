package aca.financiero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FinMensaje {
	private String id;
	private String comentario;
	private String estado;
	private String tipo;
	
	public FinMensaje(){
		id				= "";
		comentario		= "";
		estado			= "";
		tipo			= "";
			
	}	
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		id				= rs.getString("ID");
		comentario		= rs.getString("COMENTARIO");
		estado			= rs.getString("ESTADO");
		tipo			= rs.getString("TIPO");
	}
	
	public void mapeaRegId(Connection con, String Id) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT ID, COMENTARIO, ESTADO, TIPO " +				
					" FROM ENOC.FIN_MENSAJE" + 
					" WHERE ID = '"+Id+"' ");
			

			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPermiso|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	
}