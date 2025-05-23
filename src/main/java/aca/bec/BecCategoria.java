package aca.bec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BecCategoria {
	private String categoriaId;
	private String categoriaNombre;
	private String categoriaUsuario;
	private String estado;
	private String pdf;
	
	public BecCategoria(){
		categoriaId			= "";
		categoriaNombre		= "";
		categoriaUsuario    = "";
		estado 				= "0";
		pdf					= "-";
	}
	
	public String getCategoriaId() {
		return categoriaId;
	}
	public void setCategoriaId(String categoriaId) {
		this.categoriaId = categoriaId;
	}
	
	public String getCategoriaNombre() {
		return categoriaNombre;
	}
	public void setCategoriaNombre(String categoriaNombre) {
		this.categoriaNombre = categoriaNombre;
	}

	
	public String getCategoriaUsuario() {
		return categoriaUsuario;
	}
	public void setCategoriaUsuario(String categoriaUsuario) {
		this.categoriaUsuario = categoriaUsuario;
	}
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPdf() {
		return pdf;
	}
	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	public void mapeaReg(ResultSet rs) throws SQLException{
		categoriaId			= rs.getString("CATEGORIA_ID");
		categoriaNombre		= rs.getString("CATEGORIA_NOMBRE");
		categoriaUsuario    = rs.getString("USUARIO");
		estado 				= rs.getString("ESTADO");
		pdf					= rs.getString("PDF");
	}
	
	public void mapeaRegId(Connection conn, String categoriaId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT  * FROM ENOC.BEC_CATEGORIA WHERE CATEGORIA_ID = TO_NUMBER(?,'999')"); 
			
			ps.setString(1,  categoriaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecCategoria|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
}