package aca.tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/* 
 *ANTES DE REALIZAR EL TRASPASO ES IMPORTANTE VERIFICAR 
 *QUE LAS ENTREVISTAS TENGAN DEFINIDO EL PERIODO ESCOLAR
 * */
public class MenContacto {
	
	public static void main(String[] args){
		try{
			Connection conn = null;			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.20:1521:ora1", "enoc", "caminacondios");			
			PreparedStatement ps1 = null;
			PreparedStatement ps3 = null;
			ResultSet rs1 = null;
			
			String mentor = "x";			
			int folio = 0, cont=0;		
			
			ps3 = conn.prepareStatement("UPDATE MENTOR_CONTACTO SET FOLIO=? " +
					"WHERE ID_CONTACTO = ? AND ID_MENTOR=? AND MATRICULA=?");			
			
			ps1 = conn.prepareStatement("SELECT ID_MENTOR, ID_CONTACTO, MATRICULA FROM MENTOR_CONTACTO " +
					"ORDER BY PERIODO_ID, ID_MENTOR, FECHA_CONTACTO");
			rs1 = ps1.executeQuery();
			while (rs1.next()){	cont++;			
				if (!mentor.equals(rs1.getString("ID_MENTOR"))){
					folio	= 0;
					mentor	= rs1.getString("ID_MENTOR");
				} 
				folio++;
				
				ps3.setInt(1, folio);
				ps3.setString(2, rs1.getString("ID_CONTACTO"));
				ps3.setString(3, rs1.getString("ID_MENTOR") );
				ps3.setString(4, rs1.getString("MATRICULA") );
				
				if (ps3.executeUpdate()==1){
					System.out.println("insert:"+folio);
				}else{
					System.out.println("error:"+folio);
				}
				
				//System.out.println("Datos:"+ folio+": "+rs1.getString("ID_MENTOR")+": "+rs1.getString("ID_CONTACTO")+": "+rs1.getString("MATRICULA"));
			}			
			System.out.println("Salio del while");
			ps3.close();
			ps1.close();			
			conn.close();			
		}catch(Exception e){
			System.out.println("Error:aca.tools.| "+e);
		}
	}
	
}