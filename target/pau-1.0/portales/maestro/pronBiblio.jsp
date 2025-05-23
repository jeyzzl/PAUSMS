<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@ page import= "aca.plan.spring.MapaNuevoBibliografia"%>
<% 
	aca.pron.spring.PronBiblio pronBiblio = (aca.pron.spring.PronBiblio)request.getAttribute("pronBiblio");
	List<aca.pron.spring.PronBiblio> listaBiblio = (List<aca.pron.spring.PronBiblio>)request.getAttribute("listaBiblio");
	List<MapaNuevoBibliografia> lisBiblio = (List<MapaNuevoBibliografia>)request.getAttribute("lisBiblio");

	String cursoCargaId = request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
	boolean envio		= (boolean)request.getAttribute("envio");	
	
	String materia 			= (String)request.getAttribute("materia");	
	String maestroNombre	= (String)request.getAttribute("maestroNombre");

	String completoMateria 	= (String)request.getAttribute("completoMateria");
	String completoUnidades = (String)request.getAttribute("completoUnidades");
	String completoEjes 	= (String)request.getAttribute("completoEjes");
	String completoValores 	= (String)request.getAttribute("completoValores");
	String completoEsquema 	= (String)request.getAttribute("completoEsquema");
	String completoBiblio 	= (String)request.getAttribute("completoBiblio");
%>
<body>
	<div class="container-fluid">
		<h3>Plan de curso<small class="text-muted">( <%=maestroNombre%> - <%=materia%> )</small></h3>
		<div class="alert alert-success">
			<a class="btn btn-primary" href="cursos"><spring:message code="aca.Regresar"/></a>
<%		if(completoMateria.equals("Si") && completoUnidades.equals("Si") && /*completoEjes.equals("Si") && completoValores.equals("Si") &&*/ completoEsquema.equals("Si") && completoBiblio.equals("Si")){%>
			<a class="btn btn-success" href="enviarCursoCargaId?CursoCargaId=<%=cursoCargaId%>&Origen=pronBiblio">Enviar</a>
<%			if(envio){%>
				<a class="btn btn-primary" href="pdfPron?CursoCargaId=<%=cursoCargaId%>" target="_blank">Imprimir</a>
<%			}%>
<%      }%>
		</div>	
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
				<div class="navbar-nav">
					<ul class="nav nav-tabs" role="tablist">
					    <li class="nav-item nav-link"><a href="pronMateria?CursoCargaId=<%=cursoCargaId%>">Materia <%if(completoMateria.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li>
					    <li class="nav-item nav-link"><a href="pronUnidad?CursoCargaId=<%=cursoCargaId%>">Unidades <%if(completoUnidades.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li>
					    <li class="nav-item nav-link"><a href="pronEjes?CursoCargaId=<%=cursoCargaId%>">Ejes <%if(completoEjes.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li>
					    <li class="nav-item nav-link"><a href="pronValor?CursoCargaId=<%=cursoCargaId%>">Valores <%if(completoValores.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li> 
					    <li class="nav-item nav-link"><a href="pronEsquema?CursoCargaId=<%=cursoCargaId%>">Esquema <%if(completoEsquema.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li>  
					    <li class="nav-item nav-link active"><a href="#home" aria-controls="home" role="tab" data-bs-toggle="tab">Bibliografía <%if(completoBiblio.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li> 
					</ul>
				</div>
			</div>
		</nav>
		<div class="alert alert-info" role="alert">
			<form action="grabaPronBiblio" method="POST">
				<input type="hidden" name="CursoCargaId" value="<%=cursoCargaId%>">
				<strong>Orden:</strong>&nbsp;<input type="text" size="2" name="Orden" value="<%=pronBiblio.getOrden()%>">&nbsp;
				<strong>Bibliograf&iacute;a:</strong>&nbsp;<input size="100" name="BiblioNombre" maxlength="500" value="<%=pronBiblio.getBiblioNombre()%>"><br><br>
				<input type="submit" class="btn btn-primary" value="Guardar">
			</form>
		</div>
		<table class="table table-striped">
			<thead class="thead-dark">
				<tr class="success">
					<th>#</th>
					<th>Op.</th>
					<th>Bibliografía</th>
					<th>Orden</th>
				</tr>
			</thead>
			<tbody>
<% 			for(aca.pron.spring.PronBiblio biblio : listaBiblio){%>
				<tr>
					<td><%=biblio.getBiblioId()%></td>
					<td>
<%-- 						<a href="borraPronBiblio?CursoCargaId=<%=cursoCargaId%>&BiblioId=<%=biblio.getBiblioId()%>" title="Borrar"><i class="fas fa-trash" ></i></a>&nbsp;&nbsp;&nbsp; --%>
						<a href="javaScript:borrar('<%=cursoCargaId%>','<%=biblio.getBiblioId()%>')" title="Borrar"><i class="fas fa-trash" ></i></a>&nbsp;&nbsp;&nbsp;
						<a href="muestraEditaPronBiblio?CursoCargaId=<%=cursoCargaId%>&BiblioId=<%=biblio.getBiblioId()%>" title="Editar"><i class="fas fa-pencil-alt"></i></a>
					</td>
					<td><%=biblio.getBiblioNombre()%></td>
					<td><%=biblio.getOrden()%></td>
				</tr>
<% 			}%>
			</tbody>
		</table>
		<table class="table table-striped">
			<thead class="thead-dark">
				<tr class="success">
					<th>#</th>
					<th>Referencia</th>
					<th>Bibliografía</th>
				</tr>
			</thead>
			<tbody>
<%
			int row = 0;
			for(MapaNuevoBibliografia biblio : lisBiblio){
				row++;
%>
				<tr>
					<td><%=row%></td>
					<td><%=biblio.getReferencia()%></td>
					<td><%=biblio.getBibliografia()%></td>
				</tr>
<% 			}%>
			</tbody>
		</table>
	</div>
	<script>
	function borrar(CursoCargaId,BiblioId){
		var opcion = confirm("Quieres borrra la bibliografia numero "+BiblioId);
	    if (opcion == true) {
	    	location.href = "borraPronBiblio?CursoCargaId="+CursoCargaId+"&BiblioId="+BiblioId;
		} 
	}
	</script>
</body>