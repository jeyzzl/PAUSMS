package aca.musica;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MusiCalculoUtil {

	public ArrayList<MusiCalculo> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<MusiCalculo> lisCalculo	= new ArrayList<MusiCalculo>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_ID,PERIODO_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA," +
					" FORMA_PAGO, NUMPAGARE, PAGARE1, PAGARE2, PAGARE3, INSTITUCION_ID, EMPLEADO, SOBRESUELDO, ESTADO," +
					" PAGO_INICIAL, MATRICULA, RENTA, ENSENANZA, PORCENTAJE, NUMPAGARE, HAYPAGOINI"+
					" FROM ENOC.MUSI_CALCULO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MusiCalculo mcal = new MusiCalculo();
				mcal.mapeaReg(rs);
				lisCalculo.add(mcal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiCaluloUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCalculo;
	}
	
	public ArrayList<MusiCalculo> getListPagare(Connection conn, String periodoId, String orden) throws SQLException{
		
		ArrayList<MusiCalculo> lisCalculo	= new ArrayList<MusiCalculo>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_ID, PERIODO_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA," +
					" FORMA_PAGO, PAGARE1, PAGARE2, PAGARE3," +
					" (SELECT INSTITUCION_ID FROM ENOC.MUSI_ALUMNO WHERE CODIGO_ID = ENOC.MUSI_CALCULO.CODIGO_ID) AS INSTITUCION_ID, " + 
					" EMPLEADO, SOBRESUELDO, ESTADO," +
					" PAGO_INICIAL, MATRICULA, RENTA, ENSENANZA, PORCENTAJE, NUMPAGARE, HAYPAGOINI"+
					" FROM ENOC.MUSI_CALCULO " + 
					" WHERE ESTADO = 'I' " +
					" AND PERIODO_ID = '"+periodoId+"' " +
					" AND FORMA_PAGO = 'P' " + orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				MusiCalculo mcal = new MusiCalculo();
				mcal.mapeaReg(rs);
				lisCalculo.add(mcal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiCaluloUtil|getListPagare|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCalculo;
	}
	
	
	public static String getPagoInicial(Connection conn, String codigo, String periodoId ) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String pago  		= "0";
		
		try{
			comando = " SELECT PAGO_INICIAL"+
					" FROM ENOC.MUSI_CALCULO " + 
					" WHERE CODIGO_ID = '"+codigo+"'" +
					" AND PERIODO_ID = '"+periodoId+"'";
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				pago = rs.getString("PAGO_INICIAL");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiCaluloUtil|getPagoInicial|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return pago;
	}
	public static String getPagare1(Connection conn, String codigo, String periodoId ) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String pagare1 		= "0";
		
		try{
			comando = " SELECT PAGARE1"+
					" FROM ENOC.MUSI_CALCULO " + 
					" WHERE CODIGO_ID = '"+codigo+"'" +
					" AND PERIODO_ID = '"+periodoId+"'";
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				pagare1 = rs.getString("PAGARE1");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiCaluloUtil|getPagoInicial|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return pagare1;
	}
	
	public static String getPagare2(Connection conn, String codigo, String periodoId ) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String pagare2 		= "0";
		
		try{
			comando = " SELECT PAGARE2"+
					" FROM ENOC.MUSI_CALCULO " + 
					" WHERE CODIGO_ID = '"+codigo+"'" +
					" AND PERIODO_ID = '"+periodoId+"'";
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				pagare2 = rs.getString("PAGARE2");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiCaluloUtil|getPagoInicial|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return pagare2;
	}

	public static String getPagare3(Connection conn, String codigo, String periodoId ) throws SQLException{
	
	Statement st 		= conn.createStatement();
	ResultSet rs 		= null;
	String comando		= "";
	String pagare3 		= "0";
	
	try{
		comando = " SELECT PAGARE3"+
				" FROM ENOC.MUSI_CALCULO " + 
				" WHERE CODIGO_ID = '"+codigo+"'" +
				" AND PERIODO_ID = '"+periodoId+"'";
		
		rs = st.executeQuery(comando);
		if (rs.next()){
			pagare3 = rs.getString("PAGARE3");
		}
		
	}catch(Exception ex){
		System.out.println("Error - aca.musica.MusiCaluloUtil|getPagoInicial|:"+ex);
	}finally{
		try { rs.close(); } catch (Exception ignore) { }
		try { st.close(); } catch (Exception ignore) { }
	}
	
	return pagare3;
}
	
}