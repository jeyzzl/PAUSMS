<%@ include file="../../idioma.jsp"%>

<%@page import="java.sql.*"%>
<%
	aca.archivos.ArchivosProfesor bArchivosProfesor		= new aca.archivos.ArchivosProfesor();
	aca.archivos.Archivos bArchivos						= new aca.archivos.Archivos();
	
	Connection conn2 = null;
	try {		
		// Conexion a postgres
		DriverManager.registerDriver (new org.postgresql.Driver());
		conn2	= DriverManager.getConnection(aca.conecta.Conectar.coneccion(),aca.conecta.Conectar.usuario(),aca.conecta.Conectar.password());
		
		conn2.setAutoCommit(false);
		
		int nFolio			= 0;
		String origen 		= request.getParameter("clave")==null?"":request.getParameter("clave");
		String id 			= request.getParameter("id");
		String folio 		= request.getParameter("folio");
		String comentario 	= request.getParameter("comentario"); 
		String nombre 		= request.getParameter("nombre");
		String matricula 	= request.getParameter("matricula");		
		
		if(folio!=null)nFolio=Integer.parseInt(folio);
		
		String ruta 		= "../archivos/";
		String mtemp="", tabla="";
		
		if(origen.equals("P")){
			mtemp = (String)session.getAttribute("codigoAlumno");
			bArchivosProfesor.cambiaEstadoArchivo(conn2,id,nFolio,mtemp);
			tabla="archivos_profesor";
		}
		else if(!origen.equals("X")){
			bArchivos.cambiaEstadoArchivoAlumno(conn2,id,nFolio,matricula,"V");
			tabla="archivos_alumno";
		}else tabla="archivos_alumno";		
		
		/* conn2.setAutoCommit(false); */
		String nombrereal	= id+"M"+matricula+"F"+folio+"N"+nombre;
		String sql			= "SELECT archivo FROM portal."+tabla+" WHERE archivo_id = ? and folio = ? and codigo_personal=?";
		PreparedStatement ps = conn2.prepareStatement(sql);
		ps.setString(1,id);
		ps.setInt(2,Integer.parseInt(folio));
		ps.setString(3,matricula);
		ResultSet rs = ps.executeQuery();
		byte archivo[]=null;
		if (rs.next()) {
	        org.postgresql.largeobject.LargeObject obj;
			org.postgresql.largeobject.LargeObjectManager lom = ((org.postgresql.PGConnection)conn2).getLargeObjectAPI();
			long oid = rs.getLong(1);
	   		obj = lom.open(oid, org.postgresql.largeobject.LargeObjectManager.READ);
		    archivo = new byte[obj.size()];
		    //obj.read(archivo, 0, obj.size());
		    archivo =  obj.read(obj.size());
			obj.close();
	    }
		ps.close();
		rs.close();		
		
		conn2.setAutoCommit(true);
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition","attachment; filename=\""+nombre+ "\"");
		response.getOutputStream().write(archivo);
		
	}catch ( Exception e ) {
		e.printStackTrace();
	}finally{
		// cerrar la conexion..	
		if (conn2!=null) conn2.close();
	}
%>