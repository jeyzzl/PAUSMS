//Beans de la Res_Datos

package aca.residencia;

import java.sql.*;

public class ResDatos {
	private String matricula;
	private String periodoId;
	private String calle;
	private String colonia;
	private String mpio;
	private String telArea;
	private String telNum;
	private String nombreTut;
	private String apellidoTut;
	private String razon;
	private String usuario;
	private String fecha;
	private String numero;
	
	public ResDatos(){
		matricula 	= "";
		periodoId 	= "";
		calle 		= "";
		colonia 	= "";
		mpio 		= "";
		telArea 	= "";
		telNum 		= "";
		nombreTut 	= "";
		apellidoTut = "";
		razon 		= "";
		usuario 	= "";
		fecha 		= "";
		numero 		= "";
	}

	public String getApellidoTut() {
		return apellidoTut;
	}
	

	public void setApellidoTut(String apellidoTut) {
		this.apellidoTut = apellidoTut;
	}
	

	public String getCalle() {
		return calle;
	}
	

	public void setCalle(String calle) {
		this.calle = calle;
	}
	

	public String getColonia() {
		return colonia;
	}
	

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	

	public String getFecha() {
		return fecha;
	}
	

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	

	public String getMatricula() {
		return matricula;
	}
	

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	

	public String getMpio() {
		return mpio;
	}
	

	public void setMpio(String mpio) {
		this.mpio = mpio;
	}
	

	public String getNombreTut() {
		return nombreTut;
	}
	

	public void setNombreTut(String nombreTut) {
		this.nombreTut = nombreTut;
	}
	

	public String getNumero() {
		return numero;
	}
	

	public void setNumero(String numero) {
		this.numero = numero;
	}
	

	public String getPeriodoId() {
		return periodoId;
	}
	

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}
	

	public String getRazon() {
		return razon;
	}
	

	public void setRazon(String razon) {
		this.razon = razon;
	}
	

	public String getTelArea() {
		return telArea;
	}
	

	public void setTelArea(String telArea) {
		this.telArea = telArea;
	}
	

	public String getTelNum() {
		return telNum;
	}
	

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}
	

	public String getUsuario() {
		return usuario;
	}
	

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	

	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.RES_DATOS"+ 
				"(MATRICULA, PERIODO_ID, CALLE, COLONIA, MUNICIPIO, TEL_AREA, " +
				"TEL_NUMERO, TUT_NOMBRE, TUT_APELLIDOS, RAZON, USUARIO, " +
				"FECHA, NUMERO) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
				"TO_NUMBER(?,'999'), ?, TO_DATE(?,'DD/MM/YYYY'), ?)");
			
			ps.setString(1, matricula);
			ps.setString(2, periodoId);
			ps.setString(3, calle);
			ps.setString(4, colonia);
			ps.setString(5, mpio);
			ps.setString(6, telArea);
			ps.setString(7, telNum);
			ps.setString(8, nombreTut);
			ps.setString(9, apellidoTut);
			ps.setString(10,razon);
			ps.setString(11,usuario);
			ps.setString(12,fecha);
			ps.setString(13,numero);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResDatos|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.RES_DATOS" 
				+ " SET PERIODO_ID = ?,"
				+ " CALLE = ?,"
				+ " COLONIA = ?,"
				+ " MUNICIPIO = ?,"
				+ " TEL_AREA = ?,"
				+ " TEL_NUMERO = ?,"
				+ " TUT_NOMBRE = ?,"
				+ " TUT_APELLIDOS = ?,"
				+ " RAZON = TO_NUMBER(?,'999'),"
				+ " USUARIO = ?,"
				+ " FECHA = TO_DATE(?,'DD/MM/YYYY'),"
				+ " NUMERO = ?"+				
				"WHERE MATRICULA = ? ");
			ps.setString(1, periodoId);
			ps.setString(2, calle);
			ps.setString(3, colonia);
			ps.setString(4, mpio);
			ps.setString(5, telArea);
			ps.setString(6, telNum);
			ps.setString(7, nombreTut);
			ps.setString(8, apellidoTut);
			ps.setString(9, razon);			
			ps.setString(10,usuario);
			ps.setString(11,fecha);
			ps.setString(12,numero);
			ps.setString(13,matricula);
			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResDatos|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.RES_DATOS "+ 
				"WHERE MATRICULA = ? ");
			ps.setString(1, matricula);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResDatos|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		matricula 		= rs.getString("MATRICULA");		
		calle			= rs.getString("CALLE");
		colonia	 		= rs.getString("COLONIA");
		mpio 			= rs.getString("MUNICIPIO");
		telArea			= rs.getString("TEL_AREA");
		telNum 			= rs.getString("TEL_NUMERO");
		nombreTut 		= rs.getString("TUT_NOMBRE");
		apellidoTut		= rs.getString("TUT_APELLIDOS");
		razon 			= rs.getString("RAZON");
		usuario 		= rs.getString("USUARIO");
		fecha 			= rs.getString("FECHA");
		numero 			= rs.getString("NUMERO");
		periodoId 		= rs.getString("PERIODO_ID");
	}
	
	public void mapeaRegId( Connection conn, String matricula ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT MATRICULA, " +
					"CALLE, COLONIA, MUNICIPIO, TEL_AREA, TEL_NUMERO, TUT_NOMBRE, " +
					"TUT_APELLIDOS, RAZON, USUARIO, TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, " +
					"NUMERO, PERIODO_ID FROM ENOC.RES_DATOS WHERE MATRICULA = ? "); 
			ps.setString(1, matricula);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResDatos|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.RES_DATOS "+ 
				"WHERE MATRICULA = ?");
			ps.setString(1, matricula);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResDatos|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getRazon(Connection conn, String matricula) throws SQLException{	
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String razon 			= "0";
		try{
			ps = conn.prepareStatement("SELECT RAZON FROM ENOC.RES_DATOS WHERE MATRICULA = ?");
			ps.setString(1, matricula);
			
			rs = ps.executeQuery();
			if (rs.next())
				razon = rs.getString("RAZON");		
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResDatos|getRazon|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return razon;
	}
	
	public static String getDireccion(Connection conn, String matricula) throws SQLException{	
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String razon 			= "0";
		try{
			ps = conn.prepareStatement("SELECT 'Calle:'||COALESCE(CALLE,'-')||' #'||COALESCE(NUMERO,'0')||' Col. '||COALESCE(COLONIA,'-') AS DIRECCION"
					+ " FROM ENOC.RES_DATOS WHERE MATRICULA = ?");
			ps.setString(1, matricula);
			
			rs = ps.executeQuery();
			if (rs.next())
				razon = rs.getString("DIRECCION");		
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResDatos|getDireccion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return razon;
	}
}