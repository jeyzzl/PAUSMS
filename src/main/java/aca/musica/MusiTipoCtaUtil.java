package aca.musica;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MusiTipoCtaUtil {

public ArrayList<MusiTipoCta> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<MusiTipoCta> lisTipoCta	= new ArrayList<MusiTipoCta>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT TIPOCTA_ID, TIPOCTA_NOMBRE "+
			"FROM ENOC.MUSI_TIPOCTA "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MusiTipoCta tipoCta = new MusiTipoCta();
				tipoCta.mapeaReg(rs);
				lisTipoCta.add(tipoCta);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiTipoCta|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisTipoCta;
}
}