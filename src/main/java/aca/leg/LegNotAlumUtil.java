package aca.leg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LegNotAlumUtil {
	

	public boolean insertReg(Connection conn, LegNotAlum legNot) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.LEG_NOT_ALUM(CODIGO,FOLIO, NOTIFICACION_ID, FECHA, ESTADO ) " +
				" VALUES(?,TO_NUMBER(?,'99'),TO_NUMBER(?,'99'), TO_DATE(?,'DD/MM/YYYY'),?)");			
			ps.setString(1, legNot.getCodigo());
			ps.setString(2, legNot.getFolio());
			ps.setString(3, legNot.getNotificacionId());
			ps.setString(4, legNot.getFecha());
			ps.setString(5, legNot.getEstado());
			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegNotAlum|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, LegNotAlum legNot) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.LEG_NOT_ALUM"+ 
				" SET NOTIFICACION_ID = ?," +
				" FECHA = TO_DATE(?,'DD/MM/YYYY')," +
				" ESTADO = ?," +
				" WHERE CODIGO = ? " +
				" AND FOLIO = TO_NUMBER(?,'99')");
			ps.setString(1, legNot.getNotificacionId());
			ps.setString(2, legNot.getFecha());
			ps.setString(3, legNot.getEstado());
			ps.setString(4, legNot.getCodigo());
			ps.setString(5, legNot.getFolio());
			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegNotAlum|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String codigo, String folio) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.LEG_NOT_ALUM "+ 
				"WHERE CODIGO = ? AND FOLIO= TO_NUMBER(?,'99')");
			ps.setString(1, codigo);
			ps.setString(2, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegNotAlum|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public LegNotAlum mapeaRegId(Connection conn, String codigo, String folio) throws SQLException{
		LegNotAlum legNot = new LegNotAlum();
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		try{
			ps=conn.prepareStatement("SELECT CODIGO, FOLIO," +
					" NOTIFICACION_ID," +
					" TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA," +
					" ESTADO" +
					" FROM ENOC.LEG_NOT_ALUM " + 
					" WHERE CODIGO = ?" +
					" AND FOLIO= TO_NUMBER(?,'99')");
			ps.setString(1, codigo);
			ps.setString(2, folio);
			rs=ps.executeQuery();
			if(rs.next()){
				legNot.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegNotAlum|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return legNot;
	}
	
	public boolean existeReg(Connection conn, String codigo, String folio) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 			rs	= null;
		PreparedStatement 	ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.LEG_NOT_ALUM WHERE  CODIGO= ? " + 
					"AND FOLIO= TO_NUMBER(?,'99') ");
			ps.setString(1,codigo);
			ps.setString(2,folio);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegNotAlum|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
		
	}
	
	public ArrayList<LegNotAlum> getLista(Connection conn, String codigoPersonal, String orden) throws SQLException{
		ArrayList<LegNotAlum> lisNotAlum 	= new ArrayList<LegNotAlum>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando="SELECT CODIGO, FOLIO," +
					" NOTIFICACION_ID," +
					" TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA," +
					" ESTADO " +
					" FROM ENOC.LEG_NOT_ALUM " + 
					"WHERE CODIGO = '"+codigoPersonal+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				LegNotAlum doc = new LegNotAlum();
				doc.mapeaReg(rs);
				lisNotAlum.add(doc);
			}
		}catch (Exception ex){
			System.out.println("Error - aca.leg.LegNotAlumUtil|getLista|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(st!=null) st.close();
		}
		
		return lisNotAlum;
	}
}