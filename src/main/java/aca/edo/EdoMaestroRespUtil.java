package aca.edo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EdoMaestroRespUtil {
	
	public boolean insertReg(Connection conn, EdoMaestroResp mresp) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("INSERT INTO" +
				" EDO_MAESTRORESP(EDO_ID, PREGUNTA_ID, CODIGO_PERSONAL, MAESTRO, RESPUESTA)" +
				" VALUES(TO_NUMBER(?, '99'), TO_NUMBER(?, '99999'), ?, ?, ?)");
					
			ps.setString(1, mresp.getEdoId());
			ps.setString(2, mresp.getPreguntaId());
			ps.setString(3, mresp.getCodigoPersonal());
			ps.setString(4, mresp.getMaestro());
			ps.setString(5, mresp.getRespuesta());
			
			if(ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoMaestroRespUtil|insertReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	
	public boolean updateReg(Connection conn, EdoMaestroResp mresp) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EDO_MAESTRORESP" + 
				" SET MAESTRO = ?," +
				" RESPUESTA = ?" +
				" WHERE EDO_ID = TO_NUMBER(?, '99999')" +
				" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
				" AND CODIGO_PERSONAL = ? ");
			
			ps.setString(1, mresp.getMaestro());
			ps.setString(2, mresp.getRespuesta());
			ps.setString(3, mresp.getEdoId());
			ps.setString(4, mresp.getPreguntaId());
			ps.setString(5, mresp.getCodigoPersonal());
						
			if(ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoMaestroRespUtil|updateReg|:"+ex);		 
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String edoId, String preguntaId, String codigoPersonal) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.EDO_MAESTRORESP "+ 
				" WHERE EDO_ID = TO_NUMBER(?, '99999')" +
				" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
				" AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, edoId);
			ps.setString(2, preguntaId);
			ps.setString(3, codigoPersonal);
			
			if(ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoMaestroRespUtil|deleteReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}

	public EdoMaestroResp mapeaRegId(Connection con, String edoId, String preguntaId, String codigoPersonal) throws SQLException{
		EdoMaestroResp mresp = new EdoMaestroResp();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement("SELECT * " +
					" FROM ENOC.EDO_MAESTRORESP" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99999')" +
					" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
					" AND CODIGO_PERSONAL = ? ");
			
			ps.setString(1, edoId);
			ps.setString(2, preguntaId);
			ps.setString(3, codigoPersonal);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mresp.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoMaestroRespUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return mresp;
	}
	
	public boolean existeReg(Connection conn, String edoId, String preguntaId, String codigoPersonal) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EDO_MAESTRORESP" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99999')" +
					" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
					" AND CODIGO_PERSONAL = ? ");
		
			ps.setString(1, edoId);
			ps.setString(2, preguntaId);
			ps.setString(3, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoMaestroRespUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return ok;
	}
	
	
	public ArrayList<EdoMaestroResp> getListAll (Connection conn, String orden ) throws SQLException{
		ArrayList<EdoMaestroResp> list 		= new ArrayList<EdoMaestroResp>();
		Statement st 						= conn.createStatement();
		ResultSet rs		 				= null;
		String comando						= "";
		
		try{
			comando = "SELECT * " +
					" FROM ENOC.EDO_MAESTRORESP "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				EdoMaestroResp obj = new EdoMaestroResp();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoMaestroRespUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public ArrayList<EdoMaestroResp> getListRespuestas (Connection conn, String edoId, String areaId, String codigoPersonal, String maestroId,  String orden ) throws SQLException{
		ArrayList<EdoMaestroResp> list 		= new ArrayList<EdoMaestroResp>();
		Statement st 						= conn.createStatement();
		ResultSet rs		 				= null;
		String comando						= "";
		
		try{
			comando = "SELECT * " +
					" 	FROM EDO_MAESTRORESP " +
					"	WHERE EDO_ID = '"+edoId+"' " +
					"	AND CODIGO_PERSONAL = '"+codigoPersonal+"'"  +
					"	AND MAESTRO = '"+maestroId+"'" +
					"   AND EDO_ID||PREGUNTA_ID IN (" +
					"		SELECT EDO_ID||PREGUNTA_ID " +
					" 		FROM ENOC.EDO_MAESTROPREG" +
					" 		WHERE EDO_ID = '"+edoId+"' " +
					"		AND AREA_ID = '"+areaId+"'" +
					"	) "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				EdoMaestroResp obj = new EdoMaestroResp();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoMaestroRespUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
}
