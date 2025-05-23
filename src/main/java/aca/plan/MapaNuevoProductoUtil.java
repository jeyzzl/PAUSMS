package aca.plan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MapaNuevoProductoUtil {
	
	public boolean insertReg(Connection conn, MapaNuevoProducto mapaNuevoProducto) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.MAPA_NUEVO_PRODUCTO"
					+ " (CURSO_ID, UNIDAD_ID, PRODUCTO_ID, DESCRIPCION, TIPO) "
					+ " VALUES( TO_NUMBER(?, '9999999'),TO_NUMBER(?, '99'),TO_NUMBER(?, '99'), ?, ?)");
					
			ps.setString(1, mapaNuevoProducto.getCursoId());
			ps.setString(2, mapaNuevoProducto.getUnidadId());
			ps.setString(3, mapaNuevoProducto.getProductoId());
			ps.setString(4, mapaNuevoProducto.getDescripcion());
			ps.setString(5, mapaNuevoProducto.getTipo());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoProductoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, MapaNuevoProducto mapaNuevoProducto) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MAPA_NUEVO_PRODUCTO"
					+ " SET DESCRIPCION = ?,"
					+ " TIPO = ?"
					+ " WHERE CURSO_ID = TO_NUMBER(?,'9999999')"
					+ " AND UNIDAD_ID = TO_NUMBER(?, '99')"
					+ " AND PRODUCTO_ID = TO_NUMBER(?,'99')");
			
			ps.setString(1, mapaNuevoProducto.getDescripcion());
			ps.setString(2, mapaNuevoProducto.getTipo());
			ps.setString(3, mapaNuevoProducto.getCursoId());
			ps.setString(4, mapaNuevoProducto.getUnidadId());
			ps.setString(5, mapaNuevoProducto.getProductoId());
			
			if (ps.executeUpdate()== 1){
				ok = true;	
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoProductoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String cursoId, String unidadId, String productoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MAPA_NUEVO_PRODUCTO"
					+ " WHERE CURSO_ID = TO_NUMBER(?,'9999999')"
					+ " AND UNIDAD_ID = TO_NUMBER(?, '99')"
					+ " AND PRODUCTO_ID = TO_NUMBER(?,'99')");
			
			ps.setString(1, cursoId);
			ps.setString(2, unidadId);
			ps.setString(3, productoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoProductoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String cursoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MAPA_NUEVO_PRODUCTO"
					+ " WHERE CURSO_ID = TO_NUMBER(?,'9999999')");
			
			ps.setString(1, cursoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoProductoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public MapaNuevoProducto mapeaRegId( Connection conn, String cursoId, String unidadId, String productoId) throws SQLException{
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		MapaNuevoProducto mapa 	= new MapaNuevoProducto();
		
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
				mapa.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoProductoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}
	
	public boolean existeReg(Connection conn, String cursoId, String unidadId, String productoId) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MAPA_NUEVO_PRODUCTO " 
					+ " WHERE CURSO_ID = TO_NUMBER(?,'9999999')"
					+ " AND UNIDAD_ID = TO_NUMBER(?, '99')"
					+ " AND PRODUCTO_ID = TO_NUMBER(?,'99')");
			
			ps.setString(1, cursoId);
			ps.setString(2, unidadId);
			ps.setString(3, productoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoProductoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String cursoId, String unidadId ) throws SQLException{
		String maximo		 	= "1";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(PRODUCTO_ID)+1,1) AS MAXIMO FROM ENOC.MAPA_NUEVO_PRODUCTO "  
					+ " WHERE CURSO_ID = TO_NUMBER(?,'9999999')"
					+ " AND UNIDAD_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, cursoId);
			ps.setString(2, unidadId);		
			
			rs = ps.executeQuery();
			
			if (rs.next()){
				maximo = rs.getString("MAXIMO")==null?"1":rs.getString("MAXIMO");
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoProductoUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;
	}
	
	// Lista de productos en una unidad
	public ArrayList<MapaNuevoProducto> getListUnidad(Connection conn, String cursoId, String unidadId, String orden) throws SQLException{
		
		ArrayList<MapaNuevoProducto> listProductos		= new ArrayList<MapaNuevoProducto>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT CURSO_ID, UNIDAD_ID, PRODUCTO_ID, DESCRIPCION, TIPO"
				+ " FROM ENOC.MAPA_NUEVO_PRODUCTO" 
				+ " WHERE CURSO_ID = TO_NUMBER('"+cursoId+"', '9999999')"
				+ " AND UNIDAD_ID = TO_NUMBER('"+unidadId+"', '99') "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MapaNuevoProducto producto = new MapaNuevoProducto();
				producto.mapeaReg(rs);
				listProductos.add(producto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoProductoUtil|getListPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return listProductos;	
	}

}
