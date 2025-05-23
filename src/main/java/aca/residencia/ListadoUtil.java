//Beans para la tabla INTERNOS
package aca.residencia;

import java.sql.*;
import java.util.ArrayList;

public class ListadoUtil {
	public ArrayList<String> getListExternos(Connection conn, String orden ) throws SQLException{
		
		ArrayList<String> lisInter			= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		try{
			comando = "SELECT MATRICULA, "+
			"ENOC.ALUM_NOMBRE(MATRICULA) AS NOMBRE, RAZON, "+
			"MUNICIPIO, COLONIA, "+
			"CALLE||' '||CASE NUMERO WHEN 's/n' THEN NUMERO ELSE '# '||NUMERO END AS CALLE, "+
			"TEL_NUMERO AS TELEFONO, "+
			"ENOC.FACULTAD(ALUM_CARRERA_ID(MATRICULA)) AS FACULTAD, "+
			"CASE ENOC.ALUM_CARRERA_ID(MATRICULA) WHEN 'xxxxx' THEN 'X' ELSE ALUM_CARRERA_ID(MATRICULA) END AS CARRERA, "+
			"ENOC.ALUM_INSCRITO(MATRICULA) AS INSCRITO "+
			"FROM ENOC.RES_DATOS "+ 
			"ORDER BY FACULTAD,CARRERA,NOMBRE" ;
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				lisInter.add(rs.getString("MATRICULA"));
				lisInter.add(rs.getString("NOMBRE"));
				lisInter.add(rs.getString("RAZON"));
				lisInter.add(rs.getString("MUNICIPIO"));
				lisInter.add(rs.getString("COLONIA"));
				lisInter.add(rs.getString("CALLE"));
				lisInter.add(rs.getString("TELEFONO"));
				lisInter.add(rs.getString("FACULTAD"));
				lisInter.add(rs.getString("CARRERA"));
				lisInter.add(rs.getString("INSCRITO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ListadoUtil|getListExternos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInter;
	}	
}