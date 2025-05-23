package aca.musica;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MusiInstitucionUtil {

public ArrayList<MusiInstitucion> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<MusiInstitucion> lisMusiInstitucion	= new ArrayList<MusiInstitucion>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT INSTITUCION_ID, INSTITUCION_NOMBRE "+
			"FROM ENOC.MUSI_INSTITUCION "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MusiInstitucion mi = new MusiInstitucion();
				mi.mapeaReg(rs);
				lisMusiInstitucion.add(mi);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiInstitucionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMusiInstitucion;
}
}