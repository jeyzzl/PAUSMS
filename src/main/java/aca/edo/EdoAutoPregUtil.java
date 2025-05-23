package aca.edo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EdoAutoPregUtil {
	
	public boolean insertReg(Connection conn, EdoAutoPreg preg) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("INSERT INTO" +
				" EDO_AUTOPREG(PREGUNTA_ID, EDO_ID, PREGUNTA, TIPO, ORDEN)" +
				" VALUES(TO_NUMBER(?, '99'), TO_NUMBER(?, '99999'), ?, ?, ?)");
			
			ps.setString(1, preg.getPreguntaId());
			ps.setString(2, preg.getEdoId());			
			ps.setString(3, preg.getPregunta());
			ps.setString(4, preg.getTipo());
			ps.setString(5, preg.getOrden());
			
			if(ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAutoPregUtil|insertReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, EdoAutoPreg preg) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EDO_AUTOPREG" + 
				" SET PREGUNTA = ?," +
				" TIPO = ?," +
				" ORDEN = ?" +
				" WHERE PREGUNTA_ID = TO_NUMBER(?, '99')" +
				" AND EDO_ID = TO_NUMBER(?, '99999')");
			
			ps.setString(1, preg.getPregunta());
			ps.setString(2, preg.getTipo());
			ps.setString(3, preg.getOrden());
			ps.setString(4, preg.getPreguntaId());
			ps.setString(5, preg.getEdoId());
						
			if(ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAutoPregUtil|updateReg|:"+ex);		 
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String preguntaId, String edoId) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.EDO_AUTOPREG"+ 
				" WHERE PREGUNTA_ID = TO_NUMBER(?, '99')" +
				" AND EDO_ID = TO_NUMBER(?, '99999')");
			
			ps.setString(1, preguntaId);
			ps.setString(2, edoId);
			
			if(ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAutoPregUtil|deleteReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}
	
	public EdoAutoPreg mapeaRegId(Connection con, String preguntaId, String edoId) throws SQLException{
		EdoAutoPreg preg = new EdoAutoPreg();
		PreparedStatement ps = null;  
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT PREGUNTA_ID, EDO_ID, PREGUNTA, TIPO, ORDEN" +
					" FROM ENOC.EDO_AUTOPREG" + 
					" WHERE PREGUNTA_ID = TO_NUMBER(?, '99')" +
					" AND EDO_ID = TO_NUMBER(?, '99999')");
			
			ps.setString(1, preguntaId);
			ps.setString(2, edoId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				preg.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAutoPregUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return preg;
	}
	
	public boolean existeReg(Connection conn, String preguntaId, String edoId) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EDO_AUTOPREG" + 
					" WHERE PREGUNTA_ID = TO_NUMBER(?, '99')" +
					" AND EDO_ID = TO_NUMBER(?, '99999')");
		
		ps.setString(1, preguntaId);
		ps.setString(2, edoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAutoPregUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String edoId) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(PREGUNTA_ID)+1,1) AS MAXIMO FROM ENOC.EDO_AUTOPREG" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99999')");
			
			ps.setString(1, edoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAutoPregUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
		return maximo;
	}
	
	public ArrayList<EdoAutoPreg> getListEdo(Connection conn, String edoId, String orden ) throws SQLException{
		
		ArrayList<EdoAutoPreg> lisEAP	= new ArrayList<EdoAutoPreg>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";
		
		try{
			comando = "SELECT PREGUNTA_ID, EDO_ID, PREGUNTA, TIPO, ORDEN" +
					" FROM ENOC.EDO_AUTOPREG" + 
					" WHERE EDO_ID = "+edoId+" "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				EdoAutoPreg eap = new EdoAutoPreg();
				eap.mapeaReg(rs);
				lisEAP.add(eap);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAutoPregUtil|getListEdo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEAP;
	}
}