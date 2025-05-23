package aca.tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CompararCol {
	
	public void CompararColumnas() throws SQLException{
		
		java.util.ArrayList<String> listUM 		= new java.util.ArrayList<String>();
		java.util.ArrayList<String> listUNAV 	= new java.util.ArrayList<String>();
		
		Connection conUM		= null;
		Connection conUNAV		= null;
		Statement stmtUM		= null;
		Statement stmtUNAV		= null;
		ResultSet rsUM			= null;
		ResultSet rsUNAV		= null;
		int row = 0;
		
		String comando			= "";		
		try{
			
			//Coneccion a Rigel UM
			DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver ());
			conUM 		= DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.20:1521:ora1","enoc","caminacondios");
			stmtUM 		= conUM.createStatement();			
			
			comando 	= "SELECT TABLE_NAME||','||COLUMN_NAME||','||DATA_TYPE||','||DATA_LENGTH AS DATOS FROM SYS.ALL_TAB_COLUMNS" +
		 				" WHERE OWNER = 'ENOC' " +
		 				" AND TABLE_NAME IN (SELECT DISTINCT(TABLE_NAME) FROM SYS.ALL_ALL_TABLES WHERE OWNER = 'ENOC')";					
			rsUM = stmtUM.executeQuery(comando);
			while (rsUM.next()){
				listUM.add( rsUM.getString("DATOS") );		
			}			
			
			//Coneccion a Rigel UNAV
			DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver ());
			conUNAV 	= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","enoc","caminacondios");
			stmtUNAV 	= conUM.createStatement();			
			
			comando = "SELECT TABLE_NAME||','||COLUMN_NAME||','||DATA_TYPE||','||DATA_LENGTH AS DATOS FROM SYS.ALL_TAB_COLUMNS" +
		 			" WHERE OWNER = 'ENOC'"+
		 			" AND TABLE_NAME IN (SELECT DISTINCT(TABLE_NAME) FROM SYS.ALL_ALL_TABLES WHERE OWNER = 'ENOC')";
			rsUNAV = stmtUNAV.executeQuery(comando);
			while (rsUNAV.next()){
				listUNAV.add(rsUNAV.getString("DATOS"));
				row++;
			}
			
			// Comparar diferencias
			String colUM = ""; String colUNAV	= "";	
			
			for (int j=0; j<listUM.size(); j++){				
				colUM = listUM.get(j);
				System.out.print(j+": ");				
				boolean encontro = false; row=0;
				
				while ( row<listUNAV.size() && encontro ==false ){
					colUNAV = listUNAV.get(row);
					if (colUNAV.equals(colUM)) 
						encontro = true;
					row++;
				}
				if (encontro)
					System.out.println("Ok");
				else
					System.out.println("A g r e g a r");
			}	
			
			// Comparar diferencias
			colUM = ""; colUNAV	= "";
			
			for (int j=0; j<listUNAV.size(); j++){				
				colUNAV = listUNAV.get(j);
				System.out.print(j+": ");				
				boolean encontro = false; row = 0;
				
				while ( row<listUM.size() && encontro ==false ){
					colUM = listUM.get(row);
					if (colUM.equals(colUNAV)) 
						encontro = true;
					row++;
				}
				if (encontro)
					System.out.println("Ok");
				else
					System.out.println("A g r e g a r");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.tools|CompararCol|:"+ex);
		}finally{
			if (rsUM!=null) rsUM.close();
			if (stmtUM!=null) stmtUM.close();
			if (rsUNAV!=null) rsUNAV.close();
			if (stmtUNAV!=null) stmtUNAV.close();
			conUM.close();
			conUNAV.close();
		}
		
	}
	
	public static void main (String [] agg){
		try{
			CompararCol compara = new CompararCol();
			compara.CompararColumnas();
			System.out.println("Finish:");
		}catch(Exception e){
			System.out.println(e);
		}				
	}
}
