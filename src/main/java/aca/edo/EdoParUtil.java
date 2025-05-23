package aca.edo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class EdoParUtil {
	
	public boolean insertReg(Connection conn, EdoPar edoPar) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.EDO_PAR(CODIGO_PERSONAL, EDO_ID, MAESTROS, USUARIO, FECHA)" +
				" VALUES(?, TO_NUMBER(?, '99999'), ?, ?, TO_DATE(?, 'DD/MM/YYYY') )");
					
			ps.setString(1, edoPar.getCodigoPersonal());
			ps.setString(2, edoPar.getEdoId());
			ps.setString(3, edoPar.getMaestros());
			ps.setString(4, edoPar.getUsuario());
			ps.setString(5, edoPar.getFecha());

			
			if(ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoParUtil|insertReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	
	public boolean updateReg(Connection conn, EdoPar edoPar) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EDO_PAR" + 
				" SET MAESTROS = ?," +
				" USUARIO = ?,"	+
				" FECHA = TO_DATE(?, 'DD/MM/YYYY')" +
				" WHERE CODIGO_PERSONAL = ?" +
				" AND EDO_ID = TO_NUMBER(?, '99999')");
			
			ps.setString(1, edoPar.getMaestros());
			ps.setString(2, edoPar.getUsuario());
			ps.setString(3, edoPar.getFecha());
			ps.setString(4, edoPar.getCodigoPersonal());
			ps.setString(5, edoPar.getEdoId());
						
			if(ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoParUtil|updateReg|:"+ex);		 
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String codigoPersonal, String edoId) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.EDO_PAR "+ 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND EDO_ID = TO_NUMBER(?, '99999')");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, edoId);
			
			if(ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoParUtil|deleteReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}
	
	public EdoPar mapeaRegId(Connection con, String codigoPersonal, String edoId) throws SQLException{
		EdoPar edoPar = new EdoPar();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement("SELECT MAESTROS, USUARIO, FECHA" +
					" FROM ENOC.EDO_PAR" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND EDO_ID = TO_NUMBER(?, '99999')");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, edoId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				edoPar.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoParUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return edoPar;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal, String edoId) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EDO_PAR" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND EDO_ID = TO_NUMBER(?, '99999')");
		
		ps.setString(1, codigoPersonal);
		ps.setString(2, edoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoParUtil|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT COALESCE(MAX(CODIGO_PERSONAL)+1,1) AS MAXIMO FROM ENOC.EDO_PAR" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99999')");
			
			ps.setString(1, edoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoParUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
		return maximo;
	}
	
	public static String getMaestros(Connection conn, String edoId, String codigoPersonal) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maestros			= "-";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAESTROS,'-') AS MAESTROS FROM ENOC.EDO_PAR WHERE EDO_ID = TO_NUMBER(?, '99999')");
			
			ps.setString(1, edoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				maestros = rs.getString("MAESTROS");
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoParUtil|getMaestros|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
		return maestros;
	}
	
	
	public ArrayList<EdoPar> getListMaestroPar (Connection conn, String maestro, String orden ) throws SQLException{
		ArrayList<EdoPar> list 				= new ArrayList<EdoPar>();
		Statement st 						= conn.createStatement();
		ResultSet rs		 				= null;
		String comando						= "";
		
		try{
			comando = "SELECT * FROM ENOC.EDO_PAR " +
					" WHERE EDO_ID IN (SELECT EDO_ID FROM ENOC.EDO WHERE TIPO = 'P' AND now() BETWEEN F_INICIO AND F_FINAL)" +
					" AND MAESTROS LIKE '%-"+maestro+"-%' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				EdoPar obj 	= new EdoPar();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoParUtil|getListMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public static HashMap<String,String> mapMaestroPar (Connection conn, String edoId ) throws SQLException{
		
		HashMap<String,String> map			= new HashMap<String,String>();
		Statement st 						= conn.createStatement();
		ResultSet rs		 				= null;
		String comando						= "";
		
		try{
			comando = "SELECT EDO_ID,CODIGO_PERSONAL, MAESTROS FROM ENOC.EDO_PAR WHERE EDO_ID = TO_NUMBER('"+edoId+"','99999')";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				map.put(rs.getString("EDO_ID")+rs.getString("CODIGO_PERSONAL"), rs.getString("MAESTROS"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoParUtil|mapMaestroPar|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
}
