package aca.afe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AfeCCostoPuestoUtil {
	
	public AfeCCostoPuesto mapeaRegId(Connection con, String id) throws SQLException{
		AfeCCostoPuesto AfeCCP = new AfeCCostoPuesto();
		PreparedStatement ps = null;
		ResultSet rs = null;
		 
		try{
			ps = con.prepareStatement("SELECT ID,EJERCICIO_ID, CCOSTO_ID, PUESTO_ID, "+
					"TURNO,DIAS, REQUISITOS, EMAIL, MAXIMO_HORAS, CLAVE, STATUS "+
					"FROM NOE.AFE_CCOSTO_PUESTO WHERE ID = ? ");
			ps.setString(1,id);
			rs = ps.executeQuery();
			
			if(rs.next()){
				AfeCCP.mapeaReg(rs);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AfeCCostoPuestoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return AfeCCP;
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
			System.out.println("Error - aca.afe.AfeCCostoPuestoUtil|getCCostoNombre|:"+ex);
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
			System.out.println("Error - aca.afe.AfeCCostoPuestoUtil|getPuestoNombre|:"+ex);
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
		String precio	= " ";
		
		try{
			
			comando = "SELECT PRECIO_HORA FROM NOE.AFE_CAT_CATEGORIA" +
					" WHERE ID = (SELECT AFE_CATEGORIA_ID FROM NOE.AFE_CAT_PUESTO WHERE ID ="+id+")";
			rs = st.executeQuery(comando);
			if (rs.next()){
				precio 	= rs.getString(1);				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AfeCCostoPuestoUtil|getPrecio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return precio;
	}
	

}