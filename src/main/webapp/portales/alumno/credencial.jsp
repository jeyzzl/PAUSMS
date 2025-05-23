<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.matricula.spring.MatAlumno"%>
<%@ page import="aca.matricula.spring.MatEvento"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ include file="../alumno/menu.jsp"%>
<%
    String codigoPersonal 		= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno 		= (String) session.getAttribute("codigoAlumno");

    AlumPersonal alumPersonal 	= (AlumPersonal) request.getAttribute("alumPersonal");
    MatAlumno matAlumno 	    = (MatAlumno) request.getAttribute("matAlumno");
    MatEvento matEvento 	    = (MatEvento) request.getAttribute("matEvento");

    // System.out.println(matAlumno.getCodigoPersonal());
%>
<body>
<div class="container-fluid mt-1">
	<div class="alert alert-success">
		<a href="previos"><i class="far fa-arrow-alt-circle-left fa-lg"></i></a>&nbsp;
		<span class="badge rounded-pill bg-dark">1F</span> Registration Stations - ID Printing
		<small class="text-muted fs-6"> ( <%=codigoAlumno%> - <%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoMaterno()%> <%=alumPersonal.getApellidoPaterno()%>)</small>
	</div>
<% if(!matAlumno.getCodigoPersonal().equals("0")){%>
    <table class="table table-sm table-bordered w-75">
        <thead class="table-dark">
            <tr>
                <th width="10%">Event ID</th>
                <th width="20%">Event Title</th>
                <th width="20%">Status</th>
                <th>Date</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><%=matAlumno.getEventoId()%></td>
                <td><%=matEvento.getEventoNombre()%></td>
                <td><%=matAlumno.getEstado().equals("I")?"In progress":"Taken"%></td>
                <td><%=matAlumno.getFecha()%></td>
            </tr>
        </tbody>
    </table>
<%  }else{%>
    <div class="alert alert-warning">
        There are no ID Photo event registered. Please wait until it is assigned or contact your advisor.
    </div>
<%  }%>
</div>
</body>