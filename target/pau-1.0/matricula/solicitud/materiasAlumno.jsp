<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.plan.spring.MapaCurso"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp"%>
<html>
<%	
    String cargaId 		= (String) request.getAttribute("cargaId");
	String bloques 		= (String) session.getAttribute("bloques");
    String facultadId   = (String) request.getAttribute("facultadId");

    AlumPersonal alumno                 = (AlumPersonal) request.getAttribute("alumno");
    AlumAcademico academico             = (AlumAcademico) request.getAttribute("academico");

    List<AlumnoCurso> listaMaterias     = (List<AlumnoCurso>) request.getAttribute("listaMaterias");
    HashMap<String,MapaCurso> mapaCurso = (HashMap<String,MapaCurso>) request.getAttribute("mapaCurso");
%>
<body>
<div class="container-fluid">
    <h2>Subjects in Load <small  class="text-muted fs-5">( <%=cargaId%> - <%=alumno.getApellidoPaterno()+" "+alumno.getNombre()%> )</small></h2>
	<form name="frmProceso" action="listado" method="post">	
		<div class="alert alert-info">
			<a href="total?CargaId=<%=cargaId%>&Bloques=<%=bloques%>&FacultadId=<%=facultadId%>" class="btn btn-primary">Return</a>
		</div>	
	</form>
	<table style="width:70%" class="table table-sm table-bordered">
	<thead class="table-info">
		<tr class="noHover">
			<th>#</th>
			<%-- <th>Student ID</th> --%>
			<%-- <th>Name</th> --%>
			<th>Subject Code</th>
			<th>Subject Name</th>						
		</tr>
	</thead>
    <tbody>
<%      	
        int row=0;
        for(AlumnoCurso curso : listaMaterias){
            row++;
            String cursoClave = "";
            if(mapaCurso.containsKey(curso.getCursoId())){
                cursoClave = mapaCurso.get(curso.getCursoId()).getCursoClave();
            }
%>
        <tr>
            <td><%=row%></td>
            <%-- <td><%=alumno.getCodigoPersonal()%></td> --%>
            <%-- <td><%=alumno.getApellidoPaterno()+" "+alumno.getNombre()%></td> --%>
            <td><%=cursoClave%></td>
            <td><%=curso.getNombreCurso()%></td>
        </tr>
<%      } %>
    </tbody>
</div>
</body>
</html>