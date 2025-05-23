<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.diploma.spring.DipCurso"%>
<%@page import="aca.diploma.spring.DipAlumno"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	
	String diplomaId				= request.getParameter("DiplomaId")==null?"0":request.getParameter("DiplomaId");
	String curso					= (String)request.getAttribute("curso");
	
	List<DipAlumno> lisAlumnos 				= (List<DipAlumno>) request.getAttribute("lisAlumnos");	
	HashMap<String,String> mapaValidados 	= (HashMap<String,String>)request.getAttribute("mapaValidados");
%>
<script>
	function Borrar(diplomaId, codigoPersonal){
		if (confirm("¿Estás seguro de borrar este registro?")){
			document.location.href="borrarAlumno?DiplomaId="+diplomaId+"&CodigoPersonal="+codigoPersonal;
		}
	}
</script>
<body>
<div class="container-fluid">
	<h2><a href="listado"><i class="fas fa-arrow-left"></i></a>&nbsp;
	Lista de Alumnos<small class="text-muted fs-5"> ( <%=curso%> )</small></h2>
	<hr>
	<form name="frmMatriculas" action="alumnos" method="post">
	<input type="hidden" name="DiplomaId" value="<%=diplomaId%>"/>
	<div class="alert alert-info d-flex align-items-center">
		Matrículas:&nbsp;	
		<textarea class="form-control" name="Codigos" rows="1" cols="200" style="width:300px"></textarea>&nbsp; &nbsp;
		<button class="btn btn-primary" type="submit"><i class="fas fa-save"></i> Matrículas</button>&nbsp; &nbsp;
		<a href="javascript:document.frmDatos.submit();" class="btn btn-primary"><i class="fas fa-save"></i> Tabla</a>&nbsp; &nbsp;
		<a href="publicar?DiplomaId=<%=diplomaId%>" class="btn btn-success"><i class="fas fa-certificate"></i> Publicar</a>
	</div>
	</form>
	<form name="frmDatos" action="grabarDatos?DiplomaId=<%=diplomaId%>" method="post">
	<table class="table table-sm table-bordered">
	<thead>
	<tr>
		<th>Op.</th>
		<th>Clave</th>
		<th>Matrícula</th>
		<th>Nombre</th>
		<th>Publicado</th>
	</tr>
	</thead>
	<tbody>
<%	
	int row = 0;
	for(DipAlumno alumno : lisAlumnos){
		String publicado = "NO";
		if (mapaValidados.containsKey(alumno.getDiplomaId()+alumno.getCodigoPersonal())){
			publicado = "SI";
		}
%>			
	<tr>
		<td>
		<%  if(publicado.equals("NO")){ %>
			<a href="javascript:Borrar('<%=alumno.getDiplomaId()%>','<%=alumno.getCodigoPersonal()%>')"><i class="fas fa-trash-alt"></i></a>
		<%	}%>
			&nbsp;
			<a href="diplomaPdf?DiplomaId=<%=alumno.getDiplomaId()%>&CodigoPersonal=<%=alumno.getCodigoPersonal()%>"><i class="fas fa-file-pdf"></i></a>
		</td>
		<td><%=alumno.getDiplomaId()%></td>
		<td>
			<%=alumno.getCodigoPersonal()%>
			<input name="Codigo<%=alumno.getCodigoPersonal()%>" type="hidden" value="<%=alumno.getCodigoPersonal()%>"/>
		</td>
		<td><input name="Nombre<%=alumno.getCodigoPersonal()%>" class="form-control" value="<%=alumno.getNombre()%>" style="width:400px"/></td>
		<td><%=publicado%></td>
	</tr>
<%	}%>
	</tbody>
	</table>
	</form>
</div>
</body>
</html>