package aca.vista;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class FinTablaUtil {
	
	/* Lista de costos en una carga */
	public ArrayList<FinTabla> listCarga(Connection conn, String cargaId, String orden) throws SQLException{
		
		ArrayList<FinTabla> lis 		= new ArrayList<FinTabla>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = " SELECT TABLA_ID, CARGA_ID, CARRERA_ID, MODALIDAD_ID, MATRICULA, PORMATRICULA,"
					+ " LEGALES, PORLEGALES, INTERNADO, PORINTERNADO, ACFE, NOACFE, PORCREDITO, STATUS"
					+ " FROM ENOC.FIN_TABLA"
					+ " WHERE CARGA_ID = '"+cargaId+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				FinTabla obj= new FinTabla();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.FinTablaUtil|listCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public static HashMap<String, String> mapTablaCosto(Connection conn, String cargaId) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = "SELECT CARRERA_ID, MODALIDAD_ID, PCCREDITO FROM NOE.FES_TFINANCIERA_DET "+
					  " WHERE TFINANCIERA_ID IN (SELECT TFINANCIERA_ID FROM NOE.FES_TFINANCIERA_ENC WHERE CARGA_ID = '"+cargaId+"')";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CARRERA_ID")+"@@"+rs.getString("MODALIDAD_ID"), rs.getString("PCCREDITO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.FinTablaUtil|mapTablaCosto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}	
	
	public HashMap<String, FinTabla> mapTablaCargas(Connection conn, String cargas) throws SQLException{
		HashMap<String, FinTabla> mapa = new HashMap<String, FinTabla>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String llave		= "";
		try{
			comando = " SELECT TABLA_ID, CARGA_ID, CARRERA_ID, MODALIDAD_ID, MATRICULA, PORMATRICULA,"
					+ " LEGALES, PORLEGALES, INTERNADO, PORINTERNADO, ACFE, NOACFE, PORCREDITO, STATUS"
					+ " FROM ENOC.FIN_TABLA"
					+ " WHERE TABLA_ID IN (SELECT TFINANCIERA_ID FROM NOE.FES_TFINANCIERA_ENC WHERE CARGA_ID IN ("+cargas+") )";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave = rs.getString("CARGA_ID")+rs.getString("CARRERA_ID")+rs.getString("MODALIDAD_ID");
				FinTabla objeto = new FinTabla();
				objeto.mapeaReg(rs);
				mapa.put(llave, objeto);	
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.FinTablaUtil|mapTablaCargas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
}
