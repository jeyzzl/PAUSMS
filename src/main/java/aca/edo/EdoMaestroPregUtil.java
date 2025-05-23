package aca.edo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EdoMaestroPregUtil {
	
	public boolean insertReg(Connection conn, EdoMaestroPreg mpreg) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("INSERT INTO" +
				" EDO_MAESTROPREG(EDO_ID, PREGUNTA_ID, PREGUNTA, TIPO, ORDEN, AREA_ID, " +
				" COMENTARIO1, COMENTARIO2, COMENTARIO3, COMENTARIO4)" +
				" VALUES(TO_NUMBER(?, '99'), TO_NUMBER(?, '99999'), ?, ?, TO_NUMBER(?, '99'), " +
				" TO_NUMBER(?, '99'), ?, ?, ?, ?)");
					
			ps.setString(1, mpreg.getEdoId());
			ps.setString(2, mpreg.getPreguntaId());
			ps.setString(3, mpreg.getPregunta());
			ps.setString(4, mpreg.getTipo());
			ps.setString(5, mpreg.getOrden());
			ps.setString(6, mpreg.getAreaId());
			ps.setString(7, mpreg.getComentario1());
			ps.setString(8, mpreg.getComentario2());
			ps.setString(9, mpreg.getComentario3());
			ps.setString(10, mpreg.getComentario4());
			
			if(ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoMaestroPregUtil|insertReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	
	public boolean updateReg(Connection conn, EdoMaestroPreg mpreg) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EDO_MAESTROPREG" + 
				" SET PREGUNTA = ?," +
				" TIPO = ?," +
				" ORDEN = ?" +
				" AREA_ID = TO_NUMBER(?, '99')" +
				" COMENTARIO1 = ? " +
				" COMENTARIO2 = ? " +
				" COMENTARIO3 = ? " +
				" COMENTARIO4 = ? " +
				" WHERE PREGUNTA_ID = TO_NUMBER(?, '99')" +
				" AND EDO_ID = TO_NUMBER(?, '99999')");
			
			ps.setString(1, mpreg.getPregunta());
			ps.setString(2, mpreg.getTipo());
			ps.setString(3, mpreg.getOrden());
			ps.setString(4, mpreg.getAreaId());
			ps.setString(5, mpreg.getComentario1());
			ps.setString(6, mpreg.getComentario2());
			ps.setString(7, mpreg.getComentario3());
			ps.setString(8, mpreg.getComentario4());
			ps.setString(9, mpreg.getPreguntaId());
			ps.setString(10, mpreg.getEdoId());
						
			if(ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoMaestroPregUtil|updateReg|:"+ex);		 
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String preguntaId, String edoId) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.EDO_MAESTROPREG "+ 
				" WHERE PREGUNTA_ID = TO_NUMBER(?, '99')" +
				" AND EDO_ID = TO_NUMBER(?, '99999')");
			
			ps.setString(1, preguntaId);
			ps.setString(2, edoId);
			
			if(ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoMaestroPregUtil|deleteReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}
	
	public EdoMaestroPreg mapeaRegId(Connection con, String preguntaId, String edoId) throws SQLException{
		EdoMaestroPreg mpreg = new EdoMaestroPreg();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement("SELECT PREGUNTA_ID, EDO_ID, PREGUNTA, TIPO, ORDEN, AREA_ID," +
					"COMENTARIO1, COMENTARIO2, COMENTARIO3, COMENTARIO4" +
					" FROM ENOC.EDO_MAESTROPREG" + 
					" WHERE PREGUNTA_ID = TO_NUMBER(?, '99')" +
					" AND EDO_ID = TO_NUMBER(?, '99999')");
			
			ps.setString(1, preguntaId);
			ps.setString(2, edoId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mpreg.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoMaestroPregUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return mpreg;
	}
	
	public boolean existeReg(Connection conn, String preguntaId, String edoId) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EDO_MAESTROPREG" + 
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
			System.out.println("Error - aca.edo.EdoMaestroPregUtil|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT COALESCE(MAX(PREGUNTA_ID)+1,1) AS MAXIMO FROM ENOC.EDO_MAESTROPREG" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99999')");
			
			ps.setString(1, edoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoMaestroPregUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
		return maximo;
	}
	
	public static int getNumPreguntas(Connection conn, String edoId, String tipo) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		int total				= 0;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(COUNT(PREGUNTA_ID),0) AS TOTAL FROM EDO_MAESTROPREG WHERE EDO_ID = TO_NUMBER(?, '99999') AND TIPO = ?");
			
			ps.setString(1, edoId);
			ps.setString(2, tipo);
			
			rs = ps.executeQuery();
			if (rs.next())
				total = rs.getInt("TOTAL");
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoMaestroPregUtil|getNumPreguntas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
		return total;
	}
	
	public ArrayList<EdoMaestroPreg> getListPreg (Connection conn, String edoId, String orden ) throws SQLException{
		ArrayList<EdoMaestroPreg> lisEAP	= new ArrayList<EdoMaestroPreg>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";
		
		try{
			comando = "SELECT EDO_ID, PREGUNTA_ID, PREGUNTA, TIPO, ORDEN, AREA_ID, " +
					" COMENTARIO1, COMENTARIO2, COMENTARIO3, COMENTARIO4" +
					" FROM ENOC.EDO_MAESTROPREG" + 
					" WHERE EDO_ID = '"+edoId+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				EdoMaestroPreg eap = new EdoMaestroPreg();
				eap.mapeaReg(rs);
				lisEAP.add(eap);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoMaestroPregUtil|getListEdo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEAP;
	}
	
	public ArrayList<EdoMaestroPreg> getListPorArea (Connection conn, String edoId, String areaId, String orden ) throws SQLException{
		ArrayList<EdoMaestroPreg> list	= new ArrayList<EdoMaestroPreg>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";
		
		try{
			comando = "SELECT EDO_ID, PREGUNTA_ID, PREGUNTA, TIPO, ORDEN, AREA_ID, " +
					" COMENTARIO1, COMENTARIO2, COMENTARIO3, COMENTARIO4" +
					" FROM ENOC.EDO_MAESTROPREG" + 
					" WHERE EDO_ID = '"+edoId+"' AND AREA_ID = '"+areaId+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				EdoMaestroPreg obj = new EdoMaestroPreg();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoMaestroPregUtil|getListPorArea|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public ArrayList<EdoMaestroPreg> getListNoContestadas (Connection conn, String edoId, String areaId, String codigoPersonal, String maestroId,  String orden ) throws SQLException{
		ArrayList<EdoMaestroPreg> lisEAP	= new ArrayList<EdoMaestroPreg>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";
		
		try{
			comando = "SELECT EDO_ID, PREGUNTA_ID, PREGUNTA, TIPO, ORDEN, AREA_ID," +
					" COMENTARIO1, COMENTARIO2, COMENTARIO3, COMENTARIO4" +
					" FROM ENOC.EDO_MAESTROPREG" +
					" WHERE EDO_ID = '"+edoId+"' AND AREA_ID = '"+areaId+"' " +
					" AND EDO_ID||PREGUNTA_ID " +
					" NOT IN ( " +
					"	SELECT EDO_ID||PREGUNTA_ID " +
					" 	FROM EDO_MAESTRORESP " +
					"	WHERE EDO_ID = '"+edoId+"' " +
					"	AND CODIGO_PERSONAL = '"+codigoPersonal+"'"  +
					"	AND MAESTRO = '"+maestroId+"' " +
					" ) "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				EdoMaestroPreg eap = new EdoMaestroPreg();
				eap.mapeaReg(rs);
				lisEAP.add(eap);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoMaestroPregUtil|getListNoContestadas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEAP;
	}

	public ArrayList<EdoArea> getListAreas (Connection conn, String edoId, String orden ) throws SQLException{
		ArrayList<EdoArea> list 		= new ArrayList<EdoArea>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";
		
		try{
			comando = "SELECT * FROM ENOC.EDO_AREA " +
					" WHERE AREA_ID IN (SELECT AREA_ID FROM EDO_MAESTROPREG " +
					"    WHERE EDO_ID = '"+edoId+"') "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				EdoArea obj 	= new EdoArea();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoMaestroPregUtil|getListAreas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
}
