/**
 * 
 */
package aca.hca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author elifo
 *
 */
public class HcaTipoUtil {
	
	public boolean insertReg(Connection conn, HcaTipo hcaTipo) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.HCA_TIPO(TIPO_ID, TIPO_NOMBRE, ORDEN)" +
				" VALUES(TO_NUMBER(?, '99'), ?, TO_NUMBER(?, '99'))");
			
			ps.setString(1, hcaTipo.getTipoId());
			ps.setString(2, hcaTipo.getTipoNombre());			
			ps.setString(3, hcaTipo.getOrden());
			
			if(ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaTipoUtil|insertReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, HcaTipo hcaTipo) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.HCA_TIPO" + 
				" SET TIPO_NOMBRE = ?," +
				" ORDEN = TO_NUMBER(?, '99')" +
				" WHERE TIPO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, hcaTipo.getTipoNombre());
			ps.setString(2, hcaTipo.getOrden());
			ps.setString(3, hcaTipo.getTipoId());
						
			if(ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaTipoUtil|updateReg|:"+ex);		
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String tipoId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.HCA_TIPO"+ 
				" WHERE TIPO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, tipoId);
			
			if(ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaTipoUtil|deleteReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}
	
	public HcaTipo mapeaRegId(Connection con, String tipoId) throws SQLException{
		HcaTipo hcaTipo = new HcaTipo();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT TIPO_ID, TIPO_NOMBRE, ORDEN FROM ENOC.HCA_TIPO" + 
					" WHERE TIPO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, tipoId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				hcaTipo.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaTipoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return hcaTipo;
	}
	
	public boolean existeReg(Connection conn, String tipoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.HCA_TIPO" + 
					" WHERE TIPO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, tipoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaTipoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}			
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT MAX(TIPO_ID)+1 AS MAXIMO FROM ENOC.HCA_TIPO"); 
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			if(maximo == null)
				maximo = "1";
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaTipoUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return maximo;
	}
	
	public ArrayList<HcaTipo> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<HcaTipo> lisTipo	= new ArrayList<HcaTipo>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT TIPO_ID, TIPO_NOMBRE, ORDEN" +
					" FROM ENOC.HCA_TIPO "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				HcaTipo tipo = new HcaTipo();
				tipo.mapeaReg(rs);
				lisTipo.add(tipo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaTipoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisTipo;
	}
}