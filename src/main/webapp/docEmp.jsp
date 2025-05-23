<% String idJsp= "000"; %>
<%@include file= "seguro2.jsf" %>
<%@ include file="idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.emp.spring.EmpDocumento"%>
<%@page import="aca.emp.spring.EmpDocEmp"%>
<html>
<head>
	<title><spring:message code='aca.SistemaAcademico'/></title>
	<link href="academico.css" rel="STYLESHEET" type="text/css"> 
	<link href="print.css" rel="STYLESHEET" type="text/css" media="print">
	<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" type="text/css" media="screen" />	
	<script src="js/jquery-1.9.1.min.js"></script>
	
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"> 
  	<link rel="stylesheet" href="bootstrap/css/bootstrap-responsive.min.css" type="text/css" media="screen" />
  	<script src='bootstrap/js/bootstrap.min.js' type='text/javascript'></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  	<style>
  		#content{
			margin: 20px 20px 0 20px;
		}
	</style>
		
	<script type="text/javascript">
		function borrar(documentoId,hoja){
			if (confirm("¿Estas seguro de borrar este registro?")){
				location.href="borrar?DocumentoId="+documentoId;
			}	
		}	
	</script>
</head>	
<%	
	String codigoEmpleado 		= (String) session.getAttribute("codigoEmpleado");
	String nombreEmpleado 		= (String) request.getAttribute("nombreEmpleado");
	
	List<EmpDocumento> lisDocumentos	= (List<EmpDocumento>)request.getAttribute("lisDocumentos");
	
	HashMap<String, String> mapaImagenes			= (HashMap<String,String>)request.getAttribute("mapaImagenes");
%>
<body marginwidth="0" marginheight="0" leftmargin="0" topmargin="0" background="../../imagenes/back.gif">
<div class="container-fluid">
	<h2>Documentos<small class="text-muted"> ( <%=codigoEmpleado%> - <%=nombreEmpleado%>)</small></h2>
	<form name="forma" action="documentos" method='post' id="noayuda">
	<div class="alert alert-info">
		<a class="btn btn-primary" href="empleado"><i class="fas fa-arrow-left"></i> <spring:message code='aca.Regresar'/></a>
	</div>
	</form>
	<table  class="table table-fullcondensed table-striped">
		<thead>
		<tr>			
			<th width="5%"><spring:message code="aca.Numero"/></th>
			<th width="60%">Documento</th>
			<th width="5%">1</th>
			<th width="5%">2</th>
			<th width="5%">3</th>
			<th width="5%">4</th>
			<th width="5%">5</th>
			<th width="5%">6</th>
			<th width="5%">7</th>			
		</tr>
		</thead>		
		<tbody>
<%
	int row = 0;
	for (EmpDocumento documentos : lisDocumentos){
		row++;	
%>
		<tr>			
			<td><%=row%></td>			
			<td><%=documentos.getDocumentoNombre()%></td>
<%			
		for (int i=1; i<=7; i++){
			out.print("<td>");
				String tieneImagen = "NO";
				if (mapaImagenes.containsKey(codigoEmpleado+documentos.getDocumentoId()+i) ){			
					tieneImagen = "SI";					
					%><a href="verDoc?DocumentoId=<%=documentos.getDocumentoId()%>&Hoja=<%=i%>"><img src="imagenes/imagen.png" height="20px"></a><%					
				}			
			out.print("</td>");
		}	
%>	
		</tr>
<%			
		}
	%>		
		</tbody>
	</table>
	
</div>
</body>
</html>
