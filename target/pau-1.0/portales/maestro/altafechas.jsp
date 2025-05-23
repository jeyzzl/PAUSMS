<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<%
	String codigoPersonal  = (String) session.getAttribute("codigoPersonal");	
	String cursoCargaId 	= request.getParameter("CursoCargaId")==null?"-":request.getParameter("CursoCargaId");
	String materia 			= request.getParameter("Materia")==null?"-":request.getParameter("Materia");
	String mensaje		 	= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	String orden			= (String) request.getAttribute("orden");	
	String folio			= (String) request.getAttribute("folio");
	String fecha			= (String) request.getAttribute("fecha");
	String nombreMaestro	= (String) request.getAttribute("nombreMaestro");
	String nombreMateria	= (String) request.getAttribute("nombreMateria");
%>
<body>
<div class="container-fluid">
	<h2>Add Period
		<small class="text-muted h5">( <%=codigoPersonal%> - <%=nombreMaestro%> - <%=nombreMateria%> )</small>
	</h2>
	<div class="alert alert-info">
		<a href="asistencia?CursoCargaId=<%=cursoCargaId%>&Materia=<%=materia%>" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
	</div>	
	<form name="frmAlta" action="grabarfecha" method="post">
		<input name="Accion" type="hidden"/>
		<input name="CursoCargaId" type="hidden" value="<%=cursoCargaId%>"/>
		<input name="Materia" type="hidden" value="<%=materia%>"/>	
		<table class="table table-sm table-bordered" align="left" style="width:50%">
		<thead>			
		<tr> 
			<th colspan="2">Data</th>			
		</tr>
		</thead>
		<tbody>
		<tr> 
			<td><b>ID</b></td>
			<td>
				<input type="text" id="Folio" name="Folio" class="input input-small" value="<%=folio%>"/>
			</td>
		</tr>
		<tr>
			<td><b><spring:message code="aca.Fecha"/></b></td>
			<td>
				<input type="text" data-date-format="dd/mm/yyyy" id="Fecha" name="Fecha" value="<%=fecha%>"/>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<a href="javascript:Grabar();" class="btn btn-primary">Save</a>
				<%=mensaje.equals("-")?"":mensaje%>
			</td>									
		</tr>
		</tbody>	
		</table>	
	</form>	
</div>
</body>
<script type="text/javascript">
	jQuery('#Fecha').datepicker();
	
	function Grabar() {		
		document.frmAlta.submit();
	}
</script>