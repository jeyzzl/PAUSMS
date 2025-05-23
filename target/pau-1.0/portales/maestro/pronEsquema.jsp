<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>

<%
	aca.pron.spring.PronEsquema pronEsquema = (aca.pron.spring.PronEsquema)request.getAttribute("pronEsquema");
	List<aca.pron.spring.PronEsquema> listaEsquema = (List<aca.pron.spring.PronEsquema>)request.getAttribute("listaEsquema");
	
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
			<a class="btn btn-success" href="enviarCursoCargaId?CursoCargaId=<%=cursoCargaId%>&Origen=pronEsquema">Enviar</a>
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
					    <li class="nav-item nav-link active"><a href="#home" aria-controls="home" role="tab" data-bs-toggle="tab">Esquema <%if(completoEsquema.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li>
					    <li class="nav-item nav-link"><a href="pronBiblio?CursoCargaId=<%=cursoCargaId%>">Bibliografía <%if(completoBiblio.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li> 
					</ul>
				</div>
			</div>
		</nav>
		<form action="grabaPronEsquema" method="POST">
		<input type="hidden" name="CursoCargaId" value="<%=cursoCargaId%>">
		<div class="alert alert-info">						
			&nbsp;&nbsp;<strong>Estrategia:
			</strong>&nbsp;<input type="text" class="" size="60" name="EstrategiaNombre" value="<%=pronEsquema.getEstrategiaNombre()%>">				
			&nbsp;&nbsp;<strong>Valor:
			</strong><input type="text" size="1" maxlength="3" name="Valor" value="<%=pronEsquema.getValor()%>">				
			&nbsp;&nbsp;<strong>Tipo:
			</strong>
			<select name="Tipo" id="Tipo" class="input input-medium">				
				<option value="P" <%=pronEsquema.getTipo().equals("P")?"Selected":""%>>Porcentaje</option>
				<option value="V" <%=pronEsquema.getTipo().equals("V")?"Selected":""%>>Valor</option>
			</select>
			&nbsp;&nbsp;
			<strong>Orden: </strong><input type="text" size="1" maxlength="2" name="Orden" value="<%=pronEsquema.getOrden()%>">
			&nbsp;&nbsp;
			<input type="submit" class="btn btn-primary" value="Guardar">				
		</div>		
		</form>
		<table class="table table-striped">
			<thead class="thead-dark">
				<tr class="success">
					<th style="text-align:center">#</th>
					<th>Estrategia</th>
					<th style="text-align:center">Valor</th>
					<th>Tipo</th>
					<th style="text-align:center">Orden</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
<% 		for(aca.pron.spring.PronEsquema esquema : listaEsquema){
				String tipo = "";
				if(esquema.getTipo().equals("P")){
					tipo = "Porcentaje";
				}else{
					tipo = "Valor";
				}
%>
				<tr>
					<td style="text-align:center"><%=esquema.getEstrategiaId()%></td>
					<td><%=esquema.getEstrategiaNombre()%></td>
					<td style="text-align:center"><%=esquema.getValor()%></td>
					<td><%=tipo%></td>
					<td style="text-align:center"><%=esquema.getOrden()%></td>
					<td style="text-align:center"><a href="borraPronEsquema?CursoCargaId=<%=cursoCargaId%>&EstrategiaId=<%=esquema.getEstrategiaId()%>" title="Eliminar"><i class="fas fa-trash" ></i></a></td>
				</tr>
<% 			}%>
			</tbody>
		</table>
	</div>
</body>