<%@ page import= "java.util.List"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp"%>

<script type="text/javascript">
	function graba(){		
		document.forma.submit();
	}
</script>
<%
	String idCurso 					= request.getParameter("idCurso")==null?"0":request.getParameter("idCurso");
	String planId 					= request.getParameter("planId")==null?"0":request.getParameter("planId");
	String cursoNombre				= (String)request.getAttribute("cursoNombre");
	int grabados					= (int)request.getAttribute("grabados");
	int ciclo 						= request.getParameter("ciclo")==null?1:Integer.parseInt(request.getParameter("ciclo"));
	
	List<MapaCurso> lisCursos		= (List<MapaCurso>)request.getAttribute("lisCursos");
	List<String> lisPre				= (List<String>)request.getAttribute("lisPrerrequisitos");	
	
	String aMaterias 	= "";
	for(int i=0;i<lisPre.size();i++) aMaterias+=(String)lisPre.get(i)+",";		
%>
<div class="container-fluid">
	<h2><spring:message code="mapa.materia.Titulo3"/> <small class="text-muted fs-5">( <%=cursoNombre%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="materia?planId=<%=planId%>"><spring:message code="aca.Regresar"/></a>
	</div>
<%	if (grabados > 0){%>
	<div class="alert alert-success">
		<spring:message code="mapa.materia.SeGrabo"/>: <%=grabados%> <%=grabados>1?"prerrequisitos":"prerrequisito"%>
	</div>
<% 	}%>		
	<form name="forma" action="prerrequisito?idCurso=<%=idCurso%>&planId=<%=planId%>&grabar=S&ciclo=<%=ciclo%>" method="post">
	<table class="table table-sm">		
<%		
	int cicloact=0,cicloant=0;
	String color="bgcolor='#F3F3F3'";
	boolean par=false;
	for(MapaCurso materia : lisCursos){			
		cicloact = Integer.parseInt(materia.getCiclo());
		if(cicloact!=cicloant){ %>
		<tr class="table-dark">
			<td><b><%out.print("Semestre: "+cicloact);%></b></td>
			<td><b><spring:message code="aca.Id"/></b></td>
			<td><b><spring:message code="aca.Materia"/></b></td>
		</tr>
<%	}	%>
		<tr>
<%			if (!idCurso.equals(materia.getCursoId())){%>
			<td><input <%if(aMaterias.indexOf(materia.getCursoId())>=0)out.print("checked");%> type="checkbox" name="<%=materia.getCursoId()%>" value="S"/></td>
			<td>
			<%	if (aMaterias.indexOf(materia.getCursoId())>=0){%>
				  <a href="prerrequisito?idCurso=<%=materia.getCursoId()%>&planId=<%=planId%>&ciclo=<%=materia.getCiclo()%>">
			<% 	}%>
		   		<%=materia.getCursoClave()%></a>
		 	</td>
			<td><%=materia.getNombreCurso()%></td>
<% 			}%>
		</tr>
<%
		cicloant=cicloact;
		par=!par;
	}
%>					
	</table>
	<div class="alert alert-info"><input class="btn btn-primary" type="button" name="button" value="<spring:message code="aca.Guardar"/>" onclick='javascript:graba();'/></div>
	</form>
	</div>
</body>
</html>