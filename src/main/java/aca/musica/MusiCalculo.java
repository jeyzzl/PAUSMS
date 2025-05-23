package aca.musica;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author josetorres
 *
 */
public class MusiCalculo {

 	private String codigoId;
 	private String periodoId;
	private String fecha;
 	private String formaPago;
 	private String pagare1;
 	private String pagare2;
 	private String pagare3;
 	private String institucionId;
 	private String empleado;
 	private	String sobresueldo;
 	private String estado;
 	private String pagoInicial;
 	private String matricula;
 	private String renta;
 	private String ensenanza; 
	private String porcentaje;
	private String numPagare;
	private String hayPagoIni;
 	
 	public MusiCalculo(){
 		codigoId			= "";
 		periodoId			= "";
 		fecha				= "";
 		formaPago			= "";
 		pagare1				= "";
 		pagare2				= "";
 		pagare3				= "";
 		institucionId		= "";
 		empleado			= "";
 		sobresueldo			= "";
 		estado 				= "";
 		matricula 			= "";
 		renta				= "";
 		ensenanza			= "";
 		porcentaje			= "";
 		numPagare			= "";
 		hayPagoIni			= "";
 	}
 	
	/**
	 * @return the codigoId
	 */
	public String getCodigoId() {
		return codigoId;
	}

	/**
	 * @param codigoId the codigoId to set
	 */
	public void setCodigoId(String codigoId) {
		this.codigoId = codigoId;
	}

	/**
	 * @return the periodoId
	 */
	public String getPeriodoId() {
		return periodoId;
	}

	/**
	 * @param periodoId the periodoId to set
	 */
	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the formaPago
	 */
	public String getFormaPago() {
		return formaPago;
	}


	/**
	 * @param formaPago the formaPago to set
	 */
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}


	/**
	 * @return the pagare1
	 */
	public String getPagare1() {
		return pagare1;
	}


	/**
	 * @param pagare1 the pagare1 to set
	 */
	public void setPagare1(String pagare1) {
		this.pagare1 = pagare1;
	}


	/**
	 * @return the pagare2
	 */
	public String getPagare2() {
		return pagare2;
	}


	/**
	 * @param pagare2 the pagare2 to set
	 */
	public void setPagare2(String pagare2) {
		this.pagare2 = pagare2;
	}


	/**
	 * @return the pagare3
	 */
	public String getPagare3() {
		return pagare3;
	}


	/**
	 * @param pagare3 the pagare3 to set
	 */
	public void setPagare3(String pagare3) {
		this.pagare3 = pagare3;
	}


	/**
	 * @return the institucionId
	 */
	public String getInstitucionId() {
		return institucionId;
	}


	/**
	 * @param institucionId the institucionId to set
	 */
	public void setInstitucionId(String institucionId) {
		this.institucionId = institucionId;
	}


	/**
	 * @return the empleado
	 */
	public String getEmpleado() {
		return empleado;
	}


	/**
	 * @param empleado the empleado to set
	 */
	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}

	
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}		

	/**
	 * @return the sobresueldo
	 */
	public String getSobresueldo() {
		return sobresueldo;
	}

	/**
	 * @param sobresueldo the sobresueldo to set
	 */
	public void setSobresueldo(String sobresueldo) {
		this.sobresueldo = sobresueldo;
	}
	
	/**
	 * @return the pagoInicial
	 */
	public String getPagoInicial() {
		return pagoInicial;
	}

	/**
	 * @param pagoInicial the pagoInicial to set
	 */
	public void setPagoInicial(String pagoInicial) {
		this.pagoInicial = pagoInicial;
	}	

	/**
	 * @return the matricula
	 */
	public String getMatricula() {
		return matricula;
	}

	/**
	 * @param matricula the matricula to set
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}	

	/**
	 * @return the ensenanza
	 */
	public String getEnsenanza() {
		return ensenanza;
	}

	/**
	 * @param ensenanza the ensenanza to set
	 */
	public void setEnsenanza(String ensenanza) {
		this.ensenanza = ensenanza;
	}
	
	/**
	 * @return the porcentaje
	 */
	public String getPorcentaje() {
		return porcentaje;
	}

	/**
	 * @param porcentaje the porcentaje to set
	 */
	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}
	
	/**
	 * @return the renta
	 */
	public String getRenta() {
		return renta;
	}

	/**
	 * @param renta the renta to set
	 */
	public void setRenta(String renta) {
		this.renta = renta;
	}	

	/**
	 * @return the numPagare
	 */
	public String getNumPagare() {
		return numPagare;
	}

	/**
	 * @param numPagare the numPagare to set
	 */
	public void setNumPagare(String numPagare) {
		this.numPagare = numPagare;
	}

	/**
	 * @return the hayPagoIni
	 */
	public String getHayPagoIni() {
		return hayPagoIni;
	}

	/**
	 * @param hayPagoIni the hayPagoIni to set
	 */
	public void setHayPagoIni(String hayPagoIni) {
		this.hayPagoIni = hayPagoIni;
	}
	

	public boolean insertReg(Connection conn ) throws Exception{
		PreparedStatement ps = null;
		boolean ok = false; 		
 		try{
 			ps = conn.prepareStatement("INSERT INTO ENOC.MUSI_CALCULO"+ 
 				" (CODIGO_ID, PERIODO_ID, FECHA, FORMA_PAGO, PAGARE1, PAGARE2, PAGARE3, " +
 				" INSTITUCION_ID, EMPLEADO, SOBRESUELDO, ESTADO, PAGO_INICIAL, MATRICULA, RENTA, ENSENANZA, PORCENTAJE, NUMPAGARE, HAYPAGOINI) "+
 				" VALUES( ?, ?, TO_DATE(?,'DD/MM/YYYY'), ?,TO_NUMBER(?,'99999.99'), "+
 				" TO_NUMBER(?,'99999.99'), TO_NUMBER(?,'99999.99') ,TO_NUMBER(?,'99'),?, ?, ?, " +
 				" TO_NUMBER(?,'99999.99'), TO_NUMBER(?,'99999.99'), TO_NUMBER(?,'99999.99')," +
 				" TO_NUMBER(?,'99999.99'), TO_NUMBER(?,'999'), TO_NUMBER(?,'9'), ? )");
 			ps.setString(1, codigoId);
 			ps.setString(2, periodoId);
 			ps.setString(3, fecha);
 			ps.setString(4, formaPago);
 			ps.setString(5, pagare1);
 			ps.setString(6, pagare2);
 			ps.setString(7, pagare3);
 			ps.setString(8, institucionId);
 			ps.setString(9, empleado);
 			ps.setString(10, sobresueldo);
 			ps.setString(11, estado);
 			ps.setString(12, pagoInicial);
 			ps.setString(13, matricula);
 			ps.setString(14, renta);
 			ps.setString(15, ensenanza);
 			ps.setString(16, porcentaje);
 			ps.setString(17, numPagare);
 			ps.setString(18, hayPagoIni);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.MusiCalculo|insertReg|:"+ex);	
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(Connection conn ) throws Exception{ 		
 		PreparedStatement ps = null; 		
 		boolean ok = false;
 		//System.out.println("Valores:"+pagare1+":"+pagare2+":"+pagare3+":"+pagoInicial+":"+matricula+":"+ensenanza);
 		try{ 			 			
 			ps = conn.prepareStatement("UPDATE ENOC.MUSI_CALCULO"+		 
 				" SET FECHA = TO_DATE(?,'DD/MM/YYYY'),"+
 				" FORMA_PAGO = ?,"+
 				" PAGARE1 = TO_NUMBER(?,'99999.99'),"+
 				" PAGARE2 = TO_NUMBER(?,'99999.99'),"+
 				" PAGARE3 = TO_NUMBER(?,'99999.99'),"+
 				" INSTITUCION_ID = TO_NUMBER(?,'99'),"+
 				" EMPLEADO = ?," +
 				" SOBRESUELDO = ?," +
 				" ESTADO = ?," +
 				" PAGO_INICIAL = TO_NUMBER(?,'99999.99'),"+
 				" MATRICULA = TO_NUMBER(?,'99999.99'),"+
 				" RENTA = TO_NUMBER(?,'99999.99'),"+
 				" ENSENANZA = TO_NUMBER(?,'99999.99'),"+
 				" PORCENTAJE = TO_NUMBER(?,'999')," +
 				" NUMPAGARE = TO_NUMBER(?,'9')," +
 				" HAYPAGOINI = ?"+
 				" WHERE CODIGO_ID = ?" +
 				" AND PERIODO_ID = ?");
 			ps.setString(1, fecha);
 			ps.setString(2, formaPago);
 			ps.setString(3, pagare1);
 			ps.setString(4, pagare2);
 			ps.setString(5, pagare3);
 			ps.setString(6, institucionId);
 			ps.setString(7, empleado);
 			ps.setString(8, sobresueldo);
 			ps.setString(9, estado);
 			ps.setString(10, pagoInicial);
 			ps.setString(11, matricula);
 			ps.setString(12, renta);
 			ps.setString(13, ensenanza);
 			ps.setString(14, porcentaje);
 			ps.setString(15, numPagare);
 			ps.setString(16, hayPagoIni);
 			ps.setString(17, codigoId); 			
 			ps.setString(18, periodoId);
 			
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;			
			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiCalculo|updateReg|:"+ex);		
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	} 
 	
 	public boolean updateReg(Connection conn, String codigoId, String periodoId, String sobresueldo ) throws Exception{ 		
 		PreparedStatement ps = null; 		
 		boolean ok = false;
 		
 		try{ 			 			
 			ps = conn.prepareStatement("UPDATE ENOC.MUSI_CALCULO"+		 
 				" SET SOBRESUELDO ='"+sobresueldo+"'"+
 				" WHERE CODIGO_ID = '"+codigoId+"'" +
 				" AND PERIODO_ID = '"+periodoId+"'");
 			ps.setString(1, sobresueldo);
 			ps.setString(2, codigoId);
 			ps.setString(3, periodoId);					
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;			
			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiCalculo|updateReg|:"+ex);		
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	public boolean deleteReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("DELETE FROM ENOC.MUSI_CALCULO "+ 
 				"WHERE CODIGO_ID = ? " +
 				"AND PERIODO_ID = ? ");
 			ps.setString(1, codigoId);
 			ps.setString(2, periodoId);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiCalculo|deleteReg|:"+ex);	
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}
 	
 	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		codigoId 			= rs.getString("CODIGO_ID");
 		periodoId 			= rs.getString("PERIODO_ID");
 		fecha 				= rs.getString("FECHA");
 		formaPago			= rs.getString("FORMA_PAGO");
 		pagare1 			= rs.getString("PAGARE1");
 		pagare2				= rs.getString("PAGARE2");
 		pagare3 			= rs.getString("PAGARE3");
 		institucionId 		= rs.getString("INSTITUCION_ID");
 		empleado 			= rs.getString("EMPLEADO");
 		sobresueldo			= rs.getString("SOBRESUELDO");
 		estado 				= rs.getString("ESTADO");
 		pagoInicial 		= rs.getString("PAGO_INICIAL");
 		matricula 			= rs.getString("MATRICULA");
 		renta	 			= rs.getString("RENTA");
 		ensenanza 			= rs.getString("ENSENANZA");
 		porcentaje 			= rs.getString("PORCENTAJE");
 		numPagare			= rs.getString("NUMPAGARE");
 		hayPagoIni			= rs.getString("HAYPAGOINI");
 		
 	}
  	
 	public void mapeaRegId( Connection conn, String codigoId, String periodoId ) throws SQLException, IOException{
 		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement("SELECT "+
	 			" CODIGO_ID, PERIODO_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, FORMA_PAGO,"+
	 			" PAGARE1, PAGARE2, PAGARE3, INSTITUCION_ID, EMPLEADO, SOBRESUELDO, ESTADO," +
	 			" PAGO_INICIAL, MATRICULA, RENTA, ENSENANZA, PORCENTAJE, NUMPAGARE, HAYPAGOINI"+
	 			" FROM ENOC.MUSI_CALCULO WHERE CODIGO_ID = ? " + 
	 			" AND PERIODO_ID = ?");
	 		ps.setString(1, codigoId);
	 		ps.setString(2, periodoId);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiCalculo|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		} 		
 	}

 	public boolean existeReg(Connection conn) throws SQLException{
 		boolean 		ok 	= false;
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT * FROM ENOC.MUSI_CALCULO "+ 
 				"WHERE CODIGO_ID = ?" +
 				"AND PERIODO_ID = ?");
 			ps.setString(1, codigoId);
 			ps.setString(2, periodoId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiCalculo|existeReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	} 	
 	
 	public static boolean existeReg(Connection conn, String codigoId, String periodoId) throws SQLException{
 		boolean 		ok 	= false;
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT * FROM ENOC.MUSI_CALCULO "+ 
 				"WHERE CODIGO_ID = ?" +
 				"AND PERIODO_ID = ?");
 			ps.setString(1, codigoId);
 			ps.setString(2, periodoId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiCalculo|existeReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	
 	public static String getEstado(Connection conn, String codigoId, String periodoId) throws SQLException{
 		PreparedStatement ps	= null;
 		ResultSet 		rs		= null;
 		String estado 			= "X"; 
 		
 		try{
 			ps = conn.prepareStatement("SELECT ESTADO FROM ENOC.MUSI_CALCULO "+ 
 				"WHERE CODIGO_ID = ?" +
 				"AND PERIODO_ID = ?");
 			ps.setString(1, codigoId);
 			ps.setString(2, periodoId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				estado = rs.getString("ESTADO"); 			
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiCalculo|getEstado|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return estado;
 	}
 	
 	public static double getPagare1(Connection conn, String codigoId, String periodoId) throws SQLException{
 		PreparedStatement ps	= null;
 		ResultSet 		rs		= null;
 		double importe 			= 0; 
 		
 		try{
 			ps = conn.prepareStatement("SELECT COALESCE(PAGARE1,0) AS PAGARE FROM ENOC.MUSI_CALCULO "+ 
 				"WHERE CODIGO_ID = ?" +
 				"AND PERIODO_ID = ?");
 			ps.setString(1, codigoId);
 			ps.setString(2, periodoId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				importe = rs.getDouble("PAGARE");
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiCalculo|getPagare|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return importe;
 	}
 	
 	public static double getPagare2(Connection conn, String codigoId, String periodoId) throws SQLException{
 		PreparedStatement ps	= null;
 		ResultSet 		rs		= null;
 		double importe 			= 0; 
 		
 		try{
 			ps = conn.prepareStatement("SELECT COALESCE(PAGARE2,0) AS PAGARE FROM ENOC.MUSI_CALCULO "+ 
 				"WHERE CODIGO_ID = ?" +
 				"AND PERIODO_ID = ?");
 			ps.setString(1, codigoId);
 			ps.setString(2, periodoId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				importe = rs.getDouble("PAGARE");
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiCalculo|getPagare|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return importe;
 	}
 	
 	public static double getPagare3(Connection conn, String codigoId, String periodoId) throws SQLException{
 		PreparedStatement ps	= null;
 		ResultSet 		rs		= null;
 		double importe 			= 0; 
 		
 		try{
 			ps = conn.prepareStatement("SELECT COALESCE(PAGARE3,0) AS PAGARE FROM ENOC.MUSI_CALCULO "+ 
 				"WHERE CODIGO_ID = ?" +
 				"AND PERIODO_ID = ?");
 			ps.setString(1, codigoId);
 			ps.setString(2, periodoId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				importe = rs.getDouble("PAGARE");
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiCalculo|getPagare|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return importe;
 	}
	
	public static boolean updateSobresueldo(Connection conn, String codigoId, String periodoId, String sobresueldo) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MUSI_CALCULO SET SOBRESUELDO = ?  WHERE CODIGO_ID = ? AND PERIODO_ID = ?"); 
			
			ps.setString(1, sobresueldo);			
			ps.setString(2, codigoId);
			ps.setString(3, periodoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiCalculo|updateSobresueldo|:"+ex);	
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	

	
}