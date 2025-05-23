<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.ArrayList"%>
<%@page import="aca.musica.spring.MusiAlumno"%>

<% 	
	String codigo 			= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");	
	String estado 			= request.getParameter("Estado")==null?"S":request.getParameter("Estado");
	String mensaje 			= request.getParameter("mensaje")==null?"-":request.getParameter("mensaje");
	
	MusiAlumno musiAlumno 	= (MusiAlumno) request.getAttribute("musiAlumno");
%> 
<div class="container-fluid">
	<h2>Alumnos registrados</h2>	
	<div class="alert alert-info">
		<a class="btn btn-primary" href="alumnos?Estado=<%=estado%>">Regresar</a>
		<%if (!mensaje.equals("-")) out.print(mensaje);%>			
	</div>
	<form name="frmCodigo" action="grabarCodigo" method="post">
	<input type="hidden" name="Codigo" value="<%=codigo%>"/>
	<input type="hidden" name="Estado" value="<%=estado%>"/>
	<div class="row">
		<div class="span3">
			<label for="CodigoCimum">Codigo:</label>
			<input name="CodigoCimum" value="<%=musiAlumno.getCodigoUM()%>"/>
			<br>
			<label for="EstadoCimum">Estado:</label>			
			<select name="EstadoCimum" id="EstadoCimum" style="width:190px;">
				<option value="P" <%=musiAlumno.getEstado().equals("P")?"selected":""%>>En Proceso</option>
				<option value="S" <%=musiAlumno.getEstado().equals("S")?"selected":""%>>Solicitado</option>
				<option value="A" <%=musiAlumno.getEstado().equals("A")?"selected":""%>>Admitido</option>
			</select>
			<br><br>
			<a href="javascript:GrabarCodigo()" class="btn btn-primary">Grabar</a>
		</div>
	</div>				
	</form>	
</div>
<script>
	function GrabarCodigo(){
		document.frmCodigo.submit();
	}
</script>