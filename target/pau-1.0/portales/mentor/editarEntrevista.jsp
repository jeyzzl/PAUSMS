<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.mentores.spring.MentContacto"%>
<%@page import="aca.mentores.spring.MentMotivo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<script type="text/javascript">
	
	function Grabar(){
		var selectedValues = "";    
	    jQuery("#Motivo :selected").each(function(){
	    	selectedValues+= jQuery(this).val()+",";    
	    });
	    selectedValues = selectedValues.substring(0,selectedValues.length-1);	    
	    document.frmContacto.Motivos.value=selectedValues;	
		document.frmContacto.submit();
	}
</script>
<%	
	String mentorId				= (String) session.getAttribute("codigoPersonal");
	String periodoId			= (String) session.getAttribute("periodo");	
	String codigoAlumno			= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
	String mensaje				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	String alumnoNombre 		= (String)request.getAttribute("alumnoNombre");
	String mentorNombre 		= (String)request.getAttribute("mentorNombre");	
	String fecha				= aca.util.Fecha.getHoy();
	MentContacto mentContacto 	= (MentContacto) request.getAttribute("mentContacto"); 
	
	String sBgcolor				= "";	
	List<MentMotivo> lisMotivos				= (List<MentMotivo>) request.getAttribute("lisMotivos");
%>
<div class="container-fluid">
	<h2>Mentor Interview<small class="text-muted fs-5"> (<%=mentorId %>&nbsp;&nbsp;&nbsp;<%=mentorNombre%>)</small></h2>
	<form name="frmContacto" id="frmContacto" action="grabarEntrevista" method="post">	
	<input type="hidden" name="ContactoId" value="<%=mentContacto.getContactoId()%>">
	<input type="hidden" name="Motivos"/>
	<div class="alert alert-info">
	<%if (codigoAlumno.equals("0")){%>	
    	<a class="btn btn-primary" href="portal"><spring:message code='aca.Regresar'/></a>
    <%}else{%>
    	<a class="btn btn-primary" href="entrevistas_alumno?CodigoAlumno=<%=codigoAlumno%>"><spring:message code='aca.Regresar'/></a>
    <%}%>
	</div>
	<%
	if(!mensaje.equals("-")){
		out.print("<div class='alert alert-info'>"+ mensaje +"</div>");		
	}
	%>
	<table style="width:474" class="table table-fullcondensed">  	
  	<tr> 
    	<td><b><spring:message code="aca.Matricula"/>:</b></td>
    	<td>
      		<input name="CodigoAlumno" type="text" class="text" id="CodigoAlumno" size="8" maxlength="7" value="<%=codigoAlumno%>">&nbsp;<%=alumnoNombre%>
      	</td>
  	</tr>
  	<tr> 
    	<td><b>Reason:</b></td>
   		<td>
      	<select name="Motivo" id="Motivo" class="chzn-select" multiple>
<%		String motivos = ","+mentContacto.getMotivos();
		for( MentMotivo motivo : lisMotivos){ %>
     		<option value="<%=motivo.getMotivoId()%>" <%=motivos.contains(","+motivo.getMotivoId())?"selected":""%>><%=motivo.getMotivoNombre() %></option>
<%		}	%>
      	</select> 
    	</td>
  	</tr>  	
  	<tr> 
    	<td><b><spring:message code="aca.Tipo"/>:</b></td>
    	<td>
    		<input name="Tipo" type="radio" value="V">&nbsp;Visit&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    		<input name="Tipo" type="radio" value="E" checked>&nbsp;Interview&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    		<input name="Tipo" type="radio" value="A">&nbsp;Assembly&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    		<input name="Tipo" type="radio" value="R">&nbsp;Meetings
    	</td>
  	</tr>
  	<tr> 
    	<td><b>Comments:</b></td>
    	<td><textarea name="Comentario" class="text" id="Comentario" cols="60" rows="3"><%=mentContacto.getComentario()%></textarea></td>
  	</tr>
  	<tr> 
    	<td><b>Entry Date:</b></td>
    	<td>
      	<input type="text" class="text" size="10" name="Fecha" id="Fecha" data-date-format="dd/mm/yyyy" value="<%=aca.util.Fecha.getHoy() %>" style="width:130px;" />
      	(DD/MM/YYYY)
		</td>
  	</tr> 	
	</table>
	<div class="alert alert-info">
		<a href="javascript:Grabar()" class="btn btn-primary">Save</a>
	</div>
	</form>	
</div>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script>
	jQuery(".chzn-select").chosen();
	jQuery('#Fecha').datepicker();
</script>