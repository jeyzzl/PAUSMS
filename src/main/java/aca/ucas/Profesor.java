package aca.ucas;

import java.sql.*;

public class Profesor {
	
	private String codigoPersonal;
	private String folio;
	private String facultadId;
	private String carreraId;
	private String status; 

	public Profesor(){
		codigoPersonal 	= "";
		folio			= "";
		facultadId		= "";
		carreraId		= "";
		status			= "";
	}
	
	public String getCodigoPersonal(){
		return codigoPersonal;
	}
	
	public void setCodigoPersonal(String codigoPersonal){
		this.codigoPersonal = codigoPersonal;
	}
	
	public String getFolio(){
		return folio;
	}
	
	public void setFolio(String folio){
		this.folio = folio;
	}
	
	public String getFacultadId(){
		return facultadId;
	}
	
	public void setFacultadId(String facultadId){
		this.facultadId = facultadId;
	}
	
	public String getCarreraId(){
		return carreraId;
	}
	
	public void setCarreraId(String carreraId){
		this.carreraId = carreraId;
	}
	
	public String getStatus(){
		return status;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	
	public boolean insertReg (Connection conn) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.UCA_PROFESOR(CODIGO_PERSONAL, " + 
					"FOLIO, FACULTAD_ID, CARRERA_ID, STATUS) VALUES(?, TO_NUMBER(?,'99'), ?, ?, ?)");
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);
			ps.setString(3, facultadId);
			ps.setString(4, carreraId);
			ps.setString(5, status);
			
			if(ps.executeUpdate()==1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.ucas.Profesor|insertReg|:"+ex);
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.UCA_PROFESOR " + 
					"SET FACULTAD_ID = ?, CARRERA_ID = ?, STATUS = ? " +
					"WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'99')");
			ps.setString(1, facultadId);
			ps.setString(2, carreraId);
			ps.setString(3, status);
			ps.setString(4, codigoPersonal);
			ps.setString(5, folio);
			
			if (ps.executeUpdate() == 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.ucas.Profesor|updateReg|:"+ex);
		}finally{
			if(ps != null) ps.close();
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.UCA_PROFESOR " + 
					"WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'99')");
			ps.setString(1,codigoPersonal);
			ps.setString(2,folio);
			
			if(ps.executeUpdate() == 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ucas.Profesor|deleteReg|:"+ex);
		}finally{
			if(ps != null) ps.close();
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		folio			= rs.getString("FOLIO");
		facultadId		= rs.getString("FACULTAD_ID");
		carreraId		= rs.getString("CARRERA_ID");
		status			= rs.getString("STATUS");
	}
	
	public void mapeaRegId(Connection conn, String codigoPersonal, String folio) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, FOLIO, " +
					"FACULTAD_ID, CARRERA_ID, STATUS FROM ENOC.UCA_PROFESOR " + 
					"WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'99')");
			ps.setString(1,codigoPersonal);
			ps.setString(2,folio);
			rs= ps.executeQuery();
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch (Exception ex){
			System.out.println("Error - aca.ucas.Profesor|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null)rs.close();
			if(ps!=null)ps.close();
		}
	}
	
	public void mapeaRegId(Connection conn) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, FOLIO, " +
					"FACULTAD_ID, CARRERA_ID, STATUS FROM ENOC.UCA_PROFESOR " + 
					"WHERE CODIGO_PERSONAL = ? AND FACULTAD_ID= ? AND CARRERA_ID= ?");
			ps.setString(1,codigoPersonal);
			ps.setString(2,facultadId);
			ps.setString(3,carreraId);
			rs= ps.executeQuery();
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch (Exception ex){
			System.out.println("Error - aca.ucas.Profesor|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null)rs.close();
			if(ps!=null)ps.close();
		}
	}
	
	public boolean existeReg(Connection conn)throws SQLException{
		boolean ok  = false;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps= conn.prepareStatement("SELECT * FROM ENOC.UCA_PROFESOR " + 
					"WHERE CODIGO_PERSONAL = ? AND FOLIO = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);
			rs= ps.executeQuery();
			
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.ucas.Profesor|existeReg|:"+ex);
		}finally{
			if(rs!=null)rs.close();
			if(ps!=null)ps.close();
		}
		return ok;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal, String status)throws SQLException{
		boolean ok  = false;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps= conn.prepareStatement("SELECT * FROM ENOC.UCA_PROFESOR " + 
					"WHERE CODIGO_PERSONAL = ? AND STATUS = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, status);
			rs= ps.executeQuery();
			
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.ucas.Profesor|existeReg|:"+ex);
		}finally{
			if(rs!=null)rs.close();
			if(ps!=null)ps.close();
		}
		return ok;
	}
	
	public boolean existeRegFC(Connection conn)throws SQLException{
		boolean ok  = false;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps= conn.prepareStatement("SELECT * FROM ENOC.UCA_PROFESOR " + 
					"WHERE CODIGO_PERSONAL = ? AND FACULTAD_ID= ? " +
					"AND CARRERA_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, facultadId);
			ps.setString(3, carreraId);
			rs= ps.executeQuery();
			
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.ucas.Profesor|existeRegFC|:"+ex);
		}finally{
			if(rs!=null)rs.close();
			if(ps!=null)ps.close();
		}
		return ok;
	}
	
	public String maximoReg(Connection conn ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT CASE MAX(FOLIO)+1 WHEN NULL THEN 1 ELSE MAX(FOLIO)+1 END AS MAXIMO " +
					"FROM ENOC.UCA_PROFESOR WHERE CODIGO_PERSONAL = ?"); 
			ps.setString(1,codigoPersonal);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.ucas.Profesor|maximoReg|:"+ex);
		}finally{
			if(rs!=null)rs.close();
			if(ps!=null)ps.close();
		}
		
		return maximo;
	}
	
	public static String getEstatus(Connection conn, String codigoPersonal, String facultadId, String carreraId) throws SQLException{
		PreparedStatement	ps 	= null;
		ResultSet 			rs	= null;
		String status 			= "";
		
		
		try{
			ps = conn.prepareStatement("SELECT STATUS FROM ENOC.UCA_PROFESOR " + 
					"WHERE CODIGO_PERSONAL = ?  AND FACULTAD_ID= ? AND CARRERA_ID = ? ");
			ps.setString(1, codigoPersonal);
			ps.setString(2, facultadId);
			ps.setString(3, carreraId);
			
			rs = ps.executeQuery();
			if (rs.next())
				status = rs.getString("STATUS");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.ucas.Profesor|getEstatus|:"+ex);
		}finally{
			if(rs!=null)rs.close();
			if(ps!=null)ps.close();
		}
		
		return status;
	}
	
/*
	public static void main(String args[]){
		try{
			Connection Conn = null;
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.14:1521:ora1", "enoc", "caminacondios");
			
			Profesor profe = new Profesor();						
			ArrayList profe2= new ArrayList();
			profe2 = profe.getAcceso(Conn,"1010937");
			System.out.println("AccesoList="+profe2.size());
			//profe.mapeaRegId(Conn,"9800002","1");
			//if (profe.existeReg(Conn)){
		//	System.out.println("Existe..!");
			//System.out.println(acceso.getcodigoPersonal()+"--"+acceso.getAdministrador()+"--"+acceso.getCotejador());
			//}else{
			//	System.out.println("No existe..!");
		//	}
			
			Conn.commit();
			Conn.close();
			
		}catch(Exception e){
			System.out.println(e);
		}
		
	}*/	
}