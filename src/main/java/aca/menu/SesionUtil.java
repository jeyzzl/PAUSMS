/**
 * 
 */
package aca.menu;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Elifo
 *
 */
public class SesionUtil {
	public ArrayList<Sesion> getListUsuarioSemana(Connection conn, String codigoPersonal, String orden ) throws SQLException{
		
		ArrayList<Sesion> lisSesion	= new ArrayList<Sesion>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT SESION_ID, CODIGO_PERSONAL,"
					+ " TO_CHAR(F_INICIO, 'DD/MM/YYYY HH:MI:SS AM') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL, 'DD/MM/YYYY HH:MI:SS AM') AS F_FINAL, IP, FINALIZO"
					+ " FROM ENOC.MODULO_SESION A"
					+ " WHERE A.CODIGO_PERSONAL = '"+codigoPersonal+"'"
					+ " AND A.F_INICIO > FECHA_DIAS(TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY'),'RESTA',7) "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Sesion sesion = new Sesion();
				sesion.mapeaReg(rs);
				lisSesion.add(sesion);
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.SesionUtil|getListUsuarioSemana|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisSesion;
	}
}