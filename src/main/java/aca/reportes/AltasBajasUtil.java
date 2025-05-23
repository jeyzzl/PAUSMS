package aca.reportes;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AltasBajasUtil {
	
	
	public ArrayList<AltasBajas> getListAltasBajas(Connection con, String cargaId) throws SQLException{
		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String comando			= "";
		ArrayList<AltasBajas> lis 				= new ArrayList<AltasBajas>();

		try{
			
			comando = "SELECT DISTINCT(A.CODIGO_PERSONAL) AS CODIGO,  " +
					"A.APELLIDO_PATERNO||' '||A.APELLIDO_MATERNO||' '||NOMBRE AS NOMBRE, " + 
					"AP.PLAN_ID AS PLAN, " +
					"M.CARRERA_ID AS CARRERA, " +
					"SUBSTR(M.CARRERA_ID,1,3) AS FACULTAD " +
					"FROM ENOC.ALUM_PERSONAL A, ENOC.KRDX_CURSO_ACT K, ENOC.ALUM_PLAN AP, ENOC.MAPA_PLAN M " + 
					"WHERE SUBSTR(K.CURSO_CARGA_ID,1,6) = ? " +
					"AND A.CODIGO_PERSONAL = AP.CODIGO_PERSONAL " +
					"AND AP.CODIGO_PERSONAL = K.CODIGO_PERSONAL " +
					"AND A.CODIGO_PERSONAL = K.CODIGO_PERSONAL " +
					"AND M.PLAN_ID = AP.PLAN_ID " +
					"AND AP.ESTADO = '1' " +
					"AND TIPOCAL_ID != 'M' " + 
					"ORDER BY 4,3,2";
			
			ps = con.prepareStatement(comando);		
			ps.setString(1,cargaId);			
			rs = ps.executeQuery();
			while (rs.next()){							
				lis.add(new AltasBajas(rs.getString("PLAN"), rs.getString("CARRERA"), rs.getString("FACULTAD"), rs.getString("CODIGO"), rs.getString("NOMBRE")));		
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.reportes.AltasBajasUtil|getListAltasBajas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<AltasBajas> getListBajas(Connection con, String cargaId) throws SQLException{
		
		ResultSet rs 					= null;
		PreparedStatement ps			= null;
		String comando					= "";
		ArrayList<AltasBajas> lis 		= new ArrayList<AltasBajas>();

		try{		
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO,"
					+ " ENOC.ALUM_APELLIDO(CODIGO_PERSONAL) AS NOMBRE,"
					+ " PLAN_ID AS PLAN,"
					+ " CARRERA_ID AS CARRERA,"
					+ " FACULTAD_ID AS FACULTAD"
					+ " FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = ? AND ESTADO = '3'"
					+ " ORDER BY 5,4,2";			
			ps = con.prepareStatement(comando);		
			ps.setString(1,cargaId);
			
			rs = ps.executeQuery();
			while (rs.next()){							
				lis.add(new AltasBajas(rs.getString("PLAN"), rs.getString("CARRERA"), rs.getString("FACULTAD"), rs.getString("CODIGO"), rs.getString("NOMBRE")));		
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.reportes.AltasBajasUtil|getListAltasBajas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public int getNumCursos(Connection con, String cargaId, String matricula, String cadena) throws SQLException{
		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String comando			= "";
		int numero				= 0;
		try{			
			comando = "SELECT SUM(CREDITOS) AS CUENTA " +
					  "FROM ENOC.KRDX_CURSO_ACT K, ENOC.MAPA_CURSO M  " + 
					  "WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? " + 
					  "AND K.CURSO_ID = M.CURSO_ID " +
					  "AND CODIGO_PERSONAL = ?  " + cadena;
			ps = con.prepareStatement(comando);
			ps.setString(1,cargaId);
			ps.setString(2,matricula);			
			rs = ps.executeQuery();
			if (rs.next()){
				numero = rs.getInt("CUENTA");			
						
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.reportes.AltasBajasUtil|getNumCursos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numero;
	}
	
	public int getCreditosCalculo(Connection con, String cargaId, String matricula, String cadena) throws SQLException{
		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String comando			= "";
		int numero				= 0;

		try{
			
			comando = "SELECT SUM(CREDITOS) AS CREDITOS " +
					  "FROM MATEO.FES_CC_MATERIA " +
					  "WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? " +					  
					  "AND MATRICULA = ?  " + cadena ;
			
			ps = con.prepareStatement(comando);		
			ps.setString(1,cargaId);
			ps.setString(2,matricula);
			
			
			rs = ps.executeQuery();
			if (rs.next()){
				numero = rs.getInt("CREDITOS");	
						
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.reportes.AltasBajasUtil|getCreditosCalculo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numero;
	}
	
	public String  getAlumEstado(Connection con, String cargaId, String matricula) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps	= null;		
		String estado			= "";
		try{			
			String comando = "SELECT COALESCE(BLOQUE_ID||'-'|| ESTADO,'0-X') AS ESTADO " +
					  "FROM ENOC.ALUM_ESTADO " +	 
					  "WHERE CARGA_ID = ? " +
					  "AND CODIGO_PERSONAL = ? " +
					  "AND ESTADO  != 'M'" ;			
			ps = con.prepareStatement(comando);		
			ps.setString(1,cargaId);
			ps.setString(2,matricula);						
			rs = ps.executeQuery();
			while (rs.next()){
				estado = estado + " " + rs.getString("ESTADO");			
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.reportes.AltasBajasUtil|getAlumEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return estado;
	}
	
	public HashMap<String, String> mapAlumnos(Connection conn ) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = "SELECT CODIGO_PERSONAL, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS ALUMNO FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE ESTADO = '3')";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("CODIGO_PERSONAL"),rs.getString("ALUMNO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.reportes.AltasBajasUtil|mapAlumnos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
}