// Bean de datos academicos del alumno
package  aca.alumno;
import java.io.IOException;
import java.sql.*;

public class AlumReferencia{
	private String codigoPersonal;
	private String scotiabank;
	private String banamex;
	private String santander;
		
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getScotiabank() {
		return scotiabank;
	}

	public void setScotiabank(String scotiabank) {
		this.scotiabank = scotiabank;
	}

	public String getBanamex() {
		return banamex;
	}

	public void setBanamex(String banamex) {
		this.banamex = banamex;
	}
	
	public String getSantander() {
		return santander;
	}

	public void setSantander(String santander) {
		this.santander = santander;
	}

	public AlumReferencia(){
		codigoPersonal		= "";
		scotiabank			= "";
		banamex				= "";
		santander			= "";
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		codigoPersonal 	= rs.getString("CODIGO_PERSONAL");
		scotiabank 		= rs.getString("SCOTIABANK");
		banamex 		= rs.getString("BANAMEX");
		santander		= rs.getString("SANTANDER");
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal ) throws SQLException, IOException{
		
		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try{
	 		ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_REFERENCIA WHERE CODIGO_PERSONAL = ?"); 
			ps.setString(1, codigoPersonal);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){	 			
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumReferenciaUtil|mapeaRegId|:"+ex);
 		}finally{
 			try { rs.close(); } catch (Exception ignore) { }
 	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 	}
	
}