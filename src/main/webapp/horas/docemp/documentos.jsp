<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.emp.spring.EmpDocumento"%>
<%@page import="aca.emp.spring.EmpDocEmp"%>

<script type="text/javascript">
	function borrar(documentoId,hoja){
		if (confirm("¿Estas seguro de borrar este registro?")){
			location.href="borrar?DocumentoId="+documentoId;
		}	
	}
	
</script>
<%	
	String codigoEmpleado 		= (String) session.getAttribute("codigoEmpleado");
	String nombreEmpleado 		= (String) request.getAttribute("nombreEmpleado");
	
	List<EmpDocumento> lisDocumentos	= (List<EmpDocumento>)request.getAttribute("lisDocumentos");
	
	HashMap<String, String> mapaImagenes			= (HashMap<String,String>)request.getAttribute("mapaImagenes");
%>
<body >
<div class="container-fluid">
	<h2>Documentos<small  class="text-muted fs-4"> ( <%=codigoEmpleado%> - <%=nombreEmpleado%>)</small></h2>
	<form name="forma" action="documentos" method='post' id="noayuda">
	<div class="alert alert-info">
		<a class="btn btn-primary" href="nuevo"><i class="icon-white icon-plus"></i> <spring:message code='aca.Añadir'/></a>
		
	</div>
	</form>
	<table  class="table table-bordered table-striped">
		<thead>
		<tr class="table-info">			
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
					%><a href="nuevo?DocumentoId=<%=documentos.getDocumentoId()%>&Hoja=<%=i%>"><img src="../../imagenes/imagen.png" height="20px"></a><%					
				}else{
					%><a href="nuevo?DocumentoId=<%=documentos.getDocumentoId()%>&Hoja=<%=i%>"><img src="../../imagenes/upload.png" height="20px"></a><%
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
