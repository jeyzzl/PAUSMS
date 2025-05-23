package aca.leg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LegPermisosUtil {
	
	
	public boolean insertReg(Connection conn, LegPermisos permiso ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.LEG_PERMISOS(CODIGO, USUARIO_ALTA, USUARIO_BAJA, FECHA_INI, FECHA_LIM, STATUS, FOLIO) " +
				" VALUES(?, ?, ?, TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'),?,TO_NUMBER(?,'99'))");			
			ps.setString(1, permiso.getCodigo());	
			ps.setString(2, permiso.getUsuarioAlta());
			ps.setString(3, permiso.getUsuarioBaja());
			ps.setString(4, permiso.getFechaIni());
			ps.setString(5, permiso.getFechaLim());
			ps.setString(6, permiso.getStatus());
			ps.setString(7, permiso.getFolio());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegPermisos|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, LegPermisos permiso ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.LEG_PERMISOS"+ 
				" SET USUARIO_ALTA = ?," +
				" USUARIO_BAJA = ?," +
				" FECHA_INI = TO_DATE(?,'DD/MM/YYYY')," +
				" FECHA_LIM = TO_DATE(?,'DD/MM/YYYY')," +
				" STATUS = ?,"+
				" WHERE CODIGO = ? " +
				" AND FOLIO = TO_NUMBER(?,'99')");		
				
			ps.setString(1, permiso.getUsuarioAlta());
			ps.setString(2, permiso.getUsuarioBaja());
			ps.setString(3, permiso.getFechaIni());
			ps.setString(4, permiso.getFechaLim());
			ps.setString(5, permiso.getStatus());
			ps.setString(6, permiso.getCodigo());
			ps.setString(7, permiso.getFolio());
			
			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegPermisos|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String codigo, String folio) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.LEG_PERMISOS "+ 
				"WHERE CODIGO = ? AND FOLIO= TO_NUMBER(?,'99')");
			ps.setString(1, codigo);
			ps.setString(2, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegPermisos|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean existeReg(Connection conn, String codigo, String folio) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 			rs	= null;
		PreparedStatement 	ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.LEG_PERMISOS WHERE  CODIGO= ? " + 
					"AND FOLIO= TO_NUMBER(?,'99') ");
			ps.setString(1,codigo);
			ps.setString(2,folio);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegPermisos|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
		
	}
	
	public ArrayList<LegPermisos> getLista(Connection conn, String codigoPersonal, String orden) throws SQLException{
		ArrayList<LegPermisos> lisExdoc 	= new ArrayList<LegPermisos>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando="SELECT CODIGO, FOLIO," +
					" USUARIO_ALTA," +
					" USUARIO_BAJA," +
					" TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI," +
					" TO_CHAR(FECHA_LIM,'DD/MM/YYYY') AS FECHA_LIM," +
					" STATUS " +
					" FROM ENOC.LEG_PERMISOS " + 
					"WHERE CODIGO = '"+codigoPersonal+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				LegPermisos doc = new LegPermisos();
				doc.mapeaReg(rs);
				lisExdoc.add(doc);
			}
		}catch (Exception ex){
			System.out.println("Error - aca.leg.LegPermisosUtil|getLista|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(st!=null) st.close();
		}
		
		return lisExdoc;
	}
}

	
	
	
	
	
	
	
	
	
	
	
	
	
	

	