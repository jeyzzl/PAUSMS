<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.plan.spring.MapaAvance"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>
<%@ page import= "aca.catalogo.spring.CatTipoCurso"%>
<%@ page import= "aca.plan.spring.MapaCredito"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript" >

	function cambioCiclo(){
		var planId 			= document.getElementById("planId").value;
		var facultadId 		= document.getElementById("facultad").value;
		var ciclo 			= document.getElementById("ciclo").value;
		var tipo			= document.getElementById("tipo").value;
		location.href = "add?planId="+planId+"&facultad="+facultadId+"&ciclo="+ciclo+"&tipo="+tipo;
	}
	
</script>
<%
	String planId 			= request.getParameter("planId")==null?"":request.getParameter("planId");
	String facultadId		= request.getParameter("facultad")==null?"":request.getParameter("facultad");
	String cicloPlan		= request.getParameter("ciclo")==null?"0":request.getParameter("ciclo");	
	String tipoCurso		= request.getParameter("tipo")==null?"0":request.getParameter("tipo");	
	String msj 				= "";
	MapaAvance mapaAvance 	= (MapaAvance) request.getAttribute("mapaAvance");
	String facultadNombre 	= (String) request.getAttribute("facultadNombre");
	
	List<CatTipoCurso> lisTipos = (List<CatTipoCurso>) request.getAttribute("lisTipos");	
	List<MapaCredito> lisCiclos = (List<MapaCredito>) request.getAttribute("lisCiclos");
	List<MapaCurso> lisMaterias = (List<MapaCurso>) request.getAttribute("lisMaterias");
	HashMap<String, String> mapaCreditos = (HashMap<String, String>) request.getAttribute("mapaCreditos");
%>
<div class="container-fluid">
<h1><%= facultadNombre %><small class="text-muted fs-4"> ( <%= planId %> )</small></h1>
<%-- 	<h2><%=planId %></h2> --%>
	<form action="grabar" method="post" name="forma" target="_self">
		<div class="alert alert-info">
			<a href="avance?planId=<%=planId %>&facultad=<%=facultadId%>" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
			<%
			String mandatorios 	= "0";
			String electivos	= "0";
			if(mapaCreditos.containsKey("1")){
				mandatorios = mapaCreditos.get("1");
			}
			if(mapaCreditos.containsKey("2")){
				electivos = mapaCreditos.get("2");
			}
			
			%>
			Current credits in <i><%= planId %></i>:&nbsp;&nbsp;<b>Mandatory</b> - <%= mandatorios %>&nbsp;&nbsp;<b>Elective</b> - <%= electivos %>
		</div>			
			<input name="planId" id="planId" type="hidden" value=<%=planId %>>
			<input name="facultad" id ="facultad" type="hidden" value=<%=facultadId %>>
			<div class="d-flex justify-content-start">
				<div class="me-3">
					<label for="ciclo"><strong>Cycle</strong></label>
					<select name="ciclo"  class="form-select"  style="width:150px;" id="ciclo" onchange="javascript:cambioCiclo();">
						<%
							for(MapaCredito ciclo: lisCiclos){
						%>
							<option value="<%=ciclo.getCiclo() %>" <% if(ciclo.getCiclo().equals(mapaAvance.getCiclo()))out.print("selected");%>><%=ciclo.getCiclo() %></option>
						<%
							}
						%>
					</select>
				</div>
				<div class="mx-3">
					<label for="tipo"><strong><spring:message code="aca.Tipo"/></strong></label>
					<select name="tipo" class="form-select" style="width:150px;" id="tipo">
					<%
						for(CatTipoCurso tipo: lisTipos){
					%>
							<option value="<%=tipo.getTipoCursoId() %>" <% if(tipo.getTipoCursoId().equals(mapaAvance.getTipocursoId()))out.print("selected");%>><%=tipo.getNombreTipoCurso() %></option>
					<%
						}
					%>
					</select>
				</div>
				<div class="mx-3">
					<label for="creditos"><strong>Credits</strong></label>
					<input type="text" id="creditos"  class="form-control" style="width:150px;"name="creditos" maxlength="6"  class="input-small" value="<%=mapaAvance.getCreditos() %>" />
				</div>
			</div>
			<br>
			<div class="alert alert-info" >
				<a  class="btn btn-primary" onclick="javascript:guardar();"><spring:message code="aca.Guardar"/></a>
			</div>
	</form>
	<h4>Subjects in cycle <%=mapaAvance.getCiclo() %></h4>
	<table class="table table-bordered">
		<thead class="table-info">
			<tr>
				<th>#</th>
				<th>Subject Name</th>
				<th>Credits</th>
				<th>Mandatory</th>
				<th>Cycle</th>
			</tr>
		</thead>
		<tbody>
<%
			int count = 0;
			for(MapaCurso materia : lisMaterias){
				count++;
%>
			<tr>
				<td><%=count %></td>
				<td><%=materia.getNombreCurso() %></td>
				<td><%=materia.getCreditos() %></td>
				<td><%=materia.getObligatorio().equals("S")?"YES":"NO"%></td>
				<td><%=materia.getCiclo() %></td>
			</tr>
<% 			}%>
		</tbody>
	</table>
</div>

<script>
	function guardar(){
		if(document.forma.creditos.value==""){
			alert("All fields are required");
		}else{			
			document.forma.submit();
		}
	}
</script>