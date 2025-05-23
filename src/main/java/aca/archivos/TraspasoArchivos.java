package aca.archivos;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Created on 3/10/2005
 *
*/

/**
 * @author Pedro
 *
 */
public class TraspasoArchivos {
	
	public void deAlumnos(){
        Connection conn=null;
        Connection connO=null;
        	
        
        try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	 	try {
			connO = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.14:1521:ora1", "enoc", "caminacondios");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	 	System.out.println("conectado a oracle");
        PreparedStatement ps =null;
        PreparedStatement psO =null;
	    ResultSet rsO=null;
	    try {
	        psO = connO.prepareStatement("select * from ENOC.archivos_alumno"); 
	        try {
	        	String id,matricula,nombre,comentario,status;
	        	int folio;
	        	long tamano;
	        	Date fecha;
	        	//ok
	        	DriverManager.registerDriver (new org.postgresql.Driver());
	        	conn=DriverManager.getConnection(aca.conecta.Conectar.coneccion(),aca.conecta.Conectar.usuario(),aca.conecta.Conectar.password());	        	
	        	conn.setAutoCommit(false);
	        	rsO=psO.executeQuery();
	        	while(rsO.next()){
	        		id=rsO.getString(1);
	        		folio=rsO.getInt(2);
	        		matricula=rsO.getString(3);
	        		fecha=rsO.getDate(4);
	        		nombre=rsO.getString(5);
	        		comentario=rsO.getString(6);
	        		tamano=rsO.getLong(7);
	        		status=rsO.getString(8);
	        		
	    		    ps = conn.prepareStatement("INSERT INTO PORTAL.ARCHIVOS_ALUMNO(ARCHIVO_ID, " + 
	    					"FOLIO,CODIGO_PERSONAL,FECHA,NOMBRE,COMENTARIO,TAMANO,STATUS,ARCHIVO) " +
	    					"VALUES(?,?,?,?,?,?,?,?,?)");
	    		    ps.setString(1,id);
	    			ps.setInt(2,folio);
	    			ps.setString(3,matricula);
	    			ps.setDate(4,fecha);
	    			ps.setString(5,nombre);
	    			ps.setString(6,comentario);
				    ps.setLong(7,tamano);
					ps.setString(8,status);
					
					System.out.print("Archivo "+nombre+" de "+matricula+"... ");
					
//					String ruta = "E:\\academico\\academico\\portales\\archivos";
					String ruta	= "/var/tomcat4/webapps/academico/portales/archivos/";					
					
					String nombrereal=id+"M"+matricula+"F"+folio+"N "+nombre;
				    File fi = new File(ruta+"/"+nombrereal);
				    if(fi.exists()){
				    	
				    	FileInputStream fis = new FileInputStream(fi);
						org.postgresql.largeobject.LargeObjectManager lom = ((org.postgresql.PGConnection)conn).getLargeObjectAPI();
						long oid = lom.createLO(org.postgresql.largeobject.LargeObjectManager.READ | org.postgresql.largeobject.LargeObjectManager.WRITE);
						org.postgresql.largeobject.LargeObject obj = lom.open(oid, org.postgresql.largeobject.LargeObjectManager.WRITE);
						byte buf[] = new byte[(int)fi.length()];
						int le; 
						while ((le=fis.read(buf)) !=-1){
							obj.write(buf,0,le);
						}
						ps.setLong(9,oid);
						ps.executeUpdate();
						conn.commit();						
						fis.close();
				    }
				    else{
				    	if(nombre.equals("Mensaje:")){
				    		ps.setNull(9,0);
				    		ps.executeUpdate();
				    		conn.commit();
				    	}
				    	else System.out.println("No se encontro.");
				    }
				    
	        	}
	        	conn.setAutoCommit(true);
	        }
	        catch (Exception e) {
				e.printStackTrace();
			}
			 		 		 	
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            if(connO!=null)
				try {
					connO.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            System.out.println("Cerrados...");
        }
	}
	
	public void deProfesores(){
        Connection conn=null;
        Connection connO=null;
        	
        
        try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	 	try {
			connO = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.14:1521:ora1", "enoc", "caminacondios");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	 	System.out.println("conectado a oracle");
        PreparedStatement ps =null;
        PreparedStatement psO =null;
	    ResultSet rsO=null;
	    try {
	        psO = connO.prepareStatement("select * from ENOC.archivos_profesor"); 
	        try {
	        	String id,matricula,nombre,comentario,autorizacion;
	        	int folio;
	        	long tamano;
	        	Date fecha;
	        	
	        	DriverManager.registerDriver (new org.postgresql.Driver());
	        	conn=DriverManager.getConnection(aca.conecta.Conectar.coneccion(),aca.conecta.Conectar.usuario(),aca.conecta.Conectar.password());
	        	System.out.println("conectado a postgres");
	        	rsO=psO.executeQuery();
	        	conn.setAutoCommit(false);
	        	while(rsO.next()){
	        		id=rsO.getString(1);
	        		folio=rsO.getInt(2);
	        		matricula=rsO.getString(3);
	        		fecha=rsO.getDate(4);
	        		nombre=rsO.getString(5);
	        		comentario=rsO.getString(6);
	        		autorizacion=rsO.getString(7);
	        		tamano=rsO.getLong(8);
	        		
	        		ps = conn.prepareStatement("INSERT INTO PORTAL.ARCHIVOS_PROFESOR(ARCHIVO_ID, " + 
	    					"FOLIO,CODIGO_PERSONAL,FECHA,NOMBRE,COMENTARIO,AUTORIZACION,TAMANO,ARCHIVO) " +
	    					"VALUES(?,?,?,?,?,?,?,?,?)");
	    		    ps.setString(1,id);
	    			ps.setInt(2,folio);
	    			ps.setString(3,matricula);
	    			ps.setDate(4,fecha);
	    			ps.setString(5,nombre);
	    			ps.setString(6,comentario);
	    			ps.setString(7,autorizacion);
				    ps.setLong(8,tamano);
					
					System.out.print("Archivo "+nombre+" de "+matricula+"... ");
					
					//String ruta	= "/var/tomcat4/webapps/academico/portales/archivos/";
					String ruta = "E:\\academico\\academico\\portales\\archivos";
					String nombrereal="P"+id+"M"+matricula+"F"+folio+"N "+nombre;
				    File fi = new File(ruta+"/"+nombrereal);
				    if(fi.exists()){
				    	System.out.print("archivo existe...");
				    	FileInputStream fis = new FileInputStream(fi);
						ps.setBinaryStream(9,fis,(int)fi.length());
						System.out.println(fis.toString());
						ps.executeUpdate();
						conn.commit();
						System.out.println("Subido.");
						fis.close();
				    }
				    else{
				    	if(nombre.equals("Mensaje:")){
				    		ps.setNull(9,0);
				    		ps.executeUpdate();
				    		conn.commit();
				    	}
				    	else System.out.println("No se encontro.");
				    }
	        	}
	        	
	        	conn.setAutoCommit(true);
	        }
	        catch (Exception e) {
				e.printStackTrace();
			}
			 		 		 	
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            if(connO!=null)
				try {
					connO.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            System.out.println("Cerrados...");
        }
	}
    
    public static void main(String args[]) throws SQLException{
    	TraspasoArchivos t = new TraspasoArchivos();
    	//t.deAlumnos();
    	t.deProfesores();
    }
}