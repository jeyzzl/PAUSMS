package aca.plan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MapaNuevoProducto {
	private String cursoId;
	private String unidadId;
	private String productoId;
	private String descripcion;
	private String tipo;
	
	public MapaNuevoProducto(){
		cursoId = "";
		unidadId = "";
		productoId = "";
		descripcion = "";
		tipo = "";
	}

	public String getCursoId() {
		return cursoId;
	}

	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	public String getUnidadId() {
		return unidadId;
	}

	public void setUnidadId(String unidadId) {
		this.unidadId = unidadId;
	}

	public String getProductoId() {
		return productoId;
	}

	public void setProductoId(String productoId) {
		this.productoId = productoId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		cursoId 		= rs.getString("CURSO_ID");
		unidadId 		= rs.getString("UNIDAD_ID");
		productoId 		= rs.getString("PRODUCTO_ID");
		descripcion 	= rs.getString("DESCRIPCION");
		tipo 			= rs.getString("TIPO");
	}
	
	public void mapeaRegId( Connection conn, String cursoId, String unidadId, String productoId) throws SQLException{
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		
		
		try{
			ps = conn.prepareStatement("SELECT CURSO_ID, UNIDAD_ID, PRODUCTO_ID,"
					+ " DESCRIPCION, TIPO"
					+ " FROM ENOC.MAPA_NUEVO_PRODUCTO"
					+ " WHERE CURSO_ID = TO_NUMBER(?,'9999999')"
					+ " AND UNIDAD_ID = TO_NUMBER(?, '99')"
					+ " AND PRODUCTO_ID = TO_NUMBER(?,'99')");
			
			ps.setString(1, cursoId);
			ps.setString(2, unidadId);
			ps.setString(3, productoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoProductoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
	}
	
}
