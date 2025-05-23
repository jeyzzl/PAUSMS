<%@ include file= "id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.exa.spring.ExaDireccion"%>
<%@page import="aca.exa.spring.ExaPais"%>
<%@page import="aca.exa.spring.ExaEstado"%>
<%@page import="aca.exa.spring.ExaTelefono"%>
<%@page import="aca.exa.spring.ExaCorreo"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<html>
	<head></head>
<%
	String paisId 	= (String) request.getAttribute("paisId"); 

	String estadoId = (String) request.getAttribute("estadoId"); 

	List<ExaDireccion> listaExa 	= (List<ExaDireccion>) request.getAttribute("listaExa"); 
	List<ExaPais> listaPaises 		= (List<ExaPais>) request.getAttribute("listaPaises");
	List<ExaEstado> listaEstados 	= (List<ExaEstado>) request.getAttribute("listaEstados");
	
	HashMap<String, ExaPais> mapPaises 			= (HashMap<String, ExaPais>) request.getAttribute("mapPaises");
	HashMap<String, ExaEstado> mapaEstados 		= (HashMap<String, ExaEstado>) request.getAttribute("mapaEstados");
	HashMap<String, ExaTelefono> mapaTelefono 	= (HashMap<String, ExaTelefono>) request.getAttribute("mapaTelefono");
	HashMap<String, ExaCorreo> mapaCorreo 		= (HashMap<String, ExaCorreo>) request.getAttribute("mapaCorreo");
	HashMap<String, AlumPersonal> mapaEgreso 	= (HashMap<String, AlumPersonal>) request.getAttribute("mapaEgreso");
%>
	<body>
	<div class="container-fluid">
	<h2>Reporte de exalumnos</h2>
	<form action="paisEdo">  
	<div class="alert alert-info d-flex align-items-center">		
		<a class="btn btn-primary" href="menu"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
		<b>País: </b>
		<select name="PaisId" class="form-select" onchange="submit();" style="width:200px;">
			<option value="T">Todos</option>						
		<%	for(ExaPais pais : listaPaises){%>
			<option value="<%=pais.getPaisId()%>" <%=pais.getPaisId().equals(paisId)?"Selected":""%>><%=pais.getPaisNombre()%></option>	
		<%	}%>
		</select>&nbsp;
		<b><spring:message code="aca.Estado"/>: </b>
		<select name="EstadoId" onchange="submit();" class="form-select" style="width:200px;">
			<option value="T">Todos</option>				
		<%	for(ExaEstado estado : listaEstados){%>
			<option value="<%=estado.getEstadoId()%>" <%=estado.getEstadoId().equals(estadoId)?"Selected":""%>><%=estado.getNombreEstado()%></option>	
		<%	}%>
		</select>		
	</div>
	</form>
	<table class="table table-sm table-striped table-bordered" >
		<tr class="table-info">
			<th width="3%"><spring:message code="aca.Numero"/></th>
			<th><spring:message code="aca.Matricula"/></th>
			<th width="25%"><spring:message code="aca.Nombre"/></th>
			<th><spring:message code="aca.Pais"/></th>
			<th><spring:message code="aca.Estado"/></th>
			<th><spring:message code="aca.Ciudad"/></th>
			<th width="20%"><spring:message code='aca.Direccion'/></th>
			<th width="11%">CP</th>
			<th width="1%"><spring:message code="aca.Correo"/></th>
			<th><spring:message code="aca.Telefono"/></th>
		</tr>
		<%	int cont = 0;
			for(ExaDireccion exalumno : listaExa){
				cont++;
				AlumPersonal alumPersonal = new AlumPersonal();
				if(mapaEgreso.containsKey(exalumno.getMatricula())){
					alumPersonal = mapaEgreso.get(exalumno.getMatricula());
				}
				ExaTelefono exaTelefono = mapaTelefono.containsKey(exalumno.getMatricula())?mapaTelefono.get(exalumno.getMatricula()):new ExaTelefono();
				ExaCorreo exaCorreo = mapaCorreo.containsKey(exalumno.getMatricula())?mapaCorreo.get(exalumno.getMatricula()):new ExaCorreo();%>
				<tr>
					<td style="text-align:center;"><%=cont%></td>
					<td style="text-align:center;"><%=exalumno.getMatricula()%></td>
					<td><%=alumPersonal.getNombreLegal()%></td>
					<td><%=mapPaises.get(exalumno.getPaisId())==null?"-":mapPaises.get(exalumno.getPaisId()).getPaisNombre()%></td>
					<td><%=mapaEstados.get(exalumno.getPaisId()+exalumno.getEstadoId())==null?"-":mapaEstados.get(exalumno.getPaisId()+exalumno.getEstadoId()).getNombreEstado()%></td>
					<td><%=(exalumno.getCiudad()==null||exalumno.getCiudad().toLowerCase().equals("null"))?"-":exalumno.getCiudad()%></td>
					<td><%=exalumno.getDireccion()==null?"-":exalumno.getDireccion()%></td>
					<td><%=(exalumno.getCp()==null||exalumno.getCp().toLowerCase().equals("null"))?"-":exalumno.getCp()%></td>
					<td><%=exaCorreo.getCorreo().equals("")?"-":exaCorreo.getCorreo()%></td>
					<td><%=exaTelefono.getTelefono().equals("")?"-":exaTelefono.getTelefono()%></td>
				</tr>
		<%	}%>
		</table>
	</div>
	</body>
</html>