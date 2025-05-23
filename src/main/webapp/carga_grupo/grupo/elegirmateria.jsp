<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.plan.spring.MapaCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">	
	function Grabar( cursoId ){		
		if(confirm("Would you like to add this subject to the group?")){
			document.frmMateria.Accion.value="1";
			document.frmMateria.CursoId.value= cursoId;
			document.frmMateria.submit();
		}	
	}	
</script>
<%	
	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");
	String cursoCargaId 		= request.getParameter("CursoCargaId");
	String carreraId 			= request.getParameter("CarreraId");		
	String planId 		    	= request.getParameter("PlanId");	
	String planElegir 		    = request.getParameter("PlanElegir");
	
	// Consultar los planes con materias en el grupo
	String planesGrupo			= (String) request.getAttribute("planesGrupo");
	String resultado			= (String) request.getAttribute("resultado");
	
	List<MapaCurso> lisMaterias 			= (List<MapaCurso>)request.getAttribute("lisMaterias");	
	HashMap<String,String> mapaMaterias 	= (HashMap<String,String>)request.getAttribute("mapaMaterias");
%>		
<div class="container-fluid">
	<form action="elegirmateria" method="post" name="frmMateria" target="_self">
	<input type="hidden" name="CursoCargaId" value="<%=cursoCargaId%>">
	<input type="hidden" name="CarreraId" value="<%=carreraId%>">
	<input type="hidden" name="PlanId" value="<%=planId%>">
	<input type="hidden" name="PlanElegir" value="<%=planElegir%>">
	<input type="hidden" name="Accion">
	<input type="hidden" name="CursoId">
	
	<h2>Select a Plan<small class="text-muted fs-5">(Plan:<%=planElegir%>)</small></h2>	
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="elegirplan?CursoCargaId=<%=cursoCargaId%>&CarreraId=<%=carreraId%>&PlanId=<%=planId %>"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;	
		<input type="text" class="search-query form-control" placeholder="Search..." id="buscar" style="width:200px;">
	</div>
	</form>
<%	
	if (!resultado.equals("-")){
		out.println("<div class='alert'>"+resultado+"</div>");
	} 
%>	
	<table id="table" class="table table-bordered">
<%
	String ciclo = "X";	
	int row=0;
	for (MapaCurso curso : lisMaterias){
		row++;
		if (!curso.getCiclo().equals(ciclo)){
			ciclo = curso.getCiclo();			
			row=1;
%>			
		<thead>			
		<tr class="alert alert-info"> 
			<td colspan="7"><h4>Cycle <%=curso.getCiclo()%></h4></td>
		</tr>
		<tr>			
			<td width="5%"><h5>#</h5></th>
			<td width="5%"><h5>Joined?</h5></td>
			<td width="10%"><h5><spring:message code="aca.Clave"/></h5></td>
			<td width="50%"><h5><spring:message code="aca.Materia"/></h5></td>
			<td width="10%"><h5><spring:message code="aca.Creditos"/></h5></td>
			<td width="10%"><h5><spring:message code="aca.Tipo"/></h5></td>
			<td width="10%"><h5>Mandatory</h5></td>
		</tr>	
		</thead>		
<%		
		}
		String esDelGrupo = "NO";
		if (mapaMaterias.containsKey(curso.getCursoId())){
			esDelGrupo = "<span class='badge bg-success'>YES</span>";
		}
%>				
			
			<tr>	
				<td align="center"><%=row%></td>
				<td align="center"><%=esDelGrupo%></td>
				<td align="center"><%=curso.getCursoClave()%></td>
				<td>
<%
	if(!planesGrupo.contains(planElegir)){	
%>				
					<a href="javascript:Grabar('<%=curso.getCursoId()%>')">
						<%=curso.getNombreCurso()%>
<% 
	}else{
		out.print(curso.getNombreCurso());
	}
%>					</a>
					
				</td>
				<td align="center"><%=curso.getCreditos() %></td>									
				<td align="center"><%=curso.getTipoCursoId()%></td>
				<td align="center"><%=curso.getObligatorio()%></td>
			</tr>
<%	
	}
%>			
	</table>		
</div>
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>