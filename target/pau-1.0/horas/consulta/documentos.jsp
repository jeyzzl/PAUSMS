<%@ include file="id.jsp"%>
<%@ include file="../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.emp.spring.EmpDocumento"%>

<script type="text/javascript">
	function borrar(documentoId, hoja) {
		if (confirm("¿Estas seguro de borrar este registro?")) {
			location.href = "borrar?DocumentoId=" + documentoId;
		}
	}
</script>
<%
String     documentoId = request.getParameter("DocumentoId") == null ? "0" : request.getParameter("DocumentoId");
String 			  hoja = request.getParameter("Hoja") == null ? "0" : request.getParameter("Hoja");
String nombreDocumento = (String) request.getAttribute("nombreDocumento");

String codigoEmpleado = (String) request.getAttribute("codigoEmpleado");
String nombreEmpleado = (String) request.getAttribute("nombreEmpleado");
boolean existe = (boolean) request.getAttribute("existe");

List<EmpDocumento> lisDocumentos = (List<EmpDocumento>) request.getAttribute("lisDocumentos");
HashMap<String, String> mapaImagenes = (HashMap<String, String>) request.getAttribute("mapaImagenes");
%>
<body>
	<div class="container-fluid">
		<h2>
			Documentos<small class="text-muted fs-5"> ( <%=codigoEmpleado%>
				- <%=nombreEmpleado%>)
			</small>
		</h2>
		<form name="forma" action="documentos" method='post' id="noayuda">
			<div class="alert alert-info d-flex align-items-center">
				<a href="lista" class="btn btn-primary">Regresar</a>&nbsp;&nbsp;
				 
			</div>
		</form>
		<div class="row">
			<div class="col-5">
				<table class="table table-bordered table-striped">
					<thead>
						<tr class="table-info">
							<th width="5%"><spring:message code="aca.Numero" /></th>
							<th width="50%">Documento</th>
							<th width="45%">Hojas Activas</th>
						</tr>
					</thead>
					<tbody>
						<%
						int row = 0;
						for (EmpDocumento documento : lisDocumentos) {
							row++;
						%>
						<tr>
							<td><%=row%></td>
							<td><%=documento.getDocumentoNombre()%></td>
							<td>
								<%
								for (int i = 1; i <= 7; i++) {
									if (mapaImagenes.containsKey(codigoEmpleado + documento.getDocumentoId() + i)) {
								%> <a href="documentos?CodigoEmpleado=<%=codigoEmpleado%>&DocumentoId=<%=documento.getDocumentoId()%>&Hoja=<%=i%>"><span
									class="badge bg-dark rounded-pill"><%=i%></span></a>&nbsp;
						 <%
 							}
						 %> <%
 							}
 							%>
							</td>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>
			</div>
			<div class="col-7">
				<thead class="table-info">
				<%
				if (existe) { %>
					<span class="table-info">(<strong>Nombre Documento:</strong>&nbsp;<%= nombreDocumento%>)(<strong>Numero de Hoja:</strong>&nbsp;<%=hoja%>)&nbsp; </span>
				</thead>
				<div>
				<img src="docImagen?CodigoEmpleado=<%=codigoEmpleado%>&DocumentoId=<%=documentoId%>&Hoja=<%=hoja%>" width="90%">
				</div>
				<%
				}
				%>
			</div>
		</div>
	</div>
</body>
