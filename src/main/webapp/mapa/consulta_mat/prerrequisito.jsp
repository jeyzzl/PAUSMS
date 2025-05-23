<%@ page import= "java.util.List"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String idCurso 			= request.getParameter("idCurso");
	String planId 			= request.getParameter("planId");
	String facultad 		= request.getParameter("facultad");
	String paso 			= request.getParameter("paso");
	String ciclo			= request.getParameter("ciclo")==null?"1":request.getParameter("ciclo");
	String cursoNombre		= (String)request.getAttribute("cursoNombre");
	String materias			= "";
	
	List<String> lisPrerrequisitos		= (List<String>)request.getAttribute("lisPrerrequisitos");
	List<MapaCurso> lisMaterias			= (List<MapaCurso>)request.getAttribute("lisMaterias");	
	
	for(int i=0;i<lisPrerrequisitos.size();i++) materias += (String)lisPrerrequisitos.get(i)+",";		
%>
<div class="container-fluid">
	<h2><strong>Subject prerequisites: <%=cursoNombre%></strong></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="materia?planId=<%=planId%>&facultad=<%=facultad%>"><spring:message code="aca.Regresar"/></a>
	</div>
	<table style="width:30%" class="table table-sm table-bordered">
	<tr>
		<th>Id</th>
		<th><spring:message code="aca.Materia"/></th>
	</tr>
<%	
	int cicloact	= 0;
	int cicloant 	= 0;	
	boolean par		= false;
	for(MapaCurso materia : lisMaterias){		
		cicloact 	= Integer.parseInt(materia.getCiclo());
		if(cicloact != cicloant){
%>
	<tr><td colspan='3'><br><b><%out.print("Semester: "+cicloact);%></b></td></tr>
<%		}
%>
	<tr>
		<td>
<%		if (materias.indexOf(materia.getCursoId()) >= 0){%>
	 		<a href="prerrequisito?idCurso=<%=materia.getCursoId()%>&planId=<%=planId%>&paso=1&ciclo=<%=materia.getCiclo()%>&facultad=<%=facultad%>">
	  		<span class="badge badge-success"><%=materia.getCursoClave()%></span> 
	  		</a>
<% 		}else{
			out.print(materia.getCursoClave());
		}
%>
	   
	</td>
	<td><%=materia.getNombreCurso()%></td>
</tr>
<%
		cicloant=cicloact;
		par=!par;
	}
%>
</table>
</body>
</div>