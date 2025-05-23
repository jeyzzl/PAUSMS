package aca.tools;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.StringTokenizer;

public class NotasUnav {
	
	public static void main(String[] args) {		
		
		try{		
		/*	
			Connection Conn = null;						
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.10:1521:ora1", "enoc", "caminacondios");
		*/	
			String linea 		= "";
			String matricula	= "";			
			String documento	= "";
			String imagen		= "";
			String hoja			= "";
			String fuente		= "";
			String fecha_i		= "";
			String fecha_u		= "";
			String tipo			= "";
			String origen		= "";
			
			int rowTotal=0;
			
			BufferedReader leerFile = new BufferedReader(new FileReader("C://prueba.txt"));
			//&& rowTotal<1000
			
			Connection conn=null;
			DriverManager.registerDriver (new org.postgresql.Driver());
        	conn=DriverManager.getConnection("jdbc:postgresql://172.16.251.11/archivo","postgres","jete17");
			PreparedStatement ps =null;
			
			while (leerFile.ready()){
				rowTotal++;
				linea = leerFile.readLine();
				
				StringTokenizer st = new StringTokenizer(linea," ");
				/*
				if (st.hasMoreTokens()) matricula 	= st.nextToken();
				if (st.hasMoreTokens()) documento	= st.nextToken();
				if (st.hasMoreTokens()) imagen		= st.nextToken();
				if (st.hasMoreTokens()) hoja		= st.nextToken();
				if (st.hasMoreTokens()) fuente		= st.nextToken();
				if (st.hasMoreTokens()) fecha_i		= st.nextToken();
				if (st.hasMoreTokens()) fecha_u		= st.nextToken();
				if (st.hasMoreTokens()) tipo		= st.nextToken();
				if (st.hasMoreTokens()) origen		= st.nextToken();
				*/
				
				matricula 	= st.nextToken();
				documento	= st.nextToken();
				imagen		= st.nextToken();
				hoja		= st.nextToken();
				fuente		= st.nextToken();
				fecha_i		= st.nextToken();
				fecha_u		= st.nextToken();
				tipo		= st.nextToken();
				origen		= st.nextToken();
				
				if (origen==null || origen.equals(" ")||origen.equals("\\N")) origen = "N";
				imagen = "null";				
					
				System.out.println("INSERT INTO ENOC.ARCH_DOCALUM(MATRICULA,IDDOCUMENTO,IMAGEN,HOJA,FUENTE,F_INSERT,F_UPDATE,TIPO,ORIGEN) VALUES('"+matricula+"',"+documento+", "+imagen+","+hoja+",'"+fuente+"','"+fecha_i+"','"+fecha_u+"','"+tipo+"','"+origen+"');"); 
				
				ps= conn.prepareStatement("INSERT INTO ENOC.ARCH_DOCALUM(MATRICULA,IDDOCUMENTO,IMAGEN,HOJA,FUENTE,F_INSERT,F_UPDATE,TIPO,ORIGEN) VALUES('"+matricula+"',"+documento+", "+imagen+","+hoja+",'"+fuente+"','"+fecha_i+"','"+fecha_u+"','"+tipo+"','"+origen+"')"); 
				ps.executeUpdate();
			}	
			
/*			
			Conn.commit();
			Conn.close();
*/			
			System.out.println("Fin -> "+rowTotal);
			conn.close();
			
		}
		catch(Exception e){
			System.out.println(e);
		}

	}

}