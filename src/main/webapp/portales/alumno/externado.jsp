<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.text.*"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.internado.spring.IntDormitorio"%>
<%@ page import="aca.internado.spring.IntAlumno"%>


<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ include file="../alumno/menu.jsp"%>

<%
	String codigoPersonal 		= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno 		= (String) session.getAttribute("codigoAlumno");

	AlumPersonal alumPersonal 	= (AlumPersonal) request.getAttribute("alumPersonal");
	
	String mensaje 		= (String) request.getAttribute("mensaje");
	String matricula 	= (String) request.getAttribute("matricula");	

    IntAlumno internado = (IntAlumno) request.getAttribute("internado");
    IntDormitorio dormitorio = (IntDormitorio) request.getAttribute("dormitorio");
    List<IntAlumno> lisRoomates = (List<IntAlumno>) request.getAttribute("lisRoomates");
    HashMap<String, AlumPersonal> mapaAlumnos = (HashMap<String, AlumPersonal>) request.getAttribute("mapaAlumnos");
	
%>
<body>
    <div class="container-fluid mt-1">	
		<div class="alert alert-success">
			<a href="previos"><i class="far fa-arrow-alt-circle-left fa-lg"></i></a>&nbsp;
			<span class="badge rounded-pill bg-dark">1E</span>
			<spring:message code="portal.alumno.solicitudExternado.SolicitudExternado"/> <small class="text-muted"> ( <%=codigoAlumno%> - <%=alumPersonal.getNombre()%>  <%=alumPersonal.getApellidoMaterno()%> <%=alumPersonal.getApellidoPaterno()%> )</small>
		</div>	
<%      if(!internado.getCodigoPersonal().equals("0")){%>
        <table class="table table-sm table-bordered">
            <thead class="table-dark">
                <tr>
                    <th width="5%">No.</th>
                    <th width="15%">Dormitory</th>
                    <th width="10%">Bedroom</th>
                    <th width="10%">Bed</th>
                    <th width="10%">Status</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>1</td>
                    <td><%=dormitorio.getNombre()%></td>
                    <td><%=internado.getCuartoId()%></td>
                    <td><%=internado.getOrden()%></td>
                    <td><%=internado.getEstado().equals("A")?"Active":"Not Registered"%></td>
                </tr>
            </tbody>
        </table>
        <br>
        <h3>Roomates</h3>
        <table class="table table-sm table-bordered w-50">
            <thead class="table-dark">
                <tr>
                    <th width="65%">Name</th>
                    <th width="35%">Bed</th>
                </tr>
            </thead>
            <tbody>
<%          for(IntAlumno roomate : lisRoomates){
                String nombreAlumno = "";
                String apellidoAlumno = "";
                if(mapaAlumnos.containsKey(roomate.getCodigoPersonal())){
                    nombreAlumno = mapaAlumnos.get(roomate.getCodigoPersonal()).getNombre();
                    apellidoAlumno = mapaAlumnos.get(roomate.getCodigoPersonal()).getApellidoMaterno();
                }
%>
                <tr>
                    <td><%=nombreAlumno%> <%=apellidoAlumno%></td>
                    <td><%=roomate.getOrden()%></td>
                </tr>
<%          }%>
            </tbody>
        </table>
<%      }else{%>
        <div class="alert alert-warning">
            There are no bording details registered. Please wait until their assigned or contact your advisor.
        </div>
<%      }%>
	</div>
</body>