package aca.afe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AfeContratoAlumnoUtil {
	
	public AfeContratoAlumno mapeaRegId(Connection con, String id) throws SQLException{
		AfeContratoAlumno afeCA = new AfeContratoAlumno();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement("SELECT ID, CCOSTO_PUESTO_ID, MATRICULA, "+
				"STATUS, FECHA_ALTA, USER_ALTA_ID, PLAZA_ID "+				
				"FROM NOE.AFE_CONTRATO_ALUMNO WHERE ID = ? ");
			ps.setString(1,id);
			rs = ps.executeQuery();
			
			if(rs.next()){
				afeCA.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AfeContratoAlumnoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return afeCA;
	}
	
	public static String getAlumnoPuesto(Connection conn, String puesto ) throws Exception{
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String alumno		= "Vacante";
		
		try{
			
			comando = "SELECT MATRICULA FROM NOE.AFE_CONTRATO_ALUMNO" +
					" WHERE PLAZA_ID = "+puesto+" AND STATUS IN('A','I')";
			rs = st.executeQuery(comando);			 
			if (rs.next()){
				alumno 	= rs.getString(1);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AfeContratoAlumnoUtil|getAlumnoPuesto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return alumno;
	}
	

}