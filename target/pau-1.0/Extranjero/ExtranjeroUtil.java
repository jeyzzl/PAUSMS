package Extranjero;
import java.sql.*;

public class ExtranjeroUtil {
	
//	 Verifica si el alumno es extranjero y estx autorizado en asuntos legales.
	public static int autorizaExtranjero( Connection conn, String codigoPersonal ) throws SQLException, Exception{
		
		ResultSet rset 		= null;
		Statement stmt 		= conn.createStatement();
		String comando		= "";
		
		int resultado		= 0;
		boolean extranjero	= false;
	
		comando = "SELECT NACIONALIDAD FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"; 
		rset = stmt.executeQuery(comando);
		if (rset.next()){
			if ( !rset.getString("NACIONALIDAD").equals("91")){
				extranjero = true;
			}
		}
		
		// Es extranjero?
		if (extranjero){
			
			comando = "Select a.fecha_vence, b.fecha "+
					"from ENOC.leg_extdoctos a, leg_restriccion b "+ 
					"where a.codigo = '"+codigoPersonal+"' "+
					"and a.iddocumento = 2 "+
					"and a.FECHA_VENCE > b.FECHA";
			rset = stmt.executeQuery(comando);	
	   	 	if (rset.next()){
				resultado = 1;
			//FM3 autorizado");
			}else{
				comando = "Select nombre(usuario_alta) as usuario "+
						"from ENOC.leg_permisos "+ 
						"Where codigo = '"+codigoPersonal+"' "+			
						"and to_date(now(),'dd-mm-yy') between fecha_ini and fecha_lim "+
						"and status = 'A'";
				rset = stmt.executeQuery(comando);	
				if (rset.next()){
					resultado = 2;
					//Permiso autorizado
				}else{
					//Sin permiso	
				}				
			}
		}else{
			resultado = 3;
			// Es Mexicano
		}	
		return resultado;
	}	
	
	if (stmt != null) stmt.close();
	if (rset != null) rset.close();	

}