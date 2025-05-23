package aca.investiga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Karelly
 *
 */
public class InvBitacoraUtil {
	
	public boolean insertReg(Connection conn, InvBitacora bit ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.INV_BITACORA(PROYECTO_ID, FOLIO, FECHA, USUARIO, ESTADO_OLD, ESTADO_NEW) "
					+ "VALUES(?, TO_NUMBER(?,'999'), TO_DATE(?, 'DD/MM/YYYY HH24:MI:SS'), ?, ?, ?)");
			
			ps.setString(1, bit.getProyectoId());
			ps.setString(2, bit.getFolio());
			ps.setString(3, bit.getFecha());
			ps.setString(4, bit.getUsuario());
			ps.setString(5, bit.getEstadoOld());
			ps.setString(6, bit.getEstadoNew());
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvBitacora|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public InvBitacora mapeaRegId(Connection con, String proyectoId, String folio) throws SQLException{
		
		InvBitacora bit = new InvBitacora();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT PROYECTO_ID, FOLIO, TO_DATE(?, 'DD/MM/YYYY HH24:MI:SS') AS FECHA, USUARIO, ESTADO_OLD, ESTADO_NEW"
					+ " FROM ENOC.INV_BITACORA"
					+ " WHERE PROYECTO_ID = ? AND FOLIO = TO_NUMBER(?,'999')"); 
			ps.setString(1, proyectoId);
			ps.setString(2, folio);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				bit.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvBitacora|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return bit;
	}
	
	public boolean existeReg(Connection conn, String proyectoId, String folio) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.INV_BITACORA WHERE PROYECTO_ID = ? AND FOLIO = TO_NUMBER(?,'999') "); 
			ps.setString(1,proyectoId);
			ps.setString(2,folio);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvBitacora|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String proyectoId) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.INV_BITACORA WHERE PROYECTO_ID = ?"); 
			ps.setString(1,proyectoId);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvBitacora|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public String nombreEstado(Connection conn, String proyectoId) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.INV_BITACORA WHERE PROYECTO_ID = ?"); 
			ps.setString(1,proyectoId);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvBitacora|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}

	public ArrayList<InvBitacora> getListAll(Connection conn, String orden ) throws SQLException{
		ArrayList<InvBitacora> listProyectos	= new ArrayList<InvBitacora>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT PROYECTO_ID, FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA, USUARIO, ESTADO_OLD, ESTADO_NEW FROM ENOC.INV_BITACORA "+orden; 
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				InvBitacora proyecto = new InvBitacora();				
				proyecto.mapeaReg(rs);
				listProyectos.add(proyecto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvBitacoraUtil|getListAll|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }	
		}
		
		return listProyectos;
	}
	
	public ArrayList<InvBitacora> getListProyecto(Connection conn, String proyectoId, String orden ) throws SQLException{
		ArrayList<InvBitacora> listProyectos	= new ArrayList<InvBitacora>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = " SELECT PROYECTO_ID, FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA, USUARIO, ESTADO_OLD, ESTADO_NEW"
					+ " FROM ENOC.INV_BITACORA"
					+ " WHERE PROYECTO_ID = '"+proyectoId+"' "+orden; 
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				InvBitacora proyecto = new InvBitacora();				
				proyecto.mapeaReg(rs);
				listProyectos.add(proyecto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvBitacoraUtil|getListProyecto|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }	
		}
		
		return listProyectos;
	}
}