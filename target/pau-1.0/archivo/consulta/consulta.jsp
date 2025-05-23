<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.archivo.spring.ArchDocAlum"%>
<%@page import="aca.archivo.spring.ArchDocumentos"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String matricula 						= (String) session.getAttribute("codigoAlumno");
	String planActivo 						= (String) request.getAttribute("planActivo");
	String autorizado 						= (String) request.getAttribute("autorizado");
	AlumPersonal alumPersonal 				= (AlumPersonal) request.getAttribute("alumPersonal");
	AlumAcademico alumAcademico 			= (AlumAcademico) request.getAttribute("alumAcademico");
	List<ArchDocAlum> lisDocumentos			= (List<ArchDocAlum>) request.getAttribute("lisDocumentos");
	HashMap<String,String> mapaDocumentos	= (HashMap<String,String>) request.getAttribute("mapaDocumentos");
	HashMap<String,String> mapaStatus		= (HashMap<String,String>) request.getAttribute("mapaStatus");
%>
<body>
<div class="container-fluid">
	<h2>Your Documents in Archive</h2>
	<div class="alert alert-info d-flex align-items-center">
		[<%=matricula%>] [<%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoPaterno()%> <%=alumPersonal.getApellidoMaterno()%>] - [<%=planActivo%>]
	</div>	
	<table class="table table-sm table-bordered">
	<tr>
		<td colspan="10" align="center">
			<font size="2" color="#000099"><b><%=autorizado%></b></font>
		</td>
	</tr>				  
	<tr>
		<td colspan="9">
  			<a class="btn btn-primary" href="../../portales/alumno/digital" target="_blank"><b><img src="../../imagenes/scanner.gif" > View Digitized Documents</b></a>
		</td>
	</tr>
	<tr> 
		<th width="4%"><spring:message code="aca.Operacion"/></th>
		<th width="4%"><spring:message code="aca.Numero"/></th>
		<th width="35%"><spring:message code="aca.Documento"/></th>
		<th width="20%"><spring:message code="aca.Status"/></th>
		<th width="11%"><spring:message code="aca.Fecha"/></th>
		<th width="6%">Amount</th>
		<th width="12%"><spring:message code='aca.Usuario'/></th>
  	</tr>				
<%	
	for (ArchDocAlum doc : lisDocumentos){
		
		String documentoNombre = "";
		if (mapaDocumentos.containsKey(doc.getIdDocumento())){
			documentoNombre = mapaDocumentos.get(doc.getIdDocumento());
		}
		
		String statusNombre	= "";
		if (mapaStatus.containsKey(doc.getIdStatus())){
			statusNombre = mapaStatus.get(doc.getIdStatus());
		}
%>	
		<tr class="tr2"> 
	    	<td width="11%" align="center">Disabled</td>
			<td width="4%" align="center"><font color="#000000" size="2"><%=doc.getIdDocumento()%></font></td>
			<td width="35%"><font color="#000000" size="1"><%=documentoNombre%></font></td>
			<td width="20%"><font color="#000000" size="1"><%=statusNombre%></font></td>
			<td width="12%" align="center"><font color="#000000" size="1"><%=doc.getFecha()%></font></td>
			<td width="5%" align="center"><font color="#000000" size="1"><%=doc.getCantidad()%></font></td>
			<td width="12%" align="center"><font color="#000000" size="1"><%=doc.getUsuario()%></font></td>
		</tr>
<%	} %>					
	</table>	
	<table style="width:100%"   ><tr><td background="../../imagenes/shadow.gif" height="4"></td></tr></table>
</div>