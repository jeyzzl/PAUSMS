package aca.financiero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class FesContratoFinancieroUtil {
	
	public ArrayList<FesContratoFinanciero> getListAlumContrato(Connection conn, String codigoPersonal, String orden ) throws SQLException{
		ArrayList<FesContratoFinanciero> lisAlumno	= new ArrayList<FesContratoFinanciero>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT ID, MATRICULA," +
				" TO_CHAR(FECHA_VENCIMIENTO, 'DD/MM/YYYY') AS FECHA_VENCIMIENTO," +
				" IMPORTE, LIQUIDADO," +
				" TO_CHAR(FECHA_LIQUIDACION, 'DD/MM/YYYY') AS FECHA_LIQUIDACION," +
				" OBSERVACIONES" +
				" FROM NOE.FES_CONTRATO_FINANCIERO " +
				" WHERE MATRICULA = '"+codigoPersonal+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				FesContratoFinanciero contrato = new FesContratoFinanciero();
				contrato.mapeaReg(rs);
				lisAlumno.add(contrato);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesContratoFinancieroUtil|getListAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumno;
	}

	public ArrayList<FesContratoFinanciero> getListAll(Connection conn, String orden ) throws SQLException{
		ArrayList<FesContratoFinanciero> lisAlumno	= new ArrayList<FesContratoFinanciero>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT ID, MATRICULA," +
				" TO_CHAR(FECHA_VENCIMIENTO, 'DD/MM/YYYY') AS FECHA_VENCIMIENTO," +
				" IMPORTE, LIQUIDADO," +
				" TO_CHAR(FECHA_LIQUIDACION, 'DD/MM/YYYY') AS FECHA_LIQUIDACION," +
				" OBSERVACIONES" +
				" FROM ENOC.FES_CONTRATO_FINANCIERO "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				FesContratoFinanciero contrato = new FesContratoFinanciero();
				contrato.mapeaReg(rs);
				lisAlumno.add(contrato);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesContratoFinancieroUtil|getListAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumno;
	}
	
	// Determina el saldo vencido del contrato financiero
	public static double getAlumDeudaContrato(Connection conn, String codigoPersonal) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;		
		double saldo = 0;		
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(SUM(IMPORTE),0) AS DEUDA FROM NOE.FES_CONTRATO_FINANCIERO "+
				" WHERE MATRICULA = ?" +
				" AND TO_DATE(now(),'DD-MM-YYYY') > TO_DATE(FECHA_VENCIMIENTO,'DD-MM-YYYY')" +
				" AND LIQUIDADO = 'N'");
			ps.setString(1, codigoPersonal);
		
			rs= ps.executeQuery();	
			if(rs.next()){
				saldo = rs.getDouble("DEUDA");
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesContratoFinancieroUtil|getAlumDeudaContrato|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }			
		}		
		
		return saldo;
	}
	
	// Obtiene el saldo global del alumno
	public static double getAlumSaldoGlobal(Connection conn, String codigoPersonal) throws SQLException{		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;		
		double saldo = 0;		
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(SALDOGLOBAL,0) AS DEUDA FROM NOE.SALDOS_ALUMNOS "+
				" WHERE MATRICULA = ?");
			ps.setString(1, codigoPersonal);
		
			rs= ps.executeQuery();	
			if(rs.next()){
				saldo = rs.getDouble("DEUDA");
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesContratoFinancieroUtil|getAlumSaldoGlobal|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }			
		}		
		return saldo;
	}
	
	// Obtiene el saldo vencido del contrato financiero (VISTA)
	public static double getAlumSaldoVencido(Connection conn, String codigoPersonal) throws SQLException{		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;		
		double saldo = 0;		
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(SVENCIDO,0) AS DEUDA FROM NOE.SALDOS_ALUMNOS "+
				" WHERE MATRICULA = ?");
			ps.setString(1, codigoPersonal);
		
			rs= ps.executeQuery();	
			if(rs.next()){
				saldo = rs.getDouble("DEUDA");
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesContratoFinancieroUtil|getAlumSaldoVencido|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }			
		}		
		return saldo;
	}
	
	// Obtiene el saldo vencido del contrato financiero (VISTA)
	public static HashMap<String,String> mapSaldoVencido(Connection conn, String cursoCargaId) throws SQLException{
		
		HashMap<String,String> mapDeuda = new HashMap<String,String>();
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		
		try{
			ps = conn.prepareStatement("SELECT MATRICULA, COALESCE(SVENCIDO,0) AS DEUDA FROM NOE.SALDOS_ALUMNOS"+
				" WHERE MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT WHERE CURSO_CARGA_ID = ? AND TIPOCAL_ID != 'M')");
			ps.setString(1, cursoCargaId);
		
			rs= ps.executeQuery();	
			while(rs.next()){
				mapDeuda.put(rs.getString("MATRICULA"), rs.getString("DEUDA"));
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesContratoFinancieroUtil|mapSaldoVencido|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }			
		}		
		return mapDeuda;
	}
	
	// Obtiene el campo de diferencia en la vista SALDOS_ALUMNOS
	public static HashMap<String,String> mapSaldoDiferencia(Connection conn ) throws SQLException{
		
		HashMap<String,String> mapDeuda = new HashMap<String,String>();
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		
		try{
			ps = conn.prepareStatement("SELECT MATRICULA, COALESCE(DIFERENCIA,0) AS DEUDA FROM NOE.SALDOS_ALUMNOS"+
				" WHERE MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)");			
		
			rs= ps.executeQuery();	
			while(rs.next()){
				mapDeuda.put(rs.getString("MATRICULA"), rs.getString("DEUDA"));
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesContratoFinancieroUtil|mapSaldoDiferencia|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }			
		}		
		return mapDeuda;
	}
	
	// Obtiene la diferencia del contrato financiero considerando el margen de deuda permitida(VISTA)
	public static double getAlumDiferencia(Connection conn, String codigoPersonal) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs 			= null;		
		double saldo = 0;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(DIFERENCIA,0) AS DEUDA FROM NOE.SALDOS_ALUMNOS "+
				" WHERE MATRICULA = ?");
			ps.setString(1, codigoPersonal);
		
			rs= ps.executeQuery();	
			if(rs.next()){
				saldo = rs.getDouble("DEUDA");
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesContratoFinancieroUtil|getAlumDiferencia|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return saldo;
	}
	
	// Obtiene la deuda del contrato financiero (VISTA)
	public static double getAlumImporteContrato(Connection conn, String codigoPersonal) throws SQLException{		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;		
		double saldo = 0;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(IMPORTE_CONTRATOS,0) AS DEUDA FROM NOE.SALDOS_ALUMNOS "+
				" WHERE MATRICULA = ?");
			ps.setString(1, codigoPersonal);
		
			rs= ps.executeQuery();	
			if(rs.next()){
				saldo = rs.getDouble("DEUDA");
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesContratoFinancieroUtil|getAlumImporteContrato|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return saldo;
	}
	
	// Determina el saldo vencido del contrato financiero
	public static double getCancelaPortal(Connection conn, String codigoPersonal) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;		
		double saldo = 0;		
		
		try{
			ps = conn.prepareStatement("SELECT DIFERENCIA FROM NOE.SALDOS_ALUMNOS WHERE MATRICULA = ?");
			ps.setString(1, codigoPersonal);
		
			rs= ps.executeQuery();	
			if(rs.next()){
				saldo = rs.getDouble("DIFERENCIA");
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesContratoFinancieroUtil|getCancelaPortal|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }			
		}		
		
		return saldo;
	}

	public static HashMap<String, String> getMapTalleres(Connection conn) throws SQLException{
		HashMap<String, String> mapa= new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT CA.MATRICULA, (SELECT CC.NOMBRE FROM MATEO.CONT_CCOSTO CC WHERE ID_EJERCICIO='001-2012' AND CC.ID_CCOSTO = (SELECT CCOSTO_ID FROM NOE.AFE_PLAZA WHERE ID = CA.PLAZA_ID)) AS DEPTO, CA.NUMERO_HORAS" +
					" FROM NOE.AFE_CONTRATO_ALUMNO CA" +
					" WHERE CA.STATUS ='I'";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString(1), rs.getString(2)+" ("+rs.getString(3)+"hrs.)");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesContratoFinancieroUtil|getMapTalleres|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}
}