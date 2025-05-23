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
public class ModuloMensajeUtil {
	public ArrayList<ModuloMensaje> getListUsuario(Connection Conn, String usuario, String orden) throws SQLException{
		ArrayList<ModuloMensaje> lisModulo 	= new ArrayList<ModuloMensaje>();
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT MODULO_ID, NOMBRE, USUARIOS FROM ENOC.MODULO_MENSAJE" + 
					" WHERE USUARIOS LIKE '%"+usuario+"%' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ModuloMensaje modulo = new ModuloMensaje();
				modulo.mapeaReg(rs);
				lisModulo.add(modulo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.msj.ModuloMensajeutil|getListUsuario|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisModulo;
	}
}