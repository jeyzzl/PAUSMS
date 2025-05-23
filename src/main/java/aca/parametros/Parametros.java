package aca.parametros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Parametros {
	private String id;
	private String institucion;
	private String certificados;
	private String constancias;
	private String cardex;	

	public Parametros(){
		id				= "";
		institucion		= "";
		certificados	= "";
		constancias		= "";
		cardex			= "";		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String intitucion) {
		this.institucion = intitucion;
	}

	public String getCertificados() {
		return certificados;
	}

	public void setCertificados(String certificados) {
		this.certificados = certificados;
	}

	public String getConstancias() {
		return constancias;
	}

	public void setConstancias(String constancias) {
		this.constancias = constancias;
	}

	public String getCardex() {
		return cardex;
	}

	public void setCardex(String cardex) {
		this.cardex = cardex;
	}
	
	
	public boolean insertReg(Connection conn) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.PARAMETROS" + 
				"(INSTITUCION,CERTIFICADOS,CONSTANCIAS,CARDEX)" +
				" VALUES(?,?,?,?)");
			
			ps.setString(1, institucion);
			ps.setString(2, certificados);			
			ps.setString(3, constancias);
			ps.setString(4, cardex);			
			
			if(ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.parametros.Parametros|insertReg|:"+ex);	
			ok = false;
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.PARAMETROS" + 
				" SET INSTITUCION = ?," +
				" CERTIFICADOS = ?," +
				" CONSTANCIAS = ?," +
				" CARDEX = ?" +				
				" WHERE ID = TO_NUMBER(?,'99')");
			
			ps.setString(1, institucion);
			ps.setString(2, certificados);
			ps.setString(3, constancias);
			ps.setString(4, cardex);
			ps.setString(5, id);
			
			if(ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.parametros.Parametros|updateReg|:"+ex); 
			ok = false;
		}finally{
			if(ps!=null) ps.close();
		}		
		return ok;
	}
	
	public boolean deleteReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.PARAMETROS"+ 
				" WHERE ID = TO_NUMBER(?,'99')");
			
			ps.setString(1, id);
			
			if(ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.parametros.Parametros|deleteReg|:"+ex);
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		id				= rs.getString("ID");
		institucion		= rs.getString("INSTITUCION");
		certificados	= rs.getString("CERTIFICADOS");
		constancias		= rs.getString("CONSTANCIAS");
		cardex			= rs.getString("CARDEX");		
	}
	
	public void mapeaRegId(Connection conn, String id) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = conn.prepareStatement("SELECT * FROM ENOC.PARAMETROS" + 
					" WHERE ID = TO_NUMBER(?,'99')");
			
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){ 			
 			System.out.println("Error - aca.parametros.Parametros|mapeaRegId|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 			ok 		= false;
		ResultSet 			rs		= null;
		PreparedStatement 	ps		= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.PARAMETROS" + 
					" WHERE ID = TO_NUMBER(?,'99')");
			
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.parametros.Parametros|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
		return ok;
	}
	
	public static String getInstitucion(Connection conn, String id) throws SQLException{
		
		ResultSet 			rs		= null;
		PreparedStatement 	ps		= null;
		String institucion  		= ""; 
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(INSTITUCION, 'UNIVERSIDAD') AS INST FROM ENOC.PARAMETROS" + 
					" WHERE ID = TO_NUMBER(?,'99')");
			
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			if (rs.next())
				institucion = rs.getString("INST");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.parametros.Parametros|getInstitucion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
		return institucion;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}