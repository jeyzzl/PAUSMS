package aca.kardex;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AlumnoTituloUtil {
public ArrayList<KrdxAlumnoTitulo> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<KrdxAlumnoTitulo> lisTitulo		= new ArrayList<KrdxAlumnoTitulo>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CARRERA_ID, PRESIDENTE, SECRETARIO, MIEMBRO, COMENTARIO,  NOTA, USUARIO, ESTADO "+
				"FROM ENOC.KRDX_ALUMNO_TITULO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				KrdxAlumnoTitulo actividad = new KrdxAlumnoTitulo();
				actividad.mapeaReg(rs);
				lisTitulo.add(actividad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.AlumnoTituloUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisTitulo;
	}
	
	public ArrayList<KrdxAlumnoTitulo> getLista(Connection conn, String codigoPersonal, String cursoCargaId, String orden ) throws SQLException{
		
		ArrayList<KrdxAlumnoTitulo> lisActiv	= new ArrayList<KrdxAlumnoTitulo>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CARRERA_ID, PRESIDENTE, SECRETARIO, MIEMBRO, COMENTARIO,  NOTA, USUARIO, ESTADO "+
					"FROM ENOC.KRDX_ALUMNO_TITULO "+ 
					"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+
					"AND CURSO_CARGA_ID = '"+cursoCargaId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				KrdxAlumnoTitulo actividad = new KrdxAlumnoTitulo();
				actividad.mapeaReg(rs);
				lisActiv.add(actividad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.AlumnoTituloUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisActiv;
	}
}