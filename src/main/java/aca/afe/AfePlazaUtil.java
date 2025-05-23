package aca.afe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AfePlazaUtil {
	
	public AfePlaza mapeaRegId(Connection con, String id) throws SQLException{
		AfePlaza afeP = new AfePlaza();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT ID,TIPOPLAZA, EJERCICIO_ID, CCOSTO_ID, "+
					"PUESTO_ID,TURNO, DIAS, REQUISITOS, EMAIL, MAXIMO_HORAS_UNIV, MAXIMO_HORAS_BACH, "+
					"PROYECTO_ID,IMPORTE, STATUS, CLAVE "+
					"FROM NOE.AFE_PLAZA WHERE ID = ? ");
			ps.setString(1,id);
			rs = ps.executeQuery();
			
			if(rs.next()){
				afeP.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AfePlazaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return afeP;
	}	
	
	public static String getCCostoNombre(Connection conn, String ejercicioId, String ccostoId) throws Exception{
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String departamento	= " ";
		
		try{
			comando = "SELECT NOMBRE FROM MATEO.CONT_CCOSTO" +
					" WHERE ID_EJERCICIO = '"+ejercicioId+"'" +
					" AND ID_CCOSTO = '"+ccostoId+"'";
			rs = st.executeQuery(comando);
			if (rs.next()){
				departamento 	= rs.getString(1);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AfePlazaUtil|getCCostoNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return departamento;
	}
	
	public static String getPuestoNombre(Connection conn, String puestoId ) throws Exception{		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String departamento	= " ";
		
		try{
			comando = "SELECT NOMBRE FROM NOE.AFE_CAT_PUESTO" +
					" WHERE ID = '"+puestoId+"'";
			rs = st.executeQuery(comando);
			if (rs.next()){
				departamento 	= rs.getString(1);				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AfePlazaUtil|getPuestoNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return departamento;
	}
	
	public static String getPrecio(Connection conn, String id ) throws Exception{
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String precio   	= " ";
		
		try{
			
			comando = "SELECT PRECIO_HORA FROM NOE.AFE_CAT_CATEGORIA" +
					" WHERE ID = (SELECT AFE_CATEGORIA_ID FROM NOE.AFE_CAT_PUESTO WHERE ID ="+id+")";
			rs = st.executeQuery(comando);
			if (rs.next()){
				precio 	= rs.getString(1);				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AfePlazaUtil|getPrecio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return precio;
	}
		
	public ArrayList<AfePlaza> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AfePlaza> lisAcceso 	= new ArrayList<AfePlaza>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	    = "";
		
		try{			
			comando = "SELECT ID,TIPOPLAZA, EJERCICIO_ID, COALESCE(CCOSTO_ID,'-') AS CCOSTO_ID, PUESTO_ID,TURNO," +
					" DIAS, REQUISITOS, EMAIL, " +
					" COALESCE(MAXIMO_HORAS_UNIV,0) AS MAXIMO_HORAS_UNIV," +
					" COALESCE(MAXIMO_HORAS_BACH,0) AS MAXIMO_HORAS_BACH," +
					" COALESCE(PROYECTO_ID,0) AS PROYECTO_ID, " +
					" COALESCE(IMPORTE,0) AS IMPORTE, STATUS, CLAVE" +					
					" FROM NOE.AFE_PLAZA " +
					" WHERE EJERCICIO_ID ='001-'||(SELECT TRIM(TO_CHAR(now(),'YYYY')) FROM ENOC.CAT_AREA) AND STATUS='A' " +orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AfePlaza acceso = new AfePlaza();
				acceso.mapeaReg(rs);
				lisAcceso.add(acceso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AfePlazaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAcceso;
	}	
	
	public ArrayList<AfePlaza> getListVacantes(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AfePlaza> lisAcceso 	= new ArrayList<AfePlaza>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	    = "";
		
		try{			
			comando = "SELECT ID, TIPOPLAZA, EJERCICIO_ID, COALESCE(CCOSTO_ID,'-') AS CCOSTO_ID, PUESTO_ID, TURNO," +
					" DIAS, REQUISITOS, EMAIL, " +
					" COALESCE(MAXIMO_HORAS_UNIV,0) AS MAXIMO_HORAS_UNIV," +
					" COALESCE(MAXIMO_HORAS_BACH,0) AS MAXIMO_HORAS_BACH," +
					" COALESCE(PROYECTO_ID,0) AS PROYECTO_ID, " +
					" COALESCE(IMPORTE,0) AS IMPORTE, STATUS, CLAVE" +
					" FROM NOE.AFE_PLAZA " +
					" WHERE EJERCICIO_ID = '001-'||(SELECT TRIM(TO_CHAR(now(),'YYYY')) FROM ENOC.CAT_AREA) AND STATUS='A' " + 
					" AND ID NOT IN (SELECT PLAZA_ID FROM NOE.AFE_CONTRATO_ALUMNO WHERE STATUS IN('A','I')) "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AfePlaza acceso = new AfePlaza();
				acceso.mapeaReg(rs);
				lisAcceso.add(acceso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AfePlazaUtil|getListVacantes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAcceso;
	}
	
	public ArrayList<AfePlaza> getListAsignados(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AfePlaza> lisAcceso 	= new ArrayList<AfePlaza>();
		Statement st 		            = conn.createStatement();
		ResultSet rs            		= null;
		String comando	                = "";
		
		try{			
			comando = "SELECT ID,TIPOPLAZA, EJERCICIO_ID, COALESCE(CCOSTO_ID,'-') AS CCOSTO_ID, PUESTO_ID,TURNO," +
					" DIAS, REQUISITOS, EMAIL, " +
					" COALESCE(MAXIMO_HORAS_UNIV,0) AS MAXIMO_HORAS_UNIV," +
					" COALESCE(MAXIMO_HORAS_BACH,0) AS MAXIMO_HORAS_BACH," +
					" COALESCE(PROYECTO_ID,0) AS PROYECTO_ID, " +
					" COALESCE(IMPORTE,0) AS IMPORTE, STATUS, CLAVE" +					
					" FROM NOE.AFE_PLAZA " +
					" WHERE EJERCICIO_ID ='001-'||(SELECT TRIM(TO_CHAR(now(),'YYYY')) FROM ENOC.CAT_AREA) AND STATUS='A' " + 
					" AND ID IN (SELECT PLAZA_ID FROM NOE.AFE_CONTRATO_ALUMNO WHERE STATUS IN('A','I')) "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AfePlaza acceso = new AfePlaza();
				acceso.mapeaReg(rs);
				lisAcceso.add(acceso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AfePlazaUtil|getListAsignados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAcceso;
	}

}