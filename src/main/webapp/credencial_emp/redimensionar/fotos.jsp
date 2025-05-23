<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%
 	int numFotos		= (int) request.getAttribute("numFotos");
 	int numFotosRedi	= (int) request.getAttribute("numFotosRedi");	
%>
<script type="text/javascript">	
	function eliminar(codigoPersonal){
		if (confirm("¿Estas seguro de borrar este registro?")){
			location.href="eliminar?CodigoPersonal="+codigoPersonal+"&Tipo=V";
		}	
	}
</script>
<div class="container-fluid">
	<h2>Redimensionar fotos</h2>
	<div class="alert alert-info">
		<a href="grabar" class="btn btn-primary"><i class="icon-user icon-white"></i><spring:message code='aca.Grabar'/></a>
		<label>Fotos:<%=numFotos%></label> Redimensionadas: <%=numFotosRedi%>
	</div>	
	<div class="rounded border border-dark" style="width:200px">
		<img width="190" src="../../fotochica?Codigo=9800308" />
	</div>
</div>