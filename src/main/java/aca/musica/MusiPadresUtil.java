package aca.musica;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MusiPadresUtil {
	public ArrayList<MusiPadres> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<MusiPadres> lisPadres	= new ArrayList<MusiPadres>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT PAD_NOMBRE, PAD_PATERNO, PAD_MATERNO, PAD_DIRECCION, "
					+ " PAD_CORREO, PAD_OCUPACION, PAD_TELCASA, PAD_TELTRABAJO, PAD_TELCELULAR, "
					+ " MAD_NOMBRE, MAD_PATERNO, MAD_MATERNO, MAD_OCUPACION, MAD_DIRECCION, "
					+ " MAD_CORREO, MAD_TELCASA, MAD_TELTRABAJO, MAD_TELCEULUAR, "
					+ " PAD_VIVE, MAD_VIVE, CODIGO_USUARIO, PAD_RELIGION_ID, MAD_RELIGION_ID "
					+ " FROM ENOC.MUSI_PADRES "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MusiPadres padres = new MusiPadres();
				padres.mapeaReg(rs);
				lisPadres.add(padres);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiPadresUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPadres;
	}
}