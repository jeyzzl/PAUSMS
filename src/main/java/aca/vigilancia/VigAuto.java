package aca.vigilancia;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VigAuto{
	 
	private String autoId;
	private String placas;
	private String serie;
	private String engomado;
	private String alumno;
	private String empleado;
	private String otro;
	private String color; 
	private String modelo; 
	private String marca;
	private String poliza;  	
	
	public VigAuto(){
		autoId				= "";
		placas				= "";
		serie				= "";
		engomado			= ""; 		
		alumno				= "";
		empleado			= "";
		otro				= "";
		color				= "";
		modelo				= "";
		marca				= "";
		poliza				= ""; 		
	} 	

	public String getAutoId() {
		return autoId;
	}

	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}

	public String getPlacas() {
		return placas;
	}

	public void setPlacas(String placas) {
		this.placas = placas;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getEngomado() {
		return engomado;
	}

	public void setEngomado(String engomado) {
		this.engomado = engomado;
	}

	public String getAlumno() {
		return alumno;
	}

	public void setAlumno(String alumno) {
		this.alumno = alumno;
	}

	public String getEmpleado() {
		return empleado;
	}

	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getPoliza() {
		return poliza;
	}

	public void setPoliza(String poliza) {
		this.poliza = poliza;
	}

	public boolean insertReg(Connection conn ) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.VIG_AUTO"+ 
				"(AUTO_ID, PLACAS, SERIE," +
				" ENGOMADO, ALUMNO, EMPLEADO, " +
				" OTRO, COLOR, MODELO, MARCA, POLIZA )"+
				" VALUES( TO_NUMBER(?, '9999'), ?, ?," +
				" ?, ?, ?," +
				" ?, ?, ?, ?, ?)");			
			ps.setString(1, autoId);
			ps.setString(2, placas);
			ps.setString(3, serie);
			ps.setString(4, engomado);		
			ps.setString(5, alumno);
			ps.setString(6, empleado);
			ps.setString(7, otro);
			ps.setString(8, color);
			ps.setString(9, modelo);
			ps.setString(10, marca);
			ps.setString(11, poliza); 			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigAuto|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{ 		
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("UPDATE ENOC.VIG_AUTO"+ 
				" SET"+		
				" PLACAS = ?,"+
				" SERIE = ?,"+ 				
				" ENGOMADO = ?,"+
				" ALUMNO = ?,"+
				" EMPLEADO = ?,"+
				" OTRO = ?," +
				" COLOR = ?," +
				" MODELO = ?," +
				" MARCA = ?," +
				" POLIZA = ?" +
				" WHERE AUTO_ID = TO_NUMBER(?, '9999')");
				
			ps.setString(1, placas);
			ps.setString(2, serie); 			
			ps.setString(3, engomado);
			ps.setString(4, alumno);
			ps.setString(5, empleado);
			ps.setString(6, otro); 			
			ps.setString(7, color); 			
			ps.setString(8, modelo);			
			ps.setString(9, marca);			
			ps.setString(10, poliza);			
			ps.setString(11, autoId);
			 			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false; 			
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigAuto|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	 	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.VIG_AUTO"+ 
				" WHERE AUTO_ID = TO_NUMBER(?, '9999')  ");
			ps.setString(1, autoId);			
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigAuto|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
		autoId				= rs.getString("AUTO_ID");
		placas		 		= rs.getString("PLACAS");
		serie		 		= rs.getString("SERIE");
		engomado			= rs.getString("ENGOMADO");
		alumno			 	= rs.getString("ALUMNO");
		empleado			= rs.getString("EMPLEADO");
		otro				= rs.getString("OTRO");
		color 				= rs.getString("COLOR");	
		modelo 				= rs.getString("MODELO");
		marca 				= rs.getString("MARCA");
		poliza 				= rs.getString("POLIZA");	
	}
	
	public void mapeaRegId( Connection conn, String autoId) throws SQLException, IOException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
	 		ps = conn.prepareStatement("SELECT"+
	 			" AUTO_ID, PLACAS, SERIE, ENGOMADO, ALUMNO, " +
	 			" EMPLEADO, OTRO, COLOR, MODELO, MARCA, POLIZA" +
	 			" FROM ENOC.VIG_AUTO WHERE AUTO_ID = TO_NUMBER(?,'9999') "); 
	 		ps.setString(1, autoId);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigAuto|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		PreparedStatement ps	= null;
		boolean 		ok 		= false;
		ResultSet 		rs		= null;		
		
		try{
			ps = conn.prepareStatement("SELECT AUTO_ID FROM ENOC.VIG_AUTO"+ 
				" WHERE AUTO_ID = ?");
			ps.setString(1, autoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigAuto|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(AUTO_ID)+1 MAXIMO FROM ENOC.VIG_AUTO"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigAuto|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
}