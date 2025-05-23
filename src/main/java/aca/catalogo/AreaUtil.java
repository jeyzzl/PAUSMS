// Clase Util para la tabla de Cat_Area
package aca.catalogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AreaUtil{

	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	

	public boolean insertReg(Connection conn, CatArea area ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_AREA(AREA_ID, NOMBRE_AREA, CODIGO_PERSONAL, TIPO_PROMEDIO)"
					+ " VALUES( ?, ?, ?, ?)");			
			ps.setString(1, area.getAreaId());
			ps.setString(2, area.getNombreArea());
			ps.setString(3, area.getCodigoPersonal());
			ps.setString(4, area.getTipoPromedio());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.AreaUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CatArea area ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_AREA "+ 
				"SET NOMBRE_AREA = ?, CODIGO_PERSONAL = ?, TIPO_PROMEDIO = ? WHERE AREA_ID = ? ");
			ps.setString(1, area.getNombreArea());
			ps.setString(2, area.getCodigoPersonal());
			ps.setString(3, area.getTipoPromedio());
			ps.setString(4, area.getAreaId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.AreaUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn, String areaId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_AREA "+ 
				"WHERE AREA_ID = ?");
			ps.setString(1, areaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.AreaUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CatArea mapeaRegId( Connection conn, String areaId) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		CatArea area 			= new CatArea();
		
		try{
			ps = conn.prepareStatement("SELECT AREA_ID, NOMBRE_AREA, CODIGO_PERSONAL, TIPO_PROMEDIO"
					+ " FROM ENOC.CAT_AREA WHERE AREA_ID = ?"); 
			ps.setString(1, areaId);
			rs = ps.executeQuery();
			if (rs.next()){
				area.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.AreaUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return area;
	}
	
	public boolean existeReg(Connection conn, String areaId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_AREA WHERE AREA_ID = ?"); 
			ps.setString(1,areaId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.AreaUtil|existeReg|:"+ex);
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }
		
		return ok;
	}
	public String maximoReg(Connection conn ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT MAX(AREA_ID)+1 MAXIMO FROM ENOC.CAT_AREA "); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.AreaUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}	
	
	public static String getTipoPromedio(Connection conn, String areaId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String tipoPromedio			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT TIPO_PROMEDIO FROM ENOC.CAT_AREA WHERE AREA_ID = ?  "); 
			ps.setString(1, areaId);
			rs = ps.executeQuery();
			if (rs.next())
				tipoPromedio = rs.getString("TIPO_PROMEDIO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.AreaUtil|getTipoPromedio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return tipoPromedio;
	}
	
	public ArrayList<CatArea> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatArea> lisArea = new ArrayList<CatArea>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT AREA_ID, NOMBRE_AREA, CODIGO_PERSONAL, TIPO_PROMEDIO FROM ENOC.CAT_AREA "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatArea area = new CatArea();
				area.mapeaReg(rs);
				lisArea.add(area);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.AreaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisArea;
	}
	
	public List<CatArea> getListAll( String orden ) throws SQLException{
		
		List<CatArea> lista = new ArrayList<CatArea>();
		
		try{
			String query = "SELECT * FROM ENOC.CAT_AREA";
			lista = enocJdbc.query(query, new CatAreaMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.AreaUtil|getListAll|:"+ex);
		}
		
		return lista;
	}
	
}