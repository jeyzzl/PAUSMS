/*
 * Created on 26/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package aca.financiero;

/**
 * @author Jose Ery Torres
 *
 * 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

import java.sql.*;
import java.text.DecimalFormat;

public class EstadoCuenta {
	
	// Rgeresa el saldo del alumno en String
	public static String saldoAlumno( Connection conn, String codigoPersonal) throws SQLException, Exception{
		
		PreparedStatement ps	= null;
	    ResultSet rs			= null;

		DecimalFormat getformato= new DecimalFormat("###,##0.00;(###,##0.00)");	
		String COMANDO = "";

		double numSaldo			= 0;
		String strSaldo			= "0";
		
		try{
					
			COMANDO = "SELECT" +
				" COALESCE(SUM(IMPORTE* CASE NATURALEZA WHEN 'C' THEN 1 ELSE -1 END),0) AS SALDO" +
				" FROM MATEO.CONT_MOVIMIENTO" +
				" WHERE ID_AUXILIARM=?" +
				" AND ID_EJERCICIO ='001-'||TO_CHAR(now(),'YYYY')" +
				" AND ID_CTAMAYORM IN('1.1.04.01','1.1.04.29','1.1.04.30')";
			ps = conn.prepareStatement(COMANDO);
			ps.setString(1,codigoPersonal);
			rs=ps.executeQuery();
			if(rs.next()){ 
				numSaldo	=rs.getDouble("SALDO");		
				strSaldo 	= getformato.format(numSaldo);
			}
		}catch(Exception ex){
 			System.out.println("Error - aca.financiero.EstadoCuenta|saldoAlumno|:"+ex);
 		}finally{
 			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
 		}	
		
		return strSaldo;	
	}
	
	// Regresa el saldo del alumno en double
	public static double saldoAlumnoD( Connection conn, String codigoPersonal) throws SQLException, Exception{

		PreparedStatement ps	= null;
	    ResultSet rs			= null;		
		String COMANDO 			= "";
		double numSaldo			= 0;
		
		try{
					
			COMANDO = "SELECT" +
				" COALESCE(SUM(IMPORTE* CASE NATURALEZA WHEN 'C' THEN 1 ELSE -1 END),0) AS SALDO" +
				" FROM MATEO.CONT_MOVIMIENTO" +
				" WHERE ID_AUXILIARM=?" +
				" AND ID_EJERCICIO ='001-'||TO_CHAR(now(),'YYYY')" +
				" AND ID_CTAMAYORM IN('1.1.04.01','1.1.04.29','1.1.04.30')";
			ps = conn.prepareStatement(COMANDO);
			ps.setString(1,codigoPersonal);
			rs=ps.executeQuery();
			if(rs.next()){ 
				numSaldo	= rs.getDouble("SALDO");
			}
		}catch(Exception ex){
 			System.out.println("Error - aca.financiero.EstadoCuenta|saldoAlumnoD|:"+ex);
 		}finally{
 			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
		return numSaldo;
	}

	public static double getPagareNoVencido( Connection conn, String codigoPersonal) throws SQLException, Exception{

		Statement st			= conn.createStatement();
	    ResultSet rs			= null;		
		double saldoPagare		= 0;
		String comando 			= "";
		
		try{		
			comando = "SELECT " +
					" SUM(IMPORTE) AS SALDO FROM MATEO.FES_CC_PAGARE_DET" +
					" WHERE MATRICULA = '"+codigoPersonal+"'"+			
					" AND TO_DATE(TO_CHAR(FVENCIMIENTO,'DD-MM-YY'),'DD-MM-YY') >= TO_DATE(TO_CHAR(now(),'DD-MM-YY'),'DD-MM-YY')" +
					" AND STATUS = 'A'";
			rs = st.executeQuery(comando);			
		    if(rs.next()){ 
				saldoPagare	= rs.getDouble("SALDO");
			}
		}catch(Exception ex){
 			System.out.println("Error - aca.financiero.EstadoCuenta|getPagareNoVencido|:"+ex);
 		}finally{
 			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
 		}
 		
		return saldoPagare;
	}
	public static double getCostoSemestre( Connection conn, String codigoPersonal, String cargaId, String bloqueId) throws SQLException, Exception{

	       Statement st            = conn.createStatement();
	       ResultSet rs            = null;        
	       double saldoPagare      = 0;
	       String comando             = "";    
	       
	       try{        
	           comando = "SELECT COALESCE(SUM(IMPORTE*CASE WHEN NATURALEZA = 'D' THEN -1 ELSE 1 END ),0) AS SALDO" +
	                   " FROM MATEO.FES_CC_MOVIMIENTO" +
	                   " WHERE MATRICULA = '"+codigoPersonal+"'" +
	                   " AND CARGA_ID = '"+cargaId+"'" +
	                   " AND BLOQUE = '"+bloqueId+"'" +
	                   " AND (TIPOMOV BETWEEN 1 AND 10 OR TIPOMOV IN (20,22,24,26,27) )";
	           rs = st.executeQuery(comando);            
	           if(rs.next()){ 
	               saldoPagare    = rs.getDouble("SALDO");
	           }
	       }catch(Exception ex){
	            System.out.println("Error - aca.financiero.EstadoCuenta|getCostoSemestre|:"+ex);
	        }finally{
	            try { rs.close(); } catch (Exception ignore) { }
	           try { st.close(); } catch (Exception ignore) { }
	        }
	        
	       return saldoPagare;
	   }
	
}