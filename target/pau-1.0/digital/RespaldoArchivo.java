package digital;

import java.sql.*;
import java.util.*;
import java.applet.*;
import java.io.*;
import java.net.*;

public class RespaldoArchivo extends Applet{
	
	private static final long serialVersionUID = 1L;	
	ByteArrayOutputStream output;
	URL baseURL;
	String s_dir,s_fi,s_ff,base,s_tarea; 	
	RespaldoArchivo p;	
	
	// Inicializa el applet
	public void init(){
		s_tarea 	= getParameter("tarea");
		s_dir 		= getParameter("dir");		
		s_fi 		= getParameter("fi");
		s_ff 		= getParameter("ff");
		baseURL 	= getCodeBase();
		base 		= baseURL.toString(); 
		p			= new RespaldoArchivo();				  	
	}
	
	// Comienza el respaldo o la restauración
	public void start(){    
		String paso = "5";
		int cont	= 0;	
		
		// Respaldo de imagenes
		if (s_tarea.equals("1")){  
			try{
				cont = p.Respaldar(s_dir,s_fi,s_ff);
			}catch(Exception e){
				e.printStackTrace();
			}
			if (cont>0) paso = "4";			 		
			try{
				getAppletContext().showDocument(new URL(base+"respaldo.jsp?paso="+paso+"&cont="+cont), "_self");
			}catch(Exception e){
				e.printStackTrace();
			}			
		}else{
		   // Restaura las imagenes
			
		   cont = p.Restaurar(s_dir);
		   if (cont>0) paso = "4";			 		
		   try{
			   getAppletContext().showDocument(new URL(base+"respaldo.jsp?paso=R"+paso+"&cont="+cont), "_self");
		   }catch(Exception e){
			   e.printStackTrace();
		   }		 
		} // termina else de if (s_tarea.equals("1"))
	}	
		
	public int Respaldar(String direc,String fi,String ff) throws Exception{ 
		Connection  conn2 				= null;
		Statement   stmt2 				= null;
		ResultSet   rset2 				= null;		
		FileOutputStream fileOutStream	= null;
		
		org.postgresql.largeobject.LargeObject obj = null;
		org.postgresql.largeobject.LargeObjectManager lom = null;
		//boolean dev 					= false;
		String COMANDO = "",NombreArch	= "";
		int cont = 0;
		long oid = 0;
		byte buf[];
		System.out.println("Directorio: "+direc);
		System.out.println("F.inicio:   "+fi);
		System.out.println("F.final:   "+ff);		
		try{
			System.out.println("Creando conexion...");
			
			Class.forName("org.postgresql.Driver");
			conn2 = DriverManager.getConnection("jdbc:postgresql://172.16.251.11:5432/archivo","postgres","jete17");
			stmt2=conn2.createStatement();
		   
		   System.out.println("Conectado!...");
		}catch (Exception ex){
			System.out.println("No es posible conectarse... Error en la conexion");
			ex.printStackTrace(); 
		};
		
		try{
			conn2.setAutoCommit(false);			 
			lom = ((org.postgresql.PGConnection)conn2).getLargeObjectAPI();		
		}catch (Exception ex){ex.printStackTrace();};
		
		try{
			 System.out.println("contando documentos..??");
			 COMANDO = "select count(*) as total from arch_docalum where f_update >= to_date('"+fi+"', 'DD/MM/YYYY') and f_update <= to_date('"+ff+"', 'DD/MM/YYYY')"; 
			 rset2 = stmt2.executeQuery(COMANDO);
			 if (rset2.next()){
			 	System.out.println("entro in cont(*)");
			 	System.out.println(rset2.getInt(1));
			 }
			 System.out.println("Obteniendo rset de documentos...");
			 COMANDO = "select * from arch_docalum where f_update >= to_date('"+fi+"', 'DD/MM/YYYY') and f_update <= to_date('"+ff+"', 'DD/MM/YYYY')";			 
			 rset2 = stmt2.executeQuery(COMANDO);
		   
		}catch (Exception ex){ ex.printStackTrace();};
		
		if (direc.charAt(direc.length()-1)!='\\') direc += "\\";
		try{ 
			System.out.println("Respaldando...");
			while (rset2.next()){				
				NombreArch 		= direc+rset2.getString("matricula")+"-"+rset2.getString("iddocumento")+"-"+rset2.getString("fuente")+"-"+String.valueOf(rset2.getInt("hoja"))+".jpg";
				System.out.println("Nombre de Archivo:"+NombreArch);
			    fileOutStream 	= new FileOutputStream(NombreArch);
				oid 			= rset2.getLong("imagen");				
    			obj 			= lom.open(oid, org.postgresql.largeobject.LargeObjectManager.READ);    			
				buf 			= new byte[obj.size()];
				buf 			= obj.read(obj.size());  				
  				fileOutStream.write(buf,0,obj.size()); 
 				fileOutStream.flush();        
				fileOutStream.close(); 
				cont++;
				obj.close();
			 }
			 System.out.println("OK... "+String.valueOf(cont) + " archivos respaldados...");	
			 return cont;
		}catch (Exception ex){ex.printStackTrace();}
		
		finally{
 			 conn2.close();	
		};
		
		conn.setAutoCommit(true);
		
		return 0;
	}
	
	public int Restaurar(String direc){
		Connection  conn2 = null;
    	try{
    		Class.forName("org.postgresql.Driver");
    		conn2 = DriverManager.getConnection("jdbc:postgresql://172.16.251.11:5432/archivo","postgres","jete17");
    	}catch (Exception ex){ex.printStackTrace();};		
				
		if (direc.charAt(direc.length()-1)!='\\') direc += "\\";	      
		File fichero=new File(direc,".");
		int cont=0;
		StringTokenizer token;
		String NombreArch,s_matricula,s_iddoc,s_nhoja,s_fuente;
		File[] listaArchivos=fichero.listFiles(new Filtro(".jpg"));
        for(int i=0; i<listaArchivos.length; i++){   				
			NombreArch = listaArchivos[i].getName();
			token = new StringTokenizer(NombreArch,"-");
			if (token.countTokens() == 4){
				s_matricula = token.nextToken();
				s_iddoc = token.nextToken();
				s_fuente = token.nextToken();
				s_nhoja = token.nextToken();									
				s_nhoja = s_nhoja.substring(0,s_nhoja.length()-4);	
				if (upload(conn2,listaArchivos[i],s_matricula,s_iddoc,s_fuente,s_nhoja)){  
					cont++;	
					System.out.println(NombreArch + " -> OK! <"+s_matricula+"><"+s_iddoc+"><"+s_fuente+"><"+s_nhoja+">");
				}else 
					System.out.println(NombreArch + " -> NO SE PUDO");  
			}else 
				System.out.println(NombreArch + " -> No es un documento valido.");
        }
        
        try{
        	conn2.commit();		
			conn2.close();
		}catch (Exception ex){ex.printStackTrace();};		
        System.out.println("OK... "+String.valueOf(cont) + " archivos restaurados...");
		return cont;
	}
	
	public class Filtro implements FilenameFilter{
		String extension;
		
		Filtro(String extension){
			this.extension=extension;
		}
		public boolean accept(File dir, String name){
			return name.endsWith(extension);
		}
	}
	
	public boolean upload(Connection conn2, File file, String matricula, String iddoc, String fuente,String numhoja){
		Statement stmt2 		= null;
		ResultSet rset2 		= null;
		PreparedStatement pstmt	= null;		
 		try{
 			stmt2=conn2.createStatement();				
		}catch (Exception ex){ex.printStackTrace();};		
		
		boolean dev 		= false;
		String COMANDO 		= "";
		if (file.exists()){	
			long oid=0;			
			org.postgresql.largeobject.LargeObjectManager lom = null;
			org.postgresql.largeobject.LargeObject obj = null;
			FileInputStream fis=null;
			
			try{
				conn2.setAutoCommit(false);
				lom = ((org.postgresql.PGConnection)conn2).getLargeObjectAPI();			
				oid = lom.createLO(org.postgresql.largeobject.LargeObjectManager.READ | org.postgresql.largeobject.LargeObjectManager.WRITE);
				obj = lom.open(oid, org.postgresql.largeobject.LargeObjectManager.WRITE);
			}catch (Exception ex){ex.printStackTrace();};
			
			try{			
				COMANDO = "select hoja from arch_docalum  where iddocumento = '"+iddoc+"' and matricula = '"+matricula+"' and hoja = "+numhoja;			 
				rset2 = stmt2.executeQuery(COMANDO);			
			}catch (Exception ex){ex.printStackTrace();};
			
			try{						
				/*  
				if (rset2.next()) COMANDO = "update ENOC.arch_docalum set imagen = ?,fuente = '"+fuente+"', tipo = 'U',f_update = now() where iddocumento = '"+iddoc+"' and matricula = '"+matricula+"' and hoja = '"+numhoja+"'"; 
				 */
				if (rset2.next()) 
					COMANDO = "update arch_docalum set imagen = ? where iddocumento = '"+iddoc+"' and matricula = '"+matricula+"' and hoja = '"+numhoja+"'"; 
				else
					COMANDO = "insert into arch_docalum(matricula,iddocumento,imagen,hoja,fuente,tipo,f_insert,f_update) Values ('"+matricula+"','"+iddoc+"',?,"+numhoja+",'"+fuente+"','I',now(),now())";						 
				pstmt = conn2.prepareStatement(COMANDO);
			}catch (Exception ex){ex.printStackTrace();};
			
			try{
				fis = new FileInputStream(file);
				byte buf[] = new byte[(int)file.length()];
				int le; while ((le=fis.read(buf)) !=-1){obj.write(buf,0,le);}
				pstmt.setLong(1,oid);			
			}catch (Exception ex){ex.printStackTrace();};
			
			try{
				pstmt.execute();
				fis.close();			 
			}catch (Exception ex){ex.printStackTrace();};
			
			dev = true;
		}// fin de if (file.exists())
		
		return dev;
	}
	
}