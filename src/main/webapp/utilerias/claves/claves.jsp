
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<script type="text/javascript">	
	function grabarMaestros(){
		if (confirm("Esta operaci�n genera las claves de los maestros, �Est�s seguro de continuar?")){
			document.location.href="grabarMaestros";
		}		
	}		
	function grabarAlumnos(){
		if (confirm("Esta operaci�n genera las claves de los alumnos, �Est�s seguro de continuar?")){
			document.location.href="grabarAlumnos";
		}		
	}
</script>
<!-- inicio de estructura -->
<%	
	String mensaje = request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje"); 
%>
<div class="container-fluid">
	<h2>Creacion de Claves</h2>
	<div class="alert alert-info">
		<a href="javascript:grabarMaestros()" class="btn btn-primary">Claves de Maestros</a>&nbsp;
		<a href="javascript:grabarAlumnos()" class="btn btn-primary">Claves de Alumnos</a>&nbsp;&nbsp;
		<%=mensaje.equals("-")?"":mensaje%>
	</div>		
</div>