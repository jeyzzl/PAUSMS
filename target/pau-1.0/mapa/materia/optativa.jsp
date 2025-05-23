<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import="aca.plan.spring.MapaCursoElectiva"%>
<%@ page import="aca.plan.spring.MapaCurso"%>
<%@ page import="aca.catalogo.spring.CatTipoCurso"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<% 	
	String cursoId			=  (String)request.getAttribute("cursoId");
	String sem				=  (String)request.getAttribute("sem");
	String planId			=  (String)request.getAttribute("planId");
	String materiaNombre	=  (String)request.getAttribute("materiaNombre");
	
	String mensaje			= (String)request.getAttribute("Mensaje");

	List<MapaCursoElectiva> lisCursoElec		=  (List<MapaCursoElectiva>)request.getAttribute("lisCursoElec");
	
	HashMap<String,MapaCurso> mapaCursosPlan 	= (HashMap<String,MapaCurso>)request.getAttribute("mapaCursosPlan");
	HashMap<String,CatTipoCurso> mapaTipos 		= (HashMap<String,CatTipoCurso>)request.getAttribute("mapaTipos");
	
%>
<div class="container-fluid">
	<h2>Elective Subjects <small class="text-muted fs-5">&nbsp;<%=materiaNombre%></small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="materia?planId=<%=planId%>"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
		<%-- <a class="btn btn-primary" href="agregar?Plan=<%=planId%>&Semestre=<%=sem%>&Curso=<%=cursoId%>">Add from Plan</a>&nbsp;&nbsp; --%>
		<a class="btn btn-primary" href="accion_op?Accion=1&PlanId=<%=planId%>&Semestre=<%=sem%>&CursoId=<%=cursoId%>"><spring:message code="aca.Anadir"/></a>
	</div>	
	
	<form name="opta" method="post" action="optativa?Curso=<%=cursoId%>&Plan=<%=planId%>&Semestre=<%=sem%>">
	<input name="Accion" type="hidden" value = "1">
  	<table class="table table-sm table-bordered">
  	<tr class="table-info"> 
	    <th width="5%"><spring:message code="aca.Operacion"/></th>
   		<th width="5%"><spring:message code="aca.Numero"/></th>
   		<th width="10%">Cycle</th>
    	<th width="60%"><spring:message code="aca.Materia"/></th>
   		<th width="20%">Type</th>
  	</tr>
  <%
    for (MapaCursoElectiva elec : lisCursoElec){
		String materia 	= elec.getCursoNombre();
		String semestre = "0";
		String tipoNombre = "-";
		if(mapaCursosPlan.containsKey(elec.getCursoId())){
			semestre = mapaCursosPlan.get(elec.getCursoId()).getCiclo();
			
			if(elec.getCursoNombre() == null){
				materia = mapaCursosPlan.get(elec.getCursoId()).getNombreCurso();
			}
			if (mapaTipos.containsKey(mapaCursosPlan.get(elec.getCursoId()).getTipoCursoId())){
				tipoNombre = mapaTipos.get(mapaCursosPlan.get(elec.getCursoId()).getTipoCursoId()).getNombreTipoCurso();								
			}
		}
%>
  	<tr> 
    	<td align="center">
    <% if(elec.getCursoNombre()!=null){ %>
      		<a href="accion_op?Folio=<%=elec.getFolio()%>&CursoId=<%=elec.getCursoId()%>&PlanId=<%=planId%>&Semestre=<%=sem%>"> 
      			<i class="fas fa-edit"></i> 
      		</a>
      <% }%>
      		<a href="borrarOptativa?Folio=<%=elec.getFolio()%>&CursoId=<%=elec.getCursoId()%>&PlanId=<%=planId%>&Semestre=<%=sem%>"> 
				<i class="fas fa-trash-alt"></i>      		
			</a>
      	</td>
    	<td align="center"><%=elec.getFolio()%></td>
    	<td><%=semestre%></td>
    	<td><%=materia %></td>
    	<td><%=tipoNombre %></td>
  	</tr>
  <%
	}	
%>
	</table>
	</form>
</div>	