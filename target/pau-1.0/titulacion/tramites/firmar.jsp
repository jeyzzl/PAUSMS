<%@ page import= "java.util.ArrayList"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.tit.spring.TitFirma"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<head></head>
<%	
	String institucion  	= request.getParameter("Institucion")==null?"UM":request.getParameter("Institucion");	
	String tramite 			= request.getParameter("Tramite")==null?"0":request.getParameter("Tramite");
	String mensaje			= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
	
	ArrayList<TitFirma> lisFirmas		= (ArrayList<TitFirma>)request.getAttribute("lisFirmas");	
%>
<body>
<div class="container-fluid">
	<h2>Firmar Tramite<small class="text-muted fs-4">(<%=tramite%>)</small></h2>
	<form name="frmTramite" action="grabar" method="post">
	<div class="alert alert-info">
		<a class="btn btn-primary" href="tramite?Institucion=<%=institucion%>&Estado=F"><i class="fas fa-arrow-left"></i></a>
	</div>
<% 	if (mensaje.equals("2")||mensaje.equals("3")){%>	
	<div class="alert alert-danger">
		¡ Error al firmar el tramite, el password no coincide con la llave privada del empleado ! 
	</div>	
<% 	}%>	
	</form>
	<table class="table table-sm table-bordered"> 
	<thead class="table-info">
	<tr>		
		<th width="10%">#</th>
		<th width="20%">Empleado</th>
		<th width="60%">Cargo</th>		
		<th width="10%">Estado</th>	
	</tr>
	</thead>
	<tbody>
<%
	int row = 0;
	for (TitFirma firma : lisFirmas){
		row++;	
%>
	<tr>		
		<td><%=row%></td>
		<td><%=firma.getCodigoPersonal()%>-<%=firma.getPrimerApellido()%> <%=firma.getSegundoApellido()%> <%=firma.getNombre()%></td>
		<td><%=firma.getCargo()%></td>
		<td><a href="subirFirma?Institucion=<%=institucion%>&Tramite=<%=tramite%>&CodigoPersonal=<%=firma.getCodigoPersonal()%>"><i class="fa fa-upload"></i></a></td>
	</tr>
<%		
	}
%>	
	</tbody>
	</table>	
</div>
</body>
<script type="text/javascript">
	function Grabar() {
		document.frmTramite.submit();
	}	
</script>
</html>