<%@ page import="java.util.List"%>
<%@ page import="java.text.*"%>
<%@ page import="aca.residencia.spring.ResDocumento"%>
<%@ page import="aca.residencia.spring.ResExpediente"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ include file="../alumno/menu.jsp"%>

<head>
	<script type="text/javascript">
		function borrar(folio,expedienteId){
			if(confirm("Are you sure you want to delete this Document?")){
				location.href = "borrarDocumentoExpediente?Folio="+folio+"&ExpedienteId="+expedienteId;
			}
		}
	</script>
</head>
<%
	String codigoPersonal 		= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno 		= (String) session.getAttribute("codigoAlumno");
	String mensaje 				= (String) request.getAttribute("mensaje");

	ResDocumento documento 		= (ResDocumento) request.getAttribute("documento");
	ResExpediente expediente 	= (ResExpediente) request.getAttribute("expediente");
	
	List<ResDocumento> listaDocumentos = (List<ResDocumento>) request.getAttribute("listaDocumentos");

%>
<div class="container-fluid">

	<h2>New Document</h2>
	<div class="alert alert-info">
		<a href="solicitudExternado" class="btn btn-primary">Return</a>&nbsp;
	</div>	
	<div class="">	
		<form name="form" action="grabarDocumento" enctype="multipart/form-data" method="post">
			<input type="hidden" name="Folio" value="<%=documento.getFolio()%>">
			<input type="hidden" name="ExpedienteId" value="<%=expediente.getFolio()%>">
			<label><b>Description</b></label> <input type="text" class="form-control" name="Descripcion" value="<%=documento.getDescripcion()%>">
		  	<br>
		  	<span class="btn btn-file">
			  	<input type="file" id="archivo" name="archivo" required="required"/>
		  	</span>
			<br>
			<br>
			<button type="submit" class="btn btn-primary"><i class="fas fa-file-upload"></i> Upload</button>
		</form>
	</div>	
		<div class="">	
<% if(mensaje.equals("1")){%>
	<div class="aler alert-success">
		Saved
	</div>
<% }else if(mensaje.equals("2")){%>
	<div class="aler alert-success">
		Deleted
	</div>
<% }%>
		<table class="table">
			<thead>
				<tr>
					<th>Key</th>
					<th>Description</th>
					<th>Document</th>
				</tr>
			</thead>
			<tbody>
<%
		int cont = 1;
		for(ResDocumento docu : listaDocumentos){
%>
				<tr>
					<td>
						<%=cont++%>&nbsp;&nbsp;
<% 			if(expediente.getEstado().equals("S") || expediente.getEstado().equals("I")){%>
						<a href="javascript:borrar('<%=docu.getFolio()%>','<%=docu.getFolioExpediente()%>');" class="btn btn-danger" title="Delete Folder"><i class="fas fa-times"></i></a>
<% 			}%>
					</td>
					<td><%=docu.getDescripcion()%></td>
					<td>
<%			if(docu.getDocumento() != null){%>
						<a href="bajarExterno?Folio=<%=docu.getFolio()%>"><%=docu.getNombre()%></a>
<%			}%>
					</td>
				</tr>
<%
		}
%>
			</tbody>	
		</table>		
	</div>	
</div>