//Beans de la tabla ARCH_DOCUMENTOS

package aca.ssoc;
import java.sql.*;

public class Plaza {
	private String Plaza_Id;
	private String Plaza_Nombre;
	private String Institucion_Id;
	
	public Plaza(){
		Plaza_Id		= "";
		Plaza_Nombre	= "";
		Institucion_Id	= "";
		
	}
	
	
	public String getInstitucion_Id() {
		return Institucion_Id;
	}




	public void setInstitucion_Id(String institucion_Id) {
		Institucion_Id = institucion_Id;
	}




	public String getPlaza_Id() {
		return Plaza_Id;
	}




	public void setPlaza_Id(String plaza_Id) {
		Plaza_Id = plaza_Id;
	}




	public String getPlaza_Nombre() {
		return Plaza_Nombre;
	}




	public void setPlaza_Nombre(String plaza_Nombre) {
		Plaza_Nombre = plaza_Nombre;
	}



	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.SSOC_PLAZA (PLAZA_ID, " + 
					"PLAZA_NOMBRE, INSTITUCION_ID) VALUES(?,?,?)");
			ps.setString(1, Plaza_Id);
			ps.setString(2, Plaza_Nombre);
			ps.setString(3, Institucion_Id);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.Plaza|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.SSOC_PLAZA " + 
					"SET PLAZA_NOMBRE = ?, INSTITUCION_ID = ? WHERE PLAZA_ID = ?");
			ps.setString(1,Plaza_Nombre);
			ps.setString(2,Institucion_Id);
			ps.setString(3, Plaza_Id);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.plaza|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.SSOC_PLAZA WHERE PLAZA_ID = ?"); 
			ps.setString(1, Plaza_Id);
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.plaza|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		Plaza_Id 	 		= rs.getString("Plaza_Id");
		Plaza_Nombre		= rs.getString("Plaza_Nombre");
		Institucion_Id		= rs.getString("Institucion_Id");
	}
	
	public void mapeaRegId(Connection con, String Plaza_Id) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT PLAZA_ID, " +
					"PLAZA_NOMBRE, INSTITUCION_ID" +				
					"FROM ENOC.SSOC_PLAZA WHERE PLAZA_ID = ?"); 
			ps.setString(1, Plaza_Id);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.plaza|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.SSOC_PLAZA WHERE PLAZA_ID = ? "); 
			ps.setString(1, Plaza_Id);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.plaza|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String Institucion_Id) throws SQLException{
		String strMaximo 		= "01";
		int numMaximo			= 0;
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(SUBSTR(PLAZA_ID,3,2))+1,1) AS MAXIMO " +
					"FROM ENOC.SSOC_PLAZA WHERE INSTITUCION_ID = ?"); 
			ps.setString(1,Institucion_Id);
			rs = ps.executeQuery();
			if (rs.next()){
				numMaximo = rs.getInt("MAXIMO");
				if (numMaximo<10) 
					strMaximo = Institucion_Id+"0"+String.valueOf(numMaximo);
				else
					strMaximo = Institucion_Id+String.valueOf(numMaximo);
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.institucion|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return strMaximo;
	}
		
	public static String getNombre( Connection conn, String Plaza_Id) throws SQLException, Exception {
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		String nombre			= "";
		
		try{
			ps = conn.prepareStatement("SELECT PLAZA_NOMBRE FROM ENOC.SSOC_PLAZA WHERE PLAZA_ID = ? "); 
			ps.setString(1, Plaza_Id);
			rs = ps.executeQuery();
			if (rs.next()){
				nombre = rs.getString("PLAZA_NOMBRE");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.plaza|getNombre|:"+ex);
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
			
			Plaza pl = new Plaza();
			
			
			pl.setPlaza_Id("010203");
			pl.setPlaza_Nombre("PRUEBA2 2 DE SISTEMAS");
			pl.setInstitucion_Id("0102");
			
			 // MODIFICAR DOCUMENTO
			if(pl.existeReg(Conn)==true){
				System.out.println("El DOCUMENTO  " + pl.getPlaza_Id() + "ya existe");
				pl.updateReg(Conn);				
				System.out.println("Documento: "+ pl.getPlaza_Id()+":"+pl.getPlaza_Nombre()+":"+pl.getInstitucion_Id());				
			}	
			/*
			
			 // GRABAR DOCUMENTO  
			if(pl.existeReg(Conn)==false){
				if (pl.insertReg(Conn)){
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
	
			pl = null;
			
			Conn.commit();
			Conn.close();
			
		}catch(Exception e){
			System.out.println(e);
		}		
	}*/
}