<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@page import="aca.dherst.spring.DherstArchivo"%>
<%@page import="aca.dherst.spring.DherstAlumno"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<script type="text/javascript">
	function ActualizarArchivo(){	
  		document.frmArchivos.submit();
	}		
</script>
<%
    List<DherstArchivo> listArchivos = (List<DherstArchivo>) request.getAttribute("listArchivos");
    List<DherstAlumno> listAlumnos = (List<DherstAlumno>) request.getAttribute("listAlumnos");

    DherstArchivo archivo = (DherstArchivo) request.getAttribute("archivo");
    String folio = archivo.getFolio();
%>
<body>
<div class="container-fluid">
    <h2>Generate Letter</h2>
    <form name="frmArchivos" action="alumnos" mthod="post">
    <div class="alert alert-info d-flex align-items-center">
        Import file:
        <select id="folio" name="folio" class="form-select ms-1" style="width: 25rem;" onChange="javascritp:ActualizarArchivo()">
<%      for(DherstArchivo arch : listArchivos){
%>
            <option value="<%=arch.getFolio()%>" <%=arch.getFolio().equals(folio)?"selected":""%> > <%=arch.getNombre()%> </option>
<%      }%>
        </select>
    </div>
    </form>
<% if(listAlumnos != null && !listAlumnos.isEmpty()){%>
    <table class="table table-bordered">
        <thead class="table-info">
            <tr>
                <th width="5%">No.</th>
                <th width="10%">Student ID</th>
                <th width="10%">Slf. No.</th>
                <th>Name</th>
                <th>Surname</th>
                <th>Course</th>
                <th width="10%">Status</th>
                <th width="10%">Letter</th>
            </tr>
        </thead>
        <tbody>
<%      
        int row = 0;    
        for(DherstAlumno alumno : listAlumnos){
        row++;%>
            <tr>
                <td><%=row%></td>
                <td><%=alumno.getCodigoPersonal()%></td>
                <td><%=alumno.getSlfNo()%></td>
                <td><%=alumno.getNombre()%></td>
                <td><%=alumno.getApellido()%></td>
                <td><%=alumno.getPlanId()%></td>
                <td><%=alumno.getStatus().equals("T")?"Transfered":"Letter Generated"%></td>
                <td>
                    <a href="carta?folio=<%=alumno.getFolio()%>&codigoPersonal=<%=alumno.getCodigoPersonal()%>&slfNo=<%=alumno.getSlfNo()%>"><i class="fas fa-envelope text-primary"></i></a>
                </td>
            </tr>
<%          }%>
        </tbody>
    </table>
<% }else{%>
    <div class="alert alert-warning">
        No students transferred from this file.
    </div>
<% }%>
</div>
</body>