//Beans para la tabla INTERNOS
package aca.residencia;

import java.sql.*;
import java.util.ArrayList;

public class ExternosUtil {
	public ArrayList<String> getListExternos(Connection conn, String orden ) throws SQLException{
		
		ArrayList<String> lisExter			= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		try{
			comando = "SELECT MATRICULA, "+
			"ENOC.ALUM_APELLIDO(MATRICULA) AS NOMBRE, "+
			"RAZON, "+
			"TUT_NOMBRE||' '||TUT_APELLIDOS AS TUTOR, "+
			"COLONIA, "+
			"CALLE||' '|| CASE NUMERO WHEN 's/n' THEN NUMERO ELSE '# '||NUMERO END AS CALLE, "+
			"TEL_NUMERO AS TELEFONO, "+
			"ENOC.FACULTAD(ENOC.ALUM_CARRERA_ID(MATRICULA)) AS FACULTAD, "+
			"ENOC.ALUM_CARRERA_ID(MATRICULA) AS CARRERA, B.SEXO "+
		 	"FROM ENOC.RES_DATOS A, ENOC.INSCRITOS B "+ 
			"WHERE B.CODIGO_PERSONAL = A.MATRICULA "+
			"ORDER BY FACULTAD, CARRERA, NOMBRE";
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				lisExter.add(rs.getString("MATRICULA"));
				lisExter.add(rs.getString("NOMBRE"));			
				lisExter.add(rs.getString("RAZON"));
				lisExter.add(rs.getString("TUTOR"));
				lisExter.add(rs.getString("COLONIA"));
				lisExter.add(rs.getString("CALLE"));
				lisExter.add(rs.getString("TELEFONO"));
				lisExter.add(rs.getString("FACULTAD"));
				lisExter.add(rs.getString("CARRERA"));
				lisExter.add(rs.getString("SEXO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ExternosUtil|getListExternos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisExter;
	}	
	
	public ArrayList<String> getListExtDatos(Connection conn, String orden ) throws SQLException{
		
		ArrayList<String> lisExter			= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		try{
			comando = "SELECT A.CODIGO_PERSONAL, ENOC.ALUM_APELLIDO(CODIGO_PERSONAL) AS NOMBRE, " +
					"B.RAZON, TUT_NOMBRE||' '||TUT_APELLIDOS AS TUTOR, " +
					"COLONIA, CALLE||' '||CASE NUMERO WHEN 's/n' ELSE NUMERO,'# '||NUMERO END AS CALLE, " +
					"TEL_NUMERO AS TELEFONO, ENOC.FACULTAD(ALUM_CARRERA_ID(MATRICULA)) AS FACULTAD, " +
					"ALUM_CARRERA_ID(MATRICULA) AS CARRERA, SEXO " +
					"FROM ENOC.INSCRITOS A, RES_DATOS B " + 
					"WHERE A.CODIGO_PERSONAL = B.MATRICULA " +
					"AND A.RESIDENCIA_ID = 'E'";
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				lisExter.add(rs.getString("CODIGO_PERSONAL"));
				lisExter.add(rs.getString("NOMBRE"));			
				lisExter.add(rs.getString("RAZON"));
				lisExter.add(rs.getString("TUTOR"));
				lisExter.add(rs.getString("COLONIA"));
				lisExter.add(rs.getString("CALLE"));
				lisExter.add(rs.getString("TELEFONO"));
				lisExter.add(rs.getString("FACULTAD"));
				lisExter.add(rs.getString("CARRERA"));
				lisExter.add(rs.getString("SEXO"));			
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ExternosUtil|getListExtDatos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisExter;
	}	
	
	public ArrayList<String> getListInscritosExternos(Connection conn, String fechaIni, String fechaFin, String orden) throws SQLException{
	
		ArrayList<String> lisExter			= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		try{
			comando = " SELECT CODIGO_PERSONAL, ENOC.ALUM_APELLIDO(CODIGO_PERSONAL) AS NOMBRE,"
					+ " COALESCE((SELECT B.RAZON FROM ENOC.RES_DATOS B WHERE A.CODIGO_PERSONAL = B.MATRICULA),0) AS RAZON,"
					+ " COALESCE((SELECT B.TUT_NOMBRE||' '||B.TUT_APELLIDOS FROM ENOC.RES_DATOS B WHERE A.CODIGO_PERSONAL = B.MATRICULA),'') AS TUTOR,"
					+ " COALESCE((SELECT B.COLONIA FROM ENOC.RES_DATOS B WHERE A.CODIGO_PERSONAL = B.MATRICULA),'') AS COLONIA,"
					+ " COALESCE((SELECT B.CALLE||' '||CASE NUMERO WHEN 's/n' THEN 'S/N' ELSE '# '||NUMERO END AS NUMERO"
					+ " FROM ENOC.RES_DATOS B WHERE A.CODIGO_PERSONAL = B.MATRICULA),'') AS CALLE,"
					+ " COALESCE((SELECT B.TEL_NUMERO FROM ENOC.RES_DATOS B WHERE A.CODIGO_PERSONAL = B.MATRICULA),'') AS TELEFONO,"
					+ " ENOC.FACULTAD(A.CARRERA_ID) AS FACULTAD,"
					+ " A.CARRERA_ID AS CARRERA,"
					+ " SEXO"
					+ " FROM ENOC.INSCRITOS A"
					+ " WHERE RESIDENCIA_ID = 'E'"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') "+orden;
			
			rs = st.executeQuery(comando);		
			while (rs.next()){
				
				lisExter.add(rs.getString("CODIGO_PERSONAL"));
				lisExter.add(rs.getString("NOMBRE"));			
				lisExter.add(rs.getString("RAZON"));
				lisExter.add(rs.getString("TUTOR"));
				lisExter.add(rs.getString("COLONIA"));
				lisExter.add(rs.getString("CALLE"));
				lisExter.add(rs.getString("TELEFONO"));
				lisExter.add(rs.getString("FACULTAD"));
				lisExter.add(rs.getString("CARRERA"));
				lisExter.add(rs.getString("SEXO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ExternosUtil|getListInscritosExternos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisExter;
	}	
}