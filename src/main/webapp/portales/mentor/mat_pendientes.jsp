<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="aca.plan.spring.MapaCurso"%>
<%@ page import="aca.vista.spring.PlanCiclo"%>
<%@ page import="aca.catalogo.spring.CatTipoCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	
	String codigoPersonal		= request.getParameter("matricula")==null?"0":request.getParameter("matricula");
	String planAlumno			= (String)request.getAttribute("planAlumno");	
	String nombreAlumno			= (String)request.getAttribute("nombreAlumno");		
	int fs 						= 0;	
	
	List<MapaCurso> lisCursos				= (List<MapaCurso>) request.getAttribute("lisCursos");  
	List<PlanCiclo> lisCiclos 				= (List<PlanCiclo>) request.getAttribute("lisCiclos");
	HashMap<String,String> mapaPendientes 	= (HashMap<String,String>) request.getAttribute("mapaPendientes");
	HashMap<String,CatTipoCurso> mapaTipos 	= (HashMap<String,CatTipoCurso>) request.getAttribute("mapaTipos");	
%>
<div class="container-fluid">
	<h2>Alumno&nbsp;<small class="text-muted fs-5">( <%=codigoPersonal%>&nbsp;-&nbsp;<%=nombreAlumno%>)</small></h2>
	<div class="alert alert-info">
		Regresar a: &nbsp; <a href="portal" class="btn btn-primary">Portal del mentor</a> &nbsp; &nbsp; <a class="btn btn-primary" href="datosAcademicos?matricula=<%=codigoPersonal%>">Datos académicos</a>	
	</div>
	<table style="margin: 0 auto;" class="table">
  	<tr><th colspan="8">Plan de Estudios [<%= planAlumno %>]</th></tr>
<%
	if (lisCiclos.size() > 0){
		
		for(PlanCiclo planCiclo : lisCiclos){			
			
			int matPen	= 0;
			if (mapaPendientes.containsKey(planCiclo.getCiclo())){
				matPen = Integer.parseInt(mapaPendientes.get(planCiclo.getCiclo()));
			}			
			if (matPen > 0){
%>	
	<tr><td colspan="7" style="font-size:12pt;"><b>Ciclo: <%= planCiclo.getCiclo() %></b></td></tr>
	<tr>	  
	  <th style="background-image:none; border: solid 1px black;"><spring:message code="aca.Clave"/></th>
	  <th style="background-image:none; border: solid 1px black;"><spring:message code="aca.Materia"/></th>
	  <th style="background-image:none; border: solid 1px black;">HT</th>
	  <th style="background-image:none; border: solid 1px black;">HP</th>
	  <th style="background-image:none; border: solid 1px black;">FS</th>
	  <th style="background-image:none; border: solid 1px black;">Cred.</th>
	  <th style="background-image:none; border: solid 1px black;"><spring:message code="aca.Tipo"/></th>
	</tr>
<%	
				int z=0;
				for( MapaCurso curso : lisCursos ){
					z++;
					if (curso.getCiclo().equals(planCiclo.getCiclo())){
						fs = Integer.parseInt(curso.getHt()) + Integer.parseInt(curso.getHp());
						String tipoNombre = "-";
						if (mapaTipos.containsKey(curso.getTipoCursoId())){
							tipoNombre = mapaTipos.get(curso.getTipoCursoId()).getNombreTipoCurso();
						}
%>
	<tr>	  
	  <td><%= curso.getCursoId() %></td>
	  <td><%= curso.getNombreCurso() %></td>
	  <td><%= curso.getHt() %></td>
	  <td><%= curso.getHp() %></td>
	  <td><%= fs %></td>
	  <td><%= curso.getCreditos() %></td>
	  <td><%=tipoNombre%></td>
	</tr>
<%					
					} // si es el mismo ciclo
					if(z==lisCursos.size()){
%>
					<tr><td>&nbsp;</td></tr><%		
					}		
				} // for de cursos
			} // Si el semestre tiene materias pendientes
		} // for de ciclos
	} // si tiene ciclos
%>
</table>