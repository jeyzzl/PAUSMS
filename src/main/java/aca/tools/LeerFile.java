package aca.tools;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.StringTokenizer;

import aca.plan.CursoUtil;
import aca.plan.MapaCurso;
import aca.plan.MapaCursoPre;
import aca.plan.PrerrequisitoUtil;

public class LeerFile {
	
	public static void main(String[] args) {		
		
		try{		
			
			Connection Conn = null;						
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.10:1521:ora1", "enoc", "caminacondios");
			
			String linea 	= "";
			String plan 	= "";
			String curso	= "";
			String prerre	= "";
			CursoUtil cursoU = new CursoUtil();
			
			int rowTotal=0, rowInsert=0, rowError = 0;
			// C:\Trabajo\workspace\springacademico\src\main\resources\i18n
			BufferedReader leerFile = new BufferedReader(new FileReader("C://Prerre.txt"));
			while (leerFile.ready()){
				rowTotal++;
				linea = leerFile.readLine();
				StringTokenizer st = new StringTokenizer(linea,",");
				plan 	= st.nextToken();
				curso	= st.nextToken();
				prerre	= st.nextToken();
				if (plan.length()==5) plan = plan+"***";
				if (plan.length()==6) plan = plan+"**";
				if (plan.length()==7) plan = plan+"*";
				
				// Grabar los datos
				MapaCurso mp = new MapaCurso();
				PrerrequisitoUtil preu = new PrerrequisitoUtil();
				mp.setCursoId(plan+curso);
				if (cursoU.existeReg(Conn, plan+curso)){
					mp.setCursoId(plan+prerre);
					if (cursoU.existeReg(Conn, plan+prerre)){
						MapaCursoPre mcp = new MapaCursoPre();
						mcp.setCursoId(plan+curso);
						mcp.setCursoIdPre(plan+prerre);
						if (preu.insertReg(Conn, mcp)){
							rowInsert++;
						}else{
							rowError++;
						}
					}					
				}				
				//System.out.println("Datos: "+plan+curso+" - "+plan+prerre);
			    
			}	
			leerFile.close();
			System.out.println("Operaciones: "+ rowTotal+" "+rowInsert+" "+rowError);
			Conn.commit();
			Conn.close();
			//System.out.println("Fin");
			
		}
		catch(Exception e){
			System.out.println(e);
		}

	}

}