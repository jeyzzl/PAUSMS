package aca.tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import aca.conva.ConvHistorial;
import aca.conva.ConvHistorialUtil;

public class Script {
	
	public static void main(String[] args){
		try{
			Connection conn = null;	
			Connection conn2 = null;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.10:1521:ora1", "enoc", "caminacondios");
			conn2 = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.10:1521:ora1", "enoc", "caminacondios");			
			PreparedStatement ps3 = null;
			int folio = 0;
			int convalidacion = 0;
			int convalidacionAnt = 0;
			
			ConvHistorial ch = null;
			ConvHistorialUtil chu = new ConvHistorialUtil();
			ArrayList<ConvHistorial> lisHist = chu.getListAll(conn, "ORDER BY CONVALIDACION_ID");
			
			
			ps3 = conn2.prepareStatement("INSERT INTO CONV_HISTORICO2(CONVALIDACION_ID, FOLIO, FECHA, USUARIO, ESTADO)" +
			" VALUES(?,?,TO_DATE(?,'DD/MM/YYYY'),?,?)");
			
			
			for(int i = 0; i < lisHist.size(); i++){
				ch = (ConvHistorial) lisHist.get(i);
				
				convalidacion = Integer.parseInt(ch.getConvalidacionId());
				System.out.println("convalidacion_id = "+convalidacion);
				if(convalidacion == convalidacionAnt)
					folio++;
				else
					folio = 1;
				
				System.out.println("INSERT INTO CONV_HISTORICO2(CONVALIDACION_ID, FOLIO, FECHA, USUARIO, ESTADO)" +
						" VALUES("+convalidacion+","+folio+",TO_DATE('"+ch.getFecha()+"','DD/MM/YYYY'),'"+ch.getUsuario()+"','"+ch.getEstado()+"')");
				ps3.setInt(1, convalidacion);
				ps3.setInt(2, folio);
				ps3.setString(3, ch.getFecha());
				ps3.setString(4, ch.getUsuario());
				ps3.setString(5, ch.getEstado());
				System.out.println("Va a realizar el update");
				ps3.executeUpdate();
				System.out.println("Realizo el update");
				convalidacionAnt = convalidacion;
			}
			System.out.println("Salio del while");
			ps3.close();						
			conn.close();
			conn2.close();
		}catch(Exception e){
			System.out.println("Error: aca.tools "+e);
		}
	}
	
	/*public static void main(String[] args) {		
		
		try{		
		/*	
			Connection Conn = null;						
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.10:1521:ora1", "enoc", "caminacondios");
		*	
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
			
			int rowTotal=0, rowInsert=0, rowError = 0;
			
			BufferedReader leerFile = new BufferedReader(new FileReader("C://prueba.txt"));
			//&& rowTotal<1000
			
			Connection conn=null;
			DriverManager.registerDriver (new org.postgresql.Driver());
        	conn=DriverManager.getConnection(aca.conecta.Conectar.coneccion(),aca.conecta.Conectar.usuario(),aca.conecta.Conectar.password());
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
				*
				
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
*			
			System.out.println("Fin -> "+rowTotal);
			conn.close();
			
		}
		catch(Exception e){
			System.out.println(e);
		}

	}*/

}