/**
 * 
 */
package aca.radius;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author elifo
 *
 */
public class CpEquipoUtil {
	public ArrayList<CpEquipo> getListUsuario(Connection conn, String usuario, String orden ) throws SQLException{
		
		ArrayList<CpEquipo> listUsuario			= new ArrayList<CpEquipo>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT ID, USUARIO, MAC, USUARIO_EDITA, DESCRIPCION" +
					" FROM CP_EQUIPO" +
					" WHERE USUARIO = '"+usuario+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CpEquipo cpEquipo = new CpEquipo();
				cpEquipo.mapeaReg(rs);
				listUsuario.add(cpEquipo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.radius.CpEquipoUtil|getListUsuario|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return listUsuario;
	}
}
