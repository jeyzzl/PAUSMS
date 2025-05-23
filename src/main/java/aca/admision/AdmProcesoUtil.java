package aca.admision;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AdmProcesoUtil {
	public ArrayList<AdmProceso> getAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<AdmProceso> list	= new ArrayList<AdmProceso>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT FOLIO, TO_CHAR(F_REGISTRO, 'DD/MM/YYYY') AS F_REGISTRO, TO_CHAR(F_SOLICITUD, 'DD/MM/YYYY') AS F_SOLICITUD," +
					"  TO_CHAR(F_DOCUMENTOS, 'DD/MM/YYYY') AS F_DOCUMENTOS, TO_CHAR(F_ADMISION, 'DD/MM/YYYY') AS F_ADMISION," +
					"  TO_CHAR(F_CARTA, 'DD/MM/YYYY') AS F_CARTA" +
					" FROM SALOMON.ADM_PROCESO " + orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AdmProceso proceso = new AdmProceso();
				proceso.mapeaReg(rs);
				list.add(proceso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmProcesoUtil|getAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public int getCantidad(Connection conn, String orden) throws SQLException{
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT COUNT(FOLIO) AS CANTIDAD" +
					" FROM SALOMON.ADM_PROCESO " + orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				return rs.getInt("CANTIDAD");
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmProcesoUtil|getCantidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return 0;
	}
	
	public ArrayList<String> listaAnios(Connection conn, int columnaBase, String orden) throws SQLException{
		ArrayList<String> lista	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		String nombrColumna = "F_REGISTRO";
		if(columnaBase==3) nombrColumna = "F_SOLICITUD";
		if(columnaBase==4) nombrColumna = "F_DOCUMENTOS";
		if(columnaBase==5) nombrColumna = "F_ADMISION";
		if(columnaBase==6) nombrColumna = "F_CARTA";
		try{
			comando = "SELECT DISTINCT(TO_CHAR("+nombrColumna+", 'YYYY')) AS ANIOS FROM SALOMON.ADM_PROCESO " + orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				if(rs.getString("ANIOS")!=null) lista.add(rs.getString("ANIOS"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmProcesoUtil|listaAnios|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public ArrayList<String> listaMeses(Connection conn, int columnaBase, String anio, String orden) throws SQLException{
		ArrayList<String> lista	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";

		String nombrColumna = "F_REGISTRO";
		if(columnaBase==3) nombrColumna = "F_SOLICITUD";
		if(columnaBase==4) nombrColumna = "F_DOCUMENTOS";
		if(columnaBase==5) nombrColumna = "F_ADMISION";
		if(columnaBase==6) nombrColumna = "F_CARTA";
		try{
			comando = "SELECT DISTINCT(TO_CHAR("+nombrColumna+", 'MM')) AS MESES " +
					" FROM SALOMON.ADM_PROCESO WHERE TO_CHAR("+nombrColumna+", 'YYYY')='"+anio+"' " + orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				if(rs.getString("MESES")!=null) lista.add(rs.getString("MESES"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmProcesoUtil|listaMeses|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public ArrayList<String> listaDias(Connection conn, String anio, String mes, String orden) throws SQLException{
		ArrayList<String> lista	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT DISTINCT(TO_CHAR(F_REGISTRO, 'DD')) AS DIAS " +
					" FROM SALOMON.ADM_PROCESO WHERE TO_CHAR(F_REGISTRO, 'YYYY')='"+anio+"'" +
					" AND TO_CHAR(F_REGISTRO, 'MM')='"+mes+"' " + orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				lista.add(rs.getString("DIAS"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmProcesoUtil|listaDias|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
}