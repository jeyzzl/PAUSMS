<%@ page import="java.util.List" %>
<%@page import="java.util.HashMap"%>
<%@page import="java.text.*" %>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.sep.spring.SepAlumno"%>
<%@page import="aca.conva.spring.ConvEvento"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.acceso.spring.Acceso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<%@ include file= "menu.jsp" %>
<%
	Acceso acceso 					= (Acceso) request.getAttribute("acceso");
	String codigoAlumno 			= (String) session.getAttribute("codigoAlumno");
	String mensaje 					= (String) request.getAttribute("mensaje");
	AlumPersonal alumPersonal 		= (AlumPersonal)request.getAttribute("alumPersonal");
	List<SepAlumno> listSep			= (List<SepAlumno>) request.getAttribute("listSep");	
	List<String> listaFechas	 	= (List<String>) request.getAttribute("listaFechas");	
	int cont = 0;
%>
<div class="container-fluid">		
	<h3>Registrado en Secretaría de Educación<small class="text-muted fs-6"> ( <%=codigoAlumno%> | <%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoPaterno() %> <%=alumPersonal.getApellidoMaterno()%> )</small></h3>
	<form name="form" action="modificarCiclo" method="post">
		<div class="d-flex alert alert-info">
			<a class="btn btn-primary btn-sm" href="calificaciones">Regresar</a>&nbsp;&nbsp;
<%	 	if(acceso.getCodigoPersonal().equals("9800308")){%>
			Editar ciclo en:
			<select style="width: 200px;" class="form-select" name="Fecha">
<%	 		for(String fecha : listaFechas){%>
				<option><%=fecha%></option>
<%	 		}%>
			</select>&nbsp;&nbsp;
			<button class="btn btn-primary" type="submit">Editar</button>
<%	 	}%>
		</div>
	</form>
<%	 	if(mensaje.equals("1")){%>
		<div class="alert alert-success">
			Editado
		</div>
<%	 	}%>
	<table class="table table-sm">
		<tr>
			<th>N°</th>
<% 		if(acceso.getAdministrador().equals("S") ){%>
			<th>Editar</th>
<% 		}%>
			<th>Curp</th>
			<th>Nombre</th>
			<th>Ape. Pat.</th>
			<th>Ape. Mat.</th>
			<th>Fecha</th>
			<th>Plan SEP</th>
			<th>Plan UM</th>
			<th>Grado</th>
			<th>Ciclo</th>
		</tr>
<%     for(SepAlumno datos : listSep){
	   	cont++;
	   	String[]fe = datos.getFecha().substring(0,10).split("-");	 
	   	String fecha = fe[2]+"/"+fe[1]+"/"+fe[0];
%>
		<tr>
			<td><%=cont%></td>
<% 		if(acceso.getAdministrador().equals("S") ){%>
			<td><a class="btn btn-primary" href="sepAlumnoEditar?folio=<%=datos.getFolio()%>"><i class="fas fa-edit icon-white"></i></a></td>
<% 		}%>
			<td><%=datos.getCurp()%></td>
			<td><%=datos.getNombre()%></td>
			<td><%=datos.getPaterno()%></td>
			<td><%=datos.getMaterno()%></td>
			<td><%=fecha%></td>
			<td><%=datos.getPlanSep()%></td>
			<td><%=datos.getPlanUm()%></td>
			<td><%=datos.getGrado()%></td>
			<td><%=datos.getCiclo()%></td>
		</tr>
<%      }%>
	</table>
</div>
