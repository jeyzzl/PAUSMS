<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.admision.spring.AdmEncuesta"%>
<%@page import="aca.admision.spring.AdmRecomienda"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<html>
<head>
	<script type="text/javascript">
		function enviarCorreo(folio,id,email){
			if(confirm("Do you want to send an email to this contact?")){
				location.href="enviarCorreoContacto?Folio="+folio+"&Id="+id+"&Email="+email;
			}
		}
		</script>
</head>
<%	
	String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String nombreAlumno		= (String)request.getAttribute("nombre");
	String mensaje			= (String)request.getAttribute("mensaje");
	
	List<AdmRecomienda> listaRecomendaciones 		= (List<AdmRecomienda>)request.getAttribute("listaRecomendaciones");
	HashMap<String,AdmEncuesta> mapaEncuestas 		= (HashMap<String,AdmEncuesta>)request.getAttribute("mapaEncuestas");
%>
<body>
<div class="container-fluid">
	<h2>Registered Surveys<small class="text-muted fs-5"> ( <%=nombreAlumno%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="mostrarProceso?Folio=<%=folio%>"><i class="fas fa-arrow-left"></i></a>
	</div>
<% 	if(mensaje.equals("3")){%>
	<div class="alert alert-success">Sent email</div>
<% 	}%>
	<table class="table table-bordered">
	<thead class="table-info">
	<tr>
		<th width="10%">#</th>
		<th width="8%">Referee ID</th>
		<th width="20%">Referee</th>			
		<th width="20%">Email</th>			
		<th width="15%">Ocupation</th>
		<th width="35%">Opinion</th>
	</tr>
	</thead>
<%	
	int row=0;
	for(AdmRecomienda recomienda : listaRecomendaciones){
		row++;
		
		String opinion = "0";
		if (mapaEncuestas.containsKey(recomienda.getRecomendacionId())) {
			opinion = mapaEncuestas.get(recomienda.getRecomendacionId()).getOpinion();
		}		
		if(opinion.equals("1")){
			opinion = "Highly Recommended";		
		}else if(opinion.equals("2")){
			opinion = "Recommended with notes";
		}else if(opinion.equals("3")){
			opinion = "Recommended";
		}else{
			opinion = "No Recommendation";
		}
%>	
	<tr>
		<td>
			<%=row%> &nbsp;<a class="btn btn-info" href="editarContacto?Folio=<%=recomienda.getFolio()%>&Id=<%=recomienda.getRecomendacionId()%>" title="Edit"><i class="fas fa-user-edit"></i></a>
		</td>
		<td><a class="btn btn-info" href="mostrarEncuesta?Folio=<%=recomienda.getFolio()%>&Id=<%=recomienda.getRecomendacionId()%>" target="_blank"><%=recomienda.getRecomendacionId()%></a></td>
		<td><%=recomienda.getNombre()%></td>
		<td><a class="btn btn-success" onclick="enviarCorreo('<%=recomienda.getFolio()%>','<%=recomienda.getRecomendacionId()%>','<%=recomienda.getEmail()%>')"; title="Send email"><i class="fas fa-envelope-square"></i></a>&nbsp;&nbsp;<%=recomienda.getEmail()%></td>
		<td><%=recomienda.getPuesto()%></td>
		<td><%=opinion%></td>
	</tr>
<% 	}%>
	</table>	
</div>
</body>
</html>