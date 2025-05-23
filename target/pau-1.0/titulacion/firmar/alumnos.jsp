<%@ page import= "java.util.ArrayList"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.tit.spring.TitAlumno"%>
<%@ page import= "aca.tit.spring.TitTramite"%>
<%@ page import= "aca.tit.spring.TitFirma"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<head></head>
<%	 
	String institucion 				= request.getParameter("Institucion")==null?"UM":request.getParameter("Institucion");
	String tramite 					= request.getParameter("Tramite")==null?"0":request.getParameter("Tramite");
	String estado 					= request.getParameter("Estado")==null?"A":request.getParameter("Estado");
	
	TitTramite titTramite 				= (TitTramite) request.getAttribute("titTramite");
	ArrayList<TitAlumno> lisAlumnos		= (ArrayList<TitAlumno>)request.getAttribute("lisAlumnos");
	ArrayList<TitFirma> lisFirmas		= (ArrayList<TitFirma>)request.getAttribute("lisFirmas");
	HashMap<String,String> mapAlumnos 	= (HashMap<String,String>)request.getAttribute("mapAlumnos");
%>
<body>
<div class="container-fluid">
	<h2>Alumnos <small class="text-muted fs-4">(<%=titTramite.getDescripcion()%>)</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="tramite?Institucion=<%=institucion%>"><i class="fas fa-arrow-left"></i></a>&nbsp;	
	</div>
	<table class="table table-sm table-bordered">  
		<thead class="table-info">
		<tr>
			<th width="5%">#</th>
			<th width="25%">Alumno</th>
			<th width="60%">Plan</th>
			<th width="10%">Fecha</th>
			<th width="10%">Estado</th>	
<%
	for (TitFirma firma : lisFirmas){
%>			
		<th width="10%" class="center"><%=firma.getCodigoPersonal()%></th>
<%	} %>
		</tr>
		</thead>
		<tbody>
<%
		int row = 0;
		for (TitAlumno titulo : lisAlumnos){
			row++;	
%>
		<tr>
			<td><%=row%></td>
			<td><%=titulo.getCodigoPersonal()%> 
<%			if(mapAlumnos.containsKey(titulo.getCodigoPersonal())){ %> 
				<%=mapAlumnos.get(titulo.getCodigoPersonal())%>
<%			} %>
			</td>
			<td><%=titulo.getPlanId()%></td>
			<td><%=titulo.getFecha()%></td>
			<td><%=titulo.getEstado()%></td>
<%
	for (TitFirma firma : lisFirmas){
		if(!titulo.getXml().contains(firma.getSello())){
%>			
			<th width="10%" class="center"><i class="fas fa-check"></i></th>
<%		}else{%>
			<th width="10%" class="center"><i class="fas fa-trash-alt"></i></th>
<%		}
	} %>
		</tr>
<%		
	}
%>	
		</tbody>
	</table>	
</div>
</body>
</html>