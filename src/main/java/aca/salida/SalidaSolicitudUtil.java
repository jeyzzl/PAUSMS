package aca.salida;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import aca.salida.SalidaSolicitud;

public class SalidaSolicitudUtil {
	
	public ArrayList<SalidaSolicitud> getListAll(Connection conn, String orden ) throws SQLException{
		ArrayList<SalidaSolicitud> listSalidaSolicitud 	= new ArrayList<SalidaSolicitud>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT SALIDA_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, PROPOSITO, OTRO_PROPOSITO, GRUPO_ID,"+
					" TO_CHAR(FECHA_SALIDA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_SALIDA, "+
					" TO_CHAR(FECHA_LLEGADA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_LLEGADA,"+
			        " LUGAR, ALIMENTO, DESAYUNO, COMIDA, CENA, HOSPEDAJE, TRANSPORTE, USUARIO, RESPONSABLE, AUTORIZO, "+
					" FOLIO, TOTAL, TOTAL_PERSONA, COMENTARIO, ESTADO, LUGAR_SALIDA, PREAUTORIZO " +
					" FROM ENOC.SAL_SOLICITUD "+orden; 
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				SalidaSolicitud salidaSolicitud = new SalidaSolicitud();				
				salidaSolicitud.mapeaReg(rs);
				listSalidaSolicitud.add(salidaSolicitud);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaSolicitudUtil|getListAll|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }		
		}
		
		return listSalidaSolicitud;
	}
	
	public ArrayList<SalidaSolicitud> lisPorFecha(Connection conn, String fechaIni, String fechaFin, String orden ) throws SQLException{
		ArrayList<SalidaSolicitud> listSalidaSolicitud 	= new ArrayList<SalidaSolicitud>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = " SELECT SALIDA_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, PROPOSITO, OTRO_PROPOSITO, GRUPO_ID,"
					+ " TO_CHAR(FECHA_SALIDA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_SALIDA,"
					+ " TO_CHAR(FECHA_LLEGADA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_LLEGADA,"
			        + " LUGAR, ALIMENTO, DESAYUNO, COMIDA, CENA, HOSPEDAJE, TRANSPORTE, USUARIO, RESPONSABLE, AUTORIZO, FOLIO, TOTAL, TOTAL_PERSONA, COMENTARIO, ESTADO, LUGAR_SALIDA, PREAUTORIZO "
					+ " FROM ENOC.SAL_SOLICITUD"
					+ " WHERE ENOC.SAL_SOLICITUD.FECHA_SALIDA BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') "+orden; 
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				SalidaSolicitud salidaSolicitud = new SalidaSolicitud();				
				salidaSolicitud.mapeaReg(rs);
				listSalidaSolicitud.add(salidaSolicitud);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaSolicitudUtil|lisPorFecha|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }		
		}
		
		return listSalidaSolicitud;
	}
	
	public ArrayList<SalidaSolicitud> lisPorGrupoyFecha(Connection conn, String grupoId, String fechaIni, String fechaFin, String orden ) throws SQLException{
		ArrayList<SalidaSolicitud> listSalidaSolicitud 	= new ArrayList<SalidaSolicitud>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		String filtro 	= "";
		if (!grupoId.equals("0")) {
			filtro = " AND GRUPO_ID = "+grupoId+" ";
		}
		try{
			comando = " SELECT SALIDA_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, PROPOSITO, OTRO_PROPOSITO, GRUPO_ID,"
					+ " TO_CHAR(FECHA_SALIDA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_SALIDA,"
					+ " TO_CHAR(FECHA_LLEGADA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_LLEGADA,"
			        + " LUGAR, ALIMENTO, DESAYUNO, COMIDA, CENA, HOSPEDAJE, TRANSPORTE, USUARIO, RESPONSABLE, AUTORIZO, FOLIO, TOTAL, TOTAL_PERSONA, COMENTARIO, ESTADO, LUGAR_SALIDA, PREAUTORIZO "
					+ " FROM ENOC.SAL_SOLICITUD"
					+ " WHERE ENOC.SAL_SOLICITUD.FECHA_SALIDA BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') "
					+ " " + filtro + orden; 
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				SalidaSolicitud salidaSolicitud = new SalidaSolicitud();				
				salidaSolicitud.mapeaReg(rs);
				listSalidaSolicitud.add(salidaSolicitud);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaSolicitudUtil|lisPorFecha|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }		
		}
		
		return listSalidaSolicitud;
	}
	
	public ArrayList<SalidaSolicitud> getListUsuario(Connection conn, String codigoPersonal, String orden ) throws SQLException{
		ArrayList<SalidaSolicitud> listSalidaSolicitud 	= new ArrayList<SalidaSolicitud>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT SALIDA_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, PROPOSITO, OTRO_PROPOSITO, GRUPO_ID,"
					+ " TO_CHAR(FECHA_SALIDA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_SALIDA,"
					+ " TO_CHAR(FECHA_LLEGADA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_LLEGADA,"
			        + " LUGAR, ALIMENTO, DESAYUNO, COMIDA, CENA, HOSPEDAJE, TRANSPORTE, USUARIO, RESPONSABLE, AUTORIZO, FOLIO, TOTAL, TOTAL_PERSONA, COMENTARIO, ESTADO, LUGAR_SALIDA, PREAUTORIZO "
					+ " FROM ENOC.SAL_SOLICITUD"
					+ " WHERE USUARIO = '"+codigoPersonal+"' "+orden; 
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				SalidaSolicitud salidaSolicitud = new SalidaSolicitud();				
				salidaSolicitud.mapeaReg(rs);
				listSalidaSolicitud.add(salidaSolicitud);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaSolicitudUtil|getListUsuario|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }		
		}
		
		return listSalidaSolicitud;
	}
	
	public ArrayList<SalidaSolicitud> getListUsuarioPorFecha(Connection conn, String codigoPersonal, String fechaIni, String fechaFin, String orden ) throws SQLException{
		ArrayList<SalidaSolicitud> listSalidaSolicitud 	= new ArrayList<SalidaSolicitud>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = " SELECT SALIDA_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, PROPOSITO, OTRO_PROPOSITO, GRUPO_ID,"
					+ " TO_CHAR(FECHA_SALIDA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_SALIDA,"
					+ " TO_CHAR(FECHA_LLEGADA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_LLEGADA,"
			        + " LUGAR, ALIMENTO, DESAYUNO, COMIDA, CENA, HOSPEDAJE, TRANSPORTE, USUARIO, RESPONSABLE, AUTORIZO, FOLIO, TOTAL, TOTAL_PERSONA, COMENTARIO, ESTADO, LUGAR_SALIDA, PREAUTORIZO "
					+ " FROM ENOC.SAL_SOLICITUD"
					+ " WHERE GRUPO_ID IN (SELECT GRUPO_ID FROM ENOC.SAL_GRUPO WHERE USUARIOS LIKE '%-"+codigoPersonal+"-%')"
					+ " AND ENOC.SAL_SOLICITUD.FECHA_SALIDA BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') "+orden; 
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				SalidaSolicitud salidaSolicitud = new SalidaSolicitud();				
				salidaSolicitud.mapeaReg(rs);
				listSalidaSolicitud.add(salidaSolicitud);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaSolicitudUtil|getListUsuarioPorFecha|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }		
		}
		
		return listSalidaSolicitud;
	}
	
	public ArrayList<SalidaSolicitud> getListUsuarioPorFecha(Connection conn, String fechaIni, String fechaFin, String orden ) throws SQLException{
		ArrayList<SalidaSolicitud> listSalidaSolicitud 	= new ArrayList<SalidaSolicitud>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = " SELECT SALIDA_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, PROPOSITO, OTRO_PROPOSITO, GRUPO_ID,"
					+ " TO_CHAR(FECHA_SALIDA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_SALIDA,"
					+ " TO_CHAR(FECHA_LLEGADA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA_LLEGADA,"
			        + " LUGAR, ALIMENTO, DESAYUNO, COMIDA, CENA, HOSPEDAJE, TRANSPORTE, USUARIO, RESPONSABLE, AUTORIZO, FOLIO, TOTAL, TOTAL_PERSONA, COMENTARIO, ESTADO, LUGAR_SALIDA, PREAUTORIZO "
					+ " FROM ENOC.SAL_SOLICITUD"
					+ " WHERE ENOC.SAL_SOLICITUD.FECHA_SALIDA BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') "+orden; 
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				SalidaSolicitud salidaSolicitud = new SalidaSolicitud();				
				salidaSolicitud.mapeaReg(rs);
				listSalidaSolicitud.add(salidaSolicitud);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaSolicitudUtil|getListUsuarioPorFecha|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }		
		}
		
		return listSalidaSolicitud;
	}
}