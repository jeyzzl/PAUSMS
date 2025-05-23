// Clase Util para la tabla de Adm_requisito
package adm.documento;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class RequisitoUtil{
		
	public ArrayList<AdmRequisito> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AdmRequisito> lisRequisito	= new ArrayList<AdmRequisito>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARRERA_ID, DOCUMENTO_ID, MODALIDADES, REQUERIDO FROM SALOMON.ADM_REQUISITO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AdmRequisito requisito = new AdmRequisito();
				requisito.mapeaReg(rs);
				lisRequisito.add(requisito);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.RequisitoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisRequisito;
	}
	
	public ArrayList<AdmRequisito> getLista(Connection conn, String carreraId, String orden ) throws SQLException{
		
		ArrayList<AdmRequisito> lisRequisito	= new ArrayList<AdmRequisito>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CARRERA_ID, DOCUMENTO_ID, MODALIDADES, REQUERIDO FROM SALOMON.ADM_REQUISITO"+
				" WHERE CARRERA_ID = '"+carreraId+"' " + orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AdmRequisito requisito = new AdmRequisito();
				requisito.mapeaReg(rs);
				lisRequisito.add(requisito);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.RequisitoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisRequisito;
	}
	
	public HashMap<String,AdmRequisito> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,AdmRequisito> mapRequisito = new HashMap<String,AdmRequisito>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT CARRERA_ID, DOCUMENTO_ID, MODALIDADES, REQUERIDO FROM SALOMON.ADM_REQUISITO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AdmRequisito requisito = new AdmRequisito();
				requisito.mapeaReg(rs);
				llave = requisito.getCarreraId()+requisito.getDocumentoId() ;
				mapRequisito.put(llave, requisito);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ModalidadUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapRequisito;
	}
	
}