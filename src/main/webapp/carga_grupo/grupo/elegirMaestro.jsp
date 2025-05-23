<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%
	// Declaracion de variables	
	String carreraId 		= (String)request.getAttribute("carreraId");
	String planId			= (String)request.getAttribute("planId");
	String cursoCargaId		= (String)request.getAttribute("cursoCargaId");
	String bloque 			= (String)request.getAttribute("bloque");
	String materia			= (String)request.getAttribute("materia");
	String asignaCambia		= (String)request.getAttribute("asignaCambia");
	String maestroTitular	= (String)request.getAttribute("maestroTitular");
	String maestroAuxiliar	= (String)request.getAttribute("maestroAuxiliar");
	String nombre			= (String)request.getAttribute("nombre");
	String codigoPersonal	= (String)request.getAttribute("usuario");
	String mensaje			= (String)request.getAttribute("mensaje");
%>
<div class="container-fluid">
<% if(asignaCambia.equals("1")){%>
	<h2>Regular Lecturer<small class="text-muted fs-4">(<%=materia%>)</small></h2>
<% }else{%>
	<h2>Assistant Lecturer<small class="text-muted fs-4">(<%=materia%>)</small></h2>
<% }%>
	
	<form action="guardarMaestro" method="post" name="frmmaestro" target="_self">
		<input type="hidden" name="CursoCargaId" value="<%=cursoCargaId%>">
		<input type="hidden" name="CarreraId" value="<%=carreraId%>">
		<input type="hidden" name="Materia" value="<%=materia%>">
		<input type="hidden" name="AsignaCambia" value="<%=asignaCambia%>">
		<input type="hidden" name="PlanId" value="<%=planId %>">
		<input type="hidden" name="bloque" value="<%=bloque %>">
		
		<div class="alert alert-info">
			<a href="maestro?CarreraId=<%=carreraId%>&PlanId=<%=planId %>&CursoCargaId=<%=cursoCargaId%>&bloque=<%=bloque%>&Materia=<%=materia%>" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;	
		</div>
<% 		if(mensaje.equals("1")){%>
		<div class="alert alert-success">
			Modified
		</div>
<%      }else if(mensaje.equals("2") || mensaje.equals("3")){%>
		<div class="alert alert-danger">
		<%=mensaje.equals("2")?"Not Updated":"Not registered"%>
		</div>
<% 		}%>
		
<% if(asignaCambia.equals("1") && !maestroTitular.equals("-")){%>
		<div class="alert alert-info">Current Teacher: <%=maestroTitular%></div>
<% }else if(asignaCambia.equals("2") && !maestroAuxiliar.equals("-")){%>
		<div class="alert alert-info">Current Teacher: <%=maestroAuxiliar%></div>
<% }%>

		<table class="table table-bordered table-sm">	
		<tr>
<% if(asignaCambia.equals("1")){%>
		<th width="50%">Replace with: <%=nombre%>&nbsp;</th>
<% }else{%>
		<th width="50%">Lecturer to assign: <%=nombre%></th>
<% }%>
		</tr>
		<tr>
			<td width="50%">
				<img src="../../foto?Codigo=<%=codigoPersonal%>&Tipo=O" width="70" border="1"><br>
				&nbsp;&nbsp;&nbsp;
				<b><%=codigoPersonal%></b>
			</td>
		</tr>
		</table>	
		<input type="submit" class="btn btn-primary" value="Aceptar">
	</form>
</div>