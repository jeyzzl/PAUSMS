/**
 * 
 */
package aca.msj;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author general
 *
 */
public class AlumMensajeUtil {
	public ArrayList<String> getListMatriculas(Connection Conn, String query) throws SQLException{
		ArrayList<String> lisModulo 	= new ArrayList<String>();
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		String matricula;
		
		try{
			comando = query;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				matricula = rs.getString("CODIGO_PERSONAL");
				lisModulo.add(matricula);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.msj.AlumMensajeUtil|getListMatriculas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisModulo;
	}
}