<%@page import="aca.cred.CredPersonalUtil"%>
<%@page import="aca.cred.CredEventoUtil"%>
<%@page import="aca.matricula.spring.MatEvento"%>
<%@page import="aca.matricula.spring.MatAlumno"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<body>
<%
	String eventoId 	= (String) request.getAttribute("eventoId");
	String estado 		= (String) request.getAttribute("estado");
	String codigoAlumno = (String) request.getAttribute("codigoAlumno");
	String mensaje 		= request.getParameter("Mensaje")==null?"":request.getParameter("Mensaje");

	List<MatEvento> lisEventos = (List<MatEvento>) request.getAttribute("lisEventos");
	List<MatAlumno> lisAlumnos = (List<MatAlumno>) request.getAttribute("lisAlumnos");

	HashMap<String,String> mapaNombreAlumno = (HashMap<String,String>) request.getAttribute("mapaNombreAlumno");
%>
	<div class="container-fluid">
		<h2>ID Card Registration</h2>
		<form name="frmCredencial" action="credencial" method="post">
		<input type="hidden" name="CodigoAlumno" id="CodigoAlumno" value="<%=codigoAlumno%>">
		<div class="alert alert-info d-flex align-items-center">
			<label for="EventoId">Event:</label>
			<select name="EventoId" id="EventoId"  class ="form-control ms-1 me-2" onChange="javascript:cambiaEvento()" style="width:15rem;">
<%			for(MatEvento evento : lisEventos){%>
					<option value="<%=evento.getEventoId()%>" <%=eventoId.equals(evento.getEventoId())?"selected":""%>><%=evento.getEventoNombre()%></option>
<%			}%>
			</select> &nbsp;
			<a href="agregar?EventoId=<%=eventoId%>&CodigoAlumno=<%=codigoAlumno%>" class="btn btn-success"><i class="icon-user icon-white"></i>Add Student</a>
		</div>
<%		if(!mensaje.equals("")){%>
		<div class="alert alert-warning">
			<%=mensaje%>
		</div>
<%		}%>
		</form>	
		<table class="table table-bordered">
			<thead class="table-info">
				<tr>
					<th width="5%"><spring:message code="aca.Numero"/></th>
					<th width="5%"><spring:message code="aca.Operacion"/></th>
					<th width="10%">Student ID</th>
					<th><spring:message code="aca.Nombre"/></th>
					<th width="20%">Photo</th>
				</tr>
			</thead>
			<tbody>
<%		int row = 1;
		for(MatAlumno alumno : lisAlumnos){
			String nombreAlum = "";
			if(mapaNombreAlumno.containsKey(alumno.getCodigoPersonal())){
				nombreAlum = mapaNombreAlumno.get(alumno.getCodigoPersonal());
			}
%>	
			<tr>	
				<td><%=row++%></td>
				<td>
<%				if(alumno.getEstado().equals("I")){%>
					<a href="borrar?CodigoAlumno=<%=alumno.getCodigoPersonal()%>&EventoId=<%=eventoId%>"><i class="fas fa-trash text-danger"></i></a>
<%				}%>
				</td>
				<td><%=alumno.getCodigoPersonal()%></td>	
				<td><%=nombreAlum%></td>
				<td>
<%				if(alumno.getEstado().equals("I")){%>
					<a href="guardar?CodigoAlumno=<%=alumno.getCodigoPersonal()%>&EventoId=<%=eventoId%>&Estado=A" class="btn btn-primary me-3">Mark Taken</a>
					<a href="../../datos_alumno/fotocredencial/datos?CodigoAlumno=<%=alumno.getCodigoPersonal()%>" class="btn btn-secondary">Update Photo</a>
<%				}else{%>
					<%=alumno.getFecha()%>
					<a href="guardar?CodigoAlumno=<%=alumno.getCodigoPersonal()%>&EventoId=<%=eventoId%>&Estado=I" class="btn btn-warning ms-3">Remove</a>
<%				}%>
				</td>						
			</tr>
<%		}%>
			</tbody>
		</table>	
	</div>
</body>
<script>
	function Borrar(eventoId, matricula, nombre) {
		if (confirm("Are you sure you want to eliminate this record?") == true) {
			document.location.href = "agregar?Accion=3&EventoId="+ eventoId+"&Matricula="+matricula+"&Nombre="+nombre;
		}
	}
	
	function cambiaEvento(){
		document.frmCredencial.submit();
	}
</script>
</html>