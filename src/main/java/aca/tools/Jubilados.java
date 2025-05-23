package aca.tools;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.StringTokenizer;

public class Jubilados {
	
	public static void main(String[] args) {
		try{	
			System.out.println("conexion");
			Connection Conn = null;						
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.10:1521:ora1", "enoc", "caminacondios");
			
			PreparedStatement ps  = null;
			PreparedStatement ps2 = Conn.prepareStatement("SELECT MAX(ID)+1 AS MAXIMO FROM ARON.EMPLEADO");
			ResultSet rs = null;
			System.out.println("variables");
			String id			= "";
			String version		= "0";
			String clave 		= "";
			String nombre		= "";			
			String paterno		= "";
			String materno		= "";
			String fecha		= "";
			String direccion	= "";
			String genero		= "";
			String status		= "j";
			
			String linea		= "";
			
			int rowTotal=0;
			System.out.println("archivo");
			BufferedReader leerFile = new BufferedReader(new FileReader("C://jubilados.txt"));
			System.out.println("while");
			while (leerFile.ready()){
				rowTotal++;
				System.out.println("maximo");
				rs = ps2.executeQuery();
				if (rs.next()) id = rs.getString("MAXIMO");
				System.out.println("archivo:"+linea);
				linea = leerFile.readLine();
				System.out.println("tokenizer:"+linea);
				StringTokenizer st = new StringTokenizer(linea,",");
				System.out.println("token");
				
				if (st.hasMoreTokens()) clave 		= st.nextToken();
				if (st.hasMoreTokens()) nombre		= st.nextToken();
				if (st.hasMoreTokens()) paterno		= st.nextToken();
				if (st.hasMoreTokens()) materno		= st.nextToken();
				if (st.hasMoreTokens()) fecha		= st.nextToken();
				if (st.hasMoreTokens()) direccion	= st.nextToken();
				if (st.hasMoreTokens()) genero		= st.nextToken();			
				
				System.out.println("INSERT INTO ARON.EMPLEADO"+
						"(ID,VERSION,CLAVE,NOMBRE,APPATERNO,APMATERNO,FECHANACIMIENTO,DIRECCION,GENERO, STATUS) "+
						"VALUES(TO_NUMBER('"+id+"'),TO_NUMBER('"+version+"'),'"+clave+"','"+nombre+"','"+paterno+"','"+materno+"',"+
						"TO_DATE('"+fecha+"','DD/MM/YYYY'),'"+direccion+"','"+genero+"','"+status+"')");
				
				ps= Conn.prepareStatement("INSERT INTO ARON.EMPLEADO"+
						"(ID,VERSION,CLAVE,NOMBRE,APPATERNO,APMATERNO,FECHANACIMIENTO,DIRECCION,GENERO, STATUS) "+
						"VALUES(TO_NUMBER('"+id+"'),TO_NUMBER('"+version+"'),'"+clave+"','"+nombre+"','"+paterno+"','"+materno+"',"+
						"TO_DATE('"+fecha+"','DD/MM/YYYY'),'"+direccion+"','"+genero+"','"+status+"')");
				ps.executeUpdate();
			}	
					
			Conn.commit();
			Conn.close();
			System.out.println("Fin -> "+rowTotal);			
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}