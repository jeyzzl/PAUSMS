<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.TreeMap"%>
<%@page import="aca.carga.spring.CargaGrupoCurso"%>
<%@page import="aca.plan.spring.MapaCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	// Declaracion de variables	
	String cursoCargaId 	= (String)session.getAttribute("cursoCargaId");
	String carreraId 		= (String)session.getAttribute("carreraId");	
	String planId 		    = (String)session.getAttribute("planId");
	String mensaje 			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	String nombreMateria 	= (String) request.getAttribute("nombreMateria");
	
	// Lista de materias en el grupo
	List<CargaGrupoCurso> lisMaterias		    = (List<CargaGrupoCurso>) request.getAttribute("lisMaterias");
	
	HashMap<String,MapaCurso> mapaCursos		= (HashMap<String, MapaCurso>) request.getAttribute("mapaCursos");
	HashMap<String,String> mapaPlan				= (HashMap<String,String>)request.getAttribute("mapaPlan");
	HashMap<String,String> mapPlanId			= (HashMap<String,String>)request.getAttribute("mapPlanId");
	HashMap<String,String> mapaPorCurso			= (HashMap<String,String>) request.getAttribute("mapaPorCurso");

%>
<head>
<script type="text/javascript">	s
	function Borrar( cursoCargaId, cursoId, carreraId, planId ){
		if(confirm("Are you sure you want to delete the subject:"+cursoId+"?")==true){
			document.location.href="borrarEquivalente?CursoCargaId="+cursoCargaId+"&CursoId="+cursoId+"&CarreraId="+carreraId+"&PlanId="+planId;
		}	
	}	
</script>
</head>
<div class="container-fluid">
	<h2><spring:message code="cargasGrupos.grupo.Titulo4" /> <small class="text-muted fs-5">( <%=cursoCargaId%> - <%=nombreMateria%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="grupo?CarreraId=<%=carreraId%>&PlanId=<%=planId %>"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		<a class="btn btn-primary" href="elegirplan?CursoCargaId=<%=cursoCargaId%>">Join Subject</a>
	</div>
<%	if (!mensaje.equals("-")){%>
	<div class="alert alert-info"><%=mensaje%></div>
<%	}%>	
	<table class="table table-bordered">
	<thead class="table-info">
	<tr> 
  		<th width="3%"><spring:message code="aca.Op"/></th>    	
    	<th width="25%">Plan</th>
		<th width="14%"><spring:message code="aca.Clave"/></th>
	    <th width="30%"><spring:message code="aca.Nombre"/></th>
		<th width="5%"><spring:message code="aca.Tipo"/></th>
		<th width="5%" title="Weekly Instances">Weekly Instances</th>		
		<th width="5%"><spring:message code="aca.Horario"/></th>
		<th width="5%"><spring:message code="aca.Salon"/></th>
		<th width="5%" class="text-center">#Stud.</th>
	</tr>
	</thead>
<%	
	for (CargaGrupoCurso materia : lisMaterias){
	
		String nombreCurso 	= "";
		String ht			= "0";
		String hp			= "0";
		String horario		= "-";
		String salon 		= "-";
		if (mapaCursos.containsKey(materia.getCursoId()) ){			
			nombreCurso = mapaCursos.get(materia.getCursoId()).getNombreCurso();
			ht			= mapaCursos.get(materia.getCursoId()).getHt();
			hp			= mapaCursos.get(materia.getCursoId()).getHp();
			horario 	= mapaCursos.get(materia.getCursoId()).getHorario();
			salon 		= mapaCursos.get(materia.getCursoId()).getSalon();
		}
		
		String plan = "";
		if(mapPlanId.containsKey(materia.getCursoId())){
			plan = mapPlanId.get(materia.getCursoId());
		}
		
		String alumnos = "<span class='badge bg-secondary rounded-pill'>0</span>";
		if(mapaPorCurso.containsKey(materia.getCursoId())){
			alumnos = "<span class='badge bg-dark rounded-pill'>"+mapaPorCurso.get(materia.getCursoId())+"</span>";
		}
		
		String nombrePlan = "-";
		if(mapaPlan.containsKey(plan)){
			nombrePlan = mapaPlan.get(plan);
		}
%>
    <tr> 
	  <td height="18" align="center">
	  <% if (!mapaPorCurso.containsKey(materia.getCursoId())){ %>
	  	<a href="javascript:Borrar('<%=cursoCargaId%>','<%=materia.getCursoId()%>','<%=carreraId%>','<%=planId%>')"><img title="Eliminar" src="../../imagenes/no.png" title="Eliminar"/></a>	  
	  <% }%>
	  </td>      
      <td>&nbsp;<%=nombrePlan%></td>
	  <td height="18"><%=materia.getCursoId()%></td>
      <td>&nbsp;<%=nombreCurso%></td>
	  <td>&nbsp;<%=materia.getOrigen().equals("O")?"Original":"Annexed"%></td>
	  <td><%=Integer.parseInt(ht)+Integer.parseInt(hp) %></td>	  
	  <td><%=horario.equals("S")?"Yes":"No"%></td>
	  <td><%=salon.equals("S")?"Yes":"No"%></td>
	  <td class="text-center"><%=alumnos%></td>
    </tr>
<%
	}
%>		
	</table>
</div>
</body>