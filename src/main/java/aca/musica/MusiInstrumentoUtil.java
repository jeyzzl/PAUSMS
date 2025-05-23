package aca.musica;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MusiInstrumentoUtil {

	public ArrayList<MusiInstrumento> getListAll(Connection conn, String orden) throws SQLException{
			
			ArrayList<MusiInstrumento> lisMusiInstrumento	= new ArrayList<MusiInstrumento>();
			Statement st 		= conn.createStatement();
			ResultSet rs 		= null;
			String comando		= "";
			
			try{
				comando = "SELECT INSTRUMENTO_ID, INSTRUMENTO_NOMBRE, TIPO FROM ENOC.MUSI_INSTRUMENTO " +orden; 
				
				rs = st.executeQuery(comando);
				while (rs.next()){
					MusiInstrumento mi = new MusiInstrumento();
					mi.mapeaReg(rs);
					lisMusiInstrumento.add(mi);
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.musica.MusiInstrumentoUtil|getListAll|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return lisMusiInstrumento;
	}
}