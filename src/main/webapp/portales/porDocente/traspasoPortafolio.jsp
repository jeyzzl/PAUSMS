<%@page import="aca.por.PorSeccionEmp"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%@ include file= "menuPortal.jsp" %>

<%@page import="java.io.File"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.FileOutputStream"%>

<jsp:useBean id="PorDocumentoU" scope="page" class="aca.por.PorDocumentoUtil"/>
<jsp:useBean id="PorDocumento" scope="page" class="aca.por.PorDocumento"/>
<jsp:useBean id="PorSeccionEmpU" scope="page" class="aca.por.PorSeccionEmpUtil"/>
<jsp:useBean id="PorSeccionU" scope="page" class="aca.por.PorSeccionUtil"/>
<jsp:useBean id="SeccionEmp" scope="page" class="aca.por.PorSeccionEmp"/>
<jsp:useBean id="Seccion" scope="page" class="aca.por.PorSeccion"/>

<script>
	function Refrescar(){
 		document.forma.Accion.value = "0";
 		document.forma.submit();
	}
	
	function Guardar(){
 		document.forma.Accion.value = "1";
 		document.forma.submit();
	}
</script>
<%
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	String portafolioEnvia		= request.getParameter("PortafolioEnvia")==null?"0":request.getParameter("PortafolioEnvia");
	String portafolioRecibe		= request.getParameter("PortafolioRecibe")==null?"0":request.getParameter("PortafolioRecibe");
	String accion 				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	String mensaje				= "-";
	int grabado = 0, errores = 0;

	// Lista de portafolios
	ArrayList<aca.por.PorDocumento> lisPortafolio	    =  PorDocumentoU.getListAll(conEnoc, " ORDER BY POR_ID");
	
	// Lista de secciones de ese portafolio
/* 	ArrayList<aca.por.PorSeccion> lisSeccionPortafolio	=  PorSeccionU.listPortafolio(conEnoc, portafolioEnvia," ORDER BY POR_ID"); */
	ArrayList<aca.por.PorSeccionEmp> getListAll = PorSeccionEmpU.getListAll(conEnoc, " ORDER BY POR_ID");
	
	if(accion.equals("1")){
		
		ArrayList<String> listImagenes = new ArrayList<String>();
		
		String rutaCarpeta		= application.getRealPath("/WEB-INF/porDocente")+"/"+codigoPersonal;
		File carpeta = new File(rutaCarpeta);	
		
	    File[] files = carpeta.listFiles();
	    for (File file : files) {
	    	String[]fileArray = file.getName().split("-");
	    	if(fileArray[0].equals(portafolioEnvia)){
				if(!listImagenes.contains(file.getName())){
					listImagenes.add(file.getName());
				}
			}
	    }
	    
	    for(String archivo : listImagenes){
	    	String[]fileArray = archivo.split("-");
	    	String nuevoArchivo = archivo.replace(portafolioEnvia+"-"+fileArray[1], portafolioRecibe+"-"+fileArray[1]);
	    	
	    	String rutaCopia		= application.getRealPath("/WEB-INF/porDocente")+"/"+codigoPersonal+"/"+archivo;
	    	
	    	try{
	    	    
	    	    // Leer el archivo en objeto File y FileInputStream
	    	    File fi 			= new File(rutaCopia);
	    	    FileInputStream fis = new FileInputStream(fi);
	    	    
	    	    // crear un arreglo de bytes con la longitud del archivo 
	    		byte buf[] 			= new byte[(int)fi.length()];
	    	    
	    		// llenar el arreglo de bytes con los bytes del archivo
	    		fis.read(buf,0,(int)fi.length());
	    		
	    		// Escribir el archivo en el directorio del servidor de aplicaciones con el objeto FileOutputStream
	    		FileOutputStream fos = new FileOutputStream(rutaCarpeta+"/"+nuevoArchivo);
	    		fos.write(buf,0,(int)fi.length());
	    		fos.flush();
	    		
	    		// Cerrar los objetos
	    		if (fos!=null) fos.close();
	    		if (fis!=null) fis.close();
	    		
	    	}catch(Exception e){
	    		System.out.println("Error al subir el archivo: "+e);
	    	}	    	
	    }	    
		
		for(aca.por.PorSeccionEmp seccion : getListAll){
			seccion.setPorId(portafolioRecibe);
			if (!seccion.existeReg(conEnoc)){
				if (seccion.insertReg(conEnoc)){
					grabado++;	
				}else{
					errores++;
				}				
			}			
		}
		mensaje	= "Guardó:"+grabado+" - No guardó:"+errores;
	}
	
	//pageContext.setAttribute("resultado", mensaje);
%>

<div class="container-fluid">
	
		<h2>Traspasar Portafolio</h2>
		
		<% if ( !mensaje.equals("-")){ %>
	   		<div class='alert alert-success'><%=mensaje%></div>
	  	<% }%>
		<br>
		<form id="forma" name="forma" action="traspasoPortafolio" method="post">
			<div class="alert alert-info">
		 		<a class="btn btn-primary" href="portafolio"><i class="icon-arrow-left icon-white"></i> <spring:message code="aca.Regresar" /></a>
			</div>
			<input type="hidden" id="Accion" name="Accion">
			
			<div class="row">
				<div class="span5">					
					<h3>Envia</h3>
					<label><spring:message code="aca.Portafolio" />:</label>
					<select id="PortafolioEnvia" name="PortafolioEnvia" onchange="javascript:Refrescar();" style="width:360px;margin-bottom:0px;">
				<%
				for (aca.por.PorDocumento por : lisPortafolio) {
				%>
						<option value="<%=por.getPorId()  %>" <% if (portafolioEnvia.equals(por.getPorId())) out.print("selected");%> ><%=por.getPorNombre()%></option>
				<%
					}
				%>
					</select>
				</div>
				
				<div class="span2"><br><br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-success"><i class="icon-arrow-right icon-white"></i></a></div>
				
				<div class="span5">					
					<h3>Recibe</h3>
					<label><spring:message code="aca.Portafolio" />:</label>
					<select id="PortafolioRecibe" name="PortafolioRecibe" onchange="javascript:Refrescar();" style="width:360px;margin-bottom:0px;">
				<%
					for (aca.por.PorDocumento por : lisPortafolio) {
				%>
						<option value="<%=por.getPorId()  %>" <% if (portafolioRecibe.equals(por.getPorId())) out.print("selected");%> ><%=por.getPorNombre()%></option>
				<%
					}
				%>
					</select>
				</div>
			</div>	
			<br><br>
			<div class="alert alert-info">
			<%
				if(!portafolioEnvia.equals(portafolioRecibe)){	
					if(PorSeccionU.listPortafolio(conEnoc,portafolioEnvia, "ORDER BY ORDEN, SECCION_ID").size() > 0){
			%>
						<a class="btn btn-primary" href="javascript:Guardar();">
							  <i class="icon-plus icon-white"></i> <spring:message code="aca.Copiar" />
						</a>
			<%
					}else if(PorSeccionU.listPortafolio(conEnoc,portafolioRecibe, "ORDER BY ORDEN, SECCION_ID").size() > 0){
			%>
						<a class="btn btn-warning">
							  El portafolio que va a recibir ya tiene datos
						</a>
			<%
					}else {
			%>
						<a class="btn btn-danger">
							  El portafolio que quieres copiar no tiene datos
						</a>
			<%
					}
				}				 
			%>
			</div>
				
		</form>
	
	</div>

<%@ include file="../../cierra_enoc.jsp" %>