//Beans de la tabla ARCH_DOCUMENTOS

package aca.ssoc;
import java.sql.*;

public class Sector {
	private String Sector_Id;
	private String Sector_Nombre;
	
	public Sector(){
		Sector_Id		= "";
		Sector_Nombre	= "";
		
	}
	
	
	public String getSector_Id() {
		return Sector_Id;
	}



	public void setSector_Id(String sector_Id) {
		Sector_Id = sector_Id;
	}



	public String getSector_Nombre() {
		return Sector_Nombre;
	}



	public void setSector_Nombre(String sector_Nombre) {
		Sector_Nombre = sector_Nombre;
	}



	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.SSOC_SECTOR (SECTOR_ID, " + 
					"SECTOR_NOMBRE) VALUES(?,?)");
			ps.setString(1,Sector_Id);
			ps.setString(2,Sector_Nombre);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.Sector|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.SSOC_SECTOR " + 
					"SET SECTOR_NOMBRE = ? WHERE SECTOR_ID = ?");
			ps.setString(1,Sector_Nombre);
			ps.setString(2,Sector_Id);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.sector|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.SSOC_SECTOR WHERE SECTOR_ID = ?"); 
			ps.setString(1, Sector_Id);
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.sector|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		Sector_Id 	 		= rs.getString("Sector_Id");
		Sector_Nombre		= rs.getString("Sector_Nombre");
	}
	
	public void mapeaRegId(Connection con, String Sector_Id) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT SECTOR_ID, " +
					"SECTOR_NOMBRE FROM ENOC.SSOC_SECTOR WHERE SECTOR_ID = ?"); 
			ps.setString(1, Sector_Id);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.sector|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.SSOC_SECTOR WHERE SECTOR_ID = ? "); 
			ps.setString(1, Sector_Id);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.sector|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String strMaximo 		= "01";
		int numMaximo			= 0;
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(SECTOR_ID)+1 MAXIMO FROM ENOC.SSOC_SECTOR"); 
			rs = ps.executeQuery();
			if (rs.next()){
				numMaximo = rs.getInt("MAXIMO");
				if (numMaximo<10) 
					strMaximo = "0"+String.valueOf(numMaximo);
				else
					strMaximo = String.valueOf(numMaximo);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.sector|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return strMaximo;
	}
		
	public static String getNombre( Connection conn, String Sector_Id) throws SQLException, Exception {
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		String nombre			= "";
		
		try{
			ps = conn.prepareStatement("SELECT SECTOR_NOMBRE FROM ENOC.SSOC_SECTOR WHERE SECTOR_ID = ? "); 
			ps.setString(1, Sector_Id);
			rs = ps.executeQuery();
			if (rs.next()){
				nombre = rs.getString("SECTOR_NOMBRE");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.sector|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	} 

 /*
	public static void main(String args[]){
		try{
			Connection Conn = null;
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.14:1521:ora1", "enoc", "caminacondios");
			
			Sector sector = new Sector();
			
			
			sector.setSector_Id("04");
			sector.setSector_Nombre("Federales");
			//documento.setImagen("PRUEBA");
	
			 // MODIFICAR DOCUMENTO
			if(sector.existeReg(Conn)==true){
				System.out.println("El SECTOR  " + sector.getSector_Id() + " ya existe");
				sector.updateReg(Conn);				
				System.out.println("Sector: "+ sector.getSector_Id()+"-"+sector.getSector_Nombre());				
			}	
	
			
	
			 // GRABAR DOCUMENTO  
			if(sector.existeReg(Conn)==false){
				if (sector.insertReg(Conn)){
					System.out.println("Grabado..!");
				}else{
					System.out.println("Error..!");
				}
			}else{
				System.out.println("Ya existe..!");
			}
			
		/*
			// BORRAR DOCUMENTO
			if(sector.existeReg(Conn)==true){
				if (sector.deleteReg(Conn)){
					System.out.println("Borrado..!");
				}else{
					System.out.println("Error..!");
				}
			}else{
				System.out.println("No existe..!");
			}
	
			sector = null;
			
			Conn.commit();
			Conn.close();
			
		}catch(Exception e){
			System.out.println(e);
		}		
	}*/
}