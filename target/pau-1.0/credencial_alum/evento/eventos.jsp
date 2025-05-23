<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.matricula.spring.MatEvento"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<body>
<%
    String estado           = (String) request.getAttribute("estado");
    String mensaje          = (String) request.getAttribute("mensaje");

    List<MatEvento> lisEventos = (List<MatEvento>) request.getAttribute("lisEventos");
    HashMap<String,String> mapAlumnosEnEvento = (HashMap<String,String>) request.getAttribute("mapAlumnosEnEvento");
%>
<div class="container-fluid">
	<h2>Registration Events</h2>
    <div class="alert alert-info">
        <a href="agregar" class="btn btn-primary">Add</a>
	</div>
<%  if(!mensaje.equals("") && mensaje != null){%>
    <div class="alert alert-warning">
        <%=mensaje%>
    </div>
<%  }%>
    <table class="table table-bordered">
        <thead class="table-info">
            <tr>
                <td>No.</td>
                <td>Action</td>
                <td>Event ID</td>
                <td>Load ID</td>
                <td>Event Title</td>
                <td>Status</td>
            </tr>
        </thead>
        <tbody>
<%  int row = 1;
    String numAlumnos = "0";
    for(MatEvento evento : lisEventos){
        numAlumnos = "0";
         if(mapAlumnosEnEvento.containsKey(evento.getEventoId())){
            numAlumnos = mapAlumnosEnEvento.get(evento.getEventoId());
        }
%>
            <tr>
                <td><%=row++%></td>
                <td>
                    <a href="agregar?EventoId=<%=evento.getEventoId()%>"><i class="fas fa-edit"></i></a>
<%      if(numAlumnos.equals("0")){%>
                    <a href="eliminar?EventoId=<%=evento.getEventoId()%>"><i class="fas fa-trash text-danger"></i></a>
<%      }%>
                </td>
                <td><%=evento.getEventoId()%></td>
                <td><%=evento.getCargaId()%></td>
                <td><%=evento.getEventoNombre()%></td>
                <td><%=evento.getEstado().equals("A")?"Active":"Inactive"%></td>
            </tr>
<%  }%>
        </tbody>
    </table>

</div>
</body>