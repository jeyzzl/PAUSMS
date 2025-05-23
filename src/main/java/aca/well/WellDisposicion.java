 // Bean
 package  aca.well;

 import java.io.IOException;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.sql.Statement;


 /**
 * @author 
 *
 */
public class WellDisposicion{
 	private String codigoPersonal;
	private String cardiaco;
 	private String dolor;
 	private String dolorMes;
 	private String vertigo;
 	private String oseo;
 	private String presion;
 	private String razon;

 
 	
 	public WellDisposicion(){		

 		codigoPersonal	= "";
		cardiaco		= "";
		dolor			= "";
		dolorMes		= "";
		vertigo			= "";
		oseo			= "";
		presion			= "";
		razon			= "";
	
 	}
 	
	

	public String getCardiaco() {
		return cardiaco;
	}



	public void setCardiaco(String cardiaco) {
		this.cardiaco = cardiaco;
	}



	public String getDolor() {
		return dolor;
	}



	public void setDolor(String dolor) {
		this.dolor = dolor;
	}



	public String getDolorMes() {
		return dolorMes;
	}



	public void setDolorMes(String dolorMes) {
		this.dolorMes = dolorMes;
	}



	public String getVertigo() {
		return vertigo;
	}



	public void setVertigo(String vertigo) {
		this.vertigo = vertigo;
	}



	public String getOseo() {
		return oseo;
	}



	public void setOseo(String oseo) {
		this.oseo = oseo;
	}



	public String getPresion() {
		return presion;
	}



	public void setPresion(String presion) {
		this.presion = presion;
	}



	public String getRazon() {
		return razon;
	}



	public void setRazon(String razon) {
		this.razon = razon;
	}



	public String getCodigoPersonal() {
		return codigoPersonal;
	}



	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public boolean insertReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("INSERT INTO ENOC.WELL_DISPOSICION"+ 
 				"(CODIGO_PERSONAL, CARDIACO, DOLOR, DOLOR_MES, VERTIGO, OSEO, PRESION, RAZON) "+
 				"VALUES( ?,?,?,?,?,?,?,?)");
 			ps.setString(1, codigoPersonal);
 			ps.setString(2, cardiaco);
 			ps.setString(3, dolor);
 			ps.setString(4, dolorMes);
 			ps.setString(5, vertigo);
 			ps.setString(6, oseo);
 			ps.setString(7, presion);
 			ps.setString(8, razon);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.well.WellnessDisposicion|insertReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(Connection conn ) throws Exception{ 		
 		Statement st 		= conn.createStatement(); 		
 		String comando = "";
 		boolean ok = false;
 		
 		try{
 			comando = "UPDATE ENOC.WELL_DISPOSICION"+			 
 				" SET CODIGO_PERSONAL = ?,"
 				+ "CARDIACO = ?, "
 				+ "DOLOR = ?,"
 				+ "DOLOR_MES = ?,"
 				+ "VERTIGO = ?,"
 				+ "OSEO = ?,"
 				+ "PRESION = ?,"
 				+ "RAZON = ?";

			if (st.executeUpdate(comando)==1){
				ok=true;
			}
			
 		}catch(Exception ex){
 			System.out.println("Error - aca.well.WellnessDisposicion|updateReg|:"+ex);		
 		}finally{
 			try { st.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	} 
 	
 	public boolean deleteReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("DELETE FROM ENOC.WELL_DISPOSICION "+ 
 				"WHERE CODIGO_PERSONAL = ? ");
 			ps.setString(1, codigoPersonal);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.well.WellnessDisposicion|deleteReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}
 	
 	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		cardiaco		= rs.getString("CARDIACO");
		dolor			= rs.getString("DOLOR");
		dolorMes		= rs.getString("DOLOR_MES");
		vertigo			= rs.getString("VERTIGO");
		oseo			= rs.getString("OSEO");
		presion			= rs.getString("PRESION");
		razon			= rs.getString("RAZON");

 	}
  	
 	public void mapeaRegId( Connection conn, String codigoPersonal ) throws SQLException, IOException{
 		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement("SELECT "+
	 			" CODIGO_PERSONAL, CARDIACO, DOLOR, DOLOR_MES, VERTIGO, OSEO, PRESION, RAZON"+
	 			" FROM ENOC.WELL_DISPOSICION WHERE CODIGO_PERSONAL = ? "); 
	 		ps.setString(1, codigoPersonal);	
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.well.WellnessDisposicion|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 	}
	


 	public boolean existeReg(Connection conn) throws SQLException{
 		boolean 		ok 	= false;
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT * FROM ENOC.WELL_DISPOSICION "+ 
 				"WHERE CODIGO_PERSONAL = ?");
 			ps.setString(1, codigoPersonal);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.well.WellnessDisposicion|existeReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	} 	
 }