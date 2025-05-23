package aca.tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AlumUnav {
	
	public static void main(String[] args) {
		System.out.println("Inicio..!!");
		try{		
			String codigo		= "";
			String nombre 		= "";
			String tempNombre	= "X";
			String tempCodigo	= "X";
			String alumSame		= "";
			
			String nuevaClave 	= "x";
			
			int rowTotal=0, rowInsert=0;
			//System.out.println("Conect");
			Connection Conn = null;						
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.14:1521:ora1", "enoc", "caminacondios");
			PreparedStatement ps =null;
			PreparedStatement ps2 =null;
			ResultSet rs = null;
			
			ps = Conn.prepareStatement("SELECT "+
					"MATRICULA, NOMBRE||' '||APELLIDOPA||' '||APELLIDOMA AS ORDEN "+
					"FROM ALUM_UNAV WHERE COALESCE(NOMBRE,'X')!= 'X' ORDER BY 2");
			rs = ps.executeQuery();			
			while (rs.next()){
				rowTotal++;
				
				codigo 		= rs.getString("MATRICULA");
				nombre 		= rs.getString("ORDEN");
								
				if ( tempCodigo.equals("X") || tempNombre.equals(nombre)){
					alumSame = alumSame+'-'+codigo;
					tempNombre = nombre;
					tempCodigo = codigo;
				}else{ 
					rowInsert++;
					if (rowInsert<10) nuevaClave = "106000"+ String.valueOf(rowInsert);
					if (rowInsert>9 && rowInsert<100) nuevaClave = "10600"+ String.valueOf(rowInsert);
					if (rowInsert>99 && rowInsert<1000) nuevaClave = "1060"+ String.valueOf(rowInsert);
					if (rowInsert>999 && rowInsert<10000) nuevaClave = "106"+ String.valueOf(rowInsert);
					
					System.out.println("INSERT INTO ALUM_OLDCLAVE(CODIGO_PERSONAL, OLDMATRICULA) VALUES('"+nuevaClave+"','"+alumSame+"');");
					
					alumSame = '-'+codigo;
					tempNombre = nombre;
					tempCodigo = codigo;								
				}
				//System.out.println("num:"+rowTotal+":"+codigo+":"+nombre+":"+alumSame);
						
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
		
		System.out.println("Fin..!!");
	}
}