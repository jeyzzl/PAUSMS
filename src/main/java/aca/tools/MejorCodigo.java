package aca.tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.StringTokenizer;

public class MejorCodigo {
	
	public static void main(String[] args) {
		
		try{		
			String codigoPersonal	= "";
			String codigos			= "";
			String mejorCodigo		= "";
			String tempCodigo		= "";
						
			Connection Conn = null;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.14:1521:ora1", "enoc", "caminacondios");
			PreparedStatement ps =null;
			PreparedStatement ps2 =null;
			ResultSet rs = null;
			
			ps = Conn.prepareStatement("SELECT CODIGO_PERSONAL,OLDMATRICULA FROM ALUM_OLDCLAVE ORDER BY 1");
			rs = ps.executeQuery();			
			while (rs.next()){
			
				codigoPersonal 	= rs.getString("CODIGO_PERSONAL");
				codigos 		= rs.getString("OLDMATRICULA");
				
				mejorCodigo = "X";
				StringTokenizer st = new StringTokenizer(codigos,"-");
				while (st.hasMoreTokens()){
					tempCodigo = st.nextToken();
					if (mejorCodigo.equals("X")){
						mejorCodigo= tempCodigo;
					}else if (Integer.parseInt(mejorCodigo.substring(0,1))<Integer.parseInt(tempCodigo.substring(0,1))){
						mejorCodigo = tempCodigo;						
					}else if (Integer.parseInt(mejorCodigo.substring(0,1))==Integer.parseInt(tempCodigo.substring(0,1))){
						if (Integer.parseInt(mejorCodigo.substring(3,6))<Integer.parseInt(tempCodigo.substring(3,6))){
							mejorCodigo=tempCodigo;
						}
					}
					
				}
				System.out.println("UPDATE ALUM_OLDCLAVE SET MEJORCODIGO = '"+mejorCodigo+"' WHERE CODIGO_PERSONAL='"+codigoPersonal+"';");
				
														
			}												
			
			Conn.commit();
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
			if (ps2!=null) ps2.close();
			if (Conn!=null) Conn.close();
						
		}
		catch(Exception e){
			System.out.println(e);
		}

	}

}