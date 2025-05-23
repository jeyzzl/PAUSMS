package aca.tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/* 
 *ANTES DE REALIZAR EL TRASPASO ES IMPORTANTE VERIFICAR 
 *QUE LAS ENTREVISTAS TENGAN DEFINIDO EL PERIODO ESCOLAR
 * */
public class TitAlumnoFolio {
	
	public static void main(String[] args){
		try{
			Connection conn = null;			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.25:1521:ora12", "enoc", "caminacondios");			
			PreparedStatement ps1 = null;
			PreparedStatement ps3 = null;
			ResultSet rs1 = null;			
						
			int folio = 0;
			String strFolio = "";
			
			ps3 = conn.prepareStatement("UPDATE TIT_ALUMNO2 SET FOLIO_NUEVO = ? WHERE FOLIO = ?");	
			
			ps1 = conn.prepareStatement("SELECT FOLIO,CODIGO_PERSONAL,PLAN_ID,FECHA,ESTADO,INSTITUCION,XML,CADENA, FOLIO_NUEVO"
					+ " FROM TIT_ALUMNO2 WHERE INSTITUCION = 'COVOPROM'"
					+ " ORDER BY FOLIO");
			rs1 = ps1.executeQuery();
			while (rs1.next()){				 
				folio++;
				strFolio = String.valueOf(folio);
				if (strFolio.length()==1) strFolio = "CVP2019-000"+strFolio;
				if (strFolio.length()==2) strFolio = "CVP2019-00"+strFolio;
				if (strFolio.length()==3) strFolio = "CVP2019-0"+strFolio;
				if (strFolio.length()==4) strFolio = "CVP2019"+strFolio;
				
				ps3.setString(1, strFolio);
				ps3.setString(2, rs1.getString("FOLIO"));
				
				System.out.println("UPDATE:"+rs1.getString("FOLIO")+":"+strFolio);
				
			/*	
				if (ps3.executeUpdate()==1){
					System.out.println("UPDATE:"+folio);
				}else{
					System.out.println("error:"+folio);
				}
			*/	
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