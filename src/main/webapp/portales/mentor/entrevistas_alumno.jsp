<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.mentores.spring.MentContacto"%>
<%@page import="aca.mentores.spring.MentMotivo"%>
<%@page import="aca.catalogo.spring.CatPeriodo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<script type="text/javascript">
	
	function Borrar(alumno,contacto){
		if (confirm("Are you sure you want to delete this record?")==true){
			document.location.href="borrarEntrevista?CodigoAlumno="+alumno+"&ContactoId="+contacto;
		}		
	}

</script>
<%	
	String mentorId			= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno		= request.getParameter("CodigoAlumno")==null?"":request.getParameter("CodigoAlumno");
	
	String periodoId		= (String)request.getAttribute("periodoId");
	String alumnoNombre 	= (String)request.getAttribute("alumnoNombre");
	String mentorNombre 	= (String)request.getAttribute("mentorNombre");		
	String fecha			= aca.util.Fecha.getHoy();
	
	String sBgcolor			= "";	
		
	List<MentContacto> lisEntrevistas				= (List<MentContacto>) request.getAttribute("lisEntrevistas");
	HashMap<String,MentMotivo> mapaMotivos			= (HashMap<String,MentMotivo>) request.getAttribute("mapaMotivos");
%>
<div class="container-fluid">
	<h2>Interview Record <small class="text-muted fs-5">(Mentor: <%=mentorId %> - <%=mentorNombre%>)</small></h2>
	<div class="alert alert-info">	
    	<a class="btn btn-primary" href="portal?PeriodoId=<%=periodoId%>"><spring:message code='aca.Regresar'/></a>&nbsp;&nbsp;
		<a class="btn btn-success" href="editarEntrevista?CodigoAlumno=<%=codigoAlumno%>">Add Interview</a>
	</div> 
<%		
	String 	mes		= "0";
%>	
	<table style="width:1100" class="table table-sm table-nohover"> 	
<%
	int row = 0;
	for(MentContacto contacto : lisEntrevistas){		
		row++;	
		if (!contacto.getFechaContacto().substring(3,5).equals(mes)){ 
			mes = contacto.getFechaContacto().substring(3,5);
%>  
  	<tr class="alert alert-info">
		<td colspan="9" align="center"><h3>Month: <%=aca.util.Fecha.getMesNombre(Integer.parseInt(mes)) %></h3></td>
  	</tr>
  	<tr> 
    	<th width="5%">Op.</th>
    	<th width="3%">Key</th>
    	<th width="5%"><spring:message code="aca.Matricula"/></th>
    	<th width="15%"><spring:message code="aca.Nombre"/></th>
    	<th width="5%"><spring:message code="aca.Fecha"/></th>
    	<th width="5%">Reason</th>
    	<th width="5%"><spring:message code="aca.Tipo"/></th>
    	<th width="35%"><spring:message code="aca.Comentario"/></th>
		<th width="22%">S. Reason.</th>
  	</tr>
<%		
		}		
		
		String motivoNombre = "Other";
		if (mapaMotivos.containsKey(contacto.getMotivoId())){
			motivoNombre = mapaMotivos.get(contacto.getMotivoId()).getMotivoNombre();
		}
		
		String tipoNombre = "Interview";		
		if (contacto.getTipo().equals("V")) tipoNombre = "Visit"; 
		if (contacto.getTipo().equals("A")) tipoNombre = "Assembly";
		if (contacto.getTipo().equals("R")) tipoNombre = "Meeting";
		
		String arregloMotivos[] = contacto.getMotivos().split(",");
		String textoMotivos = "";
		for (String motivo : arregloMotivos){
			if (mapaMotivos.containsKey(motivo)){
				if (textoMotivos.length()==0)
					textoMotivos += mapaMotivos.get(motivo).getMotivoNombre();
				else
					textoMotivos += ", "+mapaMotivos.get(motivo).getMotivoNombre();
			}
		}
%>
	<tr> 
    	<td>		
	        <a href="editarEntrevista?CodigoAlumno=<%=contacto.getCodigoPersonal()%>&ContactoId=<%=contacto.getContactoId()%>"> 
	        	<i class="fas fa-pencil-alt"></i>	        	
	        </a>
	        &nbsp;
	        <a href="javascript:Borrar('<%=contacto.getCodigoPersonal()%>','<%=contacto.getContactoId()%>')"> 
	        	<i class="fas fa-trash" ></i>
	        </a>
        </td>
    	<td><div align="center"><%=contacto.getContactoId()%></div></td>
    	<td><div align="center"><%=contacto.getCodigoPersonal()%></div></td>
    	<td><%=alumnoNombre%></td>
    	<td><div align="center"><%=contacto.getFechaContacto()%></div></td>
    	<td><%=motivoNombre%></td>
    	<td><div align="center"><%=tipoNombre%></div></td>
		<td><div><%if(contacto.getComentario()==null){%>&nbsp;<%}else{%><%=contacto.getComentario() %><%}%></div></td>
		<td><%=textoMotivos%></td>
  	</tr>
<%		
	}
%>
	</table>
</div>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script>
	jQuery(".chzn-select").chosen();
	jQuery('#Fecha').datepicker();
</script>