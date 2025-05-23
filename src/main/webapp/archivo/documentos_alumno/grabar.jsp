<%@page import="java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.archivo.spring.ArchUbicacion"%>
<%@page import="aca.archivo.spring.ArchStatus"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<!-- inicio de estructura -->
<%
	String codigoUsuario 	= (String) session.getAttribute("codigoPersonal");
	String Matricula 		= (String) session.getAttribute("codigoAlumno");

	String IdDocumento		= request.getParameter("IdDocumento");
	String IdStatus 		= request.getParameter("IdStatus");
	String Fecha			= request.getParameter("Fecha");
	String Usuario			= request.getParameter("Usuario");
	String Cantidad			= request.getParameter("Cantidad")==null?"1":request.getParameter("Cantidad");	
	String ubicacion		= request.getParameter("Ubicacion")==null?"1":request.getParameter("Ubicacion");
	String opcion 			= request.getParameter("opcion")==null?"0":request.getParameter("opcion");
	
	String nombreAlumno 	= (String) request.getAttribute("nombreAlumno");
	String alumCarrera 		= (String) request.getAttribute("alumCarrera");
	
	AlumPlan plan 	= (AlumPlan)request.getAttribute("plan");
	
	List<ArchUbicacion> lisUbicacion	= (List<ArchUbicacion>) request.getAttribute("lisUbicacion");
	List<ArchStatus> lisStatus 			= (List<ArchStatus>)request.getAttribute("lisStatus");
	
	HashMap<String,String> mapaDocumentos	= (HashMap<String, String>)request.getAttribute("mapaDocumentos");
	HashMap<String,String> mapaStatus	 	= (HashMap<String, String>)request.getAttribute("mapaStatus");
%>
<head>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

</head>
<body>
<%if(!opcion.equals("0")){System.out.println("Opcion 1");
%>
<div class="container-fluid">
	<h3>Edit <small class="text-muted fs-6"> (<%=Matricula%> - <%= nombreAlumno %> - <%=plan.getPlanId()%> - <%=alumCarrera%> )</h3>
	<div class="alert alert-info d-flex align-items-center">
		<a href="documentos" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<form name="form1" method="post" action="add">
  	<table style="margin: 0 auto;" class="table">
    <tr> 
      <td width="100px"><b><spring:message code="aca.Documento"/>:</b></td>
      <td><%=mapaDocumentos.get(IdDocumento)%> <font color="#000000"><b>[ <%=IdDocumento%> ]</b></font></td>
    </tr>
    <tr> 
      <td><b>Status:</b></td>
      <td><%=mapaStatus.get(IdStatus)%> <font color="#000000"><b>[ <%=IdStatus%> ]</b></font></td>
    </tr>
    <tr> 
    	<td><strong>Location:</strong></td>
        <td> 
        	<select name="Ubicacion">
<%
		for(ArchUbicacion ubi :lisUbicacion){		
%>       
        	<option value="<%=ubi.getUbicacionId()%>" <%if(ubicacion.equals(ubi.getUbicacionId())) out.print("selected");%>><%=ubi.getUbicacionNombre()%></option>    
<%
		}
%>      
			</select>
        </td>
    </tr>
    <tr> 
      <td><b><spring:message code="aca.Fecha"/>:</b></td>
      <td> 
        <input type="text" class="text" id="Fecha" name="Fecha" size="11" maxlength="11" value="<%=aca.util.Fecha.getHoy()%>">
        (DD/MM/AAAA)
      </td>
    </tr>
    <tr> 
      <td><b>Amount:</b></td>
      <td> 
        <input type="text" class="text" name="Cantidad" size="3" maxlength="2" value="<%=Cantidad%>">
      </td>
    </tr>
    <tr> 
      <td><b>Image:</b></td>
      <td> Disabled</td>
    </tr>
    <tr> 
      <td><b><spring:message code="aca.Usuario"/>:</b></td>
      <td><b><%=codigoUsuario%></b></td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td colspan="2" align="center"> 
        <input type="submit" name="Grabar" value="Grabar">
        <input type="hidden" name="Matricula" value="<%=Matricula%>">	
		<input type="hidden" name="IdDocumento" value="<%=IdDocumento%>">
		<input type="hidden" name="IdStatus" value="<%=IdStatus%>">
		<input type="hidden" name="Fecha" value="<%=Fecha%>">
		<input type="hidden" name="Usuario" value="<%=codigoUsuario%>">
		<input type="hidden" name="Cantidad" value="<%=Cantidad%>">	
		<input type="hidden" name="Ubicacion" value="<%=ubicacion%>">				
		<input type="hidden" name="Opcion" value="<%=opcion%>">		
      </td>
    </tr>
  </table>
</form>
</div>
<%}else{%>
<div class="container-fluid">
	<h3>Edit <small class="text-muted fs-6"> (<%=Matricula%> - <%= nombreAlumno %> - <%=plan.getPlanId()%> - <%=alumCarrera%> )</h3>	
	<div class="alert alert-info d-flex align-items-center">
		<a href="documentos" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<form name="form1" method="post" action="add">
  	<table style="margin: 0 auto;" class="table">
  	<tr> 
      <td width="100px"><b><spring:message code="aca.Documento"/>:</b></td>
      <td><%=mapaDocumentos.get(IdDocumento)%> <font color="#000000"><b>[ <%=IdDocumento%> ]</b></font></td>
    </tr>
    <tr> 
    	<td><strong>Status:</strong></td>
        <td> 
        	<select name="IdStatus" class="form-select" style="width:200px;">
<%
			for (int i=0; i< lisStatus.size(); i++){
				ArchStatus doc = (ArchStatus) lisStatus.get(i);
%>		
			<option value="<%=doc.getIdStatus()%>" <%if(doc.getIdStatus().equals(IdStatus)) out.print("selected");%>><%=doc.getDescripcion()%></option>
<%			} %>  
        	</select>
        </td>
    </tr>
    <tr> 
    	<td><strong>Location:</strong></td>
        <td> 
        	<select name="Ubicacion" class="form-select" style="width:200px;">
<%		for(ArchUbicacion ubi :lisUbicacion){ %>       
        	<option value="<%=ubi.getUbicacionId()%>" <%if(ubicacion.equals(ubi.getUbicacionId())) out.print("selected");%>><%=ubi.getUbicacionNombre()%></option> 
<%		} %> 
        	</select>
        </td>
    </tr>
    <tr> 
    	<td><b><spring:message code="aca.Fecha"/>:</b></td>
      	<td> 
        	<input type="text" class="form-control" style="width:200px;" data-date-format="dd/mm/yyyy" id="Fecha" name="Fecha" size="11" maxlength="11" value="<%=aca.util.Fecha.getHoy()%>">(DD/MM/AAAA)
      	</td>
    </tr>
    <tr> 
      <td><b>Amount:</b></td>
      <td> 
        <input type="text" class="form-control" style="width:200px;"  name="Cantidad" size="3" maxlength="2" value="<%=Cantidad%>">
      </td>
    </tr>
    <tr> 
      <td><b><spring:message code="aca.Usuario"/>:</b></td>
      <td><b><%=codigoUsuario%></b></td>
    </tr>
    <tr>
      <td colspan="2"> 
        <input type="submit" class="btn btn-primary" name="Grabar" value="Grabar">
        <input type="hidden" name="Matricula" value="<%=codigoUsuario%>">		
		<input type="hidden" name="IdDocumento" value="<%=IdDocumento%>">
		<input type="hidden" name="IdStatus" value="<%=IdStatus%>">
		<input type="hidden" name="Fecha" value="<%=Fecha%>">
		<input type="hidden" name="Usuario" value="<%=codigoUsuario%>">
		<input type="hidden" name="Cantidad" value="<%=Cantidad%>">				
		<input type="hidden" name="Opcion" value="<%=opcion%>">		
      </td>
    </tr>
  </table>
</form>
</div>
<%} %>
</body>
<script>
	jQuery('#Fecha').datepicker();
</script>