<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page import="aca.financiero.spring.FinMaratum"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@ page import="aca.plan.spring.MapaPlan"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp"%>

<html>
<head>
	<script type="text/javascript">
		function CambiaEstado(estado) {
			document.location.href = "listado?Estado="+estado;
		}

		function Borrar(id) {
			if (confirm("¿Estás seguro de borrar está solicitud?")){
				document.location.href = "borrar?Id="+id;
			}		
		}
 	</script>	
</head>
<%		
	String codigoPersonal 		= (String) session.getAttribute("codigoPersonal");
	String estado  				= request.getParameter("Estado") == null ? "'S','A','C'" : request.getParameter("Estado");
	Acceso acceso 				= (Acceso) request.getAttribute("acceso");

	List<FinMaratum> lisSolicitudes		 		= (List<FinMaratum>)request.getAttribute("lisSolicitudes");
	HashMap<String, AlumPersonal> mapaAlumnos	= (HashMap<String, AlumPersonal>)request.getAttribute("mapaAlumnos");
	HashMap<String, MapaPlan> mapaPlanes		= (HashMap<String, MapaPlan>)request.getAttribute("mapaPlanes");
%>
<body>
<div class="container-fluid">
	<h2>Maratum<small class="text-muted fs-4"> (lista de solicitudes)</small></h2>
	<div class="alert alert-info d-flex align-items-center">
		Estado:
		<select name="Estado" onchange="javascript:CambiaEstado(this.value)" class="form-select" style="width:120px">
			<option value="'S','A','C'" <%=estado.equals("'S','A','C'") ? "selected" : ""%>>Todos</option>
			<option value="'S'" <%=estado.equals("'S'") ? "selected" : ""%>>Solicitud</option>
			<option value="'A'" <%=estado.equals("'A'") ? "selected" : ""%>>Autorizado</option>
			<option value="'C'" <%=estado.equals("'C'") ? "selected" : ""%>>No autorizado</option>
		</select>
		&nbsp; &nbsp;
		<a href="editar?Estado=<%=estado%>" class="btn btn-primary"><i class="fas fa-plus"></i> Alta</a>&nbsp;
		<a href="maratum.pdf" target="_blank" class="btn btn-primary"><i class="fas fa-download"></i> Formato Maratum</a>
		&nbsp;Este documento pdf es editable, contiene campos de formulario para rellenar, te recomendamos utilizar Acrobat, Foxit o el programa de tu preferencia.
	</div>
	<table style="width:100%" class="table table-condensed">
	<tr>
		<th>Op.</th>
		<th>#</th>
		<th>Matr&iacute;cula</th>
		<th><spring:message code="aca.Nombre"/></th>
		<th>Plan</th>		
		<th>Fecha</th>
		<th>Usuario</th>
		<th>Estado</th>	
		<th>Archivo</th>		
	</tr>
<%
	int row = 0;
	for (FinMaratum maratum: lisSolicitudes){
		row++;
		
		String nombreAlumno = "";		
		if(mapaAlumnos.containsKey(maratum.getCodigoPersonal())){
			nombreAlumno = mapaAlumnos.get(maratum.getCodigoPersonal()).getNombre()+" "+mapaAlumnos.get(maratum.getCodigoPersonal()).getApellidoPaterno()+" "+mapaAlumnos.get(maratum.getCodigoPersonal()).getApellidoMaterno();
		}		
		
		String nombrePlan	= "";
		String carreraId	= "";
		if(mapaPlanes.containsKey(maratum.getPlanId())){
			nombrePlan 	= mapaPlanes.get(maratum.getPlanId()).getCarreraSe();
			//carreraId	= mapaPlanes.get(alumDocumento.getPlanId()).getCarreraId();
		}
		
		String nombreEstado = "X";		
		if(maratum.getEstado().equals("S")){
			nombreEstado = "Solicitud";
		}else if(maratum.getEstado().equals("A")){
			nombreEstado = "Autorizado";
		}else if(maratum.getEstado().equals("C")){
			nombreEstado = "No autorizado";
		}		
%>
	<tr>
		<td>
<%	if(acceso.getAdministrador().equals("S") || codigoPersonal.equals(maratum.getUsuario()) || acceso.getSupervisor().equals("S") ){%>
			<a href="javascript:Borrar('<%=maratum.getId()%>');"><i class="fas fa-trash-alt"></i></a>
<%	}  %>			
		</td>
		<td><%=row%></td>
		<td><%=maratum.getCodigoPersonal()%></td>
		<td><%=nombreAlumno%></td>
		<td><%=nombrePlan%></td>		
		<td><%=maratum.getFecha()%></td>
		<td><%=maratum.getUsuario()%></td>
		<td>
		<%=nombreEstado%>
<%		if(acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") ){ %>						
			<a href="cambiarEstado?Id=<%=maratum.getId()%>&Estado=S" title="Cambiar estado"><span class="badge <%=maratum.getEstado().equals("S")?"bg-dark":"bg-warning"%>">S</span></a>
			<a href="cambiarEstado?Id=<%=maratum.getId()%>&Estado=A" title="Cambiar estado"><span class="badge <%=maratum.getEstado().equals("A")?"bg-dark":"bg-warning"%>">A</span></a>
			<a href="cambiarEstado?Id=<%=maratum.getId()%>&Estado=C" title="Cambiar estado"><span class="badge <%=maratum.getEstado().equals("C")?"bg-dark":"bg-warning"%>">C</span></a>
<%		}else{ 			
			out.print("<span class=\"badge bg-dark\">"+maratum.getEstado()+"</span>");		
		}	
%>
		</td>
		<td>
			<a href="bajarArchivo?Id=<%=maratum.getId()%>" title="Descargar"><i class="fas fa-download"></i> <%=maratum.getNombre()%></a>
		</td>		
	</tr>
<%	
	}
%>
	</table>
</div>
</body>
</html>
