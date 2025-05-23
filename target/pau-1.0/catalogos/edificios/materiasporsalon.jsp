<%@page import="java.util.HashMap"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<%@ page import= "aca.catalogo.CatSalon"%>

<jsp:useBean id="cursoU" scope="page" class="aca.carga.CargaGrupoCursoUtil"/>
<jsp:useBean id="cursoUtil" scope="page" class="aca.plan.CursoUtil"/>

<%
	String edificioId					= request.getParameter("EdificioId")==null?"0":request.getParameter("EdificioId");
	String salonId						= request.getParameter("salonId")==null?"00-000":request.getParameter("salonId");
	String nombre 						= aca.catalogo.SalonUtil.nombreSalon(conEnoc, salonId);
	
	ArrayList <aca.carga.CargaGrupoCurso> lisMaterias	= cursoU.getMateriasSalon(conEnoc, salonId, "");
	HashMap<String, aca.plan.MapaCurso> mapMaterias		= cursoUtil.getAllMapaCursos(conEnoc, "");	
%>

<html>
<body>
<div class="container-fluid">
	<h1><spring:message code="catalogos.edificio.MateriasEnSalon"/>: <%=nombre %> </h1>
	<div class="alert alert-info">
		<a href="salones?EdificioId=<%=edificioId%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<table class="table table-bordered">
		<tr>
			<th>#</th>
			<th><spring:message code="aca.Nombre"/></th>
		</tr>
<%
	int cont 			 = 1;
	String nombreMateria = "";
	for(aca.carga.CargaGrupoCurso materias : lisMaterias){
		
		if(mapMaterias.containsKey(materias.getCursoId())){
			nombreMateria = mapMaterias.get(materias.getCursoId()).getNombreCurso();
		}
%>
		<tr>
			<td><%=cont %></td>
			<td><%=nombreMateria %></td>
		</tr>
<%
		cont++;
	}
%>
	</table>
</div>
</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %> 