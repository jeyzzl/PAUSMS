//Beans de la tabla ARCH_DOCSTATUS
package aca.archivo;

import java.sql.*;

public class ArchDocStatus {
    private String IdDocumento;
	private String IdStatus;
		
	public String getIdDocumento() {
		return IdDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		IdDocumento = idDocumento;
	}

	public String getIdStatus() {
		return IdStatus;
	}

	public void setIdStatus(String idStatus) {
		IdStatus = idStatus;
	}

	public ArchDocStatus(){
		IdDocumento 	= "";
		IdStatus		= "";
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		IdDocumento 	 		= rs.getString("IdDocumento");
		IdStatus				= rs.getString("IdStatus");
	}
	
	
public void mapeaRegId(Connection con, String idDocumento, String idStatus) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT IDDOCUMENTO, IDSTATUS FROM ENOC.ARCH_DOCSTATUS WHERE IDDOCUMENTO = TO_NUMBER(?,'999') AND IDSTATUS = TO_NUMBER(?,'99')"); 
			ps.setString(1, idDocumento);
			ps.setString(2, idStatus);
			rs = ps.executeQuery();		
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocStatusUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
	/*	
	//PRUEBA DE METODOS
	public static void main(String args[]){
		try{
			Connection Conn = null;
				
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.14:1521:ora1", "enoc", "caminacondios");
				
			ArchDocStatus documento = new ArchDocStatus();	
				
			documento.setIdDocumento("100");
			documento.setIdStatus("23");
						
			//	 MODIFICAR DOCUMENTO
			if(documento.existeReg(Conn)==true){
				System.out.println("El DOCUMENTO  " + documento.getIdDocumento()+"ya existe");
				documento.updateReg(Conn);				
				System.out.println("Documento: "+ documento.getIdDocumento()+":"+documento.getIdStatus());				
			}
			
			//	 GRABAR DOCUMENTO  
			if(documento.existeReg(Conn)==false){
				if (documento.insertReg(Conn)){
					System.out.println("Grabado..!");
				}else{
					System.out.println("Error..!");
				}
				}else{
					System.out.println("Ya existe..!");
				}
					
			
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
			
						
				documento = null;
				
				Conn.commit();
				Conn.close();
				
		}catch(Exception ex){
			System.out.println("Error: "+ex);
		}
	}*/
}