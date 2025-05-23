package aca.financiero;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class ContEjercicioUtil {
	
	public ContEjercicio mapeaRegId(Connection conn, String idEjercicio) throws SQLException, IOException{
		
		ContEjercicio ejercicio = new ContEjercicio();
 		PreparedStatement ps = null;
 		ResultSet 		  rs = null;
 		try{
	 		ps = conn.prepareStatement("SELECT ID_EJERCICIO, NOMBRE, STATUS, MASC_BALANCE, MASC_AUXILIAR, MASC_CCOSTO, NIVEL_CONTABLE, NIVEL_TAUXILIAR" +	 					
	 				" FROM MATEO.CONT_EJERCICIO WHERE ID_EJERCICIO = ?");
	 		ps.setString(1, idEjercicio);
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			ejercicio.mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContEjercicio|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 		
 		return ejercicio;
 	}
	
	public boolean existeReg(Connection conn, String idEjercicio) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 			rs	= null;
		PreparedStatement 	ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE FROM MATEO.FES_CCOBRO WHERE ID_EJERCICIO = ? ");
			ps.setString(1, idEjercicio);
				
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContEjercicio|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getEjercicioActual(Connection conn, String empresa){
		
		String ejercicioId		= "0";
		
		try{
			ejercicioId = empresa+"-"+aca.util.Fecha.getHoy().substring(6,10);
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContEjercicio|getEjercicioActual|:"+ex);
		}
		
		return ejercicioId;
	}
	
	public ArrayList<ContEjercicio> getListAll(Connection conn, String orden) throws SQLException{
		ArrayList<ContEjercicio> lista 	= new ArrayList<ContEjercicio>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{			
			comando = "SELECT ID_EJERCICIO, NOMBRE, STATUS, MASC_BALANCE, MASC_AUXILIAR, MASC_CCOSTO, NIVEL_CONTABLE, NIVEL_TAUXILIAR FROM MATEO.CONT_EJERCICIO "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				ContEjercicio depto = new ContEjercicio();
				depto.mapeaReg(rs);
				lista.add(depto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContEjercicioUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public ArrayList<ContEjercicio> getListProximos (Connection conn, String orden) throws SQLException{
		ArrayList<ContEjercicio> lista = new ArrayList<ContEjercicio>();
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando = "";
		try{
			comando = "SELECT * FROM MATEO.CONT_EJERCICIO WHERE SUBSTR(ID_EJERCICIO, 5) >= '2013' "+orden;
			rs = st.executeQuery(comando);
			while(rs.next()){
				ContEjercicio ej = new ContEjercicio();
				ej.mapeaReg(rs);
				lista.add(ej);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContEjercicioUtil|getListProximos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lista; 
	}
}