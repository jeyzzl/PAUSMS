package aca.tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FolioNotas {

	public static void main(String[] args) {
		
		try{		
			String codigoPersonal	= "";
			String folio			= "";
			String tempCodigo		= "x";
			int numero=0;
			
			Connection Conn = null;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.14:1521:ora1", "enoc", "caminacondios");
			PreparedStatement ps =null;
			PreparedStatement ps2 =null;
			ResultSet rs = null;
			ps2 = Conn.prepareStatement("UPDATE NOTA_UNAV SET NUM = ? WHERE FOLIO= ?");
			
			ps = Conn.prepareStatement("SELECT CODIGO, FOLIO FROM NOTA_UNAV WHERE COALESCE(CODIGO,'X')!='X' ORDER BY CODIGO,PLAN,SUBSTR(FECHA,7,4),SUBSTR(FECHA,4,2),SUBSTR(FECHA,1,2)");
			rs = ps.executeQuery();			
			while (rs.next()){				
				codigoPersonal 	= rs.getString("CODIGO");
				folio			= rs.getString("FOLIO");
				if (!tempCodigo.equals(codigoPersonal)&&!tempCodigo.equals("x")){
					numero=1;
					Conn.commit();
				}else{
					numero++;
				}
				tempCodigo = codigoPersonal;				
				
				ps2.setInt(1,numero);
				ps2.setString(2,folio);
				ps2.executeUpdate();				
				//System.out.println(numero+":"+codigoPersonal+":"+folio);
				//System.out.println("UPDATE NOTA_UNAV SET NUM = "+numero+"' WHERE FOLIO='"+folio+"';");				
														
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