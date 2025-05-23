/**
 * 
 */
package aca.mentores;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author etorres
 *
 */
public class MentMotivoUtil {
	
	
	public ArrayList<MentMotivo> getListAll( Connection conn, String orden ) throws SQLException{
		
		ArrayList<MentMotivo> lisMentMotivo	= new ArrayList<MentMotivo>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT MOTIVO_ID, MOTIVO_NOMBRE, COMENTARIO FROM ENOC.MENT_MOTIVO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MentMotivo mentMotivo = new MentMotivo();
				mentMotivo.mapeaReg(rs);
				lisMentMotivo.add(mentMotivo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentorMotivoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMentMotivo;
	}
	
	public HashMap<String, MentMotivo> mapMotivo( Connection conn ) throws SQLException{
		
		HashMap<String,MentMotivo> map = new HashMap<String,MentMotivo>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT MOTIVO_ID, MOTIVO_NOMBRE, COMENTARIO FROM ENOC.MENT_MOTIVO";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MentMotivo motivo = new MentMotivo();
				motivo.mapeaReg(rs);				
				llave = motivo.getMotivoId();
				map.put(llave, motivo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentorMotivoUtil|mapMotivo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
}