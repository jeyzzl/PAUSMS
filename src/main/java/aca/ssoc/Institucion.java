//Beans de la tabla ARCH_DOCUMENTOS

package aca.ssoc;
import java.sql.*;

public class Institucion {
	private String Institucion_Id;
	private String Institucion_Nombre;
	private String Sector_Id;
	private String Direccion;
	private String Telefono;
	
	public Institucion(){
		Institucion_Id		= "";
		Institucion_Nombre	= "";
		Sector_Id			= "";
		Direccion			= "";
		Telefono			= "";
		
	}
	
	
	public String getDireccion() {
		return Direccion;
	}







	public void setDireccion(String direccion) {
		Direccion = direccion;
	}







	public String getInstitucion_Id() {
		return Institucion_Id;
	}







	public void setInstitucion_Id(String institucion_Id) {
		Institucion_Id = institucion_Id;
	}







	public String getInstitucion_Nombre() {
		return Institucion_Nombre;
	}







	public void setInstitucion_Nombre(String institucion_Nombre) {
		Institucion_Nombre = institucion_Nombre;
	}


	public String getTelefono() {
		return Telefono;
	}


	public void setTelefono(String telefono) {
		Telefono = telefono;
	}


	public String getSector_Id() {
		return Sector_Id;
	}







	public void setSector_Id(String sector_Id) {
		Sector_Id = sector_Id;
	}





	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.SSOC_INSTITUCION (INSTITUCION_ID, " + 
					"INSTITUCION_NOMBRE, SECTOR_ID, DIRECCION, TELEFONO) VALUES(?,?,?,?,?)");
			ps.setString(1,Institucion_Id);
			ps.setString(2,Institucion_Nombre);
			ps.setString(3,Sector_Id);
			ps.setString(4,Direccion);
			ps.setString(5,Telefono);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.Institucion|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.SSOC_INSTITUCION " + 
					"SET INSTITUCION_NOMBRE = ?, SECTOR_ID = ?, DIRECCION = ?, TELEFONO = ? WHERE INSTITUCION_ID = ?");
			ps.setString(1,Institucion_Nombre);
			ps.setString(2,Institucion_Id);
			ps.setString(3,Sector_Id);
			ps.setString(4,Direccion);
			ps.setString(5,Telefono);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.institucion|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.SSOC_INSTITUCION WHERE INSTITUCION_ID = ?"); 
			ps.setString(1, Institucion_Id);
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.institucion|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{		
		Institucion_Id 	 		= rs.getString("Institucion_Id");		
		Institucion_Nombre		= rs.getString("Institucion_Nombre");		
		Sector_Id				= rs.getString("Sector_Id");		
		Direccion				= rs.getString("Direccion");		
		Telefono				= rs.getString("Telefono");		
	}
	
	public void mapeaRegId(Connection con, String Institucion_Id) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT INSTITUCION_ID, " +
					"INSTITUCION_NOMBRE, SECTOR_ID, DIRECCION, TELEFONO FROM ENOC.SSOC_INSTITUCION WHERE INSTITUCION_ID = ?"); 
			ps.setString(1, Institucion_Id);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.Institucion|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.SSOC_INSTITUCION WHERE INSTITUCION_ID = ? "); 
			ps.setString(1, Institucion_Id);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.institucion|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String Sector_Id) throws SQLException{
		String strMaximo 		= "01";
		int numMaximo			= 0;
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(SUBSTR(INSTITUCION_ID,3,2))+1,1) AS MAXIMO " +
					"FROM ENOC.SSOC_INSTITUCION WHERE SECTOR_ID = ?"); 
			ps.setString(1,Sector_Id);
			rs = ps.executeQuery();
			if (rs.next()){
				numMaximo = rs.getInt("MAXIMO");
				if (numMaximo<10) 
					strMaximo = Sector_Id+"0"+String.valueOf(numMaximo);
				else
					strMaximo = Sector_Id+String.valueOf(numMaximo);
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.institucion|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return strMaximo;
	}
		
	public static String getNombre( Connection conn, String Institucion_Id) throws SQLException, Exception {
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		String nombre			= "";
		
		try{
			ps = conn.prepareStatement("SELECT INSTITUCION_NOMBRE FROM ENOC.SSOC_INSTITUCION WHERE INSTITUCION_ID = ? "); 
			ps.setString(1, Institucion_Id);
			rs = ps.executeQuery();
			if (rs.next()){
				nombre = rs.getString("INSTITUCION_NOMBRE");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.institucion|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	} 
	
	public static String getDireccion( Connection conn, String Institucion_Id) throws SQLException, Exception {
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		String direccion			= "";
		
		try{
			ps = conn.prepareStatement("SELECT DIRECCION FROM ENOC.SSOC_INSTITUCION WHERE INSTITUCION_ID = ? "); 
			ps.setString(1, Institucion_Id);
			rs = ps.executeQuery();
			if (rs.next()){
				direccion = rs.getString("DIRECCION");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.institucion|getDireccion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return direccion;
	} 

	/*
	public static void main(String args[]){
		try{
			Connection Conn = null;
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.14:1521:ora1", "enoc", "caminacondios");
			
			Institucion institucion = new Institucion();
			
			
			institucion.setInstitucion_Id("02");
			institucion.setInstitucion_Nombre("PRUEBA2 DE SISTEMAS");
			institucion.setSector_Id("02");
			//documento.setImagen("PRUEBA");
		/*	
			 // MODIFICAR DOCUMENTO
			if(documento.existeReg(Conn)==true){
				System.out.println("El DOCUMENTO  " + documento.getIdDocumento() + "ya existe");
				documento.updateReg(Conn);				
				System.out.println("Documento: "+ documento.getIdDocumento()+":"+documento.getDescripcion()+":"+documento.getImagen());				
			}	
			
			
			
			 // GRABAR DOCUMENTO  
			if(institucion.existeReg(Conn)==false){
				if (institucion.insertReg(Conn)){
					System.out.println("Grabado..!");
				}else{
					System.out.println("Error..!");
				}
			}else{
				System.out.println("Ya existe..!");
			}
			
		/*	
			// BORRAR DOCUMENTO
			if(documento.existeReg(Conn)==true){
				if (documento.deleteReg(Conn)){
					System.out.println("Borrado..!");
				}else{
					System.out.println("Error..!");
				}
			}else{
				System.out.println("No existe..!");
			}
	
			institucion = null;
			
			Conn.commit();
			Conn.close();
			
		}catch(Exception e){
			System.out.println(e);
		}		
	}*/
}