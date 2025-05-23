package aca.leg;

import java.sql.*;
import java.util.ArrayList;

public class LegExtranjeroUtil {
	

	public boolean insertReg(Connection conn, LegExtranjero legExt) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.LEG_EXTRANJERO" + 
					" (CODIGO, RNE, LIBRO, HOJA, CARRERA, COMENTARIO, TELEFONO)"+
					" VALUES( ?, ?, ?, ?, ?, ?, ?)");			
			ps.setString(1, legExt.getCodigo());
			ps.setString(2, legExt.getRne());
			ps.setString(3, legExt.getLibro());
			ps.setString(4, legExt.getHoja());
			ps.setString(5, legExt.getCarrera());
			ps.setString(6, legExt.getComentario());
			ps.setString(7, legExt.getTelefono());
		
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegExtranjero|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, LegExtranjero legExt) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.LEG_EXTRANJERO "+ 
				"SET RNE=?, CARRERA=?, LIBRO=?, HOJA=?, COMENTARIO=?, TELEFONO=? " +
				"WHERE CODIGO= ? ");
			ps.setString(1, legExt.getRne());			
			ps.setString(2, legExt.getCarrera());
			ps.setString(3, legExt.getLibro());
			ps.setString(4, legExt.getHoja());
			ps.setString(5, legExt.getComentario());
			ps.setString(6, legExt.getTelefono());
			ps.setString(7, legExt.getCodigo());
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegExtranjero|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String codigo) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.LEG_EXTRANJERO WHERE CODIGO = ?"); 
			ps.setString(1, codigo);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegExtranjero|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public LegExtranjero mapeaRegId(Connection conn, String codigo)	throws SQLException{
		LegExtranjero legExt = new LegExtranjero();
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		try{
			ps=conn.prepareStatement("SELECT CODIGO, COALESCE(RNE,'VACIO') AS RNE, " +
					"COALESCE(LIBRO,'VACIO')AS LIBRO, " +
					"COALESCE(HOJA,'VACIO') AS HOJA, " +
					"COALESCE(CARRERA,'VACIO') AS CARRERA, " +
					"COALESCE(COMENTARIO,'VACIO')AS COMENTARIO, " +				
					"COALESCE(TELEFONO,'VACIO') AS TELEFONO " +
					"FROM ENOC.LEG_EXTRANJERO " + 
					"WHERE CODIGO = ? ");
			ps.setString(1, codigo);
			rs=ps.executeQuery();
			if(rs.next()){
				legExt.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegExtranjero|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return legExt;
	}
	
	public boolean existeReg(Connection conn, String codigo) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.LEG_EXTRANJERO WHERE CODIGO = ?"); 
			ps.setString(1,codigo);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegExtranjero|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
/*	public static void main(String args[]){
		boolean variable=true;
		try{
			Connection Conn= null;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn= DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.14:1521:ora1", "enoc","caminacondios");
			LegExtranjero act= new LegExtranjero();
			act.setCodigo("1010937");
			variable= act.existeReg(Conn);
			System.out.println("ArrayList="+variable);
			Conn.commit();
			Conn.close();
		}catch(Exception e){
			System.out.println("Error main="+e);
		}
	}
	*/
	
	public ArrayList<LegExtranjero> getListaAll(Connection conn, String orden) throws SQLException{
		ArrayList<LegExtranjero> lisExt 			= new ArrayList<LegExtranjero>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando="SELECT CODIGO, RNE, LIBRO, HOJA, CARRERA, COMENTARIO, TELEFONO " +
					"FROM ENOC.LEG_EXTRANJERO " + orden; 
			
			rs=st.executeQuery(comando);
			while (rs.next()){
				LegExtranjero doc = new LegExtranjero();
				doc.mapeaReg(rs);
				lisExt.add(doc);
			}
		}catch (Exception ex){
			System.out.println("Error - aca.leg.LegExtranjeroUtil|getListaAll|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(st!=null) st.close();
		}
		
		return lisExt;
	}
	
	public ArrayList<LegExtranjero> getListInscritos(Connection conn, String orden) throws SQLException{
		ArrayList<LegExtranjero> lisExt 			= new ArrayList<LegExtranjero>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando="SELECT CODIGO, RNE, LIBRO, HOJA, CARRERA, COMENTARIO, TELEFONO" +
					" FROM ENOC.LEG_EXTRANJERO" + 
					" WHERE CODIGO IN (SELECT CODIGO_PERSONAL" +
									 " FROM ENOC.ALUM_ESTADO" + 
									 " WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA" + 
									   					" WHERE TO_DATE(now(),'DD-MM-YY')"+
									   					" BETWEEN F_INICIO AND F_FINAL)) " +
					orden;
			
			rs=st.executeQuery(comando);
			while (rs.next()){
				LegExtranjero doc = new LegExtranjero();
				doc.mapeaReg(rs);
				lisExt.add(doc);
			}
		}catch (Exception ex){
			System.out.println("Error - aca.leg.LegExtranjeroUtil|getListInscritos|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(st!=null) st.close();
		}
		
		return lisExt;
	}
	
	public ArrayList<LegExtranjero> getListInscritosModalidad(Connection conn, String modalidades, String orden) throws SQLException{
		ArrayList<LegExtranjero> lisExt 			= new ArrayList<LegExtranjero>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando="SELECT CODIGO, RNE, LIBRO, HOJA, CARRERA, COMENTARIO, TELEFONO" +
					" FROM ENOC.LEG_EXTRANJERO" + 
					" WHERE CODIGO IN (SELECT CODIGO_PERSONAL" +
									 " FROM ENOC.ALUM_ESTADO" + 
									 " WHERE MODALIDAD_ID IN ("+modalidades+") AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA" + 
									   					" WHERE TO_DATE(now(),'DD-MM-YY')"+
									   					" BETWEEN F_INICIO AND F_FINAL)) " +
					orden;
			
			rs=st.executeQuery(comando);
			while (rs.next()){
				LegExtranjero doc = new LegExtranjero();
				doc.mapeaReg(rs);
				lisExt.add(doc);
			}
		}catch (Exception ex){
			System.out.println("Error - aca.leg.LegExtranjeroUtil|getListInscritos|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(st!=null) st.close();
		}
		
		return lisExt;
	}
	
}