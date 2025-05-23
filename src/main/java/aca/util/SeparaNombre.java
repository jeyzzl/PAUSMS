// Utileria para generar datos de alumnos
package aca.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;


// Creaci{on de claves de usuarios
public class SeparaNombre{
	
	public static String[] separarApellidos(String apellido){
		StringTokenizer tk = new StringTokenizer(apellido," ");
		String aNombre[] = new String[tk.countTokens()];	
		String apellidoP="",apellidoM="";
		String dev[] = new String[2];
		dev[0]="";dev[1]="";
		int i=0;
		boolean salir = false;		
		while (tk.hasMoreTokens()) aNombre[i++] = tk.nextToken().toUpperCase(); //Llenamos el array de nombres y apellidos
		if (i>1){
			//Buscamos apellido materno
			i-=2;		
			if ((aNombre[i].equals("LA")||aNombre[i].equals("LAS")||aNombre[i].equals("LOS")) && aNombre[i-1].equals("DE")){
				apellidoM = aNombre[i-1]+" "+aNombre[i]+" "+aNombre[i+1];
				i-=2; //Posicionamos i para buscar apellido paterno
			}else if (aNombre[i].equals("DEL")){
				apellidoM = aNombre[i]+" "+aNombre[i+1];
				i--; //Posicionamos i para buscar apellido paterno
			}else{
				apellidoM = aNombre[i+1];
			}
			//Creamos el array a devolver	
			int j;
			for (j=0;j<=i;j++) dev[0]+=aNombre[j]+" ";
			dev[1] = apellidoM;
		}else{
			dev[0] = aNombre[0];
			dev[1] = " ";
		}
		return dev;
	}
	
	public static void main(String[] args){

		Connection conn = null;
		try{			
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://172.16.7.77:5432/elias","postgres","jete17");
			/*
			String[] apellidos = {" "," "};
			apellidos = separarApellidos("BAUTISTA DE LA CRUZ ");
			System.out.println(apellidos[0]+"|"+apellidos[1]);
			*/
	//--------------
				ArrayList<String> lisApellidos 	= new ArrayList<String>();
				Statement st 	= conn.createStatement();
				ResultSet rs 	= null;
				String comando	= "";
				
				try{
					comando = "SELECT APATERNO, CODIGO_ID FROM ALUM_TEMP";	 
					
					rs = st.executeQuery(comando);			
					while (rs.next()){
						lisApellidos.add(rs.getString("CODIGO_ID")+"-"+rs.getString("APATERNO"));
					}
					
				}catch(Exception ex){
					System.out.println("Error - aca.catalogo.BecAlumnoLista|getListAll|:"+ex);
				}finally{
					try { rs.close(); } catch (Exception ignore) { }
					try { st.close(); } catch (Exception ignore) { }
				}		
				
				for(Object o: lisApellidos){
					if(o!=null){
						String app = (String)o;
						
						app.replaceAll("/", " ");
						String [] tmp = app.split("-");
						String codigoId = tmp[0];
						String apellido = tmp[1];
						
						String [] nom = separarApellidos(apellido);
							System.out.println(codigoId);
							System.out.println(nom[0]+"*"+nom[1]);
							
						 		Statement sta = conn.createStatement(); 		
						 		String comndo = "";
						 		boolean ok = false;
						 		
						 		try{
						 			comndo = "UPDATE ALUM_TEMP SET APATERNO = '"+nom[0]+"', AMATERNO = '"+nom[1]+"'  WHERE CODIGO_ID = '"+codigoId+"'"; 
									if (sta.executeUpdate(comndo)==1){
										ok=true;
									}
									
						 		}catch(Exception ex){
						 			System.out.println("Error - "+ex);		
						 		}finally{
						 			if (sta!=null) sta.close();
						 		}						 		
					}
				}
	//--------------

			
		}catch(Exception ex){
			System.out.println("Error:aca.util.Mail||"+ex);
		}finally{
			try{
				if (conn!=null)conn.close();
			}catch(Exception ex){
				System.out.println("NO SE PUEDE CERRAR LA CONEXION");
			}
		}
		
	}
		
}