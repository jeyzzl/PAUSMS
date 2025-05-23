<%@page import="java.io.FileInputStream"%>
<%
try{
    FileInputStream archivo = new FileInputStream(request.getParameter("Ruta")); 
    int longitud = archivo.available();
    byte[] datos = new byte[longitud];
    archivo.read(datos);
    archivo.close();
    
    response.setContentType("application/octet-stream");
	response.setHeader("Content-Disposition","attachment;filename="+request.getParameter("NombreArchivo"));
    
    ServletOutputStream ouputStream = response.getOutputStream();
    ouputStream.write(datos);
    ouputStream.flush();
    ouputStream.close();    	
}
catch(Exception e){ 
    e.printStackTrace();
}  
%>