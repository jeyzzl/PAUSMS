<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<jsp:useBean id="bArchivos" scope="page" class="aca.archivos.TraspasoArchivos"/>
<html><body>
<%
Connection conn=null;
Connection connO=null;
	

try {
	Class.forName("oracle.jdbc.driver.OracleDriver");
} catch (ClassNotFoundException e1) {
	e1.printStackTrace();
}
	try {
	connO = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.25:1521:ora1", "enoc", "caminacondios");
} catch (SQLException e1) {
	e1.printStackTrace();
}
	out.println("<br>conectado a oracle");
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
    	out.println("<br>conectado a postgres");
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
			
			out.print("<br>Archivo "+nombre+" de "+matricula+"... ");
			
			//String ruta	= "/var/tomcat4/webapps/academico/portales/archivos/";
			//String ruta = "E:\\academico\\academico\\portales\\archivos";
			String ruta = application.getRealPath("/portales/archivos")+"/";
			String nombrereal=id+"M"+matricula+"F"+folio+"N "+nombre;
		    File fi = new File(ruta+"/"+nombrereal);
		    if(fi.exists()){
		    	out.print("archivo existe...");
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
				out.println(" Subido.");
				fis.close();
		    }
		    else{
		    	if(nombre.equals("Mensaje:")){
		    		ps.setNull(9,0);
		    		ps.executeUpdate();
		    		conn.commit();
		    	}
		    	else out.println("No se encontro.");
		    }
		    
    	}
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
    out.println("<br>Cerradas las bases...");
}


//////P R O F E S O R

        try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	 	try {
			connO = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.25:1521:ora1", "enoc", "caminacondios");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	 	out.println("<BR>conectado a oracle");
         ps =null;
         psO =null;
	     rsO=null;
	    try {
	        psO = connO.prepareStatement("select * from ENOC.archivos_profesor"); 
	        try {
	        	String id,matricula,nombre,comentario,autorizacion;
	        	int folio;
	        	long tamano;
	        	Date fecha;
	        	
	        	DriverManager.registerDriver (new org.postgresql.Driver());
	        	conn=DriverManager.getConnection(aca.conecta.Conectar.coneccion(),aca.conecta.Conectar.usuario(),aca.conecta.Conectar.password());
	        	out.println("<br>conectado a postgres");
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
					
					out.print("<br>Archivo "+nombre+" de "+matricula+"... ");
					
					//String ruta	= "/var/tomcat4/webapps/academico/portales/archivos/";
					String ruta = application.getRealPath("/portales/archivos")+"/";
					String nombrereal="P"+id+"M"+matricula+"F"+folio+"N "+nombre;
				    File fi = new File(ruta+"/"+nombrereal);
				    if(fi.exists()){
				    	out.print("archivo existe...");
				    	FileInputStream fis = new FileInputStream(fi);
						ps.setBinaryStream(9,fis,(int)fi.length());
						out.println(fis.toString());
						ps.executeUpdate();
						conn.commit();
						out.println("Subido.");
						fis.close();
				    }
				    else{
				    	if(nombre.equals("Mensaje:")){
				    		ps.setNull(9,0);
				    		ps.executeUpdate();
				    		conn.commit();
				    	}
				    	else out.println("No se encontro.");
				    }
	        	}
	        }
	        catch (Exception e) {
				e.printStackTrace();
			}
			 		 		 	
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
        	conn.setAutoCommit(true);
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
           out.println("<br>Cerrados...");
        }

%>
Listo...
</body></html>